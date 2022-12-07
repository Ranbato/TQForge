// Decompiled with JetBrains decompiler
// Type: TQ_weaponsmith.ItemAttributes
// Assembly: TQ_Blacksmith, Version=0.1.4.1, Culture=neutral, PublicKeyToken=null
// MVID: 1CC9040D-95C6-4C54-B248-9D80684B2CA8
// Assembly location: D:\Games\GOG Galaxy\Games\Titan Quest - Anniversary Edition\Downloads\Blacksmith\TQ_Blacksmith.exe

using System;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.Globalization;
using System.Text;

namespace TQ_weaponsmith
{
  public static class ItemAttributes
  {
    private static readonly string[] otherEffects = new string[7]
    {
      "characterBaseAttackSpeedTag",
      "levelRequirement",
      "offensiveGlobalChance",
      "retaliationGlobalChance",
      "racialBonusPercentDamage",
      "racialBonusAbsoluteDefense",
      "itemSkillName"
    };
    private static readonly string[] characterEffects = new string[66]
    {
      "characterStrength",
      "characterDexterity",
      "characterIntelligence",
      "characterLife",
      "characterMana",
      "characterStrengthModifier",
      "characterDexterityModifier",
      "characterIntelligenceModifier",
      "characterLifeModifier",
      "characterManaModifier",
      "characterIncreasedExperience",
      "characterRunSpeed",
      "characterAttackSpeed",
      "characterSpellCastSpeed",
      "characterRunSpeedModifier",
      "characterAttackSpeedModifier",
      "characterSpellCastSpeedModifier",
      "characterTotalSpeedModifier",
      "characterLifeRegen",
      "characterManaRegen",
      "characterLifeRegenModifier",
      "characterManaRegenModifier",
      "characterOffensiveAbility",
      "characterDefensiveAbility",
      "characterOffensiveAbilityModifier",
      "characterDefensiveAbilityModifier",
      "characterDefensiveBlockRecoveryReduction",
      "characterEnergyAbsorptionPercent",
      "characterDodgePercent",
      "characterDeflectProjectile",
      "characterDeflectProjectiles",
      "characterManaLimitReserve",
      "characterManaLimitReserveReduction",
      "characterManaLimitReserveModifier",
      "characterManaLimitReserveReductionModifier",
      "characterGlobalReqReduction",
      "characterWeaponStrengthReqReduction",
      "characterWeaponDexterityReqReduction",
      "characterWeaponIntelligenceReqReduction",
      "characterMeleeStrengthReqReduction",
      "characterMeleeDexterityReqReduction",
      "characterMeleeIntelligenceReqReduction",
      "characterHuntingStrengthReqReduction",
      "characterHuntingDexterityReqReduction",
      "characterHuntingIntelligenceReqReduction",
      "characterStaffStrengthReqReduction",
      "characterStaffDexterityReqReduction",
      "characterStaffIntelligenceReqReduction",
      "characterShieldStrengthReqReduction",
      "characterShieldIntelligenceReqReduction",
      "characterShieldDexterityReqReduction",
      "characterArmorStrengthReqReduction",
      "characterArmorDexterityReqReduction",
      "characterArmorIntelligenceReqReduction",
      "characterJewelryStrengthReqReduction",
      "characterJewelryDexterityReqReduction",
      "characterJewelryIntelligenceReqReduction",
      "characterLevelReqReduction",
      "skillCooldownReduction",
      "skillCooldownReductionModifier",
      "skillManaCostReduction",
      "skillManaCostReductionModifier",
      "projectileLaunchNumber",
      "projectilePiercingChance",
      "projectileLaunchRotation",
      "skillLifeBonus"
    };
    private static readonly string[] defenseEffects = new string[67]
    {
      "defensiveProtection",
      "defensiveProtectionModifier",
      "defensiveAbsorption",
      "defensiveAbsorptionModifier",
      "defensivePhysical",
      "defensivePhysicalDuration",
      "defensivePhysicalDurationChanceModifier",
      "defensivePhysicalDurationModifier",
      "defensivePhysicalModifier",
      "defensivePierce",
      "defensivePierceDuration",
      "defensivePierceDurationModifier",
      "defensivePierceModifier",
      "defensiveFire",
      "defensiveFireDuration",
      "defensiveFireDurationModifier",
      "defensiveFireModifier",
      "defensiveCold",
      "defensiveColdDuration",
      "defensiveColdDurationModifier",
      "defensiveColdModifier",
      "defensiveLightning",
      "defensiveLightningDuration",
      "defensiveLightningDurationModifier",
      "defensiveLightningModifier",
      "defensivePoison",
      "defensivePoisonDuration",
      "defensivePoisonDurationModifier",
      "defensivePoisonModifier",
      "defensiveLife",
      "defensiveLifeDuration",
      "defensiveLifeDurationModifier",
      "defensiveLifeModifier",
      "defensiveDisruption",
      "defensiveElemental",
      "defensiveElementalModifier",
      "defensiveElementalResistance",
      "defensiveSlowLifeLeach",
      "defensiveSlowLifeLeachDuration",
      "defensiveSlowLifeLeachDurationModifier",
      "defensiveSlowLifeLeachModifier",
      "defensiveSlowManaLeach",
      "defensiveSlowManaLeachDuration",
      "defensiveSlowManaLeachDurationModifier",
      "defensiveSlowManaLeachModifier",
      "defensiveBleeding",
      "defensiveBleedingDuration",
      "defensiveBleedingDurationModifier",
      "defensiveBleedingModifier",
      "defensiveBlockModifier",
      "defensiveReflect",
      "defensiveConfusion",
      "defensiveTaunt",
      "defensiveFear",
      "defensiveConvert",
      "defensiveTrap",
      "defensivePetrify",
      "defensiveFreeze",
      "defensiveStun",
      "defensiveStunModifier",
      "defensiveSleep",
      "defensiveSleepModifier",
      "defensiveManaBurnRatio",
      "defensivePercentCurrentLife",
      "defensiveTotalSpeedResistance",
      "damageAbsorption",
      "damageAbsorptionPercent"
    };
    private static readonly string[] offensiveEffects = new string[35]
    {
      "offensiveBasePhysical",
      "offensiveBaseCold",
      "offensiveBaseFire",
      "offensiveBasePoison",
      "offensiveBaseLightning",
      "offensiveBaseLife",
      "offensivePhysical",
      "offensivePierceRatio",
      "offensivePierce",
      "offensiveCold",
      "offensiveFire",
      "offensivePoison",
      "offensiveLightning",
      "offensiveLife",
      "offensivePercentCurrentLife",
      "offensiveManaBurn",
      "offensiveDisruption",
      "offensiveLifeLeech",
      "offensiveElemental",
      "offensiveTotalDamageReductionPercent",
      "offensiveTotalDamageReductionAbsolute",
      "offensiveTotalResistanceReductionPercent",
      "offensiveTotalResistanceReductionAbsolute",
      "offensiveFumble",
      "offensiveProjectileFumble",
      "offensiveConvert",
      "offensiveTaunt",
      "offensiveFear",
      "offensiveConfusion",
      "offensiveTrap",
      "offensiveFreeze",
      "offensivePetrify",
      "offensiveStun",
      "offensiveSleep",
      "offensiveBonusPhysical"
    };
    private static readonly string[] offensiveEffectVariables = new string[10]
    {
      "Min",
      "Max",
      "Chance",
      "XOR",
      "Global",
      "DurationMin",
      "DurationMax",
      "DrainMin",
      "DrainMax",
      "DamageRatio"
    };
    private static readonly string[] offensiveModifierEffects = new string[15]
    {
      "offensivePhysicalModifier",
      "offensivePierceRatioModifier",
      "offensivePierceModifier",
      "offensiveColdModifier",
      "offensiveFireModifier",
      "offensivePoisonModifier",
      "offensiveLightningModifier",
      "offensiveLifeModifier",
      "offensiveManaBurnRatioAdder",
      "offensiveElementalModifier",
      "offensiveTotalDamageModifier",
      "offensiveStunModifier",
      "offensiveSleepModifier",
      "skillProjectileSpeedModifier",
      "sparkMaxNumber"
    };
    private static readonly string[] offensiveSlowEffects = new string[16]
    {
      "offensiveSlowPhysical",
      "offensiveSlowBleeding",
      "offensiveSlowCold",
      "offensiveSlowFire",
      "offensiveSlowPoison",
      "offensiveSlowLightning",
      "offensiveSlowLife",
      "offensiveSlowTotalSpeed",
      "offensiveSlowAttackSpeed",
      "offensiveSlowRunSpeed",
      "offensiveSlowLifeLeach",
      "offensiveSlowManaLeach",
      "offensiveSlowOffensiveAbility",
      "offensiveSlowDefensiveAbility",
      "offensiveSlowOffensiveReduction",
      "offensiveSlowDefensiveReduction"
    };
    private static readonly string[] offensiveSlowEffectVariables = new string[7]
    {
      "Min",
      "Max",
      "DurationMin",
      "DurationMax",
      "Chance",
      "XOR",
      "Global"
    };
    private static readonly string[] offensiveSlowModifierEffects = new string[16]
    {
      "offensiveSlowPhysical",
      "offensiveSlowBleeding",
      "offensiveSlowCold",
      "offensiveSlowFire",
      "offensiveSlowPoison",
      "offensiveSlowLightning",
      "offensiveSlowLife",
      "offensiveSlowTotalSpeed",
      "offensiveSlowAttackSpeed",
      "offensiveSlowRunSpeed",
      "offensiveSlowLifeLeach",
      "offensiveSlowManaLeach",
      "offensiveSlowOffensiveAbility",
      "offensiveSlowDefensiveAbility",
      "offensiveSlowOffensiveReduction",
      "offensiveSlowDefensiveReduction"
    };
    private static readonly string[] offensiveSlowModifierEffectVariables = new string[3]
    {
      "DurationModifier",
      "Modifier",
      "ModifierChance"
    };
    private static readonly string[] retaliationEffects = new string[11]
    {
      "retaliationPhysical",
      "retaliationPierceRatio",
      "retaliationPierce",
      "retaliationCold",
      "retaliationFire",
      "retaliationPoison",
      "retaliationLightning",
      "retaliationLife",
      "retaliationStun",
      "retaliationPercentCurrentLife",
      "retaliationElemental"
    };
    private static readonly string[] retaliationEffectVariables = new string[5]
    {
      "Chance",
      "Max",
      "Min",
      "Global",
      "XOR"
    };
    private static readonly string[] retaliationModifierEffects = new string[10]
    {
      "retaliationPhysicalModifier",
      "retaliationPierceRatioModifier",
      "retaliationPierceModifier",
      "retaliationColdModifier",
      "retaliationFireModifier",
      "retaliationPoisonModifier",
      "retaliationLightningModifier",
      "retaliationLifeModifier",
      "retaliationStunModifier",
      "retaliationElementalModifier"
    };
    private static readonly string[] retaliationSlowEffects = new string[15]
    {
      "retaliationSlowPhysical",
      "retaliationSlowBleeding",
      "retaliationSlowCold",
      "retaliationSlowFire",
      "retaliationSlowPoison",
      "retaliationSlowLightning",
      "retaliationSlowLife",
      "retaliationSlowTotalSpeed",
      "retaliationSlowAttackSpeed",
      "retaliationSlowRunSpeed",
      "retaliationSlowLifeLeach",
      "retaliationSlowManaLeach",
      "retaliationSlowOffensiveAbility",
      "retaliationSlowDefensiveAbility",
      "retaliationSlowOffensiveReduction"
    };
    private static readonly string[] retaliationSlowEffectVariables = new string[7]
    {
      "Chance",
      "Max",
      "Min",
      "DurationMax",
      "DurationMin",
      "Global",
      "XOR"
    };
    private static readonly string[] retaliationSlowModifierEffects = new string[15]
    {
      "retaliationSlowPhysical",
      "retaliationSlowBleeding",
      "retaliationSlowCold",
      "retaliationSlowFire",
      "retaliationSlowPoison",
      "retaliationSlowLightning",
      "retaliationSlowLife",
      "retaliationSlowTotalSpeed",
      "retaliationSlowAttackSpeed",
      "retaliationSlowRunSpeed",
      "retaliationSlowLifeLeach",
      "retaliationSlowManaLeach",
      "retaliationSlowOffensiveAbility",
      "retaliationSlowDefensiveAbility",
      "retaliationSlowOffensiveReduction"
    };
    private static readonly string[] retaliationSlowModifierEffectVariables = new string[3]
    {
      "Modifier",
      "ModifierChance",
      "DurationModifier"
    };
    private static readonly string[] reagents = new string[3]
    {
      "reagent1BaseName",
      "reagent2BaseName",
      "reagent3BaseName"
    };
    private static readonly string[] skillEffects = new string[8]
    {
      "skillManaCost",
      "skillActiveManaCost",
      "skillChanceWeight",
      "skillActiveDuration",
      "skillTargetRadius",
      "projectileExplosionRadius",
      "skillTargetAngle",
      "skillTargetNumber"
    };
    private static readonly string[] damageQualifierEffects = new string[9]
    {
      "physicalDamageQualifier",
      "pierceDamageQualifier",
      "lightningDamageQualifier",
      "fireDamageQualifier",
      "coldDamageQualifier",
      "poisonDamageQualifier",
      "lifeDamageQualifier",
      "bleedingDamageQualifier",
      "elementalDamageQualifier"
    };
    private static readonly string[] shieldEffects = new string[2]
    {
      "defensiveBlock",
      "blockRecoveryTime"
    };
    private static Dictionary<string, ItemAttributesData> attributeDictionary = ItemAttributes.InitializeAttributeDictionary();

