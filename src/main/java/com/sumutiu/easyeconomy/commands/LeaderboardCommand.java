package com.sumutiu.easyeconomy.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.sumutiu.easyeconomy.storage.BankStorage;
import com.sumutiu.easyeconomy.storage.NameCache;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.server.level.ServerPlayer;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import static com.sumutiu.easyeconomy.EasyEconomy.EasyEconomyInitialized;
import static com.sumutiu.easyeconomy.util.EasyEconomyMessages.*;
import static net.minecraft.commands.Commands.literal;

public class LeaderboardCommand {

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(literal("leaderboard")
                .executes(ctx -> {
                    CommandSourceStack source = ctx.getSource();
                    if (!(source.getEntity() instanceof ServerPlayer player)) {
                        Logger(1, PLAYER_ONLY_COMMAND);
                        return 0;
                    }

                    if (!EasyEconomyInitialized) {
                        PrivateMessage(player, MOD_INIT_NOT_READY);
                        return 0;
                    }

                    int topN = 10;
                    List<Map.Entry<UUID, Long>> top = BankStorage.getTopBalances(topN);

                    if (top.isEmpty()) {
                        PrivateMessage(player, LEADERBOARD_EMPTY);
                        return 0;
                    }

                    PrivateMessage(player, String.format(LEADERBOARD_HEADER, top.size()));

                    var server = source.getServer();
                    for (int i = 0; i < top.size(); i++) {
                        Map.Entry<UUID, Long> entry = top.get(i);
                        String name = resolveName(server, entry.getKey());
                        PrivateMessage(player, String.format(LEADERBOARD_ENTRY, i + 1, name, entry.getValue()));
                    }

                    return 1;
                })
        );
    }

    private static String resolveName(net.minecraft.server.MinecraftServer server, UUID uuid) {
        ServerPlayer online = server.getPlayerList().getPlayer(uuid);
        if (online != null) return online.getName().getString();

        return NameCache.getName(uuid);
    }
}
