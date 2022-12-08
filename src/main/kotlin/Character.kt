private val  beginBlockPattern = byteArrayOf(

  11,
  0,
  0,
  0,
  98,
  101,
  103,
  105,
  110,
  95,
  98,
  108,
  111,
  99,
  107
)
private val endBlockPattern = byteArrayOf(

  9,
  0,
  0,
  0,
  101,
  110,
  100,
  95,
  98,
  108,
  111,
  99,
  107
)
private val lateinit caracterName:String
private val lateinit caracterFile:String
private var rawData:ByteArray
private var itemBlockStart:Int
private var itemBlockEnd:Int
private var equipmentBlockStart:Int
private var equipmentBlockEnd:Int
private var numberOfSacks:Int
private var currentlyFocusedSackNumber:Int
private var currentlySelectedSackNumber:Int
private var equipmentCtrlIOStreamVersion:Int
private val sacks:List<Sack>;
private var equipmentSack:Sack;
public var isModified = false

public Character( name:String, filePath:String):Unit
{
  caracterName = name;
  caracterFile = filePath;
}

public fun loadFile():Unit
    {
      using (FileStream input = new FileStream(this.caracterFile, FileMode.Open, FileAccess.Read))
      {
        using (InputStream InputStream = new InputStream((Stream) input))
          this.rawData = InputStream.Reads((int) input.Length);
      }
      try
      {
        this.parseRawData();
      }
      catch (ArgumentException ex)
      {
        throw;
      }
    }

    private void parseRawData()
    {
      using (MemoryStream input = new MemoryStream(this.rawData, false))
      {
        using (InputStream reader = new InputStream((Stream) input))
        {
          int num = 0;
          int start = 0;
          int offset1 = 0;
          int offset2 = 0;
          bool flag1 = false;
          bool flag2 = false;
          int nextBlockDelim;
          while ((!flag1 || !flag2) && (nextBlockDelim = this.FindNextBlockDelim(start)) != -1)
          {
            if ((int) this.rawData[nextBlockDelim] == (int) Character.beginBlockPattern[0])
            {
              ++num;
              int offset3 = nextBlockDelim + Character.beginBlockPattern.Length + 4;
              input.Seek((long) offset3, SeekOrigin.Begin);
              string upperInvariant = TQData.readCString(reader).ToUpperInvariant();
              start = (int) input.Position;
              string str;
              if (upperInvariant.Equals("BEGIN_BLOCK"))
              {
                str = "(NONAME)";
                start -= Character.beginBlockPattern.Length;
              }
              else if (upperInvariant.Equals("END_BLOCK"))
              {
                str = "(NONAME)";
                start -= Character.endBlockPattern.Length;
              }
              else if (upperInvariant.Equals("ITEMPOSITIONSSAVEDASGRIDCOORDS"))
              {
                start += 4;
                offset1 = start;
                flag1 = true;
              }
              else if (upperInvariant.Equals("USEALTERNATE"))
              {
                start += 4;
                offset2 = start;
                flag2 = true;
              }
            }
            else
            {
              --num;
              start = nextBlockDelim + Character.endBlockPattern.Length;
            }
          }
          if (flag1)
          {
            try
            {
              this.parseItemBlock(offset1, reader);
            }
            catch (ArgumentException ex)
            {
              Logger.Debug("Error parsing player file Item Block - " + this.caracterName);
              Logger.Debug(ex.ToString());
              throw new ArgumentException("Error parsing player file Item Block- " + this.caracterFile, (Exception) ex);
            }
          }
          if (true)
            return;
          try
          {
            this.parseEquipmentBlock(offset2, reader);
          }
          catch (ArgumentException ex)
          {
            Logger.Debug("Error parsing player file Item Block - " + this.caracterName);
            Logger.Debug(ex.ToString());
            throw new ArgumentException("Error parsing player file Item Block- " + this.caracterFile, (Exception) ex);
          }
        }
      }
    }

    private int FindNextBlockDelim(int start)
    {
      int index1 = 0;
      int index2 = 0;
      for (int index3 = start; index3 < this.rawData.Length; ++index3)
      {
        if ((int) this.rawData[index3] == (int) Character.beginBlockPattern[index1])
        {
          ++index1;
          if (index1 == Character.beginBlockPattern.Length)
            return index3 + 1 - index1;
        }
        else if (index1 > 0)
        {
          index1 = 0;
          if ((int) this.rawData[index3] == (int) Character.beginBlockPattern[index1])
            ++index1;
        }
        if ((int) this.rawData[index3] == (int) Character.endBlockPattern[index2])
        {
          ++index2;
          if (index2 == Character.endBlockPattern.Length)
            return index3 + 1 - index2;
        }
        else if (index2 > 0)
        {
          index2 = 0;
          if ((int) this.rawData[index3] == (int) Character.endBlockPattern[index2])
            ++index2;
        }
      }
      return -1;
    }

    private void parseEquipmentBlock(int offset, InputStream reader)
    {
      try
      {
        this.equipmentBlockStart = offset;
        reader.BaseStream.Seek((long) offset, SeekOrigin.Begin);
        TQData.validateNextString("equipmentCtrlIOStreamVersion", reader);
        this.equipmentCtrlIOStreamVersion = reader.ReadInt32();
        this.equipmentSack = new Sack();
        this.equipmentSack.SackType = SackType.Equipment;
        this.equipmentSack.IsImmortalThrone = true;
        this.equipmentSack.Parse(reader);
        this.equipmentBlockEnd = (int) reader.BaseStream.Position;
      }
      catch (ArgumentException ex)
      {
        throw;
      }
    }

    private void parseItemBlock(int offset, InputStream reader)
    {
      try
      {
        this.itemBlockStart = offset;
        reader.BaseStream.Seek((long) offset, SeekOrigin.Begin);
        TQData.validateNextString("numberOfSacks", reader);
        this.numberOfSacks = reader.ReadInt32();
        TQData.validateNextString("currentlyFocusedSackNumber", reader);
        this.currentlyFocusedSackNumber = reader.ReadInt32();
        TQData.validateNextString("currentlySelectedSackNumber", reader);
        this.currentlySelectedSackNumber = reader.ReadInt32();
        this.sacks = new Sack[this.numberOfSacks];
        for (int index = 0; index < this.numberOfSacks; ++index)
        {
          this.sacks[index] = new Sack();
          this.sacks[index].SackType = SackType.Sack;
          this.sacks[index].IsImmortalThrone = true;
          this.sacks[index].Parse(reader);
        }
        this.itemBlockEnd = (int) reader.BaseStream.Position;
      }
      catch (ArgumentException ex)
      {
        throw;
      }
    }

    public Sack CreateEmptySacks() => new Sack()
    {
      IsModified = false
    };

    public void emptyMainSack()
    {
      this.GetMainSack.EmptySack();
      this.isModified = true;
    }

    public void Save(string fileName, bool backup = false)
    {
      string str1 = "";
      if (backup)
      {
        string str2 = Path.Combine(Path.GetDirectoryName(fileName), "BlaksmithBackup_Player.chr");
        if (!File.Exists(str2))
        {
          Logger.Log("Save file backup created: " + str2);
          File.Copy(fileName, str2);
        }
        str1 = " Original file backed to BlaksmithBackup_Player.chr";
      }
      [] buffer = this.Encode();
      using (FileStream fileStream = new FileStream(fileName, FileMode.Create, FileAccess.Write))
        fileStream.Write(buffer, 0, buffer.Length);
      this.isModified = false;
      this.GetMainSack.IsModified = false;
      Logger.Log("Character data saved to " + fileName + str1);
    }

    public [] Encode()
    {
      [] sourceArray = this.EncodeItemData();
      [] destinationArray = new [this.itemBlockStart + sourceArray.Length + (this.rawData.Length - this.itemBlockEnd)];
      Array.Copy((Array) this.rawData, 0, (Array) destinationArray, 0, this.itemBlockStart);
      Array.Copy((Array) sourceArray, 0, (Array) destinationArray, this.itemBlockStart, sourceArray.Length);
      Array.Copy((Array) this.rawData, this.itemBlockEnd, (Array) destinationArray, this.itemBlockStart + sourceArray.Length, this.rawData.Length - this.itemBlockEnd);
      return destinationArray;
    }

    private [] EncodeEquipmentData()
    {
      int length;
      [] buffer;
      using (MemoryStream output = new MemoryStream(2048))
      {
        using (OutputStream writer = new OutputStream((Stream) output))
        {
          TQData.writeCString(writer, "equipmentCtrlIOStreamVersion");
          writer.Write(this.equipmentCtrlIOStreamVersion);
          this.equipmentSack.Encode(writer);
          length = (int) output.Length;
        }
        buffer = output.GetBuffer();
      }
      if (length == buffer.Length)
        return buffer;
      [] destinationArray = new [length];
      Array.Copy((Array) buffer, (Array) destinationArray, length);
      return destinationArray;
    }

    private [] EncodeItemData()
    {
      int length;
      [] buffer;
      using (MemoryStream output = new MemoryStream(2048))
      {
        using (OutputStream writer = new OutputStream((Stream) output))
        {
          TQData.writeCString(writer, "numberOfSacks");
          writer.Write(this.numberOfSacks);
          TQData.writeCString(writer, "currentlyFocusedSackNumber");
          writer.Write(this.currentlyFocusedSackNumber);
          TQData.writeCString(writer, "currentlySelectedSackNumber");
          writer.Write(this.currentlySelectedSackNumber);
          for (int index = 0; index < this.numberOfSacks; ++index)
            this.sacks[index].Encode(writer);
          length = (int) output.Length;
        }
        buffer = output.GetBuffer();
      }
      if (length == buffer.Length)
        return buffer;
      [] destinationArray = new [length];
      Array.Copy((Array) buffer, (Array) destinationArray, length);
      return destinationArray;
    }

    public string CharacterName
    {
      get => this.caracterName;
      set
      {
      }
    }

    public string FilePath
    {
      get => this.caracterFile;
      set
      {
      }
    }

    public Sack GetMainSack => this.sacks != null ? this.sacks[0] : (Sack) null;

    public override bool Equals(object obj) => obj != null && obj is Character other && this.Equals(other);

    public override int GetHashCode() => this.caracterName.GetHashCode();

    public bool Equals(Character other) => other != null && this.caracterName.Equals(other.CharacterName);
  }
}
