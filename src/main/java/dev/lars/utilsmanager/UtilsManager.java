package dev.lars.utilsmanager;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import dev.lars.utilsmanager.entity.HeartDisplayManager;
import dev.lars.utilsmanager.features.backpack.BackpackManager;
import dev.lars.utilsmanager.features.court.CourtManager;
import dev.lars.utilsmanager.features.freecam.FreeCamManager;
import dev.lars.utilsmanager.features.maintenance.MaintenanceManager;
import dev.lars.utilsmanager.features.moderation.BanManager;
import dev.lars.utilsmanager.features.moderation.KickManager;
import dev.lars.utilsmanager.features.playtime.PlaytimeManager;
import dev.lars.utilsmanager.features.rank.RankManager;
import dev.lars.utilsmanager.features.realtime.RealTime;
import dev.lars.utilsmanager.features.timer.Timer;
import dev.lars.utilsmanager.integrations.discord.DiscordBot;
import dev.lars.utilsmanager.listener.player.BedListener;
import dev.lars.utilsmanager.listener.teleporter.TeleporterListener;
import dev.lars.utilsmanager.plugin.Registrar;
import dev.lars.utilsmanager.quest.QuestManager;
import dev.lars.utilsmanager.recipes.RecipeLoader;
import dev.lars.utilsmanager.tablist.TablistManager;
import dev.lars.utilsmanager.utils.Statements;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public final class UtilsManager extends JavaPlugin {

    private static UtilsManager instance;
    private ProtocolManager protocolManager;

    private BackpackManager backpackManager;
    private TablistManager tablistManager;
    private RankManager rankManager;
    private HeartDisplayManager heartDisplayManager;
    private QuestManager questManager;
    private BanManager banManager;
    private CourtManager courtManager;
    private FreeCamManager freeCamManager;
    private DiscordBot discordBot;
    private KickManager kickManager;
    private PlaytimeManager playtimeManager;

    @Override
    public void onLoad() {
        instance = this;
        protocolManager = ProtocolLibrary.getProtocolManager();
    }

    @Override
    public void onEnable() {
        saveDefaultConfig();
        initializeManagers();
        initializeDiscordBot();
        registerGameFeatures();
        registerEventsAndCommands();
        startBackgroundTasks();

        logToConsole("UtilsManager enabled!", NamedTextColor.DARK_GREEN);
    }

    @Override
    public void onDisable() {
        Bukkit.getScheduler().cancelTasks(this);
        discordBot.disable();

        logToConsole("UtilsManager successfully disabled!", NamedTextColor.DARK_RED);
    }

    public void initializeManagers() {
        backpackManager = new BackpackManager();
        tablistManager = new TablistManager();
        rankManager = new RankManager();
        new Timer();
        new RealTime();
        new BedListener();
        heartDisplayManager = new HeartDisplayManager();
        courtManager = new CourtManager();
        banManager = new BanManager();
        new TeleporterListener();
        freeCamManager = new FreeCamManager();
        kickManager = new KickManager();
        new MaintenanceManager();
        playtimeManager = new PlaytimeManager();
        questManager = new QuestManager();
    }

    public void initializeDiscordBot() {
        String token = getConfig().getString("discord.token");
        String serverStatusChannelId = getConfig().getString("discord.serverStatusChannelID");
        String playerStatusChannelId = getConfig().getString("discord.playerStatusChannelID");
        String punishmentsChannelId = getConfig().getString("discord.punishmentsChannelID");

        if (token == null || token.isBlank()) {
            logToConsole("No Discord token found in config.yml!", NamedTextColor.RED);
        } else {
            discordBot = new DiscordBot(token, serverStatusChannelId, playerStatusChannelId, punishmentsChannelId);
        }
    }

    public void registerGameFeatures() {
        try {
            new RecipeLoader().registerRecipes();
        } catch (Exception e) {
            logToConsole("Custom recipes already loaded", NamedTextColor.GOLD);
        }
        heartDisplayManager.start();
        rankManager.checkRanks();
    }

    private void registerEventsAndCommands() {
        Registrar.listenerRegistration(this, backpackManager);
        Registrar.commandRegistration(this);
    }

    public void startBackgroundTasks() {
        banManager.run();
        playtimeManager.updateTime();
    }

    private void logToConsole(String message, NamedTextColor color) {
        Bukkit.getConsoleSender().sendMessage(
            Statements.getPrefix().append(Component.text(message, color))
        );
    }

    public static UtilsManager getInstance() {
        return instance;
    }

    public String getVersion() {
        return getPluginMeta().getVersion();
    }

    public String getApiVersion() {
        return getPluginMeta().getAPIVersion();
    }

    public List<String> getDevelopers() {
        return getPluginMeta().getAuthors();
    }

    public String getWebsite() {
        return getPluginMeta().getWebsite();
    }

    public ProtocolManager getProtocolManager() {
        return protocolManager;
    }

    public BackpackManager getBackpackManager() {
        return backpackManager;
    }

    public BanManager getBanManager() {
        return banManager;
    }

    public CourtManager getCourtManager() {
        return courtManager;
    }

    public TablistManager getTablistManager() {
        return tablistManager;
    }

    public RankManager getRankManager() {
        return rankManager;
    }

    public QuestManager getQuestManager() {
        return questManager;
    }

    public FreeCamManager getFreeCamManager() {
        return freeCamManager;
    }

    public KickManager getKickManager() {
        return kickManager;
    }

    public DiscordBot getDiscordBot() {
        return discordBot;
    }
}