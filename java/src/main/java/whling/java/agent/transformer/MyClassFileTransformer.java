package whling.java.agent.transformer;

import com.google.common.io.Files;
import javassist.*;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

/**
 * 修改字节码文件内容
 */
public class MyClassFileTransformer implements ClassFileTransformer {
    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined,
                            ProtectionDomain protectionDomain,
                            byte[] classfileBuffer) throws IllegalClassFormatException {

        /**
         * 只针对whling/java/agent/EchoTimeTask类做字节码文件修改
         */
        if (!"whling/java/agent/EchoTimeTask".equals(className)) {
            return classfileBuffer;
        }
        try {
            CtClass ctClass = ClassPool.getDefault().makeClass(new ByteArrayInputStream(classfileBuffer));

            CtConstructor[] constructors = ctClass.getConstructors();
            for (CtConstructor constructor : constructors) {
                constructor.insertAfter("System.out.println(\"constructor......\");");
            }

            CtMethod main = ctClass.getDeclaredMethod("echoTime");
            main.insertBefore("System.out.println(\"before sayHello----\");");
            main.insertAfter("System.out.println(\"after sayHello----\");");
            byte[] bytes = ctClass.toBytecode();

            /**
             * 可以不用写入到文件
             */
            File file = new File("/tmp/EchoTimeTask.class");
            Files.write(bytes, file);
            return bytes;
        } catch (NotFoundException e) {
            e.printStackTrace();
        } catch (CannotCompileException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return classfileBuffer;
    }
}
