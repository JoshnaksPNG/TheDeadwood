package org.xml;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;

import org.model.*;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;
import java.util.ArrayList;

public class XMLParser{

   public static Document getDocFromFile(String filename)
   throws ParserConfigurationException{
      {
         DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
         DocumentBuilder db = dbf.newDocumentBuilder();
         Document doc = null;
         
         try{
            doc = db.parse(filename);
         } catch (Exception ex){
            //System.out.println("XML parse failure");
            //ex.printStackTrace();
            throw new RuntimeException(ex);
         }
         return doc;
      } // exception handling
   }  
   
   // reads data from XML file and prints data
   public static ArrayList<Scene> readCardData(Document d){
      Element root = d.getDocumentElement();
      NodeList cards = root.getElementsByTagName("card");
      ArrayList<Scene> scenes = new ArrayList<Scene>();
      Role roleHolder = null;
      Scene sceneHolder = null;
      ArrayList<Role> roles = new ArrayList<Role>();
      System.out.println("Number of elements = " + cards.getLength());
      for (int i = 0; i < cards.getLength(); i++){
         System.out.println("Printing information for Scene "+(i+1));
         
         //reads data from the nodes
         Node card = cards.item(i);
         String name = card.getAttributes().getNamedItem("name").getNodeValue();
         System.out.println("name = "+ name);
         String img = card.getAttributes().getNamedItem("img").getNodeValue();
         System.out.println("img = " + img);
         int budget = Integer.parseInt(card.getAttributes().getNamedItem("budget").getNodeValue());
         System.out.println("buget = " + budget);

         //reads data
         NodeList children = card.getChildNodes();
         int sceneNum = 0;
         String sceneText = "";
         String partName = "";
         int partLevel = 0;
         String partLine = "";
         
         for (int j=0; j < children.getLength(); j++){
            Node sub = children.item(j);

            if("scene".equals(sub.getNodeName())){
               sceneNum = Integer.parseInt(sub.getAttributes().getNamedItem("number").getNodeValue());
               System.out.println("Scene # = "+sceneNum);
               sceneText = sub.getTextContent();
               System.out.println("Scene text = " + sceneText);

            } else if("part".equals(sub.getNodeName())){
               partName = sub.getAttributes().getNamedItem("name").getNodeValue();
               System.out.println("part name = "+partName);
               partLevel = Integer.parseInt(sub.getAttributes().getNamedItem("level").getNodeValue());
               System.out.println("part level = " + partLevel);

               NodeList partChildren = sub.getChildNodes();
               for (int k = 0; k < partChildren.getLength(); k++) {
                  Node partSub = partChildren.item(k);
                  if ("area".equals(partSub.getNodeName())) {
                     String partX = partSub.getAttributes().getNamedItem("x").getNodeValue();
                     System.out.print("x=" + partX);
                     String partY = partSub.getAttributes().getNamedItem("y").getNodeValue();
                     System.out.print(" y=" + partY);
                     String partH = partSub.getAttributes().getNamedItem("h").getNodeValue();
                     System.out.print(" h=" + partH);
                     String partW = partSub.getAttributes().getNamedItem("w").getNodeValue();
                     System.out.println(" w=" + partW);
                  } else if ("line".equals(partSub.getNodeName())) {
                      partLine = partSub.getTextContent();
                      System.out.println("part line = " + partLine);
                  }
               }
               
               roleHolder = new Role(partName, partLine, partLevel, true);
               roles.add(roleHolder);
               roleHolder.printString();
            }
            
         } // for childnodes
         sceneHolder = new Scene(sceneNum, name, sceneText, budget, roles);
         scenes.add(sceneHolder);
         roles = new ArrayList<>();
      } //for card node
      return scenes;
   } // method



