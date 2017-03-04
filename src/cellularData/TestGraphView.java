package cellularData;
/**
 *  Tests the GraphView class by creating an object of type GraphView and adding components to it.
 *  Creates one container of type JFrame and adds an object of type GraphView.
 *
 * @author Foothill College, Hsin Hsien (Cynthia) Chung, Zhongqiu Liu
 */

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.util.Random;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class TestGraphView 
{
	private final int FRAME_WIDTH = 900;
	private final int FRAME_HEIGHT = 700;

	/**
	 * Builds a list of countries to debug.
	 */
	private void debugListOfCountries(Country [] allCountries)
	{
		// TODO: The purpose is to help you debug
		// Note: The implementation of method is optional.
	}

	/**
	 * Creates a generic linked list. Then based on the user's input,
	 * adds a random number of countries to the list.
	 * @param allCountries      An array of Country objects
	 */
	private LinkedList<Country> buildCountryList(Country [] allCountries)
	{	
		JFrame frame = new JFrame("Cellular Graph");

		String userInput = (String)JOptionPane.showInputDialog(
				frame,
				"Enter the number of countries to graph:\n",
				"Input",
				JOptionPane.PLAIN_MESSAGE,
				null,
				null,
				"5");

		int requestedSize = Integer.parseInt(userInput);

		// Build the list out of a random selection of countries.
		Random random = new Random();

		// A singly linked list of country data.
		LinkedList<Country> selectedCountries = new LinkedList<Country>();
		if (requestedSize >= allCountries.length) {
		    
		    JOptionPane.showMessageDialog(null, "The total countries is less than the input request.",
		            "Message", JOptionPane.WARNING_MESSAGE);
		    
		    for (int i = 0; i < allCountries.length; i++){
		        selectedCountries.add(allCountries[i]);
		    }
		} else {
		    int n = 0;
    		while (n < requestedSize) {
    		    int selectedIndex = random.nextInt(allCountries.length);
    			if (selectedCountries.contains(allCountries[selectedIndex])==null) {
    		        selectedCountries.add(allCountries[selectedIndex]);
    		        n++;
    		    }
    		}
		}
		return selectedCountries;
	}


	/**
	 * Initializes the GUI with two JPanels and populates them.
	 * One panel draws the data points, the second draws the legend.
	 * @param selectedCountries      A randomly selected list of countries.
	 *
	 * Note: You may add as many panels as you see fit.
	 */
	private void initializeGui(LinkedList<Country> selectedCountries)
	{
		JFrame frame = new JFrame("Cellular Graph");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setLocation(dim.width/2-FRAME_WIDTH/2, dim.height/2-FRAME_HEIGHT/2);
		
		
		GridBagLayout layout = new GridBagLayout();
        GridBagConstraints c = new GridBagConstraints();
        
		frame.setLayout(layout);

		int graph_panel_size = FRAME_WIDTH;

		GraphView myPlots = new GraphView(graph_panel_size, FRAME_HEIGHT-50, selectedCountries);
		frame.getContentPane().add(myPlots);

		LegendPanel myLabel = myPlots.getLegendPanel();
		frame.getContentPane().add(myLabel);
		
		CalculationPanel myCalculation = myPlots.getCalculationPanel();
		frame.getContentPane().add(myCalculation, c);
		

		InstructionButton instructionButton = new InstructionButton();
		frame.getContentPane().add(instructionButton);
		
		
		c.fill = GridBagConstraints.BOTH;
        c.anchor = GridBagConstraints.NORTHWEST;
        c.insets = new Insets(0,0,0,0);
		
        c.gridx = 0;
        c.gridy = 0;
		layout.setConstraints(myPlots, c);
		
		c.gridwidth = 0;
		c.gridx = 1;
		c.gridy = 0;
		layout.setConstraints(myLabel, c);
		
		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 1;
		layout.setConstraints(myCalculation, c);
		
		c.gridx = 1;
		c.gridx = 1;
		layout.setConstraints(instructionButton, c);
		
		frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
		frame.setResizable(false);
		frame.setVisible(true);
	}

	/**
	 * Uses a CSV to parse a CSV file.
	 * Adds the data for each country to an array of Country objects.
	 * Adds a random selection of countries to a generic LinkedList object.
	 * Draws the list of countries to a JFrame.
	 */
	public static void main(String[] args) 
	{	
	    // test file with shorter number of countries and subscription years
		//final String FILENAME = "resources/cellular_short_oneDecade.csv";	
	    
	    // test file with latest set of countries and subscription years
		final String FILENAME = "resources/cellular.csv";	

		CSVReader parser = new CSVReader(FILENAME);

		String [] countryNames = parser.getCountryNames();
		int [] yearLabels = parser.getYearLabels();
		double [][] parsedTable = parser.getParsedTable();		

		Country [] countries;

		countries = new Country[countryNames.length];

		Country current;

		for (int countryIndex = 0; countryIndex < countries.length; countryIndex++)
		{
			int numberOfYears = yearLabels.length;

			current = new Country(countryNames[countryIndex]);

			for (int yearIndex = 0; yearIndex < numberOfYears; yearIndex++)
			{
				double [] allSubscriptions = parsedTable[countryIndex];
				double countryData = allSubscriptions[yearIndex];
				current.addSubscriptionYear(yearLabels[yearIndex], countryData);
			}
			countries[countryIndex] = current;
		}


		TestGraphView application = new TestGraphView();

		//application.debugListOfCountries(countries);

		LinkedList<Country> listOfCountries = application.buildCountryList(countries);

		application.initializeGui(listOfCountries);

		System.err.flush();

		System.out.println("\nDone with TestGraphView.");
	}
}