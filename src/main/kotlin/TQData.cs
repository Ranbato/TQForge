// Decompiled with JetBrains decompiler
// Type: TQ_weaponsmith.TQData
// Assembly: TQ_Blacksmith, Version=0.1.4.1, Culture=neutral, PublicKeyToken=null
// MVID: 1CC9040D-95C6-4C54-B248-9D80684B2CA8
// Assembly location: D:\Games\GOG Galaxy\Games\Titan Quest - Anniversary Edition\Downloads\Blacksmith\TQ_Blacksmith.exe

using System;
using System.Collections.Generic;
using System.IO;
using System.Text;

namespace TQ_weaponsmith
{
  public class TQData
  {
    private static Config cfg;
    public static TQData tqData = new TQData();
    public static Character selectedCharacter;
    public static string[] characterNameList;
    public static readonly List<Character> characterList = new List<Character>();

    private TQData() => TQData.cfg = Config.getConfig();

    public static TQData getTQData() => TQData.tqData;

    public static void initialize()
    {
    }

    public static bool isWeaponOrArmor(ItemType type) => ItemType.Head == type || ItemType.Chest == type || ItemType.Arm == type || ItemType.Leg == type || ItemType.Sword == type || ItemType.Axe == type || ItemType.Shield == type || ItemType.Mace == type || ItemType.Staff == type || ItemType.Spear == type || ItemType.Bow == type || ItemType.Thrown == type;

    public static void writeCString(BinaryWriter writer, string value)
    {
      byte[] bytes = Encoding.GetEncoding(1252).GetBytes(value);
      writer.Write(bytes.Length);
      writer.Write(bytes);
    }

    public static string readCString(BinaryReader reader)
    {
      int num = reader.ReadInt32();
      Encoding encoding = Encoding.GetEncoding(1252);
      byte[] bytes = reader.ReadBytes(num);
      char[] chars = new char[encoding.GetCharCount(bytes, 0, num)];
      encoding.GetChars(bytes, 0, num, chars, 0);
      return new string(chars);
    }

    public static bool validateNextString(string value, BinaryReader reader)
    {
      string str = TQData.readCString(reader);
      if (str.ToUpperInvariant().Equals(value.ToUpperInvariant()))
        return true;
      object[] objArray = new object[3]
      {
        (object) value,
        (object) str,
        (object) (reader.BaseStream.Position - (long) str.Length - 4L)
      };
      Logger.Debug("Error reading file at position " + (object) (reader.BaseStream.Position - (long) str.Length - 4L) + ".  Expecting " + value + ".  Got " + str);
      return false;
    }

    public static string ReadCString(BinaryReader reader)
    {
      int num = reader.ReadInt32();
      Encoding encoding = Encoding.GetEncoding(1252);
      byte[] bytes = reader.ReadBytes(num);
      char[] chars = new char[encoding.GetCharCount(bytes, 0, num)];
      encoding.GetChars(bytes, 0, num, chars, 0);
      return new string(chars);
    }

    public static string[] loadCharacterList()
    {
      TQData.characterNameList = (string[]) null;
      try
      {
        string[] directories = Directory.GetDirectories(Config.getConfig().saveLocation + "\\SaveData\\Main", "_*");
        if (directories == null || directories.Length < 1)
          return (string[]) null;
        List<string> stringList = new List<string>(directories.Length);
        foreach (string path in directories)
        {
          stringList.Add(Path.GetFileName(path).Substring(1));
          Logger.Debug("Character found: " + Path.GetFileName(path).Substring(1));
        }
        stringList.Sort();
        TQData.characterNameList = stringList.ToArray();
        return TQData.characterNameList;
      }
      catch (DirectoryNotFoundException ex)
      {
        return (string[]) null;
      }
    }

    public static Character loadPlayerData(string playerName)
    {
      Character character;
      try
      {
        string empty = string.Empty;
        string saveFile = TQData.cfg.getSaveFile(playerName);
        character = new Character(playerName, saveFile);
        try
        {
          character.loadFile();
        }
        catch (ArgumentException ex)
        {
          Logger.Log("Error: Unable to load character weaponSmith data: " + ex.ToString());
          throw ex;
        }
      }
      catch (IOException ex)
      {
        Logger.Log("Error: " + ex.ToString());
        throw ex;
      }
      return character;
    }

    public static Character loadCharacter(string chrName)
    {
      if (chrName == null)
        return (Character) null;
      bool flag = false;
      Character character1 = (Character) null;
      foreach (Character character2 in TQData.characterList)
      {
        if (character2.CharacterName.Equals(chrName))
        {
          flag = true;
          character1 = character2;
          break;
        }
      }
      if (flag)
        return character1;
      Character character3 = TQData.loadPlayerData(chrName);
      TQData.characterList.Add(character3);
      return character3;
    }

    public static string NormalizeRecordPath(string recordId) => recordId.ToUpperInvariant().Replace('/', '\\');

    public static ItemType getItemType(string type)
    {
      if (type != null)
        type = type.ToLowerInvariant();
      switch (type)
      {
        case "amulet":
          return ItemType.Amulet;
        case "arm":
          return ItemType.Arm;
        case "armor":
          return ItemType.Armor;
        case "arms":
          return ItemType.Arm;
        case "artifact":
          return ItemType.Artifact;
        case "axe":
          return ItemType.Axe;
        case "bow":
          return ItemType.Bow;
        case "charm":
          return ItemType.Charm;
        case "chest":
          return ItemType.Chest;
        case "club":
          return ItemType.Mace;
        case "formula":
          return ItemType.Formula;
        case "head":
          return ItemType.Head;
        case "leg":
          return ItemType.Leg;
        case "legs":
          return ItemType.Leg;
        case "mace":
          return ItemType.Mace;
        case "misc":
          return ItemType.Misc;
        case "parchment":
          return ItemType.Parchment;
        case "questitem":
          return ItemType.Quest;
        case "relic":
          return ItemType.Relic;
        case "ring":
          return ItemType.Ring;
        case "scroll":
          return ItemType.Scroll;
        case "shield":
          return ItemType.Shield;
        case "spear":
          return ItemType.Spear;
        case "staff":
          return ItemType.Staff;
        case "sword":
          return ItemType.Sword;
        case "thrown":
          return ItemType.Thrown;
        case "torso":
          return ItemType.Chest;
        case "weapon":
          return ItemType.Weapon;
        default:
          return ItemType.Weapon;
      }
    }
  }
}
