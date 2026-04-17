package edu.miu.cs.cs489appsd.lab11.arrays;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class ArrayFlattenerTestCases {

    private final ArrayFlattener arrayFlattener = new ArrayFlattener();

    @Test
    void flattenArrayWhenInputIsValidNestedArrayReturnsFlattenedArray() {
        int[][] input = {{1, 3}, {0}, {4, 5, 9}};

        int[] actual = arrayFlattener.flattenArray(input);

        assertArrayEquals(new int[]{1, 3, 0, 4, 5, 9}, actual);
    }

    @Test
    void flattenArrayWhenInputIsNullReturnsNull() {
        int[] actual = arrayFlattener.flattenArray(null);

        assertNull(actual);
    }
}
