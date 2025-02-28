package org.controller;

import jdk.jshell.spi.ExecutionControl;

import org.model.CastingOffice;
import org.model.Dice;
import org.model.Player;
import org.model.Role;
import org.model.Room;
import org.model.Scene;
import java.util.ArrayList;
import java.util.Collections;

public class SceneManager
{
    int activeScenes;

    ArrayList<Scene> currScenes;

    boolean verifyRoleRequirement(Player player, Scene scene, Role role)
    {
        if(player.getRank() >= role.getRank()) {
            return true;
        } else {
            return false;
        }
    }

    void updateRole(Player player, Scene scene, Role role)
    {
        player.setRole(role);
        role.setActor(player);
    }

    boolean rehearseScene(Player player, Scene scene)
    {
        if (player.getPracticeChips() >= scene.getBudget() - 1) {
            return false; // Player must act, they have the max amount of practice chips
        } else {
            int currentChips = player.getPracticeChips();
            player.setPracticeChips(currentChips + 1);
            return true;
        }
    }

    boolean actScene(Player player, Scene scene)
    {
        int roll = Dice.roll(player.getPracticeChips());
        int buget = scene.getBudget();
        
        if(player.getRole().isMain()) { // on card role
            if (roll >= buget) {
                CurrencyManager.updatePlayerCredit(player, 2);
                return true;
            }
        } else { // off card role
            if (roll >= buget) {
                CurrencyManager.updatePlayerCredit(player, 1);
                CurrencyManager.updatePlayerMoney(player, 1);
                return true;
            } else { // acting failed
                CurrencyManager.updatePlayerMoney(player, 1);
            }
        }

        return false;
    }

    public static void wrapPay(Room room) {
        

    }

    // method to be called by wrapPay based on players role type
    private static void mainPay(Scene scene, ArrayList<Player> onCard) {

    }

    private static void extraPay(ArrayList<Player> offCard) {
        for (Player player : offCard) {
            CurrencyManager.updatePlayerMoney(player, player.getRole().getRank());
        }
    }
}
