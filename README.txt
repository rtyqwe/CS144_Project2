1. List your relations.

User (UserID [KEY], Rating, Location, Country)

Item (ItemID [KEY], Name, Description, UserID, Started, Ends)

ItemBids (ItemID [KEY], First_Bid, Currently, Number_of_Bids, Buy_Price)

Bids (ItemID [KEY], Amount [KEY], UserID, Time)

Category (Category [KEY])

ItemCateogry (ItemID [FOREIGN], Category [FOREIGN])

2.
User Schema:
UserID -> Rating, Location, Country
UserID -> Location
UserID -> Country

Item Schema:
ItemID -> Name
ItemID -> Description
ItemID -> UserID
ItemID -> Started
ItemID -> Ends

ItemBids Schema:
ItemID -> First_Bid
ItemID -> Currently
ItemID -> Number_of_Bids
ItemID -> Buy_Price

Bids Schema:
ItemID, Amount -> UserID
ItemID, Amount -> Time

Category Schema:
Category

ItemCategory Schema:
ItemID -> Category
Category -> ItemID

3.
ItemCategory Schema is not BCNF because we notice that each Item can have multiple Categories but each Category can have multiple Items. We also designed it so that ItemID and Category are both foreign keys to the Item schema and Category schema.
