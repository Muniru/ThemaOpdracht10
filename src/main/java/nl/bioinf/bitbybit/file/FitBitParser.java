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
    public static List<StepData> StepExtractor(String file){
        List<StepDataMedium> media;
        List<StepData> stepData = new ArrayList<>();
        Gson gson = new Gson();


        try{
            FileReader reader = new FileReader(file);
            media = gson.fromJson(reader, new TypeToken<List<StepDataMedium>>() {}.getType());
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        // Remove the 0 values
        media.removeIf(x -> x.value() <= 0);

        for (StepDataMedium med : media){
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yy HH:mm:ss");
            LocalDateTime dateTime = LocalDateTime.parse(med.dateTime, formatter);

            stepData.add(
                    new StepData(
                            dateTime.toEpochSecond(ZoneOffset.UTC),
                            med.value));
        }

        return stepData;
    }

}
