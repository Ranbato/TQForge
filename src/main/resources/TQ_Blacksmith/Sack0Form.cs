// Decompiled with JetBrains decompiler
// Type: TQ_weaponsmith.Sack0Form
// Assembly: TQ_Blacksmith, Version=0.1.4.1, Culture=neutral, PublicKeyToken=null
// MVID: 1CC9040D-95C6-4C54-B248-9D80684B2CA8
// Assembly location: D:\Games\GOG Galaxy\Games\Titan Quest - Anniversary Edition\Downloads\Blacksmith\TQ_Blacksmith.exe

using System;
using System.ComponentModel;
using System.Drawing;
using System.Windows.Forms;

namespace TQ_weaponsmith
{
  public class Sack0Form : Form
  {
    private Character character;
    private IContainer components;
    private FlowLayoutPanel imagePanel;

    public Sack0Form() => this.InitializeComponent();

    public void clearStashUI() => this.imagePanel.Controls.Clear();

    public void loadStash()
    {
      Character selectedCharacter = TQData.selectedCharacter;
      this.imagePanel.Controls.Clear();
      if (selectedCharacter == null)
        return;
      if (selectedCharacter.GetMainSack == null)
      {
        Logger.Log("Character doesn't have sack.");
      }
      else
      {
        this.character = selectedCharacter;
        int num = 0;
        foreach (Item itm in selectedCharacter.GetMainSack)
        {
          this.addItem(itm);
          ++num;
        }
      }
    }

    public void reoveItem(Control ctrl) => this.imagePanel.Controls.Remove(ctrl);

    public void addItem(Item itm)
    {
      Button button = new Button();
      button.Width = itm.ItemBitmap.Width + 1;
      button.Height = itm.ItemBitmap.Height + 1;
      button.Image = (Image) itm.ItemBitmap;
      button.BackColor = Color.Transparent;
      button.ForeColor = Color.Transparent;
      button.FlatStyle = FlatStyle.Flat;
      button.FlatAppearance.MouseOverBackColor = ColorTranslator.FromHtml("#1a1d9d");
      button.FlatAppearance.BorderSize = 0;
      button.MouseDown += new MouseEventHandler(this.btn_MouseClick);
      ToolTip toolTip = new ToolTip();
      this.imagePanel.Controls.Add((Control) button);
      toolTip.ForeColor = Color.Green;
      toolTip.BackColor = Color.Black;
      toolTip.SetToolTip((Control) button, itm.ToString(false, false));
    }

    private void btn_MouseClick(object sender, EventArgs e)
    {
      if (((MouseEventArgs) e).Button != MouseButtons.Right)
        return;
      Button button = new Button();
      button.Text = "Delete";
      button.FlatStyle = FlatStyle.Flat;
      button.FlatAppearance.MouseDownBackColor = Color.Transparent;
      button.FlatAppearance.MouseOverBackColor = ColorTranslator.FromHtml("#1a1d9d");
      button.FlatAppearance.BorderSize = 0;
      this.Controls.Add((Control) button);
      button.Location = Cursor.Position;
      button.MouseClick += new MouseEventHandler(this.ctx_MouseClick);
      button.MouseLeave += new EventHandler(this.ctx_MouseLeave);
      button.BringToFront();
    }

    private void ctx_MouseClick(object sender, MouseEventArgs e)
    {
    }

    private void ctx_MouseLeave(object sender, EventArgs e)
    {
    }

    private void Sack0Form_Load(object sender, EventArgs e)
    {
    }

    private void Sack0Form_FormClosing(object sender, FormClosingEventArgs e)
    {
      if (e.CloseReason != CloseReason.UserClosing)
        return;
      this.WindowState = FormWindowState.Minimized;
      e.Cancel = true;
    }

    private void Sack0Form_Shown(object sender, EventArgs e)
    {
      this.imagePanel.VerticalScroll.Maximum = 0;
      this.imagePanel.AutoScroll = false;
      this.imagePanel.VerticalScroll.Enabled = false;
      this.imagePanel.VerticalScroll.Visible = false;
      this.imagePanel.AutoScroll = true;
      this.loadStash();
    }

    private void RemoveButton_Click(object sender, EventArgs e)
    {
    }

    private void sackItems_ItemMouseHover(object sender, ListViewItemMouseHoverEventArgs e) => new ToolTip().ForeColor = Color.Green;

    protected override void Dispose(bool disposing)
    {
      if (disposing && this.components != null)
        this.components.Dispose();
      base.Dispose(disposing);
    }

    private void InitializeComponent()
    {
      ComponentResourceManager componentResourceManager = new ComponentResourceManager(typeof (Sack0Form));
      this.imagePanel = new FlowLayoutPanel();
      this.SuspendLayout();
      this.imagePanel.AutoScroll = true;
      this.imagePanel.BackColor = Color.Transparent;
      this.imagePanel.Dock = DockStyle.Fill;
      this.imagePanel.Location = new Point(0, 0);
      this.imagePanel.Name = "imagePanel";
      this.imagePanel.Size = new Size(405, 168);
      this.imagePanel.TabIndex = 3;
      this.imagePanel.WrapContents = false;
      this.AutoScaleDimensions = new SizeF(6f, 13f);
      this.AutoScaleMode = AutoScaleMode.Font;
      this.BackColor = Color.FromArgb(46, 41, 31);
      this.ClientSize = new Size(405, 168);
      this.Controls.Add((Control) this.imagePanel);
      this.FormBorderStyle = FormBorderStyle.FixedSingle;
      this.Icon = (Icon) componentResourceManager.GetObject("$this.Icon");
      this.MaximizeBox = false;
      this.Name = nameof (Sack0Form);
      this.ShowInTaskbar = false;
      this.StartPosition = FormStartPosition.Manual;
      this.Text = "First Sack";
      this.FormClosing += new FormClosingEventHandler(this.Sack0Form_FormClosing);
      this.Load += new EventHandler(this.Sack0Form_Load);
      this.Shown += new EventHandler(this.Sack0Form_Shown);
      this.ResumeLayout(false);
    }
  }
}
