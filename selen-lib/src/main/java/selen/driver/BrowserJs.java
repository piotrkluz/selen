package selen.driver;

public interface BrowserJs {
    default void scrollBy(int top) {
        scrollBy(top, true);
    }

    default void scrollBy(int top, boolean smooth) {
        executeJs("window.scrollBy({top: arguments[0], behavior: arguments[1]})"
                , top, smooth ? "smooth" : "auto");
    }

    void executeJs(String js, Object... args);
}
