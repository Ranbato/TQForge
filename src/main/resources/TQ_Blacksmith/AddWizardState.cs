// Decompiled with JetBrains decompiler
// Type: TQ_weaponsmith.AddWizardState
// Assembly: TQ_Blacksmith, Version=0.1.4.1, Culture=neutral, PublicKeyToken=null
// MVID: 1CC9040D-95C6-4C54-B248-9D80684B2CA8
// Assembly location: D:\Games\GOG Galaxy\Games\Titan Quest - Anniversary Edition\Downloads\Blacksmith\TQ_Blacksmith.exe

using AdvancedWizardControl.EventArguments;
using Microsoft.CSharp.RuntimeBinder;
using System;
using System.Collections.Generic;
using System.Drawing;
using System.Globalization;
using System.IO;
using System.Linq;
using System.Runtime.CompilerServices;
using System.Text.RegularExpressions;
using System.Windows.Forms;

namespace TQ_weaponsmith
{
  internal class AddWizardState
  {
    public int currentPage;
    public ItemType itemType;
    public ItemType itemSubType;
    public ItemClass itemClassification;
    public string prefix;
    public string suffix;
    public int seed;
    public Info itemInfo;
    public Item item;
    public List<Info> filteredInfo;
    public List<Button> buttons = new List<Button>();
    public Button page1SelectedButton;
    public Button page2SelectedButton;
    public Button selectedItemButton;
    private string selectedBonusID;
    private string suffixID;
    public GroupBox armorGB;
    public GroupBox weaponGB;
    private Form1 mainForm;
    private ScalingLabel tooltipText;
    private Color brownBackColor = ColorTranslator.FromHtml("#2e291f");
    private Color orangeColor = ColorTranslator.FromHtml(TQColor.Orange.GetStringValue());
    private Color epicColor = ColorTranslator.FromHtml("#00a3ff");
    private Color selectedColor = ColorTranslator.FromHtml("#1a1d9d");
    private Color legendaryColor = Color.Plum;

    public AddWizardState(Form1 frm) => this.mainForm = frm;

    public void init()
    {
      this.armorGB = this.mainForm.armorGroupBox;
      this.armorGB.Location = new Point(50, 93);
      this.weaponGB = this.mainForm.weaponGroupBox;
      this.weaponGB.Location = new Point(40, 66);
      this.buttons.Add(this.mainForm.armorButton);
      this.buttons.Add(this.mainForm.weaponButton);
      this.buttons.Add(this.mainForm.ringButton);
      this.buttons.Add(this.mainForm.amuletButton);
      this.buttons.Add(this.mainForm.artifactButton);
      this.buttons.Add(this.mainForm.scrollButton);
      this.buttons.Add(this.mainForm.formulaButton);
      this.buttons.Add(this.mainForm.charmButton);
      this.buttons.Add(this.mainForm.relicButton);
      this.buttons.Add(this.mainForm.parchmentButton);
      this.buttons.Add(this.mainForm.questItemButton);
      this.buttons.Add(this.mainForm.miscButton);
      this.buttons.Add(this.mainForm.headButton);
      this.buttons.Add(this.mainForm.legButton);
      this.buttons.Add(this.mainForm.armButton);
      this.buttons.Add(this.mainForm.chestButton);
      this.buttons.Add(this.mainForm.swordButton);
      this.buttons.Add(this.mainForm.maceButton);
      this.buttons.Add(this.mainForm.bowButton);
      this.buttons.Add(this.mainForm.axeButton);
      this.buttons.Add(this.mainForm.staffButton);
      this.buttons.Add(this.mainForm.spearButton);
      this.buttons.Add(this.mainForm.shieldButton);
      this.buttons.Add(this.mainForm.thrownButton);
      for (int index = 0; index < this.buttons.Count; ++index)
      {
        Button button = this.buttons[index];
        button.FlatAppearance.MouseDownBackColor = ColorTranslator.FromHtml("#1a1d9d");
        button.FlatAppearance.MouseOverBackColor = ColorTranslator.FromHtml("#5a5ded");
        button.FlatAppearance.BorderSize = 0;
        if (index < 12)
          button.MouseClick += new MouseEventHandler(this.wizard_p1_btn_MouseClick);
        else
          button.MouseClick += new MouseEventHandler(this.wizard_p2_btn_MouseClick);
      }
    }

