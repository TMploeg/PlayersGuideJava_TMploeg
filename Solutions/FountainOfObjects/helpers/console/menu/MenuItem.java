package helpers.console.menu;

import java.util.Objects;

public class MenuItem<TValue> {
  private String name;
  private TValue value;

  public MenuItem(String name, TValue value) {
    if (name == null || name.length() == 0) {
      throw new IllegalArgumentException("name must have at least one character");
    }

    this.name = name;
    this.value = value;
  }

  public String getName() {
    return name;
  }

  public TValue getValue() {
    return value;
  }

  @Override
  public String toString() {
    return getName();
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }

    if (obj instanceof MenuItem) {
      return this.getName().equals(((MenuItem) obj).getName());
    }

    return false;
  }
  
  @Override
  public int hashCode(){
	return Objects.hash(name, value);
  }
}
