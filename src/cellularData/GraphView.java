package cellularData;

/**
 * This class will map the cellular data of a country to the width and height of the panel.
 * @author Hsin Hsien (Cynthia) Chung, Zhongqiu Liu
 */

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.text.DecimalFormat;
import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.Border;
import java.util.Iterator;
import java.util.Random;


public class GraphView extends JPanel implements MouseListener {

    public final static int MARGIN = 10;
    public final static int PADDING = 30; 
    public final static int WINDOW_BAR = 25;
    
    private final int SMALL_POINT_SIZE =  5;
    private final int LARGE_POINT_SIZE = 9;
    private final int RGB_RANGE = 255 + 1;
    private final Font SMALL_DOT_FONT = new Font("SansSerif", Font.PLAIN, 9);
    private final Font LARGE_DOT_FONT = new Font("SansSerif", Font.BOLD, 12);
    private final Font LABEL_FONT = new Font("SansSerif", Font.PLAIN, 11);
    private final Font AXIS_FONT = new Font("SansSerif", Font.BOLD, 15);
    
    private int dataMinX = 9999;
    private int dataMaxX = 0;
    private double dataMinY = 9999;
    private double dataMaxY = 0;
        
    private int width, height;
    private LinkedList<Country> countryList;
    private ColoredPoint[][] plottedDataSet;
    private LegendPanel legendPanel;
    private static int countryIndex = -1;
    
    private Ellipse2D[] ovalLocation;
    private boolean isInOval = false;
    private int pointIndex;
    private CalculationPanel calculationPanel;
    
    /**
     * The constructor that will set the size of the data map, set the border and pass data to the 
     * legendPanel class.
     * @param width The width of the data map
     * @param height The height of the data map
     * @param country The data of country names and subscriptions
     */
    public GraphView(int width, int height, LinkedList<Country> country) {
        countryList = country;
        this.width = height - 2 * MARGIN;
        this.height = height - 2 * MARGIN - WINDOW_BAR;
        
        setPreferredSize(new Dimension(this.width, this.height));
        
        findDataSize();
        setDataSet();
        
        legendPanel = new LegendPanel(this, width - this.width - 3 * MARGIN, this.height - PADDING);
        calculationPanel = new CalculationPanel(this, this.width + 3 * MARGIN - 100, 
                height - this.height - PADDING);
        
        Border border = BorderFactory.createTitledBorder("Graph View");
        setBorder(border);
        addMouseListener(this);
    }
    