    public static bool AttributeGroupHas(Collection<Variable> attributeList, string variableName)
    {
      foreach (Variable attribute in attributeList)
      {
        if (ItemAttributes.AttributeHas(attribute, variableName))
          return true;
      }
      return false;
    }

    public static bool AttributeGroupIs(Collection<Variable> attributeList, string effect)
    {
      ItemAttributesData attributeData = ItemAttributes.GetAttributeData(attributeList[0].Name);
      return attributeData != null && attributeData.Effect.ToUpperInvariant().Equals(effect.ToUpperInvariant());
    }

    public static bool AttributeGroupIs(
      Collection<Variable> attributeList,
      ItemAttributesEffectType type)
    {
      ItemAttributesData attributeData = ItemAttributes.GetAttributeData(attributeList[0].Name);
      return attributeData != null && attributeData.EffectType == type;
    }

    public static ItemAttributesEffectType AttributeGroupType(
      Collection<Variable> attributeList)
    {
      ItemAttributesData attributeData = ItemAttributes.GetAttributeData(attributeList[0].Name);
      return attributeData != null ? attributeData.EffectType : ItemAttributesEffectType.Other;
    }

    public static bool AttributeHas(Variable variable, string variableName)
    {
      if (variable == null)
        return false;
      ItemAttributesData attributeData = ItemAttributes.GetAttributeData(variable.Name);
      return attributeData != null && attributeData.Variable.ToUpperInvariant().Equals(variableName.ToUpperInvariant());
    }

