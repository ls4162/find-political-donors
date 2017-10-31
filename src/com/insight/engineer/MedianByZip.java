package com.insight.engineer;

import java.util.HashMap;
import java.util.Map;
import java.io.IOException;
import java.io.FileOutputStream;
import java.io.Writer;
import java.io.OutputStreamWriter;
import java.io.BufferedWriter;
import java.io.FileWriter;

class MedianByZip {
    // wrap recipient and zip as the key type in HashMap
    private class RZip {
        private final String recipient;
        private final String zip;

        public RZip(String recipient, String zip) {
            this.recipient = recipient;
            this.zip = zip;
        }

        @Override public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            RZip that = (RZip) o;
            if (recipient != null? !recipient.equals(that.recipient) : that.recipient != null) {
                return false;
            }
            if (zip != null? !zip.equals(that.zip) : that.zip != null) {
                return false;
            }
            return true;
        }

        @Override public int hashCode() {
            int prime = 31;
            int result = recipient != null? recipient.hashCode() : 0;
            result = prime * result + zip != null? zip.hashCode() : 0;
            return result;
        }
    }

    private HashMap<RZip, MedianVals> map;

    public MedianByZip() {
        this.map = new HashMap<RZip, MedianVals>();
    }

    public void handleByZip(String CMTE_ID, String ZIP_CODE, Integer TRANSACTION_AMT) {
        RZip rz = new RZip(CMTE_ID, ZIP_CODE);
        if (map.containsKey(rz)) {
            MedianVals vals = map.get(rz);
            vals.add(TRANSACTION_AMT);
            vals.incrementTransactions();
            vals.incrementAmount(TRANSACTION_AMT);
        } else {
            MedianVals newVals = new MedianVals(TRANSACTION_AMT, 1, TRANSACTION_AMT);
            map.put(rz, newVals);
        }

        print(rz);
    }

    private void print(RZip rz) {
        try (FileWriter fw = new FileWriter("../output/medianvals_by_zip.txt", true);   // allow append
             Writer writer = new BufferedWriter(fw))
        {
            writer.write(rz.recipient + "|" +
                         rz.zip + "|" +
                         map.get(rz).getMedian() + "|" +
                         map.get(rz).getTransactions() + "|" +
                         map.get(rz).getAmount() + "\n");
        } catch (IOException e) {
            System.err.println("Unable to write to file.");
            System.err.println("Terminating...");
            System.exit(3);
        }
    }
}
