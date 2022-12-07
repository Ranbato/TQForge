// Decompiled with JetBrains decompiler
// Type: TQ_weaponsmith.Settings
// Assembly: TQ_Blacksmith, Version=0.1.4.1, Culture=neutral, PublicKeyToken=null
// MVID: 1CC9040D-95C6-4C54-B248-9D80684B2CA8
// Assembly location: D:\Games\GOG Galaxy\Games\Titan Quest - Anniversary Edition\Downloads\Blacksmith\TQ_Blacksmith.exe

using System;
using System.ComponentModel;
using System.Drawing;
using System.IO;
using System.Windows.Forms;

namespace TQ_weaponsmith
{
  public class Settings : Form
  {
    private Config cfg;
    private IContainer components;
    private TextBox SaveTextBox;
    private FolderBrowserDialog folderBrowserDialog1;
    private Label label1;
    private Button SelectSaveButton;
    private Button SaveButton;
    private Button CancelButton;
    private Label label2;
    private TextBox InstallTextBox2;
    private Button SelectInstallButton;
    private Label label3;
    private FolderBrowserDialog folderBrowserDialog2;

    public Settings() => this.InitializeComponent();

    private void folderBrowserDialog1_HelpRequest(object sender, EventArgs e)
    {
    }

    private void button2_Click(object sender, EventArgs e)
    {
      Config.getConfig().saveLocation = this.SaveTextBox.Text;
      Config.getConfig().installLocation = this.InstallTextBox2.Text;
      Config.saveConfig();
      this.Close();
    }

    private void button3_Click(object sender, EventArgs e) => this.Close();

    private void button1_Click(object sender, EventArgs e)
    {
      if (this.folderBrowserDialog1.ShowDialog() == DialogResult.OK && !string.IsNullOrWhiteSpace(this.folderBrowserDialog1.SelectedPath))
      {
        if (!Directory.Exists(this.folderBrowserDialog1.SelectedPath + "\\SaveData\\Main"))
        {
          this.label2.Text = "Couldn't find save data in \"" + this.folderBrowserDialog1.SelectedPath + "\".";
          this.label2.Show();
          this.SaveButton.Enabled = false;
        }
        else
        {
          this.label2.Hide();
          this.SaveTextBox.Text = this.folderBrowserDialog1.SelectedPath;
          this.SaveButton.Enabled = true;
        }
      }
      else
        this.SaveTextBox.Text = "";
    }

    private void label2_Click(object sender, EventArgs e)
    {
    }

    private void Settings_Shown(object sender, EventArgs e)
    {
      this.cfg = Config.getConfig();
      if (!Directory.Exists(this.cfg.saveLocation + "\\SaveData\\Main"))
        this.SaveButton.Enabled = false;
      else
        this.SaveTextBox.Text = this.cfg.saveLocation;
      if (!Directory.Exists(this.cfg.installLocation))
        this.SaveButton.Enabled = false;
      else
        this.InstallTextBox2.Text = this.cfg.installLocation;
      this.label2.Hide();
    }

    private void button4_Click(object sender, EventArgs e)
    {
      if (this.folderBrowserDialog2.ShowDialog() == DialogResult.OK && !string.IsNullOrWhiteSpace(this.folderBrowserDialog2.SelectedPath))
      {
        string selectedPath = this.folderBrowserDialog2.SelectedPath;
        this.InstallTextBox2.Text = this.folderBrowserDialog2.SelectedPath;
      }
      else
        this.SaveTextBox.Text = "";
    }

    private void Settings_Load(object sender, EventArgs e)
    {
    }

    protected override void Dispose(bool disposing)
    {
      if (disposing && this.components != null)
        this.components.Dispose();
      base.Dispose(disposing);
    }

