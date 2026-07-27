package org.example.jav102su26.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;

import java.io.IOException;

// Translates a POST with a "_method" form field into the method named in that
// field. This lets HTML forms emit PUT/DELETE/PATCH without browser support
// (browsers only support GET and POST in <form method="...">). It is the
// Jakarta EE equivalent of Spring's HiddenHttpMethodFilter.
@WebFilter(filterName = "HttpMethodOverrideFilter", urlPatterns = "/*")
public class HttpMethodOverrideFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest http = (HttpServletRequest) request;

        if ("POST".equalsIgnoreCase(http.getMethod()) && http.getParameter("_method") != null) {
            String override = http.getParameter("_method").toUpperCase();
            chain.doFilter(new MethodOverrideRequest(http, override), response);
        } else {
            chain.doFilter(request, response);
        }
    }

    private static class MethodOverrideRequest extends HttpServletRequestWrapper {

        private final String method;

        MethodOverrideRequest(HttpServletRequest request, String method) {
            super(request);
            this.method = method;
        }

        @Override
        public String getMethod() {
            return method;
        }
    }
}
