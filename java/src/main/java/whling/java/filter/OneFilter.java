package whling.java.filter;

public class OneFilter implements Filter {
    @Override
    public void filter(Request request, Response response, FilterChain filterChain) {
        System.out.println("one filter before ...");
        filterChain.doFilter(request, response);
        System.out.println("one filter after...");
    }
}
