package com.sumutiu.easyeconomy.storage;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import static com.sumutiu.easyeconomy.EasyEconomy.STORAGE_FOLDER;
import static com.sumutiu.easyeconomy.util.EasyEconomyMessages.*;

public class BankStorage {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    // In-memory cache for balances
    private static final Map<UUID, Long> balanceCache = new ConcurrentHashMap<>();

    public static File getPlayerFile(UUID uuid) {
        return new File(STORAGE_FOLDER, uuid.toString() + ".json");
    }

    // ----------------------------
    // Read / Write (per player)
    // ----------------------------
    private static long loadFromFile(UUID uuid) {
        File file = getPlayerFile(uuid);
        if (!file.exists()) return 0L;

        try (Reader reader = new FileReader(file)) {
            PlayerBankData data = GSON.fromJson(reader, PlayerBankData.class);
            return (data != null) ? data.balance : 0L;
        } catch (IOException e) {
            Logger(2, String.format(BANK_FILE_READ_FAILED_PLAYER, uuid, e.getMessage()));
            return 0L;
        }
    }

    public static void saveToFile(UUID uuid, long balance) {
        File file = getPlayerFile(uuid);
        File tmpFile = new File(file.getParent(), file.getName() + ".tmp");

        PlayerBankData data = new PlayerBankData(balance);

        try (Writer writer = new FileWriter(tmpFile, false)) {
            GSON.toJson(data, writer);
        } catch (IOException e) {
            Logger(2, String.format(BANK_FILE_WRITE_FAILED_PLAYER, uuid, e.getMessage()));
            return;
        }

        try {
            Files.move(tmpFile.toPath(), file.toPath(), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            Logger(1, String.format(BANK_TEMP_RENAME_FAILED, uuid, e.getMessage()));
        }
    }

    // ----------------------------
    // Public API
    // ----------------------------
    public static synchronized long getBalance(UUID uuid) {
        return balanceCache.computeIfAbsent(uuid, BankStorage::loadFromFile);
    }

    public static synchronized void addBalance(UUID uuid, long amount) {
        if (amount <= 0) return;
        long newBalance = getBalance(uuid) + amount;
        balanceCache.put(uuid, newBalance);
        saveToFile(uuid, newBalance);
        Logger(0, String.format(BANK_ADDED, amount, uuid, newBalance));
    }

    public static synchronized boolean removeBalance(UUID uuid, long amount) {
        if (amount <= 0) return false;
        long current = getBalance(uuid);
        if (current < amount) return false;
        long newBalance = current - amount;
        balanceCache.put(uuid, newBalance);
        saveToFile(uuid, newBalance);
        Logger(0, String.format(BANK_REMOVED, amount, uuid, newBalance));
        return true;
    }

    public static synchronized void setBalance(UUID uuid, long amount) {
        if (amount < 0) amount = 0;
        balanceCache.put(uuid, amount);
        saveToFile(uuid, amount);
        Logger(0, "Set balance of " + uuid + " to " + amount + ".");
    }

    // Optionally clear cache when player leaves
    public static synchronized void unloadPlayer(UUID uuid) {
        balanceCache.remove(uuid);
    }

    public static synchronized List<Map.Entry<UUID, Long>> getTopBalances(int count) {
        File folder = STORAGE_FOLDER;
        if (!folder.exists()) return Collections.emptyList();

        File[] files = folder.listFiles((f) -> f.isFile() && f.getName().toLowerCase().endsWith(".json"));
        if (files == null) return Collections.emptyList();

        Map<UUID, Long> allBalances = new HashMap<>();
        for (File f : files) {
            String name = f.getName();
            int dot = name.lastIndexOf('.');
            if (dot <= 0) continue;
            try {
                UUID uuid = UUID.fromString(name.substring(0, dot));
                allBalances.put(uuid, getBalance(uuid));
            } catch (IllegalArgumentException ignored) {}
        }

        List<Map.Entry<UUID, Long>> sorted = new ArrayList<>(allBalances.entrySet());
        sorted.sort((a, b) -> Long.compare(b.getValue(), a.getValue()));

        if (sorted.size() <= count) return sorted;
        return sorted.subList(0, count);
    }

    // ----------------------------
    // Data Model
    // ----------------------------
    private static class PlayerBankData {
        long balance;

        PlayerBankData(long balance) {
            this.balance = balance;
        }
    }
}
