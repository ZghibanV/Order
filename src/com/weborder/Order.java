package com.weborder;

import java.util.Iterator;
import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class Order {
	
	public static int randomQuantity (int n) {
		Random r = new Random();
		return r.nextInt(n);
	}
	public static String randomMiddle(int n) {
		Random r = new Random();
		String middle = "";
		for (int i = 0; i < n; i++) {			
			middle += ((char)(r.nextInt(26) + 97));
			if(i == 0)
				middle = middle.toUpperCase();
		}
		return middle;
	}
	public static String randomZip ( int n) {
		Random r = new Random();
		String zip = "";
		for (int i = 0; i < n ; i++) { 
			zip += r.nextInt(10);
		}
		return zip;
	}
	public static String cardNumber( int cardType) {
		String cardNumber = "";
		switch (cardType) {
		case 0: cardNumber += 4 +  randomZip(15);break; 
		case 1: cardNumber += 5 + randomZip(15);break;
		case 2: cardNumber += 3 + randomZip(14);break;
		}		
		return cardNumber;
	}
	
	public static String expirationDate () {
		String expirationDate = "";
		int month = randomQuantity(12) + 1 ;
		expirationDate  += (month < 10 ? "0" + month  : month) + "/" + (randomQuantity(5) + 18);
		
		return expirationDate;
	}
	
	

	public static void main(String[] args) throws InterruptedException {
		System.setProperty("webdriver.chrome.driver", 
				"C:/Users/Zghiban/Documents/Selenium dependencies/drivers/chromedriver.exe");
		WebDriver driver = new ChromeDriver();
		int count = 0;
		for( int i = 0; i < 100; i ++) {
		driver.get("http://secure.smartbearsoftware.com/samples/TestComplete12/WebOrders/Login.aspx");
		driver.findElement(By.name("ctl00$MainContent$username")).sendKeys("Tester");
		driver.findElement(By.name("ctl00$MainContent$password")).sendKeys("test");
		driver.findElement(By.name("ctl00$MainContent$login_button")).click();
		driver.findElement(By.xpath("//*[@id=\"ctl00_menu\"]/li[3]/a")).click();
		driver.findElement(By.name("ctl00$MainContent$fmwOrder$txtQuantity")).sendKeys("" + randomQuantity(99)+1);
		driver.findElement(By.name("ctl00$MainContent$fmwOrder$txtName")).sendKeys("John " + randomMiddle(3) + " Smith");
		driver.findElement(By.name("ctl00$MainContent$fmwOrder$TextBox2")).sendKeys("123 Any st");
		driver.findElement(By.name("ctl00$MainContent$fmwOrder$TextBox3")).sendKeys("Anytown");
		driver.findElement(By.name("ctl00$MainContent$fmwOrder$TextBox4")).sendKeys("Virginia");
		driver.findElement(By.name("ctl00$MainContent$fmwOrder$TextBox5")).sendKeys(randomZip(5));
		
		int cardType = randomQuantity(3);
		driver.findElement(By.id("ctl00_MainContent_fmwOrder_cardList_" + cardType)).click();
		//Thread.sleep(3000);
		driver.findElement(By.name("ctl00$MainContent$fmwOrder$TextBox6")).sendKeys(cardNumber(cardType));
		//Thread.sleep(2000);
		driver.findElement(By.name("ctl00$MainContent$fmwOrder$TextBox1")).sendKeys(expirationDate());
		//Thread.sleep(2000);
		driver.findElement(By.id("ctl00_MainContent_fmwOrder_InsertButton")).click();
		
		String expected = "New order has been successfully added.";
	
		String actual = driver.findElement(By.xpath("//*[@id=\"ctl00_MainContent_fmwOrder\"]/tbody/tr/td/div/strong"))
        .getText();
		
		System.out.println(actual);
		
		if(actual.equals(expected)) {
			count ++;
			System.out.println("Pass nr:  " + count);
			
			} else {
				System.out.println("Fail");	
				System.out.println("Expected: \t" + expected);
				System.out.println("Actual: \t" + actual); break;
			}
		}
	}
}