    public void show()
    {
      this.currentPage = 0;
      this.itemType = ItemType.Weapon;
      this.itemSubType = ItemType.Head;
      this.prefix = this.suffix = "";
      if (this.page1SelectedButton != null)
        this.page1SelectedButton.BackColor = this.brownBackColor;
      this.page1SelectedButton = this.mainForm.weaponButton;
      this.mainForm.weaponButton.BackColor = ColorTranslator.FromHtml("#1a1d9d");
      this.weaponGB.Visible = false;
      this.armorGB.Visible = false;
      this.page1SelectedButton = this.mainForm.weaponButton;
      this.mainForm.MIRadioButton.Enabled = true;
      this.mainForm.MIRadioButton.Text = "Rare";
      this.mainForm.epicRadioButton.Text = "Epic";
      this.mainForm.legendaryButton.Text = "Legendary";
      this.mainForm.MIRadioButton.ForeColor = Item.GetColor(TQColor.Orange);
      this.mainForm.itemWizard.FinishButtonEnabled = false;
      this.mainForm.itemWizard.GoToPage(0);
      ((Control) this.mainForm.itemWizard).Visible = true;
      this.mainForm.ItemListBox.DisplayMember = "name";
      this.mainForm.ItemListBox.ValueMember = "value";
      this.mainForm.prefixComboBox.DisplayMember = "name";
      this.mainForm.prefixComboBox.ValueMember = "value";
      this.mainForm.suffixComboBox.DisplayMember = "name";
      this.mainForm.suffixComboBox.ValueMember = "value";
      this.mainForm.itemSelectImagePanel.Controls.Clear();
      this.mainForm.ItemListBox.Items.Clear();
      this.mainForm.miscButton.BackgroundImage = (Image) Database.DB.LoadBitmap("Items\\QuestItems\\UIBitmaps\\qi_axesickle.tex");
      this.mainForm.prefixComboBox.Tag = (object) this.mainForm.prefixTextBox;
      this.mainForm.suffixComboBox.Tag = (object) this.mainForm.suffixTextBox;
    }

    public void wizard_p1_btn_MouseClick(object sender, EventArgs e)
    {
      Button button = (Button) sender;
      if (this.page1SelectedButton != null)
        this.page1SelectedButton.BackColor = this.brownBackColor;
      this.page1SelectedButton = button;
      button.BackColor = this.selectedColor;
    }

    public void wizard_p2_btn_MouseClick(object sender, EventArgs e)
    {
      Button button = (Button) sender;
      if (this.page2SelectedButton != null)
        this.page2SelectedButton.BackColor = this.brownBackColor;
      this.page2SelectedButton = button;
      button.BackColor = ColorTranslator.FromHtml("#1a1d9d");
    }

    public void back(EventArgs e) => this.mainForm.itemWizard.FinishButtonEnabled = false;

    public void selectItem(Info inf)
    {
      this.itemInfo = inf;
      this.mainForm.pictureBox.Image = (Image) Database.DB.LoadBitmap(inf.Bitmap);
      this.mainForm.selectItemWP.SubTitle = "Selected Item: " + inf.cleanName();
    }

