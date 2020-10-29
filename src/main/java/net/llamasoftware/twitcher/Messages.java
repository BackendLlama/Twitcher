package net.llamasoftware.twitcher;

import net.minecraft.util.EnumChatFormatting;

public enum Messages {

    GENERAL_ENABLED("Enabled"),
    GENERAL_DISABLED("Disabled"),
    NOT_CONNECTED(EnumChatFormatting.RED + "You need to join a channel with /jc first!"),
    SYNTAX_JOIN_CHANNEL(EnumChatFormatting.RED + "Syntax: /jc <channel_name>"),
    JOINED_CHANNEL("Joined channel %s."),
    LEFT_CHANNEL("Left current channel."),
    MOD_CONTROLS_TOGGLE("%s mod controls."),
    SYNTAX_SET_TOKEN(EnumChatFormatting.RED + "Syntax: /twitcher settoken <access_token>"),
    SET_TOKEN("Successfully set token, you can now join a channel."),
    MESSAGE_SENT("Sent message '%s'"),
    REPLY_SENT("Sent reply '%s'"),
    JOINING("Joining channel..."),
    SUBSCRIPTION("%s just subscribed with %s! (%s month%s)"),
    RAID("%s is raiding with %s viewers!"),
    HOST_LOOP("%s is hosting %s, staying on %s."),
    CONNECTION_ERROR("An error occurred while connecting, is your token correct?"),
    CHEER("%s just cheered with %s bits!");

    private final String text;

    Messages(String text){
        this.text = text;
    }

    public String getText() {
        return text;
    }

}
