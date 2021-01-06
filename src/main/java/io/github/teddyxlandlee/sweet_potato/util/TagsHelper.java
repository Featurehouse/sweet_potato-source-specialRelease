package io.github.teddyxlandlee.sweet_potato.util;

import net.minecraft.tag.Tag;

import java.util.List;

public final class TagsHelper {
    private TagsHelper() {}

    public static <T> boolean isIn(T obj, Tag<T> tag) {
        List<T> list = tag.values();
        return list.contains(obj);
    }

    @SafeVarargs
    public static <T> boolean inAny(T obj, Tag<T>... tags) {
        for (Tag<T> tag: tags) {
            if (tag.values().contains(obj)) return true;
        } return false;
    }

    @SafeVarargs
    public static <T> boolean inAll(T obj, Tag<T>... tags) {
        for (Tag<T> tag: tags) {
            if (!tag.values().contains(obj)) return false;
        } return true;
    }
}
