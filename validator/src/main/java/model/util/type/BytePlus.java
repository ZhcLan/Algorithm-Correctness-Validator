package model.util.type;

import model.range.Range;

import java.util.UUID;

public class BytePlus extends ObjectPlus<Byte, BytePlus> {

    static {
        typeStr = BytePlus.class.getTypeName();
        TYPE_PLUS_RANGE = new Range(Byte.MIN_VALUE, Byte.MAX_VALUE);
    }

    public BytePlus() {
        this(TYPE_PLUS_RANGE);
    }

    public BytePlus(Byte value) {
        super(value);
    }

    public BytePlus(Range range) {
        this.setUuid(String.valueOf(UUID.randomUUID()));
        this.value = (byte) Range.getRandomMinToMax(range);
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