    public static string ConvertFormat(string formatValue)
    {
      StringBuilder answer = new StringBuilder(formatValue.Length);
      int startIndex = 0;
      while (startIndex < formatValue.Length)
      {
        int num = formatValue.IndexOf('{', startIndex);
        if (num == -1)
        {
          answer.Append(formatValue, startIndex, formatValue.Length - startIndex);
          startIndex = formatValue.Length;
        }
        else
        {
          if (num > startIndex)
            answer.Append(formatValue, startIndex, num - startIndex);
          startIndex = ItemAttributes.ConvertFormatStringBrackets(formatValue, answer, num + 1);
        }
      }
      return answer.ToString();
    }

    private static int ConvertFormatStringBrackets(
      string formatString,
      StringBuilder answer,
      int startPosition)
    {
      char[] anyOf = new char[3]{ '}', '%', '^' };
      while (startPosition < formatString.Length)
      {
        int num = formatString.IndexOfAny(anyOf, startPosition);
        if (num == -1)
        {
          answer.Append(formatString, startPosition, formatString.Length - startPosition);
          return formatString.Length;
        }
        if (num > startPosition)
        {
          answer.Append(formatString, startPosition, num - startPosition);
          startPosition = num;
        }
        switch (formatString[startPosition++])
        {
          case '%':
            int startIndex = startPosition;
            while (startPosition < formatString.Length && !char.IsLetter(formatString, startPosition))
              ++startPosition;
            if (startPosition >= formatString.Length)
            {
              answer.Append(formatString, startIndex - 1, formatString.Length - (startIndex - 1));
              return formatString.Length;
            }
            string precision = formatString.Substring(startIndex, startPosition - startIndex);
            string str1 = formatString.Substring(startPosition, 1);
            string str2 = formatString.Substring(startPosition + 1, 1);
            string alpha = str1;
            string formatNumber = str2;
            string str3 = ItemAttributes.ConvertScanFormatSpec(precision, alpha, formatNumber);
            answer.Append(str3);
            startPosition += 2;
            continue;
          case '^':
            ++startPosition;
            continue;
          case '}':
            return startPosition;
          default:
            continue;
        }
      }
      return startPosition;
    }

