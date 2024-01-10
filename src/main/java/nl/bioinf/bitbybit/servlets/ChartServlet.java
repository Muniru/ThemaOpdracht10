package nl.bioinf.bitbybit.servlets;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import javax.servlet.ServletException;
import javax.servlet.ServletContext;
import nl.bioinf.bitbybit.config.WebConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Base64;

@WebServlet(name = "ChartServlet", urlPatterns = "/chart")
public class ChartServlet extends HttpServlet {

    private TemplateEngine templateEngine;

    @Override
    public void init() throws ServletException {
        System.out.println("Initializing Thymeleaf template engine");
        final ServletContext servletContext = this.getServletContext();
        templateEngine = WebConfig.createTemplateEngine(servletContext);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        WebContext ctx = new WebContext(
                request,
                response,
                request.getServletContext(),
                request.getLocale());

        // Create Thymeleaf context
        JFreeChart chart = createChart();

        // Create a ChartPanel to generate the image
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setSize(400, 300);

        // Write the chart image directly to the response output stream
        try (OutputStream outputStream = response.getOutputStream()) {
            ChartUtils.writeChartAsPNG(outputStream, chart, 400, 300);

            // Process the Thymeleaf template with the context
            templateEngine.process("chart", ctx, response.getWriter());
        }
    }

    private JFreeChart createChart() {
        CategoryDataset dataset = createDataset();
        return ChartFactory.createBarChart(
                "Bar Chart Example",
                "Category",
                "Value",
                dataset
        );
    }

    private CategoryDataset createDataset() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        dataset.addValue(10, "Series", "Category A");
        dataset.addValue(14, "Series", "Category B");
        dataset.addValue(18, "Series", "Category C");
        dataset.addValue(24, "Series", "Category D");
        dataset.addValue(30, "Series", "Category E");
        return dataset;
    }
}