    /**
     * Method to draw the objects
     */
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        setupOutline(g);
        addPointToPanel(g);
    }
    
    /**
     * Method to draw the outline objects.
     * @param g The Graphics object to draw.
     */
    private void setupOutline(Graphics g) {
        DecimalFormat formatter = new DecimalFormat("0.0");
        
        g.drawLine(PADDING*2, height-PADDING*2, width-PADDING, height-PADDING*2);
        g.drawLine(PADDING*2, PADDING, PADDING*2, height-PADDING*2);
        g.setFont(AXIS_FONT);
        g.drawString("Year", width/2, height - (int)(PADDING)/2);
        g.setFont(LABEL_FONT);
        g.drawString(String.valueOf((dataMaxX+dataMinX)/2), (width- PADDING)/2 , 
                height-PADDING*2+LABEL_FONT.getSize()*2);
        g.drawString(String.valueOf(dataMinX), PADDING*2, height-PADDING*2+LABEL_FONT.getSize()*2);
        g.drawString(String.valueOf(dataMaxX), width- 3 *PADDING, 
                height-PADDING*2+LABEL_FONT.getSize()*2);
        g.drawString(String.valueOf(formatter.format(dataMinY)), PADDING, height-PADDING*2);
        g.drawString(String.valueOf(formatter.format(dataMaxY)), PADDING, PADDING*2);
        g.drawString(String.valueOf(formatter.format((dataMinY+dataMaxY)/2)), PADDING, height/2);
        
        Graphics2D g2d = (Graphics2D)g;
        AffineTransform old = g2d.getTransform();
        g2d.rotate(-Math.PI/2, (int)(PADDING*0.7), (int)(height*0.7));
        g2d.setFont(AXIS_FONT);
        g2d.drawString("Number of Subscription (Per 100 People)", 
                (int)(PADDING*0.7), (int)(height*0.7));
        g2d.setTransform(old);
    }
    
    /**
     * Method to draw the points. Set the size of points. Set the font.
     * @param g The Graphics object to draw.
     */
    private void addPointToPanel(Graphics g) {
        
        if (countryIndex == -1) {
            for (ColoredPoint[] country : plottedDataSet){
                for (ColoredPoint point: country){
                    g.setFont(SMALL_DOT_FONT);
                    g.setColor(point.getColor());
                    g.drawString(point.getLabel(), (int)point.getX(), (int)point.getY());
                    Graphics2D g2d = (Graphics2D) g;
                    Ellipse2D oval = new Ellipse2D.Double((int)point.getX(), (int)point.getY(), 
                            SMALL_POINT_SIZE, SMALL_POINT_SIZE);
                    g2d.fill(oval);
                }
            }
        } else {
            ovalLocation = new Ellipse2D[plottedDataSet[0].length];
            int ovalCounter = 0;
            for (ColoredPoint point: plottedDataSet[countryIndex]) {
                g.setColor(point.getColor());
                Graphics2D g2d = (Graphics2D) g;
                Ellipse2D oval = new Ellipse2D.Double((int)point.getX(), (int)point.getY(), 
                        LARGE_POINT_SIZE, LARGE_POINT_SIZE);
                g2d.fill(oval);
                
                ovalLocation[ovalCounter] = oval;
                ovalCounter++;
            }
            if (isInOval) {
                g.setColor(Color.BLACK);
                ColoredPoint currentPoint =  plottedDataSet[countryIndex][pointIndex];
                g.setFont(LARGE_DOT_FONT);
                g.drawString(currentPoint.getLabel(), (int)currentPoint.getX(), 
                        (int)currentPoint.getY());
            }
        }
    }
    
    
    /**
     * Method to calculate the size of the map.
     * @param value The data of year or subscription.
     * @param dataMin The minimum value of the data.
     * @param dataMax The maximum value of the data
     * @param plottedMin The minimum value that can draw on the map.
     * @param plottedMax The maximum value that can draw on the map.
     * @return a double number value of the converted data.
     */
    static public final double map(double value, double dataMin, double dataMax, 
            double plottedMin, double plottedMax){
        return plottedMin + (plottedMax - plottedMin) * ((value - dataMin) / (dataMax - dataMin));
    }
    
    
    /**
     * Method that use the map() method to calculate the map for all countries.
     * @return Each country's points with its size fit the panel's size.
     */
    private ColoredPoint[][] setDataSet(){
        plottedDataSet = new ColoredPoint[countryList.size()]
                [countryList.getIndex(0).getSubscriptions().size()];
        int currentCountryIndex = 0;
        
        Iterator<Country> itr = countryList.iterator();
        
        while (itr.hasNext()){
            Country currentCountry = itr.next();
            LinkedList<SubscriptionYear> subscritonsLinkedList = currentCountry.getSubscriptions();
            
            Iterator<SubscriptionYear> itr2 = subscritonsLinkedList.iterator();
            Random random = new Random();
            Color color = new Color(random.nextInt(RGB_RANGE), 
                    random.nextInt(RGB_RANGE), random.nextInt(RGB_RANGE));
            int currentPoint = 0;
            
            while(itr2.hasNext()){
                SubscriptionYear currentSubscriptionYear = itr2.next();
                int valueX = currentSubscriptionYear.getYear();
                double valueY = currentSubscriptionYear.getSubscriptions();
                
                double mappedX = map(valueX, dataMinX, dataMaxX, PADDING*2, width- 3 *PADDING);
                double mappedY = map(valueY, dataMinY, dataMaxY, height-PADDING*2-SMALL_POINT_SIZE, 
                        PADDING*2);
                
                ColoredPoint point = new ColoredPoint(color, mappedX, mappedY, valueX, valueY);
                
                plottedDataSet[currentCountryIndex][currentPoint] = point;
                currentPoint++;
            }
            currentCountryIndex++;
        }
        return plottedDataSet;
    }
    
    /**
     * Method that go through all the data and detect the size.
     */
    private void findDataSize(){
        Iterator<Country> itr = countryList.iterator();
        
        while (itr.hasNext()){
            Country currentCountry = itr.next();
            LinkedList<SubscriptionYear> subscritonsLinkedList = currentCountry.getSubscriptions();
            
            Iterator<SubscriptionYear> itr2 = subscritonsLinkedList.iterator();
            
            while(itr2.hasNext()){
                SubscriptionYear currentSubscriptionYear = itr2.next();
                
                int valueX = currentSubscriptionYear.getYear();
                double valueY = currentSubscriptionYear.getSubscriptions();
                
                if (valueX < dataMinX) { dataMinX = valueX; }
                if (valueX > dataMaxX) { dataMaxX = valueX; }
                if (valueY < dataMinY) { dataMinY = valueY; }
                if (valueY > dataMaxY) { dataMaxY = valueY; }
            }
        }
    }
    
    public void setCountryIndex(int index) {
        countryIndex = index;
        repaint();
    }
    
    public void showAllCountry(){
    	countryIndex = -1;
    	repaint();
    }
    /**
     * Getter method for width.
     * @return Return the width.
     */
    public int getWidth() { return width; }
    
    /**
     * Getter method for height.
     * @return Return the height.
     */
    public int getHeight() { return height; }
    
    /**
     * Getter method for plotted points.
     * @return Return the plotted data of points.
     */
    public ColoredPoint[][] getPlottedDataSet() { return plottedDataSet; }
    
    /**
     * Getter method for legendPanel
     * @return Return the legendPanel.
     */
    public LegendPanel getLegendPanel() { return legendPanel; }
    
    /**
     * Getter method for CalculationPanel.
     * @return a CalculationPanel.
     */
    public CalculationPanel getCalculationPanel() { return calculationPanel; }
    
    /**
     * Getter method for the country index currently hold within the linked list.
     * @return a integer for the country index in the linked list.
     */
    public int getCountryIndex() { return countryIndex; }
    
    /**
     * Getter method for the minimum year of the SubscriptionYear object.
     * @return the minimum year of all the SubscriptionYear object.
     */
    public int getDataMinX() { return dataMinX; }
    
    /**
     * Getter method for the maximum year of the SubscriptionYear object.
     * @return the maximum year of all the SubscriptionYear object.
     */
    public int getDataMaxX() { return dataMaxX; }
    
    /**
     * Getter method for a Country type linked list.
     * @return the Country type linked list.
     */
    public LinkedList<Country> getCountryList() { return countryList; }
    
    
    /**
     * A method to display the label by checking if the location the mouse clicks is contained in 
     * the area of the circle.
     */
	public void mouseClicked(MouseEvent e) {
		if (e.getButton() == MouseEvent.BUTTON1) {
			if (countryIndex != -1) {
				int n = 0;
				for (Ellipse2D oval : ovalLocation) {
					if (oval.contains(e.getX(), e.getY())) {
						isInOval = true;
						pointIndex = n;
						repaint();
					}
					n++;
				}
			} else {
				JOptionPane.showMessageDialog(null, 
				        "Please click a country in the right panel first.", "Alert",
						JOptionPane.ERROR_MESSAGE);
			}
		}
		else if(e.getButton() == MouseEvent.BUTTON3) {
			countryIndex = -1;
	    	repaint();
		}
	}
    
	/**
	 * A method to repaint the graph if mouse exit the panel.
	 */
    public void mouseExited(MouseEvent e) {
        isInOval = false;
        repaint();
    }
    
    public void mouseEntered(MouseEvent e) { }
    public void mousePressed(MouseEvent e) { }
    public void mouseReleased(MouseEvent e) { }
}