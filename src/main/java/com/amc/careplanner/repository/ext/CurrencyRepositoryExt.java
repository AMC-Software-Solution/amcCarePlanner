package com.amc.careplanner.repository.ext;

import com.amc.careplanner.domain.Currency;
import com.amc.careplanner.repository.CurrencyRepository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Currency entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CurrencyRepositoryExt extends CurrencyRepository{
}
