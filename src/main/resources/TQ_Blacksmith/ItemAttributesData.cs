// Decompiled with JetBrains decompiler
// Type: TQ_weaponsmith.ItemAttributesData
// Assembly: TQ_Blacksmith, Version=0.1.4.1, Culture=neutral, PublicKeyToken=null
// MVID: 1CC9040D-95C6-4C54-B248-9D80684B2CA8
// Assembly location: D:\Games\GOG Galaxy\Games\Titan Quest - Anniversary Edition\Downloads\Blacksmith\TQ_Blacksmith.exe

namespace TQ_weaponsmith
{
  public class ItemAttributesData
  {
    private ItemAttributesEffectType effectType;
    private string effect;
    private string variable;
    private string fullAttribute;
    private int suborder;

    public ItemAttributesData(
      ItemAttributesEffectType effectType,
      string attribute,
      string effect,
      string variable,
      int suborder)
    {
      this.EffectType = effectType;
      this.FullAttribute = attribute;
      this.Effect = effect;
      this.Variable = variable;
      this.Suborder = suborder;
    }

    public ItemAttributesEffectType EffectType { get; set; }

    public string Effect { get; set; }

    public string Variable { get; set; }

    public string FullAttribute { get; set; }

    public int Suborder { get; set; }
  }
}
