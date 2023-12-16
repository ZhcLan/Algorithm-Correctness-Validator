package model.config;

import model.out.Out;
import model.out.fomat.ConsoleTable;
import model.out.fomat.enums.Align;
import model.out.fomat.table.Cell;
import model.util.type.ObjectPlus;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * This class represents the configuration of logarithmic parameters,
 * which is initialized by the user display
 * It contains five fields 'times', 'clazz', 'validatorMethod',
 * 'compareMethod' and 'comparison'
 * Except for the 'comparison' field, all fields are mandatory
 * <p>
 * <p>
 * It's important to note that this class, after instantiation,
 * doesn't want to be manipulated by any entity and is read-only!
 */
public class ValidatorConfig {
    // validator times
    private int times;
    // The class object of the class of the methods to be tested,
    // 'validator method' and 'compare method'
    private Class<?> clazz;

    // logarithmic method
    private String validatorMethod;
    // comparison method of logarithmic device
    private String compareMethod;
    // How to compare the result set, only the
    // value, or the value and UUID (simulated address)
    private boolean comparison;


    public long testStart = 0;
    public long testEnd = 0;
    public long validatorStart = 0;
    public long compareStart = 0;
    public long validatorMinimum = 0;
    public long validatorMaximum = 0;
    public long validatorTotal = 0;

    public long compareMinimum = 0;
    public long compareMaximum = 0;
    public long compareTotal = 0;

    // constructor
    public ValidatorConfig(int times, Class<?> clazz, String validatorMethod, String compareMethod, boolean comparison) {
        this.times = times;
        this.clazz = clazz;
        this.validatorMethod = validatorMethod;
        this.compareMethod = compareMethod;
        this.comparison = comparison;
    }

    // constructor
    public ValidatorConfig(int times, Class<?> clazz, String validatorMethod, String compareMethod) {
        this(times, clazz, validatorMethod, compareMethod, false);
    }

    public int getTimes() {
        return times;
    }

    public Class<?> getClazz() {
        return clazz;
    }

    public String getValidatorMethod() {
        return validatorMethod;
    }

    public boolean isComparison() {
        return comparison;
    }

    public String getCompareMethod() {
        return compareMethod;
    }

    @Override
    public String toString() {
        List<Cell> header = new ArrayList<Cell>() {{
            add(new Cell(Align.CENTER, "Times"));
            add(new Cell(Align.CENTER, "Class"));
            add(new Cell(Align.CENTER, "Validator"));
            add(new Cell(Align.CENTER, "Compare"));
            add(new Cell(Align.CENTER, "Comparison"));
        }};

        List<List<Cell>> body = new ArrayList<List<Cell>>();
        List<Cell> m1 = new ArrayList<>();
        m1.add(new Cell(Align.CENTER, times + ""));
        m1.add(new Cell(Align.CENTER, clazz.getName()));
        m1.add(new Cell(Align.CENTER, validatorMethod));
        m1.add(new Cell(Align.CENTER, compareMethod));
        m1.add(new Cell(Align.CENTER, comparison ? "uuid" : "value"));
        body.add(m1);

        return Out.RED + "\nThe following test information is displayed\n"
                + Out.RESET + new ConsoleTable.ConsoleTableBuilder().addHeaders(header).addRows(body).build().toString();
    }
}
