package model;

import java.util.ArrayList;

public class PlayerManager {
    private ArrayList<Player> playersScoreArray;
    private Player root;

    private Player scoreRoot;

    public PlayerManager() {
        playersScoreArray = new ArrayList<Player>();
    }

    public Player triggerSearch(String nick) {
        return searchPlayer(root, nick);
    }

    public Player triggerSearchScore(String nick) {
        return searchPlayer(scoreRoot, nick);
    }

    public void triggerDelete(String nick) {
        if (root != null){
            root = deletePlayer(root, nick);
        }
    }

    public void triggerDeleteScore(String nick) {
        if (scoreRoot != null){
            scoreRoot = deletePlayer(scoreRoot, nick);
        }
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

    private void addTops(String nickname,int score,int placement) {
        Player playerScores = new Player(nickname,score,placement);
        if (scoreRoot == null) {
            scoreRoot = playerScores;
        } else {
            scoreRoot.insertByScore(playerScores);
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

    public Player deletePlayer(Player current, String nick){
        if (current.getNickname().compareTo(nick) == 0) {
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

        } else if (current.getNickname().compareTo(nick) < 0) {
            Player newLeftTree = deletePlayer(current.getLeft(), nick);
            current.setLeft(newLeftTree);
        } else {
            Player newRightTree = deletePlayer(current.getRight(), nick);
            current.setRight(newRightTree);
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

    public void refreshScore(String nickname,int score){
        triggerDelete(nickname);
        addPlayer(nickname, score, playersScoreArray.size() + 1);
        addTops(nickname, score, playersScoreArray.size() + 1);
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

    public void inorder(Player playerScore,int tries) {

        if (playerScore == null) {
            return;
        }

        inorder(playerScore.getScoreLess(),tries);
        if(tries<5) {
            tries++;
            playerScore.setPlacement(tries);
            playersScoreArray.add(playerScore);
        }
        inorder(playerScore.getScorePlus(),tries);
    }


    public ArrayList<Player> getPlayers() {
        return playersScoreArray;
    }
}
