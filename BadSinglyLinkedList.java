
import java.util.HashSet;

/**
 * Name: Yam Jun Jie
 * Email: junjie.yam.2025@computing.smu.edu.sg
 */

public class BadSinglyLinkedList<E extends Comparable<E>> {
    private Node<E> head = null;
    private Node<E> tail = null;
    private int size = 0;

    private static class Node<E> {
        private E element;
        private Node<E> next;
    
        public Node(E e, Node<E> n){
            element = e;
            next = n;
        }
    
        public E getElement(){
            return element;
        }
    
        public Node<E> getNext(){
            return next;
        }
    
        public void setNext(Node<E> n){
            next = n;
        }
    }

    public BadSinglyLinkedList(){

    }

    public int size(){
        return size;
    }

    public boolean isEmpty(){
        return size == 0;
    }

    public E first(){
        if (isEmpty()){
            return null;
        } 
        return head.getElement();
    }

    public E last(){
        if (isEmpty()){
            return null;
        }
        return tail.getElement();
    }

    public void addFirst(E e){
        head = new Node<>(e, head);

        if (isEmpty()){
            tail = head;
        }
        size++;
    }

    public void addLast(E e){
        Node<E> newest = new Node<>(e, null);
        if (isEmpty()){
            head = newest;
        } else {
            tail.setNext(newest);
        }
        tail = newest;
        size++;
    }

    public E removeFirst(){
        if (isEmpty()){
            return null;
        }

        E answer = head.getElement();
        head = head.getNext();
        size--;

        if (isEmpty()){
            tail = null;
        }
        return answer;
    }

    public String toString(){
        StringBuilder sb = new StringBuilder();
        Node<E> current = head;
        while (current != null) {
            sb.append(current.getElement());
            sb.append(" ");
            current = current.getNext();
        }
        return sb.toString();
    }

    // write your codes here

    // This algorithm is inefficient as it runs in O(n^2)
    public void swap(){

        // Handle size 1 or empty linked lists
        if (this.size <= 1 || this.head == null) return;

        HashSet<Node<E>> alreadySwapped = new HashSet<>();

        // Keep repeating as long as a swappable pair is found
        boolean has_pair_to_swap = false;
        do {

            Node<E> current = this.head;
            Node<E> previous = null;

            // Move to the first node not already sorted
            while (current != null && alreadySwapped.contains(current)) {
                previous = current;
                current = current.getNext();
            }

            if (current == null) break;

            Node<E> biggest = current;
            Node<E> bef_biggest = previous;
            Node<E> smallest = current;
            Node<E> bef_smallest = previous;

            has_pair_to_swap = false;

            // Continue traversing till the end, finding the next biggest and smallest nodes
            while (current != null) {
                // System.out.printf("[DEBUG] alreadySwapped: %s\n", alreadySwapped);
                // System.out.printf("[DEBUG] current: %s\n", current.getElement());

                // Check that current element has not already been swapped, and that it is bigger than biggest
                if (!alreadySwapped.contains(current) && current.getElement().compareTo(biggest.getElement()) > 0) {
                    biggest = current;
                    bef_biggest = previous;

                // Same thing for smallest
                } else if (!alreadySwapped.contains(current) && current.getElement().compareTo(smallest.getElement()) < 0) {
                    smallest = current;
                    bef_smallest = previous;
                }

                previous = current;
                current = current.getNext();
            }

            // System.out.printf("[DEBUG] biggest == smallest: %s\n", biggest == smallest);

            // If biggest==smallest, this means there is only 1 node left, ending the loop
            if (biggest != smallest) {
                has_pair_to_swap = true;

                // System.out.printf("[DEBUG] Biggest: %d, Smallest: %d\n", biggest.getElement(), smallest.getElement());
                // System.out.printf("[DEBUG] Biggest: %d, Smallest: %d, bef_smallest: %d\n", biggest.getElement(), smallest.getElement(), bef_smallest.getElement());

                swapNodes(biggest, smallest, bef_biggest, bef_smallest);
                alreadySwapped.add(biggest);
                alreadySwapped.add(smallest);
            } else {
                has_pair_to_swap = false;
            }

            // System.out.printf("[DEBUG] %s%n", toString());
        } while (has_pair_to_swap);

    }

    private void swapNodes(Node<E> biggest, Node<E> smallest, Node<E> before_biggest, Node<E> before_smallest) {

        // System.out.printf("[DEBUG] Swapping %s and %s%n", biggest.getElement(), smallest.getElement());

        // Case 1: biggest is immediately before smallest
        if (biggest.getNext() == smallest) {
            if (before_biggest != null) {
                before_biggest.setNext(smallest);
            } else {
                this.head = smallest;
            }
            biggest.setNext(smallest.getNext());
            smallest.setNext(biggest);
        } 
        // Case 2: smallest is immediately before biggest
        else if (smallest.getNext() == biggest) {
            if (before_smallest != null) {
                before_smallest.setNext(biggest);
            } else {
                this.head = biggest;
            }
            smallest.setNext(biggest.getNext());
            biggest.setNext(smallest);
        } 
        // Case 3: Nodes are not adjacent
        else {
            if (before_biggest != null) {
                before_biggest.setNext(smallest);
            } else {
                this.head = smallest;
            }

            if (before_smallest != null) {
                before_smallest.setNext(biggest);
            } else {
                this.head = biggest;
            }

            Node<E> tempNext = biggest.getNext();
            biggest.setNext(smallest.getNext());
            smallest.setNext(tempNext);
        }

        // Update tail pointer if biggest or smallest are now the last node
        if (biggest.getNext() == null) {
            this.tail = biggest;
        } else if (smallest.getNext() == null) {
            this.tail = smallest;
        }
    }
   
}

