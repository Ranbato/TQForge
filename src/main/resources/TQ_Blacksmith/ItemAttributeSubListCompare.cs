// Decompiled with JetBrains decompiler
// Type: TQ_weaponsmith.ItemAttributeSubListCompare
// Assembly: TQ_Blacksmith, Version=0.1.4.1, Culture=neutral, PublicKeyToken=null
// MVID: 1CC9040D-95C6-4C54-B248-9D80684B2CA8
// Assembly location: D:\Games\GOG Galaxy\Games\Titan Quest - Anniversary Edition\Downloads\Blacksmith\TQ_Blacksmith.exe

using System.Collections.Generic;

namespace TQ_weaponsmith
{
  internal class ItemAttributeSubListCompare : IComparer<Variable>
  {
    private static int CalcBaseOrder(ItemAttributesEffectType effectType, int subOrder) => (1000 * (int) (1 + effectType) + subOrder) * 10;

    private static int CalcOrder(Variable variable)
    {
      ItemAttributesData attributeData = ItemAttributes.GetAttributeData(variable.Name);
      return attributeData == null ? 3000000 : ItemAttributeSubListCompare.CalcBaseOrder(attributeData.EffectType, attributeData.Suborder);
    }

    protected static int Compare(Variable value1, Variable value2) => ItemAttributeSubListCompare.DoCompare(value1, value2);

    private static int DoCompare(Variable value1, Variable value2)
    {
      int num1 = ItemAttributeSubListCompare.CalcOrder(value1);
      int num2 = ItemAttributeSubListCompare.CalcOrder(value2);
      if (num1 < num2)
        return -1;
      return num1 <= num2 ? 0 : 1;
    }

    int IComparer<Variable>.Compare(Variable value1, Variable value2) => ItemAttributeSubListCompare.Compare(value1, value2);
  }
}
