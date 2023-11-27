package com.gildedrose.item;

import com.gildedrose.testfixtures.ItemMother;
import org.apache.commons.lang3.Range;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;

import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static com.gildedrose.assertions.ItemAssertions.assertThat;

class LimitedTimeItemTest {
    private static final List<LimitedTimeItem.TimeBasedQualityModifier> DEFAULT_MODIFIERS = List.of(
        new LimitedTimeItem.TimeBasedQualityModifier(Range.between(1, 5), 3),
        new LimitedTimeItem.TimeBasedQualityModifier(Range.between(6, 10), 2)
    );

    @Test
    void shouldHaveZeroQualityWhenSellInNegative() {
        var item = new LimitedTimeItem(ItemMother.backstagePass());
        item.sellIn = -1;
        item.updateQuality();
        assertThat(item).hasQuality(0);
    }

    @Test
    void shouldCapQualityAtMaximum() {
        var itemWithQualityChangeExceedingMaximum = new LimitedTimeItem(ItemMother.backstagePass());
        itemWithQualityChangeExceedingMaximum.quality = AppreciatingItem.MAXIMUM_QUALITY;

        updateItemQualityAndCompareWithExpectedQuality(itemWithQualityChangeExceedingMaximum, AppreciatingItem.MAXIMUM_QUALITY);
    }

    void updateItemQualityAndCompareWithExpectedQuality(GildedRoseItem item, int expectedQuality) {
        item.updateQuality();
        assertThat(item)
            .hasQuality(expectedQuality);
    }

    @TestFactory
    Stream<DynamicTest> defaultModifierTests() {
        return Stream.concat(
            testsForSellInOutsideDefaultRangesWithIncreasedQualityChange(),
            createTestsForLimitedTimeItem(DEFAULT_MODIFIERS)
        );
    }

    private Stream<DynamicTest> testsForSellInOutsideDefaultRangesWithIncreasedQualityChange() {
        return new Random().ints(8, 11, 25) //Random sellIn between 11 and 25 days
            .mapToObj(sellIn -> qualityChangeBasedOnSellInTest(sellIn, 1));
    }

    /**
     * Returns a test for each sellIn value to check its corresponding quality change
     *
     * @param expectedQualityModifiers expected quality changes for range of {@code sellIn} values
     * @return a Stream of tests, one for each {@code sellIn} value within the range of the passed {@linkplain com.gildedrose.item.LimitedTimeItem.TimeBasedQualityModifier}s
     */
    private Stream<DynamicTest> createTestsForLimitedTimeItem(final Collection<LimitedTimeItem.TimeBasedQualityModifier> expectedQualityModifiers) {
        return expectedQualityModifiers.stream()
            .flatMap(qualityModifier -> IntStream.range(qualityModifier.dayRange().getMinimum(), qualityModifier.dayRange().getMaximum() + 1)
                .mapToObj(sellIn -> qualityChangeBasedOnSellInTest(sellIn, qualityModifier.qualityChange())));
    }

    /**
     * Creates a test that makes a {@linkplain LimitedTimeItem} with passed {@code sellIn} and default modifiers. The test checks whether the updated quality matches expected change in quality.
     *
     * @param sellIn                the days left to sell the item. {@linkplain LimitedTimeItem}s use different changes in quality depending on this value
     * @param expectedQualityChange the expected change in {@code quality} when the item's quality is updated. The test fails if this is different from the item's actual change in quality
     * @return the test checking the item's {@code } change depending on its  {@code sellIn}.
     */
    private DynamicTest qualityChangeBasedOnSellInTest(final int sellIn, final int expectedQualityChange) {
        var limitedTimeItem = new LimitedTimeItem(ItemMother.backstagePass());
        limitedTimeItem.sellIn = sellIn;

        return DynamicTest.dynamicTest(
            String.format("Item with sell in <%d> should have quality updated by <%d>", sellIn, expectedQualityChange),
            () -> updateItemQualityAndCompareWithExpectedQuality(limitedTimeItem, ItemMother.BACKSTAGE_PASS_QUALITY + expectedQualityChange));
    }
}
