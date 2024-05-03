Himanshu Release Note:

Changes done in this commit :

 a). Required validation is applied to the Employee API.
 b). Controller advice for common exeception has been added .
 c). Custom exception has been added
 d). General test cases for the employee api got added .
 e). Integration tests with authentication endpoint.
 f). For protecting the endpoints Spring security + JWT tokens are added .
 g). For testing purpose User endpoint is added through which new user can be added and also get authenticated with required token . The token is then uses for accessing 
     employee api . Also if the token is expired user will not able to access the employee api . Currently the jwt secret key is saved in application.yml but can be saved in
     system environment variables for production use .
 h). Spring Caching has been added.
 i). Logging is added using log4j.
 j). Updated some comments for better readability of code and also the syntax .
 k). Sysout lines has been removed


Things which can be done if more time is there :

a). Mock Test data can be added to some json file .
b). Can run the test cases with "SprinRunner.class" for also testing spring bean injection and its life cycle.
c). Log type can be defined like INFO, DEBUG, WARNING etc..
d). Roles can be saved in different table and with ManyToMany relation with User table so that 1 user can have multiple roles.
e). ErrorResponse model needs to be created so that if any error will occur during the api call then it will be returned. 