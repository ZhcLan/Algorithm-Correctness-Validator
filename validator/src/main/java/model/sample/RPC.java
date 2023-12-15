package model.sample;

import model.config.ValidatorConfig;
import model.range.Fate;
import model.range.Range;
import model.reflect.ReflectiveInvoker;
import model.util.type.ObjectPlus;
import model.util.type.StringPlus;
import model.util.GlobalUtility;

import java.lang.reflect.*;
import java.util.*;

/**
 * RPC: random parameter constructor
 * Based on the parameter template(courtesy of Reflection),
 * random test parameters are generated
 */
public class RPC {

    /**
     * RPC Entry Method -> Construct a quasi-script environment for RSC
     *
     * @param config Abstraction of test arguments
     * @param volume Amount of data
     * @param values Numeric range
     * @param error  errorString constructor
     * @return random sample(Imperfect)
     * @throws ClassNotFoundException If the reflection method does not exist or cannot be found
     */
    public static Object[][] rpc(ValidatorConfig config, Range[][] volume, Range[][] values, StringBuilder error) throws ClassNotFoundException {
        // obtains method
        Method method = ReflectiveInvoker.getMethod(config.getClazz(), config.getValidatorMethod());
        // obtains generic parameter types
        java.lang.reflect.Type[] types = method.getGenericParameterTypes();
        Object[][] args = new Object[types.length][];
        for (int i = 0; i < types.length; i++) {
            ensureArgument(types[i]);
            error.append("{");
            // recursively parse and construct random samples

            args[i] = rsc(types[i], volume, values, i, 0, error, null);
            error.append("}");
            if (i != types.length - 1) {
                error.append(",");
            }
        }
        args = processArgument(args);
        return args;
    }

    /**
     * Since one parameter and one copy of it is constructed at a time, it is processed at the end
     * Processed into two sets of input parameters
     *
     * @param args pending parameters
     * @return Parameters that have been processed
     */
    private static Object[][] processArgument(Object[][] args) {
        Object[] p1 = new Object[args.length];
        Object[] p2 = new Object[args.length];
        for (int i = 0; i < args.length; i++) {
            p1[i] = args[i][0];
            p2[i] = args[i][1];
        }
        Object[][] ret = new Object[2][];
        ret[0] = p1;
        ret[1] = p2;
        return ret;
    }

