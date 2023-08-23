# Bookstore Application
The bookstore application was incorporated with Java. OOP was used to implement customer and book object and its GUI was designed with JavaFX. 
State design patter was used for this application.
Additionally, the use case diagram and the class diagram for this application was uploaded in this GitHub.
There are two types of users for this application: Owner and Customers.

The owner has a seperate login credential to access the app. The owner's username is 'admin' and the password is 'admin'.
The owner could use the bookstore application to manage/track books, as they are able to add/remove the books. Furthermore, they could also manage and track customers as they are able to add/remove customers and access their points and status.

The registered customer's login credential is based on the credential the owner set up for them. 
After the customer logged in, their points and their status (Silver/Gold) will be displayed respectively.
If the customer has less than 1000 points, their status will be silver; while if the points is 1000 or above, their status will be gold.
The customers could use the bookstore application to purchase books either directly or by redeeming points. 

For customers to purchase books, they need to select them with the check box in the table with the books that the owner added in the inventory. 
If the customer wants to redeem points and purchase and there is insufficent points to cover the complete cost, the points will cover a part of the cost 
but the customer needs to pay for the remaining. After the transaction is successful, the points will be updated based on the total cost of the purchase and the status may be updated.
