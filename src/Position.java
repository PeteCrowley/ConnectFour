import java.util.Objects;

public class Position {
    private int row;
    private int col;

    public Position(){
        row = -1;
        col = 1;
    }

    public Position(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Position position = (Position) o;
        return row == position.row && col == position.col;
    }


    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getColumn() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }
}
