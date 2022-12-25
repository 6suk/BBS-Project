package misc;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import user.UserController;

/**
 * Servlet Filter implementation class LoginCheck
 */
@WebFilter({ "/board/*", "/user/*" })
public class LoginCheck extends HttpFilter implements Filter {

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		// 필터링 전 실행
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		HttpSession ss = req.getSession();

		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8");
		String reqestURI = req.getRequestURI();
		boolean ch = true;

		if (!reqestURI.equals("bbs/user/login") && !reqestURI.equals("/bbs/user/register")) {
			String uid = (String) ss.getAttribute("uid");
			ch = uid == null ? false : true;
		}

		if (!ch) {
			req.getRequestDispatcher(UserController.LOGIN).forward(request, response);
		} else {
			chain.doFilter(request, response);
		}

	}

}
