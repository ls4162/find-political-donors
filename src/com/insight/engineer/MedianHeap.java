package com.insight.engineer;

import java.util.PriorityQueue;
import java.util.Collections;
import java.lang.Math;

// lower level implementation of keeping track of stream median
class MedianHeap {
    private PriorityQueue<Integer>  minHeap;
    private PriorityQueue<Integer>  maxHeap;
    private Integer median;

    public MedianHeap() {
        this.minHeap = new PriorityQueue<>();
        this.maxHeap = new PriorityQueue<>(Collections.reverseOrder());
        this.median = Integer.valueOf(0);
    }

    public void add(Integer amt) {
        if (amt <= median) {
            maxHeap.offer(amt);
        } else {
            minHeap.offer(amt);
        }

        if (Math.abs(minHeap.size() - maxHeap.size()) > 1) {
            balance();
        }
    }

    public Integer getMedian() {
        if (minHeap.size() > maxHeap.size()){
            median = minHeap.peek();
        } else if (minHeap.size() < maxHeap.size()) {
            median = maxHeap.peek();
        } else if (minHeap.size() == maxHeap.size()) {
            median = (int)Math.round((minHeap.peek() + maxHeap.peek()) / 2.0);
        }
        return median;
    }

    private void balance() {
        if (minHeap.size() > maxHeap.size()) {
            maxHeap.offer(minHeap.poll());
        } else {
            minHeap.offer(maxHeap.poll());
        }
    }
}