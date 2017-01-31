package com.whisperers.whisper;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;

import Encryption.Encryptor;
import Encryption.KeyGenerator;
import Encryption.PrivateKey;
import Encryption.PublicKey;
import Encryption.RSAEncryptor;

/**
 * @author Florentin NOEL et Florian DAVID
 */

class ComTask extends AsyncTask<Whisper, Void, Whisper> {
    private static String TAG = "WHISPER";

    /***
     * Encryption
     ***/
    private PublicKey sharablePublicKey;
    private PrivateKey myPrivateKey;

    private PublicKey serverPublicKey;

    private Encryptor encryptor;

    /***
     * Réseau
     ***/
    private ObjectInputStream inS;
    private ObjectOutputStream outS;

    private Socket socket;

    String address = "192.168.43.78";
    //String address = "192.168.43.78";
    String s_port = "2000";

    String default_message = "hello";

    private BackgroundFragment tasksFragment;

    public ComTask(BackgroundFragment tasksFragment) {
        this.tasksFragment = tasksFragment;
        encryptor = new RSAEncryptor();
    }

    /**
     * Generate both public and private key
     */
    private void generateKeys() {
        KeyGenerator generator = new KeyGenerator();
        sharablePublicKey = generator.generatePublicKey();
        myPrivateKey = generator.generatePrivateKey();
    }

    /**
     * Ends the connection between
     *
     * @throws IOException
     */
    public void initConnection(String address, String s_port) throws IOException {
        generateKeys();

        int port = Integer.parseInt(s_port);

        socket = new Socket(InetAddress.getByName(address), port);
        try {
            // Envoi clé
            outS = new ObjectOutputStream(new BufferedOutputStream(socket.getOutputStream()));
            outS.writeObject(sharablePublicKey);
            outS.flush();

            // Reception clé
            inS = new ObjectInputStream(socket.getInputStream());
            serverPublicKey = (PublicKey) inS.readObject();
        } catch (SocketException | UnknownHostException e) {
            Log.w(TAG, "Exception de socket ou d'hote " + e);
        } catch (IOException | ClassNotFoundException e) {
            Log.w(TAG, e);
        }
    }

    /**
     * Encrypt and send a default_message to the server
     *
     * @param message : default_message to send
     * @throws IOException
     */
    public void encryptSendMessage(String message) throws IOException {

        if (serverPublicKey == null) {
            Log.i("MESSAGING", "Je n'ai pas recu la clé public du serveur");
            return;
        }

        // Envoi d'un default_message
        String messageCrypted = encryptor.encryptToString(message, serverPublicKey);

        Log.i("MESSAGING", "Sending message.");

        outS.writeObject(messageCrypted);
        outS.flush();
    }

    /**
     * Receive and decrypt a default_message sent by the server
     *
     * @return the received default_message sent by the server
     * @throws IOException
     */
    public String receiveDecryptMessage() throws IOException {

        if (myPrivateKey == null) {
            Log.i("MESSAGING", "Je n'ai pas recu la clé public du serveur");
            return "";
        }

        try {
            // Réception de la réponse
            String response = (String) inS.readObject();
            String decryptedReponse = encryptor.decrypt(response, myPrivateKey);
            Log.i("MESSAGING", "Réponse : " + decryptedReponse);

            return decryptedReponse;
        } catch (ClassNotFoundException e) {
            Log.w(TAG, e);
            return "";
        }
    }

    /**
     * Encrypts and sends a default_message, then receive and decrypt a default_message form the server
     *
     * @param message : default_message to send
     * @throws IOException
     */
    private void PingPong(String message) throws IOException {
        encryptSendMessage(message);
        String response = receiveDecryptMessage();
        Log.i("PINGPONG", "PingPong: " + response);
    }

    /**
     * Ends the connection between
     *
     * @throws IOException
     */
    public void closeConnection() throws IOException {
        socket.close();
    }

    @Override
    protected Whisper doInBackground(Whisper... whispers) {
        try {
            initConnection(address, s_port);

            encryptSendMessage(whispers[0].getContent());
            Whisper response = new Whisper(receiveDecryptMessage(),false);

            //closeConnection();
            return response;
        } catch (IOException e) {
            Log.w(TAG, e);
        }
        return null;
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(Whisper whisper) {
        tasksFragment.onPostExecute(whisper);
    }

    /**
     * Getter on the public key to share
     *
     * @return sharable public key
     */
    public PublicKey getSharablePublicKey() {
        return sharablePublicKey;
    }
}