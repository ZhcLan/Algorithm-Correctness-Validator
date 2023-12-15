package controller;

import model.config.ValidatorConfig;
import model.range.Range;
import model.sample.Argument;
import model.util.type.IntegerPlus;
import model.util.type.ObjectPlus;

/**
 * Provide basic use cases <br />
 * Includes startup and range data volumes, data range constraints
 */
public class Main {
    // for test!
    public static IntegerPlus[] selectSort(IntegerPlus[] arr) {
        if (arr == null || arr.length < 2) {
            return arr;
        }
        for (int minIndex, i = 0; i < arr.length - 1; i++) {
            minIndex = i;
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[j].value < arr[minIndex].value) {
                    minIndex = j;
                }
            }
            ObjectPlus.swap(arr, i, minIndex);
        }
        return arr;
    }
    public static IntegerPlus[] bubbleSort(IntegerPlus[] arr) {
        if (arr == null || arr.length < 2) {
            return arr;
        }
        for (int end = arr.length - 1; end > 0; end--) {
            for (int i = 0; i < end; i++) {
                if (arr[i].value > arr[i + 1].value) {
                    ObjectPlus.swap(arr, i, i + 1);
                }
            }
        }
        return arr;
    }

    public static IntegerPlus[] insertSort(IntegerPlus[] arr) {
        if (arr == null || arr.length < 2) {
            return arr;
        }
        for (int i = 1; i < arr.length; i++) {
            for (int j = i - 1; j >= 0 && arr[j].value > arr[j + 1].value; j--) {
                ObjectPlus.swap(arr, j, j + 1);
            }
        }
        return arr;
    }

    /**
     * use case:<br/>
     * First, you need a 'ValidatorConfig' object to constrain the relevant parameters
     * of this test contains the following fields:<br/>
     * times - the number of tests<br/>
     * class - The class object of the class in which the test method is located<br/>
     * validator method name - The method name of the logarithmic method<br/>
     * compare method name - The name of the method of the comparison method<br/>
     * comparison - comparison method.(optional).is false ? comparison value.is true ?
     * compare value and UUID.<br/>
     * where comparison is not specified as false, i.e. UUID (simulated address field)
     * is not compared<br/>
     * <p>
     * <br/>
     * <br/>
     * <br/>
     * <p>
     * Second, you need an array of 'Argument' objects representing every parameter in your algorithm
     * <p>
     * For each 'Argument' object, you need to configure the relevant parameters,including the following fields
     * <p>
     * isOrder - Ordered or not (optional)
     * <p>
     * volume - the amount of data
     * <p>
     * values—The range of data
     * <p>
     * <br/>
     * <p>
     * Finally, you need to call the logarithmic activation method active
     * The first parameter is 'ValidatorConfig', which indicates the parameters of this test<br/>
     * The second parameter is an edge length parameter,
     * you can give as many parameters as you want, and the parameters will be parsed internally
     * and a random sample will be generated
     * <p>
     * It should be noted that although parsing of complex parameters is supported, it is not
     * recommended, as it will increase the complexity of the algorithm and may cause parsing errors
     * <p>
     * Another thing to note is that the basic data types are encapsulated internally (in order
     * to compare 'addresses')
     * <p>
     * So you have to use the base type of the encapsulated Plus version, otherwise undefined
     * behavior will occur
     * <p>
     * After that, you can start the test using the active method!
     * <p>
     * &ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;
     * &ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;
     * &ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;
     * &ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;
     * &ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;
     * &ensp;&ensp;&ensp;by zhc
     */
    public static void main(String[] args) {

        ValidatorConfig config = new ValidatorConfig(1_0000, Main.class, "selectSort",
                "insertSort", true);
        Argument arguments = new Argument(
                // Note that due to the recursive parsing of the parameters,
                // the last of the volume here will not be used,
                // but it needs to be stationed!
                new Range[]{new Range(5, 8), new Range(-1, -1)},
                // If the root node of the parameter is of type Plus,
                // then the values are valid from the first digit (index 0).
                // Otherwise, if the root node of the parameter is not of type Plus,
                // then the values are valid from the second digit, and the first digit is used to stand!
                new Range[]{new Range(-1, -1), new Range(-200, 200)}
        );
        // startup!
        new Active().active(config, arguments);
    }


    /*
     * Some cases:
     */

// is ok!
//    public static String test1(ArrayList<ArrayList<IntegerPlus[][][]>[]>[][] s) {
//        return "";
//    }
//
//    public static String test2(ArrayList<ArrayList<IntegerPlus[][][]>[]>[][] s) {
//        return "";
//    }


// is ok!
//    public static String test1(ArrayList<ArrayList<IntegerPlus[]>[]>[] s) {
//        return "";
//    }
//
//    public static String test2(ArrayList<ArrayList<IntegerPlus[]>[]>[] s) {
//        return "";
//    }

// is ok!
//    public static String test1(IntegerPlus[][] a) {
//        return  "";
//    }
//
//    public static String test2(IntegerPlus[][] a) {
//        return  "";
//    }

// is ok!
//    public static String test1(ArrayList<ArrayList<IntegerPlus[][]>[][]>[][] s) {
//        return "";
//    }
//
//    public static String test2(ArrayList<ArrayList<IntegerPlus[][]>[][]>[][] s) {
//        return "";
//    }


//    is ok !
//    public static String test1(HashMap<IntegerPlus[][], ArrayList<IntegerPlus[][]>> s) {
//        return "";
//    }
//
//    public static String test2(HashMap<IntegerPlus[][], ArrayList<IntegerPlus[][]>> s) {
//        return "";
//    }

    //    for test
//    public static String test1(ListNode<IntegerPlus> a) {
//        return "";
//    }
//
//    public static String test2(ListNode<IntegerPlus> a) {
//        return "";
//    }

// is ok !
//    public static String test1(IntegerPlus[] a) {
//        return "";
//    }
//
//    public static String test2(IntegerPlus[] a) {
//        return "";
//    }

    /*
     * You can cooperate with Range, Fate, and DataSet to make random data fall into the range you want
     * When specifying the parameter Range, if the nesting of the parameter is complicated, please expand
     * it according to the 'binary fork', and specify the Data Amount and Numeric Range from left to right
     */
//    public static void main(String[] args) {
//        ValidatorConfig config = new ValidatorConfig(1000, Main.class, "test1", "test2");
//        Argument argument = new Argument(
//                true,
//                new Range[]{
//                        new Range(7, 10,// -> 数据量
//                                // fate 3X3 4X4
//                                new Fate(new Range[]{new Range(2, 2), new Range(3, 3)}, new Range[]{new Range(2, 2), new Range(3, 3)})),
//                        // 占位
//                        new Range(5, 8),
//                        new Range(1, 1),
//                        new Range(1, 1),
//                        new Range(1, 1),
//                        new Range(1, 1),
//                        new Range(1, 1),
//                        new Range(-1, -1)},
//                // 默认数据范围
//                new Range[]{
//                        new Range(-1, -1),
//                        new Range(128, 200),
//                        new Range(128, 200),
//                        new Range(128, 200),
//                        new Range(128, 200),
//                        new Range(128, 200)}
//        );
//        new Active().active(config, argument);
//    }
}