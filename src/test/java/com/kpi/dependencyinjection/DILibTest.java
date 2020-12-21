package com.kpi.dependencyinjection;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.inject.Singleton;

import static org.assertj.core.api.Assertions.assertThat;


class InstanceProviderTest {

    public interface InstanceProvider {
        <T> T getInstance(Class<T> type);
    }

    public static class Example {
    }

    @Singleton
    public static class SingletonExample {
    }

    private DILib DILib;

    @BeforeEach
    void setUp() {
        DILib = new DILib();
    }

    @Test
    void instanceShouldNotBeNull() {
        DILib.bindProvider(InstanceProvider.class, () -> DILib::getInstance);
        final InstanceProvider provider = DILib.getInstance(InstanceProvider.class);

        final Example example = provider.getInstance(Example.class);

        assertThat(example).isNotNull();
    }

    @Test
    void singletonsShouldBeSame() {
        DILib.bindProvider(InstanceProvider.class, () -> DILib::getInstance);
        final InstanceProvider provider = DILib.getInstance(InstanceProvider.class);

        final SingletonExample singleton1 = provider.getInstance(SingletonExample.class);
        final SingletonExample singleton2 = provider.getInstance(SingletonExample.class);
        final SingletonExample singleton3 = DILib.getInstance(SingletonExample.class);

        assertThat(singleton1).isSameAs(singleton2);
        assertThat(singleton2).isSameAs(singleton3);
    }
}
