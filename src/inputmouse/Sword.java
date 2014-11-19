/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package inputmouse;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferStrategy;
import java.util.Vector;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Sword extends Canvas implements Runnable, MouseMotionListener {

    private JFrame window_game;
    private BufferStrategy strategy;
    private Vector listOfPositions;

    public Sword() {

        window_game = new JFrame("xxx");
        JPanel panel = (JPanel) window_game.getContentPane();
        panel.setDoubleBuffered(true);//######################
        setBounds(0, 0, 800, 600);
        panel.setPreferredSize(new Dimension(800, 600));
        panel.setLayout(null);
        panel.add(this);

        Image cursorImage = Toolkit.getDefaultToolkit().getImage("xparent.gif");
        Cursor blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(cursorImage, new Point(0, 0), "");
        setCursor(blankCursor);

        window_game.setBounds(0, 0, 800, 600);
        //  window_game.setUndecorated(true);
        window_game.setIgnoreRepaint(true);
        window_game.setResizable(false);
        window_game.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window_game.setLocationRelativeTo(null);
        window_game.setVisible(true);
        window_game.requestFocus();
        createBufferStrategy(2);
        strategy = getBufferStrategy();

        listOfPositions = new Vector();

        //  addMouseListener( this );
        addMouseMotionListener(this);
    }

    private void draw() {
        Graphics2D g = (Graphics2D) strategy.getDrawGraphics();
        g.setColor(Color.black);
        g.fillRect(0, 0, getWidth(), getHeight());
        g.setColor(Color.white);
        Stroke stroke = new BasicStroke(2, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0,
        new float[] { 3, 1 }, 0);
    g.setStroke(stroke);
        for (int j = 1; j < listOfPositions.size(); ++j) {
            Point A = (Point) (listOfPositions.elementAt(j - 1));
            Point B = (Point) (listOfPositions.elementAt(j));
            g.drawLine(A.x, A.y, B.x, B.y);
        }
        listOfPositions.clear();



        strategy.show();
        Toolkit.getDefaultToolkit().sync();
    }

    public void start() {
        Thread t = new Thread(this);
        t.setPriority(Thread.MAX_PRIORITY);
        t.start();
    }

    @Override
    public void run() {
        while (true) {
            try {
                draw();
                Thread.sleep(50);
            } catch (InterruptedException e) {
            }
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {

        if (listOfPositions.size() >= 5) {
            // delete the first element in the list
            listOfPositions.removeElementAt(0);
        }
        listOfPositions.addElement(new Point(e.getX(), e.getY()));
        e.consume();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
    }
}