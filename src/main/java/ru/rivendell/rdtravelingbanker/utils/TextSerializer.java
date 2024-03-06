package ru.rivendell.rdtravelingbanker.utils;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.ansi.ANSIComponentSerializer;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;

import java.util.ArrayList;
import java.util.List;

public class TextSerializer {

    public static final MiniMessage mm = MiniMessage.miniMessage();
    public static final LegacyComponentSerializer legacySection = LegacyComponentSerializer.legacySection();
    public static final ANSIComponentSerializer ansi = ANSIComponentSerializer.ansi();

    public static Component serializeComponent(String s) {
        return mm.deserialize(s);
    }
    public static String serializeSection(String s) {
        return legacySection.serialize(mm.deserialize(s));
    }
    public static String serializeAnsi(String s) {
        return ansi.serialize(mm.deserialize(s));
    }

    public static List<Component> serializeComponents(List<String> s) {
        List<Component> components = new ArrayList<>();

        for (String string : s) {
            components.add(mm.deserialize(string));
        }

        return components;
    }
}
