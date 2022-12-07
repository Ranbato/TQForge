// Decompiled with JetBrains decompiler
// Type: TQ_weaponsmith.Config
// Assembly: TQ_Blacksmith, Version=0.1.4.1, Culture=neutral, PublicKeyToken=null
// MVID: 1CC9040D-95C6-4C54-B248-9D80684B2CA8
// Assembly location: D:\Games\GOG Galaxy\Games\Titan Quest - Anniversary Edition\Downloads\Blacksmith\TQ_Blacksmith.exe

using System;
using System.IO;
using System.Windows.Forms;

namespace TQ_weaponsmith
{
  internal class Config
  {
    private const string FILENAME = "Configurations.txt";
    public const string CHARACTERS_FOLDER = "\\SaveData\\Main";
    public const string CHARACTERS_SAVE_FILE = "Player.chr";
    private const string SEPARATOR = "=";
    private const string COMMENT = "#";
    public const string KEY_GAME_SAVE_LOCATION = "GameSaveLocation";
    public const string KEY_GAME_INSTALL_LOCATION = "GameInstallLocation";
    public const string KEY_DETAIL_LOGS = "detailLogs";
    public const string KEY_HELP_SHOWN = "helpShown";
    public string[] itemListFiles = new string[24]
    {
      "10_club.txt",
      "11_spear.txt",
      "12_staff.txt",
      "13_throwing.txt",
      "14_shield.txt",
      "15_ring.txt",
      "16_amulet.txt",
      "17_relic.txt",
      "18_charm.txt",
      "19_artifact.txt",
      "1_prefixes.txt",
      "20_scroll.txt",
      "21_artifact_formula.txt",
      "22_lootRandomizerTable.txt",
      "23_parchments.txt",
      "24_questItems.txt",
      "2_suffixes.txt",
      "3_head.txt",
      "4_chest.txt",
      "5_arms.txt",
      "6_legs.txt",
      "7_axe.txt",
      "8_bow.txt",
      "9_sword.txt"
    };
    public const string prefixFile = "ItemwisePrefix.txt";
    public const string suffixFile = "ItemwiseSuffix.txt";
    public const string miscFile = "misc.txt";
    public bool detailLogs;
    public string saveLocation = "";
    public string installLocation = "";
    public static bool areResourcesLoaded;
    public static bool helpShown;
    private static Config cfg;
    private static bool isInitialized;

    private Config()
    {
    }

    public static void initialize()
    {
      Config.cfg = new Config();
      Config.cfg.detailLogs = false;
      Config.cfg.saveLocation = Environment.GetFolderPath(Environment.SpecialFolder.Personal) + "\\My Games\\Titan Quest - Immortal Throne";
      Config.cfg.installLocation = "";
      Config.cfg.loadFromFile();
      Config.isInitialized = true;
    }

    public static void close()
    {
    }

    public static Config getConfig()
    {
      if (Config.isInitialized)
        return Config.cfg;
      throw new Exception("Configuration not loaded.");
    }

    public static void saveConfig()
    {
      Logger.Log("saving Config");
      Config.cfg.saveToFile();
    }

    private void loadFromFile()
    {
      try
      {
        if (File.Exists("Configurations.txt"))
        {
          using (StreamReader streamReader = new StreamReader("Configurations.txt"))
          {
            string str1;
            while (!string.IsNullOrWhiteSpace(str1 = streamReader.ReadLine()) && !str1.StartsWith("#"))
            {
              int length = str1.IndexOf("=", StringComparison.Ordinal);
              if (length != -1)
              {
                string name = str1.Substring(0, length);
                string str2 = str1.Substring(length + 1);
                if (name != "" && str2 != "")
                  this.setProperty(name, str2);
              }
            }
          }
        }
        else
        {
          int num = (int) MessageBox.Show("Configuration.txt not found in local program folder.", "Error", MessageBoxButtons.OK, MessageBoxIcon.Hand);
          Logger.Log("Configuration.txt not found in local program folder.");
        }
      }
      catch (Exception ex)
      {
        Logger.Log("Error loading configuration from Configuration.txt.\n\n" + ex.ToString());
        int num = (int) MessageBox.Show("Error loading configuration from Configuration.txt.\n\n" + ex.ToString());
      }
    }

    private void saveToFile()
    {
      try
      {
        using (StreamWriter streamWriter = new StreamWriter("Configurations.txt"))
        {
          streamWriter.WriteLine("GameSaveLocation=" + this.saveLocation);
          streamWriter.WriteLine("GameInstallLocation=" + this.installLocation);
          streamWriter.WriteLine("detailLogs=" + this.detailLogs.ToString());
          streamWriter.WriteLine("helpShown=" + Config.helpShown.ToString());
        }
      }
      catch (Exception ex)
      {
        Logger.Log("Error writing configuration to Configuration.txt.\n\n" + ex.ToString());
        int num = (int) MessageBox.Show("Error writing configuration to Configuration.txt.\n\n" + ex.ToString());
      }
    }

    private void setProperty(string name, string value)
    {
      if ("GameSaveLocation".Equals(name))
        this.saveLocation = value;
      else if ("detailLogs".Equals(name))
        this.detailLogs = value.ToLower() == "true";
      else if ("GameInstallLocation".Equals(name))
      {
        this.installLocation = value;
      }
      else
      {
        if (!"helpShown".Equals(name))
          return;
        Config.helpShown = bool.Parse(value);
      }
    }

    public string getSaveFile(string characterName) => this.saveLocation + "\\SaveData\\Main\\_" + characterName + "\\Player.chr";
  }
}
