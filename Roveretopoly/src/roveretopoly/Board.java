/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package roveretopoly;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.util.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author GelmiRusso
 */

public class Board extends JFrame {
    private static JPanel contentPane;
    private JPanel middleContentPane;
    private JPanel middleUpperContentPane;
    private static JButton throwDiceButton = new JButton("Lancia!");
    private static JButton buyPropertyButton = new JButton("Acquista proprietà");
    private static JButton mortgagePropertyButton = new JButton("Ipoteca proprietà");
    private static JButton leaderboardButton = new JButton("Classifica");
    private static JButton endTurnButton = new JButton("Termina turno");
    private static JButton buyHouseButton = new JButton("Compra casa");
    private static JButton sellHouseButton = new JButton("Vendi casa");
    private static JList<String> stringList;
    private static ImageIcon imageIcon;
    private static JLabel imageLabel;
    private static ImageIcon leftDiceImageIcon;
    private static ImageIcon rightDiceImageIcon;
    private static JLabel leftDiceImageLabel;
    private static JLabel rightDiceImageLabel;
    private static Color colorsList [] = {Color.RED, Color.BLUE, Color.GREEN, Color.YELLOW, Color.PINK, Color.GRAY};
    private static int horizontalArrangement [][] = {{0,0},{13,0},{0,13},{13,13},{0,26},{13,26}};
    private static int verticalArrangement [][] = {{0,0},{13,0},{26,0},{0,13},{13,13},{26,13}};
    private static ArrayList<Pawn> pawns = new ArrayList<>();
    private static ArrayList<Property> propertyList = new ArrayList<>();
    private JPanel overlayPanel;
    private JLayeredPane layeredPane;
    private static JLabel ownerLabel;
    private static JLabel updateLabel;
    public static int propertyOpened = 0;

    public Board(int nPlayer) {
        setTitle("Tabellone");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1400, 964);
        setResizable(false);

