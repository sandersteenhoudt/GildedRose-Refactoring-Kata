package com.gildedrose.item;

import com.gildedrose.Item;
import org.apache.commons.lang3.Range;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;

public class LimitedTimeItem extends GildedRoseItem {
    public static final int DEFAULT_QUALITY_MODIFIER = 1;
    private static final List<TimeBasedQualityModifier> DEFAULT_MODIFIERS = List.of(
        new TimeBasedQualityModifier(Range.between(1, 5), 3),
        new TimeBasedQualityModifier(Range.between(6, 10), 2)
    );
    final List<TimeBasedQualityModifier> qualityModifiers;

    public LimitedTimeItem(Item item) {
        this(item, DEFAULT_MODIFIERS);
    }

    public LimitedTimeItem(Item item, final Collection<TimeBasedQualityModifier> qualityModifiers) {
        super(item);
        //TODO check for overlapping ranges?
        this.qualityModifiers = qualityModifiers.stream()
            .sorted(Comparator.comparingInt(qualityModifier -> qualityModifier.dayRange.getMaximum()))
            .toList();
    }

    @Override
    public int dailyQualityChange() {
        return qualityModifiers.stream()
            .filter(qualityModifier -> qualityModifier.dayRange().contains(sellIn))
            .findFirst()
            .map(TimeBasedQualityModifier::qualityChange)
            .orElse(DEFAULT_QUALITY_MODIFIER);
    }


    @Override
    public void updateQuality() {
        //Allowed to sell on day of concert?
        if (sellIn < 0) {
            //TODO logging
            quality = 0;
        } else {
            super.updateQuality();
        }
    }

    public record TimeBasedQualityModifier(Range<Integer> dayRange, int qualityChange) {
    }
}
