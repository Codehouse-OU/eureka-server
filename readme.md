# Spring Cloud Eureka Wrapper
## About
This is a wrapper project for [Spring Cloud Eureka](https://cloud.spring.io/spring-cloud-netflix/multi/multi_spring-cloud-eureka-server.html), a service registry and discovery tool.

## Getting Started

### Prerequisites

- Java Development Kit (JDK) 17 or higher.
- Docker and Docker Compose for containerized setups.

### Configuration
The wrapper uses two additional configuration profiles:
* basicAuth - Uses Basic Auth for authentication. Credentials are in `application-basicAuth.yml`.
* ldap - Uses LDAP authentication. Credentials are in `application-ldap.yml`. It configures Eureka in a way that the user has to have ${ldap.allowedRole} role to be able to use the service.
