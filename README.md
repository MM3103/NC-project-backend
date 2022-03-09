# ORDER SERVICE.
## 1.Project description.
There is a service for creation orders for the provision of various services.Each order has its own unique id.The application uses the UUID identification standard.
Orders from all users persist in the postgres database.Only users authorized on the server can place orders.
The application uses the keycloak service to set up authorization.Orders created by users processed by the service admin.
The mail to the admin receives a letter with the author's name and order id.Each order has its own status.When an order  created, it receives the WAITING status.
The admin can either accept or reject the order.Depending on this, the order status changes to ACCEPTED or REJECTED.
## 2. Database.
The service uses a postgres database. The database has a table of orders. 
The table consists of seven fields: id, first name, last name, email, type of order, address, order status.
To deploy the database, the application uses liquid base.
## 3. Authorization.
The application uses the keycloak service for authorization. Keycloak is an open source single sign-on implementation with access control, aimed at modern applications and services.
Each user needs to register on the keycloak service.There are two possible roles: user and admin.
Depending on the role, the user's capabilities on the service differ.
## 4. Swagger.
Swagger is a framework for defining a REST API.It  allows not only to interactively view the specification, but also to send requests.
The application uses an access token to access the swagger ui.