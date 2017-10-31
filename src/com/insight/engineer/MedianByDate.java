package com.insight.engineer;

import java.util.TreeMap;
import java.util.Comparator;
import java.util.Map;
import java.io.FileOutputStream;
import java.io.Writer;
import java.io.OutputStreamWriter;
import java.io.BufferedWriter;
import java.io.IOException;

class MedianByDate {
    // wrap recipient and date as the key type in TreeMap
    private class RDate {
        private final String recipient;
        private final String date;

        public RDate(String recipient, String date) {
            this.recipient = recipient;
            this.date = date;
        }

        @Override public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            RDate that = (RDate) o;
            if (recipient != null? !recipient.equals(that.recipient) : that.recipient != null) {
                return false;
            }
            if (date != null? !date.equals(that.date) : that.date != null) {
                return false;
            }
            return true;
        }

        @Override public int hashCode() {
            int prime = 31;
            int result = recipient != null? recipient.hashCode() : 0;
            result = prime * result + date != null? date.hashCode() : 0;
            return result;
        }
    }

    private TreeMap<RDate, MedianVals> map;

    public MedianByDate() {
        this.map = new TreeMap<RDate, MedianVals>(new Comparator<RDate>() {
            // sort alphabetical by recipient and then chronologically by date
            @Override public int compare(RDate rd1, RDate rd2) {
                int diff1 = rd1.recipient.compareTo(rd2.recipient);
                if (diff1 != 0) {
                    return diff1;
                }
                int diff2 = rd1.date.substring(4, 8).compareTo(rd2.date.substring(4, 8));
                if (diff2 != 0) {
                    return diff2;
                }
                int diff3 = rd1.date.substring(0, 2).compareTo(rd2.date.substring(0, 2));
                if (diff3 != 0) {
                    return diff3;
                }
                int diff4 = rd1.date.substring(2, 4).compareTo(rd2.date.substring(2, 4));
                return diff4;
            }
        });
    }

    public void handleByDate(String CMTE_ID, String TRANSACTION_DT, Integer TRANSACTION_AMT) {
        RDate rd = new RDate(CMTE_ID, TRANSACTION_DT);
        if (map.containsKey(rd)) {
            MedianVals vals = map.get(rd);
            vals.add(TRANSACTION_AMT);
            vals.incrementTransactions();
            vals.incrementAmount(TRANSACTION_AMT);
        } else {
            MedianVals newVals = new MedianVals(TRANSACTION_AMT, 1, TRANSACTION_AMT);
            map.put(rd, newVals);
        }
    }

    public void print() {
        try (Writer writer = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream("../output/medianvals_by_date.txt"), "utf-8"))) {
            for (Map.Entry<RDate, MedianVals> entry : map.entrySet()) {
                writer.write(entry.getKey().recipient + "|" +
                        entry.getKey().date + "|" +
                        entry.getValue().getMedian() + "|" +
                        entry.getValue().getTransactions() + "|" +
                        entry.getValue().getAmount() + "\n");
            }
        } catch (IOException e) {
            System.err.println("Unable to write to file.");
            System.err.println("Terminating...");
            System.exit(3);
        }
    }
}