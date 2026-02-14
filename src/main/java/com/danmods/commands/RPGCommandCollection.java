package com.danmods.commands;

import com.hypixel.hytale.server.core.command.system.basecommands.AbstractCommandCollection;

public class RPGCommandCollection extends AbstractCommandCollection {

    public RPGCommandCollection() {
        super("rpg", "RPG Commands");
        addSubCommand(new AddXPCommand());
        addSubCommand(new SetXPCommand());
        addSubCommand(new StatsCommand());
        addSubCommand(new XPManagerCommand());
    }
}
