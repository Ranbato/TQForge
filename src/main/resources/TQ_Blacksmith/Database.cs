// Decompiled with JetBrains decompiler
// Type: TQ_weaponsmith.Database
// Assembly: TQ_Blacksmith, Version=0.1.4.1, Culture=neutral, PublicKeyToken=null
// MVID: 1CC9040D-95C6-4C54-B248-9D80684B2CA8
// Assembly location: D:\Games\GOG Galaxy\Games\Titan Quest - Anniversary Edition\Downloads\Blacksmith\TQ_Blacksmith.exe

using System;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.Drawing;
using System.Drawing.Imaging;
using System.Globalization;
using System.IO;
using System.Text;
using System.Text.RegularExpressions;

namespace TQ_weaponsmith
{
  public class Database
  {
    private const float ITEMUNITSIZE = 32f;
    private Dictionary<string, Info> infoDB;
    private Dictionary<string, string> textDB;
    private Dictionary<string, ArcFile> arcFiles = new Dictionary<string, ArcFile>();
    private Dictionary<string, Bitmap> bitmaps = new Dictionary<string, Bitmap>();
    private Dictionary<string, Dictionary<string, Info>> prefixesTable = new Dictionary<string, Dictionary<string, Info>>();
    private Dictionary<string, Dictionary<string, Info>> suffixesTable = new Dictionary<string, Dictionary<string, Info>>();
    private Bitmap defaultBitmap;
    private string gameLanguage;
    private float scale = 1f;
    private List<Info> miscList = new List<Info>();
    public static Database DB;
    public bool AutoDetectLanguage;
    public string TQLanguage;
    public ArzFile ArzFile;
    public ArzFile ArzFileIT;
    public ArzFile ArzFileAE;
    public ArzFile ArzFileMod;

    public void dumpPrefixAndSuffixData(string file)
    {
      Dictionary<string, Dictionary<string, int>> dictionary1 = new Dictionary<string, Dictionary<string, int>>();
      using (StreamReader streamReader = new StreamReader(file))
      {
        string str1;
        while ((str1 = streamReader.ReadLine()) != null)
        {
          if (!string.IsNullOrWhiteSpace(str1) && !str1.StartsWith("#") && str1.IndexOf("=", StringComparison.Ordinal) != -1)
          {
            string[] strArray = str1.Split('=');
            string str2 = strArray[0];
            string str3 = strArray[1];
            string str4 = Regex.Replace(Path.GetFileNameWithoutExtension(str3), "_.+", "");
            if (str4.EndsWith("B") || str4.EndsWith("b") || str4.EndsWith("A") || str4.EndsWith("a"))
              str4 = str4.Substring(0, str4.Length - 1);
            string key1 = str4.Replace("MAGE", "").Replace("MELEE", "").Replace("LIGHTNING", "").Replace("COLD", "").Replace("FIRE", "");
            Dictionary<string, int> dictionary2;
            if (!dictionary1.ContainsKey(key1))
            {
              dictionary2 = new Dictionary<string, int>();
              dictionary1.Add(key1, dictionary2);
            }
            else
              dictionary2 = dictionary1[key1];
            foreach (KeyValuePair<string, float> keyValuePair in new LootTable(str3))
            {
              string key2 = keyValuePair.Key + key1;
              if (dictionary2.ContainsKey(key2))
                ++dictionary2[key2];
              else
                dictionary2.Add(key2, 1);
            }
          }
        }
        foreach (KeyValuePair<string, Dictionary<string, int>> keyValuePair1 in dictionary1)
        {
          string key = keyValuePair1.Key;
          foreach (KeyValuePair<string, int> keyValuePair2 in keyValuePair1.Value)
          {
            Info info = this.GetInfo(keyValuePair2.Key);
            string itemClassification = info.ItemClassification;
            string descriptionTag = info.DescriptionTag;
            string friendlyName = this.GetFriendlyName(descriptionTag);
            Logger.Log(key + "=" + itemClassification + "=" + (object) keyValuePair2.Value + "=" + keyValuePair2.Key + "=" + descriptionTag + "=" + friendlyName);
          }
        }
      }
    }

    public void dumpAllItemRecords()
    {
    }

    public void dumpAllTex()
    {
      List<string> stringList = new List<string>();
      string path1 = "E:\\bitmaplist.txt";
      if (!File.Exists(path1))
        return;
      Logger.Debug("Reading items from " + path1);
      using (StreamReader streamReader = new StreamReader(path1))
      {
        string str1;
        while ((str1 = streamReader.ReadLine()) != null)
        {
          if (!string.IsNullOrWhiteSpace(str1) && !str1.StartsWith("#"))
          {
            string str2 = str1.Trim();
            if (!stringList.Contains(str2))
              stringList.Add(str2);
          }
        }
      }
      string path2 = "E:\\arcitems.txt";
      if (!File.Exists(path2))
        return;
      Logger.Debug("Reading items from " + path2);
      using (StreamReader streamReader = new StreamReader(path2))
      {
        string str3;
        while ((str3 = streamReader.ReadLine()) != null)
        {
          if (!string.IsNullOrWhiteSpace(str3) && !str3.StartsWith("#"))
          {
            string str4 = str3.Trim();
            Bitmap bitmap = this.LoadBitmap(str4);
            if (bitmap != null)
            {
              string str5 = "e:\\uniqueBmapImages\\" + Path.GetFileNameWithoutExtension(str4) + ".png";
              if (!File.Exists(str5))
                bitmap.Save(str5, ImageFormat.Png);
            }
            else
              Logger.Log("bmap null =" + str4);
          }
        }
      }
    }

