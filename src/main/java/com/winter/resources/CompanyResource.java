package com.winter.resources;

import com.winter.database.entity.Company;
import com.winter.request.UpdateCompanyRequest;
import com.winter.request.WorkerRequest;
import com.winter.request.RegisterCompanyRequest;
import com.winter.request.WorkerRequestType;
import com.winter.service.entity.CompanyService;
import org.eclipse.microprofile.metrics.MetricUnits;
import org.eclipse.microprofile.metrics.annotation.Counted;
import org.eclipse.microprofile.metrics.annotation.Timed;
import org.jboss.logging.Logger;
import org.jboss.resteasy.annotations.jaxrs.PathParam;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Optional;

@Path("/api/company")
public class CompanyResource {
    private static final Logger logger = Logger.getLogger(CompanyResource.class);

    @Inject
    CompanyService companyService;

    @Counted(name = "countGetCompanies", description = "Counts how many times the getCompanies method has been invoked")
    @Timed(name = "timeGetCompanies", description = "Times how long it takes to invoke getCompanies method", unit = MetricUnits.MILLISECONDS)
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCompanies() {
        List<Company> companies = companyService.listAll();
        return Response.ok(companies).build();
    }

    @Counted(name = "countGetCompanyByUserHash", description = "Counts how many times the countGetCompanyByUserHash method has been invoked")
    @Timed(name = "timeGetCompanyByUserHash", description = "Times how long it takes to invoke timeGetCompanyByUserHash method", unit = MetricUnits.MILLISECONDS)
    @GET
    @Path("/hash/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCompanyByUserHash(@PathParam("id") String hash) {
        try {
            return companyService.getByUserHash(hash)
                    .map(company -> Response.ok(company).build())
                    .orElseGet(() -> Response.status(Response.Status.NOT_FOUND).build());
        } catch (Exception e) {
            logger.error("", e);
            return Response.serverError().build();
        }
    }

    @Counted(name = "countGetCompanyById", description = "Counts how many times the getCompanyById method has been invoked")
    @Timed(name = "timeGetCompanyById", description = "Times how long it takes to invoke getCompanyById method", unit = MetricUnits.MILLISECONDS)
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCompanyById(@PathParam("id") Long id) {
        try {
            return companyService.findById(id)
                    .map(company -> Response.ok(company).build())
                    .orElseGet(() -> Response.status(Response.Status.NOT_FOUND).build());
        } catch (Exception e) {
            logger.error("", e);
            return Response.serverError().build();
        }
    }

    @Counted(name = "countRegisterCompany", description = "Counts how many times the countRegisterCompany method has been invoked")
    @Timed(name = "timeRegisterCompany", description = "Times how long it takes to invoke timeRegisterCompany method", unit = MetricUnits.MILLISECONDS)
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response registerCompany(RegisterCompanyRequest registerCompanyRequest) {
        Optional<Company> newCompany = companyService.registerNewCompany(registerCompanyRequest.getCompanyName(), registerCompanyRequest.getUserId());
        return newCompany.map(company -> Response.ok(company).build())
                .orElseGet(() -> Response.status(Response.Status.INTERNAL_SERVER_ERROR).build());
    }

    @Counted(name = "countAddWorkerToCompany", description = "Counts how many times the countAddWorkerToCompany method has been invoked")
    @Timed(name = "timeAddWorkerToCompany", description = "Times how long it takes to invoke timeAddWorkerToCompany method", unit = MetricUnits.MILLISECONDS)
    @POST
    @Path("/worker")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response workerRequest(WorkerRequest request) {
        boolean result = false;
        if (request.getType().equals(WorkerRequestType.HIRE.getType())) {
            result = companyService.addWorkerToCompany(request.getWorkerId(), request.getCompanyId(), request.getOffice());
        } else {
            result = companyService.removeWorkerFromCompany(request.getWorkerId(), request.getCompanyId(), request.getOffice());
        }

        if (result) {
            Optional<Company> company = companyService.findById(request.getCompanyId());
            return Response.ok(company.get()).build();
        } else {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    //yep it is not rest, but post already for registerCompany due to framework bug
    @Counted(name = "countUpdateCompany", description = "Counts how many times the updateCompany method has been invoked")
    @Timed(name = "timeUpdateCompany", description = "Times how long it takes to invoke updateCompany method", unit = MetricUnits.MILLISECONDS)
    @POST
    @Path("/update")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateCompany(UpdateCompanyRequest request) {
        return companyService.updateCompanyBalance(request.getCompanyId(), request.getMoney())
                .map(company -> Response.ok(company).build())
                .orElseGet(() -> Response.status(Response.Status.NOT_FOUND).build());
    }
}
