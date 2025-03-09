package org.controller;

import org.model.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class SceneManager
{
    int _ActiveScenes;

    ArrayList<Scene> _SceneDeck;

    public SceneManager(List<Scene> scenes) {
        int activeScenes = 0;

        _SceneDeck = new ArrayList<>();

        for(Scene s: scenes)
        {
            _SceneDeck.add(s);
        }

        java.lang.System.out.println("Loaded Scenes: " + _SceneDeck.size());

        Instance = this;
    }

    public boolean verifyRoleRequirement(Player player, Scene scene, Role role) {
        if(player.getRank() >= role.getRank() && !role.isOccupied()) {
            return true;
        } else {
            return false;
        }
    }

    public void updateRole(Player player, Scene scene, Role role)
    {
        player.takeRole(scene, role);
        role.setActor(player);
    }

    public boolean rehearseScene(Player player, Scene scene)
    {
        if (player.getPracticeChips() >= scene.getBudget() - 1) {
            return false; // Player must act, they have the max amount of practice chips
        } else {
            int currentChips = player.getPracticeChips();
            player.setPracticeChips(currentChips + 1);

            System.INSTANCE.getView().PostPlayerRehearse(player);
            return true;
        }
    }

    public boolean actScene(Player player, Scene scene)
    {
        int roll = Dice.Instance.BoostedRoll(player.getPracticeChips());
        int budget = scene.getBudget();

        boolean actSuccess = roll >= budget;


        System.INSTANCE.getView().PostPlayerAct(player, player.getRole(), actSuccess);

        
        if(player.getRole().isMain()) { // on card role
            if (actSuccess) {
                CurrencyManager.updatePlayerCredit(player, 2);
            }
        } else { // off card role
            if (actSuccess) {
                CurrencyManager.updatePlayerCredit(player, 1);
                CurrencyManager.updatePlayerMoney(player, 1);
            } else { // acting failed
                CurrencyManager.updatePlayerMoney(player, 1);
            }
        }

        if(actSuccess)
        {
            ((Set)player.getCurrentRoom()).DecrementShots();
        }

        return actSuccess;
    }

    public Scene DrawScene()
    {
        Random r = new Random();

        ++_ActiveScenes;

        return _SceneDeck.remove(r.nextInt(_SceneDeck.size()));
    }

    public static void wrapPay(Set set) {
        ArrayList<Player> onCard = new ArrayList<Player>();
        ArrayList<Player> offCard = new ArrayList<Player>();
        for (Player player : set.getOccPlayers()) {
            if (player.getRole() != null) {
                if (player.getRole().isMain()) {
                    onCard.add(player);
                } else {
                    offCard.add(player);
                }
            }
        }
        //Collections.sort(onCard, Collections.reverseOrder());
        if (onCard.size() != 0) {
            mainPay(onCard, set.getScene());
            extraPay(offCard);
        }

    }

    public void wrapFilming(Set set)
    {
        wrapPay(set);

        for(Player p: set.getOccPlayers())
        {
            p.ResetAct();
        }

        System.INSTANCE.getView().SceneWrappedOnSet(set);

        set.ClearScene();

        --_ActiveScenes;
    }

    public boolean AreScenesWrapped()
    {
        return _ActiveScenes <= 1;
    }

    // method to be called by wrapPay based on players role type
    private static void mainPay(ArrayList<Player> onCard, Scene scene) {
        ArrayList<Integer> payDice = new ArrayList<Integer>();
        for (int i = 0; i < scene.getBudget(); i++) {
            payDice.add(Dice.roll());
        }
        Collections.sort(payDice);
        int numPlayers = onCard.size();
        int count = 0;
        for (int i : payDice) {
            CurrencyManager.updatePlayerMoney(onCard.get(count % numPlayers), i);
            count++;
        }
    }

    private static void extraPay(ArrayList<Player> offCard) {
        for (Player player : offCard) {
            CurrencyManager.updatePlayerMoney(player, player.getRole().getRank());
        }
    }

    public static SceneManager Instance;
}