    public void LoadAllItems()
    {
      string[] itemListFiles = Config.getConfig().itemListFiles;
      int num1 = 0;
      int num2 = 0;
      for (int index = 0; index < itemListFiles.Length; ++index)
      {
        string str1 = itemListFiles[index];
        if (File.Exists(".\\Data\\items\\" + str1))
        {
          using (StreamReader streamReader = new StreamReader(".\\Data\\items\\" + str1))
          {
            string str2;
            while ((str2 = streamReader.ReadLine()) != null)
            {
              if (!string.IsNullOrWhiteSpace(str2) && !str2.StartsWith("#") && str2.IndexOf("=", StringComparison.Ordinal) != -1)
              {
                string[] strArray = str2.Split('=');
                string str3 = strArray[0];
                string itemId = strArray[1];
                ++num2;
                Info info = this.GetInfo(itemId);
                if (info != null)
                {
                  this.LoadBitmap(info.Bitmap);
                  if (info.ItemId != null)
                  {
                    string recordType = info.RecordType;
                    string itemClassification = info.ItemClassification;
                    this.GetFriendlyName(info.DescriptionTag);
                  }
                }
              }
            }
          }
          Logger.Log("Found " + (object) num2 + " items in " + str1);
          num1 += num2;
          num2 = 0;
        }
      }
      Logger.Log("Loaded " + (object) num1 + " records.");
      string str4 = "ItemwisePrefix.txt";
      if (File.Exists(".\\Data\\items\\" + str4))
      {
        int num3 = 0;
        using (StreamReader streamReader = new StreamReader(".\\Data\\items\\" + str4))
        {
          string str5;
          while ((str5 = streamReader.ReadLine()) != null)
          {
            if (!string.IsNullOrWhiteSpace(str5) && !str5.StartsWith("#") && str5.IndexOf("=", StringComparison.Ordinal) != -1)
            {
              string[] strArray = str5.Split('=');
              string stringValue = TQData.getItemType(strArray[0]).GetStringValue();
              if (string.IsNullOrEmpty(stringValue))
                Logger.Log("Error: " + strArray[0]);
              string str6 = strArray[1];
              string str7 = strArray[2];
              string itemId1 = strArray[3];
              string str8 = strArray[3];
              ++num3;
              Info info = this.GetInfo(itemId1);
              if (info != null)
              {
                string itemId2 = info.ItemId;
                if (itemId2 != null)
                {
                  Dictionary<string, Info> dictionary;
                  if (!this.prefixesTable.ContainsKey(stringValue))
                  {
                    dictionary = new Dictionary<string, Info>();
                    this.prefixesTable.Add(stringValue, dictionary);
                  }
                  else
                    dictionary = this.prefixesTable[stringValue];
                  if (!dictionary.ContainsKey(itemId2))
                    dictionary.Add(itemId2, info);
                }
              }
            }
          }
        }
        Logger.Log("Found " + (object) num3 + " prefixes in " + str4);
      }
      string str9 = "ItemwiseSuffix.txt";
      if (!File.Exists(".\\Data\\items\\" + str9))
        return;
      int num4 = 0;
      using (StreamReader streamReader = new StreamReader(".\\Data\\items\\" + str9))
      {
        string str10;
        while ((str10 = streamReader.ReadLine()) != null)
        {
          if (!string.IsNullOrWhiteSpace(str10) && !str10.StartsWith("#") && str10.IndexOf("=", StringComparison.Ordinal) != -1)
          {
            string[] strArray = str10.Split('=');
            string stringValue = TQData.getItemType(strArray[0]).GetStringValue();
            string str11 = strArray[1];
            string str12 = strArray[2];
            string itemId3 = strArray[3];
            string str13 = strArray[3];
            ++num4;
            Info info = this.GetInfo(itemId3);
            if (info != null)
            {
              string itemId4 = info.ItemId;
              if (itemId4 != null)
              {
                Dictionary<string, Info> dictionary;
                if (!this.suffixesTable.ContainsKey(stringValue))
                {
                  dictionary = new Dictionary<string, Info>();
                  this.suffixesTable.Add(stringValue, dictionary);
                }
                else
                  dictionary = this.suffixesTable[stringValue];
                if (!dictionary.ContainsKey(itemId4))
                  dictionary.Add(itemId4, info);
              }
            }
          }
        }
      }
      Logger.Log("Found " + (object) num4 + " suffixes in " + str9);
      int num5 = 0;
      string str14 = "misc.txt";
      if (File.Exists(".\\Data\\items\\" + str14))
      {
        using (StreamReader streamReader = new StreamReader(".\\Data\\items\\" + str14))
        {
          string str15;
          while ((str15 = streamReader.ReadLine()) != null)
          {
            if (!string.IsNullOrWhiteSpace(str15) && !str15.StartsWith("#") && str15.IndexOf("=", StringComparison.Ordinal) != -1)
            {
              string[] strArray = str15.Split('=');
              string str16 = strArray[0];
              string itemId = strArray[1];
              ++num5;
              Info info = this.GetInfo(itemId);
              if (info != null)
              {
                this.LoadBitmap(info.Bitmap);
                this.miscList.Add(info);
                if (info.ItemId != null)
                {
                  string recordType = info.RecordType;
                  string itemClassification = info.ItemClassification;
                  this.GetFriendlyName(info.DescriptionTag);
                }
              }
            }
          }
        }
      }
      Logger.Log("Found " + (object) num5 + " items in " + str14);
    }

