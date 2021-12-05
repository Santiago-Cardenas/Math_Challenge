package model;

import javafx.application.Platform;
import ui.GameControllerGUI;

import java.io.IOException;

public class Chronometer extends Thread{

    private GameControllerGUI gameControl;


    public Chronometer(GameControllerGUI gameControl){
        this.gameControl = gameControl;
    }

    @Override
    public void run(){
        for(int i=30 ; i>=0 ; i--)   {
            int finalI = i;
            Platform.runLater(() ->{
                try {
                    gameControl.refreshLabel(finalI);
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
