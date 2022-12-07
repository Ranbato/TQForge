// Decompiled with JetBrains decompiler
// Type: TQ_weaponsmith.Sack
// Assembly: TQ_Blacksmith, Version=0.1.4.1, Culture=neutral, PublicKeyToken=null
// MVID: 1CC9040D-95C6-4C54-B248-9D80684B2CA8
// Assembly location: D:\Games\GOG Galaxy\Games\Titan Quest - Anniversary Edition\Downloads\Blacksmith\TQ_Blacksmith.exe

using System;
using System.Collections.Generic;
using System.Drawing;
using System.IO;

namespace TQ_weaponsmith
{
  public class Sack
  {
    private static Point[] equipmentLocationOffsets = new Point[12]
    {
      new Point(4, 0),
      new Point(4, 3),
      new Point(4, 5),
      new Point(4, 9),
      new Point(7, 6),
      new Point(4, 12),
      new Point(5, 12),
      new Point(Item.WeaponSlotIndicator, 0),
      new Point(Item.WeaponSlotIndicator, 1),
      new Point(Item.WeaponSlotIndicator, 2),
      new Point(Item.WeaponSlotIndicator, 3),
      new Point(1, 6)
    };
    private static Size[] equipmentLocationSizes = new Size[12]
    {
      new Size(2, 2),
      new Size(1, 1),
      new Size(2, 3),
      new Size(2, 2),
      new Size(2, 2),
      new Size(1, 1),
      new Size(1, 1),
      new Size(2, 5),
      new Size(2, 5),
      new Size(2, 5),
      new Size(2, 5),
      new Size(2, 2)
    };
    private static Point[] weaponLocationOffsets = new Point[4]
    {
      new Point(1, 0),
      new Point(7, 0),
      new Point(1, 9),
      new Point(7, 9)
    };
    private static Size weaponLocationSize = new Size(2, 5);
    private int beginBlockCrap;
    private int endBlockCrap;
    private int tempBool;
    private int size;
    private SackType sackType;
    private List<Item> items = new List<Item>();
    private bool isModified;
    private bool isImmortalThrone;
    private int slots;

    public Sack() => this.isModified = false;

    public void AddItem(Item item)
    {
      this.items.Add(item);
      this.IsModified = true;
    }

    public int CountTQItems()
    {
      int num = 0;
      foreach (Item obj in this.items)
      {
        if (obj.DoesStack)
          num += obj.StackSize;
        else
          ++num;
      }
      return num;
    }

    public Sack Duplicate()
    {
      Sack sack = new Sack();
      foreach (Item obj in this.items)
        sack.AddItem(obj.Clone());
      return sack;
    }

    public void EmptySack()
    {
      this.items.Clear();
      this.IsModified = true;
    }

    public void Encode(BinaryWriter writer)
    {
      if (this.sackType != SackType.Equipment)
      {
        TQData.writeCString(writer, "begin_block");
        writer.Write(this.beginBlockCrap);
        TQData.writeCString(writer, "tempBool");
        writer.Write(this.tempBool);
        TQData.writeCString(writer, "size");
        writer.Write(this.CountTQItems());
      }
      int num1 = -1;
      foreach (Item obj in this.items)
      {
        ++num1;
        obj.sackType = this.sackType;
        if (this.sackType == SackType.Equipment && (num1 == 7 || num1 == 9))
        {
          TQData.writeCString(writer, "begin_block");
          writer.Write(this.beginBlockCrap);
          TQData.writeCString(writer, "alternate");
          int num2 = num1 != 9 ? 0 : 1;
          writer.Write(num2);
        }
        obj.Encode(writer);
        if (this.sackType == SackType.Equipment)
        {
          TQData.writeCString(writer, "itemAttached");
          int num3 = string.IsNullOrEmpty(obj.BaseItemId) || num1 == 9 || num1 == 10 ? 0 : 1;
          writer.Write(num3);
        }
        if (this.sackType == SackType.Equipment && (num1 == 8 || num1 == 10))
        {
          TQData.writeCString(writer, "end_block");
          writer.Write(this.endBlockCrap);
        }
      }
      TQData.writeCString(writer, "end_block");
      writer.Write(this.endBlockCrap);
    }

    public static Point GetEquipmentLocationOffset(int equipmentSlot) => equipmentSlot < 0 || equipmentSlot > Sack.equipmentLocationOffsets.Length ? Point.Empty : Sack.equipmentLocationOffsets[equipmentSlot];

    public static Size GetEquipmentLocationSize(int equipmentSlot) => equipmentSlot < 0 || equipmentSlot > Sack.equipmentLocationSizes.Length ? Size.Empty : Sack.equipmentLocationSizes[equipmentSlot];

    public Item GetItem(int index) => this.items[index];

    public static Point GetWeaponLocationOffset(int weaponSlot) => weaponSlot < 0 || weaponSlot > Sack.NumberOfWeaponSlots ? Point.Empty : Sack.weaponLocationOffsets[weaponSlot];

    public int IndexOfItem(Item item) => this.items.IndexOf(item);

    public void InsertItem(int index, Item item)
    {
      this.items.Insert(index, item);
      this.IsModified = true;
    }

    public static bool IsWeaponSlot(int equipmentSlot) => equipmentSlot >= 0 && equipmentSlot <= Sack.equipmentLocationOffsets.Length && Sack.equipmentLocationOffsets[equipmentSlot].X == Item.WeaponSlotIndicator;

    public void Parse(BinaryReader reader)
    {
      try
      {
        this.isModified = false;
        TQData.validateNextString("begin_block", reader);
        this.beginBlockCrap = reader.ReadInt32();
        TQData.validateNextString("tempBool", reader);
        this.tempBool = reader.ReadInt32();
        TQData.validateNextString("size", reader);
        this.size = reader.ReadInt32();
        this.items = new List<Item>(this.size);
        Item obj1 = (Item) null;
        for (int index = 0; index < this.size; ++index)
        {
          Item obj2 = new Item()
          {
            sackType = this.sackType
          };
          obj2.Parse(reader);
          if (obj1 != null && obj2.DoesStack && obj2.PositionX == -1 && obj2.PositionY == -1)
          {
            ++obj1.StackSize;
          }
          else
          {
            obj1 = obj2;
            this.items.Add(obj2);
          }
        }
        TQData.validateNextString("end_block", reader);
        this.endBlockCrap = reader.ReadInt32();
      }
      catch (ArgumentException ex)
      {
        throw;
      }
    }

    public void RemoveAtItem(int index)
    {
      this.items.RemoveAt(index);
      this.IsModified = true;
    }

    public void RemoveItem(Item item)
    {
      this.items.Remove(item);
      this.IsModified = true;
    }

    public IEnumerator<Item> GetEnumerator()
    {
      for (int i = 0; i < this.Count; ++i)
        yield return this.GetItem(i);
    }

    public static Size WeaponLocationSize => Sack.weaponLocationSize;

    public static int NumberOfWeaponSlots => Sack.weaponLocationOffsets.Length;

    public bool IsModified
    {
      get => this.isModified;
      set => this.isModified = value;
    }

    public SackType SackType
    {
      get => this.sackType;
      set => this.sackType = value;
    }

    public bool IsImmortalThrone
    {
      get => this.isImmortalThrone;
      set => this.isImmortalThrone = value;
    }

    public int NumberOfSlots => this.slots;

    public int Count => this.items.Count;

    public bool IsEmpty => this.items.Count == 0;
  }
}
