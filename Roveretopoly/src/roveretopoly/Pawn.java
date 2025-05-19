/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package roveretopoly;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author GelmiRusso
 */

public class Pawn extends JComponent {
    private int x = 0;
    private int y = 0;
    private int xEnd = 0;
    private int yEnd = 0;
    private Color color;
    private Timer timer;
    private int position = 0;

    public Pawn(int xStart, int yStart, Color color) {
        setX(xStart);
        setY(yStart);
        setColor(color);
        timer = new Timer(10, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (x == xEnd && y == yEnd) {
                    Board.updatePropertyInfo(String.valueOf(position));
                    timer.stop();
                    Client.sendMessage("EndMovement:" + position + ":" + Client.getNickname());
                } else {
                    if(x == xEnd){
                        if(y > yEnd){
                            y--;
                        }
                        else{
                            y++;
                        }
                    }
                    else{
                        if(y == yEnd){
                            if(x > xEnd){
                                x--;
                            }
                            else{
                                x++;
                            }
                        }
                        else{
                            if(yEnd > y){
                                if(xEnd > x){
                                    x++;
                                }
                                else{
                                    y++;
                                }
                            }
                            else{
                                if(xEnd > x){
                                    y--;
                                }
                                else{
                                    x--;
                                }
                            }
                        }
                    } 
                    repaint();
                }
            }
        });
    }

    public void setColor(Color color){
        this.color = color;
    }

    public void setX(int x){
        this.x = x;
    }

    public void setY(int y){
        this.y = y;
    }

    public Color getColor(){
        return this.color;
    }

    public int getX(){
        return this.x;
    }

    public int getY(){
        return this.y;
    }

    public void setXEnd(int xEnd){
        this.xEnd = xEnd;
    }

    public void setYEnd(int yEnd){
        this.yEnd = yEnd;
    }

    public void setPosition(int position){
        this.position = position;
    }

    public void startAnimation(int xEnd, int yEnd, int position) {
        setXEnd(xEnd);
        setYEnd(yEnd);
        setPosition(position);
        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(getColor());
        g.fillOval(getX(), getY(), 20, 20);
    }
}

