/*
 * Copyright (c) paper-fixes
 *
 * MIT License
 */

package net.earthcomputer.paperfixes.mixin.blanketCrashFixes;

import net.earthcomputer.paperfixes.impl.blanketCrashFixes.BlanketCrashFixUtil;
import net.earthcomputer.paperfixes.impl.mixinHelper.SurroundMethodWithTryCatch;
import net.minecraft.world.entity.raid.Raid;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(Raid.class)
public class RaidMixin {
    @SurroundMethodWithTryCatch(method = "tick")
    private static void voidTryCatch(Throwable t) {
        BlanketCrashFixUtil.ignoreException(t);
    }
}
