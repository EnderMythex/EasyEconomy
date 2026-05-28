package com.sumutiu.easyeconomy;

import com.sumutiu.easyeconomy.commands.*;
import com.sumutiu.easyeconomy.storage.BankStorage;
import com.sumutiu.easyeconomy.storage.NameCache;
import eu.pb4.placeholders.api.PlaceholderResult;
import eu.pb4.placeholders.api.Placeholders;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerPlayer;

import java.io.File;
import java.util.Objects;
import java.util.UUID;

import static com.sumutiu.easyeconomy.util.EasyEconomyMessages.*;

public class EasyEconomy implements ModInitializer {

	public static File STORAGE_FOLDER;
	public static File AH_FOLDER;

	public static volatile boolean EasyEconomyInitialized = false;

	@Override
	public void onInitialize() {

		// -----------------------------
		// SERVER START (WORLD EXISTS)
		// -----------------------------
		ServerLifecycleEvents.SERVER_STARTED.register(server -> {

			long seed = server.getWorldGenSettings().options().seed();

			STORAGE_FOLDER = new File("mods/EasyEconomy_Seed_" + Long.toUnsignedString(seed) + "/Banks");
			AH_FOLDER = new File("mods/EasyEconomy_Seed_" + Long.toUnsignedString(seed) + "/AH");

			if (initPlugin()) {
				NameCache.load();
				registerBalancePlaceholder();
				EasyEconomyInitialized = true;
			} else {
				Logger(2, MOD_INIT_FAILED);
			}
		});

		CommandRegistrationCallback.EVENT.register((dispatcher, _, _) -> {
			DepositCommand.register(dispatcher);
			WithdrawCommand.register(dispatcher);
			BankCommand.register(dispatcher);
			PayCommand.register(dispatcher);
			ShopCommand.register(dispatcher);
			ShopAdminCommand.register(dispatcher);
			LeaderboardCommand.register(dispatcher);
		});

		// Player join
		ServerPlayConnectionEvents.JOIN.register((handler, _, _) -> {
			ServerPlayer player = handler.getPlayer();

			if (!EasyEconomyInitialized) {
				player.connection.disconnect(
						Component.literal(AH_NOT_INITIALIZED)
				);
				return;
			}

			UUID uuid = player.getUUID();

			NameCache.put(uuid, player.getName().getString());

			try {
				File playerFile = BankStorage.getPlayerFile(uuid);
				boolean isNewPlayer = !playerFile.exists();

				long balance = BankStorage.getBalance(uuid);

				if (isNewPlayer) {
					BankStorage.saveToFile(uuid, balance);
					Logger(0, String.format(BANK_FILE_CREATED_FOR_PLAYER, uuid));
					PrivateMessage(player, BANK_WELCOME_NEW_PLAYER);
				}

			} catch (Exception e) {
				Logger(2, String.format(BANK_INIT_FAILED, uuid, e.getMessage()));
				PrivateMessage(player, BANK_INIT_FAILED_PRIVATE);
			}
		});

		// Player quit
		ServerPlayConnectionEvents.DISCONNECT.register((handler, _) -> {
			if (EasyEconomyInitialized) {
				BankStorage.unloadPlayer(handler.getPlayer().getUUID());
			}
		});

		ServerLifecycleEvents.SERVER_STOPPED.register(_ ->
				EasyEconomyInitialized = false
		);
	}

	// Registers player balance placeholder
	private void registerBalancePlaceholder() {
		Placeholders.registerCommon(
				Identifier.fromNamespaceAndPath("easyeconomy", "balance"),
				(ctx, _) -> {

					if (!ctx.hasPlayer()) {
						return PlaceholderResult.invalid("No player");
					}

					long balance = BankStorage.getBalance(Objects.requireNonNull(ctx.player()).getUUID());

					return PlaceholderResult.value(
							Component.literal(String.valueOf(balance))
					);
				}
		);
	}


	// Initialize storage
	private static boolean initPlugin() {
		logAsciiBanner(
				MOD_ASCII_BANNER,
				Mod_ID + ": V" + getModVersion() + " - Because emeralds are overrated!"
		);

		if (!STORAGE_FOLDER.exists() || !AH_FOLDER.exists()) {
			if (STORAGE_FOLDER.mkdirs() && AH_FOLDER.mkdirs()) {
				Logger(0, MAIN_FOLDER_CREATED);
				return true;
			} else {
				Logger(2, MAIN_FOLDER_CREATION_FAILED);
				return false;
			}
		} else {
			return true;
		}
	}
}