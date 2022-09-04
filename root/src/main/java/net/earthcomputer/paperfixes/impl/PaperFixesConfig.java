/*
 * Copyright (c) paper-fixes
 *
 * MIT License
 */

package net.earthcomputer.paperfixes.impl;

import org.quiltmc.config.api.WrappedConfig;
import org.quiltmc.config.api.annotations.Comment;
import org.quiltmc.loader.api.config.QuiltConfig;

public final class PaperFixesConfig extends WrappedConfig {
    public static final PaperFixesConfig INSTANCE = QuiltConfig.create("paper-fixes", "paper-fixes", PaperFixesConfig.class);

    @Comment("""
        Whether blanket crash handlers should be added to ignore crashes.
        Note that if a crash would have occurred, the game may be put in an inconsistent state,
        which may lead to undefined behavior including item dupes.""")
    public final boolean blanketCrashFixes = true;
}
