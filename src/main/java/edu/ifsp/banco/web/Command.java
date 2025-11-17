package edu.ifsp.banco.web;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;


public interface Command extends HttpServlet {
	void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;

}
