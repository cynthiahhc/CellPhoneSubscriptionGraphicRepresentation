package cellularData;

import java.io.File;
import java.util.Scanner;
import java.io.FileNotFoundException;

/**
 * To read a CSV file from resource folder and generate an object to fill the CellularData class.
 * @author Hsin Hsien (Cynthia) Chung
 */
public class CSVReader {
    
    private String[] countryNames;
    private int[] yearLabels;
    private double[][] cellularDataTable;
    private int numOfCountries = 0;
    private int numberOfYears = 0;
    
    /**
     * The constructor of the class.
     * @param fileName is the CSV file path as the input.
     */
    public CSVReader(String fileName) {
        Scanner inputStream;
        try {
            inputStream = new Scanner(new File(fileName));
            while (inputStream.hasNextLine()) {
                String line = inputStream.nextLine();
                String[] parser = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");
                if (line.startsWith("Number of countries")) {
                    setNumberOfCountries(parser);
                    countryNames = new String[numOfCountries];
                } else if (line.startsWith("Country Name")) {
                    setNumberofYears(parser);
                    yearLabels = new int[numberOfYears];
                    cellularDataTable = new double[numOfCountries][numberOfYears];
                    setYearLabels(parser);
                } else if (!(line.startsWith("World Development Indicators"))) {
                    for (int countryIndex = 0; countryIndex < numOfCountries; countryIndex++) {
                        if (countryNames[countryIndex] == null) {
                            setCountryNames(parser, countryIndex);
                            setCellularDataTable(parser, countryIndex);
                            break;
                        }
                    }
                }
            }
            inputStream.close();
        } catch (FileNotFoundException e) {
            System.out.println("File " + fileName + " not found!");
        }
    }
    
    
    /**
     * To set the number of countries by reading through each line.
     * @param parser is an array splitting from the line through the scanner.
     */
    private void setNumberOfCountries(String[] parser) {

        numOfCountries = Integer.parseInt(parser[parser.length - 1]);
    }
    
    /**
     * To set the number of years by reading through the given parser array.
     * @param parser is an array splitting from the line through the scanner.
     */
    private void setNumberofYears(String[] parser) {
        numberOfYears = parser.length - 1;
    }
    
    /**
     * To set the yearLabels array by reading through the given parser array.
     * @param parser is an array splitting from the line through the scanner.
     */
    private void setYearLabels(String[] parser) {
        for (int n = 1; n < parser.length; n++) {
            yearLabels[n - 1] = Integer.parseInt(parser[n]);
        }
    }
    
    /**
     * To set the 2D cellularDataTable array by reading through the given parser array.
     * @param parser is an array splitting from the line through the scanner.
     * @param countryIndex is the corresponding index of the cellularDataTable array's first
     *        dimension.
     */
    private void setCellularDataTable(String[] parser, int countryIndex) {
        for (int i = 1; i < parser.length; i++) {
            cellularDataTable[countryIndex][i - 1] = Double.parseDouble(parser[i]);
        }
    }
    
    /**
     * To set the countryNames array by reading through the given parser array.
     * @param parser is an array splitting from the line through the scanner.
     * @param countryIndex is the corresponding index of the cellularDataTable array's first
     *        dimension.
     */
    private void setCountryNames(String[] parser, int countryIndex) {
        countryNames[countryIndex] = parser[0].replace("\"", "");
    }
    
    /**
     * To get all country names in an array.
     * @return a String array with all the country names.
     */
    public String[] getCountryNames() { return countryNames; }
    
    /**
     * To get all the years in an array.
     * @return a integer array with all the years. 
     */
    public int[] getYearLabels() { return yearLabels; }
    
    /**
     * To get all the cellular data in a 2D array.
     * @return a 2D array with all the cellular data.
     */
    public double[][] getParsedTable() { return cellularDataTable; }
    
    /**
     * To get the total number of years of data in the given CSV file.
     * @return an integer with the total number of years.
     */
    public int getNumberOfYears() { return numberOfYears; }

}