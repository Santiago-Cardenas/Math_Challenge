package model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class PlayerManager {
    private ArrayList<Player> playersScoreArray;
    private Player root;
    private Player scoreRoot;
    private String FILE_DATA_PATH = "data/Users";
    private int totalPlayers;

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
            totalPlayers++;
        } else {
            if(root.insert(newPlayer)==false){
                return false;
            }
            else {
                root.insert(newPlayer);
                totalPlayers++;
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
        if(current!=null) {
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
        searchPlayerAndRefreshScore(root,nickname,score);
        addTops(nickname, score, 0);
    }

    public void searchPlayerAndRefreshScore(Player node, String name,int score) {
        if (node != null) {
            if (node.getNickname().compareTo(name) == 0) {
                node.setScore(score);
            }
            if (name.compareTo(node.getNickname()) < 0) {
                searchPlayerAndRefreshScore(node.getLeft(), name, score);
            } else {
                searchPlayerAndRefreshScore(node.getRight(), name, score);
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

    public void triggerInorderExport(FileWriter fw) throws IOException {
        inorderExport(scoreRoot, fw);
    }

    public void inorderExport(Player current, FileWriter fw) throws IOException {

        if (current == null) {
            return;
        }

        inorderExport(current.getLeft(),fw);
        fw.write(current.getNickname() + "|" + current.getScore() + "|" + current.getPlacement() + "\n");
        inorderExport(current.getRight(),fw);
    }

    public void importData() throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(FILE_DATA_PATH));
        String line = br.readLine();
        while (line != null) {
            String[] parts = line.split("\\|");
            String nickname = parts[0];
            int score = Integer.parseInt(parts[1]);
            int placement = Integer.parseInt(parts[2]);

            addPlayer(nickname,score,placement);
            addTops(nickname,score,placement);

            line = br.readLine();
        }
        br.close();
    }

    public void exportData() throws IOException {
        FileWriter fw = new FileWriter(FILE_DATA_PATH,false);
        triggerInorderExport(fw);
        fw.close();
    }

    public ArrayList<Player> getPlayers() {
        return playersScoreArray;
    }
}
