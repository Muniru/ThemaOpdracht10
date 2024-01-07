package nl.bioinf.bitbybit.file;
import  nl.bioinf.bitbybit.data.WatchType;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;

import java.io.IOException;

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

    private static List<String> scanFilenamesStartingWith(String folderPath, String prefix) throws IOException {
        List<String> filenames = new ArrayList<>();

        // Define a FileVisitor to collect filenames
        FileVisitor<Path> fileVisitor = new SimpleFileVisitor<>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
                // Check if the filename starts with the specified prefix
                if (file.getFileName().toString().startsWith(prefix)) {
                    filenames.add(file.getFileName().toString());
                }
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFileFailed(Path file, IOException exc) {
                // Handle the case where file visit failed (optional)
                return FileVisitResult.CONTINUE;
            }
        };

        // Walk the file tree and apply the FileVisitor
        Files.walkFileTree(Paths.get(folderPath), fileVisitor);

        return filenames;
    }
}
