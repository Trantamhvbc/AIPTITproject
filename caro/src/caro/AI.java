/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package caro;

import com.sun.org.apache.xerces.internal.impl.xpath.regex.Match;

/**
 *
 * @author Dell
 */
public class AI {
    private int[][]  Cur ;
    private int val ;
    Cell nextAttaxkOne;
    private long [][] F;
    public AI(int[][] Cur, int val) {
        this.Cur = Cur;
        this.val = val;
       
    }
    long Pow(long a, int b){
        long S = 1;
        for(int i = 0 ; i < b  ; i++)  S = S* a;
        return S;
    }
    public int  findSuportOne(int row,int col,int parameterPot,int parameterTop){
       int n = 0;
       int i = row - 1;
       int d = 0;
       while(i > 0 && row - i <= parameterPot  ){
            if(Cur[i][col] == val ){
                i --;
                n ++;
            }
            else{
                if(Cur[i][col] == -1){
                    i--;
                    if(d == 0) d++;
                    else break;
                }
                else return 0;
            }
        }
       i = row + 1;
       d = 0;
       while(i < 21 && i - row  <= parameterTop  ){
            if(Cur[i][col] == val ){
                i ++;
                n ++;
            }
            else{
                if(Cur[i][col] == -1){
                    i++;
                    if(d == 0) d++;
                    else break;
                }
                else return 0;
            }
        }
       if((row - parameterPot-1 > 0 && Cur[row - parameterPot-1][col] == (val + 1) % 2)  ||(  row + parameterTop + 1  < 21 &&  Cur[ row + parameterTop + 1][col] == (val + 1) % 2)) n --;
       return n;
    }
    
    public int   findAttackOne(int row,int col){
        int n = 0;
        for(int i = 0 ; i <= 5 ; i ++){
            int GT = findSuportOne(row, col, i, 5-i);
            n = Math.max(GT, n);
        }
        return n;
    }
     public int  findSuportTwo(int row,int col,int parameterPot,int parameterTop){
       int n = 0;
       int i =  col - 1;
       int d = 0;
       while(i > 0 && col - i <= parameterPot  ){
            if(Cur[row][i] == val ){
                i --;
                n ++;
            }
            else{
                if(Cur[row][i] == -1){
                    i--;
                    if(d == 0) d++;
                    else break;
                }
                else return 0;
            }
        }
         i = col + 1;
         d = 0;
       while(i < 21 && i - col <= parameterTop  ){
            if(Cur[row][i] == val ){
                i ++;
                n ++;
            }
            else{
                if(Cur[row][i] == -1){
                    i++;
                    if(d == 0) d++;
                    else break;
                           
                }
                else return 0;
            }
        }
       if((col - parameterPot-1 > 0 && Cur[row][col - parameterPot-1] == (val + 1) % 2)  ||(  col + parameterTop + 1  < 21 &&  Cur[ row ][col + parameterTop + 1] == (val + 1) % 2)) n --;
       return n;
    }
    
    public int   findAttackTwo(int row,int col){
        int n = 0;
        for(int i = 0 ; i <= 5 ; i ++){
            int GT = findSuportTwo(row, col, i, 5-i);
            n = Math.max(GT, n);
        }
        return n;
    }
      public int  findSuportThree(int row,int col,int parameterPot,int parameterTop){
       int n = 0;
       int i =  1;
       int d = 0;
       while(row - i > 0 && col - i > 0  &&  i <= parameterPot  ){
            if(Cur[row  - i][ col - i] == val ){
                i ++;
                n ++;
            }
            else{
                if(Cur[row  - i][ col - i] == -1){
                    i++;
                    if(d == 0) d++;
                    else break;
                }
                else return 0;
            }
        }
         i = 1;
         d = 0;
       while(row + i < 21 && col + i < 21 && i  <= parameterTop  ){
            if(Cur[row + i][col + i] == val ){
                i ++;
                n ++;
            }
            else{
                if(Cur[row+ i][ col + i] == -1){
                    i++;
                    if(d == 0) d++;
                    else break;
                 
                }
                else return 0;
            }
        }
       int x1 = row - parameterPot - 1;
       int x2 = row + parameterTop + 1;
       int y1 = col - parameterPot - 1;
       int y2 = col + parameterTop + 1;
         // System.out.println(x1 );
       if((x1 > 0 && y1 > 0 && Cur[x1][y1] == (val + 1) % 2)  ||(  x2  < 21 && y2 < 21  && Cur[ x2 ][y2] == (val + 1) % 2)) n --;
       return n;
    }
    