    public void next(WizardEventArgs arg)
    {
      if (arg.CurrentPageIndex == 0)
      {
        this.itemType = TQData.getItemType(this.page1SelectedButton.Name.Replace("Button", ""));
        switch (this.itemType)
        {
          case ItemType.Armor:
            this.itemSubType = ItemType.Armor;
            break;
          case ItemType.Weapon:
            this.itemSubType = ItemType.Weapon;
            break;
          case ItemType.Ring:
          case ItemType.Amulet:
            arg.NextPageIndex = 2;
            break;
          case ItemType.Relic:
          case ItemType.Charm:
          case ItemType.Scroll:
            arg.NextPageIndex = 2;
            break;
          case ItemType.Formula:
          case ItemType.Artifact:
            arg.NextPageIndex = 2;
            break;
          case ItemType.Quest:
          case ItemType.Parchment:
          case ItemType.Misc:
            arg.NextPageIndex = 3;
            this.filteredInfo = Database.DB.getInfos(this.itemType, ItemClass.None);
            this.populateItemsList();
            break;
        }
      }
      else if (arg.CurrentPageIndex == 1)
        this.itemType = TQData.getItemType(this.page2SelectedButton.Name.Replace("Button", ""));
      else if (arg.CurrentPageIndex == 2)
      {
        if (this.itemType == ItemType.Formula || ItemType.Artifact == this.itemType)
        {
          if (this.mainForm.MIRadioButton.Checked)
          {
            this.itemClassification = ItemClass.Lesser;
            this.mainForm.itemNameLabel.ForeColor = this.orangeColor;
          }
          else if (this.mainForm.epicRadioButton.Checked)
          {
            this.mainForm.itemNameLabel.ForeColor = this.epicColor;
            this.itemClassification = ItemClass.Greater;
          }
          else
          {
            this.mainForm.itemNameLabel.ForeColor = this.legendaryColor;
            this.itemClassification = ItemClass.Divine;
          }
          this.filteredInfo = Database.DB.getInfos(this.itemType, this.itemClassification);
        }
        else if (ItemType.Scroll == this.itemType || ItemType.Charm == this.itemType || ItemType.Relic == this.itemType)
        {
          this.mainForm.itemNameLabel.ForeColor = Color.White;
          this.itemClassification = !this.mainForm.MIRadioButton.Checked ? (!this.mainForm.epicRadioButton.Checked ? ItemClass.Legendary : ItemClass.Epic) : ItemClass.Normal;
          this.filteredInfo = Database.DB.getInfos(this.itemType, this.itemClassification);
        }
        else if (ItemType.Ring == this.itemType || ItemType.Amulet == this.itemType)
        {
          if (this.mainForm.epicRadioButton.Checked)
          {
            this.mainForm.itemNameLabel.ForeColor = this.epicColor;
            this.itemClassification = ItemClass.Epic;
          }
          else
          {
            this.mainForm.itemNameLabel.ForeColor = this.legendaryColor;
            this.itemClassification = ItemClass.Legendary;
          }
          this.filteredInfo = Database.DB.getInfos(this.itemType, this.itemClassification);
        }
        else
        {
          if (this.mainForm.MIRadioButton.Checked)
          {
            this.itemClassification = ItemClass.Rare;
            this.mainForm.itemNameLabel.ForeColor = Color.YellowGreen;
          }
          else if (this.mainForm.epicRadioButton.Checked)
          {
            this.itemClassification = ItemClass.Epic;
            this.mainForm.itemNameLabel.ForeColor = this.epicColor;
          }
          else
          {
            this.itemClassification = ItemClass.Legendary;
            this.mainForm.itemNameLabel.ForeColor = this.legendaryColor;
          }
          this.filteredInfo = Database.DB.getInfos(this.itemType, this.itemClassification);
        }
        this.populateItemsList();
        if (this.itemInfo != null)
          return;
        arg.AllowPageChange = false;
        int num = (int) MessageBox.Show("No records found for selected type.", "Warning", MessageBoxButtons.OK, MessageBoxIcon.Asterisk);
      }
      else if (arg.CurrentPageIndex == 3)
      {
        this.mainForm.itemNameLabel.Text = this.itemInfo.cleanName();
        this.mainForm.previewItemPictureBox.BackgroundImage = (Image) Database.DB.LoadBitmap(this.itemInfo.Bitmap);
        if (ItemType.Quest == this.itemType || ItemType.Parchment == this.itemType || ItemType.Formula == this.itemType || ItemType.Scroll == this.itemType || ItemType.Misc == this.itemType)
        {
          arg.NextPageIndex = 5;
          this.mainForm.previewItemLabel.Text = this.itemInfo.cleanName();
          this.item = new Item(this.itemInfo.ItemId, this.itemInfo.RecordType, this.itemInfo.ItemClassification, this.itemInfo.DescriptionTag);
          this.item.Seed = Decimal.ToInt32(this.mainForm.seedNUDown.Value);
          this.item.GetDBData();
          this.mainForm.itemWizard.FinishButtonEnabled = true;
          this.mainForm.descriptionWebBrowser.DocumentText = "0";
          this.mainForm.descriptionWebBrowser.Document.OpenNew(true);
          this.mainForm.descriptionWebBrowser.Document.Write(this.getItemDescription());
          this.mainForm.descriptionWebBrowser.Refresh();
        }
        else if (ItemType.Artifact == this.itemType || ItemType.Relic == this.itemType || ItemType.Charm == this.itemType)
        {
          this.item = new Item(this.itemInfo.ItemId, this.itemInfo.RecordType, this.itemInfo.ItemClassification, this.itemInfo.DescriptionTag);
          this.item.Seed = Decimal.ToInt32(this.mainForm.seedNUDown.Value);
          this.item.completeRelic(this.itemInfo.CompletedRelicLevel);
          this.item.GetDBData();
          this.mainForm.suffixComboBox.Enabled = false;
          this.populateBonus();
        }
        else if (ItemType.Ring == this.itemType || ItemType.Amulet == this.itemType)
        {
          this.item = new Item(this.itemInfo.ItemId, this.itemInfo.RecordType, this.itemInfo.ItemClassification, this.itemInfo.DescriptionTag);
          this.item.Seed = Decimal.ToInt32(this.mainForm.seedNUDown.Value);
          this.item.completeRelic(this.itemInfo.CompletedRelicLevel);
          this.item.GetDBData();
          this.mainForm.suffixComboBox.Enabled = true;
          this.populateAffix(this.mainForm.prefixComboBox, this.mainForm.prefixTextBox, Database.DB.getPrefixes(this.itemType));
          this.populateAffix(this.mainForm.suffixComboBox, this.mainForm.suffixTextBox, Database.DB.getSuffixes(this.itemType), false);
        }
        else
        {
          this.item = new Item(this.itemInfo.ItemId, this.itemInfo.RecordType, this.itemInfo.ItemClassification, this.itemInfo.DescriptionTag);
          this.item.Seed = Decimal.ToInt32(this.mainForm.seedNUDown.Value);
          this.item.completeRelic(this.itemInfo.CompletedRelicLevel);
          this.item.GetDBData();
          this.mainForm.suffixComboBox.Enabled = true;
          this.populateAffix(this.mainForm.prefixComboBox, this.mainForm.prefixTextBox, Database.DB.getPrefixes(this.itemType));
          this.populateAffix(this.mainForm.suffixComboBox, this.mainForm.suffixTextBox, Database.DB.getSuffixes(this.itemType), false);
        }
      }
      else if (arg.CurrentPageIndex == 4)
      {
        this.seed = Decimal.ToInt32(this.mainForm.seedNUDown.Value);
        this.mainForm.itemWizard.FinishButtonEnabled = true;
        if (this.itemType == ItemType.Artifact || this.itemType == ItemType.Relic || this.itemType == ItemType.Charm)
        {
          object selectedItem = this.mainForm.prefixComboBox.SelectedItem;
          // ISSUE: reference to a compiler-generated field
          if (AddWizardState.\u003C\u003Eo__32.\u003C\u003Ep__1 == null)
          {
            // ISSUE: reference to a compiler-generated field
            AddWizardState.\u003C\u003Eo__32.\u003C\u003Ep__1 = CallSite<Func<CallSite, object, string>>.Create(Binder.Convert(CSharpBinderFlags.None, typeof (string), typeof (AddWizardState)));
          }
          // ISSUE: reference to a compiler-generated field
          Func<CallSite, object, string> target = AddWizardState.\u003C\u003Eo__32.\u003C\u003Ep__1.Target;
          // ISSUE: reference to a compiler-generated field
          CallSite<Func<CallSite, object, string>> p1 = AddWizardState.\u003C\u003Eo__32.\u003C\u003Ep__1;
          // ISSUE: reference to a compiler-generated field
          if (AddWizardState.\u003C\u003Eo__32.\u003C\u003Ep__0 == null)
          {
            // ISSUE: reference to a compiler-generated field
            AddWizardState.\u003C\u003Eo__32.\u003C\u003Ep__0 = CallSite<Func<CallSite, object, object>>.Create(Binder.GetMember(CSharpBinderFlags.None, "value", typeof (AddWizardState), (IEnumerable<CSharpArgumentInfo>) new CSharpArgumentInfo[1]
            {
              CSharpArgumentInfo.Create(CSharpArgumentInfoFlags.None, (string) null)
            }));
          }
          // ISSUE: reference to a compiler-generated field
          // ISSUE: reference to a compiler-generated field
          object obj = AddWizardState.\u003C\u003Eo__32.\u003C\u003Ep__0.Target((CallSite) AddWizardState.\u003C\u003Eo__32.\u003C\u003Ep__0, selectedItem);
          this.selectedBonusID = target((CallSite) p1, obj);
          this.item = new Item(this.itemInfo.ItemId, this.itemInfo.RecordType, this.itemInfo.ItemClassification, this.itemInfo.DescriptionTag);
          this.item.Seed = Decimal.ToInt32(this.mainForm.seedNUDown.Value);
          this.item.RelicBonusId = !this.mainForm.affixCheckBox.Checked ? "" : this.selectedBonusID;
          this.item.completeRelic(this.itemInfo.CompletedRelicLevel);
          this.item.GetDBData();
        }
        else if (ItemType.Ring == this.itemType || ItemType.Amulet == this.itemType)
        {
          this.item = new Item(this.itemInfo.ItemId, this.itemInfo.RecordType, this.itemInfo.ItemClassification, this.itemInfo.DescriptionTag);
          this.item.Seed = Decimal.ToInt32(this.mainForm.seedNUDown.Value);
          this.item.completeRelic(this.itemInfo.CompletedRelicLevel);
          if (this.mainForm.affixCheckBox.Checked)
          {
            this.item.PrefixId = this.selectedBonusID;
            this.item.SuffixId = this.suffixID;
          }
          else
          {
            this.item.PrefixId = "";
            this.item.SuffixId = "";
          }
          this.item.GetDBData();
        }
        else if (TQData.isWeaponOrArmor(this.itemType))
        {
          object selectedItem = this.mainForm.prefixComboBox.SelectedItem;
          // ISSUE: reference to a compiler-generated field
          if (AddWizardState.\u003C\u003Eo__32.\u003C\u003Ep__3 == null)
          {
            // ISSUE: reference to a compiler-generated field
            AddWizardState.\u003C\u003Eo__32.\u003C\u003Ep__3 = CallSite<Func<CallSite, object, string>>.Create(Binder.Convert(CSharpBinderFlags.None, typeof (string), typeof (AddWizardState)));
          }
          // ISSUE: reference to a compiler-generated field
          Func<CallSite, object, string> target = AddWizardState.\u003C\u003Eo__32.\u003C\u003Ep__3.Target;
          // ISSUE: reference to a compiler-generated field
          CallSite<Func<CallSite, object, string>> p3 = AddWizardState.\u003C\u003Eo__32.\u003C\u003Ep__3;
          // ISSUE: reference to a compiler-generated field
          if (AddWizardState.\u003C\u003Eo__32.\u003C\u003Ep__2 == null)
          {
            // ISSUE: reference to a compiler-generated field
            AddWizardState.\u003C\u003Eo__32.\u003C\u003Ep__2 = CallSite<Func<CallSite, object, object>>.Create(Binder.GetMember(CSharpBinderFlags.None, "value", typeof (AddWizardState), (IEnumerable<CSharpArgumentInfo>) new CSharpArgumentInfo[1]
            {
              CSharpArgumentInfo.Create(CSharpArgumentInfoFlags.None, (string) null)
            }));
          }
          // ISSUE: reference to a compiler-generated field
          // ISSUE: reference to a compiler-generated field
          object obj = AddWizardState.\u003C\u003Eo__32.\u003C\u003Ep__2.Target((CallSite) AddWizardState.\u003C\u003Eo__32.\u003C\u003Ep__2, selectedItem);
          this.selectedBonusID = target((CallSite) p3, obj);
          this.item = new Item(this.itemInfo.ItemId, this.itemInfo.RecordType, this.itemInfo.ItemClassification, this.itemInfo.DescriptionTag);
          this.item.Seed = Decimal.ToInt32(this.mainForm.seedNUDown.Value);
          if (this.mainForm.affixCheckBox.Checked)
          {
            this.item.PrefixId = this.selectedBonusID;
            this.item.SuffixId = this.suffixID;
          }
          else
          {
            this.item.PrefixId = "";
            this.item.SuffixId = "";
          }
          this.item.GetDBData();
        }
        this.mainForm.descriptionWebBrowser.DocumentText = "0";
        this.mainForm.descriptionWebBrowser.Document.OpenNew(true);
        this.mainForm.descriptionWebBrowser.Document.Write(this.getItemDescription());
        this.mainForm.descriptionWebBrowser.Refresh();
      }
      else
      {
        int currentPageIndex = arg.CurrentPageIndex;
      }
    }

