/*
 * Copyright (c) paper-fixes
 *
 * MIT License
 */

package net.earthcomputer.paperfixes.impl.mixinHelper;

import org.objectweb.asm.Type;
import org.objectweb.asm.tree.AnnotationNode;
import org.objectweb.asm.tree.MethodNode;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.code.Injector;
import org.spongepowered.asm.mixin.injection.struct.InjectionInfo;
import org.spongepowered.asm.mixin.transformer.MixinTargetContext;
import org.spongepowered.asm.util.asm.ASM;

import java.util.Collections;
import java.util.List;

@InjectionInfo.HandlerPrefix("catch")
@InjectionInfo.AnnotationType(SurroundMethodWithTryCatch.class)
public class SurroundMethodWithTryCatchInfo extends InjectionInfo {
    public SurroundMethodWithTryCatchInfo(MixinTargetContext mixin, MethodNode method, AnnotationNode annotation) {
        super(mixin, method, annotation);
    }

    @Override
    protected List<AnnotationNode> readInjectionPoints() {
        AnnotationNode annotation = new AnnotationNode(ASM.API_VERSION, Type.getDescriptor(At.class));
        annotation.visit("value", "HEAD");
        annotation.visitEnd();
        return Collections.singletonList(annotation);
    }

    @Override
    protected Injector parseInjector(AnnotationNode injectAnnotation) {
        return new SurroundMethodWithTryCatchInjector(this);
    }

    @Override
    protected String getDescription() {
        return "Try Catch Surrounder";
    }
}
