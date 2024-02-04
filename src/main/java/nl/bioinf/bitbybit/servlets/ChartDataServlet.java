package nl.bioinf.bitbybit.servlets;

import com.google.gson.Gson;
import nl.bioinf.bitbybit.data.WatchData;
import nl.bioinf.bitbybit.data.WatchType;
import nl.bioinf.bitbybit.file.FileHandler;
import nl.bioinf.bitbybit.file.FitBitParser;
import nl.bioinf.bitbybit.file.SamsungParser;
import nl.bioinf.bitbybit.file.WatchParser;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet(name = "ChartDataServlet", urlPatterns = "/chartdata")
public class ChartDataServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Set response content type
        response.setContentType("text/html;charset=UTF-8");

        // Get parameters from the servlet context
        String root = this.getServletContext().getInitParameter("extracted_dir");
        String uploadDir = this.getServletContext().getInitParameter("upload_dir");

        // Determine the watch type based on the file content
        WatchType myWatchType = FileHandler.getWatchTypeFromFile(uploadDir, "null");
        WatchParser watchParser = null;

        // Initialize the appropriate watch parser based on the watch type
        switch (myWatchType) {
            case NONE, APPLE -> {
                // Do nothing for unsupported watch types
            }
            case FITBIT -> {
                watchParser = new FitBitParser();
            }
            case SAMSUNG -> {
                watchParser = new SamsungParser();
            }
        }

        WatchData data = null;
        try {
            // Parse the watch data using the selected parser
            if (watchParser != null) {
                data = watchParser.Parse(root);
            }
        } catch (Exception e) {
            // Handle parsing exceptions
            System.err.println(e);
        }

        // Convert watch data to JSON format
        String json = new Gson().toJson(data);

        // Set response content type for JSONs
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        // Write JSON response to the client
        response.getWriter().write(json);

        // Removes the files from the upload directory and the extracted directory
        FileHandler.removeFilesAndDirectories(uploadDir);
        FileHandler.removeFilesAndDirectories(root);
    }
}
