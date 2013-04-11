package fir.irit.wanda.tests;

import fr.irit.wanda.dao.NamedEntityAO;
import fr.irit.wanda.entities.Entity;
import fr.irit.wanda.entities.NamedEntity;
import fr.irit.wanda.exception.NotFoundInDatabaseException;
import fr.irit.wanda.service.IPublic;
import fr.irit.wanda.service.impl.PublicImpl;

public class generalTests {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws NotFoundInDatabaseException{
		testHierarchy();
		testFather(new NamedEntity(1,"corpus","test"));
		

	}
	
	private static void testHierarchy(){
		IPublic access = new PublicImpl();
		System.out.println(access.printHierarchy());
	}
	
	private static void testFather(NamedEntity ne) throws NotFoundInDatabaseException{
		NamedEntityAO nao = new NamedEntityAO();
		Entity e = nao.getFather(ne);
		if (e==null) System.out.println("This entity has no father");
		else System.out.println("This entity father is "+ e.getEntityName()+ " "+ e.getId());
	}

}