    public void finish()
    {
      Character selectedCharacter = TQData.selectedCharacter;
      if (selectedCharacter == null)
        return;
      selectedCharacter.GetMainSack.AddItem(this.item);
      this.mainForm.reloadSackData();
      this.mainForm.maximizeSack();
    }

    private void populateBonus()
    {
      ComboBox prefixComboBox = this.mainForm.prefixComboBox;
      prefixComboBox.Items.Clear();
      LootTable bonusTable = this.item.BonusTable;
      if (bonusTable == null)
        return;
      int num = 0;
      foreach (KeyValuePair<string, float> keyValuePair in bonusTable)
      {
        ++num;
        string withoutExtension = Path.GetFileNameWithoutExtension(keyValuePair.Key);
        prefixComboBox.Items.Add((object) new
        {
          name = withoutExtension,
          value = keyValuePair.Key
        });
      }
      if (num <= 0)
        return;
      prefixComboBox.SelectedIndex = 0;
      // ISSUE: reference to a compiler-generated field
      if (AddWizardState.\u003C\u003Eo__34.\u003C\u003Ep__1 == null)
      {
        // ISSUE: reference to a compiler-generated field
        AddWizardState.\u003C\u003Eo__34.\u003C\u003Ep__1 = CallSite<Func<CallSite, object, string>>.Create(Binder.Convert(CSharpBinderFlags.None, typeof (string), typeof (AddWizardState)));
      }
      // ISSUE: reference to a compiler-generated field
      Func<CallSite, object, string> target = AddWizardState.\u003C\u003Eo__34.\u003C\u003Ep__1.Target;
      // ISSUE: reference to a compiler-generated field
      CallSite<Func<CallSite, object, string>> p1 = AddWizardState.\u003C\u003Eo__34.\u003C\u003Ep__1;
      // ISSUE: reference to a compiler-generated field
      if (AddWizardState.\u003C\u003Eo__34.\u003C\u003Ep__0 == null)
      {
        // ISSUE: reference to a compiler-generated field
        AddWizardState.\u003C\u003Eo__34.\u003C\u003Ep__0 = CallSite<Func<CallSite, object, object>>.Create(Binder.GetMember(CSharpBinderFlags.None, "value", typeof (AddWizardState), (IEnumerable<CSharpArgumentInfo>) new CSharpArgumentInfo[1]
        {
          CSharpArgumentInfo.Create(CSharpArgumentInfoFlags.None, (string) null)
        }));
      }
      // ISSUE: reference to a compiler-generated field
      // ISSUE: reference to a compiler-generated field
      object obj = AddWizardState.\u003C\u003Eo__34.\u003C\u003Ep__0.Target((CallSite) AddWizardState.\u003C\u003Eo__34.\u003C\u003Ep__0, this.mainForm.prefixComboBox.SelectedItem);
      this.selectedBonusID = target((CallSite) p1, obj);
      this.mainForm.prefixTextBox.Lines = this.getAttributeDesc(this.selectedBonusID);
    }

