1. List your relations.

User (UserID [KEY], Rating, Location, Country)

Item (ItemID [KEY], Name, Description, UserID, Started, Ends)

ItemBids (ItemID [KEY], First_Bid, Currently, Number_of_Bids, Buy_Price)

Bids (ItemID [KEY], Amount [KEY], UserID, Time)

Category (CategoryID [KEY], Name)

ItemCateogry (ItemID [FOREIGN], CategoryID [FOREIGN])