    public void dumpAllItems()
    {
      DBClasses.getClasses();
      string[] keyTable = this.ArzFileAE.GetKeyTable();
      using (StreamWriter streamWriter = new StreamWriter("E:\\temp\\ConcirneditemList.txt"))
      {
        for (int index = 0; index < keyTable.Length; ++index)
        {
          string itemId = keyTable[index];
          if (itemId != null)
          {
            Info info = this.GetInfo(itemId);
            if (info != null)
            {
              string recordType = info.RecordType;
              string itemClassification = info.ItemClassification;
              string descriptionTag = info.DescriptionTag;
              string bitmap1 = info.Bitmap;
              Bitmap bitmap2 = this.LoadBitmap(bitmap1);
              string friendlyName = this.GetFriendlyName(descriptionTag);
              if (bitmap2 != null && (string.IsNullOrEmpty(friendlyName) || string.IsNullOrEmpty(itemClassification)))
              {
                string str = "e:\\temp\\bmaps\\" + recordType + "_" + Path.GetFileNameWithoutExtension(bitmap1) + ".png";
                if (!File.Exists(str))
                  bitmap2.Save(str, ImageFormat.Png);
                streamWriter.WriteLine(recordType + "=" + itemClassification + "=" + descriptionTag + "=" + friendlyName + "=" + itemId + "=" + bitmap1);
              }
            }
          }
        }
        Logger.Log("done dumping");
      }
    }

    public List<Info> getMisc() => this.miscList;

    public List<Info> getInfos(ItemType type, ItemClass classification)
    {
      List<Info> infos = new List<Info>();
      if (type == ItemType.Misc)
        return this.miscList;
      foreach (KeyValuePair<string, Info> keyValuePair in this.infoDB)
      {
        if (this.isMatch(keyValuePair.Value, type, classification))
          infos.Add(keyValuePair.Value);
      }
      if (infos.Count <= 0)
        Logger.Log("Warning: No records found for type=" + type.GetStringValue() + " and class=" + classification.GetStringValue());
      return infos;
    }

    private bool isMatch(Info inf, ItemType type, ItemClass classification = ItemClass.None)
    {
      string stringValue = type.GetStringValue();
      if (inf.RecordType.Equals(stringValue))
      {
        if (TQData.isWeaponOrArmor(type) && classification != ItemClass.None)
          return classification.GetStringValue() == inf.ItemClassification || classification == ItemClass.Legendary && (ItemClass.Quest.GetStringValue() == inf.ItemClassification || ItemClass.Quest.GetStringValue() == inf.ItemClassification);
        if (TQData.isWeaponOrArmor(type) && classification == ItemClass.None)
          return true;
        if ((ItemType.Ring == type || ItemType.Amulet == type) && classification != ItemClass.None)
          return classification.GetStringValue() == inf.ItemClassification;
        if ((ItemType.Ring == type || ItemType.Amulet == type) && classification == ItemClass.None)
          return true;
        if (ItemType.Charm == type && classification != ItemClass.None)
        {
          string withoutExtension = Path.GetFileNameWithoutExtension(inf.ItemId);
          return ItemClass.Normal == classification && withoutExtension.StartsWith("01") || ItemClass.Epic == classification && withoutExtension.StartsWith("02") || ItemClass.Legendary == classification && withoutExtension.StartsWith("03");
        }
        if (ItemType.Charm == type && classification == ItemClass.None)
          return true;
        if (ItemType.Relic == type && classification != ItemClass.None)
        {
          string withoutExtension = Path.GetFileNameWithoutExtension(inf.ItemId);
          return ItemClass.Normal == classification && withoutExtension.StartsWith("01") || ItemClass.Epic == classification && withoutExtension.StartsWith("02") || ItemClass.Legendary == classification && withoutExtension.StartsWith("03");
        }
        if (ItemType.Relic == type && classification == ItemClass.None)
          return true;
        if (ItemType.Scroll == type && classification != ItemClass.None)
        {
          string withoutExtension = Path.GetFileNameWithoutExtension(inf.ItemId);
          return ItemClass.Normal == classification && withoutExtension.StartsWith("01") || ItemClass.Epic == classification && withoutExtension.StartsWith("02") || ItemClass.Legendary == classification && withoutExtension.StartsWith("03");
        }
        if (ItemType.Scroll == type && classification == ItemClass.None)
          return true;
        if (ItemType.Formula == type && classification != ItemClass.None)
        {
          string itemId = inf.GetString("artifactName");
          DBRecord recordFromFile = Database.DB.GetRecordFromFile(itemId);
          if (string.IsNullOrEmpty(itemId))
            return false;
          string upperInvariant = recordFromFile.GetString("artifactClassification", 0).ToUpperInvariant();
          return upperInvariant != null && string.Equals(upperInvariant, classification.GetStringValue(), StringComparison.OrdinalIgnoreCase);
        }
        if (ItemType.Formula == type && classification == ItemClass.None)
          return true;
        if (ItemType.Artifact == type && classification != ItemClass.None)
        {
          string upperInvariant = inf.GetString("artifactClassification").ToUpperInvariant();
          return upperInvariant != null && string.Equals(upperInvariant, classification.GetStringValue(), StringComparison.OrdinalIgnoreCase);
        }
        return ItemType.Artifact == type && classification == ItemClass.None || ItemType.Quest == type || ItemType.Parchment == type || classification != ItemClass.None && inf.ItemClassification.Equals((object) classification);
      }
      return ItemType.Quest == type && inf.ItemClassification.Equals("Quest");
    }

