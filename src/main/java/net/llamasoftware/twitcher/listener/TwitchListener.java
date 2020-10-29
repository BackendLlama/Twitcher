package net.llamasoftware.twitcher.listener;

import com.gikk.twirk.events.TwirkListener;
import com.gikk.twirk.types.hostTarget.HostTarget;
import com.gikk.twirk.types.notice.Notice;
import com.gikk.twirk.types.twitchMessage.TwitchMessage;
import com.gikk.twirk.types.usernotice.Usernotice;
import com.gikk.twirk.types.usernotice.subtype.Raid;
import com.gikk.twirk.types.usernotice.subtype.Subscription;
import com.gikk.twirk.types.users.TwitchUser;
import net.llamasoftware.twitcher.Messages;
import net.llamasoftware.twitcher.Twitcher;

import java.util.Optional;
import java.util.Timer;
import java.util.TimerTask;

public class TwitchListener implements TwirkListener {
    
    private final Twitcher mod;
    private final Timer timer = new Timer();
    private String lastHost = "";

    public TwitchListener(Twitcher mod) {
        this.mod = mod;
    }

    @Override
    public void onPrivMsg(TwitchUser sender, TwitchMessage message) {
        mod.getChatManager().addTwitchMessage(sender.getDisplayName(),
                message.getMessageID(),
                message.getContent(),
                mod.getSettings().isModControls(),
                sender.isMod(),
                sender.isSub());

        if(message.isCheer() || message.getBits() > 0){
            mod.getChatManager().sendImportantMessage(Messages.CHEER,
                    sender.getDisplayName(), String.valueOf(message.getBits()));
        }
    }

    @Override
    public void onUsernotice(TwitchUser user, Usernotice usernotice) {
        if(usernotice.isSubscription()) {
            Optional<Subscription> subscription = usernotice.getSubscription();
            if (subscription.isPresent()) {
                int months = subscription.get().getMonths();
                String planName = subscription.get().getSubscriptionPlanName();
                mod.getChatManager().sendImportantMessage(Messages.SUBSCRIPTION,
                        user.getDisplayName(), planName, String.valueOf(months), ((months == 1) ? "" : "s"));
            }
        }

        if(usernotice.isRaid()) {
            Optional<Raid> raid = usernotice.getRaid();
            raid.ifPresent(value -> mod.getChatManager().sendImportantMessage(Messages.RAID,
                    value.getSourceDisplayName(), String.valueOf(value.getRaidCount())));
        }
    }

    @Override
    public void onConnect() {
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                mod.getChatManager().sendGeneralMessage(Messages.JOINED_CHANNEL, true, mod.getChannel());
            }
        }, 10);
    }

    @Override
    public void onNotice(Notice notice) {
        mod.getChatManager().sendGeneralMessage(notice.getMessage(), true);
    }

    @Override
    public void onHost(HostTarget hostNotice) {
        if(hostNotice.getTarget().equalsIgnoreCase(lastHost)) {
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    mod.getChatManager().sendGeneralMessage(Messages.HOST_LOOP, true,
                            hostNotice.getTarget(), mod.getChannel(), mod.getChannel());
                }
            }, 10);
            return;
        }

        mod.joinChannel(hostNotice.getTarget());
        lastHost = mod.getChannel();
    }
    
}