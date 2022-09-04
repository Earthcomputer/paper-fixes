/*
 * Copyright (c) paper-fixes
 *
 * MIT License
 */

package net.earthcomputer.paperfixes.impl;

import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.List;
import java.util.Set;

public class PaperFixesMixinConfigPlugin implements IMixinConfigPlugin {
    private static final String MIXIN_CLASS_PREFIX = "net.earthcomputer.paperfixes.mixin.";

    @Override
    public void onLoad(String mixinPackage) {
    }

    @Override
    public String getRefMapperConfig() {
        return null;
    }

    @Override
    public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {
        if (!mixinClassName.startsWith(MIXIN_CLASS_PREFIX)) {
            throw new IllegalArgumentException("mixin class name " + mixinClassName + " does not start with " + MIXIN_CLASS_PREFIX);
        }
        int dotIndex = mixinClassName.indexOf('.', MIXIN_CLASS_PREFIX.length());
        if (dotIndex == -1) {
            throw new IllegalArgumentException("mixin class name " + mixinClassName + " is not in a subpackage");
        }
        String subpackage = mixinClassName.substring(MIXIN_CLASS_PREFIX.length(), dotIndex);

        Field field;
        try {
            field = PaperFixesConfig.class.getField(subpackage);
        } catch (NoSuchFieldException e) {
            throw new IllegalArgumentException("mixin subpackage " + subpackage + " has no corresponding field in config");
        }
        if (Modifier.isStatic(field.getModifiers()) || !field.canAccess(PaperFixesConfig.INSTANCE) || field.getType() != boolean.class) {
            throw new IllegalArgumentException("mixin subpackage " + subpackage + " has no corresponding boolean option in config");
        }
        try {
            return field.getBoolean(PaperFixesConfig.INSTANCE);
        } catch (IllegalAccessException e) {
            throw new IllegalArgumentException("Failed to access config option " + subpackage);
        }
    }

    @Override
    public void acceptTargets(Set<String> myTargets, Set<String> otherTargets) {
    }

    @Override
    public List<String> getMixins() {
        return null;
    }

    @Override
    public void preApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {
    }

    @Override
    public void postApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {
    }
}
