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

  /**
     * Scans a folder for filenames that start with the specified prefix.
     *
     * @param folderPath The path to the folder to scan.
     * @param prefix     The prefix to match at the beginning of filenames.
     * @return A list of filenames that start with the specified prefix.
     * @throws IOException If an I/O error occurs during the file tree traversal.
     */
    public static List<String> scanFilenamesStartingWith(Path folderPath, String prefix) throws IOException {
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
        Files.walkFileTree(folderPath, fileVisitor);

        return filenames;
    }
}
