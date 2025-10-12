package de.lars.utilsmanager;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import de.lars.apiManager.dataAPI.DataAPI;
import de.lars.utilsmanager.ban.BanManager;
import de.lars.utilsmanager.ban.KickManager;
import de.lars.utilsmanager.entity.EntitySummons;
import de.lars.utilsmanager.features.backpack.BackpackManager;
import de.lars.utilsmanager.features.bank.BankText;
import de.lars.utilsmanager.features.court.CourtManager;
import de.lars.utilsmanager.features.freecam.FreecamListener;
import de.lars.utilsmanager.features.maintenance.MaintenanceManager;
import de.lars.utilsmanager.features.playtime.PlaytimeManager;
import de.lars.utilsmanager.features.realtime.RealTime;
import de.lars.utilsmanager.features.timer.Timer;
import de.lars.utilsmanager.integrations.discord.DiscordBot;
import de.lars.utilsmanager.listener.player.BedListener;
import de.lars.utilsmanager.listener.player.SpawnElytraListener;
import de.lars.utilsmanager.listener.teleporter.FloorTeleporter;
import de.lars.utilsmanager.listener.teleporter.TeleporterListener;
import de.lars.utilsmanager.plugin.Registrar;
import de.lars.utilsmanager.questsystem.QuestManager;
import de.lars.utilsmanager.rank.RankManager;
import de.lars.utilsmanager.recipes.RecipeLoader;
import de.lars.utilsmanager.tablist.TablistManager;
import de.lars.utilsmanager.util.Statements;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {

    public static Main instance;
    private Timer timer;
    private BackpackManager backpackManager;
    private RealTime realTime;
    private BankText bankText;
    private TablistManager tablistManager;
    private RankManager rankManager;
    private EntitySummons entitySummons;
    private QuestManager questManager;
    private SpawnElytraListener spawnElytraListener;
    private BanManager banManager;
    private FloorTeleporter floorTeleporter;
    private CourtManager courtManager;
    private TeleporterListener teleporterListener;
    private BedListener bedListener;
    private FreecamListener freecamListener;
    private DiscordBot discordBot;
    private KickManager kickManager;
    private MaintenanceManager maintenanceManager;
    private ProtocolManager protocolManager;
    private PlaytimeManager playtimeManager;

    @Override
    public void onLoad() {
        if (protocolManager == null) {
            protocolManager = ProtocolLibrary.getProtocolManager();
        }
        instance = this;
    }

    @Override
    public void onEnable() {
        backpackManager = new BackpackManager();
        tablistManager = new TablistManager();
        rankManager = new RankManager();
        timer = new Timer();
        realTime = new RealTime();
        //bankText = new BankText();
        bedListener = new BedListener();
        entitySummons = new EntitySummons();
        //questManager = new QuestManager();
        spawnElytraListener = new SpawnElytraListener();
        floorTeleporter = new FloorTeleporter();
        courtManager = new CourtManager();
        banManager = new BanManager();
        teleporterListener = new TeleporterListener();
        freecamListener = new FreecamListener();
        kickManager = new KickManager();
        maintenanceManager = new MaintenanceManager();
        playtimeManager = new PlaytimeManager();

        saveDefaultConfig();

        String botToken = getConfig().getString("discord.token");
        String serverStatusChannelID = getConfig().getString("discord.serverStatusChannelID");
        String playerStatusChannelID = getConfig().getString("discord.playerStatusChannelID") ;
        String punishmentsChannelID = getConfig().getString("discord.punishmentsChannelID");

        discordBot = new DiscordBot(botToken, serverStatusChannelID, playerStatusChannelID, punishmentsChannelID);

        new RecipeLoader().registerRecipes();
        //entitysSummons.EntitysSummons();
        entitySummons.EntityHearths();
        banManager.runchecking();
        teleporterListener.checkTeleportTime();

        Registrar.listenerRegistration(this);
        Registrar.commandRegistration(this);
        playtimeManager.updateTime();

        Bukkit.getConsoleSender().sendMessage(Statements.getPrefix().append(Component.text("UtilsManager enabled!", NamedTextColor.GREEN)));
    }

    @Override
    public void onDisable() {
        Bukkit.getScheduler().cancelTasks(this);

        // bankText.BankTextend();
        backpackManager.save();
        // entitysSummons.EntitysSummonsEnd();
        if (!DataAPI.getApi().isMaintenanceActive()) {
            //discordBot.sendOffMessage();
        }

        discordBot.disable();
        Bukkit.getConsoleSender().sendMessage(Statements.getPrefix().append(Component.text("UtilsManager successful disabled!", NamedTextColor.DARK_RED)));
    }

    public static Main getInstance() {
        return instance;
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

    public RankManager getRangManager() {
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