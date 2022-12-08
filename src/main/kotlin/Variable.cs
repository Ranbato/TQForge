// Decompiled with JetBrains decompiler
// Type: TQ_weaponsmith.Variable
// Assembly: TQ_Blacksmith, Version=0.1.4.1, Culture=neutral, PublicKeyToken=null
// MVID: 1CC9040D-95C6-4C54-B248-9D80684B2CA8
// Assembly location: D:\Games\GOG Galaxy\Games\Titan Quest - Anniversary Edition\Downloads\Blacksmith\TQ_Blacksmith.exe

using System;
using System.Globalization;
using System.Text;

namespace TQ_weaponsmith
{
  public class Variable
  {
    private object[] values;

    public Variable(string variableName, VariableDataType dataType, int numberOfValues)
    {
      this.Name = variableName;
      this.DataType = dataType;
      this.values = new object[numberOfValues];
    }

    public int GetInt32(int index) => Convert.ToInt32(this.values[index], (IFormatProvider) CultureInfo.InvariantCulture);

    public float GetSingle(int index) => Convert.ToSingle(this.values[index], (IFormatProvider) CultureInfo.InvariantCulture);

    public string GetString(int index) => Convert.ToString(this.values[index], (IFormatProvider) CultureInfo.InvariantCulture);

    public override string ToString()
    {
      string format = "{0}";
      if (this.DataType == VariableDataType.Float)
        format = "{0:f6}";
      StringBuilder stringBuilder = new StringBuilder(64);
      stringBuilder.Append(this.Name);
      stringBuilder.Append(",");
      for (int index = 0; index < this.NumberOfValues; ++index)
      {
        if (index > 0)
          stringBuilder.Append(";");
        object[] objArray = new object[1]
        {
          this.values[index]
        };
        stringBuilder.AppendFormat((IFormatProvider) CultureInfo.InvariantCulture, format, objArray);
      }
      stringBuilder.Append(",");
      return stringBuilder.ToString();
    }

    public string ToStringValue()
    {
      string format = "{0}";
      if (this.DataType == VariableDataType.Float)
        format = "{0:f6}";
      StringBuilder stringBuilder = new StringBuilder(64);
      for (int index = 0; index < this.NumberOfValues; ++index)
      {
        if (index > 0)
          stringBuilder.Append(", ");
        object[] objArray = new object[1]
        {
          this.values[index]
        };
        stringBuilder.AppendFormat((IFormatProvider) CultureInfo.InvariantCulture, format, objArray);
      }
      return stringBuilder.ToString();
    }

    public string Name { get; private set; }

    public VariableDataType DataType { get; private set; }

    public int NumberOfValues => this.values.Length;

    public object this[int index]
    {
      get => this.values[index];
      set => this.values[index] = value;
    }
  }
}
