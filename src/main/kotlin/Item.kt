import org.jetbrains.skia.Bitmap
import java.io.File
import java.io.DataInputStream
import java.io.ObjectOutputStream
import java.io.OutputStream
import kotlin.io.path.extension
import kotlin.math.roundToInt
import kotlin.random.Random

public class Item
  {
      public var BaseItemId: String = ""
      private set
      private var beginBlockCrap1 = 0
    private var endBlockCrap1 = 0
    private var beginBlockCrap2 = 0
    private var endBlockCrap2 = 0
    private var prefixID = "";
    private var suffixID = "";
    private var relicID = "";
    private var relicID2 = "";
    private var baseItemInfo:Info? = null
    private var prefixInfo:Info? = null
    private var suffixInfo:Info? = null
    private var attributesString = "";
    private var requirementsString = "";
    private var setItemsString = "";
    private var attributeCount = 0
//    private bool isAttributeCounted;
//    private string[] bareAttributes = new string[6];
//    private float itemScalePercent = 1f;
//    private static string itemWith;
//    private static string itemRelicBonus;
//    private static string itemRelicCompleted;
//    private static string itemQuest;
//    private static string itemSeed;
//    private static string itemIT;
//    private static string itemRAG;
//    private static bool showSkillLevel;
//    public string baseItemId;
    private var RelicBonusId = "";
    private var RelicBonusId2 = "";
    private var seed = 0
    private var var1 = 0
    private var var2 = 0
    private var isSecondBonus = false
     var StackSize = 1;
     var PositionX = 0
     var PositionY = 0
    private var relicInfo:Info? = null
    private var relicBonusInfo:Info? = null
    private var itemBitmap: Bitmap? = null
    private var width = 0
    private var height = 0
     var  sackType:SackType = SackType.Sack
    public var recordType = ""
    public var itemClassification = ""
    public var descriptionTag = ""
//
//    public Item()
//    {
//      this.sackType = SackType.Sack;
//      this.positionX = this.positionY = 0;
//      Item.SetDefaultStrings();
//    }
//
//    public Item(string id, string recordType, string classification, string descriptionTag)
//    {
//      Item.SetDefaultStrings();
//      this.baseItemId = id.uppercase();
//      this.recordType = recordType.uppercase();
//      this.itemClassification = classification.uppercase();
//      this.descriptionTag = descriptionTag;
//    }
//
//    public string getFriendlyName() => Database.GetFriendlyName(this.baseItemInfo.DescriptionTag);
//


    private fun checkSubstring( substring:String):Boolean = this.hasSubstring(this.BaseItemId, substring) || this.hasSubstring(this.prefixID, substring) || this.hasSubstring(this.suffixID, substring);

      //
//    public Item Clone()
//    {
//      Item obj = (Item) this.MemberwiseClone();
//      obj.MarkModified();
//      return obj;
//    }
//
//    private void ConvertAttributeListToString(
//      DBRecord record,
//      List<Variable> attributeList,
//      string recordId,
//      List<string> results)
//    {
//      Variable attribute = attributeList[0];
//      ItemAttributesData data = ItemAttributes.GetAttributeData(attribute.Name);
//      if (data == null)
//      {
//        Logger.Debug("ConvertAttributeListToString - attribute data null for " + attribute.Name);
//        data = new ItemAttributesData(ItemAttributesEffectType.Other, attribute.Name, attribute.Name, string.Empty, 0);
//      }
//      this.ConvertOffenseAttributesToString(record, attributeList, data, recordId, results);
//    }
//
//    private static void ConvertBareAttributeListToString(
//      List<Variable> attributeList,
//      List<string> results)
//    {
//      foreach (Variable attribute in attributeList)
//      {
//        if (attribute != null)
//          results.Add(attribute.ToString());
//      }
//    }
//
//    private void ConvertOffenseAttributesToString(
//      DBRecord record,
//      List<Variable> attributeList,
//      ItemAttributesData data,
//      string recordId,
//      List<string> results)
//    {
//      int num = 0;
//      if (this.IsRelic && recordId == this.BaseItemId)
//        num = this.Number - 1;
//      else if (this.HasRelic && recordId == this.relicID)
//        num = Math.Max(this.Var1, 1) - 1;
//      if (this.IsScroll || this.IsRelic)
//        num = this.GetPetSkillLevel(record, recordId, num);
//      if (record.GetString("Class", 0).uppercase().StartsWith("SKILL", StringComparison.OrdinalIgnoreCase))
//        num = this.GetTriggeredSkillLevel(record, recordId, num);
//      ItemAttributesData itemAttributesData1 = (ItemAttributesData) null;
//      ItemAttributesData itemAttributesData2 = (ItemAttributesData) null;
//      ItemAttributesData itemAttributesData3 = (ItemAttributesData) null;
//      ItemAttributesData itemAttributesData4 = (ItemAttributesData) null;
//      ItemAttributesData itemAttributesData5 = (ItemAttributesData) null;
//      ItemAttributesData modifierData = (ItemAttributesData) null;
//      ItemAttributesData itemAttributesData6 = (ItemAttributesData) null;
//      ItemAttributesData itemAttributesData7 = (ItemAttributesData) null;
//      ItemAttributesData damageRatioData = (ItemAttributesData) null;
//      Variable minVar = (Variable) null;
//      Variable maxVar = (Variable) null;
//      Variable minDurVar = (Variable) null;
//      Variable maxDurVar = (Variable) null;
//      Variable chanceVar = (Variable) null;
//      Variable modifierVar = (Variable) null;
//      Variable durationModifierVar = (Variable) null;
//      Variable modifierChanceVar = (Variable) null;
//      Variable damageRatioVar = (Variable) null;
//      bool isGlobal = ItemAttributes.AttributeGroupHas(new Collection<Variable>((IList<Variable>) attributeList), "Global");
//      string globalIndent = (string) null;
//      if (isGlobal)
//        globalIndent = "&nbsp;&nbsp;&nbsp;&nbsp;";
//      foreach (Variable attribute in attributeList)
//      {
//        if (attribute != null)
//        {
//          ItemAttributesData itemAttributesData8 = ItemAttributes.GetAttributeData(attribute.Name) ?? new ItemAttributesData(ItemAttributesEffectType.Other, attribute.Name, attribute.Name, string.Empty, 0);
//          switch (itemAttributesData8.Variable.uppercase())
//          {
//            case "CHANCE":
//              itemAttributesData5 = itemAttributesData8;
//              chanceVar = attribute;
//              continue;
//            case "DAMAGERATIO":
//              damageRatioData = itemAttributesData8;
//              damageRatioVar = attribute;
//              continue;
//            case "DRAINMAX":
//              itemAttributesData2 = itemAttributesData8;
//              maxVar = attribute;
//              continue;
//            case "DRAINMIN":
//              itemAttributesData1 = itemAttributesData8;
//              minVar = attribute;
//              continue;
//            case "DURATIONMAX":
//              itemAttributesData4 = itemAttributesData8;
//              maxDurVar = attribute;
//              continue;
//            case "DURATIONMIN":
//              itemAttributesData3 = itemAttributesData8;
//              minDurVar = attribute;
//              continue;
//            case "DURATIONMODIFIER":
//              itemAttributesData6 = itemAttributesData8;
//              durationModifierVar = attribute;
//              continue;
//            case "MAX":
//              itemAttributesData2 = itemAttributesData8;
//              maxVar = attribute;
//              continue;
//            case "MIN":
//              itemAttributesData1 = itemAttributesData8;
//              minVar = attribute;
//              continue;
//            case "MODIFIER":
//              modifierData = itemAttributesData8;
//              modifierVar = attribute;
//              continue;
//            case "MODIFIERCHANCE":
//              itemAttributesData7 = itemAttributesData8;
//              modifierChanceVar = attribute;
//              continue;
//            default:
//              continue;
//          }
//        }
//      }
//      string labelTag = (string) null;
//      string labelColor = (string) null;
//      string label = ItemAttributes.ConvertFormat(this.GetLabelAndColorFromTag(data, recordId, ref labelTag, ref labelColor));
//      string str1 = itemAttributesData1 == null || itemAttributesData2 == null || (double) minVar.GetSingle(Math.Min(minVar.NumberOfValues - 1, num)) == (double) maxVar.GetSingle(Math.Min(maxVar.NumberOfValues - 1, num)) ? this.GetAmountSingle(data, num, minVar, maxVar, ref label, labelColor) : this.GetAmountRange(data, num, minVar, maxVar, ref label, labelColor);
//      string str2 = itemAttributesData3 == null || itemAttributesData4 == null ? Item.GetDurationSingle(num, minDurVar, maxDurVar) : Item.GetDurationRange(num, minDurVar, maxDurVar);
//      string str3 = (string) null;
//      if (damageRatioData != null)
//        str3 = Item.GetDamageRatio(num, damageRatioData, damageRatioVar);
//      string str4 = (string) null;
//      if (itemAttributesData5 != null)
//        str4 = Item.GetChance(num, chanceVar);
//      string[] strArray = new string[5];
//      int count1 = 0;
//      if (!string.IsNullOrEmpty(label))
//      {
//        label = Database.MakeSafeForHtml(label);
//        if (!string.IsNullOrEmpty(labelColor))
//          label = Item.Format("<font color={0}>{1}</font>", (object) labelColor, (object) label);
//      }
//      if (!string.IsNullOrEmpty(str4))
//        strArray[count1++] = str4;
//      if (!string.IsNullOrEmpty(str1))
//        strArray[count1++] = str1;
//      if (!string.IsNullOrEmpty(label))
//        strArray[count1++] = label;
//      if (!string.IsNullOrEmpty(str2))
//        strArray[count1++] = str2;
//      if (!string.IsNullOrEmpty(str3))
//        strArray[count1++] = str3;
//      if (!string.IsNullOrEmpty(str1) || !string.IsNullOrEmpty(str2))
//      {
//        string str5 = string.Join(" ", strArray, 0, count1);
//        string str6 = (string) null;
//        if (!isGlobal && (string.IsNullOrEmpty(str4) || data.Effect.Equals("defensiveBlock")) && recordId == this.BaseItemId && string.IsNullOrEmpty(str2) && !string.IsNullOrEmpty(str1))
//        {
//          if (this.IsWeapon && (data.Effect.Equals("offensivePierceRatio") || data.Effect.Equals("offensivePhysical") || data.Effect.Equals("offensiveBaseFire") || data.Effect.Equals("offensiveBaseCold") || data.Effect.Equals("offensiveBaseLightning") || data.Effect.Equals("offensiveBaseLife")))
//            str6 = Database.HtmlColor(Item.GetColor(ItemStyle.Mundane));
//          if (this.IsShield && (data.Effect.Equals("defensiveBlock") || data.Effect.Equals("blockRecoveryTime") || data.Effect.Equals("offensivePhysical")))
//            str6 = Database.HtmlColor(Item.GetColor(ItemStyle.Mundane));
//        }
//        if (string.IsNullOrEmpty(str6))
//          str6 = Database.HtmlColor(Item.GetColor(ItemStyle.Epic));
//        string str7 = string.Format((IFormatProvider) CultureInfo.CurrentCulture, "<font color={0}>{1}</font>", new object[2]
//        {
//          (object) str6,
//          (object) str5
//        });
//        if (isGlobal)
//          str7 = globalIndent + str7;
//        results.Add(str7);
//      }
//      else
//      {
//        str1 = (string) null;
//        str2 = (string) null;
//        str4 = (string) null;
//      }
//      string str8 = (string) null;
//      if (modifierData != null)
//        str8 = Item.GetModifier(data, num, modifierData, modifierVar);
//      string str9 = (string) null;
//      if (itemAttributesData6 != null)
//        str9 = Item.GetDurationModifier(num, durationModifierVar);
//      string str10 = (string) null;
//      if (itemAttributesData7 != null)
//        str10 = Item.GetChanceModifier(num, modifierChanceVar);
//      int count2 = 0;
//      if (!string.IsNullOrEmpty(str10))
//        strArray[count2++] = str10;
//      if (!string.IsNullOrEmpty(str8))
//        strArray[count2++] = str8;
//      if (!string.IsNullOrEmpty(str9))
//        strArray[count2++] = str9;
//      if (!string.IsNullOrEmpty(str8))
//      {
//        string parameter2 = string.Join(" ", strArray, 0, count2);
//        string str11 = Item.Format("<font color={0}>{1}</font>", (object) Database.HtmlColor(Item.GetColor(ItemStyle.Epic)), (object) parameter2);
//        if (isGlobal)
//          str11 = globalIndent + str11;
//        results.Add(str11);
//      }
//      else
//      {
//        str8 = (string) null;
//        str10 = (string) null;
//        str9 = (string) null;
//      }
//      bool flag = true;
//      foreach (Variable attribute in attributeList)
//      {
//        if (attribute != null)
//        {
//          ItemAttributesData itemAttributesData9 = ItemAttributes.GetAttributeData(attribute.Name) ?? new ItemAttributesData(ItemAttributesEffectType.Other, attribute.Name, attribute.Name, string.Empty, 0);
//          string upperInvariant1 = itemAttributesData9.Variable.uppercase();
//          if ((str1 == null || upperInvariant1 != "MIN" && upperInvariant1 != "MAX" && upperInvariant1 != "DRAINMIN" && itemAttributesData9.Variable != "DRAINMAX") && (str2 == null || upperInvariant1 != "DURATIONMIN" && upperInvariant1 != "DURATIONMAX") && (str4 == null || upperInvariant1 != "CHANCE") && (str8 == null || upperInvariant1 != "MODIFIER") && (str9 == null || upperInvariant1 != "DURATIONMODIFIER") && (str10 == null || upperInvariant1 != "MODIFIERCHANCE") && (str3 == null || upperInvariant1 != "DAMAGERATIO") && upperInvariant1 != "GLOBAL" && !(upperInvariant1 == "XOR" & isGlobal))
//          {
//            string str12 = (string) null;
//            string font = (string) null;
//            string upperInvariant2 = itemAttributesData9.FullAttribute.uppercase();
//            if (upperInvariant2 == "CHARACTERBASEATTACKSPEEDTAG")
//            {
//              if (this.IsWeapon && recordId == this.BaseItemId)
//              {
//                font = string.Format((IFormatProvider) CultureInfo.CurrentCulture, "<font color={0}>", new object[1]
//                {
//                  (object) Database.HtmlColor(Item.GetColor(ItemStyle.Mundane))
//                });
//                str12 = Database.GetFriendlyName(attribute.GetString(0));
//              }
//              else
//                str12 = string.Empty;
//            }
//            else if (upperInvariant2.EndsWith("GLOBALCHANCE", StringComparison.OrdinalIgnoreCase))
//              str12 = Item.GetGlobalChance(attributeList, num, attribute, ref font);
//            else if (upperInvariant2.StartsWith("RACIALBONUS", StringComparison.OrdinalIgnoreCase))
//              str12 = Item.GetRacialBonus(record, results, num, isGlobal, globalIndent, attribute, itemAttributesData9, str12, ref font);
//            else if (upperInvariant2 == "AUGMENTALLLEVEL")
//              str12 = Item.GetAugmentAllLevel(num, attribute, ref font);
//            else if (upperInvariant2.StartsWith("AUGMENTMASTERYLEVEL", StringComparison.OrdinalIgnoreCase))
//              str12 = Item.GetAugmentMasteryLevel(record, attribute, itemAttributesData9, ref font);
//            else if (upperInvariant2.StartsWith("AUGMENTSKILLLEVEL", StringComparison.OrdinalIgnoreCase))
//              str12 = Item.GetAugmentSkillLevel(record, attribute, itemAttributesData9, str12, ref font);
//            else if (this.IsFormulae && recordId == this.BaseItemId)
//              str12 = Item.GetFormulae(results, attribute, itemAttributesData9, str12, ref font);
//            else if (upperInvariant2 == "ITEMSKILLNAME")
//              str12 = Item.GetGrantedSkill(record, results, attribute, str12, ref font);
//            if (upperInvariant2 == "PETBONUSNAME")
//              str12 = Item.GetPetBonusName(ref font);
//            if (recordId == this.BaseItemId && upperInvariant2 == "ATTRIBUTESCALEPERCENT" && (double) this.itemScalePercent == 1.0)
//            {
//              this.itemScalePercent += attribute.GetSingle(0) / 100f;
//              str12 = string.Empty;
//            }
//            else if (upperInvariant2 == "SKILLNAME")
//              str12 = string.Empty;
//            else if (itemAttributesData9.EffectType == ItemAttributesEffectType.SkillEffect)
//              str12 = Item.GetSkillEffect(data, num, attribute, itemAttributesData9, str12, ref font);
//            else if (upperInvariant2.EndsWith("DAMAGEQUALIFIER", StringComparison.OrdinalIgnoreCase))
//            {
//              string str13 = Database.GetFriendlyName("tagDamageAbsorptionTitle");
//              if (string.IsNullOrEmpty(str13))
//                str13 = "Protects Against :";
//              if (flag)
//              {
//                results.Add(str13);
//                flag = false;
//              }
//              string str14 = itemAttributesData9.FullAttribute.Remove(itemAttributesData9.FullAttribute.Length - 15);
//              string str15 = str14.Substring(0, 1).uppercase() + str14.Substring(1);
//              string friendlyName1 = Database.GetFriendlyName("tagQualifyingDamage" + str15);
//              string friendlyName2 = Database.GetFriendlyName("formatQualifyingDamage");
//              string formatSpec = !string.IsNullOrEmpty(friendlyName2) ? ItemAttributes.ConvertFormat(friendlyName2) : "{     0}";
//              font = string.Format((IFormatProvider) CultureInfo.CurrentCulture, "<font color={0}>", new object[1]
//              {
//                (object) Database.HtmlColor(Item.GetColor(ItemStyle.Mundane))
//              });
//              str12 = Item.Format(formatSpec, (object) friendlyName1);
//            }
//            if (str12 == null)
//              str12 = Item.GetRawAttribute(data, num, attribute, ref font);
//            if (str12.Length > 0)
//            {
//              str12 = Database.MakeSafeForHtml(str12);
//              if (itemAttributesData9.Variable.Length > 0)
//              {
//                string str16 = Database.MakeSafeForHtml(itemAttributesData9.Variable);
//                string str17 = string.Format((IFormatProvider) CultureInfo.CurrentCulture, " <font color={0}>{1}</font>", new object[2]
//                {
//                  (object) Database.HtmlColor(Item.GetColor(ItemStyle.Legendary)),
//                  (object) str16
//                });
//                str12 += str17;
//              }
//              if (upperInvariant2 == "ITEMSKILLNAME" && !string.IsNullOrEmpty(record.GetString("itemSkillAutoController", 0)))
//                str12 = "<b>" + str12 + "</b>";
//              if (font != null)
//                str12 = font + str12 + "</font>";
//              if (isGlobal)
//                str12 = globalIndent + str12;
//              if (this.IsFormulae && upperInvariant2.StartsWith("REAGENT", StringComparison.OrdinalIgnoreCase))
//                str12 = "&nbsp;&nbsp;&nbsp;&nbsp;" + str12;
//              results.Add(str12);
//            }
//            if (upperInvariant2 == "PETBONUSNAME")
//            {
//              string str18 = record.GetString("petBonusName", 0);
//              DBRecord recordFromFile = Database.GetRecordFromFile(str18);
//              if (recordFromFile != null)
//              {
//                this.GetAttributesFromRecord(recordFromFile, true, str18, results);
//                results.Add(string.Empty);
//              }
//            }
//            if (upperInvariant2 == "ITEMSKILLNAME" || this.IsScroll && upperInvariant2 == "SKILLNAME")
//              this.GetSkillDescriptionAndEffects(record, results, attribute, str12);
//          }
//        }
//      }
//    }
//
//    private void ConvertPetStats(DBRecord skillRecord, List<string> results)
//    {
//      int int32 = skillRecord.GetInt32("petLimit", 0);
//      if (int32 > 1)
//      {
//        string friendlyName = Database.GetFriendlyName("SkillPetLimit");
//        string str = string.Format((IFormatProvider) CultureInfo.CurrentCulture, !string.IsNullOrEmpty(friendlyName) ? ItemAttributes.ConvertFormat(friendlyName) : "{0} Summon Limit", new object[1]
//        {
//          (object) int32.ToString((IFormatProvider) CultureInfo.CurrentCulture)
//        });
//        object[] objArray = new object[2]
//        {
//          (object) Database.HtmlColor(Item.GetColor(ItemStyle.Mundane)),
//          (object) str
//        };
//        results.Add(string.Format((IFormatProvider) CultureInfo.CurrentCulture, "<font color={0}>{1}</font>", objArray));
//      }
//      DBRecord recordFromFile1 = Database.GetRecordFromFile(skillRecord.GetString("spawnObjects", 0));
//      if (recordFromFile1 == null)
//        return;
//      string friendlyName1 = Database.GetFriendlyName("SkillPetDescriptionHeading");
//      string str1 = !string.IsNullOrEmpty(friendlyName1) ? ItemAttributes.ConvertFormat(friendlyName1) : "{0} Attributes:";
//      string tagId1 = recordFromFile1.GetString("description", 0);
//      string friendlyName2 = Database.GetFriendlyName(tagId1);
//      object[] objArray1 = new object[1]
//      {
//        (object) friendlyName2
//      };
//      string str2 = string.Format((IFormatProvider) CultureInfo.CurrentCulture, friendlyName2, objArray1);
//      object[] objArray2 = new object[2]
//      {
//        (object) Database.HtmlColor(Item.GetColor(ItemStyle.Mundane)),
//        (object) str2
//      };
//      results.Add(string.Format((IFormatProvider) CultureInfo.CurrentCulture, "<font color={0}>{1}</font>", objArray2));
//      string friendlyName3 = Database.GetFriendlyName("tagSkillPetTimeToLive");
//      string str3 = string.Format((IFormatProvider) CultureInfo.CurrentCulture, !string.IsNullOrEmpty(friendlyName3) ? ItemAttributes.ConvertFormat(friendlyName3) : "Life Time {0} Seconds", new object[1]
//      {
//        (object) skillRecord.GetSingle("spawnObjectsTimeToLive", 0)
//      });
//      object[] objArray3 = new object[2]
//      {
//        (object) Database.HtmlColor(Item.GetColor(ItemStyle.Mundane)),
//        (object) str3
//      };
//      results.Add(string.Format((IFormatProvider) CultureInfo.CurrentCulture, "<font color={0}>{1}</font>", objArray3));
//      float single1 = recordFromFile1.GetSingle("characterLife", 0);
//      if ((double) single1 != 0.0)
//      {
//        string friendlyName4 = Database.GetFriendlyName("SkillPetDescriptionHealth");
//        string str4 = string.Format((IFormatProvider) CultureInfo.CurrentCulture, !string.IsNullOrEmpty(friendlyName4) ? ItemAttributes.ConvertFormat(friendlyName4) : "{0}  Health", new object[1]
//        {
//          (object) single1
//        });
//        object[] objArray4 = new object[2]
//        {
//          (object) Database.HtmlColor(Item.GetColor(ItemStyle.Mundane)),
//          (object) str4
//        };
//        results.Add(string.Format((IFormatProvider) CultureInfo.CurrentCulture, "<font color={0}>{1}</font>", objArray4));
//      }
//      float single2 = recordFromFile1.GetSingle("characterMana", 0);
//      if ((double) single2 != 0.0)
//      {
//        string friendlyName5 = Database.GetFriendlyName("SkillPetDescriptionMana");
//        string str5 = string.Format((IFormatProvider) CultureInfo.CurrentCulture, !string.IsNullOrEmpty(friendlyName5) ? ItemAttributes.ConvertFormat(friendlyName5) : "{0}  Energy", new object[1]
//        {
//          (object) single2
//        });
//        object[] objArray5 = new object[2]
//        {
//          (object) Database.HtmlColor(Item.GetColor(ItemStyle.Mundane)),
//          (object) str5
//        };
//        results.Add(string.Format((IFormatProvider) CultureInfo.CurrentCulture, "<font color={0}>{1}</font>", objArray5));
//      }
//      results.Add(string.Empty);
//      string friendlyName6 = Database.GetFriendlyName("tagSkillPetAbilities");
//      string str6 = string.Format((IFormatProvider) CultureInfo.CurrentCulture, !string.IsNullOrEmpty(friendlyName6) ? ItemAttributes.ConvertFormat(friendlyName6) : "{0} Abilities:", new object[1]
//      {
//        (object) "Pet"
//      });
//      object[] objArray6 = new object[2]
//      {
//        (object) Database.HtmlColor(Item.GetColor(ItemStyle.Mundane)),
//        (object) str6
//      };
//      results.Add(string.Format((IFormatProvider) CultureInfo.CurrentCulture, "<font color={0}>{1}</font>", objArray6));
//      float single3 = recordFromFile1.GetSingle("handHitDamageMin", 0);
//      float single4 = recordFromFile1.GetSingle("handHitDamageMax", 0);
//      if ((double) single3 > 1.0 || (double) single4 > 2.0)
//      {
//        if ((double) single4 == 0.0 || (double) single3 == (double) single4)
//        {
//          string friendlyName7 = Database.GetFriendlyName("SkillPetDescriptionDamageMinOnly");
//          string str7 = string.Format((IFormatProvider) CultureInfo.CurrentCulture, !string.IsNullOrEmpty(friendlyName7) ? ItemAttributes.ConvertFormat(friendlyName7) : "{0}  Damage", new object[1]
//          {
//            (object) single3
//          });
//          object[] objArray7 = new object[2]
//          {
//            (object) Database.HtmlColor(Item.GetColor(ItemStyle.Mundane)),
//            (object) str7
//          };
//          results.Add(string.Format((IFormatProvider) CultureInfo.CurrentCulture, "<font color={0}>{1}</font>", objArray7));
//        }
//        else
//        {
//          string friendlyName8 = Database.GetFriendlyName("SkillPetDescriptionDamageMinMax");
//          string str8 = string.Format((IFormatProvider) CultureInfo.CurrentCulture, !string.IsNullOrEmpty(friendlyName8) ? ItemAttributes.ConvertFormat(friendlyName8) : "{0} - {1}  Damage", new object[2]
//          {
//            (object) single3,
//            (object) single4
//          });
//          object[] objArray8 = new object[2]
//          {
//            (object) Database.HtmlColor(Item.GetColor(ItemStyle.Mundane)),
//            (object) str8
//          };
//          results.Add(string.Format((IFormatProvider) CultureInfo.CurrentCulture, "<font color={0}>{1}</font>", objArray8));
//        }
//      }
//      string[] strArray = new string[17];
//      int[] numArray = new int[17];
//      int index1 = 0;
//      for (int index2 = 0; index2 < strArray.Length; ++index2)
//      {
//        string str9 = recordFromFile1.GetString("skillName" + (object) index2, 0);
//        if (!string.IsNullOrEmpty(str9))
//        {
//          strArray[index1] = str9;
//          int num = recordFromFile1.GetInt32("skillLevel" + (object) index2, 0);
//          if (num < 1)
//            num = 1;
//          numArray[index1] = num;
//          ++index1;
//        }
//      }
//      for (int index3 = 0; index3 < index1; ++index3)
//      {
//        DBRecord recordFromFile2 = Database.GetRecordFromFile(strArray[index3]);
//        DBRecord record = (DBRecord) null;
//        if (recordFromFile2 != null && recordFromFile2.GetString("Class", 0).uppercase() != "SKILL_PASSIVE")
//        {
//          string tagId2 = (string) null;
//          string str10 = (string) null;
//          string recordId = (string) null;
//          string itemId = recordFromFile2.GetString("buffSkillName", 0);
//          if (string.IsNullOrEmpty(itemId))
//          {
//            record = recordFromFile2;
//            recordId = strArray[index3];
//            tagId2 = skillRecord.GetString("skillDisplayName", 0);
//            if (tagId2.Length > 0)
//              str10 = Database.GetFriendlyName(tagId2);
//          }
//          else
//          {
//            DBRecord recordFromFile3 = Database.GetRecordFromFile(itemId);
//            if (recordFromFile3 != null)
//            {
//              record = recordFromFile3;
//              recordId = itemId;
//              tagId2 = recordFromFile3.GetString("skillDisplayName", 0);
//              if (tagId2.Length > 0)
//                str10 = Database.GetFriendlyName(tagId2);
//            }
//          }
//          if (str10.Length == 0)
//          {
//            object[] objArray9 = new object[2]
//            {
//              (object) Database.HtmlColor(Item.GetColor(ItemStyle.Legendary)),
//              (object) tagId2
//            };
//            results.Add(string.Format((IFormatProvider) CultureInfo.CurrentCulture, "<font color={0}>{1}</font>", objArray9));
//          }
//          else
//          {
//            object[] objArray10 = new object[2]
//            {
//              (object) Database.HtmlColor(Item.GetColor(ItemStyle.Mundane)),
//              (object) str10
//            };
//            results.Add(string.Format((IFormatProvider) CultureInfo.CurrentCulture, "<font color={0}>{1}</font>", objArray10));
//          }
//          this.GetAttributesFromRecord(record, true, recordId, results);
//          results.Add(string.Empty);
//        }
//      }
//    }
//
//    public Item Duplicate(bool keepItemSeed)
//    {
//      Item obj = (Item) this.MemberwiseClone();
//      if (!keepItemSeed)
//        obj.Seed = Item.GenerateSeed();
//      obj.PositionX = -1;
//      obj.PositionY = -1;
//      obj.MarkModified();
//      return obj;
//    }

    public fun Encode( writerOrg: OutputStream) {
        ObjectOutputStream(writerOrg).use { writer ->

            var stackSize = this.StackSize;
            var num1 = this.PositionX;
            var num2 = this.PositionY;
            var seed = this.seed;
            if (this.sackType != SackType.Sack && this.sackType != SackType.Player) {
                if (this.sackType == SackType.Stash) {
                    TQData.writeCString(writer, "stackCount");
                    writer.write(stackSize - 1);
                }
                TQData.writeCString(writer, "begin_block");
                writer.write(this.beginBlockCrap2);
                TQData.writeCString(writer, "baseName");
                TQData.writeCString(writer, this.BaseItemId);
                TQData.writeCString(writer, "prefixName");
                TQData.writeCString(writer, this.prefixID);
                TQData.writeCString(writer, "suffixName");
                TQData.writeCString(writer, this.suffixID);
                TQData.writeCString(writer, "relicName");
                TQData.writeCString(writer, this.relicID);
                TQData.writeCString(writer, "relicBonus");
                TQData.writeCString(writer, this.RelicBonusId);
                TQData.writeCString(writer, "seed");
                writer.write(seed);
                TQData.writeCString(writer, "var1");
                writer.write(this.var1);
                if (this.isSecondBonus) {
                    TQData.writeCString(writer, "relicName2");
                    TQData.writeCString(writer, this.relicID2);
                    TQData.writeCString(writer, "relicBonus2");
                    TQData.writeCString(writer, this.RelicBonusId2);
                    TQData.writeCString(writer, "var2");
                    writer.write(this.var2);
                }
                TQData.writeCString(writer, "end_block");
                writer.write(this.endBlockCrap2);
                if (this.sackType != SackType.Stash)
                    return;
                TQData.writeCString(writer, "xOffset");
                writer.writeFloat(num1.toFloat());
                TQData.writeCString(writer, "yOffset");
                writer.writeFloat(num2.toFloat());
            } else {
                while (true) {
                    TQData.writeCString(writer, "begin_block");
                    writer.write(this.beginBlockCrap1);
                    TQData.writeCString(writer, "begin_block");
                    writer.write(this.beginBlockCrap2);
                    TQData.writeCString(writer, "baseName");
                    TQData.writeCString(writer, this.BaseItemId);
                    TQData.writeCString(writer, "prefixName");
                    TQData.writeCString(writer, this.prefixID);
                    TQData.writeCString(writer, "suffixName");
                    TQData.writeCString(writer, this.suffixID);
                    TQData.writeCString(writer, "relicName");
                    TQData.writeCString(writer, this.relicID);
                    TQData.writeCString(writer, "relicBonus");
                    TQData.writeCString(writer, this.RelicBonusId);
                    TQData.writeCString(writer, "seed");
                    writer.write(seed);
                    TQData.writeCString(writer, "var1");
                    writer.write(this.var1);
                    if (this.isSecondBonus) {
                        TQData.writeCString(writer, "relicName2");
                        TQData.writeCString(writer, this.relicID2);
                        TQData.writeCString(writer, "relicBonus2");
                        TQData.writeCString(writer, this.RelicBonusId2);
                        TQData.writeCString(writer, "var2");
                        writer.write(this.var2);
                    }
                    TQData.writeCString(writer, "end_block");
                    writer.write(this.endBlockCrap2);
                    TQData.writeCString(writer, "pointX");
                    writer.write(num1);
                    TQData.writeCString(writer, "pointY");
                    writer.write(num2);
                    TQData.writeCString(writer, "end_block");
                    writer.write(this.endBlockCrap1);
                    if (this.DoesStack && stackSize > 1) {
                        stackSize--
                        num1 = -1;
                        num2 = -1;
                        seed = GenerateSeed();
                    } else
                        break;
                }
            }
        }
    }

//    private static bool FilterKey(string key)
//    {
//      string upperInvariant = key.uppercase();
//      return Array.IndexOf<string>(new string[76]
//      {
//        "MAXTRANSPARENCY",
//        "SCALE",
//        "CASTSSHADOWS",
//        "MARKETADJUSTMENTPERCENT",
//        "LOOTRANDOMIZERCOST",
//        "LOOTRANDOMIZERJITTER",
//        "ACTORHEIGHT",
//        "ACTORRADIUS",
//        "SHADOWBIAS",
//        "ITEMLEVEL",
//        "ITEMCOST",
//        "COMPLETEDRELICLEVEL",
//        "CHARACTERBASEATTACKSPEED",
//        "HIDESUFFIXNAME",
//        "HIDEPREFIXNAME",
//        "AMULET",
//        "RING",
//        "HELMET",
//        "GREAVES",
//        "ARMBAND",
//        "BODYARMOR",
//        "BOW",
//        "SPEAR",
//        "STAFF",
//        "MACE",
//        "SWORD",
//        "AXE",
//        "SHIELD",
//        "BRACELET",
//        "AMULET",
//        "RING",
//        "BLOCKABSORPTION",
//        "ITEMCOSTSCALEPERCENT",
//        "ITEMSKILLLEVEL",
//        "USEDELAYTIME",
//        "CAMERASHAKEAMPLITUDE",
//        "SKILLMAXLEVEL",
//        "SKILLCOOLDOWNTIME",
//        "EXPANSIONTIME",
//        "SKILLTIER",
//        "CAMERASHAKEDURATIONSECS",
//        "SKILLULTIMATELEVEL",
//        "SKILLCONNECTIONSPACING",
//        "PETBURSTSPAWN",
//        "PETLIMIT",
//        "ISPETDISPLAYABLE",
//        "SPAWNOBJECTSTIMETOLIVE",
//        "SKILLPROJECTILENUMBER",
//        "SKILLMASTERYLEVELREQUIRED",
//        "EXCLUDERACIALDAMAGE",
//        "SKILLWEAPONTINTRED",
//        "SKILLWEAPONTINTGREEN",
//        "SKILLWEAPONTINTBLUE",
//        "DEBUFSKILL",
//        "HIDEFROMUI",
//        "INSTANTCAST",
//        "WAVEENDWIDTH",
//        "WAVEDISTANCE",
//        "WAVEDEPTH",
//        "WAVESTARTWIDTH",
//        "RAGDOLLAMPLIFICATION",
//        "WAVETIME",
//        "SPARKGAP",
//        "SPARKCHANCE",
//        "PROJECTILEUSESALLDAMAGE",
//        "DROPOFFSET",
//        "DROPHEIGHT",
//        "NUMPROJECTILES",
//        "SWORD",
//        "AXE",
//        "SPEAR",
//        "RANGEDONEHAND",
//        "MACE",
//        "QUEST",
//        "CANNOTPICKUPMULTIPLE",
//        "DISPLAYASQUESTITEM"
//      }, upperInvariant) != -1 || upperInvariant.EndsWith("SOUND", StringComparison.OrdinalIgnoreCase) || upperInvariant.EndsWith("MESH", StringComparison.OrdinalIgnoreCase);
//    }
//
//    private static bool FilterRequirements(string key) => Array.IndexOf<string>(new string[4]
//    {
//      "LEVELREQUIREMENT",
//      "INTELLIGENCEREQUIREMENT",
//      "DEXTERITYREQUIREMENT",
//      "STRENGTHREQUIREMENT"
//    }, key.uppercase()) != -1;
//
//    private static bool FilterValue(Variable variable, bool allowStrings)
//    {
//      for (int index = 0; index < variable.NumberOfValues; ++index)
//      {
//        switch (variable.DataType)
//        {
//          case VariableDataType.Integer:
//            if (variable.GetInt32(index) > 0)
//              return false;
//            break;
//          case VariableDataType.Float:
//            if ((double) variable.GetSingle(index) != 0.0)
//              return false;
//            break;
//          case VariableDataType.StringVar:
//            if ((allowStrings || variable.Name.uppercase().Equals("CHARACTERBASEATTACKSPEEDTAG") || variable.Name.uppercase().Equals("ITEMSKILLNAME") || variable.Name.uppercase().Equals("SKILLNAME") || variable.Name.uppercase().Equals("PETBONUSNAME") || ItemAttributes.IsReagent(variable.Name)) && variable.GetString(index).Length > 0)
//              return false;
//            break;
//          case VariableDataType.Boolean:
//            if (variable.GetInt32(index) > 0)
//              return false;
//            break;
//        }
//      }
//      return true;
//    }
//
//    private static string Format(
//      string formatSpec,
//      object parameter1,
//      object parameter2 = null,
//      object parameter3 = null)
//    {
//      try
//      {
//        object[] objArray = new object[3]
//        {
//          parameter1,
//          parameter2,
//          parameter3
//        };
//        return string.Format((IFormatProvider) CultureInfo.CurrentCulture, formatSpec, objArray);
//      }
//      catch (ArgumentException ex)
//      {
//        string str = string.Format((IFormatProvider) CultureInfo.InvariantCulture, "\", '{0}', '{1}', '{2}'>", new object[3]
//        {
//          parameter1 == null ? (object) "NULL" : parameter1,
//          parameter2 == null ? (object) "NULL" : parameter2,
//          parameter3 == null ? (object) "NULL" : parameter3
//        });
//        return "FormatErr(\"" + formatSpec + str;
//      }
//    }
//
    public fun GenerateSeed() = Random.nextInt(Short.MAX_VALUE.toInt())
//
//    private string GetAmountRange(
//      ItemAttributesData data,
//      int varNum,
//      Variable minVar,
//      Variable maxVar,
//      ref string label,
//      string labelColor)
//    {
//      string parameter1 = (string) null;
//      string tagId = "DamageRangeFormat";
//      if (this.isInfluenceDamage(data.Effect))
//        tagId = "DamageInfluenceRangeFormat";
//      else if (data.Effect.Equals("defensiveBlock"))
//        tagId = "DefenseBlock";
//      string friendlyName = Database.GetFriendlyName(tagId);
//      string formatSpec;
//      if (string.IsNullOrEmpty(friendlyName))
//      {
//        formatSpec = "{0}..{1}";
//        parameter1 = Database.HtmlColor(Item.GetColor(ItemStyle.Legendary));
//      }
//      else
//        formatSpec = ItemAttributes.ConvertFormat(friendlyName);
//      if (label.IndexOf('{') >= 0)
//      {
//        formatSpec = label;
//        label = (string) null;
//        parameter1 = labelColor;
//      }
//      minVar[Math.Min(minVar.NumberOfValues - 1, varNum)] = (object) (float) ((double) (float) minVar[Math.Min(minVar.NumberOfValues - 1, varNum)] * (double) this.itemScalePercent);
//      maxVar[Math.Min(maxVar.NumberOfValues - 1, varNum)] = (object) (float) ((double) (float) maxVar[Math.Min(maxVar.NumberOfValues - 1, varNum)] * (double) this.itemScalePercent);
//      string parameter2 = Database.MakeSafeForHtml(Item.Format(formatSpec, minVar[Math.Min(minVar.NumberOfValues - 1, varNum)], maxVar[Math.Min(maxVar.NumberOfValues - 1, varNum)]));
//      if (!string.IsNullOrEmpty(parameter1))
//        parameter2 = Item.Format("<font color={0}>{1}</font>", (object) parameter1, (object) parameter2);
//      return parameter2;
//    }
//
//    private string GetAmountSingle(
//      ItemAttributesData data,
//      int varNum,
//      Variable minVar,
//      Variable maxVar,
//      ref string label,
//      string labelColor)
//    {
//      string parameter1 = (string) null;
//      string parameter2 = (string) null;
//      string tagId = "DamageSingleFormat";
//      if (this.isInfluenceDamage(data.Effect))
//        tagId = "DamageInfluenceSingleFormat";
//      string friendlyName = Database.GetFriendlyName(tagId);
//      string formatSpec;
//      if (string.IsNullOrEmpty(friendlyName))
//      {
//        formatSpec = "{0}";
//        parameter1 = Database.HtmlColor(Item.GetColor(ItemStyle.Legendary));
//      }
//      else
//        formatSpec = ItemAttributes.ConvertFormat(friendlyName);
//      if (label.IndexOf('{') >= 0)
//      {
//        formatSpec = label;
//        label = (string) null;
//        parameter1 = labelColor;
//      }
//      Variable variable = minVar ?? maxVar;
//      if (variable != null)
//      {
//        if (variable.DataType == VariableDataType.Float)
//          variable[Math.Min(variable.NumberOfValues - 1, varNum)] = (object) (float) ((double) (float) variable[Math.Min(variable.NumberOfValues - 1, varNum)] * (double) this.itemScalePercent);
//        parameter2 = Database.MakeSafeForHtml(Item.Format(formatSpec, variable[Math.Min(variable.NumberOfValues - 1, varNum)]));
//        if (!string.IsNullOrEmpty(parameter1))
//          parameter2 = Item.Format("<font color={0}>{1}</font>", (object) parameter1, (object) parameter2);
//      }
//      return parameter2;
//    }
//
//    private string getArtifactClass(string artifactClassification)
//    {
//      string tagId = (string) null;
//      switch (artifactClassification)
//      {
//        case "LESSER":
//          tagId = "xtagArtifactClass01";
//          goto default;
//        case null:
//          string str = Database.MakeSafeForHtml(tagId == null ? "?Unknown Artifact Class?" : Database.GetFriendlyName(tagId));
//          return string.Format((IFormatProvider) CultureInfo.CurrentCulture, "<font color={0}>{1}</font>", new object[2]
//          {
//            (object) Database.HtmlColor(Item.GetColor(ItemStyle.Broken)),
//            (object) str
//          });
//        default:
//          if (artifactClassification == "GREATER")
//            tagId = "xtagArtifactClass02";
//          if (artifactClassification == "DIVINE")
//          {
//            tagId = "xtagArtifactClass03";
//            goto case null;
//          }
//          else
//            goto case null;
//      }
//    }
//
//    public string GetAttributes(bool filtering)
//    {
//      if (this.attributesString != null)
//      {
//        List<string> results = new List<string>();
//        string text1 = Database.MakeSafeForHtml(this.ToString(true));
//        Color colorTag1 = this.GetColorTag(text1);
//        string str1 = Item.ClipColorTag(text1);
//        object[] objArray1 = new object[3]
//        {
//          (object) Convert.ToInt32(3f * Database.Scale),
//          (object) Database.HtmlColor(colorTag1),
//          (object) str1
//        };
//        results.Add(string.Format((IFormatProvider) CultureInfo.CurrentCulture, "<font size={0} color={1}><b>{2}</b></font>", objArray1));
//        if (this.baseItemInfo != null)
//        {
//          if (this.IsRelic)
//          {
//            string text2;
//            if (!this.IsRelicComplete)
//            {
//              string tagId1 = "tagRelicShard";
//              string tagId2 = "tagRelicRatio";
//              if (this.IsCharm)
//              {
//                tagId1 = "tagAnimalPart";
//                tagId2 = "tagAnimalPartRatio";
//              }
//              string parameter1 = Database.GetFriendlyName(tagId1) ?? "?Relic?";
//              string friendlyName = Database.GetFriendlyName(tagId2);
//              text2 = Item.Format(friendlyName != null ? ItemAttributes.ConvertFormat(friendlyName) : "?{0} - {1} / {2}?", (object) parameter1, (object) this.Number, (object) this.baseItemInfo.CompletedRelicLevel);
//            }
//            else
//            {
//              string tagId = "tagRelicComplete";
//              if (this.IsCharm)
//                tagId = "tagAnimalPartComplete";
//              text2 = Database.GetFriendlyName(tagId) ?? "?Completed Relic/Charm?";
//            }
//            string text3 = Database.MakeSafeForHtml(text2);
//            Color colorTag2 = this.GetColorTag(text3);
//            string str2 = Item.ClipColorTag(text3);
//            object[] objArray2 = new object[2]
//            {
//              (object) Database.HtmlColor(colorTag2),
//              (object) str2
//            };
//            results.Add(string.Format((IFormatProvider) CultureInfo.CurrentCulture, "<font color={0}>{1}</font>", objArray2));
//          }
//          else if (this.IsArtifact)
//          {
//            string upperInvariant = this.baseItemInfo.GetString("artifactClassification").uppercase();
//            results.Add(this.getArtifactClass(upperInvariant));
//          }
//          if (this.IsFormulae)
//          {
//            string tagId3 = "xtagArtifactRecipe";
//            string str3 = Database.GetFriendlyName(tagId3) ?? "Recipe";
//            object[] objArray3 = new object[2]
//            {
//              (object) Database.HtmlColor(Item.GetColor(ItemStyle.Mundane)),
//              (object) str3
//            };
//            results.Add(string.Format((IFormatProvider) CultureInfo.CurrentCulture, "<font color={0}>{1}</font><br>", objArray3));
//            string tagId4 = "xtagArtifactReagents";
//            string friendlyName = Database.GetFriendlyName(tagId4);
//            string str4 = Database.MakeSafeForHtml(Item.Format(friendlyName != null ? ItemAttributes.ConvertFormat(friendlyName) : "Required Reagents  ({0}/{1})", (object) 0, (object) 3));
//            object[] objArray4 = new object[2]
//            {
//              (object) Database.HtmlColor(Item.GetColor(ItemStyle.Relic)),
//              (object) str4
//            };
//            results.Add(string.Format((IFormatProvider) CultureInfo.CurrentCulture, "<font color={0}>{1}</font>", objArray4));
//          }
//        }
//        if ((this.IsQuestItem || this.IsRelic || this.IsPotion || this.IsParchment || this.IsScroll) && this.baseItemInfo != null && this.baseItemInfo.StyleTag.Length > 0)
//        {
//          string styleTag = this.baseItemInfo.StyleTag;
//          string friendlyName = Database.GetFriendlyName(styleTag);
//          if (friendlyName != null)
//          {
//            foreach (string wrapWord in Database.WrapWords(Database.MakeSafeForHtml(friendlyName), 30))
//            {
//              string text4 = Database.MakeSafeForHtml(wrapWord);
//              Color color = this.GetColorTag(text4);
//              if (color == Item.GetColor(this.ItemStyle))
//                color = Color.White;
//              string str5 = Item.ClipColorTag(text4);
//              string str6 = string.Format((IFormatProvider) CultureInfo.CurrentCulture, "<font color={0}>{1}</font>", new object[2]
//              {
//                (object) Database.HtmlColor(color),
//                (object) str5
//              });
//              results.Add(str6);
//            }
//            results.Add(string.Empty);
//          }
//        }
//        if (this.baseItemInfo != null)
//          this.GetAttributesFromRecord(Database.GetRecordFromFile(this.BaseItemId), filtering, this.BaseItemId, results);
//        if (this.prefixInfo != null)
//          this.GetAttributesFromRecord(Database.GetRecordFromFile(this.prefixID), filtering, this.prefixID, results);
//        if (this.suffixInfo != null)
//          this.GetAttributesFromRecord(Database.GetRecordFromFile(this.suffixID), filtering, this.suffixID, results);
//        if (this.RelicInfo != null)
//        {
//          List<string> stringList = new List<string>();
//          this.GetAttributesFromRecord(Database.GetRecordFromFile(this.relicID), filtering, this.relicID, stringList);
//          if (stringList.Count > 0)
//          {
//            string str7 = string.Format((IFormatProvider) CultureInfo.CurrentCulture, "<hr color={0}>", new object[1]
//            {
//              (object) Database.HtmlColor(Item.GetColor(ItemStyle.Broken))
//            });
//            string str8 = Database.MakeSafeForHtml(this.ToString(relicInfoOnly: true));
//            object[] objArray5 = new object[3]
//            {
//              (object) Database.HtmlColor(Item.GetColor(ItemStyle.Relic)),
//              (object) str8,
//              (object) str7
//            };
//            results.Add(string.Format((IFormatProvider) CultureInfo.CurrentUICulture, "{2}<font size=+1 color={0}><b>{1}</b></font>", objArray5));
//            string text5;
//            if (this.Var1 < this.RelicInfo.CompletedRelicLevel)
//            {
//              string tagId5 = "tagRelicShard";
//              string tagId6 = "tagRelicRatio";
//              if (this.RelicInfo.ItemClass.uppercase().Equals("ITEMCHARM"))
//              {
//                tagId5 = "tagAnimalPart";
//                tagId6 = "tagAnimalPartRatio";
//              }
//              string parameter1 = Database.GetFriendlyName(tagId5) ?? "?Relic?";
//              string friendlyName = Database.GetFriendlyName(tagId6);
//              text5 = Item.Format(friendlyName != null ? ItemAttributes.ConvertFormat(friendlyName) : "?{0} - {1} / {2}?", (object) parameter1, (object) Math.Max(1, this.Var1), (object) this.RelicInfo.CompletedRelicLevel);
//            }
//            else
//            {
//              string tagId = "tagRelicComplete";
//              if (this.RelicInfo.ItemClass.uppercase().Equals("ITEMCHARM"))
//                tagId = "tagAnimalPartComplete";
//              text5 = Database.GetFriendlyName(tagId) ?? "?Completed Relic/Charm?";
//            }
//            string str9 = Database.MakeSafeForHtml(text5);
//            object[] objArray6 = new object[2]
//            {
//              (object) Database.HtmlColor(Item.GetColor(ItemStyle.Relic)),
//              (object) str9
//            };
//            results.Add(string.Format((IFormatProvider) CultureInfo.CurrentCulture, "<font color={0}>{1}</font>", objArray6));
//            results.AddRange((IEnumerable<string>) stringList);
//          }
//        }
//        if (this.IsArtifact && this.RelicBonusInfo != null)
//        {
//          List<string> stringList = new List<string>();
//          this.GetAttributesFromRecord(Database.GetRecordFromFile(this.RelicBonusId), filtering, this.RelicBonusId, stringList);
//          if (stringList.Count > 0)
//          {
//            string tagId = "xtagArtifactBonus";
//            string str10 = Database.MakeSafeForHtml(Database.GetFriendlyName(tagId) ?? "Completion Bonus: ");
//            results.Add(string.Empty);
//            object[] objArray7 = new object[2]
//            {
//              (object) Database.HtmlColor(Item.GetColor(ItemStyle.Relic)),
//              (object) str10
//            };
//            results.Add(string.Format((IFormatProvider) CultureInfo.CurrentCulture, "<font color={0}>{1}</font>", objArray7));
//            results.AddRange((IEnumerable<string>) stringList);
//          }
//        }
//        if ((this.IsRelic || this.HasRelic) && this.RelicBonusInfo != null)
//        {
//          List<string> stringList = new List<string>();
//          this.GetAttributesFromRecord(Database.GetRecordFromFile(this.RelicBonusId), filtering, this.RelicBonusId, stringList);
//          if (stringList.Count > 0)
//          {
//            string tagId = "tagRelicBonus";
//            if (this.IsCharm || this.HasRelic && this.RelicInfo.ItemClass.uppercase().Equals("ITEMCHARM"))
//              tagId = "tagAnimalPartcompleteBonus";
//            string str11 = Database.MakeSafeForHtml(Database.GetFriendlyName(tagId) ?? "?Completed Relic/Charm Bonus:?");
//            object[] objArray8 = new object[2]
//            {
//              (object) Database.HtmlColor(Item.GetColor(ItemStyle.Relic)),
//              (object) str11
//            };
//            results.Add(string.Format((IFormatProvider) CultureInfo.CurrentCulture, "<font color={0}>{1}</font>", objArray8));
//            results.AddRange((IEnumerable<string>) stringList);
//          }
//        }
//        if (this.IsFormulae && this.baseItemInfo != null)
//        {
//          List<string> stringList = new List<string>();
//          string str12 = this.baseItemInfo.GetString("artifactName");
//          DBRecord recordFromFile = Database.GetRecordFromFile(str12);
//          if (str12 != null)
//          {
//            this.GetAttributesFromRecord(recordFromFile, filtering, str12, stringList);
//            if (stringList.Count > 0)
//            {
//              string text6 = Database.GetFriendlyName(recordFromFile.GetString("description", 0));
//              if (string.IsNullOrEmpty(text6))
//                text6 = "?Unknown Artifact Name?";
//              string str13 = Database.MakeSafeForHtml(text6);
//              results.Add(string.Empty);
//              object[] objArray9 = new object[2]
//              {
//                (object) Database.HtmlColor(Item.GetColor(ItemStyle.Artifact)),
//                (object) str13
//              };
//              results.Add(string.Format((IFormatProvider) CultureInfo.CurrentCulture, "<font color={0}>{1}</font>", objArray9));
//              string upperInvariant = recordFromFile.GetString("artifactClassification", 0).uppercase();
//              results.Add(this.getArtifactClass(upperInvariant));
//              results.AddRange((IEnumerable<string>) stringList);
//            }
//          }
//        }
//        string str14 = string.Format((IFormatProvider) CultureInfo.CurrentCulture, "<hr color={0}>", new object[1]
//        {
//          (object) Database.HtmlColor(Item.GetColor(ItemStyle.Broken))
//        });
//        string str15 = Database.MakeSafeForHtml(string.Format((IFormatProvider) CultureInfo.CurrentCulture, Item.ItemSeed, new object[2]
//        {
//          (object) this.Seed,
//          (object) (float) (this.Seed != 0 ? (double) this.Seed / (double) short.MaxValue : 0.0)
//        }));
//        object[] objArray10 = new object[3]
//        {
//          (object) Database.HtmlColor(Item.GetColor(ItemStyle.Broken)),
//          (object) str15,
//          (object) str14
//        };
//        results.Add(string.Format((IFormatProvider) CultureInfo.CurrentCulture, "{2}<font color={0}>{1}</font>", objArray10));
//        if (this.IsImmortalThrone)
//        {
//          string str16 = Database.MakeSafeForHtml(Item.ItemIT);
//          object[] objArray11 = new object[2]
//          {
//            (object) Database.HtmlColor(Item.GetColor(ItemStyle.Rare)),
//            (object) str16
//          };
//          results.Add(string.Format((IFormatProvider) CultureInfo.CurrentCulture, "<font color={0}>{1}</font>", objArray11));
//        }
//        if (this.IsRagnarok)
//        {
//          string str17 = Database.MakeSafeForHtml(Item.ItemRAG);
//          object[] objArray12 = new object[2]
//          {
//            (object) Database.HtmlColor(Item.GetColor(ItemStyle.Rare)),
//            (object) str17
//          };
//          results.Add(string.Format((IFormatProvider) CultureInfo.CurrentCulture, "<font color={0}>{1}</font>", objArray12));
//        }
//        string[] array = new string[results.Count];
//        results.CopyTo(array);
//        this.attributesString = string.Join("<br>", array);
//      }
//      return this.attributesString;
//    }
//
//    public void GetAttributesFromRecord(
//      DBRecord record,
//      bool filtering,
//      string recordId,
//      List<string> results,
//      bool convertStrings = true)
//    {
//      Dictionary<string, List<Variable>> dictionary = new Dictionary<string, List<Variable>>();
//      if (record == null)
//      {
//        Logger.Debug("GetAttributesFromRecord - record is null. record ID:" + recordId);
//        results.Add("<unknown>");
//      }
//      else
//      {
//        List<string> stringList = new List<string>();
//        stringList.Clear();
//        foreach (Variable variable in record)
//        {
//          if (!Item.FilterValue(variable, !filtering) && (!filtering || !Item.FilterKey(variable.Name)) && (!filtering || !Item.FilterRequirements(variable.Name)))
//          {
//            ItemAttributesData itemAttributesData = ItemAttributes.GetAttributeData(variable.Name) ?? new ItemAttributesData(ItemAttributesEffectType.Other, variable.Name, variable.Name, string.Empty, 0);
//            string key = itemAttributesData.EffectType != ItemAttributesEffectType.DamageQualifierEffect ? itemAttributesData.EffectType.ToString() + ":" + itemAttributesData.Effect : itemAttributesData.EffectType.ToString() + ":DamageQualifier";
//            List<Variable> variableList;
//            try
//            {
//              variableList = dictionary[key];
//            }
//            catch (KeyNotFoundException ex)
//            {
//              variableList = new List<Variable>();
//              dictionary[key] = variableList;
//            }
//            variableList.Add(variable);
//            if (recordId != this.relicID && recordId != this.RelicBonusId && !this.isAttributeCounted && !stringList.Contains(key))
//            {
//              string upperInvariant = variable.Name.uppercase();
//              if (!upperInvariant.Contains("CHANCE") && !upperInvariant.Contains("DURATION") && upperInvariant != "CHARACTERBASEATTACKSPEEDTAG" && upperInvariant != "OFFENSIVEPHYSICALMIN" && upperInvariant != "OFFENSIVEPHYSICALMAX" && upperInvariant != "DEFENSIVEPROTECTION" && upperInvariant != "DEFENSIVEBLOCK" && upperInvariant != "BLOCKRECOVERYTIME" && upperInvariant != "OFFENSIVEGLOBALCHANCE" && upperInvariant != "RETALIATIONGLOBALCHANCE" && upperInvariant != "OFFENSIVEPIERCERATIOMIN")
//              {
//                if (upperInvariant.StartsWith("AUGMENTSKILLLEVEL", StringComparison.OrdinalIgnoreCase))
//                {
//                  this.attributeCount += variable.GetInt32(0);
//                  stringList.Add(key);
//                }
//                else
//                {
//                  ++this.attributeCount;
//                  stringList.Add(key);
//                }
//              }
//            }
//          }
//        }
//        if (this.attributeCount > 0)
//          this.isAttributeCounted = true;
//        List<Variable>[] array = new List<Variable>[dictionary.Count];
//        dictionary.Values.CopyTo(array, 0);
//        Array.Sort<List<Variable>>(array, (IComparer<List<Variable>>) new ItemAttributeListCompare(this.IsArmor || this.IsShield));
//        for (int index1 = 0; index1 < array.Length; ++index1)
//        {
//          List<Variable> list1 = array[index1];
//          if (ItemAttributes.AttributeGroupIs(new Collection<Variable>((IList<Variable>) list1), "offensiveGlobalChance") || ItemAttributes.AttributeGroupIs(new Collection<Variable>((IList<Variable>) list1), "retaliationGlobalChance"))
//          {
//            int index2 = index1 + 1;
//            if (index2 < array.Length)
//            {
//              List<Variable> list2 = array[index2];
//              if (!ItemAttributes.AttributeGroupHas(new Collection<Variable>((IList<Variable>) list2), "Global"))
//              {
//                list1.Add((Variable) null);
//                list1.Add((Variable) null);
//              }
//              else if (ItemAttributes.AttributeGroupHas(new Collection<Variable>((IList<Variable>) list2), "XOR"))
//                list1.Add((Variable) null);
//            }
//            else
//            {
//              list1.Add((Variable) null);
//              list1.Add((Variable) null);
//            }
//          }
//        }
//        foreach (List<Variable> variableList in array)
//        {
//          if (ItemAttributes.AttributeGroupIs(new Collection<Variable>((IList<Variable>) variableList), ItemAttributesEffectType.DamageQualifierEffect))
//            variableList.Sort((IComparer<Variable>) new ItemAttributeSubListCompare());
//          if (!convertStrings)
//            Item.ConvertBareAttributeListToString(variableList, results);
//          else
//            this.ConvertAttributeListToString(record, variableList, recordId, results);
//        }
//      }
//    }
//
//    private static string GetAugmentAllLevel(
//      int variableNumber,
//      Variable variable,
//      ref string font)
//    {
//      string tagId = "ItemAllSkillIncrement";
//      string friendlyName = Database.GetFriendlyName(tagId);
//      string formatSpec;
//      if (string.IsNullOrEmpty(friendlyName))
//      {
//        formatSpec = "?+{0} to all skills?";
//        object[] objArray = new object[1]
//        {
//          (object) Database.HtmlColor(Item.GetColor(ItemStyle.Legendary))
//        };
//        font = string.Format((IFormatProvider) CultureInfo.CurrentCulture, "<font color={0}>", objArray);
//      }
//      else
//      {
//        formatSpec = ItemAttributes.ConvertFormat(friendlyName);
//        object[] objArray = new object[1]
//        {
//          (object) Database.HtmlColor(Item.GetColor(ItemStyle.Epic))
//        };
//        font = string.Format((IFormatProvider) CultureInfo.CurrentCulture, "<font color={0}>", objArray);
//      }
//      return Item.Format(formatSpec, variable[Math.Min(variable.NumberOfValues - 1, variableNumber)]);
//    }
//
//    private static string GetAugmentMasteryLevel(
//      DBRecord record,
//      Variable variable,
//      ItemAttributesData attributeData,
//      ref string font)
//    {
//      string variableName = "augmentMasteryName" + attributeData.FullAttribute.Substring(19, 1);
//      string str = record.GetString(variableName, 0);
//      if (string.IsNullOrEmpty(str))
//        str = variableName;
//      string parameter2 = (string) null;
//      DBRecord recordFromFile = Database.GetRecordFromFile(str);
//      if (recordFromFile != null)
//      {
//        string tagId = recordFromFile.GetString("skillDisplayName", 0);
//        if (!string.IsNullOrEmpty(tagId))
//          parameter2 = Database.GetFriendlyName(tagId);
//      }
//      if (string.IsNullOrEmpty(parameter2))
//      {
//        parameter2 = Path.GetFileNameWithoutExtension(str);
//        object[] objArray = new object[1]
//        {
//          (object) Database.HtmlColor(Item.GetColor(ItemStyle.Legendary))
//        };
//        font = string.Format((IFormatProvider) CultureInfo.CurrentCulture, "<font color={0}>", objArray);
//      }
//      string friendlyName = Database.GetFriendlyName("ItemMasteryIncrement");
//      string formatSpec;
//      if (string.IsNullOrEmpty(friendlyName))
//      {
//        formatSpec = "?+{0} to skills in {1}?";
//        if (font == null)
//        {
//          object[] objArray = new object[1]
//          {
//            (object) Database.HtmlColor(Item.GetColor(ItemStyle.Legendary))
//          };
//          font = string.Format((IFormatProvider) CultureInfo.CurrentCulture, "<font color={0}>", objArray);
//        }
//      }
//      else
//      {
//        formatSpec = ItemAttributes.ConvertFormat(friendlyName);
//        if (string.IsNullOrEmpty(font))
//        {
//          object[] objArray = new object[1]
//          {
//            (object) Database.HtmlColor(Item.GetColor(ItemStyle.Epic))
//          };
//          font = string.Format((IFormatProvider) CultureInfo.CurrentCulture, "<font color={0}>", objArray);
//        }
//      }
//      return Item.Format(formatSpec, variable[0], (object) parameter2);
//    }
//
//    private static string GetAugmentSkillLevel(
//      DBRecord record,
//      Variable variable,
//      ItemAttributesData attributeData,
//      string line,
//      ref string font)
//    {
//      string variableName = "augmentSkillName" + attributeData.FullAttribute.Substring(17, 1);
//      string str = record.GetString(variableName, 0);
//      if (!string.IsNullOrEmpty(str))
//      {
//        string parameter2 = (string) null;
//        DBRecord recordFromFile1 = Database.GetRecordFromFile(str);
//        if (recordFromFile1 != null)
//        {
//          string itemId1 = recordFromFile1.GetString("buffSkillName", 0);
//          if (string.IsNullOrEmpty(itemId1))
//          {
//            string tagId1 = recordFromFile1.GetString("skillDisplayName", 0);
//            if (!string.IsNullOrEmpty(tagId1))
//              parameter2 = Database.GetFriendlyName(tagId1);
//            else if (recordFromFile1.GetString("Class", 0).Contains("PetModifier"))
//            {
//              string itemId2 = recordFromFile1.GetString("petSkillName", 0);
//              DBRecord recordFromFile2 = Database.GetRecordFromFile(itemId2);
//              if (recordFromFile2 != null)
//              {
//                string tagId2 = recordFromFile2.GetString("skillDisplayName", 0);
//                if (!string.IsNullOrEmpty(tagId2))
//                  parameter2 = Database.GetFriendlyName(tagId2);
//              }
//            }
//          }
//          else
//          {
//            DBRecord recordFromFile3 = Database.GetRecordFromFile(itemId1);
//            if (recordFromFile3 != null)
//            {
//              string tagId = recordFromFile3.GetString("skillDisplayName", 0);
//              if (!string.IsNullOrEmpty(tagId))
//                parameter2 = Database.GetFriendlyName(tagId);
//            }
//          }
//        }
//        if (string.IsNullOrEmpty(parameter2))
//          parameter2 = Path.GetFileNameWithoutExtension(str);
//        string friendlyName = Database.GetFriendlyName("ItemSkillIncrement");
//        string formatSpec;
//        if (string.IsNullOrEmpty(friendlyName))
//        {
//          formatSpec = "?+{0} to skill {1}?";
//          object[] objArray = new object[1]
//          {
//            (object) Database.HtmlColor(Item.GetColor(ItemStyle.Legendary))
//          };
//          font = string.Format((IFormatProvider) CultureInfo.CurrentCulture, "<font color={0}>", objArray);
//        }
//        else
//        {
//          formatSpec = ItemAttributes.ConvertFormat(friendlyName);
//          object[] objArray = new object[1]
//          {
//            (object) Database.HtmlColor(Item.GetColor(ItemStyle.Epic))
//          };
//          font = string.Format((IFormatProvider) CultureInfo.CurrentCulture, "<font color={0}>", objArray);
//        }
//        line = Item.Format(formatSpec, variable[0], (object) parameter2);
//      }
//      return line;
//    }
//
//    public string[] GetBareAttributes(bool filtering)
//    {
//      if (this.bareAttributes[0] != null)
//      {
//        List<string> results = new List<string>();
//        if (this.baseItemInfo != null)
//        {
//          string str1 = string.Empty;
//          string str2 = string.Empty;
//          if (this.baseItemInfo.StyleTag.Length > 0 && !this.IsPotion && !this.IsRelic && !this.IsScroll && !this.IsParchment && !this.IsQuestItem)
//            str1 = Database.GetFriendlyName(this.baseItemInfo.StyleTag) + " ";
//          if (this.baseItemInfo.QualityTag.Length > 0)
//            str2 = Database.GetFriendlyName(this.baseItemInfo.QualityTag) + " ";
//          object[] objArray1 = new object[2]
//          {
//            (object) Database.HtmlColor(Item.GetColor(ItemStyle.Rare)),
//            (object) (str1 + str2 + Database.GetFriendlyName(this.baseItemInfo.DescriptionTag))
//          };
//          results.Add(string.Format((IFormatProvider) CultureInfo.CurrentCulture, "<font color={0}>{1}</font>", objArray1));
//          object[] objArray2 = new object[2]
//          {
//            (object) Database.HtmlColor(Item.GetColor(ItemStyle.Relic)),
//            (object) this.BaseItemId
//          };
//          results.Add(string.Format((IFormatProvider) CultureInfo.CurrentCulture, "<font color={0}>{1}</font>", objArray2));
//          this.GetAttributesFromRecord(Database.GetRecordFromFile(this.BaseItemId), filtering, this.BaseItemId, results, false);
//        }
//        if (results != null)
//        {
//          string[] array = new string[results.Count];
//          results.CopyTo(array);
//          this.bareAttributes[0] = string.Join("<br>", array);
//          results.Clear();
//        }
//        else
//          this.bareAttributes[0] = string.Empty;
//        if (this.prefixInfo != null)
//        {
//          object[] objArray3 = new object[2]
//          {
//            (object) Database.HtmlColor(Item.GetColor(ItemStyle.Rare)),
//            (object) Database.GetFriendlyName(this.prefixInfo.DescriptionTag)
//          };
//          results.Add(string.Format((IFormatProvider) CultureInfo.CurrentCulture, "<font color={0}>{1}</font>", objArray3));
//          object[] objArray4 = new object[2]
//          {
//            (object) Database.HtmlColor(Item.GetColor(ItemStyle.Relic)),
//            (object) this.prefixID
//          };
//          results.Add(string.Format((IFormatProvider) CultureInfo.CurrentCulture, "<font color={0}>{1}</font>", objArray4));
//          this.GetAttributesFromRecord(Database.GetRecordFromFile(this.prefixID), filtering, this.prefixID, results, false);
//        }
//        if (results != null)
//        {
//          string[] array = new string[results.Count];
//          results.CopyTo(array);
//          this.bareAttributes[2] = string.Join("<br>", array);
//          results.Clear();
//        }
//        else
//          this.bareAttributes[2] = string.Empty;
//        if (this.suffixInfo != null)
//        {
//          object[] objArray5 = new object[2]
//          {
//            (object) Database.HtmlColor(Item.GetColor(ItemStyle.Rare)),
//            (object) Database.GetFriendlyName(this.suffixInfo.DescriptionTag)
//          };
//          results.Add(string.Format((IFormatProvider) CultureInfo.CurrentCulture, "<font color={0}>{1}</font>", objArray5));
//          object[] objArray6 = new object[2]
//          {
//            (object) Database.HtmlColor(Item.GetColor(ItemStyle.Relic)),
//            (object) this.suffixID
//          };
//          results.Add(string.Format((IFormatProvider) CultureInfo.CurrentCulture, "<font color={0}>{1}</font>", objArray6));
//          this.GetAttributesFromRecord(Database.GetRecordFromFile(this.suffixID), filtering, this.suffixID, results, false);
//        }
//        if (results != null)
//        {
//          string[] array = new string[results.Count];
//          results.CopyTo(array);
//          this.bareAttributes[3] = string.Join("<br>", array);
//          results.Clear();
//        }
//        else
//          this.bareAttributes[3] = string.Empty;
//      }
//      return this.bareAttributes;
//    }
//
//    private static string GetChance(int varNum, Variable chanceVar)
//    {
//      string parameter2 = (string) null;
//      string parameter1 = (string) null;
//      string formatValue = Database.GetFriendlyName("ChanceOfTag");
//      if (string.IsNullOrEmpty(formatValue))
//      {
//        formatValue = "?{%.1f0}% Chance of?";
//        parameter1 = Database.HtmlColor(Item.GetColor(ItemStyle.Legendary));
//      }
//      string formatSpec = ItemAttributes.ConvertFormat(formatValue);
//      if (chanceVar != null)
//      {
//        parameter2 = Database.MakeSafeForHtml(Item.Format(formatSpec, chanceVar[Math.Min(chanceVar.NumberOfValues - 1, varNum)]));
//        if (!string.IsNullOrEmpty(parameter1))
//          parameter2 = Item.Format("<font color={0}>{1}</font>", (object) parameter1, (object) parameter2);
//      }
//      return parameter2;
//    }
//
//    private static string GetChanceModifier(int varNum, Variable modifierChanceVar)
//    {
//      string parameter1 = (string) null;
//      string friendlyName = Database.GetFriendlyName("ChanceOfTag");
//      string formatSpec;
//      if (friendlyName == null)
//      {
//        formatSpec = "?{%.1f0}% Chance of?";
//        parameter1 = Database.HtmlColor(Item.GetColor(ItemStyle.Legendary));
//      }
//      else
//        formatSpec = ItemAttributes.ConvertFormat(friendlyName);
//      string parameter2 = Database.MakeSafeForHtml(Item.Format(formatSpec, modifierChanceVar[Math.Min(modifierChanceVar.NumberOfValues - 1, varNum)]));
//      if (parameter1 != null)
//        parameter2 = Item.Format("<font color={0}>{1}</font>", (object) parameter1, (object) parameter2);
//      return parameter2;
//    }
//
//    public Color GetColor() => Item.GetColor(this.ItemStyle);
//
//    public static Color GetColor(ItemStyle style)
//    {
//      switch (style)
//      {
//        case ItemStyle.Broken:
//          return Item.GetColor(TQColor.DarkGray);
//        case ItemStyle.Mundane:
//          return Item.GetColor(TQColor.White);
//        case ItemStyle.Common:
//          return Item.GetColor(TQColor.Yellow);
//        case ItemStyle.Rare:
//          return Item.GetColor(TQColor.Green);
//        case ItemStyle.Epic:
//          return Item.GetColor(TQColor.Blue);
//        case ItemStyle.Legendary:
//          return Item.GetColor(TQColor.Purple);
//        case ItemStyle.Quest:
//          return Item.GetColor(TQColor.Purple);
//        case ItemStyle.Relic:
//          return Item.GetColor(TQColor.Orange);
//        case ItemStyle.Potion:
//          return Item.GetColor(TQColor.Red);
//        case ItemStyle.Scroll:
//          return Item.GetColor(TQColor.YellowGreen);
//        case ItemStyle.Parchment:
//          return Item.GetColor(TQColor.Blue);
//        case ItemStyle.Formulae:
//          return Item.GetColor(TQColor.Turquoise);
//        case ItemStyle.Artifact:
//          return Item.GetColor(TQColor.Turquoise);
//        default:
//          return Color.White;
//      }
//    }
//
//    public static Color GetColor(TQColor color)
//    {
//      switch (color)
//      {
//        case TQColor.Aqua:
//          return Color.FromArgb(0, (int) byte.MaxValue, (int) byte.MaxValue);
//        case TQColor.Blue:
//          return Color.FromArgb(0, 163, (int) byte.MaxValue);
//        case TQColor.LightCyan:
//          return Color.FromArgb(224, (int) byte.MaxValue, (int) byte.MaxValue);
//        case TQColor.DarkGray:
//          return Color.FromArgb(153, 153, 153);
//        case TQColor.Fuschia:
//          return Color.FromArgb((int) byte.MaxValue, 0, (int) byte.MaxValue);
//        case TQColor.Green:
//          return Color.FromArgb(64, (int) byte.MaxValue, 64);
//        case TQColor.Indigo:
//          return Color.FromArgb(75, 0, 130);
//        case TQColor.Khaki:
//          return Color.FromArgb(195, 176, 145);
//        case TQColor.YellowGreen:
//          return Color.FromArgb(145, 203, 0);
//        case TQColor.Maroon:
//          return Color.FromArgb(128, 0, 0);
//        case TQColor.Orange:
//          return Color.FromArgb((int) byte.MaxValue, 173, 0);
//        case TQColor.Purple:
//          return Color.FromArgb(217, 5, (int) byte.MaxValue);
//        case TQColor.Red:
//          return Color.FromArgb((int) byte.MaxValue, 0, 0);
//        case TQColor.Silver:
//          return Color.FromArgb(224, 224, 224);
//        case TQColor.Turquoise:
//          return Color.FromArgb(0, (int) byte.MaxValue, 209);
//        case TQColor.White:
//          return Color.FromArgb((int) byte.MaxValue, (int) byte.MaxValue, (int) byte.MaxValue);
//        case TQColor.Yellow:
//          return Color.FromArgb((int) byte.MaxValue, 245, 43);
//        default:
//          return Color.White;
//      }
//    }
//
//    public Color GetColorTag(string text)
//    {
//      if (!string.IsNullOrEmpty(text))
//      {
//        string str;
//        if (text.StartsWith("^", StringComparison.OrdinalIgnoreCase))
//        {
//          str = text.Substring(1, 1).uppercase();
//        }
//        else
//        {
//          int num = text.IndexOf("{^", StringComparison.OrdinalIgnoreCase);
//          str = num != -1 ? text.Substring(num + 2, 1).uppercase() : (string) null;
//        }
//        if (string.IsNullOrEmpty(str))
//          return Item.GetColor(this.ItemStyle);
//        switch (str)
//        {
//          case "A":
//            return Item.GetColor(TQColor.Aqua);
//          case "B":
//            return Item.GetColor(TQColor.Blue);
//          case "C":
//            return Item.GetColor(TQColor.LightCyan);
//          case "D":
//            return Item.GetColor(TQColor.DarkGray);
//          case "F":
//            return Item.GetColor(TQColor.Fuschia);
//          case "G":
//            return Item.GetColor(TQColor.Green);
//          case "I":
//            return Item.GetColor(TQColor.Indigo);
//          case "K":
//            return Item.GetColor(TQColor.Khaki);
//          case "L":
//            return Item.GetColor(TQColor.YellowGreen);
//          case "M":
//            return Item.GetColor(TQColor.Maroon);
//          case "O":
//            return Item.GetColor(TQColor.Orange);
//          case "P":
//            return Item.GetColor(TQColor.Purple);
//          case "R":
//            return Item.GetColor(TQColor.Red);
//          case "S":
//            return Item.GetColor(TQColor.Silver);
//          case "T":
//            return Item.GetColor(TQColor.Turquoise);
//          case "W":
//            return Item.GetColor(TQColor.White);
//          case "Y":
//            return Item.GetColor(TQColor.Yellow);
//        }
//      }
//      return Item.GetColor(this.ItemStyle);
//    }
//
//    private static string GetDamageRatio(
//      int varNum,
//      ItemAttributesData damageRatioData,
//      Variable damageRatioVar)
//    {
//      string parameter1 = (string) null;
//      string tagId = "Damage" + damageRatioData.FullAttribute.Substring(9, damageRatioData.FullAttribute.Length - 20) + "Ratio";
//      string friendlyName = Database.GetFriendlyName(tagId);
//      string formatSpec;
//      if (string.IsNullOrEmpty(friendlyName))
//      {
//        formatSpec = "{0:f1}% ?" + damageRatioData.FullAttribute + "?";
//        parameter1 = Database.HtmlColor(Item.GetColor(ItemStyle.Legendary));
//      }
//      else
//        formatSpec = ItemAttributes.ConvertFormat(friendlyName);
//      string parameter2 = Database.MakeSafeForHtml(Item.Format(formatSpec, damageRatioVar[Math.Min(damageRatioVar.NumberOfValues - 1, varNum)]));
//      if (!string.IsNullOrEmpty(parameter1))
//        parameter2 = Item.Format("<font color={0}>{1}</font>", (object) parameter1, (object) parameter2);
//      return parameter2;
//    }
//
    public fun GetDBData()
    {
      this.BaseItemId = Item.CheckExtension(this.BaseItemId)
      this.baseItemInfo = Database.GetInfo(this.BaseItemId);
      this.prefixID = Item.CheckExtension(this.prefixID);
      this.suffixID = Item.CheckExtension(this.suffixID);
      this.prefixInfo = Database.GetInfo(this.prefixID);
      this.suffixInfo = Database.GetInfo(this.suffixID);
      this.relicID = Item.CheckExtension(this.relicID);
      this.RelicBonusId = Item.CheckExtension(this.RelicBonusId);
      this.relicInfo = Database.GetInfo(this.relicID);
      this.relicBonusInfo = Database.GetInfo(this.RelicBonusId);
        var baseitem = baseItemInfo
      if (baseitem != null) {
          this.itemBitmap =
              if (!this.IsRelic || this.IsRelicComplete) Database.LoadBitmap(baseitem.Bitmap ?:"") else Database.LoadBitmap(
                  baseitem.ShardBitmap ?:""
              );
      }
      if (this.itemBitmap == null)
        this.itemBitmap = Database.LoadBitmap("DefaultBitmap");
        val itemBM = itemBitmap
      if (itemBM != null)
      {
        this.width = ( itemBM.width * Database.Scale /  Database.ItemUnitSize).toInt()
        this.height = (itemBM.height * Database.Scale /  Database.ItemUnitSize).toInt()
      }
      else
      {
        this.width = 1;
        this.height = 1;
      }
    }
//
//    private static string GetDurationModifier(int varNum, Variable durationModifierVar)
//    {
//      string parameter1 = (string) null;
//      string friendlyName = Database.GetFriendlyName("ImprovedTimeFormat");
//      string formatSpec;
//      if (string.IsNullOrEmpty(friendlyName))
//      {
//        formatSpec = "?with {0:f0}% Improved Duration?";
//        parameter1 = Database.HtmlColor(Item.GetColor(ItemStyle.Legendary));
//      }
//      else
//        formatSpec = ItemAttributes.ConvertFormat(friendlyName);
//      string parameter2 = Database.MakeSafeForHtml(Item.Format(formatSpec, durationModifierVar[Math.Min(durationModifierVar.NumberOfValues - 1, varNum)]));
//      if (parameter1 != null)
//        parameter2 = Item.Format("<font color={0}>{1}</font>", (object) parameter1, (object) parameter2);
//      return parameter2;
//    }
//
//    private static string GetDurationRange(int varNum, Variable minDurVar, Variable maxDurVar)
//    {
//      string parameter1 = (string) null;
//      string friendlyName = Database.GetFriendlyName("DamageFixedRangeFormatTime");
//      string formatSpec;
//      if (string.IsNullOrEmpty(friendlyName))
//      {
//        formatSpec = "over {0}..{1} seconds";
//        parameter1 = Database.HtmlColor(Item.GetColor(ItemStyle.Legendary));
//      }
//      else
//        formatSpec = ItemAttributes.ConvertFormat(friendlyName);
//      string parameter2 = Database.MakeSafeForHtml(Item.Format(formatSpec, minDurVar[Math.Min(minDurVar.NumberOfValues - 1, varNum)], maxDurVar[Math.Min(maxDurVar.NumberOfValues - 1, varNum)]));
//      if (!string.IsNullOrEmpty(parameter1))
//        parameter2 = Item.Format("<font color={0}>{1}</font>", (object) parameter1, (object) parameter2);
//      return parameter2;
//    }
//
//    private static string GetDurationSingle(int varNum, Variable minDurVar, Variable maxDurVar)
//    {
//      string parameter2 = (string) null;
//      string parameter1 = (string) null;
//      string friendlyName = Database.GetFriendlyName("DamageFixedSingleFormatTime");
//      string formatSpec;
//      if (string.IsNullOrEmpty(friendlyName))
//      {
//        formatSpec = "{0}";
//        parameter1 = Database.HtmlColor(Item.GetColor(ItemStyle.Legendary));
//      }
//      else
//        formatSpec = ItemAttributes.ConvertFormat(friendlyName);
//      Variable variable = minDurVar ?? maxDurVar;
//      if (variable != null)
//      {
//        parameter2 = Database.MakeSafeForHtml(Item.Format(formatSpec, variable[Math.Min(variable.NumberOfValues - 1, varNum)]));
//        if (!string.IsNullOrEmpty(parameter1))
//          parameter2 = Item.Format("<font color={0}>{1}</font>", (object) parameter1, (object) parameter2);
//      }
//      return parameter2;
//    }
//
//    private void GetDynamicRequirementsFromRecord(
//      SortedList<string, Variable> requirements,
//      Info itemInfo)
//    {
//      DBRecord recordFromFile1 = Database.GetRecordFromFile(itemInfo.ItemId);
//      if (recordFromFile1 == null)
//        return;
//      string str1 = "itemLevel";
//      Variable variable1 = recordFromFile1[str1];
//      if (variable1 == null)
//        return;
//      string stringValue1 = variable1.ToStringValue();
//      string itemId = itemInfo.GetString("itemCostName");
//      DBRecord recordFromFile2 = Database.GetRecordFromFile(itemId);
//      if (recordFromFile2 == null)
//        return;
//      string requirementEquationPrefix = Item.GetRequirementEquationPrefix(itemInfo.ItemClass);
//      foreach (Variable variable2 in recordFromFile2)
//      {
//        if (string.Compare(variable2.Name, 0, requirementEquationPrefix, 0, requirementEquationPrefix.Length, StringComparison.OrdinalIgnoreCase) <= 0)
//        {
//          if (Item.FilterValue(variable2, true))
//            break;
//          string str2 = variable2.Name.Replace(requirementEquationPrefix, string.Empty).Replace("Equation", string.Empty);
//          string key = str2.Replace(str2[0], char.uppercase(str2[0]));
//          if (key.Equals("Level"))
//            key = "LevelRequirement";
//          if (!key.Equals("Cost"))
//          {
//            double num1;
//            try
//            {
//              num1 = Math.Ceiling(Convert.ToDouble((object) ExpressionEvaluate.CreateExpression(variable2.ToStringValue().Replace(str1, stringValue1).Replace("totalAttCount", Convert.ToString(this.attributeCount, (IFormatProvider) CultureInfo.InvariantCulture))).Evaluate(), (IFormatProvider) CultureInfo.InvariantCulture));
//            }
//            catch (FormatException ex)
//            {
//              num1 = 1.0;
//            }
//            Variable variable3 = new Variable(string.Empty, VariableDataType.Integer, 1);
//            int num2;
//            try
//            {
//              num2 = Convert.ToInt32((object) num1, (IFormatProvider) CultureInfo.InvariantCulture);
//            }
//            catch (OverflowException ex)
//            {
//              num2 = 0;
//            }
//            variable3[0] = (object) num2;
//            string stringValue2 = variable3.ToStringValue();
//            if (requirements.ContainsKey(key))
//            {
//              if (string.Compare(stringValue2, requirements[key].ToStringValue(), StringComparison.OrdinalIgnoreCase) <= 0)
//                break;
//              requirements.Remove(key);
//            }
//            requirements.Add(key, variable3);
//          }
//        }
//      }
//    }
//
//    private static string GetFormulae(
//      List<string> results,
//      Variable variable,
//      ItemAttributesData attributeData,
//      string line,
//      ref string font)
//    {
//      if (attributeData.FullAttribute.StartsWith("reagent", StringComparison.OrdinalIgnoreCase))
//      {
//        DBRecord recordFromFile = Database.GetRecordFromFile(variable.GetString(0));
//        if (recordFromFile != null)
//        {
//          string tagId = recordFromFile.GetString("description", 0);
//          if (!string.IsNullOrEmpty(tagId))
//          {
//            string friendlyName = Database.GetFriendlyName(tagId);
//            string formatSpec = "{0}";
//            object[] objArray = new object[1]
//            {
//              (object) Database.HtmlColor(Item.GetColor(ItemStyle.Common))
//            };
//            font = string.Format((IFormatProvider) CultureInfo.CurrentCulture, "<font color={0}>", objArray);
//            line = Item.Format(formatSpec, (object) friendlyName);
//          }
//        }
//        return line;
//      }
//      if (attributeData.FullAttribute.Equals("artifactCreationCost"))
//      {
//        string formatSpec = ItemAttributes.ConvertFormat(Database.GetFriendlyName("xtagArtifactCost"));
//        object[] objArray1 = new object[1]
//        {
//          (object) Database.HtmlColor(Item.GetColor(ItemStyle.Rare))
//        };
//        font = string.Format((IFormatProvider) CultureInfo.CurrentCulture, "<font color={0}>", objArray1);
//        results.Add(string.Empty);
//        object[] objArray2 = new object[1]{ variable[0] };
//        line = Item.Format(formatSpec, (object) string.Format((IFormatProvider) CultureInfo.CurrentCulture, "{0:N0}", objArray2));
//      }
//      return line;
//    }
//
//    private static string GetGlobalChance(
//      List<Variable> attributeList,
//      int varNum,
//      Variable v,
//      ref string font)
//    {
//      string tagId = "GlobalPercentChanceOfAllTag";
//      if (attributeList.Count > 2)
//        return string.Empty;
//      if (attributeList.Count > 1)
//        tagId = "GlobalPercentChanceOfOneTag";
//      string friendlyName = Database.GetFriendlyName(tagId);
//      string formatSpec;
//      if (friendlyName == null)
//      {
//        formatSpec = string.Format((IFormatProvider) CultureInfo.CurrentCulture, "{0:f1}% ?{0}?", new object[1]
//        {
//          (object) tagId
//        });
//        object[] objArray = new object[1]
//        {
//          (object) Database.HtmlColor(Item.GetColor(ItemStyle.Legendary))
//        };
//        font = string.Format((IFormatProvider) CultureInfo.CurrentCulture, "<font color={0}>", objArray);
//      }
//      else
//      {
//        formatSpec = ItemAttributes.ConvertFormat(friendlyName);
//        object[] objArray = new object[1]
//        {
//          (object) Database.HtmlColor(Item.GetColor(ItemStyle.Epic))
//        };
//        font = string.Format((IFormatProvider) CultureInfo.CurrentCulture, "<font color={0}>", objArray);
//      }
//      return Item.Format(formatSpec, v[Math.Min(v.NumberOfValues - 1, varNum)]);
//    }
//
//    private static string GetGrantedSkill(
//      DBRecord record,
//      List<string> results,
//      Variable variable,
//      string line,
//      ref string font)
//    {
//      DBRecord recordFromFile1 = Database.GetRecordFromFile(variable.GetString(0));
//      if (recordFromFile1 == null)
//        return line;
//      results.Add(string.Empty);
//      object[] objArray1 = new object[1]
//      {
//        (object) Database.HtmlColor(Item.GetColor(ItemStyle.Mundane))
//      };
//      font = string.Format((IFormatProvider) CultureInfo.CurrentCulture, "<font color={0}>", objArray1);
//      string str1 = Database.GetFriendlyName("tagItemGrantSkill");
//      if (string.IsNullOrEmpty(str1))
//        str1 = "Grants Skill :";
//      results.Add(font + str1 + "</font>");
//      string parameter1 = (string) null;
//      string itemId1 = recordFromFile1.GetString("buffSkillName", 0);
//      if (string.IsNullOrEmpty(itemId1))
//      {
//        string tagId = recordFromFile1.GetString("skillDisplayName", 0);
//        if (!string.IsNullOrEmpty(tagId))
//        {
//          parameter1 = Database.GetFriendlyName(tagId);
//          if (string.IsNullOrEmpty(parameter1))
//            parameter1 = Path.GetFileNameWithoutExtension(variable.GetString(0));
//        }
//      }
//      else
//      {
//        DBRecord recordFromFile2 = Database.GetRecordFromFile(itemId1);
//        if (recordFromFile2 != null)
//        {
//          string tagId = recordFromFile2.GetString("skillDisplayName", 0);
//          if (!string.IsNullOrEmpty(tagId))
//          {
//            parameter1 = Database.GetFriendlyName(tagId);
//            if (string.IsNullOrEmpty(parameter1))
//              parameter1 = Path.GetFileNameWithoutExtension(variable.GetString(0));
//          }
//        }
//      }
//      string str2 = (string) null;
//      string tagId1 = (string) null;
//      string itemId2 = record.GetString("itemSkillAutoController", 0);
//      if (!string.IsNullOrEmpty(itemId2))
//      {
//        DBRecord recordFromFile3 = Database.GetRecordFromFile(itemId2);
//        if (recordFromFile3 != null)
//          str2 = recordFromFile3.GetString("triggerType", 0);
//      }
//      if (!string.IsNullOrEmpty(str2))
//      {
//        switch (str2.uppercase())
//        {
//          case "ATTACKENEMY":
//            tagId1 = "xtagAutoSkillCondition07";
//            break;
//          case "CASTBUFF":
//            tagId1 = "xtagAutoSkillCondition06";
//            break;
//          case "HITBYENEMY":
//            tagId1 = "xtagAutoSkillCondition03";
//            break;
//          case "HITBYMELEE":
//            tagId1 = "xtagAutoSkillCondition04";
//            break;
//          case "HITBYPROJECTILE":
//            tagId1 = "xtagAutoSkillCondition05";
//            break;
//          case "LOWHEALTH":
//            tagId1 = "xtagAutoSkillCondition01";
//            break;
//          case "LOWMANA":
//            tagId1 = "xtagAutoSkillCondition02";
//            break;
//          case "ONEQUIP":
//            tagId1 = "xtagAutoSkillCondition08";
//            break;
//          default:
//            tagId1 = string.Empty;
//            break;
//        }
//      }
//      string parameter2 = string.IsNullOrEmpty(tagId1) ? string.Empty : Database.GetFriendlyName(tagId1);
//      if (string.IsNullOrEmpty(parameter2))
//      {
//        object[] objArray2 = new object[1]
//        {
//          (object) Database.HtmlColor(Item.GetColor(ItemStyle.Epic))
//        };
//        font = string.Format((IFormatProvider) CultureInfo.CurrentCulture, "<font color={0}>", objArray2);
//      }
//      else
//      {
//        object[] objArray3 = new object[1]
//        {
//          (object) Database.HtmlColor(Item.GetColor(ItemStyle.Mundane))
//        };
//        font = string.Format((IFormatProvider) CultureInfo.CurrentCulture, "<font color={0}>", objArray3);
//      }
//      line = Item.Format("{0} {1}", (object) parameter1, (object) parameter2);
//      return line;
//    }
//
//    public string GetItemSetString()
//    {
//      if (this.setItemsString != null)
//      {
//        string[] setItems = this.GetSetItems(true);
//        if (setItems != null)
//        {
//          string[] strArray = new string[setItems.Length];
//          int num = 0;
//          foreach (string str1 in setItems)
//          {
//            if (num == 0)
//            {
//              string friendlyName = Database.GetFriendlyName(str1);
//              object[] objArray = new object[2]
//              {
//                (object) Database.HtmlColor(Item.GetColor(ItemStyle.Rare)),
//                (object) friendlyName
//              };
//              strArray[num++] = string.Format((IFormatProvider) CultureInfo.CurrentCulture, "<font color={0}>{1}</font>", objArray);
//            }
//            else
//            {
//              Info info = Database.GetInfo(str1);
//              if (info != null)
//              {
//                string str2 = "&nbsp;&nbsp;&nbsp;&nbsp;" + Database.GetFriendlyName(info.DescriptionTag);
//                object[] objArray = new object[2]
//                {
//                  (object) Database.HtmlColor(Item.GetColor(ItemStyle.Common)),
//                  (object) str2
//                };
//                strArray[num++] = string.Format((IFormatProvider) CultureInfo.CurrentCulture, "<font color={0}>{1}</font>", objArray);
//              }
//            }
//          }
//          this.setItemsString = string.Join("<br>", strArray);
//        }
//        else
//          this.setItemsString = string.Empty;
//      }
//      return this.setItemsString;
//    }
//
//    private string GetLabelAndColorFromTag(
//      ItemAttributesData data,
//      string recordId,
//      ref string labelTag,
//      ref string labelColor)
//    {
//      labelTag = ItemAttributes.GetAttributeTextTag(data);
//      if (string.IsNullOrEmpty(labelTag))
//      {
//        labelTag = "?" + data.FullAttribute + "?";
//        labelColor = Database.HtmlColor(Item.GetColor(ItemStyle.Legendary));
//      }
//      if (labelTag.uppercase().Equals("DEFENSEABSORPTIONPROTECTION"))
//      {
//        if (!this.IsArmor || recordId != this.BaseItemId)
//        {
//          labelTag = "DefenseAbsorptionProtectionBonus";
//          labelColor = Database.HtmlColor(Item.GetColor(ItemStyle.Epic));
//        }
//        else
//          labelColor = Database.HtmlColor(Item.GetColor(ItemStyle.Mundane));
//      }
//      string labelAndColorFromTag = Database.GetFriendlyName(labelTag);
//      if (string.IsNullOrEmpty(labelAndColorFromTag))
//      {
//        labelAndColorFromTag = "?" + labelTag + "?";
//        labelColor = Database.HtmlColor(Item.GetColor(ItemStyle.Legendary));
//      }
//      return labelAndColorFromTag;
//    }
//
//    private static string GetModifier(
//      ItemAttributesData data,
//      int varNum,
//      ItemAttributesData modifierData,
//      Variable modifierVar)
//    {
//      string parameter1 = (string) null;
//      string attributeTextTag = ItemAttributes.GetAttributeTextTag(data);
//      string formatSpec;
//      if (string.IsNullOrEmpty(attributeTextTag))
//      {
//        formatSpec = "{0:f1}% ?" + modifierData.FullAttribute + "?";
//        parameter1 = Database.HtmlColor(Item.GetColor(ItemStyle.Legendary));
//      }
//      else
//      {
//        string friendlyName = Database.GetFriendlyName(attributeTextTag);
//        if (string.IsNullOrEmpty(friendlyName))
//        {
//          formatSpec = "{0:f1}% ?" + modifierData.FullAttribute + "?";
//          parameter1 = Database.HtmlColor(Item.GetColor(ItemStyle.Legendary));
//        }
//        else
//          formatSpec = ItemAttributes.ConvertFormat(friendlyName);
//      }
//      string parameter2 = Database.MakeSafeForHtml(Item.Format(formatSpec, modifierVar[Math.Min(modifierVar.NumberOfValues - 1, varNum)]));
//      if (!string.IsNullOrEmpty(parameter1))
//        parameter2 = Item.Format("<font color={0}>{1}</font>", (object) parameter1, (object) parameter2);
//      return parameter2;
//    }
//
//    private static string GetPetBonusName(ref string font)
//    {
//      string tagId = "xtagPetBonusNameAllPets";
//      string friendlyName = Database.GetFriendlyName(tagId);
//      if (string.IsNullOrEmpty(friendlyName))
//      {
//        string petBonusName = "?Bonus to All Pets:?";
//        object[] objArray = new object[1]
//        {
//          (object) Database.HtmlColor(Item.GetColor(ItemStyle.Legendary))
//        };
//        font = string.Format((IFormatProvider) CultureInfo.CurrentCulture, "<font color={0}>", objArray);
//        return petBonusName;
//      }
//      string petBonusName1 = ItemAttributes.ConvertFormat(friendlyName);
//      object[] objArray1 = new object[1]
//      {
//        (object) Database.HtmlColor(Item.GetColor(ItemStyle.Relic))
//      };
//      font = string.Format((IFormatProvider) CultureInfo.CurrentCulture, "<font color={0}>", objArray1);
//      return petBonusName1;
//    }
//
//    private int GetPetSkillLevel(DBRecord record, string recordId, int varNum)
//    {
//      if (record.GetString("Class", 0).uppercase().StartsWith("SKILL_ATTACK", StringComparison.OrdinalIgnoreCase))
//      {
//        string itemId = Database.GetRecordFromFile(this.baseItemInfo.GetString("skillName")).GetString("spawnObjects", 0);
//        if (string.IsNullOrEmpty(itemId))
//          return varNum;
//        DBRecord recordFromFile = Database.GetRecordFromFile(itemId);
//        int num = 0;
//        for (int index = 0; index < 17 && !(recordFromFile.GetString("skillName" + (object) index, 0) == recordId); ++index)
//          ++num;
//        varNum = Math.Max(recordFromFile.GetInt32("skillLevel" + (object) num, 0), 1) - 1;
//      }
//      return varNum;
//    }
//
//    private static string GetRacialBonus(
//      DBRecord record,
//      List<string> results,
//      int varNum,
//      bool isGlobal,
//      string globalIndent,
//      Variable v,
//      ItemAttributesData d,
//      string line,
//      ref string font)
//    {
//      string[] allStrings = record.GetAllStrings("racialBonusRace");
//      if (allStrings != null)
//      {
//        for (int index = 0; index < allStrings.Length; ++index)
//        {
//          string parameter2 = Database.GetFriendlyName(allStrings[index]);
//          if (parameter2 == null)
//          {
//            allStrings[index] = allStrings[index] + "s";
//            parameter2 = Database.GetFriendlyName(allStrings[index]);
//          }
//          if (parameter2 == null)
//            parameter2 = allStrings[index].Remove(allStrings[index].Length - 1);
//          string tagId = d.FullAttribute.Substring(0, 1).uppercase() + d.FullAttribute.Substring(1);
//          string friendlyName = Database.GetFriendlyName(tagId);
//          string formatSpec = friendlyName != null ? ItemAttributes.ConvertFormat(friendlyName) : tagId + " {0} {1}";
//          if (line != null)
//          {
//            line = Database.MakeSafeForHtml(line);
//            if (d.Variable.Length > 0)
//            {
//              string str1 = Database.MakeSafeForHtml(d.Variable);
//              string str2 = string.Format((IFormatProvider) CultureInfo.CurrentCulture, "<font color={0}>{1}</font>", new object[2]
//              {
//                (object) Database.HtmlColor(Item.GetColor(ItemStyle.Legendary)),
//                (object) str1
//              });
//              line += str2;
//            }
//            if (font != null)
//              line = font + line + "</font>";
//            if (isGlobal)
//              line = globalIndent + line;
//            results.Add(line);
//          }
//          line = Item.Format(formatSpec, v[Math.Min(v.NumberOfValues - 1, varNum)], (object) parameter2);
//          object[] objArray = new object[1]
//          {
//            (object) Database.HtmlColor(Item.GetColor(ItemStyle.Epic))
//          };
//          font = string.Format((IFormatProvider) CultureInfo.CurrentCulture, "<font color={0}>", objArray);
//        }
//      }
//      return line;
//    }
//
//    private static string GetRawAttribute(
//      ItemAttributesData attributeData,
//      int variableNumber,
//      Variable variable,
//      ref string font)
//    {
//      string tagId = ItemAttributes.GetAttributeTextTag(attributeData);
//      if (tagId == null)
//      {
//        tagId = "?" + attributeData.FullAttribute + "?";
//        object[] objArray = new object[1]
//        {
//          (object) Database.HtmlColor(Item.GetColor(ItemStyle.Legendary))
//        };
//        font = string.Format((IFormatProvider) CultureInfo.CurrentCulture, "<font color={0}>", objArray);
//      }
//      string formatValue = Database.GetFriendlyName(tagId);
//      if (formatValue == null)
//      {
//        formatValue = "?" + tagId + "?";
//        object[] objArray = new object[1]
//        {
//          (object) Database.HtmlColor(Item.GetColor(ItemStyle.Legendary))
//        };
//        font = string.Format((IFormatProvider) CultureInfo.CurrentCulture, "<font color={0}>", objArray);
//      }
//      string formatSpec = ItemAttributes.ConvertFormat(formatValue);
//      string rawAttribute;
//      if (formatSpec.IndexOf('{') >= 0)
//      {
//        rawAttribute = Item.Format(formatSpec, variable[Math.Min(variable.NumberOfValues - 1, variableNumber)]);
//        if (font == null)
//        {
//          object[] objArray = new object[1]
//          {
//            (object) Database.HtmlColor(Item.GetColor(ItemStyle.Epic))
//          };
//          font = string.Format((IFormatProvider) CultureInfo.CurrentCulture, "<font color={0}>", objArray);
//        }
//      }
//      else
//        rawAttribute = Database.VariableToStringNice(variable);
//      if (font == null)
//      {
//        object[] objArray = new object[1]
//        {
//          (object) Database.HtmlColor(Item.GetColor(ItemStyle.Legendary))
//        };
//        font = string.Format((IFormatProvider) CultureInfo.CurrentCulture, "<font color={0}>", objArray);
//      }
//      return rawAttribute;
//    }
//
//    private static string GetRequirementEquationPrefix(string itemClass)
//    {
//      switch (itemClass.uppercase())
//      {
//        case "ARMORJEWELRY_AMULET":
//          return "amulet";
//        case "ARMORJEWELRY_BRACELET":
//          return "bracelet";
//        case "ARMORJEWELRY_RING":
//          return "ring";
//        case "ARMORPROTECTIVE_FOREARM":
//          return "forearm";
//        case "ARMORPROTECTIVE_HEAD":
//          return "head";
//        case "ARMORPROTECTIVE_LOWERBODY":
//          return "lowerBody";
//        case "ARMORPROTECTIVE_UPPERBODY":
//          return "upperBody";
//        case "WEAPONARMOR_SHIELD":
//          return "shield";
//        case "WEAPONHUNTING_BOW":
//          return "bow";
//        case "WEAPONHUNTING_RANGEDONEHAND":
//          return "rangedOneHand";
//        case "WEAPONHUNTING_SPEAR":
//          return "spear";
//        case "WEAPONMAGICAL_STAFF":
//          return "staff";
//        case "WEAPONMELEE_AXE":
//          return "axe";
//        case "WEAPONMELEE_MACE":
//          return "mace";
//        case "WEAPONMELEE_SWORD":
//          return "sword";
//        default:
//          return "none";
//      }
//    }
//
//    public string GetRequirements()
//    {
//      if (this.requirementsString != null)
//      {
//        SortedList<string, Variable> requirements = new SortedList<string, Variable>();
//        if (this.baseItemInfo != null)
//        {
//          Item.GetRequirementsFromRecord(requirements, Database.GetRecordFromFile(this.BaseItemId));
//          this.GetDynamicRequirementsFromRecord(requirements, this.baseItemInfo);
//        }
//        if (this.prefixInfo != null)
//          Item.GetRequirementsFromRecord(requirements, Database.GetRecordFromFile(this.prefixID));
//        if (this.suffixInfo != null)
//          Item.GetRequirementsFromRecord(requirements, Database.GetRecordFromFile(this.suffixID));
//        if (this.RelicInfo != null)
//          Item.GetRequirementsFromRecord(requirements, Database.GetRecordFromFile(this.relicID));
//        if (this.IsFormulae && this.baseItemInfo != null)
//        {
//          string itemId = this.baseItemInfo.GetString("artifactName");
//          Item.GetRequirementsFromRecord(requirements, Database.GetRecordFromFile(itemId));
//        }
//        string friendlyName1 = Database.GetFriendlyName("MeetsRequirement");
//        string formatSpec = friendlyName1 != null ? ItemAttributes.ConvertFormat(friendlyName1) : "?Required? {0}: {1:f0}";
//        List<string> stringList = new List<string>();
//        foreach (KeyValuePair<string, Variable> keyValuePair in requirements)
//        {
//          Variable variable = keyValuePair.Value;
//          string text;
//          if (variable.NumberOfValues > 1 || variable.DataType == VariableDataType.StringVar)
//          {
//            text = keyValuePair.Key + ": " + variable.ToStringValue();
//          }
//          else
//          {
//            string friendlyName2 = Database.GetFriendlyName(keyValuePair.Key);
//            if (friendlyName2 == null)
//            {
//              string str = "?" + keyValuePair.Key + "?";
//              continue;
//            }
//            text = Item.Format(formatSpec, (object) friendlyName2, variable[0]);
//          }
//          string str1 = string.Format((IFormatProvider) CultureInfo.CurrentCulture, "<font color={0}>{1}</font>", new object[2]
//          {
//            (object) Database.HtmlColor(Item.GetColor(ItemStyle.Broken)),
//            (object) Database.MakeSafeForHtml(text)
//          });
//          stringList.Add(str1);
//        }
//        if (stringList.Count > 0)
//        {
//          string[] array = new string[stringList.Count];
//          stringList.CopyTo(array);
//          this.requirementsString = string.Join("<br>", array);
//        }
//        else
//          this.requirementsString = string.Empty;
//      }
//      return this.requirementsString;
//    }
//
//    private static void GetRequirementsFromRecord(
//      SortedList<string, Variable> requirements,
//      DBRecord record)
//    {
//      if (record == null)
//      {
//        Logger.Debug("GetRequirementsFromRecord Error - record was null. ");
//      }
//      else
//      {
//        if (TQDebug.ItemDebugLevel > 1)
//          Logger.Debug(record.Id);
//        foreach (Variable variable in record)
//        {
//          if (TQDebug.ItemDebugLevel > 2)
//            Logger.Debug(variable.Name);
//          if (!Item.FilterValue(variable, false) && Item.FilterRequirements(variable.Name))
//          {
//            string stringValue = variable.ToStringValue();
//            string str1 = variable.Name.Replace("Requirement", string.Empty);
//            string str2 = str1.Substring(0, 1).ToUpper(CultureInfo.InvariantCulture) + str1.Substring(1);
//            if (str2.Equals("Level"))
//              str2 = "LevelRequirement";
//            if (requirements.ContainsKey(str2))
//            {
//              Variable requirement = requirements[str2];
//              if (stringValue.Contains(",") || requirement.Name.Contains(","))
//              {
//                if (string.Compare(stringValue, requirement.ToStringValue(), StringComparison.OrdinalIgnoreCase) > 0)
//                  requirements.Remove(str2);
//                else
//                  continue;
//              }
//              else if (variable.GetInt32(0) > requirement.GetInt32(0))
//                requirements.Remove(str2);
//              else
//                continue;
//            }
//            if (TQDebug.ItemDebugLevel > 1)
//              Logger.Debug(Item.Format("Added Requirement {0}={1}", (object) str2, (object) stringValue));
//            requirements.Add(str2, variable);
//          }
//        }
//        if (TQDebug.ItemDebugLevel <= 1)
//          return;
//        Logger.Debug("Exiting Item.GetDynamicRequirementsFromRecord()");
//      }
//    }
//
//    public string[] GetSetItems(bool includeName)
//    {
//      if (this.baseItemInfo == null)
//        return (string[]) null;
//      string itemId = this.baseItemInfo.GetString("itemSetName");
//      if (string.IsNullOrEmpty(itemId))
//        return (string[]) null;
//      DBRecord recordFromFile = Database.GetRecordFromFile(itemId);
//      if (recordFromFile == null)
//        return (string[]) null;
//      string[] allStrings = recordFromFile.GetAllStrings("setMembers");
//      if (allStrings == null || allStrings.Length == 0)
//        return (string[]) null;
//      if (!includeName)
//        return allStrings;
//      string[] setItems = new string[allStrings.Length + 1];
//      setItems[0] = recordFromFile.GetString("setName", 0);
//      allStrings.CopyTo((Array) setItems, 1);
//      return setItems;
//    }
//
//    private void GetSkillDescriptionAndEffects(
//      DBRecord record,
//      List<string> results,
//      Variable variable,
//      string line)
//    {
//      if (string.IsNullOrEmpty(record.GetString("itemSkillAutoController", 0)) && !this.IsScroll)
//        return;
//      DBRecord dbRecord = Database.GetRecordFromFile(variable.GetString(0));
//      string str1 = string.Empty;
//      if (!this.IsScroll)
//      {
//        string str2 = line;
//        string str3 = str2.Remove(str2.IndexOf("</b>", StringComparison.OrdinalIgnoreCase));
//        str1 = str3.Substring(str3.IndexOf("<b>", StringComparison.OrdinalIgnoreCase));
//      }
//      int columns = str1.Length;
//      if (columns < 30)
//        columns = 30;
//      if (dbRecord == null)
//        return;
//      string str4 = dbRecord.GetString("buffSkillName", 0);
//      if (!this.IsScroll)
//      {
//        if (string.IsNullOrEmpty(str4))
//        {
//          string tagId = dbRecord.GetString("skillBaseDescription", 0);
//          if (tagId.Length > 0)
//          {
//            string friendlyName1 = Database.GetFriendlyName(tagId);
//            if (friendlyName1.Length > 0)
//            {
//              foreach (string wrapWord in Database.WrapWords(Database.MakeSafeForHtml(friendlyName1), columns))
//              {
//                object[] objArray = new object[2]
//                {
//                  (object) Database.HtmlColor(Item.GetColor(ItemStyle.Mundane)),
//                  (object) wrapWord
//                };
//                results.Add(string.Format((IFormatProvider) CultureInfo.CurrentCulture, "<font color={0}>&nbsp;&nbsp;&nbsp;&nbsp;{1}</font>", objArray));
//              }
//              if (Item.ShowSkillLevel)
//              {
//                string friendlyName2 = Database.GetFriendlyName("MenuLevel");
//                string formatSpec = !string.IsNullOrEmpty(friendlyName2) ? ItemAttributes.ConvertFormat(friendlyName2) : "Level:   {0}";
//                int int32 = record.GetInt32("itemSkillLevel", 0);
//                if (int32 > 0)
//                {
//                  line = Item.Format(formatSpec, (object) int32);
//                  line = Database.MakeSafeForHtml(line);
//                  object[] objArray = new object[2]
//                  {
//                    (object) Database.HtmlColor(Item.GetColor(ItemStyle.Mundane)),
//                    (object) line
//                  };
//                  results.Add(string.Format((IFormatProvider) CultureInfo.CurrentCulture, "<font color={0}>&nbsp;&nbsp;&nbsp;&nbsp;{1}</font>", objArray));
//                }
//              }
//            }
//          }
//        }
//        else
//        {
//          DBRecord recordFromFile = Database.GetRecordFromFile(str4);
//          if (recordFromFile != null)
//          {
//            string tagId = recordFromFile.GetString("skillBaseDescription", 0);
//            if (!string.IsNullOrEmpty(tagId))
//            {
//              foreach (string wrapWord in Database.WrapWords(Database.GetFriendlyName(tagId), columns))
//              {
//                object[] objArray = new object[2]
//                {
//                  (object) Database.HtmlColor(Item.GetColor(ItemStyle.Mundane)),
//                  (object) wrapWord
//                };
//                results.Add(string.Format((IFormatProvider) CultureInfo.CurrentCulture, "<font color={0}>&nbsp;&nbsp;&nbsp;&nbsp;{1}</font>", objArray));
//              }
//              if (Item.ShowSkillLevel)
//              {
//                string friendlyName = Database.GetFriendlyName("MenuLevel");
//                string formatSpec = !string.IsNullOrEmpty(friendlyName) ? ItemAttributes.ConvertFormat(friendlyName) : "Level:   {0}";
//                int int32 = record.GetInt32("itemSkillLevel", 0);
//                if (int32 > 0)
//                {
//                  line = Item.Format(formatSpec, (object) int32);
//                  line = Database.MakeSafeForHtml(line);
//                  object[] objArray = new object[2]
//                  {
//                    (object) Database.HtmlColor(Item.GetColor(ItemStyle.Mundane)),
//                    (object) line
//                  };
//                  results.Add(string.Format((IFormatProvider) CultureInfo.CurrentCulture, "<font color={0}>&nbsp;&nbsp;&nbsp;&nbsp;{1}</font>", objArray));
//                }
//              }
//            }
//          }
//        }
//      }
//      if (dbRecord.GetString("skillDisplayName", 0).Length == 0 && string.IsNullOrEmpty(str4) && !this.IsScroll)
//        dbRecord = (DBRecord) null;
//      if (dbRecord != null && !this.IsScroll)
//        results.Add(string.Empty);
//      if (dbRecord == null)
//        return;
//      if (dbRecord.GetString("Class", 0).uppercase().Equals("SKILL_SPAWNPET"))
//        this.ConvertPetStats(dbRecord, results);
//      else if (!string.IsNullOrEmpty(str4))
//        this.GetAttributesFromRecord(Database.GetRecordFromFile(str4), true, str4, results);
//      else
//        this.GetAttributesFromRecord(dbRecord, true, variable.GetString(0), results);
//    }
//
//    private static string GetSkillEffect(
//      ItemAttributesData baseAttributeData,
//      int variableNumber,
//      Variable variable,
//      ItemAttributesData currentAttributeData,
//      string line,
//      ref string font)
//    {
//      string tagId1 = ItemAttributes.GetAttributeTextTag(baseAttributeData);
//      if (string.IsNullOrEmpty(tagId1))
//      {
//        tagId1 = "?" + baseAttributeData.FullAttribute + "?";
//        object[] objArray = new object[1]
//        {
//          (object) Database.HtmlColor(Item.GetColor(ItemStyle.Legendary))
//        };
//        font = string.Format((IFormatProvider) CultureInfo.CurrentCulture, "<font color={0}>", objArray);
//      }
//      string formatValue = Database.GetFriendlyName(tagId1);
//      if (string.IsNullOrEmpty(formatValue))
//      {
//        formatValue = "?" + tagId1 + "?";
//        object[] objArray = new object[1]
//        {
//          (object) Database.HtmlColor(Item.GetColor(ItemStyle.Legendary))
//        };
//        font = string.Format((IFormatProvider) CultureInfo.CurrentCulture, "<font color={0}>", objArray);
//      }
//      if (TQDebug.ItemDebugLevel > 2)
//        Logger.Debug("Item.label (scroll) = " + formatValue);
//      string str = ItemAttributes.ConvertFormat(formatValue);
//      string tagId2 = (string) null;
//      string formatSpec = (string) null;
//      if (currentAttributeData.FullAttribute.EndsWith("Cost", StringComparison.OrdinalIgnoreCase))
//        tagId2 = "SkillIntFormat";
//      else if (currentAttributeData.FullAttribute.EndsWith("Duration", StringComparison.OrdinalIgnoreCase))
//        tagId2 = "SkillSecondFormat";
//      else if (currentAttributeData.FullAttribute.EndsWith("Radius", StringComparison.OrdinalIgnoreCase))
//        tagId2 = "SkillDistanceFormat";
//      if (!string.IsNullOrEmpty(tagId2))
//      {
//        string friendlyName = Database.GetFriendlyName(tagId2);
//        if (string.IsNullOrEmpty(friendlyName))
//        {
//          formatSpec = "?{0} {1}?";
//          object[] objArray = new object[1]
//          {
//            (object) Database.HtmlColor(Item.GetColor(ItemStyle.Legendary))
//          };
//          font = string.Format((IFormatProvider) CultureInfo.CurrentCulture, "<font color={0}>", objArray);
//        }
//        else
//        {
//          if (TQDebug.ItemDebugLevel > 2)
//            Logger.Debug("Item.formatspec (2 parameter) = " + friendlyName);
//          formatSpec = ItemAttributes.ConvertFormat(friendlyName);
//          object[] objArray = new object[1]
//          {
//            (object) Database.HtmlColor(Item.GetColor(ItemStyle.Epic))
//          };
//          font = string.Format((IFormatProvider) CultureInfo.CurrentCulture, "<font color={0}>", objArray);
//        }
//      }
//      if (string.IsNullOrEmpty(tagId2))
//      {
//        object[] objArray = new object[1]
//        {
//          (object) Database.HtmlColor(Item.GetColor(ItemStyle.Epic))
//        };
//        font = string.Format((IFormatProvider) CultureInfo.CurrentCulture, "<font color={0}>", objArray);
//        line = Item.Format(str, variable[Math.Min(variable.NumberOfValues - 1, variableNumber)]);
//        return line;
//      }
//      line = Item.Format(formatSpec, variable[Math.Min(variable.NumberOfValues - 1, variableNumber)], (object) str);
//      return line;
//    }
//
//    private int GetTriggeredSkillLevel(DBRecord record, string recordId, int varNum)
//    {
//      DBRecord recordFromFile1 = Database.GetRecordFromFile(this.baseItemInfo.ItemId);
//      if (recordFromFile1.GetString("itemSkillAutoController", 0) != null)
//      {
//        int int32 = recordFromFile1.GetInt32("itemSkillLevel", 0);
//        if (record.GetString("Class", 0).uppercase().StartsWith("SKILLBUFF", StringComparison.OrdinalIgnoreCase))
//        {
//          DBRecord recordFromFile2 = Database.GetRecordFromFile(this.baseItemInfo.GetString("itemSkillName"));
//          if (recordFromFile2 != null && recordFromFile2.GetString("buffSkillName", 0) == recordId)
//            varNum = Math.Max(int32, 1) - 1;
//          return varNum;
//        }
//        if (recordFromFile1.GetString("itemSkillName", 0) == recordId)
//          varNum = Math.Max(int32, 1) - 1;
//      }
//      return varNum;
//    }
//
    private fun hasSubstring(fullstring:String, substring:String):Boolean = fullstring != null && fullstring.uppercase().indexOf(substring,0,true) >= 0;
//
//
//    private bool isInfluenceDamage(string dataEffect) => dataEffect.EndsWith("Stun", StringComparison.OrdinalIgnoreCase) || dataEffect.EndsWith("Freeze", StringComparison.OrdinalIgnoreCase) || dataEffect.EndsWith("Petrify", StringComparison.OrdinalIgnoreCase) || dataEffect.EndsWith("Trap", StringComparison.OrdinalIgnoreCase) || dataEffect.EndsWith("Convert", StringComparison.OrdinalIgnoreCase) || dataEffect.EndsWith("Fear", StringComparison.OrdinalIgnoreCase) || dataEffect.EndsWith("Confusion", StringComparison.OrdinalIgnoreCase) || dataEffect.EndsWith("Disruption", StringComparison.OrdinalIgnoreCase);
//
//    public Item MakeEmptyCopy(string baseItemId)
//    {
//      if (string.IsNullOrEmpty(baseItemId))
//        throw new ArgumentNullException(baseItemId, "The base item ID cannot be NULL or Empty.");
//      return new Item()
//      {
//        beginBlockCrap1 = this.beginBlockCrap1,
//        endBlockCrap1 = this.endBlockCrap1,
//        beginBlockCrap2 = this.beginBlockCrap2,
//        endBlockCrap2 = this.endBlockCrap2,
//        BaseItemId = baseItemId,
//        prefixID = string.Empty,
//        suffixID = string.Empty,
//        relicID = string.Empty,
//        RelicBonusId = string.Empty,
//        Seed = Item.GenerateSeed(),
//        Var1 = this.Var1,
//        relicID2 = string.Empty,
//        RelicBonusId2 = string.Empty,
//        Var2 = this.Var2,
//        PositionX = -1,
//        PositionY = -1
//      };
//    }
//
//    public Item MakeEmptyItem() => new Item()
//    {
//      beginBlockCrap1 = this.beginBlockCrap1,
//      endBlockCrap1 = this.endBlockCrap1,
//      beginBlockCrap2 = this.beginBlockCrap2,
//      endBlockCrap2 = this.endBlockCrap2,
//      BaseItemId = string.Empty,
//      prefixID = string.Empty,
//      suffixID = string.Empty,
//      relicID = string.Empty,
//      RelicBonusId = string.Empty,
//      Seed = Item.GenerateSeed(),
//      Var1 = this.Var1,
//      relicID2 = string.Empty,
//      RelicBonusId2 = string.Empty,
//      Var2 = this.Var2,
//      PositionX = -1,
//      PositionY = -1
//    };
//
//    public void MarkModified()
//    {
//      this.attributesString = (string) null;
//      this.requirementsString = (string) null;
//      this.setItemsString = (string) null;
//    }
//
    public fun Parse( reader: DataInputStream)
    {

        if (this.sackType == SackType.Stash)
        {
          TQData.validateNextString("stackCount", reader);
          this.beginBlockCrap1 = reader.readInt()
        }
        else if (this.sackType == SackType.Sack || this.sackType == SackType.Player)
        {
          TQData.validateNextString("begin_block", reader);
          this.beginBlockCrap1 = reader.readInt()
        }
        TQData.validateNextString("begin_block", reader);
        this.beginBlockCrap2 = reader.readInt()
        TQData.validateNextString("baseName", reader);
        this.BaseItemId = TQData.readCString(reader);
        TQData.validateNextString("prefixName", reader);
        this.prefixID = TQData.readCString(reader);
        TQData.validateNextString("suffixName", reader);
        this.suffixID = TQData.readCString(reader);
        TQData.validateNextString("relicName", reader);
        this.relicID = TQData.readCString(reader);
        TQData.validateNextString("relicBonus", reader);
        this.RelicBonusId = TQData.readCString(reader);
        TQData.validateNextString("seed", reader);
        this.seed = reader.readInt()
        TQData.validateNextString("var1", reader);
        this.var1 = reader.readInt()
        if (TQData.readCString(reader).uppercase() == "relicName2".uppercase())
        {
          this.isSecondBonus = true;
          this.relicID2 = TQData.readCString(reader);
          TQData.validateNextString("relicBonus2", reader);
          this.RelicBonusId2 = TQData.readCString(reader);
          TQData.validateNextString("var2", reader);
          this.var2 = reader.readInt()
          TQData.validateNextString("end_block", reader);
        }
        this.endBlockCrap2 = reader.readInt()
        if (this.sackType == SackType.Stash)
        {
          TQData.validateNextString("xOffset", reader);
          this.PositionX =  reader.readFloat().roundToInt()
          TQData.validateNextString("yOffset", reader);
          this.PositionY = reader.readFloat().roundToInt()
        }
        else if (this.sackType == SackType.Equipment)
        {
          this.PositionX = 0;
          this.PositionY = 0;
        }
        else
        {
          TQData.validateNextString("pointX", reader);
          this.PositionX = reader.readInt()
          TQData.validateNextString("pointY", reader);
          this.PositionY = reader.readInt()
          TQData.validateNextString("end_block", reader);
          this.endBlockCrap1 = reader.readInt()
        }
        this.GetDBData();
        if (this.sackType == SackType.Stash)
          this.StackSize = this.beginBlockCrap1 + 1;
        else
          this.StackSize = 1;
      }
 

//
//    public Item PopAllButOneItem()
//    {
//      if (!this.DoesStack || this.StackSize < 2)
//        return (Item) null;
//      Item obj = (Item) this.MemberwiseClone();
//      obj.StackSize = this.StackSize - 1;
//      obj.Var1 = 0;
//      obj.Seed = Item.GenerateSeed();
//      obj.PositionX = -1;
//      obj.PositionY = -1;
//      obj.MarkModified();
//      this.MarkModified();
//      this.StackSize = 1;
//      return obj;
//    }
//
//    public void RefreshBareAttributes() => Array.Clear((Array) this.bareAttributes, 0, this.bareAttributes.Length);
//
//    public Item RemoveRelic()
//    {
//      if (!this.HasRelic)
//        return (Item) null;
//      Item obj = this.MakeEmptyCopy(this.relicID);
//      obj.GetDBData();
//      obj.RelicBonusId = this.RelicBonusId;
//      obj.RelicBonusInfo = this.RelicBonusInfo;
//      this.relicID = string.Empty;
//      this.RelicBonusId = string.Empty;
//      this.Var1 = 0;
//      this.var2 = 0;
//      this.relicID2 = string.Empty;
//      this.relicBonusId2 = string.Empty;
//      this.RelicInfo = (Info) null;
//      this.RelicBonusInfo = (Info) null;
//      this.MarkModified();
//      return obj;
//    }
//
//    private static void SetDefaultStrings()
//    {
//      if (string.IsNullOrEmpty(Item.ItemIT))
//        Item.ItemIT = "Immortal Throne Item";
//      if (string.IsNullOrEmpty(Item.ItemRAG))
//        Item.ItemRAG = "Ragnarok Item";
//      if (string.IsNullOrEmpty(Item.ItemWith))
//        Item.ItemWith = "with";
//      if (string.IsNullOrEmpty(Item.ItemRelicBonus))
//        Item.ItemRelicBonus = "(Completion Bonus: {0})";
//      if (string.IsNullOrEmpty(Item.ItemRelicCompleted))
//        Item.ItemRelicCompleted = "(Completed)";
//      if (string.IsNullOrEmpty(Item.ItemQuest))
//        Item.ItemQuest = "(Quest Item)";
//      if (!string.IsNullOrEmpty(Item.ItemSeed))
//        return;
//      Item.ItemSeed = "itemSeed: {0} (0x{0:X8}) ({1:p3})";
//    }
//
//    public string ToString(bool basicInfoOnly = false, bool relicInfoOnly = false)
//    {
//      string[] strArray = new string[16];
//      int count = 0;
//      if (!relicInfoOnly)
//      {
//        if (!this.IsRelic && !string.IsNullOrEmpty(this.prefixID))
//        {
//          if (this.prefixInfo != null)
//          {
//            strArray[count] = Database.GetFriendlyName(this.prefixInfo.DescriptionTag);
//            if (string.IsNullOrEmpty(strArray[count]))
//              strArray[count] = this.prefixID;
//          }
//          else
//            strArray[count] = this.prefixID;
//          ++count;
//        }
//        if (this.baseItemInfo == null)
//        {
//          strArray[count++] = this.BaseItemId;
//        }
//        else
//        {
//          if (!string.IsNullOrEmpty(this.baseItemInfo.StyleTag) && !this.IsPotion && !this.IsRelic && !this.IsScroll && !this.IsParchment && !this.IsQuestItem)
//          {
//            strArray[count] = Database.GetFriendlyName(this.baseItemInfo.StyleTag);
//            if (string.IsNullOrEmpty(strArray[count]))
//              strArray[count] = this.baseItemInfo.StyleTag;
//            ++count;
//          }
//          if (!string.IsNullOrEmpty(this.baseItemInfo.QualityTag))
//          {
//            strArray[count] = Database.GetFriendlyName(this.baseItemInfo.QualityTag);
//            if (string.IsNullOrEmpty(strArray[count]))
//              strArray[count] = this.baseItemInfo.QualityTag;
//            ++count;
//          }
//          strArray[count] = Database.GetFriendlyName(this.baseItemInfo.DescriptionTag);
//          if (string.IsNullOrEmpty(strArray[count]))
//            strArray[count] = this.BaseItemId;
//          ++count;
//          if (!basicInfoOnly && this.IsRelic)
//          {
//            if (this.IsRelicComplete)
//            {
//              if (!string.IsNullOrEmpty(this.RelicBonusId))
//              {
//                string withoutExtension = Path.GetFileNameWithoutExtension(TQData.NormalizeRecordPath(this.RelicBonusId));
//                string tagId = "tagRelicBonus";
//                if (this.IsCharm)
//                  tagId = "tagAnimalPartcompleteBonus";
//                string str = Database.GetFriendlyName(tagId);
//                if (string.IsNullOrEmpty(str))
//                  str = "Completion Bonus: ";
//                object[] objArray = new object[2]
//                {
//                  (object) str,
//                  (object) withoutExtension
//                };
//                strArray[count] = string.Format((IFormatProvider) CultureInfo.CurrentCulture, "({0} {1})", objArray);
//              }
//              else
//              {
//                string tagId = "tagRelicComplete";
//                if (this.IsCharm)
//                  tagId = "tagAnimalPartComplete";
//                string str = Database.GetFriendlyName(tagId);
//                if (string.IsNullOrEmpty(str))
//                  str = "Completed";
//                strArray[count] = "(" + str + ")";
//              }
//            }
//            else
//            {
//              string tagId1 = "tagRelicShard";
//              string tagId2 = "tagRelicRatio";
//              if (this.IsCharm)
//              {
//                tagId1 = "tagAnimalPart";
//                tagId2 = "tagAnimalPartRatio";
//              }
//              string parameter1 = Database.GetFriendlyName(tagId1);
//              if (string.IsNullOrEmpty(parameter1))
//                parameter1 = "Relic";
//              string friendlyName = Database.GetFriendlyName(tagId2);
//              string formatSpec = !string.IsNullOrEmpty(friendlyName) ? ItemAttributes.ConvertFormat(friendlyName) : "{0} - {1} / {2}";
//              strArray[count] = "(" + Item.Format(formatSpec, (object) parameter1, (object) this.Number, (object) this.baseItemInfo.CompletedRelicLevel) + ")";
//            }
//            ++count;
//          }
//          else if (!basicInfoOnly && this.IsArtifact)
//          {
//            if (!string.IsNullOrEmpty(this.RelicBonusId))
//            {
//              string withoutExtension = Path.GetFileNameWithoutExtension(TQData.NormalizeRecordPath(this.RelicBonusId));
//              string tagId = "xtagArtifactBonus";
//              string str = Database.GetFriendlyName(tagId);
//              if (string.IsNullOrEmpty(str))
//                str = "Completion Bonus: ";
//              object[] objArray = new object[2]
//              {
//                (object) str,
//                (object) withoutExtension
//              };
//              strArray[count] = string.Format((IFormatProvider) CultureInfo.CurrentCulture, "({0} {1})", objArray);
//            }
//            ++count;
//          }
//          else if (this.DoesStack && this.Number > 1)
//          {
//            object[] objArray = new object[1]
//            {
//              (object) this.Number
//            };
//            strArray[count] = string.Format((IFormatProvider) CultureInfo.CurrentCulture, "({0:n0})", objArray);
//            ++count;
//          }
//        }
//        if (!this.IsRelic && this.suffixID.Length > 0)
//        {
//          if (this.suffixInfo != null)
//          {
//            strArray[count] = Database.GetFriendlyName(this.suffixInfo.DescriptionTag);
//            if (string.IsNullOrEmpty(strArray[count]))
//              strArray[count] = this.suffixID;
//          }
//          else
//            strArray[count] = this.suffixID;
//          ++count;
//        }
//      }
//      if (!basicInfoOnly && this.HasRelic)
//      {
//        if (!relicInfoOnly)
//          strArray[count++] = Item.ItemWith;
//        if (this.RelicInfo != null)
//        {
//          strArray[count] = Database.GetFriendlyName(this.RelicInfo.DescriptionTag);
//          if (string.IsNullOrEmpty(strArray[count]))
//            strArray[count] = this.relicID;
//        }
//        else
//          strArray[count] = this.relicID;
//        ++count;
//        if (!relicInfoOnly)
//        {
//          if (this.RelicInfo != null)
//          {
//            if (this.Var1 >= this.RelicInfo.CompletedRelicLevel)
//            {
//              if (!relicInfoOnly && !string.IsNullOrEmpty(this.RelicBonusId))
//              {
//                object[] objArray = new object[1]
//                {
//                  (object) Path.GetFileNameWithoutExtension(TQData.NormalizeRecordPath(this.RelicBonusId))
//                };
//                strArray[count] = string.Format((IFormatProvider) CultureInfo.CurrentCulture, Item.ItemRelicBonus, objArray);
//              }
//              else
//                strArray[count] = Item.ItemRelicCompleted;
//            }
//            else
//            {
//              object[] objArray = new object[2]
//              {
//                (object) Math.Max(1, this.Var1),
//                (object) this.RelicInfo.CompletedRelicLevel
//              };
//              strArray[count] = string.Format((IFormatProvider) CultureInfo.CurrentCulture, "({0:n0}/{1:n0})", objArray);
//            }
//          }
//          else
//          {
//            object[] objArray = new object[1]
//            {
//              (object) this.Var1
//            };
//            strArray[count] = string.Format((IFormatProvider) CultureInfo.CurrentCulture, "({0:n0}/??)", objArray);
//          }
//          ++count;
//        }
//      }
//      if (!relicInfoOnly && this.IsQuestItem)
//        strArray[count++] = Item.ItemQuest;
//      if (!basicInfoOnly && !relicInfoOnly)
//      {
//        if (this.IsImmortalThrone)
//          strArray[count++] = "(IT)";
//        if (this.IsRagnarok)
//          strArray[count++] = "(RAG)";
//      }
//      return string.Join(" ", strArray, 0, count);
//    }
//


      //
//    public static string ItemWith
//    {
//      get => Item.itemWith;
//      set => Item.itemWith = value;
//    }
//
//    public static string ItemRelicBonus
//    {
//      get => Item.itemRelicBonus;
//      set => Item.itemRelicBonus = value;
//    }
//
//    public static string ItemRelicCompleted
//    {
//      get => Item.itemRelicCompleted;
//      set => Item.itemRelicCompleted = value;
//    }
//
//    public static string ItemQuest
//    {
//      get => Item.itemQuest;
//      set => Item.itemQuest = value;
//    }
//
//    public static string ItemSeed
//    {
//      get => Item.itemSeed;
//      set => Item.itemSeed = value;
//    }
//
//    public static string ItemIT
//    {
//      get => Item.itemIT;
//      set => Item.itemIT = value;
//    }
//
//    public static string ItemRAG
//    {
//      get => Item.itemRAG;
//      set => Item.itemRAG = value;
//    }
//
//    public static bool ShowSkillLevel
//    {
//      get => Item.showSkillLevel;
//      set => Item.showSkillLevel = value;
//    }

//
//    public string RelicBonusId
//    {
//      get => this.relicBonusId;
//      set => this.relicBonusId = value;
//    }
//
//    public string RelicBonusId2
//    {
//      get => this.relicBonusId2;
//      set => this.relicBonusId2 = value;
//    }
//
//    public int Seed
//    {
//      get => this.seed;
//      set => this.seed = value;
//    }
//
//    public string PrefixId
//    {
//      get => this.prefixID;
//      set => this.prefixID = value;
//    }
//
//    public string SuffixId
//    {
//      get => this.suffixID;
//      set => this.suffixID = value;
//    }
//
//    public int Var1
//    {
//      get => this.var1;
//      set => this.var1 = value;
//    }
//
//    public int Var2
//    {
//      get => this.var2;
//      set => this.var2 = value;
//    }
//
//    public int StackSize
//    {
//      get => this.stackSize;
//      set => this.stackSize = value;
//    }

//    public Point Location
//    {
//      get => new Point(this.PositionX, this.PositionY);
//      set
//      {
//        this.PositionX = value.X;
//        this.PositionY = value.Y;
//      }
//    }
//
//    public Info RelicInfo { get; private set; }
//
//    public Info RelicBonusInfo { get; set; }
//
//    public Bitmap ItemBitmap { get; private set; }
//
//    public int Width { get; private set; }
//
//    public int Height { get; private set; }
//
//    public Size Size => new Size(this.Width, this.Height);
//
//    public int Right => this.Location.X + this.Width;
//
//    public int Bottom => this.Location.Y + this.Height;
//
//    public bool IsInWeaponSlot => this.PositionX == Item.WeaponSlotIndicator;
//
    public val IsImmortalThrone = this.checkSubstring("XPACK\\");
//
    public val IsRagnarok = this.checkSubstring("XPACK2\\");
//
    public val IsAnyXPack = this.IsImmortalThrone || this.IsRagnarok;

    val IsScroll:Boolean

      get()
      {
          val baseitem = baseItemInfo
        if (baseitem != null) {
            return baseitem.ItemClass.uppercase() == "ONESHOT_SCROLL";
        }
        return this.IsAnyXPack && this.BaseItemId.uppercase().indexOf("\\SCROLLS\\") >= 0
      }

//
//    public bool IsParchment => this.IsAnyXPack && this.BaseItemId.uppercase().IndexOf("\\PARCHMENTS\\", StringComparison.OrdinalIgnoreCase) >= 0;
//
//    public bool IsFormulae
//    {
//      get
//      {
//        if (this.baseItemInfo != null)
//          return this.baseItemInfo.ItemClass.uppercase().Equals("ITEMARTIFACTFORMULA");
//        return this.IsAnyXPack && this.BaseItemId.uppercase().IndexOf("\\ARCANEFORMULAE\\", StringComparison.OrdinalIgnoreCase) >= 0;
//      }
//    }
//
//    public bool IsArtifact
//    {
//      get
//      {
//        if (this.baseItemInfo != null)
//          return this.baseItemInfo.ItemClass.uppercase().Equals("ITEMARTIFACT");
//        return this.IsAnyXPack && !this.IsFormulae && this.BaseItemId.uppercase().IndexOf("\\ARTIFACTS\\", StringComparison.OrdinalIgnoreCase) >= 0;
//      }
//    }
//
//    public bool IsShield => this.baseItemInfo != null && this.baseItemInfo.ItemClass.uppercase().Equals("WEAPONARMOR_SHIELD");
//
//    public bool IsArmor => this.baseItemInfo != null && this.baseItemInfo.ItemClass.uppercase().StartsWith("ARMORPROTECTIVE", StringComparison.OrdinalIgnoreCase);
//
//    public bool IsHelm => this.baseItemInfo != null && this.baseItemInfo.ItemClass.uppercase().Equals("ARMORPROTECTIVE_HEAD");
//
//    public bool IsBracer => this.baseItemInfo != null && this.baseItemInfo.ItemClass.uppercase().Equals("ARMORPROTECTIVE_FOREARM");
//
//    public bool IsTorsoArmor => this.baseItemInfo != null && this.baseItemInfo.ItemClass.uppercase().Equals("ARMORPROTECTIVE_UPPERBODY");
//
//    public bool IsGreave => this.baseItemInfo != null && this.baseItemInfo.ItemClass.uppercase().Equals("ARMORPROTECTIVE_LOWERBODY");
//
//    public bool IsRing => this.baseItemInfo != null && this.baseItemInfo.ItemClass.uppercase().Equals("ARMORJEWELRY_RING");
//
//    public bool IsAmulet => this.baseItemInfo != null && this.baseItemInfo.ItemClass.uppercase().Equals("ARMORJEWELRY_AMULET");
//
//    public bool IsWeapon => this.baseItemInfo != null && !this.IsShield && this.baseItemInfo.ItemClass.uppercase().StartsWith("WEAPON", StringComparison.OrdinalIgnoreCase);
//
//    public bool Is2HWeapon
//    {
//      get
//      {
//        if (this.baseItemInfo == null)
//          return false;
//        return this.baseItemInfo.ItemClass.uppercase().Equals("WEAPONHUNTING_BOW") || this.baseItemInfo.ItemClass.uppercase().Equals("WEAPONMAGICAL_STAFF");
//      }
//    }
//
//    public bool IsWeaponShield => this.baseItemInfo != null && this.baseItemInfo.ItemClass.uppercase().StartsWith("WEAPON", StringComparison.OrdinalIgnoreCase);
//
//    public bool IsQuestItem
//    {
//      get
//      {
//        if (this.baseItemInfo != null)
//        {
//          if (this.baseItemInfo.ItemClassification.uppercase().Equals("QUEST"))
//            return true;
//        }
//        else if (!this.IsAnyXPack)
//        {
//          if (this.BaseItemId.uppercase().IndexOf("QUEST", StringComparison.OrdinalIgnoreCase) >= 0)
//            return true;
//        }
//        else if (this.BaseItemId.uppercase().IndexOf("QUESTS", StringComparison.OrdinalIgnoreCase) >= 0)
//          return true;
//        return false;
//      }
//    }
//
//    public ItemStyle ItemStyle
//    {
//      get
//      {
//        if (this.prefixInfo != null && this.prefixInfo.ItemClassification.uppercase().Equals("BROKEN"))
//          return ItemStyle.Broken;
//        if (this.IsArtifact)
//          return ItemStyle.Artifact;
//        if (this.IsFormulae)
//          return ItemStyle.Formulae;
//        if (this.IsScroll)
//          return ItemStyle.Scroll;
//        if (this.IsParchment)
//          return ItemStyle.Parchment;
//        if (this.IsRelic)
//          return ItemStyle.Relic;
//        if (this.IsPotion)
//          return ItemStyle.Potion;
//        if (this.IsQuestItem)
//          return ItemStyle.Quest;
//        if (this.baseItemInfo != null)
//        {
//          if (this.baseItemInfo.ItemClassification.uppercase().Equals("EPIC"))
//            return ItemStyle.Epic;
//          if (this.baseItemInfo.ItemClassification.uppercase().Equals("LEGENDARY"))
//            return ItemStyle.Legendary;
//          if (this.baseItemInfo.ItemClassification.uppercase().Equals("RARE"))
//            return ItemStyle.Rare;
//        }
//        if (this.suffixInfo != null && this.suffixInfo.ItemClassification.uppercase().Equals("RARE") || this.prefixInfo != null && this.prefixInfo.ItemClassification.uppercase().Equals("RARE"))
//          return ItemStyle.Rare;
//        return this.suffixInfo != null || this.prefixInfo != null ? ItemStyle.Common : ItemStyle.Mundane;
//      }
//    }
//
//    public string ItemClass => this.baseItemInfo?.ItemClass;
//
    public val DoesStack = this.IsPotion || this.IsScroll;
//
//    public bool HasNumber
//    {
//      get
//      {
//        if (this.DoesStack)
//          return true;
//        return this.IsRelic && !this.IsRelicComplete;
//      }
//    }
//
//    public int Number
//    {
//      get
//      {
//        if (this.DoesStack)
//          return this.StackSize;
//        return this.IsRelic ? Math.Max(this.Var1, 1) : 0;
//      }
//      set
//      {
//        if (this.DoesStack)
//        {
//          this.StackSize = value;
//        }
//        else
//        {
//          if (!this.IsRelic)
//            return;
//          this.Var1 = Math.Min(value, this.baseItemInfo.CompletedRelicLevel);
//        }
//      }
//    }
//
    public val HasRelic = !this.IsRelic && this.relicID.isNotEmpty()
//
    public val IsPotion:Boolean
    get() {
        val baseItem = baseItemInfo
        return if (baseItem == null) {
            BaseItemId.uppercase().indexOf("ONESHOT\\POTION") != -1
        } else {
            (baseItem.ItemClass.uppercase()
                    == "ONESHOT_POTIONHEALTH") || (baseItem.ItemClass.uppercase()
                == "ONESHOT_POTIONMANA")
        }

    }
//
//    public bool IsCharm
//    {
//      get
//      {
//        if (this.baseItemInfo != null)
//          return this.baseItemInfo.ItemClass.uppercase().Equals("ITEMCHARM");
//        return !this.IsImmortalThrone ? this.BaseItemId.uppercase().IndexOf("ANIMALRELICS", StringComparison.OrdinalIgnoreCase) != -1 : this.BaseItemId.uppercase().IndexOf("\\CHARMS\\", StringComparison.OrdinalIgnoreCase) != -1;
//      }
//    }
//
    public val IsRelic:Boolean
    get()
      {
          val baseItem = baseItemInfo
          return if (baseItem != null) {
              baseItem.ItemClass.uppercase() == ("ITEMRELIC") || baseItem.ItemClass.uppercase() == ("ITEMCHARM")
          }else
              if (!this.IsAnyXPack) {
                  this.BaseItemId.uppercase().indexOf("RELICS") != -1
              } else {
                  this.BaseItemId.uppercase().indexOf("\\RELICS\\") != -1 || this.BaseItemId.uppercase()
                      .indexOf("\\CHARMS\\") != -1
              }
      }


    public val IsRelicComplete:Boolean
        get()
      {
          val baseItem = baseItemInfo ?: return false
          return this.var1 >= baseItem.CompletedRelicLevel || baseItem.CompletedRelicLevel == 1;
      }


//    public void completeRelic(int level)
//    {
//      if (this.baseItemInfo != null)
//        this.Var1 = this.baseItemInfo.CompletedRelicLevel;
//      else
//        this.var1 = level;
//    }
//
//    public LootTable BonusTable
//    {
//      get
//      {
//        if (this.baseItemInfo != null)
//        {
//          string tableId = (string) null;
//          if (this.IsRelic)
//            tableId = this.baseItemInfo.GetString("bonusTableName");
//          else if (this.HasRelic)
//          {
//            if (this.RelicInfo != null)
//              tableId = this.RelicInfo.GetString("bonusTableName");
//          }
//          else if (this.IsArtifact)
//          {
//            string path1 = Path.Combine(Path.GetDirectoryName(this.BaseItemId), "arcaneformulae");
//            string str = Path.GetFileNameWithoutExtension(this.BaseItemId);
//            if (str.uppercase() == "E_GA_SANDOFKRONOS")
//              str = str.Insert(9, "s");
//            string path2 = str + "_formula";
//            string itemId = Path.ChangeExtension(Path.Combine(path1, path2), Path.GetExtension(this.BaseItemId));
//            DBRecord recordFromFile = Database.GetRecordFromFile(itemId);
//            if (recordFromFile != null)
//              tableId = recordFromFile.GetString("artifactBonusTableName", 0);
//          }
//          if (tableId != null && tableId.Length > 0)
//            return new LootTable(tableId);
//        }
//        return (LootTable) null;
//      }
//    }
//
//    public int ItemGroup
//    {
//      get
//      {
//        if (this.baseItemInfo == null)
//          return 0;
//        int itemGroup;
//        if (this.baseItemInfo.ItemClass.uppercase().Equals("ONESHOT_POTIONHEALTH"))
//        {
//          itemGroup = 0;
//        }
//        else
//        {
//          if (this.baseItemInfo.ItemClass.uppercase().Equals("ONESHOT_POTIONMANA"))
//            return 1;
//          if (this.baseItemInfo.ItemClass.uppercase().Equals("ARMORJEWELRY_AMULET"))
//            return 2;
//          if (this.baseItemInfo.ItemClass.uppercase().Equals("ARMORJEWELRY_RING"))
//            return 3;
//          if (this.baseItemInfo.ItemClass.uppercase().Equals("ITEMCHARM"))
//            return 4;
//          if (this.baseItemInfo.ItemClass.uppercase().Equals("ITEMRELIC"))
//            return 5;
//          if (this.baseItemInfo.ItemClass.uppercase().Equals("ITEMARTIFACTFORMULA"))
//            return 6;
//          if (this.baseItemInfo.ItemClass.uppercase().Equals("ITEMARTIFACT"))
//            return 7;
//          if (this.baseItemInfo.ItemClass.uppercase().Equals("ONESHOT_SCROLL"))
//            return 8;
//          if (this.baseItemInfo.ItemClass.uppercase().Equals("ARMORPROTECTIVE_LOWERBODY"))
//            return 9;
//          if (this.baseItemInfo.ItemClass.uppercase().Equals("ARMORPROTECTIVE_FOREARM"))
//            return 10;
//          if (this.baseItemInfo.ItemClass.uppercase().Equals("ARMORPROTECTIVE_HEAD"))
//            return 11;
//          if (this.baseItemInfo.ItemClass.uppercase().Equals("ARMORPROTECTIVE_UPPERBODY"))
//            return 12;
//          if (this.baseItemInfo.ItemClass.uppercase().Equals("WEAPONARMOR_SHIELD"))
//            return 13;
//          if (this.baseItemInfo.ItemClass.uppercase().Equals("WEAPONMELEE_AXE"))
//            return 14;
//          if (this.baseItemInfo.ItemClass.uppercase().Equals("WEAPONMELEE_MACE"))
//            return 15;
//          if (this.baseItemInfo.ItemClass.uppercase().Equals("WEAPONMELEE_SWORD"))
//            return 16;
//          if (this.baseItemInfo.ItemClass.uppercase().Equals("WEAPONHUNTING_BOW"))
//            return 17;
//          if (this.baseItemInfo.ItemClass.uppercase().Equals("WEAPONMAGICAL_STAFF"))
//            return 18;
//          if (this.baseItemInfo.ItemClass.uppercase().Equals("WEAPONHUNTING_SPEAR"))
//            return 19;
//          itemGroup = !this.baseItemInfo.ItemClass.uppercase().Equals("WEAPONHUNTING_RANGEDONEHAND") ? 0 : 20;
//        }
//        return itemGroup;
//      }
//    }
      companion object {
          const val WeaponSlotIndicator = -3
    public fun ClipColorTag(text:String):String
    {
      if (text.startsWith("^"))
      {
        return text.substring(2)
      }
      if (text.contains("{^"))
      {
        val length = text.indexOf("{^")
        if (length != -1)
        {
          val num = text.indexOf('}', length + 1);
          return text.substring(0, length) + text.substring(num + 1);
        }
      }
      return text;
    }

    private fun CheckExtension(itemId:String?):String
    {
        return if (itemId == null) {
            ""
        }else
            if(itemId.length < 4 || File(itemId).toPath().extension.uppercase() ==".DBR")  {itemId} else {
                "$itemId.dbr"
            }
    }
}
}

