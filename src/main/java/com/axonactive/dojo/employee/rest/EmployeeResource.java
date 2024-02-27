package com.axonactive.dojo.employee.rest;

import com.axonactive.dojo.base.exception.EntityNotFoundException;
import com.axonactive.dojo.base.message.DeleteSuccessMessage;
import com.axonactive.dojo.employee.dto.*;
import com.axonactive.dojo.employee.service.EmployeeService;
import io.swagger.annotations.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;
import javax.json.JsonObject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.*;


@Path("employees")
@Api(tags = "Employees API")
public class EmployeeResource {

    @Inject
    private EmployeeService employeeService;

    @GET
    @Path("getEmployeeFromDepartment/{departmentId}")
    @Produces({ MediaType.APPLICATION_JSON })
    @ApiOperation(value = "Get all employee list from a department")
    @ApiResponses({
            @ApiResponse(
                    code = 200,
                    message = "Get all employee list successfully",
                    response = EmployeeListResponseDTO.class, responseContainer = "List"
            ),
            @ApiResponse(
                    code = 500,
                    message = "Server side errors"
            )
    })
    public Response getEmployeesFromDepartment(@PathParam("departmentId") Long departmentId) throws EntityNotFoundException {
        EmployeeListResponseDTO employeeListResponseDTO = this.employeeService.findEmployeesByDepartment(departmentId);
        return Response.ok().entity(employeeListResponseDTO).build();
    }

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
        EmployeeDTO employeeDTO = this.employeeService.findEmployeeById(id);
        return Response.ok().entity(employeeDTO).build();
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
    public Response getAllEmployees() throws EntityNotFoundException {
        EmployeeListResponseDTO employeeDTO = this.employeeService.findAllEmployee();
        return Response.ok().entity(employeeDTO).build();
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
        EmployeeDTO employeeDTO = this.employeeService.add(reqDTO);
        return Response.ok(employeeDTO).build();
    }

    @PUT
    @Path("update")
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
    public Response update(@Valid UpdateEmployeeRequestDTO reqDTO) throws EntityNotFoundException {
        EmployeeDTO employeeDTO = this.employeeService.update(reqDTO);
        return Response.ok().entity(employeeDTO).build();
    }

    @DELETE
    @Path("delete")
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
    public Response delete(@Valid DeleteEmployeeRequestDTO reqDTO) throws EntityNotFoundException {
        DeleteSuccessMessage result = this.employeeService.delete(reqDTO);
        return Response.ok().entity(result).build();
    }

}