   public static Room[] readBoardData(Document d) {
      Element root = d.getDocumentElement();
      NodeList sets = root.getElementsByTagName("set");
      NodeList trailer = root.getElementsByTagName("trailer");
      NodeList office = root.getElementsByTagName("office");
      // ArrayList<Set> rooms = new ArrayList<Set>();
      // Set room = null;
      ArrayList<Role> roles = new ArrayList<Role>(); // off card roles
      String partName = "", setName = "", line = "";
      int partLevel = 0, partX = 0, partY = 0, partH = 0, partW = 0;
      int sceneX = 0, sceneY = 0, sceneH = 0, sceneW = 0, takes = 0;
      Room[] rooms = new Room[12];

      ArrayList<String> neighbors = new ArrayList<String>();

      for (int i = 0; i < sets.getLength(); i++) {
         System.out.println("Printing information for set " + (i + 1));

         Node set = sets.item(i);
         NodeList children = set.getChildNodes();
         setName = set.getAttributes().getNamedItem("name").getNodeValue();
         // ArrayList<String> neighbors = new ArrayList<String>();
         
         System.out.println("Set Name = " + setName);

         for (int j = 0; j < children.getLength(); j++) {
            Node sub = children.item(j);

            if ("neighbors".equals(sub.getNodeName())) {
               NodeList neighborChildren = sub.getChildNodes();
               
               for (int k = 0; k < neighborChildren.getLength(); k++) {
                  Node neighborSub = neighborChildren.item(k);
                  
                  if ("neighbor".equals(neighborSub.getNodeName())) {
                     String neighbor = neighborSub.getAttributes().getNamedItem("name").getNodeValue();
                     neighbors.add(neighbor);
                     System.out.println("neighbor = " + neighbor);
                  }
               }
            } else if ("area".equals(sub.getNodeName())) {
               sceneX = Integer.parseInt(sub.getAttributes().getNamedItem("x").getNodeValue());
               System.out.print("Scene Dimensions: x = " + sceneX);
               sceneY = Integer.parseInt(sub.getAttributes().getNamedItem("y").getNodeValue());
               System.out.print(", y = " + sceneY);
               sceneH = Integer.parseInt(sub.getAttributes().getNamedItem("h").getNodeValue());
               System.out.print(", h = " + sceneH);
               sceneW = Integer.parseInt(sub.getAttributes().getNamedItem("w").getNodeValue());
               System.out.println(", w =" + sceneW);
            } else if ("takes".equals(sub.getNodeName())) {
               NodeList takeChildren = sub.getChildNodes();
               for (int k = 0; k < takeChildren.getLength(); k++) {
                  Node takeSub = takeChildren.item(k);

                  if ("take".equals(takeSub.getNodeName())) {
                     String numTakes = takeSub.getAttributes().getNamedItem("number").getNodeValue();
                     System.out.println("takes = " + numTakes);

                     NodeList takeGrandchildren = takeSub.getChildNodes();
                     for (int h = 0; h < takeGrandchildren.getLength(); h++) {
                        Node takeSubSub = takeGrandchildren.item(h);
                        if ("area".equals(takeSubSub.getNodeName())) {
                           takes++;
                           String takeX = takeSubSub.getAttributes().getNamedItem("x").getNodeValue();
                           System.out.print("Take Dimensions: x = " + takeX);
                           String takeY = takeSubSub.getAttributes().getNamedItem("y").getNodeValue();
                           System.out.print(", y = " + takeY);
                           String takeH = takeSubSub.getAttributes().getNamedItem("h").getNodeValue();
                           System.out.print(", h = " + takeH);
                           String takeW = takeSubSub.getAttributes().getNamedItem("w").getNodeValue();
                           System.out.println(", w = " + takeW);
                        }
                     }
                  }
               }
            } else if ("parts".equals(sub.getNodeName())) {
               NodeList partChildren = sub.getChildNodes();

               for (int k = 0; k < partChildren.getLength(); k++) {
                  Node partSub = partChildren.item(k);

                  if ("part".equals(partSub.getNodeName())) {
                     partName = partSub.getAttributes().getNamedItem("name").getNodeValue();
                     System.out.println("partName = " + partName);
                     partLevel = Integer.parseInt(partSub.getAttributes().getNamedItem("level").getNodeValue());
                     System.out.println("partLvl  = " + partLevel);

                     NodeList partGrandchildren = partSub.getChildNodes();
                     for (int l = 0; l < partGrandchildren.getLength(); l++) {
                        Node partSubSub = partGrandchildren.item(l);
                        if ("area".equals(partSubSub.getNodeName())) {
                           partX = Integer.parseInt(partSubSub.getAttributes().getNamedItem("x").getNodeValue());
                           System.out.print("Part Dimensions: x = " + partX);
                           partY = Integer.parseInt(partSubSub.getAttributes().getNamedItem("y").getNodeValue());
                           System.out.print(", y = " + partY);
                           partH = Integer.parseInt(partSubSub.getAttributes().getNamedItem("h").getNodeValue());
                           System.out.print(", h = " + partH);
                           partW = Integer.parseInt(partSubSub.getAttributes().getNamedItem("w").getNodeValue());
                           System.out.println(", w = " + partW);

                        } else if ("line".equals(partSubSub.getNodeName())) {
                           line = partSubSub.getTextContent();
                           // System.out.println("Part " + partName + " Line = " + line);
                        }
                     }
                     
                  }

                  // Not sure what's happeneing on even numbers, the same part runs twice
                  if (k % 2 == 1) {
                     Role roleHolder = new Role(partName, line, partLevel, false);
                     roles.add(roleHolder);
                     // System.out.println("Size of roles = " + roles.size());
                     roleHolder.printString();
                  }
                  
               }

               
            }
            
         }
         rooms[i] = new Set(setName, sceneX, sceneY, sceneW, sceneH, neighbors, null, roles, takes);
         System.out.println("Size of roles = " + roles.size());
         neighbors.clear();
         roles.clear();
         
         System.out.println();
      }
      System.out.println("Printing information for trailer.");
      // creating a trailer room specially at index 0 of the rooms array
      System.out.println("Printing information for trailer.");
      Node tr = trailer.item(0);
      NodeList trChildren = tr.getChildNodes();
      for (int t = 0; t < trChildren.getLength(); t++) {
         Node sub = trChildren.item(t);

         // ArrayList<String> neighbors = new ArrayList<String>();

         if ("neighbors".equals(sub.getNodeName())) {
               NodeList neighborChildren = sub.getChildNodes();
               for (int k = 0; k < neighborChildren.getLength(); k++) {
                  Node neighborSub = neighborChildren.item(k);
                  if ("neighbor".equals(neighborSub.getNodeName())) {
                     String neighbor = neighborSub.getAttributes().getNamedItem("name").getNodeValue();
                     neighbors.add(neighbor);
                  }
               }
               System.out.println("Number of neighbors = " + neighbors.size());
               System.out.println("Containing: " + neighbors.toString());
               // neighbors.clear();

         } else if ("area".equals(sub.getNodeName())) {
               sceneX = Integer.parseInt(sub.getAttributes().getNamedItem("x").getNodeValue());
               System.out.print("Scene Dimensions: x = " + sceneX);
               sceneY = Integer.parseInt(sub.getAttributes().getNamedItem("y").getNodeValue());
               System.out.print(", y = " + sceneY);
               sceneH = Integer.parseInt(sub.getAttributes().getNamedItem("h").getNodeValue());
               System.out.print(", h = " + sceneH);
               sceneW = Integer.parseInt(sub.getAttributes().getNamedItem("w").getNodeValue());
               System.out.println(", w =" + sceneW);
               
         }
      }
      rooms[10] = new Room("trailer", sceneX, sceneY, sceneW, sceneH, neighbors);
      neighbors.clear();

      Node of = office.item(0);
      NodeList ofChildren = of.getChildNodes();
      for (int o = 0; o < ofChildren.getLength(); o++) {
         Node sub = ofChildren.item(o);
         if ("neighbors".equals(sub.getNodeName())) {
            NodeList neighborChildren = sub.getChildNodes();
            for (int k = 0; k < neighborChildren.getLength(); k++) {
               Node neighborSub = neighborChildren.item(k);
               if ("neighbor".equals(neighborSub.getNodeName())) {
                  String neighbor = neighborSub.getAttributes().getNamedItem("name").getNodeValue();
                  neighbors.add(neighbor);
               }
            }
            System.out.println("Number of neighbors = " + neighbors.size());
            System.out.println("Containing neighbors: " + neighbors.toString());
            // neighbors.clear();

         } else if ("area".equals(sub.getNodeName())) {
            sceneX = Integer.parseInt(sub.getAttributes().getNamedItem("x").getNodeValue());
            System.out.print("Scene Dimensions: x = " + sceneX);
            sceneY = Integer.parseInt(sub.getAttributes().getNamedItem("y").getNodeValue());
            System.out.print(", y = " + sceneY);
            sceneH = Integer.parseInt(sub.getAttributes().getNamedItem("h").getNodeValue());
            System.out.print(", h = " + sceneH);
            sceneW = Integer.parseInt(sub.getAttributes().getNamedItem("w").getNodeValue());
            System.out.println(", w =" + sceneW);
         }
      }
      rooms[11] = new CastingOffice("office", sceneX, sceneY, sceneW, sceneH, neighbors);
      neighbors.clear();
      
      // for(int i = 0; i < 12; i++) {
      //    System.out.println(rooms[i].GetName());
      //    for (String element : rooms[i]._NeigborNames) {
      //       System.out.println("Neighbor = " +element);
      //    }
      //   System.out.println();
      // }

      return rooms;
   }



}//class