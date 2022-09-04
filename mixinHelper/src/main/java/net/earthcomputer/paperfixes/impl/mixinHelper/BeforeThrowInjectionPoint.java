/*
 * Copyright (c) paper-fixes
 *
 * MIT License
 */

package net.earthcomputer.paperfixes.impl.mixinHelper;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.InsnList;
import org.spongepowered.asm.mixin.injection.InjectionPoint;
import org.spongepowered.asm.mixin.injection.struct.InjectionPointData;

import java.util.Collection;

@InjectionPoint.AtCode(namespace = "paperfixes", value = "throw")
public class BeforeThrowInjectionPoint extends InjectionPoint {
    public BeforeThrowInjectionPoint(InjectionPointData data) {
        super(data);
    }

    @Override
    public boolean find(String desc, InsnList insns, Collection<AbstractInsnNode> nodes) {
        boolean found = false;
        for (AbstractInsnNode node = insns.getFirst(); node != null; node = node.getNext()) {
            if (node.getOpcode() == Opcodes.ATHROW) {
                found = true;
                nodes.add(node);
            }
        }
        return found;
    }
}
