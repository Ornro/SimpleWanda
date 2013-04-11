package fr.irit.wanda.service;

import java.util.Collection;

import fr.irit.wanda.entities.Annotation;
import fr.irit.wanda.entities.MetadataContent;
import fr.irit.wanda.entities.NamedEntity;
import fr.irit.wanda.entities.Video;
import fr.irit.wanda.exception.NotFoundInDatabaseException;

import org.apache.commons.fileupload.FileItem;

public interface IRegisteredUser {

	/**
	 * Sends an annotation to the server and saves the file
	 * 
	 * @param annotation
	 *            the object representation of the annotation
	 * @param annotationFile
	 *            the file that will be saved
	 * @return true if success false if not
	 */
	public boolean submitAnnotation(Annotation annotation,
			FileItem annotationFile);

	/**
	 * Returns an annotation file using it name (security check)
	 * 
	 * @param annotationName
	 *            the name of the annotation
	 * @return the matching file
	 */
	public FileItem getAnnotation(String annotationName);

	/**
	 * Returns the metatada concerning an entity.
	 * 
	 * @param concerned
	 *            the entity of which you want metadata
	 * @return MetadataContent Collection representing the content of all the
	 *         public metadata
	 * @throws NotFoundInDatabaseException
	 */
	public Collection<MetadataContent> getMetadata(NamedEntity concerned) throws NotFoundInDatabaseException;

	/**
	 * Returns a video file using it name (security check)
	 * 
	 * @param videoName
	 *            the name of the video to get
	 * @return the matching file
	 */
	public FileItem getVideo(String videoName);

	/**
	 * Sets a new privacy for the named entity (security check)
	 * 
	 * @param entity
	 *            the entity to edit
	 * @param privacy
	 *            the new privacy
	 * @return true if success false if not
	 */
	public boolean editPrivacy(NamedEntity entity, boolean privacy) throws NotFoundInDatabaseException;
	
	/**
	 * Creates a new video
	 * 
	 * @param video
	 *            the new video
	 * @return true if success false if not
	 */
	public boolean createVideo(Video video);
}
