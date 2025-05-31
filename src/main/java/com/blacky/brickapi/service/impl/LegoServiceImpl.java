package com.blacky.brickapi.service.impl;

import com.blacky.brickapi.config.LegoUrlProperties;
import com.blacky.brickapi.dto.LegoSetDto;
import com.blacky.brickapi.dto.LegoSetValuesDto;
import com.blacky.brickapi.entity.LegoInventory;
import com.blacky.brickapi.mapper.LegoSetMapper;
import com.blacky.brickapi.repository.LegoInventoryRepository;
import com.blacky.brickapi.service.LegoService;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class LegoServiceImpl implements LegoService {
    private final LegoInventoryRepository legoInventoryRepository;
    private final LegoSetMapper legoSetMapper;
    private final LegoUrlProperties legoUrlProperties;

    @Autowired
    LegoServiceImpl(LegoInventoryRepository legoInventoryRepository, LegoSetMapper legoSetMapper,
                    LegoUrlProperties legoUrlProperties) {
        this.legoInventoryRepository = legoInventoryRepository;
        this.legoSetMapper = legoSetMapper;
        this.legoUrlProperties = legoUrlProperties;
    }

    @Override
    public List<LegoSetDto> getLegoSets(String id) {
        List<LegoInventory> legoInventory = legoInventoryRepository.findAllByLegoSet_SetNumberStartsWith(id + "-");
        List<LegoSetDto> dtos = new ArrayList<>();
        for (LegoInventory inventory : legoInventory) {
            dtos.add(legoSetMapper.legoSetToLegoSetDto(inventory.getLegoSet()));
        }
        return dtos;
    }

    @Override
    @Transactional
    public List<LegoSetValuesDto> scrapRetiringSets() {
        int pagesCount = 15;
        double eur = 25.26;

        List<LegoSetValuesDto> products = new ArrayList<>();

        long startTime = System.currentTimeMillis();

        // virtual multi-threading lego.com
        try (ExecutorService virtualExecutor = Executors.newVirtualThreadPerTaskExecutor()) {
            List<Runnable> tasks = new ArrayList<>();

            for (int i = 1; i <= pagesCount; i++) {
                int page = i;
                tasks.add(() -> {
                    try {
                        scrapLegoRetiringPage(legoUrlProperties.getLegoRetiredItem() + page, products);
                    } catch (Exception e) {
                        System.err.println("Error processing product: " + page);
                        e.printStackTrace();
                    }
                });
            }
            tasks.forEach(virtualExecutor::execute);
        }
        // multi-threading lego.com end

        // virtual multi-threading brickEconomy
        try (ExecutorService virtualExecutor = Executors.newVirtualThreadPerTaskExecutor()) {
            List<Runnable> tasks = new ArrayList<>();

            for (LegoSetValuesDto product : products) {
                tasks.add(() -> {
                    try {
                        scrapBrickEconomy(product, legoUrlProperties.getBrickeconomyItem() +
                                product.getLegoSetDto().getSetNumber() + "/");
                    } catch (IOException e) {
                        System.err.println("Error processing product: " + product.getLegoSetDto().getSetNumber());
                        e.printStackTrace();
                    }
                });
            }
            tasks.forEach(virtualExecutor::execute);
        }
        // virtual multi-threading brickEconomy end

        long endTime = System.currentTimeMillis();

        System.out.println(products.size() + "products in " + (endTime - startTime) + " milliseconds");
        return products;
    }

    private void scrapLegoRetiringPage(String url, List<LegoSetValuesDto> products) {
        try {
            Document doc = Jsoup.connect(url).get();
            Elements elements = doc.select("article[data-test=product-leaf]");

            for (Element productElement : elements) {
                LegoSetValuesDto dto = new LegoSetValuesDto();
                String setNumber = productElement.attr("data-test-key");

                Element priceText = productElement.selectFirst("span[data-test=product-leaf-price]");
                if (priceText != null) {
                    dto.setPrice(parsePrice(priceText.text()));
                }

                Element salePriceText = productElement.selectFirst("span[data-test=product-leaf-discounted-price]");
                if (salePriceText != null) {
                    dto.setSalePrice(parsePrice(salePriceText.text()));
                }

                Element insiderPriceText = productElement.selectFirst("span[data-test=product-leaf-insider-price]");
                if (insiderPriceText != null) {
                    Double salePrice = Double.valueOf(insiderPriceText.text().replaceAll("[^0-9,]", "").
                            replace(",", "."));
                    dto.setSalePrice(salePrice);
                }
                dto.setSetNumber(setNumber);
                synchronized (products) { // ÚPRAVA: Synchronizace přístupu k seznamu
                    dto.setLegoSetDto(getLegoSets(setNumber).getLast());
                    products.add(dto);
                }
            }

        } catch (Exception e) {
            System.err.println("Error fetching LEGO page: " + e.getMessage());
        }
    }

    private void scrapBrickEconomy(LegoSetValuesDto set, String url) throws IOException {

        Document doc = Jsoup.connect(url)
                .ignoreHttpErrors(true)
                .userAgent("Chrome")
                .get();
        System.out.println("Set: " + set.getSetNumber() + " | URL: " + url);

        // PRICING
        Element panelSetPricing = doc.getElementById("ContentPlaceHolder1_PanelSetPricing");
        if (panelSetPricing != null) {

            Element investmentElement = panelSetPricing.selectFirst("span.label.side-box-head-label-small");
            if (investmentElement != null) {
                set.setInvestmentText(investmentElement.text());
            }
            Element pricingSection = doc.getElementById("ContentPlaceHolder1_PanelSetPricing");

            if (pricingSection != null) {
                // Najdeme všechny řádky v sekci
                Elements rows = pricingSection.select(".row.rowlist");

                // Iterujeme přes řádky a hledáme Retail Price
                for (Element row : rows) {
                    Element label = row.selectFirst(".col-xs-5.text-muted");
                    Element value = row.selectFirst(".col-xs-7");

                    if (label != null && value != null && "Retail price".equals(label.text())) {
                        String retailPriceText = value.text();
                        double retailPrice = parsePrice(retailPriceText);

                        System.out.println("Retail Price: " + retailPrice);
                        break;
                    }
                }
            }
        }

        //PREDICTIONS
        Element panelSetPredictions = doc.getElementById("ContentPlaceHolder1_PanelSetPredictions");
        if (panelSetPredictions != null) {
            
        }
    }

    private double parsePrice(String priceText) {
        try {
            String normalizedPrice = priceText.replaceAll("[^\\d.,]", "");
            normalizedPrice = normalizedPrice.replace(",", ".");
            return Double.parseDouble(normalizedPrice);
        } catch (NumberFormatException e) {
            System.err.println("Failed to parse price: " + priceText);
            return 0.0;
        }
    }
}
