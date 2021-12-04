package model;

import java.util.ArrayList;


public class PlayerManager {
    private ArrayList<Player> players;
    private Player root;
    private boolean isDeleted = false;

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public PlayerManager() {
        players = new ArrayList<Player>();
    }

    public boolean addPlayer(String nickname,int score,int placement) {
        Player newPlayer = new Player(nickname,score,placement);
        if (root == null) {
            root = newPlayer;
        } else {
            if(root.insert(newPlayer)==false){
                return false;
            }
            else {
                root.insert(newPlayer);
            }
        }
        return true;
    }
    public void triggerDelete(String nick) {
        if (root != null){
            root = deletePlayer(root, nick);
        }
    }
    public Player triggerSearch(String nick) {
        return searchPlayer(root, nick);
    }
    public Player deletePlayer(Player current, String nick){
        if (current.getNickname().compareTo(nick)==0){
            if (current.getLeft() == null && current.getRight() == null){
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

        } else if (current.getNickname().compareTo(nick)<0){
            Player newLeftTree = deletePlayer(current.getLeft(), nick);
            current.setLeft(newLeftTree);
        } else {
            Player newRightTree = deletePlayer(current.getRight(), nick);
            current.setRight(newRightTree);
        }

        return current;
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

    public Player getMin(Player current) {
        if (current.getLeft() == null) {
            return current;
        } else {
            return getMin(current.getLeft());
        }
    }

    public Player getRoot() {
        return root;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }
}