    private void CreateDefaultBitmap()
    {
      if (TQDebug.DatabaseDebugLevel > 0)
        TQDebug.DebugWriteLine("Database.CreateDefaultBitmap()");
      Bitmap bitmap = new Bitmap(this.ItemUnitSize, this.ItemUnitSize);
      Graphics graphics = Graphics.FromImage((Image) bitmap);
      graphics.FillRectangle((Brush) new SolidBrush(Color.Black), 0, 0, this.ItemUnitSize, this.ItemUnitSize);
      SolidBrush solidBrush1 = new SolidBrush(Color.Orange);
      float num = 5f * this.Scale;
      graphics.FillRectangle((Brush) solidBrush1, Convert.ToInt32(num), Convert.ToInt32(num), this.ItemUnitSize - Convert.ToInt32(2f * num), this.ItemUnitSize - Convert.ToInt32(2f * num));
      SolidBrush solidBrush2 = new SolidBrush(Color.FromArgb(1, 1, 1));
      Font font = new Font("Arial", (float) (this.ItemUnitSize - Convert.ToInt32(4f * num)), GraphicsUnit.Pixel);
      StringFormat format = new StringFormat()
      {
        Alignment = StringAlignment.Center
      };
      graphics.DrawString("?", font, (Brush) solidBrush2, new RectangleF(num, num, (float) this.ItemUnitSize - 2f * num, (float) this.ItemUnitSize - 2f * num), format);
      bitmap.MakeTransparent(Color.Black);
      this.defaultBitmap = bitmap;
      if (TQDebug.DatabaseDebugLevel <= 0)
        return;
      TQDebug.DebugWriteLine("Exiting Database.CreateDefaultBitmap()");
    }

    public static bool ExtractArcFile(string arcFileName, string destination)
    {
      if (TQDebug.DatabaseDebugLevel > 0)
        TQDebug.DebugWriteLine(string.Format((IFormatProvider) CultureInfo.InvariantCulture, "Database.ExtractARCFile('{0}', '{1}')", new object[2]
        {
          (object) arcFileName,
          (object) destination
        }));
      bool arcFile;
      try
      {
        arcFile = new ArcFile(arcFileName).ExtractArcFile(destination);
      }
      catch (IOException ex)
      {
        TQDebug.DebugWriteLine("Exception occurred");
        TQDebug.DebugWriteLine(ex.ToString());
        arcFile = false;
      }
      if (TQDebug.DatabaseDebugLevel > 1)
        TQDebug.DebugWriteLine(string.Format((IFormatProvider) CultureInfo.InvariantCulture, "Extraction Result = {0}", new object[1]
        {
          (object) arcFile
        }));
      if (TQDebug.DatabaseDebugLevel > 0)
        TQDebug.DebugWriteLine("Exiting Database.ReadARCFile()");
      return arcFile;
    }

    private string FigureDBFileToUse(string rootFolder)
    {
      if (TQDebug.DatabaseDebugLevel > 0)
        TQDebug.DebugWriteLine(string.Format((IFormatProvider) CultureInfo.InvariantCulture, "Database.FigureDBFileToUse({0})", new object[1]
        {
          (object) rootFolder
        }));
      if (TQDebug.DatabaseDebugLevel > 1)
      {
        TQDebug.DebugWriteLine("Detecting Titan Quest text files");
        TQDebug.DebugWriteLine(string.Format((IFormatProvider) CultureInfo.InvariantCulture, "rootFolder = {0}", new object[1]
        {
          (object) rootFolder
        }));
      }
      if (!Directory.Exists(rootFolder))
      {
        if (TQDebug.DatabaseDebugLevel > 0)
          TQDebug.DebugWriteLine("Error - Root Folder does not exist");
        return (string) null;
      }
      string str1 = Path.Combine(rootFolder, "Text_");
      string str2 = ".arc";
      string str3 = (string) null;
      string gameLanguage = this.GameLanguage;
      if (TQDebug.DatabaseDebugLevel > 1)
      {
        TQDebug.DebugWriteLine(string.Format((IFormatProvider) CultureInfo.InvariantCulture, "gameLanguage = {0}", new object[1]
        {
          gameLanguage == null ? (object) "NULL" : (object) gameLanguage
        }));
        TQDebug.DebugWriteLine(string.Format((IFormatProvider) CultureInfo.InvariantCulture, "baseFile = {0}", new object[1]
        {
          (object) str1
        }));
      }
      if (gameLanguage != null)
      {
        if (TQDebug.DatabaseDebugLevel > 2)
          TQDebug.DebugWriteLine("Try looking up cultureID");
        foreach (CultureInfo culture in CultureInfo.GetCultures(CultureTypes.NeutralCultures))
        {
          if (TQDebug.DatabaseDebugLevel > 2)
            TQDebug.DebugWriteLine(string.Format((IFormatProvider) CultureInfo.InvariantCulture, "Trying {0}", new object[1]
            {
              (object) culture.EnglishName.ToUpperInvariant()
            }));
          if (culture.EnglishName.ToUpperInvariant().Equals(gameLanguage.ToUpperInvariant()) || culture.DisplayName.ToUpperInvariant().Equals(gameLanguage.ToUpperInvariant()))
          {
            str3 = culture.TwoLetterISOLanguageName;
            break;
          }
        }
        if (str3 != null && str3.ToUpperInvariant() == "CS")
          str3 = "CZ";
        if (TQDebug.DatabaseDebugLevel > 1)
          TQDebug.DebugWriteLine(string.Format((IFormatProvider) CultureInfo.InvariantCulture, "cultureID = {0}", new object[1]
          {
            (object) str3
          }));
        if (str3 != null)
        {
          string path = str1 + str3 + str2;
          if (TQDebug.DatabaseDebugLevel > 1)
          {
            TQDebug.DebugWriteLine("Detected cultureID from gameLanguage");
            TQDebug.DebugWriteLine(string.Format((IFormatProvider) CultureInfo.InvariantCulture, "filename = {0}", new object[1]
            {
              (object) path
            }));
          }
          if (File.Exists(path))
          {
            this.WriteDebugLine_ExitFigureDBFile();
            return path;
          }
        }
      }
      string letterIsoLanguageName = CultureInfo.CurrentUICulture.TwoLetterISOLanguageName;
      if (TQDebug.DatabaseDebugLevel > 1)
      {
        TQDebug.DebugWriteLine("Using cultureID from OS");
        TQDebug.DebugWriteLine(string.Format((IFormatProvider) CultureInfo.InvariantCulture, "cultureID = {0}", new object[1]
        {
          (object) letterIsoLanguageName
        }));
      }
      if (letterIsoLanguageName != null)
      {
        string path = str1 + letterIsoLanguageName + str2;
        if (TQDebug.DatabaseDebugLevel > 1)
          TQDebug.DebugWriteLine(string.Format((IFormatProvider) CultureInfo.InvariantCulture, "filename = {0}", new object[1]
          {
            (object) path
          }));
        if (File.Exists(path))
        {
          this.WriteDebugLine_ExitFigureDBFile();
          return path;
        }
      }
      string str4 = "EN";
      string path1 = str1 + str4 + str2;
      if (TQDebug.DatabaseDebugLevel > 1)
      {
        TQDebug.DebugWriteLine("Forcing English Language");
        TQDebug.DebugWriteLine(string.Format((IFormatProvider) CultureInfo.InvariantCulture, "cultureID = {0}", new object[1]
        {
          (object) str4
        }));
        TQDebug.DebugWriteLine(string.Format((IFormatProvider) CultureInfo.InvariantCulture, "filename = {0}", new object[1]
        {
          (object) path1
        }));
      }
      if (File.Exists(path1))
      {
        this.WriteDebugLine_ExitFigureDBFile();
        return path1;
      }
      if (TQDebug.DatabaseDebugLevel > 0)
        TQDebug.DebugWriteLine("Detection Failed - searching for files");
      string[] files = Directory.GetFiles(rootFolder, "Text_??.arc");
      if (files != null && files.Length != 0)
      {
        if (TQDebug.DatabaseDebugLevel > 1)
        {
          TQDebug.DebugWriteLine("Found some files");
          TQDebug.DebugWriteLine(string.Format((IFormatProvider) CultureInfo.InvariantCulture, "filename = {0}", new object[1]
          {
            (object) files[0]
          }));
        }
        this.WriteDebugLine_ExitFigureDBFile();
        return files[0];
      }
      if (TQDebug.DatabaseDebugLevel > 0)
      {
        TQDebug.DebugWriteLine("Failed to determine Language file!");
        TQDebug.DebugWriteLine("Exiting Database.FigureDBFileToUse()");
      }
      return (string) null;
    }

