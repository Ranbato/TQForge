
  public enum class ItemClass(value:String)
  {
    None("None"),
    Lesser("Lesser"),
    Greater("Greater"),
    Divine("Divine"),
    Normal("Normal"),
    Epic("Epic"),
    Legendary("Legendary"),
    Rare("Rare"),
    Magical("Magical"),
    Quest("Quest");


    fun getStringVal(itemClass: ItemClass): String {
        return itemClass.value;
      }
        fun getByVal(value: String):ItemClass? = ItemClass.values().find { it.value == value }
}
