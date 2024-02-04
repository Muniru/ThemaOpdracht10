package nl.bioinf.bitbybit.servlets;

import com.google.gson.Gson;
import nl.bioinf.bitbybit.data.WatchData;
import nl.bioinf.bitbybit.data.WatchType;
import nl.bioinf.bitbybit.file.FileHandler;
import nl.bioinf.bitbybit.file.FitBitParser;
import nl.bioinf.bitbybit.file.SamsungParser;
import nl.bioinf.bitbybit.file.WatchParser;

import javax.servlet.ServletException;
import javax.servlet.ServletContext;
import nl.bioinf.bitbybit.config.WebConfig;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;

@WebServlet(name = "ChartDataServlet", urlPatterns = "/chartdata")
public class ChartDataServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        String root = this.getServletContext().getInitParameter("extracted_dir");
        String uploadDir = this.getServletContext().getInitParameter("upload_dir");

        WatchType myWatchType = FileHandler.GetWatchTypeFromFile(uploadDir, "null");
        WatchParser l = null;
        switch (myWatchType) {
            case NONE, APPLE -> {
                l = null;
            }
            case FITBIT -> {
                l = new FitBitParser();
            }
            case SAMSUNG -> {
                l = new SamsungParser();
            }

        }

        WatchData data = null;
        try {
            data = l.Parse(root);
        }catch (Exception e ) {
            System.out.println(e);
        }

        String json = new Gson().toJson(data);

        // Return results as JSON
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(json);
    }

}
