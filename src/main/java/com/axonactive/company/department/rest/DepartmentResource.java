package com.axonactive.company.department.rest;

import com.axonactive.company.base.exception.BadRequestException;
import com.axonactive.company.base.exception.EntityNotFoundException;
import com.axonactive.company.base.message.DeleteSuccessMessage;
import com.axonactive.company.department.dto.*;
import com.axonactive.company.department.service.DepartmentService;
import io.swagger.annotations.*;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("departments")
@Api(tags = "Departments API")
public class DepartmentResource {

    @Inject
    private DepartmentService departmentService;

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @ApiOperation(value = "Get all department list")
    @ApiResponses({
            @ApiResponse(
                    code = 200,
                    message = "Get all department list successfully",
                    response = DepartmentDTO.class, responseContainer = "List"
            ),
            @ApiResponse(
                    code = 500,
                    message = "Server errors"
            )
    })
    public Response findAll() {
        List<DepartmentDTO> departmentDTOS = this.departmentService.findAll();
        return Response.ok().entity(departmentDTOS).build();
    }

    @GET
    @Path("{id}")
    @Produces({ MediaType.APPLICATION_JSON })
    @ApiOperation(value = "Get department by id")
    @ApiResponses({
            @ApiResponse(
                    code = 200,
                    message = "Get department successfully",
                    response = DepartmentDTO.class
            ),
            @ApiResponse(
                    code = 500,
                    message = "Server errors"
            )
    })
    public Response findById(@PathParam("id") Long id) throws EntityNotFoundException {
        DepartmentDTO departmentDTO = this.departmentService.findById(id);
        return Response.ok().entity(departmentDTO).build();
    }

    @POST
    @Path("add")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    @ApiOperation(value = "Create new department")
    @ApiModelProperty
    @ApiResponses({
            @ApiResponse(
                    code = 200,
                    message = "Create department successfully",
                    response = DepartmentDTO.class
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
    public Response add(@Valid CreateDepartmentRequestDTO createDepartmentRequestDTO) throws BadRequestException {
        DepartmentDTO departmentDTO = this.departmentService.add(createDepartmentRequestDTO);
        return Response.ok().entity(departmentDTO).build();
    }

    @PUT
    @Path("update")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    @ApiOperation(value = "Update department ")
    @ApiModelProperty
    @ApiResponses({
            @ApiResponse(
                    code = 200,
                    message = "Update department successfully",
                    response = DepartmentDTO.class
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
    public Response update(@Valid UpdateDepartmentRequestDTO updateDepartmentRequestDTO) throws EntityNotFoundException, BadRequestException {
        DepartmentDTO departmentDTO = this.departmentService.update(updateDepartmentRequestDTO);
        return Response.ok().entity(departmentDTO).build();
    }

    @DELETE
    @Path("delete")
    @ApiOperation(value = "Delete softly department")
    @ApiModelProperty
    @ApiResponses({
            @ApiResponse(
                    code = 200,
                    message = "Delete department successfully",
                    response = DeleteSuccessMessage.class
            ),
            @ApiResponse(
                    code = 400,
                    message = "Request sent to the server is invalid"
            ),
            @ApiResponse(
                    code = 500,
                    message = "Request cannot be fulfilled through browser due to server-side problems"
            )
    })
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public Response delete(@Valid DeleteDepartmentRequestDTO deleteDepartmentRequestDTO) throws EntityNotFoundException, BadRequestException {
        DeleteSuccessMessage result = this.departmentService.delete(deleteDepartmentRequestDTO);
        return Response.ok().entity(result).build();
    }
}
