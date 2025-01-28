# Use OpenJDK as base
FROM openjdk:17-jdk

# Set environment variables
ENV DB_HOST=${DB_HOST}
ENV DB_PORT=${DB_PORT}
ENV DB_NAME=${DB_NAME}
ENV DB_USER=${DB_USER}
ENV DB_PASSWORD=${DB_PASSWORD}
ENV PORT=${PORT}

# Copy WAR file and run it
COPY target/your-app.war /app.war

# Expose Render's port and run the app
EXPOSE ${PORT}
ENTRYPOINT ["java", "-jar", "/app.war"]
