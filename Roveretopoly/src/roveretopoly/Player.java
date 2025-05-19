/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package roveretopoly;

import java.util.ArrayList;

/**
 * @author GelmiRusso
 */

public class Player {
    public int playerNumber;
    private String nickname;
    private String color;
    private int money = 1500;
    private ArrayList<Property> propertyList = new ArrayList<>();
    private int position = 1;
    private boolean freePrisonExit = false;

    public Player() {
    }

    public Player(String nickname, int playerNumber) {
        setNickname(nickname);
        setPlayerNumber(playerNumber);
        switch (playerNumber) {
            case 1:
                setColor("#FF0000");
                break;
            case 2:
                setColor("#0000FF");
                break;
            case 3:
                setColor("#00FF00");
                break;
            case 4:
                setColor("#FFD700");
                break;
            case 5:
                setColor("#FF69B4");
                break;
            case 6:
                setColor("#808080");
                break;
        }
    }

    public void setPlayerNumber(int playerNumber){
        this.playerNumber = playerNumber;
    }

    public int getPlayerNumber(){
        return this.playerNumber;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getColor() {
        return this.color;
    }

    public String getNickname() {
        return this.nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getMoney() {
        return this.money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public void addMoney(int moneyToAdd){
        money += moneyToAdd;
    }

    public void removeMoney(int moneyToRemove){
        money -= moneyToRemove;
    }

    public ArrayList<Property> getPropertyList() {
        return this.propertyList;
    }

    public void setPropertyList(ArrayList<Property> propertyList) {
        this.propertyList = propertyList;
    }


    public int getPosition() {
        return this.position;
    }

    public void setPositionn(int position) {
        this.position = position;
    }

    public boolean addPosition(int value){
        if(position + value >  40){
            position = position + value - 40;
            return true;
        }
        else{
            position += value;
            return false;
        }
    }

    public boolean isFreePrisonExit() {
        return this.freePrisonExit;
    }

    public boolean getFreePrisonExit() {
        return this.freePrisonExit;
    }

    public void setFreePrisonExit(boolean freePrisonExit) {
        this.freePrisonExit = freePrisonExit;
    }
}
