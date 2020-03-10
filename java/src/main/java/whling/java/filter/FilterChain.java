package whling.java.filter;

import java.util.ArrayList;
import java.util.List;

public class FilterChain {


    private Servlet servlet;

    private List<Filter> filters = new ArrayList<>();

    public void doFilter(Request request, Response response) {
        if (pos < filters.size()) {
            filters.get(pos++).filter(request, response, this);
        } else {
            servlet.doService(request, response);
        }
    }


    private int pos = 0;

    public void setServlet(Servlet servlet) {
        this.servlet = servlet;
    }

    public void addFilter(Filter filter) {
        filters.add(filter);
    }
}
