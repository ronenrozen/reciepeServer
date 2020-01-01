package Utils;

import java.util.List;

public class StringUtils {

    public static boolean isEmptyTrimmed(String str) {
        return (str.trim().isEmpty());
    }

    public static boolean isFullyContainedInArray(List<String> containedArray, List<String> containingArray) {
        System.out.println("Contained array: " + containedArray.toString());
        System.out.println("Containing array: " + containingArray.toString());
        if (containedArray.size() > containingArray.size()) {
            System.out.println("Contained array is larger than containing array.");
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
