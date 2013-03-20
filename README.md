## Introduction

OSGi specific assertions and utility classes that help to write OSGi integration/system tests.

### Blog with more samples and tutorials

[http://knowhowlab.org](http://knowhowlab.org)

### Mailing List

[http://groups.google.com/group/knowhowlab-osgi-testing](http://groups.google.com/group/knowhowlab-osgi-testing)

## Usage

### Add Maven dependencies:
    <dependency>
        <groupId>org.knowhowlab.osgi</groupId>
        <artifactId>org.knowhowlab.osgi.testing.utils</artifactId>
        <version>1.2.0</version>
    </dependency>
    <dependency>
        <groupId>org.knowhowlab.osgi</groupId>
        <artifactId>org.knowhowlab.osgi.testing.assertions</artifactId>
        <version>1.2.0</version>
    </dependency>

### Add dependency in PaxExam tests
    mavenBundle().groupId("org.knowhowlab.osgi").artifactId("org.knowhowlab.osgi.testing.utils").version("1.2.0"),
    mavenBundle().groupId("org.knowhowlab.osgi").artifactId("org.knowhowlab.osgi.testing.assertions").version("1.2.0")

## Changes

### 1.2.0 (20 Mar 2013)

- Added EventAdmin utils and assertions
- Added ConfigurationAdmin utils and assertions
- Added PaxExam 3.0.x integration tests
- bug fixes and improvements

#### Samples

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
        // start bundle in 2 sec
        startBundleAsync(getBundleContext(), "org.knowhowlab.osgi.testing.it.test.bundle", 200);

        assertEvent("org/osgi/framework/ServiceEvent/REGISTERED", FilterUtils.eq("service.objectClass", "org.knowhowlab.osgi.testing.it.testbundle.service.Echo"), 1, TimeUnit.SECONDS);
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

### 1.1.0 (15 Dec 2012)

- Added support of Bundle Events, Service Events and Framework Events
- Added support of asynchronous change of Bundle and Service states

#### Samples

    ...
    // start bundle in 2 sec
    startBundleAsync(getBundleContext(), "org.knowhowlab.osgi.testing.it.test.bundle", 2, TimeUnit.SECONDS);
    // bundle is still stopped
    assertBundleState(Bundle.RESOLVED | Bundle.INSTALLED, "org.knowhowlab.osgi.testing.it.test.bundle", 
                      1L, TimeUnit.MILLISECONDS);
    // bundle is active after 5 sec
    assertBundleState(Bundle.ACTIVE, "org.knowhowlab.osgi.testing.it.test.bundle", 5, TimeUnit.SECONDS);
    ...
    // start bundle in 2 sec
    startBundleAsync(getBundleContext(), "org.knowhowlab.osgi.testing.it.test.bundle", 2, TimeUnit.SECONDS);
    // expect bundle event within 5 sec
    assertBundleEvent(BundleEvent.STARTED, "org.knowhowlab.osgi.testing.it.test.bundle", 5, TimeUnit.SECONDS);
    ...
    // register service in 2 sec
    registerServiceAsync(getBundleContext(), TestService.class, new TestService(), null, 2, TimeUnit.SECONDS);
    // service registered within 5 sec
    assertServiceEvent(ServiceEvent.REGISTERED, TestService.class, 5, TimeUnit.SECONDS);
    ...
    // change start level
    Executors.newSingleThreadScheduledExecutor().schedule(new Runnable() {
        public void run() {
            StartLevel startLevel = ServiceUtils.getService(getBundleContext(), StartLevel.class);
            startLevel.setStartLevel(10);
        }
    }, 2, TimeUnit.SECONDS);
    // start level changed within 5 sec
    assertFrameworkEvent(FrameworkEvent.STARTLEVEL_CHANGED, 0, 5, TimeUnit.SECONDS);


## There is a comparison of the same test with and without OSGiLab testing assertions and utils.

### With

    @Test
    public void test_With_OSGiAssertions() throws BundleException, InvalidSyntaxException {
        // asserts that test bundle is installed
        assertBundleAvailable("org.osgilab.testing.it.commons.test.bundle");
        // asserts that test bundle is resolved
        assertBundleState(Bundle.RESOLVED, "org.osgilab.testing.it.commons.test.bundle");
        // gets bundle instance
        Bundle bundle = findBundle(getBundleContext(), "org.osgilab.testing.it.commons.test.bundle");
        // asserts that test service is unavailable
        assertServiceUnavailable("org.osgi.testing.it.commons.testbundle.service.Echo");
        // start bundle
        bundle.start();
        // asserts that test bundle is active
        assertBundleState(Bundle.ACTIVE, "org.osgilab.testing.it.commons.test.bundle");
        // asserts that test service is available within 2 seconds
        assertServiceAvailable("org.osgi.testing.it.commons.testbundle.service.Echo", 2, TimeUnit.SECONDS);
        // asserts that test service with custom properties is available
        assertServiceAvailable(and(create(Echo.class), eq("testkey", "testvalue")));
        // gets service by class and filter
        Echo echo = ServiceUtils.getService(getBundleContext(), Echo.class, eq("testkey", "testvalue"));
        // asserts service method call
        Assert.assertEquals("test", echo.echo("test"));
        // stops bundle
        bundle.stop();
        // asserts that test bundle is resolved
        assertBundleState(Bundle.RESOLVED, "org.osgilab.testing.it.commons.test.bundle");
        // asserts that test service is unregistered
        assertServiceUnavailable(Echo.class);
    }

### Without:

    @Test
    public void test_Without_OSGiAssertions() throws BundleException, InterruptedException, InvalidSyntaxException {
        ServiceTracker packageAdminTracker = new ServiceTracker(bc, PackageAdmin.class.getName(), null);
        packageAdminTracker.open();
        PackageAdmin packageAdmin = (PackageAdmin) packageAdminTracker.getService();
        Assert.assertNotNull(packageAdmin);
        packageAdminTracker.close();
        Bundle[] bundles = packageAdmin.getBundles("org.osgilab.testing.it.commons.test.bundle", null);
        // asserts that test bundle is installed
        Assert.assertNotNull(bundles);
        Assert.assertTrue(bundles.length > 0);
        // gets bundle instance
        Bundle bundle = bundles[0];
        // asserts that test bundle is resolved
        Assert.assertEquals(Bundle.RESOLVED, bundle.getState());
        ServiceTracker serviceTracker1 = new ServiceTracker(bc, "org.osgi.testing.it.commons.testbundle.service.Echo", null);
        serviceTracker1.open();
        Assert.assertEquals(0, serviceTracker1.size());
        // start bundle
        bundle.start();
        // asserts that test bundle is active
        Assert.assertEquals(Bundle.ACTIVE, bundle.getState());
        // asserts that test service is available within 2 seconds
        Assert.assertNotNull(serviceTracker1.waitForService(2000));
        // asserts that test service with custom properties is available
        ServiceTracker serviceTracker2 = new ServiceTracker(bc, FrameworkUtil.createFilter(
                "(&(" + Constants.OBJECTCLASS + "=org.osgi.testing.it.commons.testbundle.service.Echo)" +
                        "(testkey=testvalue))"), null);
        serviceTracker2.open();
        Assert.assertTrue(serviceTracker2.size() > 0);
        // gets service by class and filter
        Echo echo = (Echo) serviceTracker2.getService();
        // asserts service method call
        Assert.assertEquals("test", echo.echo("test"));
        // stops bundle
        bundle.stop();
        // asserts that test bundle is resolved
        Assert.assertEquals(Bundle.RESOLVED, bundle.getState());
        // asserts that test service is unregistered
        Assert.assertEquals(0, serviceTracker1.size());
    }


As you can see with OSGi assertions and utils you can concentrate on your functionality testing without any low-level OSGi API calls.
