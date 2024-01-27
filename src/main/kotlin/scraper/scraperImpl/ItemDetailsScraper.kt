package org.quickoline.scraper.scraperImpl

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.openqa.selenium.By.*
import org.openqa.selenium.WebElement
import org.openqa.selenium.chrome.ChromeDriver
import org.quickoline.scraper.data.ScraperType
import java.io.File

fun scrapeItemDetails(browser: ChromeDriver, initialDataList: MutableList<LinkedHashMap<String, String>>, scraperType: ScraperType) {

    val finalDataList = mutableListOf<LinkedHashMap<String, String>>()

    try {
        println("detailed scraping started :)")
        initialDataList.forEach { scrapedMap ->

            browser.get(scrapedMap["url"])
            val xPath = "/html/body/div/div[1]/table/tbody/tr/td[1]"

            val mainTableList = browser.findElement(ByXPath(xPath)).findElements(ByTagName("tbody"))

            val tableOne = mainTableList[0].findElements(ByTagName("tr"))
            val tableTwo = mainTableList[1].findElements(ByTagName("tr"))

            val combinedTable = scrapedMap + scrapTableOne(tableOne) + scrapTableTwo(tableTwo)

            finalDataList.add(combinedTable as LinkedHashMap<String, String>)
        }

        val jsonString = Json.encodeToString(finalDataList)
        File("JsonData/${scraperType.toString().lowercase()}.json").writeText(jsonString)

    } catch (e: Exception) {
        e.printStackTrace()
        println("Error while scraping details")
    } finally {
        browser.quit()
    }
}

fun scrapTableOne(tableOne: MutableList<WebElement>): LinkedHashMap<String, String> {

    val tableOneMap = LinkedHashMap<String, String>()

    try {

        tableOne.forEach { element ->

            val base = element.findElements(ByTagName("td"))
            val key = base[0].text.trim(':').lowercase()
            val value = base[1].text.trim().lowercase()

            tableOneMap[key] = value
        }
    } catch (e: Exception) {
        e.printStackTrace()
        println("Error while scraping detailsTable1")
    }
    return tableOneMap
}


fun scrapTableTwo(tableTwo: MutableList<WebElement>): LinkedHashMap<String, String> {

    val basicDetailsMap = LinkedHashMap<String, String>()

    try {

        for (i in 1..2) {
            // Table2-1
            val basicDetailsList = tableTwo[i].findElements(ByTagName("td"))
            basicDetailsList.forEach { element ->

                if ((i == 2 && element.text.lowercase().contains("age limit")) || i == 1) {

                    element.findElements(tagName("li")).forEach {

                        val splitedDetails = it.text.split(":")

                        var key = ""
                        var value = ""
                        if (splitedDetails.size == 2) {
                            key = splitedDetails[0]
                            value = splitedDetails[1]
                        } else {
                            key = splitedDetails[0]
                        }
                        basicDetailsMap[key] = value
                    }
                }
            }
        }

    } catch (e: Exception) {
        e.printStackTrace()
        println("Error while scraping detailsTable2")
    }
    return basicDetailsMap
}