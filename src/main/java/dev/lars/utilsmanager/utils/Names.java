package dev.lars.utilsmanager.utils;

import org.jetbrains.annotations.NotNull;

public final class Names {

    private Names() {}

    public static final String BASE_PACKAGE = "dev.lars.utilsmanager";

    public static final class Permissions {
        public static final String ADMIN_BASE = "utilsmanager.admin";
        public static final String BAN = ADMIN_BASE + ".ban";
        public static final String KICK = ADMIN_BASE + ".kick";
        public static final String BACKPACK_USE = "utilsmanager.backpack.use";
        public static final String BACKPACK_OTHER = "utilsmanager.backpack.other";
        public static final String HOME_SET = "utilsmanager.home.set";
        public static final String HOME_USE = "utilsmanager.home.use";
        public static final String ECONOMY_ADMIN = "utilsmanager.economy.admin";
    }

    public static final class ConfigKeys {
        public static final String MESSAGES_FILE = "messages_en.yml";
        public static final String MAIN_CONFIG = "config.yml";
        public static final String PREFIX_KEY = "prefix";
        public static final String MAINTENANCE_ENABLED = "maintenance.enabled";
    }

    public static final class MessageKeys {
        public static final String PREFIX = "prefix";
        public static final String NO_PERMISSION = "no-permission";
        public static final String ONLY_PLAYER = "only-player";
        public static final String PLAYER_NOT_FOUND = "player-not-found";
        public static final String INVALID_USAGE = "invalid-usage";
        public static final String ERROR_OCCURRED = "error-occurred";

        public static final String BACKPACK_OPENED = "backpack.opened";
        public static final String BACKPACK_SAVED = "backpack.saved";
        public static final String BACKPACK_NONE = "backpack.no-backpack";

        public static final String HOME_SET = "home.set";
        public static final String HOME_DELETED = "home.deleted";
        public static final String HOME_NOT_SET = "home.not-set";
        public static final String HOME_TELEPORT = "home.teleport";

        public static final String ECONOMY_BALANCE = "economy.balance";
        public static final String ECONOMY_PAY_SUCCESS = "economy.pay-success";
        public static final String ECONOMY_PAY_RECEIVED = "economy.pay-received";

        public static final String BAN_BANNED = "ban.banned";
        public static final String BAN_BROADCAST = "ban.ban-broadcast";
        public static final String KICK_KICKED = "kick.kicked";

        public static final String RELOAD = "reload.success";
    }

    public static @NotNull String formatPermission(String node) {
        return node;
    }
}
