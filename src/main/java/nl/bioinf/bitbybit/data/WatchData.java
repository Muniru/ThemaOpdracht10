package nl.bioinf.bitbybit.data;

import java.util.ArrayList;
import java.util.List;

public class WatchData {

    public WatchType watchType;
    public List<StepData> stepData;
    public WatchData(WatchType watchType){
        this.watchType = watchType;
        this.stepData = new ArrayList<>();
    }

    public WatchData(WatchType watchType, List<StepData> stepData){
        this.watchType = watchType;
        this.stepData = stepData;
    }

    public void AddSteps(StepData newStepData){
        this.stepData.add(newStepData);
    }

    public int GetSteps(int unixFrom, int unixTo) {
        // Check if the list of StepData is empty, return 0 if true
        if (this.stepData.isEmpty()) {
            return 0;
        }

        // Use a lambda expression and stream to filter StepData records based on the time range
        List<StepData> conditionList = this.stepData
                .stream()
                .filter(x -> x.dateTime() >= unixFrom && x.dateTime() <= unixTo)
                .toList();

        // Check if the filtered list is empty, return 0 if true, otherwise calculate the sum of value
        return conditionList.isEmpty() ? 0 : conditionList.stream().mapToInt(StepData::value).sum();
    }

}
