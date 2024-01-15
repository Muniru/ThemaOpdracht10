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