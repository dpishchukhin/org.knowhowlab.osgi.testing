package org.knowhowlab.osgi.testing.it.paxexam;

import org.junit.Test;
import org.ops4j.pax.exam.Configuration;
import org.ops4j.pax.exam.Option;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static org.knowhowlab.osgi.testing.assertions.OSGiAssert.getBundleContext;
import static org.knowhowlab.osgi.testing.assertions.cmpn.ConfigurationAdminAssert.*;
import static org.knowhowlab.osgi.testing.utils.cmpn.ConfigurationAdminUtils.deleteConfiguration;
import static org.knowhowlab.osgi.testing.utils.cmpn.ConfigurationAdminUtils.supplyConfiguration;
import static org.ops4j.pax.exam.CoreOptions.mavenBundle;
import static org.ops4j.pax.exam.CoreOptions.vmOptions;
import static org.ops4j.pax.exam.OptionUtils.combine;

/**
 * EventAdmin tests
 *
 * @author dpishchukhin
 */
public class NewIn_1_2_CA_IntegrationTest extends AbstractTest {
    /**
     * Install test bundle, ConfigurationAdmin and EventAdmin
     *
     * @return config
     */
    @Configuration
    public static Option[] customTestConfiguration() {
        return combine(baseConfiguration(),
                mavenBundle("org.apache.felix", "org.apache.felix.eventadmin", "1.3.2"),
                mavenBundle("org.apache.felix", "org.apache.felix.configadmin", "1.6.0"),
                mavenBundle("org.knowhowlab.osgi", "org.knowhowlab.osgi.testing.it.test.bundle", System.getProperty("project.version")).noUpdate().noStart());
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
