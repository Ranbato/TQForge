// Decompiled with JetBrains decompiler
// Type: TQ_weaponsmith.ArzFile
// Assembly: TQ_Blacksmith, Version=0.1.4.1, Culture=neutral, PublicKeyToken=null
// MVID: 1CC9040D-95C6-4C54-B248-9D80684B2CA8
// Assembly location: D:\Games\GOG Galaxy\Games\Titan Quest - Anniversary Edition\Downloads\Blacksmith\TQ_Blacksmith.exe

using System;
using System.Collections.Generic;
using System.Globalization;
using System.IO;
using System.IO.Compression;

namespace TQ_weaponsmith
{
  public class ArzFile
  {
    private byte[] rawData;
    private string fileName;
    private string[] strings;
    private Dictionary<string, ArzFile.RecordInfo> recordInfo;
    private Dictionary<string, DBRecord> cache;
    private string[] keys;
    private string[] requiredKeys;
    public int requiredKeyIndex;

    public ArzFile(string fileName)
    {
      this.fileName = fileName;
      this.cache = new Dictionary<string, DBRecord>();
    }

    private void BuildKeyTable()
    {
      if (this.recordInfo == null || this.recordInfo.Count == 0)
        return;
      int index = 0;
      DBClasses.getClasses();
      this.keys = new string[this.recordInfo.Count];
      this.requiredKeys = new string[this.recordInfo.Count];
      this.requiredKeyIndex = 0;
      foreach (string key in this.recordInfo.Keys)
      {
        this.keys[index] = key;
        string recordType = this.recordInfo[key].RecordType;
        if (recordType != null && !string.IsNullOrEmpty(recordType))
          this.requiredKeys[this.requiredKeyIndex++] = key;
        ++index;
      }
      Array.Sort<string>(this.keys);
    }

    public DBRecord GetItem(string recordId)
    {
      DBRecord dbRecord = (DBRecord) null;
      if (string.IsNullOrEmpty(recordId))
        return dbRecord;
      recordId = TQData.NormalizeRecordPath(recordId);
      try
      {
        return this.cache[recordId];
      }
      catch (KeyNotFoundException ex1)
      {
        ArzFile.RecordInfo recordInfo;
        try
        {
          recordInfo = this.recordInfo[recordId];
        }
        catch (KeyNotFoundException ex2)
        {
          return (DBRecord) null;
        }
        return recordInfo.Decompress(this);
      }
    }

    public string[] GetKeyTable()
    {
      if (this.keys == null || this.keys.Length == 0)
        this.BuildKeyTable();
      return (string[]) this.keys.Clone();
    }

    public string[] GetRequiredKeyTable()
    {
      if (this.requiredKeys == null || this.requiredKeys.Length == 0)
        this.BuildKeyTable();
      return (string[]) this.requiredKeys.Clone();
    }

    public DBRecord GetRecordNotCached(string recordId)
    {
      recordId = TQData.NormalizeRecordPath(recordId);
      try
      {
        return this.cache[recordId];
      }
      catch (KeyNotFoundException ex1)
      {
        try
        {
          return this.recordInfo[recordId].Decompress(this);
        }
        catch (KeyNotFoundException ex2)
        {
          return (DBRecord) null;
        }
      }
    }

    private string Getstring(int index) => this.strings[index];

    public bool Read()
    {
      StreamWriter outStream = (StreamWriter) null;
      if (TQDebug.DatabaseDebugLevel > 2)
        outStream = new StreamWriter("arzOut.txt", false);
      try
      {
        FileStream input = new FileStream(this.fileName, FileMode.Open, FileAccess.Read, FileShare.Read);
        this.rawData = new BinaryReader((Stream) input).ReadBytes((int) input.Length);
        BinaryReader reader = new BinaryReader((Stream) new MemoryStream(this.rawData, false));
        try
        {
          int[] numArray = new int[6];
          for (int index = 0; index < 6; ++index)
          {
            numArray[index] = reader.ReadInt32();
            outStream?.WriteLine("Header[{0}] = {1:n0} (0x{1:X})", (object) index, (object) numArray[index]);
          }
          int pos = numArray[1];
          int numEntries = numArray[3];
          this.ReadStringTable(numArray[4], reader, outStream);
          this.ReadRecordTable(pos, numEntries, reader, outStream);
          for (int index = 0; index < 4; ++index)
          {
            int num = reader.ReadInt32();
            outStream?.WriteLine("{0:n0} 0x{0:X}", (object) num);
          }
        }
        catch (IOException ex)
        {
          throw;
        }
        finally
        {
          reader.Close();
        }
      }
      catch (IOException ex)
      {
        if (!TQDebug.DebugEnabled)
          TQDebug.DebugEnabled = true;
        TQDebug.DebugWriteLine(ex.ToString());
        return false;
      }
      finally
      {
        outStream?.Close();
      }
      return true;
    }