    public void populateAffix(
      ComboBox cb,
      TextBox tb,
      Dictionary<string, Info> affixes,
      bool isPrefix = true)
    {
      int num = 0;
      cb.Items.Clear();
      cb.ResetText();
      if (cb.Tag != null)
        ((TextBoxBase) cb.Tag).Clear();
      foreach (KeyValuePair<string, Info> affix in affixes)
      {
        ++num;
        string str = affix.Value.cleanName() ?? Path.GetFileNameWithoutExtension(affix.Key);
        object[] objArray = new object[2]
        {
          (object) str,
          (object) affix.Value
        };
        cb.Items.Add((object) new
        {
          name = str,
          value = affix.Key
        });
      }
      if (num <= 0)
        return;
      cb.SelectedIndex = 0;
      // ISSUE: reference to a compiler-generated field
      if (AddWizardState.\u003C\u003Eo__35.\u003C\u003Ep__1 == null)
      {
        // ISSUE: reference to a compiler-generated field
        AddWizardState.\u003C\u003Eo__35.\u003C\u003Ep__1 = CallSite<Func<CallSite, object, string>>.Create(Binder.Convert(CSharpBinderFlags.None, typeof (string), typeof (AddWizardState)));
      }
      // ISSUE: reference to a compiler-generated field
      Func<CallSite, object, string> target = AddWizardState.\u003C\u003Eo__35.\u003C\u003Ep__1.Target;
      // ISSUE: reference to a compiler-generated field
      CallSite<Func<CallSite, object, string>> p1 = AddWizardState.\u003C\u003Eo__35.\u003C\u003Ep__1;
      // ISSUE: reference to a compiler-generated field
      if (AddWizardState.\u003C\u003Eo__35.\u003C\u003Ep__0 == null)
      {
        // ISSUE: reference to a compiler-generated field
        AddWizardState.\u003C\u003Eo__35.\u003C\u003Ep__0 = CallSite<Func<CallSite, object, object>>.Create(Binder.GetMember(CSharpBinderFlags.None, "value", typeof (AddWizardState), (IEnumerable<CSharpArgumentInfo>) new CSharpArgumentInfo[1]
        {
          CSharpArgumentInfo.Create(CSharpArgumentInfoFlags.None, (string) null)
        }));
      }
      // ISSUE: reference to a compiler-generated field
      // ISSUE: reference to a compiler-generated field
      object obj = AddWizardState.\u003C\u003Eo__35.\u003C\u003Ep__0.Target((CallSite) AddWizardState.\u003C\u003Eo__35.\u003C\u003Ep__0, cb.SelectedItem);
      string id = target((CallSite) p1, obj);
      if (isPrefix)
        this.selectedBonusID = id;
      else
        this.suffixID = id;
      tb.Lines = this.getAttributeDesc(id);
    }

