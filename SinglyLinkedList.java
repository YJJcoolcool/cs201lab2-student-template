
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Name: Yam Jun Jie
 * Email: junjie.yam.2025@computing.smu.edu.sg
 */

public class SinglyLinkedList<E extends Comparable<E>> {
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

    public SinglyLinkedList(){

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
    /**
     * Swaps elements such that:
     * - Largest element swaps position with Smallest element
     * - 2nd Largest element swaps position with 2nd Smallest element, etc.
     */
    public void swap() {
        
        // Handle size 1 or empty linked lists
        if (this.size <= 1 || this.head == null) return;

        // Store the original order of nodes into an ArrayList
        List<Node<E>> nodes = new ArrayList<>();
        Node<E> current = this.head;
        while (current != null) {
            nodes.add(current);
            current = current.getNext();
        }

        // Create a sorted copy of nodes to be used as a reference list
        List<Node<E>> sortedNodes = new ArrayList<>(nodes);
        sortedNodes.sort((a, b) -> a.getElement().compareTo(b.getElement()));

        // Track current array index position for each node
        Map<Node<E>, Integer> nodesIndexMap = new HashMap<>();
        for (int i = 0; i < size; i++) {
            nodesIndexMap.put(nodes.get(i), i);
        }

        // Swap positions of k-th smallest and k-th largest nodes
        int n = size;
        for (int k = 0; k < n / 2; k++) {
            // k-th item from the front of the List
            Node<E> smallest = sortedNodes.get(k);
            // k-th item from the back of the List
            Node<E> largest = sortedNodes.get(n - 1 - k);

            if (smallest == largest) {
                continue;
            }

            // Get the original index
            int smallestIndex = nodesIndexMap.get(smallest);
            int largestIndex = nodesIndexMap.get(largest);

            if (smallestIndex == largestIndex) {
                continue;
            }

            // Swap positions of the smallest and largest in the nodes list
            nodes.set(smallestIndex, largest);
            nodes.set(largestIndex, smallest);

            // Update position map
            nodesIndexMap.put(smallest, largestIndex);
            nodesIndexMap.put(largest, smallestIndex);
        }

        // Re-link nodes and update head/tail
        this.head = nodes.get(0);
        for (int i = 0; i < n - 1; i++) {
            nodes.get(i).setNext(nodes.get(i + 1));
        }
        nodes.get(n - 1).setNext(null);
        this.tail = nodes.get(n - 1);
    }
   
}

