package model.util;

import model.range.Range;
import model.sample.Argument;

import java.text.SimpleDateFormat;

/**
 * Global Utility Classes:
 * Contains some required global properties and global methods
 */
public class GlobalUtility {
    // logarithmic time statistics

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
    public static long testStart = 0;
    public static long testEnd = 0;
    public static long validatorStart = 0;
    public static long compareStart = 0;
    public static long validatorMinimum = 0;
    public static long validatorMaximum = 0;
    public static long validatorTotal = 0;

    public static long compareMinimum = 0;
    public static long compareMaximum = 0;
    public static long compareTotal = 0;


    // Global parameters to support the return of data after testing
    public static Argument[] arguments = null;
    // Define a constant RED, which represents the prefix of the red text
    public static final String RED = "\u001B[31m";
    // Define a constant RESET that resets the prefix of the colo
    public static final String RESET = "\u001B[0m";

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
     *
     * @param range range objects
     * @return a number in the range of min to max
     */
    public static double getRandomMinToMax(Range range) {
        return getRandomMinToMax(range.getMin(), range.getMax());
    }

    /**
     * Format the given timestamp as a string
     *
     * @param timestamp timestamp
     * @return Formatted strings
     */
    public static String formatTime(long timestamp) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
        return format.format(timestamp);
    }
}
