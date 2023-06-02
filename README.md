# Spring Boot Product Management API

This project is a Spring Boot-based API for managing product details. It provides endpoints for retrieving product information, updating item details, and performing other operations related to product management.

## Table of Contents

- [Technologies Used](#technologies-used)
- [Setup Instructions](#setup-instructions)
- [API Endpoints](#api-endpoints)
- [Usage Examples](#usage-examples)

## Technologies Used

- Java
- Spring Boot
- Spring Data JPA
- MySQL (or any other database of your choice)
- Maven (or Gradle)

## Setup Instructions

1. Clone the repository and navigate to the project directory.

2. Ensure you have Java and Maven (or Gradle) installed on your system.

3. Configure the database connection properties in the `application.properties` file.

4. Run the following command to start the application:

   ```bash
   mvn spring-boot:run
   ```
   
5. The API will be accessible at http://localhost:8080/api/items.


## API Endpoints

1. Retrieve Top Selling Products

    - Endpoint: /api/items/top_selling
    
      Method: GET

      Description: Retrieves the top 10 selling items based on the quantity sold.
    
2. Product Details

    - Endpoint: /api/items/product_details/low_stock

      Method: GET

      Description: Returns the count of items that have stock less than the specified threshold (default: 100).

    - Endpoint: /api/items/product_details/all_items

      Method: GET

      Description: Returns the count of all items currently present in stock.

    - Endpoint: /api/items/product_details/item_groups

      Method: GET

      Description: Returns the count of items in each item group.

3. Update Item Details

    - Endpoint: /api/items/update

      Method: POST

      Description: Allows updating the name, quantity, remaining quantity, and price of an item. Expects a JSON object representing the updated item details in the request body.

    - Endpoint: /api/items/update/{id}

      Method: DELETE

      Description: Deletes the item with the specified ID.
      
      
## Usage Examples

To retrieve the top-selling products:
  ```http
  GET /api/products/top_selling
  ```
To get the count of items with low stock:
  ```http
  GET /api/products/product_details/low_stock?threshold=50
  ```
To update item details:
  ```http
  POST /api/products/update
  ```
  ```json
  {
    "id": 39,
    "itemDescription": "Updated Item",
    "quantity": 50,
    "remainingQuantity": 40,
    "price": 9.99
  }
  ```
To delete an item:
  ```http
  DELETE /api/products/update/123
  ```
