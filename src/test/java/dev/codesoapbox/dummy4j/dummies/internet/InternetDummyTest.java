package dev.codesoapbox.dummy4j.dummies.internet;

import dev.codesoapbox.dummy4j.Dummy4j;
import dev.codesoapbox.dummy4j.ExpressionResolver;
import dev.codesoapbox.dummy4j.dummies.LoremDummy;
import dev.codesoapbox.dummy4j.dummies.internet.InternetDummy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.net.MalformedURLException;
import java.net.URL;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class InternetDummyTest {

    @Mock
    private Dummy4j dummy4j;

    @Mock
    private LoremDummy loremDummy;

    @Mock
    private ExpressionResolver expressionResolver;

    private InternetDummy internetDummy;

    @BeforeEach
    void setUp() {
        internetDummy = new InternetDummy(dummy4j);
    }

    @Test
    void shouldReturnSimpleUrl() throws MalformedURLException {
        URL expected = new URL("http", "test.dev", "");
        mockSimpleUrl();

        URL actual = internetDummy.url()
                .build();

        assertAll(
                () -> assertEquals(expected, actual),
                () -> assertEquals("http://test.dev", actual.toString()),
                () -> assertNotNull(actual.toURI())
        );
    }

    private void mockSimpleUrl() {
        mockExpressionResolver();
        when(expressionResolver.resolveKey("internet.protocol"))
                .thenReturn("http");
        when(expressionResolver.resolve("#{internet.domain}"))
                .thenReturn("dev");
        when(dummy4j.lorem())
                .thenReturn(loremDummy);
        when(loremDummy.word())
                .thenReturn("test");
    }

    private void mockExpressionResolver() {
        when(dummy4j.expressionResolver())
                .thenReturn(expressionResolver);
    }
}