package nl.bioinf.bitbybit.file;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class CSVReader {

    public static void main(String[] args) {
        String csvFile = "C:\\Users\\btnap\\OneDrive - Hanzehogeschool Groningen\\Documents\\School\\Thema_10\\ThemaOpdracht10\\testdata\\com.samsung.shealth.tracker.pedometer_day_summary.20231122112365.csv"; // Replace with the path to your CSV file

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            String line;

            // Read and skip the header (first line)
            br.readLine();

            while ((line = br.readLine()) != null) {
                // Split the line by the comma (or any other delimiter)
                String[] fields = line.split(",");

                // Check if the field at position 2 (0-based index) is not empty
                if (!fields[10].isEmpty()) {
                    // Include fields at positions 0, 2, and 4 (0-based index)
                    for (int i : new int[]{1, 20}) {
                        System.out.print(fields[i] + " | ");
                    }
                    System.out.println(); // Move to the next line after printing a record
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
