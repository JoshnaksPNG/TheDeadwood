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
      
         doc = parsing.getDocFromFile("cards.xml");
         parsing.readBookData(doc);
      
      }catch (Exception e){
      
         System.out.println("Error = "+e);
      
      }
      
   
   }
}