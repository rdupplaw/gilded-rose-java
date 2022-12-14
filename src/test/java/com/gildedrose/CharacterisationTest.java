package com.gildedrose;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CharacterisationTest {
    @Test
    void characterisationTest() throws URISyntaxException, IOException {
        Item[] items = new Item[]{
            new Item("+5 Dexterity Vest", 10, 20), //
            new Item("Aged Brie", 2, 0), //
            new Item("Elixir of the Mongoose", 5, 7), //
            new Item("Sulfuras, Hand of Ragnaros", 0, 80), //
            new Item("Sulfuras, Hand of Ragnaros", -1, 80),
            new Item("Backstage passes to a TAFKAL80ETC concert", 15, 20),
            new Item("Backstage passes to a TAFKAL80ETC concert", 10, 49),
            new Item("Backstage passes to a TAFKAL80ETC concert", 5, 49),
            // this conjured item does not work properly yet
            new Item("Conjured Mana Cake", 3, 6)};

        GildedRose app = new GildedRose(items);

        int days = 30;

        StringBuilder actualOutput = new StringBuilder();

        for (int i = 0; i < days; i++) {
            actualOutput.append("-------- day ").append(i).append(" --------\n");
            actualOutput.append("name, sellIn, quality\n");
            for (Item item : items) {
                actualOutput.append(item).append("\n");
            }
            actualOutput.append("\n");
            app.updateQuality();
        }

        String expectedOutput = String.join("\n", Files.readAllLines(Paths.get(Objects.requireNonNull(getClass().getClassLoader().getResource("expected-output.txt")).toURI())));

        assertEquals(expectedOutput, actualOutput.toString());
    }

}
