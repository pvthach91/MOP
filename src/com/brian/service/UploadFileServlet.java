package com.brian.service;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Iterator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

public class UploadFileServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;

	public void doPost(HttpServletRequest req, HttpServletResponse res) 
             throws ServletException, IOException {
		boolean isMultipart= ServletFileUpload.isMultipartContent(req);
		 if(isMultipart){
			  FileItemFactory factory = new DiskFileItemFactory();
	          ServletFileUpload upload = new ServletFileUpload(factory);
	          upload.setFileSizeMax(1024*100);//100K
	          upload.setSizeMax(1024*200);
	          try{
	        	  Iterator items=upload.parseRequest(req).iterator();
	        	  while(items.hasNext()){
	        		  FileItem item = (FileItem) items.next();
	        		  if(!item.isFormField()){
	        			//取出上传文件的文件名称
	        			//String name = item.getName();
	        			  InputStream in = item.getInputStream();
	        			  StringBuffer out = new StringBuffer();
	        			  byte[] b = new byte[4096];
	        			  for(int n; (n=in.read(b)) !=-1;){ 
	        				  out.append(new String(b,0,n)); 
	        			  }
	        			  //System.out.println("大小："+item.getSize());
	        			 //System.out.println("内容："+ out.toString());
	        			  PrintWriter wt = res.getWriter();
	        			  wt.println(out.toString());
	        			  wt.flush();
	        			  wt.close();
	        		  }
	        	  }
	          }catch(Exception ex){
	        	  System.out.println(ex.getMessage());
	          }
		 }
		/**
		int totalbytes = req.getContentLength();
		byte[] b = new byte[totalbytes];
		DataInputStream in = new DataInputStream(req.getInputStream());
        in.readFully(b);
        in.close();
        String reqcontent = new String(b);
        System.out.println("文件内容："+reqcontent);
        BufferedReader reqbuf = new BufferedReader(new StringReader(reqcontent));
        */
	 }
}
