package isoslug;

import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.function.UnaryOperator;
import java.util.stream.IntStream;

public class Util {

    /**
     * Returns a function that applies a given function n times.
     * For example:
     * <pre>
     * iterate(f, 1)(x) = f(x)
     * iterate(f, 2)(x) = f(f(x))
     * iterate(f, 3)(x) = f(f(f(x)))
     * </pre>
     *
     * @param f The function to apply
     * @param n The number of times to apply the function.
     * @return A function that applies {@code f}, {@code n} times.
     */
    public static <T> UnaryOperator<T> iterate(final UnaryOperator<T> f, final int n) {
        final BiFunction<T, Integer, T> apply = (t, ign) -> f.apply(t);
        final BinaryOperator<T> last = (a, b) -> b;
        return val -> IntStream.range(0, n).boxed().reduce(val, apply, last);
    }
}
