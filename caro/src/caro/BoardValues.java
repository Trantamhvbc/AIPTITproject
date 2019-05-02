/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package caro;

import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author Dell
 */
public class BoardValues {
    private int [][] table;
    private int width;
	// chieu cao cua ban co
    private int height;
    private long [][] F;
    private long [][] A;
    private long [][] thu ;//= {{0,2,9,99,769}};
    public BoardValues(int [][] table, int width, int height) {
        this.table = table;
        this.width = width;
        this.height = height;
        F = new long [100][5] ;
        thu = new long [100][5];
        thu[1][0] = 4;
        thu[2][0] = 18 ;
        thu[3][0] = 198 ;
        thu[4][0] = 1538;
        F[1][0] = 8 ;
        F[2][0] = 54 ;
        F[3][0] = 512 ;
        F[4][0] = 2916 ;
        F[5][0] = F[4][0] * 5;
        for(int i = 1 ; i < 6 ; i++){
            thu[i][1] = thu[i][1] / 2;
            F[i][1] = F[i][0] / 2;
        }
       // for(int i = 1 ; i < 6 ; i++) F[i][2] = 0;
        A = new long [height+8][width+8];
    }
    public void resetValue(){
        for(int i = 1 ; i <= height ; i++){
            for(int j = 1 ; j <= width ; j++) A[i][j] = 0;
        }
    }
    public void setValue(int row, int col ,long value){
        A[row][col] = value;
    }
    public void setPointBoardTable(int row,int col,int val){
        table[row][col] = val;
    }
    public Cell  findSuportOne(int row,int col,int val){
        int n = 0;
        int i = row;
        int d = 0;
        int run = 0;
       while(i > 0 ){
            if(table[i][col] == val ){
                i --;
                n ++;
            }
            else{
                if(table[i][col] == -1){
                    break;
                }
                else{
                    d++;
                    break;
                }
            }
        }
       i = row + 1;
       //System.out.println(n);
       while(i <= height   ){
            if(table[i][col] == val ){
                i ++;
                n ++;
            }
            else{
                if(table[i][col] == -1){
                    break;
                }
                else {
                    d++;
                    break;
                }
            }
        }
    return new Cell(n,d);
    }
    
     public Cell  findSuportTwo(int row,int col,int val){
       int n = 0;
       int i =  col;
       int d = 0;
       long ret = 0;
       while(i > 0 ){
            if(table[row][i] == val ){
                i --;
                n ++;
            }
            else{
                if(table[row][i] == -1){
                    break;     
                }
                else {
                    d++;
                    break;
                }
            }
        }
        i = col + 1;
       while(i <= width ){
 
            if(table[row][i] == val ){
                i ++;
                n ++;
            }
            else{
                if(table[row][i] == -1){
                    break;     
                }
                else {
                    d++;
                    break;
                }
            }
        }
    return new Cell(n,d);
}
    
