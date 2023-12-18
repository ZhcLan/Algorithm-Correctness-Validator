package model.util.type;

import model.range.DataSet;
import model.range.Range;

import java.util.UUID;

public class IntegerPlus extends ObjectPlus<Integer, IntegerPlus> {

    static {
        typeStr = IntegerPlus.class.getTypeName();
        TYPE_PLUS_RANGE = new Range(Integer.MIN_VALUE, Integer.MAX_VALUE);
    }

    public IntegerPlus() {
        this(TYPE_PLUS_RANGE);
    }

    public IntegerPlus(Integer value) {
        super(value);
    }


    public IntegerPlus(Range range) {
        this.setUuid(String.valueOf(UUID.randomUUID()));
        this.value = (int) Range.getRandomMinToMax(range);
    }

    public IntegerPlus(DataSet set, Range range) {
        this.setUuid(String.valueOf(UUID.randomUUID()));
        try {
            int randomIndex = (int) Range.getRandomMinToMax(range);
            this.value = (Integer) set.getSet()[randomIndex];
        } catch (Exception e) {
            throw new IllegalArgumentException("random index out of bound");
        }

    }

    @Override
    public boolean equals(Object o) {
        if (o == null && this != null) {
            return false;
        }

        if (o == null && this == null) {
            return true;
        }

        ObjectPlus cmpObj1 = (ObjectPlus) o;
        ObjectPlus cmpObj2 = this;

        if (cmpObj1.value.equals(cmpObj2.value)) {
            return true;
        }

        return false;
    }
}
