package com.sumutiu.easyeconomy.util;

import com.sumutiu.easyeconomy.storage.AHStorage;
import com.sumutiu.easyeconomy.storage.AHStorageHelper;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.MenuProvider;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.inventory.AbstractContainerMenu;
import org.jspecify.annotations.NonNull;

import java.util.List;

import static com.sumutiu.easyeconomy.util.EasyEconomyMessages.*;

public class AHScreenFactory {

    public static void open(ServerPlayer player) {
        List<AHStorage.AHListing> allActive = AHStorageHelper.getAllActiveListings();

        if (allActive.isEmpty()) {
            EasyEconomyMessages.PrivateMessage(player, SHOP_NO_ACTIVE_LISTING);
            return;
        }

        MenuProvider factory = new MenuProvider() {

            @Override
            public @NonNull Component getDisplayName() {
                return Component.literal("Shop");
            }

            @Override
            public AbstractContainerMenu createMenu(int syncId, @NonNull Inventory playerInventory, @NonNull Player playerEntity) {
                return new AHScreenHandler(
                        syncId,
                        new SimpleContainer(AHScreenHandler.SIZE),
                        allActive,
                        playerEntity
                );
            }
        };

        player.openMenu(factory);
    }

    public static void openWithFilter(ServerPlayer player, String itemId) {
        List<AHStorage.AHListing> allActive = AHStorageHelper.getAllActiveListings();

        if (allActive.isEmpty()) {
            EasyEconomyMessages.PrivateMessage(player, SHOP_NO_ACTIVE_LISTING);
            return;
        }

        List<AHStorage.AHListing> filtered = new java.util.ArrayList<>();
        for (AHStorage.AHListing l : allActive) {
            if (l.itemId != null && l.itemId.equals(itemId)) {
                filtered.add(l);
            }
        }

        if (filtered.isEmpty()) {
            EasyEconomyMessages.PrivateMessage(player, SHOP_LIST_NONE);
            return;
        }

        MenuProvider factory = new MenuProvider() {

            @Override
            public @NonNull Component getDisplayName() {
                return Component.literal("Shop");
            }

            @Override
            public AbstractContainerMenu createMenu(int syncId, @NonNull Inventory playerInventory, @NonNull Player playerEntity) {
                AHScreenHandler handler = new AHScreenHandler(
                        syncId,
                        new SimpleContainer(AHScreenHandler.SIZE),
                        filtered,
                        playerEntity
                );
                handler.setFilter(itemId);
                return handler;
            }
        };

        player.openMenu(factory);
    }
}