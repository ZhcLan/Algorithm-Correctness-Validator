package controller;

import model.config.ValidatorConfig;
import model.out.Out;
import model.reflect.ReflectiveInvoker;
import model.sample.Argument;
import model.util.GlobalUtility;

import static model.util.GlobalUtility.testStart;

/**
 * <p>
 * Activate the logarithmic device.
 * <p>
 * Prepare the relevant environment for the logarithm before activation.
 * For example, construct the global static property 'GlobalUtility.arguments'
 * , etc...
 */
public class Active {
    /**
     * If the test is successful, the test information will be displayed when
     * the logarithm returns the results (all verification successful).
     * <p>
     * If the test fails, the test will be terminated by returning false when
     * comparing the result set internally in 'verification'
     * <p>
     * Displays errors, as well as error conditions, reminds users to resize
     * samples and debug
     *
     * @param config    logarithmic arguments
     * @param arguments parameter arguments
     */
    public void active(ValidatorConfig config, Argument... arguments) {
        if (arguments == null) {
            throw new IllegalArgumentException("arguments is null!");
        }

        if (testStart == 0) {
            testStart = System.currentTimeMillis();
        }

        GlobalUtility.arguments = arguments;

        // Partial information about the parameters is obtained through reflection
        for (int i = 0; i < arguments.length; i++) {
            arguments[i].setTypeStr(ReflectiveInvoker.getTypeStr(config.getClazz(), config.getValidatorMethod(), i));
            arguments[i].setClazz(ReflectiveInvoker.getClassObj(config.getClazz(), config.getValidatorMethod(), i));
        }

        // display arguments
        Out.showArguments(arguments);
        // display test config
        System.out.println(config.toString());

        // start !
        boolean result = new Validator().verification(config, arguments);
        GlobalUtility.testEnd = System.currentTimeMillis();

        // if success display success log!
        if (result) {
            Out.showSuccessInfo(config);
        }
    }
}