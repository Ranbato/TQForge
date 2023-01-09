// Decompiled with JetBrains decompiler
// Type: TQ_weaponsmith.Logger
// Assembly: TQ_Blacksmith, Version=0.1.4.1, Culture=neutral, PublicKeyToken=null
// MVID: 1CC9040D-95C6-4C54-B248-9D80684B2CA8
// Assembly location: D:\Games\GOG Galaxy\Games\Titan Quest - Anniversary Edition\Downloads\Blacksmith\TQ_Blacksmith.exe

using System;
using System.IO;

namespace TQ_weaponsmith
{
  internal class Logger
  {
    public const string LOG_FILE_NAME = "logs.txt";
    private static StreamWriter sw;
    private static bool detailLogs;
    private static bool isInitialized;

    private Logger()
    {
    }

    public static void initialize(bool detailLogs)
    {
      Logger.sw = !File.Exists("logs.txt") ? File.CreateText("logs.txt") : File.AppendText("logs.txt");
      Logger.detailLogs = detailLogs;
      Logger.isInitialized = true;
    }

    public static void close()
    {
      try
      {
        Logger.Log("Application closed");
        Logger.sw.Dispose();
      }
      catch (Exception ex)
      {
      }
    }

    public static void Log(string message)
    {
      if (!Logger.isInitialized)
        return;
      Logger.sw.WriteLine("{0} :{1}", (object) DateTime.Now.ToString("yyyy/MM/dd HH:mm:ss.ff"), (object) message);
      Logger.sw.Flush();
    }

    public static void Debug(string info)
    {
      if (!Logger.detailLogs)
        return;
      Logger.Log("Debug: " + info);
    }
  }
}
