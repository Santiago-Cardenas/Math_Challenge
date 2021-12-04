package model;

import javafx.application.Platform;
import ui.GameControllerGUI;

import java.io.IOException;

public class Cronometer extends Thread{

    private GameControllerGUI gameControll;


    public Cronometer(GameControllerGUI gameControll){
        this.gameControll = gameControll;

    }
    @Override
    public void run(){
        for(int i=5 ; i>=0 ; i--)   {
            int finalI = i;
            Platform.runLater(() ->{
                try {
                    gameControll.refreshLabel(finalI);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


    }
}
