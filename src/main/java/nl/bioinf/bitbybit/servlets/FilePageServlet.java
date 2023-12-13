package nl.bioinf.bitbybit.servlets;
import nl.bioinf.bitbybit.config.WebConfig; //change to your situation!
import nl.bioinf.bitbybit.file.FileHandler;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.Locale;

@WebServlet(name = "FilePageServlet", urlPatterns = "/file-upload")
public class FilePageServlet extends HttpServlet {
    private TemplateEngine templateEngine;

    @Override
    public void init() throws ServletException {
        System.out.println("Initializing Thymeleaf template engine");
        final ServletContext servletContext = this.getServletContext();
        this.templateEngine = WebConfig.createTemplateEngine();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String watchType = request.getParameter("watch_category");
        Locale locale = request.getLocale();
        WebContext ctx = new WebContext(
                request,
                response,
                request.getServletContext(),
                locale);
        if (watchType != null) {
            final String watch = FileHandler.getWatchType(watchType);
            ctx.setVariable("watch_type", watchType);
            ctx.setVariable("watch_msg", watch);
        } else {
            ctx.setVariable("watch_type", "none");
            ctx.setVariable("watch_msg", "no msg");
        }
        templateEngine.process("file-upload", ctx, response.getWriter());
    }

    public void process(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        //this step is optional; standard settings also suffice
        WebConfig.configureResponse(response);
        WebContext ctx = new WebContext(
                request,
                response,
                request.getServletContext(),
                request.getLocale());
        ctx.setVariable("currentDate", new Date());
        WebConfig.createTemplateEngine(getServletContext()).
                process("file-upload", ctx, response.getWriter());
    }

}


