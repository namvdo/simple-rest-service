package com.namdv.jaxrs.service;

import exception.InvalidPartyException;
import exception.TheaterException;
import exception.ValidationException;
import model.Coordinate;
import model.Theater;
import response.BaseResponse;
import response.CheckSeatResponse;
import response.ReserveSeatResponse;
import response.ViewTheaterResponse;

import java.util.List;
import java.util.Set;

/**
 * The interface Theater service.
 */
public interface TheaterService {

    /**
     * Gets available seat sets.
     *
     * @param requiredSeats the required seats
     * @return the available seat sets together
     */
    CheckSeatResponse getAvailableSeatSets(String requiredSeats);

    /**
     * Reserve a seat
     *
     * @param party  the party which the client signs as
     * @param row the row number, start from 0
     * @param column the column number, start from 0
     * @return ReserveSeatResponse with Coordinate parameterized type Coordinate signifies
     * the position after reserving.
     * @throws TheaterException when there is no theater creation just yet.
     * @throws InvalidPartyException when the party provided is not in the correct format.
     * @throws ValidationException when parsing the numbers.
     */
    ReserveSeatResponse<Coordinate> reserveSeat(String party, String row, String column) throws TheaterException, InvalidPartyException, ValidationException;

    /**
     * Represent the view of the theater, 0 indicate free position
     * letters indicate the position has been taken by some party.
     * @return the ViewTheaterResponse with the parameterized String displays the theater.
     */
    ViewTheaterResponse<String> viewTheater();
}
