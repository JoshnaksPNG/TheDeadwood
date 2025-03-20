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
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;

public class BoardLayersListener extends JFrame implements IView
{
    ArrayList<Player> _AllPlayers = new ArrayList<>(8);
    private static HashMap<Integer, JLabel[]> playerLabels = new HashMap<>();

    HashMap<Player, PlayerDetails> PlayerBoardDetails;

    HashMap<Integer, ImageIcon> PlayerRankDice;

    // JLabels
    JLabel boardlabel;
    JLabel cardlabel;
    JLabel playerlabel;
    JLabel pRankLabel;
    JLabel mLabel;
    JLabel take;
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
    ImageIcon shot = new ImageIcon("src/main/java/org/assets/shot.png");

    // JLayered Pane
    JLayeredPane bPane;
    JPanel infoPanel;

    JTextPane textPane;
    JScrollPane scrollPane;

    // ArrayList<JLabel[]> takes;
  
  
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
        setSize(icon.getIconWidth()+400,icon.getIconHeight()+200);
      
        // Add a scene card to this room
        cardlabel = new JLabel();
        // ImageIcon cIcon =  new ImageIcon("01.png");
        // cardlabel.setIcon(cIcon);
        // cardlabel.setBounds(20,65,cIcon.getIconWidth()+2,cIcon.getIconHeight());
        // cardlabel.setOpaque(true);
   
        // Add the card to the lower layer
        // bPane.add(cardlabel, Integer.valueOf(1));
   
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
        addInfoPanel();


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

        PlayerBoardDetails = new HashMap<>(8);
        PlayerRankDice = new HashMap<>(6);

        {
            PlayerRankDice.put(1, new ImageIcon("src/main/java/org/assets/dice/DieOne.png"));
            PlayerRankDice.put(2, new ImageIcon("src/main/java/org/assets/dice/DieTwo.png"));
            PlayerRankDice.put(3, new ImageIcon("src/main/java/org/assets/dice/DieThree.png"));
            PlayerRankDice.put(4, new ImageIcon("src/main/java/org/assets/dice/DieFour.png"));
            PlayerRankDice.put(5, new ImageIcon("src/main/java/org/assets/dice/DieFive.png"));
            PlayerRankDice.put(6, new ImageIcon("src/main/java/org/assets/dice/DieSix.png"));
        }
      
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

    public void addInfoPanel() {
        infoPanel = new JPanel();
        infoPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 15, 5)); // Left-aligned, spaced out
        infoPanel.setBackground(new Color(0, 0, 0, 150)); // Semi-transparent black
        infoPanel.setBounds(50, icon.getIconHeight()+10, 1400, 80); // Adjust position and size
        bPane.add(infoPanel, Integer.valueOf(1));
    }

    public void initializePlayerInfo(Player player) {
        JPanel playerPanel = new JPanel(new GridLayout(4, 1)); // 4 rows for structured data
        playerPanel.setPreferredSize(new Dimension(150, 70)); // Fixed size for consistency
        playerPanel.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2)); // White border
        playerPanel.setBackground(new Color(50, 50, 50, 200)); // Darker semi-transparent
    
        // Create labels for player information
        JLabel nameLabel = new JLabel("Player: " + player.getPlayerNumber());
        JLabel rankLabel = new JLabel("Rank: " + player.getRank());
        JLabel moneyLabel = new JLabel("Money: $" + player.getMoney());
        JLabel creditLabel = new JLabel("Credits: " + player.getCredit());
        JLabel pchipLevel = new JLabel("PChips: " + player.getPracticeChips());
    
        // Set text color
        nameLabel.setForeground(Color.WHITE);
        rankLabel.setForeground(Color.WHITE);
        moneyLabel.setForeground(Color.WHITE);
        creditLabel.setForeground(Color.WHITE);
        pchipLevel.setForeground(Color.WHITE);
    
        // Add labels to the player panel
        playerPanel.add(nameLabel);
        playerPanel.add(rankLabel);
        playerPanel.add(moneyLabel);
        playerPanel.add(creditLabel);
        playerPanel.add(pchipLevel);
    
        // Store references to the labels for updates
        playerLabels.put(player.getPlayerNumber(), new JLabel[]{rankLabel, moneyLabel, creditLabel, pchipLevel});
    
        // Add player panel to the info panel
        infoPanel.add(playerPanel,Integer.valueOf(4));
    }

    public void updatePlayerInfo() {
        for (Player player : _AllPlayers) {
            if (playerLabels.containsKey("" + player.getPlayerNumber())) {
                JLabel[] labels = playerLabels.get("" + player.getPlayerNumber());
                labels[0].setText("Rank: " + player.getRank());
                labels[1].setText("Money: $" + player.getMoney());
                labels[2].setText("Credits: " + player.getCredit());
                labels[3].setText("PChips: " + player.getPracticeChips());
            }
        }
    }

    public void setEventsText() {
        textPane = new JTextPane();

        // Set some initial text in the JTextPane
        textPane.setText("Beginning Deadwood\n");

        // Set the JTextPane to be non-editable (optional)
        textPane.setEditable(false);

        // Create a JScrollPane and place the JTextPane inside it
        scrollPane = new JScrollPane(textPane);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

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
                // s.printTakesList();
                placeScene(s);
                placeCardBack(s);
                placeTakes(s);
            }
        }
    }
  
    // place the cards to the board, skeleton
    public void placeScene(Set s){
        cardlabel = new JLabel();
        ImageIcon cardImage =  new ImageIcon("src/main/java/org/assets/cards/" + s.getScene().getImg());
        cardlabel.setIcon(cardImage);
        cardlabel.setBounds(s.getX(),s.getY(),cardImage.getIconWidth(),cardImage.getIconHeight());
        cardlabel.setOpaque(true);
        
        s.getScene().setImage(cardlabel);
        // Add the card to the lower layer
        bPane.add(cardlabel, Integer.valueOf(2));        
        bPane.revalidate();
        bPane.repaint();
    }
      
      
