# QuickoScraper
***Kotlin project that utilizes Selenium and kotlin to scrape job and vacancy data from sarkariresult.com!***

## Features

- Scrapes job postings and vacancy details from sarkariresult.com.
- Customizable data extraction based on your needs.
- Uses Kotlin for concise and efficient code.
- Leverages Selenium for reliable web interaction.

## Usage

```kotlin
fun main() {

    val scraper = QuickoScraper()
    scraper.scrape(ScraperType.LATESTJOBS)

    //  The program will output the scraped data to JsonData folder.
} 
```

## Disclaimer

**This project is for educational purposes only. Please use this program responsibly and respect the website's terms of use and DMCA.**