    private static string ConvertScanFormatSpec(
      string precision,
      string alpha,
      string formatNumber)
    {
      if (alpha.Equals("s"))
        return string.Format((IFormatProvider) CultureInfo.CurrentCulture, "{{{0}}}", new object[1]
        {
          (object) formatNumber
        });
      if (alpha.Equals("d") || alpha.Equals("f"))
      {
        if (precision.Length < 1)
          return string.Format((IFormatProvider) CultureInfo.CurrentCulture, "{{{0}}}", new object[1]
          {
            (object) formatNumber
          });
        int index = 0;
        int num1 = precision[index] == '+' ? 1 : 0;
        if (num1 != 0)
          ++index;
        int num2 = precision[index] == '.' ? 1 : 0;
        string str = string.Empty;
        int result = 0;
        if (num2 != 0)
        {
          int startIndex = index + 1;
          if (!int.TryParse(precision.Substring(startIndex, 1), out result))
            result = 0;
          if (result > 0)
            str = ".".PadRight(result + 1, '0');
        }
        return num1 != 0 ? string.Format((IFormatProvider) CultureInfo.CurrentCulture, "{{{0}:+#0{1};-#0{1}}}", new object[2]
        {
          (object) formatNumber,
          (object) str
        }) : string.Format((IFormatProvider) CultureInfo.CurrentCulture, "{{{0}:#0{1}}}", new object[2]
        {
          (object) formatNumber,
          (object) str
        });
      }
      return string.Format((IFormatProvider) CultureInfo.CurrentCulture, "{{{0}}}", new object[1]
      {
        (object) formatNumber
      });
    }

