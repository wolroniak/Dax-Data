package fom.wolniakhajri.wa.models;

public class Company {
    private String name;
    private String symbol;

    public Company(String name, String symbol) {
        this.name = name;
        this.symbol = symbol;
    }

    public String getName() {
        return name;
    }

    public String getSymbol() {
        return symbol;
    }
}
