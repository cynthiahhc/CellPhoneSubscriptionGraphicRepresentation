package cellularData;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;

/**
 * A class to create a panel to calculate the amount of subscription.
 * @author Hsin Hsien (Cynthia) Chung, Zhongqiu Liu
 */
public class CalculationPanel extends JPanel {
    
    private GraphView graphView;
    private JLabel resultLabel = new JLabel();
    
    /**
     * The constructor of the class to create a big panel containing 3 labels, 2 text fields and 1 
     * button.
     * @param graphView the main graph that all the data are associated with.
     * @param width the width of the panel.
     * @param height the height of the panel.
     */
    public CalculationPanel(GraphView graphView, int width, int height) {
        this.graphView = graphView;
        
        setPreferredSize(new Dimension(width, height));
        Border border = BorderFactory.createTitledBorder("Calculate Subscription Rate");
        setBorder(border);
        setLayout(new FlowLayout(FlowLayout.LEFT));
        
        addToPanel();
    }
    
    /**
     * To add the components into the panel.
     */
    private void addToPanel() {
        YearField startYearField = new YearField(String.valueOf(graphView.getDataMinX()));
        YearField endYearField = new YearField(String.valueOf(graphView.getDataMaxX()));
        CalculationButton calculationButton = new CalculationButton(startYearField, endYearField);
        
        add(new JLabel("  From "));
        add(startYearField);
        add(new JLabel(" to "));
        add(endYearField);
        add(calculationButton);
        add(resultLabel);
    }
    
    /**
     * The text field sub-class showing default number when displaying.
     * @author Hsin Hsien (Cynthia) Chung, Zhongqiu Liu
     */
    private class YearField extends JTextField {
        
        private final Dimension FIELD_SIZE = new Dimension(50,20);
        
        /**
         * The constructor of the class to set default number.
         * @param text the year. 
         */
        public YearField(String text) {
            super(text, 4);
            setPreferredSize(FIELD_SIZE);
        }
    }
    
    /**
     * The button sub-class with ActionListener.
     * @author Hsin Hsien (Cynthia) Chung, Zhongqiu Liu
     */
    private class CalculationButton extends JButton implements ActionListener {
        
        private int countryIndex;;
        private YearField startYearField;
        private YearField endYearField;
        
        /**
         * The constructor of the class which creates a button with text on it.
         * @param startYearField The text field with an input of the start year for calculation.
         * @param endYearField The text field with an input of the end year for calculation.
         */
        public CalculationButton(YearField startYearField, YearField endYearField) {
            super("Calculate");
            
            setPreferredSize(new Dimension(100, 20));
            
            this.startYearField = startYearField;
            this.endYearField = endYearField;
            
            addActionListener(this);
        }
        
        /**
         * Calculate the subscription rate, after clicking the button. If there is any exception, 
         * a dialog will pop out.
         */
        public void actionPerformed(ActionEvent e) {
            countryIndex = graphView.getCountryIndex();
            
            if (countryIndex == -1) {
                JOptionPane.showMessageDialog(null, "Please click a country in the right panel first.",
                        "Alert", JOptionPane.ERROR_MESSAGE);
            } else {
                try {
                    calculateTextFieldInput();
                } catch(IllegalArgumentException ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Illegal Argument",
                            JOptionPane.WARNING_MESSAGE);
                    resultLabel.setText(null);
                }
            }
        }
        
        
        /**
         * Calculate the subscription rate.
         */
        private void calculateTextFieldInput() {
            String startYearStr = startYearField.getText();
            String endYearStr = endYearField.getText();
            int startYear = Integer.parseInt(startYearStr);
            int endYear = Integer.parseInt(endYearStr);
            
            Country country = (Country)graphView.getCountryList().getIndex(countryIndex);
            double result = country.getNumSubscriptionsForPeriod(startYear, endYear);
            
            DecimalFormat formatter = new DecimalFormat("0.00");
            
            resultLabel.setText("The total number of subscriptions is " + formatter.format(result));
        }
    }
}
