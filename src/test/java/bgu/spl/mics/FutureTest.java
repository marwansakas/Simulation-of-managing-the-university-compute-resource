package bgu.spl.mics;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

class FutureTest {
    private static Future<String> future;

    @BeforeEach
    void setUp() {
        future = new Future<>();
    }
    @Test
    void resolve() {
        assertFalse(future.isDone());
        future.resolve("done");
        assertTrue(future.isDone());
    }

    @Test
    void get() {
        future.resolve("done");
        assertEquals("done", future.get());
    }

    @Test
    void testGet() {
        assertNull(future.get(0, TimeUnit.MILLISECONDS));
        future.resolve("done");
        assertEquals("done", future.get(5,TimeUnit.MILLISECONDS));
    }
}