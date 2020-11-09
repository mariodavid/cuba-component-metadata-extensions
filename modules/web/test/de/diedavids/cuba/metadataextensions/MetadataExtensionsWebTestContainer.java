package de.diedavids.cuba.metadataextensions;

import com.haulmont.cuba.web.testsupport.TestContainer;

import java.util.ArrayList;
import java.util.Arrays;

public class MetadataExtensionsWebTestContainer extends TestContainer {

    public MetadataExtensionsWebTestContainer() {
        appComponents = new ArrayList<>(Arrays.asList(
                "com.haulmont.cuba"
                // add CUBA add-ons and custom app components here
        ));
        appPropertiesFiles = Arrays.asList(
                // List the files defined in your web.xml
                // in appPropertiesConfig context parameter of the web module
                "de/diedavids/cuba/metadataextensions/web-app.properties",
                // Add this file which is located in CUBA and defines some properties
                // specifically for test environment. You can replace it with your own
                // or add another one in the end.
                "com/haulmont/cuba/web/testsupport/test-web-app.properties",
                "de/diedavids/cuba/metadataextensions/test-web-app.properties"

        );
    }

    public static class Common extends MetadataExtensionsWebTestContainer {

        // A common singleton instance of the test container which is initialized once for all tests
        public static final MetadataExtensionsWebTestContainer.Common INSTANCE = new MetadataExtensionsWebTestContainer.Common();

        private static volatile boolean initialized;

        private Common() {
        }

        @Override
        public void before() throws Throwable {
            if (!initialized) {
                super.before();
                initialized = true;
            }
            setupContext();
        }

        @Override
        public void after() {
            cleanupContext();
            // never stops - do not call super
        }
    }
}