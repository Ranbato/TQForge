// Decompiled with JetBrains decompiler
// Type: TQ_weaponsmith.HelpForm
// Assembly: TQ_Blacksmith, Version=0.1.4.1, Culture=neutral, PublicKeyToken=null
// MVID: 1CC9040D-95C6-4C54-B248-9D80684B2CA8
// Assembly location: D:\Games\GOG Galaxy\Games\Titan Quest - Anniversary Edition\Downloads\Blacksmith\TQ_Blacksmith.exe

using System.ComponentModel;
using System.Drawing;
using System.Windows.Forms;

namespace TQ_weaponsmith
{
  public class HelpForm : Form
  {
    private IContainer components;
    public WebBrowser helpBrowser;

    public HelpForm() => this.InitializeComponent();

    protected override void Dispose(bool disposing)
    {
      if (disposing && this.components != null)
        this.components.Dispose();
      base.Dispose(disposing);
    }

    private void InitializeComponent()
    {
      this.helpBrowser = new WebBrowser();
      this.SuspendLayout();
      this.helpBrowser.Dock = DockStyle.Fill;
      this.helpBrowser.Location = new Point(0, 0);
      this.helpBrowser.MinimumSize = new Size(20, 20);
      this.helpBrowser.Name = "helpBrowser";
      this.helpBrowser.Size = new Size(542, 407);
      this.helpBrowser.TabIndex = 0;
      this.AutoScaleDimensions = new SizeF(6f, 13f);
      this.AutoScaleMode = AutoScaleMode.Font;
      this.ClientSize = new Size(542, 407);
      this.Controls.Add((Control) this.helpBrowser);
      this.Name = nameof (HelpForm);
      this.StartPosition = FormStartPosition.CenterScreen;
      this.Text = "help";
      this.ResumeLayout(false);
    }
  }
}