    public string GetFriendlyName(string tagId)
    {
      try
      {
        return this.textDB[tagId.ToUpperInvariant()];
      }
      catch (KeyNotFoundException ex)
      {
        return (string) null;
      }
    }

    private static int getIndexOfNewLineTag(string text, int startIndex)
    {
      int indexOfNewLineTag = text.IndexOf("{^N}", startIndex, StringComparison.Ordinal);
      if (indexOfNewLineTag < 0)
        indexOfNewLineTag = text.IndexOf("{^n}", startIndex, StringComparison.Ordinal);
      return indexOfNewLineTag;
    }

    public Info GetInfo(string itemId)
    {
      Info info1 = (Info) null;
      if (string.IsNullOrEmpty(itemId))
        return info1;
      itemId = TQData.NormalizeRecordPath(itemId);
      Info info2;
      try
      {
        info2 = this.infoDB[itemId];
      }
      catch (KeyNotFoundException ex)
      {
        DBRecord recordFromFile = this.GetRecordFromFile(itemId);
        if (recordFromFile == null)
          return (Info) null;
        info2 = new Info(recordFromFile);
        this.infoDB.Add(itemId, info2);
      }
      return info2;
    }

    public string GetItemAttributeFriendlyText(string itemAttribute, bool addVariable = true)
    {
      ItemAttributesData attributeData = ItemAttributes.GetAttributeData(itemAttribute);
      if (attributeData == null)
        return "?" + itemAttribute + "?";
      string attributeTextTag = ItemAttributes.GetAttributeTextTag(attributeData);
      if (string.IsNullOrEmpty(attributeTextTag))
        return "?" + itemAttribute + "?";
      string attributeFriendlyText = this.GetFriendlyName(attributeTextTag);
      if (string.IsNullOrEmpty(attributeFriendlyText))
        attributeFriendlyText = "ATTR<" + itemAttribute + "> TAG<" + attributeTextTag + ">";
      if (addVariable && attributeData.Variable.Length > 0)
        attributeFriendlyText = attributeFriendlyText + " " + attributeData.Variable;
      return attributeFriendlyText;
    }

    public DBRecord GetRecordFromFile(string itemId)
    {
      itemId = TQData.NormalizeRecordPath(itemId);
      DBRecord recordFromFile = (DBRecord) null;
      if (this.ArzFileMod != null)
        recordFromFile = this.ArzFileMod.GetItem(itemId);
      if (recordFromFile == null && this.ArzFileAE != null)
        recordFromFile = this.ArzFileAE.GetItem(itemId);
      if (recordFromFile == null && this.ArzFileIT != null)
        recordFromFile = this.ArzFileIT.GetItem(itemId);
      if (recordFromFile == null && this.ArzFile != null)
        recordFromFile = this.ArzFile.GetItem(itemId);
      return recordFromFile;
    }

