package controller;

import utils.PageUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CheckSeatController extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String appContext = request.getContextPath();
        request.getRequestDispatcher(appContext + PageUtil.CHECK_SEATS_JSP).forward(request, response);
    }
}
