package nl.bioinf.bitbybit.servlets;

import nl.bioinf.bitbybit.file.FileHandler;

import javax.servlet.ServletException;
import javax.servlet.ServletContext;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet(name = "UnzipServlet", urlPatterns = "/unzip")
public class UnzipServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Set response content type
        response.setContentType("text/html;charset=UTF-8");

        // Get parameters from the servlet context
        ServletContext servletContext = this.getServletContext();
        String uploadDir = servletContext.getInitParameter("upload_dir");
        String extractedDir = servletContext.getInitParameter("extracted_dir");

        // Unzip the files from the upload directory to the extracted directory
        FileHandler.unzip(uploadDir, extractedDir);
    }
}
