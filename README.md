This application using the Maven build tool and it is only run with some of the following requirements:
* Java 1.8
* Jersey Servlet Container 2.5.1
* Tomcat Server ver 8.x (my version is 8.5.57), or you can choose another web server
* Some Maven plugins also need installing.
* All the libraries included in the `lib` folder.

Dependencies are managed under the `pom.xml` file, which are automatically generated
when you create a Maven project, you need to install all of them.

I have included some requests in Postman, please take a look
at some photo attached in the `/postman-img` folder.

The application context is `/`.

The REST service has the following path `/cinema/*`.

For setting up the theater:
* http://localhost:8080/cinema/theater/setup (You need to provide the `row`, `column` and `distance` in the body of the POST
request)

For checking seat sets available:
* http://localhost:8080/cinema/client/seats (You need to provide the `seats` in the body of the POST request)

For reserving a seat:
* http://localhost:8080/cinema/client/reserve (You have to provide `party`, `row`, and `column` in the body of the POST request)

You can also view the theater layout with the GET request to: http://localhost:8080/cinema/client/view


I also create simple GUI for users for input the form data.

All requests I respond with the Json data.


Also, some unit tests are under the `test/java` folder.