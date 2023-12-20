package nl.bioinf.bitbybit.data;

import java.util.ArrayList;
import java.util.List;

public class WatchData {

    private WatchType watchType;
    private List<StepData> stepData;
    public WatchData(WatchType watchType){
        this.watchType = watchType;
        this.stepData = new ArrayList<>();
    }

    public int GetSteps(int unixFrom, int unixTo){
        // Impliment Lambda Expression
        if (this.stepData.isEmpty()) return 0;

        List<StepData> conditionList = this.stepData.
                stream().
                filter(x-> x.unixTime() >= unixFrom && x.unixTime() <= unixTo).
                toList();

        if(conditionList.isEmpty()) return 0;


        // Sum step values and return!
        return 0;
    }

}