    public void selectPrefix(string id)
    {
      this.selectedBonusID = id;
      this.mainForm.prefixTextBox.Lines = this.getAttributeDesc(this.selectedBonusID);
    }

    public void selectSuffix(string id)
    {
      this.suffixID = id;
      this.mainForm.suffixTextBox.Lines = this.getAttributeDesc(id);
    }

    private void populateItemsList()
    {
      bool flag = true;
      this.mainForm.itemSelectImagePanel.Controls.Clear();
      this.mainForm.ItemListBox.Items.Clear();
      Dictionary<string, Info> dictionary = new Dictionary<string, Info>();
      foreach (Info info in this.filteredInfo)
      {
        Button button = new Button();
        button.Tag = (object) info;
        Bitmap bitmap = Database.DB.LoadBitmap(info.Bitmap);
        if (bitmap == null)
        {
          Logger.Debug("AWS: bitmap Not found for : " + info.ItemId + " bmLocation:" + info.Bitmap);
        }
        else
        {
          string caption = info.cleanName();
          button.Width = bitmap.Width + 1;
          button.Height = bitmap.Height + 1;
          button.Image = (Image) bitmap;
          button.BackColor = Color.Transparent;
          button.ForeColor = Color.Transparent;
          button.FlatStyle = FlatStyle.Flat;
          button.FlatAppearance.MouseDownBackColor = ColorTranslator.FromHtml("#1a1d9d");
          button.FlatAppearance.MouseOverBackColor = ColorTranslator.FromHtml("#5a5ded");
          button.FlatAppearance.BorderSize = 0;
          button.MouseDown += new MouseEventHandler(this.itemSelected_MouseClick);
          this.mainForm.itemSelectImagePanel.Controls.Add((Control) button);
          this.mainForm.toolTip.SetToolTip((Control) button, caption);
          if (flag)
          {
            flag = false;
            this.itemInfo = info;
            button.BackColor = ColorTranslator.FromHtml("#1a1d9d");
            this.selectedItemButton = button;
            this.mainForm.selectItemWP.SubTitle = "Selected Item: " + caption;
          }
        }
      }
      this.filteredInfo = this.filteredInfo.OrderBy<Info, string>((Func<Info, string>) (o => o.cleanName())).ToList<Info>();
      int num = 0;
      foreach (Info info in this.filteredInfo)
      {
        Bitmap bitmap = Database.DB.LoadBitmap(info.Bitmap);
        if (bitmap == null)
        {
          Logger.Debug("AWS: bitmap Not found for : " + info.ItemId + " bmLocation:" + info.Bitmap);
        }
        else
        {
          this.mainForm.ItemListBox.Items.Add((object) new
          {
            name = info.cleanName(),
            value = info
          });
          if (this.itemInfo.ItemId.Equals(info.ItemId))
          {
            this.mainForm.pictureBox.Image = (Image) bitmap;
            this.mainForm.ItemListBox.SelectedIndex = num;
          }
          ++num;
        }
      }
    }

