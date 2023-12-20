package model.reflect;

import model.range.DataSet;
import model.range.Range;

import java.lang.reflect.*;
import java.util.Arrays;
import java.util.Collection;

/**
 * The reflection method is encapsulated to provide support for getting the relevant
 * parameters of the method at runtime
 * The core method is the call method, which calls a user-given method via reflection
 */
public class ReflectiveInvoker {
    /**
     * @param clazz      class object
     * @param methodName method name
     * @return Returns a reference to a method named methodName in clazz
     */
    public static Method getMethod(Class<?> clazz, String methodName) {
        Method[] methods = clazz.getDeclaredMethods();
        Method method = null;

        for (Method m : methods) {
            if (m.getName().equals(methodName)) {
                method = m;
                break;
            }
        }

        if (method == null) {
            throw new IllegalArgumentException("method name or clazz error,the name of the method could not be found in clazz class object");

        }

        return method;
    }

    public static Object getInstance(String classObjectStr, DataSet set, Range range) throws ClassNotFoundException {
        Class<?> clazz = Class.forName(classObjectStr);
        Object ret;
        Constructor<?> constructor;

        try {
            if (range == null) {
                constructor = clazz.getDeclaredConstructor();
            } else if (set == null) {
                constructor = clazz.getDeclaredConstructor(Range.class);
            } else {
                constructor = clazz.getDeclaredConstructor(DataSet.class, Range.class);
            }
        } catch (NoSuchMethodException e) {
            throw new RuntimeException("Failed to construct an instance:" + clazz.getName() + "there is no Range construct for this class!!");
        }

        constructor.setAccessible(true);

        try {
            if (range == null) {
                ret = constructor.newInstance();
            } else if (set == null) {
                ret = constructor.newInstance(range);
            } else {
                ret = constructor.newInstance(set, range);
            }
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            System.out.println(classObjectStr + " " + Arrays.toString(set.getSet()) + range);
            throw new RuntimeException("The call to the suitable constructor failed, and the class is :" + clazz.getName() + ",please check!");
        }
        return ret;
    }

    /**
     * @param clazz      class object
     * @param methodName method name
     * @param i          the ith parameter
     * @return return the full class name string of the method parameter
     */
    public static String getTypeStr(Class<?> clazz, String methodName, int i) {
        Method method = getMethod(clazz, methodName);
        Type[] types = method.getGenericParameterTypes();
        return types[i].getTypeName();
    }

    /**
     * @param clazz      class object
     * @param methodName method name
     * @param i          the ith parameter
     * @return returns a class object of parameter type
     */
    public static Class<?> getClassObj(Class<?> clazz, String methodName, int i) {
        Method method = getMethod(clazz, methodName);
        Class<?>[] argumentsClassObject = method.getParameterTypes();
        return argumentsClassObject[i];
    }

    /**
     * @param clazz     the class object of the class in which the method is located
     * @param name      method name
     * @param arguments method parameters
     * @return result of set
     */
    public static Object call(Class<?> clazz, String name, Object[] arguments) {
        Method method = getMethod(clazz, name);

        try {
            Constructor<?> constructor = clazz.getDeclaredConstructor();

            constructor.setAccessible(true);

            Object o = constructor.newInstance();

            return method.invoke(o, arguments);
        } catch (NoSuchMethodException | InvocationTargetException | InstantiationException |
                 IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}