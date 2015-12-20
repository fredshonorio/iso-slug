package isoslug;

/**
 * A size 2 tuple.
 */
public class Tup2<A, B> {
    public final A _1;
    public final B _2;

    private Tup2(A a, B b) {
        _1 = a;
        _2 = b;
    }

    public static <A, B> Tup2<A, B> tup2(A a, B b) {
        return new Tup2<>(a, b);
    }
}
