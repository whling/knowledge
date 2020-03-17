package whling.java.filter;

/**
 * java web filter的原理：
 *  每次请求过来，根据请求url知道请求servlet，把该servlet加入到filterchain中，再把符合条件的filter加入到filterchain里面
 *  紧接着调用该filterchain的doFilter方法，filter调用完，才会去做具体的servlet的service方法
 *
 *  参考：https://www.jianshu.com/p/be47c9d89175
 */
public class Main {


    public static void main(String[] args) {
        FilterChain filterChain = new FilterChain();
        filterChain.setServlet(new Servlet());
        filterChain.addFilter(new OneFilter());
        filterChain.addFilter(new TwoFilter());

        filterChain.doFilter(new Request(),new Response());
    }
}
