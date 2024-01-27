package org.quickoline.scraper.scraperImpl

import org.openqa.selenium.By.ById
import org.openqa.selenium.By.ByTagName
import org.openqa.selenium.chrome.ChromeDriver
import org.quickoline.scraper.data.ScraperType

fun scrapeMarquee(browser: ChromeDriver, baseUrl: String, type: ScraperType) {

    try {
        browser.get(baseUrl)
        val scrapedDataList = mutableListOf<LinkedHashMap<String, String>>()

        val marqueeLines = browser.findElements(ById("marquee1"))
        marqueeLines.forEach { line ->

            val marquee = line.findElements(ByTagName("a"))
            marquee.map { element ->

                val title = element.text
                val url = element.getAttribute("href")

                val scrapedMap = LinkedHashMap<String, String>()
                scrapedMap["title"] = title
                scrapedMap["url"] = url

                scrapedDataList.add(scrapedMap)
                println("$title : $url")
            }
        }

        scrapeItemDetails(browser, scrapedDataList, type)

    } catch (e: Exception) {
        e.printStackTrace()
        println("Error while scraping marquee")
    }
}


