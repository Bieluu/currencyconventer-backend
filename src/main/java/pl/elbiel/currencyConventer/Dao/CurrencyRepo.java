package pl.elbiel.currencyConventer.Dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.elbiel.currencyConventer.Models.Currency;

import java.math.BigDecimal;

@Repository
public interface CurrencyRepo extends CrudRepository<Currency, String>{
    @Query("SELECT value FROM Currency where code = ?1")
    BigDecimal findValueById(String id);
}