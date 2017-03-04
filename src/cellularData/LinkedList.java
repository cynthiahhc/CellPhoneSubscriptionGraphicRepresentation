package cellularData;

import java.util.Iterator;

/**
 * A class generates an object as a list containing the information for linked Node.
 * @author Hsin Hsien (Cynthia) Chung
 *
 */
public class LinkedList<T> implements Iterable<T> {
    
    private Node<T> head = null;
    private T data;
    
    /**
     * A constructor to create an empty LinkedList object.
     */
    LinkedList() {
    }
    
    /**
     * To add a type T object to the end of the list.
     * @param data the type T object to be added to the list.
     */
    public void add(T data) {
        
        if (head == null) {
            head = new Node<T>(data);
            
        } else {
            Node<T> current = head;
            
            while(current.getNext()!= null) {
                current = current.getNext();
            }
            current.setNext(new Node<T>(data));
        }
    }
    
    /**
     * To find the given type T object in the list, or return an empty type T object.
     * @param data the type T object to be searched for in the list.
     * @return a type T object, or an empty object if the searched type T object cannot be found.
     */
    public T contains(T data) {
        Node<T> current = head;
        while (current != null) {
            if ((current.getData()).equals((T)data)) {
                return current.getData();
            }
            current = current.getNext();
        }
        return null;
    }
    
    /**
     * To return a string containing all the information about every type T object in the list. 
     */
    public String toString() {
        String list = "\n";
        Node<T> current = head;
        int count = 1;
        while (current != null) {
            list += String.format("%03d",count) + ". " + current.toString() + "\n";
            current = current.getNext();
            count++;
        }
        return list;
    }
    
    
    /**
     * To find the type T object of the given index in the list.
     * @param index the index number in the list.
     * @return a type T object of the given index.
     */
    public T getIndex(int index) {
        Node<T> current = head;
        
        for (int counter = 0; counter < index; counter++) {
            current = current.getNext();
        }
        return current.getData(); 
    }
    
    /**
     * To find the size of the list.
     * @return an integer indicating the size of the list.
     */
    public int size() {
        Node<T> current = head;
        int size = 0;
        while (current!= null) {
            size++;
            current = current.getNext();
        }
        return size;
    }
    
    /**
     * To insert an type T object at the given index number in the list.
     * @param data the type T object.
     * @param index the index number where the type T object is inserted at with a start from 0. 
     * @throws IndexOutOfBoundsException an exception showing the error message when the index number is negative.
     */
    public void insertAtIndex(T data, int index) throws IndexOutOfBoundsException {
        Node<T> current = head;
        Node<T> newInsert;
        
        if (index < 0) {
            throw new IndexOutOfBoundsException
                    ("Invaid index: " + index + ". Index should be a positive number. The country " 
                    + data.toString() + " was not added to the list.");
        }
        
        if (index == 0) {
            newInsert = new Node<T>(data, current);
            head = newInsert;
        } else {
            for (int counter = 0; counter < index - 1; counter++) {
                if (current.getNext() == null) {
                    break;
                }
                current = current.getNext();
            }
        }
        newInsert = new Node<T>(data, current.getNext());
        current.setNext(newInsert);
    }
    
    
    /**
     * A constructor to create a type T Iterator ListIterator object. 
     */
    public Iterator<T> iterator() {
        return new ListIterator();
    }
    
    
    /**
     * An inner class which generates a ListIterator object.
     * @author Cynthia Chung
     *
     */
    private class ListIterator implements Iterator<T> {
        private Node<T> current;
        
        /**
         * A constructor to create an ListIterator object with a Node object.
         */
        public ListIterator() {
            current = head;
        }
        
        /**
         * To check if the next Node exists.
         */
        public boolean hasNext()  {
            if (current == null) {
                return false;
            }
            return true;
        }
        
        /**
         * To move to the next Node and return the Node.
         */
        public T next() {
            T data = current.getData();
            current = current.getNext();
            return data;
        }
    }
}
