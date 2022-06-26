The main aim of this project is to understand the Software Development Life Cycle by actually developing a web application.

UPSTAC stads for Unified Platform for Syndromic Mapping, Testing, Analytics and Consultation.

With the advent of the internet era, we see that most of the daily activities can be performed online like purchasing clothes, groceries, booking flight tickets and so on.
Wouldn't it be great if we could also consult doctors online and doctors could provide with medical advise.

Moreover, wouldn't it be great if we could just make an appotment for a test and the tester from the diagostic lab will visit your home and take your blood sample.
This will prevent a lot of other hazards as well.

So there is a need for developing a unified platform which will closely connect the doctors, the patients and the testers.

The motivation behind developing this application was that, through survey results, we found out that people are not satisfied with the current mechanism of visiting 
laboratories, giving their blood samples for test and then separtely going to the doctor.
Instead of that, wouldn't it be great if the patient could request for a test from the comfort of their home and give his sample.
This is speacilly useful in times when deseases can be highly contageous.
Hence it is important for doctors to remotely monitor the patients and provide them the health advise.

Hence this applicaton was built where one can register and request for a health checkup or a lab test online. This app will act as a single unified platfomr for testers,
doctord and govt authorities. It will also help to keep proper track of the affected patients and prevent spreading of contagious deseases.

Prominent features of the application:
1.Requests for laboratory tests and health check ups can be made online.
2.A real-time notification of the test results will be received
3.The application can be utilised for updating health parameters of the patient.
4.Doctors can also use this application for faster diagonosis of patients.
5.Test result data of anonymous users can be utilised for research purposes.
6.Government can use the data to decide the lockdown/unlock protocols.


**To-Do Tasks:**

Implement the Lab Results and Consultation Feature in the UPSTAC API application. In order to implement this, follow the instructions given below:

With respect to the Tester, complete the code in 'LabRequestController' class to return the list of test requests assigned to current tester.
Specifically, need to implement the "getForTester()" method.
With respect to the Doctor, complete the code in ‘ConsultationController’ class to view all the test requests, the test requests assigned to current doctor,
assign consultations to themselves and update the doctor suggestions.
Specifically, need to implement the "getForConsultations()", "getForDoctor()", "assignForConsultation()" and the "updateConsultation()" methods.