    public static string HtmlColor(Color color) => string.Format((IFormatProvider) CultureInfo.InvariantCulture, "#{0:X2}{1:X2}{2:X2}", new object[3]
    {
      (object) color.R,
      (object) color.G,
      (object) color.B
    });

    public byte[] LoadArcFileData(
      string scope,
      string resourceId,
      string rootFolder,
      bool alternateFolder)
    {
      resourceId = TQData.NormalizeRecordPath(resourceId);
      int length = resourceId.IndexOf('\\');
      if (length <= 0)
        return (byte[]) null;
      string path = resourceId.Substring(0, length);
      rootFolder = Path.Combine(rootFolder, "Resources");
      bool flag1 = path.ToUpperInvariant().Equals("XPACK");
      if (flag1 || !flag1 & alternateFolder)
      {
        rootFolder = Path.Combine(rootFolder, "XPack");
        int num = length;
        length = resourceId.IndexOf('\\', length + 1);
        if (length <= 0)
          return (byte[]) null;
        path = resourceId.Substring(num + 1, length - num - 1);
        resourceId = resourceId.Substring(num + 1);
      }
      bool flag2 = path.ToUpperInvariant().Equals("XPACK2");
      if (flag2 || !flag2 & alternateFolder)
      {
        rootFolder = Path.Combine(rootFolder, "XPack2");
        int num1 = length;
        int num2 = resourceId.IndexOf('\\', length + 1);
        if (num2 <= 0)
          return (byte[]) null;
        path = resourceId.Substring(num1 + 1, num2 - num1 - 1);
        resourceId = resourceId.Substring(num1 + 1);
      }
      return this.ReadARCFile(Path.Combine(rootFolder, Path.ChangeExtension(path, ".arc")), resourceId);
    }

    private void LoadARZFile()
    {
      if (TQDebug.DatabaseDebugLevel > 0)
        TQDebug.DebugWriteLine("Database.LoadARZFile()");
      string empty = string.Empty;
      try
      {
        string installLocation = Config.getConfig().installLocation;
        this.ArzFileAE = this.LoadARZFile(Path.Combine(Path.Combine(Config.getConfig().installLocation, nameof (Database)), "database.arz"), "Anniversary Edition");
      }
      catch (Exception ex)
      {
        throw new FileLoadException("Error in load arz File for TQ\nuse AE:" + true.ToString(), ex.ToString());
      }
      if (TQDebug.DatabaseDebugLevel <= 0)
        return;
      TQDebug.DebugWriteLine("Exiting Database.LoadARZFile()");
    }

    private ArzFile LoadARZFile(string filepath, string scope)
    {
      if (TQDebug.DatabaseDebugLevel > 1)
      {
        TQDebug.DebugWriteLine("Load " + scope + " database arz file");
        TQDebug.DebugWriteLine(string.Format((IFormatProvider) CultureInfo.InvariantCulture, "file = {0}", new object[1]
        {
          (object) filepath
        }));
      }
      ArzFile arzFile = (ArzFile) null;
      if (File.Exists(filepath))
      {
        arzFile = new ArzFile(filepath);
        arzFile.Read();
      }
      return arzFile;
    }

    public Bitmap LoadBitmap(string resourceId)
    {
      Bitmap bitmap1 = (Bitmap) null;
      if (string.IsNullOrEmpty(resourceId))
        return bitmap1;
      if (TQDebug.DatabaseDebugLevel > 0)
        TQDebug.DebugWriteLine(string.Format((IFormatProvider) CultureInfo.InvariantCulture, "Database.LoadBitmap({0})", new object[1]
        {
          (object) resourceId
        }));
      resourceId = TQData.NormalizeRecordPath(resourceId);
      Bitmap bitmap2;
      try
      {
        bitmap2 = this.bitmaps[resourceId];
      }
      catch (KeyNotFoundException ex1)
      {
        byte[] data = this.LoadResource(resourceId);
        if (data == null)
        {
          if (TQDebug.DatabaseDebugLevel > 0)
            TQDebug.DebugWriteLine("Failure loading resource.  Using default bitmap");
          bitmap2 = this.DefaultBitmap;
        }
        else
        {
          if (TQDebug.DatabaseDebugLevel > 1)
            TQDebug.DebugWriteLine(string.Format((IFormatProvider) CultureInfo.InvariantCulture, "Loaded resource size={0}", new object[1]
            {
              (object) data.Length
            }));
          try
          {
            bitmap2 = BitmapCode.LoadFromTexMemory(data, 0, data.Length);
          }
          catch (Exception ex2)
          {
            Logger.Log("Error loading bitmap for " + resourceId);
            bitmap2 = (Bitmap) null;
          }
          if (bitmap2 == null)
          {
            if (TQDebug.DatabaseDebugLevel > 0)
              TQDebug.DebugWriteLine(string.Format((IFormatProvider) CultureInfo.InvariantCulture, "Failure creating bitmap from resource data len={0}", new object[1]
              {
                (object) data.Length
              }));
            bitmap2 = this.DefaultBitmap;
          }
          if (TQDebug.DatabaseDebugLevel > 1)
            TQDebug.DebugWriteLine(string.Format((IFormatProvider) CultureInfo.InvariantCulture, "Created Bitmap {0} x {1}", new object[2]
            {
              (object) bitmap2.Width,
              (object) bitmap2.Height
            }));
        }
        this.bitmaps.Add(resourceId, bitmap2);
      }
      if (TQDebug.DatabaseDebugLevel > 0)
        TQDebug.DebugWriteLine("Exiting Database.LoadBitmap()");
      return bitmap2;
    }

    public void LoadDBFile()
    {
      this.LoadTextDB();
      this.LoadARZFile();
      this.infoDB = new Dictionary<string, Info>();
      this.LoadRelicOverlayBitmap();
    }

