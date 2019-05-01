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
public class BoardTable {
    private int [][] table;
    private int width;
	// chieu cao cua ban co
    private int height;
    private long [][] F;
    private long [][] A;
    public BoardTable(int [][] table, int width, int height) {
        this.table = table;
        this.width = width;
        this.height = height;
        F = new long [100][5] ;
        F[1][0] = 10 ;
        F[2][0] = 50 ;
        F[3][0] = 1000 ;
        F[4][0] = 5000 ;
        F[5][0] = F[4][0] * 5;
        for(int i = 1 ; i < 6 ; i++) F[i][1] = F[i][0] / 2;
        for(int i = 1 ; i < 6 ; i++) F[i][2] = 0;
        A = new long [height+4][width+4];
    }
    public void resetValue(){
        for(int i = 1 ; i < height ; i++){
            for(int j = 1 ; j < width ; j++) A[i][j] = 0;
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
     for(int i = 1; i <= height ; i++){
         for(int j = 1; j <= width ; j++){
               if(table[i][j] == -1)
                    A[i][j] = FindAttack(i, j, val);
         }
     }
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
