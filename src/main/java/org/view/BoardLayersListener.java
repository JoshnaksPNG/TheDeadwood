package org.view;

import java.awt.*;
import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

import org.controller.BoardManager;
import org.controller.SceneManager;
import org.controller.System;
import org.controller.System.TurnDetails;
import org.controller.System.TurnDetails.ActionType;
import org.model.*;

import javax.imageio.ImageIO;
import javax.smartcardio.Card;

import java.awt.event.*;
import java.util.ArrayList;
import java.util.HashMap;

public class BoardLayersListener extends JFrame implements IView {

   ArrayList<Player> _AllPlayers;

  // JLabels
   JLabel boardlabel;
   JLabel cardlabel;
   JLabel playerlabel;
   JLabel mLabel;
   JLabel[] playerInfo = {new JLabel("Player 1"), new JLabel("Player 2"), new JLabel("Player 3"), new JLabel("Player 4"), 
                           new JLabel("Player 5"), new JLabel("Player 6"), new JLabel("Player 7"), new JLabel("Player 8")};

   //JButtons
   JButton bAct;
   JButton bRehearse;
   JButton bMove;
   JButton bTakeRole;
   JButton bUpgrade;
   JButton bEndTurn;

   ImageIcon icon;
   
   // JLayered Pane
   JLayeredPane bPane;

   JTextPane textPane;
   JScrollPane scrollPane;
  
  
   // Constructor
   public BoardLayersListener() {
      
      // Set the title of the JFrame
      super("Deadwood");
      // Set the exit option for the JFrame
      setDefaultCloseOperation(EXIT_ON_CLOSE);
   
      // Create the JLayeredPane to hold the display, cards, dice and buttons
      bPane = getLayeredPane();
   
      // Create the deadwood board
      boardlabel = new JLabel();
      icon =  new ImageIcon("src/main/java/org/assets/board.jpg");
      boardlabel.setIcon(icon); 
      boardlabel.setBounds(0,0,icon.getIconWidth(),icon.getIconHeight());
   
      // Add the board to the lowest layer
      bPane.add(boardlabel, Integer.valueOf(0));
   
      // Set the size of the GUI
      setSize(icon.getIconWidth()+200,icon.getIconHeight());
      
      // Add a scene card to this room
      cardlabel = new JLabel();
      ImageIcon cIcon =  new ImageIcon("01.png");
      cardlabel.setIcon(cIcon); 
      cardlabel.setBounds(20,65,cIcon.getIconWidth()+2,cIcon.getIconHeight());
      cardlabel.setOpaque(true);
   
      // Add the card to the lower layer
      bPane.add(cardlabel, Integer.valueOf(1));
   
      // Add a dice to represent a player. 
      // Role for Crusty the prospector. The x and y co-ordiantes are taken from Board.xml file
      // playerlabel = new JLabel();
      // ImageIcon pIcon = new ImageIcon("r2.png");
      // playerlabel.setIcon(pIcon);
      // //playerlabel.setBounds(114,227,pIcon.getIconWidth(),pIcon.getIconHeight());  
      // playerlabel.setBounds(114,227,46,46);
      // playerlabel.setVisible(false);
      // bPane.add(playerlabel, Integer.valueOf(3));

      addButtons(icon);

      // cardlabel = new JLabel();
      // ImageIcon cardImage =  new ImageIcon("src/main/java/org/assets/cards/01.png");
      // cardlabel.setIcon(cardImage);
      // cardlabel.setBounds(100,100,cardImage.getIconWidth(),cardImage.getIconHeight());
      // cardlabel.setOpaque(true);

      // Add the card to the lower layer
      bPane.add(cardlabel, Integer.valueOf(2));
      // addScenes();



      String[] options = {"Option 1", "Option 2", "Option 3"};
      JComboBox<String> comboBox = new JComboBox<>(options);

      comboBox.setBounds(icon.getIconWidth()+150, 120, 100, 20);
      
      // Add an action listener to the combo box to get the selected item when changed
      comboBox.addActionListener(e -> {
          // Get the selected item
          String selectedOption = (String) comboBox.getSelectedItem();
          
          // Print the selected option (or use it for other purposes)
          try {
            addText(textPane, "Selected option " + selectedOption);
            comboBox.setVisible(false);
         } catch (BadLocationException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
         }
      });
      
      // Add the combo box to the frame
      bPane.add(comboBox, Integer.valueOf(4));

      setEventsText(); // noneditable text box that shows the system messages

      bPane.setVisible(true);
   }
   

