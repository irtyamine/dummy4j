package dev.codesoapbox.dummy4j;

import dev.codesoapbox.dummy4j.dummies.Dummies;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static java.util.Collections.singletonList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class Dummy4jConstructionTest {

    @Test
    void shouldConstructWithDefaultValues() {
        Dummy4j dummy = new Dummy4j();
        assertNotNull(dummy.dummies);
        assertNotNull(dummy.expressionResolver);
        assertNotNull(dummy.randomService);
        assertEquals(dummy.randomService, dummy.expressionResolver.randomService);
    }

    @Test
    void shouldConstructWithCustomSeedAndLocales() {
        List<String> locales = singletonList("en");
        long seed = 1234L;
        Dummy4j dummy = new Dummy4j(seed, locales);
        assertEquals(locales, dummy.expressionResolver.locales);
        assertEquals(seed, dummy.randomService.getSeed());
    }

    @Test
    void shouldConstructWithSeedAndDefaultLocales() {
        long seed = 1234L;
        Dummy4j dummy = new Dummy4j(seed);
        assertEquals(singletonList("en"), dummy.expressionResolver.locales);
        assertEquals(seed, dummy.randomService.getSeed());
    }

    @Test
    void shouldConstructWithDefaultSeedAndCustomLocales() {
        List<String> locales = singletonList("en");
        Dummy4j dummy = new Dummy4j(locales);
        assertEquals(locales, dummy.expressionResolver.locales);
    }

    @Test
    void shouldConstructWithCustomDependencies() {
        ExpressionResolver expressionResolver = mock(ExpressionResolver.class);
        RandomService randomService = mock(RandomService.class);
        Dummies dummies = mock(Dummies.class);

        Dummy4j dummy = new Dummy4j(expressionResolver, randomService, d -> dummies);

        assertEquals(expressionResolver, dummy.expressionResolver);
        assertEquals(randomService, dummy.randomService);
        assertEquals(dummies, dummy.dummies);
    }

}