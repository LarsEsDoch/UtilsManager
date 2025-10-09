# Minecraft Plugin Setup
# 1. Configuration
To use the plugin, create a configuration file in src/resources named config.yml with the following content:

      discord:
            token: "Your Bot Token"
            applicationID: "public application ID"
            serverStatusChannelID: "Discord channel ID for server updates"
            playerStatusChannelID: "Discord channel ID for player updates"
            punishmentsChannelID: "Discord channel ID for punishments updates"


Replace each placeholder with your actual database details.

# 2. Building the Plugin

To build the Minecraft plugin, make sure to include the following files in your build artifacts:

paper-plugin.yml

config.yml

These files must be present in the final JAR so that Paper can load your plugin and its configuration correctly.
