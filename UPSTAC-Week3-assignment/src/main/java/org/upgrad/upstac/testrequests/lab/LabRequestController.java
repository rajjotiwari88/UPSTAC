package org.upgrad.upstac.testrequests.lab;


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
@RequestMapping("/api/labrequests")
public class LabRequestController {

    Logger log = LoggerFactory.getLogger(LabRequestController.class);


    @Autowired
    private TestRequestUpdateService testRequestUpdateService;

    @Autowired
    private TestRequestQueryService testRequestQueryService;

    @Autowired
    private TestRequestFlowService testRequestFlowService;

    @Autowired
    private UserLoggedInService userLoggedInService;




    //returns the list of new test requests being requested
    //through the 'Test Requests' option in the application.

    @GetMapping("/to-be-tested")
    @PreAuthorize("hasAnyRole('TESTER')")
    public List<TestRequest> getForTests()  {

        //Using the findBy() method from testRequestQueryService class to return
        //the list of test requests, being requested to current tester, with request status as 'INITIATED'.

        return testRequestQueryService.findBy(RequestStatus.INITIATED);

    }



    //returns the list of all the test requests assigned to current tester
    //through the 'Request History' option in the application.

    @GetMapping
    @PreAuthorize("hasAnyRole('TESTER')")
    public List<TestRequest> getForTester()  {

        //Creating an object of User class and storing the current logged in user
        //Using the findByTester() method from testRequestQueryService class to return
        //the list of test requests assigned to current tester by making use of the above created User object.

        User loggedInTester=userLoggedInService.getLoggedInUser();

        return testRequestQueryService.findByTester(loggedInTester);

    }



    //enables the logged in tester to assign the requested test to himself
    //through the 'Assign Me' option in the application.

    @PreAuthorize("hasAnyRole('TESTER')")
    @PutMapping("/assign/{id}")
    public TestRequest assignForLabTest(@PathVariable Long id) {

        //Creating an object of User class and storing the current logged in user
        //Using the assignForLab() method of testRequestUpdateService to assign the particular id to the current user.

        User tester =userLoggedInService.getLoggedInUser();

        return   testRequestUpdateService.assignForLabTest(id,tester);

    }



    //enables the logged in tester to update the lab results
    //through the 'Update Lab Result' option, in the application, after successfully conducting the lab test.

    @PreAuthorize("hasAnyRole('TESTER')")
    @PutMapping("/update/{id}")
    public TestRequest updateLabTest(@PathVariable Long id,@RequestBody CreateLabResult createLabResult) {

        // Creating an object of the User class to get the logged in user
        // Making use of updateLabTest() method from testRequestUpdateService class
        //to update the current test request id with the lab result details by the current user(object created).

        try {

            User tester=userLoggedInService.getLoggedInUser();

            return testRequestUpdateService.updateLabTest(id,createLabResult,tester);

        }catch (ConstraintViolationException e) {

            throw asConstraintViolation(e);

        }catch (AppException e) {

            throw asBadRequest(e.getMessage());

        }

    }

}
