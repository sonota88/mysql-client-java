package sample;

import java.util.Properties;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import util.Utils;

public class MainTest {

    Main sut;
    
    @Before
    public void setup() {
        sut = new Main();
    }

    @Ignore
    @Test
    public void main(){
        Properties props = Utils.readProps("/path/to/config.properties");

        sut.main(
                props,
                "select * from foo limit 3"
                );
    }

}
