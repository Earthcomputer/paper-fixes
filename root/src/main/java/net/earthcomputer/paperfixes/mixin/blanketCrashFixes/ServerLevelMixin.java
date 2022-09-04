/*
 * Copyright (c) paper-fixes
 *
 * MIT License
 */

package net.earthcomputer.paperfixes.mixin.blanketCrashFixes;

import net.earthcomputer.paperfixes.impl.blanketCrashFixes.BlanketCrashFixUtil;
import net.earthcomputer.paperfixes.impl.mixinHelper.SurroundMethodWithTryCatch;
import net.minecraft.server.level.ServerLevel;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(ServerLevel.class)
public class ServerLevelMixin {
    @SurroundMethodWithTryCatch(method = {
        "tick",
        "advanceWeatherCycle",
        "wakeUpAllPlayers",
        "tickTime",
        "tickBlock",
        "tickFluid",
        "tickChunk",
        "runBlockEvents",
        "sendGameEvents"
    })
    private static void voidTryCatch(Throwable t) {
        BlanketCrashFixUtil.ignoreException(t);
    }

    @SurroundMethodWithTryCatch(method = "doBlockEvent")
    private static boolean booleanTryCatch(Throwable t) {
        BlanketCrashFixUtil.ignoreException(t);
        return false;
    }
}