    public void itemSelected_MouseClick(object sender, EventArgs e)
    {
      Button button = (Button) sender;
      this.itemInfo = (Info) button.Tag;
      this.selectedItemButton.BackColor = this.brownBackColor;
      button.BackColor = ColorTranslator.FromHtml("#1a1d9d");
      this.selectedItemButton = button;
      this.mainForm.selectItemWP.SubTitle = "Selected Item: " + this.itemInfo.cleanName();
    }

    public string[] getAttributeDesc(string id)
    {
      List<string> results = new List<string>();
      this.item.GetAttributesFromRecord(Database.DB.GetRecordFromFile(id), true, id, results);
      string[] attributeDesc = new string[results.Count];
      for (int index = 0; index < results.Count; ++index)
      {
        string input = results[index];
        attributeDesc[index] = Regex.Replace(input, "<.+?>", "");
      }
      return attributeDesc;
    }

    public string getItemDescription()
    {
      string text = this.item.ToString(false, false);
      Color colorTag = this.item.GetColorTag(text);
      string str1 = Item.ClipColorTag(text);
      this.mainForm.previewItemLabel.ForeColor = colorTag;
      this.mainForm.previewItemLabel.Text = str1;
      string str2;
      try
      {
        string attributes = this.item.GetAttributes(true);
        string itemSetString = this.item.GetItemSetString();
        string requirements = this.item.GetRequirements();
        if (requirements.Length < 1)
          str2 = attributes;
        else if (itemSetString.Length < 1)
        {
          string str3 = Database.MakeSafeForHtml("?Requirements?");
          string.Format((IFormatProvider) CultureInfo.InvariantCulture, "<font size=+2 color={0}>{1}</font><br>", new object[2]
          {
            (object) Database.HtmlColor(Item.GetColor(ItemStyle.Potion)),
            (object) str3
          });
          string str4 = string.Format((IFormatProvider) CultureInfo.InvariantCulture, "<hr color={0}><br>", new object[1]
          {
            (object) Database.HtmlColor(Item.GetColor(ItemStyle.Broken))
          });
          str2 = attributes + str4 + requirements;
        }
        else
        {
          string str5 = Database.MakeSafeForHtml("?Requirements?");
          string.Format((IFormatProvider) CultureInfo.InvariantCulture, "<font size=+2 color={0}>{1}</font><br>", new object[2]
          {
            (object) Database.HtmlColor(Item.GetColor(ItemStyle.Potion)),
            (object) str5
          });
          string str6 = string.Format((IFormatProvider) CultureInfo.InvariantCulture, "<hr color={0}>", new object[1]
          {
            (object) Database.HtmlColor(Item.GetColor(ItemStyle.Broken))
          });
          string str7 = string.Format((IFormatProvider) CultureInfo.InvariantCulture, "<hr color={0}><br>", new object[1]
          {
            (object) Database.HtmlColor(Item.GetColor(ItemStyle.Broken))
          });
          str2 = attributes + str6 + itemSetString + str7 + requirements;
        }
      }
      catch (IOException ex)
      {
        Logger.Log("AddWizrdState: " + ex.ToString());
        str2 = "error getting description";
      }
      return "<html><body style='background-color:#2e291f;font-size:12'>" + str2 + "</html></body>";
    }

