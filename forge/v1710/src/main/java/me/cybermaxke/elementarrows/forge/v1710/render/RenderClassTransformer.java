/**
 * This file is part of ElementalArrows.
 * 
 * Copyright (c) 2014, Cybermaxke
 * 
 * ElementalArrows is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * ElementalArrows is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with ElementalArrows. If not, see <http://www.gnu.org/licenses/>.
 */
package me.cybermaxke.elementarrows.forge.v1710.render;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import net.minecraft.launchwrapper.IClassTransformer;
import static me.cybermaxke.elementarrows.forge.v1710.FLoadingPlugin.Obfuscated;

/**
 * A transformer for the entity render class.
 */
public class RenderClassTransformer implements IClassTransformer {
	/**
	 * Classes
	 */
	private static final String deobfuscatedEntity = "net/minecraft/entity/Entity";
	private static final String deobfuscatedEntityLivingBase = "net/minecraft/entity/EntityLivingBase";
	private static final String deobfuscatedModelBase = "net/minecraft/client/model/ModelBase";
	private static final String deobfuscatedRendererLivingEntity = "net/minecraft/client/renderer/entity/RendererLivingEntity";
	private static final String deobfuscatedRender = "net/minecraft/client/renderer/entity/Render";

	private static final String obfuscatedEntity = "sa";
	private static final String obfuscatedEntityLivingBase = "sv";
	private static final String obfuscatedModelBase = "bhr";
	private static final String obfuscatedRendererLivingEntity = "boh";
	private static final String obfuscatedRender = "bno";

	/**
	 * Methods
	 */
	private static final String deobfuscatedDoRender = "doRender";
	private static final String deobfuscatedRenderModel = "renderModel";
	private static final String deobfuscatedRenderEquippedItems = "renderEquippedItems";

	private static final String obfuscatedDoRender = "a";
	private static final String obfuscatedRenderModel = "a";
	private static final String obfuscatedRenderEquippedItems = "c";

	/**
	 * Fields
	 */
	private static final String deobfuscatedMainModel = "mainModel";

	private static final String obfuscatedMainModel = "i";

	// Classes
	String cEntityLivingBase = Obfuscated ? obfuscatedEntityLivingBase : deobfuscatedEntityLivingBase;
	String cRendererLivingEntity = Obfuscated ? obfuscatedRendererLivingEntity : deobfuscatedRendererLivingEntity;
	String cRender = Obfuscated ? obfuscatedRender : deobfuscatedRender;
	String cEntity = Obfuscated ? obfuscatedEntity : deobfuscatedEntity;
	String cModelBase = Obfuscated ? obfuscatedModelBase : deobfuscatedModelBase;

	// Methods
	String mDoRender = Obfuscated ? obfuscatedDoRender : deobfuscatedDoRender;
	String mRenderModel = Obfuscated ? obfuscatedRenderModel : deobfuscatedRenderModel;
	String mRenderEquippedItems = Obfuscated ? obfuscatedRenderEquippedItems : deobfuscatedRenderEquippedItems;

	// Fields
	String fMainModel = Obfuscated ? obfuscatedMainModel : deobfuscatedMainModel;

	@Override
	public byte[] transform(String name, String transformedName, byte[] basicClass) {
		String cRendererLivingEntity = Obfuscated ? obfuscatedRendererLivingEntity : deobfuscatedRendererLivingEntity;
		String name0 = name.replace('.', '/');

		boolean flag;

		// Only transform the RendererLivingEntity class or classes that extend it
		if (name0.equals(cRendererLivingEntity)) {
			flag = true;
		} else if (getSuperClasses(name0).contains(cRendererLivingEntity)) {
			flag = false;
		} else {
			return basicClass;
		}

		// Read the class bytecode from the byte array
		ClassReader reader = new ClassReader(basicClass);
		ClassWriter writer = new ClassWriter(0);

		if (flag) {
			reader.accept(new RenderClassVisitor2(writer), 0);
		} else {
			reader.accept(new RenderClassVisitor(writer), 0);
		}

		return writer.toByteArray();
	}

	class RenderClassVisitor extends ClassVisitor {

		public RenderClassVisitor(ClassVisitor cv) {
			super(Opcodes.ASM4, cv);
		}

		@Override
	    public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
			if (name.equals(mRenderEquippedItems) && desc.equals("(L" + cEntityLivingBase + ";F)V")) {
				return new RenderMethodVisitorCall(this.cv.visitMethod(access, name, desc, signature, exceptions));
			} else {
				return super.visitMethod(access, name, desc, signature, exceptions);
			}
		}

		class RenderMethodVisitorCall extends MethodVisitor {

