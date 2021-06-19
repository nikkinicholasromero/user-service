# User Service

#### Build the JAR using Maven
``
mvn clean package install
``

#### Build the Docker Image using the Dockerfile
``
docker build -t nikkinicholasromero/user-service .
``

#### Push the Docker Image to Docker Hub
``
docker push nikkinicholasromero/user-service
``

#### Start a Docker Container using the Docker Image
``
docker run --name user-service-container -d -p 8080:8080 nikkinicholasromero/user-service
``
