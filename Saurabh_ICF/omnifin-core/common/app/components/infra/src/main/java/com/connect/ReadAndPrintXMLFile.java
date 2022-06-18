package com.connect;
import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import com.logger.LoggerMsg;
/**
 * 
 * @author admin
 *
 */
public class ReadAndPrintXMLFile{

    @SuppressWarnings("static-access")
	public static void callXML(){
    try {
    	/**
    	 * 
    	 */
    	 Dbconfiguration dbconfiguration=new Dbconfiguration();

            DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
            Document doc = docBuilder.parse (new File("DB.xml"));

            // normalize text representation
            doc.getDocumentElement ().normalize ();
           // System.out.println ("Root element of the doc is " +      doc.getDocumentElement().getNodeName());


            NodeList listOfPersons = doc.getElementsByTagName("dbconfig");
            int totalPersons = listOfPersons.getLength();
          
            for(int s=0; s<listOfPersons.getLength() ; s++){


                Node firstPersonNode = listOfPersons.item(s);
                if(firstPersonNode.getNodeType() == Node.ELEMENT_NODE){


                    Element firstPersonElement = (Element)firstPersonNode;

                    //-------
                    NodeList firstNameList = firstPersonElement.getElementsByTagName("ipaddress");
                    Element firstNameElement = (Element)firstNameList.item(0);

                    NodeList textFNList = firstNameElement.getChildNodes();
                  
                   String ipaddress=textFNList.item(0).getNodeValue().trim();
                   
                   //System.out.println("ipadress"+ipaddress);
                   
                  //  Dbconfiguration.setURl();
                   /* System.out.println("First Name : " + 
                           ((Node)textFNList.item(0)).getNodeValue().trim());*/

                    //-------
                    NodeList lastNameList = firstPersonElement.getElementsByTagName("schema");
                    
                    Element lastNameElement = (Element)lastNameList.item(0);

                    NodeList textLNList = lastNameElement.getChildNodes();
                    String schema=textLNList.item(0).getNodeValue().trim();
                    String finalurl=ipaddress+"/"+schema;
                    Dbconfiguration.setURl(finalurl);
                    //System.out.println("shemac"+schema);
                    /*System.out.println("Last Name : " + 
                           ((Node)textLNList.item(0)).getNodeValue().trim());*/

                    //----
                    NodeList ageList = firstPersonElement.getElementsByTagName("username");
                    Element ageElement = (Element)ageList.item(0);

                    NodeList textAgeList = ageElement.getChildNodes();
                    String username=textAgeList.item(0).getNodeValue().trim();
                    dbconfiguration.setUsername(username);
                   // System.out.println("username"+username);
                    /*System.out.println("Age : " + 
                           ((Node)textAgeList.item(0)).getNodeValue().trim());*/
                    NodeList password = firstPersonElement.getElementsByTagName("password");
                    Element password2 = (Element)password.item(0);
                    LoggerMsg.info("printing the status window of Element");
                    NodeList textAgeList2 = password2.getChildNodes();
                   String passwordstr=textAgeList2.item(0).getNodeValue().trim();
                   dbconfiguration.setPassword(passwordstr);
                   //System.out.println("password"+passwordstr);
                    /*System.out.println("Age : " + 
                           ((Node)textAgeList.item(0)).getNodeValue().trim());*/
                    //------


                }//end of if clause


            }//end of for loop with s var


        }catch (SAXParseException err) {
        	LoggerMsg.debug ("** Parsing error" + ", line " 
             + err.getLineNumber () + ", uri " + err.getSystemId ());
        	LoggerMsg.debug(" " + err.getMessage ());
        

        }catch (SAXException e) {
        Exception x = e.getException ();
        ((x == null) ? e : x).printStackTrace ();

        }catch (Throwable t) {
        	LoggerMsg.debug("Error from ReadAndPrintXMLFile class ::"+t.getMessage());
        t.printStackTrace ();
        }
        //System.exit (0);

    }//end of main


}