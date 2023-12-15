package model.range;


/**
 * If you need to make a special value, please use the Collection collection,
 * <p>
 * Only the Collection collection provides support for the Fate class
 * <p>
 * If you use other interfaces or 2D arrays, specifying Fate will result in undefined behavior
 * <p>
 * And multidimensional arrays cannot be sorted
 */
public class Fate {
    /**
     * You should specify the fate at the top of the leaf node, after which
     * the fate-range will be brought into the leaf node to be created
     * <p>
     * If the value you want to specify is not a collection, then you can
     * specify it in the leaf node!
     */
    private model.range.Range[] times;
    private Range[] value;

    public Fate(model.range.Range[] times, Range[] value) {
        this.times = times;
        this.value = value;
    }

    public model.range.Range[] getTimes() {
        return times;
    }

    public Range[] getValue() {
        return value;
    }

    public void setValue(Range[] value) {
        this.value = value;
    }
}