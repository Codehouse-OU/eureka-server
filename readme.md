# Spring Cloud Eureka Wrapper

## About

This project serves as a wrapper
for [Spring Cloud Eureka](https://cloud.spring.io/spring-cloud-netflix/multi/multi_spring-cloud-eureka-server.html), a
robust service registry and discovery tool. It offers enhanced authentication mechanisms, making it simpler and more
secure to integrate into your microservices architecture.

## Features

- Easy integration with existing Spring Cloud Eureka setups.
- Additional authentication methods: Basic Auth and LDAP.
- Simplified configuration for service discovery in microservices.

## Prerequisites

- Java Development Kit (JDK) 17 or higher.
- Docker and Docker Compose for containerized environments.

## Installation

To install the Spring Cloud Eureka Wrapper, clone the repository and build the project using Gradle:

```shell
git clone https://github.com/Codehouse-OU/eureka-server.git
cd eureka-server
./gradlew build
```

## Configuration

This wrapper supports two authentication profiles:

- **Basic Auth**: Credentials provided in `application-basicAuth.yml`.
- **LDAP**: Integration with LDAP for authentication as specified in `application-ldap.yml`.

## Usage

### Docker Setup

#### Basic Auth

```yaml
# Docker Compose sample for Basic Auth
version: '3.7'
services:
  eureka-server:
    image: codehouseou/eureka-server:latest
    ports:
      - 8761:8761
    environment:
      - SPRING_PROFILES_ACTIVE=basicAuth
      - SPRING_SECURITY_USER_NAME=eureka
      - SPRING_SECURITY_USER_PASSWORD=5eVutmFK3EeGvYoxkKTQEYxNi
```

#### LDAP

```yaml
# Docker Compose sample for LDAP Auth
version: '3.7'
services:
  eureka-server:
    image: codehouseou/eureka-server:latest
    ports:
      - 8761:8761
    environment:
      - SPRING_PROFILES_ACTIVE=ldap
      # Configure LDAP auth for the server.
      - SPRING_PROFILES_ACTIVE=ldap
      # Configure LDAP connection.
      - LDAP_URL=ldap://server/DC=a,DC=b
      - LDAP_USERNAME=username
      - LDAP_PASSWORD=password
      # Configure LDAP search for users
      - LDAP_USER_SEARCH_BASE=OU=Org
      - LDAP_USER_SEARCH_FILTER=(sAMAccountName={0})
      # Configure LDAP search for roles
      - LDAP_GROUP_SEARCH_BASE=OU=Administrative Roles,OU=Role Groups,OU=Management,OU=Org
      - LDAP_GROUP_SEARCH_FILTER (member={0})
      # Configure role that is allowed to access Eureka
      - LDAP_ALLOWED_ROLE=ROLE_Role Eureka
```

### Running with Gradle

Run the server with the desired profile (either `basicAuth` or `ldap`):

```shell
./gradlew bootRun --args='--spring.profiles.active=basicAuth'
```

## Testing

To test the server, use the following curl command:

```shell
curl http://eureka:5eVutmFK3EeGvYoxkKTQEYxNi@localhost:8761/eureka/v2/apps -H 'accept: application/json'
```

## Troubleshooting

* When getting 401 error, check that the profile is set correctly. And the configuration is correct.

## Contributing

We welcome contributions!
