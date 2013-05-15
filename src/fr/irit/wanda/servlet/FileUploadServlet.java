package fr.irit.wanda.servlet;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import fr.irit.wanda.dao.FileAO;

/**
 * Servlet implementation class FileUploadServlet
 */
@WebServlet("/Upload")
public class FileUploadServlet extends Servlet {
	private static final long serialVersionUID = 1L;
	private static final long MaxMemorySize = 1073741824; // 1go
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FileUploadServlet() {
        super();
    }

    public void upload(HttpServletRequest request){
    	boolean isMultipart = ServletFileUpload.isMultipartContent(request);
    	
    	FileItemFactory factory = new DiskFileItemFactory();

    	// Configure a repository (to ensure a secure temp location is used)
    	ServletContext servletContext = this.getServletConfig().getServletContext();
    	File repository = (File) servletContext.getAttribute("javax.servlet.context.tempdir");
    	((DiskFileItemFactory) factory).setRepository(repository);

    	// Create a new file upload handler
    	ServletFileUpload upload = new ServletFileUpload(factory);
    	
    	// Set max size for uploaded file
    	upload.setSizeMax(MaxMemorySize);

    	// Parse the request
    	try {
			List<FileItem> items = upload.parseRequest(request);
			
			Iterator<FileItem> iter = items.iterator();
	    	while (iter.hasNext()) {
	    	    FileItem item = iter.next();

	    	    if (!item.isFormField()) {
	    	    	//FileAO.save(item,); unfinished
                }
	    	}
		} catch (FileUploadException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}    	
    }
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}
}
