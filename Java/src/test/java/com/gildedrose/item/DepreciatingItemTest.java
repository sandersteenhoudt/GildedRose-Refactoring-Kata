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

class DepreciatingItemTest {
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
                int expectedUpdatedQuality = Integer.max(originalQuality - qualityChange, 0); // Quality can never be negative

                return Arguments.of(
                    new DepreciatingItem(faker.commerce().productName(), 13, originalQuality, qualityChange),
                    expectedUpdatedQuality
                );
            });
    }

    @ParameterizedTest
    @MethodSource("itemsAndExpectedQualities")
    void shouldUpdateQualityUsingDailyQualityChange(DepreciatingItem item, int expectedQuality) {
        item.updateQuality();
        assertThat(item)
            .hasQuality(expectedQuality);
    }

    @Test
    void shouldHaveQualityOfZeroInsteadOfNegative() {
        var itemWithBelowZeroQualityDegradation = new DepreciatingItem(ItemMother.dexterityVest(), ItemMother.VEST_QUALITY + 1);
        shouldUpdateQualityUsingDailyQualityChange(itemWithBelowZeroQualityDegradation, 0);
    }


    @Test
    void shouldDecrementSellInDaysWhenUpdating() {
        var item = new DepreciatingItem(ItemMother.dexterityVest());
        item.decreaseSellIn();
        assertThat(item)
            .hasDaysLeftToSell(ItemMother.VEST_SELL_IN_DAYS - 1);

    }
}
