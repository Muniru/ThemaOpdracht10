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

        String csvFile = "";
        try {
            csvFile = FileHandler.ScanFilenamesStartingWithFullPath(
                    Paths.get(root),
                    "com.samsung.shealth.tracker.pedometer_day_summary").get(0);
        } catch (Exception e){
            System.out.println(e);
        }

        List<StepData> myStepdata = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            String line;

            // Read and skip the header (first line)
            br.readLine();

            while ((line = br.readLine()) != null) {
                // Split the line by the comma (or any other delimiter)
                String[] fields = line.split(",");

                // Check if the field at position 2 (0-based index) is not empty
                if (!fields[10].isEmpty()) {

                    //System.out.println(fields[20]+ " | "+ fields[1]);
                    //long dateTime = ;
                    try{
                        long dateTime = Long.parseLong(fields[20]);
                        int steps = Integer.parseInt(fields[1]);
                        myStepdata.add(new StepData(dateTime, steps));
                    } catch (Exception e) {}

//
//                    for (int i : new int[]{1, 20}) {
//                        System.out.print(fields[i] + " | ");
//                    }


                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new WatchData(WatchType.SAMSUNG, myStepdata);
    }
}
