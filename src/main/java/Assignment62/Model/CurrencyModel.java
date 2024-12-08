package Assignment62.Model;

import javax.persistence.*;

@Entity
@Table(name = "currency")
public class CurrencyModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String abbreviation;
    private String name;
    private double conversionRate;

    public CurrencyModel() {
    }

    public CurrencyModel(String abbreviation, String name, double conversionRate) {
        this.abbreviation = abbreviation;
        this.name = name;
        this.conversionRate = conversionRate;
    }

    public int getId() {
        return id;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public String getName() {
        return name;
    }

    public double getConversionRate() {
        return conversionRate;
    }

    public double convert(double amount, CurrencyModel result) {
        return amount * (result.getConversionRate() / this.conversionRate);
    }
}
