package controller;

import model.config.ValidatorConfig;
import model.range.Range;
import model.sample.Argument;
import model.util.type.IntegerPlus;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * @BelongsProject: v2_validator
 * @BelongsPackage: controller
 * @Author: zhc
 * @Description:
 * @Version: 1.0
 */
public class Demo {
    public static String f(LinkedList<ArrayList<IntegerPlus[]>> a) {
        return "";
    }

    public static String g(LinkedList<ArrayList<IntegerPlus[]>> a) {
        return "";
    }

    public static void main(String[] args) {
        // 1. 创建验证器配置
        ValidatorConfig config = new ValidatorConfig(1_0000, Demo.class, "f", "g");
        // 当然,你也可以不写成这种嵌套的写法

        // 2. 创建输入约束
        Argument argument = new Argument(
                new Range[]{// volume
                        new Range(2, 3),     // LinkedList     有3~4 个元素(ArrayList)
                        new Range(3, 4),     // ArrayList      有5~6 个元素(IntegerPlus[])
                        new Range(5, 6),     // IntegerPlus[]  有7~8 个元素(IntegerPlus - 整形)
                        new Range(-1,-1)     // 最后位无效占位,但是必须写出!
                },
                new Range[]{// value
                        new Range(-1,-1),    // 第一位无效占位,但是必须写出!
                        new Range(-1,-1),    // 表示LinkedList的数据范围,它不需要,所以使用(-1,-1)站位,后面同理
                        new Range(-1,-1),    // 表示IntegerPlus[]的数值范围
                        new Range(10,20)     // IntegerPlus的数值范围 (10,20)之间
                }
        );

        // 3. 激活验证器
        new Active().active(config,argument);// 第二个参数是 Argument ... args ,是一个边长参数,你必须将所有参数全部传入,否则无法完成反射
    }
}

