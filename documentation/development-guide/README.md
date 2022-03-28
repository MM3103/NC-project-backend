# ORDER SERVICE 
Order service is a service for creation orders for the provision of various services 
##Order model
Each order consists of seven fields: id, first name, last name, email, type of order, address and order status. 
The order structure is implemented in the model Order. 
The model also has a custom builder. 
It is required so that when creating a new order, all fields are filled.
##Database
Orders from all users persist in the postgres database. 
The service uses a postgres database. 
The database has a table of orders. 
The table consists of seven fields: id, first name, last name, email, type of order, address, order status. 
To deploy the database, the application uses liquid base.
##Order service
The order service implements various operations, such as: adding a new order, deleting an order, updating an order, reading an order by ID. 
In addition, it implements special methods for the admin, such as: reading all orders, accepting and rejecting an order.
##Authorization 
The application uses the keycloak service for authorization. Keycloak is an open source single sign-on implementation with access control, aimed at modern applications and services. 
Only users authorized on the server can place orders. 
Each user needs to register on the keycloak service.There are two possible roles: user and admin. 
Depending on the role, the user's capabilities on the service differ. 
##Swagger
Swagger is a framework for defining a REST API. 
It  allows not only to interactively view the specification, but also to send requests. 
The application uses an access token to access the swagger ui. 
## API Documentation 

   **Localhost base URL: http://localhost:8484**

   **Note: In order to test order service Endpoints you need to use an HTTP Client Tool, Postman is recommended. You can also test by url: http://localhost:8440/swagger-ui.html**
 
### Show orders
 Returns json list of orders data
 
 * **CURL**
 
        curl -X 'GET' \
          'http://localhost:8484/order/getAll' \
          -H 'accept: */*' \
          -H 'Authorization: Bearer 
        Request URL
    
 * **Success Response:**

    * **Code**: 200
    
      **Content:** 
      
      ```json
      [  {
           "id": "795a7711-354a-4685-a92b-473814663646",
           "firstName": "admin",
           "lastName": "admin",
           "email": "adminadmin@yandex.ru",
           "typeOrder": "order1",
           "address": "order1",
           "orderStatus": "ACCEPTED"
         }, {...}]
       ```   
### Show Order
Returns json of a specific Order data based on its id
 
* **CURL**
 
        curl -X 'GET' \
          'http://localhost:8484/order/fbfc8ad5-36c9-41bc-b4f2-17fd534350d5' \
          -H 'accept: */*' \
          -H 'Authorization: Bearer 

* **Success Response:**

   * **Code**: 200
    
     **Content:** 
      
     ```json
     {
           "id": "fbfc8ad5-36c9-41bc-b4f2-17fd534350d5",
           "firstName": "admin",
           "lastName": "admin",
           "email": "adminadmin@yandex.ru",
           "typeOrder": "order1",
           "address": "order1",
           "orderStatus": "WAITING"
      }
      ```
### Create Order

Create new Order from json structure with values sent in body of request

* **CURL**

        curl -X 'POST' \
          'http://localhost:8484/order' \
          -H 'accept: */*' \
          -H 'Authorization: Bearer 
          -H 'Content-Type: application/json' \
          -d '{
          "typeOrder": "string",
          "address": "string",
          "id": "3fa85f64-5717-4562-b3fc-2c963f66afa6",
          "firstName": "string",
          "lastName": "string",
          "email": "string",
          "orderStatus": "WAITING"
        }'

* **Success Response:**

  * **Code:** 200 <br />
    **Content:** 
    
      ```json
        {
      "id": "3fa85f64-5717-4562-b3fc-2c963f66afa6",
      "firstName": "string",
      "lastName": "string",
      "email": "string",
      "typeOrder": "string",
      "address": "string",
      "orderStatus": "WAITING"
        }
       ```
### Update Order Data

  Updates the values sent as json in request body of a specific Order by its ID, returns updated json Person structure

* **CURL**

        curl -X 'PATCH' \
          'http://localhost:8484/order/47f687bb-5bc5-4c6e-8a36-76733973c1aa' \
          -H 'accept: */*' \
          -H 'Authorization: Bearer 
          -H 'Content-Type: application/json' \
          -d '{
          "typeOrder": "string",
          "address": "string",
          "id": "3fa85f64-5717-4562-b3fc-2c963f66afa6",
          "firstName": "string",
          "lastName": "string",
          "email": "string",
          "orderStatus": "WAITING"
        }'

* **Success Response:**

  * **Code:** 200 <br />
    **Content:** 
    
        Order  successfully updated
        
### Delete Order
Deletes Order by its ID 
 
* **CURL**
 
        curl -X 'DELETE' \
          'http://localhost:8484/order/1bc3d149-94f7-497a-a623-aae9e81cf596' \
          -H 'accept: */*' \
          -H 'Authorization: Bearer 
    
* **Success Response:**

   * **Code**: 200

     **Content:** 

         Order successfully deleted