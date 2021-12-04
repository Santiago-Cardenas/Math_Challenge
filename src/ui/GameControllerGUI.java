package ui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.fxml.FXML;
import model.Player;
import model.PlayerManager;
import model.Questions_Generator;

import java.io.IOException;
import java.util.ArrayList;

public class GameControllerGUI {

    private Questions_Generator questionsGenerator;

    public GameControllerGUI() {
        questionsGenerator = new Questions_Generator();
    }

    @FXML
    private TextField nicknameTxt;


    @FXML
    private ImageView Math_Logo;


    // Question Window

    @FXML
    private Label N1;

    @FXML
    private Label OP;

    @FXML
    private Label N2;

    @FXML
    private Label R1;

    @FXML
    private Label R3;

    @FXML
    private Label R2;

    @FXML
    private Label R4;

    @FXML
    private Label ProgressScore;

    @FXML
    private RadioButton bt_A;

    @FXML
    private ToggleGroup Answer;

    @FXML
    private RadioButton bt_C;

    @FXML
    private RadioButton bt_B;

    @FXML
    private RadioButton bt_D;

    //Score Window

    @FXML
    private Label response;

    @FXML
    private TextField nickname_To_Search;

    @FXML
    private Label OP1;

    @FXML
    private TableView<Player> topTC;

    @FXML
    private TableColumn<Player, Integer> placementTC;

    @FXML
    private TableColumn<Player, String> nicknameTC;

    @FXML
    private TableColumn<Player, Integer> scoreTC;

    private ObservableList<Player> observableList;

    private Stage mainStage;

    private PlayerManager playerManager = new PlayerManager();
    private int answer;
    private int score;


    public Stage getMainStage() {
        return mainStage;
    }

    public void setMainStage(Stage mainStage) {
        this.mainStage = mainStage;
        answer=0;
        score=0;
    }

    private void initializeTableView() {
        observableList = FXCollections.observableArrayList(playerManager.getPlayers());

        topTC.setItems(observableList);
        placementTC.setCellValueFactory(new PropertyValueFactory<Player,Integer>("PLACEMENT"));
        nicknameTC.setCellValueFactory(new PropertyValueFactory<Player,String>("NICKNAME"));
        scoreTC.setCellValueFactory(new PropertyValueFactory<Player,Integer>("SCORE"));
    }

    @FXML
    void go_To_Questions(ActionEvent event) throws IOException {

        if(playerManager.addPlayer(nicknameTxt.getText(), 0,0)==false){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning 000");
            alert.setHeaderText(null);
            alert.setContentText("This nickname is already on use!");
        }
        else {
            playerManager.addPlayer(nicknameTxt.getText(), 0,0);
            showQuestionWindow();
        }
    }

    @FXML
    void next_Question(ActionEvent event) throws IOException {
        refreshScore();
        showQuestionWindow();
    }

    @FXML
    void delete_Player(ActionEvent event) {
        String nickname = nickname_To_Search.getText();
        Player playerDeleted = playerManager.deletePlayer(playerManager.getRoot(), nickname);
        if(playerDeleted==null){
            response.setText("There is no player with this nickname registered");
        }
        else {
            response.setText(playerDeleted.getNickname() + " was deleted");
        }
    }

    @FXML
    void search_Player(ActionEvent event) {
        String nickname = nickname_To_Search.getText();
        Player playerSearched = playerManager.searchPlayer(playerManager.getRoot(), nickname);
        if(playerSearched==null){
            response.setText("There is no player with this nickname registered");
        }
        else {
            response.setText("The player " + nickname + " got a score of " + playerSearched.getScore() + " and is placed at " + playerSearched.getPlacement());
        }
    }

    private void showQuestionWindow() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Question_Window.fxml"));
        fxmlLoader.setController(this);
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);

        showQuestions();

        mainStage.setScene(scene);
        mainStage.setTitle("Questions");
        mainStage.show();
    }

    public void showQuestions(){
        questionsGenerator.generateQuestion();
        ArrayList<Integer> questionParts = questionsGenerator.getQuestion();
        ProgressScore.setText(String.valueOf(score));
        N1.setText(questionParts.get(0).toString());
        N2.setText(questionParts.get(1).toString());
        setAnswers(questionParts);
        switch (questionParts.get(2)){
            case 0:
                OP.setText("+");
                break;
            case 1:
                OP.setText("-");
                break;
            case 2:
                OP.setText("*");
                break;
            case 3:
                OP.setText("/");
                break;
        }
    }

    private void setAnswers (ArrayList<Integer> questionParts){
        int placement = (int)(Math.random()*4);

        switch (placement){
            case 0:
                R1.setText(questionParts.get(3).toString());
                R2.setText(questionParts.get(4).toString());
                R3.setText(questionParts.get(5).toString());
                R4.setText(questionParts.get(6).toString());
                answer=1;
                break;
            case 1:
                R1.setText(questionParts.get(4).toString());
                R2.setText(questionParts.get(3).toString());
                R3.setText(questionParts.get(5).toString());
                R4.setText(questionParts.get(6).toString());
                answer=2;
                break;
            case 2:
                R1.setText(questionParts.get(4).toString());
                R2.setText(questionParts.get(5).toString());
                R3.setText(questionParts.get(3).toString());
                R4.setText(questionParts.get(6).toString());
                answer=3;
                break;
            case 3:
                R1.setText(questionParts.get(4).toString());
                R2.setText(questionParts.get(5).toString());
                R3.setText(questionParts.get(6).toString());
                R4.setText(questionParts.get(3).toString());
                answer=4;
                break;
        }
    }

    private void refreshScore(){
        switch (answer){
            case 1:
                if(bt_A.isSelected()==true){
                    score=score+10;
                }
                else{
                    score=score-10;
                }
                ProgressScore.setText(String.valueOf(score));
                break;
            case 2:
                if(bt_B.isSelected()==true){
                    score+=10;
                }
                else{
                    score-=10;
                }
                ProgressScore.setText(String.valueOf(score));
                break;
            case 3:
                if(bt_C.isSelected()==true){
                    score+=10;
                }
                else{
                    score-=10;
                }
                ProgressScore.setText(String.valueOf(score));
                break;
            case 4:
                if(bt_D.isSelected()==true){
                    score+=10;
                }
                else{
                    score-=10;
                }
                ProgressScore.setText(String.valueOf(score));
                break;
        }

    }
}
