package com.gildedrose.testfixtures;

import com.gildedrose.Item;

public class ItemMother {
    public static final String VEST_NAME = "+5 Dexterity Vest";
    public static final int VEST_SELL_IN_DAYS = 10;
    public static final int VEST_QUALITY = 20;

    public static final String AGED_BRIE_NAME = "Aged Brie";
    public static final int AGED_BRIE_SELL_IN_DAYS = 2;
    public static final int AGED_BRIE_QUALITY = 0;
    public static final String SULFURAS_NAME = "Sulfuras, Hand of Ragnaros";
    public static final int SULFURAS_SELL_IN_DAYS = -1;
    public static final int SULFURAS_QUALITY = 80;
    public static final String BACKSTAGE_PASS_NAME = "Backstage passes to a TAFKAL80ETC concert";
    public static final int BACKSTAGE_PASS_SELL_IN_DAYS = 15;
    public static final int BACKSTAGE_PASS_QUALITY = 20;


    public static Item dexterityVest() {
        return new Item(VEST_NAME, VEST_SELL_IN_DAYS, VEST_QUALITY);
    }

    public static Item agedBrie() {
        return new Item(AGED_BRIE_NAME, AGED_BRIE_SELL_IN_DAYS, AGED_BRIE_QUALITY);
    }

    public static Item sulfuras() {
        return new Item(SULFURAS_NAME, SULFURAS_SELL_IN_DAYS, SULFURAS_QUALITY);
    }

    public static Item backstagePass() {
        return new Item(BACKSTAGE_PASS_NAME, BACKSTAGE_PASS_SELL_IN_DAYS, BACKSTAGE_PASS_QUALITY);
    }
}
