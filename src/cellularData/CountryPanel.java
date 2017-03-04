package cellularData;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.Iterator;

import javax.swing.JPanel;

/**
 * A graph panel listing country names and the corresponding colors.
 * @author Hsin Hsien (Cynthia) Chung, Zhongqiu Liu
 *
 */
public class CountryPanel extends JPanel{
    
    public static final int FONT_SIZE = 12;
    
    private String[] countryNameArray;
    
    private int longestName = 0;
    private GraphView graphView;
    
    /**
     * A constructor create a CountryPanel object displaying the list and the color.
     * @param graphView the main graph that all the data are associated with.
     * @param width the width of the panel.
     * @param height the height of the panel.
     */
    public CountryPanel(GraphView graphView, int width, int height) {
        
        this.graphView = graphView;
        
        findCountryNameArray();
        setPreferredSize(new Dimension(longestName * 7, height));
        
        GridBagLayout layout = new GridBagLayout();
        GridBagConstraints c = new GridBagConstraints();
        setLayout(layout);
        
        c.fill = GridBagConstraints.BOTH;
        c.anchor = GridBagConstraints.NORTHWEST;
        c.weightx = 1;
        c.weighty = 1;
        
        for (int countryIndex = 0; countryIndex < countryNameArray.length; countryIndex++) {
            SingleCountryPanel singlePanel = 
                    new SingleCountryPanel(graphView, height/countryNameArray.length, longestName, countryIndex);
            c.gridwidth = 0;
            add(singlePanel, c);
        }
    }
    
    
    /**
     * To find the name of the selected countries and calculated the maximum length of name of the selected country.
     * @return a String array with the selected country names.
     */
    private String[] findCountryNameArray() {
        countryNameArray = new String[graphView.getCountryList().size()];
        int countryNameIndex = 0;
        
        Iterator<Country> itr = graphView.getCountryList().iterator();
        while (itr.hasNext()) {
            Country currentCountry = itr.next();
            countryNameArray[countryNameIndex] = currentCountry.getName();
            
            if (countryNameArray[countryNameIndex].length() > longestName) {
                longestName = countryNameArray[countryNameIndex].length();
            }
            countryNameIndex++;
        }
        return countryNameArray;
    }
    
    /**
     * To get the character count of longest name in the countryNameArray.
     * @return the character count of the longest name.
     */
    public int getLongestName() { return longestName; }
    
    /**
     * To get the array with the country names obtaining from the Country linked list.
     * @return a string array containing the country names.
     */
    public String[] getCountryNameArray() { return countryNameArray; } 
}
