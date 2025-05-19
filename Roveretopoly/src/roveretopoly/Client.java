/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package roveretopoly;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Scanner;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

/**
 * @author GelmiRusso
 */

public class Client extends JFrame implements Runnable {

    private String host;
    private static Board finestra;
    private int port;
    private static String nickname;
    static JLabel statusLabel;
    private boolean ready = false;
    Scanner sc;
    static PrintStream output;

    public static void main(String[] args) throws UnknownHostException, IOException {
        int port = getPortAvailable();
        Client application = new Client("127.0.0.1", port);
        System.out.println(port);                                                                                                                                                                                                                                                                                                                                                           
        application.run();
        application.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);                                                                                                                                                                                                                                                                                                                                                                                                                              
    }

    public static int getPortAvailable() {
        int basePort = 12345;
        int maxPorts = 5;

        for (int i = 0; i < maxPorts; i++) {
            int port = basePort + i;
            if (!isLockFileExists(port)) {
                return port;
            }
        }

        return -1;
    }

    public static boolean isLockFileExists(int port) {
        File lockFile = new File("server_lock_" + port + ".lock");
        return lockFile.exists();
    }


    private class ImagePanel extends JPanel {
        private BufferedImage backgroundImage;

        public ImagePanel(String imagePath) {
            try {
                backgroundImage = ImageIO.read(new File(imagePath));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (backgroundImage != null) {
                int panelWidth = getWidth();
                int panelHeight = getHeight();
                Image scaledImage = backgroundImage.getScaledInstance(panelWidth, panelHeight, Image.SCALE_SMOOTH);
                g.drawImage(scaledImage, 0, 0, null);
            }
        }
    }

    public Client(String host, int port) {
        this.host = host;
        this.port = port;
        setTitle("Menu");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1400, 964);
        setResizable(false);

        ImagePanel backgroundPanel = new ImagePanel("immagini/Menu.png");
        backgroundPanel.setBounds(0, 0, getWidth(), getHeight());
        add(backgroundPanel);

        setLayout(null);

        JTextField nicknameField = new JTextField();
        nicknameField.setBounds(543, 653, 200, 30);
        add(nicknameField);

        JButton sendButton = new JButton("Invia");
        sendButton.setBounds(756, 653, 80, 30);
        sendButton.setRolloverEnabled(false);
        add(sendButton);

        statusLabel = new JLabel("Default");
        statusLabel.setFont(new Font("Arial", Font.BOLD, 16));
        statusLabel.setForeground(Color.GRAY);

        int labelWidth = 400;
        int labelHeight = 20;
        statusLabel.setBounds(537, 693, labelWidth, labelHeight);
        add(statusLabel);

        ImageIcon playIcon = new ImageIcon("immagini/Pronto.png");
        JButton playButton = new JButton(playIcon);
        playButton.setText("");
        playButton.setBorder(null);
        add(playButton);
        playButton.setEnabled(false);

        int buttonWidth = playIcon.getIconWidth();
        int buttonHeight = playIcon.getIconHeight();
        playButton.setRolloverEnabled(false);
        playButton.setBounds(575, 723, buttonWidth, buttonHeight);

        setComponentZOrder(nicknameField, 0);
        setComponentZOrder(statusLabel, 1);
        setComponentZOrder(sendButton, 2);
        setComponentZOrder(playButton, 3);
        setComponentZOrder(backgroundPanel, 4);

        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Ottieni il nickname inserito dall'utente
                nickname = nicknameField.getText();

                if (!nickname.isEmpty()) {
                    nicknameField.setEnabled(false);
                    sendButton.setEnabled(false);
                    playButton.setEnabled(true);
                }
            }
        });
        playButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SwingUtilities.invokeLater(() -> {
                    if (!ready) {
                        sendMessage("Nickname:" + nickname);
                        ready = true;
                        playButton.setEnabled(false);
                        sendMessage("Ready!");
                    }
                });
            }
        });
        SwingUtilities.invokeLater(() -> {
            this.setVisible(true);
        });
    }

    public static void openTabellone(String nPlayer){
        SwingUtilities.invokeLater(() -> {
            JFrame clientFrame = (JFrame) SwingUtilities.getRoot(statusLabel); // Ottieni la finestra Client
            clientFrame.dispose();
            finestra = new Board(Integer.valueOf(nPlayer));
            finestra.setVisible(true);
        });
        
    }

    public void run() {
        try {
            // connect client to server
            Socket client = new Socket(host, port);
            System.out.println("Client successfully connected to server!");
            new Thread(new ReceivedMessagesHandler(client.getInputStream())).start();
            sc = new Scanner(System.in);
            System.out.println("Send messages: ");
            output = new PrintStream(client.getOutputStream());
            while (sc.hasNextLine()) {
                sendMessage(nickname + ": " + sc.nextLine());
            }
            output.close();
            sc.close();
            client.close();
        } catch (Exception e) {
        }
    }

    public static void sendMessage(String msg) {
        output.println(msg);
    }

    public static void reloadMoney(String [] array, ArrayList<String> colors){
        Board.updateStringList(array, colors);
    }

    public static void setNickname(String nick){
        nickname = nick;
    }
    
    public static String getNickname(){
        return nickname;
    }
}

