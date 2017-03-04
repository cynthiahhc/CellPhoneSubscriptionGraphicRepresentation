package cellularData;
/**
 * A class generates an object with the year label and the subscription of the year.
 * @author Hsin Hsien (Cynthia) Chung
  */
public class SubscriptionYear implements Cloneable {
    
    private int year;
    private double subscriptions;
    
    /**
     * To create a SubscriptionYear object with a year and the subscription of the year.
     * @param year the year of the given data.
     * @param subscriptions the number associates with the given year.
     */
    public SubscriptionYear(int year, double subscriptions) {
        setYear(year);
        setSubscriptions(subscriptions);
    }
    
    /**
     * To set the year of the object.
     * @param year the subscription year of the given data.
     */
    public void setYear(int year) {
        this.year = year;
    }
    
    /**
     * To set the subscription associated with the given year.
     * @param subscriptions the amount of subscription of the given data.
     */
    public void setSubscriptions(double subscriptions) {
        this.subscriptions = subscriptions;
    }
    
    /**
     * To get the year label as an integer.
     * @return a integer as the year of the object.
     */
    public int getYear() {
        return this.year;
    }
    
    /**
     * To get the subscription amount.
     * @return a double as the subscription amount of the object.
     */
    public double getSubscriptions() {
        return this.subscriptions;
    }
    
    /**
     * To get the subscription amount of the object as a String.
     */
    public String toString() {
        return String.valueOf(subscriptions);
    }
    
    /**
     * To deep copy the SubscriptionYear object.
     */
    protected Object clone() {
        return new SubscriptionYear(this.year, this.subscriptions);
    }
    
}
