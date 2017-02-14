/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Client;

import MessageExchange.MessageReceiver;
import MessageExchange.MessageDisplayer;
import static MessageExchange.MessageDisplayer.DEFAULT_TEXT;
import MessageExchange.MessageSender;
import MessageExchange.MessageWriter;
import MessageExchange.Whisper;

import java.awt.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import javax.swing.*;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

/**
 *
 * @author flodavid
 */
public class ClientGUI extends javax.swing.JFrame implements ActionListener, MessageDisplayer, MessageWriter {
    private final static Logger LOGGER = Logger.getLogger(ClientGUI.class);
    
    private MessageSender sender;
    private String SENDER_NAME = "SENDER_NAME";
    private String lastReceived;
    private String[] WELCOME_MESSAGES;
    private boolean exchangeStarted;
        
    /**
     * Creates a new chat window
     * @param isWaitingConnection : Defines if the application is waiting for connection
     * (must wait for a client connection ) or if it waits for the server to start
     */
    public ClientGUI(boolean isWaitingConnection) {
        exchangeStarted= false;

        if (isWaitingConnection){
            WELCOME_MESSAGES = new String[]{
                "Tentatives de connexion au serveur en cours ... ", "========================="};
        } else {
            WELCOME_MESSAGES = new String[]{
                "<html><i>Attente de la connexion d'un contact</i></html>"};
            try {
                Enumeration<NetworkInterface> n = NetworkInterface.getNetworkInterfaces();
                for (; n.hasMoreElements();)
                {
                    NetworkInterface e = n.nextElement();

                    Enumeration<InetAddress> a = e.getInetAddresses();
                    for (; a.hasMoreElements();)
                    {
                        InetAddress addr = a.nextElement();
                        
                        if (addr.isSiteLocalAddress() && !addr.getHostAddress().endsWith(".0.1")) {
                            String[] tmpWELCOME_MESSAGES = WELCOME_MESSAGES;
                            WELCOME_MESSAGES = new String[WELCOME_MESSAGES.length +1];
                            System.arraycopy(tmpWELCOME_MESSAGES, 0, WELCOME_MESSAGES, 0, tmpWELCOME_MESSAGES.length-1);
                            WELCOME_MESSAGES[WELCOME_MESSAGES.length-2]= "<html>Adresse de connexion : <u>" + addr.getHostAddress() + "</u></html>";
                        }
                    }
                }
                    
                WELCOME_MESSAGES[WELCOME_MESSAGES.length-1] = "=========================";
                
            } catch (SocketException se) {
                WELCOME_MESSAGES = new String[]{"<html><b style=\"color:#FF0000\";>Impossible de trouver l'hôte<b><html>"};
            }
        }
        
        initComponents();
//        messagesList.setDropMode(DropMode.INSERT);
        initPlaceHolder();
        
        if (isWaitingConnection) {
            setLocation(getWidth(), 0);
        }
    }
    
