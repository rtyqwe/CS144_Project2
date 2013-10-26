/* CS144
 *
 * Parser skeleton for processing item-???.xml files. Must be compiled in
 * JDK 1.5 or above.
 *
 * Instructions:
 *
 * This program processes all files passed on the command line (to parse
 * an entire diectory, type "java MyParser myFiles/*.xml" at the shell).
 *
 * At the point noted below, an individual XML file has been parsed into a
 * DOM Document node. You should fill in code to process the node. Java's
 * interface for the Document Object Model (DOM) is in package
 * org.w3c.dom. The documentation is available online at
 *
 * http://java.sun.com/j2se/1.5.0/docs/api/index.html
 *
 * A tutorial of Java's XML Parsing can be found at:
 *
 * http://java.sun.com/webservices/jaxp/
 *
 * Some auxiliary methods have been written for you. You may find them
 * useful.
 */

package edu.ucla.cs.cs144;

import java.io.*;
import java.sql.PreparedStatement;
import java.text.*;
import java.util.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.FactoryConfigurationError;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.ErrorHandler;

import edu.ucla.cs.cs144.Schemas.*;


class MyParser {
    
    static final String columnSeparator = "|*|";
    static DocumentBuilder builder;
    
    static final String[] typeName = {
	"none",
	"Element",
	"Attr",
	"Text",
	"CDATA",
	"EntityRef",
	"Entity",
	"ProcInstr",
	"Comment",
	"Document",
	"DocType",
	"DocFragment",
	"Notation",
    };
    
    static class MyErrorHandler implements ErrorHandler {
        
        public void warning(SAXParseException exception)
        throws SAXException {
            fatalError(exception);
        }
        
        public void error(SAXParseException exception)
        throws SAXException {
            fatalError(exception);
        }
        
        public void fatalError(SAXParseException exception)
        throws SAXException {
            exception.printStackTrace();
            System.out.println("There should be no errors " +
                               "in the supplied XML files.");
            System.exit(3);
        }
        
    }
    
    /* Non-recursive (NR) version of Node.getElementsByTagName(...)
     */
    static Element[] getElementsByTagNameNR(Element e, String tagName) {
        Vector< Element > elements = new Vector< Element >();
        Node child = e.getFirstChild();
        while (child != null) {
            if (child instanceof Element && child.getNodeName().equals(tagName))
            {
                elements.add( (Element)child );
            }
            child = child.getNextSibling();
        }
        Element[] result = new Element[elements.size()];
        elements.copyInto(result);
        return result;
    }
    
    /* Returns the first subelement of e matching the given tagName, or
     * null if one does not exist. NR means Non-Recursive.
     */
    static Element getElementByTagNameNR(Element e, String tagName) {
        Node child = e.getFirstChild();
        while (child != null) {
            if (child instanceof Element && child.getNodeName().equals(tagName))
                return (Element) child;
            child = child.getNextSibling();
        }
        return null;
    }
    
    /* Returns the text associated with the given element (which must have
     * type #PCDATA) as child, or "" if it contains no text.
     */
    static String getElementText(Element e) {
        if (e.getChildNodes().getLength() == 1) {
            Text elementText = (Text) e.getFirstChild();
            return elementText.getNodeValue();
        }
        else
            return "";
    }
    
    /* Returns the text (#PCDATA) associated with the first subelement X
     * of e with the given tagName. If no such X exists or X contains no
     * text, "" is returned. NR means Non-Recursive.
     */
    static String getElementTextByTagNameNR(Element e, String tagName) {
        Element elem = getElementByTagNameNR(e, tagName);
        if (elem != null)
            return getElementText(elem);
        else
            return "";
    }
    
