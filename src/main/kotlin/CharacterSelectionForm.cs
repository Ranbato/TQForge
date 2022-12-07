// Decompiled with JetBrains decompiler
// Type: TQ_weaponsmith.CharacterSelectionForm
// Assembly: TQ_Blacksmith, Version=0.1.4.1, Culture=neutral, PublicKeyToken=null
// MVID: 1CC9040D-95C6-4C54-B248-9D80684B2CA8
// Assembly location: D:\Games\GOG Galaxy\Games\Titan Quest - Anniversary Edition\Downloads\Blacksmith\TQ_Blacksmith.exe

using System;
using System.ComponentModel;
using System.Drawing;
using System.Windows.Forms;

namespace TQ_weaponsmith
{
  public class CharacterSelectionForm : Form
  {
    private Form1 parentForm;
    private IContainer components;
    private Label label1;
    private ComboBox characterListCombo;
    private Button SelectButton;
    private Label label2;
    private Button cancelButton;

    public CharacterSelectionForm(Form1 mainForm)
    {
      this.parentForm = mainForm;
      this.InitializeComponent();
    }

    private void CharacterSelectionForm_Shown(object sender, EventArgs e)
    {
      this.SelectButton.Enabled = true;
      this.characterListCombo.Items.Clear();
      this.characterListCombo.Items.AddRange((object[]) TQData.characterNameList);
      this.characterListCombo.SelectedIndex = 0;
    }

    private void SelectButton_Click(object sender, EventArgs e)
    {
      string str = this.characterListCombo.SelectedItem.ToString();
      this.SelectButton.Enabled = false;
      try
      {
        Character character = TQData.loadCharacter(str);
        if (TQData.selectedCharacter != null)
          this.parentForm.sackForm.clearStashUI();
        if (character.GetMainSack != null)
        {
          TQData.selectedCharacter = character;
          Logger.Log("Selected character " + str);
          this.parentForm.setCurrentCharacterLabel(str);
          this.parentForm.showSack();
          this.parentForm.smithyToolStripMenuItem.Enabled = true;
          this.Close();
        }
        else
        {
          int num = (int) MessageBox.Show("The character doesn't have sack created. Visit first caravan in game and exit.");
        }
      }
      catch (Exception ex)
      {
        int num = (int) MessageBox.Show("Choose another character. Unable to load character " + str + " data: " + ex.ToString(), "Error", MessageBoxButtons.OK, MessageBoxIcon.Hand);
        Logger.Log("Unable to load character " + str + " data: " + ex.ToString());
      }
    }

    private void CharacterSelectionForm_Load(object sender, EventArgs e)
    {
      if (TQData.selectedCharacter == null)
        this.cancelButton.Enabled = false;
      else
        this.cancelButton.Enabled = true;
    }

    private void cancelButton_Click(object sender, EventArgs e) => this.Close();

    private void characterListCombo_SelectedIndexChanged(object sender, EventArgs e) => this.SelectButton.Enabled = true;

    protected override void Dispose(bool disposing)
    {
      if (disposing && this.components != null)
        this.components.Dispose();
      base.Dispose(disposing);
    }

    private void InitializeComponent()
    {
      this.label1 = new Label();
      this.characterListCombo = new ComboBox();
      this.SelectButton = new Button();
      this.label2 = new Label();
      this.cancelButton = new Button();
      this.SuspendLayout();
      this.label1.AutoSize = true;
      this.label1.Font = new Font("Microsoft Sans Serif", 10.25f, FontStyle.Regular, GraphicsUnit.Point, (byte) 0);
      this.label1.Location = new Point(163, 21);
      this.label1.Name = "label1";
      this.label1.Size = new Size(232, 17);
      this.label1.TabIndex = 0;
      this.label1.Text = "Select a character from main quest.";
      this.characterListCombo.AutoCompleteMode = AutoCompleteMode.SuggestAppend;
      this.characterListCombo.AutoCompleteSource = AutoCompleteSource.ListItems;
      this.characterListCombo.DropDownStyle = ComboBoxStyle.DropDownList;
      this.characterListCombo.FormattingEnabled = true;
      this.characterListCombo.Location = new Point(139, 79);
      this.characterListCombo.Name = "characterListCombo";
      this.characterListCombo.Size = new Size(287, 21);
      this.characterListCombo.TabIndex = 1;
      this.characterListCombo.SelectedIndexChanged += new EventHandler(this.characterListCombo_SelectedIndexChanged);
      this.SelectButton.Location = new Point(248, 115);
      this.SelectButton.Name = "SelectButton";
      this.SelectButton.Size = new Size(75, 23);
      this.SelectButton.TabIndex = 2;
      this.SelectButton.Text = "Select";
      this.SelectButton.UseVisualStyleBackColor = true;
      this.SelectButton.Click += new EventHandler(this.SelectButton_Click);
      this.label2.AutoSize = true;
      this.label2.Font = new Font("Microsoft Sans Serif", 10.25f, FontStyle.Regular, GraphicsUnit.Point, (byte) 0);
      this.label2.Location = new Point(113, 48);
      this.label2.Name = "label2";
      this.label2.Size = new Size(352, 17);
      this.label2.TabIndex = 3;
      this.label2.Text = "For safety select a dummy/mule character to work with.";
      this.cancelButton.Location = new Point(476, 138);
      this.cancelButton.Name = "cancelButton";
      this.cancelButton.Size = new Size(75, 23);
      this.cancelButton.TabIndex = 4;
      this.cancelButton.Text = "Cancel";
      this.cancelButton.UseVisualStyleBackColor = true;
      this.cancelButton.Click += new EventHandler(this.cancelButton_Click);
      this.AutoScaleDimensions = new SizeF(6f, 13f);
      this.AutoScaleMode = AutoScaleMode.Font;
      this.BackColor = Color.Tan;
      this.ClientSize = new Size(563, 173);
      this.ControlBox = false;
      this.Controls.Add((Control) this.cancelButton);
      this.Controls.Add((Control) this.label2);
      this.Controls.Add((Control) this.SelectButton);
      this.Controls.Add((Control) this.characterListCombo);
      this.Controls.Add((Control) this.label1);
      this.MaximizeBox = false;
      this.MinimizeBox = false;
      this.Name = nameof (CharacterSelectionForm);
      this.ShowIcon = false;
      this.ShowInTaskbar = false;
      this.SizeGripStyle = SizeGripStyle.Hide;
      this.StartPosition = FormStartPosition.CenterScreen;
      this.Text = nameof (CharacterSelectionForm);
      this.Load += new EventHandler(this.CharacterSelectionForm_Load);
      this.Shown += new EventHandler(this.CharacterSelectionForm_Shown);
      this.ResumeLayout(false);
      this.PerformLayout();
    }
  }
}