    private void ReadRecordTable(
      int pos,
      int numEntries,
      BinaryReader reader,
      StreamWriter outStream)
    {
      this.recordInfo = new Dictionary<string, ArzFile.RecordInfo>((int) Math.Round((double) numEntries * 1.2));
      reader.BaseStream.Seek((long) pos, SeekOrigin.Begin);
      outStream?.WriteLine("RecordTable located at 0x{0:X}", (object) pos);
      for (int index = 0; index < numEntries; ++index)
      {
        ArzFile.RecordInfo recordInfo = new ArzFile.RecordInfo();
        recordInfo.Decode(reader, 24, this);
        this.recordInfo.Add(TQData.NormalizeRecordPath(recordInfo.ID), recordInfo);
        outStream?.WriteLine("{0},{1},{2}", (object) index, (object) recordInfo.ID, (object) recordInfo.RecordType);
      }
    }

    private void ReadStringTable(int pos, BinaryReader reader, StreamWriter outStream)
    {
      reader.BaseStream.Seek((long) pos, SeekOrigin.Begin);
      int length = reader.ReadInt32();
      this.strings = new string[length];
      outStream?.WriteLine("stringTable located at 0x{1:X} numstrings= {0:n0}", (object) length, (object) pos);
      for (int index = 0; index < length; ++index)
      {
        this.strings[index] = TQData.ReadCString(reader);
        outStream?.WriteLine("{0},{1}", (object) index, (object) this.strings[index]);
      }
    }

    public int Count => this.recordInfo.Count;

    public class RecordInfo
    {
      private int offset;
      private int idStringIndex = -1;
      public string ID;
      public string RecordType;

      public RecordInfo() => this.RecordType = string.Empty;

      public void Decode(BinaryReader inReader, int baseOffset, ArzFile arzFile)
      {
        this.idStringIndex = inReader.ReadInt32();
        this.RecordType = TQData.ReadCString(inReader);
        this.offset = inReader.ReadInt32() + baseOffset;
        inReader.ReadInt32();
        inReader.ReadInt32();
        inReader.ReadInt32();
        this.ID = arzFile.Getstring(this.idStringIndex);
      }