    public Cell findSuportThree(int row,int col,int val){
       long ret = 0;
       int n = 0;
       int i =  0;
       int d = 0;
       while(row  - i > 0 && col - i > 0 ){
            if(table[row  - i][ col - i] == val ){
                i ++;
                n ++;
            }
            else{
                if(table[row  - i][ col - i] == -1){
                   break;
                }
                else {
                    d++;
                    break;
                }
            }
        }
        i = 1;
       while(row + i <= height && col + i <= width ){
            if(table[row + i][col + i] == val ){
                i ++;
                n ++;
            }
            else{
                if(table[row+ i][ col + i] == -1){
                    break;
                 
                }
                else {
                    d++; break;
                }
            }
        }
     return new Cell(n,d);
         // System.out.println(x1 );
    }
    
    
    public Cell  findSuportFor(int row,int col,int val){
       long ret = 0;
       int n = 0;
       int i =  0;
       int d = 0;
       while(row - i > 0 && col + i <= width  ){
           //System.out.println( row - i);
            if(table[row  - i][ col + i] == val ){
                i ++;
                n ++;
            }
            else{
                if(table[row  - i][ col + i] == -1){
                   break;
                }
                else{
                    d++;
                    break;
                }
            }
        }
         i =  1;
       while(row + i <= height && col - i > 0   ){
            if(table[row + i][col - i] == val ){
                i ++;
                n ++;
            }
            else{
                if(table[row+ i][ col - i] == -1){
                    break;
                }
                else{
                    d++;
                    break;
                }
            }
        }
   return new Cell(n,d);
 }
   
public void  calRow(){
    for (int row = 1; row <= height; row++)
        for (int col = 1; col <= width - 4; col++) {
                int x = 0;
                int y = 0;
                for (int i = 0; i < 5; i++) {
                        if (table[row][col + i] == 0) // neu quan do la cua nguoi
                                x++;
                        if (table[row][col + i] == 1) // neu quan do la cua may
                                y++;
                }
                // trong vong 5 o chi co 1 loai quan hoac khong co quan nao
                if (x == 0 || y == 0){
                    int d = 0;
                    if(col + 5 <= width) if(table[row][col + 5] == 0) d++; 
                    if(col - 1 > 0) if(table[row][col - 1] == 0) d++;
                        for (int i = 0; i < 5; i++) {
                                if (table[row][col + i] == -1) { // neu o chua danh
                                        if (y == 0) // x khac 0
                                                A[row][col + i] += thu[x][d]; // cho diem phong ng
                                        if (x == 0) // y khac 0
                                            A[row][col + i] += F[y][d];
                                        if (x == 4 || y == 4)
                                                A[row][col + i] *= 2;
                                }
                        }
                }
        }

}
    
public void  callCollow(){
    for (int row = 1; row <= height-4; row++)
        for (int col = 1; col <= width; col++) {
                int x = 0;
                int y = 0;
                for (int i = 0; i < 5; i++) {
                        if (table[row + i][col ] == 0) // neu quan do la cua nguoi
                                x++;
                        if (table[row + i][col] == 1) // neu quan do la cua may
                                y++;
                }
                // trong vong 5 o chi co 1 loai quan hoac khong co quan nao
                if (x == 0 || y == 0){
                     int d = 0;
                    if(row + 5 <= height) if(table[row+5][col ] == 0) d++; 
                    if(row - 1 > 0) if(table[row -1][col] == 0) d++;
                    for (int i = 0; i < 5; i++) {
                            if (table[row + i][col] == -1) { // neu o chua danh
                                    if (y == 0) // ePC khac 0
                                            A[row + i][col] +=thu[x][d]; // cho diem phong ng
                                    if (x == 0) // eHuman khac 0
                                        A[row + i][col] += F[y][d];
                                    if (x == 4 || y == 4)
                                            A[row + i][col] *= 2;
                            }
                    }
                }
        }
}
    