    private void initPlaceHolder() {
        messageField.setForeground(Color.GRAY);
        messageField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (messageField.getText().equals(DEFAULT_TEXT)) {
                    messageField.setText("");
                    messageField.setForeground(Color.BLACK);
                }
            }
            @Override
            public void focusLost(FocusEvent e) {
                if (messageField.getText().isEmpty()) {
                    resetMessageField();
                }
            }
        });
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        tabPanel = new javax.swing.JPanel();
        messagesScrollPane = new javax.swing.JScrollPane();
        messagesList = new javax.swing.JList<>();
        messagePanel = new javax.swing.JPanel();
        messageField = new javax.swing.JTextField();
        cancelButton = new javax.swing.JButton();
        jSplitPane1 = new javax.swing.JSplitPane();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jMenu3 = new javax.swing.JMenu();
        author_1Item = new javax.swing.JMenuItem();
        author_2Item = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Whisper");

        tabPanel.setLayout(new java.awt.BorderLayout());

        resetMessages();
        messagesList.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                messagesListMouseDragged(evt);
            }
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                messagesListMouseMoved(evt);
            }
        });
        messagesList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                messagesListMouseClicked(evt);
            }
        });
        messagesScrollPane.setViewportView(messagesList);

        tabPanel.add(messagesScrollPane, java.awt.BorderLayout.CENTER);

        messagePanel.setLayout(new java.awt.BorderLayout());

        messageField.setText(DEFAULT_TEXT);
        messageField.setToolTipText("Message à envoyer");
        messageField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                messageFieldActionPerformed(evt);
            }
        });
        messageField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                messageFieldKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                messageFieldKeyTyped(evt);
            }
        });
        messagePanel.add(messageField, java.awt.BorderLayout.CENTER);

        cancelButton.setText("Cancel");
        cancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelButtonActionPerformed(evt);
            }
        });
        messagePanel.add(cancelButton, java.awt.BorderLayout.LINE_START);

        jButton1.setText("Resend");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jSplitPane1.setRightComponent(jButton1);

        jButton2.setText("Send");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jSplitPane1.setLeftComponent(jButton2);

        messagePanel.add(jSplitPane1, java.awt.BorderLayout.LINE_END);

        tabPanel.add(messagePanel, java.awt.BorderLayout.PAGE_END);

        jTabbedPane1.addTab("<html><i>SENDER_NAME</i></html>", tabPanel);

        getContentPane().add(jTabbedPane1, java.awt.BorderLayout.CENTER);

        jMenu1.setText("Se connecter");

        jMenuItem1.setText("Connexion à l'aut'");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        jMenuItem2.setText("Attendre la connexion");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem2);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("A propos");
        jMenuBar1.add(jMenu2);

        jMenu3.setText("Contacter les auteurs");

        author_1Item.setText("Florentin Noël");
        author_1Item.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                author_1ItemActionPerformed(evt);
            }
        });
        jMenu3.add(author_1Item);

        author_2Item.setText("Florian David");
        author_2Item.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                author_2ItemActionPerformed(evt);
            }
        });
        jMenu3.add(author_2Item);

        jMenuBar1.add(jMenu3);

        setJMenuBar(jMenuBar1);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void messageFieldKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_messageFieldKeyTyped
        if (messageField.getText().equals(DEFAULT_TEXT)) messageField.setText("");
    }//GEN-LAST:event_messageFieldKeyTyped

    private void messageFieldKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_messageFieldKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) sendMessage();
    }//GEN-LAST:event_messageFieldKeyPressed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void messageFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_messageFieldActionPerformed
        if (messageField.getText().equals(DEFAULT_TEXT)) messageField.setText("");
    }//GEN-LAST:event_messageFieldActionPerformed

    private void messagesListMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_messagesListMouseClicked
        Whisper whisp= (Whisper)messagesList.getSelectedValue();
        SimpleDateFormat formatter = new SimpleDateFormat("H:mm");
        
        messagesList.setToolTipText(formatter.format(whisp.getTime()));
    }//GEN-LAST:event_messagesListMouseClicked

    private void messagesListMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_messagesListMouseMoved
        ListModel m = messagesList.getModel();

        SimpleDateFormat formatter = new SimpleDateFormat("H:mm");

        int index = messagesList.locationToIndex(evt.getPoint());
        Date date= ((Whisper)m.getElementAt(index)).getTime();
        if( index>-1 ) {
            messagesList.setToolTipText(formatter.format(date));
        }
    }//GEN-LAST:event_messagesListMouseMoved

    private void messagesListMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_messagesListMouseDragged
        // TODO add your handling code here:
    }//GEN-LAST:event_messagesListMouseDragged

    private void author_2ItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_author_2ItemActionPerformed
        Desktop desktop;
        if (Desktop.isDesktopSupported()) {
            desktop = Desktop.getDesktop();
            try {
                desktop.mail(new URI("mailto:fl.david.53@gmail.com?subject=Whisper"));
            } catch (IOException | URISyntaxException ex) {
                LOGGER.log(Level.ERROR, "Impossible de créer un mail", ex);
            }
        }
    }//GEN-LAST:event_author_2ItemActionPerformed

    private void author_1ItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_author_1ItemActionPerformed
        Desktop desktop;
        if (Desktop.isDesktopSupported()) {
            desktop = Desktop.getDesktop();
            try {
                desktop.mail(new URI("mailto:flo.noel53@gmail.com?subject=Whisper"));
            } catch (IOException | URISyntaxException ex) {
                LOGGER.log(Level.ERROR, "Impossible de créer un mail", ex);
            }
        }
    }//GEN-LAST:event_author_1ItemActionPerformed

    private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelButtonActionPerformed
        resetMessageField();
    }//GEN-LAST:event_cancelButtonActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        if ( !(messageField.getText().isEmpty() || messageField.getText().equals(DEFAULT_TEXT)) ) sendMessage();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        resendMessage();
    }//GEN-LAST:event_jButton1ActionPerformed

    /**
     *
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        Object source= e.getSource();

        if(source.getClass() == MessageReceiver.class) {
           messagesList.add(e.getActionCommand(), this);
        }
    }
    
    @Override
    public void showMessage(String message) {
        addMessage(new Whisper(message));
        lastReceived= message;
    }

    @Override
    public void showMessage(Whisper message)     {
        if (message.hasBeenSendByMe()) {
            addMessage(message);
        } else {
            updateSenderName(message.getSenderName());
            addMessage(message);
            lastReceived= message.getContent();
        }
    }

    @Override
    public void setMessageSender(MessageSender msgSender) {
        sender= msgSender;
    }

    @Override
    public void startSending() {
        setVisible(true);
    }
    

    @Override
    public void stopSending() {
        setVisible(false);
    }
    
    @Override
    public void chatStarted() {
        exchangeStarted= true;
        startConversation();
        resetMessages();
    }
    
    private void resetMessageField() {
        if (messageField.hasFocus()) {
            messageField.setForeground(Color.BLACK);
            messageField.setText("");
        } else {
            messageField.setForeground(Color.GRAY);
            messageField.setText(DEFAULT_TEXT);
        }
    }
    
    private void resetMessages() {
        Whisper[] whispers = new Whisper[WELCOME_MESSAGES.length];
        for (int i= 0; i < WELCOME_MESSAGES.length; ++i) {
            whispers[i]= new Whisper(WELCOME_MESSAGES[i], Whisper.getSYSTEM());
        }
        setMessages(whispers);
    }
    
    private void startConversation() {
        WELCOME_MESSAGES = new String[]{"Début de la conversation",
            "======================"};
    }
    
   private void setMessages(Whisper[] messages) {
        messagesList.setModel(new javax.swing.AbstractListModel<Whisper>() {
            Whisper[] strings = messages;
            @Override
            public int getSize() { return strings.length; }
            @Override
            public Whisper getElementAt(int i) { return strings[i]; }
        });
   }
        
    private void addMessage(Whisper message) {
        javax.swing.AbstractListModel<Whisper> listModel = (javax.swing.AbstractListModel<Whisper>)messagesList.getModel();
        Whisper[] messages= new Whisper[listModel.getSize() + 1];
        
        int i= 0;
        for (; i < listModel.getSize(); ++i) {
            messages[i]= listModel.getElementAt(i);
        }
        messages[i]= message;
        
        setMessages(messages);
    }
    
    public void sendMessage() {
        try {
            if (exchangeStarted) {
                Whisper whisp= new Whisper(messageField.getText());
                sender.sendMessage(messageField.getText());
                addMessage(whisp);
                resetMessageField();
            } else {
                addMessage(new Whisper("<html><i style=\"color:#FF0000\";>Vous devez attendre la connexion d'un contact</i></html>", Whisper.getSYSTEM()));
            }
        } catch (IOException e) {
            System.err.println("Impossible d'envoyer le message" + e.getMessage());
        }
    }
    
    public void resendMessage() {
        try {
            if (exchangeStarted) {
                if (lastReceived != null) {
                    if (!"".equals(lastReceived)) {
                        sender.sendMessage(lastReceived);
                        Whisper whisp= new Whisper(lastReceived);
                        addMessage(whisp);
                        lastReceived= "";
                    } else addMessage(new Whisper("<html><i style=\"color:#FF0000\";>Vous avez déjà renvoyé le dernier message reçu</i></html>", Whisper.getSYSTEM()));
                } else addMessage(new Whisper("<html><i style=\"color:#FF0000\";>Vous n'avez pas encore reçu de message</i></html>", Whisper.getSYSTEM()));
            } else addMessage(new Whisper("<html><i style=\"color:#FF0000\";>Vous devez attendre la connexion d'un contact</i></html>", Whisper.getSYSTEM()));
        } catch (IOException e) {
            System.err.println("Impossible d'envoyer le message" + e.getMessage());
        }
    }
    
    public void updateSenderName(String senderName) {
        
        SENDER_NAME= senderName;
        jTabbedPane1.setTitleAt(jTabbedPane1.getSelectedIndex(), SENDER_NAME);
        jTabbedPane1.setFont(new Font("Lobster Two",Font.ITALIC,18));
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        //address = "192.168.99.107";
        
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ClientGUI.class.getName()).log(java.util.logging.Level.SEVERE, "Class non trouvée", ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ClientGUI.class.getName()).log(java.util.logging.Level.SEVERE, "Erreur d'instanciation", ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ClientGUI.class.getName()).log(java.util.logging.Level.SEVERE, "Accès illégal !", ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ClientGUI.class.getName()).log(java.util.logging.Level.SEVERE, "Les éléments d'interface ne sont pas supportés", ex);
        }
        //</editor-fold>

        
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new ClientGUI(false).setVisible(true);
            
//                ClientGUI.client.stopChat();
//                client.closeConnection();
        });
        
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem author_1Item;
    private javax.swing.JMenuItem author_2Item;
    private javax.swing.JButton cancelButton;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTextField messageField;
    private javax.swing.JPanel messagePanel;
    private javax.swing.JList<Whisper> messagesList;
    private javax.swing.JScrollPane messagesScrollPane;
    private javax.swing.JPanel tabPanel;
    // End of variables declaration//GEN-END:variables

}
