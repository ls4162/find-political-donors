package com.insight.engineer;

// wrap median, number of transactions, total amount and a MedianHeap object as the value type in map.
// medianHeap is used to keep track of the stream median.
class MedianVals {
    private Integer median;
    private int transactions;
    private long amount;
    private MedianHeap medianHeap;

    public MedianVals(Integer median, int transactions, Integer amount) {
        this.median = median;
        this.transactions = transactions;
        this.amount = amount;
        medianHeap = new MedianHeap();
        medianHeap.add(median);
    }

    public void add(Integer amt) {
        medianHeap.add(amt);
        median = medianHeap.getMedian();
    }

    public void incrementTransactions() {
        transactions++;
    }

    public void incrementAmount(Integer amt) {
        amount += amt;
    }

    public Integer getMedian() {
        return median;
    }

    public int getTransactions() {
        return transactions;
    }

    public long getAmount() {
        return amount;
    }
}