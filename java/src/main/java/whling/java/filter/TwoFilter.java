package whling.java.filter;

public class TwoFilter implements Filter {
    @Override
    public void filter(Request request, Response response, FilterChain filterChain) {
        System.out.println("two filter before..");
        filterChain.doFilter(request, response);
        System.out.println("two filter after..");
    }
}
