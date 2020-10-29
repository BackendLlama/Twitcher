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

public class ReplyCommand implements ICommand {

    private final Twitcher mod;

    public ReplyCommand(Twitcher mod) {
        this.mod = mod;
    }

    @Override
    public String getCommandName() {
        return "tcreply";
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
        if (!mod.isConnected()) {
            mod.getChatManager().sendGeneralMessage(Messages.NOT_CONNECTED, false);
            return;
        }

        if(args.length < 2)
            return;

        String reply = String.join(" ", Arrays.copyOfRange(args, 1, args.length));

        mod.getClient().serverMessage("@reply-parent-msg-id=" + args[0] + " PRIVMSG #" + mod.getChannel() + " :" + reply);
        mod.getChatManager().sendGeneralMessage(Messages.REPLY_SENT, true, reply);
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