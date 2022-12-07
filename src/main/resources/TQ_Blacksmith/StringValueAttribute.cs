// Decompiled with JetBrains decompiler
// Type: TQ_weaponsmith.StringValueAttribute
// Assembly: TQ_Blacksmith, Version=0.1.4.1, Culture=neutral, PublicKeyToken=null
// MVID: 1CC9040D-95C6-4C54-B248-9D80684B2CA8
// Assembly location: D:\Games\GOG Galaxy\Games\Titan Quest - Anniversary Edition\Downloads\Blacksmith\TQ_Blacksmith.exe

using System;

namespace TQ_weaponsmith
{
  public class StringValueAttribute : Attribute
  {
    public string StringValue { get; protected set; }

    public StringValueAttribute(string value) => this.StringValue = value;
  }
}
