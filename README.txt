1. List your relations.

Item (ItemID [KEY], Name, Category, Currently, Buy_Price, First_Bid, Number_of_Bids, Bids, Started, Ends, Seller (User), Description)

Category (CategoryID [KEY], Name)

User (UserID [KEY], Rating, Location, Country)



User (UserID [KEY], Rating, Location, Country)

Item (ItemID [KEY], Name, Description, UserID, Started, Ends)

ItemBids (ItemID [KEY], First_Bid, Currently, Number_of_Bids, Buy_Price)

Bids (ItemID [KEY], Amount [KEY], UserID, Time)

Category (CategoryID [KEY], Name)

ItemCateogry (ItemID [FOREIGN], CategoryID [FOREIGN])
