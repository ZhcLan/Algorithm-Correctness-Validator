package model.util.type;

import model.range.Range;
import model.util.GlobalUtility;

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
        this.value = (int) GlobalUtility.getRandomMinToMax(range);
    }

}
