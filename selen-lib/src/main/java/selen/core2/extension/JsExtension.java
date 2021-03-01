package selen.core2.extension;

import org.apache.commons.lang3.ArrayUtils;
import org.openqa.selenium.JavascriptExecutor;

public interface JsExtension extends WebElementProvider {
    default String innerHTML() {
        return (String) executeJs("return el.innerHTML");
    }
    default String outerHTML() {
        return (String) executeJs("return el.outerHTML");
    }
    default long selectionStart() {
        return (long) executeJs("return el.selectionStart");
    }
    default long selectionEnd() {
        return (long) executeJs("return el.selectionEnd");
    }

    default boolean hasFocus() {
        return (Boolean) executeJs("return el === document.activeElement");
    }

    default JsExtension focus() {
        executeJs("el.focus()");
        return this;
    }

    default JsExtension scrollIntoView() {
        return scrollIntoView(false, true);
    }

    /**
     * @param smooth Uses smooth scroll (EXPERIMENTAL)
     * @param center If set, the element will be scrolled to center of window.
     */
    default JsExtension scrollIntoView(boolean smooth, boolean center) {
        executeJs(String.format("el.scrollIntoView({behavior: '%s', block: '%s'})",
                smooth ? "smooth" : "auto", center ? "center" : "start"));
        return this;
    }

    /**
     * Executes JavaScript inside browser.
     * Current element have "el" name in JS Scope:
     *
     * {@code
     *     myElement.executeJs("console.log(el.outerHTML)")
     *     myElement.executeJs("console.log(arguments[0])", argumentComesFromJava)
     * }
     */
    default Object executeJs(String jsCode, Object... arguments) {
        JavascriptExecutor executor = (JavascriptExecutor) getDriver();
        // add WebElement as last argument
        Object[] allArgs = ArrayUtils.add(arguments, getWebElement());
        int lastArg = allArgs.length - 1;
        return executor.executeScript("let el = arguments[" + lastArg + "];\n" + jsCode, allArgs);
    }
}
