package org.model;

import org.controller.BoardManager;

import java.util.ArrayList;
import java.util.List;

public class Room
{
    ArrayList<Player> occPlayers;

    ArrayList<Room> NeighboringRooms;

    public ArrayList<String> _NeigborNames;

    String _Name;

    int _PosX;

    int _PosY;

    int _Width;

    int _Height;

    public Room(String name, int x, int y, int h, int w, List<String> neighborNames)
    {
        _Name = name;
        _PosX = x;
        _PosY = y;
        _Height = h;
        _Width = w;

        occPlayers = new ArrayList<>();

        _NeigborNames = new ArrayList<>();

        for (String neighborName: neighborNames)
        {
            _NeigborNames.add(neighborName);
        }
    }

    public void AttachNeighbors(BoardManager boardManager)
    {
        NeighboringRooms = new ArrayList<>(_NeigborNames.size());

        for (String neighborName: _NeigborNames)
        {
            NeighboringRooms.add(boardManager.GetRoomByName(neighborName));
        }
    }

    public boolean IsNeighboringRoom(Room neighbor)
    {
        boolean isValidNeighbor = NeighboringRooms.contains(neighbor);

        return isValidNeighbor;
    }

    public ArrayList<String> GetNeighborNames()
    {
        return _NeigborNames;
    }

    public boolean IsValidNeighborName(String name)
    {
        for(String n: _NeigborNames)
        {
            if(n.equals(name))
            {
                return true;
            }
        }

        return false;
    }

    public void AddPlayer(Player player)
    {
        occPlayers.add(player);
    }

    public void RemovePlayer(Player player)
    {
        occPlayers.remove(player);
    }

    public String GetName(){return _Name;}

    public ArrayList<Player> getOccPlayers() {
        return occPlayers;
    }
}
