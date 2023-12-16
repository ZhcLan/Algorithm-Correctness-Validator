package model.sample;

import model.config.ValidatorConfig;
import model.out.Out;
import model.range.Range;
import model.util.type.IntegerPlus;
import model.util.type.ObjectPlus;

import java.util.*;

// 使用比较器
// 给用户提供两个全局默认比较器
// value
// value & uuid

/**
 * A single set of test samples, including the main sample 'validator sample',
 * the clone sample 'lone sample' and the error sample 'error sample'
 * <p>
 * The main sample 'validator sample' is used to test the validator method
 * <p>
 * The cloned sample 'validator sample' is used to test the compare method
 * <p>
 * The 'error sample' is a retention sample of the original sample,
 * if the test fails, the original sample is displayed
 */
public class Sample {
    public final Object[] validatorSample;// Sample logarithmic method
    public final Object[] cloneSample;    // The logarithmic sample clone uuid & value is the same
    public final String errorSample;      // Error Sample is used to output error information

    /**
     * constructor
     *
     * @param config validator config
     * @param volume amount of data
     * @param values numeric rang
     */
    public Sample(ValidatorConfig config, Argument[] arguments, Range[][] volume, Range[][] values) {
        try {
            StringBuilder error = new StringBuilder();      // Output-type parameters
            Object[][] parameter = RPC.rpc(config, arguments, volume, values, error);
            validatorSample = parameter[0];
            cloneSample = parameter[1];
            errorSample = error.toString();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Compare the result sets
     */
    public boolean compareResultSet(List<Object> validatorResultRet, List<Object> compareResultSet, ValidatorConfig config) {
        if (validatorResultRet == null && compareResultSet != null) {
            System.out.print("\n\n" + Out.RED + "Verification failed, please adjust the sample size debug!" + Out.RESET);
            System.out.println("\nsample:" + errorSample);
            System.out.println(config.getValidatorMethod() + ":" + null);
            System.out.println(config.getCompareMethod() + ":" + compareResultSet);
            System.out.println(Out.RED + "validator result set is empty and compare result set is not empty!" + Out.RESET);
            return false;
        }

        if (validatorResultRet != null && compareResultSet == null) {
            System.out.print("\n\n" + Out.RED + "Verification failed, please adjust the sample size debug!" + Out.RESET);
            System.out.println("\nsample:" + errorSample);
            System.out.println(config.getValidatorMethod() + ":" + validatorResultRet);
            System.out.println(config.getCompareMethod() + ":" + null);
            System.out.println(Out.RED + "compare result set is empty and validator result set is not empty!" + Out.RESET);
        }


        if (validatorResultRet == null && compareResultSet == null) {
            return true;
        }

        assert compareResultSet != null;
        if (validatorResultRet.size() != compareResultSet.size()) {
            System.out.print("\n\n" + Out.RED + "Verification failed, please adjust the sample size debug!" + Out.RESET);
            System.out.println("\nsample:" + errorSample);
            System.out.println(config.getValidatorMethod() + ":" + validatorResultRet);
            System.out.println(config.getCompareMethod() + ":" + compareResultSet);
            System.out.println(Out.RED + "The length of validator result set and compare result set is different!" + Out.RESET);
            return false;
        }

        // compare value & uuid (address filed)
        if (config.isComparison()) {
            for (int i = 0; i < validatorResultRet.size(); i++) {
                int result = new ObjectPlus<Integer, IntegerPlus>(-1).compare(validatorResultRet.get(i), compareResultSet.get(i));
                if (result == 0) {
                    // v1 != v2 && i1 != i2
                    System.out.print("\n\n" + Out.RED + "Verification failed, please adjust the sample size debug!" + Out.RESET);
                    System.out.println("\nsample:" + errorSample);
                    System.out.println(Out.outValueAndUUID(validatorResultRet, compareResultSet));
                    System.out.println(Out.RED + "The addresses and values of the validator result set and compare result set elements are not equal!" + Out.RESET);
                    return false;
                } else if (result == -2) {
                    // v1 = v2 && i1 != i2
                    System.out.print("\n\n" + Out.RED + "Verification failed, please adjust the sample size debug!" + Out.RESET);
                    System.out.println("\nsample:" + errorSample);
                    System.out.println(Out.outValueAndUUID(validatorResultRet, compareResultSet));
                    System.out.println(Out.RED + "The addresses of the validator result set and compare result set elements are not equal!" + Out.RESET);
                    return false;
                } else if (result == -1) {
                    // v1 != v2 && i1 = i2
                    System.out.print("\n\n" + Out.RED + "Verification failed, please adjust the sample size debug!" + Out.RESET);
                    System.out.println("\nsample:" + errorSample);
                    System.out.println(config.getValidatorMethod() + ":" + validatorResultRet);
                    System.out.println(config.getCompareMethod() + ":" + compareResultSet);
                    System.out.println(Out.RED + "The values of the validator result set and compare validator elements are not equal!" + Out.RESET);
                    return false;
                }
            }
        } else {
            // only compare value
            for (int i = 0; i < validatorResultRet.size(); i++) {
                if (validatorResultRet.get(i) == null && compareResultSet.get(i) == null) {
                    continue;
                }

                if (validatorResultRet.get(i) == null && compareResultSet.get(i) != null || validatorResultRet.get(i) != null && compareResultSet.get(i) == null) {
                    return false;
                }

                int result = Integer.MIN_VALUE;
                try {
                    result = new ObjectPlus<Integer, IntegerPlus>(-1).compare(validatorResultRet.get(i), compareResultSet.get(i));
                } catch (ClassCastException e) {
                    result = validatorResultRet.get(0).equals(compareResultSet.get(0)) ? 1 : 0;
                }
                if (result == 0 || result == -1) {
                    System.out.print("\n\n" + Out.RED + "Verification failed, please adjust the sample size debug!" + Out.RESET);
                    System.out.println("\nsample:" + errorSample);
                    System.out.println(config.getValidatorMethod() + ":" + validatorResultRet);
                    System.out.println(config.getCompareMethod() + ":" + compareResultSet);
                    System.out.println(Out.RED + "The values of the list1 and list2 elements are not equal," + Out.RESET);
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * combine 'volume' and 'values' to get a two-dimensional structure
     *
     * @param volume
     * @param values
     * @param arguments
     */
    public static void combination(Range[][] volume, Range[][] values, Argument... arguments) {
        for (int i = 0; i < arguments.length; i++) {
            volume[i] = arguments[i].getVolume();
            values[i] = arguments[i].getValues();
        }
    }

    /**
     * Please do not return a two-value set
     *
     * @param result The result set after the calculation
     * @return
     */
    public static ArrayList<Object> resultAsList(Object result) {
        ArrayList<Object> ret = new ArrayList<>();
        if (result == null) {
            ret = null;
        } else {
            try {
                ret.addAll((Collection<?>) result);
            } catch (Exception e_non_collection) {
                try {
                    Object[] arr = (Object[]) result;
                    ret.addAll(Arrays.asList(arr));
                } catch (Exception e_non_array) {
                    // ObjectPlus
                    ret.add(result);
                }
            }
        }
        return ret;
    }
}