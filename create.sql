CREATE TABLE User 
  ( 
     UserID   INT NOT NULL PRIMARY KEY AUTO_INCREMENT, 
     Rating   INT, 
     Location VARCHAR(255), 
     Country  VARCHAR(255)
  ); 

CREATE TABLE Item 
  ( 
     ItemID      INT NOT NULL PRIMARY KEY AUTO_INCREMENT, 
     Name        VARCHAR(255), 
     Description VARCHAR(4000), 
     UserID      INT NOT NULL, 
     Started     TIMESTAMP DEFAULT NULL, 
     Ended       TIMESTAMP DEFAULT NULL
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
     UserID INT, 
     Time   TIMESTAMP DEFAULT NULL, 
     PRIMARY KEY(ItemID, Amount) 
  ); 

CREATE TABLE Category 
  ( 
     CategoryID INT NOT NULL PRIMARY KEY AUTO_INCREMENT, 
     Name       VARCHAR(255) 
  ); 

CREATE TABLE ItemCategory 
  ( 
     ItemID     INT NOT NULL AUTO_INCREMENT, 
     CategoryID INT NOT NULL AUTO_INCREMENT, 
     INDEX (ItemID), 
     INDEX (CategoryID), 
     FOREIGN KEY (ItemID) REFERENCES Item(ItemID), 
     FOREIGN KEY (CategoryID) REFERENCES Category(CategoryID) 
  ); 