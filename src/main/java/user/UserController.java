package user;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.mindrot.jbcrypt.BCrypt;


@WebServlet({ "/user/list", "/user/login", "/user/register", "/user/logout", "/user/delete", "/user/deleteConfirm",
		"/user/update" })
public class UserController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private RequestDispatcher rd;
	private UserDAO dao = new UserDAO();
	private static final String LIST = "/user/list", LIST_VIEW = "/user/list.jsp", LOGIN = "/user/login",
			LOGIN_VIEW = "/user/login.jsp", LOGOUT = "/user/logout", REG = "/user/register",
			REG_VIEW = "/user/register.jsp", UPDATE = "/user/update", UPDATE_VIEW = "/user/update.jsp",
			DEL = "/user/delete", MSG = "/user/msg.jsp";
	private User u;
	private String uid, pwd, Uname;
	private int page;

	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf8");
		response.setContentType("text/html; charset=utf8");
		String method = request.getMethod();
		HttpSession ss = request.getSession();
		String[] emailReg;

		switch (request.getServletPath()) {
		case LIST:
			if (request.getParameter("page") == null) {
				response.sendRedirect("/bbs" + LIST + "?page=1");
			} else {
				ss.setAttribute("menu", "user");
				
				/** 페이지네이션 */
				page = Integer.parseInt(request.getParameter("page"));
				ss.setAttribute("currentUserPage", page);
				request.setAttribute("pageList", getPagination());

				/** 리스트 */
				List<User> list = dao.userList(page);
				request.setAttribute("userList", list);
				Forward(request, response, LIST_VIEW);
			}
			break;

		case REG:
			switch (method) {
			case "GET":
				Forward(request, response, REG_VIEW);
				break;

			case "POST":
				uid = request.getParameter("uid");
				String[] pwdBox = request.getParameterValues("pwd");
				Uname = request.getParameter("uname");

				/** 이메일 미기입 시 */
				emailReg = request.getParameterValues("email");
				if (!emailReg[1].isEmpty())
					u = new User(uid, pwdBox[0], Uname, emailReg[1]);
				else
					u = new User(uid, pwdBox[0], Uname);

				/** 아이디 중복 검사 */
				if (dao.getUserInfo(uid).getUid() == null) {
					// 패스워드 확인
					if (!pwdBox[0].equals(pwdBox[1])) {
						// 메세지 -> REG
						request.setAttribute("msg", "입력된 패스워드가 다릅니다!");
						request.setAttribute("url", REG);
						Forward(request, response, MSG);
					} else {
						dao.regUser(u);
						SetSession(ss, uid);
						// 메세지 -> LIST
						request.setAttribute("msg", u.getUname() + "님 환영합니다!");
						request.setAttribute("url", LIST);
						Forward(request, response, MSG);
					}
				} else {
					// 메세지 -> REG
					request.setAttribute("msg", u.getUid() + "는 중복된 아이디입니다.");
					request.setAttribute("url", REG);
					Forward(request, response, MSG);
				}
			}
			break;

		case LOGIN:
			switch (method) {
			case "GET":
				Forward(request, response, LOGIN_VIEW);
				break;

			case "POST":
				uid = request.getParameter("uid");
				pwd = request.getParameter("pwd");
				u = dao.getUserInfo(uid);

				if (u.getUid() != null) {
					if (BCrypt.checkpw(pwd, u.getPwd())) {
						/** Session Setting */
						SetSession(ss, uid);
						// 메세지 -> LIST
						request.setAttribute("msg", u.getUname() + "님 환영합니다!");
						request.setAttribute("url", LIST);
						Forward(request, response, MSG);
					} else { // 패스워드 불일치
						// 메세지 -> LOGIN
						request.setAttribute("msg", "패스워드를 확인해주세요!");
						request.setAttribute("url", LOGIN);
						Forward(request, response, MSG);
					}
				} else {
					// 메세지 -> LOGIN
					request.setAttribute("msg", "아이디를 확인해주세요!");
					request.setAttribute("url", LOGIN);
					Forward(request, response, MSG);
					break;
				}
				break;
			}
			break;

		case LOGOUT:
			uid = (String) ss.getAttribute("uid");
			Uname = (String) ss.getAttribute("uname");
			u = dao.getUserInfo(uid);
			ss.invalidate();
			// 메세지 -> LIST
			request.setAttribute("msg", Uname + "님 로그아웃 되었습니다!");
			request.setAttribute("url", LIST);
			Forward(request, response, MSG);
			break;

		case UPDATE:
			switch (method) {
			case "GET":
				uid = request.getParameter("uid");
				u = dao.getUserInfo(uid);
				request.setAttribute("user", u);
				Forward(request, response, UPDATE_VIEW);
				break;
			case "POST":
				uid = request.getParameter("uid");
//				pwd = request.getParameter("pwd");
				Uname = request.getParameter("name");
				String pwdbox[] = request.getParameterValues("pwd");

				/** 이메일 미기입 시 */
				emailReg = request.getParameterValues("email");
				if (!emailReg[1].isEmpty())
					u = new User(uid, pwd, Uname, emailReg[1]);
				else
					u = new User(uid, pwd, Uname);

				if (pwdbox.equals("") || pwdbox == null) {
					dao.nonPwdUpdateUser(u);
					SetSession(ss, uid);
					response.sendRedirect(LIST);
				} else if (pwdbox[0].equals(pwdbox[1])) {
					dao.updateUser(u);
					SetSession(ss, uid);
					// 메세지 -> LIST
					request.setAttribute("msg", uid + " : 정보 수정 완료!");
					request.setAttribute("url", LIST);
					Forward(request, response, MSG);
				} else {
					request.setAttribute("msg", "입력된 패스워드가 다릅니다!");
					request.setAttribute("url", LIST);
					Forward(request, response, MSG);
				}
				break;
			}
			break;

		case DEL:
			uid = request.getParameter("uid");
			request.setAttribute("deluid", uid);
			Forward(request, response, DEL + ".jsp");
			break;

		case "/user/deleteConfirm":
			uid = request.getParameter("uid");
			u = dao.getUserInfo(uid);
			dao.delUser(uid);
			response.sendRedirect("/bbs"+LIST);
			break;

		default:
			System.out.println("Controlloer 잘못된 경로");
			break;
		}

	}

	private void Forward(HttpServletRequest request, HttpServletResponse response, String url)
			throws ServletException, IOException {
		rd = request.getRequestDispatcher(url);
		rd.forward(request, response);
	}

	private void SetSession(HttpSession ss, String uid) {
		User info = dao.getUserInfo(uid);
		ss.setAttribute("uid", info.getUid());
		ss.setAttribute("uname", info.getUname());
		ss.setAttribute("regDate", info.getRegDate());
		ss.setAttribute("email", info.getEmail());
		System.out.println("[Session Setting] : " + ss.getAttribute("uid") + ", " + ss.getAttribute("uname"));
	}
	
	private static List<String> getPagination() {
		UserDAO dao = new UserDAO();
		int totalPages = dao.getUserPageCnt();
		List<String> pageList = new ArrayList<>();
		for (int i = 1; i <= totalPages; i++)
			pageList.add(String.valueOf(i));
		return pageList;
	}

}
