package isoslug;

import com.pholser.junit.quickcheck.Property;
import com.pholser.junit.quickcheck.runner.JUnitQuickcheck;
import org.junit.runner.RunWith;

import static isoslug.IsoSlug.decode;
import static isoslug.IsoSlug.encode;
import static org.junit.Assert.*;

@RunWith(JUnitQuickcheck.class)
public class IsoSlugTest {
    @Property(trials = 1000) public void symmetry(String original) {
        assertEquals(original, decode(encode(original)));
    }
}
