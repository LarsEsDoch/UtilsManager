package de.lars.utilsmanager.features.court;

import dev.lars.apimanager.apis.courtAPI.CourtAPI;
import dev.lars.apimanager.apis.languageAPI.LanguageAPI;
import de.lars.utilsmanager.UtilsManager;
import de.lars.utilsmanager.utils.RankStatements;
import de.lars.utilsmanager.utils.Statements;
import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class CourtCommand implements BasicCommand {

    @Override
    public void execute(@NotNull CommandSourceStack stack, @NotNull String[] args) {
        if (!(stack.getExecutor() instanceof Player player)) {
            stack.getSender().sendMessage(Statements.getOnlyPlayers());
            return;
        }
        if (args.length == 0) {
            sendUsage(player);
            return;
        }
        Player criminal = Bukkit.getPlayer("Opfer");
        Player prosecutor = Bukkit.getPlayer("Opfer2");
        Boolean CourtOnWaiting = false;
        Boolean CourtonGoing = false;
        for (Player onlineplayer: Bukkit.getOnlinePlayers()) {
            if (CourtAPI.getApi().getStatus(onlineplayer) == 2 || CourtAPI.getApi().getStatus(onlineplayer) == 3) {
                criminal = onlineplayer;
                prosecutor = Bukkit.getPlayer(CourtAPI.getApi().getProsecutor(onlineplayer));
                CourtOnWaiting = true;
                break;
            }
        }
        if (CourtOnWaiting) {
            switch (args[0].toLowerCase()) {
                case "join": {
                    if (player == criminal) {
                        if (LanguageAPI.getApi().getLanguage(player) == 2) {
                            player.sendMessage(Statements.getPrefix().append(Component.text("Du wurdest angeklagt!", NamedTextColor.RED)));
                        } else {
                            player.sendMessage(Statements.getPrefix().append(Component.text("You have been charged!", NamedTextColor.RED)));
                        }
                        return;
                    }
                    if (player == prosecutor) {
                        if(LanguageAPI.getApi().getLanguage(player) == 2) {
                            player.sendMessage(Statements.getPrefix().append(Component.text("Du hast jemanden angeklagt!", NamedTextColor.RED)));
                        } else {
                            player.sendMessage(Statements.getPrefix().append(Component.text("You accused someone!", NamedTextColor.RED)));
                        }
                        return;
                    }
                    if (UtilsManager.getInstance().getCourtManager().join(player) == 507) {
                        if(LanguageAPI.getApi().getLanguage(player) == 2) {
                            player.sendMessage(Statements.getPrefix().append(Component.text("Du kommst bereits zur Gerichtsversammlung!", NamedTextColor.RED)));
                        } else {
                            player.sendMessage(Statements.getPrefix().append(Component.text("You're already coming to court meeting!", NamedTextColor.RED)));
                        }
                        return;
                    }
                    if (UtilsManager.getInstance().getCourtManager().join(player) == 404) {
                        if(LanguageAPI.getApi().getLanguage(player) == 2) {
                            player.sendMessage(Statements.getPrefix().append(Component.text("Mehr Spieler können nicht der Gerichtsversammlung kommen!", NamedTextColor.RED)));
                        } else {
                            player.sendMessage(Statements.getPrefix().append(Component.text("More player can`t go to the curt meeting!", NamedTextColor.RED)));
                        }
                        return;
                    }
                    if(LanguageAPI.getApi().getLanguage(player) == 2) {
                        player.sendMessage(Statements.getPrefix()
                                .append(Component.text("Du kommst zu der Gerichtsversammlung von ", NamedTextColor.WHITE))
                                .append(RankStatements.getRank(criminal))
                                .append(Component.text(".", NamedTextColor.WHITE)));
                    } else {
                        player.sendMessage(Statements.getPrefix()
                                .append(Component.text("You come to the court meeting of ", NamedTextColor.WHITE))
                                .append(RankStatements.getRank(criminal))
                                .append(Component.text(".", NamedTextColor.WHITE)));
                    }
                    UtilsManager.getInstance().getCourtManager().join(player);
                    break;
                }
                case "pas": {
                    if (player == criminal) {
                        if (LanguageAPI.getApi().getLanguage(player) == 2) {
                            player.sendMessage(Statements.getPrefix().append(Component.text("Du wurdest angeklagt!", NamedTextColor.RED)));
                        } else {
                            player.sendMessage(Statements.getPrefix().append(Component.text("You have been charged!", NamedTextColor.RED)));
                        }
                        return;
                    }
                    if (player == prosecutor) {
                        if(LanguageAPI.getApi().getLanguage(player) == 2) {
                            player.sendMessage(Statements.getPrefix().append(Component.text("Du hast jemanden angeklagt!", NamedTextColor.RED)));
                        } else {
                            player.sendMessage(Statements.getPrefix().append(Component.text("You accused someone!", NamedTextColor.RED)));
                        }
                        return;
                    }
                    if (UtilsManager.getInstance().getCourtManager().pas(player) == 507) {
                        if(LanguageAPI.getApi().getLanguage(player) == 2) {
                            player.sendMessage(Statements.getPrefix().append(Component.text("Du kommst gar nicht zur Gerichtsversammlung!", NamedTextColor.RED)));
                        } else {
                            player.sendMessage(Statements.getPrefix().append(Component.text("You aren't coming to court meeting already!", NamedTextColor.RED)));
                        }
                        return;
                    }
                    if(LanguageAPI.getApi().getLanguage(player) == 2) {
                        player.sendMessage(Statements.getPrefix().append(Component.text("Du kommst nun nicht mehr zur Gerichtsversammlung!", NamedTextColor.RED)));
                    } else {
                        player.sendMessage(Statements.getPrefix().append(Component.text("You won't come to the court meeting anymore!", NamedTextColor.RED)));
                    }
                    UtilsManager.getInstance().getCourtManager().pas(player);
                    break;
                }
                default:
                    sendUsage(player);
                    break;
            }
        } else {
            if (CourtonGoing) {
                switch (args[0].toLowerCase()) {
                    case "witness": {
                        if (player == criminal) {
                            if (LanguageAPI.getApi().getLanguage(player) == 2) {
                                player.sendMessage(Statements.getPrefix().append(Component.text("Du wurdest angeklagt!", NamedTextColor.RED)));
                            } else {
                                player.sendMessage(Statements.getPrefix().append(Component.text("You have been charged!", NamedTextColor.RED)));
                            }
                            return;
                        }
                        if (player == prosecutor) {
                            if(LanguageAPI.getApi().getLanguage(player) == 2) {
                                player.sendMessage(Statements.getPrefix().append(Component.text("Du hast jemanden angeklagt!", NamedTextColor.RED)));
                            } else {
                                player.sendMessage(Statements.getPrefix().append(Component.text("You accused someone!", NamedTextColor.RED)));
                            }
                            return;
                        }
                        if (UtilsManager.getInstance().getCourtManager().witness(player) == -1) {
                            if(LanguageAPI.getApi().getLanguage(player) == 2) {
                                player.sendMessage(Statements.getPrefix().append(Component.text("Mehr Zeugen kann es nicht geben!", NamedTextColor.RED)));
                            } else {
                                player.sendMessage(Statements.getPrefix().append(Component.text("There can't be more witnesses!", NamedTextColor.RED)));
                            }
                        }
                        if (UtilsManager.getInstance().getCourtManager().witness(player) == 1) {
                            if(LanguageAPI.getApi().getLanguage(player) == 2) {
                                player.sendMessage(Statements.getPrefix().append(Component.text("Du bist nun nicht mehr auf dem Zeugen Stuhl.", NamedTextColor.BLUE)));
                            } else {
                                player.sendMessage(Statements.getPrefix().append(Component.text("You are no longer on the witness chair.", NamedTextColor.BLUE)));
                            }
                        }
                        if (UtilsManager.getInstance().getCourtManager().witness(player) == 0) {
                            if(LanguageAPI.getApi().getLanguage(player) == 2) {
                                player.sendMessage(Statements.getPrefix().append(Component.text("Du bist nun auf dem Zeugen Stuhl.", NamedTextColor.BLUE)));
                            } else {
                                player.sendMessage(Statements.getPrefix().append(Component.text("You are now on the witness chair.", NamedTextColor.BLUE)));
                            }
                        }
                        break;
                    }
                    case "judged": {
                        if (player == criminal) {
                            if (LanguageAPI.getApi().getLanguage(player) == 2) {
                                player.sendMessage(Statements.getPrefix().append(Component.text("Du wurdest angeklagt!", NamedTextColor.RED)));
                            } else {
                                player.sendMessage(Statements.getPrefix().append(Component.text("You have been charged!", NamedTextColor.RED)));
                            }
                            return;
                        }
                        if (player == prosecutor) {
                            if(LanguageAPI.getApi().getLanguage(player) == 2) {
                                player.sendMessage(Statements.getPrefix().append(Component.text("Du hast jemanden angeklagt!", NamedTextColor.RED)));
                            } else {
                                player.sendMessage(Statements.getPrefix().append(Component.text("You accused someone!", NamedTextColor.RED)));
                            }
                            return;
                        }
                        if (UtilsManager.getInstance().getCourtManager().judged(player) == 0) {
                            if(LanguageAPI.getApi().getLanguage(player) == 2) {
                                player.sendMessage(Statements.getPrefix().append(Component.text("Du bereits abgestimmt!", NamedTextColor.RED)));
                            } else {
                                player.sendMessage(Statements.getPrefix().append(Component.text("You already voted!", NamedTextColor.RED)));
                            }
                        } else {
                            if(LanguageAPI.getApi().getLanguage(player) == 2) {
                                player.sendMessage(Statements.getPrefix().append(Component.text("Du hast gegen die Freilassung gestimmt.", NamedTextColor.DARK_PURPLE)));
                            } else {
                                player.sendMessage(Statements.getPrefix().append(Component.text("You voted against release.", NamedTextColor.DARK_PURPLE)));
                            }
                        }
                        break;
                    }
                    case "free": {
                        if (player == criminal) {
                            if (LanguageAPI.getApi().getLanguage(player) == 2) {
                                player.sendMessage(Statements.getPrefix().append(Component.text("Du wurdest angeklagt!", NamedTextColor.RED)));
                            } else {
                                player.sendMessage(Statements.getPrefix().append(Component.text("You have been charged!", NamedTextColor.RED)));
                            }
                            return;
                        }
                        if (player == prosecutor) {
                            if(LanguageAPI.getApi().getLanguage(player) == 2) {
                                player.sendMessage(Statements.getPrefix().append(Component.text("Du hast jemanden angeklagt!", NamedTextColor.RED)));
                            } else {
                                player.sendMessage(Statements.getPrefix().append(Component.text("You accused someone!", NamedTextColor.RED)));
                            }
                            return;
                        }
                        if (UtilsManager.getInstance().getCourtManager().free(player) == 0) {
                            if(LanguageAPI.getApi().getLanguage(player) == 2) {
                                player.sendMessage(Statements.getPrefix().append(Component.text("Du bereits abgestimmt!", NamedTextColor.RED)));
                            } else {
                                player.sendMessage(Statements.getPrefix().append(Component.text("You already voted!", NamedTextColor.RED)));
                            }
                        } else {
                            if(LanguageAPI.getApi().getLanguage(player) == 2) {
                                player.sendMessage(Statements.getPrefix().append(Component.text("Du hast für die Freilassung gestimmt.", NamedTextColor.DARK_PURPLE)));
                            } else {
                                player.sendMessage(Statements.getPrefix().append(Component.text("You voted for release.", NamedTextColor.DARK_PURPLE)));
                            }
                        }
                        break;
                    }
                    default:
                        sendUsage(player);
                        break;
                }
                return;
            }
            if(LanguageAPI.getApi().getLanguage(player) == 2) {
                player.sendMessage(Statements.getPrefix().append(Component.text("Aktuell ist keiner Angeklagt!", NamedTextColor.RED)));
            } else {
                player.sendMessage(Statements.getPrefix().append(Component.text("Currently nobody is accused!", NamedTextColor.RED)));
            }
        }
    }

    private void sendUsage(CommandSender sender) {
        Player player = (Player) sender;
        if (LanguageAPI.getApi().getLanguage(player) == 2) {
            sender.sendMessage(NamedTextColor.GRAY + "Verwendung" + NamedTextColor.DARK_GRAY + ": " + NamedTextColor.BLUE + "/court join, pas, witness, judged, free");
        } else {
            sender.sendMessage(NamedTextColor.GRAY + "Use" + NamedTextColor.DARK_GRAY + ": " + NamedTextColor.BLUE + "/court join, pas, witness, judged, convinced");
        }

    }
}