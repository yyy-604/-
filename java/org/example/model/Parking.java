package org.example.model;

import java.util.Arrays;
import java.util.EnumSet;

public class Parking extends Product {
    public enum Option { PUBLIC, PRIVATE, GENERAL, ON_SITE, MOBILE, FREE, PAID };

    private String name;
    private String address;

    private EnumSet<Option> options;

    public Parking(
        String name,
        String address) {
        super();

        this.name = name;
        this.address = address;
        this.options = EnumSet.noneOf(Option.class);

    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public EnumSet<Option> getOptions() { return options; }
    public void setOptions(EnumSet<Option> options) { this.options = options; }

    public boolean hasOptions(Option... optionsToCheck) {
        return Arrays.stream(optionsToCheck).allMatch(options::contains);
    }
    public void addOptions(Option... optionsToAdd) {
        options.addAll(EnumSet.copyOf(Arrays.asList(optionsToAdd)));
    }
    public void removeOptions(Option... optionsToRemove) {
        options.removeAll(EnumSet.copyOf(Arrays.asList(optionsToRemove)));
    }
}