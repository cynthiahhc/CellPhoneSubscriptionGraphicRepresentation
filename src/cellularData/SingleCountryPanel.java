package cellularData;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JPanel;

/**
 * A class to create a small panel with a color circle and a country name implementing MouseListener.
 * @author Hsin Hsien (Cynthia) Chung, Zhongqiu Liu
 *
 */
public class SingleCountryPanel extends JPanel implements MouseListener{
	private final int POINT_SIZE = 12;
    public static final int FONT_SIZE = 12;
    private Font font = new Font("SansSerif", Font.BOLD, FONT_SIZE);
    private GraphView graphView;
    private int countryIndex;
    
    /**
     * The constructor of the class to create a small panel for a single country.
     * @param graphView the main graph that all the data are associated with.
     * @param height the height of the panel.
     * @param longestName the character count of all the country in the country name array.
     * @param countryIndex the country index of the country in the Country linked list and country 
     * name array.
     */
    public SingleCountryPanel(GraphView graphView, int height, int longestName, int countryIndex) {
    	this.graphView = graphView;
        this.countryIndex = countryIndex;
        setPreferredSize(new Dimension(longestName*7, height));
        addMouseListener(this);
        }
    
    /**
     * To draw all the objects in the panel.
     */
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        g.setColor(graphView.getPlottedDataSet()[countryIndex][0].getColor());
        g.fillOval(GraphView.PADDING / 2,GraphView.PADDING, POINT_SIZE, POINT_SIZE);
        g.setColor(Color.BLACK);
        g.setFont(font);
        g.drawString(graphView.getLegendPanel().getCountryPanel().getCountryNameArray()[countryIndex],
                (int)(1.5 * GraphView.PADDING), GraphView.PADDING + POINT_SIZE);
    }
    
    /**
     * When the mouse clicks the left button, the country index will set to the clicked panel and 
     * update GraphView accordingly. When the mouse clicks the right button, it will reset GraphView. 
     */
    public void mouseClicked(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            graphView.setCountryIndex(countryIndex);
        } else if (e.getButton() == MouseEvent.BUTTON3) {
            graphView.showAllCountry();
        }
    }
    
    public void mouseEntered(MouseEvent e) { }
    public void mouseExited(MouseEvent e) {	}
    public void mousePressed(MouseEvent e) { }
    public void mouseReleased(MouseEvent e) { }
}
