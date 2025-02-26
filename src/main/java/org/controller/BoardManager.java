package org.controller;

import jdk.jshell.spi.ExecutionControl;
import org.model.Player;
import org.model.Room;

import java.util.ArrayList;

public class BoardManager
{
    Room[][] rooms;

    ArrayList<Room> _AllRooms;

    Room _Trailer;

    SceneManager _SceneManager;

    public BoardManager(SceneManager sceneManager)
    {
        _SceneManager = sceneManager;

        _AllRooms = new ArrayList<>();
    }

    /**
     * Checks to see if the target destination is a valid position
     * for the player to move to.
     *
     * @param player Player for which to check move validity
     * @param room Target room to check move validity
     *
     * */
    public boolean checkDestination(Player player, Room room)
    {
        Room initialPosition = player.getCurrentRoom();

        boolean isValidDestination = initialPosition.IsNeighboringRoom(room);

        return isValidDestination;
    }

    public void AddRoom(Room room)
    {
        if(!_AllRooms.contains(room))
        {
            _AllRooms.add(room);
        }
    }

    /**
     * Initialize a board
     * */
    public void initializeBoard()
    {
        for (Room r: _AllRooms)
        {
            r.AttachNeighbors(this);
        }
    }


    /**
     * Move a player to target position.
     *
     * @param player Player which will move
     * @param targetRoom Target room to move player to
     * */
    public void movePlayer(Player player, Room targetRoom)
    {
        player.getCurrentRoom().RemovePlayer(player);

        targetRoom.AddPlayer(player);
    }

    public Room GetRoomByName(String name)
    {
        for (Room r:_AllRooms)
        {
            if(r.GetName().equals(name))
            {
                return r;
            }
        }

        throw new RuntimeException("Board does not contain room with name: "+ name);
    }
}
