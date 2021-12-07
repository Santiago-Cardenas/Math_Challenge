package model;

import java.io.*;
import java.util.ArrayList;

public class PlayerManager {
    private ArrayList<Player> playersScoreArray;
    private Player root;
    private Player scoreRoot;

    public PlayerManager() {
        playersScoreArray = new ArrayList<Player>();
        this.root = loadPlayerName();
        this.scoreRoot = loadPlayerScore();
    }

    public Player triggerSearch(String nick) {
        return searchPlayer(root, nick);
    }

    public Player triggerSearchScore(String nick) {
        return searchPlayer(scoreRoot, nick);
    }

    public void triggerDelete(String nickname) {
        if (root != null){
            root = deletePlayer(root, nickname);
        }
    }

    public void triggerDeleteScore(String nickname) {
        if (scoreRoot != null){
            scoreRoot = deletePlayer(scoreRoot, nickname);
        }
    }

    public Player addPlayer(String nickname,int score) {
        Player newPlayer = new Player(nickname,score);
        if (root == null) {
            root = newPlayer;
        } else {
            root.insert(newPlayer);
        }
        return newPlayer;
    }

    public void addTops(Player player) {
        if (scoreRoot == null) {
            scoreRoot = player;
        } else {
            scoreRoot.insertByScore(player);
        }
    }

    public Player searchPlayer(Player node, String name) {
        if (node == null) {
            return null;
        }
        if (node.getNickname().compareTo(name) == 0) {
            return node;
        }
        if (name.compareTo(node.getNickname()) < 0) {
            return searchPlayer(node.getLeft(), name);
        } else {
            return searchPlayer(node.getRight(), name);
        }
    }

    public Player deletePlayer(Player current, String nickname){
        if (current.getNickname().compareTo(nickname) == 0) {
            if (current.getLeft() == null && current.getRight() == null) {
                return null;
            } else if (current.getLeft() != null &&
                    current.getRight() != null) {
                Player successor = getMin(current.getRight());
                Player newRightTree = deletePlayer(current.getRight(), successor.getNickname());

                successor.setLeft(current.getLeft());
                successor.setRight(newRightTree);
                return successor;
            } else if (current.getLeft() != null) {
                return current.getLeft();
            } else {
                return current.getRight();
            }

        } else if (current.getNickname().compareTo(nickname) < 0) {
            Player newRightTree = deletePlayer(current.getRight(), nickname);
            current.setRight(newRightTree);
        } else {
            Player newLeftTree = deletePlayer(current.getLeft(), nickname);
            current.setLeft(newLeftTree);
        }

        return current;
    }

    public void removePlayer(String nickname){
        for(int i=0; i<playersScoreArray.size();i++){
            if(playersScoreArray.get(i).getNickname().equals(nickname)){
                playersScoreArray.remove(i);
            }
        }
    }

    public Player getMin(Player current) {
        if (current.getLeft() == null) {
            return current;
        } else {
            return getMin(current.getLeft());
        }
    }

    public void triggerInorder() {
        inorder(scoreRoot,0);
    }

    public void inorder(Player playerScore, int top) {
        if (playerScore == null) {
            return;
        }
        inorder(playerScore.getScorePlus(),top);
        if(top<5) {
            top++;
            playersScoreArray.add(playerScore);
        }
        inorder(playerScore.getScoreLess(),top);
    }

    public ArrayList<Player> getPlayers() {
        return playersScoreArray;
    }

    public boolean savePlayerName(){
        try{
            File file = new File("src/data/playersName.txt");
            FileOutputStream fos = new FileOutputStream(file);
            ObjectOutputStream output = new ObjectOutputStream(fos);
            output.writeObject(this.root);
            output.close();

            return true;
        } catch (Exception ex){
            ex.printStackTrace();

            return false;
        }
    }

    public Player loadPlayerName(){
        try {
            File file = new File("src/data/playersName.txt");
            FileInputStream fis = new FileInputStream(file);
            ObjectInputStream input = new ObjectInputStream(fis);
            Player root = (Player) input.readObject();

            return root;
        } catch (Exception ex){
            ex.printStackTrace();

            return null;
        }
    }

    public boolean savePlayerScore(){
        try{
            File file = new File("src/data/playersScore.txt");
            FileOutputStream fos = new FileOutputStream(file);
            ObjectOutputStream output = new ObjectOutputStream(fos);
            output.writeObject(this.scoreRoot);
            output.close();

            return true;
        } catch (Exception ex){
            ex.printStackTrace();

            return false;
        }
    }

    public Player loadPlayerScore(){
        try{
            File file = new File("src/data/playersScore.txt");
            FileInputStream fis = new FileInputStream(file);
            ObjectInputStream input = new ObjectInputStream(fis);
            Player scoreRoot = (Player) input.readObject();

            return scoreRoot;
        } catch (Exception ex){
            ex.printStackTrace();

            return null;
        }
    }

    public Player getRoot() {
        return root;
    }

    public Player getScoreRoot() {
        return scoreRoot;
    }

    public void setRoot(Player root) {
        this.root = root;
    }

    public void setScoreRoot(Player scoreRoot) {
        this.scoreRoot = scoreRoot;
    }
}