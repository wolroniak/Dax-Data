package fom.wolniakhajri.wa.models;

public class Company {
    private final String name;
    private final String symbol;

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
