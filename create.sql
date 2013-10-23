CREATE TABLE User 
  ( 
     UserID   VARCHAR(255) NOT NULL PRIMARY KEY, 
     Rating   INT, 
     Location VARCHAR(255), 
     Country  VARCHAR(255)
  ); 

CREATE TABLE Item 
  ( 
     ItemID      INT NOT NULL PRIMARY KEY AUTO_INCREMENT, 
     Name        VARCHAR(255), 
     Description VARCHAR(4000), 
     UserID      VARCHAR(255) NOT NULL, 
     Started     TIMESTAMP NULL, 
     Ended       TIMESTAMP NULL
  ); 

CREATE TABLE ItemBids 
  ( 
     ItemID         INT NOT NULL PRIMARY KEY AUTO_INCREMENT, 
     First_Bid      DECIMAL(8, 2), 
     Currently      DECIMAL(8, 2), 
     Number_of_Bids INT, 
     Buy_Price      DECIMAL(8, 2) 
  ); 

CREATE TABLE Bids 
  ( 
     ItemID INT NOT NULL, 
     Amount DECIMAL(8, 2) NOT NULL, 
     UserID VARCHAR(255) NOT NULL, 
     Time   TIMESTAMP NULL, 
     PRIMARY KEY(ItemID, Amount) 
  ); 

CREATE TABLE Category 
  ( 
     Category VARCHAR(255) NOT NULL PRIMARY KEY
  ); 

CREATE TABLE ItemCategory 
  ( 
     ItemID     INT NOT NULL, 
     Category   VARCHAR(255) NOT NULL, 
     FOREIGN KEY (ItemID) REFERENCES Item(ItemID), 
     FOREIGN KEY (Category) REFERENCES Category(Category),
     PRIMARY KEY (ItemID, Category)
  ); 
