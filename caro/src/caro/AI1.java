/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package caro;

import com.sun.org.apache.xerces.internal.impl.xpath.regex.Match;
import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author Dell
 */
public class AI1 {
    private int val ;
    Cell nextAttaxkOne;
    private int level;
    BoardValues cur;
    Cell maxCell = null;
    int maxWide = 100;
    long C = 0;
    public AI1(int[][] Cur,int level) {
        this.cur = new BoardValues(Cur, 20, 20);
        this.val = 1;
        this.level = level;
        maxWide = 10;
    }
    public void alphaBeta(long  alpha, long beta, int depth) {

	try_Max(alpha, beta, depth);
		
    }
public  long try_Max(long alpha, long beta, int depth){
  //  cur.resetValue();
    long ret = cur.maxPoint().getValues();
    if(depth <= 0) return ret;
    else{
        cur.caculator(val);
        ret = Long.MIN_VALUE;
        ArrayList<Cell> list = new ArrayList<>();
        for (int i = 0; i < maxWide; i++) {
			Cell node = cur.maxPoint();
			if(node == null)
				break;
			list.add(node);
			cur.setValue(node.getRow(), node.getColumn(), 0);
		}
        for (int i = 0; i < list.size(); i++){
            Cell run = list.get(i);
            cur.setPointBoardTable(run.getRow(), run.getColumn(), val);
            ret = Math.max(ret, try_Min( alpha,  beta,depth - 1));
            cur.setPointBoardTable(run.getRow(), run.getColumn(), -1);
            if(ret >= beta || cur.checkWin(run.getRow(), run.getColumn(), val)){
                maxCell = run;
                return ret;
            }
            alpha = Math.max(alpha, ret);
        }
        return ret;
//        int v = Integer.MIN_VALUE;
//		for (int i = 0; i < list.size(); i++) {
//			Point com = list.get(i);
//			state.setPosition(com.x, com.y, 2);
//			v = Math.max(v, minValue(state, alpha, beta, depth+1));
//			state.setPosition(com.x, com.y, 0);
//			if(v>= beta || state.checkEnd(com.x, com.y)==2){
//				goPoint = com;
//				return v;
//				
//			}
//			alpha = Math.max(alpha, v);
//		}
//
//		return v;
        
    }
    
}
public  long try_Min(long alpha, long beta, int depth){
    long ret = cur.maxPoint().getValues();
    if(depth <= 0) return ret;
    else{
        ArrayList<Cell> list = new ArrayList<>();
        for (int i = 0; i < maxWide; i++) {
			Cell node = cur.maxPoint();
			if(node == null)
				break;
			list.add(node);
			cur.setValue(node.getRow(), node.getColumn(), 0);
		}
        for (int i = 0; i < list.size(); i++){
            Cell run = list.get(i);
            cur.setPointBoardTable(run.getRow(), run.getColumn(), (val+1) % 2); 
            ret = Math.min(ret, try_Max( alpha, beta,depth - 1));
            cur.setPointBoardTable(run.getRow(), run.getColumn(), -1);
            if(ret <= alpha || cur.checkWin(run.getRow(), run.getColumn(), (val+1)%2)){
                return ret;
            }
            beta = Math.min(beta, ret);
        }
        return ret;
    }
}
public  Cell NexAtack(){
    alphaBeta(0, 1,level);
    return maxCell;
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
