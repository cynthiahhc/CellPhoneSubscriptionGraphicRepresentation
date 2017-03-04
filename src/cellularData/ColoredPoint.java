package cellularData;

import java.awt.Color;
import java.awt.Point;
import java.text.DecimalFormat;

/**
 * A class to create a ColoredPoint object with a color and point location.
 * @author Hsin Hsien (Cynthia) Chung, Zhongqiu Liu
 *
 */
public class ColoredPoint extends Point {
    private Color color;
    private int originalX;
    private double originalY;
    
    
    /**
     * A constructor to create a ColoredPointed object with a color and point location.
     * @param color the color of the point.
     * @param mappedX the x-location of the point.
     * @param mappedY the y-location of the point.
     * @param originalX the original x data.
     * @param originalY the original y data.
     */
    ColoredPoint(Color color, double mappedX, double mappedY, int originalX, double originalY) {
        super();
        setLocation(mappedX, mappedY);
        setColor(color);
        setOriginalX(originalX);
        setOriginalY(originalY);
    }
    
    /**
     * To set the color of the point.
     * @param color the color of the point.
     */
    public void setColor(Color color) {
        this.color = color;
    }
    
    /**
     * To set the original x data.
     * @param originalX the original x data.
     */
    public void setOriginalX(int originalX) {
        this.originalX = originalX;
    }
    
    /**
     * To set the original y data.
     * @param originalY the original y data.
     */
    public void setOriginalY(double originalY) {
        this.originalY = originalY;
    }
    
    /**
     * To get the color of the point.
     * @return the color of the point.
     */
    public Color getColor() {
        return color;
    }
    
    /**
     * To get the label of mapped location.
     * @return a String of location formatted as (x, y).
     */
    public String getLabel() {
    	DecimalFormat formatter = new DecimalFormat("0.0");
        return "(" + originalX + ", " + formatter.format(originalY) + ")";
    }
}
