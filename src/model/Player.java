package model;

import javafx.scene.control.Alert;

public class Player {
    private String nickname;
    private int score;
    private int placement;
    private Player right;
    private Player left;

    public Player(String nickname, int score, int placement) {
        this.nickname = nickname;
        this.score = score;
        this.placement = placement;
    }

    public boolean insert(Player newProgrammer){

        if (newProgrammer.getNickname().compareTo(nickname) < 0 || newProgrammer.getNickname().compareTo(nickname) == 0 ) {
            if (this.left == null) {
                this.left = newProgrammer;
            } else {
                this.left.insert(newProgrammer);
            }

        } else if (newProgrammer.getNickname().compareTo(nickname) > 0) {

            if (this.right == null) {
                this.right = newProgrammer;
            } else {
                this.right.insert(newProgrammer);
            }

        } else {
            return false;
        }

        return true;
    }

    public Player getRight() {
        return right;
    }

    public void setRight(Player right) {
        this.right = right;
    }

    public Player getLeft() {
        return left;
    }

    public void setLeft(Player left) {
        this.left = left;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getPlacement() {
        return placement;
    }

    public void setPlacement(int placement) {
        this.placement = placement;
    }
}
