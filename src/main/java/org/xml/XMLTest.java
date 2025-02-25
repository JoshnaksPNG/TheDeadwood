package org.xml;

// Example Code for parsing XML file
// Dr. Moushumi Sharmin
// CSCI 345

import org.w3c.dom.Document;


public class XMLTest{

   public static void main(String args[]){
   
      Document doc = null;
      XMLParser parsing = new XMLParser();
      try{
      
         doc = parsing.getDocFromFile("TheDeadwood/src/main/java/org/xml/cards.xml");
         parsing.readCardData(doc);
      
      }catch (Exception e){
      
         System.out.println("Error = "+e);
      
      }
      
   
   }
}