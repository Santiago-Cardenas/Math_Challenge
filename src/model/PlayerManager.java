package model;

import java.util.ArrayList;


public class PlayerManager {
    private ArrayList<Player> players;
    private Player root;

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

    public Player deletePlayer(Player current, String nickname) {
        if(current!=null) {
            if (current.getNickname().compareTo(nickname) == 0) {
                if (current.getLeft() == null && current.getRight() == null) {
                    return null;
                } else if (current.getLeft() != null && current.getRight() != null) {
                    Player aux = getMin(current.getRight());
                    Player rightT = deletePlayer(current.getRight(), aux.getNickname());
                    aux.setLeft(current.getLeft());
                    aux.setRight(rightT);
                    return aux;

                } else if (current.getLeft() != null) {
                    return current.getLeft();
                } else {
                    return current.getRight();
                }
            } else if (nickname.compareTo(current.getNickname()) < 0) {
                Player leftT = deletePlayer(current.getLeft(), nickname);
                current.setLeft(leftT);
            } else {
                Player newRightTree = deletePlayer(current.getRight(), nickname);
                current.setRight(newRightTree);
            }
        }
        else{
            return null;
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
