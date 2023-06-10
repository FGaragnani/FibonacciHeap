package com.fibonacciheap;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FibonacciHeapTest {

    @Test
    void size() {
        FibonacciHeap<Integer> fibonacciHeap = new FibonacciHeap<>(3, 4, 1, 5, 4, 3);
        assertEquals(fibonacciHeap.size(), 6);
        fibonacciHeap.add(8);
        assertEquals(fibonacciHeap.size(), 7);

        List<Node<Integer>> nodes = new ArrayList<>();
        nodes.add(new Node<>(3));
        nodes.get(0).addChildren(4);
        nodes.get(0).addChildren(6);
        nodes.get(0).getNodes().get(1).addChildren(7);
        nodes.add(new Node<>(5));
        nodes.get(1).addChildren(8);
        fibonacciHeap = new FibonacciHeap<>(nodes);
        assertEquals(6, fibonacciHeap.size());
    }

    @Test
    void isEmpty() {
        FibonacciHeap<String> fibonacciHeap = new FibonacciHeap<>();
        assertTrue(fibonacciHeap.isEmpty());
        fibonacciHeap.add("ciao");
        assertFalse(fibonacciHeap.isEmpty());
    }

    @Test
    void add() {
        FibonacciHeap<Integer> fibonacciHeap = new FibonacciHeap<>(10, 2, 4, 8, 77, 2, 3);
        fibonacciHeap.add(1);
        fibonacciHeap.add(3);
        assertEquals(1, (int) fibonacciHeap.element());

        List<Node<Integer>> nodes = new ArrayList<>();
        nodes.add(new Node<>(3));
        nodes.get(0).addChildren(4);
        nodes.get(0).addChildren(6);
        nodes.get(0).getNodes().get(1).addChildren(7);
        nodes.add(new Node<>(5));
        nodes.get(1).addChildren(8);
        fibonacciHeap = new FibonacciHeap<>(nodes);
        fibonacciHeap.add(10);
        assertEquals(fibonacciHeap.peek(), 3);
        assertEquals(fibonacciHeap.size(), 7);
    }

    @Test
    void toArray() {
        FibonacciHeap<Double> fibonacciHeap = new FibonacciHeap<>(1.2, 13., 4.678, 90.);
        assertNotEquals(0, Arrays.stream(fibonacciHeap.toArray()).filter(object -> object instanceof Double).filter(obj -> obj.equals(90.)).count());
    }

    @Test
    void clear() {
        FibonacciHeap<Integer> fibonacciHeap = new FibonacciHeap<>(13, 12, 11, 76, 20);
        fibonacciHeap.clear();
        assertTrue(fibonacciHeap.isEmpty());

        List<Node<Integer>> nodes = new ArrayList<>();
        nodes.add(new Node<>(3));
        nodes.get(0).addChildren(4);
        nodes.get(0).addChildren(6);
        nodes.get(0).getNodes().get(1).addChildren(7);
        nodes.add(new Node<>(5));
        nodes.get(1).addChildren(8);
        fibonacciHeap = new FibonacciHeap<>(nodes);
        fibonacciHeap.clear();
        assertTrue(fibonacciHeap.isEmpty());
    }

    @Test
    void element() {
        FibonacciHeap<Integer> fibonacciHeap = new FibonacciHeap<>(13, 12, 0, 98);
        assertEquals(fibonacciHeap.element(), 0);
        fibonacciHeap.clear();
        assertThrows(RuntimeException.class, fibonacciHeap::element);
    }

    @Test
    void peek() {
        FibonacciHeap<Integer> fibonacciHeap = new FibonacciHeap<>(13, 12, 0, 98);
        assertEquals(fibonacciHeap.peek(), 0);
        fibonacciHeap.clear();
        assertNull(fibonacciHeap.peek());
    }

    @Test
    void contains() {
        FibonacciHeap<Integer> fibonacciHeap = new FibonacciHeap<>(13, 12, 0, 98);
        assertFalse(fibonacciHeap.contains(11));
        assertTrue(fibonacciHeap.contains(13));

        List<Node<Integer>> nodes = new ArrayList<>();
        nodes.add(new Node<>(3));
        nodes.get(0).addChildren(4);
        nodes.get(0).addChildren(6);
        nodes.get(0).getNodes().get(1).addChildren(7);
        nodes.add(new Node<>(5));
        nodes.get(1).addChildren(8);
        fibonacciHeap = new FibonacciHeap<>(nodes);
        assertTrue(fibonacciHeap.contains(5));
        assertTrue(fibonacciHeap.contains(7));
        assertFalse(fibonacciHeap.contains(1));
    }
}