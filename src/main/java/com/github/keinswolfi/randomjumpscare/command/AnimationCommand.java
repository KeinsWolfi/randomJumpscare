package com.github.keinswolfi.randomjumpscare.command;

import com.github.keinswolfi.randomjumpscare.RandomJumpscare;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;

public class AnimationCommand extends CommandBase {
    @Override
    public String getCommandName() {
        return "startanimation";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "";
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) throws CommandException {
        RandomJumpscare.trigger = true;
    }

    @Override
    public boolean canCommandSenderUseCommand(ICommandSender sender) {
        return true;
    }
}
