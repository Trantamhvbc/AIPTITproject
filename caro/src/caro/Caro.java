/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package caro;

import com.sun.org.apache.bcel.internal.generic.TABLESWITCH;
import java.applet.Applet;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import static javafx.scene.input.KeyCode.R;
import javafx.scene.layout.Background;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author Dell
 */
public class Caro extends Application {

    /**
     * @param args the command line arguments
     */
    private int [][] table ;
    private static Stage stage;
    private static  GridPane paneTable;
    private static BorderPane paneMain;
    private Button[][] buttons ;
    private int valueswar = 0;
    public static void main(String[] args) {
         launch(args);

    }

    @Override
    public void start(Stage primaryStage)  {
       //throw new UnsupportedOperationException("Not supported yet."); 
        stage = primaryStage;
        createTable();
       
        
    //    buttons[5][5].setFont( new Font("", size));
    //    buttons[5][5].setText("O");
    //    buttons[6][5].setText("O");
        stage.show();
    }
    public void createTable(){   
        buttons = new Button[22][22];
        table = new int[22][22]; 
        paneTable= new GridPane();
      //  Frame J = new Frame();
        for(int i=1;i<=20;i++){
            for(int j=1;j<=20;j++){
                buttons[i][j] = new Button();
                buttons[i][j].setPrefSize(40,40);
                paneTable.add(buttons[i][j],j,i);
                int row = i,col =j;
                buttons[i][j].setOnAction(e -> actionCell(row,col));
                table[i][j] = -1;
            }
        }
            
        
        paneTable.setAlignment(Pos.CENTER);
        VBox paneCentre = new VBox(20);
        paneCentre.getChildren().addAll(paneTable);
        paneMain = new BorderPane(paneCentre);
        stage.setScene(new Scene(paneMain));
        //stage.show();
    
    }
    public void actionCell(int row, int collow){
        if(table[row][collow] != -1){
        
        }
        else{
            for(int  i = 1 ; i < 21 ;  i ++)
                for(int j = 1; j < 21 ; j++) buttons[i][j].setTextFill(Color.BLACK);
            if(valueswar == 0)
                buttons[row][collow].setText("O");
            else 
                buttons[row][collow].setText("X");
            buttons[row][collow].setTextFill(Color.RED);
            table[row][collow] = valueswar;
            valueswar = valueswar + 1;
            valueswar = valueswar % 2;
            AI a = new AI(table, valueswar);
            Cell next = a.NexAtack();
            int x = next.getRow();
            int y = next.getColumn();
            System.out.println("x O ca ro la: "+ x);
            if(valueswar == 0)
                buttons[x][y].setText("O");
            else 
                buttons[x][y].setText("X");
            buttons[x][y].setTextFill(Color.GREEN);
            table[x][y] = valueswar;
            valueswar = valueswar + 1;
            valueswar = valueswar % 2;
        }
            
    }
}
