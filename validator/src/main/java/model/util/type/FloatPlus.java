package model.util.type;

import model.range.Range;

import java.util.UUID;

public class FloatPlus extends ObjectPlus<Float, FloatPlus> {

    static {
        typeStr = FloatPlus.class.getTypeName();
        TYPE_PLUS_RANGE = new Range(Float.MIN_VALUE, Float.MAX_VALUE);
    }

    public FloatPlus() {
        this(TYPE_PLUS_RANGE);
    }

    public FloatPlus(Float value) {
        super(value);
    }

    public FloatPlus(Range range) {
        this.setUuid(String.valueOf(UUID.randomUUID()));
        this.value = (float) Range.getRandomMinToMax(range);
    }

}