   // add the buttons to the board
   public void addButtons(ImageIcon icon) {
      // Create the Menu for action buttons
      mLabel = new JLabel("MENU");
      mLabel.setBounds(icon.getIconWidth()+40,0,100,20);
      bPane.add(mLabel, Integer.valueOf(2));

      // Create Action buttons
      bAct = new JButton("ACT");
      bAct.setBackground(Color.white);
      bAct.setBounds(icon.getIconWidth()+10, 30,100, 20);
      bAct.addMouseListener(new boardMouseListener());

      bRehearse = new JButton("REHEARSE");
      bRehearse.setBackground(Color.white);
      bRehearse.setBounds(icon.getIconWidth()+10,60,100, 20);
      bRehearse.addMouseListener(new boardMouseListener());

      bMove = new JButton("MOVE");
      bMove.setBackground(Color.white);
      bMove.setBounds(icon.getIconWidth()+10,90,100, 20);
      bMove.addMouseListener(new boardMouseListener());

      bTakeRole = new JButton("TAKE ROLE");
      bTakeRole.setBackground(Color.white);
      bTakeRole.setBounds(icon.getIconWidth()+10, 120, 100, 20);
      bTakeRole.addMouseListener(new boardMouseListener());

      bUpgrade = new JButton("Upgrade");
      bUpgrade.setBackground(Color.white);
      bUpgrade.setBounds(icon.getIconWidth()+10,150,100, 20);
      bUpgrade.addMouseListener(new boardMouseListener());

      bEndTurn = new JButton("END TURN");
      bEndTurn.setBackground(Color.white);
      bEndTurn.setBounds(icon.getIconWidth()+10,180,100, 20);
      bEndTurn.addMouseListener(new boardMouseListener());


      // Place the action buttons in the top layer
      bPane.add(bAct, Integer.valueOf(2));
      bPane.add(bRehearse, Integer.valueOf(2));
      bPane.add(bMove, Integer.valueOf(2));
      bPane.add(bUpgrade, Integer.valueOf(2));
      bPane.add(bTakeRole, Integer.valueOf(2));
      bPane.add(bEndTurn, Integer.valueOf(2));
   }

   public void setEventsText() {
      textPane = new JTextPane();

      // Set some initial text in the JTextPane
      textPane.setText("Beginning Deadwood\n");

      // Set the JTextPane to be non-editable (optional)
      textPane.setEditable(false);

      // Create a JScrollPane and place the JTextPane inside it
      scrollPane = new JScrollPane(textPane);

      // Set the position and size for the JScrollPane using setBounds(x, y, width, height)
      scrollPane.setBounds(icon.getIconWidth()+10,300,200, 200);
      scrollPane.getVerticalScrollBar().addAdjustmentListener(new AdjustmentListener() {  
         public void adjustmentValueChanged(AdjustmentEvent e) {  
             e.getAdjustable().setValue(e.getAdjustable().getMaximum());  
         }
     });
      // Add the JScrollPane to the JLayeredPane
      bPane.add(scrollPane, Integer.valueOf(1));
   }

   // add scenes to the board, skeleton
   public void addScenes(){
      ArrayList<Room> rooms = BoardManager.Instance.GetAllRoomReadOnly();
      for (int i = 0; i < 10; i++) { // There are 10 rooms that are sets and they should be 0-9 inclusive
         if (rooms.get(i) instanceof Set) {
            Set s = (Set) rooms.get(i);
            placeScene(s);
            // placeCardBack(s);
         }
      }
   }
  
