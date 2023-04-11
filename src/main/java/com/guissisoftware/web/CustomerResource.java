package com.guissisoftware.web;

import com.guissisoftware.exception.ServiceException;
import com.guissisoftware.models.dto.Customer;
import com.guissisoftware.service.CustomerService;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.Objects;

@Path("/customers")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Customers", description = "All Customers routes")
@Slf4j
public class CustomerResource {

    private final CustomerService customerService;

    @Inject
    public CustomerResource(CustomerService customerService){
        this.customerService = customerService;
    }


    @GET
    @APIResponse(
            responseCode = "200",
            description = "Get all Customers",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON,
                    schema = @Schema(type = SchemaType.ARRAY, implementation = Customer.class)
            )
    )
    @APIResponse(
            responseCode = "401",
            description = "You are not authorised to access this endpoint",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON
            )
    )
    public Response getAll(){
        return Response.ok(customerService.findAll()).build();
    }

    @GET
    @Path("/{id}")
    @APIResponse(
            responseCode = "200",
            description = "Get customer by Id",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON,
                    schema = @Schema(type = SchemaType.OBJECT, implementation = Customer.class)
            )
    )
    @APIResponse(
            responseCode = "404",
            description = "No customer exist with such id",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON
            )
    )
    public Response getById(@Parameter(name = "id", required = true) @PathParam("id") Long id){
        return this.customerService.findById(id).map(
                customer -> Response.ok(customer).build()).orElse(
                        Response.status(Response.Status.NOT_FOUND).build()
        );
    }


    @POST
    @APIResponse(
            responseCode = "201",
            description = "Customer created",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON,
                    schema = @Schema(type = SchemaType.OBJECT, implementation = Customer.class)
            )
    )
    @APIResponse(
            responseCode = "400",
            description = "Invalid customer",
            content = @Content(mediaType = MediaType.APPLICATION_JSON)
    )
    @APIResponse(
            responseCode = "400",
            description = "Customer already exist with this id",
            content = @Content(mediaType = MediaType.APPLICATION_JSON)
    )
    public Response postUser(@NotNull @Valid Customer customer, @Context UriInfo uriInfo){
        this.customerService.save(customer);
        URI uri = uriInfo.getAbsolutePathBuilder().path(Long.toString(customer.getId())).build();
        return Response.created(uri).entity(customer).build();
    }

    @PUT
    @Path("/{id}")
    @APIResponse(
            responseCode = "204",
            description = "Customer updated",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON,
                    schema = @Schema(type = SchemaType.OBJECT, implementation = Customer.class)
            )
    )
    @APIResponse(
            responseCode = "400",
            description = "Path variable id does not match with customer id",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON
            )
    )
    @APIResponse(
            responseCode = "404",
            description = "No customer found with such an id",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON
            )
    )
    public Response putUser(@Parameter(name = "id", required = true) @PathParam("id") Long id, @NotNull @Valid Customer customer){
        if(!Objects.equals(id, customer.getId())){
            throw new ServiceException("Path variable id does not match with customer id");
        }
        this.customerService.update(customer);
        return Response.status(Response.Status.NO_CONTENT).build();
    }


}