    /* Returns the amount (in XXXXX.xx format) denoted by a money-string
     * like $3,453.23. Returns the input if the input is an empty string.
     */
    static String strip(String money) {
        if (money.equals(""))
            return money;
        else {
            double am = 0.0;
            NumberFormat nf = NumberFormat.getCurrencyInstance(Locale.US);
            try { am = nf.parse(money).doubleValue(); }
            catch (ParseException e) {
                System.out.println("This method should work for all " +
                                   "money values you find in our data.");
                System.exit(20);
            }
            nf.setGroupingUsed(false);
            return nf.format(am).substring(1);
        }
    }
    
    static String convertToTimestamp(String time) {
    	SimpleDateFormat format = null;
    	SimpleDateFormat sqlFormat = null;
    	Date date = null;
    	try {
            format = new SimpleDateFormat("MMM-dd-yy HH:mm:ss");
			date = format.parse(time);
			sqlFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return sqlFormat.format(date);
    }
    
    /* Process one items-???.xml file.
     */
    static void processFile(File xmlFile) {
        Document doc = null;
        try {
            doc = builder.parse(xmlFile);
        }
        catch (IOException e) {
            e.printStackTrace();
            System.exit(3);
        }
        catch (SAXException e) {
            System.out.println("Parsing error on file " + xmlFile);
            System.out.println("  (not supposed to happen with supplied XML files)");
            e.printStackTrace();
            System.exit(3);
        }
        
        /* At this point 'doc' contains a DOM representation of an 'Items' XML
         * file. Use doc.getDocumentElement() to get the root Element. */
        System.out.println("Successfully parsed - " + xmlFile);
        
        /* Fill in code here (you will probably need to write auxiliary
            methods). */
        
        //ArrayList<Item> items = new ArrayList<Item>();
        // recursiveSetSchema(doc, 0, items);
        // Process new schemas from items
        // Make new files from schemas
        
        ArrayList<Item> items = setAllSchema(doc);
        
        HashSet<String> categorySet = new HashSet<String>();
        HashSet<UserSchema> userSchemaSet = new HashSet<UserSchema>();

        for (Item item : items) {
        	// Item Schema
        	ItemSchema itemSchema = new ItemSchema();
            itemSchema.setDescription(item.getDescription());
            itemSchema.setEnded(item.getEnds());
            itemSchema.setItemId(item.getId());
            itemSchema.setName(item.getName());
            itemSchema.setStarted(item.getStarted());
            itemSchema.setUserId(item.getSeller().getId());
            writeSchemaToFile(itemSchema, "item.dat");
            
            // Item Bids Schema
            ItemBidsSchema itemBidsSchema = new ItemBidsSchema();
            itemBidsSchema.setBuyPrice(item.getBuyPrice());
            itemBidsSchema.setCurrently(item.getCurrently());
            itemBidsSchema.setFirstBid(item.getFirstBid());
            itemBidsSchema.setItemId(item.getId());
            itemBidsSchema.setNumberOfBids(item.getNumberOfBids());
            writeSchemaToFile(itemBidsSchema, "itemBids.dat");
            
            // User Schema
            UserSchema sellerSchema = new UserSchema();
            sellerSchema.setCountry(item.getSeller().getCountry());
            sellerSchema.setLocation(item.getSeller().getLocation());
            sellerSchema.setRating(item.getSeller().getRating());
            sellerSchema.setUserId(item.getSeller().getId());
            if (!isInSet(item.getSeller().getId(), userSchemaSet)) {
                userSchemaSet.add(sellerSchema);
            }


            // Item Category Schema
            ItemCategorySchema itemCategorySchema = new ItemCategorySchema();
            for (String category : item.getCategories()) {
                itemCategorySchema.setItemId(item.getId());
                itemCategorySchema.setCategoryId(category);
                writeSchemaToFile(itemCategorySchema, "itemCategory.dat");
            }
            
            // set for categories
            for (String category : item.getCategories()) {
            	categorySet.add(category);
            }
            
            // Bids Table
            for (Bid bid : item.getBids().getBid()) {
            	BidsSchema bidSchema = new BidsSchema();
            	bidSchema.setAmount(bid.getAmount());
            	bidSchema.setTime(bid.getTime());
            	bidSchema.setItemId(item.getId());
            	bidSchema.setUserId(bid.getUser().getId());
                writeSchemaToFile(bidSchema, "bids.dat");
            }
        }
        
        for (Item item : items) {
        	 for (Bid bid : item.getBids().getBid()) {
             	UserSchema userSchema = new UserSchema();
             	userSchema.setCountry(bid.getUser().getCountry());
             	userSchema.setLocation(bid.getUser().getLocation());
             	userSchema.setRating(bid.getUser().getRating());
             	userSchema.setUserId(bid.getUser().getId());
                if (!isInSet(bid.getUser().getId(), userSchemaSet)) {
                	userSchemaSet.add(userSchema);
                }
             }
        }
        
       
        
        // Category Schema
        CategorySchema categorySchema = new CategorySchema();
        for (String category : categorySet) {
        	categorySchema.setCategoryId(category);
            writeSchemaToFile(categorySchema, "category.dat");
        }
        
        for (UserSchema userSchema : userSchemaSet) {
        	writeSchemaToFile(userSchema, "user.dat");
        }
        
        /**************************************************************/
        
    }

    static boolean isInSet(String userId, HashSet<UserSchema> set) {
    	for (UserSchema user : set) {
    		if(userId.equals(user.getUserId()))
    			return true;
    	}
    	return false;
    }
    
    static String cleanString(String s) {
    	/*
    	 * \0	An ASCII NUL (0x00) character.
\'	A single quote (Ò'Ó) character.
\"	A double quote (Ò"Ó) character.
\b	A backspace character.
\n	A newline (linefeed) character.
\r	A carriage return character.
\t	A tab character.
\Z	ASCII 26 (Control+Z). See note following the table.
\\	A backslash (Ò\Ó) character.
\%	A Ò%Ó character. See note following the table.
\_	A Ò_Ó character. See note following the table.
    	 */
    	
        String replacements[][] = new String[][ ]{
                {"\\",  "\\\\"},
                {"\0", "\\0"},
                {"'", "\\'"}, 
                {"\"",  "\\\""},
                {"\b",  "\\b"},
                {"\n",  "\\n"},
                {"\r",  "\\r"},
                {"\t",  "\\t"},
                {"\\Z", "\\\\Z"}, 
                {"%", "\\%"},     
                {"_", "\\_"}
        };
        for (String[] c : replacements) {
            s = s.replace(c[0], c[1]);
        }
        return s;
    }
    
    public static ArrayList<Item>  setAllSchema(Document doc) {
    	Element root = doc.getDocumentElement();
    	NodeList itemList = root.getElementsByTagName("Item");
    	ArrayList<Item> items = new ArrayList<Item>();
    	
    	// For all items
    	for(int i = 0; i < itemList.getLength(); i++) {
    		Item itemContainer = new Item();
    		Node item = itemList.item(i);
    		itemContainer.setId(item.getAttributes().getNamedItem("ItemID").getNodeValue());
    		itemContainer.setName(cleanString(getElementTextByTagNameNR((Element) item, "Name")));
    		
    		ArrayList<String> categories = new ArrayList<String>();
    		Element[] categoryList = getElementsByTagNameNR((Element) item, "Category");
    		for (Element categoryEle : categoryList) {
    			categories.add(getElementText(categoryEle));
    		}
    		itemContainer.setCategories(categories);

    		itemContainer.setCurrently(strip(getElementTextByTagNameNR((Element) item, "Currently")));
    		itemContainer.setBuyPrice(strip(getElementTextByTagNameNR((Element) item, "Buy_Price")));
    		itemContainer.setFirstBid(strip(getElementTextByTagNameNR((Element) item, "First_Bid")));
    		itemContainer.setNumberOfBids(getElementTextByTagNameNR((Element) item, "Number_of_Bids"));
    		itemContainer.setStarted(convertToTimestamp(getElementTextByTagNameNR((Element) item, "Started")));
    		itemContainer.setEnds(convertToTimestamp(getElementTextByTagNameNR((Element) item, "Ends")));
    		
    		if (getElementTextByTagNameNR((Element) item, "Description").length() >= 4000) {
        		itemContainer.setDescription(cleanString(getElementTextByTagNameNR((Element) item, "Description").substring(4000)));
    		}
    		else {
        		itemContainer.setDescription(cleanString(getElementTextByTagNameNR((Element) item, "Description")));
    		}
    		
    		//Seller
    		User seller = new User();
    		Element sellerNode = getElementByTagNameNR((Element) item, "Seller");
    		seller.setId(cleanString(sellerNode.getAttribute("UserID")));
    		seller.setRating(sellerNode.getAttribute("Rating"));
    		seller.setCountry(cleanString(getElementTextByTagNameNR((Element) item, "Country").trim()));
    		seller.setLocation(cleanString(getElementTextByTagNameNR((Element) item, "Location").trim()));
    		itemContainer.setSeller(seller);
    		
    		//Bids
    		Bids bids = new Bids();
    		ArrayList<Bid> itemBidList = new ArrayList<Bid>();
    		Element bidsNode = getElementByTagNameNR((Element) item, "Bids");
    		Element[] bidsList = getElementsByTagNameNR((Element) bidsNode, "Bid");
    		for (Element bidEle : bidsList) {
    			Bid bid = new Bid();
    			User bidUser = new User();
    			Element bidder = getElementByTagNameNR(bidEle, "Bidder");
    			
        		// Bidder
        		bidUser.setId(cleanString(bidder.getAttribute("UserID")));
        		bidUser.setRating(bidder.getAttribute("Rating"));
        		
        		if(getElementTextByTagNameNR((Element) bidder, "Country").trim().isEmpty())
        			bidUser.setCountry("\\N");
        		else
        			bidUser.setCountry(cleanString(getElementTextByTagNameNR((Element) bidder, "Country").trim()));
        		
        		if (getElementTextByTagNameNR((Element) bidder, "Location").trim().isEmpty())
        			bidUser.setLocation("\\N");
        		else
        			bidUser.setLocation(cleanString(getElementTextByTagNameNR((Element) bidder, "Location").trim()));
        			
    			bid.setUser(bidUser);
    			// Time and Amount
    			bid.setTime(convertToTimestamp(getElementTextByTagNameNR(bidEle, "Time")));
    			bid.setAmount(strip(getElementTextByTagNameNR(bidEle, "Amount")));
    			
    			itemBidList.add(bid);
    		}
    		bids.setBid(itemBidList);
    		itemContainer.setBids(bids);
    		
    		items.add(itemContainer);
    	}
    	return items;
    }
    
    public static void writeSchemaToFile(Object schema, String filename) {
    	FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(filename,true); //the true will append the new data
            fileWriter.write(schema.toString() + "\n");//appends the string to the file
        }
        catch(IOException ioe) {
            System.err.println("IOException: " + ioe.getMessage());
        }
        finally {
            try {
				fileWriter.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
        }
    }
    
    public static void main (String[] args) {
        if (args.length == 0) {
            System.out.println("Usage: java MyParser [file] [file] ...");
            System.exit(1);
        }
        
        /* Initialize parser. */
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setValidating(false);
            factory.setIgnoringElementContentWhitespace(true);      
            builder = factory.newDocumentBuilder();
            builder.setErrorHandler(new MyErrorHandler());
        }
        catch (FactoryConfigurationError e) {
            System.out.println("unable to get a document builder factory");
            System.exit(2);
        } 
        catch (ParserConfigurationException e) {
            System.out.println("parser was unable to be configured");
            System.exit(2);
        }
        
        /* Process all files listed on command line. */
        for (int i = 0; i < args.length; i++) {
            File currentFile = new File(args[i]);
            processFile(currentFile);
        }
    }
}