   // place the cards to the board, skeleton
   public void placeScene(Set s){
      cardlabel = new JLabel();
      ImageIcon cardImage =  new ImageIcon("src/main/java/org/assets/cards/" + s.getScene().getImg());

      try {
         addText(textPane, "Image = " + cardImage + " Dimentions w " + cardImage.getIconWidth() + 
         " height = " + cardImage.getIconHeight());
      } catch (BadLocationException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }

      cardlabel.setIcon(cardImage);
      cardlabel.setBounds(s.getX(),s.getY(),cardImage.getIconWidth(),cardImage.getIconHeight());
      cardlabel.setOpaque(true);
      // Add the card to the lower layer
      bPane.add(cardlabel, Integer.valueOf(2));
   }
      
      
   // place the back of the cards to the board, skeleton
   public void placeCardBack(Set s){
      cardlabel = new JLabel();
      ImageIcon cardImage =  new ImageIcon("src/main/java/org/assets/cardBack.png");
      cardlabel.setIcon(cardImage);
      cardlabel.setBounds(s.getX(),s.getY(),cardImage.getIconWidth(),cardImage.getIconHeight());
      cardlabel.setOpaque(true);
      // Add the card back on top of card
      bPane.add(cardlabel, Integer.valueOf(3));
   }

   public void promptRolesSelections(Player player) {
      // Create a JComboBox with some items
      Room currRoom = player.getCurrentRoom();

      if (currRoom.GetName().equals("office") || currRoom.GetName().equals("trailer")) {
         try {
            addText(textPane, "No roles in this area");
         } catch (BadLocationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
         }
      } else {

         Set set = (Set) currRoom;
         ArrayList<Role> roles = set.GetAllRoles();
         String[] items = new String[roles.size()];

         for (int i = 0; i < roles.size(); i++) {
            items[i] = roles.get(i).getName();
         }
         JComboBox<String> comboBox = new JComboBox<>(items);

         // Set the position and size of the JComboBox using setBounds(x, y, width, height)
         comboBox.setBounds(icon.getIconWidth()+150, 120, 100, 20);

         comboBox.addActionListener(e -> {
            // Get the selected item and perform whatever action here
            String selectedOption = (String) comboBox.getSelectedItem();
            
            // Print the selected option
            try {
               addText(textPane, "Selected " + selectedOption);
               comboBox.setVisible(false);
            } catch (BadLocationException e1) {
               // TODO Auto-generated catch block
               e1.printStackTrace();
            }
        });
   
         // Add the JComboBox to the JLayeredPane at layer 1
         bPane.add(comboBox, Integer.valueOf(1)); // Adding at layer 1
      }

      
  }

   // void for now, might become System.TurnDetails
   private void PromptMove(Player player){
      boolean canTakeRole = player.getCurrentRoom() instanceof Set &&
               ((Set)player.getCurrentRoom()).getScene() != null &&
               ((Set) player.getCurrentRoom()).HasAvailableRank(player.getRank());

      Room currRoom = player.getCurrentRoom();
      ArrayList<String> neighbors = currRoom.GetNeighborNames();
      String[] items = new String[neighbors.size()];

      for (int i = 0; i < neighbors.size(); i++) {
         items[i] = neighbors.get(i);
      }
      JComboBox<String> comboBox = new JComboBox<>(items);

      // Set the position and size of the JComboBox using setBounds(x, y, width, height)
      comboBox.setBounds(icon.getIconWidth()+150,90,100, 20);

      comboBox.addActionListener(e -> {
         // Get the selected item and perform whatever action here
         String selectedOption = (String) comboBox.getSelectedItem();
         
         // Print the selected option
         try {
            addText(textPane, "Selected " + selectedOption);
         } catch (BadLocationException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
         }
         comboBox.setVisible(false);
      });

      // Add the JComboBox to the JLayeredPane at layer 1
      bPane.add(comboBox, Integer.valueOf(1)); // Adding at layer 1
   }

   
  




   @Override
   public void AddPlayer(Player player, Room room) {

   }

   @Override
   public void PostPlayerMove(Player player, Room room) {

   }

   @Override
   public void PlayerTakeRole(Player player, Role role) {

   }

   @Override
   public void PlayerReleaseRole(Player player) {

   }

   @Override
   public void DisplayPlayerCurrency(Player player) {

   }

   @Override
   public void PlayerPaid(Player player, int amount, boolean isMoney) {

   }

