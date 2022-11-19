package com.example.util;

import com.example.bean.BoardVO;
import com.example.dao.BoardDAO;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.io.File;

public class JDBCUtil {
	public static Connection getConnection(){  
	    Connection con=null;  
	    try{  
	        Class.forName("com.mysql.cj.jdbc.Driver");
	        con= DriverManager.getConnection("jdbc:mysql://walab.handong.edu:3306/p3_22000672","p3_22000672","doh1jo5Aez");
	    }catch(Exception e){
	    	System.out.println(e);
	    }  
	    return con;  
	}  
	
	public static void main(String ars[]) {
		Connection conn = getConnection();
		if(conn != null)
			System.out.println("DB 연결됨!");
		else
			System.out.println("DB 연결중 오류 !");
	}

	public static class FileUpload {
		public BoardVO uploadPhoto (HttpServletRequest request) {
			String filename = "";
			int sizeLimit = 15 * 1024 * 1024;

			String realPath = request.getServletContext().getRealPath("upload");

			File dir = new File(realPath);
			if (!dir.exists()) dir.mkdirs();

			BoardVO one = null;
			MultipartRequest multipartRequest = null;
			try {
				multipartRequest = new MultipartRequest(request, realPath, sizeLimit, "utf-8", new DefaultFileRenamePolicy());

				filename = multipartRequest.getFilesystemName("photo");

				one = new BoardVO();
				String seq = multipartRequest.getParameter("seq");
				if(seq!=null&&!seq.equals("")) one.setSeq(Integer.parseInt(seq));
				one.setTitle(multipartRequest.getParameter("title"));
				one.setWriter(multipartRequest.getParameter("writer"));
				one.setContent(multipartRequest.getParameter("content"));
				if(seq!=null && !seq.equals("")) {
					BoardDAO dao = new BoardDAO();
					String oldfilename = dao.getPhotoFilename(Integer.parseInt(seq));
					if(filename!=null&&oldfilename!=null)
						FileUpload.deleteFile(request, oldfilename);
					else if(filename==null&&oldfilename!=null)
						filename = oldfilename;
				}
				one.setPhoto(filename);
			} catch (IOException e) {
				e.printStackTrace();
			}
			filename = multipartRequest.getFilesystemName("photo");
			return one;
		}

		public static void deleteFile(HttpServletRequest request, String filename) {
			String filePath = request.getServletContext().getRealPath("upload");

			File f = new File(filePath + "/" + filename);
			if (f.exists()) f.delete();
		}
	}
}