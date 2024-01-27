package org.quickoline.scraper.scraperImpl

import org.openqa.selenium.By.ById
import org.openqa.selenium.By.ByTagName
import org.openqa.selenium.chrome.ChromeDriver
import org.quickoline.scraper.data.ScraperType

fun scrapeTable(browser: ChromeDriver, baseUrl: String, type: ScraperType, path: String) {

    try {
        browser.get(baseUrl + path)
        val scrapedDataList = mutableListOf<LinkedHashMap<String, String>>()

        val tableItemsList = browser.findElement(ById("post"))
            .findElements(ByTagName("ul")).take(50) // Picks only top 150 items

        tableItemsList.forEach { element ->

            val baseTag = element.findElement(ByTagName("a"))
            val title = baseTag.text
            val url = baseTag.getAttribute("href")

            val scrapedMap = LinkedHashMap<String, String>()
            scrapedMap["title"] = title
            scrapedMap["url"] = url

            scrapedDataList.add(scrapedMap)
            println("$title : $url")
        }

        scrapeItemDetails(browser, scrapedDataList, type)

    } catch (e: Exception) {
        e.printStackTrace()
    }

}