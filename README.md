<h1 align="center" style="font-weight: bold;">Proposal Management System</h1>

<p align="center">
 <a href="#functionalities">Functionalities</a> • 
 <a href="#technologies">Technologies</a> • 
 <a href="#getting-started">Getting Started</a> • 
 <a href="#api-endpoints">API Endpoints</a> •
 <a href="#developer">Developer</a> •
 <a href="#contribute">Contribute</a>
</p>

<p align="center">
    <b>The Proposal Management System is a microservice designed to simulate a loan application process. Users can create loan proposals that are sent to a credit analysis service. Throughout the lifecycle of the proposal, users are notified in real time of the status at each phase via text messages. These notifications are handled by a notification microservice using AWS SNS, ensuring that users are promptly informed whether their proposals have been approved or not.</b>
</p>

<h2 id="functionalities">Functionalities</h2>

- **Register Proposals:** Users can create new loan proposals, which are then sent to the credit analysis service for evaluation.
- **List all proposals:** retrieve and display all proposals, allowing the user to check the status of whether it has been approved or denied and the details of each.

<h2 id="technologies">Technologies</h2>

- Java 17
- Postgres
- Spring Framework 3.3.2
- Spring Web 
- Spring Data JPA
- Swagger 2.5.0
- Apache Maven 3.3.2
- Spring Validation I/O
- GIT 2.34.1
- Docker
- Docker Compose
- ProblemDetail
- RabbitMQ
- AWS SNS
- MapStruct
- Design pattern: Strategy
- Shell

<h2 id="getting-started">🚀 Getting started</h2>

This section guides you through running the project locally.

<h3>Pre-requisites</h3>

Before you begin, ensure you have the following software installed:

* Java Development Kit (JDK) -  https://www.oracle.com/java/technologies/downloads/
* Maven - https://maven.apache.org/download.cgi
* Docker - https://www.docker.com/products/docker-desktop/
* Docker compose - https://docs.docker.com/compose/install/
* Git - https://git-scm.com/
* RabbitMQ - https://www.rabbitmq.com/
* PostgreSQL - https://www.postgresql.org/
* AWS - https://aws.amazon.com/pt/

**Optional:**
* IDE (Integrated Development Environment) - (e.g., IntelliJ IDEA, Eclipse)
* Client HTTP - (Postman, Insominia, Bruno)

<h3>Running the Project</h3>

1.  **Clone the Repository:**
```
git clone git@github.com:RayanSf18/Proposal-management-System.git
```
2. **Navigate to the Project Directory:**
```
cd proposal-management-system.
```
3. **Config .application.properties variables from notification:**

```
AWS_KEY_ID={YOUR_AWS_KEY_ID}
AWS_SECRET={YOUR_AWS_SECRET}
```
5. **Run Postgres on Docker compose:**
```
cd/docker

docker-compose up -d
```
5. **Start the Application:**
```
1. Notification
mvn spring-boot:run

2. Credit-analisys
mvn spring-boot:run

3. Proposal
mvn spring-boot:run

```
5. **server of application:**
```
http://localhost:8080/
```

<h2 id="api-endpoints">API Endpoints</h2>

<p>View endpoint results in:  <a href="http://localhost:8080/swagger-ui/index.html#/">Swagger</a></p>

| route               | description                                          
|----------------------|-----------------------------------------------------
| <kbd>POST /proposals</kbd>     | This endpoint register a new proposal with the provided details.
| <kbd>GET /proposals</kbd>     | This endpoint is used to search for all proposals.

<h2 id="developer">👨‍💻 Developer</h2>
<table>
  <tr>
    <td align="center">
      <a href="#">
        <img src="https://avatars.githubusercontent.com/u/127986772?v=4" width="100px;" alt="Rayan Silva Profile Picture"/><br>
        <sub>
          <b>Rayan silva</b>
        </sub>
      </a>
    </td>
  </tr>
</table>

<h2 id="contribute">🤝 Contribute</h2>

1. Fork the repository.
2. Create a new branch (git checkout -b feature/AmazingFeature).
3. Make your changes.
4. Commit your changes (git commit -m 'Add some AmazingFeature').
5. Push to the branch (git push origin feature/AmazingFeature).
6. Open a pull request.

<h3>Documentations that might help</h3>

[📝 How to create a Pull Request](https://www.atlassian.com/br/git/tutorials/making-a-pull-request)

[💾 Commit pattern](https://gist.github.com/joshbuchea/6f47e86d2510bce28f8e7f42ae84c716)

Enjoy coding! 😄
