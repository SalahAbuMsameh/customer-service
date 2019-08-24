# customer-service

Provides customer related information & functions

# Requirements:

- Apache Maven 3.3
- Java 1.8

# Getting Started

Clone and build the project

```bash
    git clone https://github.com/SalahAbuMsameh/customer-service.git
    cd customer-service
    mvn spring-boot:run
```

Run with logging, profile settings and memory arguments:

`mvn spring-boot:run -Xmx4g -Dlogging.level.com.omerio.marketplace.checkout=TRACE -Dspring.profiles.active=stub`

## Swagger Docs

**The Swagger Docs can be accessed on the following URLs:**

[http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

[http://localhost:8080/v2/api-docs](http://localhost:8080/v2/api-docs)
