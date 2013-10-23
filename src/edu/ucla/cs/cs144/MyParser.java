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
        
        ArrayList<Item> items = new ArrayList<Item>();
        recursiveSetSchema(doc, 0, items);
        
        // Process new schemas from items
        // Make new files from schemas
        
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
            itemBidsSchema.setCurrently(strip(item.getCurrently()));
            itemBidsSchema.setFirstBid(strip(item.getFirstBid()));
            itemBidsSchema.setItemId(item.getId());
            itemBidsSchema.setNumberOfBids(item.getNumberOfBids());
            writeSchemaToFile(itemBidsSchema, "itemBids.dat");
            
            // User Schema
            UserSchema sellerSchema = new UserSchema();
            sellerSchema.setCountry(item.getSeller().getCountry());
            sellerSchema.setLocation(item.getSeller().getLocation());
            sellerSchema.setRating(item.getSeller().getRating());
            sellerSchema.setUserId(item.getSeller().getId());
            userSchemaSet.add(sellerSchema);
            
            for (Bid bid : item.getBids().getBid()) {
            	UserSchema userSchema = new UserSchema();
            	userSchema.setCountry(bid.getUser().getCountry());
            	userSchema.setLocation(bid.getUser().getLocation());
            	userSchema.setRating(bid.getUser().getRating());
            	userSchema.setUserId(bid.getUser().getId());
            	userSchemaSet.add(userSchema);
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
            	bidSchema.setAmount(strip(bid.getAmount()));
            	bidSchema.setTime(bid.getTime());
            	bidSchema.setItemId(item.getId());
            	bidSchema.setUserId(bid.getUser().getId());
                writeSchemaToFile(bidSchema, "bids.dat");
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

    public static void recursiveSetSchema(Node n, int level, List<Item> items) {       
        // dump out node name, type, and value  
        String ntype = typeName[n.getNodeType()];
        String nname = n.getNodeName();
        String nvalue = n.getNodeValue(),
		       itemID = "",
		       name = "",
		       currently = "",
		       country = "",
		       started = "",
		       ends = "",
		       firstBid = "",
		       numberOfBids = "",
        	   description = "",
        	   location = "",
        	   buyPrice = "",
        	   sellerID = "",
        	   sellerRating = "";

        Item item = new Item();
        ArrayList<String> nCategories = new ArrayList<String>();
        ArrayList<Bid> nBids = new ArrayList<Schemas.Bid>();
        User seller = new User();
        if ("Item".equals(nname)) {
        	NodeList itemNodes = n.getChildNodes();
    		itemID = n.getAttributes().getNamedItem("ItemID").getNodeValue();
    		
    		item.setId(itemID);
    		
        	for (int i=0; i < itemNodes.getLength(); i++) {
        		Node childNode = n.getChildNodes().item(i);
        		String childNodeName = childNode.getNodeName();

        		String nextChildValue = "";
        		
        		if (childNode.getChildNodes().getLength() != 0) {
        			nextChildValue = childNode.getChildNodes().item(0).getNodeValue();
        		}
        		
            	if ("Category".equals(childNodeName)) {
                	nCategories.add(childNode.getChildNodes().item(0).getNodeValue());
                }
            	if ("First_Bid".equals(childNodeName)){
            		item.setFirstBid(nextChildValue);
                }
            	if ("Number_of_Bids".equals(childNodeName)) {
            		item.setNumberOfBids(nextChildValue); 
                }
            	if ("Currently".equals(childNodeName)) {
            		item.setCurrently(nextChildValue);
                }
            	if ("Buy_Price".equals(childNodeName)) {
            		item.setBuyPrice(nextChildValue);
            	}
            	if ("Started".equals(childNodeName)) {
            		item.setStarted(nextChildValue);
                }
            	if ("Ends".equals(childNodeName)) {
            		item.setEnds(nextChildValue);
                }
            	if ("Country".equals(childNodeName)) {
            		seller.setCountry(nextChildValue); 
                }
            	if ("Location".equals(childNodeName)) {
            		seller.setLocation(nextChildValue);
                }
            	if ("Description".equals(childNodeName)) {
            		if (nextChildValue.length() > 4000) {
            			nextChildValue = nextChildValue.substring(0, 4000);
            		}
            		item.setDescription(nextChildValue);
                }
            	if ("Bids".equals(childNodeName)) {
            		NodeList bidsList = childNode.getChildNodes();
            		
                    for(int j=0; j<bidsList.getLength(); j++) {
                    	Bid bid = new Bid();
                    	Node bidChild = bidsList.item(j);
            			Node bidder = bidChild.getChildNodes().item(0);
            			NodeList bidderChildList = bidder.getChildNodes();
            			User bidUser = new User();
            			Node timeNode = bidChild.getChildNodes().item(1);
            			Node amountNode = bidChild.getChildNodes().item(2);
            			
            			bidUser.setRating(bidder.getAttributes().getNamedItem("Rating").getNodeValue());
            			bidUser.setId(bidder.getAttributes().getNamedItem("UserID").getNodeValue());
            			bid.setAmount(amountNode.getChildNodes().item(0).getNodeValue());
            			bid.setTime(timeNode.getChildNodes().item(0).getNodeValue());
            			            			
            			for(int k=0; k<bidderChildList.getLength(); k++) {
            				Node bidderChild = bidderChildList.item(k);
            				String bidderChildNodeName = bidderChild.getNodeName();
            				String bidderChildNodeValue = "";
            				if (bidderChild.getChildNodes().getLength() != 0) {
            					bidderChildNodeValue = bidderChild.getChildNodes().item(0).getNodeValue();
            				}
            				
            				if ("Location".equals(bidderChildNodeName)) {
            					bidUser.setLocation(bidderChildNodeValue);
            				}
            				if ("Country".equals(bidderChildNodeName)) {
            					bidUser.setCountry(bidderChildNodeValue);
            				}            				
            			
            			}
            			
            			bid.setUser(bidUser);
            			nBids.add(bid);
                    }
            		// for all bid in bids attach to new bid
            		// append to bid array
                    
            	}
            	if ("Seller".equals(childNodeName)) {
            		seller.setRating(childNode.getAttributes().getNamedItem("Rating").getNodeValue());
            		seller.setId(childNode.getAttributes().getNamedItem("UserID").getNodeValue());
            	}
            	
        	}
        	Bids bids = new Bids();
        	bids.setBid(nBids);
        	item.setBids(bids);
        	item.setSeller(seller);
        	item.setCategories(nCategories);
        	item.setSeller(seller);
        	items.add(item);
        }
        else {
        	 // now walk through its children list
            org.w3c.dom.NodeList nlist = n.getChildNodes();
            
            for(int i=0; i<nlist.getLength(); i++)
                recursiveSetSchema(nlist.item(i), level+1, items);
        }
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
