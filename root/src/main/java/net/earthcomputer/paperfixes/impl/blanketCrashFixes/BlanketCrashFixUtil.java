/*
 * Copyright (c) paper-fixes
 *
 * MIT License
 */

package net.earthcomputer.paperfixes.impl.blanketCrashFixes;

import com.mojang.logging.LogUtils;
import org.slf4j.Logger;

public enum BlanketCrashFixUtil {
    ;
    private static final Logger LOGGER = LogUtils.getLogger();

    public static void ignoreException(Throwable throwable) {
        LOGGER.error("An exception that would have caused a crash was ignored. Please report this to the relevant mod author or to paper-fixes if it's vanilla.", throwable);
    }
}
