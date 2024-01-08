import java.nio.file.*;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class ZipHandler {
    public static void main(String[] args) {
        String zipFilePath = "path/to/your/file.zip";
        String tempDirectoryPath = "path/to/temp/directory";

        try {
            unzip(zipFilePath, tempDirectoryPath);
            System.out.println("Zip file extracted successfully to: " + tempDirectoryPath);
        } catch (IOException e) {
            System.err.println("Error extracting zip file: " + e.getMessage());
        }
    }

    private static void unzip(String zipFilePath, String destDirectory) throws IOException {
        try (ZipInputStream zipInputStream = new ZipInputStream(new FileInputStream(zipFilePath))) {
            ZipEntry entry;
            while ((entry = zipInputStream.getNextEntry()) != null) {
                Path entryPath = Paths.get(destDirectory, entry.getName());
                if (entry.isDirectory()) {
                    Files.createDirectories(entryPath);
                } else {
                    Files.copy(zipInputStream, entryPath, StandardCopyOption.REPLACE_EXISTING);
                }
                zipInputStream.closeEntry();
            }
        }
    }
}