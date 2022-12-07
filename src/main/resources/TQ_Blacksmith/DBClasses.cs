// Decompiled with JetBrains decompiler
// Type: TQ_weaponsmith.DBClasses
// Assembly: TQ_Blacksmith, Version=0.1.4.1, Culture=neutral, PublicKeyToken=null
// MVID: 1CC9040D-95C6-4C54-B248-9D80684B2CA8
// Assembly location: D:\Games\GOG Galaxy\Games\Titan Quest - Anniversary Edition\Downloads\Blacksmith\TQ_Blacksmith.exe

using System.Collections.Generic;
using System.Linq;

namespace TQ_weaponsmith
{
  internal class DBClasses
  {
    private static string[] arr = new string[25]
    {
      "ARMORJEWELRY_AMULET",
      "ARMORJEWELRY_BRACELET",
      "ARMORJEWELRY_RING",
      "ARMORPROTECTIVE_FOREARM",
      "ARMORPROTECTIVE_HEAD",
      "ARMORPROTECTIVE_LOWERBODY",
      "ARMORPROTECTIVE_UPPERBODY",
      "ITEMARTIFACT",
      "ITEMARTIFACTFORMULA",
      "ITEMCHARM",
      "ITEMEQUIPMENT",
      "ITEMRELIC",
      "PROJECTILEARROWLIKE",
      "QUESTITEM",
      "WEAPONARMOR_SHIELD",
      "WEAPONHUNTING_BOW",
      "WEAPONHUNTING_RANGEDONEHAND",
      "WEAPONHUNTING_SPEAR",
      "WEAPONMAGICAL_STAFF",
      "WEAPONMELEE_AXE",
      "WEAPONMELEE_MACE",
      "WEAPONMELEE_SWORD",
      "ONESHOT_SCROLL",
      "LOOTRANDOMIZER",
      "LOOTRANDOMIZERTABLE"
    };
    private static readonly List<string> classes = new List<string>();

    public static List<string> getClasses()
    {
      List<string> classes = DBClasses.classes;
      if (classes.Count <= 0)
        classes.AddRange((IEnumerable<string>) ((IEnumerable<string>) DBClasses.arr).ToArray<string>());
      return DBClasses.classes;
    }
  }
}
