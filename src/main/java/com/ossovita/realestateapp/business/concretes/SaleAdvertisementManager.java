package com.ossovita.realestateapp.business.concretes;

import com.ossovita.realestateapp.business.abstracts.SaleAdvertisementService;
import com.ossovita.realestateapp.core.dataAccess.SaleAdvertisementRepository;
import com.ossovita.realestateapp.core.entities.SaleAdvertisement;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
@Slf4j
public class SaleAdvertisementManager implements SaleAdvertisementService {

    private RabbitTemplate rabbitTemplate;
    private DirectExchange directExchange;
    private SaleAdvertisementRepository saleAdvertisementRepository;
    private UserManager userManager;
    Random random;

    public SaleAdvertisementManager(RabbitTemplate rabbitTemplate, DirectExchange directExchange, SaleAdvertisementRepository saleAdvertisementRepository, UserManager userManager, Random random) {
        this.rabbitTemplate = rabbitTemplate;
        this.directExchange = directExchange;
        this.saleAdvertisementRepository = saleAdvertisementRepository;
        this.userManager = userManager;
        this.random = random;
    }

    @Override
    public void createDummySaleAdvertisementRequest(int piece) {
        rabbitTemplate.convertAndSend(directExchange.getName(), "saleAdvertisementRouting", piece);
    }

    @Override
    public void createDummySaleAdvertisement(int piece) {

        for (int i = 0; i < piece; i++) {
            String title = getRandomFirstWordForTitle() + " " + getRandomSecondWordForTitle();
            String description = getRandomDescription();
            long userPk = userManager.getRandomUserPk();
            SaleAdvertisement saleAdvertisement = SaleAdvertisement.builder()
                    .title(title)
                    .description(description)
                    .createdAt(LocalDateTime.now())
                    .userFk(userPk)
                    .build();
            saleAdvertisementRepository.save(saleAdvertisement);
        }
    }

    private String getRandomFirstWordForTitle() {
        List<String> firstWords = List.of("Kiralık", "Temiz", "Doktordan", "İhtiyaçtan satılık");
        return firstWords.get(random.nextInt(firstWords.size()));
    }

    private String getRandomSecondWordForTitle() {
        List<String> secondWords = List.of("Ev", "Araba", "Villa", "Arsa");
        return secondWords.get(random.nextInt(secondWords.size()));
    }

    private String getRandomDescription() {
        List<String> descriptions = List.of("İhtiyaçtan satılık", "Asansörlü", "Kombili", "Fiber altyapısı var", "Güven Emlak", "Aktaş Emlak", "Kalite güven bizim işimiz");
        List<String> selectedDescriptionList = new ArrayList<>();
        for (int i = 0; i < 3; i++) {//daha önce seçilmeyen description'lardan 3 tane seç
            String selected = descriptions.get(random.nextInt(descriptions.size()));
            if (!selectedDescriptionList.contains(selected)) {
                selectedDescriptionList.add(selected);
            }
        }
        return String.join("...", selectedDescriptionList);
    }


}
