package com.sumutiu.easyeconomy.storage;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import static com.sumutiu.easyeconomy.EasyEconomy.STORAGE_FOLDER;
import static com.sumutiu.easyeconomy.util.EasyEconomyMessages.Logger;

public class NameCache {

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final Type MAP_TYPE = new TypeToken<Map<String, String>>() {}.getType();
    private static final Map<UUID, String> cache = new ConcurrentHashMap<>();

    public static File getFile() {
        return new File(STORAGE_FOLDER, "name_cache.json");
    }

    public static void load() {
        File file = getFile();
        if (!file.exists()) return;

        try (Reader reader = new FileReader(file)) {
            Map<String, String> raw = GSON.fromJson(reader, MAP_TYPE);
            if (raw != null) {
                cache.clear();
                for (Map.Entry<String, String> entry : raw.entrySet()) {
                    try {
                        cache.put(UUID.fromString(entry.getKey()), entry.getValue());
                    } catch (IllegalArgumentException ignored) {}
                }
            }
        } catch (IOException e) {
            Logger(2, "Failed to load name cache: " + e.getMessage());
        }
    }

    public static void save() {
        File file = getFile();
        try (Writer writer = new FileWriter(file)) {
            Map<String, String> raw = new java.util.HashMap<>();
            for (Map.Entry<UUID, String> entry : cache.entrySet()) {
                raw.put(entry.getKey().toString(), entry.getValue());
            }
            GSON.toJson(raw, writer);
        } catch (IOException e) {
            Logger(2, "Failed to save name cache: " + e.getMessage());
        }
    }

    public static void put(UUID uuid, String name) {
        if (uuid == null || name == null || name.isBlank()) return;
        cache.put(uuid, name);
        save();
    }

    public static String getName(UUID uuid) {
        return cache.getOrDefault(uuid, uuid.toString().substring(0, 8) + "...");
    }

    public static UUID getUUID(String name) {
        if (name == null || name.isBlank()) return null;
        for (Map.Entry<UUID, String> entry : cache.entrySet()) {
            if (entry.getValue().equalsIgnoreCase(name)) {
                return entry.getKey();
            }
        }
        return null;
    }

    public static String[] getAllNames() {
        return cache.values().toArray(new String[0]);
    }

    public static String[] getAutocompleteSuggestions() {
        java.util.List<String> suggestions = new java.util.ArrayList<>();
        for (Map.Entry<UUID, String> entry : cache.entrySet()) {
            String name = entry.getValue();
            if (name != null && !name.isBlank()) {
                suggestions.add(name);
            }
        }

        if (STORAGE_FOLDER != null && STORAGE_FOLDER.exists()) {
            File[] files = STORAGE_FOLDER.listFiles((f) -> f.isFile() && f.getName().toLowerCase().endsWith(".json") && !f.getName().equals("name_cache.json"));
            if (files != null) {
                for (File f : files) {
                    int dot = f.getName().lastIndexOf('.');
                    if (dot <= 0) continue;
                    try {
                        UUID uuid = UUID.fromString(f.getName().substring(0, dot));
                        if (!cache.containsKey(uuid)) {
                            suggestions.add(uuid.toString());
                        }
                    } catch (IllegalArgumentException ignored) {}
                }
            }
        }

        return suggestions.toArray(new String[0]);
    }
}
