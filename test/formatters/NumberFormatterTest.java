package formatters;

import org.junit.Test;

import static formatters.NumberFormatter.format;
import static org.fest.assertions.Assertions.assertThat;

public class NumberFormatterTest {

    @Test
    public void should_format() {
        assertThat(format(1)).isEqualTo("1");
        assertThat(format(10)).isEqualTo("10");
        assertThat(format(100)).isEqualTo("100");
        assertThat(format(1000)).isEqualTo("1 000");
        assertThat(format(1000000)).isEqualTo("1 000 000");
    }

}
