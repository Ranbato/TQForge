// Decompiled with JetBrains decompiler
// Type: TQ_weaponsmith.BitmapCode
// Assembly: TQ_Blacksmith, Version=0.1.4.1, Culture=neutral, PublicKeyToken=null
// MVID: 1CC9040D-95C6-4C54-B248-9D80684B2CA8
// Assembly location: D:\Games\GOG Galaxy\Games\Titan Quest - Anniversary Edition\Downloads\Blacksmith\TQ_Blacksmith.exe

using System;
using System.Drawing;
using System.Drawing.Imaging;
using System.IO;
using System.Security.Permissions;
using Tao.DevIl;

namespace TQ_weaponsmith
{
  public static class BitmapCode
  {
    [PermissionSet(SecurityAction.Demand, Name = "FullTrust")]
    private static Bitmap DDSDataToBMP(byte[] ddsData)
    {
      int Images;
      Il.ilGenImages(1, out Images);
      Il.ilBindImage(Images);
      Il.ilLoadL(1079, ddsData, ddsData.Length);
      int integer1 = Il.ilGetInteger(3556);
      int integer2 = Il.ilGetInteger(3557);
      Rectangle rect = new Rectangle(0, 0, integer1, integer2);
      Il.ilConvertImage(32993, 5121);
      Bitmap bmp = new Bitmap(integer1, integer2);
      BitmapData bitmapdata = bmp.LockBits(rect, ImageLockMode.WriteOnly, PixelFormat.Format32bppArgb);
      switch (Il.ilCopyPixels(0, 0, 0, Il.ilGetInteger(3556), Il.ilGetInteger(3557), 1, 32993, 5121, bitmapdata.Scan0))
      {
        case 1286:
        case 1296:
          throw new InvalidDataException("Cannot Convert Bitmap.");
        default:
          Il.ilDeleteImages(1, ref Images);
          bmp.UnlockBits(bitmapdata);
          return bmp;
      }
    }

    public static Bitmap LoadFromDdsFile(string fileName)
    {
      byte[] numArray;
      using (FileStream fileStream = new FileStream(fileName, FileMode.Open, FileAccess.Read))
      {
        int offset = 0;
        numArray = new byte[Convert.ToInt32(fileStream.Length)];
        int num;
        while ((num = fileStream.Read(numArray, offset, numArray.Length - offset)) > 0)
          offset += num;
      }
      if (numArray.Length >= 128)
      {
        uint uint32 = BitConverter.ToUInt32(numArray, 0);
        switch (uint32)
        {
          case 542327876:
          case 1381188676:
            if (uint32 == 1381188676U)
              numArray[3] = (byte) 32;
            if (BitConverter.ToInt32(numArray, 4) == 124 && BitConverter.ToInt32(numArray, 76) == 32 && BitConverter.ToInt32(numArray, 88) == 32)
            {
              numArray[80] = (byte) ((uint) numArray[80] | 1U);
              break;
            }
            break;
        }
      }
      return BitmapCode.LoadFromDdsMemory(numArray, 0, numArray.Length);
    }

    public static Bitmap LoadFromDdsMemory(byte[] data, int offset, int count) => BitmapCode.LoadFromMemory(data, offset, count);

    private static Bitmap LoadFromMemory(byte[] data, int offset, int count)
    {
      if (data == null)
        throw new ArgumentNullException(nameof (data));
      if (offset < 0 || offset > data.Length)
        throw new ArgumentOutOfRangeException(nameof (offset));
      if (count < 0 || data.Length - offset < count)
        throw new ArgumentOutOfRangeException(nameof (count));
      byte[] numArray = new byte[count];
      Buffer.BlockCopy((Array) data, offset, (Array) numArray, 0, count);
      return BitmapCode.DDSDataToBMP(numArray) ?? (Bitmap) null;
    }

    [EnvironmentPermission(SecurityAction.LinkDemand, Unrestricted = true)]
    public static Bitmap LoadFromTexFile(string fileName)
    {
      byte[] numArray;
      using (FileStream fileStream = new FileStream(fileName, FileMode.Open, FileAccess.Read))
      {
        int offset = 0;
        numArray = new byte[Convert.ToInt32(fileStream.Length)];
        int num;
        while ((num = fileStream.Read(numArray, offset, numArray.Length - offset)) > 0)
          offset += num;
      }
      return BitmapCode.LoadFromTexMemory(numArray, 0, numArray.Length);
    }

    public static Bitmap LoadFromTexMemory(byte[] data, int offset, int count)
    {
      if (data == null)
        throw new ArgumentNullException(nameof (data));
      if (offset < 0 || offset > data.Length)
        throw new ArgumentOutOfRangeException(nameof (offset));
      if (count < 0 || data.Length - offset < count)
        throw new ArgumentOutOfRangeException(nameof (count));
      if (data.Length < 12)
        throw new InvalidDataException("TEX is not long enough to be valid.");
      if (BitConverter.ToUInt32(data, offset) != 22562132U)
        Logger.Log("Invalid TEX magic");
      int int32_1 = BitConverter.ToInt32(data, offset + 4);
      if (int32_1 < 0 || int32_1 > count - offset)
        throw new InvalidDataException("TEX texture offset is invalid.");
      int int32_2 = BitConverter.ToInt32(data, offset + 8);
      if (int32_2 < 0 || int32_2 > count - offset - int32_1)
        throw new InvalidDataException("TEX texture length is invalid.");
      if (int32_2 < 4)
        throw new InvalidDataException("Cannot read TEX texture image magic.");
      int num = offset + int32_1 + 12;
      switch (BitConverter.ToUInt32(data, num))
      {
        case 542327876:
        case 1381188676:
          if (int32_2 >= 128)
          {
            if (BitConverter.ToInt32(data, num + 4) != 124 || BitConverter.ToInt32(data, num + 76) != 32)
              throw new InvalidDataException("Invalid Header format.");
            byte[] numArray = new byte[int32_2];
            Buffer.BlockCopy((Array) data, num, (Array) numArray, 0, int32_2);
            numArray[3] = (byte) 32;
            int int32_3 = BitConverter.ToInt32(numArray, 88);
            if (int32_3 >= 24)
            {
              numArray[92] = (byte) 0;
              numArray[93] = (byte) 0;
              numArray[94] = byte.MaxValue;
              numArray[95] = (byte) 0;
              numArray[96] = (byte) 0;
              numArray[97] = byte.MaxValue;
              numArray[98] = (byte) 0;
              numArray[99] = (byte) 0;
              numArray[100] = byte.MaxValue;
              numArray[101] = (byte) 0;
              numArray[102] = (byte) 0;
              numArray[103] = (byte) 0;
              if (int32_3 == 32)
              {
                numArray[80] = (byte) ((uint) numArray[80] | 1U);
                numArray[104] = (byte) 0;
                numArray[105] = (byte) 0;
                numArray[106] = (byte) 0;
                numArray[107] = byte.MaxValue;
              }
            }
            numArray[109] = (byte) ((uint) numArray[109] | 16U);
            return BitmapCode.LoadFromDdsMemory(numArray, 0, numArray.Length);
          }
          break;
      }
      throw new InvalidDataException("Unknown texture format.");
    }
  }
}
