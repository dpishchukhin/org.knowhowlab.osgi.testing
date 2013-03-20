package org.knowhowlab.osgi.testing.it.paxexam;

import org.junit.Test;
import org.ops4j.pax.exam.Option;
import org.ops4j.pax.exam.junit.Configuration;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static org.knowhowlab.osgi.testing.assertions.OSGiAssert.getBundleContext;
import static org.knowhowlab.osgi.testing.assertions.cmpn.ConfigurationAdminAssert.*;
import static org.knowhowlab.osgi.testing.utils.cmpn.ConfigurationAdminUtils.deleteConfiguration;
import static org.knowhowlab.osgi.testing.utils.cmpn.ConfigurationAdminUtils.supplyConfiguration;
import static org.ops4j.pax.exam.CoreOptions.mavenBundle;
import static org.ops4j.pax.exam.OptionUtils.combine;

/**
 * EventAdmin tests
 *
 * @author dpishchukhin
 */
public class NewIn_1_2_CA_IntegrationTest extends AbstractTest {
    private static final String KNOWHOWLAB_TOPICS_TEST = "knowhowlab/topics/test";

    /**
     * Install test bundle, ConfigurationAdmin and EventAdmin
     *
     * @return config
     */
    @Configuration
    public static Option[] customTestConfiguration() {
        return combine(baseConfiguration(),
                mavenBundle().groupId("org.apache.felix").artifactId("org.apache.felix.eventadmin").version("1.3.2"),
                mavenBundle().groupId("org.apache.felix").artifactId("org.apache.felix.configadmin").version("1.6.0"),
                mavenBundle().groupId("org.knowhowlab.osgi").artifactId("org.knowhowlab.osgi.testing.it.test.bundle").
                        version(System.getProperty("project.version")).noUpdate().noStart());
    }

    @Test
    public void test_Configuration_availability() {
        assertConfigurationUnavailable("test.pid", null, null);

        Map<String, String> config = new HashMap<String, String>();
        config.put("test.key", "test.value");
        supplyConfiguration(getBundleContext(), "test.pid", null, config, 0);

        assertConfigurationUpdated("test.pid", null, null, 500, TimeUnit.MILLISECONDS);

        assertConfigurationAvailable("test.pid", null, null);
    }

    @Test
    public void test_Configuration_manipulations() {
        assertConfigurationUnavailable("test.pid", null, null);

        Map<String, String> config = new HashMap<String, String>();
        config.put("test.key", "test.value");
        supplyConfiguration(getBundleContext(), "test.pid", null, config, 0);

        assertConfigurationUpdated("test.pid", null, null, 500, TimeUnit.MILLISECONDS);

        assertConfigurationAvailable("test.pid", null, null);

        deleteConfiguration(getBundleContext(), "test.pid", null, 0);

        assertConfigurationDeleted("test.pid", null, null, 500, TimeUnit.MILLISECONDS);

        assertConfigurationUnavailable("test.pid", null, null);
    }
}
