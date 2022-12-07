// Decompiled with JetBrains decompiler
// Type: TQ_weaponsmith.ScalingLabel
// Assembly: TQ_Blacksmith, Version=0.1.4.1, Culture=neutral, PublicKeyToken=null
// MVID: 1CC9040D-95C6-4C54-B248-9D80684B2CA8
// Assembly location: D:\Games\GOG Galaxy\Games\Titan Quest - Anniversary Edition\Downloads\Blacksmith\TQ_Blacksmith.exe

using System.Drawing;
using System.Windows.Forms;

namespace TQ_weaponsmith
{
  public class ScalingLabel : Label
  {
    public void Revert(Point location, Size size) => this.Revert(location, size, SystemColors.ControlText);

    public void Revert(Point location, Size size, Color textColor)
    {
      this.Font = Form1.RevertFont;
      this.Location = location;
      this.Size = size;
      this.ForeColor = textColor;
    }

    protected override void ScaleControl(SizeF factor, BoundsSpecified specified)
    {
      this.Font = new Font(this.Font.Name, this.Font.SizeInPoints * factor.Height, this.Font.Style);
      base.ScaleControl(factor, specified);
    }
  }
}
