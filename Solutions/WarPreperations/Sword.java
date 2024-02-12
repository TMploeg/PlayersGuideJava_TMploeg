public record Sword(Material material, Gemstone gemstone, double length, double crossguardWidth) {
  public static Sword defaultSword() {
    return new Sword(Material.IRON, Gemstone.NONE, 50.0, 10.0);
  }

  public Sword withMaterial(Material material) {
    return new Sword(material, gemstone(), length(), crossguardWidth());
  }

  public Sword withGemstone(Gemstone gemstone) {
    return new Sword(material(), gemstone, length(), crossguardWidth());
  }

  public Sword withLength(double length) {
    return new Sword(material(), gemstone(), length, crossguardWidth());
  }

  public Sword withCrossguardWidth(double crossguardWidth) {
    return new Sword(material(), gemstone(), length(), crossguardWidth);
  }

  @Override
  public String toString() {
    String vowels = "aeiou";

    String materialString =
        ((vowels.indexOf(material().toString().toLowerCase().charAt(0)) != -1) ? "an" : "a") + " ";
    materialString +=
        material().toString().toLowerCase() + (material() == Material.WOOD ? "en" : "");
    materialString += " sword";

    String lengthString = " of ";
    lengthString += round2Decimals(length());
    lengthString += " cm long";

    boolean hasGemstone = gemstone() != Gemstone.NONE;

    String crossguardString = (hasGemstone ? "," : " and") + " a crossguard width of ";
    crossguardString += round2Decimals(crossguardWidth());
    crossguardString += " cm";

    String gemstoneString =
        hasGemstone ? " and an embedded " + gemstone().toString().toLowerCase() : "";

    return materialString + lengthString + crossguardString + gemstoneString;
  }

  private double round2Decimals(double d) {
    return ((int) (d * 100)) / 100.0;
  }
}
