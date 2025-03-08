package org.xml;

import org.w3c.dom.Document;


public class XMLTest{

   public static void main(String args[]){
   
      Document doc1 = null;
      Document doc2 = null;
      XMLParser parsing = new XMLParser();
      try{
      
         // doc1 = parsing.getDocFromFile("TheDeadwood/src/main/java/org/xml/cards.xml");
         // parsing.readCardData(doc1);

         // doc2 = parsing.getDocFromFile("src/main/java/org/xml/board.xml");
         // parsing.readBoardData(doc2);
         parsing.readCardData();
      }catch (Exception e){
      
         System.out.println("Error = "+e);
      
      }
      
   
   }
}