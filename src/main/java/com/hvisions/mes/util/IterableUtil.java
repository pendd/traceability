package com.hvisions.mes.util;

import java.util.Objects;
import java.util.function.BiConsumer;

public class IterableUtil {
    private IterableUtil() {
    }

    /**
     * 集合的forEach方法,带有索引.
     *
     * <pre>
     * // 使用方法:
     * IterableUtil.forEach(results, (index, item) -> {
     *     // results是一个要循环的集合;
     *     // index是索引,可以任意命名;
     *     // item是集合中的每个对象,可以任意命名.
     * });
     * </pre>
     *
     */
    public static <E> void forEach(Iterable<? extends E> elements,
            BiConsumer<Integer, ? super E> action) {

        Objects.requireNonNull(elements);
        Objects.requireNonNull(action);

        int index = 0;
        for (E element : elements) {
            action.accept(index++, element);
        }
    }
}
