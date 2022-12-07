// Decompiled with JetBrains decompiler
// Type: TQ_weaponsmith.LoadDataForm
// Assembly: TQ_Blacksmith, Version=0.1.4.1, Culture=neutral, PublicKeyToken=null
// MVID: 1CC9040D-95C6-4C54-B248-9D80684B2CA8
// Assembly location: D:\Games\GOG Galaxy\Games\Titan Quest - Anniversary Edition\Downloads\Blacksmith\TQ_Blacksmith.exe

using System;
using System.ComponentModel;
using System.Drawing;
using System.Threading;
using System.Windows.Forms;

namespace TQ_weaponsmith
{
  public class LoadDataForm : Form
  {
    private IContainer components;
    private Label LoddingDataLabel;

    public LoadDataForm() => this.InitializeComponent();

    private void LoadDataForm_Shown(object sender, EventArgs e) => ThreadPool.RegisterWaitForSingleObject((WaitHandle) new AutoResetEvent(false), (WaitOrTimerCallback) ((state, bTimeout) =>
    {
      this.loadResources();
      this.closeForm();
    }), (object) "This is my state variable", TimeSpan.FromSeconds(1.0), true);

    private void loadResources()
    {
      Database.DB = new Database();
      Database.DB.AutoDetectLanguage = true;
      Database.DB.TQLanguage = "en";
      try
      {
        Database.DB.LoadDBFile();
        Database.DB.LoadAllItems();
        TQData.loadCharacterList();
        Config.areResourcesLoaded = true;
      }
      catch (Exception ex)
      {
        int num = (int) MessageBox.Show(ex.ToString(), "Error Loading Resources", MessageBoxButtons.OK, MessageBoxIcon.Exclamation, MessageBoxDefaultButton.Button1);
        Logger.Log("Error loading resources " + ex.ToString());
        Form1.mainForm.Close();
      }
    }

    private void closeForm()
    {
      if (this.InvokeRequired)
        this.Invoke((Delegate) new LoadDataForm.CloseFormCallback(this.closeForm));
      else
        this.Close();
    }

    private void LoadDataForm_Load(object sender, EventArgs e)
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
      ComponentResourceManager componentResourceManager = new ComponentResourceManager(typeof (LoadDataForm));
      this.LoddingDataLabel = new Label();
      this.SuspendLayout();
      componentResourceManager.ApplyResources((object) this.LoddingDataLabel, "LoddingDataLabel");
      this.LoddingDataLabel.BackColor = Color.Black;
      this.LoddingDataLabel.ForeColor = Color.Chartreuse;
      this.LoddingDataLabel.Name = "LoddingDataLabel";
      this.LoddingDataLabel.UseWaitCursor = true;
      componentResourceManager.ApplyResources((object) this, "$this");
      this.AutoScaleMode = AutoScaleMode.Font;
      this.BackColor = Color.Black;
      this.ControlBox = false;
      this.Controls.Add((Control) this.LoddingDataLabel);
      this.FormBorderStyle = FormBorderStyle.None;
      this.Name = nameof (LoadDataForm);
      this.ShowInTaskbar = false;
      this.UseWaitCursor = true;
      this.Load += new EventHandler(this.LoadDataForm_Load);
      this.Shown += new EventHandler(this.LoadDataForm_Shown);
      this.ResumeLayout(false);
      this.PerformLayout();
    }

    private delegate void CloseFormCallback();
  }
}
