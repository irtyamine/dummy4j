package dev.codesoapbox.dummy4j.dummies.internet;

import dev.codesoapbox.dummy4j.Dummy4j;
import dev.codesoapbox.dummy4j.ExpressionResolver;
import dev.codesoapbox.dummy4j.RandomService;
import dev.codesoapbox.dummy4j.dummies.LoremDummy;
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
class UrlBuilderTest {

    private static final String TEN_CHARS = "TENcharsss";
    private static final String SIXTY_CHARS = "SIXTYcharslkjhgfdsazqwertyuioplkjhgfdsazqwertyuioplkjhgfdsaz";

    @Mock
    Dummy4j dummy4j;

    @Mock
    private LoremDummy loremDummy;

    @Mock
    private ExpressionResolver expressionResolver;

    @Mock
    private RandomService randomService;

    private UrlBuilder builder;

    @BeforeEach
    void setUp() {
        builder = new UrlBuilder(dummy4j);
    }

    @Test
    void shouldReturnSimpleUrl() throws MalformedURLException {
        URL expected = new URL("http", "test.dev", "");
        mockSimpleUrl();

        URL actual = builder.build();

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

    @Test
    void shouldReturnUrlWithFile() throws MalformedURLException {
        URL expected = new URL("http", "test.dev", "/" + TEN_CHARS + ".html");
        mockSimpleUrl();
        when(loremDummy.characters(10))
                .thenReturn(TEN_CHARS);

        URL actual = builder
                .withFilePath()
                .build();

        assertEquals(expected, actual);
    }

    @Test
    void shouldReturnUrlWithFileAndMinLength() throws MalformedURLException {
        URL expected = new URL("http", "test.dev", "/" + TEN_CHARS + SIXTY_CHARS + ".html");
        mockSimpleUrl();
        when(loremDummy.characters(60))
                .thenReturn(SIXTY_CHARS);
        when(loremDummy.characters(10))
                .thenReturn(TEN_CHARS);

        URL actual = builder
                .withFilePath()
                .minLength(80)
                .build();

        assertEquals(expected, actual);
    }

    @Test
    void shouldReturnUrlWithWordsInQueryParams() throws MalformedURLException {
        URL expected = new URL("http", "test.dev", "?test=test");
        mockSimpleUrl();
        mockRandomService();
        when(randomService.nextInt(6))
                .thenReturn(4);

        URL actual = builder
                .withQueryParams()
                .build();

        assertEquals(expected, actual);
    }

    @Test
    void shouldReturnUrlWithDigitsInQueryParams() throws MalformedURLException {
        URL expected = new URL("http", "test.dev", "?test=123");
        mockSimpleUrl();
        mockRandomService();
        when(randomService.nextInt(6))
                .thenReturn(5);
        when(randomService.nextInt())
                .thenReturn(123);

        URL actual = builder
                .withQueryParams()
                .build();

        assertEquals(expected, actual);
    }

    private void mockRandomService() {
        when(dummy4j.random())
                .thenReturn(randomService);
    }

    @Test
    void shouldReturnUrlWithMultipleQueryParams() throws MalformedURLException {
        URL expected = new URL("http", "test.dev", "?test=test&test=test");
        mockSimpleUrl();
        mockRandomService();
        when(randomService.nextInt(6))
                .thenReturn(4);

        URL actual = builder
                .withQueryParams(2)
                .build();

        assertEquals(expected, actual);
    }

    @Test
    void shouldReturnUrlWithQueryParamsAndMinLength() throws MalformedURLException {
        URL expected = new URL("http", "test.dev", "?test=test" + SIXTY_CHARS);
        mockSimpleUrl();
        mockRandomService();
        when(randomService.nextInt(6))
                .thenReturn(4);
        when(loremDummy.characters(60))
                .thenReturn(SIXTY_CHARS);

        URL actual = builder
                .withQueryParams()
                .minLength(80)
                .build();

        assertEquals(expected, actual);
    }

    @Test
    void shouldReturnUrlWithMultipleQueryParamsAndFile() throws MalformedURLException {
        URL expected = new URL("http", "test.dev", "/" + TEN_CHARS + ".html" + "?test=test&test=test");
        mockSimpleUrl();
        mockRandomService();
        when(randomService.nextInt(6))
                .thenReturn(4);
        when(loremDummy.characters(10))
                .thenReturn(TEN_CHARS);

        URL actual = builder
                .withQueryParams(2)
                .withFilePath()
                .build();

        assertEquals(expected, actual);
    }

    @Test
    void shouldReturnUrlWithMultipleQueryParamsAndFileAndMinLength() throws MalformedURLException {
        URL expected = new URL("http", "test.dev", "/" + TEN_CHARS + ".html" +
                "?test=test&test=test" + SIXTY_CHARS);
        mockSimpleUrl();
        mockRandomService();
        when(randomService.nextInt(6))
                .thenReturn(4);
        when(loremDummy.characters(60))
                .thenReturn(SIXTY_CHARS);
        when(loremDummy.characters(10))
                .thenReturn(TEN_CHARS);

        URL actual = builder
                .withQueryParams(2)
                .withFilePath()
                .minLength(80)
                .build();

        assertEquals(expected, actual);
    }

    @Test
    void shouldReturnUrlWithSpecifiedProtocol() throws MalformedURLException {
        URL expected = new URL("https", "test.dev", "");
        mockSimpleUrl();

        URL actual = builder
                .withProtocol("https")
                .build();

        assertEquals(expected, actual);
    }

    @Test
    void shouldReturnUrlWithSpecifiedProtocolAndMinimumLength() throws MalformedURLException {
        URL expected = new URL("https", "test" + SIXTY_CHARS + ".dev", "");
        mockSimpleUrl();
        when(loremDummy.characters(60))
                .thenReturn(SIXTY_CHARS);

        URL actual = builder
                .withProtocol("https")
                .minLength(60)
                .build();

        assertEquals(expected, actual);
    }
}