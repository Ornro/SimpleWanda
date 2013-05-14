package fr.irit.wanda.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.irit.wanda.dao.ContainerAO;
import fr.irit.wanda.entities.NamedEntity;
import fr.irit.wanda.exception.NotAllowedToProceedException;
import fr.irit.wanda.service.IRequest;
import fr.irit.wanda.service.impl.RequestImpl;

/**
 * Servlet implementation class Choose
 */
@WebServlet("/Choose")
public class Choose extends Servlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Choose() {
        super("benjamin.babic@hotmail.fr");
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String message = null;
		request.setAttribute("returnedString", message);
		getServletContext().getRequestDispatcher("/test.jsp")
				.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	private String addSons(NamedEntity container){
		RequestImpl ir= new RequestImpl("benjamin.babic@hotmail.fr");
		String chaine="";
		try {
			if (ir.isAllowedToProceed(container)) {
				for (String son:new ContainerAO().getSonsNames(container)){
					chaine += "<li>" + ir.printAJAXCreateLink(container.getId()+"_"+son+"_"+container.getEntityName())+ "<img src=\"/SimpleWanda/img/add.png \" class=\"icon\"/\\><small>Add "+son+"</small> </a></li>";
				}
			}
		} catch (NotAllowedToProceedException e) {
			System.err.println("ERROR");
		}
		return chaine;
	}
}
