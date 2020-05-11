package cz.st52530.recipes.uitest

import geb.Browser
import org.junit.jupiter.api.Test
import org.openqa.selenium.By
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT
)
class LoginUiTest {

    @Value("\${recipes.frontend.url}")
    String frontendUrl

    @Test
    void loginTest() {
        Browser.drive {
            go frontendUrl
            assert title == "Login | UPCE"

            // a) typing text into input using GEB jQuery-like API
            $("input[name='username']").value("devglan")

            // a) typing text into input using core WebDriver API
            driver.findElement(By.name("password")).sendKeys("devglan")

            driver.findElement(By.xpath("//button[*[contains(text(),'Login')]]")).click()

            WebDriverWait wait = new WebDriverWait(driver, 10);
            wait.until(ExpectedConditions.titleIs("List user | UPCE"))

        }
    }
}
