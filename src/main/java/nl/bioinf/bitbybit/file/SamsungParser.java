package nl.bioinf.bitbybit.file;

import nl.bioinf.bitbybit.data.StepData;
import nl.bioinf.bitbybit.data.WatchData;
import nl.bioinf.bitbybit.data.WatchType;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class SamsungParser implements WatchParser {

    @Override
    public WatchData Parse(String root) {

        // Initialize the CSV file path
        String csvFile = "";

        try {
            // Find the CSV file starting with a specific prefix
            csvFile = FileHandler.scanFilenamesStartingWithFullPath(
                    Paths.get(root),
                    "com.samsung.shealth.tracker.pedometer_day_summary").get(0);
        } catch (Exception e) {
            System.out.println(e);
        }

        // Initialize a list to store step data
        List<StepData> myStepdata = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            String line;

            // Read and skip the header (first line) of the CSV file
            br.readLine();

            // Read each line in the CSV file
            while ((line = br.readLine()) != null) {
                // Split the line by the comma (or any other delimiter)
                String[] fields = line.split(",");

                // Check if the field at position 2 (0-based index) is not empty
                if (!fields[10].isEmpty()) {

                    // Extract date-time and steps information from the CSV file
                    try {
                        long dateTime = Long.parseLong(fields[20]);
                        int steps = Integer.parseInt(fields[1]);
                        myStepdata.add(new StepData(dateTime, steps));
                    } catch (Exception e) {
                        // Handle any parsing exceptions if necessary
                        System.err.println(e);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Return the parsed data as a WatchData object
        return new WatchData(WatchType.SAMSUNG, myStepdata);
    }
}
