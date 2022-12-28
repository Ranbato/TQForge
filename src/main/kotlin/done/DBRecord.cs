// Decompiled with JetBrains decompiler
// Type: TQ_weaponsmith.DBRecord
// Assembly: TQ_Blacksmith, Version=0.1.4.1, Culture=neutral, PublicKeyToken=null
// MVID: 1CC9040D-95C6-4C54-B248-9D80684B2CA8
// Assembly location: D:\Games\GOG Galaxy\Games\Titan Quest - Anniversary Edition\Downloads\Blacksmith\TQ_Blacksmith.exe

using System;
using System.Collections;
using System.Collections.Generic;
using System.Globalization;
using System.IO;

namespace TQ_weaponsmith
{
  public class DBRecord : IEnumerable<Variable>, IEnumerable
  {
    private Dictionary<string, Variable> variables;
    private string id;
    private string recordType;

    public DBRecord(string id, string recordType)
    {
      this.Id = id;
      this.RecordType = recordType;
      this.variables = new Dictionary<string, Variable>();
    }

    public string[] GetAllStrings(string variableName)
    {
      Variable variable;
      try
      {
        variable = this.variables[variableName.ToUpperInvariant()];
      }
      catch (KeyNotFoundException ex)
      {
        return (string[]) null;
      }
      string[] allStrings = new string[variable.NumberOfValues];
      for (int index = 0; index < variable.NumberOfValues; ++index)
        allStrings[index] = variable.GetString(index);
      return allStrings;
    }

    public IEnumerator<Variable> GetEnumerator()
    {
      foreach (KeyValuePair<string, Variable> variable in this.variables)
        yield return variable.Value;
    }

    public int GetInt32(string variableName, int index)
    {
      try
      {
        return this.variables[variableName.ToUpperInvariant()].GetInt32(index);
      }
      catch (KeyNotFoundException ex)
      {
        return 0;
      }
    }

    public float GetSingle(string variableName, int index)
    {
      try
      {
        return this.variables[variableName.ToUpperInvariant()].GetSingle(index);
      }
      catch (KeyNotFoundException ex)
      {
        return 0.0f;
      }
    }

    public string GetString(string variableName, int index)
    {
      Variable variable;
      try
      {
        variable = this.variables[variableName.ToUpperInvariant()];
      }
      catch (KeyNotFoundException ex)
      {
        return string.Empty;
      }
      return variable.GetString(index) ?? string.Empty;
    }

    public void Set(Variable variable) => this.variables.Add(variable.Name.ToUpperInvariant(), variable);

    IEnumerator IEnumerable.GetEnumerator() => (IEnumerator) this.GetEnumerator();

    public string ToShortString() => string.Format((IFormatProvider) CultureInfo.CurrentCulture, "{0},{1},{2}", new object[3]
    {
      (object) this.Id,
      (object) this.RecordType,
      (object) this.Count
    });

    public void Write(string baseFolder, string fileName = null)
    {
      string path1 = Path.Combine(baseFolder, this.Id);
      string path2 = Path.GetDirectoryName(path1);
      if (fileName != null)
      {
        path1 = Path.Combine(baseFolder, fileName);
        path2 = baseFolder;
      }
      if (!Directory.Exists(path2))
        Directory.CreateDirectory(path2);
      using (StreamWriter streamWriter = new StreamWriter(path1, false))
      {
        foreach (Variable variable in this)
          streamWriter.WriteLine(variable.ToString());
      }
    }

    public string Id { get; private set; }

    public string RecordType { get; private set; }

    public int Count => this.variables.Count;

    public Variable this[string variableName]
    {
      get
      {
        try
        {
          return this.variables[variableName.ToUpperInvariant()];
        }
        catch (KeyNotFoundException ex)
        {
          return (Variable) null;
        }
      }
    }
  }
}
