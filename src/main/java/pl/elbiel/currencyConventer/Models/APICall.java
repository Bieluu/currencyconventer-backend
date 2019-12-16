package pl.elbiel.currencyConventer.Models;

import lombok.Getter;
import javax.persistence.*;

@Entity
@Getter
@Table(name="apicall")
public class APICall {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id")
    int id;
    @Column(name = "apiUrl")
    String apiUrl;
    @Column(name="whenCalled")
    String whenCalled;


    public APICall(String apiUrl, String whenCalled)
    {
        this.apiUrl = apiUrl;
        this.whenCalled = whenCalled;
    }
}