    public static ItemAttributesData GetAttributeData(string attribute)
    {
      ItemAttributesData attributeData = (ItemAttributesData) null;
      if (string.IsNullOrEmpty(attribute))
        return attributeData;
      try
      {
        return ItemAttributes.attributeDictionary[attribute.ToUpperInvariant()];
      }
      catch (KeyNotFoundException ex)
      {
        return attributeData;
      }
    }

    public static string GetAttributeTextTag(string attribute)
    {
      ItemAttributesData attributeData = ItemAttributes.GetAttributeData(attribute);
      return attributeData == null ? attribute : ItemAttributes.GetAttributeTextTag(attributeData);
    }

    public static string GetAttributeTextTag(ItemAttributesData data)
    {
      string empty = string.Empty;
      if (data == null || string.IsNullOrEmpty(data.Effect))
        return empty;
      switch (data.EffectType)
      {
        case ItemAttributesEffectType.ShieldEffect:
          return ItemAttributes.GetShieldEffectTextTag(data.Effect);
        case ItemAttributesEffectType.SkillEffect:
          return ItemAttributes.GetSkillEffectTextTag(data.Effect);
        case ItemAttributesEffectType.Offense:
          return ItemAttributes.GetOffensiveEffectTextTag(data.Effect);
        case ItemAttributesEffectType.OffenseModifier:
          return ItemAttributes.GetOffensiveModifierEffectTextTag(data.Effect);
        case ItemAttributesEffectType.OffenseSlow:
          return ItemAttributes.GetOffensiveSlowEffectTextTag(data.Effect);
        case ItemAttributesEffectType.OffenseSlowModifier:
          return ItemAttributes.GetOffensiveSlowModifierEffectTextTag(data.Effect);
        case ItemAttributesEffectType.Retaliation:
          return ItemAttributes.GetRetaliationEffectTextTag(data.Effect);
        case ItemAttributesEffectType.RetaliationModifier:
          return ItemAttributes.GetRetaliationModifierEffectTextTag(data.Effect);
        case ItemAttributesEffectType.RetaliationSlow:
          return ItemAttributes.GetRetaliationSlowEffectTextTag(data.Effect);
        case ItemAttributesEffectType.RetaliationSlowModifier:
          return ItemAttributes.GetRetaliationSlowModifierEffectTextTag(data.Effect);
        case ItemAttributesEffectType.Defense:
          return ItemAttributes.GetDefenseEffectTextTag(data.Effect);
        case ItemAttributesEffectType.Character:
          return ItemAttributes.GetCharacterEffectTextTag(data.Effect);
        case ItemAttributesEffectType.Other:
          return data.Effect;
        case ItemAttributesEffectType.Reagent:
          return data.Effect;
        default:
          return data.FullAttribute;
      }
    }

