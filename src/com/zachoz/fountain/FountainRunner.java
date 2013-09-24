package com.zachoz.fountain;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.FallingBlock;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class FountainRunner {

    Fountain fountain;
    boolean valid = true;

    public FountainRunner(Fountain fountain) {
        this.fountain = fountain;
        run();
    }

    public void run() {
        new BukkitRunnable() {
            public void run() {
                if (!valid) this.cancel();
                try {
                    World world = fountain.getLocation().getWorld();
                    FallingBlock block = world.spawnFallingBlock(fountain.getLocation(), Material.WATER, (byte) 4);

                    float x = (float) Math.random() / 12;
                    float y = (float) 0.7;
                    float z = (float) Math.random() / 12;
                    if(Math.random() > 0.5){
                        x = x - (x * 2);
                    }
                    if(Math.random() > 0.5){
                        z = z - (z * 2);
                    }

                    block.setVelocity(new Vector(x, y, z));
                    block.setDropItem(false);
                } catch (NullPointerException ex) {
                    this.cancel();
                    // Doesn't matter. It's just that you may get ONE stack trace when removing the fountain
                }
            }
        }.runTaskTimer(FountainPlugin.getInstance(), 3L, 3L);
    }

    /* -- Sprinker vectors
    Random doubles from 0 1 with a 50% chance of being a minus, this makes it sploosh in all directions.
     */

}
