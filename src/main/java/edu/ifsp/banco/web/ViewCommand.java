package edu.ifsp.banco.web;

import java.io.IOException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class ViewCommand implements Command {
	private enum Action {
		FORWARD, REDIRECT
	};

	private final String path;
	private final Action action;

	public ViewCommand(String destination) {
		int pos = destination.indexOf(":");
		if (pos == -1) {
			throw new IllegalArgumentException(
					"Destination format is invalid. Expected ':' after 'forward' or 'redirect'");
		}
		String action = destination.substring(0, pos);
		this.action = Action.valueOf(action.toUpperCase());
		this.path = destination.substring(pos + 1);
	}

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		switch (action) {
		case FORWARD:
			RequestDispatcher rd = request.getRequestDispatcher(path);
			rd.forward(request, response);
			break;
		case REDIRECT:
			response.sendRedirect(request.getContextPath() + path);
			break;
		}
	}

}
