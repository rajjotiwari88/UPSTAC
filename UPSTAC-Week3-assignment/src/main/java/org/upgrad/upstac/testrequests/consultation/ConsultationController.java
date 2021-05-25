package org.upgrad.upstac.testrequests.consultation;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.upgrad.upstac.config.security.UserLoggedInService;
import org.upgrad.upstac.exception.AppException;
import org.upgrad.upstac.testrequests.RequestStatus;
import org.upgrad.upstac.testrequests.TestRequest;
import org.upgrad.upstac.testrequests.TestRequestQueryService;
import org.upgrad.upstac.testrequests.TestRequestUpdateService;
import org.upgrad.upstac.testrequests.flow.TestRequestFlowService;
import org.upgrad.upstac.users.User;

import javax.validation.ConstraintViolationException;
import java.util.List;

import static org.upgrad.upstac.exception.UpgradResponseStatusException.asBadRequest;
import static org.upgrad.upstac.exception.UpgradResponseStatusException.asConstraintViolation;


@RestController
@RequestMapping("/api/consultations")
public class ConsultationController {

    Logger log = LoggerFactory.getLogger(ConsultationController.class);


    @Autowired
    private TestRequestUpdateService testRequestUpdateService;

    @Autowired
    private TestRequestQueryService testRequestQueryService;

    @Autowired
    TestRequestFlowService  testRequestFlowService;

    @Autowired
    private UserLoggedInService userLoggedInService;




    //returns the list of requested consultations after successfully completing the lab test
    //through the 'Consultations Requested' option in the application.

    @GetMapping("/in-queue")
    @PreAuthorize("hasAnyRole('DOCTOR')")
    public List<TestRequest> getForConsultations()  {

        //Using the findBy() method from testRequestQueryService class
        //to get the list of test requests having status as 'LAB_TEST_COMPLETED'.

        return testRequestQueryService.findBy(RequestStatus.LAB_TEST_COMPLETED);

    }



    //returns the list of all the test/consultation requests assigned to current doctor
    //through the 'Request History' option in the application.

    @GetMapping
    @PreAuthorize("hasAnyRole('DOCTOR')")
    public List<TestRequest> getForDoctor()  {

        //Creating an object of User class and storing the current logged in user
        //Using the findByDoctor() method from testRequestQueryService class to return
        //the list of test requests assigned to current doctor(making use of the above created User object)

        User loggedInDoctor= userLoggedInService.getLoggedInUser();

        return testRequestQueryService.findByDoctor(loggedInDoctor);

    }



    //enables the logged in doctor to assign the requested test to himself
    //through the 'Assign Me' option in the application.

    @PreAuthorize("hasAnyRole('DOCTOR')")
    @PutMapping("/assign/{id}")
    public TestRequest assignForConsultation(@PathVariable Long id) {

        //Creating an object of User class and storing the current logged in user
        //Using the assignForConsultation() method of testRequestUpdateService
        //to assign the particular id to the current user.

        try {

            User loggedInDoctor=userLoggedInService.getLoggedInUser();

            return testRequestUpdateService.assignForConsultation(id,loggedInDoctor);

        }catch (AppException e) {

            throw asBadRequest(e.getMessage());

        }

    }



    //enables the logged in doctor to update the result of the current test request id with doctor suggestion and comments
    //through the 'Update Consultation' option, in the application.

    @PreAuthorize("hasAnyRole('DOCTOR')")
    @PutMapping("/update/{id}")
    public TestRequest updateConsultation(@PathVariable Long id,@RequestBody CreateConsultationRequest testResult) {

        // Creating an object of the User class to get the logged in user
        // Making use of updateConsultation() method from testRequestUpdateService class
        //to update the current test request id with the testResult details by the current user(object created).

        try {

            User loggedInDoctor=userLoggedInService.getLoggedInUser();

            return testRequestUpdateService.updateConsultation(id,testResult,loggedInDoctor);

        }catch (ConstraintViolationException e) {

            throw asConstraintViolation(e);

        }catch (AppException e) {

            throw asBadRequest(e.getMessage());

        }

    }

}
