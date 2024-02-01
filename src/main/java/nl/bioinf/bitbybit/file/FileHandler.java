package nl.bioinf.bitbybit.file;
import  nl.bioinf.bitbybit.data.WatchType;

import java.io.*;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;

import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

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
    public static List<String> ScanFilenamesStartingWith(Path folderPath, String prefix) throws IOException {
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

    public static List<String> ScanFilenamesStartingWithFullPath(Path folderPath, String prefix) throws IOException {
        List<String> filenames = new ArrayList<>();

        // Define a FileVisitor to collect filenames
        FileVisitor<Path> fileVisitor = new SimpleFileVisitor<>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
                // Check if the filename starts with the specified prefix
                if (file.getFileName().toString().startsWith(prefix)) {
                    filenames.add(file.toAbsolutePath().toString());
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

    public static WatchType GetWatchTypeFromFile(String folderPath, String filename){

        Path filePath = Paths.get(folderPath, filename);

        try {
            // Read all lines from the file into a List<String>
            List<String> lines = Files.readAllLines(filePath);

            switch (lines.get(0).toUpperCase()){
                case "APPLE":
                    return WatchType.APPLE;
                case "FITBIT":
                    return WatchType.FITBIT;
                case "SAMSUNG":
                    return WatchType.SAMSUNG;
            }
        } catch (IOException e) {
            // Handle IOException, e.g., file not found or unable to read
            e.printStackTrace();
        }

        return WatchType.NONE;
    }

    public static List<String> ScanFilenamesEndWith(Path folderPath, String end) throws IOException {
        List<String> filenames = new ArrayList<>();

        // Define a FileVisitor to collect filenames
        FileVisitor<Path> fileVisitor = new SimpleFileVisitor<>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
                // Check if the filename starts with the specified prefix
                if (file.getFileName().toString().endsWith(end)) {
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

    public static void UnZip(String fileZipLoc, String destDirLoc) throws IOException {
        final File destDir = new File(destDirLoc);
        String fileLoc = fileZipLoc + ScanFilenamesEndWith(Paths.get(fileZipLoc), ".zip").get(0);
        System.out.println(fileLoc);
        final byte[] buffer = new byte[16384];
        final ZipInputStream zis = new ZipInputStream(new FileInputStream(fileLoc));
        ZipEntry zipEntry = zis.getNextEntry();
        while (zipEntry != null) {
            final File newFile = newFile(destDir, zipEntry);
            if (zipEntry.isDirectory()) {
                if (!newFile.isDirectory() && !newFile.mkdirs()) {
                    throw new IOException("Failed to create directory " + newFile);
                }
            } else {
                File parent = newFile.getParentFile();
                if (!parent.isDirectory() && !parent.mkdirs()) {
                    throw new IOException("Failed to create directory " + parent);
                }

                final FileOutputStream fos = new FileOutputStream(newFile);
                int len;
                while ((len = zis.read(buffer)) > 0) {
                    fos.write(buffer, 0, len);
                }
                fos.close();
            }
            zipEntry = zis.getNextEntry();
        }
        zis.closeEntry();
        zis.close();
    }
    public static File newFile(File destinationDir, ZipEntry zipEntry) throws IOException {
        File destFile = new File(destinationDir, zipEntry.getName());

        String destDirPath = destinationDir.getCanonicalPath();
        String destFilePath = destFile.getCanonicalPath();

        if (!destFilePath.startsWith(destDirPath + File.separator)) {
            throw new IOException("Entry is outside of the target dir: " + zipEntry.getName());
        }

        return destFile;
    }
}
