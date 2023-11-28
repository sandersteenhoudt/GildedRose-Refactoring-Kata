package com.gildedrose.item;

import com.gildedrose.testfixtures.ItemMother;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;

import java.util.Random;
import java.util.stream.Stream;

import static com.gildedrose.assertions.ItemAssertions.assertThat;
import static com.gildedrose.item.AppreciatingItem.MAXIMUM_QUALITY;

class AppreciatingItemTest {

    @TestFactory
    Stream<DynamicTest> qualityChangeTests() {
        var random = new Random();
        var faker = new Faker();
        return random.ints(100, 1, 6)
            .mapToObj(qualityChange -> {
                var originalQuality = random.nextInt(30);
                int expectedUpdatedQuality = originalQuality + qualityChange;
                return DynamicTest.dynamicTest(
                    String.format("Item  with quality <%d> and daily quality change of <%d> should result in updated quality of <%d>", originalQuality, qualityChange, expectedUpdatedQuality),
                    () -> {
                        var item = new AppreciatingItem(faker.commerce().productName(), 13, originalQuality, qualityChange);
                        item.updateQuality();
                        assertThat(item).hasQuality(expectedUpdatedQuality);
                    });
            });
    }

    @Test
    void shouldCapQualityAtMaximum() {
        var itemWithQualityChangeExceedingMaximum = new AppreciatingItem(ItemMother.agedBrie(), MAXIMUM_QUALITY + 1);
        itemWithQualityChangeExceedingMaximum.updateQuality();
        assertThat(itemWithQualityChangeExceedingMaximum).hasQuality(MAXIMUM_QUALITY);
    }

    @Test
    void shouldDecrementSellInDaysWhenUpdating() {
        var item = new AppreciatingItem(ItemMother.agedBrie());
        item.decreaseSellIn();
        assertThat(item)
            .hasDaysLeftToSell(ItemMother.AGED_BRIE_SELL_IN_DAYS - 1);
    }

}
