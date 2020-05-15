package cz.st52530.recipes.uitest

import cz.st52530.recipes.model.database.User
import cz.st52530.recipes.testutil.Creator
import geb.Browser
import org.junit.jupiter.api.Test
import org.openqa.selenium.By
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.crypto.password.PasswordEncoder

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT
)
class LoginUiTest {

    @Value("\${recipes.frontend.url}")
    private String frontendUrl

    @Autowired
    private Creator creator

    @Autowired
    private PasswordEncoder encoder

    @Test
    void givenCorrectLoginDetails_thenLoginIsSuccessful() {
        // Prepare user.
        creator.saveEntity(new User(
                "username",
                encoder.encode("password"),
                "My Name"
        ))

        Browser.drive {
            go frontendUrl
            assert title == "Přihlášení | Rodinné recepty"

            // Type username with jQuery-like syntax.
            $("input[name='username']").value("username")

            // Type password using core WebDriver API
            driver.findElement(By.name("password")).sendKeys("password")

            // Click on login button by xpath expression.
            driver.findElement(By.xpath("//button[*[contains(text(),'Přihlásit')]]")).click()

            WebDriverWait wait = new WebDriverWait(driver, 5)
            wait.until(ExpectedConditions.titleIs("Seznam receptů | Rodinné recepty"))
        }
    }

    @Test
    void givenWrongLoginDetails_thenLoginFails() {
        Browser.drive {
            go frontendUrl
            assert title == "Přihlášení | Rodinné recepty"

            // Type username with jQuery-like syntax.
            $("input[name='username']").value("username")

            // Type password using core WebDriver API
            driver.findElement(By.name("password")).sendKeys("password")

            // Click on login button by xpath expression.
            driver.findElement(By.xpath("//button[*[contains(text(),'Přihlásit')]]")).click()

            WebDriverWait wait = new WebDriverWait(driver, 5)
            wait.until(ExpectedConditions.textToBe(By.id("password-helper-text"), "Špatné jméno nebo heslo."))
            assert title == "Přihlášení | Rodinné recepty"
        }
    }
}
