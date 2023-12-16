package model.util.type;

import model.range.Range;

import java.util.UUID;

public class BooleanPlus extends ObjectPlus<Boolean, BooleanPlus> {
    static {
        typeStr = Boolean.class.getTypeName();
        TYPE_PLUS_RANGE = new Range(-1, 1);
    }

    public BooleanPlus() {
        this(TYPE_PLUS_RANGE);
    }

    public BooleanPlus(Boolean value) {
        super(value);
    }

    public BooleanPlus(Range range) {
        this.setUuid(String.valueOf(UUID.randomUUID()));
        this.value = getRandomBoolean(range);
    }

    // if random < 0 return false
    // if random > 0 return true
    // if random = 0 return Math.random() < 0.5
    private Boolean getRandomBoolean(Range range) {
        double random = Range.getRandomMinToMax(range);
        if (random < 0) {
            return false;
        } else if (random > 0) {
            return true;
        }
        return Math.random() < 0.5;
    }
}