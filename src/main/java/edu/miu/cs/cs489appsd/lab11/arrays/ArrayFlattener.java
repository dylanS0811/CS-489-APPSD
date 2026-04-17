package edu.miu.cs.cs489appsd.lab11.arrays;

import java.util.Arrays;
import java.util.Objects;

public class ArrayFlattener implements ArrayFlattenerService {

    @Override
    public int[] flattenArray(int[][] nestedArray) {
        if (nestedArray == null) {
            return null;
        }

        return Arrays.stream(nestedArray)
                .filter(Objects::nonNull)
                .flatMapToInt(Arrays::stream)
                .toArray();
    }

    public static void main(String[] args) {
        int[][] input = {{1, 3}, {0}, {4, 5, 9}};
        System.out.println(Arrays.toString(new ArrayFlattener().flattenArray(input)));
    }
}
