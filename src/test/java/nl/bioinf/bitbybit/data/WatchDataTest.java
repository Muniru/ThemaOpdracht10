package nl.bioinf.bitbybit.data;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class WatchDataTest {

    @org.junit.jupiter.api.Test
    void getSteps() {
        WatchData watchData = new WatchData(WatchType.FITBIT);
        watchData.AddSteps(new StepData(1704378129, 10024));
        watchData.AddSteps(new StepData(1704398129, 1009));
        watchData.AddSteps(new StepData(1704498129, 3024));
        watchData.AddSteps(new StepData(1704578129, 18024));
        int totalSteps = 10024 + 1009 + 3024 + 18024;

        assertTrue(32081 ==  watchData.GetSteps(0, 1704578130));
        assertTrue(0 == watchData.GetSteps(0,1));
        assertTrue(4033 ==  watchData.GetSteps(1704398129, 1704498129));
    }
}