package loki2302;

/**
 * Represents a calculator.
 */
public class Calculator {
    private final Adder adder;
    private final Subtractor subtractor;

    /**
     * Construct a new {@link Calculator} instance using the supplied {@link Adder}
     * @param adder object of type {@link Adder} that will be used for addition
     * @param subtractor object of type {@link Subtractor} that will be used for subtraction
     */
    public Calculator(
            Adder adder,
            Subtractor subtractor) {

        this.adder = adder;
        this.subtractor = subtractor;
    }

    /**
     * Calculates a sum of 2 integers
     * @param a first integer
     * @param b second integers
     * @return the sum of a and b
     */
    public int addNumbers(int a, int b) {
        return adder.add(a, b);
    }

    /**
     * Calculates a difference of 2 integers
     * @param a first integer
     * @param b second integers
     * @return the difference of a and b
     */
    public int subtractNumbers(int a, int b) {
        return subtractor.subtract(a, b);
    }

}
