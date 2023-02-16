# PROJECT DESCRIPTION

## Task variant: Cargo Delivery

Web application was developed in order to provide delivery services by delivery company.
The website contains information about the directions of delivery, as well as about tariffs 
(tariffs depend on the distance, weight and dimensions of the cargo).

There are three roles:
* User;
* Authorized User;
* Manager.


### User (Guest) functionality:

* Can view information on the site (delivery tariffs).
* Keep track the goods.
* Calculate the cost of services.


### Manager functionality:

* Processes applications:
 - set delivery date;
 - set order current status;
 - create payment bill for delivery service.
* Receive reports on deliveries by days in pdf format.
* Receive reports on deliveries by destinations in pdf format.


### Authorized user (User) functionality:

* User in his account can pay for the delivery receipt.
* Can watch his list information about goods in his personal room.
* Can create a request for delivery of goods.
* Can change a request for delivery of goods.
* Can specify the information about order:
  - Sender information:
    - name;
    - surname;
    - phone;
    - the city where goods delivery from;
    - street name;
    - street number;
    - house number;
  - Recipient information:  
    - name;
    - surname;
    - phone;
    - the city where goods delivery to;
    - street name;
    - street number;
    - house number;
  - Order information:
    - name;
    - price;
    - weight;
    - type (Document, parcel or cargo);
    - delivery type (By truck or courier);
    - delivery distance;
    - order dimensions (length, height, width);
    - description.
* Can filter his order list information by delivery direction.
* Can top up the balance;
* Can pay for delivery services;


