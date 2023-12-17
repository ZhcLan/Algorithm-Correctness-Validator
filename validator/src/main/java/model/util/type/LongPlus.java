package model.util.type;

import model.range.Range;

import java.util.UUID;

public class LongPlus extends ObjectPlus<Long, LongPlus> {

    static {
        typeStr = LongPlus.class.getTypeName();
        TYPE_PLUS_RANGE = new Range(Long.MIN_VALUE, Long.MAX_VALUE);
    }

    public LongPlus() {
        this(TYPE_PLUS_RANGE);
    }

    public LongPlus(Long value) {
        super(value);
    }

    public LongPlus(Range range) {
        this.setUuid(String.valueOf(UUID.randomUUID()));
        this.value = (long) Range.getRandomMinToMax(range);
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
        ObjectPlus cmpObj2 =  this;

        if (cmpObj1.value.equals(cmpObj2.value)) {
            return true;
        }

        return false;
    }
}
