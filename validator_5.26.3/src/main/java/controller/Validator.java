package controller;

import model.config.ValidatorConfig;
import model.out.Out;
import model.out.progress.ProgressBar;
import model.range.Range;
import model.reflect.ReflectiveInvoker;
import model.sample.Argument;
import model.sample.Sample;
import model.util.ListNode;

import java.util.Arrays;
import java.util.List;
/**
 * 做成一个独立的代码沙箱,
 * 用户提交代码   =>     编译      =>     生产随机样本     => 测试       =>      得到结果
 * 字节码  -> 独立编译
 * <p>
 * 编译包(Sample)
 * <p>
 * range
 */

/**
 * The main control logic of the logarithmic
 * Complete reflection calls to 'validator method' and 'compare method'
 * At the same time, some test information is recorded, such as the test time
 */
public class Validator {
    /**
     * Start validating your methods and provide real-time feedback
     *
     * @param config    test arguments
     * @param arguments parameter arguments
     * @return return whether the result set is 'equal'
     */
    public boolean verification(ValidatorConfig config, Argument... arguments) {
        System.out.println("\n" + Out.RED + "测试中,当前进度:" + Out.RESET);
        ProgressBar progressBar = new ProgressBar(config.getTimes(), 100);
        for (int i = 0; i < config.getTimes(); i++) {
            progressBar.update(i);
            // generate random logarithmic sample
            Sample sample = new Sample(config, arguments);

            // If you need to observe the input of production, please break it here
            if (config.validatorStart == 0) {
                config.validatorStart = System.currentTimeMillis();
            }

            long start = System.currentTimeMillis();
            // reflect call validator method
            Object result1 = ReflectiveInvoker.call(config.getClazz(), config.getValidatorMethod(), sample.validatorSample);

            long end = System.currentTimeMillis();
            config.validatorTotal += (end - start);

            if (end - start > config.validatorMaximum) {
                config.validatorMaximum = (end - start);
            }

            if (end - start < config.validatorMinimum) {
                config.validatorMinimum = (end - start);
            }

            if (config.compareStart == 0) {
                config.compareStart = System.currentTimeMillis();
            }
            long start1 = System.currentTimeMillis();

            // reflect call compare method
            Object result2 = ReflectiveInvoker.call(config.getClazz(), config.getCompareMethod(), sample.cloneSample);

            long end1 = System.currentTimeMillis();

            config.compareTotal += (end1 - start1);
            if (end1 - start1 > config.compareMaximum) {
                config.compareMaximum = (end1 - start1);
            }

            if (end1 - start1 < config.compareMinimum) {
                config.compareMinimum = (end1 - start1);
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