    /**
     * First of all, the recursive idea of this method is introduced:
     * One parameter is processed at a time, and for each parameter,
     * it is treated as a collection
     * <p>
     * Collections are divided into single-value collections and
     * double-value key-value pairs, collections (please do not use
     * interfaces in parameters, because that will cause parsing failures)
     * <p>
     * Only need to adapt these two interfaces.
     * The conditions for recursion are getActualTypeArguments or getComponentType
     * <p>
     * But!
     * <p>
     * Since arrays are a very special type of data in the JVM, we need to expand them separately
     * <p>
     * <p>
     * If you give a hash table with a range of keys that don't randomly come up
     * with a corresponding number of keys, an endless loop will occur
     * Do your best to deal with key duplication internally
     * <p>
     * The core method of the whole program, but also the most complex
     * module in the development process
     * <p>
     * During development, the parsing of parameters, from fixed mapping parsing
     * to full parsing using recursion, was a huge pain point
     * <p>
     * Due to the unfamiliarity with the Reflection API, I spent a lot of time
     * in development to get rid of Reflection and consult the relevant documentation,
     * including reading some of the source code of Reflection
     * <p>
     *
     * @return It means that the two test parameters (value & uuid want to vomit)
     * and errorString together form a set of random test samples
     */
    /* unchecked */
    public static Object[] rsc(Type root, Range[][] volume, Range[][] values, int index, int used,
                               StringBuilder error/*指定特殊值的字段*/, Range fate)
            throws ClassNotFoundException {
        Object[] ret = new Object[2];
        try {
            // Map or Collection
            // create root
            ret[0] = ReflectiveInvoker.getInstance(((ParameterizedType) root).getRawType().getTypeName(), null);
            ret[1] = ReflectiveInvoker.getInstance(((ParameterizedType) root).getRawType().getTypeName(), null);
        } catch (ClassCastException e) {
            if (root instanceof GenericArrayType) {
                // Generic Array
                int size = (int) GlobalUtility.getRandomMinToMax(GlobalUtility.arguments[index].getVolume()[used]);
                Object array1;
                Object array2;

                // Manually construct the GenericArrayType signature
                String forNameStr = getArraySignatureByType(root);
                Class<?> componentType = Class.forName(forNameStr).getComponentType();
                array1 = Array.newInstance(componentType, size);
                array2 = Array.newInstance(componentType, size);
                error.append("[");
                for (int i = 0; i < size; i++) {
                    Object[] subArray = rsc(((GenericArrayType) root).getGenericComponentType(), volume, values, index, used + 1, error, null);
                    if (i != size - 1) {
                        error.append(",");
                    }
                    Array.set(array1, i, subArray[0]);
                    Array.set(array2, i, subArray[1]);
                }
                error.append("]");
                try {
                    if (GlobalUtility.arguments[index].isOrder()) {
                        Arrays.sort((Object[]) array1);
                        Arrays.sort((Object[]) array2);
                    }
                } catch (Exception eSort) {
                    System.out.println("sort fail,please don't sort complex parameters");
                }
                ret[0] = array1;
                ret[1] = array2;
            } else if (root instanceof Class && ((Class<?>) root).getComponentType() != null) {
                // common array
                int size = (int) GlobalUtility.getRandomMinToMax(GlobalUtility.arguments[index].getVolume()[used]);
                Object array1;
                Object array2;
                try {
                    array1 = Array.newInstance(((Class<?>) root).getComponentType(), size);
                    array2 = Array.newInstance(((Class<?>) root).getComponentType(), size);
                    error.append("[");
                    for (int i = 0; i < size; i++) {
                        Object[] subArray = rsc(((Class<?>) root).getComponentType(), volume, values, index, used + 1, error, null);
                        if (i != size - 1) {
                            error.append(",");
                        }
                        Array.set(array1, i, subArray[0]);
                        Array.set(array2, i, subArray[1]);
                    }
                    error.append("]");
                } catch (NullPointerException eNull) {
                    array1 = Array.newInstance(((Class<?>) root).getComponentType(), size);
                    array2 = Array.newInstance(((Class<?>) root).getComponentType(), size);

                    assert error != null;
                    error.append("[");
                    for (int i = 0; i < size; i++) {
                        Object[] subArray = rsc(((Class<?>) root).getComponentType(), volume, values, index, used + 1, error, null);
                        if (i != size - 1) {
                            error.append(",");
                        }
                        Array.set(array1, i, subArray[0]);
                        Array.set(array2, i, subArray[1]);
                    }
                    error.append("[");
                }
                try {
                    if (GlobalUtility.arguments[index].isOrder()) {
                        Arrays.sort((Object[]) array1);
                        Arrays.sort((Object[]) array2);
                    }

                } catch (Exception eSort) {
                    System.out.println("sort fail,please don't sort complex parameters");
                }

                ret[0] = array1;
                ret[1] = array2;
            } else {
                // leaf -> Plus Type
                // special process if types is string plus type
                Range random = values[index][used];
                if (root.getTypeName().contains("String")) {
                    int len = (int) GlobalUtility.getRandomMinToMax(volume[index][used]);
                    ret[0] = new StringPlus(random, len);
                    ret[1] = StringPlus.clone((StringPlus) (ret[0]));
                } else {
                    if (fate != null) {
                        ret[0] = ReflectiveInvoker.getInstance(root.getTypeName(), fate);
                        error.append(ret[0] + "");
                        // Restore the site
                        fate = null;
                    } else {
                        ret[0] = ReflectiveInvoker.getInstance(root.getTypeName(), random);
                        error.append(ret[0] + "");
                    }
                    ret[1] = ObjectPlus.clone(root.getTypeName(), ret[0]);
                }
            }
        }
        java.lang.reflect.Type[] types;
        try {
            types = ((ParameterizedType) root).getActualTypeArguments();
        } catch (ClassCastException e) {
            // Recursive exports
            return ret;
        }

        if (types.length == 2) {// Interface Map
            Map<Object, Object> map1 = (Map<Object, Object>) ret[0];
            Map<Object, Object> map2 = (Map<Object, Object>) ret[1];

            Type keyType = types[0];
            Type valType = types[1];

            int count = 0;
            int size = (int) GlobalUtility.getRandomMinToMax(volume[index][used]);
            error.append("[");
            while (count < size) {
                Object[] rightKey;
                /*
                 * Note!
                 * if the hash table already contains this key, try to find a new key if possible
                 */
                error.append("(");
                do {
                    rightKey = rsc(keyType, volume, values, index, used + 1, error, null);
                } while (map1.containsKey(rightKey[0]));
                error.append(",");

                Object[] rightVal = rsc(valType, volume, values, index, used + 2, error, null);

                error.append(")");
                map1.put(rightKey[0], rightVal[0]);

                map2.put(rightKey[1], rightVal[1]);
                count++;
            }
            error.append("]");
        } else if (types.length == 1) {// Interface Collection
            Collection<Object> col1 = (Collection<Object>) ret[0];
            Collection<Object> col2 = (Collection<Object>) ret[1];
            Type colType = types[0];
            ArrayList<Object> list1 = new ArrayList<>();
            ArrayList<Object> list2 = new ArrayList<>();
            int count = 0;
            int size = (int) GlobalUtility.getRandomMinToMax(volume[index][used]);
            Fate fateful = GlobalUtility.arguments[index].getVolume()[used].getFate();
            if (fateful != null) {
                int total = 0;
                int[] times = new int[fateful.getTimes().length];
                for (int i = 0; i < fateful.getTimes().length; i++) {
                    times[i] = (int) GlobalUtility.getRandomMinToMax(fateful.getTimes()[i]);
                    total += times[i];
                }
                // 1. The number of special values is total and fill in the value
                count = 0;
                // Gets a special value of fate
                for (int i = 0; i < times.length; i++) {
                    for (int j = 0; j < times[i]; j++) {
                        Range range = fateful.getValue()[i];
                        // Returns two leaf nodes
                        Object[] objects = rsc(colType, volume, values, index, used + 1, error, range);
                        error.append(",");
                        list1.add(objects[0]);
                        list2.add(objects[1]);
                        count++;
                    }
                }
                fate = null;
                //  2. The remaining count - total number is filled in with random values
                while (count < size) {
                    // Assign a value to fate first
                    Object[] objects = rsc(colType, volume, values, index, used + 1, error, null);
                    if (count != size - 1) {
                        error.append(",");
                    }
                    list1.add(objects[0]);
                    list2.add(objects[1]);
                    count++;
                }
            } else {
                while (count < size) {
                    Object[] objects = rsc(colType, volume, values, index, used + 1, error, null);
                    if (count != size - 1) {
                        error.append(",");
                    }
                    list1.add(objects[0]);
                    list2.add(objects[1]);
                    count++;
                }
            }
            // If the parameters are ordered, sort them
            if (GlobalUtility.arguments[index].isOrder()) {
                list1.sort((o1, o2) -> ((ObjectPlus<?, ?>) o1).compareTo(o2));
                list2.sort((o1, o2) -> ((ObjectPlus<?, ?>) o1).compareTo(o2));
            }
            col1.addAll(list1);
            col2.addAll(list2);
        }
        return ret;
    }

