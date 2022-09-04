/*
 * Copyright (c) paper-fixes
 *
 * MIT License
 */

package net.earthcomputer.paperfixes.impl;

import net.earthcomputer.paperfixes.impl.blanketCrashFixes.BlanketCrashFixTransformers;
import net.earthcomputer.paperfixes.impl.mixinHelper.PaperFixesMixinHelper;

public class PaperFixesInit implements Runnable {
    @Override
    public void run() {
        PaperFixesMixinHelper.bootstrap();

        if (PaperFixesConfig.INSTANCE.blanketCrashFixes) {
            BlanketCrashFixTransformers.register();
        }
    }
}
