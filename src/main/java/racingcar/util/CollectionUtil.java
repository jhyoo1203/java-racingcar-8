package racingcar.util;

public final class CollectionUtil {

    private CollectionUtil() { }

    public static boolean isEmpty(Iterable<?> collection) {
        if (collection == null) {
            return true;
        }
        return !collection.iterator().hasNext();
    }
}
