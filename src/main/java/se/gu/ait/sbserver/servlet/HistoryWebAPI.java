package se.gu.ait.sbserver.servlet;

import javax.servlet.*;
import javax.servlet.http.*;

import java.io.PrintWriter;
import java.io.OutputStreamWriter;

import static java.nio.charset.StandardCharsets.UTF_8;

import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Predicate;
import java.util.Locale;

import se.gu.ait.sbserver.domain.Product;
import se.gu.ait.sbserver.storage.ProductLine;
import se.gu.ait.sbserver.storage.ProductLineFactory;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class HistoryWebAPI extends HttpServlet {

    private static final Logger logger = LogManager.getLogger(HistoryWebAPI.class.getName());

    @Override
    public void init() throws ServletException {
        Locale.setDefault(Locale.US);
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("Request:\n" + request);
        System.out.println("Response:\n" + response);
        logger.info("Starting up");
        request.setCharacterEncoding(UTF_8.name());
        //response.setContentType("text/html;charset="+UTF_8.name());
        response.setContentType("application/json;charset=" + UTF_8.name());
        PrintWriter out =
                new PrintWriter(new OutputStreamWriter(response.getOutputStream(),
                        UTF_8), true);
        ParameterParser paramParser;
        StringBuilder sb;
        try {
            System.out.println("Creating ParameterParser");
            paramParser = new ParameterParser(request.getQueryString().split("&"));
            System.out.println("Filtering ParameterParser");
            Predicate<Product> filter = paramParser.filter();
            System.out.println("Setting property");
            System.setProperty("ProductLine", getServletContext().getInitParameter("ProductLine"));
            //System.out.println("ProductLine property: " + System.getProperty("ProductLine"));
            System.out.println("Creting productline");
            ProductLine productLine = ProductLineFactory.getProductLine();
            System.out.println("Filtering products");
            List<Product> products = new ArrayList<Product>();
            System.out.println(paramParser.addedStartDate() + "   " + paramParser.addedEndDate());
            if (!paramParser.addedStartDate().equals("") || !paramParser.addedEndDate().equals("date()")) {
                System.out.println("Getting by added history");
                products = productLine.getProductsByAddedHistory(paramParser.addedStartDate(), paramParser.addedEndDate());
            } else if (paramParser.nr() != -1 || !paramParser.priceStartDate().equals("") || !paramParser.priceEndDate().equals("date()")) {
                products = productLine.getProductByPriceHistory(paramParser.nr(), paramParser.priceStartDate(), paramParser.priceEndDate());
            }
            System.out.println("Formaterar till JSON");
            Formatter formatter = FormatterFactory.getFormatter();
            String json = formatter.format(products);
            sb = new StringBuilder(json);
            if (paramParser.invalidArgs().size() != 0 &&
                    products.size() == 0) {
                sb = new StringBuilder("{ \"error\": \"")
                        .append("Bad parameters: ")
                        .append(paramParser.invalidArgs().toString())
                        .append("\" }");
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            } else if (products.size() == 0) {
                sb = new StringBuilder("{ \"error\": \"")
                        .append("No products matched the criteria ")
                        .append(request.getQueryString())
                        .append("\" }");
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            }
            out.println(sb.toString());
        } catch (NullPointerException e) {
            logger.info("No parameter passed");
        }
        out.close();
    }
    /* GOT broken pipe - TODO: investigate why - no time now */
}
