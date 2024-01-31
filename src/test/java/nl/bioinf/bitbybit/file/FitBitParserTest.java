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
        Path path = Paths.get(System.getProperty("user.dir"), "data");
        System.out.println(path.toString());
        WatchData data = l.Parse(path.toString());
        System.out.println(data.GetSteps(1702214640, 1702891680));

    }

    @Test
    void stepExtractor(){
        Path path = Paths.get(System.getProperty("user.dir"), "data", "testStep.json");
        List<StepData> l = FitBitParser.StepExtractor(path.toString());
        int sum = l.stream().mapToInt(StepData::value).sum();
        assertTrue(sum == 354);
        assertTrue(l.size() == 4);

    }
}