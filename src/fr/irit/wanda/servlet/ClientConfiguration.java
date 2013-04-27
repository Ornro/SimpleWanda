package fr.irit.wanda.servlet;

import fr.irit.wanda.service.IRequest;
import fr.irit.wanda.service.impl.RequestImpl;


/**
 * Si le client se complexifie ou se d�localise (e.g application client charg�e par le navigateur en JAVA)
 * cette classe de configuration servira � r�cup�rer l'interface de requ�te et sa configuration g�n�ral.
 * 
 * @author Wanda Team
 *
 */
public class ClientConfiguration {
	public IRequest remoteRequest;
	
	public ClientConfiguration(String userEmail){
		remoteRequest = new RequestImpl(userEmail);
	}
}
