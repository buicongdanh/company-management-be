package com.axonactive.company.employee.rest;

import com.axonactive.company.base.exception.EntityNotFoundException;
import com.axonactive.company.base.message.DeleteSuccessMessage;
import com.axonactive.company.employee.dto.*;
import com.axonactive.company.employee.service.EmployeeService;
import io.swagger.annotations.*;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.*;


@Path("employees")
@Api(tags = "Employees API")
public class EmployeeResource {

    @Inject
    private EmployeeService employeeService;

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_JSON})
    @ApiOperation(value = "Get employee by id")
    @ApiResponses({
            @ApiResponse(
                    code = 200,
                    message = "Get employee successfully",
                    response = EmployeeDTO.class, responseContainer = "List"
            ),
            @ApiResponse(
                    code = 500,
                    message = "Server side errors"
            )
    })
    public Response findEmployeeById(@PathParam("id") Long id) throws EntityNotFoundException {
        return Response.ok().entity(this.employeeService.findEmployeeById(id)).build();
    }

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @ApiOperation(value = "Get all employee")
    @ApiResponses({
            @ApiResponse(
                    code = 200,
                    message = "Get employee successfully",
                    response = EmployeeDTO.class, responseContainer = "List"
            ),
            @ApiResponse(
                    code = 500,
                    message = "Server side errors"
            )
    })
    public Response getAllEmployees() {
        return Response.ok().entity(this.employeeService.getAllEmployee()).build();
    }

    @GET
    @Path("active")
    @Produces({MediaType.APPLICATION_JSON})
    @ApiOperation(value = "Get all employee")
    @ApiResponses({
            @ApiResponse(
                    code = 200,
                    message = "Get employee successfully",
                    response = EmployeeDTO.class, responseContainer = "List"
            ),
            @ApiResponse(
                    code = 500,
                    message = "Server side errors"
            )
    })
    public Response getActiveEmployees() {
        return Response.ok().entity(this.employeeService.getActiveEmployees()).build();
    }

    @POST
    @Path("add")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    @ApiOperation(value = "Create new employee")
    @ApiModelProperty
    @ApiResponses({
            @ApiResponse(
                    code = 200,
                    message = "Create employee successfully",
                    response = EmployeeDTO.class
            ),
            @ApiResponse(
                    code = 400,
                    message = "Invalid request"
            ),
            @ApiResponse(
                    code = 500,
                    message = "Server side errors"
            )
    })
    public Response add(@Valid CreateEmployeeRequestDTO reqDTO) throws EntityNotFoundException {
        return Response.ok(this.employeeService.add(reqDTO)).build();
    }

    @PATCH
    @Path("update/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    @ApiOperation(value = "Update employee")
    @ApiModelProperty
    @ApiResponses({
            @ApiResponse(
                    code = 200,
                    message = "Update employee successfully",
                    response = EmployeeDTO.class
            ),
            @ApiResponse(
                    code = 400,
                    message = "Invalid request"
            ),
            @ApiResponse(
                    code = 500,
                    message = "Server side errors"
            )
    })
    public Response update(@Valid UpdateEmployeeDTO updateEmployeeDTO, @PathParam("id") long employeeID) throws EntityNotFoundException {
        return Response.ok().entity(this.employeeService.update(updateEmployeeDTO, employeeID)).build();
    }

    @DELETE
    @Path("delete/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    @ApiOperation(value = "Delete employee")
    @ApiModelProperty
    @ApiResponses({
            @ApiResponse(
                    code = 200,
                    message = "Delete employee successfully",
                    response = DeleteSuccessMessage.class
            ),
            @ApiResponse(
                    code = 400,
                    message = "Invalid request"
            ),
            @ApiResponse(
                    code = 500,
                    message = "Server errors"
            )
    })
    public Response delete(@PathParam("id") long employeeID) throws EntityNotFoundException {
        return Response.ok().entity(this.employeeService.delete(employeeID)).build();
    }

    @GET
    @Path("/department/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    @ApiOperation(value = "Get all employee from a department")
    @ApiModelProperty
    @ApiResponses({
            @ApiResponse(
                    code = 200,
                    message = "Delete employee successfully",
                    response = DeleteSuccessMessage.class
            ),
            @ApiResponse(
                    code = 400,
                    message = "Invalid request"
            ),
            @ApiResponse(
                    code = 500,
                    message = "Server errors"
            )
    })
    public Response getAllEmployeesByDepartment(@PathParam("id") long employeeID) throws EntityNotFoundException {
        return Response.ok().entity(this.employeeService.getAllEmployeesByDepartment(employeeID)).build();
    }

}
