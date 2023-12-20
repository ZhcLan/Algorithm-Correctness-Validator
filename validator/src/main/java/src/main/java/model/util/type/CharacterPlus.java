package model.util.type;

import model.range.DataSet;
import model.range.Range;

import java.util.UUID;

public class CharacterPlus extends ObjectPlus<Character, CharacterPlus> {
    static {
        typeStr = Character.class.getTypeName();
        TYPE_PLUS_RANGE = new Range(Character.MIN_VALUE, Character.MAX_VALUE);
    }

    public CharacterPlus() {
        this(TYPE_PLUS_RANGE);
    }

    public CharacterPlus(Character value) {
        super(value);
    }

    public CharacterPlus(Range range) {
        this.setUuid(String.valueOf(UUID.randomUUID()));
        this.value = (char) Range.getRandomMinToMax(range);
    }

    public CharacterPlus(DataSet set, Range range) {
        this.setUuid(String.valueOf(UUID.randomUUID()));
        int randomIndex = (int) Range.getRandomMinToMax(range);
        this.value = (Character) set.getSet()[Math.abs(randomIndex % set.getSet().length)];
    }

    public static Character getRandomChar(Range range) {
        return new CharacterPlus(range).value;
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