    /**
     * Type
     * java.util.ArrayList<model.type.IntegerPlus[]>[][]
     * <p>
     * signature
     * [[Ljava.util.ArrayList;
     * <p>
     * Handle this kind of string
     * <p>
     * 1. Scan from front to back to get all class names
     * <p>
     * 2. The number of [] obtained from the back-forward
     * <p>
     * scan is the number of signatures L, which is the
     * number of dimensions of the array
     */
    private static String getArraySignatureByType(Type root) {
        StringBuilder s = new StringBuilder();
        String fullClassName = root.getTypeName();
        String dimensions = root.getTypeName();
        for (int i = 0; i < fullClassName.length(); i++) {
            if ('<' != fullClassName.charAt(i)) {
                s.append(fullClassName.charAt(i));
            } else {
                break;
            }
        }
        int count = 0;
        for (int i = dimensions.length() - 1; i >= 0; i -= 2) {
            if (']' == dimensions.charAt(i) && '[' == dimensions.charAt(i - 1)) {
                count++;
            } else {
                break;
            }
        }
        StringBuilder ret = new StringBuilder();
        for (int i = 0; i < count; i++) {
            ret.append("[");
        }

        ret.append("L");

        ret.append(s);// full class name

        ret.append(";");

        return ret.toString();
    }

