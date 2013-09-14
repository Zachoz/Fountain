package com.zachoz.fountain;

import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.List;

public class Fountain {

    Location location;
    String name;
    FountainRunner runner;

    public static int nextID;

    public Fountain(String name, Location loc, boolean isNew) {
        this.location = loc;
        this.name = name;
        FountainPlugin.fountains.put(name, this);
        this.runner = new FountainRunner(this);
        if (isNew) saveToConfig();
    }

    /**
     * Get's the Fountain's location
     *
     * @return The Fountain's location
     */
    public Location getLocation() {
        return this.location;
    }

    /**
     * Get the name of the Fountain
     *
     * @return Name of the Fountain
     */
    public String getName() {
        return this.name;
    }

    /**
     * Removes a fountain
     *
     * @param permanemt Whether or not to remove it from persistence
     */
    public void remove(boolean permanemt) {
        runner.valid = false;
        runner = null;
        if (permanemt) {
            List<String> list = FountainPlugin.getInstance().getConfig().getStringList("fountains");
            list.remove(toString());
            FileConfiguration config = FountainPlugin.getInstance().getConfig();
            config.set("fountains", list);
            FountainPlugin.getInstance().saveConfig();
        }

        FountainPlugin.fountains.remove(this.getName());
        this.location = null;
    }

    public void saveToConfig() {
        List<String> list = FountainPlugin.getInstance().getConfig().getStringList("fountains");
        list.add(toString());
        FileConfiguration config = FountainPlugin.getInstance().getConfig();
        config.set("fountains", list);
        FountainPlugin.getInstance().saveConfig();
    }

    @Override
    public String toString() {
        return getName() + ":" + getLocation().getWorld().getName() + ":" + getLocation().getX()
                + ":" + getLocation().getY() + ":" + getLocation().getZ();
    }

}
