package nl.bioinf.bitbybit.servlets;

import com.google.gson.Gson;
import nl.bioinf.bitbybit.data.WatchData;
import nl.bioinf.bitbybit.file.FitBitParser;
import nl.bioinf.bitbybit.file.WatchParser;

import javax.servlet.ServletException;
import javax.servlet.ServletContext;
import nl.bioinf.bitbybit.config.WebConfig;
import org.thymeleaf.TemplateEngine;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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

        WatchParser l = new FitBitParser();

        WatchData data = l.Parse("/home/master/Documents/Hanze/Thema_10/ThemaOpdracht10/data/"); // Location of the actual root /data

        String json = new Gson().toJson(data);

        // Return results as JSON
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(json);
    }
}
