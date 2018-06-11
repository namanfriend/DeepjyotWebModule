package com.nas.app.common.servlet;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DownloadServlet extends HttpServlet {  
		  
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {  
		response.setContentType("text/html");  
		PrintWriter out = response.getWriter();  
		String fileName = request.getParameter("fileName");
		String temDireLocation = System.getProperty("java.io.tmpdir");
		String filenameFull = temDireLocation + fileName;   
		System.out.println("File Location: " + filenameFull);
		response.setContentType("APPLICATION/OCTET-STREAM");   
		response.setHeader("Content-Disposition","attachment; filename=\"" + filenameFull + "\"");   
		  
		FileInputStream fileInputStream = new FileInputStream(filenameFull);  
		int i;   
		while ((i=fileInputStream.read()) != -1) {  
			out.write(i);   
		}   
		fileInputStream.close();   
		out.close();   
	}	
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {  
		response.setContentType("text/html");  
		PrintWriter out = response.getWriter();  
		String fileName = request.getParameter("fileName");
		String temDireLocation = System.getProperty("java.io.tmpdir");
		String filenameFull = temDireLocation + "\\" + fileName;   
		System.out.println("File Location: " + filenameFull);
		response.setContentType("APPLICATION/OCTET-STREAM");   
		response.setHeader("Content-Disposition","attachment; filename=\"" + filenameFull + "\"");   
		  
		FileInputStream fileInputStream = new FileInputStream(filenameFull);  
		int i;   
		while ((i=fileInputStream.read()) != -1) {  
			out.write(i);   
		}   
		fileInputStream.close();   
		out.close();   
	}
}  
