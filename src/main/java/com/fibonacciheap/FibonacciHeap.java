package com.fibonacciheap;

import java.util.*;

public class FibonacciHeap<E extends Comparable<E>> implements Queue<E> {

    private Node<E> min;
    private List<Node<E>> roots;
    private int size;

    public FibonacciHeap(){
        this.roots = new ArrayList<>();
        size = recursiveSize(roots);
        min = minimum(roots);
    }

    public FibonacciHeap(List<Node<E>> roots) {
        this.roots = roots;
        size = recursiveSize(roots);
        min = minimum(roots);
    }

    public FibonacciHeap(Node<E>...nodes) {
        this.roots = new ArrayList<>(List.of(nodes));
        size = recursiveSize(roots);
        min = minimum(roots);
    }

    @SafeVarargs
    public FibonacciHeap(E...elements){
        this.roots = new ArrayList<>();
        Arrays.stream(elements).forEach(element -> roots.add(new Node<>(element)));
        size = recursiveSize(roots);
        min = minimum(roots);
    }

    private int recursiveSize(List<Node<E>> list){
        int size = 0;
        if(!list.isEmpty()){
            for(Node<E> node : list){
                size++;
                size += recursiveSize(node.getNodes());
            }
        }
        return size;
    }

    private Node<E> minimum(List<Node<E>> roots){
        Node<E> min = null;
        for(Node<E> node : roots){
            if(min == null){
                min = node;
            } else if (min.getElement().compareTo(node.getElement()) > 0) {
                min = node;
            }
        }
        return min;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return roots.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        // TODO
        return false;
    }

    @Override
    public Iterator<E> iterator() {
        // TODO
        return null;
    }

    @Override
    public Object[] toArray() {
        // TODO
        return new Object[0];
    }

    @Override
    public <T> T[] toArray(T[] a) {
        // TODO
        return null;
    }

    @Override
    public boolean add(E e) {
        if(e == null){
            return false;
        }
        Node<E> toInsert = new Node<>(e);
        roots.add(toInsert);
        size++;
        if(min == null){
            min = toInsert;
        } else if (min.getElement().compareTo(e) > 0) {
            min = toInsert;
        }
        return true;
    }

    @Override
    public boolean remove(Object o) {
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        return false;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return false;
    }

    @Override
    public void clear() {

    }

    @Override
    public boolean equals(Object o) {
        return false;
    }

    @Override
    public int hashCode() {
        return 0;
    }

    @Override
    public boolean offer(E e) {
        return false;
    }

    @Override
    public E remove() {
        return null;
    }

    @Override
    public E poll() {
        return null;
    }

    @Override
    public E element() {
        return null;
    }

    @Override
    public E peek() {
        return null;
    }
}

class Node<E> {

    E element;
    List<Node<E>> nodes;

    public Node() {}

    public Node(E element) {
        this.element = element;
        this.nodes = new ArrayList<>();
    }

    public Node(E element, List<Node<E>> nodes) {
        this.element = element;
        this.nodes = nodes;
    }

    public E getElement() {
        return element;
    }

    public void setElement(E element){
        this.element = element;
    }

    public List<Node<E>> getNodes() {
        return nodes;
    }

    public void setNodes(List<Node<E>> nodes) {
        this.nodes = nodes;
    }

    public boolean isLeaf(){
        return nodes.isEmpty();
    }

}
