import org.jetbrains.skia.Point
import utils.LittleEndianDataInputStream
import java.io.InputStream
import java.io.DataInputStream
import java.io.OutputStream
import java.nio.ByteBuffer

public class Sack
  {
    private val equipmentLocationOffsets =  arrayOf<Pair<Int,Int>>(
    
      Pair(4, 0),
      Pair(4, 3),
      Pair(4, 5),
       Pair(4, 9),
       Pair(7, 6),
       Pair(4, 12),
       Pair(5, 12),
       Pair(Item.WeaponSlotIndicator, 0),
       Pair(Item.WeaponSlotIndicator, 1),
       Pair(Item.WeaponSlotIndicator, 2),
       Pair(Item.WeaponSlotIndicator, 3),
       Pair(1, 6)
    )
    private val  equipmentLocationSizes = arrayOf<Pair<Int,Int>>(

      Pair(2, 2),
      Pair(1, 1),
      Pair(2, 3),
      Pair(2, 2),
      Pair(2, 2),
      Pair(1, 1),
      Pair(1, 1),
      Pair(2, 5),
      Pair(2, 5),
      Pair(2, 5),
      Pair(2, 5),
      Pair(2, 2)
      )
    private val weaponLocationOffsets = arrayOf<Pair<Int,Int>>(

       Pair(1, 0),
       Pair(7, 0),
       Pair(1, 9),
       Pair(7, 9)
      )
    private val weaponLocationSize = Pair(2, 5);
    private var beginBlockCrap:Int =0
    private var endBlockCrap:Int =0
    private var tempBool:Int =0
    private var size:Int =0
     var  sackType:SackType = SackType.Sack
    private val items:MutableList<Item> = mutableListOf()
     var isModified = false
     var IsImmortalThrone = true
    private var slots:Int =0


//
//    public void AddItem(Item item)
//    {
//      this.items.Add(item);
//      this.IsModified = true;
//    }
//
    public fun CountTQItems():Int
    {
      var num = 0
      this.items.forEach { obj ->

        if (obj.DoesStack)
          num += obj.StackSize;
        else
          num++
      }
      return num
    }

//    public Sack Duplicate()
//    {
//      Sack sack = new Sack();
//      foreach (Item obj in this.items)
//        sack.AddItem(obj.Clone());
//      return sack;
//    }
//
    public fun EmptySack():Unit
    {
      items.clear()
      isModified = true
    }

    public fun Encode( writer: OutputStream)
    {
      if (this.sackType != SackType.Equipment)
      {
        TQData.writeCString(writer, "begin_block");
        writer.write(this.beginBlockCrap);
        TQData.writeCString(writer, "tempBool");
        writer.write(this.tempBool);
        TQData.writeCString(writer, "size");
        writer.write(this.CountTQItems());
      }
      var num1 = -1;
      items.forEach { obj->

        ++num1;
        obj.sackType = this.sackType;
        if (this.sackType == SackType.Equipment && (num1 == 7 || num1 == 9))
        {
          TQData.writeCString(writer, "begin_block");
          writer.write(this.beginBlockCrap);
          TQData.writeCString(writer, "alternate");
          val num2 = if(num1 != 9)  0 else 1
          writer.write(num2)
        }
        obj.Encode(writer);
        if (this.sackType == SackType.Equipment)
        {
          TQData.writeCString(writer, "itemAttached");
          val num3 = if(obj.BaseItemId.isNullOrEmpty() || num1 == 9 || num1 == 10)  0 else 1
          writer.write(num3);
        }
        if (this.sackType == SackType.Equipment && (num1 == 8 || num1 == 10))
        {
          TQData.writeCString(writer, "end_block");
          writer.write(this.endBlockCrap);
        }
      }
      TQData.writeCString(writer, "end_block");
      writer.write(this.endBlockCrap);
    }

//    public static Point GetEquipmentLocationOffset(int equipmentSlot) => equipmentSlot < 0 || equipmentSlot > Sack.equipmentLocationOffsets.Length ? Point.Empty : Sack.equipmentLocationOffsets[equipmentSlot];
//
//    public static Size GetEquipmentLocationSize(int equipmentSlot) => equipmentSlot < 0 || equipmentSlot > Sack.equipmentLocationSizes.Length ? Size.Empty : Sack.equipmentLocationSizes[equipmentSlot];
//
//    public Item GetItem(int index) => this.items[index];
//
//    public static Point GetWeaponLocationOffset(int weaponSlot) => weaponSlot < 0 || weaponSlot > Sack.NumberOfWeaponSlots ? Point.Empty : Sack.weaponLocationOffsets[weaponSlot];
//
//    public int IndexOfItem(Item item) => this.items.IndexOf(item);
//
//    public void InsertItem(int index, Item item)
//    {
//      this.items.Insert(index, item);
//      this.IsModified = true;
//    }
//
//    public static bool IsWeaponSlot(int equipmentSlot) => equipmentSlot >= 0 && equipmentSlot <= Sack.equipmentLocationOffsets.Length && Sack.equipmentLocationOffsets[equipmentSlot].X == Item.WeaponSlotIndicator;
//
    public fun Parse( reader: ByteBuffer):Unit
    {
        this.isModified = false
        TQData.validateNextString(reader,"begin_block")
        this.beginBlockCrap = reader.getInt()
        TQData.validateNextString(reader,"tempBool")
        this.tempBool = reader.getInt()
        TQData.validateNextString(reader,"size")
        this.size = reader.getInt()
        var obj1:Item? = null
        for ( index in 0 until this.size)
        {
          val obj2 = Item()
          obj2.sackType = this.sackType

          obj2.Parse(reader);
          if (obj1 != null && obj2.DoesStack && obj2.PositionX == -1 && obj2.PositionY == -1)
          {
            ++obj1.StackSize;
          }
          else
          {
            obj1 = obj2;
            this.items.add(obj2);
          }
        }
        TQData.validateNextString(reader,"end_block")
        this.endBlockCrap = reader.getInt();

    }
//
//    public void RemoveAtItem(int index)
//    {
//      this.items.RemoveAt(index);
//      this.IsModified = true;
//    }
//
//    public void RemoveItem(Item item)
//    {
//      this.items.Remove(item);
//      this.IsModified = true;
//    }
//
//    public IEnumerator<Item> GetEnumerator()
//    {
//      for (int i = 0; i < this.Count; ++i)
//        yield return this.GetItem(i);
//    }
//
//    public static Size WeaponLocationSize => Sack.weaponLocationSize;
//
//    public static int NumberOfWeaponSlots => Sack.weaponLocationOffsets.Length;
//
//    public bool IsModified
//    {
//      get => this.isModified;
//      set => this.isModified = value;
//    }
//
//    public SackType SackType
//    {
//      get => this.sackType;
//      set => this.sackType = value;
//    }
//
//    public bool IsImmortalThrone
//    {
//      get => this.isImmortalThrone;
//      set => this.isImmortalThrone = value;
//    }
//
//    public int NumberOfSlots => this.slots;
//
//    public int Count => this.items.Count;
//
//    public bool IsEmpty => this.items.Count == 0;
  }

