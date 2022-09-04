/*
 * Copyright (c) paper-fixes
 *
 * MIT License
 */

package net.earthcomputer.paperfixes.mixin.blanketCrashFixes;

import net.earthcomputer.paperfixes.impl.blanketCrashFixes.BlanketCrashFixUtil;
import net.earthcomputer.paperfixes.impl.mixinHelper.SurroundMethodWithTryCatch;
import net.minecraft.server.level.ChunkHolder;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(ChunkHolder.class)
public class ChunkHolderMixin {
    @SurroundMethodWithTryCatch(method = "broadcastChanges")
    private static void voidTryCatch(Throwable t) {
        BlanketCrashFixUtil.ignoreException(t);
    }
}
