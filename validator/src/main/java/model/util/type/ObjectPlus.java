package model.util.type;

import model.range.Range;
import model.reflect.ReflectiveInvoker;

import java.util.Comparator;
import java.util.Objects;
import java.util.UUID;


// uncheck!

/**
 * The base type is encapsulated to ensure that the input of both
 * <p>
 * sets of data is the same every time it happens
 *
 * @param <E>
 * @param <T>
 */
public class ObjectPlus<E extends Comparable, T extends ObjectPlus<?, ?>> implements Comparable, Comparator {
    /**
     * A unique identifier that is unique within the same set of parameters
     * <p>
     * However, it occurs twice in a set of samples
     * <p>
     * Used to identify if two sets of inputs in a sample are identical
     */
    private String uuid;

    /**
     * A fully qualified class name string of this type
     */
    public static String typeStr = ObjectPlus.class.getTypeName();

    /**
     * Store the truth value of the element, make it easy to use, and make it public
     * <p>
     * Here E is a type variable can only be
     * <p>
     * byte     short       int         long
     * float    double      boolean     char
     * Byte     Short       Integer     Long
     * Float    Double      Boolean     Character
     * String
     * <p>
     * Other types are not adapted, and if used, there will be undefined behavior
     */
    public E value;

    /**
     * The range of values that correspond to the type
     */
    public static Range TYPE_PLUS_RANGE;

    /**
     * Please do not attempt to rewrite and modify the UUID
     */
    public ObjectPlus() {
        this.uuid = String.valueOf(UUID.randomUUID());
    }

    public ObjectPlus(E value) {
        // set uuid
        this();
        this.value = value;
    }

    /**
     * Make sure subclasses override this construct method
     * <p>
     * This construct is the basis for recursively creating
     * <p>
     * samples by reflection, without which the samples
     * <p>
     * would not have been successfully created
     *
     * @param range range of maybe value
     */
    public ObjectPlus(Range range) {
        // ... ...
    }

    /**
     * A copy of the recursively generated leaves (of a Plus type)
     * <p>
     * ensures that the recursion creates two sets of 'identical' inputs
     *
     * @param typeStr full class name string
     * @param obj     the instance that was cloned
     * @return clone instance
     */
    public static ObjectPlus<?, ?> clone(String typeStr, Object obj) {
        ObjectPlus ret = null;
        try {
            ObjectPlus<?, ?> o = (ObjectPlus<?, ?>) obj;
            ret = (ObjectPlus<?, ?>) ReflectiveInvoker.getInstance(typeStr, null);
            ret.uuid = o.uuid;
            ret.value = o.value;
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("clone error!");
        }

        return ret;
    }

    //     uuid1.compareToUuid(uuid2);
    public boolean compareToUuid(String uuid) {
        return uuid.equals(this.uuid);
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    @Override
    public String toString() {
        return value + "";
    }


    /**
     * 2023.12.12
     * <p>
     * Please make sure that the incoming o1 and o2 are all Plus types, because the
     * comparison results of non-Plus types without ID are inaccurate, so there is
     * no internal processing of the basic type, and you need to judge when calling
     * <p>
     * If the result set is of the Single-Value Plus type, then just pass it in in
     * its entirety!
     * <p>
     * comparison: value & uuid
     * 1.   v1 != v2 && u1 != u2 -> return 0
     * 2.   v1 != v2 && u1 == u2 -> return -1
     * 2.1  v1 ==v2 &&  u1 != u2 -> return -2
     * 2.2  v1 == v2 && u1 == u2 -> return 1
     *
     * @param o1 the first object to be compared.
     * @param o2 the second object to be compared.
     * @return compared result
     */
    @Override
    public int compare(Object o1, Object o2) {
        if (o1 == null) {
            throw new IllegalArgumentException("ObjectPlus.java error compareTo object o1 is null");
        }

        if (o2 == null) {
            throw new IllegalArgumentException("ObjectPlus.java error compareTo object o2 is null");
        }
        ObjectPlus<?, ?> obj1 = (ObjectPlus<?, ?>) o1;
        ObjectPlus<?, ?> obj2 = (ObjectPlus<?, ?>) o2;

        // v1 != v2 && i1 != i2
        if (!obj1.value.equals(obj2.value) && !obj1.uuid.equals(obj2.uuid)) {
            return 0;
        } else if (obj1.value != obj2.value && Objects.equals(obj1.uuid, obj2.uuid)) {
            // v1 != v2 && i1 = i2
            return -1;
        }

        // v1 = v2 && i1 != i2
        if (obj1.value.equals(obj2.value) && !obj1.uuid.equals(obj2.uuid)) {
            return -2;
        }

        // v1 = v2 && i1 = i2
        if (obj1.uuid.equals(obj2.uuid) && obj1.value.equals(obj2.value)) {
            return 1;
        }

        return 0;
    }


    @Override
    public boolean equals(Object o) {
        if (o == null && this != null) {
            return false;
        }

        if (o == null && this == null) {
            return true;
        }

        T cmpObj1 = (T) o;
        T cmpObj2 = (T) this;

        if (cmpObj1.value.equals(cmpObj2.value) && cmpObj1.getUuid().equals(cmpObj2.getUuid())) {
            return true;
        }

        return false;
    }

    /**
     * @param o the object to be compared.
     * @return this.value > o.value ?
     */
    @Override
    public int compareTo(Object o) {
        ObjectPlus<?, ?> t = (ObjectPlus<?, ?>) o;
        if (t.value.compareTo(this.value) > 0) {
            return -1;
        } else if (t.value.compareTo(this.value) < 0) {
            return 1;
        }
        return 0;
    }

    public E getValue() {
        return value;
    }

    public void setValue(E value) {
        this.value = value;
    }

    public static void swap(ObjectPlus<?, ?>[] num, int i, int j) {
        ObjectPlus<?, ?> t = num[i];
        num[i] = num[j];
        num[j] = t;
    }


}