    /**
     * recursively check whether the parameters meet the parsing criteria,
     * if not, an exception is thrown;
     *
     * @param type Type of the parameter
     */
    public static void ensureArgument(Type type) {
        if (type instanceof GenericArrayType) {
            java.lang.reflect.Type componentType = ((GenericArrayType) type).getGenericComponentType();
            if (componentType instanceof TypeVariable) {
                throw new IllegalArgumentException("Generic variables are contained in the parameter generic array" + componentType + ",Unable to complete parsing!");
            }
        } else if (type instanceof ParameterizedType) {
            // Determine whether the generic parameter type is used: that is, the type with angle brackets: class name<var1,var2...>
            java.lang.reflect.Type[] TypeList = ((ParameterizedType) type).getActualTypeArguments();
            for (int i = 0; i < TypeList.length; i++) {
                if (TypeList[i] instanceof TypeVariable) {
                    throw new IllegalArgumentException((i + 1) + "th parameter types are generic-type variables" + TypeList[i] + "，Unable to complete parsing!");
                } else if (TypeList[i] instanceof WildcardType) {
                    // WildcardType
                    throw new IllegalArgumentException((i + 1) + "th parameter types are generic wildcard expressions" + TypeList[i] + "，Unable to complete parsing!");
                } else if (TypeList[i] instanceof Class) {
                    return;
                } else if (TypeList[i] instanceof ParameterizedType) {
                    ensureArgument(TypeList[i]);
                }
            }
        } else if (type instanceof TypeVariable) {
            // TypeVariable
            throw new IllegalArgumentException("There are type variables in the parameter type(TypeVariable):" + type + "，Unable to complete parsing!");
        } else if (type instanceof WildcardType) {
            // WildcardType
            throw new IllegalArgumentException("There is a wildcard expression in the parameter type:" + type + ",Unable to complete parsing!");
        }
    }

    /**
     * Deprecated!!!!!!
     */
    public static Type getArrayRootType(Type type) {
        Type ret = null;
        while (type != null) {
            type = ((Class<?>) type).getComponentType();
            if (type != null) {
                ret = type;
            }
        }
        return ret;
    }

    /**
     * Returns two sets of n-dimensional arrays (common arrays) created
     * dimension[0] * dimension[1] * ... ... * dimension[n - 1] An array of dimensions!
     * Deprecated!!!!!!
     */
    public static Object[] initArray(Class<?> componentRootClazz, int[] dimensions, String typeStr, StringBuilder error)
            throws InstantiationException, IllegalAccessException {
        if (dimensions.length == 1) {
            error.append("[");
            Object leafArray1 = Array.newInstance(componentRootClazz.getComponentType(), dimensions[0]);
            Object leafArray2 = Array.newInstance(componentRootClazz.getComponentType(), dimensions[0]);
            for (int i = 0; i < Array.getLength(leafArray1); i++) {
                Object o1 = componentRootClazz.getComponentType().newInstance();
                error.append(o1);
                Object o2 = ObjectPlus.clone(typeStr, o1);
                Array.set(leafArray1, i, o1);
                Array.set(leafArray2, i, o2);
                if (i != Array.getLength(leafArray1)) {
                    error.append(",");
                }
            }
            error.append("]");
            return new Object[]{leafArray1, leafArray2};
        } else {
            error.append("L[");
            Object array1 = Array.newInstance(componentRootClazz.getComponentType(), dimensions[0]);
            Object array2 = Array.newInstance(componentRootClazz.getComponentType(), dimensions[0]);
            for (int i = 0; i < dimensions[0]; i++) {
                Object[] subArray = initArray(componentRootClazz.getComponentType(), Arrays.copyOfRange(dimensions, 1, dimensions.length), typeStr, error);
                error.append("]");
                Array.set(array1, i, subArray[0]);
                Array.set(array2, i, subArray[1]);
            }
            return new Object[]{array1, array2};
        }
    }
}