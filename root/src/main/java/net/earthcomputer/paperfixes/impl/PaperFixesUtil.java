/*
 * Copyright (c) paper-fixes
 *
 * MIT License
 */

package net.earthcomputer.paperfixes.impl;

import com.chocohead.mm.api.ClassTinkerers;
import org.objectweb.asm.tree.ClassNode;
import org.quiltmc.loader.api.QuiltLoader;

import java.util.function.Consumer;

public enum PaperFixesUtil {
    ;

    public static void addTransformer(String intermediaryName, Consumer<ClassNode> transformer) {
        String mappedName = QuiltLoader.getMappingResolver().mapClassName("intermediary", "net.minecraft." + intermediaryName);
        ClassTinkerers.addTransformation(mappedName, transformer);
    }

    public static String mapMethod(String owner, String name, String desc) {
        return QuiltLoader.getMappingResolver().mapMethodName("intermediary", "net.minecraft." + owner, name, desc);
    }
}
