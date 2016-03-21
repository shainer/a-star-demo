
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.BasicStroke;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.util.*;

/**
 *
 * @author shainer
 * chiama costruttore di pathfinding che crea la mappa con i muri, la partenza e l'arrivo
 * disegna la mappa con 2d
 * fai il pathfinding
 * fai l'animazione del punto se esiste un percorso
 */
public class Main extends JPanel {

    public static void main(String[] args) {
        JFrame frame = new JFrame("A* pathfinding");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new Main());
        frame.setSize(400, 400);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        final int START = 25;
        final int SIZE = 35;

        RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        rh.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setRenderingHints(rh);

        BasicStroke bs = new BasicStroke(4, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_BEVEL);
        g2d.setStroke(bs);

        PathFinding pf = new PathFinding();
        int rows = pf.getNumberRows();
        int cols = pf.getNumberColumns();
        Square[][] map = pf.getMap();
        Square source = pf.getSource();
        Square target = pf.getTarget();


        for (int x = START, i = 0; x < START + (SIZE * rows); x += SIZE, i++) {
            for (int y = START, j = 0; y < START + (SIZE * cols); y += SIZE, j++) {
                g2d.setColor(Color.black);
                g2d.drawRect(y, x, SIZE, SIZE);

                if (map[i][j].isWall()) {
                    g2d.setColor(Color.blue);
                } else {
                    g2d.setColor(Color.green);
                }
                g2d.fillRect(y + 3, x + 3, SIZE - 3, SIZE - 3);

                if (map[i][j].equals(source)) {
                    g2d.setColor(Color.blue);
                    g2d.fillOval(y + (SIZE / 3), x + (SIZE / 3), 10, 10);
                }
                if (map[i][j].equals(target)) {
                    g2d.setColor(Color.blue);
                    g2d.drawLine(y + (SIZE / 3), x + (SIZE / 3), (y + SIZE) - SIZE / 3, (x + SIZE) - SIZE / 3);
                    g2d.drawLine(y + (SIZE / 3), x + SIZE / 2 + SIZE / 3 - 4, y + SIZE / 2 + SIZE / 3 - 4, x + SIZE / 3);
                }
            }
        }

        pf.A_star();
        if (pf.findPath()) {
            map = pf.getMap();
            List<Square> path = pf.getPath();

            for (Square p : path) {
                int xCoord = (p.getRow() * SIZE) + START + SIZE/2 - 5;
                int yCoord = (p.getColumn() * SIZE) + START + SIZE/2 - 5;
                g2d.setColor(Color.red);
                g2d.fillOval(yCoord, xCoord, 10, 10);
            }
        } else {
            g2d.setColor(Color.black);
            g2d.drawString("No path exists", 50, 250);
        }
    }
}
