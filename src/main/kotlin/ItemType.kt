
  public enum class ItemType(val value: String)
  {
    Armor(""),
    Weapon(""),
   Ring("ArmorJewelry_Ring"),
   Amulet("ArmorJewelry_Amulet"),
   Relic("ItemRelic"),
   Charm("ItemCharm"),
   Formula("ItemArtifactFormula"),
   Artifact("ItemArtifact"),
   Scroll("OneShot_Scroll"),
   Quest("QuestItem"),
   Parchment("ItemEquipment"),
   Misc("Misc"),
   Head("ArmorProtective_Head"),
   Chest("ArmorProtective_UpperBody"),
   Arm("ArmorProtective_Forearm"),
   Leg("ArmorProtective_LowerBody"),
   Sword("WeaponMelee_Sword"),
   Axe("WeaponMelee_Axe"),
   Mace("WeaponMelee_Mace"),
   Bow("WeaponHunting_Bow"),
   Spear("WeaponHunting_Spear"),
   Staff("WeaponMagical_Staff"),
   Shield("WeaponArmor_Shield"),
   Thrown("WeaponHunting_RangedOneHand");

    fun getStringVal(itemType: ItemType): String {
      return itemType.value;
    }
      fun getByVal(value: String):ItemType? = ItemType.values().find { it.value == value }
    }


