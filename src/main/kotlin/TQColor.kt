
  public enum class TQColor(val value: String)
  {
    Aqua("#00FFFF"),
    Blue("#0000FF"),
    LightCyan("#E0FFFF"),
    DarkGray("#A9A9A9"),
    Fuschia("#FF00FF"),
    Green("#008000"),
    Indigo("#4B0082"),
    Khaki("#F0E68C"),
    YellowGreen("#9ACD32"),
    Maroon("#800000"),
    Orange("#FFA500"),
    Purple("#800080"),
    Red("#FF0000"),
    Silver("#C0C0C0"),
    Turquoise("#40E0D0"),
    White("#FFFFFF"),
    Yellow("#FFFF00");

    fun getStringVal(color: TQColor): String {
      return color.value;
    }
    fun getByVal(value: String):TQColor? = TQColor.values().find { it.value == value }
  }

