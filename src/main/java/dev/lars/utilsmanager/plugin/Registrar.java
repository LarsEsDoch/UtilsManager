package dev.lars.utilsmanager.plugin;

import dev.lars.utilsmanager.commands.admin.*;
import dev.lars.utilsmanager.commands.economy.*;
import dev.lars.utilsmanager.commands.player.*;
import dev.lars.utilsmanager.commands.teleport.home.DeleteHomeCommand;
import dev.lars.utilsmanager.commands.teleport.home.HomeCommand;
import dev.lars.utilsmanager.commands.teleport.home.SetHomeCommand;
import dev.lars.utilsmanager.commands.teleport.spawn.SetSpawnCommand;
import dev.lars.utilsmanager.commands.teleport.spawn.SpawnCommand;
import dev.lars.utilsmanager.commands.teleport.spawn.ToggleBedCommand;
import dev.lars.utilsmanager.features.backpack.BackpackCommand;
import dev.lars.utilsmanager.features.backpack.BackpackConfigurationCommand;
import dev.lars.utilsmanager.features.backpack.BackpackManager;
import dev.lars.utilsmanager.features.bank.BankTextListener;
import dev.lars.utilsmanager.features.chunk.ChunkCommand;
import dev.lars.utilsmanager.features.chunk.ChunkOwnerListener;
import dev.lars.utilsmanager.features.court.CourtCommand;
import dev.lars.utilsmanager.features.court.ReportCommand;
import dev.lars.utilsmanager.features.freecam.FreeCamCommand;
import dev.lars.utilsmanager.features.freecam.FreeCamLeaveCommand;
import dev.lars.utilsmanager.features.freecam.FreeCamLeaveListener;
import dev.lars.utilsmanager.features.maintenance.MaintenanceCommand;
import dev.lars.utilsmanager.features.maintenance.MaintenanceListener;
import dev.lars.utilsmanager.features.moderation.BanCommand;
import dev.lars.utilsmanager.features.moderation.BanListener;
import dev.lars.utilsmanager.features.moderation.KickCommand;
import dev.lars.utilsmanager.features.moderation.UnbanCommand;
import dev.lars.utilsmanager.features.rank.RankCommand;
import dev.lars.utilsmanager.features.rank.RankShopCommand;
import dev.lars.utilsmanager.features.rank.RankShopListener;
import dev.lars.utilsmanager.features.realtime.RealTimeCommand;
import dev.lars.utilsmanager.features.timer.TimerCommand;
import dev.lars.utilsmanager.listener.misc.FunListeners;
import dev.lars.utilsmanager.listener.misc.NetherListener;
import dev.lars.utilsmanager.listener.misc.ShopListener;
import dev.lars.utilsmanager.listener.misc.StairClickListener;
import dev.lars.utilsmanager.listener.player.*;
import dev.lars.utilsmanager.listener.server.ServerPingListener;
import dev.lars.utilsmanager.listener.teleporter.FloorTeleporterListener;
import dev.lars.utilsmanager.quest.QuestManager;
import dev.lars.utilsmanager.recipes.RecipeLoader;
import dev.lars.utilsmanager.scoreboard.ToggleScoreboardCommand;
import io.papermc.paper.command.brigadier.Commands;
import io.papermc.paper.plugin.lifecycle.event.LifecycleEventManager;
import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

public class Registrar {

    public static void listenerRegistration(JavaPlugin plugin, BackpackManager backpackManager) {
        PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.registerEvents(new JoinListener(), plugin);
        pluginManager.registerEvents(new QuitListener(), plugin);
        pluginManager.registerEvents(new ChatListener(), plugin);
        pluginManager.registerEvents(new FunListeners(), plugin);
        pluginManager.registerEvents(new DeathListener(), plugin);
        pluginManager.registerEvents(new RespawnListener(), plugin);
        pluginManager.registerEvents(new SpawnElytraListener(), plugin);
        pluginManager.registerEvents(new BankTextListener(), plugin);
        pluginManager.registerEvents(new ShopListener(), plugin);
        pluginManager.registerEvents(new RankShopListener(), plugin);
        pluginManager.registerEvents(new QuestManager(), plugin);
        pluginManager.registerEvents(new BanListener(), plugin);
        pluginManager.registerEvents(new StairClickListener(), plugin);
        pluginManager.registerEvents(new FloorTeleporterListener(), plugin);
        pluginManager.registerEvents(new ChunkOwnerListener(), plugin);
        pluginManager.registerEvents(new ServerPingListener(), plugin);
        pluginManager.registerEvents(new FreeCamLeaveListener(), plugin);
        pluginManager.registerEvents(new MaintenanceListener(), plugin);
        pluginManager.registerEvents(new RecipeLoader(), plugin);
        pluginManager.registerEvents(new NetherListener(), plugin);
        pluginManager.registerEvents(backpackManager, plugin);
    }

    public static void commandRegistration(JavaPlugin plugin) {
        LifecycleEventManager<@NotNull Plugin> manager = plugin.getLifecycleManager();
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
            commands.register("deletehome", "Delete your own placed home", new DeleteHomeCommand());
        });

        manager.registerEventHandler(LifecycleEvents.COMMANDS, event -> {
            final Commands commands = event.registrar();
            commands.register("sethome", "Create your own home to come back later", new SetHomeCommand());
        });

        manager.registerEventHandler(LifecycleEvents.COMMANDS, event -> {
            final Commands commands = event.registrar();
            commands.register("spawn", "Teleport to the spawn of the World", new SpawnCommand());
        });

        manager.registerEventHandler(LifecycleEvents.COMMANDS, event -> {
            final Commands commands = event.registrar();
            commands.register("setspawn", "Set the new spawn", new SetSpawnCommand());
        });

        manager.registerEventHandler(LifecycleEvents.COMMANDS, event -> {
            final Commands commands = event.registrar();
            commands.register("setbpslots", "Change the slots of a player Backpack", new BackpackConfigurationCommand());
        });

        manager.registerEventHandler(LifecycleEvents.COMMANDS, event -> {
            final Commands commands = event.registrar();
            commands.register("toggletime", "Decide if the time should sync with local time zone", new RealTimeCommand());
        });

        manager.registerEventHandler(LifecycleEvents.COMMANDS, event -> {
            final Commands commands = event.registrar();
            commands.register("addcoins", "Give a player some money", new AddCoinsCommand());
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
            commands.register("setcoins", "Set the balance of a player to specific amount", new SetCoinsCommand());
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
            commands.register("freeze", "Scare and freeze a player", new FreezeCommand());
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
            commands.register("freecamleave", "Close the freecam and play along", new FreeCamLeaveCommand());
        });

        manager.registerEventHandler(LifecycleEvents.COMMANDS, event -> {
            final Commands commands = event.registrar();
            commands.register("serverrestart", "Restart the server after a period of time", new RestartCommand());
        });

        manager.registerEventHandler(LifecycleEvents.COMMANDS, event -> {
            final Commands commands = event.registrar();
            commands.register("sr", "Restart the server after a period of time", new RestartCommand());
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

        manager.registerEventHandler(LifecycleEvents.COMMANDS, event -> {
            final Commands commands = event.registrar();
            commands.register("reply", "Reply another player", new ReplyCommand());
        });

        manager.registerEventHandler(LifecycleEvents.COMMANDS, event -> {
            final Commands commands = event.registrar();
            commands.register("r", "Reply another player", new ReplyCommand());
        });
    }
}