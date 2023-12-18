package model.util.type;

import model.range.DataSet;
import model.range.Range;

import java.util.UUID;

/**
 * Due to the special nature of string types, special judgments are required when constructing random parameters
 */
public class StringPlus extends ObjectPlus<String, StringPlus> {
    static {
        typeStr = String.class.getTypeName();
        // Represents the data range for each character of the string
        TYPE_PLUS_RANGE = CharacterPlus.TYPE_PLUS_RANGE;
    }

    public StringPlus() {
        this(TYPE_PLUS_RANGE, (int) Range.getRandomMinToMax(10, 20));
    }

    public StringPlus(String value) {
        super(value);
    }

    public StringPlus(Range range, int len) {
        this.setUuid(String.valueOf(UUID.randomUUID()));
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < len; i++) {
            stringBuilder.append(CharacterPlus.getRandomChar(range));
        }
        this.value = stringBuilder.toString();
    }

    public StringPlus(DataSet set,Range range, int len) {
        this.setUuid(String.valueOf(UUID.randomUUID()));
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < len; i++) {
            stringBuilder.append(new CharacterPlus(set,range).value);
        }
        this.value = stringBuilder.toString();
    }

    public static StringPlus clone(StringPlus strPlus) {
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < strPlus.value.length(); i++) {
            s.append(strPlus.value.charAt(i));
        }

        StringPlus stringPlus = new StringPlus(s.toString());
        stringPlus.setUuid(strPlus.getUuid());
        return stringPlus;
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