package dev.codesoapbox.dummy4j;

import java.util.List;

/**
 * An abstract dummy builder which can optionally be extended to build classes extending Dummy4j.
 *
 * @see Dummy4jBuilder
 * @since 0.3.1
 */
public abstract class AbstractDummy4jBuilder<T extends AbstractDummy4jBuilder<T, E>, E> {

    protected Long seed;
    protected List<String> locale;
    protected List<String> paths;

    public AbstractDummy4jBuilder() {
    }

    public T seed(Long seed) {
        self().seed = seed;
        return self();
    }

    protected abstract T self();

    public T locale(List<String> locale) {
        self().locale = locale;
        return self();
    }

    public T paths(List<String> paths) {
        self().paths = paths;
        return self();
    }

    public abstract E build();
}
