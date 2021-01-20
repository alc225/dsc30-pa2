/*
    Name: Alvin Chung
    PID:  A14839442
 */

import java.util.EmptyStackException;

/**
 * This class is designed to create a stack that handles strictly ints.This
 * class is later used in ImageEditor, and a charStack version is created
 * in ExprDecomposer. This class has many of the same features as a typical
 * stack, such as push, pop, peek, etc.
 *
 * @author Alvin Chung
 * @since  01/19/21
 */
public class IntStack {

    /* instance variables */
    private int[] data;
    private int nElems;
    private double loadFactor;
    private double shrinkFactor;
    private int init_Capacity;
    private static final int min_Capacity = 5;
    private static final double min_loadF = 0.67;
    private static final double max_shrinkF = 0.33;
    private static final double init_loadF = 0.75;
    private static final double init_shrinkF = 0.25;

    /**
     * Constructor takes in capacity, load factor, and shrink factor and
     * initializes it.
     *
     * @param capacity An int that is used for max amount of items (aka data size)
     * @param loadF A double that represents load factor
     * @param shrinkF A double that represents shrink factor
     * @throws IllegalArgumentException if capacity, loadF, or shrinkF are not within the range.
     */
    public IntStack(int capacity, double loadF, double shrinkF) {
        if (capacity < min_Capacity) {
            throw new IllegalArgumentException();
        }
        if (loadF < min_loadF || loadF > 1) {
            throw new IllegalArgumentException();
        }
        if (shrinkF < 0 || shrinkF > max_shrinkF) {
            throw new IllegalArgumentException();
        }
        this.data = new int[capacity];
        loadFactor = loadF;
        shrinkFactor = shrinkF;
        this.init_Capacity = capacity;
    }

    /**
     * A constructor that only takes in capacity and loadF. shrinkF
     * is initialized at 0.25.
     * @param capacity An int that represents max capacity
     * @param loadF A double that represents load factor
     * @throws IllegalArgumentException if capacity and loadF are out of range
     */
    public IntStack(int capacity, double loadF) {
        this(capacity, loadF, init_shrinkF);
    }

    /**
     * A constructor that only takes in capacity. shrinkF is initialized
     * at 0.25, while loadF is initialized at 0.75.
     * @param capacity An int that represents max capacity
     * @throws IllegalArgumentException if capacity is out of range
     */
    public IntStack(int capacity) {
        this(capacity, init_loadF, init_shrinkF);
    }

    /**
     * Returns true if the boolean is empty, false otherwise.
     *
     * @return a boolean that's true if the boolean is empty, false otherwise.
     */
    public boolean isEmpty() {
        if (nElems == 0) {
            return true;
        }
        return false;
    }

    /**
     * Clears the stack and resets to the initial capacity.
     */
    public void clear() {
        data = new int[init_Capacity];
        nElems = 0;
    }

    /**
     * Returns the number of elements currently in the stack.
     *
     * @return an int of the number of elements
     */
    public int size() {
        return nElems;
    }

    /**
     * Returns the max amount of elements that can be stored in the stack
     * @return an int of the max amount
     */
    public int capacity() {
        return data.length;
    }

    /**
     * Returns the most recently added element
     * @return the top element (int)
     * @throws EmptyStackException if the stack is empty
     */
    public int peek() {
        if (isEmpty()) {
            throw new EmptyStackException();
        }
        return data[nElems - 1];
    }

    /**
     * Adds an element to the top of the stack. If the size/capacity is
     * greater than or equal to load factor, the capacity will double before
     * adding the element.
     * @param element An int to be added to the stack
     */
    public void push(int element) {
        if ((double) nElems / capacity() >= loadFactor) {
            int double_multiplier = 2;
            int[] double_Capacity = new int[capacity() * double_multiplier];
            for (int i = 0; i < capacity(); i++) {
                double_Capacity[i] = data[i];
            }
            data = double_Capacity;
        }
        data[nElems] = element;
        nElems++;
    }

    /**
     * Removes the top element of the stack and returns it. If size/capacity
     * is less than or equal to shrinkFactor, the capacity is halved. However,
     * the capacity will never drop below the initial capacity.
     * @return the removed element (int)
     * @throws EmptyStackException if the stack is empty
     */
    public int pop() {
        int output = peek();
        data[nElems - 1] = 0;
        nElems--;
        if ((double) nElems / capacity() <= shrinkFactor) {
            int[] half_Capacity;
            int half_divisor = 2;
            if (capacity() / half_divisor <= init_Capacity) {
                half_Capacity = new int[init_Capacity];
            }
            else {
                half_Capacity = new int[capacity() / half_divisor];
            }
            for (int i = 0; i < nElems; i++) {
                half_Capacity[i] = data[i];
            }
            data = half_Capacity;
        }
        return output;
    }

    /**
     * Pushes multiple elements in the input array
     * to the stack starting from index 0.
     * @param elements an int array of elements to be added
     * @throws IllegalArgumentException if elements is null
     */
    public void multiPush(int[] elements) {
        if (elements == null) {
            throw new IllegalArgumentException();
        }
        for (int i = 0; i < elements.length; i++) {
            push(elements[i]);
        }
    }

    /**
     * Removes and returns given amount of elements from the stack in
     * an int array.
     * @param amount An int of how many elements are to be removed.
     * @return an int array of removed elements
     * @throws IllegalArgumentException if amount is negative
     */
    public int[] multiPop(int amount) {
        int[] output;
        if (amount < 0) {
            throw new IllegalArgumentException();
        }
        if (amount > size()) {
            output = new int[size()];
            for (int i = 0; i < size(); i++) {
                output[i] = pop();
            }
        }
        else {
            output = new int[amount];
            for (int i = 0; i < amount; i++) {
                output[i] = pop();
            }
        }
        return output;
    }
}
