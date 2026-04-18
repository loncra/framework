package io.github.loncra.framework.spring.web.test.device;

import io.github.loncra.framework.spring.web.device.DeviceUtils;
import nl.basjes.parse.useragent.UserAgent;
import org.junit.jupiter.api.Test;

public class DeviceUtilsTest {

    @Test
    public void testGetDevice() {
        UserAgent agent = DeviceUtils.getDevice("Mozilla/5.0 (Windows; U; Windows NT 5.1; en-US; rv:1.8.1.11) Gecko/20071127 Firefox/2.0.0.11");
        for (String fieldName : agent.getAvailableFieldNamesSorted()) {
            System.out.println(fieldName + " = " + agent.getValue(fieldName));
        }
    }
}