class ReceivedMessagesHandler implements Runnable {

    private InputStream server;

    public ReceivedMessagesHandler(InputStream server) {
        this.server = server;
    }

    @Override
    public void run() {
        // receive server messages and print out to screen
        Scanner s = new Scanner(server);
        while (s.hasNextLine()) {
            String msg = s.nextLine();
            String[] splittedMessage = msg.split(":");
            switch (splittedMessage[0]) {
                case "PlayerNumber":
                    Client.statusLabel.setText("Giocatori in attesa " + splittedMessage[1] + "/6 | Giocatori pronti: " + splittedMessage[3]);
                    break;
                case "OpenTabellone":
                    Client.openTabellone(splittedMessage[1]);
                    break;
                case "ReloadMoney":
                    SwingUtilities.invokeLater(() -> {
                        String [] splittedMessageReduced = new String [splittedMessage.length -1];
                        ArrayList <String> colors = new ArrayList<>(); 
                        for(int i = 1; i < splittedMessage.length; i++){
                            splittedMessageReduced[i-1] = splittedMessage[i];
                        }
                        for(int i = 0; i < splittedMessageReduced.length; i = i + 3){
                            splittedMessageReduced[i] = splittedMessageReduced[i] + ": " + splittedMessageReduced[i+1];
                            colors.add(splittedMessageReduced[i+2]);
                        }
                        String [] splittedMessageMoreReduced = new String [splittedMessageReduced.length / 3]; 
                        for(int i = 0; i < splittedMessageMoreReduced.length; i++){
                            splittedMessageMoreReduced[i] = splittedMessageReduced[i*3] + "M";
                        }
                        Client.reloadMoney(splittedMessageMoreReduced, colors);
                    });
                    break;
                
                case "StartTurn":
                    SwingUtilities.invokeLater(() -> {
                        Board.enableThrowDiceButton(true);
                    });    
                    break;
                case "DiceValues":
                    int [] splittedMessageReduced = new int [splittedMessage.length -1]; 
                    for(int i = 1; i < splittedMessage.length; i++){
                        splittedMessageReduced[i-1] = Integer.valueOf(splittedMessage[i]);
                    }
                    Board.diceThrowed(splittedMessageReduced);
                    break;
                case "MovePawn":
                    Board.movePawn(Integer.valueOf(splittedMessage[1]), Integer.valueOf(splittedMessage[2]));
                    break;
                case "ContinueTurn": 
                    Board.checkProperty(Integer.valueOf(splittedMessage[1]), splittedMessage[2]);
                    Board.enableEndTurnButton(true);
                    break;
                case "BuyProperty":
                    Board.buyProperty(Integer.valueOf(splittedMessage[1]), Integer.valueOf(splittedMessage[2]), splittedMessage[3]);
                    break;
                case "BuyHouse":
                    Board.buyHouse(Integer.valueOf(splittedMessage[1]), Integer.valueOf(splittedMessage[2]), splittedMessage[3]);
                    break;
                case "SellHouse":
                    Board.sellHouse(Integer.valueOf(splittedMessage[1]));
                    break;
                case "ShowMessage":
                    Board.showMessage(splittedMessage[1]);
                    break;
                case "ShowGameUpdate":
                    Board.showGameUpdate(splittedMessage[1]);
                    break;
                case "PropertyBought":
                    Board.propertyBought(Integer.valueOf(splittedMessage[1]), splittedMessage[2]);
                    break;
                case "HouseBought":
                    Board.houseBought(Integer.valueOf(splittedMessage[1]));
                    break;
                case "MortgageProperty":
                    Board.mortgageProperty(Integer.valueOf(splittedMessage[1]));
                    break;
                case "KillClient":
                    Board.killPlayer(splittedMessage[1], Integer.valueOf(splittedMessage[2]));
                    break;
                case "KillBoard":
                    Board.killBoard();
                    break;
                case "LeaderboardList":
                    JFrame frameLeaderboard = new JFrame("Classifica");
                    JPanel panel = new JPanel();
                    panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
                    
                    for (int i = splittedMessage.length-1; i > 0; i--) {
                        JLabel label = new JLabel(i + ") " + splittedMessage[i]);
                        panel.add(label);
                    }

                    frameLeaderboard.add(panel);
                    frameLeaderboard.setSize(300, 200);
                    frameLeaderboard.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
                    frameLeaderboard.setVisible(true);
                    break;
                default:
                    System.out.println("ERRORE, MESSAGGIO NON IDENTIFICATO!!!: " + msg);
                    break;
            }
        }
        s.close();
    }
}
