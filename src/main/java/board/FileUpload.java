package board;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import misc.JSONUtil;

@WebServlet("/board/fileUpload")
public class FileUpload extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String tmpPath = "c:/Temp/upload";
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		String dest = request.getParameter("dest");

		/** 업로드된 파일을 저장할 저장소 */
		DiskFileItemFactory factory = new DiskFileItemFactory();
		factory.setRepository(new File(tmpPath)); // 저장할 위치를 File객체로 생성
		factory.setSizeThreshold(1024 * 1024); // MaxMemorySize 1MB

		/** 파일변환 -> 리스트에 담기 */
		ServletFileUpload fu = new ServletFileUpload(factory);
		fu.setSizeMax(1024 * 1024 * 100); // maxRequestSize 전체 파일 용량
		fu.setFileSizeMax(1024 * 1024 * 10); // maxFileSize 파일 한개당 용량
		
		List<String> fileList = new ArrayList<>();
		List<String> delList = new ArrayList<>();
		
		try {
			List<FileItem> items = fu.parseRequest(request);
			/** 파일 저장 */
			for (FileItem i : items) {
				// 첨부 파일일 때
				if (!i.isFormField() && i.getSize() > 0) {
					String fileName = i.getName();
					File uploadFile = new File(tmpPath + File.separator + fileName);
					i.write(uploadFile); // 임시 파일을 파일로 씀
					fileList.add(fileName);
				}
				// 다른 타입 request일 때
				else if (i.isFormField()) {
					//파일 삭제
					if(i.getFieldName().equals("delFile")) {
						delList.add(i.getString("UTF-8"));
						File delfile = new File(tmpPath + File.separator + i.getString("UTF-8"));
						delfile.delete();
					}else request.setAttribute(i.getFieldName(), i.getString("UTF-8"));
				}
			}
			
			if(dest.equals("update")) {
				request.setAttribute("addList", fileList);
				request.setAttribute("delList", delList);
			}else {
				request.setAttribute("fileList", fileList);
			}
			
			RequestDispatcher rd = request.getRequestDispatcher("/board/"+ dest);
			rd.forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}