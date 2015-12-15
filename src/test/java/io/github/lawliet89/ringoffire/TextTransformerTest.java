package io.github.lawliet89.ringoffire;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.xd.dirt.server.singlenode.SingleNodeApplication;
import org.springframework.xd.dirt.test.SingleNodeIntegrationTestSupport;
import org.springframework.xd.dirt.test.SingletonModuleRegistry;
import org.springframework.xd.dirt.test.process.SingleNodeProcessingChain;
import org.springframework.xd.module.ModuleType;
import org.springframework.xd.test.RandomConfigurationSupport;

import static org.junit.Assert.assertEquals;
import static org.springframework.xd.dirt.test.process.SingleNodeProcessingChainSupport.chain;

public class TextTransformerTest {
    private static SingleNodeApplication application;

    private static int RECEIVE_TIMEOUT = 5000;

    private static String moduleName = "text-transformer";

    /**
     * Start the single node container, binding random unused ports, etc. to not conflict with any other instances
     * running on this host. Configure the ModuleRegistry to include the project module.
     */
    @BeforeClass
    public static void setUp() {
        application = new SingleNodeApplication().run();
        SingleNodeIntegrationTestSupport singleNodeIntegrationTestSupport = new SingleNodeIntegrationTestSupport
                (application);
        singleNodeIntegrationTestSupport.addModuleRegistry(new SingletonModuleRegistry(ModuleType.processor,
                moduleName));

    }

    /**
     * This test creates a stream with the module under test, or in general a "chain" of processors. The
     * SingleNodeProcessingChain is a test fixture that allows the test to send and receive messages to verify each
     * message is processed as expected.
     */
    @Test
    public void test() {
        String streamName = "ring-of-fire-stream";
        String payload = "lorem ipsum";

        String processingChainUnderTest = moduleName;

        SingleNodeProcessingChain chain = chain(application, streamName, processingChainUnderTest);

        chain.sendPayload(payload);

        String result = (String) chain.receivePayload(RECEIVE_TIMEOUT);

        assertEquals("lorem ipsum foobar", result);

        //Unbind the source and sink channels from the message bus
        chain.destroy();
    }
}