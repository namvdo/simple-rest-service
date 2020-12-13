package com.namdv.jaxrs.service;

import model.Theater;
import response.BaseResponse;
import response.SetupResponse;

/**
 * The interface Set up theater.
 */
public interface SetUpTheater {
    /**
     * Sets up the theater with given row, column and min distance.
     *
     * @param row - entered row
     * @param column - entered column
     * @param minDistance - entered min distance between different parties.
     * @return Theater
     */
    SetupResponse<Theater> setUpTheater(String row, String column, String minDistance);
}
