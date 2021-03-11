# The Stack Machine Application

  Table of contents

  * [**About the application**](#about-the-application)
  * [**Version history**](#version-history)
  * [**Assumptions**](#assumptions)
  * [**Operation manual**](#operation-manual)
    + [**Pre-requsites**](#pre-requsites)
    + [**Start up application**](#start-up-application)
    + [**Command guide**](#command-guide)
  * [**Architecture**](#architecture)
    + [**Data storage**](#data-storage)
    + [**Validation**](#validation)
    + [**Logging**](#logging)
  * [**Technology stack**](#technology-stack)

## **About the application**

The Stack Machine Application allows user to manupulate a stack using commands with Polish postfix notation.

<br/>
<br/>
<img src="https://bn1301files.storage.live.com/y4m5sfc3cTVrdc2zgBi_JTLy5vYrK5RYVq3Mqs4-ySrp2lBXo1OrEOUJkZWlKgRtUXjmHi2NHYo6JVzVRDx-t2b7RErXx-aPb6JSLj_u3fS1OMqoXlXpG-t5R440btgLNE0rDEMTITRyvvK0nyGEV_Jrufyomyjpj8llhQRghRxTgfGDOyzIDRXuw7QnLZ_5y4z?width=838&height=762&cropmode=none" alt="Welcome_Page">

## **Version history**

  | Editor | Version | Date |Description|
  | --- | --- | --- | --- |
  | Patrick Cheung | 1.0.0| 11 Mar 2021 |Initial release|

## **Assumptions**

The following assumptions are being made for the application:
* Application is a console application
* Only 1 user will use the application at a time
* There is only 1 stack in the system
* The state of the stack will not persist, and will be lost when system shuts down
* Input is case-insensitve

## **Operation manual**

  ### Pre-requsites

  * Maven
  * JDK11

  ### **Start up application**

  The applcation can be started directly by running the following command at the project root folder

    mvn spring-boot:run

  If you would like to create a JAR file from the source, run the following command

    mvn clean package

  A JAR file will be created in the "target" folder and can be executed by the following command

    java -jar stack-machine-<artifact version>.jar

  ### **Command guide**
  
  #### **PUSH command**
  
  Pushes the numeric value <xyz> to the top of the stack.

  Format:
  
    PUSH <xyz> or <xyz>

  <img src="https://bn1301files.storage.live.com/y4mdC4ZibiwgRLlWWpxV4tALpUJ2Vngb0fNBuSBUBmSwMerVYJrYwaT4ZFu6GYCoNi-usdZWXHne70UAIsQ1AGoqn1vjgnVlsr6d5igD5x6O-ZpU1M9aC8KlPHn3hU4OSK2n528zLEC1QcXA5AzmVGvlE74-ZEzZC3bqEZ7VJp2N9zAGMZBwP7BNuE1O1a2qYrd?width=594&height=166&cropmode=none" alt="Push_Command">  

  #### **POP command**
  
  Removes the top element from the stack.

  If there are no more elements in the stack, system will print "There are now no elements in the stack".

  Format:
  
    POP

  <img src="https://bn1301files.storage.live.com/y4mkXeK5r56pO9d01k7tMMfP_SESJhFmKJB2RdsGCEe_d8RHnmdin4YqjRMck-3wyDNU9z_C1bTaxaX0HpFj9gYE2KWqmtGfZt2qM8S3kYPvAcYURYPhxd_9fEMq-AlliJePWCMZr5MQ8tJDAdAoS849SKHCvQwkt2DEEXomuH2svOiHVRheZ9rNNP12oFrSPKg?width=872&height=302&cropmode=none" alt="Pop_Command">  

  #### **CLEAR command**
  
  Removes all elements from the stack.

  After executing the command, system will print "There are now no elements in the stack".

  Format:
  
    CLEAR

  <img src="https://bn1301files.storage.live.com/y4mcrtqleGMVhPvno8Mn6ddyyPmtlmikjLsef28KjZ8mdM6XXb7R4xhcWD883JBFNMB2VfbUv04DpAL0L0QlZT_EF2I5tpOVekZdr8G583ESe0Vabz_dyaIYxgV-r50315LuBtuxpaCuJ5NoIP-BP98_-g20NBulSwVmNgVf2rg3Jed_njWEicIS57o0fbauPUW?width=872&height=308&cropmode=none" alt="Clear_Command">  

  #### **ADD command**
  
  Adds the top 2 elements on the stack and pushes the result back to the stack.

  Format:
  
    ADD

  <img src="https://bn1301files.storage.live.com/y4m6rvb2Ru-dKuWXbgpQ3IZqao3mXGant7QbOC_eZoGf5igRml6a_Ex6zq6lTXs8AZlGv6GWN_BxrK1oFW2NyliKWIPhAfRxBnnqAM2DfTmEZz0DYWVOEaAxfIq0Q3v4hmQPmwLOQgDLxdNjV0qBmO_NrGflpSXTHSfs3c7dGSwACVx5zZ7pRpEK_ui7mTkfNnD?width=872&height=308&cropmode=none" alt="Mul_Command">  

  #### **MUL command**
  
  Multiplies the top 2 elements on the stack and pushes the result back to the stack.

  Format:
  
    MUL

  <img src="https://bn1301files.storage.live.com/y4mRE5sjoCRrIBl6d234AseptRgx4QV6T0_l1eNr2fQr_uKhnkg-y-dAEdzBIdRx5XvAMD_qRp9Y4UIXJhCQsunGhSv6ndiQUU24Lg9fZhVDph_xemZbNMQ0ZZ1IqfIMI9RdAto31f5BmibfNOyXsfFjOetyO9an7yJILksCUjbo62UszkEP7O3ITJmdgLWRyv9?width=872&height=308&cropmode=none" alt="Mul_Command"> 

  #### **NEG command**
  
  Negates the top element on the stack and pushes the result back to the stack.

  Format:
  
    NEG

  <img src="https://bn1301files.storage.live.com/y4mYK1OO4haNBAWvCZhlnKHPrGVqfqGr72He-W2_L6lScJyJ7PEmFffAWd5UEcwbS0IazrRsb0FMBalyTEp6LqwfdBeAnH1qPUiabWm12H9GLt5-eST89m05VlQiK04yCH1dBsmwGlWP0pO9ysp_v-ewA8zZjcp1wP1-el_aHFpuAffCYuhBbKsz7XA4S4q1GF7?width=872&height=308&cropmode=none" alt="Neg_Command"> 

  #### **INV command**
  
  Inverts the top element on the stack and pushes the result back to the stack.

  Format:
  
    INV

  <img src="https://bn1301files.storage.live.com/y4mzIEkm72vgvu286jGjvk1b2TkNebHTCLT7Da91pan6kCZKb91oxOuRGZ0wfhWKxw0Q0tPnJ0zlljro8NiSr1NS6y6HyJjcZMtKY2M0YQ8XcPRBz6iXZZEAjcx-4mJNG0YAySa0ETvIqMaHEWRcr_HZD3a1JPP4Ro9FzOY1RcE1j8ZveltdtXADj4V-NJ1nJDI?width=872&height=308&cropmode=none" alt="Inv_Command"> 

  #### **UNDO command**
  
  If the previous command is one of the "contaminating" operations, i.e. "PUSH/POP/CLEAR/ADD/MUL/NEG/INV", the last instruction is undone leaving the stack in the same state as before that instruction.

  If there are no commands having executed yet, executing UNDO will have no effect, i.e. the stack will still be empty.

  Format:
  
    UNDO

  <img src="https://bn1301files.storage.live.com/y4mla39YJqR_-ypRe94OnnR4o0YGFt-YpDuNZ9VQtDpigvFG9SUnb4yhE7k2O68BJcvcAx0GQag6V8GOkkwA6t90SD5nVa6NbwG1QN8ehtXL-pW4M3XULmidl9l67ZY8mCSCJb_44bK6pVLZUHahy8lR22oXX1mojW2P_kitPY1O8pC28gSFcXt9YAY-gxcjJvG?width=872&height=308&cropmode=none" alt="Undo_Command"> 

  #### **PRINT command**
  
  Prints all elements that are currently on the stack, in the order of top element to bottom element.

  If there are no elements in the stack, system will print "There are now no elements in the stack".

  Format:
  
    PRINT

  <img src="https://bn1301files.storage.live.com/y4mPtzKFxDonSCLKFdPM8xPDBfXOJsjPzgteIEyQV9MY7X1vCDNOAVpodhJn4Vj7Gpq47otieqCYoxwhmILsKZ_5tCyPceeRDiXLMKbhsa6uOY8wSeEaaqJwO6bc1GW4Lyc1Q8qeYG7-H21HlmJlCWr_VmOOE20XKgTnIvk0Q4O-NRY07Ps1T-V5jNPlNLLqDAD?width=872&height=308&cropmode=none" alt="Print_Command"> 

  #### **QUIT command**
  
  Exits the program.

  Format:
  
    QUIT

  <img src="https://bn1301files.storage.live.com/y4mL0KfnOQhEX3B1r8JiY4f7F9X4HNSRPpqnLiP0uHK-DFp45_HFNQgkl06j7RDJE0v6I3DyYamw9ZVH9OrgxVk1wdQ-t1FGtR1EmPW83sN25SA6uXzOXVXAOFcdo9Sua2mw9RFYYgy4PC-imaukaMl-OmTfLcVKH194PwxeQnJlwiKg4_rqFLeiPceL_WZugdh?width=872&height=212&cropmode=none" alt="Quit_Command"> 
  
<br/>
<br/>

All the displayed values will be rounded off to 2 decimal places.

<br/>
<br/>

Example instruction sequence:
<br/>
<br/>
<img src="https://bn1301files.storage.live.com/y4mJIGDwMCGVCi_ZnM-g1o-QUDbxYxox5hV6TG4gIq8X60onjWTaxfhZnhyoFjJwKs_WlVO3jEwU8h9Au6kGIaDyZN8YEFqr3JL_4jyM5Uqu46qBClWjtM78lEOw5YG3g686702r4m5k_3IMp0oXSjVpxF-cjgRZ4w1i2-qPaoc7GJ1TCt-kEw2-aEU3QfRgv17?width=872&height=1160&cropmode=none" alt="Example_Instruction_Sequence"> 


## **Architecture**

There are 2 main parts that constitutes the application, the frontend console and the backend services.

The frontend console receives input from the user, delegates the actual handling to the services, and displays messages to the user.

The backend services provide functions that corresponds to the commands. The return value in the process will be provided back to the console and shown to the user.

### Data storage

A StackMachine bean is created on system start up. It has a stack as its property, which is what the program manipulates with. 

### Validation

A backend validation service is created to perform actions related to vefiying the user's inputs. Any problematic commands will result in an IllegalArgumentException. The error message will be displayed in the console notifying user of the casue of the issue.

### Logging

Logs will be grouped by INFO (currently at [JAR FOLDER]/logs/info.log) and ERROR logs (currently at [JAR FOLDER]/logs/error.log).

The info log contains all logs of the system, including system error logs if any.

The error log contains errors related to the program run.

All log files will be housekept daily or when the file size exceed the limit.

Log settings can be configured by editing logback-spring.xml.

### Testing

Unit tests of the backend services will be run in the test phase of the maven build.

Integration test cases and results can be found in "Integration_test.docx".

## **Technology stack**

* Java 11
* Spring Boot v2.4.3
  * Dependencies:
    * lombok (Provides getters/setters to entity/model class properties, and provides slf4j support for logging)
    * spring-boot-starter-test (Provides JUnit support)
    * spring-boot-maven-plugin (Provides Maven support)
    * spring-boot-starter-actuator (For development)
    * spring-boot-devtools (For development)
