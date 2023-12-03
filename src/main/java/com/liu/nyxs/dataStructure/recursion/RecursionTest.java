package com.liu.nyxs.dataStructure.recursion;


/**
 * 二分查找，冒泡排序 -》递归
 */

public class RecursionTest {

    public static int search(int[] a, int target){
        return f(a, target, 0, a.length);

    }


    private static int f(int[] a, int target, int i, int j){
        int m = (i + j) >>> 1;
        if (target < a[m]){
            return f(a, target, i, m - 1);

        } else if (target > a[m]){
            return f(a, target, m - 1, j);

        } else {
            return m;

        }

    }

    public static void main(String[] args) {
        /*int[] a = {1, 4, 9, 22, 61, 77, 88, 93};
        System.out.println(search(a, 88));*/

        int[] a = {1, 4, 99, 2, 61, 77, 88, 93};
        int x = 0;
        bubble(a, a.length - 1);
        for (int i : a){
            System.out.println(i);

        }


    }


    /**
     * 递归版冒泡排序
     * 此处做了一个优化就是引入了中间值x， 只要i和i+1的值有需要移动位置的， 那么就会把i赋值给x
     * 这样做的作用是， x后面的都不需要排序（即正序）， 那么递归范围就能每次由原来的[0, j - 1]缩短到[0, x]
     * @param arr 需要排序的数组
     * @param j   arr.length - 1
     */
    private static void bubble(int[] arr, int j){
        if (j == 0){
            return;

        }
        int x = 0;
        for (int i= 0; i < j; i++){
            if (arr[i] > arr[i + 1]){
                int temp = arr[i];
                arr[i] = arr[i + 1];
                arr[i + 1] = temp;
                x = i;

            }
        }
        bubble(arr, x);

    }


}
