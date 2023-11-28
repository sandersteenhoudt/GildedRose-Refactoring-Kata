package com.gildedrose.item;

import com.gildedrose.testfixtures.ItemMother;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;

import java.util.Random;
import java.util.stream.Stream;

import static com.gildedrose.assertions.ItemAssertions.assertThat;

class DepreciatingItemTest {

    /**
     * Generates a stream of items with a random starting quality and quality change
     *
     * @return stream of tests
     */
    @TestFactory
    Stream<DynamicTest> qualityChangeTests() {
        var random = new Random();
        var faker = new Faker();
        return random.ints(100, 1, 6)
            .mapToObj(qualityChange -> {
                var originalQuality = random.nextInt(30);
                int expectedUpdatedQuality = Integer.max(originalQuality - qualityChange, 0); // Quality can never be negative

                return DynamicTest.dynamicTest(
                    String.format("Item  with quality <%d> and daily quality change of <%d> should result in updated quality of <%d>", originalQuality, qualityChange, expectedUpdatedQuality),
                    () -> {
                        var item = new DepreciatingItem(faker.commerce().productName(), 13, originalQuality, qualityChange);
                        item.updateQuality();
                        assertThat(item).hasQuality(expectedUpdatedQuality);
                    });
            });
    }

    @Test
    void shouldHaveQualityOfZeroInsteadOfNegative() {
        var itemWithBelowZeroQualityDegradation = new DepreciatingItem(ItemMother.dexterityVest(), ItemMother.VEST_QUALITY + 1);
        itemWithBelowZeroQualityDegradation.updateQuality();
        assertThat(itemWithBelowZeroQualityDegradation).hasQuality(0);
    }


    @Test
    void shouldDecrementSellInDaysWhenUpdating() {
        var item = new DepreciatingItem(ItemMother.dexterityVest());
        item.decreaseSellIn();
        assertThat(item)
            .hasDaysLeftToSell(ItemMother.VEST_SELL_IN_DAYS - 1);
    }
}