    public void callCrossMain(){
        for (int row = 1; row <= height-4; row++)
            for (int col = 1; col <= width - 4; col++) {
                            int x = 0;
                            int y = 0;
                            for (int i = 0; i < 5; i++) {
                                    if (table[row + i][col + i ] == 0) // neu quan do la cua nguoi
                                            x++;
                                    if (table[row + i][col + i] == 1) // neu quan do la cua may
                                            y++;
                            }
                            // trong vong 5 o chi co 1 loai quan hoac khong co quan nao
                            if (x == 0 || y == 0){
                                int d = 0;
                                if(row + 5 <= height && col + 5 <= width ) if(table[row+5][col + 5] == 0) d++; 
                                if(row - 1 > 0 && col - 1 > 0) if(table[row - 1][col - 1] == 0) d++;
                                for (int i = 0; i < 5; i++) {
                                        if (table[row + i][col+ i] == -1) { // neu o chua danh
                                                if (y == 0) // ePC khac 0
                                                        A[row + i][col+ i] +=  thu[x][d]; // cho diem phong ng
                                                if (x == 0) // eHuman khac 0
                                                    A[row + i][col+ i] += F[y][0];
                                                if (x == 4 || y == 4)
                                                        A[row+ i][col + i] *= 2;
                                        }
                                }
                            }
                    }
         // System.out.println(x1 );
    }
    
    
public void callCrossExtra(){
  for (int row = 5; row <= height; row++)
	for (int col = 1; col <= width - 4; col++) {
            int x = 0;
            int y = 0;
            for (int i = 0; i < 5; i++) {
                    if (table[row - i][col + i ] == 0) // neu quan do la cua nguoi
                            x++;
                    if (table[row - i][col + i] == 1) // neu quan do la cua may
                            y++;
            }
            // trong vong 5 o chi co 1 loai quan hoac khong co quan nao
            if (x == 0 || y == 0){
                int d = 0;
                if(row - 5 > 0 && col + 5 <= width) if(table[row-5][col + 5] == 0) d++; 
                if(row + 1 < height && col - 1 > 0) if(table[row+1][col - 1] == 0) d++;
                for (int i = 0; i < 5; i++) {
                        if (table[row - i][col+ i] == -1) { // neu o chua danh
                                if (y == 0) // ePC khac 0
                                        A[row - i][col+ i] += thu[x][d]; // cho diem phong ng
                                if (x == 0) // eHuman khac 0
                                    A[row - i][col+ i] += F[y][d];
                                if (x == 4 || y == 4)
                                        A[row - i][col + i] *= 2;
                        }
                }             
            }        
        }
 }
    
    
long FindAttack(int row,int col,int val){
    long S = 0;
    table[row][col] = val;
    Cell save = findSuportOne(row, col, val);
    S = F[ save.getRow()][save.getColumn()];
    save = findSuportTwo(row, col, val);
    S = S +  F[ save.getRow()][save.getColumn()];
    save = findSuportThree(row, col, val);
    S = S +  F[ save.getRow()][save.getColumn()];
    save = findSuportFor(row, col, val);
    S = S +  F[ save.getRow()][save.getColumn()];
    val = (val+1) % 2;
    table[row][col] = val;
    save = findSuportOne(row, col, val);
    S +=  F[ save.getRow()][save.getColumn()] - F[ save.getRow()][save.getColumn() + 1];
    save = findSuportTwo(row, col, val);
    S = S +  F[ save.getRow()][save.getColumn()] - F[ save.getRow()][save.getColumn() + 1] ;
    save =findSuportThree(row, col, val);
    S = S +  F[ save.getRow()][save.getColumn()] - F[ save.getRow()][save.getColumn() + 1];
    save = findSuportFor(row, col, val);
    S = S +  F[ save.getRow()][save.getColumn()] - F[ save.getRow()][save.getColumn() + 1];
    val = (val+1) % 2;
    table[row][col] = -1;
    return S;
}

public  boolean checkWin(int row , int col,int val){
    Cell C = findSuportOne(row, col, val);
    if(C.getRow() == 5 && ( C.getColumn() == 1 || C.getColumn() == 0 )) return true;
    C = findSuportTwo(row, col, val);
    if(C.getRow() == 5 && ( C.getColumn() == 1 || C.getColumn() == 0 )) return true;
     C = findSuportThree(row, col, val);
    if(C.getRow() == 5 && ( C.getColumn() == 1 || C.getColumn() == 0 )) return true;
     C = findSuportFor(row, col, val);
    if(C.getRow() == 5 && ( C.getColumn() == 1 || C.getColumn() == 0 )) return true;
    return false;
}
 
 public void caculator(int val){
     resetValue();
     calRow();
     callCollow();
     callCrossExtra();
     callCrossMain();
 }
 public Cell  maxPoint(){
     Cell cell = null;
    int maxx = 1 ,maxy = 1;
    long S = Long.MIN_VALUE;
    for(int i = 1 ; i < 21 ; i++ )
       for(int j = 1 ; j < 21 ; j++){
           if(A[i][j] > S){
                   S = A[i][j];
                   maxx = i;
                   maxy = j;
               //System.out.println(j);
           }

       }
    if(S == Long.MIN_VALUE) return cell;
    ArrayList <Cell> Arr = new ArrayList();
    for(int i = 1 ; i < 21 ; i++){
        for(int j = 1; j < 21 ; j++)
            if(A[i][j] == S){
                Cell C = new Cell(i, j,S);
                Arr.add(C);
            }
    }
    Random rd = new Random();
    int i =  rd.nextInt(Arr.size());
    return Arr.get(i);
 }
 public int checkEnd(){
     int oki = 0;
     for(int i = 1; i <= height ; i++){
         for(int j = 1; j <= width ; j++){
             if(table[i][j] != -1){
                 oki = 1;
                 if(checkWin(i, j, table[i][j])){
                     return table[i][j];
                }
             }
         }
     }
    if(oki == 1)
        return -1;
    else return -2;
 }
 
}
