package cellularData;

/**
 * A class generates an object with a Type T object and another Type T Node object.
 * @author Hsin Hsien (Cynthia) Chung
 *
 */
public class Node<T> implements Cloneable {
    
    private T data;
    private Node<T> next;
    
    /**
     * A constructor to create a Node object with a type T object and an empty Node object.
     * @param data a type T object.
     */
    public Node(T data) {
        setData(data);
        setNext(null);
    }
    
    /**
     * A constructor to create a Node object with a type T object and another Node object.
     * @param data a type T object.
     * @param next the Node object containing the type T object and another Node object.
     */
    public Node(T data, Node<T> next) {
        setData(data);
        setNext(next);
    }
    
    /**
     * To set a type T object into the object.
     * @param data a type T object.
     */
    public void setData(T data) {
        this.data = data;
    }
    
    /**
     * To link another object to this object.
     * @param next another object being linked to this object.
     */
    public void setNext(Node<T> next) {
        this.next = next;
    }
    
    /**
     * To get the type T object in the object.
     * @return the type T object.
     */
    public T getData() {
        return data;
    }
    
    /**
     * To get an Node object which is linked to the object.
     * @return the Node object including the type T object and another Node object.
     */
    public Node<T> getNext() {
        return next;
    }
    
    /**
     * To get a String containing the type T object and another type T object of the Node.
     */
    public String toString() {
        return data.toString();
    }
}
