package net.llamasoftware.twitcher.manager;

import net.llamasoftware.twitcher.Messages;
import net.llamasoftware.twitcher.Twitcher;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.event.ClickEvent;
import net.minecraft.event.HoverEvent;
import net.minecraft.util.*;

public class ChatManager {

    private final Twitcher mod;
    private static final String PREFIX = EnumChatFormatting.DARK_AQUA + "[" + EnumChatFormatting.AQUA
                                        + "T" + EnumChatFormatting.DARK_AQUA + "] ";

    public ChatManager(Twitcher mod) {
        this.mod = mod;
    }

    public void addTwitchMessage(String userName, String msgId, String message, boolean controls, boolean mod, boolean sub){
        EntityPlayerSP player = Minecraft.getMinecraft().thePlayer;

        if(player == null)
            return;

        IChatComponent componentBan       = null;
        IChatComponent componentTimeout   = null;
        IChatComponent componentDeleteMsg = null;
        IChatComponent componentReply      = null;

        if(controls) {
            componentBan = buildChatComponent("\u00abB\u00bb", EnumChatFormatting.RED,
                    "Ban " + userName,
                    "/tcban " + userName + " " + message, ClickEvent.Action.RUN_COMMAND);
            componentTimeout = buildChatComponent("\u00abT\u00bb", EnumChatFormatting.YELLOW,
                    "Timeout " + userName + " for 5 minutes",
                    "/tcban " + userName + " " + message, ClickEvent.Action.RUN_COMMAND);
            componentDeleteMsg = buildChatComponent("\u00ab\u2717\u00bb", EnumChatFormatting.GRAY,
                    "Delete message '" + message + "'",
                    "/tcdeletemsg " + msgId, ClickEvent.Action.RUN_COMMAND);
            componentReply = buildChatComponent("\u00abR\u00bb", EnumChatFormatting.GREEN,
                    "Reply to message '" + message + "'",
                    "/tcreply " + msgId + " ", ClickEvent.Action.SUGGEST_COMMAND);
        }

        ChatComponentText componentMsg = new ChatComponentText(stripMessage(message));
        componentMsg.setChatStyle(new ChatStyle().setColor(EnumChatFormatting.AQUA));

        IChatComponent component = new ChatComponentText("");

        if(controls) {
            component = component
                    .appendSibling(componentBan)
                    .appendSibling(componentTimeout)
                    .appendSibling(componentDeleteMsg)
                    .appendSibling(componentReply)
                    .appendText(" ");
        }

        String roles = "";
        if(mod || sub){
            roles += " (";
            if(mod)
                roles += EnumChatFormatting.GREEN + ""
                        + EnumChatFormatting.BOLD + "M" + (sub ? EnumChatFormatting.DARK_AQUA + ", " : "");
            if(sub){
                roles += EnumChatFormatting.GOLD + ""
                        + EnumChatFormatting.BOLD + "S";
            }
            roles += EnumChatFormatting.DARK_AQUA + ")";
        }

        component = component
                .appendText(EnumChatFormatting.DARK_AQUA +
                        (this.mod.getSettings().isModControls() ? "": PREFIX) + userName + roles + ": ")
                .appendSibling(componentMsg);

        player.addChatMessage(component);
    }

    public void sendImportantMessage(Messages message, String... placeholders){

        EntityPlayerSP player = Minecraft.getMinecraft().thePlayer;
        if(player == null)
            return;

        ChatComponentText empty = new ChatComponentText("");
        ChatStyle style         = new ChatStyle().setColor(EnumChatFormatting.AQUA).setItalic(true);
        
        player.addChatMessage(empty);
        sendGeneralMessage(message.getText(), style, true, placeholders);
        player.addChatMessage(empty);

    }

    public void sendGeneralMessage(Messages message, boolean prefix, String... placeholders){
        sendGeneralMessage(message.getText(), prefix, placeholders);
    }

    public void sendGeneralMessage(String message, boolean prefix, String... placeholders){
        sendGeneralMessage(message, new ChatStyle().setColor(EnumChatFormatting.AQUA), prefix, placeholders);
    }

    public void sendGeneralMessage(String message, ChatStyle style, boolean prefix, String... placeholders){
        EntityPlayerSP player = Minecraft.getMinecraft().thePlayer;
        if(player != null){
            ChatComponentText componentText = new ChatComponentText(String.format(message, (Object[]) placeholders));
            componentText.setChatStyle(style);

            ChatComponentText componentPrefix = new ChatComponentText((prefix ? PREFIX : ""));
            componentPrefix.setChatStyle(new ChatStyle().setColor(EnumChatFormatting.DARK_AQUA));

            player.addChatMessage(componentPrefix.appendSibling(componentText));
        }
    }


    private String stripMessage(String message){
        return StringUtils.stripControlCodes(message.replace("\u0001ACTION", "")
                .replace("\u0001", "")).trim();
    }

    private IChatComponent buildChatComponent(String text, EnumChatFormatting colour, String tooltip,
                                              String command, ClickEvent.Action action){
        IChatComponent component = new ChatComponentText(text);
        ChatStyle chatStyle = new ChatStyle().setColor(colour)
                .setChatClickEvent(
                        new ClickEvent(action, command) {

                            @Override
                            public Action getAction() {
                                return action;
                            }

                        }).setChatHoverEvent(
                        new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ChatComponentText(tooltip)));

        component.setChatStyle(chatStyle);
        return component;
    }


}