package de.lars.utilsManager;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import de.lars.apiManager.dataAPI.DataAPI;
import de.lars.apiManager.playersAPI.PlayerAPI;
import de.lars.utilsManager.chunks.ChunkCommand;
import de.lars.utilsManager.court.CourtCommand;
import de.lars.utilsManager.court.ReportCommand;
import de.lars.utilsManager.discordBot.DiscordBot;
import de.lars.utilsManager.backpack.BackpackCommand;
import de.lars.utilsManager.backpack.BackpackManager;
import de.lars.utilsManager.backpack.BackpackconfigurationCommand;
import de.lars.utilsManager.ban.*;
import de.lars.utilsManager.bank.BankText;
import de.lars.utilsManager.bank.BankTextListener;
import de.lars.utilsManager.coins.*;
import de.lars.utilsManager.commands.*;
import de.lars.utilsManager.commands.homes.*;
import de.lars.utilsManager.court.CourtManager;
import de.lars.utilsManager.entitys.EntitysSummons;
import de.lars.utilsManager.freecam.FreeCamCloseCommand;
import de.lars.utilsManager.freecam.FreeCamCommand;
import de.lars.utilsManager.freecam.FreecamListener;
import de.lars.utilsManager.listener.*;
import de.lars.utilsManager.maintenance.MaintenanceCommand;
import de.lars.utilsManager.maintenance.MaintenanceListener;
import de.lars.utilsManager.maintenance.MaintenanceManager;
import de.lars.utilsManager.questsystem.QuestManager;
import de.lars.utilsManager.ranks.*;
import de.lars.utilsManager.realtime.RealTime;
import de.lars.utilsManager.realtime.RealTimeCommand;
import de.lars.utilsManager.recipes.RecipeLoader;
import de.lars.utilsManager.scoreboard.ToggleScoreboardCommand;
import de.lars.utilsManager.tablist.TablistManager;
import de.lars.utilsManager.teleporter.FloorTeleporter;
import de.lars.utilsManager.teleporter.TeleporterListener;
import de.lars.utilsManager.timer.Timer;
import de.lars.utilsManager.timer.TimerCommand;
import de.lars.utilsManager.utils.Statements;
import io.papermc.paper.command.brigadier.Commands;
import io.papermc.paper.plugin.lifecycle.event.LifecycleEventManager;
import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {

    public static Main instance;
    private Timer timer;
    private BackpackManager backpackManager;
    private RealTime realTime;
    private BankText bankText;
    private TablistManager tablistManager;
    private RankManager rankManager;
    private EntitysSummons entitysSummons;
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
        entitysSummons = new EntitysSummons();
        //questManager = new QuestManager();
        spawnElytraListener = new SpawnElytraListener();
        floorTeleporter = new FloorTeleporter();
        courtManager = new CourtManager();
        banManager = new BanManager();
        teleporterListener = new TeleporterListener();
        freecamListener = new FreecamListener();
        kickManager = new KickManager();
        maintenanceManager = new MaintenanceManager();

        saveDefaultConfig();

        String botToken = getConfig().getString("discord.token");
        String serverStatusChannelID = getConfig().getString("discord.serverStatusChannelID");
        String playerStatusChannelID = getConfig().getString("discord.playerStatusChannelID") ;
        String punishmentsChannelID = getConfig().getString("discord.punishmentsChannelID");

        discordBot = new DiscordBot(botToken, serverStatusChannelID, playerStatusChannelID, punishmentsChannelID);

        new RecipeLoader().registerRecipes();
        // entitysSummons.EntitysSummons();
        entitysSummons.EntityHearths();
        banManager.runchecking();
        teleporterListener.checkTeleportTime();

        listenerRegistration();
        commandRegistration();
        updateTime();

        Bukkit.getConsoleSender().sendMessage(Statements.getPrefix().append(Component.text("UtilsManager enabled!", NamedTextColor.DARK_GREEN)));
    }

    public void updateTime() {
        Bukkit.getScheduler().runTaskTimerAsynchronously(Main.getInstance(), bukkitTask ->  {
            for (Player online: Bukkit.getOnlinePlayers()) {
                PlayerAPI.getApi().setPlaytime(online, PlayerAPI.getApi().getPlaytime(online) + 5);
            }
        }, 100, 100);
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

    private void listenerRegistration() {
        PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.registerEvents(new JoinListener(), this);
        pluginManager.registerEvents(new QuitListener(), this);
        pluginManager.registerEvents(new ChatListener(), this);
        pluginManager.registerEvents(new ClickListeners(), this);
        pluginManager.registerEvents(new DeathListener(), this);
        pluginManager.registerEvents(new RespawnListener(), this);
        pluginManager.registerEvents(new SpawnElytraListener(), this);
        pluginManager.registerEvents(new BankTextListener(), this);
        pluginManager.registerEvents(new Banhammer(), this);
        pluginManager.registerEvents(new ShopListener(), this);
        pluginManager.registerEvents(new RankShopListener(), this);
        //pluginManager.registerEvents(new QuestManager(), this);
        pluginManager.registerEvents(new BanListener(), this);
        pluginManager.registerEvents(new StairClickListener(), this);
        pluginManager.registerEvents(new FloorTeleporter(), this);
        //pluginManager.registerEvents(new ChunkOwnerListener(), this);
        pluginManager.registerEvents(new ServerPingListener(), this);
        pluginManager.registerEvents(new FreecamListener(), this);
        pluginManager.registerEvents(new MaintenanceListener(), this);
        pluginManager.registerEvents(new RecipeLoader(), this);
        pluginManager.registerEvents(new NetherListener(), this);

    }

    public void commandRegistration() {
        LifecycleEventManager<Plugin> manager = this.getLifecycleManager();
        manager.registerEventHandler(LifecycleEvents.COMMANDS, event -> {
            final Commands commands = event.registrar();
            commands.register("timer", "Control the timer for yourself", new TimerCommand());
        });

        manager.registerEventHandler(LifecycleEvents.COMMANDS, event -> {
            final Commands commands = event.registrar();
            commands.register("backpack", "Open your own backpack", new BackpackCommand());
        });

        manager.registerEventHandler(LifecycleEvents.COMMANDS, event -> {
            final Commands commands = event.registrar();
            commands.register("bp", "Open your own backpack", new BackpackCommand());
        });

        manager.registerEventHandler(LifecycleEvents.COMMANDS, event -> {
            final Commands commands = event.registrar();
            commands.register("home", "Teleport to your own set homes or the public ones", new HomeCommand());
        });

        manager.registerEventHandler(LifecycleEvents.COMMANDS, event -> {
            final Commands commands = event.registrar();
            commands.register("deletehome", "Delete your own placed home", new deleteHomeCommand());
        });

        manager.registerEventHandler(LifecycleEvents.COMMANDS, event -> {
            final Commands commands = event.registrar();
            commands.register("sethome", "Create your own home to come back later", new setHomeCommand());
        });

        manager.registerEventHandler(LifecycleEvents.COMMANDS, event -> {
            final Commands commands = event.registrar();
            commands.register("spawn", "Teleport to the spawn of the World", new SpawnCommand());
        });

        manager.registerEventHandler(LifecycleEvents.COMMANDS, event -> {
            final Commands commands = event.registrar();
            commands.register("setbpslots", "Change the slots of a player Backpack", new BackpackconfigurationCommand());
        });

        manager.registerEventHandler(LifecycleEvents.COMMANDS, event -> {
            final Commands commands = event.registrar();
            commands.register("toggletime", "Decide if the time should snyc with local time zone", new RealTimeCommand());
        });

        manager.registerEventHandler(LifecycleEvents.COMMANDS, event -> {
            final Commands commands = event.registrar();
            commands.register("addcoins", "Give a player some money", new AddcoinsCommand());
        });

        manager.registerEventHandler(LifecycleEvents.COMMANDS, event -> {
            final Commands commands = event.registrar();
            commands.register("pay", "Pay a player money", new PayCommand());
        });

        manager.registerEventHandler(LifecycleEvents.COMMANDS, event -> {
            final Commands commands = event.registrar();
            commands.register("wallet", "See how much money you or an other player has left", new WalletCommand());
        });

        manager.registerEventHandler(LifecycleEvents.COMMANDS, event -> {
            final Commands commands = event.registrar();
            commands.register("setcoins", "Set the balance of a player to specific amount", new Setcoins());
        });

        manager.registerEventHandler(LifecycleEvents.COMMANDS, event -> {
            final Commands commands = event.registrar();
            commands.register("buy", "Buy some Materials", new BuyCommand());
        });

        manager.registerEventHandler(LifecycleEvents.COMMANDS, event -> {
            final Commands commands = event.registrar();
            commands.register("sell", "Sell some Materials", new SellCommand());
        });

        manager.registerEventHandler(LifecycleEvents.COMMANDS, event -> {
            final Commands commands = event.registrar();
            commands.register("language", "Change your language", new LanguageCommand());
        });

        manager.registerEventHandler(LifecycleEvents.COMMANDS, event -> {
            final Commands commands = event.registrar();
            commands.register("gifts", "Look if you have gifts and claim them", new GiftCommand());
        });

        manager.registerEventHandler(LifecycleEvents.COMMANDS, event -> {
            final Commands commands = event.registrar();
            commands.register("togglebed", "Decide where to spawn after death or join", new ToggleBedCommand());
        });

        manager.registerEventHandler(LifecycleEvents.COMMANDS, event -> {
            final Commands commands = event.registrar();
            commands.register("togglescoreboard", "Decide if you want a scoreboard", new ToggleScoreboardCommand());
        });

        manager.registerEventHandler(LifecycleEvents.COMMANDS, event -> {
            final Commands commands = event.registrar();
            commands.register("shop", "Open the shop overview", new ShopCommand());
        });

        manager.registerEventHandler(LifecycleEvents.COMMANDS, event -> {
            final Commands commands = event.registrar();
            commands.register("prices", "See the prices of materials", new PriceCommand());
        });

        manager.registerEventHandler(LifecycleEvents.COMMANDS, event -> {
            final Commands commands = event.registrar();
            commands.register("setrank", "Change the rank of a player", new RankCommand());
        });

        manager.registerEventHandler(LifecycleEvents.COMMANDS, event -> {
            final Commands commands = event.registrar();
            commands.register("rankshop", "Open the shop for the 5 ranks to buy", new RankShopCommand());
        });

        manager.registerEventHandler(LifecycleEvents.COMMANDS, event -> {
            final Commands commands = event.registrar();
            commands.register("nick", "Hide your Name", new NickCommand());
        });

        manager.registerEventHandler(LifecycleEvents.COMMANDS, event -> {
            final Commands commands = event.registrar();
            commands.register("freeezee", "Scare and freeze a player", new FrezeCommand());
        });

        manager.registerEventHandler(LifecycleEvents.COMMANDS, event -> {
            final Commands commands = event.registrar();
            commands.register("invsee", "See and check the inventory of a player", new InvseeCommand());
        });

        manager.registerEventHandler(LifecycleEvents.COMMANDS, event -> {
            final Commands commands = event.registrar();
            commands.register("prefix", "Change your prefix", new PrefixCommand());
        });

        manager.registerEventHandler(LifecycleEvents.COMMANDS, event -> {
            final Commands commands = event.registrar();
            commands.register("ban", "Ban a player for a period of time", new BanCommand());
        });

        manager.registerEventHandler(LifecycleEvents.COMMANDS, event -> {
            final Commands commands = event.registrar();
            commands.register("unban", "Unban a player to forgive", new UnbanCommand());
        });

        manager.registerEventHandler(LifecycleEvents.COMMANDS, event -> {
            final Commands commands = event.registrar();
            commands.register("status", "Change your Status", new StatusCommand());
        });

        manager.registerEventHandler(LifecycleEvents.COMMANDS, event -> {
            final Commands commands = event.registrar();
            commands.register("performance", "Check the performance of the server", new PerformanceCommand());
        });

        manager.registerEventHandler(LifecycleEvents.COMMANDS, event -> {
            final Commands commands = event.registrar();
            commands.register("report", "Report a player to get him in court", new ReportCommand());
        });

        manager.registerEventHandler(LifecycleEvents.COMMANDS, event -> {
            final Commands commands = event.registrar();
            commands.register("court", "Decide if you want to join the court or be a witnesser", new CourtCommand());
        });

        manager.registerEventHandler(LifecycleEvents.COMMANDS, event -> {
            final Commands commands = event.registrar();
            commands.register("chunk", "Mangement for the chunk you are in or in general", new ChunkCommand());
        });

        manager.registerEventHandler(LifecycleEvents.COMMANDS, event -> {
            final Commands commands = event.registrar();
            commands.register("heal", "Heal a player", new HealCommand());
        });

        manager.registerEventHandler(LifecycleEvents.COMMANDS, event -> {
            final Commands commands = event.registrar();
            commands.register("feed", "Feed a player", new FeedCommand());
        });

        manager.registerEventHandler(LifecycleEvents.COMMANDS, event -> {
            final Commands commands = event.registrar();
            commands.register("claimgift", "Claim the gifts you've stored", new ClaimGiftCommand());
        });

        manager.registerEventHandler(LifecycleEvents.COMMANDS, event -> {
            final Commands commands = event.registrar();
            commands.register("entity", "Summon lots of entitys", new EntityCommand());
        });

        manager.registerEventHandler(LifecycleEvents.COMMANDS, event -> {
            final Commands commands = event.registrar();
            commands.register("clearchat", "Clear the chat for every player", new ClearChatCommand());
        });

        manager.registerEventHandler(LifecycleEvents.COMMANDS, event -> {
            final Commands commands = event.registrar();
            commands.register("test1", "Test Command for Troll", new MagnetCommand());
        });

        manager.registerEventHandler(LifecycleEvents.COMMANDS, event -> {
            final Commands commands = event.registrar();
            commands.register("searchplayer", "Search after avery player on the server", new SearchCommand());
        });

        manager.registerEventHandler(LifecycleEvents.COMMANDS, event -> {
            final Commands commands = event.registrar();
            commands.register("freecam", "Move freely in spectator mode around", new FreeCamCommand());
        });

        manager.registerEventHandler(LifecycleEvents.COMMANDS, event -> {
            final Commands commands = event.registrar();
            commands.register("freecamleave", "Close the freecam and play along", new FreeCamCloseCommand());
        });

        manager.registerEventHandler(LifecycleEvents.COMMANDS, event -> {
            final Commands commands = event.registrar();
            commands.register("serverrestart", "Restart the server after a period of time", new RestartCommand());
        });

        manager.registerEventHandler(LifecycleEvents.COMMANDS, event -> {
            final Commands commands = event.registrar();
            commands.register("sudo", "Make a player perform a command or send a message", new SudoCommand());
        });

        manager.registerEventHandler(LifecycleEvents.COMMANDS, event -> {
            final Commands commands = event.registrar();
            commands.register("timeout", "Kick the player for short period of time", new KickCommand());
        });

        manager.registerEventHandler(LifecycleEvents.COMMANDS, event -> {
            final Commands commands = event.registrar();
            commands.register("vanish", "Hide yourself form other players", new VanishCommand());
        });

        manager.registerEventHandler(LifecycleEvents.COMMANDS, event -> {
            final Commands commands = event.registrar();
            commands.register("announce", "Send every player a message as the server", new AnnounceCommand());
        });

        manager.registerEventHandler(LifecycleEvents.COMMANDS, event -> {
            final Commands commands = event.registrar();
            commands.register("maintenance", "Set the server under maintenance so you can work privately", new MaintenanceCommand());
        });

        manager.registerEventHandler(LifecycleEvents.COMMANDS, event -> {
            final Commands commands = event.registrar();
            commands.register("msg", "Message another player", new MsgCommand());
        });

        Bukkit.getConsoleSender().sendMessage(Statements.getPrefix().append(Component.text("UtilsManager Commands registered!", NamedTextColor.GREEN)));
    }
}
