package dev.codesoapbox.dummy4j.dummies.internet;

import dev.codesoapbox.dummy4j.Dummy4j;

import java.net.MalformedURLException;
import java.net.URL;

public final class UrlBuilder {

    private final Dummy4j dummy4j;

    private boolean withQueryParams;
    private int howManyParams;
    private boolean withFilePath;
    private boolean withProtocol;
    private String protocol;
    private int minLength;

    public UrlBuilder(Dummy4j dummy4j) {
        this.dummy4j = dummy4j;
    }

    public UrlBuilder minLength(int minLength) {
        this.minLength = minLength;
        return this;
    }

    public UrlBuilder withQueryParams() {
        withQueryParams = true;
        howManyParams = 1;
        return this;
    }

    public UrlBuilder withQueryParams(int howManyParams) {
        withQueryParams();
        this.howManyParams = howManyParams;
        return this;
    }

    public UrlBuilder withFilePath() {
        withFilePath = true;
        return this;
    }

    public UrlBuilder withProtocol(String protocol) {
        withProtocol = true;
        this.protocol = protocol;
        return this;
    }

    public URL build() throws MalformedURLException {
        URL url = createSimpleUrl();

        if (withFilePath) {
            String file = url.getFile() + "/" + dummy4j.lorem().characters(10) + ".html";
            url = new URL(url.getProtocol(), url.getHost(), file);
        }

        if (withQueryParams) {
            StringBuilder path = fillPathWithParams(url);
            url = new URL(url.getProtocol(), url.getHost(), path.toString());
        }

        if (withProtocol) {
            url = new URL(protocol, url.getHost(), url.getFile());
        }

        url = adjustToMinLength(url);

        return url;
    }

    private URL createSimpleUrl() throws MalformedURLException {
        return new URL((dummy4j.expressionResolver().resolveKey("internet.protocol")), dummy4j.lorem().word() +
                "." + dummy4j.expressionResolver().resolve("#{internet.domain}"), "");
    }

    private StringBuilder fillPathWithParams(URL url) {
        StringBuilder path = new StringBuilder(url.getFile() + "?");
        for (int i = 0; i < howManyParams; i++) {
            path.append(dummy4j.lorem().word()).append("=");
            if (dummy4j.random().nextInt(6) > 4) {
                path.append(dummy4j.random().nextInt());
            } else {
                path.append(dummy4j.lorem().word());
            }
            if (i < howManyParams - 1) {
                path.append("&");
            }
        }
        return path;
    }

    private URL adjustToMinLength(URL url) throws MalformedURLException {
        while (url.toString().length() < minLength) {
            String originalFile = url.getFile();
            if (withFilePath && !withQueryParams) {
                String withoutExtension = originalFile.substring(0, originalFile.length() - 5);
                String file = withoutExtension.concat(dummy4j.lorem().characters(60));
                url = new URL(url.getProtocol(), url.getHost(), file + ".html");
            } else if (withQueryParams) {
                String file = originalFile.concat(dummy4j.lorem().characters(60));
                url = new URL(url.getProtocol(), url.getHost(), file);
            } else {
                String[] hostParts = url.getHost().split("\\.");
                String host = hostParts[0].concat(dummy4j.lorem().characters(60)) + "." + hostParts[1];
                url = new URL(url.getProtocol(), host, url.getFile());
            }
        }
        return url;
    }
}
