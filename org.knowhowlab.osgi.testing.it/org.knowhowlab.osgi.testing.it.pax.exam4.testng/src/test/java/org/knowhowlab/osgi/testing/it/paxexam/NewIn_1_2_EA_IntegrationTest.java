package org.knowhowlab.osgi.testing.it.paxexam;

import org.knowhowlab.osgi.testing.utils.FilterUtils;
import org.ops4j.pax.exam.Configuration;
import org.ops4j.pax.exam.Option;
import org.osgi.framework.InvalidSyntaxException;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static org.knowhowlab.osgi.testing.assertions.OSGiAssert.getBundleContext;
import static org.knowhowlab.osgi.testing.assertions.cmpn.EventAdminAssert.assertEvent;
import static org.knowhowlab.osgi.testing.utils.BundleUtils.startBundleAsync;
import static org.knowhowlab.osgi.testing.utils.cmpn.EventAdminUtils.postEvent;
import static org.ops4j.pax.exam.CoreOptions.mavenBundle;
import static org.ops4j.pax.exam.OptionUtils.combine;

/**
 * EventAdmin tests
 *
 * @author dpishchukhin
 */
public class NewIn_1_2_EA_IntegrationTest extends AbstractTest {
    private static final String KNOWHOWLAB_TOPICS_TEST = "knowhowlab/topics/test";

    /**
     * Install test bundle and EventAdmin
     *
     * @return config
     */
    @Configuration
    public static Option[] customTestConfiguration() {
        return combine(baseConfiguration(),
                mavenBundle("org.apache.felix", "org.apache.felix.eventadmin", "1.3.2"),
                mavenBundle("org.knowhowlab.osgi", "org.knowhowlab.osgi.testing.it.test.bundle", System.getProperty("project.version")).noUpdate().noStart());
    }

    @Test
    public void test_Post_Event() {
        postEvent(getBundleContext(), KNOWHOWLAB_TOPICS_TEST, 200);

        assertEvent(KNOWHOWLAB_TOPICS_TEST, 500, TimeUnit.MILLISECONDS);
    }

    @Test
    public void test_Post_Event_With_Filters() throws InvalidSyntaxException {
        Map<String, String> props = new HashMap<String, String>();
        props.put("prop_key", "val123");

        postEvent(getBundleContext(), KNOWHOWLAB_TOPICS_TEST, props, 200);
        assertEvent(KNOWHOWLAB_TOPICS_TEST, FilterUtils.eq("prop_key", "val123"), 500, TimeUnit.MILLISECONDS);

        postEvent(getBundleContext(), KNOWHOWLAB_TOPICS_TEST, props, 200);
        try {
            assertEvent(KNOWHOWLAB_TOPICS_TEST, FilterUtils.eq("prop_key", "val555"), 500, TimeUnit.MILLISECONDS);
        } catch (AssertionError e) {
        }
    }

    @Test
    public void test_Service_Event() throws InvalidSyntaxException {
        // start bundle in 1 sec
        startBundleAsync(getBundleContext(), "org.knowhowlab.osgi.testing.it.test.bundle", 1000);

        assertEvent("org/osgi/framework/ServiceEvent/REGISTERED", FilterUtils.eq("service.objectClass", "org.knowhowlab.osgi.testing.it.testbundle.service.Echo"), 2, TimeUnit.SECONDS);
    }
}
