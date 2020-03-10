package whling.java.filter;

public interface Filter {

    void filter(Request request, Response response, FilterChain filterChain);
}
