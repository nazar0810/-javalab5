package com.kpi.dependencyinjection;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class BindTest {

    private static class CustomInterfaceExample implements MyCustomInterface {
    }

    interface MyCustomInterface {
    }

    static abstract class AbstractExample {
    }

    private DILib DILib;
    private CustomInterfaceExample interfaceExample;
    private AbstractExample abstractExample;

    @BeforeEach
    void setUp() {
        DILib = new DILib();
        interfaceExample = new CustomInterfaceExample();
        abstractExample = new AbstractExample(){};
    }

    @Test
    void instancesFromInterfaceImplementationShouldBeSame() {
        DILib.bindInstance(CustomInterfaceExample.class, interfaceExample);

        final CustomInterfaceExample instance = DILib.getInstance(CustomInterfaceExample.class);

        assertThat(instance).isSameAs(interfaceExample);
    }

    @Test
    void instancesInterfaceShouldBeSame() {
        DILib.bindInstance(MyCustomInterface.class, interfaceExample);

        final MyCustomInterface instance = DILib.getInstance(MyCustomInterface.class);

        assertThat(instance).isSameAs(interfaceExample);
    }

    @Test
    void instancesFromAbstractClassShouldBeSame() {
        DILib.bindInstance(AbstractExample.class, abstractExample);

        final AbstractExample instance = DILib.getInstance(AbstractExample.class);

        assertThat(instance).isSameAs(abstractExample);
    }
}
