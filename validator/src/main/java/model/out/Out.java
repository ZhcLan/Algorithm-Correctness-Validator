package model.out;

import model.config.ValidatorConfig;
import model.out.fomat.ConsoleTable;
import model.out.fomat.enums.Align;
import model.out.fomat.table.Cell;
import model.sample.Argument;
import model.util.type.ObjectPlus;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Presentation of the test data
 */
public class Out {

    // Define a constant RED, which represents the prefix of the red text
    public static final String RED = "\u001B[31m";
    // Define a constant RESET that resets the prefix of the colo
    public static final String RESET = "\u001B[0m";
    public static void showArguments(Argument[] arguments) {

        List<Cell> header = new ArrayList<Cell>() {{
            add(new Cell(Align.CENTER, "Number"));
            add(new Cell(Align.CENTER, "Type"));
            add(new Cell(Align.CENTER, "Order"));
            add(new Cell(Align.CENTER, "Volume"));
            add(new Cell(Align.CENTER, "Values"));
        }};


        List<List<Cell>> body = new ArrayList<>();


        for (int i = 0; i < arguments.length; i++) {
            List<Cell> inner = new ArrayList<>();
            inner.add(new Cell(Align.CENTER, i + 1 + ""));
            inner.add(new Cell(Align.CENTER, arguments[i].getClazz().getCanonicalName()));
            inner.add(new Cell(Align.CENTER, Boolean.toString(arguments[i].isOrder())));
            inner.add(new Cell(Align.CENTER, Arrays.toString(arguments[i].getVolume())));
            inner.add(new Cell(Align.CENTER, Arrays.toString(arguments[i].getValues())));
            body.add(inner);
        }


        System.out.println(RED + "The following Arguments information is displayed\n" + RESET +
                new ConsoleTable.ConsoleTableBuilder().addHeaders(header).addRows(body).build().toString());
    }

    public static void showSuccessInfo(ValidatorConfig config) {
        List<Cell> header = new ArrayList<Cell>() {{
            add(new Cell(Align.CENTER, "Time"));
            add(new Cell(Align.CENTER, "MethodName"));
            add(new Cell(Align.CENTER, "Times"));
            add(new Cell(Align.CENTER, "MinimumTime"));
            add(new Cell(Align.CENTER, "MaximumTime"));
            add(new Cell(Align.CENTER, "AverageTime"));// total time / test times
            add(new Cell(Align.CENTER, "TotalTime"));
            add(new Cell(Align.CENTER, "ValidatorTime"));// 对数器时间开销
        }};

        List<List<Cell>> body = new ArrayList<>();
        List<Cell> m1 = new ArrayList<>();
        m1.add(new Cell(Align.CENTER, formatTime(config.validatorStart)));// 处理时间格式(2023.11.30)
        m1.add(new Cell(Align.CENTER, config.getValidatorMethod()));
        m1.add(new Cell(Align.CENTER, config.getTimes() + "次"));// 测试次数
        m1.add(new Cell(Align.CENTER, config.validatorMinimum + "ms"));
        m1.add(new Cell(Align.CENTER, config.validatorMaximum + "ms"));
        m1.add(new Cell(Align.CENTER, config.validatorTotal / config.getTimes() + "ms"));
        m1.add(new Cell(Align.CENTER, config.validatorTotal + "ms"));
        m1.add(new Cell(Align.CENTER, config.testEnd - config.testStart + "ms"));
        body.add(m1);

        List<Cell> m2 = new ArrayList<>();
        m2.add(new Cell(Align.CENTER, formatTime(config.compareStart) + ""));// 处理时间格式(2023.11.30)
        m2.add(new Cell(Align.CENTER, config.getCompareMethod()));
        m2.add(new Cell(Align.CENTER, config.getTimes() + "次"));// 测试次数
        m2.add(new Cell(Align.CENTER, config.compareMinimum + "ms"));
        m2.add(new Cell(Align.CENTER, config.compareMaximum + "ms"));
        m2.add(new Cell(Align.CENTER, config.compareTotal / config.getTimes() + "ms"));
        m2.add(new Cell(Align.CENTER, config.compareTotal + "ms"));
        m2.add(new Cell(Align.CENTER, config.testEnd - config.testStart + "ms"));
        body.add(m2);

        System.out.println(RED + "\n\nThe verification is successful. The following information is displayed\n" + RESET +
                new ConsoleTable.ConsoleTableBuilder().addHeaders(header).addRows(body).build().toString() + "\n");
    }

    /**
     * If the result of the comparison of the two result sets is the
     * same value and the address is not the same, then this method
     * will provide a comparison of the output results of the two
     * methods , contains value and uuid
     *
     * @param validatorResultSet The result set of validator method
     * @param compareResultSet   The result set of compare method
     * @return A string displayed as a list
     */
    public static String outValueAndUUID(List<Object> validatorResultSet, List<Object> compareResultSet) {
        List<Cell> header = new ArrayList<Cell>() {{
            add(new Cell(Align.CENTER, "Index"));
            add(new Cell(Align.CENTER, "Validator Result Set value"));
            add(new Cell(Align.CENTER, "Compare Result Set value"));
            add(new Cell(Align.CENTER, "Validator Result Set UUID"));
            add(new Cell(Align.CENTER, "Compare Result Set UUID"));
        }};

        List<List<Cell>> body = new ArrayList<>();

        for (int i = 0; i < validatorResultSet.size(); i++) {
            List<Cell> m = new ArrayList<>();
            ObjectPlus<?, ?> t1 = (ObjectPlus<?, ?>) validatorResultSet.get(i);
            ObjectPlus<?, ?> t2 = (ObjectPlus<?, ?>) compareResultSet.get(i);
            m.add(new Cell(Align.CENTER, i + " "));
            m.add(new Cell(Align.CENTER, t1.getValue() + ""));
            m.add(new Cell(Align.CENTER, t2.getValue() + ""));
            m.add(new Cell(Align.CENTER, t1.getUuid()));
            m.add(new Cell(Align.CENTER, t2.getUuid()));
            body.add(m);
        }
        return new ConsoleTable.ConsoleTableBuilder().addHeaders(header).addRows(body).build().toString();
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
