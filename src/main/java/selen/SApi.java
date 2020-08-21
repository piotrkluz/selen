package selen;

public class SApi {
    public static SSelector $(String cssOrXpath) {
        return new SSelectorImpl(cssOrXpath);
    }
}
