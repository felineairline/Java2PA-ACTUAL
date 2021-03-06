::Title and Purpose of Application::
C195 Software II Performance Assessment
This application is meant for use in managing appointment schedules and customer records.
This application also features a reports page for specific business use reports.

::Author Information::
Author: Ashley Barone
Contact Info: abaro20@wgu.edu
Application Version: 1.0.0.1
Date: January 18, 2022

::Version Information::
IntelliJ IDEA 2021.3 (Community Edition)
Build #IC-213.5744.223, built on November 27, 2021
Runtime version: 11.0.13+7-b1751.19 amd64
VM: OpenJDK 64-Bit Server VM by JetBrains s.r.o.
Windows 10 10.0
GC: G1 Young Generation, G1 Old Generation
Memory: 768M
Cores: 4

Kotlin: 213-1.5.10-release-949-IJ5744.223

JDK version: JDK-17.0.1

JavaFX version: JavaFX-SDK-17.0.1

JDBC version: MySQL-Connector-Java-8.0.26

::Running the Program::
To run this program, first, open the folder in IntelliJ. Next, you have to update your IDE settings to the program.
To do this, you will access File>Project Structure to create a PATH_TO_FX leading to your JavaFX SDK library.
Then, you will access File>Settings to add your JavaFX SDK library and MySQL Connector library.
Finally you will create a new Application configuration with the following format:
 - Select main.Main as your Main class.
 - Click Modify Options and enable VM Option. In that field, you will paste this:
     ---module-path ${PATH_TO_FX} --add-modules javafx.fxml,javafx.controls,javafx.graphics
 - Next, you will ensure you have the correct JavaFX SDK selected in the top combo box.
 - Finally, you can name your configuration and click Apply then Okay.
If you need to change the Database credentials and information, you can do so in src/dbaccess/JDBC.java to ensure proper connection.

::Additional Report::
In addition to the two reports required for this application, I have included a third report to calculate the number of customers in each distinct division.
This report will allow the business to better serve their customers in each division by adjusting to any increase or decrease in customer counts.
