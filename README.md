# GitHub Listener
Simple project app allowing to list all not-forked user repositories with all its branches.
## Tech Stack
> Language: *Java 17*
> Frameworks: *SpringBoot 3, JUnit, Mockito*
> Build tool: *Maven*
> Architecture: *RESTful API*
## How to use
If you want you can use your personal GitHub token by typing it in *application.yml* file:

    github:
      token: "YOUR_TOKEN"
Use command `mvn spring-boot:run` in root folder to start app.

To list user repositories send **GET** request on `localhost:8080/api?username=USER`. Set the **Accept** header to `application/json`.