    private static string GetCharacterEffectTextTag(string effect)
    {
      if (effect.ToUpperInvariant() == "CHARACTERGLOBALREQREDUCTION")
        return "CharcterItemGlobalReduction";
      return effect.ToUpperInvariant() == "CHARACTERDEFLECTPROJECTILE" ? "CharacterDeflectProjectiles" : effect;
    }

    private static string GetDefenseEffectTextTag(string effect)
    {
      switch (effect.ToUpperInvariant())
      {
        case "DEFENSIVEPROTECTION":
          return "DefenseAbsorptionProtection";
        case "DEFENSIVESLEEP":
          return "xtagDefense" + effect.Substring(9);
        case "DEFENSIVETOTALSPEEDRESISTANCE":
          return "xtagTotalSpeedResistance";
        case "DAMAGEABSORPTION":
          return "SkillDamageAbsorption";
        case "DAMAGEABSORPTIONPERCENT":
          return "SkillDamageAbsorptionPercent";
        default:
          return effect.ToUpperInvariant().StartsWith("DEFENSIVESLOW", StringComparison.Ordinal) ? "Defense" + effect.Substring(13) : "Defense" + effect.Substring(9);
      }
    }

    private static string GetOffensiveEffectTextTag(string effect)
    {
      if (effect.ToUpperInvariant().StartsWith("SKILL", StringComparison.Ordinal))
        return effect;
      switch (effect.ToUpperInvariant())
      {
        case "OFFENSIVEBASEPHYSICAL":
        case "OFFENSIVEPHYSICAL":
          return "DamageBasePhysical";
        case "OFFENSIVEFUMBLE":
          return "DamageDurationFumble";
        case "OFFENSIVEMANABURN":
          return "DamageManaDrain";
        case "OFFENSIVEPIERCERATIO":
          return "DamageBasePierceRatio";
        case "OFFENSIVEPROJECTILEFUMBLE":
          return "DamageDurationProjectileFumble";
        case "OFFENSIVESLEEP":
          return "xtagDamageSleep";
        default:
          return effect.ToUpperInvariant().StartsWith("OFFENSIVEBASE", StringComparison.Ordinal) ? "Damage" + effect.Substring(13) : "Damage" + effect.Substring(9);
      }
    }

    private static string GetOffensiveModifierEffectTextTag(string effect)
    {
      switch (effect.ToUpperInvariant())
      {
        case "OFFENSIVEMANABURNRATIOADDER":
          return "DamageModifierManaBurn";
        case "OFFENSIVESLEEPMODIFIER":
          return "xtagDamageModifierSleep";
        case "OFFENSIVETOTALDAMAGEMODIFIER":
          return "xtagDamageModifierTotalDamage";
        case "SPARKMAXNUMBER":
          return "xtagSparkMaxNumber";
        default:
          return effect.ToUpperInvariant().StartsWith("SKILL", StringComparison.Ordinal) ? effect : "DamageModifier" + effect.Substring(9, effect.Length - 17);
      }
    }

    private static string GetOffensiveSlowEffectTextTag(string effect) => "DamageDuration" + effect.Substring(13);

    private static string GetOffensiveSlowModifierEffectTextTag(string effect) => "DamageDurationModifier" + effect.Substring(13);

    private static string GetRetaliationEffectTextTag(string effect) => effect;

    private static string GetRetaliationModifierEffectTextTag(string effect) => "RetaliationModifier" + effect.Substring(11, effect.Length - 19);

    private static string GetRetaliationSlowEffectTextTag(string effect) => "RetaliationDuration" + effect.Substring(15);

    private static string GetRetaliationSlowModifierEffectTextTag(string effect) => "RetaliationDurationModifier" + effect.Substring(15);

    private static string GetShieldEffectTextTag(string effect) => effect.ToUpperInvariant() == "BLOCKRECOVERYTIME" ? "ShieldBlockRecoveryTime" : "Defense" + effect.Substring(9);

    private static string GetSkillEffectTextTag(string effect)
    {
      if (effect.ToUpperInvariant() == "SKILLCHANCEWEIGHT")
        return effect;
      return effect.ToUpperInvariant().StartsWith("PROJECTILE", StringComparison.Ordinal) ? effect.Substring(10) : effect.Substring(5);
    }

