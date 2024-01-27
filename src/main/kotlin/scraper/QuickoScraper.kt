package org.quickoline.scraper

import com.google.common.collect.ImmutableMap
import org.openqa.selenium.By
import org.openqa.selenium.PageLoadStrategy
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.chrome.ChromeOptions
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait
import org.quickoline.scraper.data.ScraperType
import org.quickoline.scraper.scraperImpl.scrapeMarquee
import org.quickoline.scraper.scraperImpl.scrapeTable
import org.quickoline.scraper.scraperImpl.scrapeTrending
import java.time.Duration


class QuickoScraper {

    private var browser: ChromeDriver
    private var baseUrl: String = "https://www.sarkariresult.com"

    init {
        val options = ChromeOptions()
//        options.addArguments("--headless=new")  // Run the chromedriver in headless mode
        options.setPageLoadStrategy(PageLoadStrategy.EAGER)
        options.setExperimentalOption("prefs", ImmutableMap.of("profile.default_content_settings.javascript", 2))
        browser = ChromeDriver(options)
        browser.manage().window().maximize()
        WebDriverWait(browser, Duration.ofMillis(5000)).until(ExpectedConditions.presenceOfElementLocated(By.ByTagName("body")))
    }

    fun scrape(type: ScraperType) {
        when (type) {
            ScraperType.MARQUEE -> scrapeMarquee(browser, baseUrl, type)
            ScraperType.TRENDING -> scrapeTrending(browser, baseUrl, type)
            ScraperType.RESULT -> scrapeTable(browser, baseUrl, type, "/result/")
            ScraperType.ADMITCARD -> scrapeTable(browser, baseUrl, type,"/admitcard/")
            ScraperType.LATESTJOBS -> scrapeTable(browser, baseUrl, type,"/latestjob/")
            ScraperType.ANSWERKEY -> scrapeTable(browser, baseUrl, type,"/answerkey/")
            ScraperType.SYLLABUS -> scrapeTable(browser, baseUrl, type,"/syllabus/")
            ScraperType.ADMISSION -> scrapeTable(browser, baseUrl, type,"/admission/")
            ScraperType.IMPORTANT -> scrapeTable(browser, baseUrl, type,"/important/")
        }
    }

}