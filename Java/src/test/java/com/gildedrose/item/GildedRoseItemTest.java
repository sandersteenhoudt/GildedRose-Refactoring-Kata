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

class GildedRoseItemTest {
    /**
     * Generates a stream of items with a random starting quality and quality change
     *
     * @return
     */
    static Stream<Arguments> itemsAndExpectedQualities() {
        var random = new Random();
        var faker = new Faker();

        return random.ints(100, -3, 4)
            .mapToObj(qualityChange -> {
                var originalQuality = random.nextInt(30);
                int expectedUpdatedQuality = Integer.max(originalQuality + qualityChange, 0); // Quality can never be negative

                return Arguments.of(
                    new GildedRoseItem(faker.commerce().productName(), 13, originalQuality, qualityChange),
                    expectedUpdatedQuality
                );
            });
    }

    @ParameterizedTest
    @MethodSource("itemsAndExpectedQualities")
    void shouldUpdateQualityUsingDailyQualityChange(GildedRoseItem item, int expectedQuality) {
        item.updateQuality();
        assertThat(item)
            .hasQuality(expectedQuality);
    }

    @Test
    void shouldHaveQualityOfZeroInsteadOfNegative() {
        var itemWithBelowZeroQualityDegradation = new GildedRoseItem(ItemMother.dexterityVest(), -1 - ItemMother.VEST_QUALITY);
        shouldUpdateQualityUsingDailyQualityChange(itemWithBelowZeroQualityDegradation, 0);
    }

    @Test
    void shouldCapQualityAtMaximum() {
        var itemWithQualityChangeExceedingMaximum = new GildedRoseItem(ItemMother.dexterityVest(), GildedRoseItem.MAXIMUM_QUALITY + 1);
        shouldUpdateQualityUsingDailyQualityChange(itemWithQualityChangeExceedingMaximum, GildedRoseItem.MAXIMUM_QUALITY);
    }

    @Test
    void shouldDecrementSellInDaysWhenUpdating() {
        var item = new GildedRoseItem(ItemMother.dexterityVest());
        item.updateSellInDays();
        assertThat(item)
            .hasDaysLeftToSell(ItemMother.VEST_SELL_IN_DAYS - 1);

    }
}
