package org.model;

import org.controller.SceneManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Set extends Room
{
    Scene _Scene;

    ArrayList<Role> _OffCardRoles;

    int _Shots;

    public Set(String name, int x, int y, int w, int h, List<String> neighborNames, Scene scene, List<Role> offCardRoles, int takes)
    {
        super(name, x, y, w, h, neighborNames);

        _OffCardRoles = new ArrayList<>();

        for (Role role:offCardRoles)
        {
            _OffCardRoles.add(role);
        }

        _Scene = scene;

        if(_SetShots.containsKey(name))
        {
            _Shots = _SetShots.get(name);
        } else
        {
            _Shots = 2;
        }
    }

    public Role GetRoleByName(String roleName)
    {
        for(Role r: _OffCardRoles)
        {
            if(r.getName().equals(roleName))
            {
                return r;
            }
        }

        for(Role r: _Scene.getRoles())
        {
            if(r.getName().equals(roleName))
            {
                return r;
            }
        }

        return null;
    }

    public ArrayList<Role> GetAllRoles()
    {
        ArrayList<Role> roles = new ArrayList<>();

        for(Role r: _OffCardRoles)
        {
            roles.add(r);
        }

        for(Role r: _Scene.getRoles())
        {
            roles.add(r);
        }

        return roles;
    }

    public ArrayList<Role> GetAvailableRoles()
    {
        ArrayList<Role> roles = new ArrayList<>();

        for(Role r: _OffCardRoles)
        {
            if(!r.isOccupied())
            {
                roles.add(r);
            }
        }


        for(Role r: _Scene.getRoles())
        {
            if(!r.isOccupied())
            {
                roles.add(r);
            }
        }

        return roles;
    }

    public boolean HasAvailableRank(int rank)
    {//System.out.println(rank);
        ArrayList<Role> available = GetAvailableRoles();

        for (Role r: available)
        {
            if(r.getRank() <= rank)
            {
                //System.out.println(r.getName());
                return true;
            }
        }

        return false;
    }

    public boolean HasRoleName(String roleName)
    {
        for(Role r: _OffCardRoles)
        {
            if(r.getName().equals(roleName))
            {
                return true;
            }
        }

        for(Role r: _Scene.getRoles())
        {
            if(r.getName().equals(roleName))
            {
                return true;
            }
        }

        return false;
    }

    public boolean IsRoleOffCard(Role role)
    {
        return _OffCardRoles.contains(role);
    }

    public int GetShots()
    {
        return _Shots;
    }

    public void DecrementShots()
    {
        _Shots -= 1;

        if(_Shots <= 0)
        {
            // Wrap Scenes Here
            SceneManager.Instance.wrapFilming(this);
        }
    }

    public void RequestNewScene()
    {
        _Scene = SceneManager.Instance.DrawScene();
    }

    public void ClearScene()
    {
        _Scene = null;
    }
    
    public Scene getScene() {
        return _Scene;
    }

    private static HashMap<String, Integer> _SetShots = new HashMap<>(Map.of(
            "Train Station", 3,
            "Secret Hideout", 3,
            "Church", 2,
            "Hotel", 3,
            "Main Street", 3,
            "Jail", 1,
            "General Store", 2,
            "Ranch", 2,
            "Bank", 1,
            "Saloon", 2
    ));
}