    public void updateSubclassUI()
    {
      if (this.page2SelectedButton != null)
        this.page2SelectedButton.BackColor = this.brownBackColor;
      if (this.itemSubType == ItemType.Armor)
      {
        this.armorGB.Visible = true;
        this.armorGB.Location = new Point(80, 80);
        this.weaponGB.Visible = false;
        this.page2SelectedButton = this.mainForm.headButton;
      }
      else
      {
        this.page2SelectedButton = this.mainForm.swordButton;
        this.armorGB.Visible = false;
        this.weaponGB.Visible = true;
      }
      this.page2SelectedButton.BackColor = this.selectedColor;
    }

    public void updateItemPage()
    {
      this.mainForm.itemSelectionTabControl.SelectedTab = this.mainForm.ImageTabPage;
      if (this.itemType != ItemType.Parchment && this.itemType != ItemType.Formula && this.itemType != ItemType.Scroll)
        return;
      this.mainForm.itemSelectionTabControl.SelectedTab = this.mainForm.listTabPage;
    }

    public void updatePrefixPage()
    {
      this.mainForm.affixCheckBox.Checked = false;
      this.mainForm.suffixComboBox.Visible = false;
      this.mainForm.suffixTextBox.Visible = false;
      this.mainForm.prefixComboBox.Visible = false;
      this.mainForm.prefixTextBox.Visible = false;
    }

    public void enableAffixControls(bool enable)
    {
      if (enable)
      {
        this.mainForm.prefixComboBox.Visible = true;
        this.mainForm.prefixTextBox.Visible = true;
        this.mainForm.suffixComboBox.Visible = true;
        this.mainForm.suffixTextBox.Visible = true;
        if (this.itemType != ItemType.Artifact && this.itemType != ItemType.Relic && this.itemType != ItemType.Charm)
          return;
        this.mainForm.suffixComboBox.Visible = false;
        this.mainForm.suffixTextBox.Visible = false;
      }
      else
      {
        this.mainForm.prefixComboBox.Visible = false;
        this.mainForm.prefixTextBox.Visible = false;
        this.mainForm.suffixComboBox.Visible = false;
        this.mainForm.suffixTextBox.Visible = false;
      }
    }

    public void updateClassRadioButtons()
    {
      this.mainForm.MIRadioButton.Visible = true;
      this.mainForm.MIRadioButton.Select();
      this.mainForm.MIRadioButton.ForeColor = Item.GetColor(ItemStyle.Rare);
      this.mainForm.MIRadioButton.Text = "Monster Infrequent";
      this.mainForm.epicRadioButton.Text = "Epic";
      this.mainForm.legendaryButton.Text = "Legendary";
      if (this.itemType == ItemType.Ring || this.itemType == ItemType.Amulet)
      {
        this.mainForm.MIRadioButton.Visible = false;
        this.mainForm.epicRadioButton.Select();
      }
      else if (this.itemType == ItemType.Relic || this.itemType == ItemType.Charm || this.itemType == ItemType.Scroll)
      {
        this.mainForm.MIRadioButton.Text = "Normal";
      }
      else
      {
        if (this.itemType != ItemType.Artifact && this.itemType != ItemType.Formula)
          return;
        this.mainForm.MIRadioButton.Text = "Lesser";
        this.mainForm.MIRadioButton.ForeColor = this.orangeColor;
        this.mainForm.epicRadioButton.Text = "Greater";
        this.mainForm.legendaryButton.Text = "Devine";
      }
    }

    public void cancel() => ((Control) this.mainForm.itemWizard).Visible = false;
  }
}
