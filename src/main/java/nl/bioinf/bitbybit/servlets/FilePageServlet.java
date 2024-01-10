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
import java.io.IOException;

@WebServlet(name = "FilePageServlet", urlPatterns = "/file-upload")
public class FilePageServlet extends HttpServlet {

    private TemplateEngine templateEngine;
    private String uploadDir;

    @Override
    public void init() throws ServletException {
        final ServletContext servletContext = this.getServletContext();
        this.templateEngine = WebConfig.createTemplateEngine(servletContext);
        this.uploadDir = servletContext.getInitParameter("upload_dir");
        System.out.println(uploadDir);

        //or, use relative to this app:
        // gets absolute path of the web application
        //String applicationPath = getServletContext().getRealPath("");
        //String uploadFilePath = applicationPath + File.separator + uploadDir;
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //simply go back to upload form
        WebContext ctx = new WebContext(request, response, request.getServletContext(), request.getLocale());
        templateEngine.process("file-upload", ctx, response.getWriter());
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        File fileSaveDir = new File(this.uploadDir);
        if (! fileSaveDir.exists()) {
            throw new IllegalStateException("Upload dir does not exist: " + this.uploadDir);
        }

        //Do this only if you are sure there won't be any file name conflicts!
        //An existing one will simply be overwritten
        String fileName;
        for (Part part : request.getParts()) {
            fileName = part.getSubmittedFileName();
            part.write(this.uploadDir + File.separator + fileName);
        }

        //go back to the upload form
        WebContext ctx = new WebContext(request, response, request.getServletContext(), request.getLocale());
        ctx.setVariable("message", "upload successful, wanna do another on?");
        ctx.setVariable("Upload_directory", uploadDir );
        templateEngine.process("file-upload", ctx, response.getWriter());
    }
}