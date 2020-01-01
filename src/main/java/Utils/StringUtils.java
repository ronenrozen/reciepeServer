package Utils;

import java.util.List;

public class StringUtils {

    public static boolean isEmptyTrimmed(String str) {
        return (str.trim().isEmpty());
    }

    public static boolean isFullyContainedInArray(List<String> containedArray, List<String> containingArray) {
        if (containedArray.size() > containingArray.size()) {
            return false;
        }
        else {
            for (int i = 0; i < containedArray.size(); ++i) {
                if (!containingArray.contains(containedArray.get(i).toUpperCase()))
                    return false;
            }
            return true;
        }
    }


}
