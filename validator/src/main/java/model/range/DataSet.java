package model.range;

import java.util.Arrays;

/**
 * 生产随机数的数据集,如果为空,默认使用Range当做随机数值
 */
public class DataSet {
    // read only
    // 请使用引用类型,而不要使用基本数据类型
    private Object[] set;

    public DataSet(Object[] set) {
        this.set = set;
    }

    public DataSet() {
    }

    public Object[] getSet() {
        return set;
    }

    public static DataSet number = new DataSet(new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 0});


    // 你可以使用一下基本数据集进行拼接,也可以自定义数据集
    public static DataSet lowercase = new DataSet(
            new Character[]{
                    'a', 'b', 'c', 'd',
                    'e', 'f', 'g', 'h',
                    'i', 'j', 'k', 'l',
                    'm', 'n', 'o', 'p',
                    'q', 'r', 's', 't',
                    'u', 'v', 'w', 'x',
                    'y', 'z'
            }
    );

    public static DataSet uppercase = new DataSet(
            new Character[]{
                    'A', 'B', 'C', 'D',
                    'E', 'F', 'G', 'H',
                    'I', 'J', 'K', 'L',
                    'M', 'N', 'O', 'P',
                    'Q', 'R', 'S', 'T',
                    'U', 'V', 'W', 'X',
                    'Y', 'Z'
            }
    );


    public static DataSet numericCharacter = new DataSet(
            new Character[]{
                    '1', '2', '3',
                    '4', '5', '6',
                    '7', '8', '9',
                    '0'
            }
    );

    // ( )
    public static DataSet parentheses = new DataSet(
            new Character[]{
                    '(', ')'
            }
    );

    // < >


    // { }
    public static DataSet angleBrackets = new DataSet(
            new Character[]{
                    '{', '}'
            }
    );

    // + - * /
    public static DataSet operator = new DataSet(
            new Character[]{
                    '+', '-',
                    '*', '/'
            }
    );

    // 拼接数据集
    public static DataSet append(DataSet... sets) {
        int size = 0;
        for (int i = 0; i < sets.length; i++) {
            size += sets[i].set.length;
        }

        Object[] ret = new Object[size];

        int count = 0;
        for (int i = 0; i < sets.length; i++) {
            for (int j = 0; j < sets[i].set.length; j++) {
                ret[count++] = sets[i].set[j];
            }
        }
        return new DataSet(ret);
    }
}
