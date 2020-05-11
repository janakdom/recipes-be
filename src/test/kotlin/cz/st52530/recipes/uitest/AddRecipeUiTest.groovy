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
class AddRecipeUiTest {

    @Value("\${recipes.frontend.url}")
    private String frontendUrl

    @Autowired
    private Creator creator

    @Autowired
    private PasswordEncoder encoder

    @Test
    void givenCorrectInformation_thenAddRecipeIsSuccessful() {
        // TODO: Prepare categories + ingredients.

        login()

        // Navigate to Add Recipe page.
        Browser.drive {
            assert title == "Seznam receptů | Rodinné recepty"

            driver.findElement(By.linkText("PŘIDAT RECEPT")).click()
            WebDriverWait wait = new WebDriverWait(driver, 5)
            wait.until(ExpectedConditions.titleIs("Přidání receptu | Rodinné recepty"))
        }

        // Actually add the recipe!
        Browser.drive {
            driver.findElement(By.xpath("/html/body/div[@id='root']/div[@class='MuiBox-root MuiBox-root-5']/main[@class='MuiContainer-root makeStyles-mainContainer-2 MuiContainer-maxWidthLg']/div[@class='MuiContainer-root makeStyles-root-177 MuiContainer-maxWidthMd']/ul[@class='makeStyles-root-268']/li/div[@class='MuiButtonBase-root MuiChip-root makeStyles-chip-269 MuiChip-colorPrimary MuiChip-clickableColorPrimary MuiChip-deletableColorPrimary MuiChip-outlined MuiChip-outlinedPrimary MuiChip-clickable MuiChip-deletable']")).click()
            driver.findElement(By.linkText("Přidat kategorii")).click()
        }
    }

    private Browser login() {
        // Prepare user.
        creator.saveEntity(new User(
                "username",
                encoder.encode("password"),
                "My Name"
        ))

        return Browser.drive {
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
}
