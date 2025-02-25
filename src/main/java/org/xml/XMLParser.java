package org.xml;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;

import org.model.Role;
import org.model.Scene;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;
import java.util.ArrayList;

public class XMLParser{

   public Document getDocFromFile(String filename)
   throws ParserConfigurationException{
      {
         DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
         DocumentBuilder db = dbf.newDocumentBuilder();
         Document doc = null;
         
         try{
            doc = db.parse(filename);
         } catch (Exception ex){
            System.out.println("XML parse failure");
            ex.printStackTrace();
         }
         return doc;
      } // exception handling
   }  
   
   // reads data from XML file and prints data
   public ArrayList<Scene> readCardData(Document d){
      Element root = d.getDocumentElement();
      NodeList cards = root.getElementsByTagName("card");
      ArrayList<Scene> scenes = new ArrayList<Scene>();
      Role roleHolder = null;
      Scene sceneHolder = null;
      ArrayList<Role> roles = new ArrayList<Role>();
      for (int i=0; i < cards.getLength();i++){
         System.out.println("Printing information for Scene "+(i+1));
         
         //reads data from the nodes
         Node card = cards.item(i);
         String name = card.getAttributes().getNamedItem("name").getNodeValue();
         System.out.println("name = "+ name);
         
         //reads data
         NodeList children = card.getChildNodes();
         int sceneNum = 0;
         String sceneText = "";
         String partName = "";
         int level = 0;
         String partLine = "";
         int budget = Integer.parseInt(card.getAttributes().getNamedItem("budget").getNodeValue());
         for (int j=0; j < children.getLength(); j++){
            Node sub = children.item(j);
            if("scene".equals(sub.getNodeName())){
               sceneNum = Integer.parseInt(sub.getAttributes().getNamedItem("number").getNodeValue());
               sceneText = sub.getTextContent();
               System.out.println("Scene # = "+sceneNum);
               System.out.println("Scene text = " + sceneText);
            } else if("part".equals(sub.getNodeName())){
               partName = sub.getAttributes().getNamedItem("name").getNodeValue();
               System.out.println("part name = "+partName);
               level = Integer.parseInt(sub.getAttributes().getNamedItem("level").getNodeValue());
               System.out.println("part level = "+level);

               NodeList partChildren = sub.getChildNodes();
               for (int k = 0; k < partChildren.getLength(); k++) {
                  Node partSub = partChildren.item(k);
                  if ("line".equals(partSub.getNodeName())) {
                      partLine = partSub.getTextContent();
                  }
               }
               System.out.println("part line = "+partLine);
               roleHolder = new Role(partName, partLine, level, true);
            }
            roles.add(roleHolder);
         } //for childnodes
         sceneHolder = new Scene(sceneNum, name, sceneText, budget, roles);
         scenes.add(sceneHolder);
         roles.clear();
      } //for card node
      return scenes;
   } // method





}//class