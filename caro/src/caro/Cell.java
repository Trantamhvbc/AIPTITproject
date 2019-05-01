 package caro;

import java.io.Serializable;

public class Cell implements Serializable {
    private int row;
    private int column;
    private  long values ;

    public Cell(int row, int column) {
        this.row = row;
        this.column = column;
    }
     public Cell(int row, int column,long values) {
        this.row = row;
        this.column = column;
        this.values = values;
    }

    public long getValues() {
        return values;
    }
    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }
}
