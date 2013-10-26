package edu.ucla.cs.cs144;

import java.util.ArrayList;

public class Schemas {
	static class Item {
		String id, name, currently, buyPrice, firstBid, numberOfBids, 
			    started, ends, description;
		ArrayList<String> categories = new ArrayList<String>();
		User seller;
		Bids bids;
		
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getCurrently() {
			return currently;
		}
		public void setCurrently(String currently) {
			this.currently = currently;
		}
		public String getBuyPrice() {
			return buyPrice;
		}
		public void setBuyPrice(String buyPrice) {
			this.buyPrice = buyPrice;
		}
		public String getFirstBid() {
			return firstBid;
		}
		public void setFirstBid(String firstBid) {
			this.firstBid = firstBid;
		}
		public String getNumberOfBids() {
			return numberOfBids;
		}
		public void setNumberOfBids(String numberOfBids) {
			this.numberOfBids = numberOfBids;
		}
		public String getStarted() {
			return started;
		}
		public void setStarted(String started) {
			this.started = started;
		}
		public String getEnds() {
			return ends;
		}
		public void setEnds(String ends) {
			this.ends = ends;
		}
		public String getDescription() {
			return description;
		}
		public void setDescription(String description) {
			this.description = description;
		}
		public ArrayList<String> getCategories() {
			return categories;
		}
		public void setCategories(ArrayList<String> categories) {
			this.categories = categories;
		}
		public User getSeller() {
			return seller;
		}
		public void setSeller(User seller) {
			this.seller = seller;
		}
		public Bids getBids() {
			return bids;
		}
		public void setBids(Bids bids) {
			this.bids = bids;
		}
		
	}
	
	static class User {
		String id, rating, location, country;

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public String getRating() {
			return rating;
		}

		public void setRating(String rating) {
			this.rating = rating;
		}

		public String getLocation() {
			return location;
		}

		public void setLocation(String location) {
			this.location = location;
		}

		public String getCountry() {
			return country;
		}

		public void setCountry(String country) {
			this.country = country;
		}
		
		
	}
	
	static class Bids {
		ArrayList<Bid> bid = new ArrayList<Bid>();

		public ArrayList<Bid> getBid() {
			return bid;
		}

		public void setBid(ArrayList<Bid> bid) {
			this.bid = bid;
		}
		
	}
	
	static class Bid {
		User user;
		String time, amount;
		public User getUser() {
			return user;
		}
		public void setUser(User user) {
			this.user = user;
		}
		public String getTime() {
			return time;
		}
		public void setTime(String time) {
			this.time = time;
		}
		public String getAmount() {
			return amount;
		}
		public void setAmount(String amount) {
			this.amount = amount;
		}
		
	}
	  
	
	
	
	
	
    //ItemCateogry (ItemID [FOREIGN], CategoryID [FOREIGN])
    static class ItemCategorySchema {
    	String itemId, categoryId;
		
    	public String getItemId() {
			return itemId;
		}
		public void setItemId(String itemId) {
			this.itemId = itemId;
		}
		public String getCategoryId() {
			return categoryId;
		}
		public void setCategoryId(String categoryId) {
			this.categoryId = categoryId;
		}
		
		@Override
		public String toString() {
			return itemId + ",\t" + categoryId;
		}
    }
    
    // Category (CategoryID [KEY], Name)
    static class CategorySchema {
    	String category;

		public String getCategory() {
			return category;
		}

		public void setCategoryId(String categoryId) {
			this.category = categoryId;
		}


		@Override
		public String toString() {
			return category;
		}
    }
    
    // Bids (ItemID [KEY], Amount [KEY], UserID, Time)
    static class BidsSchema {
    	String itemId, amount, userId, time;

		public String getItemId() {
			return itemId;
		}

		public void setItemId(String itemId) {
			this.itemId = itemId;
		}

		public String getAmount() {
			return amount;
		}

		public void setAmount(String amount) {
			this.amount = amount;
		}

		public String getUserId() {
			return userId;
		}

		public void setUserId(String userId) {
			this.userId = userId;
		}

		public String getTime() {
			return time;
		}

		public void setTime(String time) {
			this.time = time;
		}

		@Override
		public String toString() {
			return itemId + ",\t" + amount
					+ ",\t" + userId + ",\t" + time;
		}
    }
    
    // ItemBids (ItemID [KEY], First_Bid, Currently, Number_of_Bids, Buy_Price)
    static class ItemBidsSchema {
    	String itemId, firstBid, currently, numberOfBids, buyPrice;

		public String getItemId() {
			return itemId;
		}

		public void setItemId(String itemId) {
			this.itemId = itemId;
		}

		public String getFirstBid() {
			return firstBid;
		}

		public void setFirstBid(String firstBid) {
			this.firstBid = firstBid;
		}

		public String getCurrently() {
			return currently;
		}

		public void setCurrently(String currently) {
			this.currently = currently;
		}

		public String getNumberOfBids() {
			return numberOfBids;
		}

		public void setNumberOfBids(String numberOfBids) {
			this.numberOfBids = numberOfBids;
		}

		public String getBuyPrice() {
			return buyPrice;
		}

		public void setBuyPrice(String buyPrice) {
			this.buyPrice = buyPrice;
		}

		@Override
		public String toString() {
			return itemId + ",\t"
					+ firstBid + ",\t" + currently + ",\t"
					+ numberOfBids + ",\t" + buyPrice;
		}
    	
    }
    
    // Item (ItemID [KEY], Name, Description, UserID, Started, Ends)
    static class ItemSchema {
    	String itemId, name, description, userId, started, ended;

		public String getItemId() {
			return itemId;
		}

		public void setItemId(String itemId) {
			this.itemId = itemId;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getDescription() {
			return description;
		}

		public void setDescription(String description) {
			this.description = description;
		}

		public String getUserId() {
			return userId;
		}

		public void setUserId(String userId) {
			this.userId = userId;
		}

		public String getStarted() {
			return started;
		}

		public void setStarted(String started) {
			this.started = started;
		}

		public String getEnded() {
			return ended;
		}

		public void setEnded(String ended) {
			this.ended = ended;
		}

		@Override
		public String toString() {
			return itemId + ",\t" + '"'+  name + '"' + ",\t" 
					+ '"' + description + '"' + ",\t" + userId
					+ ",\t" + started + ",\t" + ended;
		}
    	
    }
    
    // User (UserID [KEY], Rating, Location, Country)
    static class UserSchema {
    	String userId, rating, location, country;

		public String getUserId() {
			return userId;
		}

		public void setUserId(String userId) {
			this.userId = userId;
		}

		public String getRating() {
			return rating;
		}

		public void setRating(String rating) {
			this.rating = rating;
		}

		public String getLocation() {
			return location;
		}

		public void setLocation(String location) {
			this.location = location;
		}

		public String getCountry() {
			return country;
		}

		public void setCountry(String country) {
			this.country = country;
		}

		@Override
		public String toString() {
			return userId + ",\t" + '"' + rating + '"'
					+ ",\t" + '"' + location + '"' +  ",\t" + '"' + country + '"';
		}
    	
    }
}
