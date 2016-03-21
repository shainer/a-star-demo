import java.util.List;
import java.util.LinkedList;
import java.util.Random;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.LinkedHashSet;

public class PathFinding {

    private final int N_ROWS = 5;
    private final int N_COLS = 7;
    private final int N_WALLS = 16;
    private Square source, target;
    private Square[][] map;
    private List<Square> path;

    public PathFinding() {
        Random generator = new Random();
        map = new Square[N_ROWS][N_COLS];

        for (int i = 0; i < N_ROWS; i++)
        {
            for (int j = 0; j < N_COLS; j++)
            {
                map[i][j] = new Square();
                map[i][j].setRow(i);
                map[i][j].setColumn(j);
            }
        }
        generateRandomPattern(generator);
        source = getRandomSquare(generator);
        target = getRandomSquare(generator);
    }

    public Square[][] getMap() {
        return map;
    }

    public Square getSource() {
        return source;
    }

    public Square getTarget() {
        return target;
    }

    public int getNumberRows() {
        return N_ROWS;
    }

    public int getNumberColumns() {
        return N_COLS;
    }

    public void A_star() {
        List<Square> closedList = new LinkedList<Square>();
        PriorityQueue<Square> openList = new PriorityQueue<Square>();
        openList.add(source);

        while (!closedList.contains(target) && !openList.isEmpty()) {
            Square current = openList.poll();
            closedList.add(0, current);

            for (Square s : getAdjacentSquares(current)) {
                if (!closedList.contains(s)) {
                    if (!openList.contains(s)) {
                        s.setParent(current);
                        if (s.getRow() == current.getRow() || s.getColumn() == current.getColumn()) {
                            s.setG(current.getG() + 10);
                        } else {
                            s.setG(current.getG() + 14);
                        }
                        s.setH(manhattanHeuristic(s));
                        openList.add(s);

                    } else {
                        int p = calculateNewG(current, s);
                        if (p < s.getG()) {
                            openList.remove(s);
                            s.setG(p);
                            s.setParent(current);
                            openList.add(s);
                        }
                    }
                }
            }
        }
    }

    private int manhattanHeuristic(Square s) {
        return 10 * (Math.abs(s.getRow() - target.getRow() + Math.abs(s.getColumn() - target.getColumn())));
    }

    private Square getRandomSquare(Random generator) {
        int x, y;

        do
        {
            x = generator.nextInt(N_ROWS);
            y = generator.nextInt(N_COLS);
        } while (map[x][y].isWall());

        return map[x][y];
    }

    private void generateRandomPattern(Random generator) {
        for (int i = 0; i < N_WALLS; i++) {
            int x = generator.nextInt(N_ROWS);
            int y = generator.nextInt(N_COLS);
            map[x][y].setWall(true);
        }
    }

    private Set<Square> getAdjacentSquares(Square current) {
        Set<Square> adjacents = new LinkedHashSet<Square>();
        int x = current.getRow();
        int y = current.getColumn();

        for (int i = Math.max(0, x-1); i <= Math.min(x+1, N_ROWS-1); i++)
        {
            for (int j = Math.max(0, y-1); j <= Math.min(y+1, N_COLS-1); j++)
            {
                if (!(i == x && j == y) && !map[i][j].isWall())
                {
                    adjacents.add(map[i][j]);
                }
            }
        }
        return adjacents;
    }

    private int calculateNewG(Square current, Square s) {
        int G = 0;

        if (s.getRow() == current.getRow() || s.getColumn() == current.getColumn()) {
            G = current.getG() + 10;
        } else {
            G = current.getG() + 14;
        }

        return G;
    }

    public boolean findPath() {
        path = new LinkedList<Square>();
      
        Square p = target;
        if (target.getParent() == null) {
            return false;
        }

        do {
            p.setPath(true);
            path.add(0, p);
            p = p.getParent();
        } while (!p.equals(source));

        path.remove(target);
        source.setPath(true);
        return true;
    }
    
    public List<Square> getPath()
    {
      return path;
    }

    private void printMap(Square[][] map) {
        for (int i = 0; i < N_ROWS; i++) {
            for (int j = 0; j < N_COLS; j++) {
                if (map[i][j].isPath()) {
                    System.out.print("T ");
                } else if (map[i][j].isWall()) {
                    System.out.print("W ");
                } else {
                    System.out.print("F ");
                }
            }
            System.out.println();
        }
    }
}
