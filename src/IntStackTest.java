import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class IntStackTest {
    IntStack test1, test2, test3, test4;
    @Before
    public void setUp() throws Exception {
        test1 = new IntStack(6, 0.8, 0.33);
        test2 = new IntStack(5, 0.7);
        test3 = new IntStack(10);
        test4 = new IntStack(5);

    }

    @org.junit.Test
    public void testConstructors() {
        try {
            IntStack with_shrink1 = new IntStack(4,0.7, 0.1);
            fail("Exception capacity not thrown");
        }
        catch (Exception e) {
            System.out.println(e);
        }
        try {
            IntStack with_shrink2 = new IntStack(5, 1.2, 0);
            fail("Exception loadFactor not thrown");
        }
        catch (Exception e) {
            System.out.println(e);
        }
        try {
            IntStack with_shrink3 = new IntStack(5, 0.7, -1);
            fail("Exception shrinkFactor not thrown");
        } catch (Exception e) {
            System.out.println(e);
        }
        try {
            IntStack with_load1 = new IntStack(10, -1);
            fail("Exception loadFactor not thrown");
        } catch (Exception e) {
            System.out.println(e);
        }
        try {
            IntStack with_load2 = new IntStack(4, 1);
            fail("Exception capacity not thrown");
        } catch (Exception e) {
            System.out.println(e);
        }
        try {
            IntStack with_cap1 = new IntStack(3);
            fail("Exception capacity not thrown");
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @Test
    public void push() {
        test1.push(1);
        assertEquals(1, test1.peek());
        test2.push(5);
        test2.push(6);
        test2.push(7);
        test2.push(8);
        test2.push(9);
        assertEquals(test2.peek(), 9);
        assertEquals(10, test2.capacity());

    }

    @org.junit.Test
    public void pop() {
        test1.push(1);
        assertEquals(test1.pop(), 1);
        try {
            test3.pop();
            fail("EmptyStackException not thrown");
        } catch (Exception e) {
            System.out.println(e);
        }
        test2.push(5);
        test2.push(6);
        test2.push(7);
        test2.push(8);
        test2.push(9);
        assertEquals(test2.capacity(), 10);
        assertEquals(test2.pop(), 9);
        test2.pop();
        test2.pop();
        assertEquals(test2.capacity(), 5);
    }

    @org.junit.Test
    public void isEmpty() {
        test2.push(1);
        assertEquals(false, test2.isEmpty());
        assertEquals(true, test1.isEmpty());
        assertEquals(true, test4.isEmpty());

    }

    @org.junit.Test
    public void clear() {
        test2.push(5);
        test2.push(6);
        test2.push(7);
        test2.push(8);
        test2.push(9);
        assertEquals(test2.capacity(), 10);
        test2.clear();
        assertEquals(test2.capacity(), 5);
        assertTrue(test2.isEmpty());
        test3.push(1);
        test3.clear();
        assertTrue(test3.isEmpty());
        assertEquals(test3.size(), 0);
    }

    @org.junit.Test
    public void size() {
        test2.push(1);
        assertEquals(test2.size(), 1);
        test2.push(3);
        test2.push(1);
        assertEquals(test2.size(), 3);
        test2.pop();
        assertEquals(test2.size(), 2);
    }

    @org.junit.Test
    public void capacity() {
        assertEquals(test2.capacity(), 5);
        test2.push(5);
        test2.push(6);
        test2.push(7);
        test2.push(8);
        test2.push(9);
        assertEquals(test2.capacity(), 10);
        test2.pop();
        test2.pop();
        test2.pop();
        assertEquals(test2.capacity(), 5);
        assertEquals(test3.capacity(), 10);


    }

    @org.junit.Test
    public void peek() {
        test4.push(3);
        assertEquals(test4.peek(), 3);
        test4.pop();
        try {
            test4.peek();
            fail("EmptyStackException not thrown");
        } catch (Exception e) {
            System.out.println(e);
        }
        test2.push(20);
        assertEquals(test2.peek(), 20);
    }


    @org.junit.Test
    public void multiPush() {
        test2.multiPush(new int[]{1,2,3,4,5});
        assertEquals(test2.peek(), 5);
        assertEquals(test2.capacity(), 10);
        test1.multiPush(new int[]{3,4});
        assertEquals(test1.peek(), 4);
        try {
            test3.multiPush(null);
            fail("IllegalArgumentException not Thrown");
        } catch (Exception e) {
            System.out.println(e);
        }

    }

    @org.junit.Test
    public void multiPop() {
        test2.multiPush(new int[]{1,2,3,4,5});
        test2.multiPop(3);
        assertEquals(test2.capacity(), 5);
        assertEquals(test2.peek(), 2);
        test2.multiPop(2);
        assertTrue(test2.isEmpty());
        try {
            test2.multiPop(-1);
            fail("IllegalArgumentException not Thrown");
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}