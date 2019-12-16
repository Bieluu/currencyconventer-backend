package pl.elbiel.currencyConventer.Models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
@Table(name="currency")
public class Currency {

    @Id
    @JsonProperty("code")
    @Column(name="code")
    private String code;
    @JsonProperty("currency")
    @Column(name="currency")
    private String currency;
    @JsonProperty("mid")
    @Column(name= "value", precision=18, scale=7)
    private BigDecimal value;
}
