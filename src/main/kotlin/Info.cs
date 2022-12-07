// Decompiled with JetBrains decompiler
// Type: TQ_weaponsmith.Info
// Assembly: TQ_Blacksmith, Version=0.1.4.1, Culture=neutral, PublicKeyToken=null
// MVID: 1CC9040D-95C6-4C54-B248-9D80684B2CA8
// Assembly location: D:\Games\GOG Galaxy\Games\Titan Quest - Anniversary Edition\Downloads\Blacksmith\TQ_Blacksmith.exe

using System;
using System.IO;

namespace TQ_weaponsmith
{
  public class Info
  {
    private DBRecord record;
    private string descriptionVar;
    private string itemClassificationVar;
    private string bitmapVar;
    private string shardBitmapVar;
    private string itemClassVar;
    private string completedRelicLevelVar;
    private string qualityVar;
    private string styleVar;
    private string itemScalePercent;

    public Info(DBRecord record)
    {
      this.record = record;
      this.AssignVariableNames();
    }

    private void AssignVariableNames()
    {
      string upperInvariant = this.record.RecordType.ToUpperInvariant();
      if (upperInvariant.StartsWith("LOOTRANDOMIZER", StringComparison.OrdinalIgnoreCase))
      {
        this.descriptionVar = "lootRandomizerName";
        this.itemClassificationVar = "itemClassification";
        this.bitmapVar = string.Empty;
        this.shardBitmapVar = string.Empty;
        this.itemClassVar = string.Empty;
        this.completedRelicLevelVar = string.Empty;
        this.qualityVar = string.Empty;
        this.styleVar = string.Empty;
      }
      else if (upperInvariant.StartsWith("ITEMRELIC", StringComparison.OrdinalIgnoreCase) || upperInvariant.StartsWith("ITEMCHARM", StringComparison.OrdinalIgnoreCase))
      {
        this.descriptionVar = "description";
        this.itemClassificationVar = "itemClassification";
        this.bitmapVar = "relicBitmap";
        this.shardBitmapVar = "shardBitmap";
        this.itemClassVar = "Class";
        this.completedRelicLevelVar = "completedRelicLevel";
        this.qualityVar = string.Empty;
        this.styleVar = "itemText";
      }
      else if (upperInvariant.StartsWith("ONESHOT_DYE", StringComparison.OrdinalIgnoreCase))
      {
        this.descriptionVar = "description";
        this.itemClassificationVar = string.Empty;
        this.bitmapVar = "bitmap";
        this.shardBitmapVar = string.Empty;
        this.itemClassVar = "Class";
        this.completedRelicLevelVar = string.Empty;
        this.qualityVar = string.Empty;
        this.styleVar = string.Empty;
      }
      else if (upperInvariant.StartsWith("ONESHOT", StringComparison.OrdinalIgnoreCase) || upperInvariant.StartsWith("QUESTITEM", StringComparison.OrdinalIgnoreCase))
      {
        this.descriptionVar = "description";
        this.itemClassificationVar = "itemClassification";
        this.bitmapVar = "bitmap";
        this.shardBitmapVar = string.Empty;
        this.itemClassVar = "Class";
        this.completedRelicLevelVar = string.Empty;
        this.qualityVar = string.Empty;
        this.styleVar = "itemText";
      }
      else if (upperInvariant.StartsWith("ITEMARTIFACTFORMULA", StringComparison.OrdinalIgnoreCase))
      {
        this.descriptionVar = "description";
        this.itemClassificationVar = "itemClassification";
        this.bitmapVar = "artifactFormulaBitmapName";
        this.shardBitmapVar = string.Empty;
        this.itemClassVar = "Class";
        this.completedRelicLevelVar = string.Empty;
        this.qualityVar = string.Empty;
        this.styleVar = string.Empty;
      }
      else if (upperInvariant.StartsWith("ITEMARTIFACT", StringComparison.OrdinalIgnoreCase))
      {
        this.descriptionVar = "description";
        this.itemClassificationVar = "itemClassification";
        this.bitmapVar = "artifactBitmap";
        this.shardBitmapVar = string.Empty;
        this.itemClassVar = "Class";
        this.completedRelicLevelVar = string.Empty;
        this.qualityVar = string.Empty;
        this.styleVar = string.Empty;
      }
      else if (upperInvariant.StartsWith("ITEMEQUIPMENT", StringComparison.OrdinalIgnoreCase))
      {
        this.descriptionVar = "description";
        this.itemClassificationVar = "itemClassification";
        this.bitmapVar = "bitmap";
        this.shardBitmapVar = string.Empty;
        this.itemClassVar = "Class";
        this.completedRelicLevelVar = string.Empty;
        this.qualityVar = string.Empty;
        this.styleVar = "itemText";
      }
      else
      {
        this.descriptionVar = "itemNameTag";
        this.itemClassificationVar = "itemClassification";
        this.bitmapVar = "bitmap";
        this.shardBitmapVar = string.Empty;
        this.itemClassVar = "Class";
        this.completedRelicLevelVar = string.Empty;
        this.qualityVar = "itemQualityTag";
        this.styleVar = "itemStyleTag";
      }
      this.itemScalePercent = "itemScalePercent";
    }

    public string friendlyName() => Database.DB.GetFriendlyName(this.DescriptionTag);

    public string cleanName()
    {
      string text = this.friendlyName();
      if (string.IsNullOrEmpty(text))
        text = Path.GetFileNameWithoutExtension(this.ItemId);
      string str = Item.ClipColorTag(text);
      string s1 = this.GetString("itemLevel");
      string s2 = this.GetString("levelRequirement");
      int num1 = 0;
      int num2 = 0;
      if (!string.IsNullOrEmpty(s1))
      {
        try
        {
          num1 = int.Parse(s1);
        }
        catch (Exception ex)
        {
          num1 = 0;
        }
      }
      if (!string.IsNullOrEmpty(s2))
      {
        try
        {
          num2 = int.Parse(s2);
        }
        catch (Exception ex)
        {
          num2 = 0;
        }
      }
      if (num1 > num2)
        str = str + " (" + (object) num1 + ")";
      else if (num1 < num2)
        str = str + " (" + (object) num2 + ")";
      return str;
    }

    public int GetInt32(string variable) => this.record.GetInt32(variable, 0);

    public float GetSingle(string variable) => this.record.GetSingle(variable, 0);

    public string GetString(string variable) => this.record.GetString(variable, 0);

    public string ItemId => this.record.Id;

    public string RecordType => this.record.RecordType;

    public string DescriptionTag => this.GetString(this.descriptionVar);

    public string ItemClassification => this.GetString(this.itemClassificationVar);

    public string QualityTag => this.GetString(this.qualityVar);

    public string StyleTag => this.GetString(this.styleVar);

    public string Bitmap => this.GetString(this.bitmapVar);

    public string ShardBitmap => this.GetString(this.shardBitmapVar);

    public string ItemClass => this.GetString(this.itemClassVar);

    public string ItemLevel => this.GetString("itemLevel");

    public int CompletedRelicLevel => this.GetInt32(this.completedRelicLevelVar);

    public float ItemScalePercent => (float) (1.0 + (double) this.GetSingle(this.itemScalePercent) / 100.0);
  }
}
