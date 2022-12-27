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
	public static final String BLIST = "/board/list", WRITE = "/board/write", BUPDATE = "/board/update",
			BDET = "/board/detail", BDEL = "/board/delete", BDEL_CON = "/board/deleteConfirm", REPLY = "/board/reply",
			BBS = "/bbs";
	public static final String BLIST_V = "/WEB-INF/view/board/list.jsp", WRITE_V = "/WEB-INF/view/board/write.jsp",
			BDEL_V = "/WEB-INF/view/board/delete.jsp", BDET_V = "/WEB-INF/view/board/detail.jsp",
			BUPDATE_V = "/WEB-INF/view/board/update.jsp", MSG = "/WEB-INF/view/user/msg.jsp";
	private RequestDispatcher rd;
	private static BoardDao dao = new BoardDao();
	private ReplyDao rdao = new ReplyDao();
	private Board b;
	private String uid, pwd, Uname, title, content, files, field, query;
	private int page, bid;
	private LocalDate today = LocalDate.now(); // 0000-00-00 00:00:00
	private List<Board> list;

	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
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
			request.setAttribute("today", today);
			/** 페이지네이션 세팅 */
			setPagination(request, page, field, query);

			/** 리스트 출력 */
			list = dao.boardList(field, query, page);
			request.setAttribute("boardList", list);
			Forward(request, response, BLIST_V);
			break;

		case WRITE:
			/** GET */
			if (method.equals("GET")) {
				Forward(request, response, WRITE_V);
			}
			/** POST */
			else {
				JSONUtil json = new JSONUtil();
				List<String> fileList = (List<String>) request.getAttribute("fileList");
				System.out.println(fileList.toString());
				
				title = (String) request.getAttribute("title");
				content = (String) request.getAttribute("content");
				files = json.stringify(fileList);
				b = new Board(ssuid, title, content, files);
				dao.insertBoard(b);
				response.sendRedirect(BBS + BLIST);
			}
			break;

		case BDET:
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
			request.setAttribute("fileList", getFileList(b));

			List<Reply> rlist = rdao.replyList(bid);
			request.setAttribute("rList", rlist);

			Forward(request, response, BDET_V);
			break;

		case REPLY:
			content = request.getParameter("content");
			uid = request.getParameter("uid"); // 작성자
			bid = Integer.parseInt(request.getParameter("bid"));
			int ismine = uid.equals(ssuid) ? 1 : 0;
			Reply reply = new Reply(content, ismine, ssuid, bid);
			rdao.insert(reply);
			dao.replyCountUpdate(bid);
			response.sendRedirect(BBS + BDET + "?bid=" + bid + "&uid=" + uid + "&option=DNI" + "#content");
			break;

		case BDEL:
			bid = Integer.parseInt(request.getParameter("bid"));
			request.setAttribute("delbid", bid);
			Forward(request, response, BDEL_V);
			break;

		case BDEL_CON:
			bid = Integer.parseInt(request.getParameter("bid"));
			dao.delBoard(bid);
			response.sendRedirect(BBS + BLIST + "?page=" + ss.getAttribute("currentBoardPage"));
			break;

		case BUPDATE:
			/** GET */
			if (method.equals("GET")) {
				bid = Integer.parseInt(request.getParameter("bid"));
				b = dao.getBoardDetail(bid);
				request.setAttribute("binfo", b);
				request.setAttribute("fileList", getFileList(b));
				Forward(request, response, BUPDATE_V);

			}
			/** POST */
			else {
				// bid title content files
				String bidStr = (String) request.getAttribute("bid");
				bid = Integer.parseInt(bidStr);
				title = (String) request.getAttribute("title");
				content = (String) request.getAttribute("content");
				
				/** 첨부파일 다중삭제 및 수정 */
				b = dao.getBoardDetail(bid);
				List<String> fileList =  getFileList(b);
				List<String> delList = (List<String>) request.getAttribute("delList");
				List<String> addList = (List<String>) request.getAttribute("addList");
				
				for(String dFile : delList) fileList.remove(dFile);
				for(String aFile : addList) fileList.add(aFile);
				
				JSONUtil json = new JSONUtil();
				files = json.stringify(fileList);
				
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

	private static List<String> getFileList(Board b) {
		// DB에 있는 JSON 파일 -> LIST로 변환 후 리턴
		String jsonFiles = b.getFiles();
		if (jsonFiles != null && jsonFiles != "") {
			JSONUtil json = new JSONUtil();
			List<String> fileList = json.parse(jsonFiles);

			return fileList;
		}
		return null;
	}

}