// place the back of the cards to the board, skeleton
    public void placeCardBack(Set s){
        cardlabel = new JLabel();
        ImageIcon cardImage =  new ImageIcon("src/main/java/org/assets/cardBack.png");
        cardlabel.setIcon(cardImage);
        cardlabel.setBounds(s.getX(),s.getY(),cardImage.getIconWidth(),cardImage.getIconHeight());
        cardlabel.setOpaque(true);
        
        s.setCardBack(cardlabel);
        // Add the card back on top of card
        bPane.add(cardlabel, Integer.valueOf(3));
        bPane.revalidate();
        bPane.repaint();
    }

    public void placeTakes(Set s){
        ArrayList<int[]> takeList = s.getTakeList(); // coordinates for shot
        ArrayList<JLabel> takes = new ArrayList<>();
        for (int[] arr : takeList) {
           JLabel take = new JLabel();
           take.setIcon(shot);
           take.setBounds(arr[0], arr[1], arr[3], arr[2]);
           take.setOpaque(false);
           bPane.add(take, Integer.valueOf(4));
           takes.add(take);
        }  
        s.setTakes(takes);
        bPane.revalidate();
        bPane.repaint();
    }

    public void removeTake(Set s) {
        ArrayList<JLabel> takes = s.getTakes();
        bPane.remove(takes.remove(0));
        // s.setTakes(takes);
        bPane.revalidate();
        bPane.repaint();
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

    // remove a scene card from the board
    public void removeCard(Set set) {
        Scene scene = set.getScene();
        JLabel cardlabel = scene.getImage();
        bPane.remove(cardlabel);
        bPane.revalidate();
        bPane.repaint();
    }

    public void flipCard(Set set) {
        JLabel cardBack = set.getCardBack();
        bPane.remove(cardBack);
        bPane.revalidate();
        bPane.repaint();
    }
  




    @Override
    public void AddPlayer(Player player, Room room)
    {
        PlayerInitializerFrame initializerFrame = new PlayerInitializerFrame();

        PlayerDetails details = initializerFrame.PromptPlayerCreation();

        initializerFrame.dispose();

        PlayerBoardDetails.put(player, details);

        LogText("Initialized Player " + player.getPlayerNumber() + " as: " + details.PlayerName);
        
        initializePlayerInfo(player);

        _AllPlayers.add(player);

        initPlayerDie(player);

        setPlayerDie(player, BoardManager.Instance.GetTrailer().getX(), BoardManager.Instance.GetTrailer().getY());
    }

    @Override
    public void PostPlayerMove(Player player, Room room) {
        PlayerDetails details = PlayerBoardDetails.get(player);
        
        // issues with referencing objects, must create a new object for each image label
        /*
        bPane.remove(playerlabel);

        playerlabel = new JLabel(details.PlayerIcon);
        ImageIcon pIcon = details.PlayerIcon;
        playerlabel.setOpaque(false);
        playerlabel.setBounds(room.getX(),room.getY(),pIcon.getIconWidth(),pIcon.getIconHeight());
        playerlabel.setVisible(false);
        playerlabel.setVisible(true);
        bPane.add(playerlabel, Integer.valueOf(3));

        // issues with referencing objects, must create a new object for each image label
        bPane.remove(pRankLabel);

        pRankLabel = new JLabel(PlayerRankDice.get(player.getRank()));
        pRankLabel.setOpaque(false);
        pRankLabel.setBounds(room.getX(),room.getY(),pIcon.getIconWidth(),pIcon.getIconHeight());
        pRankLabel.setVisible(false);
        pRankLabel.setVisible(true);
        bPane.add(pRankLabel, Integer.valueOf(4));
        */

        setPlayerDie(player, room.getX(), room.getY());

        if(room instanceof Set) {
            Set set = (Set) room;
            if(set.getScene() != null) {
                set.getScene().flipCard();
                flipCard(set);
            }
        }

        bPane.revalidate();
        bPane.repaint();

        LogText("Player " + player.getPlayerNumber() + " moved to room: " + room.GetName());
    }

    @Override
    public void PlayerTakeRole(Player player, Role role) {
        LogText("Player take role");
        
        PlayerDetails details = PlayerBoardDetails.get(player);
        Room room = player.getCurrentRoom();
        int[] area = role.getArea();

        playerlabel = new JLabel(details.PlayerIcon);
        ImageIcon pIcon = details.PlayerIcon;
        playerlabel.setOpaque(false);
        if (role.isMain()) {
            playerlabel.setBounds(room.getX() + area[0],room.getY() + area[1],pIcon.getIconWidth(),pIcon.getIconHeight());
        } else {
            playerlabel.setBounds(area[0],area[1],pIcon.getIconWidth(),pIcon.getIconHeight());
        }
        playerlabel.setVisible(false);
        playerlabel.setVisible(true);
        bPane.add(playerlabel, Integer.valueOf(3));

        pRankLabel = new JLabel(PlayerRankDice.get(player.getRank()));
        pRankLabel.setOpaque(false);
        if (role.isMain()) {
            pRankLabel.setBounds(room.getX() + area[0],room.getY() + area[1],pIcon.getIconWidth(),pIcon.getIconHeight());
        } else {
            pRankLabel.setBounds(area[0],area[1],pIcon.getIconWidth(),pIcon.getIconHeight());
        }
        // pRankLabel.setBounds(room.getX() + area[0],room.getY() + area[1],pIcon.getIconWidth(),pIcon.getIconHeight());
        pRankLabel.setVisible(false);
        pRankLabel.setVisible(true);
        bPane.add(pRankLabel, Integer.valueOf(4));
        bPane.revalidate();
        bPane.repaint();

        LogText("Player " + player.getPlayerNumber() + " took role: " + role.getName());

    }

    @Override
    public void PlayerReleaseRole(Player player) {
        LogText("Player release role");
    }

    @Override
    public void DisplayPlayerCurrency(Player player) {
        LogText("Display player currency");
    }

    @Override
    public void PlayerPaid(Player player, int amount, boolean isMoney)
    {
        if (isMoney) {
            LogText("Player " + player.getPlayerNumber()+" paid " + amount + " Dollars");
        } else {
            LogText("Player " + player.getPlayerNumber()+" paid " + amount + " Credits");
        }

        updatePlayerInfo();
    }

    @Override
    public TurnDetails PromptPlayerTurnAction(Player player)
    {
        for(Player p: _AllPlayers)
        {
            updatePlayerDieRank(p);
            viewPlayerdie(p, false);

        }

        viewPlayerdie(player, true);

        LogText("Prompt player turn action");
        boolean isWorkValid = player.isInRole();

        boolean isUpgradeValid =
                (player.getCurrentRoom() instanceof CastingOffice) &&
                        (CastingOffice.CanUpgradePlayer(player));

        boolean canTakeRole = player.getCurrentRoom() instanceof Set &&
                ((Set)player.getCurrentRoom()).getScene() != null &&
                ((Set) player.getCurrentRoom()).HasAvailableRank(player.getRank());

        ArrayList<String> ActionOptions = new ArrayList<>();

        if(isWorkValid)
        {
            boolean canRehearse = player.getPracticeChips() <
                    ((Set)player.getCurrentRoom()).getScene().getBudget() - 1;

            ActionOptions.add("Act");

            if(canRehearse)
            {
                ActionOptions.add("Rehearse");
            }
        } else
        {
            if(player.GetCanMove())
            {
                ActionOptions.add("Move");
            }

            if(canTakeRole)
            {
                ActionOptions.add("Take Role");
            }
        }

        if(isUpgradeValid)
        {
            ActionOptions.add("Upgrade");
        }

        ActionOptions.add("Skip");

        String[] optionArray = new String[ActionOptions.size()];
        optionArray = ActionOptions.toArray(optionArray);

        int optionIDX = JOptionPane.showOptionDialog(null, "What action would you like to take?",
                PlayerBoardDetails.get(player).PlayerName + " (Player " + player.getPlayerNumber() + ") Action Selection", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, optionArray, optionArray[0]);

        String ChosenOption = optionArray[optionIDX];

        TurnDetails details = null;

        switch (ChosenOption)
        {
            case "Act" ->
            {
                details = new TurnDetails(ActionType.Act);
            }

            case "Rehearse" ->
            {
                details = new TurnDetails(ActionType.Rehearse);
            }

            case "Move" ->
            {
                details = PromptMoveRoom(player);
            }

            case "Take Role" ->
            {
                details = PromptRoleTaking(player);
            }

            case "Upgrade" ->
            {
                details = PromptUpgrade(player);
            }

            case "Skip" ->
            {
                details = new TurnDetails(ActionType.Skip);
            }
        }
        updatePlayerInfo();
        return details;
    }

    private System.TurnDetails PromptUpgrade(Player player)
    {
        boolean isMoneyValid = player.getMoney() >= CastingOffice.getCost(player.getRank() + 1, true);
        boolean isCreditsValid = player.getCredit() >= CastingOffice.getCost(player.getRank() + 1, false);

        ArrayList<String> UpgradeOptions = new ArrayList<>();

        if(isMoneyValid)
        {
            UpgradeOptions.add("Money");
        }

        if(isCreditsValid)
        {
            UpgradeOptions.add("Credits");
        }
        String[] optionArray = new String[UpgradeOptions.size()];
        optionArray = UpgradeOptions.toArray(optionArray);

        int optionIDX = JOptionPane.showOptionDialog(null, "How do you want to upgrade your rank?",
                PlayerBoardDetails.get(player).PlayerName + " (Player " + player.getPlayerNumber() + ") Upgrade Selection", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, optionArray, optionArray[0]);

        String ChosenOption = optionArray[optionIDX];

        TurnDetails.UpgradeCurrency currency = null;

        switch (ChosenOption)
        {
            case "Money" ->
            {
                currency = TurnDetails.UpgradeCurrency.Money;
            }

            case "Credits" ->
            {
                currency = TurnDetails.UpgradeCurrency.Credits;
            }
        }



        return new TurnDetails(ActionType.Upgrade, currency);
    }

    private System.TurnDetails PromptRoleTaking(Player player)
    {
        ArrayList<String> RoleOptions = new ArrayList<>();

        for(Role role: ((Set) player.getCurrentRoom()).GetAvailableRoles())
        {
            if(SceneManager.Instance.verifyRoleRequirement(player, null, role))
            {
                RoleOptions.add(role.getName());
            }
        }

        String[] optionArray = new String[RoleOptions.size()];
        optionArray = RoleOptions.toArray(optionArray);

        int optionIDX = JOptionPane.showOptionDialog(null, "Which role would you like to take?",
                PlayerBoardDetails.get(player).PlayerName + " (Player " + player.getPlayerNumber() + ") Role Selection", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, optionArray, optionArray[0]);

        String ChosenOption = optionArray[optionIDX];

        Role chosenRole = ((Set) player.getCurrentRoom()).GetRoleByName(ChosenOption);

        return new TurnDetails(TurnDetails.ActionType.TakeRole, chosenRole);
    }

    private System.TurnDetails PromptMoveRoom(Player player)
    {
        ArrayList<String> RoomOptions = new ArrayList<>();

        for(String neigborName: player.getCurrentRoom().GetNeighborNames())
        {
            RoomOptions.add(neigborName);
        }

        String[] optionArray = new String[RoomOptions.size()];
        optionArray = RoomOptions.toArray(optionArray);

        int optionIDX = JOptionPane.showOptionDialog(null, "Which room would you like to move to?",
                PlayerBoardDetails.get(player).PlayerName + " (Player " + player.getPlayerNumber() + ") Room Selection", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, optionArray, optionArray[0]);

        String ChosenOption = optionArray[optionIDX];

        Room chosenRoom = BoardManager.Instance.GetRoomByName(ChosenOption);

        return new TurnDetails(ActionType.Move, chosenRoom);
    }

    @Override
    public int PromptPlayerAmount() {
        int numPlayers = 0;
        String[] options = new String[] {"2", "3", "4", "5", "6", "7", "8"};
        int option =  JOptionPane.showOptionDialog(null, "How many players are playing?", "Player Selection",
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
        if (isSuccess)
        {
            LogText("\n\"" + role.getLine() + "\"");

            LogText("Player " + player.getPlayerNumber() +
                    " successfully acted out their role: " + role.getName() + ".\n" +
                    "A shot has been finished, and they have received payment.");
            Set set = (Set) player.getCurrentRoom();
            removeTake(set);
            
        } else
        {
            LogText("Player " + player.getPlayerNumber() +
                    " failed to act out their role: " + role.getName() + ".");
        }
    }

    @Override
    public void PostPlayerRehearse(Player player) {
        LogText("Player " + player.getPlayerNumber() +
                " rehearsed and earned a practice chip for a total of: " +
                player.getPracticeChips());
        updatePlayerInfo();
    }

    @Override
    public void PostPlayerSkip(Player player) {
        LogText("Player " + player.getPlayerNumber() +
                " skipped their turn!");
        updatePlayerInfo();
    }

    @Override
    public void SceneWrappedOnSet(Set set) {
        LogText("\nFilming of " + set.getScene().getName() +
                " has wrapped on " + set.GetName() +
                ". All Actors have been paid and released from their roles.");
        bPane.remove(set.getScene().getImage());
        set.ClearScene();
    }

    private void LogText(String text)
    {
        try
        {
            addText(textPane, text);
        } catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    // Method to append text to the JTextPane
    private void addText(JTextPane textPane, String text) throws BadLocationException {
        Document doc = textPane.getDocument();
        doc.insertString(doc.getLength(), text + "\n", null); // Append text at the end
    }


    private void setPlayerDie(Player p, int x, int y)
    {
        PlayerDetails details = PlayerBoardDetails.get(p);

        playerlabel = PlayerBoardDetails.get(p).DiePanel;
        ImageIcon pIcon = details.PlayerIcon;
        playerlabel.setOpaque(false);
        playerlabel.setBounds(x,y,pIcon.getIconWidth(),pIcon.getIconHeight());
        playerlabel.setVisible(false);
        playerlabel.setVisible(true);
        //bPane.add(playerlabel, Integer.valueOf(3));

        pRankLabel = PlayerBoardDetails.get(p).RankPanel;
        pRankLabel.setOpaque(false);
        pRankLabel.setBounds(x,y,pIcon.getIconWidth(),pIcon.getIconHeight());
        pRankLabel.setVisible(false);
        pRankLabel.setVisible(true);
        //bPane.add(pRankLabel, Integer.valueOf(4));
    }

    private void initPlayerDie(Player p)
    {
        PlayerDetails deets = PlayerBoardDetails.get(p);

        deets.DiePanel = new JLabel(deets.PlayerIcon);
        deets.RankPanel = new JLabel(PlayerRankDice.get(1));

        bPane.add(deets.DiePanel, Integer.valueOf(3));
        bPane.add(deets.RankPanel, Integer.valueOf(4));
    }

    private void updatePlayerDieRank(Player p)
    {
        PlayerDetails deets = PlayerBoardDetails.get(p);

        //deets.RankPanel.setVisible(false);

        deets.RankPanel.setIcon(PlayerRankDice.get(p.getRank()));
        // java.lang.System.out.println(p.getRank());
    }

    private void viewPlayerdie(Player p, boolean b)
    {
        PlayerDetails deets = PlayerBoardDetails.get(p);

        deets.DiePanel.setVisible(b);
        deets.RankPanel.setVisible(b);
    }

    private void loadPlayerLocations()
    {

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

    /*public static void main(String[] args) {
  
    BoardLayersListener board = new BoardLayersListener();
    board.setVisible(true);
   
    // Take input from the user about number of players
    // String numPlayers = JOptionPane.showInputDialog(board, "How many players?");
    board.PromptPlayerAmount();
    //  System.out.println("Num players = " + numPlayers);
    }*/


    public static class PlayerDetails
    {
        public PlayerDetails(String name, Color color, ImageIcon icon, ImageIcon pawn)
        {
            PlayerColor = color;
            PlayerName = name;
            PlayerIcon = icon;
            PlayerPawn = pawn;
        }

        public Color PlayerColor;

        public String PlayerName;

        public ImageIcon PlayerIcon;

        public ImageIcon PlayerPawn;

        public JLabel DiePanel;

        public JLabel RankPanel;
    }
}