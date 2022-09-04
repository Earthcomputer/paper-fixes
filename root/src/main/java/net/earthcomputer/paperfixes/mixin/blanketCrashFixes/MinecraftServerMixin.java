/*
 * Copyright (c) paper-fixes
 *
 * MIT License
 */

package net.earthcomputer.paperfixes.mixin.blanketCrashFixes;

import net.earthcomputer.paperfixes.impl.blanketCrashFixes.BlanketCrashFixUtil;
import net.earthcomputer.paperfixes.impl.mixinHelper.SurroundMethodWithTryCatch;
import net.minecraft.server.MinecraftServer;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(MinecraftServer.class)
public class MinecraftServerMixin {
    @SurroundMethodWithTryCatch(method = {"tickServer", "tickChildren"})
    private static void voidTryCatch(Throwable t) {
        BlanketCrashFixUtil.ignoreException(t);
    }

    @SurroundMethodWithTryCatch(method = "saveEverything")
    private static boolean booleanTryCatch(Throwable t) {
        BlanketCrashFixUtil.ignoreException(t);
        return false;
    }
}
