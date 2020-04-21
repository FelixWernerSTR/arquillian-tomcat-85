package de.fewe.arquillian;

import org.junit.Assert;
import org.junit.Test;

public class DemoLibTest {

    @Test
    public void stupidNumberUnitTest() {
        final DemoLib demoLib = new DemoLib();
        Assert.assertEquals("MUST be zero initially",demoLib.getNumAndIncrement(), 0);
        Assert.assertEquals("MUST be one by now",demoLib.getNumAndIncrement(), 1);
    }
}
