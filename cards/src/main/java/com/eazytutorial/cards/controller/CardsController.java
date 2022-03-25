package com.eazytutorial.cards.controller;

import com.eazytutorial.cards.config.CardsServiceConfig;
import com.eazytutorial.cards.model.CardModel;
import com.eazytutorial.cards.model.Properties;
import com.eazytutorial.cards.repo.CardModelRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CardsController {

    @Autowired
    private CardModelRepository cardModelRepository;

    @Autowired
    private CardsServiceConfig cardsServiceConfig;

    @PostMapping("add")
    public CardModel saveCard(@RequestBody CardModel cardModel) {
        return cardModelRepository.save(cardModel);
    }
    @GetMapping("/cards")
    public List<CardModel> getCards(){
        return cardModelRepository.findAll();
    }

    @GetMapping("/cards/properties")
    public String getCardsProperties() throws JsonProcessingException {
        ObjectWriter objectWriter = new ObjectMapper().writer().withDefaultPrettyPrinter();
        Properties properties = new Properties(cardsServiceConfig.getMsg(), cardsServiceConfig.getBuildVersion(), cardsServiceConfig.getMailDetails(), cardsServiceConfig.getActiveBranches());
        return objectWriter.writeValueAsString(properties);

    }
}
