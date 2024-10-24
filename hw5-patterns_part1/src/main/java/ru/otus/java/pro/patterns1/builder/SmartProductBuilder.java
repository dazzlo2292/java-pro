package ru.otus.java.pro.patterns1.builder;

public class SmartProductBuilder implements ProductBuilder {
    private int id;
    private String title;
    private String description;

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

    public Product build() {
        return new Product(this.id, this.title, this.description);
    }
}
