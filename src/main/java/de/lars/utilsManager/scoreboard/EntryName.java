package de.lars.utilsManager.scoreboard;

import org.bukkit.ChatColor;

public enum EntryName {

    ENTRY_0(0, ChatColor.RED.toString()),
    ENTRY_1(1, ChatColor.YELLOW.toString()),
    ENTRY_2(2, ChatColor.BLUE.toString()),
    ENTRY_3(3, ChatColor.BLACK.toString()),
    ENTRY_4(4, ChatColor.LIGHT_PURPLE.toString()),
    ENTRY_5(5, ChatColor.DARK_PURPLE.toString()),
    ENTRY_6(6, ChatColor.DARK_BLUE.toString()),
    ENTRY_7(7, ChatColor.DARK_GREEN.toString()),
    ENTRY_8(8, ChatColor.DARK_RED.toString()),
    ENTRY_9(9, ChatColor.DARK_GRAY.toString()),
    ENTRY_10(10, ChatColor.DARK_AQUA.toString()),
    ENTRY_11(11, ChatColor.AQUA.toString()),
    Entry_12(12, ChatColor.GRAY.toString()),
    Entry_13(13, ChatColor.GREEN.toString()),
    Entry_14(14, ChatColor.GOLD.toString());

    private final int entry;
    private final String entryName;

    EntryName(int entry, String entryName) {
        this.entry = entry;
        this.entryName = entryName;
    }

    public int getEntry() {
        return entry;
    }

    public String getEntryName() {
        return entryName;
    }
}
