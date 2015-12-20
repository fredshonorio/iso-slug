package isoslug;

import static isoslug.Tup2.tup2;
import static isoslug.Util.iterate;
import static java.nio.charset.StandardCharsets.UTF_8;
import static java.util.stream.Collectors.joining;
import static javax.xml.bind.DatatypeConverter.parseHexBinary;
import static javax.xml.bind.DatatypeConverter.printHexBinary;

public class IsoSlug {

    private static final int DOT = (int) '.';
    private static final int UNDER = (int) '_';
    private static final int DASH = (int) '-';
    private static final int A = (int) 'A';
    private static final int a = (int) 'a';
    private static final int Z = (int) 'Z';
    private static final int z = (int) 'z';

    /**
     * Checks if a codepoint is a plain url-safe character.
     */
    static boolean isPlain(int code) {
        return (code >= a && code <= z)
                || (code >= A && code <= Z)
                || code == DOT
                || code == UNDER;
    }

    /**
     * Returns a String containing the character with the given codepoint.
     */
    static String stringOfCode(int code) {
        return new String(Character.toChars(code));
    }

    /**
     * Returns a string with the hex representation of the UTF-8 byte sequence of a unicode codepoint.
     */
    static String codepointHex(int code) {
        byte[] utf8bytes = stringOfCode(code).getBytes(UTF_8);
        return printHexBinary(utf8bytes).toLowerCase();
    }

    /**
     * Encodes a codepoint in plain characters.
     */
    static String encodeChar(int code) {
        return isPlain(code) ? stringOfCode(code)
                : code == DASH ? "--"
                : "-" + codepointHex(code) + "-";
    }

    /**
     * Decodes an encoded part.
     *
     * @return The encoded character, in a string.
     */
    public static String decodePart(String part) {
        return part.isEmpty() ? "-"
                : new String(parseHexBinary(part), UTF_8);
    }

    /**
     * Replaces a single encoded part with it's decoded form.
     * Accepts a pivot that marks where the search should begin.
     *
     * @param args A tuple of the string and the pivot
     * @return A tuple of the string with a single value replaced, and the pivot, advanced to find the next part.
     */
    public static Tup2<String, Integer> replaceSinglePart(Tup2<String, Integer> args) {

        String current = args._1;
        int pivot = args._2;

        // find the first and second dashes after the pivot
        int f = current.indexOf("-", pivot);
        int s = current.indexOf("-", f + 1);

        // split string, decode encoded part
        String left = current.substring(0, f);
        String encoded = current.substring(f + 1, s);
        String right = current.substring(s + 1);

        String decoded = decodePart(encoded);

        // return new string with decoded part, advance pivot to after the first dash in the old string
        return tup2(left + decoded + right, f + 1);
    }

    /**
     * Decodes a slug
     *
     * @return The decoded original value.
     */
    public static String decode(String slug) {
        // count the number of encoded parts
        long dashes = slug.codePoints().filter(c -> c == DASH).count();
        int parts = (int) dashes / 2;
        // apply function as many times as there are parts, starting at the beginning of the string
        return iterate(IsoSlug::replaceSinglePart, parts).apply(tup2(slug, 0))._1;
    }

    /**
     * Encodes a string in a slug
     *
     * @return The encoded slug.
     */
    public static String encode(String string) {
        return string.codePoints()
                .sequential()
                .mapToObj(IsoSlug::encodeChar)
                .collect(joining());
    }
}
