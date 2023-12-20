package nl.bioinf.bitbybit.file;
import  nl.bioinf.bitbybit.data.WatchType;

public class FileHandler {
    public static String getWatchType(String watchType) {
        WatchType type = WatchType.valueOf(watchType.toUpperCase());
        return "WHAT A COOL" + type + "!!!";
    }

    //type not sure
    public static Object UploadWatchData()
    {
        //https://www.theserverside.com/blog/Coffee-Talk-Java-News-Stories-and-Opinions/Java-File-Upload-Servlet-Ajax-Example
        return null;
    }
}