    private static Dictionary<string, ItemAttributesData> InitializeAttributeDictionary()
    {
      ItemAttributes.attributeDictionary = new Dictionary<string, ItemAttributesData>();
      int suborder1 = 0;
      foreach (string otherEffect in ItemAttributes.otherEffects)
      {
        ItemAttributes.attributeDictionary.Add(otherEffect.ToUpperInvariant(), new ItemAttributesData(ItemAttributesEffectType.Other, otherEffect, otherEffect, string.Empty, suborder1));
        ++suborder1;
      }
      int suborder2 = 0;
      foreach (string shieldEffect in ItemAttributes.shieldEffects)
      {
        string upperInvariant = shieldEffect.ToUpperInvariant();
        ItemAttributes.attributeDictionary.Add(upperInvariant, new ItemAttributesData(ItemAttributesEffectType.ShieldEffect, shieldEffect, shieldEffect, "Min", suborder2));
        ItemAttributes.attributeDictionary.Add(upperInvariant + "CHANCE", new ItemAttributesData(ItemAttributesEffectType.ShieldEffect, shieldEffect, shieldEffect, "Chance", suborder2));
        ++suborder2;
      }
      int suborder3 = 0;
      foreach (string characterEffect in ItemAttributes.characterEffects)
      {
        string upperInvariant = characterEffect.ToUpperInvariant();
        ItemAttributes.attributeDictionary.Add(upperInvariant, new ItemAttributesData(ItemAttributesEffectType.Character, characterEffect, characterEffect, "Min", suborder3));
        ItemAttributes.attributeDictionary.Add(upperInvariant + "CHANCE", new ItemAttributesData(ItemAttributesEffectType.Character, characterEffect, characterEffect, "Chance", suborder3));
        ++suborder3;
      }
      int suborder4 = 0;
      foreach (string defenseEffect in ItemAttributes.defenseEffects)
      {
        string upperInvariant = defenseEffect.ToUpperInvariant();
        ItemAttributes.attributeDictionary.Add(upperInvariant, new ItemAttributesData(ItemAttributesEffectType.Defense, defenseEffect, defenseEffect, "Min", suborder4));
        ItemAttributes.attributeDictionary.Add(upperInvariant + "CHANCE", new ItemAttributesData(ItemAttributesEffectType.Defense, defenseEffect, defenseEffect, "Chance", suborder4));
        ++suborder4;
      }
      int suborder5 = 0;
      foreach (string offensiveEffect in ItemAttributes.offensiveEffects)
      {
        foreach (string offensiveEffectVariable in ItemAttributes.offensiveEffectVariables)
          ItemAttributes.attributeDictionary.Add(offensiveEffect.ToUpperInvariant() + offensiveEffectVariable.ToUpperInvariant(), new ItemAttributesData(ItemAttributesEffectType.Offense, offensiveEffect + offensiveEffectVariable, offensiveEffect, offensiveEffectVariable, suborder5));
        ++suborder5;
      }
      int suborder6 = 0;
      foreach (string offensiveModifierEffect in ItemAttributes.offensiveModifierEffects)
      {
        string upperInvariant = offensiveModifierEffect.ToUpperInvariant();
        ItemAttributes.attributeDictionary.Add(upperInvariant, new ItemAttributesData(ItemAttributesEffectType.OffenseModifier, offensiveModifierEffect, offensiveModifierEffect, "Min", suborder6));
        ItemAttributes.attributeDictionary.Add(upperInvariant + "CHANCE", new ItemAttributesData(ItemAttributesEffectType.OffenseModifier, offensiveModifierEffect, offensiveModifierEffect, "Chance", suborder6));
        ++suborder6;
      }
      int suborder7 = 0;
      foreach (string offensiveSlowEffect in ItemAttributes.offensiveSlowEffects)
      {
        foreach (string slowEffectVariable in ItemAttributes.offensiveSlowEffectVariables)
          ItemAttributes.attributeDictionary.Add(offensiveSlowEffect.ToUpperInvariant() + slowEffectVariable.ToUpperInvariant(), new ItemAttributesData(ItemAttributesEffectType.OffenseSlow, offensiveSlowEffect + slowEffectVariable, offensiveSlowEffect, slowEffectVariable, suborder7));
        ++suborder7;
      }
      int suborder8 = 0;
      foreach (string slowModifierEffect in ItemAttributes.offensiveSlowModifierEffects)
      {
        foreach (string modifierEffectVariable in ItemAttributes.offensiveSlowModifierEffectVariables)
          ItemAttributes.attributeDictionary.Add(slowModifierEffect.ToUpperInvariant() + modifierEffectVariable.ToUpperInvariant(), new ItemAttributesData(ItemAttributesEffectType.OffenseSlowModifier, slowModifierEffect + modifierEffectVariable, slowModifierEffect, modifierEffectVariable, suborder8));
        ++suborder8;
      }
      int suborder9 = 0;
      foreach (string retaliationEffect in ItemAttributes.retaliationEffects)
      {
        foreach (string retaliationEffectVariable in ItemAttributes.retaliationEffectVariables)
          ItemAttributes.attributeDictionary.Add(retaliationEffect.ToUpperInvariant() + retaliationEffectVariable.ToUpperInvariant(), new ItemAttributesData(ItemAttributesEffectType.Retaliation, retaliationEffect + retaliationEffectVariable, retaliationEffect, retaliationEffectVariable, suborder9));
        ++suborder9;
      }
      int suborder10 = 0;
      foreach (string retaliationModifierEffect in ItemAttributes.retaliationModifierEffects)
      {
        string upperInvariant = retaliationModifierEffect.ToUpperInvariant();
        ItemAttributes.attributeDictionary.Add(upperInvariant, new ItemAttributesData(ItemAttributesEffectType.RetaliationModifier, retaliationModifierEffect, retaliationModifierEffect, "Min", suborder10));
        ItemAttributes.attributeDictionary.Add(upperInvariant + "CHANCE", new ItemAttributesData(ItemAttributesEffectType.RetaliationModifier, retaliationModifierEffect, retaliationModifierEffect, "Chance", suborder10));
        ++suborder10;
      }
      int suborder11 = 0;
      foreach (string retaliationSlowEffect in ItemAttributes.retaliationSlowEffects)
      {
        foreach (string slowEffectVariable in ItemAttributes.retaliationSlowEffectVariables)
          ItemAttributes.attributeDictionary.Add(retaliationSlowEffect.ToUpperInvariant() + slowEffectVariable.ToUpperInvariant(), new ItemAttributesData(ItemAttributesEffectType.RetaliationSlow, retaliationSlowEffect + slowEffectVariable, retaliationSlowEffect, slowEffectVariable, suborder11));
        ++suborder11;
      }
      int suborder12 = 0;
      foreach (string slowModifierEffect in ItemAttributes.retaliationSlowModifierEffects)
      {
        foreach (string modifierEffectVariable in ItemAttributes.retaliationSlowModifierEffectVariables)
          ItemAttributes.attributeDictionary.Add(slowModifierEffect.ToUpperInvariant() + modifierEffectVariable.ToUpperInvariant(), new ItemAttributesData(ItemAttributesEffectType.RetaliationSlowModifier, slowModifierEffect + modifierEffectVariable, slowModifierEffect, modifierEffectVariable, suborder12));
        ++suborder12;
      }
      int suborder13 = 0;
      foreach (string damageQualifierEffect in ItemAttributes.damageQualifierEffects)
      {
        ItemAttributes.attributeDictionary.Add(damageQualifierEffect.ToUpperInvariant(), new ItemAttributesData(ItemAttributesEffectType.DamageQualifierEffect, damageQualifierEffect, damageQualifierEffect, string.Empty, suborder13));
        ++suborder13;
      }
      int suborder14 = 0;
      foreach (string reagent in ItemAttributes.reagents)
      {
        ItemAttributes.attributeDictionary.Add(reagent.ToUpperInvariant(), new ItemAttributesData(ItemAttributesEffectType.Reagent, reagent, reagent, string.Empty, suborder14));
        ++suborder14;
      }
      int suborder15 = 0;
      foreach (string skillEffect in ItemAttributes.skillEffects)
      {
        ItemAttributes.attributeDictionary.Add(skillEffect.ToUpperInvariant(), new ItemAttributesData(ItemAttributesEffectType.SkillEffect, skillEffect, skillEffect, string.Empty, suborder15));
        ++suborder15;
      }
      return ItemAttributes.attributeDictionary;
    }

    public static bool IsReagent(string name) => Array.IndexOf<string>(ItemAttributes.reagents, name) != -1;
  }
}