			public RenderMethodVisitorCall(MethodVisitor mv) {
				super(Opcodes.ASM4, mv);
			}

			@Override
		    public void visitInsn(int opcode) {
				if (opcode == Opcodes.RETURN){
					this.visitCall();
		        }
				super.visitInsn(opcode);
			}

			public void visitCall() {
				super.visitVarInsn(Opcodes.ALOAD, 0);
				super.visitVarInsn(Opcodes.ALOAD, 1);
				super.visitVarInsn(Opcodes.FLOAD, 2);
				super.visitMethodInsn(Opcodes.INVOKESTATIC, "me/cybermaxke/elementarrows/forge/v1710/render/RenderCallbackManagerBase", "call2", "(L" + cRender + ";L" + cEntity + ";F)V", false);
			}

		}

	}

	class RenderClassVisitor2 extends RenderClassVisitor {

		public RenderClassVisitor2(ClassVisitor cv) {
			super(cv);
		}

		@Override
	    public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
			if (name.equals(mDoRender) && desc.equals("(L" + cEntityLivingBase + ";DDDFF)V")) {
				return new RenderMethodVisitorCall1(this.cv.visitMethod(access, name, desc, signature, exceptions));
			} else if (name.equals(mRenderModel) && desc.equals("(L" + cEntityLivingBase + ";FFFFFF)V")) {
				return new RenderMethodVisitorCall2(this.cv.visitMethod(access, name, desc, signature, exceptions));
			} else {
				return super.visitMethod(access, name, desc, signature, exceptions);
			}
		}

		class RenderMethodVisitorCall1 extends MethodVisitor {
			int i;

			public RenderMethodVisitorCall1(MethodVisitor mv) {
				super(Opcodes.ASM4, mv);
			}

			@Override
		    public void visitInsn(int opcode) {
				if (opcode == Opcodes.RETURN && this.i++ >= 1){
					this.visitCall1();
		        }
				super.visitInsn(opcode);
			}

			public void visitCall1() {
				super.visitVarInsn(Opcodes.ALOAD, 0);
				super.visitVarInsn(Opcodes.ALOAD, 1);
				super.visitVarInsn(Opcodes.DLOAD, 2);
				super.visitVarInsn(Opcodes.DLOAD, 4);
				super.visitVarInsn(Opcodes.DLOAD, 6);
				super.visitVarInsn(Opcodes.FLOAD, 8);
				super.visitVarInsn(Opcodes.FLOAD, 9);
				super.visitMethodInsn(Opcodes.INVOKESTATIC, "me/cybermaxke/elementarrows/forge/v1710/render/RenderCallbackManagerBase", "call0", "(L" + cRender + ";L" + cEntity + ";DDDFF)V", false);
			}

		}

		class RenderMethodVisitorCall2 extends MethodVisitor {

			public RenderMethodVisitorCall2(MethodVisitor mv) {
				super(Opcodes.ASM4, mv);
			}

			@Override
		    public void visitInsn(int opcode) {
				if (opcode == Opcodes.RETURN){
					this.visitCall2();
		        }
				super.visitInsn(opcode);
			}

			public void visitCall2() {
				super.visitVarInsn(Opcodes.ALOAD, 0);
				super.visitVarInsn(Opcodes.ALOAD, 1);
				super.visitVarInsn(Opcodes.ALOAD, 0);
				super.visitFieldInsn(Opcodes.GETFIELD, cRendererLivingEntity, fMainModel, "L" + cModelBase + ";");
				super.visitVarInsn(Opcodes.FLOAD, 2);
				super.visitVarInsn(Opcodes.FLOAD, 3);
				super.visitVarInsn(Opcodes.FLOAD, 4);
				super.visitVarInsn(Opcodes.FLOAD, 5);
				super.visitVarInsn(Opcodes.FLOAD, 6);
				super.visitVarInsn(Opcodes.FLOAD, 7);
				super.visitMethodInsn(Opcodes.INVOKESTATIC, "me/cybermaxke/elementarrows/forge/v1710/render/RenderCallbackManagerBase", "call1", "(L" + cRender + ";L" + cEntity + ";L" + cModelBase + ";FFFFFF)V", false);
			}

		}

	}

	static List<String> getSuperClasses(String className) {
		List<String> list = new ArrayList<String>();

		try {
			while (true) {
				if (className == null) {
					break;
				}

				ClassReader reader = new ClassReader(className);
				className = reader.getSuperName();

				if (className == null || className.equals("java/lang/Object")) {
					break;
				}

				list.add(className);
			}
		} catch (IOException e) {
			return list;
		}

		return list;
	}

}