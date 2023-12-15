package model.range;

/**
 * It has not been implemented at this time,
 * and it is uncertain whether it will be
 * implemented in the future
 */
public class DataSet {
    private Object[] dataSet;

    /**
     * If the dataset is not empty, the random index modulops the length of the array
     * Get a valid index to get the random data corresponding to the dataset
     *
     * @param dataSet data set
     */
    public DataSet(Object[] dataSet) {
        this.dataSet = dataSet;
    }
}