# Spring Boot File Upload Demo
This project demonstrates file upload, view, and download using Spring Boot MVC and Hibernate.
#######################
# SERVER CONFIGURATION #
#######################
# Application port
server.port=8080

#######################
# DATABASE CONFIGURATION #
#######################
# MySQL database connection
spring.datasource.url=jdbc:mysql://localhost:3306/mydb?useSSL=false&serverTimezone=UTC
spring.datasource.username=root
spring.datasource.password=yourpassword

# Hibernate JPA Configuration
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect

#######################
# FILE UPLOAD CONFIGURATION #
#######################
# Max file size
spring.servlet.multipart.max-file-size=20MB
spring.servlet.multipart.max-request-size=20MB

#######################
# EMAIL CONFIGURATION (Optional) #
#######################
spring.mail.host=smtp.gmail.com
spring.mail.port=587
spring.mail.username=youremail@gmail.com
spring.mail.password=yourEmailPassword
spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.starttls.enable=true

#######################
# H2 DATABASE (Optional - for testing) #
#######################
spring.h2.console.enabled=true
spring.h2.console.path=/h2-console

#######################
# LOGGING CONFIGURATION #
#######################
# Show SQL queries in console
logging.level.org.hibernate.SQL=DEBUG
logging.level.org.hibernate.type.descriptor.sql.BasicBinder=TRACE

#######################
# TIMEZONE & ENCODING #
#######################
spring.jackson.time-zone=Asia/Kolkata
spring.jackson.serialization.indent_output=true
spring.mvc.view.encoding=UTF-8
spring.http.encoding.charset=UTF-8
spring.http.encoding.enabled=true
spring.http.encoding.force=true
