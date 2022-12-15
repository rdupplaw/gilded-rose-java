package com.gildedrose;

import java.util.Arrays;

class GildedRose {
    Item[] items;

    public GildedRose(Item[] items) {
        this.items = items;
    }

    InventoryItem mapItemToInventoryItem(Item item) {
        switch (item.name) {
            case "Aged Brie":
                return new AgedBrie(item);
            case "Backstage passes to a TAFKAL80ETC concert":
                return new BackstagePass(item);
            case "Sulfuras, Hand of Ragnaros":
                return new Sulfuras();
            default:
                return new GenericInventoryItem(item);
        }
    }

    public void updateQuality() {
        Arrays.stream(items).map(this::mapItemToInventoryItem).forEach(InventoryItem::updateQuality);
    }

    private interface InventoryItem {
        void updateQuality();
    }

    private static class GenericInventoryItem implements InventoryItem {
        private final Item item;

        public GenericInventoryItem(Item item) {
            this.item = item;
        }

        @Override
        public void updateQuality() {
            item.sellIn = item.sellIn - 1;

            if (item.quality > 0) {
                item.quality -= 1;
            }

            if (item.sellIn < 0 && item.quality > 0) {
                item.quality -= 1;
            }
        }
    }

    private static class AgedBrie implements InventoryItem {
        private final Item item;

        public AgedBrie(Item item) {
            this.item = item;
        }

        @Override
        public void updateQuality() {
            item.sellIn = item.sellIn - 1;

            if (item.quality < 50) {
                item.quality = item.quality + 1;
            }

            if (item.sellIn < 0 && item.quality < 50) {
                item.quality = item.quality + 1;
            }
        }
    }

    private static class BackstagePass implements InventoryItem {
        private final Item item;

        public BackstagePass(Item item) {
            this.item = item;
        }

        @Override
        public void updateQuality() {
            item.sellIn = item.sellIn - 1;

            if (item.sellIn < 0) {
                item.quality = 0;
                return;
            }

            if (item.quality > 49) {
                return;
            }

            item.quality = item.quality + 1;

            if (item.sellIn < 10 && item.quality < 50) {
                item.quality = item.quality + 1;
            }

            if (item.sellIn < 5 && item.quality < 50) {
                item.quality = item.quality + 1;
            }
        }
    }

    private static class Sulfuras implements InventoryItem {

        public Sulfuras() {
        }

        @Override
        public void updateQuality() {
        }
    }
}
