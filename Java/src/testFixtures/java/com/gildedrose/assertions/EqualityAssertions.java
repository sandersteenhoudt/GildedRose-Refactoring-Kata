package com.gildedrose.assertions;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.ObjectAssert;

public interface EqualityAssertions {

    /**
     * Constructs an equality assertion with a readable failure message
     *
     * @param thingToCheck description of the object of the assertion, used in the failure message
     * @param expected     the expected value
     * @param actual       the actual object being tested
     * @param <T>          type of the object being tested
     * @return the assertion with failure message
     */
    default <T> ObjectAssert<T> constructEqualityAssertion(final String thingToCheck, final T expected,
        final T actual) {
        return Assertions.assertThat(actual)
            .withFailMessage("Expected %s to be <%s> but was <%s>", thingToCheck, expected, actual)
            .isEqualTo(expected);
    }
}
