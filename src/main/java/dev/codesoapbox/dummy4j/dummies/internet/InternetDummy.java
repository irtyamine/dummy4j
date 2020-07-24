package dev.codesoapbox.dummy4j.dummies.internet;

import dev.codesoapbox.dummy4j.Dummy4j;

/**
 * @since 0.5.0
 */
public class InternetDummy {

    private final Dummy4j dummy4j;

    public InternetDummy(Dummy4j dummy4j) {
        this.dummy4j = dummy4j;
    }

    public UrlBuilder url() {
        return new UrlBuilder(dummy4j);
    }
}
