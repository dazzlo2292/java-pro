package ru.otus.java.pro.patterns2;

import ru.otus.java.pro.patterns2.db.ItemDao;

import java.util.List;

public class ItemsService {
    private final ItemDao itemDao;

    public ItemsService(ItemDao itemDao) {
        this.itemDao = itemDao;
    }

    public void createItems(int count) {
        for (int i = 1; i <= count; i++) {
            int price = (int)(Math.random() * 100 * i);
            itemDao.save(new Item(i,"Title №" + i, price));
        }
    }

    public void updateAllPrices() {
        List<Item> items = itemDao.getItemList();
        for (Item item : items) {
            int newPrice = item.price() * 2;
            itemDao.setPrice(item, newPrice);
        }
    }
}