        propertyList.add(new Property("Via", "", 1, 420, 420, 0, new int[]{}, 0, 0, false, false));
        propertyList.add(new Property("Burger King", "Marrone", 2, 368, 420, 60, new int[]{2,10,30,90,160,250}, 50, 30, true, true));
        propertyList.add(new Property("Probabilità", "", 3, 331, 420, 0, new int[]{}, 0, 0, false, false));
        propertyList.add(new Property("MC Donald's", "Marrone", 4, 294, 420, 60, new int[]{4,20,60,180,320,450}, 50, 30, true, true));
        propertyList.add(new Property("Tassa patrimoniale", "", 5, 256, 420, 0, new int[]{}, 0, 0, false, false));
        propertyList.add(new Property("Fermata Stazione", "Nero", 6, 220, 420, 200, new int[]{25,50,100,200}, 0, 100, true, false));
        propertyList.add(new Property("Luxottica", "Azzurro", 7, 182, 420, 100, new int[]{6,30,90,270,400,550}, 50, 50, true, true));
        propertyList.add(new Property("Imprevisti", "", 8, 145, 420, 0, new int[]{}, 0, 0, false, false));
        propertyList.add(new Property("Bartolini", "Azzurro", 9, 108, 420, 100, new int[]{6,30,90,270,400,550}, 50, 50, true, true));
        propertyList.add(new Property("Metalsistem", "Azzurro", 10, 70, 420, 120, new int[]{8,40,100,300,450,600}, 50, 60, true, true));
        propertyList.add(new Property("Transito", "", 11, 15, 420, 0, new int[]{}, 0, 0, false, false));
        propertyList.add(new Property("Cfp Veronesi", "Fucsia", 12, 7, 369, 140, new int[]{10,50,150,450,625,750}, 100, 70, true, true));
        propertyList.add(new Property("Dolomiti Energia", "Bianco", 13, 7, 331, 150, new int[]{}, 0, 75, true, false));
        propertyList.add(new Property("Liceo Antonio Rosmini", "Fucsia", 14, 7, 294, 140, new int[]{10,50,150,450,625,750}, 100, 70, true, true));
        propertyList.add(new Property("ITT Marconi", "Fucsia", 15, 7, 257, 160, new int[]{10,50,150,450,625,750}, 100, 80, true, true));
        propertyList.add(new Property("Fermata Marconi", "Nero", 16, 7, 219, 200, new int[]{25,50,100,200}, 0, 100, true, false));
        propertyList.add(new Property("Eurospar", "Arancio", 17, 7, 182, 180, new int[]{14,70,200,550,750,900}, 100, 90, true, true));
        propertyList.add(new Property("Probabilità", "", 18, 7, 144, 0, new int[]{}, 0, 0, false, false));
        propertyList.add(new Property("Conad", "Arancio", 19, 7, 108, 180, new int[]{14,70,200,550,750,900}, 100, 90, true, true));
        propertyList.add(new Property("Iper Orvea", "Arancio", 20, 7, 69, 200, new int[]{16,80,220,600,800,1000}, 100, 100, true, true));
        propertyList.add(new Property("Posteggio Gratuito", "", 21, 7, 7, 0, new int[]{}, 0, 0, false, false));
        propertyList.add(new Property("Putipù", "Rosso", 22, 69, 7, 220, new int[]{18,90,250,700,875,1050}, 150, 110, true, true));
        propertyList.add(new Property("Imprevisti", "", 23, 107, 7, 0, new int[]{}, 0, 0, false, false));
        propertyList.add(new Property("La Torretta", "Rosso", 24, 144, 7, 220, new int[]{18,90,250,700,875,1050}, 150, 110, true, true));
        propertyList.add(new Property("La Mia Africa", "Rosso", 25, 182, 7, 240, new int[]{20,100,300,750,925,1100}, 150, 120, true, true));
        propertyList.add(new Property("Fermata Rosmini", "Nero", 26, 219, 7, 200, new int[]{25,50,100,200}, 0, 100, true, false));
        propertyList.add(new Property("Parco del Brione", "Giallo", 27, 256, 7, 260, new int[]{22,110,330,800,975,1150}, 150, 130, true, true));
        propertyList.add(new Property("Giardini Perlasca", "Giallo", 28, 294, 7, 260, new int[]{22,110,330,800,975,1150}, 150, 130, true, true));
        propertyList.add(new Property("Trentino Trasporti", "Bianco", 29, 331, 7, 150, new int[]{}, 0, 75, true, false));
        propertyList.add(new Property("Bosco della Città", "Giallo", 30, 368, 7, 280, new int[]{24,120,360,850,1025,1200}, 150, 140, true, true));
        propertyList.add(new Property("Vai in Prigione", "", 31, 420, 7, 0, new int[]{}, 0, 0, false, false));
        propertyList.add(new Property("Viale Trento", "Verde", 32, 420, 65, 300, new int[]{26,130,390,900,1100,1275}, 200, 150, true, true));
        propertyList.add(new Property("Corso Bettini", "Verde", 33, 420, 108, 300, new int[]{26,130,390,900,1100,1275}, 200, 150, true, true));
        propertyList.add(new Property("Probabilità", "", 34, 420, 144, 0, new int[]{}, 0, 0, false, false));
        propertyList.add(new Property("Corso Antonio Rosmini", "Verde", 35, 420, 182, 320, new int[]{28,150,450,1000,1200,1400}, 200, 160, true, true));
        propertyList.add(new Property("Fermata Via Paoli", "Nero", 36, 420, 219, 200, new int[]{25,50,100,200}, 0, 100, true, false));
        propertyList.add(new Property("Imprevisti", "", 37, 420, 256, 0, new int[]{}, 0, 0, false, false));
        propertyList.add(new Property("Viale dei Giardini", "Blu", 38, 420, 294, 350, new int[]{35,175,500,1100,1300,1500}, 200, 175, true, true));
        propertyList.add(new Property("Tassa di Lusso", "", 39, 420, 332, 0, new int[]{}, 0, 0, false, false));
        propertyList.add(new Property("MART", "Blu", 40, 420, 368, 400, new int[]{50,200,600,1400,1700,2000}, 200, 200, true, true));
        
        layeredPane = new JLayeredPane();
        layeredPane.setPreferredSize(new Dimension(1400, 964));
        // Creazione degli angoli
        Angle angolo1 = new Angle(0, 0, "1");
        Angle angolo2 = new Angle(800, 0, "2");
        Angle angolo3 = new Angle(0, 800, "3");
        Angle angolo4 = new Angle(800, 800, "4");

