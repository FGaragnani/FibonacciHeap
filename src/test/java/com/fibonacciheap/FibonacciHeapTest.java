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
        FibonacciHeap<Integer> fibonacciHeap = new FibonacciHeap<>();
        assertTrue(fibonacciHeap.isEmpty());
        fibonacciHeap.add(3);
        assertFalse(fibonacciHeap.isEmpty());
    }

    @Test
    void add() {

    }
}