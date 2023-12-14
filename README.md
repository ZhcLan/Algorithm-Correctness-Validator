# Validator 对数器

算法对数器是一种用于验证算法正确性的测试工具。通过生成大量随机测试数据，可以有效地检测算法在各种情况下的表现，提高算法的可靠性和稳定性。

-   使用算法对数器对算法进行验证，确保算法的正确性；
-   使用算法对数器对算法进行性能测试，找出潜在的问题并进行优化；

# ACV - 通用算法对数器

基本介绍:

1.   由于在Java中获取地址比较困难,所以内部针对基本类型作了封装

| Java 基本类型包装类 |  ACV封装类型 |
| :------------------- | :------------ |
| Byte                 | BytePlus      |
| Short                | ShortPlus     |
| Integer              | IntegerPlus   |
| Long                 | LongPlus      |
| Float                | FloatPlus     |
| Double               | DoublePlus    |
| Character            | CharacterPlus |
| Object               | ObjectPlus    |

除此之外,针对String类型,提供了StringPlus,但是由于String类型天然是集合,所以在确定Argument的volume字段时,你需要为它分配一个长度

2.   关于ValidatorConfig类

你需要通过ValidatorConfig类的对象为此次对数器验证提供必要的参数.包含以下字段:

-   int times - 验证次数
-   Class<?> clazz - 测试方法类所在类的类对象
-   String validatorMethod - 对数器方法
-   String compareMethod - 对照方法
-   boolean Comparison - 比较方式(true - 比较‘’地址“和”值“” false 只比较“值”  -- 可选项 默认为false)

需要注意的是，这个类在实例化后，不希望被任何实体操作，并且是只读的 !请不要尝试对这个类的对象进行操作

3.   关于参数Sample类

内部会通过ValidatorConfig对象传入的算法所在类文件的类对象,和方法名找到主要测试方法和对照方法,(下面简称为validator方法和compare方法),并且通过Argument对象数组,获取到方法参数的数据约束,从而递归对参数进行解析,并创建出随机测试样本,这组测试样本包括:

-   Object[] validatorSample 对数器方法测试样本
-   Object[] cloneSample       对数器方法测试样本的拷贝样本(由于内部封装了“地址”,你可以认为这两组样本完全相等)
-   String errorStr                存留样本,在测试失败后作为原始样本进行展示

4.   ReflectiveInvoker类

ReflectiveInvoker类对反射API进行了封装,为在内部获取方法的各种信息提供支持

通过3我们就可以获取到测试样本,然后通过ReReflectiveInvoker类的call方法就可以调用到Validator Method 和Compare Method并且获取到两个方法计算的输出,之后会将两个方法的输出交给compareResultSet方法,它会按照用户指定的方式(“address and values” or only “values”),比较结果集

-   如果测试失败(结果集不相等),会给出测试失败的样本

-   测试成功后(结果集相等),会给出两个算法的耗时情况

由于内部只是针对算法的输出进行值和“地址”的比较,所以测试之前你需要考虑你所编写的算法输出是否是幂等的(典型的是排序算法,如果进行地址比较,那么如歌一个方法具有稳定性,一个方法不具有稳定性那么也会测试失败)



那么,ACV的大致流程就介绍完成了,接下来介绍的是一些不足的地方

5.   算法的输入

由于内部是针对参数类型全解析(可以解析包含泛型,数组,和Plus类型),所以在递归函数的控制上比较的困难,因此,如果相对参数进行限制就更加的困难,你可以对参数作出的限制

-   是否有序 你可以在构造Argument时显示指定isOrder为true,那么内部会尽可能让这组样本有序,但是由于参数可能出现嵌套,例如,`LinkedList<ArrayList<IntegerPlus[]>>`,因此,如果你需要有序的样本输入,那么尽可能保证你的方法参数不要进行嵌套(建议使用ArrayList 或 一维数组)

-   指定特殊值 如果你对输入的参数要求里面出现特定值,或者特定范围的值,你可以使用Fate类(Range 的成员),在构造Argument时进行指定,同样的由于递归控制难度较大,并未对所有参数支持Fate,如果你想使用这个功能,请使用Collection接口下的集合
-   特殊的,由于内部是通过反射拿到参数信息,所以你的参数不能出现接口,请全部使用其实现类,否则无法完成测试样本的创建

6.   算法的输出

由于算法的输出普遍比较单一,所以针对输出,要求不能是Map接口下的双值类

7.   对于参数的解析(rsc方法),目前是支持了对所有参数(参数中不能出现通配符`?`,类型变量`TypeVariable`,因为这两种参数无法反射创建实例),所以你可以对其进行测试,不保证完全正确,目前仅进行了部分单一的测试

8.   补充:关于Argument类

Argument类用于抽象参数,一个Argument对象表示一个参数,如果你有n个参数,那么你需要给激活函数`new Active().active(validatorConfig,args1,args2...)`传入n个Argument对象,表示n个参数的数据约束,Argument类有五个成员

-   Class<?> clazz 参数的类对象 不需要你显示指定,内部会通过反射获取
-   String typeStr 参数类对象的全类名 同样,内部会通过反射获取
-   final boolean isOrder 参数是否要求有序
-   final Range[] volume 数据量的范围
-   final Range[] values   数据值的范围

# 关于数据量和数据范围的指定

你可以在构造时通过Argument构造函数进行调整数据量`Range[][] volume`和数据范围`Range[][] values`

在指定volume时,你需要注意的是,最后位用于站位,可以随意指定

在指定values时,你需要注意的是,第一位用于站位,你可以随意指定

volume和values的占位不可省略,不可省略,不可省略!!! 否则会出现数组越界异常

例如对`LinkedList<ArrayList<IntegerPlus[]>>`的volume和values进行指定

```java
package controller;

import model.config.ValidatorConfig;
import model.range.Range;
import model.sample.Argument;
import model.type.IntegerPlus;

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


```

Validator 样本

<img width="1388" alt="image" src="https://github.com/ZhcLan/Algorithm-Correctness-Validator/assets/115857104/bf73da5a-2b16-43f6-ad47-34082bcc0bd0">


# 如何使用

1.   下载并导入jar包
2.   创建与算法匹配的ValidatorConfig和Argument对象
3.   使用active激活对数器

# 下载Jar包

[Validator_5.26](https://github.com/ZhcLan/Algorithm-Correctness-Validator/releases/tag/validator)

