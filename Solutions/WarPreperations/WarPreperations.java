public class WarPreperations {
  public static void main(String[] args) {
    Sword sword = Sword.defaultSword().withMaterial(Material.STEEL).withGemstone(Gemstone.SAPPHIRE);

    System.out.println(sword);
  }
}
