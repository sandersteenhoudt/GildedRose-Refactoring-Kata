package com.gildedrose.item;

import com.gildedrose.Item;
import org.apache.commons.lang3.Range;
import org.slf4j.Logger;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

public class LimitedTimeItem extends AppreciatingItem {
    private static final Logger log = getLogger(ItemConverter.class);
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
        if (isExpired()) {
            log.debug("Item {} is expired, quality is 0", name);
            quality = 0;
        } else {
            super.updateQuality();
        }
    }

    public record TimeBasedQualityModifier(Range<Integer> dayRange, int qualityChange) {
        @Override
        public String toString() {
            return "TimeBasedQualityModifier{" +
                "dayRange=" + dayRange +
                ", qualityChange=" + qualityChange +
                '}';
        }
    }
}
