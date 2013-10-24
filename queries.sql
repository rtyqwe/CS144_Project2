-- Find the number of users in the database.
SELECT Count(1) 
FROM   User; 

-- Find the number of sellers from "New York", (i.e., sellers whose location is exactly the string "New York"). Pay special attention to case sensitivity. You should match the sellers from "New York" but not from "new york".
SELECT Count(User.UserID) 
FROM   User 
       INNER JOIN Item 
               ON Item.UserID = User.UserID 
WHERE  BINARY User.Location = 'New York'; 

-- Find the number of auctions belonging to exactly four categories.
SELECT Count(1) 
FROM   (SELECT ItemID 
        FROM   ItemCategory 
        GROUP  BY ItemID 
        HAVING( Count(ItemID) = 4 )) AS count; 

-- Find the ID(s) of current (unsold) auction(s) with the highest bid.
SELECT ItemBids.ItemID 
FROM   ItemBids 
WHERE  ItemBids.ItemID IN (SELECT Item.ItemID 
                           FROM   Item 
                           WHERE  Item.Ended > '2001-12-20 00:00:01') 
ORDER  BY ItemBids.Currently DESC 
LIMIT  1; 

-- Find the number of sellers whose rating is higher than 1000.
SELECT Count(DISTINCT User.UserID) 
        FROM   User, 
               Item 
        WHERE  User.Rating > 1000 
               AND User.UserID = Item.UserID; 

-- Find the number of users who are both sellers and bidders.
SELECT Count(DISTINCT Item.UserID) 
FROM   Item 
       JOIN Bids 
         ON Item.UserID = Bids.UserID; 

-- Find the number of categories that include at least one item with a bid of more than $100.
SELECT Count(1)
FROM  (SELECT  ItemID 
        FROM   ItemCategory 
        WHERE  ItemCategory.ItemID IN (SELECT DISTINCT ItemID 
                                       FROM   Bids 
                                       WHERE  Amount > 100.00) 
        GROUP  BY Category) AS count; 