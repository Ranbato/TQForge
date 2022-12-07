// Decompiled with JetBrains decompiler
// Type: TQ_weaponsmith.ItemAttributeListCompare
// Assembly: TQ_Blacksmith, Version=0.1.4.1, Culture=neutral, PublicKeyToken=null
// MVID: 1CC9040D-95C6-4C54-B248-9D80684B2CA8
// Assembly location: D:\Games\GOG Galaxy\Games\Titan Quest - Anniversary Edition\Downloads\Blacksmith\TQ_Blacksmith.exe

using System.Collections.Generic;
using System.Collections.ObjectModel;

namespace TQ_weaponsmith
{
  internal class ItemAttributeListCompare : IComparer<List<Variable>>
  {
    private bool isArmor;

    public ItemAttributeListCompare(bool isArmor) => this.isArmor = isArmor;

    private int CalcBaseOrder(ItemAttributesEffectType effectType, int subOrder)
    {
      int num = (int) effectType;
      if (this.isArmor)
      {
        switch (effectType)
        {
          case ItemAttributesEffectType.ShieldEffect:
            num = 0;
            break;
          case ItemAttributesEffectType.Defense:
            num = 1;
            break;
          default:
            if (num < 10)
            {
              ++num;
              break;
            }
            break;
        }
      }
      return (1000 * (1 + num) + subOrder) * 10;
    }

    private int CalcOrder(List<Variable> attributes)
    {
      Variable attribute = attributes[0];
      ItemAttributesData attributeData1 = ItemAttributes.GetAttributeData(attribute.Name);
      if (attribute.Name.Equals("itemSkillName"))
        return 4000000;
      if (attributeData1 == null)
        return 3000000;
      int order = this.CalcBaseOrder(attributeData1.EffectType, attributeData1.Suborder);
      if (attributeData1.FullAttribute.Equals("characterBaseAttackSpeedTag"))
      {
        ItemAttributesData attributeData2 = ItemAttributes.GetAttributeData("offensivePierceRatioMin");
        order = this.CalcBaseOrder(attributeData2.EffectType, attributeData2.Suborder) + 1;
      }
      else if (attributeData1.FullAttribute.Equals("retaliationGlobalChance"))
        order = ItemAttributeListCompare.MakeGlobal(this.CalcBaseOrder(ItemAttributesEffectType.Retaliation, 0) - 1);
      else if (attributeData1.FullAttribute.Equals("offensiveGlobalChance"))
        order = ItemAttributeListCompare.MakeGlobal(this.CalcBaseOrder(ItemAttributesEffectType.Offense, 0) - 1);
      else if (this.isArmor && attributeData1.FullAttribute.Equals("offensivePhysicalMin"))
      {
        ItemAttributesData attributeData3 = ItemAttributes.GetAttributeData("blockRecoveryTime");
        order = this.CalcBaseOrder(attributeData3.EffectType, attributeData3.Suborder) + 1;
      }
      if (ItemAttributes.AttributeGroupHas(new Collection<Variable>((IList<Variable>) attributes), "Global"))
        order = ItemAttributeListCompare.MakeGlobal(order);
      return order;
    }

    protected int Compare(List<Variable> value1, List<Variable> value2) => this.DoCompare(value1, value2);

    private int DoCompare(List<Variable> value1, List<Variable> value2)
    {
      int num1 = this.CalcOrder(value1);
      int num2 = this.CalcOrder(value2);
      if (num1 < num2)
        return -1;
      return num1 <= num2 ? 0 : 1;
    }

    private static int MakeGlobal(int order) => order + 10000000;

    int IComparer<List<Variable>>.Compare(
      List<Variable> value1,
      List<Variable> value2)
    {
      return this.Compare(value1, value2);
    }
  }
}
