package ua.travel.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Locale;

/**
 * Set locale for user.
 * Get locale from session. If params is empty set default locale
 */
@WebFilter(servletNames = {"pageController", "executeController"})
public class LocaleFilter implements Filter {

    private static final String UTF = "UTF-8";
    private static final String PARAM_LAN = "lan";
    private static final String ATTRIBUTE_LOCALE = "locale";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest)servletRequest;
        request.setCharacterEncoding(UTF);
        String localeParam = request.getParameter(PARAM_LAN);
        if(localeParam != null){
            if(localeParam.equals("UA")){
                request.getSession().setAttribute(ATTRIBUTE_LOCALE, new Locale("ua", "UA"));
            }else {
                request.getSession().setAttribute(ATTRIBUTE_LOCALE, Locale.ENGLISH);
            }
        }
        Locale userLocale = (Locale) request.getSession().getAttribute(ATTRIBUTE_LOCALE);
        if(userLocale == null){
            request.getSession().setAttribute(ATTRIBUTE_LOCALE, Locale.ENGLISH);
        }
        filterChain.doFilter(request,servletResponse);
    }

    @Override
    public void destroy() {

    }
}
