package org.model;

import java.util.ArrayList;
import java.util.List;

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
    }

    public Role GetRoleByName(String roleName)
    {
        for(Role r: _OffCardRoles)
        {
            if(r.getName() == roleName)
            {
                return r;
            }
        }

        for(Role r: _Scene.getRoles())
        {
            if(r.getName() == roleName)
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

    public boolean HasRoleName(String roleName)
    {
        for(Role r: _OffCardRoles)
        {
            if(r.getName() == roleName)
            {
                return true;
            }
        }

        for(Role r: _Scene.getRoles())
        {
            if(r.getName() == roleName)
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
        }
    }
}
