/*
 * Copyright (c) paper-fixes
 *
 * MIT License
 */

package net.earthcomputer.paperfixes.impl.mixinHelper;

import org.jetbrains.annotations.Nullable;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.JumpInsnNode;
import org.objectweb.asm.tree.LabelNode;
import org.objectweb.asm.tree.MethodInsnNode;

public enum BytecodeUtil {
    ;

    @Nullable
    public static MethodInsnNode findMethodInsn(@Nullable AbstractInsnNode insn, int opcode, String owner, String name, String desc) {
        for (; insn != null; insn = insn.getNext()) {
            if (insn instanceof MethodInsnNode methodInsn) {
                if (methodInsn.getOpcode() == opcode && methodInsn.owner.equals(owner) && methodInsn.name.equals(name) && methodInsn.desc.equals(desc)) {
                    return methodInsn;
                }
            }
        }
        return null;
    }

    @Nullable
    public static JumpInsnNode findJumpInsn(@Nullable AbstractInsnNode insn, JumpInsnType type) {
        for (; insn != null; insn = insn.getNext()) {
            if (insn instanceof JumpInsnNode jumpInsn) {
                if (type.accepts(insn.getOpcode())) {
                    return jumpInsn;
                }
            }
        }
        return null;
    }

    @Nullable
    public static LabelNode findLabel(@Nullable AbstractInsnNode insn) {
        for (; insn != null; insn = insn.getNext()) {
            if (insn instanceof LabelNode label) {
                return label;
            }
        }
        return null;
    }

    public enum JumpInsnType {
        UNCONDITIONAL, EQUALITY, INEQUALITY;

        boolean accepts(int opcode) {
            return switch (opcode) {
                case Opcodes.GOTO -> this == UNCONDITIONAL;
                case Opcodes.IFEQ, Opcodes.IFNE, Opcodes.IF_ICMPEQ, Opcodes.IF_ACMPNE -> this == EQUALITY;
                default -> this == INEQUALITY;
            };
        }
    }
}
