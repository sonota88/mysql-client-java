package util;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class UtilsTest {

    private static final String BS = "\\";
    private static final String DQ = "\"";

    @Test
    public void escape_basic(){
        assertThat(
                Utils.escape("\\ \" \b \f \n \r \t"),
                is(BS + BS
                        + " " + BS + DQ
                        + " " + BS + "b"
                        + " " + BS + "f"
                        + " " + BS + "n"
                        + " " + BS + "r"
                        + " " + BS + "t"
                        )
                );
    }

    @Test
    public void escape_multi_occurence(){
        assertThat(
                Utils.escape("\n\n"),
                is(
                        BS + "n"
                        + BS + "n"
                        )
                );
    }

    @Test
    public void escape_bs_normal_char(){
        assertThat(
                Utils.escape(BS + "n"),
                is(
                        BS + BS
                        + "n"
                        )
                );
    }

}