    public Bitmap LoadRelicOverlayBitmap()
    {
      Bitmap bitmap = this.LoadBitmap("Items\\Relic\\ItemRelicOverlay.tex");
      return bitmap == this.DefaultBitmap ? (Bitmap) null : bitmap;
    }

    public byte[] LoadResource(string resourceId)
    {
      string installLocation = Config.getConfig().installLocation;
      return this.LoadArcFileData("AE", resourceId, installLocation, false);
    }

    private void LoadTextDB()
    {
      if (TQDebug.DatabaseDebugLevel > 0)
        TQDebug.DebugWriteLine("Database.LoadTextDB()");
      this.textDB = new Dictionary<string, string>();
      try
      {
        if (Config.getConfig().installLocation != null)
        {
          string use = this.FigureDBFileToUse(Path.Combine(Config.getConfig().installLocation, "Text"));
          if (TQDebug.DatabaseDebugLevel > 1)
          {
            TQDebug.DebugWriteLine("Find Anniversary Edition text file");
            TQDebug.DebugWriteLine(string.Format((IFormatProvider) CultureInfo.InvariantCulture, "dbFile = {0}", new object[1]
            {
              (object) use
            }));
          }
          if (use != null)
          {
            this.ParseTextDB(use, "text\\commonequipment.txt");
            this.ParseTextDB(use, "text\\menu.txt");
            this.ParseTextDB(use, "text\\monsters.txt");
            this.ParseTextDB(use, "text\\npc.txt");
            this.ParseTextDB(use, "text\\quest.txt");
            this.ParseTextDB(use, "text\\skills.txt");
            this.ParseTextDB(use, "text\\ui.txt");
            this.ParseTextDB(use, "text\\uniqueequipment.txt");
            this.ParseTextDB(use, "text\\xcommonequipment.txt");
            this.ParseTextDB(use, "text\\xmenu.txt");
            this.ParseTextDB(use, "text\\xmonsters.txt");
            this.ParseTextDB(use, "text\\xnpc.txt");
            this.ParseTextDB(use, "text\\xquest.txt");
            this.ParseTextDB(use, "text\\xskills.txt");
            this.ParseTextDB(use, "text\\xui.txt");
            this.ParseTextDB(use, "text\\xuniqueequipment.txt");
            this.ParseTextDB(use, "text\\x2commonequipment.txt");
            this.ParseTextDB(use, "text\\x2monsters.txt");
            this.ParseTextDB(use, "text\\x2npc.txt");
            this.ParseTextDB(use, "text\\x2quest.txt");
            this.ParseTextDB(use, "text\\x2ui.txt");
            this.ParseTextDB(use, "text\\x2uniqueequipment.txt");
            this.ParseTextDB(use, "text\\modstrings.txt");
          }
        }
      }
      catch (Exception ex)
      {
        throw new FileLoadException("Error in load textDB for AE", ex.ToString());
      }
      if (this.textDB.Count == 0)
      {
        if (TQDebug.DatabaseDebugLevel > 0)
          TQDebug.DebugWriteLine("Exception - Could not load Text DB.");
        throw new FileLoadException("Could not load Text DB.");
      }
      if (TQDebug.DatabaseDebugLevel <= 0)
        return;
      TQDebug.DebugWriteLine("Exiting Database.LoadTextDB()");
    }

    public static string MakeSafeForHtml(string text)
    {
      text = Regex.Replace(text, "&", "&amp;");
      text = Regex.Replace(text, "<", "&lt;");
      text = Regex.Replace(text, ">", "&gt;");
      return text;
    }

    private void ParseTextDB(string databaseFile, string filename)
    {
      if (TQDebug.DatabaseDebugLevel > 0)
        TQDebug.DebugWriteLine(string.Format((IFormatProvider) CultureInfo.InvariantCulture, "Database.ParseTextDB({0}, {1})", new object[2]
        {
          (object) databaseFile,
          (object) filename
        }));
      byte[] buffer = this.ReadARCFile(databaseFile, filename);
      if (buffer == null)
      {
        if (TQDebug.DatabaseDebugLevel <= 0)
          return;
        TQDebug.DebugWriteLine(string.Format((IFormatProvider) CultureInfo.InvariantCulture, "Error in ARC File: {0} does not contain an entry for '{1}'", new object[2]
        {
          (object) databaseFile,
          (object) filename
        }));
      }
      else
      {
        using (StreamReader streamReader = new StreamReader((Stream) new MemoryStream(buffer), Encoding.Default))
        {
          char ch = '=';
          string str1;
          while ((str1 = streamReader.ReadLine()) != null)
          {
            string str2 = str1.Trim();
            if (str2.Length >= 2 && !str2.StartsWith("//", StringComparison.Ordinal))
            {
              char[] chArray = new char[1]{ ch };
              string[] strArray = str2.Split(chArray);
              if (strArray.Length >= 2)
              {
                string str3 = strArray[1].Trim();
                if (str3.IndexOf('[') != -1)
                {
                  int startIndex = str3.IndexOf(']') + 1;
                  int num = str3.IndexOf('[', startIndex);
                  string str4 = num != -1 ? str3.Substring(startIndex, num - startIndex) : str3.Substring(startIndex);
                  int length = str4.IndexOf("//");
                  if (length != -1)
                    str4 = str4.Substring(0, length);
                  str3 = str4.Trim();
                }
                this.textDB[strArray[0].Trim().ToUpperInvariant()] = str3;
              }
            }
          }
        }
        if (TQDebug.DatabaseDebugLevel <= 0)
          return;
        TQDebug.DebugWriteLine("Exiting Database.ParseTextDB()");
      }
    }

