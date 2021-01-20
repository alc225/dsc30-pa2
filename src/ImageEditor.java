/*
    Name: Alvin Chung
    PID:  A14839442
 */

/**
 * This class represents an Image Editing service that lets you change
 * each pixel. The image is represented by a 2D int array.There are several
 * options for each pixel: scale, assign, and delete. There is also the
 * option to undo and redo changes using the IntStack.
 *
 * @author Alvin Chung
 * @since  01/19/21
 */
public class ImageEditor {

    /* static constants */
    private static final int MAX_PIXEL_VALUE      = 255;
    private static final int STACKS_INIT_CAPACITY = 30;

    /* instance variables */
    private int[][] image;
    private IntStack undo;
    private IntStack redo;

    /**
     * Constructor that takes in a 2D int array of an image,
     * and initializes it as well as the undo and redo stack.
     * @param image a 2D int array of the pixels in an image
     * @throws IllegalArgumentException if the rows are not
     * all the same length, or the number of rows or columns
     * is zero.
     */
    ImageEditor(int[][] image) {
        if (image.length == 0 || image[0].length == 0) {
            throw new IllegalArgumentException();
        }
        for (int i = 0; i < image.length; i++) {
            if (image[i].length != image[0].length) {
                throw new IllegalArgumentException();
            }
        }
        this.image = image;
        this.undo = new IntStack(STACKS_INIT_CAPACITY);
        this.redo = new IntStack(STACKS_INIT_CAPACITY);
    }

    /**
     * Returns the image
     * @return a 2D array of the image
     */
    int[][] getImage() {
        return this.image;
    }

    /**
     * Multiplies the current pixel at image[i][j] by the
     * given scaleFactor. If this amount exceeds the max
     * pixel value of 255, the value is set to 255.
     * @param i the row number (int)
     * @param j the column number (int)
     * @param scaleFactor a double of which to scale the pixel by.
     * @throws IndexOutOfBoundsException if the row or column is
     * out of range.
     * @throws IllegalArgumentException if the scaleFactor is negative
     */
    public void scale(int i, int j, double scaleFactor) {
        if (i > image.length || i < 0) {
            throw new IndexOutOfBoundsException();
        }
        if (j > image[0].length || j < 0) {
            throw new IndexOutOfBoundsException();
        }
        if (scaleFactor < 0) {
            throw new IllegalArgumentException();
        }
        redo.clear();
        undo.push(i);
        undo.push(j);
        undo.push(image[i][j]);
        if (image[i][j] * scaleFactor > MAX_PIXEL_VALUE) {
            image[i][j] = MAX_PIXEL_VALUE;
        }
        else {
            image[i][j] = (int) (image[i][j] * scaleFactor);
        }
    }

    /**
     * Changes a pixel at image[i][j] to the given int color.
     * @param i the row number (int)
     * @param j the column number(int)
     * @param color the new color (int)
     * @throws IndexOutOfBoundsException if the row or column
     * are out of bounds.
     * @throws IllegalArgumentException if the color is not in
     * the pixel range (0-255).
     */
    public void assign(int i, int j, int color) {
        if (i > image.length || i < 0) {
            throw new IndexOutOfBoundsException();
        }
        if (j > image[0].length || j < 0) {
            throw new IndexOutOfBoundsException();
        }
        if (color < 0 || color > MAX_PIXEL_VALUE) {
            throw new IllegalArgumentException();
        }
        redo.clear();
        undo.push(i);
        undo.push(j);
        undo.push(image[i][j]);
        image[i][j] = color;
    }

    /**
     * Removes the color at the given row and column by
     * setting it to 0.
     *
     * @param i the row number (int)
     * @param j the height number (int)
     * @throws IndexOutOfBoundsException if the row or column
     * are out of bounds.
     */
    public void delete(int i, int j) {
        if (i > image.length || i < 0) {
            throw new IndexOutOfBoundsException();
        }
        if (j > image[0].length || j < 0) {
            throw new IndexOutOfBoundsException();
        }
        redo.clear();
        undo.push(i);
        undo.push(j);
        undo.push(image[i][j]);
        image[i][j] = 0;
    }

    /**
     * Undoes the most recent change. Returns false if there is
     * no operation to undo, and true if it was able to undo the
     * last operation.
     * @return a boolean indicating success
     */
    public boolean undo() {
        if (undo.isEmpty()) {
            return false;
        }
        int original_Value = undo.pop();
        int width = undo.pop();
        int height = undo.pop();
        redo.push(height);
        redo.push(width);
        redo.push(image[height][width]);
        image[height][width] = original_Value;
        return true;
    }

    /**
     * Redoes the next operation (undo the undo). Returns false
     * if there is nothing to redo, and true if it was able to
     * redo the last operation.
     * @return a boolean indicating success
     */
    public boolean redo() {
        if (redo.isEmpty()) {
            return false;
        }
        int original_Value = redo.pop();
        int width = redo.pop();
        int height = redo.pop();
        undo.push(height);
        undo.push(width);
        undo.push(image[height][width]);
        image[height][width] = original_Value;
        return true;
    }
}
