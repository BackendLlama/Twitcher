package net.llamasoftware.twitcher.command;

import net.llamasoftware.twitcher.Messages;
import net.llamasoftware.twitcher.Twitcher;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.*;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TwitcherCommand implements ICommand {

    private final HelpIndexCommand[] helpIndex = new HelpIndexCommand[]{
                new HelpIndexCommand("twitcher", "Shows this help menu"),
                new HelpIndexCommand("twitcher settoken", "Sets the access token", "<access_token>"),
                new HelpIndexCommand("twitcher modcontrols", "Toggles the moderator controls"),
                new HelpIndexCommand("jc", "Joins a channel", "<channel>"),
                new HelpIndexCommand("tc", "Sends a message/command in the twitch chat", "<input>"),
                new HelpIndexCommand("lc", "Leaves current channel"),
                new HelpIndexCommand("tcban", "Bans an user from the channel", "<user> [reason]"),
                new HelpIndexCommand("tctimeout", "Timeouts an user from the channel", "<user> <duration>"),
                new HelpIndexCommand("tcunban", "Revokes a ban on n user", "<user>"),
                new HelpIndexCommand("tcuntimeout", "Revokes a timeout on n user", "<user>")};

    private final Twitcher mod;
    private static final String OAUTH_PREFIX = "oauth:";

    public TwitcherCommand(Twitcher mod) {
        this.mod = mod;
    }

    @Override
    public String getCommandName() {
        return "twitcher";
    }

    @Override
    public String getCommandUsage(ICommandSender iCommandSender) {
        return "";
    }

    @Override
    public List<String> getCommandAliases() {
        return new ArrayList<>();
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) {

        if(args.length == 0){
            sendHelpIndex(sender, helpIndex);
            return;
        }

        switch (args[0].toLowerCase()){
            case "settoken":{
                if(args.length != 2) {
                    mod.getChatManager().sendGeneralMessage(Messages.SYNTAX_SET_TOKEN, false);
                    return;
                }

                mod.joinChannel(null);
                mod.getSettings().setToken((args[1].startsWith(OAUTH_PREFIX) ? "" : OAUTH_PREFIX) + args[1]);
                mod.saveSettings();
                mod.getChatManager().sendGeneralMessage(Messages.SET_TOKEN, true);

                break;
            }

            case "modcontrols":{
                mod.getSettings().setModControls(!mod.getSettings().isModControls());
                mod.saveSettings();
                mod.getChatManager().sendGeneralMessage(Messages.MOD_CONTROLS_TOGGLE, true,
                        mod.getSettings().isModControls() ? Messages.GENERAL_ENABLED.getText() : Messages.GENERAL_DISABLED.getText());
                break;
            }
        }

    }

    private void sendHelpIndex(ICommandSender sender, HelpIndexCommand... commands){
        Arrays.stream(commands).forEach(helpIndexCommand -> sender.addChatMessage(helpIndexCommand.build()));
    }

    public static class HelpIndexCommand {

        private final String name;
        private final String description;
        private final String[] args;

        public HelpIndexCommand(String name, String description, String... args){
            this.name        = name;
            this.description = description;
            this.args        = args;
        }

        public IChatComponent build(){
            StringBuilder builder = new StringBuilder(" ");
            Arrays.stream(args).forEach(a -> builder.append(a).append(" "));

            return new ChatComponentText(String.format(EnumChatFormatting.DARK_AQUA + "> "
                    + EnumChatFormatting.AQUA + "/%s%s" + EnumChatFormatting.DARK_AQUA + "- ", name, builder.toString()))
                    .appendSibling(new ChatComponentText(description).setChatStyle(new ChatStyle().setColor(EnumChatFormatting.GRAY)));
        }

    }

    @Override
    public boolean canCommandSenderUseCommand(ICommandSender iCommandSender) {
        return true;
    }

    @Override
    public List<String> addTabCompletionOptions(ICommandSender iCommandSender, String[] strings, BlockPos blockPos) {
        return new ArrayList<>();
    }

    @Override
    public boolean isUsernameIndex(String[] strings, int i) {
        return false;
    }

    @Override
    public int compareTo(@Nonnull ICommand o) {
        return 0;
    }

}