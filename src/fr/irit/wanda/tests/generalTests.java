package fr.irit.wanda.tests;

import fr.irit.wanda.entities.NamedEntity;
import fr.irit.wanda.exception.NotFoundInDatabaseException;
import fr.irit.wanda.service.IRequest;
import fr.irit.wanda.service.impl.RequestImpl;

public class generalTests {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws NotFoundInDatabaseException{
		testHierarchy();


	}

	private static void testHierarchy(){
		IRequest access = new RequestImpl("benjamin.babic@hotmail.fr");
		System.out.println(access.printHierarchy());
	}

	private static void testFather(NamedEntity ne) throws NotFoundInDatabaseException{

	}

}