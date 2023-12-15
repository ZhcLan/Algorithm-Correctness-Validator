package model.util.type;

import model.range.Range;
import model.util.GlobalUtility;

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
        this.value = (char) GlobalUtility.getRandomMinToMax(range);
    }

    public static Character getRandomChar(Range range) {
        return new CharacterPlus(range).value;
    }
}