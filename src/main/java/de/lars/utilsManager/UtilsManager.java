package de.lars.utilsmanager;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import de.lars.utilsmanager.entity.EntitySummons;
import de.lars.utilsmanager.features.backpack.BackpackManager;
import de.lars.utilsmanager.features.court.CourtManager;
import de.lars.utilsmanager.features.freecam.FreecamListener;
import de.lars.utilsmanager.features.maintenance.MaintenanceManager;
import de.lars.utilsmanager.features.moderation.BanManager;
import de.lars.utilsmanager.features.moderation.KickManager;
import de.lars.utilsmanager.features.playtime.PlaytimeManager;
import de.lars.utilsmanager.features.rank.RankManager;
import de.lars.utilsmanager.features.realtime.RealTime;
import de.lars.utilsmanager.features.timer.Timer;
import de.lars.utilsmanager.integrations.discord.DiscordBot;
import de.lars.utilsmanager.listener.player.BedListener;
import de.lars.utilsmanager.listener.player.SpawnElytraListener;
import de.lars.utilsmanager.listener.teleporter.FloorTeleporterListener;
import de.lars.utilsmanager.listener.teleporter.TeleporterListener;
import de.lars.utilsmanager.plugin.Registrar;
import de.lars.utilsmanager.quest.QuestManager;
import de.lars.utilsmanager.recipes.RecipeLoader;
import de.lars.utilsmanager.tablist.TablistManager;
import de.lars.utilsmanager.util.Statements;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class UtilsManager extends JavaPlugin {

    private static UtilsManager instance;
    private ProtocolManager protocolManager;

    private Timer timer;
    private BackpackManager backpackManager;
    private RealTime realTime;
    private TablistManager tablistManager;
    private RankManager rankManager;
    private EntitySummons entitySummons;
    private SpawnElytraListener spawnElytraListener;
    private QuestManager questManager;
    private BanManager banManager;
    private FloorTeleporterListener floorTeleporterListener;
    private CourtManager courtManager;
    private TeleporterListener teleporterListener;
    private BedListener bedListener;
    private FreecamListener freecamListener;
    private DiscordBot discordBot;
    private KickManager kickManager;
    private MaintenanceManager maintenanceManager;
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

        logToConsole("UtilsManager enabled!", NamedTextColor.GREEN);
    }

    @Override
    public void onDisable() {
        Bukkit.getScheduler().cancelTasks(this);
        discordBot.disable();

        logToConsole("UtilsManager successfully disabled!", NamedTextColor.DARK_RED);
    }

    private void initializeManagers() {
        backpackManager = new BackpackManager();
        tablistManager = new TablistManager();
        rankManager = new RankManager();
        timer = new Timer();
        realTime = new RealTime();
        bedListener = new BedListener();
        entitySummons = new EntitySummons();
        spawnElytraListener = new SpawnElytraListener();
        floorTeleporterListener = new FloorTeleporterListener();
        courtManager = new CourtManager();
        banManager = new BanManager();
        teleporterListener = new TeleporterListener();
        freecamListener = new FreecamListener();
        kickManager = new KickManager();
        maintenanceManager = new MaintenanceManager();
        playtimeManager = new PlaytimeManager();
        questManager = new QuestManager();
    }

    private void initializeDiscordBot() {
        String token = getConfig().getString("discord.token");
        String serverStatusChannelId = getConfig().getString("discord.serverStatusChannelID");
        String playerStatusChannelId = getConfig().getString("discord.playerStatusChannelID");
        String punishmentsChannelId = getConfig().getString("discord.punishmentsChannelID");

        if (token == null || token.isBlank()) {
            logToConsole("No Discord token found in config.yml!", NamedTextColor.RED);
            return;
        }

        discordBot = new DiscordBot(token, serverStatusChannelId, playerStatusChannelId, punishmentsChannelId);
    }

    private void registerGameFeatures() {
        new RecipeLoader().registerRecipes();
        entitySummons.EntityHearths();
    }

    private void registerEventsAndCommands() {
        Registrar.listenerRegistration(this);
        Registrar.commandRegistration(this);
    }

    private void startBackgroundTasks() {
        banManager.run();
        teleporterListener.checkTeleportTime();
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

    public RealTime getRealTime() {
        return realTime;
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

    public FreecamListener getFreecamListener() {
        return freecamListener;
    }

    public KickManager getKickManager() {
        return kickManager;
    }

    public DiscordBot getDiscordBot() {
        return discordBot;
    }
}