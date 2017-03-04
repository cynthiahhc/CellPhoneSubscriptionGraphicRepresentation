package cellularData;

import java.awt.Dimension;
import javax.swing.BorderFactory;
import javax.swing.JScrollPane;
import javax.swing.border.Border;

/**
 * A class generates a Legend panel displaying the selected country names and the corresponding colors.
 * @author Hsin Hsien (Cynthia) Chung, Zhongqiu Liu
 */
public class LegendPanel extends JScrollPane {
    
    private CountryPanel countryPanel;
    
    /**
     * A constructor create a LegendPanel object attaching a CountryPanel object.
     * @param graphView the main graph that all the data are associated with.
     * @param width the width of the panel.
     * @param height the height of the panel.
     */
    public LegendPanel(GraphView graphView, int width, int height) {
        super(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        
        Border border = BorderFactory.createTitledBorder("Legend");
        setBorder(border);
        
        countryPanel = new CountryPanel(graphView, 350, graphView.getPlottedDataSet().length * (CountryPanel.FONT_SIZE + GraphView.PADDING));
        
        setPreferredSize(new Dimension(width, height));
        setViewportView(countryPanel);
    }
    
    /**
     * To get the CountryPanel in the Legend Panel.
     * @return a CountryPanel.
     */
    public CountryPanel getCountryPanel() { return countryPanel; }
}