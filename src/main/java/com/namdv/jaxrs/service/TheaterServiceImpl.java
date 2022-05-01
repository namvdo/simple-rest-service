package com.namdv.jaxrs.service;

import exception.InvalidPartyException;
import exception.TheaterException;
import exception.ValidationException;
import model.Coordinate;
import model.Theater;
import response.CheckSeatResponse;
import response.ReserveSeatResponse;
import response.ViewTheaterResponse;
import utils.PageUtil;
import validation.BaseValidation;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.util.List;
import java.util.Set;

/**
 * @author namvdo
 */
@Path("/client")
public class TheaterServiceImpl implements TheaterService {
    private static final Theater theater = SetUpTheaterImpl.getTheater();

    /**
     * Sends client to the reservation form,
     * and returns the text value specify that the request is not
     * suitable.
     *
     * @param request  the servlet request
     * @param response the servlet response
     * @return the reserve page
     * @throws ServletException the servlet exception
     * @throws IOException      the io exception
     */
    @GET
    @Produces(MediaType.TEXT_HTML)
    @Path("/reserve")
    public String getReservePage(@Context HttpServletRequest request, @Context HttpServletResponse response) throws
            ServletException, IOException {
        String appContext = request.getContextPath();
        request.getRequestDispatcher(appContext + PageUtil.RESERVE_FORM).forward(request, response);
        return PageUtil.NOT_SUITABLE_REQUEST_METHOD_MESSAGE;
    }

    /**
     * Sends client to the seats checking form,
     * and returns the text value specify that the request is not
     * suitable.*
     *
     * @param request  the request
     * @param response the response
     * @return the check seat page
     * @throws ServletException the servlet exception
     * @throws IOException      the io exception
     */
    @GET
    @Produces(MediaType.TEXT_HTML)
    @Path("/seats")
    public String getCheckSeatPage(@Context HttpServletRequest request, @Context HttpServletResponse response) throws ServletException, IOException {
        String appContext = request.getContextPath();
        request.getRequestDispatcher(appContext + PageUtil.CHECK_SEATS_FORM).forward(request, response);
        return PageUtil.NOT_SUITABLE_REQUEST_METHOD_MESSAGE;
    }


    @Override
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/seats")
    public CheckSeatResponse getAvailableSeatSets(@FormParam("seats") String seats) {
        String message;
        String result;
        try {
            if (BaseValidation.isValidRequiredSeatsInput(seats, theater)) {
                List<Set<Integer>> availableSeats = theater.getAvailableSeatSets(Integer.parseInt(seats));
                if (availableSeats.isEmpty()) {
                    message = PageUtil.NO_SEAT_AVAILABLE;
                    result = PageUtil.FAIL_RESPONSE;
                } else {
                    message = "The seats required that are available together";
                    result = PageUtil.OK_RESPONSE;
                }
                return new CheckSeatResponse(result, message, availableSeats);
            } else {
                result = PageUtil.FAIL_RESPONSE;
                message = PageUtil.INVALID_NUMBER_FORMAT;
                return new CheckSeatResponse(result, message, null);
            }
        } catch (TheaterException e) {
            e.getCause();
            message = PageUtil.THEATER_NOT_YET_OPEN;
            result = PageUtil.FAIL_RESPONSE;
            return new CheckSeatResponse(result, message, null);
        } catch (ValidationException e) {
            e.getCause();
            message = PageUtil.NUMBER_NOT_IN_RANGE;
            result = PageUtil.FAIL_RESPONSE;
            return new CheckSeatResponse(result, message, null);
        }
    }

    @Override
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/reserve")
    public synchronized ReserveSeatResponse<Coordinate> reserveSeat(@FormParam("party") String party, @FormParam("row") String row,
                                                                    @FormParam("column") String column) {
        String message;
        String result;
        try {
            if (BaseValidation.isValidSeatPosition(party, row, column, theater)) {
                int r = Integer.parseInt(row);
                int c = Integer.parseInt(column);
                boolean isSucceeded = theater.reserveSeat(party, r, c);
                if (isSucceeded) {
                    message = "You have succeeded reserving a seat.";
                    result = PageUtil.OK_RESPONSE;
                    String seatNumber = r * theater.getColumnsPerRow() + c + 1 + "";
                    Coordinate location = new Coordinate(r, c);
                    return new ReserveSeatResponse<>(result, message, party, location, seatNumber);
                } else {
                    if (!theater.isPositionFree(Integer.parseInt(row), Integer.parseInt(column))) {
                        message = "This position is already taken";
                        result = PageUtil.FAIL_RESPONSE;
                        Coordinate location = new Coordinate(r, c);
                        return new ReserveSeatResponse<>(result, message, party, location, Integer.parseInt(row)
                                * theater.getColumnsPerRow() + Integer.parseInt(column) + 1 + "");

                    } else {
                        message = "Reservation failed! The min distance between parties" +
                                " are " + theater.getMinDistance();
                        result = PageUtil.FAIL_RESPONSE;
                        return new ReserveSeatResponse<>(result, message, party, null, null);
                    }
                }
            } else {
                message = "Invalid number format in the row or column, try again";
                result = "FAIL";
                return new ReserveSeatResponse<>(result, message, party, null, null);
            }
        } catch (TheaterException e) {
            e.getCause();
            message = PageUtil.THEATER_NOT_YET_OPEN;
            result = PageUtil.FAIL_RESPONSE;
            return new ReserveSeatResponse<>(result, message, party, null, null);
        } catch (InvalidPartyException e) {
            e.getCause();
            message = "The party is not valid, must not use reversed keyword, and must be letters";
            result = PageUtil.FAIL_RESPONSE;
            return new ReserveSeatResponse<>(result, message, party, null, null);
        } catch (ValidationException e) {
            e.getCause();
            message = PageUtil.NUMBER_NOT_IN_RANGE;
            result = PageUtil.FAIL_RESPONSE;
            return new ReserveSeatResponse<>(result, message, party, null, null);
        }
    }

    @Override
    @GET
    @Path("/view")
    @Produces(MediaType.APPLICATION_JSON)
    public ViewTheaterResponse<String> viewTheater() {
        if (theater == null) {
            return new ViewTheaterResponse<>(PageUtil.FAIL_RESPONSE, PageUtil.THEATER_NOT_YET_OPEN, "");
        } else {
            return new ViewTheaterResponse<>(PageUtil.OK_RESPONSE, "The theater's info", theater.toString());
        }
    }
}
