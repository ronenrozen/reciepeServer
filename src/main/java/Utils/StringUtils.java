package Utils;

import java.util.List;
import java.util.stream.Collectors;

public class StringUtils {

    public static boolean isEmptyTrimmed(String str) {
        return (str.trim().isEmpty());
    }


    public static List<String> listToUpperCase(List<String> lst) {
        return lst.stream().map(String::toUpperCase).collect(Collectors.toList()); }


}
