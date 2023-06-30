import io.appium.java_client.remote.MobileCapabilityType;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import java.net.MalformedURLException;
import java.net.URL;

public class LoginLogoutTest {
    private AppiumDriver<MobileElement> driver;
    private String username;
    private String password;
    String appiumServerUrl = "http://127.0.0.1:4723/wd/hub";

    @BeforeClass
        public void setup() throws MalformedURLException {
            DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
            desiredCapabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, "Android");
            desiredCapabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "Device_Name");
            desiredCapabilities.setCapability(MobileCapabilityType.APP, "loginAppForTesting.apk");

        try {
            // Initialize the driver with the Appium server URL and desired capabilities
            driver = new AndroidDriver<>(new URL(appiumServerUrl), desiredCapabilities);
        } catch (Exception e) {
            e.printStackTrace();
        }

        username = "system";

    }

    private String getOTPFromScreen() {
        MobileElement otpElement = driver.findElementById("appPackage:id/otpTextView");
        // Get the OTP value
        String otpValue = otpElement.getText();
        return otpValue;
    }

    @Test
    public void loginLogoutTest() {
        password = getOTPFromScreen();
        // Login
        MobileElement usernameField = driver.findElementById("appPackage:id/usernameEditText");
        MobileElement passwordField = driver.findElementById("appPackage:id/passwordEditText");
        MobileElement loginButton = driver.findElementById("appPackage:id/loginButton");

        usernameField.sendKeys(username);
        passwordField.sendKeys(password);
        loginButton.click();

        // Successful login
        MobileElement welcomePage = driver.findElementById("appPackage:id/welcome_page");
        String loggedInText = welcomePage.getText();
        String expectedText = "Welcome, You are successfully logged in";

        assert loggedInText.equals(expectedText) : "Login unsuccessful";
        System.out.println("Login successful!");

        // Logout
        MobileElement signOutButton = driver.findElementById("appPackage:id/logoutButton");
        signOutButton.click();
        System.out.println("Clicked on Logout button.");

        // Confirm logout Select "Yes"
        MobileElement yesButton = driver.findElementById("appPackage:id/yesButton");
        yesButton.click();
        System.out.println("Selected 'Yes' to confirm logout.");
    }

    @AfterClass
    public void tearDown() {
        // Quit the driver and close the session
        if (driver != null) {
            driver.quit();
        }
    }
}
