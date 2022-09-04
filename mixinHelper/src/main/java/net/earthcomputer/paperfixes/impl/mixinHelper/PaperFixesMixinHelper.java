/*
 * Copyright (c) paper-fixes
 *
 * MIT License
 */

package net.earthcomputer.paperfixes.impl.mixinHelper;

import org.spongepowered.asm.mixin.injection.InjectionPoint;
import org.spongepowered.asm.mixin.injection.struct.InjectionInfo;

public enum PaperFixesMixinHelper {
    ;

    public static void bootstrap() {
        InjectionPoint.register(BeforeThrowInjectionPoint.class, "paperfixes");
        InjectionInfo.register(RedirectThrowInfo.class);
        InjectionInfo.register(SurroundMethodWithTryCatchInfo.class);
    }
}
