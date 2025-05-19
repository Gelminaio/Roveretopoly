/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package roveretopoly;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import javax.swing.SwingUtilities;

/**
 * @author GelmiRusso
 */

public class Server {
    private static boolean gameStarted;
    private static boolean turnEndend = false;
    private static int turnPlayer = 1;
    private static int lastPlayer = 0;
    private static int port;
    private static List<PrintStream> clients;
    private ServerSocket server;
    private static int numberOfClientsConnected = 0;
    private static int numberOfClientsReady = 0;
    private static boolean turnContinued = false;
    private static ArrayList<Player> playerList = new ArrayList<>();
    private static ArrayList<Player> deadPlayerList = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        int maxPorts = 5;
        int basePort = 12345;
        int port = findAvailablePort(basePort, maxPorts);

        if (port != -1) {
            new Server(port).run();
        } else {
            System.out.println("Non ci sono porte disponibili per l'istanza del server.");
        }
    }

    private static int findAvailablePort(int basePort, int maxPorts) {
        for (int i = 0; i < maxPorts; i++) {
            int port = basePort + i;
            if (!isServerRunning(port)) {
                return port;
            }
        }
        return -1;
    }

    private static boolean isServerRunning(int port) {
        File lockFile = new File("server_lock_" + port + ".lock");
        return lockFile.exists();
    }

    public static void createLockFile(int port) {
        File lockFile = new File("server_lock_" + port + ".lock");
        try {
            lockFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Server(int port) {
        Server.port = port;
        clients = new ArrayList<PrintStream>();
    }

    public void run() throws IOException {
        server = new ServerSocket(port) {
            protected void finalize() throws IOException {
                this.close();
            }
        };
        System.out.println("La porta " + port + " è aperta!");

        while (true) {

            Socket client = server.accept();
            System.out.println("Connessione stabilita con il client: " + client.getInetAddress().getHostAddress());

            if (numberOfClientsConnected < 6 && !gameStarted) {
                numberOfClientsConnected++;
                clients.add(new PrintStream(client.getOutputStream()));
                new Thread(new ClientHandler(this, client.getInputStream())).start();
                broadcastMessages("PlayerNumber:" + numberOfClientsConnected + ":ReadyPlayers:" + numberOfClientsReady);
            } else {
                PrintStream removedClient = new PrintStream(client.getOutputStream());
                removedClient.println("Server pieno, stai venendo trasferito ad un'altra lobby...");
            }
        }
    }

    public void broadcastMessages(String msg) {
        for (PrintStream client : clients) {
            client.println(msg);
        }
    }

    public static int getTurnPlayer() {
        return turnPlayer;
    }

    public static void addTurnPlayer() {
        if (turnPlayer < numberOfClientsReady) {
            turnPlayer++;
        } else {
            turnPlayer = 1;
        }
    }

    public static int getLastPlayer() {
        return lastPlayer;
    }

    public static void addLastPlayer() {
        if (lastPlayer < numberOfClientsReady) {
            lastPlayer++;
        } else {
            lastPlayer = 1;
        }
    }

    public static void removeLastPlayer() {
        lastPlayer--;
    }

    public void sendMessage(int clientIndex, String msg) {
        clients.get(clientIndex).println(msg);
    }

    public static void addReadyClient() {
        numberOfClientsReady++;
    }

    public static void removeReadyClient() {
        numberOfClientsReady--;
    }

    public static int getReadyClients() {
        return numberOfClientsReady;
    }

    public static int getConnectedClients() {
        return numberOfClientsConnected;
    }

    public static void setGameStarted(boolean value) {
        gameStarted = value;
    }

    public static boolean getGameStarted() {
        return gameStarted;
    }

    public static void addPlayer(String nickname) {
        playerList.add(new Player(nickname, getReadyClients() + 1));
    }

    public static void removePlayer(int index) {
        playerList.remove(index);
    }

    public static void removeClient(int index) {
        clients.remove(index);
    }

    public static Player getPlayer(int index) {
        return playerList.get(index);
    }

    public static int numberOfPlayers() {
        return playerList.size();
    }

    public static void addDeadPlayer(Player player) {
        deadPlayerList.add(player);
    }

    public static void removeDeadPlayer(int index) {
        deadPlayerList.remove(index);
    }

    public static Player getDeadPlayer(int index) {
        return deadPlayerList.get(index);
    }

    public static int numberOfDeadPlayers() {
        return deadPlayerList.size();
    }

    public static boolean getTurnEnded() {
        return turnEndend;
    }

    public static void setTurnEnded(boolean value) {
        turnEndend = value;
    }

    public static boolean getTurnContinued() {
        return turnContinued;
    }

    public static void setTurnContinued(boolean value) {
        turnContinued = value;
    }

    public static int getPort() {
        return port;
    }
}

class ClientHandler implements Runnable {
    private Server server;
    private InputStream client;

    public ClientHandler(Server server, InputStream client) {
        this.server = server;
        this.client = client;
    }

    @Override
    public void run() {
        String msg;
        Scanner sc = new Scanner(this.client);
        while (sc.hasNextLine()) {
            msg = sc.nextLine();
            switch (msg) {
                case "Ready!":
                    Server.addReadyClient();
                    server.broadcastMessages("PlayerNumber:" + Server.getConnectedClients() + ":ReadyPlayers:"
                            + Server.getReadyClients());
                    if (Server.getConnectedClients() > 1 && Server.getConnectedClients() == Server.getReadyClients()) {
                        server.broadcastMessages("OpenTabellone:" + Server.getConnectedClients());
                        String s = "ReloadMoney:";
                        for (int i = 0; i < Server.numberOfPlayers(); i++) {
                            s += Server.getPlayer(i).getNickname();
                            s += ":";
                            s += Server.getPlayer(i).getMoney();
                            s += ":";
                            s += Server.getPlayer(i).getColor();
                            s += ":";
                        }
                        s = s.substring(0, s.length() - 1);
                        server.broadcastMessages(s);
                        Server.setGameStarted(true);
                        Server.createLockFile(Server.getPort());
                        server.sendMessage(Server.getTurnPlayer() - 1, "StartTurn");
                        break;
                    }
                    break;

                case "DiceThrowed":
                    Server.addLastPlayer();
                    String s = "DiceValues:";
                    Random random = new Random();
                    for (int i = 0; i < 34; i++) {
                        s += random.nextInt(6) + 1;
                        s += ':';
                    }
                    int firstDiceValue = random.nextInt(6) + 1;
                    int secondDiceValue = random.nextInt(6) + 1;
                    s += firstDiceValue;
                    s += ':';
                    s += secondDiceValue;
                    Server.setTurnEnded(false);
                    server.broadcastMessages(s);
                    if (Server.getPlayer(Server.getTurnPlayer() - 1).addPosition(firstDiceValue + secondDiceValue)) {
                        Server.getPlayer(Server.getTurnPlayer() - 1).addMoney(200);
                        server.sendMessage(Server.getTurnPlayer() - 1,
                                "ShowMessage:Hai ricevuto 200M passando dal VIA!");
                        String string = "ReloadMoney:";
                        for (int i = 0; i < Server.numberOfPlayers(); i++) {
                            string += Server.getPlayer(i).getNickname();
                            string += ":";
                            string += Server.getPlayer(i).getMoney();
                            string += ":";
                            string += Server.getPlayer(i).getColor();
                            string += ":";
                        }
                        server.broadcastMessages(string.substring(0, string.length() - 1));
                    }
                    break;
                case "EndTurn":
                    System.out.println("TurnEnded:" + Server.getTurnEnded() + " TurnPlayer:" + Server.getTurnPlayer()
                            + " LastPlayer:" + Server.getLastPlayer());
                    if (!Server.getTurnEnded() && Server.getTurnPlayer() == Server.getLastPlayer()) {
                        System.out.println("caris");
                        Server.setTurnEnded(true);
                        Server.addTurnPlayer();
                        server.sendMessage(Server.getTurnPlayer() - 1, "StartTurn");
                        Server.setTurnContinued(false);
                    }
                    break;
                case "StartMovement":
                    server.broadcastMessages("MovePawn:" + (Server.getTurnPlayer() - 1) + ":"
                            + Server.getPlayer(Server.getTurnPlayer() - 1).getPosition());
                    break;
                case "BuyPropertyPressed":
                    server.sendMessage(Server.getTurnPlayer() - 1,
                            "BuyProperty:"
                                    + (Server.getPlayer(Server.getTurnPlayer() - 1).getMoney() + ":"
                                            + Server.getPlayer(Server.getTurnPlayer() - 1).getPosition())
                                    + ":" + Server.getPlayer(Server.getTurnPlayer() - 1).getNickname());
                    break;
                case "ReloadMoney":
                    String string = "ReloadMoney:";
                    for (int i = 0; i < Server.numberOfPlayers(); i++) {
                        string += Server.getPlayer(i).getNickname();
                        string += ":";
                        string += Server.getPlayer(i).getMoney();
                        string += ":";
                        string += Server.getPlayer(i).getColor();
                        string += ":";
                    }
                    s = string.substring(0, string.length() - 1);
                    server.broadcastMessages(s);
                    break;
                default:
                    String msgSplitted[] = msg.split(":");
                    switch (msgSplitted[0]) {
                        case "Nickname":
                            Server.addPlayer(msgSplitted[1]);
                            break;
                        case "EndMovement":
                            if (msgSplitted[2].equals(Server.getPlayer(Server.getTurnPlayer() - 1).getNickname())) {
                                int posizione = Integer.valueOf(msgSplitted[1]);
                                if (posizione == 5) {
                                    Server.getPlayer(Server.getTurnPlayer() - 1).removeMoney(200);
                                } else {
                                    if (posizione == 39) {
                                        Server.getPlayer(Server.getTurnPlayer() - 1).removeMoney(100);
                                    } else {
                                        if (posizione == 3 || posizione == 8 || posizione == 18 || posizione == 23
                                                || posizione == 34 || posizione == 37) {
                                            Random rand = new Random();
                                            int numeroCasuale = rand.nextInt(81) + 20;
                                            Server.getPlayer(Server.getTurnPlayer() - 1).addMoney(numeroCasuale);
                                        } else {
                                            if (posizione == 31) {
                                                Server.getPlayer(Server.getTurnPlayer() - 1).removeMoney(50);
                                            }
                                        }
                                    }
                                }
                                String string3 = "ReloadMoney:";
                                for (int i = 0; i < Server.numberOfPlayers(); i++) {
                                    string3 += Server.getPlayer(i).getNickname();
                                    string3 += ":";
                                    string3 += Server.getPlayer(i).getMoney();
                                    string3 += ":";
                                    string3 += Server.getPlayer(i).getColor();
                                    string3 += ":";
                                }
                                s = string3.substring(0, string3.length() - 1);
                                server.broadcastMessages(s);
                                Server.setTurnContinued(true);
                                server.sendMessage(Server.getTurnPlayer() - 1, "ContinueTurn:" + msgSplitted[1] + ":"
                                        + Server.getPlayer(Server.getTurnPlayer() - 1).getNickname());
                            }
                            break;
                        case "RemoveMoney":
                            Server.getPlayer(Server.getTurnPlayer() - 1).removeMoney(Integer.valueOf(msgSplitted[1]));
                            string = "ReloadMoney:";
                            for (int i = 0; i < Server.numberOfPlayers(); i++) {
                                string += Server.getPlayer(i).getNickname();
                                string += ":";
                                string += Server.getPlayer(i).getMoney();
                                string += ":";
                                string += Server.getPlayer(i).getColor();
                                string += ":";
                            }
                            s = string.substring(0, string.length() - 1);
                            server.broadcastMessages(s);
                            break;
                        case "PropertyBought":
                            server.broadcastMessages("PropertyBought:" + msgSplitted[1] + ":" + msgSplitted[2]);
                            break;
                        case "HouseBought":
                            server.broadcastMessages("HouseBought:" + msgSplitted[1]);
                            break;
                        case "PayMoney":
                            int i = 0;
                            boolean playerDead = false;
                            while (!Server.getPlayer(i).getNickname().equals(msgSplitted[1])) {
                                i++;
                            }
                            Server.getPlayer(i).addMoney(Integer.valueOf(msgSplitted[2]));
                            if (Server.getPlayer(Server.getTurnPlayer() - 1).getMoney() >= Integer
                                    .valueOf(msgSplitted[2])) {
                                Server.getPlayer(Server.getTurnPlayer() - 1)
                                        .removeMoney(Integer.valueOf(msgSplitted[2]));
                            } else {
                                server.broadcastMessages(
                                        "KillClient:" + Server.getPlayer(Server.getTurnPlayer() - 1).getNickname() + ":"
                                                + (Server.getTurnPlayer() - 1));
                                server.sendMessage(Server.getTurnPlayer() - 1, "KillBoard");
                                Server.removeReadyClient();
                                Server.addDeadPlayer(Server.getPlayer(Server.getTurnPlayer() - 1));
                                Server.removePlayer(Server.getTurnPlayer() - 1);
                                Server.removeClient(Server.getTurnPlayer() - 1);
                                if (Server.getTurnPlayer() - 1 == Server.getReadyClients()) {
                                    Server.addTurnPlayer();
                                } else {
                                    Server.removeLastPlayer();
                                }
                                playerDead = true;
                            }
                            string = "ReloadMoney:";
                            for (int j = 0; j < Server.numberOfPlayers(); j++) {
                                string += Server.getPlayer(j).getNickname();
                                string += ":";
                                string += Server.getPlayer(j).getMoney();
                                string += ":";
                                string += Server.getPlayer(j).getColor();
                                string += ":";
                            }
                            s = string.substring(0, string.length() - 1);
                            server.broadcastMessages(s);
                            server.broadcastMessages(
                                    "ShowGameUpdate:" + Server.getPlayer(Server.getTurnPlayer() - 1).getNickname()
                                            + " ha pagato " + Integer.valueOf(msgSplitted[2]) + " soldi a "
                                            + Server.getPlayer(i).getNickname() + "!");

                            if (playerDead) {
                                SwingUtilities.invokeLater(() -> {
                                    Server.setTurnEnded(true);
                                    server.sendMessage(Server.getTurnPlayer() - 1, "StartTurn");
                                    Server.setTurnContinued(false);
                                });
                            }
                            break;
                        case "PropertyMortgaged":
                            if (msgSplitted[3].equals(Server.getPlayer(Server.getTurnPlayer() - 1).getNickname())) {
                                Server.getPlayer(Server.getTurnPlayer() - 1).addMoney(Integer.valueOf(msgSplitted[1]));
                            }
                            string = "ReloadMoney:";
                            for (int j = 0; j < Server.numberOfPlayers(); j++) {
                                string += Server.getPlayer(j).getNickname();
                                string += ":";
                                string += Server.getPlayer(j).getMoney();
                                string += ":";
                                string += Server.getPlayer(j).getColor();
                                string += ":";
                            }
                            s = string.substring(0, string.length() - 1);
                            server.broadcastMessages(s);
                            server.broadcastMessages(
                                    "ShowGameUpdate:" + Server.getPlayer(Server.getTurnPlayer() - 1).getNickname()
                                            + " ha ipotecato " + msgSplitted[2] + "!");
                            break;
                        case "HouseSelled":
                            if (msgSplitted[2].equals(Server.getPlayer(Server.getTurnPlayer() - 1).getNickname())) {
                                Server.getPlayer(Server.getTurnPlayer() - 1).addMoney(Integer.valueOf(msgSplitted[1]));
                            }
                            string = "ReloadMoney:";
                            for (int j = 0; j < Server.numberOfPlayers(); j++) {
                                string += Server.getPlayer(j).getNickname();
                                string += ":";
                                string += Server.getPlayer(j).getMoney();
                                string += ":";
                                string += Server.getPlayer(j).getColor();
                                string += ":";
                            }
                            s = string.substring(0, string.length() - 1);
                            server.broadcastMessages(s);
                            server.broadcastMessages(
                                    "ShowGameUpdate:" + Server.getPlayer(Server.getTurnPlayer() - 1).getNickname()
                                            + " ha venduto una casa!");
                            break;
                        case "MortgagedPropertyPressed":
                            server.broadcastMessages("MortgageProperty:" + Integer.valueOf(msgSplitted[1]));
                            break;
                        case "BuyHousePressed":
                            server.sendMessage(Server.getTurnPlayer() - 1,
                                    "BuyHouse:"
                                            + (Server.getPlayer(Server.getTurnPlayer() - 1).getMoney() + ":"
                                                    + Integer.valueOf(msgSplitted[1]))
                                            + ":" + Server.getPlayer(Server.getTurnPlayer() - 1).getNickname());
                            break;
                        case "SellHousePressed":
                            server.broadcastMessages(
                                    "SellHouse:" + Integer.valueOf(msgSplitted[1]));
                            break;
                        case "ShowLeaderboard":
                            int index = 0;
                            String leaderBoardList = "";
                            for (int ii = 0; ii < Server.getReadyClients(); ii++) {
                                if (Server.getPlayer(ii).getNickname().equals(msgSplitted[1])) {
                                    index = ii;
                                }
                            }
                            for (int jj = 0; jj < Server.numberOfDeadPlayers(); jj++) {
                                leaderBoardList += ":" + Server.getDeadPlayer(jj).getNickname();
                            }
                            server.sendMessage(index, "LeaderboardList" + leaderBoardList);
                            break;
                        default:
                            System.out.println("ERRORE! Messaggio non identificato, server side");
                            break;
                    }
                    break;
            }
        }
        sc.close();
    }
}