        layeredPane.add(angolo1, JLayeredPane.DEFAULT_LAYER);
        layeredPane.add(angolo2, JLayeredPane.DEFAULT_LAYER);
        layeredPane.add(angolo3, JLayeredPane.DEFAULT_LAYER);
        layeredPane.add(angolo4, JLayeredPane.DEFAULT_LAYER);
        int i = 1;
        for (int j = 8; j >= 0; j--) {
            Box casella = new Box(125 + (j * 75), 800, 0, i + "", i+1);
            add(casella);
            layeredPane.add(casella, JLayeredPane.DEFAULT_LAYER);
            i++;
        }
        for (int j = 8; j >= 0; j--) {
            Box casella = new Box(0, 125 + (j * 75), 90, i + "", i+2);
            add(casella);
            layeredPane.add(casella, JLayeredPane.DEFAULT_LAYER);
            i++;
        }
        for (int j = 0; j < 9; j++) {
            Box casella = new Box(125 + (j * 75), 0, 180, i + "", i+3);
            add(casella);
            layeredPane.add(casella, JLayeredPane.DEFAULT_LAYER);
            i++;
        }
        for (int j = 0; j < 9; j++) {
            Box casella = new Box(800, 125 + (j * 75), 270, i + "", i+4);
            add(casella);
            layeredPane.add(casella, JLayeredPane.DEFAULT_LAYER);
            i++;
        }

        contentPane = new JPanel();
        contentPane.setBounds(925, 0, 459, 925);
        contentPane.setLayout(new BorderLayout());
        contentPane.setBorder(new LineBorder(Color.decode("#d93a33"), 10));

        middleContentPane = new JPanel();
        middleUpperContentPane = new JPanel();
        middleUpperContentPane.setLayout(new FlowLayout(FlowLayout.CENTER));
        middleContentPane.setBounds(263, 350, 400, 260);
        middleContentPane.setLayout(new BorderLayout());
        

        stringList = new JList<>();

        JPanel panel = new JPanel(new BorderLayout());
        JPanel upperPanel = new JPanel(new BorderLayout());
        upperPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        ownerLabel = new JLabel("Benvenuto a ROVERETOPOLY!");
        updateLabel = new JLabel("Qui verranno mostrati gli aggiornamenti della partita!");
        ownerLabel.setFont(new Font("Arial", Font.BOLD, 16));
        updateLabel.setFont(new Font("Arial", Font.BOLD, 16));
        ownerLabel.setForeground(Color.GRAY);
        updateLabel.setForeground(Color.GRAY);
        upperPanel.add(ownerLabel, BorderLayout.NORTH);
        JPanel partialPanel = new JPanel(new BorderLayout());
        JPanel abovePartialPanel = new JPanel(new BorderLayout());
        abovePartialPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        JPanel belowPartialPanel = new JPanel(new BorderLayout());
        belowPartialPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        JPanel middlePartialPanel = new JPanel(new BorderLayout());
        middlePartialPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        JPanel middlePanel = new JPanel(new BorderLayout());

        imageIcon = new ImageIcon("Immagini/Proprietà/1.png");
        imageLabel = new JLabel(imageIcon);
        leftDiceImageIcon = new ImageIcon("Immagini/dado/diceFace6.png");
        rightDiceImageIcon = new ImageIcon("Immagini/dado/diceFace6.png");
        leftDiceImageLabel = new JLabel(leftDiceImageIcon);
        rightDiceImageLabel = new JLabel(rightDiceImageIcon);

        upperPanel.add(imageLabel, BorderLayout.SOUTH);
        buyPropertyButton.setPreferredSize(new Dimension(200, 30));
        mortgagePropertyButton.setPreferredSize(new Dimension(200, 30));
        leaderboardButton.setPreferredSize(new Dimension(200, 30));
        endTurnButton.setPreferredSize(new Dimension(200, 30));
        buyHouseButton.setPreferredSize(new Dimension(200, 30));
        sellHouseButton.setPreferredSize(new Dimension(200, 30));
        throwDiceButton.setBackground(Color.lightGray);
        buyPropertyButton.setBackground(Color.lightGray);
        mortgagePropertyButton.setBackground(Color.lightGray);
        leaderboardButton.setBackground(Color.lightGray);
        endTurnButton.setBackground(Color.lightGray);
        buyHouseButton.setBackground(Color.lightGray);
        sellHouseButton.setBackground(Color.lightGray);

