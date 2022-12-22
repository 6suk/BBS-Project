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

import misc.JSONUtil;

@WebServlet({ "/board/list", "/board/write", "/board/update", "/board/detail", "/board/delete", "/board/deleteConfirm",
		"/board/reply" })
public class BoardController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private RequestDispatcher rd;
	private static BoardDao dao = new BoardDao();
	private ReplyDao rdao = new ReplyDao();
	private static final String BLIST = "/board/list", WRITE = "/board/write", BUPDATE = "/board/update",
			BDETAIL = "/board/detail", BDEL = "/board/delete", BDEL_CON = "/board/deleteConfirm",
			REPLY = "/board/reply", MSG = "/user/msg.jsp";
	private Board b;
	private String uid, pwd, Uname, title, content, files, field, query;
	private int page, bid;
	private LocalDate today = LocalDate.now(); // 0000-00-00 00:00:00
	private List<Board> list;

	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf8");
		String method = request.getMethod();
		HttpSession ss = request.getSession();
		ss.setAttribute("menu", "board");
		String ssuid = (String) ss.getAttribute("uid");
		/** Get field, query */
		String field = request.getParameter("field") == null ? "title" : request.getParameter("field");
		String query = request.getParameter("query") == null ? "" : request.getParameter("query");
		int page = request.getParameter("page") == null ? 1 : Integer.parseInt(request.getParameter("page"));

		switch (request.getServletPath()) {

		case BLIST:
			// LIST + SEARCH (POST)
			request.setAttribute("today", today);
			/** 페이지네이션 세팅 */
			setPagination(request, page, field, query);

			/** 리스트 출력 */
			list = dao.boardList(field, query, page);
			request.setAttribute("boardList", list);
			Forward(request, response, BLIST + ".jsp");
			break;

		case WRITE:
			/** GET */
			if (method.equals("GET")) {
				Forward(request, response, WRITE + ".jsp");
			}
			/** POST */
			else {
				title = (String) request.getAttribute("title");
				content = (String) request.getAttribute("content");
				files = (String) request.getAttribute("files");
				b = new Board(ssuid, title, content, files);
				dao.insertBoard(b);
				response.sendRedirect("/bbs" + BLIST);
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

			String jsonFiles = b.getFiles();
			if (jsonFiles != null && jsonFiles != "") {
				JSONUtil json = new JSONUtil();
				List<String> fileList = json.parse(jsonFiles);
				request.setAttribute("fileList", fileList);
			}

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

	/** ver1 pagination */
	private static List<String> getPagination(String field, String query) {
		BoardDao dao = new BoardDao();
		int totalPages = dao.getBoardPageCnt(field, query);
		List<String> pageList = new ArrayList<>();
		for (int i = 1; i <= totalPages; i++)
			pageList.add(String.valueOf(i));

		return pageList;
	}

	/** ver2 pagination */
	private static void setPagination(HttpServletRequest request, int page, String field, String query) {
		HttpSession ss = request.getSession();
		List<String> pageList = new ArrayList<>();

		/** DB에서 총 페이지 개수 받아 pageList에 담기 */
		int totalPages = dao.getBoardPageCnt(field, query);
		for (int i = 1; i <= totalPages; i++)
			pageList.add(String.valueOf(i));

		/** 세션 세팅 / 리퀘스트 세팅 */
		ss.setAttribute("currentBoardPage", page); // 현재 페이지 세션 저장
		request.setAttribute("pageList", pageList); // 페이지네이션 리스트 저장
	}

}
