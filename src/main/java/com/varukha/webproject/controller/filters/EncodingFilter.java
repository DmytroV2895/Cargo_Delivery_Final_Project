package com.varukha.webproject.controller.filters;

import com.varukha.webproject.command.ParameterAndAttribute;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.annotation.WebInitParam;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

/**
 * Class EncodingFilter used as encoding filter in order to set encoding.
 *
 * @author Dmytro Varukha
 * @version 1.0
 */
@WebFilter(filterName = "EncodingFilter", urlPatterns = { "/*" }, initParams = {
		@WebInitParam(name = "encoding", value = "UTF-8", description = "Encoding Param"),
		@WebInitParam(name = "defaultLocale", value = "uk_UA", description = "Default locale")})

public class EncodingFilter implements Filter {
	private String code;
	private String defaultLocale;

	@Override
	public void init(FilterConfig fConfig) {
		code = fConfig.getInitParameter("encoding");
		defaultLocale = fConfig.getInitParameter("defaultLocale");
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpSession session = req.getSession();
		String codeRequest = request.getCharacterEncoding();
		if (codeRequest == null || !codeRequest.equalsIgnoreCase(code)) {
			request.setCharacterEncoding(code);
		}
		String codeResponse = response.getCharacterEncoding();
		if (codeResponse == null || !codeResponse.equalsIgnoreCase(code)) {
			response.setCharacterEncoding(code);
		}
		session.setAttribute(ParameterAndAttribute.LOCALE, defaultLocale);
		chain.doFilter(request, response);
	}
}
