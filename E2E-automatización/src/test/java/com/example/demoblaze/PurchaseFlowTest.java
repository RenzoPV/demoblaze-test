package com.example.demoblaze;

import org.junit.jupiter.api.BeforeEach;

import java.util.List;
import java.util.Random;
import java.time.Duration;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.WebElement;

//OPCIÓN 1 DE AUTOMATIZACIÓN E2E

public class PurchaseFlowTest {
    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeEach
    void setUp() {
        driver = DriverFactory.getDriver();
        driver.get("https://demoblaze.com/index.html");
        wait = new WebDriverWait(driver, Duration.ofSeconds(10)); // Esperar 10 segundos
    }

    @AfterEach
    void tearDown() throws InterruptedException {
        Thread.sleep(1500); 
        DriverFactory.quitDriver();
    }

    void addProducts() {
        List<WebElement> products = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector("a.hrefch")));
        
        System.out.println("\n" + "=".repeat(60));
        System.out.println("   [REPORTE EJECUCIÓN] - FLUJO DE COMPRA DEMOBLAZE");
        System.out.println("=".repeat(60));
        System.out.println("[INFO] " + java.time.LocalTime.now() + " - Navegando a: https://demoblaze.com");
        System.out.println("[INFO] " + java.time.LocalTime.now() + " - Productos detectados en Home: " + products.size());

        Random rnd = new Random();
        int f = rnd.nextInt(products.size());
        int s;

        do {
            s = rnd.nextInt(products.size());
        } while (f == s);

        addProduct(products.get(f));
        driver.findElement(By.cssSelector("#navbarExample a[href='index.html']")).click();
        WebElement product = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector("a.hrefch"))).get(s);
        addProduct(product);
        driver.findElement(By.cssSelector("#navbarExample a[href='index.html']")).click();
    }

    void addProduct(WebElement product) {
        String nombre = product.getText();
        System.out.println("[STEP] " + java.time.LocalTime.now() + " -> Añadiendo: " + nombre);

        product.click();
        wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Add to cart"))).click();
        wait.until(ExpectedConditions.alertIsPresent()).accept();

        System.out.println("[OK] Producto agregado con éxito.");
    }

    void viewCart() {
        driver.findElement(By.linkText("Cart")).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(text(), 'Place Order')]"))).click();
    }

    void fillForm() throws InterruptedException{
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("name"))).sendKeys("Test User");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("country"))).sendKeys("Peru");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("city"))).sendKeys("Lima");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("card"))).sendKeys("1111222233334444");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("month"))).sendKeys("december");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("year"))).sendKeys("2025");

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[contains(text(), 'Purchase')]"))).click();
        Thread.sleep(1500);
    }

    void completePurchase() {
        String mensajeConfirmacion = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".sweet-alert h2"))).getText();
        String detallesRecibo = driver.findElement(By.cssSelector(".sweet-alert p")).getText();
       
        //Validamos que el texto sea el esperado
        Assertions.assertEquals("Thank you for your purchase!", mensajeConfirmacion, "El mensaje de éxito no coincide.");
        
        System.out.println("\n" + "*".repeat(60));
        System.out.println("          VALIDACIÓN FUNCIONAL: EXITOSA");
        System.out.println("*".repeat(60));
        System.out.println("[MESSAGE]: " + mensajeConfirmacion);
        
        // Formatear el recibo para que no salga en una sola línea
        System.out.println("[RECEIPT DETAILS]:");
        String[] info = detallesRecibo.split("\n");
        for(String linea : info) {
            System.out.println("   >> " + linea.trim());
        }
        System.out.println("*".repeat(60) + "\n");
        
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button.confirm"))).click();
    }

    @Test
    void testCompletePurchaseFlow() throws InterruptedException {
        addProducts();
        viewCart();
        fillForm();
        completePurchase();
    }
}
