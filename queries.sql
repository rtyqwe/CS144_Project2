-- Find the number of users in the database.
SELECT Count(UserID) 
FROM   User; 

-- Find the number of sellers from "New York", (i.e., sellers whose location is exactly the string "New York"). Pay special attention to case sensitivity. You should match the sellers from "New York" but not from "new york".
SELECT Count(UserID) 
FROM   User 
       INNER JOIN Item 
               ON Item.UserID = User.UserID; 
