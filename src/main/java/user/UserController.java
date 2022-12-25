package user;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.mindrot.jbcrypt.BCrypt;

import board.BoardController;

@WebServlet({ "/user/list", "/user/login", "/user/register", "/user/logout", "/user/delete", "/user/deleteConfirm",
		"/user/update" })
public class UserController extends HttpServlet {
	public static final String LIST = "/user/list", LOGIN = "/user/login", LOGOUT = "/user/logout",
			REG = "/user/register", UPDATE = "/user/update", DEL = "/user/delete", DEL_CON = "/user/deleteConfirm",
			BBS = "/bbs";
	public static final String LOGIN_VIEW = "/WEB-INF/view/user/login.jsp", LIST_VIEW = "/WEB-INF/view/user/list.jsp",
			REG_VIEW = "/WEB-INF/view/user/register.jsp", UPDATE_VIEW = "/WEB-INF/view/user/update.jsp",
			DEL_VIEW = "/WEB-INF/view/user/delete.jsp", MSG = "/WEB-INF/view/user/msg.jsp";
	private static final long serialVersionUID = 1L;
	private RequestDispatcher rd;
	private static UserDAO dao = new UserDAO();
	private User u;
	private String uid, pwd, Uname, email;

	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String method = request.getMethod();
		HttpSession ss = request.getSession();
		int page = request.getParameter("page") == null ? 1 : Integer.parseInt(request.getParameter("page"));
		System.out.println(page);
		switch (request.getServletPath()) {
		case LIST:
			ss.setAttribute("menu", "user");

			/** 페이지네이션 */
			setPagination(request, page);

			/** 리스트 */
			List<User> list = dao.userList(page);
			request.setAttribute("userList", list);
			Forward(request, response, LIST_VIEW);
			break;

		case REG:
			ss.setAttribute("menu", "reg");
			switch (method) {
			case "GET":
				Forward(request, response, REG_VIEW);
				break;

			case "POST":
				uid = request.getParameter("uid");
				String[] pwdBox = request.getParameterValues("pwd");
				Uname = request.getParameter("uname");
				email = request.getParameter("email");

				if (email.isEmpty())
					u = new User(uid, pwdBox[0], Uname);
				else
					u = new User(uid, pwdBox[0], Uname, email);

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
						request.setAttribute("url", BoardController.BLIST);
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
				ss.setAttribute("menu", "login");
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
						request.setAttribute("url", BoardController.BLIST);
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
			ss.setAttribute("menu", "user");
			switch (method) {
			case "GET":
				uid = request.getParameter("uid");
				u = dao.getUserInfo(uid);
				request.setAttribute("user", u);
				Forward(request, response, UPDATE_VIEW);
				break;
			case "POST":
				uid = request.getParameter("uid");
				Uname = request.getParameter("name");
				String pwdbox[] = request.getParameterValues("pwd");

				/** 이메일 미기입 시 */
				email = request.getParameter("email");
				if (email.isEmpty())
					u = new User(uid, pwdbox[0], Uname);
				else
					u = new User(uid, pwdbox[0], Uname, email);
				
				/** 패스워드 검증 */
				if (pwdbox[0].isEmpty() && pwdbox[1].isEmpty()) {
					dao.nonPwdUpdateUser(u);
					SetSession(ss, uid);
					
					// 메세지 -> LIST
					request.setAttribute("msg", uid + " : 정보 수정 완료!");
					request.setAttribute("url", LIST + "?page=" + ss.getAttribute("currentUserPage"));
					Forward(request, response, MSG);
				} else if (pwdbox[0].equals(pwdbox[1])) {
					dao.updateUser(u);
					
					//어드민이 수정했을때 (추가)
					if (ss.getAttribute("uid").equals("admin"))
						SetSession(ss, "admin");
					else
						SetSession(ss, uid);

					// 메세지 -> LIST
					request.setAttribute("msg", uid + " : 정보 수정 완료!");
					request.setAttribute("url", LIST + "?page=" + ss.getAttribute("currentUserPage"));
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
			ss.setAttribute("menu", "user");
			uid = request.getParameter("uid");
			request.setAttribute("deluid", uid);
			Forward(request, response, DEL_VIEW);
			break;

		case "/user/deleteConfirm":
			ss.setAttribute("menu", "user");
			uid = request.getParameter("uid");
			u = dao.getUserInfo(uid);
			dao.delUser(uid);
			response.sendRedirect(BBS + LIST);
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

	/** ver2 pagination */
	private static void setPagination(HttpServletRequest request, int page) {
		HttpSession ss = request.getSession();
		List<String> pageList = new ArrayList<>();

		/** DB에서 총 페이지 개수 받아 pageList에 담기 */
		int totalPages = dao.getUserPageCnt();
		for (int i = 1; i <= totalPages; i++)
			pageList.add(String.valueOf(i));

		/** 세션 세팅 / 리퀘스트 세팅 */
		ss.setAttribute("currentUserPage", page); // 현재 페이지 세션 저장
		request.setAttribute("pageList", pageList); // 페이지네이션 리스트 저장
	}

}
