package com.airline.flightdata.infrastructure.adapter.in.rest;

import com.airline.flightdata.domain.port.in.GetAirportUseCase;
import com.airline.flightdata.infrastructure.adapter.in.rest.dto.AirportResponse;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import java.util.List;

@Path("/api/v1/airports")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Airports", description = "Consulta de aeropuertos")
public class AirportController {

    private final GetAirportUseCase getAirportUseCase;

    public AirportController(GetAirportUseCase getAirportUseCase) {
        this.getAirportUseCase = getAirportUseCase;
    }

    @GET
    @Operation(summary = "Listar todos los aeropuertos")
    public List<AirportResponse> getAllAirports() {
        return getAirportUseCase.findAll()
            .stream().map(AirportResponse::from).toList();
    }

    @GET
    @Path("/{iataCode}")
    @Operation(summary = "Obtener aeropuerto por código IATA")
    public Response getAirportById(@PathParam("iataCode") String iataCode) {
        return getAirportUseCase.findById(iataCode.toUpperCase())
            .map(airport -> Response.ok(AirportResponse.from(airport)).build())
            .orElse(Response.status(Response.Status.NOT_FOUND).build());
    }
}
