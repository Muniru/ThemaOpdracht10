package nl.bioinf.bitbybit.file;

import nl.bioinf.bitbybit.data.StepData;
import nl.bioinf.bitbybit.data.WatchData;
import org.junit.jupiter.api.Test;

import javax.swing.text.html.parser.Parser;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FitBitParserTest {


    @Test
    void parse() {
        WatchParser l = new FitBitParser();
        Path path = Paths.get("/home/muniru/Documents/Zorin_Hanze/Thema_10/ThemaOpdracht10/data/");
        WatchData data = l.Parse(path.toString());
        System.out.println(data.GetSteps(1702214640, 1702891680));

    }

    @Test
    void stepExtractor(){
        List<StepData> l = FitBitParser.StepExtractor("/home/muniru/Documents/Zorin_Hanze/Thema_10/ThemaOpdracht10/data/testStep.json");
        int sum = l.stream().mapToInt(StepData::value).sum();
        assertTrue(sum == 354);
        assertTrue(l.size() == 4);

    }
}