public class Square implements Comparable {

    private int F, G, H;
    private int row, column;
    private Square parent;
    private boolean isPath;
    private boolean isWall;

    public Square() {
        this.isWall = this.isPath = false;
        this.F = this.G = this.H = 0;
        this.parent = null;
    }

    public int compareTo(Object o) {
        Square s = (Square)o;

        if (this.F < s.getF()) {
            return -1;
        }
        else if (this.F > s.getF()) {
            return 1;
        }
        return 0;
    }

    public boolean equals(Object o) {
        if (o == null || o.getClass() != getClass()) {
            return false;
        }
        Square s = (Square) o;

        return (s.row == row) && (s.column == column);
    }

    public int getF() {
        return F;
    }

    public int getG() {
        return G;
    }

    public void setG(int G) {
        this.G = G;
        this.F = this.G + this.H;
    }

    public int getH() {
        return H;
    }

    public void setH(int H) {
        this.H = H;
        this.F = this.G + this.H;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public boolean isPath() {
        return isPath;
    }

    public void setPath(boolean isPath) {
        this.isPath = isPath;
    }

    public boolean isWall() {
        return isWall;
    }

    public void setWall(boolean isWall) {
        this.isWall = isWall;
    }

    public Square getParent() {
        return parent;
    }

    public void setParent(Square parent) {
        this.parent = parent;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }
    
    public String toString()
    {
      return "X=" + row + "e Y=" + column;
    }
}
