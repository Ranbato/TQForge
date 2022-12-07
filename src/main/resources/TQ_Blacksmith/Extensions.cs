// Decompiled with JetBrains decompiler
// Type: TQ_weaponsmith.Extensions
// Assembly: TQ_Blacksmith, Version=0.1.4.1, Culture=neutral, PublicKeyToken=null
// MVID: 1CC9040D-95C6-4C54-B248-9D80684B2CA8
// Assembly location: D:\Games\GOG Galaxy\Games\Titan Quest - Anniversary Edition\Downloads\Blacksmith\TQ_Blacksmith.exe

using System;

namespace TQ_weaponsmith
{
  public static class Extensions
  {
    public static string GetStringValue(this Enum value)
    {
      StringValueAttribute[] customAttributes = value.GetType().GetField(value.ToString()).GetCustomAttributes(typeof (StringValueAttribute), false) as StringValueAttribute[];
      return customAttributes.Length == 0 ? (string) null : customAttributes[0].StringValue;
    }
  }
}
