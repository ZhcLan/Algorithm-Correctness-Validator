package model.range;

/*
 * 优化结果集 DataSet  -> It's ok , to be tested
 * 链表的长度为1时的特殊情况处理
 * 优化排序,Fate特殊值
 */


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

    public Range(double min, double max, DataSet dataSet) {
        this.min = min;
        this.max = max;
        this.dataSet = dataSet;
    }

    public Range(double min, double max, DataSet dataSet, Fate fate) {
        this.min = min;
        this.max = max;
        this.dataSet = dataSet;
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

    /**
     * Due to the operating system's random scheduling strategy for processes/threads,
     * the statistics here are inaccurate and inaccurate
     * <p>
     * If you want to clearly perceive the difference between the two algorithms in
     * terms of running time, please zoom in on the number of random samples, but this
     * will result in a longer running time
     * <p>
     * Because the design and implementation of the entire logarithmic device does not
     * consider the optimization of performance and the large number of internal
     * reflections will also lead to the degradation of performance, so far, it is all
     * about the implementation of functions
     * <p>
     * Of course, JNI was considered to call C/C++ instead of some of the inefficient
     * code, but this was not done due to the cross-platform nature of Java and the
     * complexity of the actual development
     */


    /**
     * Based on the given minimum and maximum values, a random number is generated
     *
     * @param min minimum
     * @param max maximum
     * @return a number ,range of min to max
     */
    public static double getRandomMinToMax(double min, double max) {
        //  If the minimum value is equal to the maximum value, the minimum value is returned directly
        if (min == max) {
            return (double) min;
        }

        // If the minimum value is greater than the maximum value, swap the values of both
        if (min > max) {
            double t = min;
            min = max;
            max = t;
        }
        // Generates a random number ranging from minimum to maximum (both minimum and maximum) -> [min,max]
        return (Math.random() * (max - min + 1)) + min;
    }

    /**
     * Based on a given range, a random number is generated
     * @param range range objects
     * @return a number in the range of min to max
     */
    public static double getRandomMinToMax(Range range) {
        return getRandomMinToMax(range.getMin(), range.getMax());
    }
}
