package caro;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.*;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Random;

public class Client extends Application {
    private Stage stage;
    private double sceneWidth = 750;
    private double sceneHeight = 516;
    private Scene sceneLogin,scenePlay;
    private ObjectInputStream reader;
    private ObjectOutputStream writer;
    private TextField tfUsername;
    private int id,numberOfClickedButton;
    private boolean isPlaying;
    private int[][] table,p,pd;
    private int maxRow = 20, maxCol = 20;
    private Button[][] buttons;
    private ImageView imageView;
    public GridPane paneTable;
    private TextArea chatMessages,message;
    private ObservableList<String> imageList;
    private ChoiceBox<String> choiceBox;
    private int BOT = 1, HUMAN = 0;
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage){
        stage = primaryStage;
        createImageList();
        login();
    }
    public void login(){

        Label gameName = new Label("Game Caro");
        gameName.setTextFill(Color.BLUE);
        gameName.getStyleClass().add("my-label");

        VBox paneLogin = new VBox(20);
        HBox paneUsername = new HBox(10);
        HBox paneImage = new HBox(10);

        Label lbUsername = new Label("Username");
        lbUsername.setPrefWidth(80);
        tfUsername = new TextField();
        tfUsername.setPromptText("Enter your username here!");
        tfUsername.setPrefColumnCount(20);


        paneUsername.getChildren().addAll(lbUsername,tfUsername);
        paneUsername.setAlignment(Pos.CENTER);

        Label lbChoiceBox = new Label("Choose the character");
        lbUsername.setPrefWidth(80);
        choiceBox = new ChoiceBox();
        choiceBox.setItems(imageList);
        choiceBox.setValue("naruto");

        paneImage.getChildren().addAll(lbChoiceBox,choiceBox);
        paneImage.setAlignment(Pos.CENTER);

        HBox paneButtons = new HBox(20);

        Button btnPlay = new Button("Vs human");
        btnPlay.setPrefWidth(120);
        btnPlay.setOnAction(e ->  play());
        btnPlay.getStyleClass().add("my-button");
        btnPlay.getStyleClass().add("bordered");

        Button btnPlayWithBot = new Button("Vs Bot");
        btnPlayWithBot.setPrefWidth(120);
        btnPlayWithBot.setOnAction(e -> playWithBot());
        btnPlayWithBot.getStyleClass().add("my-button");
        btnPlayWithBot.getStyleClass().add("bordered");

        paneButtons.getChildren().addAll(btnPlay,btnPlayWithBot);
        paneButtons.setAlignment(Pos.CENTER);

        paneLogin.getChildren().addAll(gameName,paneUsername,paneImage,paneButtons);
        paneLogin.setAlignment(Pos.CENTER);

        sceneLogin = new Scene(paneLogin,sceneWidth,sceneHeight);
        sceneLogin.getStylesheets().add("simple.css");
        stage.setScene(sceneLogin);
        stage.show();
    }
    public void play(){
        table = new int[maxRow+1][maxCol+1];
        for(int i=1;i<=maxRow;i++){
            for(int j=1;j<=maxCol;j++) table[i][j] = -1;
        }
        isPlaying = false;
        buttons = new Button[maxRow+1][maxCol+1];
        numberOfClickedButton = 0;

        buildGUI();
        getConnection();

    }
    public void buildGUI(){
        createTable();
        paneTable.setAlignment(Pos.TOP_LEFT);
        for(int i=1;i<=maxRow;i++){
            for(int j=1;j<=maxCol;j++) {
                int row = i,col = j;
                buttons[i][j].setOnAction(e -> choose(row,col));
            }
        }

        VBox paneUser = new VBox(20);
        Label username = new Label();
        username.setText(tfUsername.getText());
        username.getStyleClass().add("name-label");

        chatMessages = new TextArea();
        chatMessages.setPrefColumnCount(15);
        chatMessages.setPrefHeight(90);
        chatMessages.setEditable(false);

        imageView = new ImageView();
        imageView.setImage(new Image( "image/" + choiceBox.getValue() + ".png"));
        imageView.setFitHeight(320);
        imageView.setFitWidth(280);

        HBox paneChat = new HBox(5);

        message = new TextArea();
        message.setWrapText(true);
        message.setPrefColumnCount(15);
        message.setPrefHeight(30);

        Button btnSend = new Button("SEND");
        btnSend.setFont(new Font(15));
        btnSend.setOnAction(e -> sendMessage());

        paneChat.getChildren().addAll(message,btnSend);
        paneUser.getChildren().addAll(username,imageView,chatMessages,paneChat);

        HBox paneMid = new HBox(30);
        paneMid.getChildren().addAll(paneTable,paneUser);

        Label gameName = new Label("Game Caro");
        gameName.setTextFill(Color.BLUE);
        gameName.getStyleClass().add("my-label");

        BorderPane paneMain = new BorderPane();
        paneMain.setCenter(paneMid);
        paneMain.setTop(gameName);
        paneMain.setPadding(new Insets(20,20,20,20));
        paneMain.setAlignment(gameName,Pos.CENTER);

        BorderPane.setMargin(gameName,new Insets(0,0,20,0));
        scenePlay = new Scene(paneMain);
        scenePlay.getStylesheets().add("simple.css");
        stage.setScene(scenePlay);
        stage.show();
    }
    public void createTable(){
        paneTable = new GridPane();
        for(int i=1;i<=maxRow;i++){
            for(int j=1;j<=maxCol;j++){
                buttons[i][j] = new Button();
                buttons[i][j].setPrefSize(50,50);
                paneTable.add(buttons[i][j],j,i);
            }
        }
    }
    public void getConnection(){
        try {
            Socket socket = new Socket("127.0.0.1",5008);
            reader = new ObjectInputStream(socket.getInputStream());
            writer = new ObjectOutputStream(socket.getOutputStream());
            Thread t = new Thread(new InformationReader());
            t.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private class InformationReader implements Runnable{
        @Override
        public void run() {
            Object object;
            try{
                while((object = reader.readObject()) != null){
                    if(object instanceof Integer){
                        id = (Integer) object;
                        isPlaying = true;
                    }
                    if(object instanceof Cell){
                        Cell cell = (Cell) object;
                        int row = cell.getRow();
                        int col = cell.getColumn();
                        if(numberOfClickedButton % 2 == id) table[row][col] = id;
                        else table[row][col] = (id+1)%2;
                        numberOfClickedButton++;
                        Platform.runLater(() -> updateCellPicture(row,col));
                        if(numberOfClickedButton > 8){
                            if(checkWin() == 1) win();
                            else if(checkWin() == 0) loose();
                        }
                    }
                    if(object instanceof String){
                        String text = (String) object;
                        Platform.runLater(() -> chatMessages.appendText(text + "\n"));
                    }
                }
            }catch (Exception ex){
            }
        }
    }
    public void choose(int row,int col){
        if(table[row][col] != -1){
            MessageBox.show("That cell has been ticked","Please Tick Another");
            return;
        }
        if(isPlaying && numberOfClickedButton % 2 == id ){
            try {
                writer.writeObject(new Cell(row,col));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else{
            MessageBox.show("Not your turn!","Please Wait");
        }
    }
    public void updateCellPicture(int row,int col){
        if(numberOfClickedButton%2 == 1) {
            buttons[row][col].setText("X");
           // buttons[row][col].setAccessibleText(STYLESHEET_MODENA);
        }
        else buttons[row][col].setText("O");
        
    }
    public int checkWin(){
        if(numberOfClickedButton == 16*16){
            tie();
        }
        int r1 = checkNgang();
        int r2 = checkDoc();
        int r3 = checkCheo1();
        int r4 = checkCheo2();
        if(r1 == id || r2 == id  || r3 == id || r4 == id){
            return 1;
        }
        if(r1 == ((id+1)%2) || r2 == ((id+1)%2)  || r3 == ((id+1)%2) || r4 == ((id+1)%2)){
            return 0;
        }
        return -1;
    }
    public int checkNgang(){
        for(int i=1;i<=maxRow;i++){
            for(int j=1;j<=maxCol- 4 ;j++){
                int v = table[i][j];
                if( v!= -1 && v == table[i][j+1] && v == table[i][j+2] && v == table[i][j+3] && v == table[i][j+4]) return v;
            }
        }
        return -1;
    }
    public int checkDoc(){
        for(int j=1;j<=maxCol;j++){
            for(int i=1;i<= maxRow - 4;i++){
                int v = table[i][j];
                if( v!= -1 && v == table[i+1][j] && v == table[i+2][j] && v == table[i+3][j] && v == table[i+4][j]) return v;
            }
        }
        return -1;
    }
    public int checkCheo1(){
        int u = maxRow - 4;
        while(u>0){
            int i = u, j = 1;
            while(i + 4 <= maxRow){
                int x = table[i][j];
                if(x != -1 && x == table[i+1][j+1] && x == table[i+2][j+2] && x == table[i+3][j+3] && x == table[i+4][j+4]) return x;
                i++; j++;
            }
            u--;
        }
        int v = maxCol - 4;
        while( v > 0){
            int i = 1, j = v;
            while(j + 4 <= maxCol){
                int x = table[i][j];
                if(x != -1 && x == table[i+1][j+1] && x == table[i+2][j+2] && x == table[i+3][j+3] && x == table[i+4][j+4]) return x;
                i++; j++;
            }
            v--;
        }
        return -1;
    }
    public int checkCheo2(){
        int u = maxRow - 4;
        while(u > 0){
            int i = u, j = maxCol;
            while(i + 4 <= maxRow){
                int x = table[i][j];
                if(x != -1 && x == table[i+1][j-1] && x == table[i+2][j-2] && x == table[i+3][j-3] && x == table[i+4][j-4]) return x;
                i++; j--;
            }
            u--;
        }
        int v = 5;
        while(v <= maxCol){
            int i = 1, j = v;
            while(j - 4  > 0){
                int x = table[i][j];
                if(x != -1 && x == table[i+1][j-1] && x == table[i+2][j-2] && x == table[i+3][j-3] && x == table[i+4][j-4]) return x;
                i++; j--;
            }
            v++;
        }
        return -1;
    }
    public void win(){
        id = (id+1)%2;
        Platform.runLater(() ->{

            MessageBox.show("You won","Result");
            reset();
        });

    }
    public void loose(){
        id = (id+1)%2;
        Platform.runLater(() ->{
            MessageBox.show("You loose","Result");
            reset();
        });
    }
    public void tie(){
        id = (id+1)%2;
        Platform.runLater(() ->{
            MessageBox.show("Tie","Result");
            reset();
        });
    }
    public void reset(){
        for(int i=1;i<=maxRow;i++){
            for(int j=1;j<maxRow;j++) table[i][j] = -1;
        }
        for(int i=1;i<=maxRow;i++){
            for(int j=1;j<=maxCol;j++) buttons[i][j].setGraphic(null);
        }
        numberOfClickedButton = 0;
    }
    public void sendMessage(){
        try {
            String textMessage = tfUsername.getText() + ": " + message.getText();
            writer.writeObject(textMessage);
            message.setText("");
            message.requestFocus();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void createImageList(){
        imageList = FXCollections.observableArrayList();
        imageList.add("naruto");
        imageList.add("sasuke");
        imageList.add("boruto");
        imageList.add("gara");
        imageList.add("growNaruto");
        imageList.add("growSasuke");
        imageList.add("hagoromo");
        imageList.add("hashirama");
        imageList.add("itachi");
        imageList.add("kabuto");
        imageList.add("kakashi");
        imageList.add("kiba");
        imageList.add("madara");
        imageList.add("minato");
        imageList.add("orochimaru");
        imageList.add("pain");
        imageList.add("sakura");
        imageList.add("sasuke2");
        imageList.add("sasuke3");
        imageList.add("tsunade");
    }
    public void playWithBot(){
        id = HUMAN;
        table = new int[maxRow+2][maxCol+2];
        for(int i=1;i<=maxRow;i++){
            for(int j=1;j<=maxCol;j++) table[i][j] = -1;
        }
        p = new int[maxRow+2][maxCol+2];
        pd = new int[maxRow+2][maxCol+2];
        buttons = new Button[maxRow+2][maxCol+2];
        numberOfClickedButton = 0;
        buildGUIBot();
        start();
    }
    public void buildGUIBot(){
        createTable();
        paneTable.setAlignment(Pos.CENTER);
        for(int i=1;i<=maxRow;i++){
            for(int j=1;j<=maxCol;j++){
                int row = i,col =j;
                buttons[i][j].setOnAction(e -> chooseWithBot(row,col));
            }
        }
        Label gameName = new Label("Play with Bot");
        gameName.getStyleClass().add("my-label");
        gameName.setTextFill(Color.BLUE);

        VBox paneCentre = new VBox(20);
        paneCentre.getChildren().addAll(gameName,paneTable);
        paneCentre.setAlignment(Pos.CENTER);

        ImageView imgNaruto = new ImageView();
        imgNaruto.setImage(new Image("image/naruto.png"));
        imgNaruto.setFitHeight(420);
        imgNaruto.setFitWidth(280);

        ImageView imgSasuke = new ImageView();
        imgSasuke.setImage(new Image("image/sasuke.png"));
        imgSasuke.setFitHeight(320);
        imgSasuke.setFitWidth(280);

        BorderPane paneMain = new BorderPane(paneCentre);
        paneMain.setLeft(imgNaruto);
        paneMain.setRight(imgSasuke);
        paneMain.setPadding(new Insets(30));
        paneMain.getStylesheets().add("simple.css");
        BorderPane.setAlignment(imgNaruto,Pos.BOTTOM_LEFT);
        BorderPane.setAlignment(imgSasuke,Pos.BOTTOM_RIGHT);
        stage.setScene(new Scene(paneMain));
    }
    public void chooseWithBot(int row,int col){
        if(table[row][col] != -1){
            MessageBox.show("This cell has been ticked","Please tick another");
            return;
        }
        buttons[row][col].setGraphic(new ImageView("image/o.png"));
        table[row][col] = HUMAN;
        numberOfClickedButton++;
        if(numberOfClickedButton >= 9){
           if(checkWin() == 1){

               MessageBox.show("You won","Result");
               reset();
               start();
           }
        }
        botPlay();
        if(numberOfClickedButton >= 9){
            if(checkWin() == 0){

                MessageBox.show("You loose","Result");
                reset();
                start();
            }
        }
    }
    public void start(){
         Alert gameMode = new Alert(Alert.AlertType.CONFIRMATION);
        gameMode.setTitle("Chọn người chơi trước");
        gameMode.setHeaderText("Bạn có muốn chơi trước không ?");
        Optional<ButtonType> result = gameMode.showAndWait();
        if(result.get() == ButtonType.CANCEL){
            botPlay();
        }
    
    }
    public void botPlay(){
        Cell nm = findNextMove();
        int row = nm.getRow(), col = nm.getColumn();
        table[row][col] = BOT;
        buttons[row][col].setGraphic(new ImageView("image/x.png"));
        numberOfClickedButton++;
    }
    public Cell findNextMove(){
            AI1 A = new AI1(table, 6);
           
            Cell cell = A.NexAtack();    
            if(cell == null ){
                System.out.println(111);
                return new Cell(10,10);
            }
            else return cell;
    }
}