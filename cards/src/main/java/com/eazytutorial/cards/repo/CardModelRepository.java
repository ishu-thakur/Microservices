package com.eazytutorial.cards.repo;

import com.eazytutorial.cards.model.CardModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardModelRepository extends JpaRepository<CardModel,String> {
}
