package org.model;

import java.util.ArrayList;
import java.util.List;

public class Set extends Room
{
    Scene _Scene;

    ArrayList<Role> _OffCardRoles;

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
}
