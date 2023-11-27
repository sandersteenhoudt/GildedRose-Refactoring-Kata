package com.gildedrose;

import com.gildedrose.testfixtures.ItemMother;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static com.gildedrose.assertions.ItemAssertions.assertThat;

//TODO clean this up, logic is mainly handled in corresponding item classes now so the tests are duplicated
class GildedRoseTest {

    private Item startAppAndGetUpdatedItem(Item item) {
        GildedRose app = new GildedRose(new Item[]{item});
        app.updateQuality();
        return app.items[0];
    }

    /**
     * Depreciating items lose quality over time.
     */
    @Nested
    class DepreciatingItems {

        @Test
        void shouldDecrementSellInDaysByOneWhenUpdatingQuality() {
            Item updatedVest = startAppAndGetUpdatedItem(ItemMother.dexterityVest());
            assertThat(updatedVest).hasDaysLeftToSell(ItemMother.VEST_SELL_IN_DAYS - 1);
        }

        @Test
        void shouldDecrementQualityByOneWhenSellByDateNotReached() {
            Item vest = ItemMother.dexterityVest();
            vest.sellIn = 5;
            Item updatedVest = startAppAndGetUpdatedItem(vest);
            assertThat(updatedVest).hasQuality(ItemMother.VEST_QUALITY - 1);
        }

        @Test
        void shouldDecrementQualityByTwoWhenSellByDateReached() {
            Item vest = ItemMother.dexterityVest();
            vest.sellIn = -1;
            Item updatedVest = startAppAndGetUpdatedItem(vest);
            assertThat(updatedVest).hasQuality(ItemMother.VEST_QUALITY - 2);
        }

        @Test
        void shouldDecrementQualityByTwoWhenSellByDateExceeded() {
            Item vest = ItemMother.dexterityVest();
            vest.sellIn = -1;
            Item updatedVest = startAppAndGetUpdatedItem(vest);
            assertThat(updatedVest).hasQuality(ItemMother.VEST_QUALITY - 2);
        }


        //TODO conjured items

    }

    @Nested
    class AppreciatingItems {
        private static final int MAX_QUALITY = 50;

        @Test
        void shouldIncreaseInQuality() {
            Item brie = startAppAndGetUpdatedItem(ItemMother.agedBrie());
            assertThat(brie).hasQuality(ItemMother.AGED_BRIE_QUALITY + 1);
        }

        @Test
        void qualityShouldBeCappedAtMaximum() {
            Item brie = ItemMother.agedBrie();
            brie.quality = MAX_QUALITY;
            Item updatedVest = startAppAndGetUpdatedItem(brie);
            assertThat(updatedVest).hasQuality(MAX_QUALITY);
        }

        //TODO use test factory with ranges to make more robust
        @Nested
        class BackstagePasses {

            @Test
            void shouldIncreaseQualityByOneWhenSellInDaysMoreThanTen() {
                Item backstagePass = ItemMother.backstagePass();
                backstagePass.sellIn = 11;
                assertThat(startAppAndGetUpdatedItem(backstagePass)).hasQuality(ItemMother.BACKSTAGE_PASS_QUALITY + 1);
            }

            @Test
            void shouldIncreaseQualityByTwoWhenSellInDaysBetweenTenAndFive() {
                Item backstagePass = ItemMother.backstagePass();
                backstagePass.sellIn = 9;
                assertThat(startAppAndGetUpdatedItem(backstagePass)).hasQuality(ItemMother.BACKSTAGE_PASS_QUALITY + 2);
            }

            @Test
            void shouldIncreaseQualityByThreeWhenSellInDaysLessThanFive() {
                Item backstagePass = ItemMother.backstagePass();
                backstagePass.sellIn = 4;
                assertThat(startAppAndGetUpdatedItem(backstagePass)).hasQuality(ItemMother.BACKSTAGE_PASS_QUALITY + 3);
            }

            //"Expired" is ambiguous here (in the context of concert tickets), so to allow selling tickets on the day of the concert, expired is -1
            @Test
            void shouldDropQualityToZeroWhenExpired() {
                Item backstagePass = ItemMother.backstagePass();
                backstagePass.sellIn = -1;
                assertThat(startAppAndGetUpdatedItem(backstagePass)).hasQuality(0);
            }
        }
    }

    @Nested
    class LegendaryItems {
        private static final int LEGENDARY_QUALITY = 80;
        private static final int LEGENDARY_SELL_IN_DAYS = 0;


        @Test
        void shouldNotChangeInQuality() {
            Item sulfuras = startAppAndGetUpdatedItem(ItemMother.sulfuras());
            assertThat(sulfuras).hasQuality(LEGENDARY_QUALITY);
            assertThat(startAppAndGetUpdatedItem(sulfuras)).hasQuality(LEGENDARY_QUALITY);
        }

        @Test
        void shouldNotChangeInSellInDays() {
            Item sulfuras = startAppAndGetUpdatedItem(ItemMother.sulfuras());
            assertThat(sulfuras).hasDaysLeftToSell(LEGENDARY_SELL_IN_DAYS);
            assertThat(startAppAndGetUpdatedItem(sulfuras)).hasDaysLeftToSell(LEGENDARY_SELL_IN_DAYS);
        }
    }

}
