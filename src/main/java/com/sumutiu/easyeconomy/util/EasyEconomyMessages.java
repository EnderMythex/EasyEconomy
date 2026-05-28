package com.sumutiu.easyeconomy.util;

import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.ChatFormatting;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EasyEconomyMessages {

    // ----------------------------
    // Core / General
    // ----------------------------
    public static final String MOD_ASCII_BANNER = """
         ______                ______                                     \s
        |  ____|              |  ____|                                    \s
        | |__   __ _ ___ _   _| |__   ___ ___  _ __   ___  _ __ ___  _   _\s
        |  __| / _` / __| | | |  __| / __/ _ \\| '_ \\ / _ \\| '_ ` _ \\| | | |
        | |___| (_| \\__ \\ |_| | |___| (_| (_) | | | | (_) | | | | | | |_| |
        |______\\__,_|___/\\__, |______\\___\\___/|_| |_|\\___/|_| |_| |_|\\__, |
                          __/ |                                       __/ |
                         |___/                                       |___/\s
        """;

    public static final String Mod_ID = "[EasyEconomy]";
    //public static final String INVALID_CONNECTION_HANDLER = "Invalid connection handler or player during join event.";
    public static final String PLAYER_ONLY_COMMAND = "This command can only be used by players.";

    // ----------------------------
    // Configuration / Storage
    // ----------------------------
    public static final String MAIN_FOLDER_CREATED = "Mod folders have been created successfully.";
    public static final String MAIN_FOLDER_CREATION_FAILED = "Failed to create the mod folders.";
    public static final String MOD_INIT_FAILED = "Mod has failed to initialize. Error in creating the mod folders.";

    public static final String BANK_FILE_READ_FAILED_PLAYER = "Failed to load bank file for player %s. Error: %s.";
    public static final String BANK_FILE_WRITE_FAILED_PLAYER = "Failed to save bank file for player %s. Error: %s.";
    public static final String BANK_TEMP_RENAME_FAILED = "Failed to rename temp bank file for player %s. Error: %s.";

    // ----------------------------
    // Bank - General
    // ----------------------------
    public static final String BANK_BALANCE = "Your bank balance: %d diamonds.";
    public static final String BANK_BALANCE_INSUFFICIENT = "Insufficient balance. You have %d diamonds in bank.";
    public static final String BANK_BALANCE_NO_SPACE = "Not enough inventory space to withdraw %d diamonds. Free up some slots.";
    public static final String BANK_BALANCE_ERROR = "Failed to withdraw due to concurrent change. Try again.";
    public static final String BANK_BALANCE_CONFIRM = "Withdrew %d diamonds from your bank.";
    public static final String INVENTORY_EMPTY = "You have no diamonds to deposit.";
    public static final String BANK_DEPOSIT_QTY = "Deposited %d diamonds to your bank.";
    public static final String BANK_DEPOSIT_FAILED = "Failed to deposit for player %s. Error: %s.";
    public static final String BANK_DEPOSIT_FAILED_PRIVATE = "An error occurred while depositing. Please contact an admin.";
    public static final String BANK_PAY_NEGATIVE = "The quantity must be positive.";

    public static final String LEADERBOARD_HEADER = "--- Rich List (Top %d) ---";
    public static final String LEADERBOARD_ENTRY = "#%d - %s: %d diamonds";
    public static final String LEADERBOARD_EMPTY = "No players found.";

    // ----------------------------
    // Bank - Welcome / Notifications
    // ----------------------------
    public static final String BANK_WELCOME_NEW_PLAYER = "Welcome to Easy Economy! Your bank has been created with 0 diamonds.";
    public static final String BANK_FILE_CREATED_FOR_PLAYER = "Created new bank file for player %s.";
    public static final String BANK_INIT_FAILED = "Failed to initialize bank file for player %s. Error: %s.";
    public static final String BANK_INIT_FAILED_PRIVATE = "An error occurred while initializing your bank. Please contact an admin.";

    // ----------------------------
    // Bank - Pay Command
    // ----------------------------
    public static final String BANK_PAY_SUCCESS_SENT = "You paid %d diamonds to %s.";
    public static final String BANK_PAY_SUCCESS_RECEIVED = "You received %d diamonds from %s.";
    public static final String BANK_PAY_FAILED_INSUFFICIENT = "You do not have enough diamonds to pay %s. Your balance: %d.";
    public static final String BANK_PAY_FAILED_SELF = "You cannot pay yourself.";
    public static final String BANK_PAY_FAILED_PLAYER_NOT_FOUND = "Player '%s' not found or not online.";
    public static final String BANK_PAY_FAILED_ERROR = "An error occurred while processing your payment. Please contact an admin.";

    // ----------------------------
    // Bank - Logging / Errors
    // ----------------------------
    public static final String BANK_ADDED = "Added %d diamonds to player %s (new balance: %d).";
    public static final String BANK_REMOVED = "Removed %d diamonds from player %s (new balance: %d).";

    public static final String BANK_READ_FAILED = "Failed to read bank for player %s. Error: %s.";
    public static final String BANK_READ_FAILED_PRIVATE = "An error occurred while reading your bank. Please contact an admin.";
    public static final String BANK_WITHDRAW_FAILED = "Failed to withdraw for player %s. Error: %s.";
    public static final String BANK_WITHDRAW_FAILED_PRIVATE = "An error occurred while withdrawing. Please contact an admin.";
    public static final String PAY_FAILED_ERROR = "Failed to process payment from %s to %s. Error: %s.";

    // ----------------------------
    // Shop - General
    // ----------------------------
    public static final String SHOP_SELL_EMPTY = "You are not holding any item.";
    public static final String SHOP_SELL_NO_PRICE = "Price must be greater than 0.";
    public static final String SHOP_SELL_CONFIRMATION = "Listed %d of %s on the Shop for %d diamonds.";
    public static final String SHOP_BUY_ERROR = "Could not retrieve item from listing.";
    public static final String SHOP_BUY_CONFIRMATION = "Bought %d of %s for %d diamonds from %s.";
    public static final String SHOP_WITHDRAW_ERROR = "Failed to withdraw balance. Try again.";
    public static final String SHOP_BUY_NO_MONEY = "Not enough diamonds in your deposit to purchase this item.";
    public static final String SHOP_BUY_NO_SPACE = "Not enough inventory space to purchase this item.";
    public static final String SHOP_NO_ACTIVE_LISTING = "There are currently no active shop listings.";
    public static final String SHOP_LIST_EMPTY_HAND = "You are not holding any item. Hold an item to search the shop.";
    public static final String SHOP_LIST_NONE = "No listings found for this item.";

    // ----------------------------
    // Shop Admin
    // ----------------------------
    public static final String SHOPADMIN_SET_SUCCESS = "Set balance of %s to %d diamonds.";
    public static final String SHOPADMIN_LIST_HEADER = "--- All Players (%d) ---";
    public static final String SHOPADMIN_LIST_ENTRY = "%s: %d diamonds";
    public static final String SHOPADMIN_LIST_EMPTY = "No players found.";

    // ----------------------------
    // AH - Logging / Errors
    // ----------------------------
    public static final String AH_NO_FILE = "No file for player: %s.";
    public static final String AH_FILE_LOAD_ERROR = "Failed to load AH for %s. Error: %s.";
    public static final String AH_FILE_SAVE_ERROR = "Failed to save AH for %s. Error: %s.";
    public static final String AH_INVALID_ID = "Invalid item ID in listing: %s.";
    public static final String AH_ID_NOT_FOUND = "Item not found for ID: %s.";
    public static final String AH_ID_ITEMSTACK_ERROR = "Error converting listing to ItemStack: %s.";
    public static final String AH_ID_NBT_ERROR = "Failed to parse item NBT: %s.";
    public static final String AH_FILE_ERROR = "Could not list files in folder: %s.";
    public static final String AH_FOLDER_NOT_FOUND = "Folder not found: %s.";
    public static final String AH_FILE_NAME_ERROR = "Skipping file with unexpected name: %s.";
    public static final String AH_FILE_NAME_NO_UUID = "Skipping non-UUID file: %s.";
    public static final String AH_LISTING_INFO = "Found %d active listings in %s.";
    public static final String AH_LISTING_EMPTY = "No active listings in %s.";
    public static final String AH_LISTING_ALL = "Total active listings found: %s.";
    public static final String AH_NOT_INITIALIZED = "EasyEconomy mod is not initialized. Try again later.";
    public static final String MOD_INIT_NOT_READY = "Mod has not initialized.";

    private static final Logger LOGGER = LoggerFactory.getLogger(Mod_ID);

    // ----------------------------
    // Player messaging
    // ----------------------------
    public static void PrivateMessage(ServerPlayer player, String message) {
        if (isConnected(player)) {
            player.sendSystemMessage(
                    Component.literal(Mod_ID + ": ")
                            .withStyle(style -> style.withColor(ChatFormatting.GREEN))
                            .append(Component.literal(message)
                                    .withStyle(style -> style.withColor(ChatFormatting.WHITE)))
            );
        }
    }

    // ----------------------------
    // Logging
    // ----------------------------
    public static void Logger(int type, String message) {
        switch (type) {
            case 0 -> LOGGER.info(Mod_ID + ": {}", message);
            case 1 -> LOGGER.warn(Mod_ID + ": {}", message);
            case 2 -> LOGGER.error(Mod_ID + ": {}", message);
        }
    }

    // ----------------------------
    // Helper Methods
    // ----------------------------
    public static String getModVersion() {
        return FabricLoader.getInstance()
                .getModContainer("easyeconomy")
                .map(ModContainer::getMetadata)
                .map(meta -> meta.getVersion().getFriendlyString())
                .orElse("unknown");
    }

    public static boolean isConnected(ServerPlayer player) {
        return player != null && player.connection.getPlayer() == player;
    }

    public static void logAsciiBanner(String banner, String footer) {
        LOGGER.info("");
        for (String line : banner.stripTrailing().split("\n")) {
            LOGGER.info(line);
        }
        LOGGER.info("");
        LOGGER.info(footer);
        LOGGER.info("");
    }
}