package com.gildedrose.item;

import com.gildedrose.testfixtures.ItemMother;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Random;
import java.util.stream.Stream;

import static com.gildedrose.assertions.ItemAssertions.assertThat;
import static com.gildedrose.item.AppreciatingItem.MAXIMUM_QUALITY;

class AppreciatingItemTest {
    /**
     * Generates a stream of items with a random starting quality and quality change
     *
     * @return
     */
    static Stream<Arguments> itemsAndExpectedQualities() {
        var random = new Random();
        var faker = new Faker();

        return random.ints(100, 0, 6)
            .mapToObj(qualityChange -> {
                var originalQuality = random.nextInt(30);
                int expectedUpdatedQuality = originalQuality + qualityChange;

                return Arguments.of(
                    new AppreciatingItem(faker.commerce().productName(), 13, originalQuality, qualityChange),
                    expectedUpdatedQuality
                );
            });
    }

    @ParameterizedTest
    @MethodSource("itemsAndExpectedQualities")
    void shouldUpdateQualityUsingDailyQualityChange(AppreciatingItem item, int expectedQuality) {
        item.updateQuality();
        assertThat(item)
            .hasQuality(expectedQuality);
    }

    @Test
    void shouldCapQualityAtMaximum() {
        var itemWithQualityChangeExceedingMaximum = new AppreciatingItem(ItemMother.agedBrie(), MAXIMUM_QUALITY + 1);
        shouldUpdateQualityUsingDailyQualityChange(itemWithQualityChangeExceedingMaximum, MAXIMUM_QUALITY);
    }

}
