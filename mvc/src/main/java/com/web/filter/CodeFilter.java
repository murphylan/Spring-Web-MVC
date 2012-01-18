package com.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang.StringUtils;

import com.utils.encode.EncodeUtils;

public class CodeFilter extends HttpServlet implements Filter {
	/**
	 * 判断用户输入的验证码是否正确
	 */
	private static final long serialVersionUID = -5838154525730151323L;

	public void init(FilterConfig config) throws ServletException {
	}

	public void destroy() {
	}

	public void doFilter(ServletRequest servletRequest,
			ServletResponse servletResponse, FilterChain filterChain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		HttpServletResponse response = (HttpServletResponse) servletResponse;
		String code = new String(request.getParameter("j_code").getBytes(
				"8859_1"), "UTF-8");
		code = EncodeUtils.cookieEscape(code);
		Cookie[] cookie = request.getCookies();
		String codes = "";
		for (int i = 0; cookie != null && i < cookie.length; i++) {
			if ("codes".equals(cookie[i].getName())) {
				codes = cookie[i].getValue();
			}
		}
		String url = request.getContextPath() + "/login.jsp?error=5";
		if (StringUtils.isNotBlank(codes)) {
			if (code.equalsIgnoreCase(codes)) {
				filterChain.doFilter(request, response);
			} else {
				response.sendRedirect(url);
			}
		} else {
			response.sendRedirect(url);
		}
	}
}
