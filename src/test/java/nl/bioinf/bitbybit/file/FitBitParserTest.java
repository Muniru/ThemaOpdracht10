package nl.bioinf.bitbybit.file;

import nl.bioinf.bitbybit.data.StepData;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FitBitParserTest {


    @Test
    void parse() {
    }

    @Test
    void stepExtractor(){
        List<StepData> l = FitBitParser.StepExtractor("/home/muniru/Documents/Zorin_Hanze/Thema_10/ThemaOpdracht10/data/testStep.json");
        int sum = l.stream().mapToInt(StepData::value).sum();
        assertTrue(sum == 354);
        assertTrue(l.size() == 4);

    }
}