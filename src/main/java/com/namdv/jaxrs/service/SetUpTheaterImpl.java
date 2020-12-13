package com.namdv.jaxrs.service;

import exception.TheaterException;
import response.SetupResponse;
import validation.BaseValidation;
import exception.ValidationException;
import model.*;
import utils.PageUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.*;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.io.IOException;

/**
 * @author namvdo
 */
@Path("/theater")
public class SetUpTheaterImpl implements SetUpTheater {
    private static Theater theater;

    /**
     * Sends the theater operator request to a page with the form that
     * his can set up the theater.
     * @param servletRequest the current servlet request
     * @param response the current servlet response
     * @return String indicates not suitable request method
     * @throws IOException      the io exception
     * @throws ServletException the servlet exception
     */
    @GET
    @Produces(MediaType.TEXT_HTML)
    @Path("/setup")
    public String getSetupPage(@Context HttpServletRequest servletRequest,
                                            @Context HttpServletResponse response)
            throws IOException, ServletException {
        String appContext = servletRequest.getContextPath();
        servletRequest.getRequestDispatcher(appContext + PageUtil.SET_UP_CINEMA_FORM).forward(servletRequest, response);
        return PageUtil.NOT_SUITABLE_REQUEST_METHOD_MESSAGE;
    }

    /**
     *
     * @param row - the row available in the theater
     * @param column - the column available in the theater
     * @param distance - the min distance between different parties
     * @return The SetupResponse with the parameterized type of Theater
     * signifies the creation of new theater instance. Only one theater instance
     * will be created when the application's running.
     */
    @Override
    @POST
    @Path("/setup")
    @Produces(MediaType.APPLICATION_JSON)
    public SetupResponse<Theater> setUpTheater(@FormParam("row") String row,
                                                            @FormParam("column") String column,
                                                            @FormParam("distance") String distance) {
        String message;
        String result;
        try {
            if (theater != null) {
                throw new TheaterException("The theater set up already there.");
            }
            if (BaseValidation.isValidSetupTheater(row, column, distance)) {
                message = "Set up theater successfully!";
                result = "OK";
                int r = Integer.parseInt(row);
                int c = Integer.parseInt(column);
                int minDistance = Integer.parseInt(distance);
                theater = new Theater(r, c, minDistance);
                return new SetupResponse<>(result, message, theater);
            } else {
                message = "Invalid number format, please try again!";
                result = "FAIL";
                return new SetupResponse<>(result, message, null);
            }
        } catch (ValidationException e) {
            e.getCause();
            return new SetupResponse<>("Fail", "Fail to set up the theater", null);
        } catch (TheaterException e) {
            e.getCause();
            return new SetupResponse<>("Fail", "The theater already set up", theater);
        }
    }

    /**
     * The the theater instance.
     *
     * @return the theater
     */
    public static Theater getTheater() {
        return theater;
    }
}
