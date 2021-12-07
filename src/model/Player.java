package model;

import java.io.Serializable;

public class Player implements Serializable {

    private static final long serialVersionUID = 1L;

    private String nickname;
    private int score;
    private Player right;
    private Player left;
    private Player scorePlus;
    private Player scoreLess;

    public Player(String nickname, int score) {
        this.nickname = nickname;
        this.score = score;
    }  //

    public void insert(Player newProgrammer){
        if (newProgrammer.getNickname().compareTo(nickname) < 0) {
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
        }
    }  //

    public void insertByScore(Player player){
        if (player.getScore() < score) {
            if (this.scoreLess == null) {
                this.scoreLess = player;
            } else {
                this.scoreLess.insertByScore(player);
            }
        } else if (player.getScore() > score) {
            if (this.scorePlus == null) {
                this.scorePlus = player;
            } else {
                this.scorePlus.insertByScore(player);
            }
        } else if (player.getScore() == score) {
            if (this.scoreLess == null) {
                this.scoreLess = player;
            } else {
                this.scoreLess.insertByScore(player);
            }
        }
    } //

    public Player getRight() {
        return right;
    } //

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

    public Player getScorePlus() {
        return scorePlus;
    }

    public void setScorePlus(Player scorePlus) {
        this.scorePlus = scorePlus;
    }

    public Player getScoreLess() {
        return scoreLess;
    }

    public void setScoreLess(Player scoreLess) {
        this.scoreLess = scoreLess;
    }
}
