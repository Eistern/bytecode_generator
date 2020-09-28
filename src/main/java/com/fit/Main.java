package com.fit;

import java.io.FileOutputStream;
import java.io.IOException;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class Main {
  public static void main(String[] args) throws IOException {
    ClassWriter classWriter = new ClassWriter(ClassWriter.COMPUTE_FRAMES + ClassWriter.COMPUTE_MAXS);

    classWriter.visit(Opcodes.V1_8, Opcodes.ACC_PUBLIC, "Main", null, "java/lang/Object", null);

    //Default <init>
    MethodVisitor initVisitor = classWriter.visitMethod(Opcodes.ACC_PUBLIC, "<init>", "()V", null, null);
    initVisitor.visitCode();
    initVisitor.visitVarInsn(Opcodes.ALOAD, 0);
    initVisitor.visitMethodInsn(Opcodes.INVOKESPECIAL, "java/lang/Object", "<init>", "()V", false);
    initVisitor.visitInsn(Opcodes.RETURN);
    initVisitor.visitMaxs(1, 1);
    initVisitor.visitEnd();

    //main method
    MethodVisitor methodVisitor = classWriter
        .visitMethod(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, "main", "([Ljava/lang/String;)V",
            null, null);
    methodVisitor.visitCode();

    //var1 = new Random()
    methodVisitor.visitTypeInsn(Opcodes.NEW, "java/util/Random");
    methodVisitor.visitIntInsn(Opcodes.ASTORE, 1);
    methodVisitor.visitIntInsn(Opcodes.ALOAD, 1);
    methodVisitor.visitMethodInsn(Opcodes.INVOKESPECIAL, "java/util/Random", "<init>", "()V", false);

    //var2 = var1.nextInt(100)
    methodVisitor.visitIntInsn(Opcodes.ALOAD, 1);
    methodVisitor.visitIntInsn(Opcodes.BIPUSH, 100);
    methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/util/Random", "nextInt", "(I)I", false);
    methodVisitor.visitIntInsn(Opcodes.ISTORE, 2);

    //var3 = new Scanner(System.in)
    methodVisitor.visitTypeInsn(Opcodes.NEW, "java/util/Scanner");
    methodVisitor.visitIntInsn(Opcodes.ASTORE, 3);
    methodVisitor.visitIntInsn(Opcodes.ALOAD, 3);
    methodVisitor.visitFieldInsn(Opcodes.GETSTATIC, "java/lang/System", "in", "Ljava/io/InputStream;");
    methodVisitor.visitMethodInsn(Opcodes.INVOKESPECIAL, "java/util/Scanner", "<init>", "(Ljava/io/InputStream;)V", false);

    //var4 = var2 + 1
    methodVisitor.visitIntInsn(Opcodes.ILOAD, 2);
    methodVisitor.visitIntInsn(Opcodes.ISTORE, 4);
    methodVisitor.visitIincInsn(4, 1);

    methodVisitor.visitFieldInsn(Opcodes.GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
    methodVisitor.visitLdcInsn("Guess from 0 to 100");
    methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false);

    Label whileStart = new Label();
    Label whileFinish = new Label();

    //while (var4 != var 2) { (while loop start)
    methodVisitor.visitLabel(whileStart);
    methodVisitor.visitIntInsn(Opcodes.ILOAD, 4);
    methodVisitor.visitIntInsn(Opcodes.ILOAD, 2);
    methodVisitor.visitJumpInsn(Opcodes.IF_ICMPEQ, whileFinish);

    //var4 = var3.nextInt()
    methodVisitor.visitIntInsn(Opcodes.ALOAD, 3);
    methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/util/Scanner", "nextInt", "()I", false);
    methodVisitor.visitIntInsn(Opcodes.ISTORE, 4);

    Label secondCheck = new Label();

    //if (var4 < var2) {sout("Lower");}
    methodVisitor.visitIntInsn(Opcodes.ILOAD, 4);
    methodVisitor.visitIntInsn(Opcodes.ILOAD, 2);
    methodVisitor.visitJumpInsn(Opcodes.IF_ICMPGE, secondCheck);
    methodVisitor.visitFieldInsn(Opcodes.GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
    methodVisitor.visitLdcInsn("Greater");
    methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false);

    //if (var4 > var2) {sout("Greater");}
    methodVisitor.visitLabel(secondCheck);
    methodVisitor.visitIntInsn(Opcodes.ILOAD, 4);
    methodVisitor.visitIntInsn(Opcodes.ILOAD, 2);
    methodVisitor.visitJumpInsn(Opcodes.IF_ICMPLE, whileStart);
    methodVisitor.visitFieldInsn(Opcodes.GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
    methodVisitor.visitLdcInsn("Lower");
    methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false);

    // } (while loop end)
    methodVisitor.visitJumpInsn(Opcodes.GOTO, whileStart);
    methodVisitor.visitLabel(whileFinish);

    methodVisitor.visitFieldInsn(Opcodes.GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
    methodVisitor.visitLdcInsn("Exactly");
    methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false);

    methodVisitor.visitInsn(Opcodes.RETURN);
    methodVisitor.visitMaxs(10, 10);
    methodVisitor.visitEnd();

    classWriter.visitEnd();

    FileOutputStream outputStream = new FileOutputStream("Main.class");
    outputStream.write(classWriter.toByteArray());
    outputStream.close();
  }
}
