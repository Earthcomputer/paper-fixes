/*
 * Copyright (c) paper-fixes
 *
 * MIT License
 */

package net.earthcomputer.paperfixes.mixin.blanketCrashFixes;

import net.earthcomputer.paperfixes.impl.blanketCrashFixes.BlanketCrashFixUtil;
import net.earthcomputer.paperfixes.impl.mixinHelper.SurroundMethodWithTryCatch;
import net.minecraft.world.level.dimension.end.EndDragonFight;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(EndDragonFight.class)
public class EndDragonFightMixin {
    @SurroundMethodWithTryCatch(method = "tick")
    private static void voidTryCatch(Throwable t) {
        BlanketCrashFixUtil.ignoreException(t);
    }
}
