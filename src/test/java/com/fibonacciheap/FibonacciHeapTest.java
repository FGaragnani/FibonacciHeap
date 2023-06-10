package com.fibonacciheap;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FibonacciHeapTest {

    @Test
    void size() {
        FibonacciHeap<Integer> fibonacciHeap = new FibonacciHeap<>(3, 4, 1, 5, 4, 3);
        assertEquals(fibonacciHeap.size(), 6);
        fibonacciHeap.add(8);
        assertEquals(fibonacciHeap.size(), 7);
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
    }
}