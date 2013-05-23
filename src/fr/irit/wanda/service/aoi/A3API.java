package fr.irit.wanda.service.aoi;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.bouncycastle.asn1.x500.RDN;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x500.style.BCStyle;
import org.bouncycastle.asn1.x500.style.IETFUtils;
import org.bouncycastle.cert.jcajce.JcaX509CertificateHolder;

import fr.irit.wanda.dao.LinkedEntityAO;
import fr.irit.wanda.dao.UserAO;
import fr.irit.wanda.entities.User;
import fr.irit.wanda.service.impl.RequestImpl;
import fr.irit.wanda.servlet.Servlet;



public class A3API extends Servlet{
	
	private static final int BUFSIZE = 4500;
	
	Collection<String> errors = new ArrayList<String>();
	public void doGet(HttpServletRequest request, HttpServletResponse response)
	throws IOException, ServletException{
		Collection<String> errors = new ArrayList<String>();
			
		try {
			// Auth
			User caller = new RequestImpl(request).getCaller(request);
			
			// Param retrieval
			int jobid = getInt(request, "jobid");
			
			// Service
			File vid = new LinkedEntityAO().getVideoFile(jobid, caller); 
			
			// Forward.
			System.out.println("A3:"+caller.getId()+" wants to download file : "+vid.getPath());
			doDownload(request, response, vid.getPath(), vid.hashCode()+"_"+vid.getName());
	
		}catch (Exception e) {
			errors.add(e.getMessage()); 
			request.setAttribute("errors", errors); // TODO change redirection
			super.getServletContext().getRequestDispatcher("/WEB-INF/vues/atapi.jsp").forward(request, response);
		}
	}
	
	private void doDownload( HttpServletRequest req, HttpServletResponse resp,
            String filename, String original_filename )
	throws IOException
	{
		File                file        = new File(filename);
		int                 length   = 0;
		ServletOutputStream op       = resp.getOutputStream();
		ServletContext      context  = getServletContext();
		String              mimetype = context.getMimeType( filename );
		
		//
		//  Set the response and go!
		//
		//
		resp.setContentType( (mimetype != null) ? mimetype : "application/octet-stream" );
		resp.setContentLength( (int)file.length() );
		resp.setHeader( "Content-Disposition", "attachment; filename=\"" + original_filename + "\"" );
		
		//
		//  Stream to the requester.
		//
		byte[] bbuf = new byte[BUFSIZE];
		DataInputStream in = new DataInputStream(new FileInputStream(file));
		
		while ((in != null) && ((length = in.read(bbuf)) != -1))
		{
		op.write(bbuf,0,length);
		}
		
		in.close();
		op.flush();
		op.close();
	}
}
