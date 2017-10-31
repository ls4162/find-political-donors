package com.insight.engineer;

import java.util.*;
import java.util.regex.Pattern;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;

class Driver {
    public static void main(String[] args) {

        MedianByZip medianByZip = new MedianByZip();
        MedianByDate medianByDate = new MedianByDate();

        File dir = new File("../input/");
        for (File file : dir.listFiles(new FilenameFilter() {
            @ Override public boolean accept(File dir, String fileName) {
                return fileName.endsWith(".txt");
            }
        })) {
            try {
                Scanner sc = new Scanner(file);
                while (sc.hasNextLine()) {
                    String line = sc.nextLine();
                    Tokenize st = new Tokenize(line,"|");
                    // 21 fields for each line
                    String[] temp = new String[21];
                    int index = 0;
                    while (st.hasMoreTokens()) {
                        temp[index++] = st.nextToken();
                    }

                    String CMTE_ID = temp[0];
                    String ZIP_CODE = temp[10];
                    String TRANSACTION_DT = temp[13];
                    String TRANSACTION_AMT = temp[14];
                    String OTHER_ID = temp[15];

                    // skip invalid line
                    if (!validRecord(CMTE_ID, TRANSACTION_AMT, OTHER_ID)) {
                        continue;
                    }
                    // pass to MedianByZip if zip_code is valid
                    if (validZip(ZIP_CODE)) {
                        medianByZip.handleByZip(CMTE_ID, ZIP_CODE.substring(0, 5), Integer.valueOf(TRANSACTION_AMT));
                    }
                    // pass to MeidanByDate if date is valid
                    if (validDate(TRANSACTION_DT)) {
                        medianByDate.handleByDate(CMTE_ID, TRANSACTION_DT, Integer.valueOf(TRANSACTION_AMT));
                    }
                }

                sc.close();

            } catch (FileNotFoundException e) {
                System.err.println("Unable to find input file");
                System.err.println("Terminating...");
                System.exit(3);
            }
        }

        medianByDate.print();
    }

    private static boolean validRecord(String CMTE_ID, String TRANSACTION_AMT, String OTHER_ID) {
        return CMTE_ID != null && TRANSACTION_AMT != null  && OTHER_ID.equals("");
    }

    private static boolean validZip(String ZIP_CODE) {
        return ZIP_CODE != null && ZIP_CODE.length() >= 5;
    }

    private static boolean validDate(String TRANSACTION_DT) {
        return TRANSACTION_DT != null && TRANSACTION_DT.length() == 8;
    }
}