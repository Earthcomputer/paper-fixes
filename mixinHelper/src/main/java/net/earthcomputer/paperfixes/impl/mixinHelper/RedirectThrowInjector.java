/*
 * Copyright (c) paper-fixes
 *
 * MIT License
 */

package net.earthcomputer.paperfixes.impl.mixinHelper;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.InsnNode;
import org.spongepowered.asm.mixin.injection.code.Injector;
import org.spongepowered.asm.mixin.injection.struct.InjectionInfo;
import org.spongepowered.asm.mixin.injection.struct.InjectionNodes;
import org.spongepowered.asm.mixin.injection.struct.Target;
import org.spongepowered.asm.util.Bytecode;

public class RedirectThrowInjector extends Injector {
    static final Type THROWABLE_TYPE = Type.getObjectType("java/lang/Throwable");

    public RedirectThrowInjector(InjectionInfo info) {
        this(info, "@RedirectThrow");
    }

    public RedirectThrowInjector(InjectionInfo info, String annotationType) {
        super(info, annotationType);
    }

    @Override
    protected void inject(Target target, InjectionNodes.InjectionNode node) {
        AbstractInsnNode targetNode = node.getCurrentTarget();

        if (!Bytecode.isStatic(this.methodNode)) {
            throw new IllegalArgumentException("Throw redirector must be static");
        }

        if (node.isReplaced()) {
            throw new IllegalArgumentException("Throw redirector target failure for " + this.info);
        }

        if (targetNode.getOpcode() != Opcodes.ATHROW) {
            throw new IllegalArgumentException("@RedirectThrow can only target athrow instructions");
        }

        if (this.methodArgs.length != 1 || !this.methodArgs[0].equals(THROWABLE_TYPE)) {
            throw new IllegalArgumentException("@RedirectThrow must take exactly one argument of type Throwable");
        }
        if (!this.returnType.equals(target.returnType)) {
            throw new IllegalArgumentException("@RedirectThrow must return the same as the target method");
        }

        InsnList replacement = new InsnList();
        AbstractInsnNode champion = this.invokeHandler(replacement);
        replacement.add(new InsnNode(this.returnType.getOpcode(Opcodes.IRETURN)));
        target.replaceNode(targetNode, champion, replacement);
    }
}
