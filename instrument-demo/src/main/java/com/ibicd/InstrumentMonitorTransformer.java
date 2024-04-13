package com.ibicd;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtBehavior;
import javassist.CtClass;
import javassist.expr.ExprEditor;
import javassist.expr.MethodCall;

import java.lang.instrument.ClassFileTransformer;
import java.security.ProtectionDomain;
import java.util.HashSet;
import java.util.Set;

public class InstrumentMonitorTransformer implements ClassFileTransformer {
    private static final Set<String> classNames = new HashSet<String>();

    static {
        classNames.add("com.ibicd.HelloService");
    }

    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined,
                            ProtectionDomain protectionDomain, byte[] classfileBuffer) throws RuntimeException {
        String replacedClassName = className.replaceAll("/", ".");
        if (!classNames.contains(replacedClassName)){
            return null;
        }
        try {
            CtClass ctClass = ClassPool.getDefault().get(replacedClassName);
            CtBehavior[] declaredBehaviors = ctClass.getDeclaredBehaviors();
            for (CtBehavior declaredBehavior : declaredBehaviors) {
                //增强方法
                enhanceMethod(declaredBehavior);
            }
            return ctClass.toBytecode();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void enhanceMethod(CtBehavior method) throws ClassNotFoundException, CannotCompileException {
        if (method.isEmpty()) {
            return;
        }
        String methodName = method.getName();
        // 不增强main方法
        if (methodName.equalsIgnoreCase("main")) {
            return;
        }
        // 增强@Fox修饰的方法
        if(null == method.getAnnotation(Julie.class)){
            return;
        }

        final StringBuilder source = new StringBuilder();
        source.append("{")
                // 前置增强: 加入时间戳
                .append("long start = System.currentTimeMillis();\n")
                // 保留原有的代码处理逻辑
                .append("$_ = $proceed($$);\n")
                .append("System.out.print(\"method:[" + methodName + "]\");")
                .append("\n")
                // 后置增强
                .append("System.out.println(\" cost:[\" +(System.currentTimeMillis() -start)+ \"ms]\");")
                .append("}");

        ExprEditor editor = new ExprEditor() {
            @Override
            public void edit(MethodCall methodCall) throws CannotCompileException {
                methodCall.replace(source.toString());
            }
        };
        method.instrument(editor);

    }
}
