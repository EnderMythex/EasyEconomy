package com.sumutiu.easyeconomy.storage;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

import static com.sumutiu.easyeconomy.EasyEconomy.AH_FOLDER;
import static com.sumutiu.easyeconomy.util.EasyEconomyMessages.*;

public class AHStorage {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    public static class AHListing {
        public String itemId;
        public int quantity;
        public long price; // in diamonds
        public long timestamp; // epoch millis
        public UUID seller;
        public String sellerName;
        public String nbt; // Serialized ItemStack SNBT

        public AHListing(String itemId, int quantity, long price, UUID seller, String sellerName, String nbt) {
            this.itemId = itemId;
            this.quantity = quantity;
            this.price = price;
            this.timestamp = System.currentTimeMillis();
            this.seller = seller;
            this.sellerName = sellerName;
            this.nbt = nbt;
        }
    }

    public static File getFile(UUID uuid) {
        return new File(AH_FOLDER, uuid.toString() + ".json");
    }

    public static List<AHListing> loadListings(UUID uuid) {
        File file = getFile(uuid);
        if (!file.exists()) {
            Logger(0, String.format(AH_NO_FILE, uuid));
            return new ArrayList<>();
        }

        try (Reader reader = new FileReader(file)) {
            AHListing[] arr = GSON.fromJson(reader, AHListing[].class);
            return arr != null ? new ArrayList<>(Arrays.asList(arr)) : new ArrayList<>();
        } catch (IOException e) {
            Logger(2, String.format(AH_FILE_LOAD_ERROR, uuid, e.getMessage()));
            return new ArrayList<>();
        }
    }

    public static void saveListings(UUID uuid, List<AHListing> listings) {
        try (Writer writer = new FileWriter(getFile(uuid))) {
            GSON.toJson(listings, writer);
        } catch (IOException e) {
            Logger(2, String.format(AH_FILE_SAVE_ERROR, uuid, e.getMessage()));
        }
    }

    public static List<AHListing> getActiveListings(List<AHListing> all) {
        long now = System.currentTimeMillis();
        return all.stream()
                .filter(l -> now - l.timestamp < 24*60*60*1000L)
                .collect(Collectors.toList());
    }

    private static final Object BUY_LOCK = new Object();

    public static Object getBuyLock() {
        return BUY_LOCK;
    }

    public static synchronized boolean removeListing(UUID seller, long timestamp) {
        List<AHListing> sellerListings = loadListings(seller);
        boolean removed = sellerListings.removeIf(l -> l.timestamp == timestamp && l.seller.equals(seller));
        if (removed) {
            saveListings(seller, sellerListings);
        }
        return removed;
    }

    public static List<AHListing> getExpiredListings(List<AHListing> all) {
        long now = System.currentTimeMillis();
        return all.stream()
                .filter(l -> now - l.timestamp >= 24*60*60*1000L)
                .collect(Collectors.toList());
    }
}
