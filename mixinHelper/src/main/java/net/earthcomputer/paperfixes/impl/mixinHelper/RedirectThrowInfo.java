/*
 * Copyright (c) paper-fixes
 *
 * MIT License
 */

package net.earthcomputer.paperfixes.impl.mixinHelper;

import org.objectweb.asm.tree.AnnotationNode;
import org.objectweb.asm.tree.MethodNode;
import org.spongepowered.asm.mixin.injection.code.Injector;
import org.spongepowered.asm.mixin.injection.struct.InjectionInfo;
import org.spongepowered.asm.mixin.transformer.MixinTargetContext;

@InjectionInfo.HandlerPrefix("redirectThrow")
@InjectionInfo.AnnotationType(RedirectThrow.class)
public class RedirectThrowInfo extends InjectionInfo {
    public RedirectThrowInfo(MixinTargetContext mixin, MethodNode method, AnnotationNode annotation) {
        super(mixin, method, annotation);
    }

    @Override
    protected Injector parseInjector(AnnotationNode injectAnnotation) {
        return new RedirectThrowInjector(this);
    }

    @Override
    protected String getDescription() {
        return "Throw Redirector";
    }
}
