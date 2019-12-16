package pl.elbiel.currencyConventer.Services;

import org.springframework.stereotype.Service;
import pl.elbiel.currencyConventer.Models.Currency;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Optional;

@Service
public class CurrencyService {

    public BigDecimal calculateCurrencies (Optional<Currency> first, Optional<Currency> second){
            if(first.isPresent() && second.isPresent()){
                BigDecimal getFirst = first.get().getValue();
                BigDecimal getSecond = second.get().getValue();
                return getFirst.divide(getSecond, 7, RoundingMode.HALF_EVEN);
            }
            return null;
    }
    public BigDecimal calculateCurrencies(BigDecimal amount,Optional<Currency> first, Optional<Currency> second){
        try {
            BigDecimal result = calculateCurrencies(first, second).multiply(amount);
            return result;
        }catch (NullPointerException e){
            return null;
        }

    }
}