   @Override
   public TurnDetails PromptPlayerTurnAction(Player player) {
      // TODO Auto-generated method stub
      throw new UnsupportedOperationException("Unimplemented method 'PromptPlayerTurnAction'");
   }

   @Override
   public int PromptPlayerAmount() {
      int numPlayers = 0;
      String[] options = new String[] {"2", "3", "4", "5", "6", "7", "8"};
      int option =  JOptionPane.showOptionDialog(null, "How many players are playing?", "Message",
            JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,null, options, options[0]);
      try {
         addText(textPane, options[option] + " Players are playing");
      } catch (BadLocationException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }
      return Integer.parseInt(options[option]);
   }

   @Override
   public void EndDay(int day) {
      
      try {
         addText(textPane, "Ending Day");
      } catch (BadLocationException e1) {
         // TODO Auto-generated catch block
         e1.printStackTrace();
      }
   }

   @Override
   public void BeginDay(int DayNumber) {
      addScenes();

      try {
         addText(textPane, "Starting new Day");
      } catch (BadLocationException e1) {
         // TODO Auto-generated catch block
         e1.printStackTrace();
      }




   }

   @Override
   public void EndGame() {

   }

   @Override
   public void PostPlayerAct(Player player, Role role, boolean isSuccess) {

   }

   @Override
   public void PostPlayerRehearse(Player player) {

   }

   @Override
   public void SceneWrappedOnSet(Set set) {

   }


   // Method to append text to the JTextPane
   private void addText(JTextPane textPane, String text) throws BadLocationException {
      Document doc = textPane.getDocument();
      doc.insertString(doc.getLength(), text + "\n", null); // Append text at the end
   }














   // This class implements Mouse Events
   class boardMouseListener implements MouseListener{
  
      // Code for the different button clicks
      public void mouseClicked(MouseEvent e) {
         
         if (e.getSource()== bAct){
            playerlabel.setVisible(true);
            // System.out.println("Acting is Selected\n");
            try {
               addText(textPane, "Act was selected");
            } catch (BadLocationException e1) {
               // TODO Auto-generated catch block
               e1.printStackTrace();
            }
            
            // if (System.INSTANCE.getActivePlayer().isInRole()) {
            //    // act role
            // } else {
            //    // not in role so can't act
            // }
         }
         else if (e.getSource()== bRehearse){
            // System.out.println("Rehearse is Selected\n");
            try {
               addText(textPane, "Rehearse was selected");
            } catch (BadLocationException e1) {
               // TODO Auto-generated catch block
               e1.printStackTrace();
            }
         }
         else if (e.getSource()== bMove){
            // System.out.println("Move is Selected\n");
            try {
               addText(textPane, "Move was selected");
            } catch (BadLocationException e1) {
               // TODO Auto-generated catch block
               e1.printStackTrace();
            }
         } else if (e.getSource() == bTakeRole) {
            try {
               addText(textPane, "Take role was selected");
            } catch (BadLocationException e1) {
               // TODO Auto-generated catch block
               e1.printStackTrace();
            }
         } else if (e.getSource() == bUpgrade) {
            // System.out.println("Upgrade was selected\n");
            try {
               addText(textPane, "Upgrade was selected");
            } catch (BadLocationException e1) {
               // TODO Auto-generated catch block
               e1.printStackTrace();
            }
         } else if (e.getSource() == bEndTurn) {
            try {
               addText(textPane, "End Turn was selected");
            } catch (BadLocationException e1) {
               // TODO Auto-generated catch block
               e1.printStackTrace();
            }
         }  
      }
      public void mousePressed(MouseEvent e) {
      }
      public void mouseReleased(MouseEvent e) {
      }
      public void mouseEntered(MouseEvent e) {
      }
      public void mouseExited(MouseEvent e) {
      }
   }


  public static void main(String[] args) {
  
   BoardLayersListener board = new BoardLayersListener();
   board.setVisible(true);
   
   // Take input from the user about number of players
   // String numPlayers = JOptionPane.showInputDialog(board, "How many players?"); 
   board.PromptPlayerAmount();
   //  System.out.println("Num players = " + numPlayers);
  }



}