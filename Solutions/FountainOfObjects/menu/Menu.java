package menu;

import java.util.List;

public class Menu<TValue> {
  private List<MenuItem<TValue>> menuItems;

  public Menu(List<MenuItem<TValue>> menuItems) {
    if (!menuItemsAreValid(menuItems)) {
      throw new IllegalArgumentException("menu must have at least one item");
    }

    this.menuItems = menuItems;
  }

  public List<MenuItem<TValue>> getMenuItems() {
    return menuItems;
  }

  public MenuItem<TValue> getMenuItem(int index) {
    if (index < 0 || index >= menuItems.size()) {
      throw new IndexOutOfBoundsException("index '" + index + "' is out of bounds");
    }

    return menuItems.get(index);
  }

  private static <TValue> boolean menuItemsAreValid(List<MenuItem<TValue>> menuItems) {
    if (menuItems == null || menuItems.size() == 0) {
      return false;
    }

    for (int i = 0; i < menuItems.size() - 1; i++) {
      for (int ii = i + 1; ii < menuItems.size(); ii++) {
        if (menuItems.get(i).equals(menuItems.get(ii))) {
          return false;
        }
      }
    }

    return true;
  }
}
