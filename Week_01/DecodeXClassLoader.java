import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.IntStream;

/**
 * 自定义classloader 加载xlass文件
 *
 * @author henengqiang
 * @date 2020/10/19
 */
public class DecodeXClassLoader extends ClassLoader {

    public static void main(String[] args) {
        String className = "Hello";
        String suffix = "xlass";
        String methodName = "hello";
        try {
            Class<?> clazz = new DecodeXClassLoader().findClass(className, suffix);
            Object obj = clazz.newInstance();
            Method method = clazz.getMethod(methodName);
            method.invoke(obj);
        } catch (IOException
                | InstantiationException
                | IllegalAccessException
                | NoSuchMethodException
                | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    private Class<?> findClass(String name, String suffix) throws IOException {
        byte[] bytes = Files.readAllBytes(Paths.get(".\\Week_01\\" + name + "." + suffix));
        byte[] helloBytes = new byte[bytes.length];
        IntStream.range(0, bytes.length).forEach(i -> helloBytes[i] = (byte) (255 - bytes[i]));
        return super.defineClass(name, helloBytes, 0, helloBytes.length);
    }

}