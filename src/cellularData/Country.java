package cellularData;

import java.text.DecimalFormat;

/**
 * A class generates an object with the country name and the SubscriptionYear object array.
 * @author Hsin Hsien (Cynthia) Chung
 */
public class Country {
    
    private String name;
    private LinkedList<SubscriptionYear> subscriptions = new LinkedList<SubscriptionYear>();
    private int minYear;
    private int maxYear;
    
    /**
     * To create a Country object containing the name of the country.
     * @param countryName the country name of the object.
     */
    public Country(String countryName) {
        setName(countryName);
    }
    
    /**
     * To set the country name of the object.
     * @param countryName the name of the country.
     */
    public void setName(String countryName) {
        this.name = countryName;
    }
    
    /**
    * To create a new SubscriptionYear object and add it in the subscriptions LinkedList.
    * @param year the year associates with the data.
    * @param subscription the data in certain year.
    */
    public void addSubscriptionYear(int year, double subscription) {
        subscriptions.add(new SubscriptionYear(year, subscription));
        
        if (subscriptions.size() == 1) {
            minYear = year;
            maxYear = year;
        } else if (year > maxYear) {
            maxYear = year;
        } else if (year < minYear) {
            minYear = year;
        }
    }
    
    /**
    * To get the total number of subscriptions between valid start and end years. 
    * @param startYear the first year of the period for calculation.
    * @param endYear the last year of the period for calculation.
    * @return a double number of total subscriptions in the given valid period.
    */
    public double getNumSubscriptionsForPeriod(int startYear, int endYear) throws IllegalArgumentException {
        String err1, err2, err3, err4, validSubscription;
        err1 = err2 = err3 = err4 = "";
        int requestedStartYear = startYear, requestedEndYear = endYear;
        int totalStartYear = minYear;
        int totalEndYear = maxYear;
        double totalSubscription = 0;
        
        java.util.Iterator<SubscriptionYear> iterator = subscriptions.iterator();
        
        if (startYear > endYear) {
            err1 = "Illegal Argument Request: The start year is greater than the end year.\n";
            int tempYear = endYear; endYear = startYear; startYear = tempYear;
        }
        if (isInvalidPeriod(totalStartYear, totalEndYear, startYear, endYear)) {
            err2 = printInvalidStartAndEnd(totalStartYear, totalEndYear, startYear, endYear);
            startYear = totalStartYear;
            endYear = totalEndYear;
        } else if (endYear > totalEndYear) {
            err3 = printInvalidEnd(totalEndYear, startYear, endYear);
            endYear = totalEndYear;
        } else if (startYear < totalStartYear ) {
            err4 = printInvalidStart(totalStartYear, startYear, endYear);
            startYear = totalStartYear;
        }
        
        while (iterator.hasNext()) {
            SubscriptionYear current = iterator.next();
            if (current.getYear() >= startYear && current.getYear() <= endYear) {
                totalSubscription += current.getSubscriptions();
            }
        }
        
        DecimalFormat formatter = new DecimalFormat("0.00");
        validSubscription = "The total number of subscriptions is " + formatter.format(totalSubscription);
        
        if (err1 != "" || err2 != "" || err3 != "" || err4 != "") {
            throw new IllegalArgumentException("\n" + err1 + err2 + err3 + err4 + "\n" + validSubscription);
        }
        return totalSubscription;
    }
    
    /**
     * To find out if the time period is valid comparing to the time period in the object.
     * @param totalStartYear is the starting year of all the existing subscription.
     * @param totalEndYear is the ending year of all the existing subscription.
     * @param startYear is the starting year of the requested period.
     * @param endYear is the ending year of the requested period.
     * @return a boolean indicating if the time period is valid
     */
    private boolean isInvalidPeriod(int totalStartYear, int totalEndYear, int startYear, int endYear) {
        return ((startYear < totalStartYear && endYear > totalEndYear) 
                || (startYear < totalStartYear && endYear <= totalStartYear) 
                || (startYear >= totalEndYear && endYear > totalEndYear));
    }
    
    /**
     * To get the illegal argument request with invalid start year and end year.
     * @param totalStartYear is the starting year of all the existing subscription.
     * @param totalEndYear is the ending year of all the existing subscription.
     * @param startYear is the starting year of the requested period.
     * @param endYear is the ending year of the requested period.
     * @return a String of the illegal argument.
     */
    private String printInvalidStartAndEnd
            (int totalStartYear, int totalEndYear, int startYear, int endYear) {
        return "Illegal Argument Request of start year " + startYear 
                       + " and end year " + endYear + ". \nValid period for " + this.getName() 
                       + " is " + totalStartYear + " to " + totalEndYear + ".\n";
    }
    
    /**
     * To get the illegal argument request with an invalid end year.
     * @param totalEndYear is the ending year of all the existing subscription.
     * @param startYear is the starting year of the requested period.
     * @param endYear is the ending year of the requested period.
     * @return a String of the illegal argument.
     */
    private String printInvalidEnd(int totalEndYear, int startYear, int endYear) {
        return "Illegal Argument Request of end year " + endYear 
                       + ". \nValid period for " + this.getName() + " is " + startYear 
                       + " to " + totalEndYear + ".\n";
    }
    
    /**
     * To get the illegal argument request with an invalid start year.
     * @param totalStartYear is the starting year of all the existing subscription.
     * @param startYear is the starting year of the requested period.
     * @param endYear is the ending year of the requested period.
     * @return a String of the illegal argument.
     */
    private String printInvalidStart(int totalStartYear, int startYear, int endYear) {
        return "Illegal Argument Request of start year " + startYear 
                       + ". \nValid period for " + this.getName() + " is " + totalStartYear 
                       + " to " + endYear + ".\n";
    }
    
    /**
    * To get a formatted country name and subscriptions as a String.
    */
    public String toString() {
        
        if (subscriptions == null) {
            return this.getName();
        }
        
        final int TOTAL_COUNTRY_HEADER_LENGTH = 52;
        
        String spacingAfterHeader = "";
        String subscriptionToString = "";
        
        for (int n = 0; n < TOTAL_COUNTRY_HEADER_LENGTH - this.getName().length(); n++) {
            spacingAfterHeader += " ";
        }
        
        for (SubscriptionYear subscription : subscriptions) {
            subscriptionToString += String.format("%10.2f", subscription.getSubscriptions());
        }
        
        String finalString = this.getName() + spacingAfterHeader + " " + subscriptionToString;
        return finalString;
    }
    
    
    /**
     * To get the name of the country.
     * @return a String as the name of the country.
     */
    public String getName() {
        return this.name;
    }
    
    /**
    * To get the subscriptions LinkedList.
    * @return a SubscriptionYear type of LinkedList.
    */
    public LinkedList<SubscriptionYear> getSubscriptions() {
        return subscriptions;
    }
    
    /**
     * To find if 2 Country object have the same country names.
     */
    public boolean equals(Object country) {
        if (this.getName().equalsIgnoreCase(((Country)country).getName())){
            return true;
        }
        return false;
    }
}
