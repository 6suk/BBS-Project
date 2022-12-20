package board;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet({ "/board/list", "/board/search", "/board/write", "/board/update", "/board/detail", "/board/delete",
		"/board/deleteConfirm" })
public class BoardController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private RequestDispatcher rd;
	private BoardDao dao = new BoardDao();
	// private ReplyDao rdao = new ReplyDao();
	private static final String BLIST = "/board/list", WRITE = "/board/write", BUPDATE = "/board/update",
			BDETAIL = "/board/detail", BDEL = "/board/delete", BDEL_CON = "/board/deleteConfirm",
			SEARCH = "/board/search";
	private Board b;
	private String uid, pwd, Uname, title, content, files;
	private int page, bid;

	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf8");
		response.setContentType("text/html; charset=utf8");
		String method = request.getMethod();
		HttpSession ss = request.getSession();
		ss.setAttribute("menu", "board");
		uid = (String) ss.getAttribute("uid");

		switch (request.getServletPath()) {

		case BLIST:
			if (request.getParameter("page") == null) {
				response.sendRedirect("/bbs" + BLIST + "?page=1");
			} else {
				LocalDate today = LocalDate.now(); // 0000-00-00 00:00:00
				request.setAttribute("today", today);

				/** 페이지네이션 */
				page = Integer.parseInt(request.getParameter("page"));
				ss.setAttribute("currentBoardPage", page);
				request.setAttribute("pageList", getPagination());
				
				/** 리스트 */
				List<Board> list = dao.boardList("title", "", page);
				request.setAttribute("boardList", list);
				Forward(request, response, BLIST + ".jsp");
			}
			break;

		case WRITE:
			/** GET */
			if (method.equals("GET")) {
				Forward(request, response, WRITE + ".jsp");
			}
			/** POST */
			else {
				title = request.getParameter("title");
				content = request.getParameter("content");
				files = request.getParameter("files");
				b = new Board(uid, title, content, files);
				dao.insert(b);
				Forward(request, response, BLIST);
			}
			break;
			
		case BDETAIL:
			bid = Integer.parseInt(request.getParameter("bid"));
			Board b = dao.getBoardDetail(bid);
			request.setAttribute("board", b);
			Forward(request, response, BDETAIL+".jsp");
			break;
			
		default:
			break;
		}

	}

	private void Forward(HttpServletRequest request, HttpServletResponse response, String url)
			throws ServletException, IOException {
		rd = request.getRequestDispatcher(url);
		rd.forward(request, response);
	}
	
	private static List<String> getPagination() {
		BoardDao dao = new BoardDao();
		int totalPages = dao.getBoardPageCnt();
		List<String> pageList = new ArrayList<>();
		for (int i = 1; i <= totalPages; i++)
			pageList.add(String.valueOf(i));
		
		return pageList;
	}

}
