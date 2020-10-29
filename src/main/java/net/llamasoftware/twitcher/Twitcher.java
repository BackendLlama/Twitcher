package net.llamasoftware.twitcher;

import com.gikk.twirk.Twirk;
import com.gikk.twirk.TwirkBuilder;
import net.llamasoftware.twitcher.command.*;
import net.llamasoftware.twitcher.listener.TwitchListener;
import net.llamasoftware.twitcher.manager.ChatManager;
import net.llamasoftware.twitcher.misc.Settings;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.stream.Collectors;

@Mod(modid = "twitcher", version = "1.0")
public class Twitcher {

    private Settings settings;
    private Twirk client;
    private String channel;
    private ChatManager chatManager;

    private static final String SETTINGS_FILE = "twitcher_settings.txt";

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        chatManager = new ChatManager(this);

        loadSettings();
        registerCommands();
    }

    private void registerCommands(){
        Arrays.asList(new BanCommand(this),
                new DeleteMsgCommand(this),
                new JoinChannelCommand(this),
                new TcCommand(this),
                new TimeoutCommand(this),
                new UnbanCommand(this),
                new UntimeoutCommand(this),
                new ReplyCommand(this),
                new LeaveChannelCommand(this),
                new TwitcherCommand(this))
            .forEach(ClientCommandHandler.instance::registerCommand);
    }

    public void joinChannel(String name){
        new Thread(() -> {
            channel = null;

            if(client != null)
                client.close();

            if(name == null || name.isEmpty())
                return;

            try {
                client = new TwirkBuilder("#" + name.toLowerCase(),
                        "TwitcherMod", settings.getToken()).build();

                client.addIrcListener(new TwitchListener(this));
                client.connect();
                channel = name;
            } catch (IOException | InterruptedException e) {
                chatManager.sendGeneralMessage(Messages.CONNECTION_ERROR, true);
                e.printStackTrace();
            }
        }).start();
    }

    private void loadSettings(){
        File file = new File(SETTINGS_FILE);
        if(!file.exists()) {
            try {
                if(file.createNewFile()) {
                    settings = new Settings("token", false);
                    saveSettings();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        String data = "";
        try {
            data = Files.lines(Paths.get(file.getAbsolutePath()), StandardCharsets.UTF_8)
                    .collect(Collectors.joining());
        } catch (IOException e) {
            e.printStackTrace();
        }

        settings = Settings.deserialize(data);
    }

    public void saveSettings(){
        try {
            Files.write(Paths.get(SETTINGS_FILE),
                    settings.serialize().getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean isConnected() {
        return channel != null;
    }

    public String getChannel() {
        return channel;
    }

    public Twirk getClient() {
        return client;
    }

    public Settings getSettings() {
        return settings;
    }

    public ChatManager getChatManager() {
        return chatManager;
    }

}