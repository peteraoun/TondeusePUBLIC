package lawnmower;

import org.junit.Test;
import org.mockito.Mockito;
import org.slf4j.Logger;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class LawnMowerProcessTest {

    @Test
    public void testProcess_OneMower_Success() {
        // Arrange
        List<String> input = Arrays.asList("5", "5", "1", "2", "N", "LMLMLMLMM");
        LawnMowerProcessor processor = new LawnMowerProcessor();

        // Act
        List<LawnMower> result = processor.process(input);

        // Assert
        assert result != null;
        assertEquals(1, result.size());
        // Add more assertions to validate the result
    }

    @Test
    public void testProcess_MultipleMowers_Success() {
        // Arrange
        List<String> input = Arrays.asList("5", "5", "1", "2", "N", "LMLMLMLMM", "3", "3", "E", "MMRMMRMRRM");
        LawnMowerProcessor processor = new LawnMowerProcessor();

        // Act
        List<LawnMower> result = processor.process(input);

        // Assert
        assert result != null;
        assertEquals(2, result.size());
        // Add more assertions to validate the result
    }

    @Test
    public void testProcess_InvalidLawnSize_Failure() {
        // Arrange
        List<String> input = Arrays.asList("0", "5", "1", "2", "N", "LMLMLMLMM");
        LawnMowerProcessor processor = new LawnMowerProcessor();

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> processor.process(input));
    }

    @Test
    public void testProcess_InvalidMowerPosition_Failure() {
        // Arrange
        List<String> input = Arrays.asList("5", "5", "6", "2", "N", "LMLMLMLMM");
        LawnMowerProcessor processor = new LawnMowerProcessor();

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> processor.process(input));
    }

    @Test
    public void testProcess_MissingData_Info() {
        // Arrange
        Logger logger = Mockito.mock(Logger.class);
        List<String> input = Arrays.asList("5", "5", "1", "2", "N");
        LawnMowerProcessor processor = new LawnMowerProcessor();
        processor.setLogger(logger);

        // Act
        processor.process(input);

        // Assert
        verify(logger, times(1)).info("One or more mower data are missing!");
    }
}
