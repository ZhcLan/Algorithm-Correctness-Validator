package model.util.type;

import model.range.Range;
import model.util.GlobalUtility;

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
        this.value = (short) GlobalUtility.getRandomMinToMax(range);
    }

}
