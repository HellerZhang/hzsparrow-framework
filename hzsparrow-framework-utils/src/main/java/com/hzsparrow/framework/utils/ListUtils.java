package com.hzsparrow.framework.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * List工具类
 *
 * @author HellerZhang
 * @since 2019年5月20日 下午6:46:41
 */
public class ListUtils {

    /**
     * 取差集 取list1有，list2中没有的值
     *
     * @param list1 list1
     * @param list2 list2
     * @param <T>   类型
     * @return 差集
     * @author HellerZhang
     * @since 2019年5月21日 上午8:53:07
     */
    public static <T> List<T> differenceSet(List<T> list1, List<T> list2) {
        // 结果List
        List<T> rsList = new ArrayList<T>();

        // 是否存在，默认为false不存在
        boolean isHaveInList2 = false;

        for (T t : list1) {
            if (rsList.contains(t)) {
                continue;
            }
            isHaveInList2 = false;
            for (T t2 : list2) {
                if (t.equals(t2)) {
                    isHaveInList2 = true;
                    break;
                }
            }
            if (!isHaveInList2) {
                rsList.add(t);
            }
        }
        return rsList;
    }

    /**
     * 取两个数组的交集
     *
     * @param list1 list1
     * @param list2 list2
     * @param <T>   类型
     * @return 交集
     * @author Heller.Zhang
     * @since 2019年6月18日 下午7:37:45
     */
    public static <T> List<T> intersection(List<T> list1, List<T> list2) {
        // 结果List
        List<T> rsList = new ArrayList<T>();

        for (T list1Entity : list1) {
            boolean isInterSection = false;
            for (T list2Entity : list2) {
                if (list1Entity.equals(list2Entity)) {
                    isInterSection = true;
                    break;
                }
            }
            if (isInterSection) {
                rsList.add(list1Entity);
            }
        }

        return rsList;
    }

    /**
     * 通过Stream取list<strong>交集</strong>
     *
     * @param list1 list1
     * @param list2 list2
     * @param <T>   类型
     * @return 交集
     * @author HellerZhang
     * @since 2019年7月5日 下午5:43:01
     */
    public static <T> List<T> intersectionStream(List<T> list1, List<T> list2) {
        return list1.stream().filter(item -> list2.contains(item)).collect(Collectors.toList());
    }

    /**
     * 通过Stream取list<strong>差集</strong>
     *
     * @param list1 list1
     * @param list2 list2
     * @param <T>   类型
     * @return 差集
     * @author HellerZhang
     * @since 2019年7月5日 下午5:51:14
     */
    public static <T> List<T> reduce(List<T> list1, List<T> list2) {
        return list1.stream().filter(item -> !list2.contains(item)).collect(Collectors.toList());
    }

    /**
     * 通过Stream取list<strong>并集</strong>
     *
     * @param list1 list1
     * @param list2 list2
     * @param <T>   类型
     * @return 并集
     * @author HellerZhang
     * @since 2019年7月5日 下午5:52:50
     */
    public static <T> List<T> listAll(List<T> list1, List<T> list2) {
        list1.addAll(list2);
        return list1;
    }

    /**
     * 通过Stream取list<strong>去重并集</strong>
     *
     * @param list1 list1
     * @param list2 list2
     * @param <T>   类型
     * @return 去重并集
     * @author HellerZhang
     * @since 2019年7月5日 下午5:53:09
     */
    public static <T> List<T> listAllDistinct(List<T> list1, List<T> list2) {
        return listAll(list1, list2).stream().distinct().collect(Collectors.toList());
    }

    public static void main(String[] args) {
        List<Integer> list1 = new ArrayList<Integer>();
        list1.add(1);
        list1.add(2);
        List<Integer> list2 = new ArrayList<Integer>();
        list2.add(2);
        list2.add(3);
        List<Integer> list = ListUtils.listAll(list1, list2);
        System.out.println(list);
    }

}
