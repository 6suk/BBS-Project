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

import com.google.protobuf.Empty;

@WebServlet({ "/board/list", "/board/search", "/board/write", "/board/update", "/board/detail", "/board/delete",
		"/board/deleteConfirm", "/board/reply" })
public class BoardController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private RequestDispatcher rd;
	private BoardDao dao = new BoardDao();
	private ReplyDao rdao = new ReplyDao();
	private static final String BLIST = "/board/list", WRITE = "/board/write", BUPDATE = "/board/update",
			BDETAIL = "/board/detail", BDEL = "/board/delete", BDEL_CON = "/board/deleteConfirm",
			SEARCH = "/board/search", REPLY = "/board/reply", MSG = "/user/msg.jsp";
	private Board b;
	private String uid, pwd, Uname, title, content, files;
	private int page, bid;

	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf8");
		String method = request.getMethod();
		HttpSession ss = request.getSession();
		ss.setAttribute("menu", "board");
		String ssuid = (String) ss.getAttribute("uid");

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
				b = new Board(ssuid, title, content, files);
				dao.insertBoard(b);
				Forward(request, response, BLIST);
			}
			break;

		case BDETAIL:
			bid = Integer.parseInt(request.getParameter("bid"));
			uid = request.getParameter("uid");
			// 댓글 작성 후 조회수 증가 막기
			String option = request.getParameter("option");

			// 조회수 증가
			if (!uid.equals(ssuid) && option == null) {
				dao.viewCountUpdate(bid);
			}
			Board b = dao.getBoardDetail(bid);
			request.setAttribute("board", b);

			List<Reply> rlist = rdao.replyList(bid);
			request.setAttribute("rList", rlist);

			Forward(request, response, BDETAIL + ".jsp");
			break;

		case REPLY:
			content = request.getParameter("content");
			uid = request.getParameter("uid"); // 작성자
			bid = Integer.parseInt(request.getParameter("bid"));
			int ismine = uid.equals(ssuid) ? 1 : 0;
			Reply reply = new Reply(content, ismine, ssuid, bid);
			rdao.insert(reply);
			dao.replyCountUpdate(bid);
			response.sendRedirect("/bbs/board/detail?bid=" + bid + "&uid=" + uid + "&option=DNI" + "#content");
			break;

		case BDEL:
			bid = Integer.parseInt(request.getParameter("bid"));
			request.setAttribute("delbid", bid);
			Forward(request, response, BDEL + ".jsp");
			break;

		case BDEL_CON:
			bid = Integer.parseInt(request.getParameter("bid"));
			dao.delBoard(bid);
			response.sendRedirect("/bbs" + BLIST + "?page=" + ss.getAttribute("currentBoardPage"));
			break;

		case BUPDATE:
			/** GET */
			if (method.equals("GET")) {
				bid = Integer.parseInt(request.getParameter("bid"));
				b = dao.getBoardDetail(bid);
				request.setAttribute("binfo", b);
				Forward(request, response, BUPDATE + ".jsp");
			}
			/** POST */
			else {
				// bid title content files
				bid = Integer.parseInt(request.getParameter("bid"));
				title = request.getParameter("title");
				content = request.getParameter("content");
				files = request.getParameter("files");
				b = new Board(bid, title, content, files);
				dao.updateBoard(b);

				// 메세지 -> LIST
				request.setAttribute("msg", "수정이 완료 되었습니다!");
				request.setAttribute("url", BLIST + "?page=" + ss.getAttribute("currentBoardPage"));
				Forward(request, response, MSG);
			}
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
