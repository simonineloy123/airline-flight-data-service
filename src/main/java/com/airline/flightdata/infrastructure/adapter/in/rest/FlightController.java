package com.airline.flightdata.infrastructure.adapter.in.rest;

import com.airline.flightdata.domain.model.FlightStatus;
import com.airline.flightdata.domain.port.in.*;
import com.airline.flightdata.infrastructure.adapter.in.rest.dto.*;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import java.util.List;
import java.util.UUID;

@Path("/api/v1/flights")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Flights", description = "Gestión de vuelos")
public class FlightController {

    private final CreateFlightUseCase      createFlightUseCase;
    private final GetFlightUseCase         getFlightUseCase;
    private final UpdateFlightStatusUseCase updateFlightStatusUseCase;

    public FlightController(
        CreateFlightUseCase createFlightUseCase,
        GetFlightUseCase getFlightUseCase,
        UpdateFlightStatusUseCase updateFlightStatusUseCase
    ) {
        this.createFlightUseCase       = createFlightUseCase;
        this.getFlightUseCase          = getFlightUseCase;
        this.updateFlightStatusUseCase = updateFlightStatusUseCase;
    }

    @POST
    @Operation(summary = "Crear un nuevo vuelo")
    public Response createFlight(@Valid CreateFlightRequest request) {
        var command = new CreateFlightCommand(
            request.flightNumber(),
            request.origin().toUpperCase(),
            request.destination().toUpperCase(),
            request.aircraftId(),
            request.scheduledDep(),
            request.scheduledArr(),
            request.passengers(),
            request.gate()
        );
        var flight = createFlightUseCase.execute(command);
        return Response.status(Response.Status.CREATED)
            .entity(FlightResponse.from(flight))
            .build();
    }

    @GET
    @Operation(summary = "Listar todos los vuelos")
    public List<FlightResponse> getAllFlights(
        @QueryParam("status") FlightStatus status,
        @QueryParam("origin") String origin,
        @QueryParam("destination") String destination
    ) {
        if (status != null) {
            return getFlightUseCase.findByStatus(status)
                .stream().map(FlightResponse::from).toList();
        }
        if (origin != null) {
            return getFlightUseCase.findByOrigin(origin.toUpperCase())
                .stream().map(FlightResponse::from).toList();
        }
        if (destination != null) {
            return getFlightUseCase.findByDestination(destination.toUpperCase())
                .stream().map(FlightResponse::from).toList();
        }
        return getFlightUseCase.findAll()
            .stream().map(FlightResponse::from).toList();
    }

    @GET
    @Path("/{id}")
    @Operation(summary = "Obtener vuelo por ID")
    public Response getFlightById(@PathParam("id") UUID id) {
        return getFlightUseCase.findById(id)
            .map(flight -> Response.ok(FlightResponse.from(flight)).build())
            .orElse(Response.status(Response.Status.NOT_FOUND).build());
    }

    @GET
    @Path("/number/{flightNumber}")
    @Operation(summary = "Obtener vuelo por número")
    public Response getFlightByNumber(@PathParam("flightNumber") String flightNumber) {
        return getFlightUseCase.findByFlightNumber(flightNumber)
            .map(flight -> Response.ok(FlightResponse.from(flight)).build())
            .orElse(Response.status(Response.Status.NOT_FOUND).build());
    }

    @PATCH
    @Path("/{id}/status")
    @Operation(summary = "Actualizar estado de un vuelo")
    public Response updateFlightStatus(
        @PathParam("id") UUID id,
        @Valid UpdateFlightStatusRequest request
    ) {
        var flight = updateFlightStatusUseCase.execute(id, request.status(), request.delayMinutes());
        return Response.ok(FlightResponse.from(flight)).build();
    }
}
