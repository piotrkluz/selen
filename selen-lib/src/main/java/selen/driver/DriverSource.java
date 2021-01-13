package selen.driver;

import io.github.bonigarcia.wdm.WebDriverManager;
import lombok.SneakyThrows;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.*;
import org.openqa.selenium.remote.codec.w3c.W3CHttpCommandCodec;
import org.openqa.selenium.remote.codec.w3c.W3CHttpResponseCodec;

import java.io.*;
import java.lang.reflect.Field;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collections;
import java.util.Optional;

public class DriverSource {
    private static final boolean REUSE_BROWSER_BETWEEN_RUNS = true; // todo extract to configuration
    private static final ConnectionStore connectionStore = new ConnectionStore();

    private static WebDriver driver;

    public static WebDriver getDriver() {
        if(driver == null) {
            driver = REUSE_BROWSER_BETWEEN_RUNS
                    ? tryReConnectToActiveBrowser().orElseGet(DriverSource::openNewBrowser)
                    : openNewBrowser();
        }

        return driver;
    }

    @SneakyThrows
    private static RemoteWebDriver openNewBrowser() {
        WebDriverManager.chromedriver().setup();
        ChromeDriver newDriver = new ChromeDriver();
        if(REUSE_BROWSER_BETWEEN_RUNS) connectionStore.storeConnectionString(newDriver);

        return newDriver;
    }

    public static void close() {
        if (driver != null) driver.close();
        driver = null;
    }

    private static Optional<RemoteWebDriver> tryReConnectToActiveBrowser() {
        try {
            return Optional.of(restoreDriverConnection(connectionStore.getConnectionString()));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    private static RemoteWebDriver restoreDriverConnection(String connectionString) throws WebDriverException, MalformedURLException {
        String[] connectionInfo = connectionString.split(">>>");
        RemoteWebDriver recreatedDriver = createDriverFromSession(new SessionId(connectionInfo[0]), new URL(connectionInfo[1]));
        recreatedDriver.getCurrentUrl(); // should throw if not connected
        return recreatedDriver;
    }

    /**
     * Hack used to restore webdriver connection
     * @link https://tarunlalwani.com/post/reusing-existing-browser-session-selenium/
     */
    private static RemoteWebDriver createDriverFromSession(final SessionId sessionId, URL command_executor){
        CommandExecutor executor = new HttpCommandExecutor(command_executor) {
            @Override
            public Response execute(Command command) throws IOException {
                Response response;
                if (command.getName().equals("newSession")) {
                    response = new Response();
                    response.setSessionId(sessionId.toString());
                    response.setStatus(0);
                    response.setValue(Collections.<String, String>emptyMap());

                    try {
                        Field commandCodec = this.getClass().getSuperclass().getDeclaredField("commandCodec");
                        commandCodec.setAccessible(true);
                        commandCodec.set(this, new W3CHttpCommandCodec());

                        Field responseCodec = this.getClass().getSuperclass().getDeclaredField("responseCodec");
                        responseCodec.setAccessible(true);
                        responseCodec.set(this, new W3CHttpResponseCodec());
                    } catch (NoSuchFieldException | IllegalAccessException e) {
                        e.printStackTrace();
                        throw new RuntimeException(e);
                    }

                } else {
                    response = super.execute(command);
                }
                return response;
            }
        };

        return new RemoteWebDriver(executor, new DesiredCapabilities());
    }
}
