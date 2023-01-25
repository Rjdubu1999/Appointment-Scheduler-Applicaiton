Title: C195 Appointment Scheduler Application
Application Purpose: Create a functional GUI which can be used to store appointment
and customer information that is stored and retrieved from a MYSQL database.

Author: Ryan Wilkinson
Contact: rwilk75@wgu.edu
Student Application version: Version 1.0
MySQL Connection Driver: mysql-connector-java--8.0.25
JavaFX Version: JavaFX-SDK-17.0.1

Intellij:IntelliJ IDEA 2021.1.3 (Community Edition)
         Build #IC-211.7628.21, built on June 30, 2021
         Runtime version: 11.0.11+9-b1341.60 amd64
         VM: OpenJDK 64-Bit Server VM by JetBrains s.r.o.
         Windows 10 10.0
         GC: G1 Young Generation, G1 Old Generation
         Memory: 768M
         Cores: 4

         Kotlin: 211-1.4.32-release-IJ7628.19

Program User Guide:

- Launch application.
- Login form will appear and the user will need to use the username admin and password admin to
gain access to the application
-After a successful login, the user can choose from three selections: appointments, customers, and reports.
-If the user selects the appointments button they will be directed to a screen where they will be able to add, delete
and modify appointments.
-If the user selects the customers button they will be directed to a screen where they will be able to add, delete
 and modify customers.
 -If the user selects the Reports button they will be directed to a screen with the reports about
 this program that has tables that show information about the program and the data inside the tables.

 Description of third report of choice:
 The report I did was on the first level divisions and the amount of time they occur.
 This was accomplished with a query and then placed into two different table columns in the report controller.


 Customer Screen Operation:
 -To add a customer add the information to the fields available then click the add button to
 put the new customers information into the table and to the database
 -to modify a customer click on a customer in the table to highlight them, then click the modify customer button which
 will take the data and load it into the fields below the table. Then the user will update the customers information.
 -to save the modified customer information after updating, click save. the new data will be loaded into the table and
 database.


 Appointment screen operation:
 -Open the appointment screen.
 -Add an appointment by clicking add appointment. The screen will be opened and the user will fill in the fields with
 all the information about the appointment.
 -Click save and then the appointment will be loaded into the table on the main appointment screen.
 -To modify and update an appointment click on an appointment to load all of the information into the fields below the
 main appointment table. update fields as pleased.
 -To save click the save button after modifying the appointment fields.
 -to delete an appointment, highlight an appointment then click delete. Press ok to accept the confirmation window