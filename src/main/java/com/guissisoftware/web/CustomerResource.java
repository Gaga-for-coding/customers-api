package com.guissisoftware.web;

import com.guissisoftware.models.dto.Customer;
import com.guissisoftware.service.CustomerService;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/customers")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Customers", description = "All Customers' routes")
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
    public Response get(){
        return Response.ok(customerService.findAll()).build();
    }



}
