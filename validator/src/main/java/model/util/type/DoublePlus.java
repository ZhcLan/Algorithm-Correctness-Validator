package model.util.type;

import model.range.Range;

import java.util.UUID;

public class DoublePlus extends ObjectPlus<Double, DoublePlus> {

    static {
        typeStr = DoublePlus.class.getTypeName();
        TYPE_PLUS_RANGE = new Range(Double.MIN_VALUE, Double.MAX_VALUE);
    }

    public DoublePlus() {
        this(TYPE_PLUS_RANGE);
    }

    public DoublePlus(Double value) {
        super(value);
    }

    public DoublePlus(Range range) {
        this.setUuid(String.valueOf(UUID.randomUUID()));
        this.value = Range.getRandomMinToMax(range);
    }

}
