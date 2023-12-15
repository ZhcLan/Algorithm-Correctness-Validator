package model.sample;

import model.range.Range;

/**
 * This class is a simple abstraction of algorithm parameters,
 * and the coupling with the random sample constructor RPC is not high,
 * but it provides support for the internal transfer of parameters
 * <p>
 * <p>
 * When considering the amount of data, please expand according to the
 * 'binary fork', since the first data is the root and there is only one,
 * so start using (-1, -1) for standing .When considering the numerical range,
 * please use the (-1, -1) position for the last digit
 */
public class Argument {
    private Class<?> clazz; //  parameters' class object,courtesy of reflection
    private String typeStr; //  the full class name string of the parameter,courtesy of reflection
    private final boolean isOrder;//  Whether the parameter data collection is ordered(optional)
    private final Range[] volume; //  Amount of data, required field
    private final Range[] values; //  Numeric range, required field

    public Argument(boolean isOrder, Range[] volume, Range[] values) {
        this.isOrder = isOrder;
        this.volume = volume;
        this.values = values;
    }

    public Argument(Range[] volume, Range[] values) {
        this(false, volume, values);
    }

    public Range[] getVolume() {
        return volume;
    }

    public Range[] getValues() {
        return values;
    }

    public Class<?> getClazz() {
        return clazz;
    }

    public void setClazz(Class<?> clazz) {
        this.clazz = clazz;
    }

    public String getTypeStr() {
        return typeStr;
    }

    public void setTypeStr(String typeStr) {
        this.typeStr = typeStr;
    }

    public boolean isOrder() {
        return isOrder;
    }


}