package nl.bioinf.bitbybit.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.file.*;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
//
//public class TestZipReader {
//    public static void main(String[] args) {
//        String zipFilePath =
//                "/homes/ltennapel/Documents/Thema_10/Project/ThemaOpdracht10/temp_test/samsunghealth_btnapel_20231122112365.zip";
//        String tempDirectoryPath =
//                "/homes/ltennapel/Documents/Thema_10/Project/ThemaOpdracht10/temp_test/Extracted/";
////
//        try {
//            unzip(zipFilePath, tempDirectoryPath);
//            System.out.println("Zip file extracted successfully to: " + tempDirectoryPath);
//        } catch (IOException e) {
//            System.err.println("Error extracting zip file: " + e.getMessage());
//        }
//    }
////
//    private static void unzip(String zipFilePath, String destDirectory) throws IOException {
//        try (ZipInputStream zipInputStream = new ZipInputStream(new FileInputStream(zipFilePath))) {
//            ZipEntry entry;
//            while ((entry = zipInputStream.getNextEntry()) != null) {
//                Path entryPath = Paths.get(destDirectory, entry.getName());
//                if (entry.isDirectory()) {
//                    Files.createDirectories(entryPath);
//                } else {
//                    Files.copy(zipInputStream, entryPath, StandardCopyOption.REPLACE_EXISTING);
//                }
//                zipInputStream.closeEntry();
//            }
//        }
//    }
//}

public class TestZipReader {
    public static void main(final String[] args) throws IOException {
        final String fileZip = "C:\\Users\\btnap\\OneDrive - Hanzehogeschool" +
                " Groningen\\Documents\\School\\Thema_10\\ThemaOpdracht10\\testdata\\samsunghealth_btnapel_20231122112365.zip";
        final File destDir = new File("C:\\Users\\btnap\\OneDrive - Hanzehogeschool Groningen" +
                "\\Documents\\School\\Thema_10\\ThemaOpdracht10\\temp_test\\extracted");
        final byte[] buffer = new byte[16384];
        final ZipInputStream zis = new ZipInputStream(new FileInputStream(fileZip));
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