package dev.codesoapbox.dummy4j;

import dev.codesoapbox.dummy4j.definitions.providers.DefinitionProvider;
import dev.codesoapbox.dummy4j.definitions.LocalizedDummyDefinitions;
import dev.codesoapbox.dummy4j.exceptions.MissingLocaleException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static java.util.Collections.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ExpressionResolverConstructionTest {

    @Mock
    private NumberService numberService;

    @Mock
    private DefinitionProvider definitionProvider;

    @Test
    void shouldConstructWithDefaultLocaleWhenNull() {
        LocalizedDummyDefinitions localizedDefinitions = mock(LocalizedDummyDefinitions.class);
        when(localizedDefinitions.getLocale())
                .thenReturn("en");
        List<LocalizedDummyDefinitions> definitions = singletonList(localizedDefinitions);
        when(definitionProvider.get())
                .thenReturn(definitions);

        ExpressionResolver resolver = new ExpressionResolver(null, numberService, definitionProvider);

        assertEquals(singletonList("en"), resolver.locales);
    }

    @Test
    void shouldConstructWithDefaultLocaleWhenEmpty() {
        LocalizedDummyDefinitions localizedDefinitions = mock(LocalizedDummyDefinitions.class);
        when(localizedDefinitions.getLocale())
                .thenReturn("en");
        List<LocalizedDummyDefinitions> definitions = singletonList(localizedDefinitions);
        when(definitionProvider.get())
                .thenReturn(definitions);

        ExpressionResolver resolver = new ExpressionResolver(emptyList(), numberService, definitionProvider);

        assertEquals(singletonList("en"), resolver.locales);
    }

    @Test
    void shouldConstructWithCustomLocale() {
        String customLocale = "pl";
        LocalizedDummyDefinitions localizedDefinitions = mock(LocalizedDummyDefinitions.class);
        when(localizedDefinitions.getLocale())
                .thenReturn(customLocale);
        List<LocalizedDummyDefinitions> definitions = singletonList(localizedDefinitions);
        when(definitionProvider.get())
                .thenReturn(definitions);

        ExpressionResolver resolver = new ExpressionResolver(singletonList(customLocale), numberService,
                definitionProvider);

        assertEquals(singletonList(customLocale), resolver.locales);
    }

    @Test
    void shouldThrowExceptionIfLocaleNotFoundInDefinitions() {
        String customLocale = "pl";
        LocalizedDummyDefinitions localizedDefinitions = mock(LocalizedDummyDefinitions.class);
        when(localizedDefinitions.getLocale())
                .thenReturn(customLocale);
        List<LocalizedDummyDefinitions> definitions = singletonList(localizedDefinitions);
        when(definitionProvider.get())
                .thenReturn(definitions);

        assertThrows(MissingLocaleException.class,
                () -> new ExpressionResolver(singletonList("en"), numberService, definitionProvider));
    }

}