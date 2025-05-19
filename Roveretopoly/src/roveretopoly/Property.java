/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package roveretopoly;

/**buyable
 * @author GelmiRusso
 */

public class Property {
    private String name;
    private String color;
    private String owner; //Proprietario
    private int position; //da 1 a 40
    private int coordinateX;
    private int coordinateY;
    private int price; //prezzo di acquisto 
    private int rentPrices []; //prezzo da pagare se ci finisci sopra
    private int house_cost; //costo costruzione casa
    private int mortgage_amount; //importo ipoteca
    private boolean mortgaged = false; //ipotecato
    private boolean buyable;
    private boolean upgradeable;
    private int level = 0;

    public Property() {
    }

    public Property(String name, String color, int position, int coordinateX, int coordinateY, int price, int rentPrices[], int house_cost, int mortgage_amount, boolean buyable, boolean upgradeable) {
        setName(name);
        setColor(color);
        setPosition(position);
        setCoordinateX(coordinateX);
        setCoordinateY(coordinateY);
        setPrice(price);
        setRent(rentPrices);
        setHouse_cost(house_cost);
        setMortgage_amount(mortgage_amount);
        setBuyable(buyable);
        setUpgradeable(upgradeable);
    }

    public int getCoordinateX() {
        return this.coordinateX;
    }

    public int getCoordinateY() {
        return this.coordinateY;
    }

    public void setCoordinateX(int coordinateX){
        this.coordinateX = coordinateX;
    }
        
    public void setCoordinateY(int coordinateY){
        this.coordinateY = coordinateY;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return this.color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getOwner() {
        return this.owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public int getPosition() {
        return this.position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getPrice() {
        return this.price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getRent(int index) {
        return this.rentPrices[index];
    }

    public void setRent(int [] rentPrices) {
        this.rentPrices = rentPrices;
    }

    public int getHouse_cost() {
        return this.house_cost;
    }

    public void setHouse_cost(int house_cost) {
        this.house_cost = house_cost;
    }

    public int getMortgage_amount() {
        return this.mortgage_amount;
    }

    public void setMortgage_amount(int mortgage_amount) {
        this.mortgage_amount = mortgage_amount;
    }

    public boolean isMortgaged() {
        return this.mortgaged;
    }

    public boolean getMortgaged() {
        return this.mortgaged;
    }

    public void setMortgaged(boolean mortgaged) {
        this.mortgaged = mortgaged;
    }

    public boolean isBuyable() {
        return this.buyable;
    }

    public boolean getBuyable() {
        return this.buyable;
    }

    public void setBuyable(boolean buyable) {
        this.buyable = buyable;
    }

    public boolean isUpgradeable() {
        return this.upgradeable;
    }

    public boolean getUpgradeable() {
        return this.upgradeable;
    }

    public void setUpgradeable(boolean upgradeable) {
        this.upgradeable = upgradeable;
    }

    public int getLevel() {
        return this.level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void addLevel() {
        setLevel(getLevel()+1);
    }

    public void removeLevel(){
        setLevel(getLevel()-1);
    }
    
    public int getCurrentCost(){
        return rentPrices[level];
    }
}

