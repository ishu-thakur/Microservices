package com.eazytutorial.cards.model;

import lombok.Data;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@ToString
@Entity(name = "card_data")
public class CardModel {

    @Id
    private String trackingId;
    private Long cardNumber;
    private String name;

}
