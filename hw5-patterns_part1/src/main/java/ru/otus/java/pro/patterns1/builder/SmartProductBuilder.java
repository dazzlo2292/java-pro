package ru.otus.java.pro.patterns1.builder;

public final class SmartProductBuilder implements ProductBuilder {
    private int id;
    private String title;
    private String description;
    private int cost;
    private int weight;
    private int width;
    private int length;
    private int height;

    @Override
    public SmartProductBuilder id(int id) {
        this.id = id;
        return this;
    }

    @Override
    public SmartProductBuilder title(String title) {
        this.title = title;
        return this;
    }

    @Override
    public SmartProductBuilder description(String description) {
        this.description = description;
        return this;
    }

    @Override
    public SmartProductBuilder cost(int cost) {
        this.cost = cost;
        return this;
    }

    @Override
    public SmartProductBuilder weight(int weight) {
        this.weight = weight;
        return this;
    }

    @Override
    public SmartProductBuilder width(int width) {
        this.width = width;
        return this;
    }

    @Override
    public SmartProductBuilder length(int length) {
        this.length = length;
        return this;
    }

    @Override
    public SmartProductBuilder height(int height) {
        this.height = height;
        return this;
    }

    public Product build() {
        return new Product(this.id, this.title, this.description, this.cost, this.weight, this.width, this.length, this.height);
    }
}
