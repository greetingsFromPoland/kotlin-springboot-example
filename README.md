# Shopping Platform REST API

## Overview

This project is part of a Kotlin technical exercise focused on developing a shopping platform. The platform calculates
the price of products based on configurable discount policies. It features a REST API that allows clients to retrieve
product information by UUID and calculate the total price for a given product and quantity, applying count-based or/and
percentage-based discounts.

## Features

- **Retrieve Product Information**: Fetch details of a product using its UUID.
- **Calculate Total Price**: Compute the total price for a specified product and quantity, incorporating applicable
  discounts.
- **Configurable Discount Policies**: Supports count-based discounts that increase with quantity and percentage-based
  discounts on the total price.

## Technical Stack

- **Language**: Kotlin 1.9.24
- **Frameworks**: Spring Boot 3.3.1, Spring Framework
- **Build Tool**: Gradle

## Prerequisites

- JDK 17
- Docker

## API Documentation

This section provides a comprehensive guide to the REST API endpoints available in the shopping platform, including
details on request and response models, as well as example `curl` commands for interacting with the API.

### Endpoints

#### Retrieve Product Information

- **GET** `/api/v1/product/{uuid}`
    - Retrieves detailed information about a product by its UUID.
    - **Path Parameters**:
        - `uuid`: The unique identifier of the product.
    - **Response**: `ProductResponse`

#### Calculate Total Price

- **POST** `/api/v1/product/{uuid}/calculate-price`
    - Calculates the total price for a specified product and quantity, applying applicable discounts.
    - **Path Parameters**:
        - `uuid`: The unique identifier of the product.
    - **Request Body**: `CalculateDiscountRequest`
    - **Response**: `CalculateDiscountResponse`

### Models

#### `ProductResponse`

```json
{
  "ProductResponse": {
    "id": "String",
    "name": "String",
    "price": "Double"
  }
}
```

#### `CalculateDiscountRequest`

```json
{
  "CalculateDiscountRequest": {
    "quantity": "Int"
  }
}
```

#### `CalculateDiscountResponse`

```json
{
  "CalculateDiscountResponse": {
    "totalPrice": "Double",
    "uuid": "String",
    "quantity": "Int"
  }
}
```

### Example Requests

#### Retrieve Product Information

```shell
curl -X GET "http://localhost:8080/api/v1/product/{uuid}"
```

Replace `{uuid}` with the actual product UUID.

#### Calculate Total Price

```shell
curl -X POST "http://localhost:8080/api/v1/product/{uuid}/calculate-price" \
     -H "Content-Type: application/json" \
     -d '{"quantity": 10}'
```

Replace `{uuid}` with the product UUID and adjust the `quantity` in the request body as needed.

### Development Profile (`dev`)

The development profile, named `dev`, is designed to facilitate the application testing process. Upon activation, the
application automatically loads a test configuration along with a set of test data. This enables developers to quickly
test application functionalities without the need for manual data preparation.

#### Test Data

In the development profile, the following predefined products are available immediately upon application startup:

- **IPhone**: `7a7c5aa2-54cb-42e6-bc35-c424c3b56ec1`
- **Samsung**: `a78eecc1-98dd-4c57-82ce-7a2d09b2ca80`
- **Xiaomi**: `5faea262-17e7-4043-ab77-3386ee893abf`
- **Huawei**: `e21e7cf1-254f-409b-88cb-ce656cd5233d`

#### Test Configuration

The configuration for the `dev` profile includes product-based discounts, which are dynamically applied depending on the
quantity of the product purchased, and a fixed percentage discount on the entire order. Quantity-based discounts
increase with the number of products, offering greater savings for larger orders.

##### Count-Based Discounts

Count-based discounts are applied based on the quantity of a product in an order. The discounts increase with the
quantity, encouraging larger purchases. The specific thresholds and discount rates are as follows:

- **10 units**: 15% discount
- **20 units**: 20% discount
- **100 units**: 25% discount

##### Percentage-Based Discount

In addition to count-based discounts, a flat percentage-based discount is applied to the total order value. This
discount is:

- **10%** on the entire order

#### Running the Application in Development Profile

To run the application in the development profile using Gradle, the following command can be used in the terminal:

```
./gradlew bootRun --args='--spring.profiles.active=dev'
```

This command activates the `dev` profile, enabling the loading of test data and configuration, thus facilitating testing
and development of the application.

### Building the Project into an Executable JAR

To build project into an executable JAR file, follow these steps:

1. **Open your terminal** and navigate to the root directory of project.
2. **Execute the Gradle wrapper command** to build the project:
   ```shell
   ./gradlew clean build
   ```
   This command cleans any previous builds, compiles your code, runs tests, and packages your application into an
   executable JAR file located in the `build/libs` directory.

3. **Verify the JAR file** in the `build/libs/` directory. You should find your executable JAR, ready for deployment.

### Adding an External Configuration File

To add an external YAML configuration file, follow these steps:

1. **Create your YAML configuration file**, named `application-prod.yml`, and place it in a suitable directory outside
   of your JAR, such as a `config` directory.
2. **Configure your application** to use this file by specifying its location when running the JAR:
   ```shell
   java -jar application.jar --spring.config.location=file:./config/application-prod.yml
   ```
   In your `application-prod.yml`, you can define all production-specific settings, such as database credentials,
   logging configurations, etc.

## Building and Running the Project with Docker

This section guides you through the process of creating a Docker image for the project and running the image with an
external configuration file.

### Creating a Docker Image

The `Dockerfile` provided in the project root directory is designed to build the project and create a Docker image in a
two-stage process. You do not need to manually build the project before creating the Docker image. Follow these steps:

1. Ensure Docker is installed and running on your machine.
2. Open your terminal and navigate to the root directory of the project.
3. Execute the following command to build the Docker image:

```
docker build -t eshop:latest .
```

This command utilizes the multi-stage build process defined in the `Dockerfile` to first build the project using a
Gradle wrapper and then create a Docker image named `eshop` with the `latest` tag.

### Running the Docker Image with External Configuration

To run the Docker image with an external configuration file, follow these steps:

1. Create an external YAML configuration file, named `application-prod.yml`, and place it in a suitable directory on
   your host machine.
2. Run the Docker container, specifying the external configuration file location using the `SPRING_CONFIG_LOCATION`
   environment variable. Use the following command:

```
docker run -d -p 8080:8080 -v /path/to/your/config:/config -e SPRING_CONFIG_LOCATION=file:/config/ eshop:latest
```

Replace `/path/to/your/config` with the actual path to the directory containing your `application-prod.yml` file. This
command runs the `eshop` Docker container in detached mode, maps port 8080 of the container to port 8080 on the host,
mounts the configuration directory from the host to the `/config` directory in the container, and sets
the `SPRING_CONFIG_LOCATION` environment variable to the location of the external configuration directory.


## License

This project is licensed under the MIT License - see the LICENSE file for details.
