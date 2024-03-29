package nl.bioinf.bitbybit.servlets;

import nl.bioinf.bitbybit.config.WebConfig;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.Part;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

@WebServlet(name = "FilePageServlet", urlPatterns = "/file-upload")
public class FilePageServlet extends HttpServlet {

    private TemplateEngine templateEngine;
    private String uploadDir;
    private String extractedDir;

    @Override
    public void init() throws ServletException {
        // Initialize Thymeleaf template engine and get upload and extracted directories
        final ServletContext servletContext = this.getServletContext();
        this.templateEngine = WebConfig.createTemplateEngine(servletContext);
        this.uploadDir = servletContext.getInitParameter("upload_dir");
        this.extractedDir = servletContext.getInitParameter("extracted_dir");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Display the file upload form
        WebContext ctx = new WebContext(request, response, request.getServletContext(), request.getLocale());
        templateEngine.process("file-upload", ctx, response.getWriter());
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Ensure the upload directory exists
        File fileSaveDir = new File(this.uploadDir);
        if (!fileSaveDir.exists()) {
            throw new IllegalStateException("Upload dir does not exist: " + this.uploadDir);
        }

        // Process each part of the multipart request
        String fileName = null;
        String zipFilePath = uploadDir;

        for (Part part : request.getParts()) {
            fileName = part.getSubmittedFileName();
            if (fileName != null)
                zipFilePath += fileName;
            part.write(this.uploadDir + File.separator + fileName);
        }

        try {
            // Unzip the uploaded file to the specified destination directory
            unzip(zipFilePath, extractedDir);
        } catch (IOException e) {
            System.err.println("Error extracting zip file: " + e.getMessage());
        }

        // Display a success message and go back to the upload form
        WebContext ctx = new WebContext(request, response, request.getServletContext(), request.getLocale());
        ctx.setVariable("message", "Upload successful. Want to upload another file?");
        ctx.setVariable("Upload_directory", uploadDir);
        ctx.setVariable("watch_category", request.getParameter("watch_category"));
        templateEngine.process("stepsGraph", ctx, response.getWriter());
    }

    private void unzip(String zipFilePath, String destDirectory) throws IOException {
        // Unzip the provided zip file to the specified destination directory
        byte[] buffer = new byte[16384];

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
