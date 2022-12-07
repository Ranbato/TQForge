// Decompiled with JetBrains decompiler
// Type: TQ_weaponsmith.TQDebug
// Assembly: TQ_Blacksmith, Version=0.1.4.1, Culture=neutral, PublicKeyToken=null
// MVID: 1CC9040D-95C6-4C54-B248-9D80684B2CA8
// Assembly location: D:\Games\GOG Galaxy\Games\Titan Quest - Anniversary Edition\Downloads\Blacksmith\TQ_Blacksmith.exe

using System.IO;

namespace TQ_weaponsmith
{
  public static class TQDebug
  {
    private static bool debugEnabled;
    private static int arcFileDebugLevel;
    private static int databaseDebugLevel;
    private static int itemDebugLevel;
    private static int itemAttributesDebugLevel;

    public static void DebugWrite(string message)
    {
      if (!TQDebug.DebugEnabled)
        return;
      StreamWriter streamWriter = new StreamWriter("tqdebug.txt", true);
      streamWriter.Write(message);
      streamWriter.Close();
    }

    public static void DebugWriteLine(string message)
    {
      if (!TQDebug.DebugEnabled)
        return;
      StreamWriter streamWriter = new StreamWriter("tqdebug.txt", true);
      streamWriter.WriteLine(message);
      streamWriter.Close();
    }

    public static bool DebugEnabled
    {
      get => TQDebug.debugEnabled;
      set
      {
        bool debugEnabled = TQDebug.debugEnabled;
        TQDebug.debugEnabled = value;
        if (!TQDebug.debugEnabled || debugEnabled == TQDebug.debugEnabled || !File.Exists("tqdebug.txt"))
          return;
        File.Delete("tqdebug.txt");
      }
    }

    public static int DatabaseDebugLevel
    {
      get => TQDebug.DebugEnabled ? TQDebug.databaseDebugLevel : 0;
      set => TQDebug.databaseDebugLevel = value;
    }

    public static int ArcFileDebugLevel
    {
      get => TQDebug.DebugEnabled ? TQDebug.arcFileDebugLevel : 0;
      set => TQDebug.arcFileDebugLevel = value;
    }

    public static int ItemDebugLevel
    {
      get => TQDebug.DebugEnabled ? TQDebug.itemDebugLevel : 0;
      set => TQDebug.itemDebugLevel = value;
    }

    public static int ItemAttributesDebugLevel
    {
      get => TQDebug.DebugEnabled ? TQDebug.itemAttributesDebugLevel : 0;
      set => TQDebug.itemAttributesDebugLevel = value;
    }
  }
}
