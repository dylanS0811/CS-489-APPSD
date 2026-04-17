package edu.miu.cs.cs489appsd.lab11.arrays;

import java.util.Arrays;

public class ArrayReversor {

    private final ArrayFlattenerService arrayFlattenerService;

    public ArrayReversor(ArrayFlattenerService arrayFlattenerService) {
        this.arrayFlattenerService = arrayFlattenerService;
    }

    public int[] reverseArray(int[][] nestedArray) {
        int[] flattenedArray = arrayFlattenerService.flattenArray(nestedArray);
        if (flattenedArray == null) {
            return null;
        }

        int[] reversedArray = new int[flattenedArray.length];
        for (int i = 0; i < flattenedArray.length; i++) {
            reversedArray[i] = flattenedArray[flattenedArray.length - 1 - i];
        }
        return reversedArray;
    }

    public static void main(String[] args) {
        int[][] input = {{1, 3}, {0}, {4, 5, 9}};
        ArrayReversor arrayReversor = new ArrayReversor(new ArrayFlattener());
        System.out.println(Arrays.toString(arrayReversor.reverseArray(input)));
    }
}