    private void InitializeComponent()
    {
      this.SaveTextBox = new TextBox();
      this.folderBrowserDialog1 = new FolderBrowserDialog();
      this.label1 = new Label();
      this.SelectSaveButton = new Button();
      this.SaveButton = new Button();
      this.CancelButton = new Button();
      this.label2 = new Label();
      this.InstallTextBox2 = new TextBox();
      this.SelectInstallButton = new Button();
      this.label3 = new Label();
      this.folderBrowserDialog2 = new FolderBrowserDialog();
      this.SuspendLayout();
      this.SaveTextBox.Location = new Point(5, 25);
      this.SaveTextBox.Name = "SaveTextBox";
      this.SaveTextBox.ReadOnly = true;
      this.SaveTextBox.Size = new Size(450, 20);
      this.SaveTextBox.TabIndex = 0;
      this.folderBrowserDialog1.RootFolder = Environment.SpecialFolder.MyComputer;
      this.folderBrowserDialog1.SelectedPath = "%userprofile%\\Documents";
      this.folderBrowserDialog1.ShowNewFolderButton = false;
      this.folderBrowserDialog1.HelpRequest += new EventHandler(this.folderBrowserDialog1_HelpRequest);
      this.label1.AutoSize = true;
      this.label1.Location = new Point(2, 9);
      this.label1.Name = "label1";
      this.label1.Size = new Size(550, 13);
      this.label1.TabIndex = 1;
      this.label1.Text = "Select game save data location e.g. 'C:\\Users\\<userName>\\Documents\\My Games\\Titan Quest - Immortal Throne'";
      this.SelectSaveButton.Location = new Point(472, 25);
      this.SelectSaveButton.Name = "SelectSaveButton";
      this.SelectSaveButton.Size = new Size(75, 23);
      this.SelectSaveButton.TabIndex = 2;
      this.SelectSaveButton.Text = "Select";
      this.SelectSaveButton.UseVisualStyleBackColor = true;
      this.SelectSaveButton.Click += new EventHandler(this.button1_Click);
      this.SaveButton.Location = new Point(380, (int) sbyte.MaxValue);
      this.SaveButton.Name = "SaveButton";
      this.SaveButton.Size = new Size(75, 23);
      this.SaveButton.TabIndex = 3;
      this.SaveButton.Text = "Save";
      this.SaveButton.UseVisualStyleBackColor = true;
      this.SaveButton.Click += new EventHandler(this.button2_Click);
      this.CancelButton.Location = new Point(472, (int) sbyte.MaxValue);
      this.CancelButton.Name = "CancelButton";
      this.CancelButton.Size = new Size(75, 23);
      this.CancelButton.TabIndex = 4;
      this.CancelButton.Text = "Cancel";
      this.CancelButton.UseVisualStyleBackColor = true;
      this.CancelButton.Click += new EventHandler(this.button3_Click);
      this.label2.AutoSize = true;
      this.label2.ForeColor = Color.DarkRed;
      this.label2.Location = new Point(2, 48);
      this.label2.Name = "label2";
      this.label2.Size = new Size(32, 13);
      this.label2.TabIndex = 5;
      this.label2.Text = "Error:";
      this.label2.Click += new EventHandler(this.label2_Click);
      this.InstallTextBox2.Location = new Point(5, 91);
      this.InstallTextBox2.Name = "InstallTextBox2";
      this.InstallTextBox2.ReadOnly = true;
      this.InstallTextBox2.Size = new Size(450, 20);
      this.InstallTextBox2.TabIndex = 6;
      this.SelectInstallButton.Location = new Point(472, 88);
      this.SelectInstallButton.Name = "SelectInstallButton";
      this.SelectInstallButton.Size = new Size(75, 23);
      this.SelectInstallButton.TabIndex = 7;
      this.SelectInstallButton.Text = "Select";
      this.SelectInstallButton.UseVisualStyleBackColor = true;
      this.SelectInstallButton.Click += new EventHandler(this.button4_Click);
      this.label3.AutoSize = true;
      this.label3.Location = new Point(2, 75);
      this.label3.Name = "label3";
      this.label3.Size = new Size(138, 13);
      this.label3.TabIndex = 8;
      this.label3.Text = "Select game install directory";
      this.folderBrowserDialog2.RootFolder = Environment.SpecialFolder.MyComputer;
      this.folderBrowserDialog2.SelectedPath = "%userprofile%\\Documents";
      this.folderBrowserDialog2.ShowNewFolderButton = false;
      this.AutoScaleDimensions = new SizeF(6f, 13f);
      this.AutoScaleMode = AutoScaleMode.Font;
      this.BackColor = SystemColors.AppWorkspace;
      this.ClientSize = new Size(563, 173);
      this.ControlBox = false;
      this.Controls.Add((Control) this.label3);
      this.Controls.Add((Control) this.SelectInstallButton);
      this.Controls.Add((Control) this.InstallTextBox2);
      this.Controls.Add((Control) this.label2);
      this.Controls.Add((Control) this.CancelButton);
      this.Controls.Add((Control) this.SaveButton);
      this.Controls.Add((Control) this.SelectSaveButton);
      this.Controls.Add((Control) this.label1);
      this.Controls.Add((Control) this.SaveTextBox);
      this.FormBorderStyle = FormBorderStyle.FixedDialog;
      this.Name = nameof (Settings);
      this.SizeGripStyle = SizeGripStyle.Hide;
      this.StartPosition = FormStartPosition.CenterScreen;
      this.Text = nameof (Settings);
      this.Load += new EventHandler(this.Settings_Load);
      this.Shown += new EventHandler(this.Settings_Shown);
      this.ResumeLayout(false);
      this.PerformLayout();
    }
  }
}
