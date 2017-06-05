package ua.travel.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Locale;

/**
 * Created by yuuto on 6/2/17.
 */
@WebFilter(servletNames = {"pageController", "executeController"})
public class LocaleFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest)servletRequest;
        request.setCharacterEncoding("UTF-8");
        String localeParam = request.getParameter("lan");
        if(localeParam != null){
            if(localeParam.equals("UA")){
                request.getSession().setAttribute("locale", new Locale("ua", "UA"));
            }else {
                request.getSession().setAttribute("locale", Locale.ENGLISH);
            }
        }
        Locale userLocale = (Locale) request.getSession().getAttribute("locale");
        if(userLocale == null){
            request.getSession().setAttribute("locale", Locale.ENGLISH);
        }
        filterChain.doFilter(request,servletResponse);
    }

    @Override
    public void destroy() {

    }
}
