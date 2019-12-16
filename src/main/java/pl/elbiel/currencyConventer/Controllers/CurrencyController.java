package pl.elbiel.currencyConventer.Controllers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.elbiel.currencyConventer.Dao.APICallRepo;
import pl.elbiel.currencyConventer.Dao.CurrencyRepo;
import pl.elbiel.currencyConventer.Models.APICall;
import pl.elbiel.currencyConventer.Models.Currency;
import pl.elbiel.currencyConventer.Services.CurrencyService;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api/convert/")
public class CurrencyController {


    final CurrencyRepo currencyRepo;

    final CurrencyService currencyService;

    final APICallRepo apiCallRepo;

    public CurrencyController(CurrencyRepo currencyRepo, CurrencyService currencyService, APICallRepo apiCallRepo) {
        this.currencyRepo = currencyRepo;
        this.currencyService = currencyService;
        this.apiCallRepo = apiCallRepo;
    }

    @PostConstruct
    public void getValuesFromApi() throws IOException {
        //Add PLN manually since not in nbp api
        currencyRepo.save(new Currency("PLN", "polski złoty", new BigDecimal(1)));

        //Get values from nbp api
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode = mapper.readTree(new URL("http://api.nbp.pl/api/exchangerates/tables/a/")).get(0).get("rates");
        List<Currency> currency = mapper.readValue(String.valueOf(jsonNode), new TypeReference<List<Currency>>(){});
        currencyRepo.saveAll(currency);
    }

    @ModelAttribute("dbCall")
    public void dbCall(HttpServletRequest request) {
        Date date = new Date();
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        String formattedDate= dateFormat.format(date);
        apiCallRepo.save(new APICall(request.getRequestURI(), formattedDate));
    }

    @GetMapping("/all")
    public Iterable<Currency> showAllCurrencies(){
        return currencyRepo.findAll();
    }

    @GetMapping("/{id}")
    public Optional<Currency> showOneCurrency(@PathVariable(value="id") String id){
        return currencyRepo.findById(id.toUpperCase());
    }

    @GetMapping("/{id1}/{id2}")
    public Map<String,Object> convertTwoCurrencies(@PathVariable(value="id1") String id1,
                                                   @PathVariable(value="id2") String id2){
            BigDecimal result = currencyService.calculateCurrencies(currencyRepo.findById(id1.toUpperCase()),
                                                                    currencyRepo.findById(id2.toUpperCase()));
            return getStringObjectMap(id1, id2, result);
    }

    @GetMapping("/{amount}/{id1}/{id2}")
    public Map<String,Object> convertTwoCurrencies(@PathVariable(value="amount") BigDecimal amount,
                                     @PathVariable(value="id1") String id1,
                                     @PathVariable(value="id2") String id2)
    {
        BigDecimal result = currencyService.calculateCurrencies(amount,
                                        currencyRepo.findById(id1.toUpperCase()),
                                        currencyRepo.findById(id2.toUpperCase()));

        return getStringObjectMap(id1, id2,result, amount);

    }

    private Map<String, Object> getStringObjectMap(@PathVariable("id1") String id1, @PathVariable("id2") String id2, BigDecimal result) {
        Map<String,Object> json = new HashMap<>();
        json.put("firstCurrency", id1.toUpperCase());
        json.put("secondCurrency", id2.toUpperCase());
        json.put("result", result);
        return json;
    }

    private Map<String, Object> getStringObjectMap(@PathVariable("id1") String id1, @PathVariable("id2") String id2, BigDecimal result, BigDecimal amount) {
        Map json = getStringObjectMap(id1, id2, result);
        json.put("amountOfFirstCurrency", amount);
        return json;
    }
}
