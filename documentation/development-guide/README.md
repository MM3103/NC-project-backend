# ORDER SERVICE 
Order service is a service for creation orders for the provision of various services 
##Order model
The order contains the following fields: id, first name, last name, email, type of order,city_id,street_id,house,flat, address,
creation time ,modification time, self installation and order status. 
The order structure is implemented in the model Order. 
The model also has a custom builder. 
It is required so that when creating a new order, all fields are filled.
##City model
Each city consists of two fields: id and name. The city structure is implemented in the model City.
##Street model
Each street consists of three fields: id, name and city_id. The street structure is implemented in the model Street.
##Database
Orders from all users persist in the postgres database. 
The service uses a postgres database. 
The database has tables of orders,cities and streets. 
The table orders consists of fourteen fields: id, first name, last name, email, type of order,city_id,street_id,house,flat, address,
creation time ,modification time, self installation and order status.  
The table cities consists of two fields: id and name.
The table streets consists of three fields: id, name and city_id.
To deploy the database, the application uses liquid base.
##Order service
The order service implements various operations, such as: adding a new order, deleting an order, updating an order, reading an order by ID. 
In addition, it implements special methods for the admin, such as: reading all orders, accepting and rejecting an order.
##City service
The city service implements various operations, such as: adding a new city, deleting a city, updating a city, reading a city by ID.
##Street service
The street service implements various operations, such as: adding a new street, deleting a street, updating a street, reading a street by ID. 
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
        [
          {
            "typeOrder": "water",
            "id": "1a2184c5-a7c3-4522-8ce4-750c66b44511",
            "firstName": "admin",
            "lastName": "admin",
            "email": "adminadmin@yandex.ru",
            "city": {
              "id": 3,
              "name": "NN",
              "cityStatus": "ACTIVE"
            },
            "street": {
              "id": 4,
              "name": "Lenina",
              "city": {
                "id": 3,
                "name": "NN",
                "cityStatus": "ACTIVE"
              },
              "streetStatus": "ACTIVE"
            },
            "house": 5,
            "flat": 5,
            "selfInstallation": true,
            "address": "City: NN, street: Lenina, house:  5, flat:  5",
            "orderStatus": "WAITING",
            "creation_time": "2022-05-01T18:21:40.286779+03:00",
            "modification_time": null
          },
          {
            "typeOrder": "water",
            "id": "31d8e889-c1ad-451c-887c-8a940efa77c0",
            "firstName": "admin",
            "lastName": "admin",
            "email": "adminadmin@yandex.ru",
            "city": {
              "id": 3,
              "name": "NN",
              "cityStatus": "ACTIVE"
            },
            "street": {
              "id": 4,
              "name": "Lenina",
              "city": {
                "id": 3,
                "name": "NN",
                "cityStatus": "ACTIVE"
              },
              "streetStatus": "ACTIVE"
            },
            "house": 3,
            "flat": 234,
            "selfInstallation": true,
            "address": "City: NN, street: Lenina, house:  3, flat:  234",
            "orderStatus": "ARCHIVED",
            "creation_time": "2022-05-01T18:12:25.590775+03:00",
            "modification_time": "2022-05-01T18:13:49.756381+03:00"
          },
          {
            "typeOrder": "repair",
            "id": "5117e48e-2642-4756-947a-7c767d90798a",
            "firstName": "admin",
            "lastName": "admin",
            "email": "adminadmin@yandex.ru",
            "city": {
              "id": 3,
              "name": "NN",
              "cityStatus": "ACTIVE"
            },
            "street": {
              "id": 5,
              "name": "Gagarina",
              "city": {
                "id": 3,
                "name": "NN",
                "cityStatus": "ACTIVE"
              },
              "streetStatus": "ACTIVE"
            },
            "house": 4,
            "flat": 55,
            "selfInstallation": false,
            "address": "City: NN, street: Gagarina, house:  4, flat:  55",
            "orderStatus": "ARCHIVED",
            "creation_time": "2022-05-01T18:16:30.49808+03:00",
            "modification_time": "2022-05-01T18:18:11.794252+03:00"
          }]
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
          "typeOrder": "repair",
          "id": "5117e48e-2642-4756-947a-7c767d90798a",
          "firstName": "admin",
          "lastName": "admin",
          "email": "adminadmin@yandex.ru",
          "city": {
            "id": 3,
            "name": "NN",
            "cityStatus": "ACTIVE"
          },
          "street": {
            "id": 5,
            "name": "Gagarina",
            "city": {
              "id": 3,
              "name": "NN",
              "cityStatus": "ACTIVE"
            },
            "streetStatus": "ACTIVE"
          },
          "house": 4,
          "flat": 55,
          "selfInstallation": false,
          "address": "City: NN, street: Gagarina, house:  4, flat:  55",
          "orderStatus": "ARCHIVED",
          "creation_time": "2022-05-01T18:16:30.49808+03:00",
          "modification_time": "2022-05-01T18:18:11.794252+03:00"
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
            "id": "3fa85f64-5717-4562-b3fc-2c963f66afa6",
            "firstName": "string",
            "lastName": "string",
            "email": "string",
            "city": {
              "id": 0,
              "name": "string",
              "cityStatus": "ACTIVE"
            },
            "street": {
              "id": 0,
              "name": "string",
              "city": {
                "id": 0,
                "name": "string",
                "cityStatus": "ACTIVE"
              },
              "streetStatus": "ACTIVE"
            },
            "house": 0,
            "flat": 0,
            "selfInstallation": true,
            "address": "string",
            "orderStatus": "WAITING",
            "creation_time": "2022-05-03T13:45:37.712Z",
            "modification_time": "2022-05-03T13:45:37.712Z"
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
        "city": {
              "id": 0,
              "name": "string",
              "cityStatus": "ACTIVE"
            },
            "street": {
              "id": 0,
              "name": "string",
              "city": {
                "id": 0,
                "name": "string",
                "cityStatus": "ACTIVE"
              },
              "streetStatus": "ACTIVE"
            },
      "house": 1,
      "flat": 1,
      "self installation": true,
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
              "id": "3fa85f64-5717-4562-b3fc-2c963f66afa6",
              "firstName": "string",
              "lastName": "string",
              "email": "string",
              "city": {
                "id": 0,
                "name": "string",
                "cityStatus": "ACTIVE"
              },
              "street": {
                "id": 0,
                "name": "string",
                "city": {
                  "id": 0,
                  "name": "string",
                  "cityStatus": "ACTIVE"
                },
                "streetStatus": "ACTIVE"
              },
              "house": 0,
              "flat": 0,
              "selfInstallation": true,
              "address": "string",
              "orderStatus": "WAITING",
              "creation_time": "2022-05-03T13:44:11.846Z",
              "modification_time": "2022-05-03T13:44:11.846Z"
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