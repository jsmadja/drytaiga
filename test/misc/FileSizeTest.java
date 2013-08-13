package misc;

import org.junit.Test;

import static org.fest.assertions.Assertions.assertThat;

public class FileSizeTest {

    @Test
    public void should_convert_in_Mo() {
        assertThat(new FileSize(17846380L).toString()).isEqualTo("17.0 Mo");
        assertThat(new FileSize(17946380L).toString()).isEqualTo("17.1 Mo");
    }

    @Test
    public void should_convert_in_Ko() {
        assertThat(new FileSize(352990L).toString()).isEqualTo("344.7 Ko");
    }

}
