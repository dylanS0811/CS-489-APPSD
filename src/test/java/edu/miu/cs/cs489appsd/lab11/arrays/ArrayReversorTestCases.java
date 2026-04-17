package edu.miu.cs.cs489appsd.lab11.arrays;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ArrayReversorTestCases {

    @Test
    void reverseArrayWhenInputIsValidNestedArrayReturnsReversedFlattenedArray() {
        int[][] input = {{1, 3}, {0}, {4, 5, 9}};
        ArrayFlattenerService arrayFlattenerService = mock(ArrayFlattenerService.class);
        when(arrayFlattenerService.flattenArray(input))
                .thenReturn(new int[]{1, 3, 0, 4, 5, 9});
        ArrayReversor arrayReversor = new ArrayReversor(arrayFlattenerService);

        int[] actual = arrayReversor.reverseArray(input);

        assertArrayEquals(new int[]{9, 5, 4, 0, 3, 1}, actual);
        verify(arrayFlattenerService).flattenArray(input);
    }

    @Test
    void reverseArrayWhenInputIsNullReturnsNull() {
        ArrayFlattenerService arrayFlattenerService = mock(ArrayFlattenerService.class);
        when(arrayFlattenerService.flattenArray(null)).thenReturn(null);
        ArrayReversor arrayReversor = new ArrayReversor(arrayFlattenerService);

        int[] actual = arrayReversor.reverseArray(null);

        assertNull(actual);
        verify(arrayFlattenerService).flattenArray(null);
    }
}
