package com.airline.flightdata.infrastructure.adapter.in.rest;

import com.airline.flightdata.domain.exception.AirportNotFoundException;
import com.airline.flightdata.domain.exception.DuplicateFlightException;
import com.airline.flightdata.domain.exception.FlightNotFoundException;
import com.airline.flightdata.domain.exception.InvalidFlightStateException;
import com.airline.flightdata.infrastructure.adapter.in.rest.dto.ErrorResponse;
import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import org.jboss.logging.Logger;

@Provider
public class GlobalExceptionHandler implements ExceptionMapper<Exception> {

    private static final Logger LOG = Logger.getLogger(GlobalExceptionHandler.class);

    @Override
    public Response toResponse(Exception exception) {
        return switch (exception) {
            case FlightNotFoundException e -> Response
                .status(Response.Status.NOT_FOUND)
                .entity(ErrorResponse.of(404, "Not Found", e.getMessage()))
                .build();

            case AirportNotFoundException e -> Response
                .status(Response.Status.NOT_FOUND)
                .entity(ErrorResponse.of(404, "Not Found", e.getMessage()))
                .build();

            case DuplicateFlightException e -> Response
                .status(Response.Status.CONFLICT)
                .entity(ErrorResponse.of(409, "Conflict", e.getMessage()))
                .build();

            case InvalidFlightStateException e -> Response
                .status(422)
                .entity(ErrorResponse.of(422, "Unprocessable Entity", e.getMessage()))
                .build();

            case ConstraintViolationException e -> Response
                .status(Response.Status.BAD_REQUEST)
                .entity(ErrorResponse.of(400, "Bad Request", e.getMessage()))
                .build();

            case NotFoundException e -> Response
                .status(Response.Status.NOT_FOUND)
                .entity(ErrorResponse.of(404, "Not Found", "Recurso no encontrado"))
                .build();

            default -> {
                LOG.errorf("Error no manejado: %s", exception.getMessage());
                yield Response
                    .status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(ErrorResponse.of(500, "Internal Server Error", "Error interno del servidor"))
                    .build();
            }
        };
    }
}
