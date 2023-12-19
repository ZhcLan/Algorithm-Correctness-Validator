package model.util.type;

import model.range.DataSet;
import model.range.Range;

import java.util.UUID;

public class ShortPlus extends ObjectPlus<Short, ShortPlus> {

    static {
        typeStr = ShortPlus.class.getTypeName();
        TYPE_PLUS_RANGE = new Range(Short.MIN_VALUE, Byte.MAX_VALUE);
    }

    public ShortPlus() {
        this(TYPE_PLUS_RANGE);
    }

    public ShortPlus(Short value) {
        super(value);
    }

    public ShortPlus(Range range) {
        this.setUuid(String.valueOf(UUID.randomUUID()));
        this.value = (short) Range.getRandomMinToMax(range);
    }

    public ShortPlus(DataSet set, Range range) {
        this.setUuid(String.valueOf(UUID.randomUUID()));
        int randomIndex = (int) Range.getRandomMinToMax(range);
        this.value = (Short) set.getSet()[randomIndex % set.getSet().length];
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
