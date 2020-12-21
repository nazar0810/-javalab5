package com.kpi.dependencyinjection;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.inject.Singleton;

import static org.assertj.core.api.Assertions.assertThat;


class InterfaceTest {

    interface A {
    }

    interface B {
    }

    public static class FirstExample implements A {
    }

    public static class SecondExample implements A, B {
    }

    @Singleton interface CheckIfSingleton {
    }

    public static class NonSingleton implements CheckIfSingleton {
    }

    private DILib DILib;

    @BeforeEach
    void setUp() {
        DILib = new DILib();
    }

    @Test
    void shouldBind() {
        DILib.bindInterface(A.class, FirstExample.class);

        final A instance = DILib.getInstance(A.class);
        assertThat(instance).isNotNull().isInstanceOf(FirstExample.class);
    }

    @Test
    void shouldOverrideLastBinding() {
        DILib.bindInterface(A.class, FirstExample.class);
        DILib.bindInterface(A.class, SecondExample.class);

        final A instance = DILib.getInstance(A.class);

        assertThat(instance).isNotNull().isInstanceOf(SecondExample.class);
    }

    @Test
    void singletonInterfacesShouldNotBeSame() {
        DILib.bindInterface(CheckIfSingleton.class, NonSingleton.class);

        final CheckIfSingleton firstInstance = DILib.getInstance(CheckIfSingleton.class);
        final CheckIfSingleton secondInstance = DILib.getInstance(CheckIfSingleton.class);

        assertThat(firstInstance).isNotSameAs(secondInstance);
    }
}
