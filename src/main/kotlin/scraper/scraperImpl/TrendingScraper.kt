package org.quickoline.scraper.scraperImpl

import org.openqa.selenium.By.ById
import org.openqa.selenium.By.ByTagName
import org.openqa.selenium.By.ByXPath
import org.openqa.selenium.chrome.ChromeDriver
import org.quickoline.scraper.data.ScraperType

fun scrapeTrending(browser: ChromeDriver, baseUrl: String, type: ScraperType) {

    try {
        browser.get(baseUrl)
        val scrapedDataList = mutableListOf<LinkedHashMap<String, String>>()

        val cardCount = browser.findElement(ByXPath("/html/body/div[4]/table/tbody"))
            .findElements(ByTagName("div")).size  // This may be a failure point

        for (i in 1..cardCount) {

            val trendingCard = browser.findElement(ById("image$i"))
            val title = trendingCard.text.replace("Apply Online", "").trim()
            val url = trendingCard.findElement(ByTagName("a")).getAttribute("href")

            val scrapedMap = LinkedHashMap<String, String>()
            scrapedMap["title"] = title
            scrapedMap["url"] = url

            scrapedDataList.add(scrapedMap)
            println("$title : $url")
        }

        scrapeItemDetails(browser, scrapedDataList, type)

    } catch (e: Exception) {
        e.printStackTrace()
        println("Error while scraping trendingTopics")
    }
    
}