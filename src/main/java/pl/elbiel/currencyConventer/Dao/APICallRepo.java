package pl.elbiel.currencyConventer.Dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.elbiel.currencyConventer.Models.APICall;

@Repository
public interface APICallRepo extends CrudRepository<APICall, Integer> { }
