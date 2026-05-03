package filters;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;

/**
 * Protects static resources — actual auth/role logic lives in FrontController.
 * This filter only blocks direct JSP access (views are in WEB-INF so already protected).
 */
public class AuthFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest  req  = (HttpServletRequest)  request;
        HttpServletResponse resp = (HttpServletResponse) response;
        String uri = req.getRequestURI();

        // Allow: /app (FrontController), error pages, static assets, accessDenied
        if (uri.contains("/app") || uri.contains("/accessDenied") ||
            uri.contains("/error") || uri.contains("/css/") || uri.contains("/js/")) {
            chain.doFilter(request, response);
            return;
        }

        // Block any direct URL access — redirect through FrontController
        HttpSession session = req.getSession(false);
        if (session != null && session.getAttribute("user") != null) {
            resp.sendRedirect(req.getContextPath() + "/app?action=list");
        } else {
            resp.sendRedirect(req.getContextPath() + "/app?action=login");
        }
    }

    @Override
    public void destroy() {}
}
