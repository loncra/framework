package io.github.loncra.framework.wechat;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class OfficialPropertiesTest {

    @Test
    void officialAccountExtensionDisabledByDefault() {
        assertThat(new OfficialProperties().isEnabled()).isFalse();
    }
}
