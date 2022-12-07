// Decompiled with JetBrains decompiler
// Type: TQ_weaponsmith.LootTable
// Assembly: TQ_Blacksmith, Version=0.1.4.1, Culture=neutral, PublicKeyToken=null
// MVID: 1CC9040D-95C6-4C54-B248-9D80684B2CA8
// Assembly location: D:\Games\GOG Galaxy\Games\Titan Quest - Anniversary Edition\Downloads\Blacksmith\TQ_Blacksmith.exe

using System;
using System.Collections;
using System.Collections.Generic;
using System.Linq;

namespace TQ_weaponsmith
{
  public class LootTable : IEnumerable<KeyValuePair<string, float>>, IEnumerable
  {
    private string tableId;
    private Dictionary<string, float> data;
    private float totalWeight;

    public LootTable(string tableId)
    {
      this.tableId = tableId;
      this.data = new Dictionary<string, float>();
      this.LoadTable();
    }

    public IEnumerator<KeyValuePair<string, float>> GetEnumerator()
    {
      float totalWeight = this.totalWeight;
      if ((double) totalWeight == 0.0)
        totalWeight = 1f;
      foreach (KeyValuePair<string, float> keyValuePair in this.data)
        yield return new KeyValuePair<string, float>(keyValuePair.Key, keyValuePair.Value / totalWeight);
    }

    private void LoadTable()
    {
      DBRecord recordFromFile = Database.DB.GetRecordFromFile(this.tableId);
      if (recordFromFile == null)
        return;
      Dictionary<int, float> outer = new Dictionary<int, float>();
      Dictionary<int, string> inner = new Dictionary<int, string>();
      foreach (Variable variable in recordFromFile)
      {
        string upperInvariant = variable.Name.ToUpperInvariant();
        if (upperInvariant.StartsWith("RANDOMIZERWEIGHT", StringComparison.Ordinal))
        {
          int result;
          if (int.TryParse(upperInvariant.Substring(16), out result))
          {
            float num = -1f;
            if (variable.DataType == VariableDataType.Integer)
              num = (float) variable.GetInt32(0);
            else if (variable.DataType == VariableDataType.Float)
              num = variable.GetSingle(0);
            if ((double) num > 0.0)
              outer.Add(result, num);
          }
        }
        else
        {
          int result;
          if (upperInvariant.StartsWith("RANDOMIZERNAME", StringComparison.Ordinal) && int.TryParse(upperInvariant.Substring(14), out result))
          {
            string str = variable.GetString(0);
            if (!string.IsNullOrEmpty(str))
              inner.Add(result, str);
          }
        }
      }
      IEnumerable<KeyValuePair<string, float>> source = outer.Join<KeyValuePair<int, float>, KeyValuePair<int, string>, int, KeyValuePair<string, float>>((IEnumerable<KeyValuePair<int, string>>) inner, (Func<KeyValuePair<int, float>, int>) (weight => weight.Key), (Func<KeyValuePair<int, string>, int>) (name => name.Key), (Func<KeyValuePair<int, float>, KeyValuePair<int, string>, KeyValuePair<string, float>>) ((weight, name) => new KeyValuePair<string, float>(name.Value, weight.Value)));
      foreach (KeyValuePair<string, float> keyValuePair in source)
      {
        if (this.data.ContainsKey(keyValuePair.Key))
          this.data[keyValuePair.Key] += keyValuePair.Value;
        else
          this.data.Add(keyValuePair.Key, keyValuePair.Value);
      }
      this.totalWeight = source.Sum<KeyValuePair<string, float>>((Func<KeyValuePair<string, float>, float>) (kvp => kvp.Value));
    }

    IEnumerator IEnumerable.GetEnumerator() => (IEnumerator) this.GetEnumerator();

    public int Length => this.data.Count;
  }
}
