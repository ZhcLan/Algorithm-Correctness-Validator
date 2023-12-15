package model.range;

/**
 * Use Range, which allows the user to specify
 * the range of data and the range of values
 * <p>
 * For each Plus type (excluding ObjectPlus),
 * a single-parameter Range construction method is provided,
 * and when analyzing parameters to construct random samples
 * <p>
 * It will be constructed according to the value range specified by the user,
 * and because the StringPlus type is more special,
 * <p>
 * we provide him with StringPlus( Range range , int len)
 * Constructio <br/>
 */
public final class Range {
    private final double min;
    private final double max;

    // data set
    DataSet dataSet;

    // special value fate
    Fate fate;

    public Range(double min, double max) {
        checkRange();
        this.min = min;
        this.max = max;
    }

    public Range(double min, double max, Fate fate) {
        this.min = min;
        this.max = max;
        this.fate = fate;
    }

    /**
     * Check whether min is greater than max
     */
    private void checkRange() {
        if (this.min > this.max) {
            throw new IllegalArgumentException("range error: minValue > maxValue");
        }
    }

    public double getMin() {
        return min;
    }

    @Override
    public String toString() {
        return "(" + min + "," + max + ')';
    }

    public double getMax() {
        return max;
    }

    public DataSet getDataSet() {
        return dataSet;
    }

    public void setDataSet(DataSet dataSet) {
        this.dataSet = dataSet;
    }

    public Fate getFate() {
        return fate;
    }

    public void setFate(Fate fate) {
        this.fate = fate;
    }
}
