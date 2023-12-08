package pl.pacinho.adventofcode2023.challange.day8.tools;

public class NavigationTools {

    public static int nextNavigationIdx(int navigateLength, int navigationIdx) {
        return navigationIdx == navigateLength - 1
                ? 0
                : navigationIdx + 1;
    }
}