        abovePartialPanel.add(buyPropertyButton, BorderLayout.WEST);
        abovePartialPanel.add(mortgagePropertyButton, BorderLayout.EAST);
        belowPartialPanel.add(leaderboardButton, BorderLayout.WEST);
        belowPartialPanel.add(endTurnButton, BorderLayout.EAST);
        middlePartialPanel.add(buyHouseButton, BorderLayout.WEST);
        middlePartialPanel.add(sellHouseButton, BorderLayout.EAST);
        partialPanel.add(abovePartialPanel, BorderLayout.NORTH);
        partialPanel.add(middlePartialPanel, BorderLayout.CENTER);
        partialPanel.add(belowPartialPanel, BorderLayout.SOUTH);
        panel.add(partialPanel, BorderLayout.SOUTH);
        panel.add(upperPanel, BorderLayout.CENTER);
        middleUpperContentPane.add(updateLabel, BorderLayout.CENTER);
        middlePanel.add(middleUpperContentPane, BorderLayout.NORTH);
        middlePanel.add(leftDiceImageLabel, BorderLayout.WEST);
        middlePanel.add(rightDiceImageLabel, BorderLayout.EAST);
        middlePanel.add(throwDiceButton, BorderLayout.SOUTH);
        throwDiceButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                enableThrowDiceButton(false);
                Client.sendMessage("DiceThrowed");
            }
        });

        buyPropertyButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Client.sendMessage("BuyPropertyPressed");
            }
        });
        
        mortgagePropertyButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Client.sendMessage("MortgagedPropertyPressed:" + propertyOpened);
            }
        });
        leaderboardButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Client.sendMessage("ShowLeaderboard:" + Client.getNickname());
            }
        });
        endTurnButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                enableEndTurnButton(false);
                enableBuyPropertyButton(false);
                enableMortgagePropertyButton(false);
                enableBuyHouseButton(false);
                enableSellHouseButton(false);
                Client.sendMessage("EndTurn");
            }
        });
        buyHouseButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                enableBuyHouseButton(false);
                Client.sendMessage("BuyHousePressed:" + propertyOpened);
            }
        });
        sellHouseButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                enableSellHouseButton(false);
                Client.sendMessage("SellHousePressed:" + propertyOpened);
            }
        });
        enableThrowDiceButton(false);
        enableBuyPropertyButton(false);
        enableMortgagePropertyButton(false);
        enableShowLeaderboardButton(true);
        enableEndTurnButton(false);
        enableBuyHouseButton(false);
        enableSellHouseButton(false);
        

        panel.add(new JScrollPane(stringList), BorderLayout.NORTH);

        contentPane.add(panel, BorderLayout.CENTER);
        middleContentPane.add(middlePanel, BorderLayout.CENTER);

        add(contentPane);
        add(middleContentPane);

        overlayPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
            }
        };
        overlayPanel.setLayout(null);
        overlayPanel.setBounds(0, 0, 1400, 964);
        overlayPanel.setOpaque(false);
        

        for(int j = 0; j < nPlayer; j++){
            pawns.add(new Pawn(420+horizontalArrangement[j][0], 420+horizontalArrangement[j][1], colorsList[j]));
            pawns.get(j).setBounds(0, 0, 1400, 964);
            overlayPanel.add(pawns.get(j));   
        }
        
        layeredPane.add(overlayPanel, JLayeredPane.PALETTE_LAYER);
        add(layeredPane);
    }

    public static void enableThrowDiceButton(boolean value) {
        throwDiceButton.setEnabled(value);
        if(value){
            throwDiceButton.setBackground(Color.WHITE);   
        }
        else{
            throwDiceButton.setBackground(Color.lightGray);
        }
    }

    public static void enableBuyPropertyButton(boolean value){
        buyPropertyButton.setEnabled(value);
        if(value){
            buyPropertyButton.setBackground(new Color(0x5CCE5C));   
        }
        else{
            buyPropertyButton.setBackground(Color.lightGray);
        }
    }
    
    public static void enableMortgagePropertyButton(boolean value){
        mortgagePropertyButton.setEnabled(value);
        if(value){
            mortgagePropertyButton.setBackground(new Color(0xF94848));   
        }
        else{
            mortgagePropertyButton.setBackground(Color.lightGray);
        }
    }

    public static void enableShowLeaderboardButton(boolean value){
        leaderboardButton.setEnabled(value);
        if(value){
            leaderboardButton.setBackground(Color.blue);   
        }
        else{
            leaderboardButton.setBackground(Color.lightGray);
        }
    }

    public static void enableEndTurnButton(boolean value){
        endTurnButton.setEnabled(value);
        if(value){
            endTurnButton.setBackground(new Color(0xA865C9));   
        }
        else{
            endTurnButton.setBackground(Color.lightGray);
        }
    }

    public static void enableBuyHouseButton(boolean value){
        buyHouseButton.setEnabled(value);
        if(value){
            buyHouseButton.setBackground(new Color(0xA865C9));   
        }
        else{
            buyHouseButton.setBackground(Color.lightGray);
        }
    }

    public static void enableSellHouseButton(boolean value){
        sellHouseButton.setEnabled(value);
        if(value){
            sellHouseButton.setBackground(new Color(0xA865C9));   
        }
        else{
            sellHouseButton.setBackground(Color.lightGray);
        }
    }

    public static void checkProperty(int position, String nickname){
        if(propertyList.get(position-1).isBuyable()){
            enableBuyPropertyButton(true);
        }
        else{
            if(propertyList.get(position-1).getOwner() != null && !nickname.equals(propertyList.get(position-1).getOwner())){
                enableMortgagePropertyButton(false);
                Client.sendMessage("PayMoney:" + propertyList.get(position-1).getOwner() + ":" + propertyList.get(position-1).getCurrentCost());
            }
        }
    }

    public static void checkButtonsAvaible(int position){
        if(propertyList.get(position).getOwner() != null && propertyList.get(position).getOwner().equals(Client.getNickname())){
            if(getEndTurnButton()){
                enableMortgagePropertyButton(true);
                if(propertyList.get(position).getLevel() < 5 && propertyList.get(position).isUpgradeable()){
                    boolean r = true;
                    for(Property p : propertyList){
                        if(p.getColor().equals(propertyList.get(position).getColor())){
                            if(p.getOwner() == null || !p.getOwner().equals(propertyList.get(position).getOwner())){
                                r = false;
                                break;
                            }
                        }
                    }
                    enableBuyHouseButton(r);
                    if(r && propertyList.get(position).getLevel() >= 1){
                        enableSellHouseButton(true);
                    }
                }
            }
        }
    }

    public static void mortgageProperty(int position){
        propertyList.get(position-1).setOwner(null);
        propertyList.get(position-1).setBuyable(true);
        propertyList.get(position-1).setLevel(0);
        updatePropertyInfo(String.valueOf(position));
        Client.sendMessage("PropertyMortgaged:" + propertyList.get(position-1).getMortgage_amount() + ":" + propertyList.get(position-1).getName() + ":" + Client.getNickname());
    }

    public static Property getProperty(int i){
        return propertyList.get(i);
    }

    public static boolean getThrowDiceButton() {
        return throwDiceButton.isEnabled();
    }

    public static boolean getBuyPropertyButton() {
        return buyPropertyButton.isEnabled();
    }

    public static boolean getMortgagePropertyButton() {
        return mortgagePropertyButton.isEnabled();
    }

    public static boolean getEndTurnButton() {
        return endTurnButton.isEnabled();
    }

    public static void updateStringList(String[] newArray, ArrayList<String> colors) {
        try {
            ArrayList <String> coloredArrayList = new ArrayList<>();
            for(int i = 0; i < colors.size(); i++){
                // coloredArrayList.add("<html><font color='" + colors.get(i) + "'>" + Server.getPlayer(i).getNickname() + ": " + Server.getPlayer(i).getColor() + "M" + "</font></html>");
                coloredArrayList.add("<html><font color='" + colors.get(i) + "'>" + newArray[i] + "</font></html>");
            }
            String coloredArray [] = new String[coloredArrayList.size()];
            for(int i = 0; i < coloredArrayList.size(); i++){
                coloredArray[i] = coloredArrayList.get(i);
            }
            stringList.setListData(coloredArray);
        } catch (Exception e) {
        }
    }

    public static void diceThrowed(int[] array) {
        int delay = 50;
        for (int i = 0; i < array.length; i = i + 2) {
            try {
                Thread.sleep(delay + (i * 2));
                leftDiceImageIcon = new ImageIcon("Immagini/dado/diceFace" + array[i] + ".png");
                rightDiceImageIcon = new ImageIcon("Immagini/dado/diceFace" + array[i + 1] + ".png");
                leftDiceImageLabel.setIcon(leftDiceImageIcon);
                rightDiceImageLabel.setIcon(rightDiceImageIcon);
            } catch (Exception e) {
            }
        }
        Client.sendMessage("StartMovement");
        if (getThrowDiceButton()) {
            enableThrowDiceButton(false);
        }
    }

    public static void buyProperty(int playerMoney, int playerPosition, String playerName){
        if(playerMoney >=  Board.getProperty(playerPosition-1).getPrice()){
            Client.sendMessage("RemoveMoney:" + Board.getProperty(playerPosition-1).getPrice());
            Client.sendMessage("PropertyBought:" + playerPosition + ":" + playerName);
        }
        else{
            showMessage("Non hai abbastanza denaro!");
        }
        enableBuyPropertyButton(false);    
    }

    public static void buyHouse(int playerMoney, int playerPosition, String playerName){
        if(playerMoney >= Board.getProperty(playerPosition-1).getHouse_cost()){
            Client.sendMessage("RemoveMoney:" + Board.getProperty(playerPosition-1).getHouse_cost());
            Client.sendMessage("HouseBought:" + playerPosition);
        }
        else{
            showMessage("Non hai abbastanza denaro!");
        }
        enableBuyHouseButton(false);    
    }

    public static void sellHouse(int position){
        propertyList.get(position-1).removeLevel();
        updatePropertyInfo(String.valueOf(position));
        Client.sendMessage("HouseSelled:" + (propertyList.get(position-1).getHouse_cost()/2) + ":" + Client.getNickname());  
    }

    public static void propertyBought(int playerPosition, String playerName){
        Board.getProperty(playerPosition-1).setBuyable(false);
        Board.getProperty(playerPosition-1).setOwner(playerName);
        updatePropertyInfo(String.valueOf(playerPosition));
    }
    
    public static void houseBought(int playerPosition){
        Board.getProperty(playerPosition-1).addLevel();
        updatePropertyInfo(String.valueOf(playerPosition));
    } 
    
    public static void updatePropertyInfo(String propertyNumber) {
        if(!getBuyPropertyButton()){
            propertyOpened = Integer.valueOf(propertyNumber);
            enableMortgagePropertyButton(false);
            checkButtonsAvaible(Integer.valueOf(propertyNumber) - 1);
            imageIcon = new ImageIcon("Immagini/Proprietà/" + propertyNumber + ".png");
            imageLabel.setIcon(imageIcon);
            String owner = "nessuno";
            if(propertyList.get(Integer.valueOf(propertyNumber) - 1).getOwner() != null){
                owner = propertyList.get(Integer.valueOf(propertyNumber) - 1).getOwner();
            }
            ownerLabel.setText("Proprietà di: " + owner + " | Case: " + propertyList.get(Integer.valueOf(propertyNumber)-1).getLevel());
        }
    }

    public static void showMessage(String msg){
        SwingUtilities.invokeLater(() -> {
            JOptionPane.showMessageDialog(null, msg);
        });
    }

    public static void showGameUpdate(String msg){
        updateLabel.setText(msg);
    }

    public static void movePawn(int pawnNumber, int position){
        int[][] arrangement;
        if(position-1 >= 1 && position-1 <= 11 || position-1 >= 21 && position-1 <= 31){
            arrangement = horizontalArrangement;
        }
        else{
            arrangement = verticalArrangement;
        }
        pawns.get(pawnNumber).startAnimation(propertyList.get(position-1).getCoordinateX()+arrangement[pawnNumber][0], propertyList.get(position-1).getCoordinateY()+arrangement[pawnNumber][1], position);
    }

    public static void killPlayer(String deadPlayerName, int deadPlayerIndex){
        pawns.remove(deadPlayerIndex);
        for(int i = 0; i < propertyList.size(); i++){
            if(propertyList.get(i).getOwner() != null && propertyList.get(i).getOwner().equals(deadPlayerName)){
                propertyList.get(i).setLevel(0);
                propertyList.get(i).setOwner(null);
                propertyList.get(i).setMortgaged(false);
                if(!(i == 5 || i == 15 || i == 25 || i == 35 || i == 12 || i == 28)){
                    propertyList.get(i).setUpgradeable(true);
                }
            }
        }
    }

    public static void killBoard(){
        JFrame boardFrame = (JFrame) SwingUtilities.getRoot(contentPane); // Ottieni la finestra Client
        boardFrame.dispose();
    }

    // METODO DA ELIMINARE, SOLO PER FASE DI TEST:
    // METODO DA ELIMINARE, SOLO PER FASE DI TEST:
    // METODO DA ELIMINARE, SOLO PER FASE DI TEST:
    public static void main(String args[]) {
        SwingUtilities.invokeLater(() -> {
            Board finestra = new Board(Server.getConnectedClients());
            finestra.setVisible(true);
        });
    }
}

