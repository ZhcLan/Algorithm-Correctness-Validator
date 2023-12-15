package controller;

import model.config.ValidatorConfig;
import model.out.progress.ProgressBar;
import model.range.Range;
import model.reflect.ReflectiveInvoker;
import model.sample.Argument;
import model.sample.Sample;
import model.util.GlobalUtility;
import model.util.ListNode;

import java.util.Arrays;
import java.util.List;

import static model.util.GlobalUtility.*;

/**
 * The main control logic of the logarithmic
 * Complete reflection calls to 'validator method' and 'compare method'
 * At the same time, some test information is recorded, such as the test time
 */
public class Validator {
    /**
     * Start validating your methods and provide real-time feedback
     * @param config    test arguments
     * @param arguments parameter arguments
     * @return return whether the result set is 'equal'
     */
    public boolean verification(ValidatorConfig config, Argument... arguments) {
        System.out.println("\n" + GlobalUtility.RED + "测试中,当前进度:" + GlobalUtility.RESET);
        ProgressBar progressBar = new ProgressBar(config.getTimes(), 100);
        for (int i = 0; i < config.getTimes(); i++) {
            progressBar.update(i);
            Range[][] volume = new Range[arguments.length][];
            Range[][] values = new Range[arguments.length][];
            Sample.combination(volume, values, arguments);
            // generate random logarithmic sample
            Sample sample = new Sample(config, volume, values);

            // If you need to observe the input of production, please break it here
            if (GlobalUtility.validatorStart == 0) {
                GlobalUtility.validatorStart = System.currentTimeMillis();
            }

            long start = System.currentTimeMillis();
            // reflect call validator method
            Object result1 = ReflectiveInvoker.call(config.getClazz(), config.getValidatorMethod(), sample.validatorSample);

            long end = System.currentTimeMillis();
            validatorTotal += (end - start);

            if (end - start > validatorMaximum) {
                validatorMaximum = (end - start);
            }

            if (end - start < validatorMinimum) {
                validatorMinimum = (end - start);
            }

            if (compareStart == 0) {
                compareStart = System.currentTimeMillis();
            }
            long start1 = System.currentTimeMillis();

            // reflect call compare method
            Object result2 = ReflectiveInvoker.call(config.getClazz(), config.getCompareMethod(), sample.cloneSample);

            long end1 = System.currentTimeMillis();

            compareTotal += (end1 - start1);
            if (end1 - start1 > compareMaximum) {
                compareMaximum = (end1 - start1);
            }

            if (end1 - start1 < compareMinimum) {
                compareMinimum = (end1 - start1);
            }

            // conversion test result is List
            List<Object> list1 = Sample.resultAsList(result1);
            List<Object> list2 = Sample.resultAsList(result2);

            if (!sample.compareResultSet(list1, list2, config)) {
                return false;
            }
        }
        return true;
    }
}
