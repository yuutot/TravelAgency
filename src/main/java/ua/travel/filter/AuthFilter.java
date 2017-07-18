package ua.travel.filter;

import ua.travel.entity.User;
import ua.travel.security.SecurityContext;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Controls user access to pages
 */
@WebFilter(servletNames = {"pageController", "executeController"})
public class AuthFilter implements Filter {

    private static final String ATTRIBUTE_USER = "user";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        User user = (User) request.getSession().getAttribute(ATTRIBUTE_USER);
        SecurityContext securityContext = SecurityContext.getInstance();
        if (securityContext.checkUserCredentials(request, user)) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            ((HttpServletResponse) servletResponse).sendRedirect("/login");
        }
    }

    @Override
    public void destroy() {

    }
}
