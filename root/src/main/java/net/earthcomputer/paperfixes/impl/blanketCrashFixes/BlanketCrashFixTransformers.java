/*
 * Copyright (c) paper-fixes
 *
 * MIT License
 */

package net.earthcomputer.paperfixes.impl.blanketCrashFixes;

import net.earthcomputer.paperfixes.impl.mixinHelper.BytecodeUtil;
import net.earthcomputer.paperfixes.impl.PaperFixesUtil;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.*;

public class BlanketCrashFixTransformers {
    private static final String ITERATOR = "java/util/Iterator";
    private static final String BLANKET_CRASH_FIX_UTIL = "net/earthcomputer/paperfixes/impl/blanketCrashFixes/BlanketCrashFixUtil";
    private static final String IGNORE_EXCEPTION = "ignoreException";
    private static final String IGNORE_EXCEPTION_DESC = "(Ljava/lang/Throwable;)V";
    private static final String THROWABLE = "java/lang/Throwable";

    private static final String LEVEL = "class_1937";

    public static void register() {
        PaperFixesUtil.addTransformer(LEVEL, BlanketCrashFixTransformers::transformLevel);
    }

    private static void transformLevel(ClassNode level) {
        String tickBlockEntities = PaperFixesUtil.mapMethod(LEVEL, "method_18471", "()V");
        boolean seenTickBlockEntities = false;

        for (MethodNode method : level.methods) {
            if (method.name.equals(tickBlockEntities) && "()V".equals(method.desc)) {
                transformLevelTickBlockEntities(method);
                seenTickBlockEntities = true;
            }
        }

        if (!seenTickBlockEntities) {
            throw new IllegalStateException("Didn't transform tickBlockEntities");
        }
    }

    private static void transformLevelTickBlockEntities(MethodNode method) {
        // search for Iterator.hasNext
        MethodInsnNode hasNext = BytecodeUtil.findMethodInsn(method.instructions.getFirst(), Opcodes.INVOKEINTERFACE, ITERATOR, "hasNext", "()Z");
        if (hasNext == null) {
            throw new IllegalStateException("Could not find Iterator.hasNext() call");
        }

        // find label before hasNext
        LabelNode preLabel = null;
        for (AbstractInsnNode insn = hasNext; insn != null; insn = insn.getPrevious()) {
            if (insn instanceof LabelNode label) {
                preLabel = label;
                break;
            }
        }
        if (preLabel == null) {
            throw new IllegalStateException("Could not find label before Iterator.hasNext() call");
        }

        // find jump after hasNext
        JumpInsnNode jumpInsn = BytecodeUtil.findJumpInsn(hasNext, BytecodeUtil.JumpInsnType.EQUALITY);
        if (jumpInsn == null) {
            throw new IllegalStateException("Could not find jump insn after Iterator.hasNext() call");
        }

        LabelNode startLabel = BytecodeUtil.findLabel(jumpInsn);
        if (startLabel == null) {
            throw new IllegalStateException("Could not find start label after Iterator.hasNext() call");
        }
        LabelNode endLabel = jumpInsn.label;
        LabelNode catchLabel = new LabelNode();

        InsnList catchBlock = new InsnList();
        catchBlock.add(catchLabel);
        catchBlock.add(new MethodInsnNode(Opcodes.INVOKESTATIC, BLANKET_CRASH_FIX_UTIL, IGNORE_EXCEPTION, IGNORE_EXCEPTION_DESC, false));
        catchBlock.add(new JumpInsnNode(Opcodes.GOTO, preLabel));

        method.instructions.insertBefore(endLabel, catchBlock);

        method.tryCatchBlocks.add(new TryCatchBlockNode(startLabel, endLabel, catchLabel, THROWABLE));
    }
}
