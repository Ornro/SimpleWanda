package fr.irit.wanda.dao;

import java.io.File;

import javax.servlet.http.HttpServletRequest;


import org.apache.commons.fileupload.FileItem;

import fr.irit.wanda.exception.FileSavingException;

public class FileAO extends DAO {

	private static String DESTINATION_DIR_PATH = "/usr/local/wanda/videos";

	public static void upload(HttpServletRequest request){
		
	}

	public static String save(FileItem fileToSave, String id, String format, String resolution) throws FileSavingException{
		String finalPath = DESTINATION_DIR_PATH+"/"+id+"/."+resolution+format;
		File destinationFile = new File(finalPath);
		try {
			if (destinationFile.createNewFile()){
				fileToSave.write(destinationFile);
			}
		} catch (Exception e) {
			// why? oh my god why would that happen?
			e.printStackTrace();
			throw new FileSavingException();
		}

		return finalPath;
	}
}