      public DBRecord Decompress(ArzFile arzFile)
      {
        byte[] buffer = this.DecompressBytes(arzFile);
        int num1 = buffer.Length / 4;
        if (buffer.Length % 4 > 0)
        {
          if (!TQDebug.DebugEnabled)
            TQDebug.DebugEnabled = true;
          TQDebug.DebugWriteLine(string.Format((IFormatProvider) CultureInfo.InvariantCulture, "Error in ARZFile - {0}", new object[1]
          {
            (object) arzFile.fileName
          }));
          TQDebug.DebugWriteLine(string.Format((IFormatProvider) CultureInfo.InvariantCulture, "Error while parsing arz record {0}, data Length = {1} which is not a multiple of 4", new object[2]
          {
            (object) this.ID,
            (object) buffer.Length
          }));
          throw new ArgumentException(string.Format((IFormatProvider) CultureInfo.InvariantCulture, "Error while parsing arz record {0}, data Length = {1} which is not a multiple of 4", new object[2]
          {
            (object) this.ID,
            (object) buffer.Length
          }));
        }
        DBRecord dbRecord = new DBRecord(this.ID, this.RecordType);
        using (BinaryReader binaryReader = new BinaryReader((Stream) new MemoryStream(buffer, false)))
        {
          int num2 = 0;
          while (num2 < num1)
          {
            short dataType = binaryReader.ReadInt16();
            short numberOfValues = binaryReader.ReadInt16();
            int index1 = binaryReader.ReadInt32();
            string variableName = arzFile.Getstring(index1);
            if (variableName == null)
            {
              if (!TQDebug.DebugEnabled)
                TQDebug.DebugEnabled = true;
              TQDebug.DebugWriteLine(string.Format((IFormatProvider) CultureInfo.InvariantCulture, "Error in ARZFile - {0}", new object[1]
              {
                (object) arzFile.fileName
              }));
              TQDebug.DebugWriteLine(string.Format((IFormatProvider) CultureInfo.InvariantCulture, "Error while parsing arz record {0}, variable is NULL", new object[1]
              {
                (object) this.ID
              }));
              throw new ArgumentNullException(string.Format((IFormatProvider) CultureInfo.InvariantCulture, "Error while parsing arz record {0}, variable is NULL", new object[1]
              {
                (object) this.ID
              }));
            }
            if (dataType < (short) 0 || dataType > (short) 3)
            {
              if (!TQDebug.DebugEnabled)
                TQDebug.DebugEnabled = true;
              TQDebug.DebugWriteLine(string.Format((IFormatProvider) CultureInfo.InvariantCulture, "Error in ARZFile - {0}", new object[1]
              {
                (object) arzFile.fileName
              }));
              TQDebug.DebugWriteLine(string.Format((IFormatProvider) CultureInfo.InvariantCulture, "Error while parsing arz record {0}, variable {2}, bad dataType {3}", new object[3]
              {
                (object) this.ID,
                (object) variableName,
                (object) dataType
              }));
              throw new ArgumentOutOfRangeException(string.Format((IFormatProvider) CultureInfo.InvariantCulture, "Error while parsing arz record {0}, variable {2}, bad dataType {3}", new object[3]
              {
                (object) this.ID,
                (object) variableName,
                (object) dataType
              }));
            }
            Variable variable = new Variable(variableName, (VariableDataType) dataType, (int) numberOfValues);
            if (numberOfValues < (short) 1)
            {
              if (!TQDebug.DebugEnabled)
                TQDebug.DebugEnabled = true;
              TQDebug.DebugWriteLine(string.Format((IFormatProvider) CultureInfo.InvariantCulture, "Error in ARZFile - {0}", new object[1]
              {
                (object) arzFile.fileName
              }));
              TQDebug.DebugWriteLine(string.Format((IFormatProvider) CultureInfo.InvariantCulture, "Error while parsing arz record {0}, variable {1}, bad valCount {2}", new object[3]
              {
                (object) this.ID,
                (object) variableName,
                (object) numberOfValues
              }));
              throw new ArgumentException(string.Format((IFormatProvider) CultureInfo.InvariantCulture, "Error while parsing arz record {0}, variable {1}, bad valCount {2}", new object[3]
              {
                (object) this.ID,
                (object) variableName,
                (object) numberOfValues
              }));
            }
            num2 += 2 + (int) numberOfValues;
            for (int index2 = 0; index2 < (int) numberOfValues; ++index2)
            {
              switch (variable.DataType)
              {
                case VariableDataType.Integer:
                case VariableDataType.Boolean:
                  int num3 = binaryReader.ReadInt32();
                  variable[index2] = (object) num3;
                  break;
                case VariableDataType.Float:
                  float num4 = binaryReader.ReadSingle();
                  variable[index2] = (object) num4;
                  break;
                case VariableDataType.StringVar:
                  int index3 = binaryReader.ReadInt32();
                  string str1 = arzFile.Getstring(index3);
                  string str2 = str1 != null ? str1.Trim() : string.Empty;
                  variable[index2] = (object) str2;
                  break;
                default:
                  int num5 = binaryReader.ReadInt32();
                  variable[index2] = (object) num5;
                  break;
              }
            }
            dbRecord.Set(variable);
          }
        }
        return dbRecord;
      }

      private byte[] DecompressBytes(ArzFile arzFile)
      {
        if (arzFile == null)
          throw new ArgumentNullException(nameof (arzFile), "arzFile is null.");
        using (MemoryStream memoryStream1 = new MemoryStream(arzFile.rawData, false))
        {
          memoryStream1.Seek((long) this.offset, SeekOrigin.Begin);
          memoryStream1.ReadByte();
          memoryStream1.ReadByte();
          using (DeflateStream deflateStream = new DeflateStream((Stream) memoryStream1, CompressionMode.Decompress))
          {
            using (MemoryStream memoryStream2 = new MemoryStream())
            {
              byte[] buffer = new byte[1024];
              int count;
              while ((count = deflateStream.Read(buffer, 0, 1024)) > 0)
                memoryStream2.Write(buffer, 0, count);
              return memoryStream2.ToArray();
            }
          }
        }
      }
    }
  }
}
