# Twitcher

Simple Forge (1.8.9) mod for connecting your Minecraft chat with Twitch chat.

## Features
* Connect directly to Twitch Chat, send and receive messages in real-time
* Clickable moderation controls (ban, time out and delete messages)
* Receive responses from different commands
* Supports threads
* Supports hosts/raids (transfers to new chat)
* Supports cheer and subscription messages

## Setup
1. Download the mod and place it in the ./minecraft/mods folder
2. Generate a chat token from https://twitchapps.com/tmi/
3. Set the token with **/twitcher settoken `<access_token>`**
4. Join any channel with **/jc `<channel>`**
5. [OPTIONAL] Enable moderation controls with **/twitcher modcontrols**

## Commands
* **/twitcher** - *Shows the help menu*
* **/twitcher settoken `<access_token>`** - *Sets the access token*
* **/jc `<channel>`** - *Joins a channel*
* **/tc `<input>`** - *Sends a message/command in the twitch chat*
* **/lc** - *Leaves the current channel*
* **/tcban `<user>` `[reason]`** - *Bans an user from the channel*
* **/tctimeout `<user>` `<duration>`** - *Timeouts a user from the channel*
* **/tcunban `<user>`** - *Revokes a ban on a user*
* **/tcuntimeout `<user>`** - *Revokes a timeout on a user*

Download: https://llamasoftware.net/mods/twitcher/download/1.0/twitcher-1.0.jar
Built with the help of [Java-Twirk](https://github.com/Gikkman/Java-Twirk)
