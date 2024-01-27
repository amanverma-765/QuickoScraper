package org.quickoline

import org.quickoline.scraper.QuickoScraper
import org.quickoline.scraper.data.ScraperType

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
fun main() {

    val scraper = QuickoScraper()
    scraper.scrape(ScraperType.LATESTJOBS)
}