/*
 * Copyright (c) paper-fixes
 *
 * MIT License
 */

package net.earthcomputer.paperfixes.impl.mixinHelper;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.LabelNode;
import org.objectweb.asm.tree.TryCatchBlockNode;
import org.spongepowered.asm.mixin.injection.code.Injector;
import org.spongepowered.asm.mixin.injection.struct.InjectionInfo;
import org.spongepowered.asm.mixin.injection.struct.InjectionNodes;
import org.spongepowered.asm.mixin.injection.struct.Target;
import org.spongepowered.asm.util.Bytecode;

public class SurroundMethodWithTryCatchInjector extends Injector {
    public SurroundMethodWithTryCatchInjector(InjectionInfo info) {
        this(info, "@SurroundMethodWithTryCatch");
    }

    public SurroundMethodWithTryCatchInjector(InjectionInfo info, String annotationType) {
        super(info, annotationType);
    }

    @Override
    protected void inject(Target target, InjectionNodes.InjectionNode node) {
        if (!Bytecode.isStatic(this.methodNode)) {
            throw new IllegalArgumentException("Try catch surrounder must be static");
        }

        if (this.methodArgs.length != 1 || !this.methodArgs[0].equals(RedirectThrowInjector.THROWABLE_TYPE)) {
            throw new IllegalArgumentException("@SurroundMethodWithTryCatch must take exactly one argument of type Throwable");
        }
        if (!this.returnType.equals(target.returnType)) {
            throw new IllegalArgumentException("@SurroundMethodWithTryCatch must return the same as the target method");
        }

        LabelNode firstLabel = BytecodeUtil.findLabel(target.insns.getFirst());
        if (firstLabel == null) {
            throw new IllegalStateException("Could not find first label");
        }
        LabelNode catchLabel = new LabelNode();

        InsnList catchBlock = new InsnList();
        catchBlock.add(catchLabel);
        this.invokeHandler(catchBlock);
        catchBlock.add(new InsnNode(this.returnType.getOpcode(Opcodes.IRETURN)));

        target.insns.add(catchBlock);

        target.method.tryCatchBlocks.add(new TryCatchBlockNode(firstLabel, catchLabel, catchLabel, "java/lang/Throwable"));
    }
}
