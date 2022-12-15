package com.gildedrose;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

class GildedRose {
    Item[] items;
    private final List<InventoryItem> inventoryItems;

    public GildedRose(Item[] items) {
        this.items = items;
        this.inventoryItems = Arrays.stream(items).map(this::mapItemToInventoryItem).collect(Collectors.toList());
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
        for (InventoryItem item : inventoryItems) {
            item.updateQuality();
        }
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
            if (item.quality > 0) {
                item.quality = item.quality - 1;
            }

            item.sellIn = item.sellIn - 1;

            if (item.sellIn < 0) {
                if (item.quality > 0) {
                    item.quality = item.quality - 1;
                }
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
            if (item.quality < 50) {
                item.quality = item.quality + 1;
            }

            item.sellIn = item.sellIn - 1;

            if (item.sellIn < 0) {
                if (item.quality < 50) {
                    item.quality = item.quality + 1;
                }
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
            if (item.quality < 50) {
                item.quality = item.quality + 1;

                if (item.sellIn < 11) {
                    if (item.quality < 50) {
                        item.quality = item.quality + 1;
                    }
                }

                if (item.sellIn < 6) {
                    if (item.quality < 50) {
                        item.quality = item.quality + 1;
                    }
                }
            }

            item.sellIn = item.sellIn - 1;

            if (item.sellIn < 0) {
                item.quality = 0;
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
