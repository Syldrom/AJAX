/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import model.CustomerEntity;
import com.google.gson.*;

import model.DAO;
import model.DataSourceFactory;

/**
 *
 * @author rbastide
 */
@WebServlet(name = "customersInJSON", urlPatterns = {"/customersInJSON"})
public class CustomersInJsonServlet extends HttpServlet {

	/**
	 * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
	 *
	 * @param request servlet request
	 * @param response servlet response
	 * @throws ServletException if a servlet-specific error occurs
	 * @throws IOException if an I/O error occurs
	 */
	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {

		response.setContentType("application/json;charset=UTF-8");

		
		try (PrintWriter out = response.getWriter()) {
			// Trouver la valeur du paramètre HTTP state
			String state = request.getParameter("state");

			// Créér le DAO avec sa source de données
			DAO dao = new DAO(DataSourceFactory.getDataSource(DataSourceFactory.DriverType.embedded));

			List<CustomerEntity> customers = dao.customersInState(state);
			
			// Générer du JSON
			Gson gson = new Gson();
			String gsonData = gson.toJson(customers);
			out.println(gsonData);
			
		} catch (Exception ex) {
			Logger.getLogger("JSONServlet").log(Level.SEVERE, "Action en erreur", ex);
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, ex.getMessage());
		}
	}

	// <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
	/**
	 * Handles the HTTP <code>GET</code> method.
	 *
	 * @param request servlet request
	 * @param response servlet response
	 * @throws ServletException if a servlet-specific error occurs
	 * @throws IOException if an I/O error occurs
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {
		processRequest(request, response);
	}

	/**
	 * Handles the HTTP <code>POST</code> method.
	 *
	 * @param request servlet request
	 * @param response servlet response
	 * @throws ServletException if a servlet-specific error occurs
	 * @throws IOException if an I/O error occurs
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {
		processRequest(request, response);
	}

	/**
	 * Returns a short description of the servlet.
	 *
	 * @return a String containing servlet description
	 */
	@Override
	public String getServletInfo() {
		return "Short description";
	}// </editor-fold>

}