/*
    Name: Alvin Chung
    PID:  A14839442
 */

import java.util.EmptyStackException;
import java.util.Locale;

/**
 * A class that contains a method that can decompose a normal
 * arithmetic expression using a variation of IntStack that is
 * used exclusively for char.
 *
 * @author Alvin Chung
 * @since  01/19/21
 */
public class ExprDecomposer {

    private static final int STACKS_INIT_CAPACITY = 30;

    /**
     * This method takes in a string that represents a normal
     * arithmetic expression. It then converts it into a special
     * expression that has no parenthesis, and have the operators
     * in different locations.
     * @param expr The input string that is to be converted
     * @return the converted string as a char array.
     */
    public char[] decompose(String expr) {
        String output = "";
        String clean_output = expr.replaceAll("\\s", "");
        CharStack tokens = new CharStack(STACKS_INIT_CAPACITY);
        for (int i = 0; i < clean_output.length(); i++) {
            char nextChar = clean_output.charAt(i);
            if (isOperator(nextChar)) {
                tokens.push(nextChar);
            }
            else if (nextChar == ')') {
                char tempChar = tokens.pop();
                while (tempChar != '(') {
                    output += tempChar;
                    tempChar = tokens.pop();
                }
            }
            else if (nextChar == '(') {
                tokens.push(nextChar);
            }
            else {
                output += nextChar;
            }
        }
        for (int i = 0; i < tokens.size(); i++) {
            output += tokens.pop();
        }
        return output.toCharArray();
    }

    /**
     * UTILITY METHOD, DO NOT MODIFY *
     * Check if the given token represents a digit
     *
     * @param token to check
     * @return boolean true if token is a digit, false otherwise
     */
    private boolean isDigit(char token) {
        return (token >= '0') && (token <= '9');
    }

    /**
     * UTILITY METHOD, DO NOT MODIFY *
     * Check if the given token represents an operator
     *
     * @param token to check
     * @return boolean true if token is an operator, false otherwise
     */
    private boolean isOperator(char token) {
        return (token == '+') || (token == '-') || (token == '*') || (token == '/');
    }

    /**
     * A variation of IntStack used for char instead. It instead contains
     * a char array for its data, and uses some of the methods created
     * in CharStack.
     */
    protected class CharStack {

        private char[] data;
        private int nElems;
        private double loadFactor;
        private double shrinkFactor;
        private int init_Capacity;
        private static final double init_loadF = 0.75;
        private static final double init_shrinkF = 0.25;
        private static final int min_Capacity = 5;
        private static final double min_loadF = 0.67;
        private static final double max_shrinkF = 0.33;


        /**
         * A constructor that takes in capacity, loadF, and shrinkF.
         * @param capacity a max capacity of elements in the stack
         * @param loadF a loadFactor
         * @param shrinkF a shrink Factor
         * @throws IllegalArgumentException if capacity, loadF, or shrinkF
         * are out of range.
         */
        public CharStack(int capacity, double loadF, double shrinkF) {
            if (capacity < min_Capacity) {
                throw new IllegalArgumentException();
            }
            if (loadF < min_loadF || loadF > 1) {
                throw new IllegalArgumentException();
            }
            if (shrinkF < 0 || shrinkF > max_shrinkF) {
                throw new IllegalArgumentException();
            }
            this.data = new char[capacity];
            loadFactor = loadF;
            shrinkFactor = shrinkF;
            this.init_Capacity = capacity;
        }

        /**
         * A constructor that uses the default values for the loadFactor
         * and shrinkFactor.
         * @param capacity the max amount of elements in the stack
         * @throws IllegalArgumentException if capacity is out of range.
         */
        public CharStack(int capacity) {
            this(capacity, init_loadF, init_shrinkF);

        }

        /**
         * Checks to see if the stack is empty.
         * @return true if the stack is empty, false otherwise.
         */
        public boolean isEmpty() {
            if (nElems == 0) {
                return true;
            }
            return false;
        }

        /**
         * Returns the current amount of elements in the stack.
         * @return an int amount
         */
        public int size() {
            return nElems;
        }

        /**
         * Returns the max amount of elements in the stack
         * @return an int capacity
         */
        public int capacity() {
            return data.length;
        }

        /**
         * Returns the top element on the stack.
         * @return the char element
         * @throws EmptyStackException if the stack is empty.
         */
        public char peek() {
            if (isEmpty()) {
                throw new EmptyStackException();
            }
            return data[nElems - 1];
        }

        /**
         * Adds the element to the stack.
         * @param element the char to be added
         */
        public void push(char element) {
            if ((double) nElems / capacity() >= loadFactor) {
                int double_multiplier = 2;
                char[] double_Capacity = new char[capacity() * double_multiplier];
                for (int i = 0; i < capacity(); i++) {
                    double_Capacity[i] = data[i];
                }
                data = double_Capacity;
            }
            data[nElems] = element;
            nElems++;
        }

        /**
         * Removes the top element of the stack.
         * @return The removed char element.
         * @throws EmptyStackException if the stack is empty
         */
        public char pop() {
            char output = peek();
            data[nElems - 1] = 0;
            nElems--;
            if ((double) nElems / capacity() <= shrinkFactor) {
                char[] half_Capacity;
                int half_divisor = 2;
                if (capacity() / half_divisor <= init_Capacity) {
                    half_Capacity = new char[init_Capacity];
                } else {
                    half_Capacity = new char[capacity() / half_divisor];
                }
                for (int i = 0; i < nElems; i++) {
                    half_Capacity[i] = data[i];
                }
                data = half_Capacity;
            }
            return output;
        }

    }
}
