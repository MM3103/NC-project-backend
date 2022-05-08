# ORDER SERVICE

Order service is a service for manages orders for the provision of various services. 

## Order Management 

### Order life cycle

When a new order is created, it receives the status WAITING. 
After the order status can be changed to ACCEPTED or REJECTED, depending on the decision of the administrator. 
When an order has the status ACCEPTED or REJECTED its status can no longer be changed.
Every 24h orders with statuses ACCEPTED and REJECTED get status ARCHIVED.

### Administrator

Administrator manages the life cycle of all orders. 
The administrator creates a list of cities and streets where the user can place orders. 
When a user places an order, a message with the number of this order and a link is sent to the admin's mail. 
The task of the admin is to review the order and either accept or reject it. 
Depending on this, the order status will be changed to ACCEPTED or REJECTED. 
Also, the admin can view the orders of all users. 

### Users

Regular users manage only their own orders.