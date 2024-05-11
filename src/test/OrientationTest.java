package test;

import org.junit.Test;
import orientation.Orientation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

public class OrientationTest {

    @Test
    public void testTurnRight() {
        // Test de rotation à droite depuis chaque orientation
        assertEquals(Orientation.E, Orientation.N.turnRight());
        assertEquals(Orientation.S, Orientation.E.turnRight());
        assertEquals(Orientation.W, Orientation.S.turnRight());
        assertEquals(Orientation.N, Orientation.W.turnRight());
    }

    @Test
    public void testTurnLeft() {
        // Test de rotation à gauche depuis chaque orientation
        assertEquals(Orientation.W, Orientation.N.turnLeft());
        assertEquals(Orientation.N, Orientation.E.turnLeft());
        assertEquals(Orientation.E, Orientation.S.turnLeft());
        assertEquals(Orientation.S, Orientation.W.turnLeft());
    }

    @Test
    public void testValueOfWithInvalidInput() {
        assertThrows(IllegalArgumentException.class, () -> Orientation.valueOf("InvalidOrientation"));
    }
}
