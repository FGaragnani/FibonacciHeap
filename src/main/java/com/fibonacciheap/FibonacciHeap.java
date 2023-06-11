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
                if (contains(e, node.getNodes())) {
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
                if (contains(element, node.getNodes())) {
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
        if(a.length < size){
            a = (T[]) new Object[size];
        }
        int i = 0;
        for(Iterator<E> iterator = iterator(); iterator.hasNext(); ){
            a[i] = (T) iterator.next();
            i++;
        }
        return a;
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
        if(o == null || !contains(o)){
            return false;
        }

        E element = (E) o;
        if(element.compareTo(min.getElement()) == 0){
            for(Node<E> root : roots){
                if(root.getElement().compareTo(element) == 0){
                    roots.addAll(root.getNodes());
                    roots.remove(root);
                    size--;
                    return true;
                }
            }
        }
        for(Node<E> root : roots){
            if(root.getElement().compareTo(element) == 0){
                roots.addAll(root.getNodes());
                roots.remove(root);
                size--;
                return true;
            }
            for(Node<E> children : root.getNodes()){
                if(children.getElement().compareTo(element) > 0){
                    continue;
                }
                if(remove(root, children, element)){
                    size--;
                    return true;
                }
            }
        }
        size--;
        return true;
    }

    private boolean remove(Node<E> father, Node<E> children, E toRemove){
        if(children.getElement().compareTo(toRemove) == 0){

            if(children.isLeaf()){
                father.getNodes().remove(children);
            } else {
                roots.addAll(children.getNodes());
                father.getNodes().remove(children);
                children.nodes = null;
            }

            return true;
        } else {
            for(Node<E> node : children.getNodes()){
                if(node.getElement().compareTo(toRemove) > 0){
                    continue;
                }
                if(remove(children, node, toRemove)){
                    return true;
                }
            }
        }

        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        for (Object e : c) {
            if (!contains(e)) {
                return false;
            }
        }
        return true;
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
        for(Object o : c){
            if(!remove(o)){
                return false;
            }
        }
        return true;
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
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        FibonacciHeap<?> that = (FibonacciHeap<?>) o;
        return size == that.size && Objects.equals(min, that.min) && Objects.equals(roots, that.roots);
    }

    @Override
    public int hashCode() {
        return Objects.hash(min, roots, size);
    }

    @Override
    public boolean offer(E e) {
        return add(e);
    }

    @Override
    public E remove() {
        if(roots.isEmpty()){
            throw new RuntimeException("The heap is empty!");
        }
        E element = min.getElement();
        remove(element);

        HashMap<Integer, Node<E>> degrees = new HashMap<>();
        for (Node<E> root : roots) {
            if (!degrees.containsKey(root.degree())) {
                degrees.put(root.degree(), root);
                continue;
            }

            Node<E> toMerge1 = root;
            Node<E> toMerge2 = degrees.get(toMerge1.degree());

            while (true) {

                if (toMerge1.getElement().compareTo(toMerge2.getElement()) < 0) {
                    degrees.remove(toMerge1.degree());
                    toMerge1.getNodes().add(toMerge2);
                    if (degrees.containsKey(toMerge1.degree())) {
                        toMerge2 = degrees.get(toMerge1.degree());
                    } else {
                        degrees.put(toMerge1.degree(), toMerge1);
                        break;
                    }
                } else {
                    degrees.remove(toMerge1.degree());
                    toMerge2.getNodes().add(toMerge1);
                    if (degrees.containsKey(toMerge2.degree())) {
                        toMerge1 = toMerge2;
                        toMerge2 = degrees.get(toMerge1.degree());
                    } else {
                        degrees.put(toMerge2.degree(), toMerge2);
                        break;
                    }
                }
            }
        }

        roots = new ArrayList<>();
        for(Map.Entry<Integer, Node<E>> entry : degrees.entrySet()){
            roots.add(entry.getValue());
        }
        min = minimum(roots);

        return element;
    }

    @Override
    public E poll() {
        if(roots.isEmpty()){
            return null;
        }
        return remove();
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

    public void decreaseKey(Node<E> node, E newKey) {
        if (node.getElement().compareTo(newKey) <= 0) {
            return;
        }
        for (Node<E> root : roots) {
            if (root == node) {
                root.setElement(newKey);
                if(min.getElement().compareTo(newKey) > 0){
                    min = node;
                }
                return;
            } else if (root.getElement().compareTo(node.getElement()) > 0) {
                continue;
            }
            for (Node<E> children : root.getNodes()) {
                if(children.getElement().compareTo(node.getElement()) > 0){
                    continue;
                }
                decreaseKey(root, children, node, newKey);
                if (node.getElement().equals(newKey)) {
                    if(min.getElement().compareTo(newKey) > 0){
                        min = node;
                    }
                    return;
                }
            }
        }
    }

    private void decreaseKey(Node<E> father, Node<E> toCheck, Node<E> toFind, E newKey) {
        if (toFind.getElement().equals(newKey)) {

            return;

        } else if (toCheck == toFind) {

            toCheck.setElement(newKey);
            if(father.getElement().compareTo(newKey) > 0){
                roots.add(toCheck);
                father.getNodes().remove(toCheck);
            }

        } else {

            for(Node<E> children : toCheck.getNodes()){
                if(children.getElement().compareTo(toFind.getElement()) > 0){
                    continue;
                }
                decreaseKey(toCheck, children, toFind, newKey);
                if(toFind.getElement().equals(newKey)){
                    return;
                }
            }

        }
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

    public Node() {
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

    public void setElement(E e) {
        this.element = e;
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

    public int degree() {
        return nodes.size();
    }
}
