package net.llamasoftware.twitcher.command;

import net.llamasoftware.twitcher.Messages;
import net.llamasoftware.twitcher.Twitcher;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.BlockPos;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BanCommand implements ICommand {

    private final Twitcher mod;

    public BanCommand(Twitcher mod) {
        this.mod = mod;
    }

    @Override
    public String getCommandName() {
        return "tcban";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "";
    }

    @Override
    public List<String> getCommandAliases() {
        return new ArrayList<>();
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) {
        if(!mod.isConnected()){
            mod.getChatManager().sendGeneralMessage(Messages.NOT_CONNECTED, false);
            return;
        }

        if(args.length < 1)
            return;

        String reason = args.length > 1 ? " " + String.join(" ", Arrays.copyOfRange(args, 1, args.length)) : "";

        mod.getClient().channelMessage("/ban " + args[0] + reason);
    }

    @Override
    public boolean canCommandSenderUseCommand(ICommandSender sender) {
        return true;
    }

    @Override
    public List<String> addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos) {
        return null;
    }

    @Override
    public boolean isUsernameIndex(String[] args, int index) {
        return false;
    }

    @Override
    public int compareTo(@Nonnull ICommand iCommand) {
        return 0;
    }

}