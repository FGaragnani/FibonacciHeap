package com.fibonacciheap;

import java.util.*;

public class FibonacciHeap<E extends Comparable<E>> implements Queue<E> {

    private Node<E> min;
    private List<Node<E>> roots;
    private int size;

    public FibonacciHeap() {
        this.roots = new ArrayList<>();
        size = recursiveSize(roots);
        min = minimum(roots);
    }

    public FibonacciHeap(List<Node<E>> roots) {
        this.roots = roots;
        size = recursiveSize(roots);
        min = minimum(roots);
    }

    @SafeVarargs
    public FibonacciHeap(Node<E>... nodes) {
        this.roots = new ArrayList<>(List.of(nodes));
        size = recursiveSize(roots);
        min = minimum(roots);
    }

    @SafeVarargs
    public FibonacciHeap(E... elements) {
        this.roots = new ArrayList<>();
        Arrays.stream(elements).forEach(element -> roots.add(new Node<>(element)));
        size = recursiveSize(roots);
        min = minimum(roots);
    }

    private int recursiveSize(List<Node<E>> list) {
        int size = 0;
        if (!list.isEmpty()) {
            for (Node<E> node : list) {
                size++;
                size += recursiveSize(node.getNodes());
            }
        }
        return size;
    }

    private Node<E> minimum(List<Node<E>> roots) {
        Node<E> min = null;
        for (Node<E> node : roots) {
            if (min == null) {
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
        E e = (E) o;
        for (Node<E> node : roots) {
            if (node.getElement().compareTo(e) == 0) {
                return true;
            } else if (node.getElement().compareTo(e) < 0) {
                if(contains(e, node.getNodes())){
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public Iterator<E> iterator() {
        return getAll().iterator();
    }

    private boolean contains(E element, List<Node<E>> nodes) {
        for (Node<E> node : nodes) {
            if (node.getElement().compareTo(element) == 0) {
                return true;
            } else if (node.getElement().compareTo(element) < 0) {
                if(contains(element, node.getNodes())){
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public Object[] toArray() {
        return getAll().toArray();
    }

    @Override
    public <T> T[] toArray(T[] a) {
        // TODO
        return null;
    }

    @Override
    public boolean add(E e) {
        if (e == null) {
            return false;
        }
        Node<E> toInsert = new Node<>(e);
        roots.add(toInsert);
        size++;
        if (min == null) {
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
        for (E e : c) {
            if (!add(e)) {
                return false;
            }
        }
        return true;
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
        for (Node<E> node : roots) {
            clear(node);
        }
        roots.clear();
        size = 0;
        min = minimum(roots);
    }

    private void clear(Node<E> node) {
        if (node.isLeaf()) {
            return;
        }
        for (Node<E> children : node.getNodes()) {
            clear(children);
        }
        node.getNodes().clear();
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
        return add(e);
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
        if (min == null || roots.isEmpty()) {
            throw new RuntimeException("The heap is empty.");
        }
        return min.getElement();
    }

    @Override
    public E peek() {
        if (min == null) {
            return null;
        }
        return min.getElement();
    }

    private List<E> getAll() {
        List<E> toRet = new ArrayList<>();
        for (Node<E> node : roots) {
            toRet.add(node.getElement());
            toRet.addAll(getAll(node.getNodes()));
        }
        return toRet;
    }

    private List<E> getAll(List<Node<E>> nodes) {
        List<E> toRet = new ArrayList<>();
        for (Node<E> node : nodes) {
            toRet.add(node.element);
            toRet.addAll(getAll(node.getNodes()));
        }
        return toRet;
    }
}

class Node<E> {

    E element;
    List<Node<E>> nodes;

    public Node(){
        element = null;
        nodes = new ArrayList<>();
    }

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

    public List<Node<E>> getNodes() {
        return nodes;
    }

    public boolean isLeaf() {
        return nodes.isEmpty();
    }

    public void addChildren(E e) {
        addChildren(new Node<>(e));
    }

    public void addChildren(Node<E> node) {
        nodes.add(node);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Node<?> node = (Node<?>) o;
        return Objects.equals(element, node.element) && Objects.equals(nodes, node.nodes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(element, nodes);
    }
}