    public int   findAttackThree(int row,int col){
        int n = 0;
        for(int i = 0 ; i <= 5 ; i ++){
            int GT = findSuportThree(row, col, i, 5-i);
            n = Math.max(GT, n);
        }
        return n;
    }
    public int  findSuportFor(int row,int col,int parameterPot,int parameterTop){
       int n = 0;
       int i =  1;
       int d = 0;
       while(row - i > 0 && col + i < 21  &&  i <= parameterPot  ){
           //System.out.println( row - i);
            if(Cur[row  - i][ col + i] == val ){
                i ++;
                n ++;
            }
            else{
                if(Cur[row  - i][ col + i] == -1){
                    i++;
                    if(d == 0) d++;
                    else break;
                }
                else return 0;
            }
        }
         i =  1;
         d = 0;
       while(row + i < 21 && col - i > 0 && i  <= parameterTop  ){
            if(Cur[row + i][col - i] == val ){
                i ++;
                n ++;
            }
            else{
                if(Cur[row+ i][ col - i] == -1){
                    i++;
                    if(d == 0) d++;
                    else break;
                }
                else return 0;
            }
        }
       int x1 = row - parameterPot - 1;
       int x2 = row + parameterTop + 1;
       int y1 = col + parameterPot + 1;
       int y2 = col - parameterTop - 1;
       if((x1 > 0 && y1 < 21 && Cur[x1][y1] == (val + 1) % 2)  ||(  x2  < 21 && y2 > 0  && Cur[ x2 ][y2] == (val + 1) % 2)) n --;
       return n;
    }
    
    public int   findAttackFor(int row,int col){
        int n = 0;
        for(int i = 0 ; i <= 5 ; i ++){
            int GT = findSuportFor(row, col, i, 5-i);
            n = Math.max(GT, n);
        }
        return n+1;
    }
    long FindAttack(int row,int col){
        long S = 0;
        S = S + Pow(50, findAttackOne(row, col));
        S = S + Pow(50, findAttackTwo(row, col));
        S = S + Pow(50, findAttackThree(row, col));
        S = S + Pow(50, findAttackFor(row, col));
        Cur[row][col] = val;
        val = (val + 1) % 2;
         int t;
        if(row + 1 < 21 && Cur[row + 1][col ] == val){
            t = findSuportOne(row, col ,0, 4);
            S = S + Pow(50,t)+ 30 * Pow(50, t - 1)  ;
        }
            
        if(row - 1 > 0 && Cur[row + 1][col ] == val){
             t = findSuportOne(row, col ,0, 4);
            S = S + Pow(50,t)+ 30 * Pow(50, t - 1)  ;
        }
            
        if(col + 1 < 21 && Cur[row][col + 1] == val){
             t = findSuportTwo(row, col, 0, 4);
            S = S + Pow(50,t)+ 30 * Pow(50, t - 1)  ;
        }
        if(col - 1  >0 && Cur[row][col - 1] == val){
             t = findSuportTwo(row, col, 4, 0);
            S = S + Pow(50,t)+ 30 * Pow(50, t - 1)  ;
        }
        if(row + 1 < 21 && col + 1 < 21 && Cur[row + 1][col + 1] == val){
             t = findSuportThree(row, col, 0, 4);
            S = S + Pow(50,t)+ 30 * Pow(50, t - 1)  ;
        }
        if(row - 1 > 0 && col - 1 > 0 && Cur[row - 1][col - 1] == val){
             t = findSuportThree(row, col, 4, 0);
            S = S + Pow(50,t)+ 30 * Pow(50, t - 1)  ;
        } 
        if(row + 1 < 21 && col - 1 > 0 && Cur[row+1][col-1] == val){
             t = findSuportFor(row, col, 0, 4);
            S = S + Pow(50,t)+ 30 * Pow(50, t - 1)  ;
        }
        if(row - 1 > 0 && col + 1 > 0 && Cur[row-1][col+1] == val){
             t = findSuportFor(row, col, 4, 0);
            S = S + Pow(50,t)+ 30 * Pow(50, t - 1)  ;
        }
        val = (val + 1) % 2;
        Cur[row ][col] = -1;
        return S;
    }
     Cell NexAtack(){
        long [][] A= new long[21][21];
        for(int i = 1 ; i < 21 ; i++ )
            for(int j = 1 ; j < 21 ; j++)
                if(Cur[i][j] == -1)
                A[i][j] = FindAttack(i,j );
        int maxx = 0 ,maxy = 0;
        long S = 0;
        for(int i = 1 ; i < 21 ; i++ )
            for(int j = 1 ; j < 21 ; j++){
                if(A[i][j] > S){
                    S = A[i][j];
                    maxx = i;
                    maxy = j;
 
                    System.out.println(j);
                }
            
            }
         for(int i = 1 ; i < 21 ; i++){
            for(int j = 1; j < 21 ; j++) System.out.printf(A[i][j] + " ");
            System.out.println("");
        }
         System.out.println("\n");
        Cell Ans = new Cell(maxx, maxy);
        return Ans;
    }
//    public static void main(String[] args) {
//        int [][] D = new int [21][21];
//        for(int i = 0 ;i < 21 ; i ++)
//            for(int j = 0 ; j < 21 ; j++ ) D[i][j] = -1;
//        D[4 ][5] = 0;
//        D[6][5] = 1;
//        D[7][5] = 1;
//        D[13][5] = -1;
//        AI A;
//        A = new AI(D, 1);
//        for(int i = 1 ; i < 21 ; i++){
//            for(int j = 1; j < 21 ; j++) System.out.printf(D[i][j] + " ");
//            System.out.println("");
//        }
//        A.NexAtack();
//        System.out.println(A.findAttackFor(8, 3));     
//        
   // }
}