    private byte[] ReadARCFile(string arcFileName, string dataId)
    {
      try
      {
        if (TQDebug.DatabaseDebugLevel > 0)
          TQDebug.DebugWriteLine(string.Format((IFormatProvider) CultureInfo.InvariantCulture, "Database.ReadARCFile('{0}', '{1}')", new object[2]
          {
            (object) arcFileName,
            (object) dataId
          }));
        ArcFile arcFile;
        try
        {
          arcFile = this.arcFiles[arcFileName];
        }
        catch (KeyNotFoundException ex)
        {
          arcFile = new ArcFile(arcFileName);
          this.arcFiles.Add(arcFileName, arcFile);
        }
        byte[] data = arcFile.GetData(dataId);
        if (TQDebug.DatabaseDebugLevel > 0)
          TQDebug.DebugWriteLine("Exiting Database.ReadARCFile()");
        return data;
      }
      catch (Exception ex)
      {
        TQDebug.DebugWriteLine("Exception occurred");
        TQDebug.DebugWriteLine(ex.ToString());
        throw;
      }
    }

    public string VariableToStringNice(Variable variable)
    {
      StringBuilder stringBuilder = new StringBuilder(64);
      stringBuilder.Append(this.GetItemAttributeFriendlyText(variable.Name));
      stringBuilder.Append(": ");
      stringBuilder.Append(variable.ToStringValue());
      return stringBuilder.ToString();
    }

    public static Collection<string> WrapWords(string text, int columns)
    {
      List<string> list = new List<string>();
      int startIndex = 0;
      int num = text.IndexOf(' ');
      int indexOfNewLineTag = Database.getIndexOfNewLineTag(text, startIndex);
      while (text.Length - startIndex > columns && num >= 0)
      {
        while (num >= 0 && num - startIndex < columns)
        {
          if (num < indexOfNewLineTag || indexOfNewLineTag < 0)
            num = text.IndexOf(' ', num + 1);
          else if (indexOfNewLineTag >= 0)
          {
            string str = text.Substring(startIndex, indexOfNewLineTag - startIndex);
            startIndex = indexOfNewLineTag + 4;
            list.Add(str);
            indexOfNewLineTag = Database.getIndexOfNewLineTag(text, startIndex);
          }
        }
        if (num >= 0 && (num < indexOfNewLineTag || indexOfNewLineTag < 0))
        {
          string str = text.Substring(startIndex, num - startIndex);
          startIndex = num + 1;
          list.Add(str);
        }
        else if (indexOfNewLineTag >= 0)
        {
          string str = text.Substring(startIndex, indexOfNewLineTag - startIndex);
          startIndex = indexOfNewLineTag + 4;
          list.Add(str);
          indexOfNewLineTag = Database.getIndexOfNewLineTag(text, startIndex);
        }
      }
      for (; indexOfNewLineTag >= 0; indexOfNewLineTag = Database.getIndexOfNewLineTag(text, startIndex))
      {
        string str = text.Substring(startIndex, indexOfNewLineTag - startIndex);
        startIndex = indexOfNewLineTag + 4;
        list.Add(str);
      }
      list.Add(text.Substring(startIndex));
      return new Collection<string>((IList<string>) list);
    }

    private void WriteDebugLine_ExitFigureDBFile()
    {
      if (TQDebug.DatabaseDebugLevel <= 0)
        return;
      TQDebug.DebugWriteLine("Exiting Database.FigureDBFileToUse()");
    }

    public static float DesignDpi => 96f;

    public string TooltipBodyTag => string.Format((IFormatProvider) CultureInfo.CurrentCulture, "<body bgcolor=#2e291f text=white><font face=\"Albertus MT\" size={0}>", new object[1]
    {
      (object) Convert.ToInt32(9f * this.Scale)
    });

    public string TooltipTitleTag => string.Format((IFormatProvider) CultureInfo.CurrentCulture, "<body bgcolor=#2e291f text=white><font face=\"Albertus MT\" size={0}>", new object[1]
    {
      (object) Convert.ToInt32(10f * this.Scale)
    });

    public string GameLanguage
    {
      get
      {
        if (this.gameLanguage == null && !this.AutoDetectLanguage)
          this.gameLanguage = "en";
        return this.gameLanguage;
      }
    }

    public Bitmap DefaultBitmap
    {
      get
      {
        if (this.defaultBitmap == null)
          this.CreateDefaultBitmap();
        return this.defaultBitmap;
      }
    }

    public Dictionary<string, Info> getPrefixes(ItemType type)
    {
      if (type == ItemType.Thrown)
        type = ItemType.Bow;
      if (this.prefixesTable.ContainsKey(type.GetStringValue()))
        return this.prefixesTable[type.GetStringValue()];
      Logger.Debug("No prefixes found for type " + type.GetStringValue());
      return new Dictionary<string, Info>();
    }

    public Dictionary<string, Info> getSuffixes(ItemType type)
    {
      if (type == ItemType.Thrown)
        type = ItemType.Bow;
      if (this.suffixesTable.ContainsKey(type.GetStringValue()))
        return this.suffixesTable[type.GetStringValue()];
      Logger.Debug("No suffixes found for type " + type.GetStringValue());
      return new Dictionary<string, Info>();
    }

    public int ItemUnitSize => Convert.ToInt32(32f * this.Scale);

    public int HalfUnitSize => this.ItemUnitSize / 2;

    public float Scale
    {
      get => this.scale;
      set
      {
        if ((double) value < 0.40000000596046448)
          value = 0.4f;
        else if ((double) value > 2.0)
          value = 2f;
        this.scale = value;
      }
    }
  }
}
