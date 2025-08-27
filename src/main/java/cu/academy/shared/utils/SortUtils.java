package cu.academy.shared.utils;

import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class SortUtils {

    public static <T> List<T> sortResults(String text, List<T> elements, Function<T, String> keyExtractor) {
        String firstWord = (text.split(" ").length > 1) ? text.split(" ")[0] : text;
        Comparator<T> comparator = Comparator.comparingDouble(
                p -> StringUtils.similarity(keyExtractor.apply(p).split(" ")[0], firstWord));
        return elements.stream().sorted(comparator.reversed()).collect(Collectors.toList());
    }

}
