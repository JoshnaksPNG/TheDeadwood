package org.view;

import java.awt.*;
import javax.swing.*;

import org.model.Player;
import org.model.Role;
import org.model.Room;
import org.model.Set;
import org.controller.BoardManager;
import org.controller.SceneManager;
import org.controller.System;
import org.controller.System.TurnDetails;
import org.controller.System.TurnDetails.ActionType;
import org.model.*;

import javax.imageio.ImageIO;
import java.awt.event.*;
import java.util.ArrayList;

public class BoardLayersListener extends JFrame implements IView {

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
  
  // JLayered Pane
  JLayeredPane bPane;
  
  
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
      ImageIcon icon =  new ImageIcon("src/main/java/org/assets/board.jpg");
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
      playerlabel = new JLabel();
      ImageIcon pIcon = new ImageIcon("r2.png");
      playerlabel.setIcon(pIcon);
      //playerlabel.setBounds(114,227,pIcon.getIconWidth(),pIcon.getIconHeight());  
      playerlabel.setBounds(114,227,46,46);
      playerlabel.setVisible(false);
      bPane.add(playerlabel, Integer.valueOf(3));

      addButtons(icon);

      // JTextPane textPane = new JTextPane();

      // // Set the preferred size and position using setBounds(x, y, width, height)
      // textPane.setBounds(50, 50, 500, 200); // Position (50,50) and size (500x200)

      // // Optional: Set some text in the JTextPane
      // textPane.setText("This is a sample text in the JTextPane.\nYou can add more text here.\n");

      // // Set the text pane to be non-editable (optional)
      // textPane.setEditable(false);

      // // Add the JTextPane to the JLayeredPane
      // bPane.add(textPane, Integer.valueOf(5)); // Adding at layer 1



      JTextPane textPane = new JTextPane();

      // Set some initial text in the JTextPane
      textPane.setText("This is a sample text in the JTextPane.\nYou can add more text here.\n");

      // Set the JTextPane to be non-editable (optional)
      textPane.setEditable(false);

      // Create a JScrollPane and place the JTextPane inside it
      JScrollPane scrollPane = new JScrollPane(textPane);

      // Set the position and size for the JScrollPane using setBounds(x, y, width, height)
      scrollPane.setBounds(50, 50, 500, 200); // Position (50, 50) and size (500x200)

      // Add the JScrollPane to the JLayeredPane
      bPane.add(scrollPane, Integer.valueOf(1));






      
      // JPanel panel = new JPanel();
      // panel.setBounds(icon.getIconWidth() + 40, 200, 200, 400);
      // // panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
    
      // // Add a large number of labels to the panel.
      // for (int i = 1; i <= 50; i++) {
        
      //     JLabel label = new JLabel("Label " + i);
      //     panel.add(label);
        
      // }
    
      // // Create a JScrollPane and set the panel as its viewport.
      // JScrollPane scrollPane = new JScrollPane(panel);
      // bPane.add(scrollPane, Integer.valueOf(2));
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
      return 0;
   }

   @Override
   public void EndDay(int day) {
      
   }

   @Override
   public void BeginDay(int DayNumber) {
      ArrayList<Room> rooms = BoardManager.Instance.GetAllRoomReadOnly();




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
















   // This class implements Mouse Events
   class boardMouseListener implements MouseListener{
  
      // Code for the different button clicks
      public void mouseClicked(MouseEvent e) {
         
         if (e.getSource()== bAct){
            playerlabel.setVisible(true);
            // System.out.println("Acting is Selected\n");
            
            if (System.INSTANCE.getActivePlayer().isInRole()) {
               // act role
            } else {
               // not in role so can't act
            }
         }
         else if (e.getSource()== bRehearse){
            // System.out.println("Rehearse is Selected\n");
         }
         else if (e.getSource()== bMove){
            // System.out.println("Move is Selected\n");
         } else if (e.getSource() == bUpgrade) {
            // System.out.println("Upgrade was selected\n");
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
    String numPlayers = JOptionPane.showInputDialog(board, "How many players?"); 
   //  System.out.println("Num players = " + numPlayers);
  }



}