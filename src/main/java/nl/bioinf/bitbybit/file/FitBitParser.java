package nl.bioinf.bitbybit.file;

import nl.bioinf.bitbybit.data.StepData;
import nl.bioinf.bitbybit.data.WatchData;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

public class FitBitParser implements WatchParser{

    private record StepDataMedium(String dateTime, int value){}
    @Override
    public WatchData Parse() {

        String folderLoc = "/home/muniru/Documents/Zorin_Hanze/Thema_10/ThemaOpdracht10/data/";
        String exportLoc = "Takeout/Fitbit/Global Export Data";

        // Find Files to open

        return null;
    }

    /**
     * Extracts StepData from a JSON file containing StepDataMedium objects.
     *
     * @param file The path to the JSON file.
     * @return A list of StepData objects extracted from the JSON file.
     */
    public static List<StepData> StepExtractor(String file) {
        // List to hold StepDataMedium objects parsed from JSON
        List<StepDataMedium> media;

        // List to hold the final StepData objects
        List<StepData> stepData = new ArrayList<>();

        // Gson object for JSON parsing
        Gson gson = new Gson();

        try {
            // Read JSON file and parse it into a List of StepDataMedium objects
            FileReader reader = new FileReader(file);
            media = gson.fromJson(reader, new TypeToken<List<StepDataMedium>>() {}.getType());
        } catch (FileNotFoundException e) {
            // If the file is not found, throw a runtime exception
            throw new RuntimeException(e);
        }

        // Remove StepDataMedium objects with value less than or equal to 0
        media.removeIf(x -> x.value() <= 0);

        // Convert StepDataMedium objects to StepData objects and add them to the result list
        for (StepDataMedium med : media) {
            // Parse date and time from the StepDataMedium object
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yy HH:mm:ss");
            LocalDateTime dateTime = LocalDateTime.parse(med.dateTime, formatter);

            // Create a new StepData object and add it to the result list
            stepData.add(
                    new StepData(
                            dateTime.toEpochSecond(ZoneOffset.UTC),
                            med.value));
        }

        // Return the final list of StepData objects
        return stepData;
    }

}
