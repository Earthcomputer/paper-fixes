/*
 * Copyright (c) paper-fixes
 *
 * MIT License
 */

package net.earthcomputer.paperfixes.mixin.blanketCrashFixes;

import net.earthcomputer.paperfixes.impl.blanketCrashFixes.BlanketCrashFixUtil;
import net.earthcomputer.paperfixes.impl.mixinHelper.SurroundMethodWithTryCatch;
import net.minecraft.server.level.ServerChunkCache;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(ServerChunkCache.class)
public class ServerChunkCacheMixin {
    @SurroundMethodWithTryCatch(method = {"tick", "tickChunks"})
    private static void voidTryCatch(Throwable t) {
        BlanketCrashFixUtil.ignoreException(t);
    }
}
