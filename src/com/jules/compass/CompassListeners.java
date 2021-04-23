package com.jules.compass;

import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.*;


public class CompassListeners implements Listener {

   private List<Pair<Player, Integer>> listPlayers = new ArrayList<Pair<Player, Integer>>();

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event)
    {
        Player player = event.getPlayer();
        if( player.getInventory().getItemInMainHand().getType() == Material.COMPASS)
        {
            World world = player.getWorld();
            Chunk player_chunk = world.getChunkAt(player.getLocation());
            int maxHeight = world.getMaxHeight();
            int total = this.CountResources(player_chunk,maxHeight);

            for (Pair<Player, Integer> pair : this.listPlayers) {

                if(pair.getL() == player && pair.getR() != total)
                {
                    this.listPlayers.remove(pair);
                    this.listPlayers.add(new Pair<Player, Integer>(player,total));
                    player.sendMessage("There is "+total+" chest or furnace in this chunk.");
                    break;
                }
            }
        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event)
    {
        this.listPlayers.add(new Pair<Player, Integer>(event.getPlayer(),0));
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event)
    {
        this.removePlayerFromList(event.getPlayer());
    }

    private void removePlayerFromList(Player player)
    {
        for (Pair<Player, Integer> pair : this.listPlayers) {

            if(pair.getL() == player)
            {
                this.listPlayers.remove(pair);
                break;
            }
        }
    }

    private int CountResources(Chunk chunk, int maxHeight)
    {
        final int minX = chunk.getX() << 4;
        final int minZ = chunk.getZ() << 4;
        final int maxX = minX | 15;
        final int maxZ = minZ | 15;
        int total = 0;

        for (int x = minX; x <= maxX; ++x) {
            for (int y = 0; y <= maxHeight; ++y) {
                for (int z = minZ; z <= maxZ; ++z) {
                    Material block = chunk.getBlock(x,y,z).getType();
                    if(block == Material.CHEST || block == Material.FURNACE)
                    {
                        total++;
                    }
                }
            }
        }

        return total;
    }
}
