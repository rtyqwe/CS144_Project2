-- Find the number of users in the database.
SELECT Count(UserID) 
FROM   User; 

-- Find the number of sellers from "New York", (i.e., sellers whose location is exactly the string "New York"). Pay special attention to case sensitivity. You should match the sellers from "New York" but not from "new york".
SELECT Count(UserID) 
FROM   User 
       INNER JOIN Item 
               ON Item.UserID = User.UserID 
WHERE  BINARY User.Location = 'New York'; 

-- Find the number of auctions belonging to exactly four categories.
SELECT Count(*) 
FROM   ItemCategory 
GROUP  BY UserID 
HAVING Count (UserID) = 4;

-- Find the ID(s) of current (unsold) auction(s) with the highest bid.
SELECT ItemID 
FROM   Item 
WHERE  (SELECT ItemID 
        FROM   ItemBids 
        WHERE  Currently = (SELECT Max(Currently) 
                            FROM   itembids)) 
       AND Ended > '2001-12-20 00:00:01'; 

-- Find the number of sellers whose rating is higher than 1000.
SELECT Count(*) 
FROM   Users 
       INNER JOIN Item 
               ON Item.UserID = USER.userid 
WHERE  Rating > 1000; 

-- Find the number of users who are both sellers and bidders.
SELECT Count(*) 
FROM   Item 
       INNER JOIN Bids 
               ON Item.UserID = Bids.UserID;

-- Find the number of categories that include at least one item with a bid of more than $100.
SELECT Count(*) 
FROM   ItemCategory 
WHERE  ItemCategory.ItemID = (SELECT DISTINCT ItemID 
                              FROM   Bids 
                              WHERE  Amount > 100); 
                              