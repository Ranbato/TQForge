// Decompiled with JetBrains decompiler
// Type: TQ_weaponsmith.Form1
// Assembly: TQ_Blacksmith, Version=0.1.4.1, Culture=neutral, PublicKeyToken=null
// MVID: 1CC9040D-95C6-4C54-B248-9D80684B2CA8
// Assembly location: D:\Games\GOG Galaxy\Games\Titan Quest - Anniversary Edition\Downloads\Blacksmith\TQ_Blacksmith.exe

using AdvancedWizardControl.Enums;
using AdvancedWizardControl.EventArguments;
using AdvancedWizardControl.Wizard;
using AdvancedWizardControl.WizardPages;
using Microsoft.CSharp.RuntimeBinder;
using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Diagnostics;
using System.Drawing;
using System.IO;
using System.Runtime.CompilerServices;
using System.Windows.Forms;
using TQ_weaponsmith.Properties;

namespace TQ_weaponsmith
{
  public class Form1 : Form
  {
    private Config cfg;
    private TQData tqData;
    public static Form1 mainForm;
    private Form settingsForm;
    private Form characterSelectionForm;
    private Form loadDataForm;
    public Sack0Form sackForm;
    private AddWizardState aws;
    private AboutBox aboutBox;
    private HelpForm helpFrm;
    private Button[] buttons = new Button[23];
    public static Font RevertFont = new Font("Microsoft Sans Serif", 11.25f);
    private IContainer components;
    private MenuStrip menuStrip1;
    private ToolStripMenuItem toolStripMenuItem1;
    private ToolStripMenuItem fileToolStripMenuItem;
    private ToolStripMenuItem settingsToolStripMenuItem;
    private ToolStripMenuItem exitToolStripMenuItem;
    private ToolStripMenuItem createToolStripMenuItem;
    private ToolStripMenuItem helpToolStripMenuItem;
    private ToolStripMenuItem usageToolStripMenuItem;
    private ToolStripMenuItem tQResourcesToolStripMenuItem;
    private ToolStripMenuItem onlineItemDatabaseToolStripMenuItem;
    private ToolStripMenuItem moddingToolsToolStripMenuItem;
    private ToolStripMenuItem youtubeStreamersToolStripMenuItem;
    private ToolStripMenuItem lineOfEpicHerosToolStripMenuItem;
    private ToolStripMenuItem clexPlayesToolStripMenuItem;
    private ToolStripMenuItem aboutToolStripMenuItem;
    private ToolStripMenuItem forgeAnItemToolStripMenuItem;
    private ToolStripMenuItem clearMainSackToolStripMenuItem;
    private ToolStripMenuItem openCharacterToolStripMenuItem;
    private ToolStripSeparator toolStripSeparator1;
    private ToolStripMenuItem saveToolStripMenuItem;
    private ToolStripMenuItem saveAsToolStripMenuItem;
    private ToolStripSeparator toolStripSeparator2;
    private Label CurrentCharacterLabel;
    private FolderBrowserDialog SaveAsFolderBrowser;
    private ToolStripMenuItem fileToolStripMenuItem1;
    private ToolStripMenuItem selectCharacterToolStripMenuItem;
    private ToolStripMenuItem saveChangesToolStripMenuItem;
    private ToolStripMenuItem saveToToolStripMenuItem;
    private ToolStripMenuItem settingsToolStripMenuItem1;
    private ToolStripMenuItem exitToolStripMenuItem1;
    private ToolStripMenuItem createItemToolStripMenuItem;
    private ToolStripMenuItem emptyTheSackToolStripMenuItem;
    private ToolStripMenuItem helpToolStripMenuItem1;
    private ToolStripMenuItem usageToolStripMenuItem1;
    private ToolStripMenuItem tQOnlineItemDatabaseToolStripMenuItem;
    private ToolStripMenuItem onlineItemDatabaseToolStripMenuItem1;
    private ToolStripMenuItem activeYoutubersToolStripMenuItem;
    private ToolStripMenuItem devToolsToolStripMenuItem;
    private ToolStripMenuItem aboutToolStripMenuItem1;
    private AdvancedWizardPage selectItemSubTypeWP;
    private AdvancedWizardPage prefixWP;
    private AdvancedWizardPage classSelectWP;
    public AdvancedWizardPage selectTypeWP;
    public GroupBox armorGroupBox;
    public GroupBox weaponGroupBox;
    private GroupBox classificationGroupBox;
    private AdvancedWizardPage previewWP;
    public Button weaponButton;
    public Button ringButton;
    public Button amuletButton;
    public Button artifactButton;
    public Button scrollButton;
    public Button formulaButton;
    public Button charmButton;
    public Button relicButton;
    public Button parchmentButton;
    public Button questItemButton;
    public Button headButton;
    public Button legButton;
    public Button armButton;
    public Button chestButton;
    public Button swordButton;
    public Button maceButton;
    public Button bowButton;
    public Button axeButton;
    public Button staffButton;
    public Button spearButton;
    public Button shieldButton;
    public Button thrownButton;
    public RadioButton legendaryButton;
    public RadioButton epicRadioButton;
    public RadioButton MIRadioButton;
    public FlowLayoutPanel itemSelectImagePanel;
    public Label itemNameLabel;
    public ComboBox prefixComboBox;
    public ComboBox suffixComboBox;
    public AdvancedWizard itemWizard;
    public Button armorButton;
    public ToolStripMenuItem smithyToolStripMenuItem;
    public ToolTip toolTip;
    public ListBox ItemListBox;
    public PictureBox pictureBox;
    public AdvancedWizardPage selectItemWP;
    public PictureBox previewItemPictureBox;
    public Label previewItemLabel;
    public WebBrowser descriptionWebBrowser;
    public TextBox prefixTextBox;
    public Label seedLabel;
    public NumericUpDown seedNUDown;
    public TextBox suffixTextBox;
    public TabPage ImageTabPage;
    public TabPage listTabPage;
    public TabControl itemSelectionTabControl;
    public Button miscButton;
    private ToolStripMenuItem theLineOfEpicHeroesToolStripMenuItem;
    private ToolStripMenuItem clexPlaysToolStripMenuItem;
    private ToolStripMenuItem titanCalcToolStripMenuItem;
    private ToolStripMenuItem modsToolStripMenuItem;
    public CheckBox affixCheckBox;

    public Form1() => this.InitializeComponent();

    public void showSack()
    {
      this.sackForm.loadStash();
      this.sackForm.Show();
    }

    public void minimizeSack() => this.sackForm.WindowState = FormWindowState.Minimized;

    public void maximizeSack() => this.sackForm.WindowState = FormWindowState.Normal;

    public void reloadSackData() => this.sackForm.loadStash();

    private void Form1_Load(object sender, EventArgs e)
    {
      Form1.mainForm = this;
      this.aboutBox = new AboutBox();
      this.helpFrm = new HelpForm();
      this.helpFrm.helpBrowser.Url = new Uri(Path.GetFullPath(".\\Data\\help.html"));
      this.settingsForm = (Form) new Settings();
      this.characterSelectionForm = (Form) new CharacterSelectionForm(this);
      this.loadDataForm = (Form) new LoadDataForm();
      this.sackForm = new Sack0Form();
      this.sackForm.MdiParent = (Form) this;
      ((Control) this.itemWizard).Visible = false;
      this.aws = new AddWizardState(this);
      this.armorGroupBox.Paint += new PaintEventHandler(this.PaintBorderlessGroupBox);
      this.classificationGroupBox.Paint += new PaintEventHandler(this.PaintBorderlessGroupBox);
      this.weaponGroupBox.Paint += new PaintEventHandler(this.PaintBorderlessGroupBox);
      Config.initialize();
      this.cfg = Config.getConfig();
      Logger.initialize(Config.getConfig().detailLogs);
      Logger.Log("==================================================");
      Logger.Log("Starting the application...");
      Logger.Log("Game save path: " + Path.GetFullPath(this.cfg.saveLocation));
    }

    private void Form1_FormClosing(object sender, FormClosingEventArgs e)
    {
      Character selectedCharacter = TQData.selectedCharacter;
      if (selectedCharacter != null && selectedCharacter.GetMainSack.IsModified && MessageBox.Show("Do you want to save changes done for the selected character?", "Alert", MessageBoxButtons.YesNo, MessageBoxIcon.Question) == DialogResult.Yes)
        selectedCharacter.Save(this.cfg.getSaveFile(selectedCharacter.CharacterName), true);
      Config.close();
      Logger.close();
    }

    private void Form1_Shown(object sender, EventArgs e)
    {
      this.showGreeting();
      this.checkSaveLocation();
      if (this.loadDataForm.ShowDialog() == DialogResult.Abort)
        this.Close();
      this.selectCharacter();
      this.initWizard();
    }

    private void initWizard() => this.aws.init();

    private void createItem()
    {
      this.minimizeSack();
      this.aws.show();
    }

    private void selectCharacter()
    {
      TQData.loadCharacterList();
      if (TQData.characterNameList != null && TQData.characterNameList.Length != 0)
      {
        int num1 = (int) this.characterSelectionForm.ShowDialog();
      }
      else
      {
        int num2 = (int) MessageBox.Show("There are no characters in main quest. Create a character in game and try to select character again.", "Alert", MessageBoxButtons.OK, MessageBoxIcon.Exclamation, MessageBoxDefaultButton.Button1);
      }
    }

    private void showGreeting()
    {
      if (Config.helpShown)
        return;
      int num = (int) this.helpFrm.ShowDialog();
      Config.helpShown = true;
      Config.saveConfig();
    }

    private void checkSaveLocation()
    {
      if (!Directory.Exists(this.cfg.saveLocation + "\\SaveData\\Main"))
      {
        Logger.Log("Couldn't find save data in '" + this.cfg.saveLocation + "'.");
        int num1 = (int) MessageBox.Show("Couldn't find save data at \"" + this.cfg.saveLocation + "\". \n\nSpecify save location in settings.", "Warning", MessageBoxButtons.OK, MessageBoxIcon.Hand);
        int num2 = (int) this.settingsForm.ShowDialog((IWin32Window) this);
      }
      if (Directory.Exists(this.cfg.installLocation + "\\Database"))
        return;
      Logger.Log("Couldn't find game installation in'" + this.cfg.installLocation + "'.");
      int num3 = (int) MessageBox.Show("Couldn't find game installation in \"" + this.cfg.installLocation + "\". \n\nSpecify install location in settings.", "Warning", MessageBoxButtons.OK, MessageBoxIcon.Hand);
      int num4 = (int) this.settingsForm.ShowDialog((IWin32Window) this);
    }

    private void PaintBorderlessGroupBox(object sender, PaintEventArgs p)
    {
      GroupBox groupBox = (GroupBox) sender;
      p.Graphics.Clear(ColorTranslator.FromHtml("#2e291f"));
      p.Graphics.DrawString(groupBox.Text, groupBox.Font, Brushes.Black, 0.0f, 0.0f);
    }

    private void aboutToolStripMenuItem_Click(object sender, EventArgs e)
    {
    }

    private void createToolStripMenuItem_Click(object sender, EventArgs e)
    {
    }

    private void settingsToolStripMenuItem_Click(object sender, EventArgs e)
    {
      int num = (int) this.settingsForm.ShowDialog((IWin32Window) this);
    }

    private void clearMainSackToolStripMenuItem_Click(object sender, EventArgs e)
    {
      if (MessageBox.Show("All the items from character weaponSmith's main sack will be removed!", "Warning", MessageBoxButtons.OKCancel, MessageBoxIcon.Exclamation) != DialogResult.OK || TQData.selectedCharacter == null)
        return;
      TQData.selectedCharacter.emptyMainSack();
      this.sackForm.loadStash();
      Logger.Log("Sack cleared");
    }

    private void openCharacterToolStripMenuItem_Click(object sender, EventArgs e) => this.selectCharacter();

    private void CurrentCharacterLabel_Click(object sender, EventArgs e)
    {
    }

    public void setCurrentCharacterLabel(string charName)
    {
      if (!string.IsNullOrWhiteSpace(charName))
      {
        if (charName.StartsWith("_"))
          charName = charName.Substring(1);
        this.CurrentCharacterLabel.Text = charName;
      }
      else
        this.CurrentCharacterLabel.Text = "select character";
    }

    private void LoddingDataLabel_Click(object sender, EventArgs e)
    {
    }

    private void exitToolStripMenuItem_Click(object sender, EventArgs e)
    {
      Character selectedCharacter = TQData.selectedCharacter;
      if (selectedCharacter != null && selectedCharacter.GetMainSack.IsModified && MessageBox.Show("Do you want to save changes done for the selected character?", "Alert", MessageBoxButtons.YesNo, MessageBoxIcon.Question) == DialogResult.Yes)
        selectedCharacter.Save(this.cfg.getSaveFile(selectedCharacter.CharacterName), true);
      this.Close();
    }

    private void folderBrowserDialog2_HelpRequest(object sender, EventArgs e)
    {
    }

    private void saveAsToolStripMenuItem_Click(object sender, EventArgs e)
    {
      Character selectedCharacter = TQData.selectedCharacter;
      if (selectedCharacter == null || this.SaveAsFolderBrowser.ShowDialog() != DialogResult.OK || string.IsNullOrWhiteSpace(this.SaveAsFolderBrowser.SelectedPath))
        return;
      string fileName = this.SaveAsFolderBrowser.SelectedPath + "\\" + selectedCharacter.CharacterName + "_Player.chr";
      selectedCharacter.Save(fileName);
      int num = (int) MessageBox.Show("Character file saved to:\n " + fileName, "Save As", MessageBoxButtons.OK, MessageBoxIcon.Asterisk);
    }

    private void saveToolStripMenuItem_Click(object sender, EventArgs e)
    {
      Character selectedCharacter = TQData.selectedCharacter;
      if (selectedCharacter == null || !selectedCharacter.GetMainSack.IsModified)
        return;
      selectedCharacter.Save(this.cfg.getSaveFile(selectedCharacter.CharacterName), true);
      int num = (int) MessageBox.Show("Character file saved!", "Save", MessageBoxButtons.OK, MessageBoxIcon.Asterisk);
    }

    private void panel1_Paint(object sender, PaintEventArgs e)
    {
    }

    private void createItemToolStripMenuItem_Click(object sender, EventArgs e) => this.createItem();

    private void advancedWizardPage1_Paint(object sender, PaintEventArgs e)
    {
    }

    private void weaponButton_Click(object sender, EventArgs e)
    {
    }

    private void itemWizard_Next(object sender, WizardEventArgs e) => this.aws.next(e);

    private void itemWizard_Cancel(object sender, EventArgs e) => this.aws.cancel();

    private void itemWizard_Back(object sender, EventArgs e) => this.aws.back(e);

    private void ItemListBox_SelectedIndexChanged(object sender, EventArgs e)
    {
      object selectedItem = ((ListBox) sender).SelectedItem;
      // ISSUE: reference to a compiler-generated field
      if (Form1.\u003C\u003Eo__45.\u003C\u003Ep__1 == null)
      {
        // ISSUE: reference to a compiler-generated field
        Form1.\u003C\u003Eo__45.\u003C\u003Ep__1 = CallSite<Func<CallSite, object, Info>>.Create(Binder.Convert(CSharpBinderFlags.None, typeof (Info), typeof (Form1)));
      }
      // ISSUE: reference to a compiler-generated field
      Func<CallSite, object, Info> target = Form1.\u003C\u003Eo__45.\u003C\u003Ep__1.Target;
      // ISSUE: reference to a compiler-generated field
      CallSite<Func<CallSite, object, Info>> p1 = Form1.\u003C\u003Eo__45.\u003C\u003Ep__1;
      // ISSUE: reference to a compiler-generated field
      if (Form1.\u003C\u003Eo__45.\u003C\u003Ep__0 == null)
      {
        // ISSUE: reference to a compiler-generated field
        Form1.\u003C\u003Eo__45.\u003C\u003Ep__0 = CallSite<Func<CallSite, object, object>>.Create(Binder.GetMember(CSharpBinderFlags.None, "value", typeof (Form1), (IEnumerable<CSharpArgumentInfo>) new CSharpArgumentInfo[1]
        {
          CSharpArgumentInfo.Create(CSharpArgumentInfoFlags.None, (string) null)
        }));
      }
      // ISSUE: reference to a compiler-generated field
      // ISSUE: reference to a compiler-generated field
      object obj = Form1.\u003C\u003Eo__45.\u003C\u003Ep__0.Target((CallSite) Form1.\u003C\u003Eo__45.\u003C\u003Ep__0, selectedItem);
      this.aws.selectItem(target((CallSite) p1, obj));
    }

    private void itemWizard_Finish(object sender, EventArgs e)
    {
      this.aws.finish();
      ((Control) Form1.mainForm.itemWizard).Visible = false;
    }

    private void prefixWP_PageShow(object sender, WizardPageEventArgs e)
    {
      this.itemWizard.FinishButtonEnabled = false;
      this.aws.updatePrefixPage();
    }

    private void selectItemWP_PageShow(object sender, WizardPageEventArgs e)
    {
      this.itemWizard.FinishButtonEnabled = false;
      this.aws.updateItemPage();
    }

    private void prefixTextBox_TextChanged(object sender, EventArgs e)
    {
    }

    private void prefixComboBox_SelectedIndexChanged(object sender, EventArgs e)
    {
      object selectedItem = ((ComboBox) sender).SelectedItem;
      // ISSUE: reference to a compiler-generated field
      if (Form1.\u003C\u003Eo__50.\u003C\u003Ep__1 == null)
      {
        // ISSUE: reference to a compiler-generated field
        Form1.\u003C\u003Eo__50.\u003C\u003Ep__1 = CallSite<Func<CallSite, object, string>>.Create(Binder.Convert(CSharpBinderFlags.None, typeof (string), typeof (Form1)));
      }
      // ISSUE: reference to a compiler-generated field
      Func<CallSite, object, string> target = Form1.\u003C\u003Eo__50.\u003C\u003Ep__1.Target;
      // ISSUE: reference to a compiler-generated field
      CallSite<Func<CallSite, object, string>> p1 = Form1.\u003C\u003Eo__50.\u003C\u003Ep__1;
      // ISSUE: reference to a compiler-generated field
      if (Form1.\u003C\u003Eo__50.\u003C\u003Ep__0 == null)
      {
        // ISSUE: reference to a compiler-generated field
        Form1.\u003C\u003Eo__50.\u003C\u003Ep__0 = CallSite<Func<CallSite, object, object>>.Create(Binder.GetMember(CSharpBinderFlags.None, "value", typeof (Form1), (IEnumerable<CSharpArgumentInfo>) new CSharpArgumentInfo[1]
        {
          CSharpArgumentInfo.Create(CSharpArgumentInfoFlags.None, (string) null)
        }));
      }
      // ISSUE: reference to a compiler-generated field
      // ISSUE: reference to a compiler-generated field
      object obj = Form1.\u003C\u003Eo__50.\u003C\u003Ep__0.Target((CallSite) Form1.\u003C\u003Eo__50.\u003C\u003Ep__0, selectedItem);
      this.aws.selectPrefix(target((CallSite) p1, obj));
    }

    private void suffixComboBox_SelectedIndexChanged(object sender, EventArgs e)
    {
      object selectedItem = ((ComboBox) sender).SelectedItem;
      // ISSUE: reference to a compiler-generated field
      if (Form1.\u003C\u003Eo__51.\u003C\u003Ep__1 == null)
      {
        // ISSUE: reference to a compiler-generated field
        Form1.\u003C\u003Eo__51.\u003C\u003Ep__1 = CallSite<Func<CallSite, object, string>>.Create(Binder.Convert(CSharpBinderFlags.None, typeof (string), typeof (Form1)));
      }
      // ISSUE: reference to a compiler-generated field
      Func<CallSite, object, string> target = Form1.\u003C\u003Eo__51.\u003C\u003Ep__1.Target;
      // ISSUE: reference to a compiler-generated field
      CallSite<Func<CallSite, object, string>> p1 = Form1.\u003C\u003Eo__51.\u003C\u003Ep__1;
      // ISSUE: reference to a compiler-generated field
      if (Form1.\u003C\u003Eo__51.\u003C\u003Ep__0 == null)
      {
        // ISSUE: reference to a compiler-generated field
        Form1.\u003C\u003Eo__51.\u003C\u003Ep__0 = CallSite<Func<CallSite, object, object>>.Create(Binder.GetMember(CSharpBinderFlags.None, "value", typeof (Form1), (IEnumerable<CSharpArgumentInfo>) new CSharpArgumentInfo[1]
        {
          CSharpArgumentInfo.Create(CSharpArgumentInfoFlags.None, (string) null)
        }));
      }
      // ISSUE: reference to a compiler-generated field
      // ISSUE: reference to a compiler-generated field
      object obj = Form1.\u003C\u003Eo__51.\u003C\u003Ep__0.Target((CallSite) Form1.\u003C\u003Eo__51.\u003C\u003Ep__0, selectedItem);
      this.aws.selectSuffix(target((CallSite) p1, obj));
    }

    private void classSelectWP_PageShow(object sender, WizardPageEventArgs e) => this.aws.updateClassRadioButtons();

    private void selectItemSubTypeWP_PageShow(object sender, WizardPageEventArgs e) => this.aws.updateSubclassUI();

    private void onlineItemDatabaseToolStripMenuItem1_Click(object sender, EventArgs e) => this.openUrlSafe("https://www.tq-db.net");

    private void theLineOfEpicHeroesToolStripMenuItem_Click(object sender, EventArgs e) => this.openUrlSafe("https://www.youtube.com/channel/UCKEtunnaOrAW-s_7N0xpq1g");

    private void clexPlaysToolStripMenuItem_Click(object sender, EventArgs e) => this.openUrlSafe(" https://www.youtube.com/channel/UCWP2ABMB20FD2N_-ochNERQ");

    private void devToolsToolStripMenuItem_Click(object sender, EventArgs e) => this.openUrlSafe("https://titanquest.4fansites.de/downloads,1,Tools.html");

    private void titanCalcToolStripMenuItem_Click(object sender, EventArgs e) => this.openUrlSafe(" http://www.titancalc.com/");

    private void modsToolStripMenuItem_Click(object sender, EventArgs e) => this.openUrlSafe(" https://www.kirmiziperfect.com/category/titan-quest-anniversary-edition/");

    private void aboutToolStripMenuItem1_Click(object sender, EventArgs e)
    {
      int num = (int) this.aboutBox.ShowDialog();
    }

    private void openUrlSafe(string url)
    {
      try
      {
        Process.Start("IEXPLORE", url);
      }
      catch (Exception ex)
      {
        Logger.Log("Error openning url: " + ex.ToString());
      }
    }

    private void usageToolStripMenuItem1_Click(object sender, EventArgs e)
    {
      int num = (int) this.helpFrm.ShowDialog();
    }

    private void affixCheckBox_CheckedChanged(object sender, EventArgs e) => this.aws.enableAffixControls(this.affixCheckBox.Checked);

    protected override void Dispose(bool disposing)
    {
      if (disposing && this.components != null)
        this.components.Dispose();
      base.Dispose(disposing);
    }

    private void InitializeComponent()
    {
      this.components = (IContainer) new Container();
      ComponentResourceManager componentResourceManager = new ComponentResourceManager(typeof (Form1));
      this.menuStrip1 = new MenuStrip();
      this.fileToolStripMenuItem1 = new ToolStripMenuItem();
      this.selectCharacterToolStripMenuItem = new ToolStripMenuItem();
      this.saveChangesToolStripMenuItem = new ToolStripMenuItem();
      this.saveToToolStripMenuItem = new ToolStripMenuItem();
      this.settingsToolStripMenuItem1 = new ToolStripMenuItem();
      this.exitToolStripMenuItem1 = new ToolStripMenuItem();
      this.smithyToolStripMenuItem = new ToolStripMenuItem();
      this.createItemToolStripMenuItem = new ToolStripMenuItem();
      this.emptyTheSackToolStripMenuItem = new ToolStripMenuItem();
      this.helpToolStripMenuItem1 = new ToolStripMenuItem();
      this.usageToolStripMenuItem1 = new ToolStripMenuItem();
      this.tQOnlineItemDatabaseToolStripMenuItem = new ToolStripMenuItem();
      this.onlineItemDatabaseToolStripMenuItem1 = new ToolStripMenuItem();
      this.titanCalcToolStripMenuItem = new ToolStripMenuItem();
      this.activeYoutubersToolStripMenuItem = new ToolStripMenuItem();
      this.theLineOfEpicHeroesToolStripMenuItem = new ToolStripMenuItem();
      this.clexPlaysToolStripMenuItem = new ToolStripMenuItem();
      this.devToolsToolStripMenuItem = new ToolStripMenuItem();
      this.modsToolStripMenuItem = new ToolStripMenuItem();
      this.aboutToolStripMenuItem1 = new ToolStripMenuItem();
      this.toolStripMenuItem1 = new ToolStripMenuItem();
      this.fileToolStripMenuItem = new ToolStripMenuItem();
      this.openCharacterToolStripMenuItem = new ToolStripMenuItem();
      this.toolStripSeparator1 = new ToolStripSeparator();
      this.saveToolStripMenuItem = new ToolStripMenuItem();
      this.saveAsToolStripMenuItem = new ToolStripMenuItem();
      this.toolStripSeparator2 = new ToolStripSeparator();
      this.settingsToolStripMenuItem = new ToolStripMenuItem();
      this.exitToolStripMenuItem = new ToolStripMenuItem();
      this.createToolStripMenuItem = new ToolStripMenuItem();
      this.forgeAnItemToolStripMenuItem = new ToolStripMenuItem();
      this.clearMainSackToolStripMenuItem = new ToolStripMenuItem();
      this.helpToolStripMenuItem = new ToolStripMenuItem();
      this.usageToolStripMenuItem = new ToolStripMenuItem();
      this.tQResourcesToolStripMenuItem = new ToolStripMenuItem();
      this.onlineItemDatabaseToolStripMenuItem = new ToolStripMenuItem();
      this.moddingToolsToolStripMenuItem = new ToolStripMenuItem();
      this.youtubeStreamersToolStripMenuItem = new ToolStripMenuItem();
      this.lineOfEpicHerosToolStripMenuItem = new ToolStripMenuItem();
      this.clexPlayesToolStripMenuItem = new ToolStripMenuItem();
      this.aboutToolStripMenuItem = new ToolStripMenuItem();
      this.CurrentCharacterLabel = new Label();
      this.SaveAsFolderBrowser = new FolderBrowserDialog();
      this.itemWizard = new AdvancedWizard();
      this.prefixWP = new AdvancedWizardPage();
      this.affixCheckBox = new CheckBox();
      this.suffixTextBox = new TextBox();
      this.prefixTextBox = new TextBox();
      this.suffixComboBox = new ComboBox();
      this.prefixComboBox = new ComboBox();
      this.itemNameLabel = new Label();
      this.selectItemWP = new AdvancedWizardPage();
      this.seedLabel = new Label();
      this.seedNUDown = new NumericUpDown();
      this.itemSelectionTabControl = new TabControl();
      this.ImageTabPage = new TabPage();
      this.itemSelectImagePanel = new FlowLayoutPanel();
      this.listTabPage = new TabPage();
      this.ItemListBox = new ListBox();
      this.pictureBox = new PictureBox();
      this.classSelectWP = new AdvancedWizardPage();
      this.classificationGroupBox = new GroupBox();
      this.legendaryButton = new RadioButton();
      this.epicRadioButton = new RadioButton();
      this.MIRadioButton = new RadioButton();
      this.selectItemSubTypeWP = new AdvancedWizardPage();
      this.weaponGroupBox = new GroupBox();
      this.thrownButton = new Button();
      this.shieldButton = new Button();
      this.staffButton = new Button();
      this.spearButton = new Button();
      this.maceButton = new Button();
      this.bowButton = new Button();
      this.axeButton = new Button();
      this.swordButton = new Button();
      this.armorGroupBox = new GroupBox();
      this.headButton = new Button();
      this.legButton = new Button();
      this.armButton = new Button();
      this.chestButton = new Button();
      this.selectTypeWP = new AdvancedWizardPage();
      this.miscButton = new Button();
      this.armorButton = new Button();
      this.questItemButton = new Button();
      this.parchmentButton = new Button();
      this.artifactButton = new Button();
      this.scrollButton = new Button();
      this.formulaButton = new Button();
      this.charmButton = new Button();
      this.relicButton = new Button();
      this.amuletButton = new Button();
      this.ringButton = new Button();
      this.weaponButton = new Button();
      this.previewWP = new AdvancedWizardPage();
      this.descriptionWebBrowser = new WebBrowser();
      this.previewItemLabel = new Label();
      this.previewItemPictureBox = new PictureBox();
      this.toolTip = new ToolTip(this.components);
      this.menuStrip1.SuspendLayout();
      ((Control) this.itemWizard).SuspendLayout();
      ((Control) this.prefixWP).SuspendLayout();
      ((Control) this.selectItemWP).SuspendLayout();
      this.seedNUDown.BeginInit();
      this.itemSelectionTabControl.SuspendLayout();
      this.ImageTabPage.SuspendLayout();
      this.listTabPage.SuspendLayout();
      ((ISupportInitialize) this.pictureBox).BeginInit();
      ((Control) this.classSelectWP).SuspendLayout();
      this.classificationGroupBox.SuspendLayout();
      ((Control) this.selectItemSubTypeWP).SuspendLayout();
      this.weaponGroupBox.SuspendLayout();
      this.armorGroupBox.SuspendLayout();
      ((Control) this.selectTypeWP).SuspendLayout();
      ((Control) this.previewWP).SuspendLayout();
      ((ISupportInitialize) this.previewItemPictureBox).BeginInit();
      this.SuspendLayout();
      this.menuStrip1.ImageScalingSize = new Size(20, 20);
      this.menuStrip1.Items.AddRange(new ToolStripItem[3]
      {
        (ToolStripItem) this.fileToolStripMenuItem1,
        (ToolStripItem) this.smithyToolStripMenuItem,
        (ToolStripItem) this.helpToolStripMenuItem1
      });
      this.menuStrip1.Location = new Point(0, 0);
      this.menuStrip1.Name = "menuStrip1";
      this.menuStrip1.Padding = new Padding(8, 2, 0, 2);
      this.menuStrip1.Size = new Size(1188, 28);
      this.menuStrip1.TabIndex = 0;
      this.menuStrip1.Text = "menuStrip1";
      this.fileToolStripMenuItem1.DropDownItems.AddRange(new ToolStripItem[5]
      {
        (ToolStripItem) this.selectCharacterToolStripMenuItem,
        (ToolStripItem) this.saveChangesToolStripMenuItem,
        (ToolStripItem) this.saveToToolStripMenuItem,
        (ToolStripItem) this.settingsToolStripMenuItem1,
        (ToolStripItem) this.exitToolStripMenuItem1
      });
      this.fileToolStripMenuItem1.Name = "fileToolStripMenuItem1";
      this.fileToolStripMenuItem1.Size = new Size(44, 24);
      this.fileToolStripMenuItem1.Text = "File";
      this.selectCharacterToolStripMenuItem.Name = "selectCharacterToolStripMenuItem";
      this.selectCharacterToolStripMenuItem.Size = new Size(191, 26);
      this.selectCharacterToolStripMenuItem.Text = "Select Character";
      this.selectCharacterToolStripMenuItem.Click += new EventHandler(this.openCharacterToolStripMenuItem_Click);
      this.saveChangesToolStripMenuItem.Name = "saveChangesToolStripMenuItem";
      this.saveChangesToolStripMenuItem.Size = new Size(191, 26);
      this.saveChangesToolStripMenuItem.Text = "Save changes";
      this.saveChangesToolStripMenuItem.Click += new EventHandler(this.saveToolStripMenuItem_Click);
      this.saveToToolStripMenuItem.Name = "saveToToolStripMenuItem";
      this.saveToToolStripMenuItem.Size = new Size(191, 26);
      this.saveToToolStripMenuItem.Text = "Save to...";
      this.saveToToolStripMenuItem.Click += new EventHandler(this.saveAsToolStripMenuItem_Click);
      this.settingsToolStripMenuItem1.Name = "settingsToolStripMenuItem1";
      this.settingsToolStripMenuItem1.Size = new Size(191, 26);
      this.settingsToolStripMenuItem1.Text = "Settings";
      this.settingsToolStripMenuItem1.Click += new EventHandler(this.settingsToolStripMenuItem_Click);
      this.exitToolStripMenuItem1.Name = "exitToolStripMenuItem1";
      this.exitToolStripMenuItem1.Size = new Size(191, 26);
      this.exitToolStripMenuItem1.Text = "Exit";
      this.exitToolStripMenuItem1.Click += new EventHandler(this.exitToolStripMenuItem_Click);
      this.smithyToolStripMenuItem.DropDownItems.AddRange(new ToolStripItem[2]
      {
        (ToolStripItem) this.createItemToolStripMenuItem,
        (ToolStripItem) this.emptyTheSackToolStripMenuItem
      });
      this.smithyToolStripMenuItem.Enabled = false;
      this.smithyToolStripMenuItem.Name = "smithyToolStripMenuItem";
      this.smithyToolStripMenuItem.Size = new Size(66, 24);
      this.smithyToolStripMenuItem.Text = "Smithy";
      this.createItemToolStripMenuItem.Name = "createItemToolStripMenuItem";
      this.createItemToolStripMenuItem.Size = new Size(183, 26);
      this.createItemToolStripMenuItem.Text = "Create Item";
      this.createItemToolStripMenuItem.Click += new EventHandler(this.createItemToolStripMenuItem_Click);
      this.emptyTheSackToolStripMenuItem.Name = "emptyTheSackToolStripMenuItem";
      this.emptyTheSackToolStripMenuItem.Size = new Size(183, 26);
      this.emptyTheSackToolStripMenuItem.Text = "Empty the sack";
      this.emptyTheSackToolStripMenuItem.Click += new EventHandler(this.clearMainSackToolStripMenuItem_Click);
      this.helpToolStripMenuItem1.DropDownItems.AddRange(new ToolStripItem[3]
      {
        (ToolStripItem) this.usageToolStripMenuItem1,
        (ToolStripItem) this.tQOnlineItemDatabaseToolStripMenuItem,
        (ToolStripItem) this.aboutToolStripMenuItem1
      });
      this.helpToolStripMenuItem1.Name = "helpToolStripMenuItem1";
      this.helpToolStripMenuItem1.Size = new Size(53, 24);
      this.helpToolStripMenuItem1.Text = "Help";
      this.usageToolStripMenuItem1.Name = "usageToolStripMenuItem1";
      this.usageToolStripMenuItem1.Size = new Size(173, 26);
      this.usageToolStripMenuItem1.Text = "Usage";
      this.usageToolStripMenuItem1.Click += new EventHandler(this.usageToolStripMenuItem1_Click);
      this.tQOnlineItemDatabaseToolStripMenuItem.DropDownItems.AddRange(new ToolStripItem[5]
      {
        (ToolStripItem) this.onlineItemDatabaseToolStripMenuItem1,
        (ToolStripItem) this.titanCalcToolStripMenuItem,
        (ToolStripItem) this.activeYoutubersToolStripMenuItem,
        (ToolStripItem) this.devToolsToolStripMenuItem,
        (ToolStripItem) this.modsToolStripMenuItem
      });
      this.tQOnlineItemDatabaseToolStripMenuItem.Name = "tQOnlineItemDatabaseToolStripMenuItem";
      this.tQOnlineItemDatabaseToolStripMenuItem.Size = new Size(173, 26);
      this.tQOnlineItemDatabaseToolStripMenuItem.Text = "TQ Resources";
      this.onlineItemDatabaseToolStripMenuItem1.Name = "onlineItemDatabaseToolStripMenuItem1";
      this.onlineItemDatabaseToolStripMenuItem1.Size = new Size(228, 26);
      this.onlineItemDatabaseToolStripMenuItem1.Text = "Online Item Database";
      this.onlineItemDatabaseToolStripMenuItem1.Click += new EventHandler(this.onlineItemDatabaseToolStripMenuItem1_Click);
      this.titanCalcToolStripMenuItem.Name = "titanCalcToolStripMenuItem";
      this.titanCalcToolStripMenuItem.Size = new Size(228, 26);
      this.titanCalcToolStripMenuItem.Text = "Titan Calc";
      this.titanCalcToolStripMenuItem.Click += new EventHandler(this.titanCalcToolStripMenuItem_Click);
      this.activeYoutubersToolStripMenuItem.DropDownItems.AddRange(new ToolStripItem[2]
      {
        (ToolStripItem) this.theLineOfEpicHeroesToolStripMenuItem,
        (ToolStripItem) this.clexPlaysToolStripMenuItem
      });
      this.activeYoutubersToolStripMenuItem.Name = "activeYoutubersToolStripMenuItem";
      this.activeYoutubersToolStripMenuItem.Size = new Size(228, 26);
      this.activeYoutubersToolStripMenuItem.Text = "Active Youtubers";
      this.theLineOfEpicHeroesToolStripMenuItem.Name = "theLineOfEpicHeroesToolStripMenuItem";
      this.theLineOfEpicHeroesToolStripMenuItem.Size = new Size(240, 26);
      this.theLineOfEpicHeroesToolStripMenuItem.Text = "The Line of Epic Heroes";
      this.theLineOfEpicHeroesToolStripMenuItem.Click += new EventHandler(this.theLineOfEpicHeroesToolStripMenuItem_Click);
      this.clexPlaysToolStripMenuItem.Name = "clexPlaysToolStripMenuItem";
      this.clexPlaysToolStripMenuItem.Size = new Size(240, 26);
      this.clexPlaysToolStripMenuItem.Text = "Clex Plays";
      this.clexPlaysToolStripMenuItem.Click += new EventHandler(this.clexPlaysToolStripMenuItem_Click);
      this.devToolsToolStripMenuItem.Name = "devToolsToolStripMenuItem";
      this.devToolsToolStripMenuItem.Size = new Size(228, 26);
      this.devToolsToolStripMenuItem.Text = "Dev Tools";
      this.devToolsToolStripMenuItem.Click += new EventHandler(this.devToolsToolStripMenuItem_Click);
      this.modsToolStripMenuItem.Name = "modsToolStripMenuItem";
      this.modsToolStripMenuItem.Size = new Size(228, 26);
      this.modsToolStripMenuItem.Text = "Mods";
      this.modsToolStripMenuItem.Click += new EventHandler(this.modsToolStripMenuItem_Click);
      this.aboutToolStripMenuItem1.Name = "aboutToolStripMenuItem1";
      this.aboutToolStripMenuItem1.Size = new Size(173, 26);
      this.aboutToolStripMenuItem1.Text = "About";
      this.aboutToolStripMenuItem1.Click += new EventHandler(this.aboutToolStripMenuItem1_Click);
      this.toolStripMenuItem1.Name = "toolStripMenuItem1";
      this.toolStripMenuItem1.Size = new Size(12, 20);
      this.fileToolStripMenuItem.DropDownItems.AddRange(new ToolStripItem[7]
      {
        (ToolStripItem) this.openCharacterToolStripMenuItem,
        (ToolStripItem) this.toolStripSeparator1,
        (ToolStripItem) this.saveToolStripMenuItem,
        (ToolStripItem) this.saveAsToolStripMenuItem,
        (ToolStripItem) this.toolStripSeparator2,
        (ToolStripItem) this.settingsToolStripMenuItem,
        (ToolStripItem) this.exitToolStripMenuItem
      });
      this.fileToolStripMenuItem.Name = "fileToolStripMenuItem";
      this.fileToolStripMenuItem.Size = new Size(37, 20);
      this.fileToolStripMenuItem.Text = "File";
      this.openCharacterToolStripMenuItem.Name = "openCharacterToolStripMenuItem";
      this.openCharacterToolStripMenuItem.Size = new Size(191, 26);
      this.openCharacterToolStripMenuItem.Text = "Select Character";
      this.openCharacterToolStripMenuItem.Click += new EventHandler(this.openCharacterToolStripMenuItem_Click);
      this.toolStripSeparator1.Name = "toolStripSeparator1";
      this.toolStripSeparator1.Size = new Size(188, 6);
      this.saveToolStripMenuItem.Name = "saveToolStripMenuItem";
      this.saveToolStripMenuItem.Size = new Size(191, 26);
      this.saveToolStripMenuItem.Text = "Save";
      this.saveToolStripMenuItem.Click += new EventHandler(this.saveToolStripMenuItem_Click);
      this.saveAsToolStripMenuItem.Name = "saveAsToolStripMenuItem";
      this.saveAsToolStripMenuItem.Size = new Size(191, 26);
      this.saveAsToolStripMenuItem.Text = "Save as...";
      this.saveAsToolStripMenuItem.Click += new EventHandler(this.saveAsToolStripMenuItem_Click);
      this.toolStripSeparator2.Name = "toolStripSeparator2";
      this.toolStripSeparator2.Size = new Size(188, 6);
      this.settingsToolStripMenuItem.Name = "settingsToolStripMenuItem";
      this.settingsToolStripMenuItem.Size = new Size(191, 26);
      this.settingsToolStripMenuItem.Text = "Settings";
      this.settingsToolStripMenuItem.Click += new EventHandler(this.settingsToolStripMenuItem_Click);
      this.exitToolStripMenuItem.Name = "exitToolStripMenuItem";
      this.exitToolStripMenuItem.Size = new Size(191, 26);
      this.exitToolStripMenuItem.Text = "Exit";
      this.exitToolStripMenuItem.Click += new EventHandler(this.exitToolStripMenuItem_Click);
      this.createToolStripMenuItem.DropDownItems.AddRange(new ToolStripItem[2]
      {
        (ToolStripItem) this.forgeAnItemToolStripMenuItem,
        (ToolStripItem) this.clearMainSackToolStripMenuItem
      });
      this.createToolStripMenuItem.Name = "createToolStripMenuItem";
      this.createToolStripMenuItem.Size = new Size(56, 20);
      this.createToolStripMenuItem.Text = "Smithy";
      this.createToolStripMenuItem.Click += new EventHandler(this.createToolStripMenuItem_Click);
      this.forgeAnItemToolStripMenuItem.Name = "forgeAnItemToolStripMenuItem";
      this.forgeAnItemToolStripMenuItem.Size = new Size(187, 26);
      this.clearMainSackToolStripMenuItem.Name = "clearMainSackToolStripMenuItem";
      this.clearMainSackToolStripMenuItem.Size = new Size(187, 26);
      this.clearMainSackToolStripMenuItem.Text = "Clear main sack";
      this.clearMainSackToolStripMenuItem.Click += new EventHandler(this.clearMainSackToolStripMenuItem_Click);
      this.helpToolStripMenuItem.DropDownItems.AddRange(new ToolStripItem[3]
      {
        (ToolStripItem) this.usageToolStripMenuItem,
        (ToolStripItem) this.tQResourcesToolStripMenuItem,
        (ToolStripItem) this.aboutToolStripMenuItem
      });
      this.helpToolStripMenuItem.Name = "helpToolStripMenuItem";
      this.helpToolStripMenuItem.Size = new Size(44, 20);
      this.helpToolStripMenuItem.Text = "Help";
      this.usageToolStripMenuItem.Name = "usageToolStripMenuItem";
      this.usageToolStripMenuItem.Size = new Size(173, 26);
      this.usageToolStripMenuItem.Text = "Usage";
      this.tQResourcesToolStripMenuItem.DropDownItems.AddRange(new ToolStripItem[3]
      {
        (ToolStripItem) this.onlineItemDatabaseToolStripMenuItem,
        (ToolStripItem) this.moddingToolsToolStripMenuItem,
        (ToolStripItem) this.youtubeStreamersToolStripMenuItem
      });
      this.tQResourcesToolStripMenuItem.Name = "tQResourcesToolStripMenuItem";
      this.tQResourcesToolStripMenuItem.Size = new Size(173, 26);
      this.tQResourcesToolStripMenuItem.Text = "TQ Resources";
      this.onlineItemDatabaseToolStripMenuItem.Name = "onlineItemDatabaseToolStripMenuItem";
      this.onlineItemDatabaseToolStripMenuItem.Size = new Size(228, 26);
      this.onlineItemDatabaseToolStripMenuItem.Text = "Online Item Database";
      this.moddingToolsToolStripMenuItem.Name = "moddingToolsToolStripMenuItem";
      this.moddingToolsToolStripMenuItem.Size = new Size(228, 26);
      this.moddingToolsToolStripMenuItem.Text = "Modding Tools";
      this.youtubeStreamersToolStripMenuItem.DropDownItems.AddRange(new ToolStripItem[2]
      {
        (ToolStripItem) this.lineOfEpicHerosToolStripMenuItem,
        (ToolStripItem) this.clexPlayesToolStripMenuItem
      });
      this.youtubeStreamersToolStripMenuItem.Name = "youtubeStreamersToolStripMenuItem";
      this.youtubeStreamersToolStripMenuItem.Size = new Size(228, 26);
      this.youtubeStreamersToolStripMenuItem.Text = "Youtube streamers";
      this.lineOfEpicHerosToolStripMenuItem.Name = "lineOfEpicHerosToolStripMenuItem";
      this.lineOfEpicHerosToolStripMenuItem.Size = new Size(232, 26);
      this.lineOfEpicHerosToolStripMenuItem.Text = "The Line of Epic Heros";
      this.clexPlayesToolStripMenuItem.Name = "clexPlayesToolStripMenuItem";
      this.clexPlayesToolStripMenuItem.Size = new Size(232, 26);
      this.clexPlayesToolStripMenuItem.Text = "Clex Playes";
      this.aboutToolStripMenuItem.Name = "aboutToolStripMenuItem";
      this.aboutToolStripMenuItem.Size = new Size(173, 26);
      this.aboutToolStripMenuItem.Text = "About";
      this.CurrentCharacterLabel.AutoSize = true;
      this.CurrentCharacterLabel.BackColor = Color.Black;
      this.CurrentCharacterLabel.Font = new Font("Microsoft Sans Serif", 12f, FontStyle.Bold, GraphicsUnit.Point, (byte) 0);
      this.CurrentCharacterLabel.ForeColor = Color.YellowGreen;
      this.CurrentCharacterLabel.Location = new Point(905, 49);
      this.CurrentCharacterLabel.Margin = new Padding(4, 0, 4, 0);
      this.CurrentCharacterLabel.Name = "CurrentCharacterLabel";
      this.CurrentCharacterLabel.RightToLeft = RightToLeft.Yes;
      this.CurrentCharacterLabel.Size = new Size(174, 25);
      this.CurrentCharacterLabel.TabIndex = 1;
      this.CurrentCharacterLabel.Text = "Select Character";
      this.CurrentCharacterLabel.Click += new EventHandler(this.CurrentCharacterLabel_Click);
      this.SaveAsFolderBrowser.Description = "Save Player.chr";
      this.SaveAsFolderBrowser.RootFolder = Environment.SpecialFolder.MyComputer;
      this.SaveAsFolderBrowser.Tag = (object) "Save Player.chr";
      this.SaveAsFolderBrowser.HelpRequest += new EventHandler(this.folderBrowserDialog2_HelpRequest);
      this.itemWizard.BackButtonEnabled = true;
      this.itemWizard.BackButtonText = "< Back";
      ((UserControl) this.itemWizard).BorderStyle = BorderStyle.FixedSingle;
      this.itemWizard.ButtonLayout = ButtonLayoutKind.Default;
      this.itemWizard.ButtonsVisible = true;
      this.itemWizard.CancelButtonText = "&Cancel";
      ((Control) this.itemWizard).Controls.Add((Control) this.prefixWP);
      ((Control) this.itemWizard).Controls.Add((Control) this.selectItemWP);
      ((Control) this.itemWizard).Controls.Add((Control) this.classSelectWP);
      ((Control) this.itemWizard).Controls.Add((Control) this.selectItemSubTypeWP);
      ((Control) this.itemWizard).Controls.Add((Control) this.selectTypeWP);
      ((Control) this.itemWizard).Controls.Add((Control) this.previewWP);
      this.itemWizard.CurrentPageIsFinishPage = false;
      this.itemWizard.FinishButton = true;
      this.itemWizard.FinishButtonEnabled = true;
      this.itemWizard.FinishButtonText = "Add to Sack";
      this.itemWizard.FlatStyle = FlatStyle.Standard;
      this.itemWizard.HelpButton = false;
      this.itemWizard.HelpButtonText = "&Help";
      ((Control) this.itemWizard).Location = new Point(249, 78);
      ((Control) this.itemWizard).Margin = new Padding(4);
      ((Control) this.itemWizard).Name = "itemWizard";
      this.itemWizard.NextButtonEnabled = true;
      this.itemWizard.NextButtonText = "Next >";
      this.itemWizard.ProcessKeys = false;
      ((Control) this.itemWizard).Size = new Size(706, 432);
      ((Control) this.itemWizard).TabIndex = 3;
      this.itemWizard.TouchScreen = false;
      this.itemWizard.WizardPages.Add(this.selectTypeWP);
      this.itemWizard.WizardPages.Add(this.selectItemSubTypeWP);
      this.itemWizard.WizardPages.Add(this.classSelectWP);
      this.itemWizard.WizardPages.Add(this.selectItemWP);
      this.itemWizard.WizardPages.Add(this.prefixWP);
      this.itemWizard.WizardPages.Add(this.previewWP);
      this.itemWizard.Cancel += new EventHandler(this.itemWizard_Cancel);
      this.itemWizard.Next += new EventHandler<WizardEventArgs>(this.itemWizard_Next);
      this.itemWizard.Back += new EventHandler(this.itemWizard_Back);
      this.itemWizard.Finish += new EventHandler(this.itemWizard_Finish);
      ((Control) this.prefixWP).BackColor = Color.FromArgb(46, 41, 31);
      ((Control) this.prefixWP).Controls.Add((Control) this.affixCheckBox);
      ((Control) this.prefixWP).Controls.Add((Control) this.suffixTextBox);
      ((Control) this.prefixWP).Controls.Add((Control) this.prefixTextBox);
      ((Control) this.prefixWP).Controls.Add((Control) this.suffixComboBox);
      ((Control) this.prefixWP).Controls.Add((Control) this.prefixComboBox);
      ((Control) this.prefixWP).Controls.Add((Control) this.itemNameLabel);
      ((Control) this.prefixWP).Dock = DockStyle.Fill;
      this.prefixWP.Header = true;
      this.prefixWP.HeaderBackgroundColor = Color.White;
      this.prefixWP.HeaderFont = new Font("Tahoma", 10f, FontStyle.Bold);
      this.prefixWP.HeaderImage = (Image) Resources.blackSmith1;
      this.prefixWP.HeaderImageVisible = true;
      this.prefixWP.HeaderTitle = "Select prefixe and suffixe for the item";
      ((Control) this.prefixWP).Location = new Point(0, 0);
      ((Control) this.prefixWP).Margin = new Padding(4);
      ((Control) this.prefixWP).Name = "prefixWP";
      this.prefixWP.PreviousPage = 3;
      ((Control) this.prefixWP).Size = new Size(704, 390);
      this.prefixWP.SubTitle = "";
      this.prefixWP.SubTitleFont = new Font("Tahoma", 8f);
      ((Control) this.prefixWP).TabIndex = 5;
      this.prefixWP.PageShow += new EventHandler<WizardPageEventArgs>(this.prefixWP_PageShow);
      this.affixCheckBox.AutoSize = true;
      this.affixCheckBox.Font = new Font("Microsoft Sans Serif", 9f, FontStyle.Bold, GraphicsUnit.Point, (byte) 0);
      this.affixCheckBox.ForeColor = Color.White;
      this.affixCheckBox.Location = new Point(64, 103);
      this.affixCheckBox.Name = "affixCheckBox";
      this.affixCheckBox.Size = new Size(112, 22);
      this.affixCheckBox.TabIndex = 8;
      this.affixCheckBox.Text = "Add affixes";
      this.affixCheckBox.UseVisualStyleBackColor = true;
      this.affixCheckBox.CheckedChanged += new EventHandler(this.affixCheckBox_CheckedChanged);
      this.suffixTextBox.BackColor = Color.FromArgb(46, 41, 31);
      this.suffixTextBox.Font = new Font("Microsoft Sans Serif", 8.25f, FontStyle.Bold, GraphicsUnit.Point, (byte) 0);
      this.suffixTextBox.ForeColor = Color.LightSteelBlue;
      this.suffixTextBox.Location = new Point(423, 222);
      this.suffixTextBox.Margin = new Padding(4);
      this.suffixTextBox.Multiline = true;
      this.suffixTextBox.Name = "suffixTextBox";
      this.suffixTextBox.ReadOnly = true;
      this.suffixTextBox.Size = new Size(268, 104);
      this.suffixTextBox.TabIndex = 7;
      this.prefixTextBox.BackColor = Color.FromArgb(46, 41, 31);
      this.prefixTextBox.Font = new Font("Microsoft Sans Serif", 8.25f, FontStyle.Bold, GraphicsUnit.Point, (byte) 0);
      this.prefixTextBox.ForeColor = Color.LightSteelBlue;
      this.prefixTextBox.Location = new Point(11, 222);
      this.prefixTextBox.Margin = new Padding(4);
      this.prefixTextBox.Multiline = true;
      this.prefixTextBox.Name = "prefixTextBox";
      this.prefixTextBox.ReadOnly = true;
      this.prefixTextBox.Size = new Size(268, 104);
      this.prefixTextBox.TabIndex = 6;
      this.prefixTextBox.TextChanged += new EventHandler(this.prefixTextBox_TextChanged);
      this.suffixComboBox.BackColor = Color.FromArgb(46, 41, 31);
      this.suffixComboBox.ForeColor = Color.GreenYellow;
      this.suffixComboBox.FormattingEnabled = true;
      this.suffixComboBox.Location = new Point(423, 183);
      this.suffixComboBox.Margin = new Padding(4);
      this.suffixComboBox.Name = "suffixComboBox";
      this.suffixComboBox.Size = new Size(268, 24);
      this.suffixComboBox.Sorted = true;
      this.suffixComboBox.TabIndex = 3;
      this.suffixComboBox.SelectedIndexChanged += new EventHandler(this.suffixComboBox_SelectedIndexChanged);
      this.prefixComboBox.BackColor = Color.FromArgb(46, 41, 31);
      this.prefixComboBox.ForeColor = Color.GreenYellow;
      this.prefixComboBox.FormattingEnabled = true;
      this.prefixComboBox.Location = new Point(11, 185);
      this.prefixComboBox.Margin = new Padding(4);
      this.prefixComboBox.Name = "prefixComboBox";
      this.prefixComboBox.Size = new Size(268, 24);
      this.prefixComboBox.Sorted = true;
      this.prefixComboBox.TabIndex = 2;
      this.prefixComboBox.SelectedIndexChanged += new EventHandler(this.prefixComboBox_SelectedIndexChanged);
      this.itemNameLabel.AutoSize = true;
      this.itemNameLabel.Font = new Font("Microsoft Sans Serif", 10f, FontStyle.Bold, GraphicsUnit.Point, (byte) 0);
      this.itemNameLabel.ForeColor = Color.Silver;
      this.itemNameLabel.Location = new Point(236, 134);
      this.itemNameLabel.Margin = new Padding(4, 0, 4, 0);
      this.itemNameLabel.Name = "itemNameLabel";
      this.itemNameLabel.Size = new Size(99, 20);
      this.itemNameLabel.TabIndex = 1;
      this.itemNameLabel.Text = "Item Name";
      this.itemNameLabel.TextAlign = ContentAlignment.MiddleCenter;
      ((Control) this.selectItemWP).BackColor = Color.FromArgb(46, 41, 31);
      ((Control) this.selectItemWP).Controls.Add((Control) this.seedLabel);
      ((Control) this.selectItemWP).Controls.Add((Control) this.seedNUDown);
      ((Control) this.selectItemWP).Controls.Add((Control) this.itemSelectionTabControl);
      ((Control) this.selectItemWP).Dock = DockStyle.Fill;
      this.selectItemWP.Header = true;
      this.selectItemWP.HeaderBackgroundColor = Color.White;
      this.selectItemWP.HeaderFont = new Font("Tahoma", 10f, FontStyle.Bold);
      this.selectItemWP.HeaderImage = (Image) Resources.blackSmith1;
      this.selectItemWP.HeaderImageVisible = true;
      this.selectItemWP.HeaderTitle = "Select Item";
      ((Control) this.selectItemWP).Location = new Point(0, 0);
      ((Control) this.selectItemWP).Margin = new Padding(4);
      ((Control) this.selectItemWP).Name = "selectItemWP";
      this.selectItemWP.PreviousPage = 2;
      ((Control) this.selectItemWP).Size = new Size(704, 390);
      this.selectItemWP.SubTitle = "Selected Item:";
      this.selectItemWP.SubTitleFont = new Font("Tahoma", 9f, FontStyle.Bold);
      ((Control) this.selectItemWP).TabIndex = 4;
      this.selectItemWP.PageShow += new EventHandler<WizardPageEventArgs>(this.selectItemWP_PageShow);
      this.seedLabel.BackColor = Color.Transparent;
      this.seedLabel.Font = new Font("Microsoft Sans Serif", 10f, FontStyle.Bold, GraphicsUnit.Point, (byte) 0);
      this.seedLabel.ForeColor = Color.SeaShell;
      this.seedLabel.Location = new Point(449, 347);
      this.seedLabel.Margin = new Padding(4, 0, 4, 0);
      this.seedLabel.Name = "seedLabel";
      this.seedLabel.Size = new Size(137, 26);
      this.seedLabel.TabIndex = 7;
      this.seedLabel.Text = "Item Seed";
      this.seedLabel.TextAlign = ContentAlignment.MiddleCenter;
      this.seedLabel.Visible = false;
      this.seedNUDown.BackColor = Color.LightGray;
      this.seedNUDown.Font = new Font("Microsoft Sans Serif", 10f, FontStyle.Bold, GraphicsUnit.Point, (byte) 0);
      this.seedNUDown.ForeColor = Color.SaddleBrown;
      this.seedNUDown.Increment = new Decimal(new int[4]
      {
        100,
        0,
        0,
        0
      });
      this.seedNUDown.Location = new Point(595, 345);
      this.seedNUDown.Margin = new Padding(4);
      this.seedNUDown.Maximum = new Decimal(new int[4]
      {
        30000,
        0,
        0,
        0
      });
      this.seedNUDown.Minimum = new Decimal(new int[4]
      {
        1,
        0,
        0,
        0
      });
      this.seedNUDown.Name = "seedNUDown";
      this.seedNUDown.Size = new Size(97, 26);
      this.seedNUDown.TabIndex = 6;
      this.seedNUDown.TextAlign = HorizontalAlignment.Right;
      this.toolTip.SetToolTip((Control) this.seedNUDown, "Item Seed (0-30,000)");
      this.seedNUDown.Value = new Decimal(new int[4]
      {
        15000,
        0,
        0,
        0
      });
      this.itemSelectionTabControl.Controls.Add((Control) this.ImageTabPage);
      this.itemSelectionTabControl.Controls.Add((Control) this.listTabPage);
      this.itemSelectionTabControl.Font = new Font("Microsoft Sans Serif", 8.25f, FontStyle.Bold, GraphicsUnit.Point, (byte) 0);
      this.itemSelectionTabControl.Location = new Point(1, 95);
      this.itemSelectionTabControl.Margin = new Padding(0);
      this.itemSelectionTabControl.Name = "itemSelectionTabControl";
      this.itemSelectionTabControl.Padding = new Point(0, 0);
      this.itemSelectionTabControl.SelectedIndex = 0;
      this.itemSelectionTabControl.Size = new Size(704, 250);
      this.itemSelectionTabControl.TabIndex = 1;
      this.ImageTabPage.BackColor = Color.FromArgb(46, 41, 31);
      this.ImageTabPage.Controls.Add((Control) this.itemSelectImagePanel);
      this.ImageTabPage.Location = new Point(4, 26);
      this.ImageTabPage.Margin = new Padding(4);
      this.ImageTabPage.Name = "ImageTabPage";
      this.ImageTabPage.Padding = new Padding(4);
      this.ImageTabPage.Size = new Size(696, 220);
      this.ImageTabPage.TabIndex = 0;
      this.ImageTabPage.Text = "Images";
      this.itemSelectImagePanel.AutoScroll = true;
      this.itemSelectImagePanel.BackColor = Color.Transparent;
      this.itemSelectImagePanel.Dock = DockStyle.Fill;
      this.itemSelectImagePanel.Location = new Point(4, 4);
      this.itemSelectImagePanel.Margin = new Padding(0);
      this.itemSelectImagePanel.Name = "itemSelectImagePanel";
      this.itemSelectImagePanel.Size = new Size(688, 212);
      this.itemSelectImagePanel.TabIndex = 4;
      this.itemSelectImagePanel.WrapContents = false;
      this.listTabPage.BackColor = Color.FromArgb(46, 41, 31);
      this.listTabPage.Controls.Add((Control) this.ItemListBox);
      this.listTabPage.Controls.Add((Control) this.pictureBox);
      this.listTabPage.Location = new Point(4, 26);
      this.listTabPage.Margin = new Padding(0);
      this.listTabPage.Name = "listTabPage";
      this.listTabPage.Padding = new Padding(4);
      this.listTabPage.Size = new Size(696, 220);
      this.listTabPage.TabIndex = 1;
      this.listTabPage.Text = "List";
      this.ItemListBox.BackColor = Color.FromArgb(41, 39, 31);
      this.ItemListBox.BorderStyle = BorderStyle.None;
      this.ItemListBox.ForeColor = Color.Goldenrod;
      this.ItemListBox.ItemHeight = 17;
      this.ItemListBox.Location = new Point(317, 6);
      this.ItemListBox.Margin = new Padding(4);
      this.ItemListBox.Name = "ItemListBox";
      this.ItemListBox.Size = new Size(333, 204);
      this.ItemListBox.TabIndex = 1;
      this.ItemListBox.SelectedIndexChanged += new EventHandler(this.ItemListBox_SelectedIndexChanged);
      this.pictureBox.Location = new Point(68, 4);
      this.pictureBox.Margin = new Padding(4);
      this.pictureBox.Name = "pictureBox";
      this.pictureBox.Size = new Size(156, 183);
      this.pictureBox.SizeMode = PictureBoxSizeMode.CenterImage;
      this.pictureBox.TabIndex = 0;
      this.pictureBox.TabStop = false;
      ((Control) this.classSelectWP).BackColor = Color.FromArgb(46, 41, 31);
      ((Control) this.classSelectWP).Controls.Add((Control) this.classificationGroupBox);
      ((Control) this.classSelectWP).Dock = DockStyle.Fill;
      this.classSelectWP.Header = true;
      this.classSelectWP.HeaderBackgroundColor = Color.White;
      this.classSelectWP.HeaderFont = new Font("Tahoma", 10f, FontStyle.Bold);
      this.classSelectWP.HeaderImage = (Image) Resources.blackSmith1;
      this.classSelectWP.HeaderImageVisible = true;
      this.classSelectWP.HeaderTitle = "Sellect Item Classification";
      ((Control) this.classSelectWP).Location = new Point(0, 0);
      ((Control) this.classSelectWP).Margin = new Padding(4);
      ((Control) this.classSelectWP).Name = "classSelectWP";
      this.classSelectWP.PreviousPage = 1;
      ((Control) this.classSelectWP).Size = new Size(704, 390);
      this.classSelectWP.SubTitle = "";
      this.classSelectWP.SubTitleFont = new Font("Tahoma", 8f);
      ((Control) this.classSelectWP).TabIndex = 3;
      this.classSelectWP.PageShow += new EventHandler<WizardPageEventArgs>(this.classSelectWP_PageShow);
      this.classificationGroupBox.Controls.Add((Control) this.legendaryButton);
      this.classificationGroupBox.Controls.Add((Control) this.epicRadioButton);
      this.classificationGroupBox.Controls.Add((Control) this.MIRadioButton);
      this.classificationGroupBox.Location = new Point(240, 145);
      this.classificationGroupBox.Margin = new Padding(4);
      this.classificationGroupBox.Name = "classificationGroupBox";
      this.classificationGroupBox.Padding = new Padding(4);
      this.classificationGroupBox.Size = new Size(292, 180);
      this.classificationGroupBox.TabIndex = 1;
      this.classificationGroupBox.TabStop = false;
      this.legendaryButton.AutoSize = true;
      this.legendaryButton.Font = new Font("Microsoft Sans Serif", 10f, FontStyle.Bold, GraphicsUnit.Point, (byte) 0);
      this.legendaryButton.ForeColor = Color.MediumSlateBlue;
      this.legendaryButton.Location = new Point(36, 117);
      this.legendaryButton.Margin = new Padding(4);
      this.legendaryButton.Name = "legendaryButton";
      this.legendaryButton.Size = new Size(117, 24);
      this.legendaryButton.TabIndex = 2;
      this.legendaryButton.Tag = (object) "Legendary";
      this.legendaryButton.Text = "Legendary";
      this.legendaryButton.UseVisualStyleBackColor = true;
      this.epicRadioButton.AutoSize = true;
      this.epicRadioButton.Font = new Font("Microsoft Sans Serif", 10f, FontStyle.Bold, GraphicsUnit.Point, (byte) 0);
      this.epicRadioButton.ForeColor = Color.DodgerBlue;
      this.epicRadioButton.Location = new Point(36, 76);
      this.epicRadioButton.Margin = new Padding(4);
      this.epicRadioButton.Name = "epicRadioButton";
      this.epicRadioButton.Size = new Size(67, 24);
      this.epicRadioButton.TabIndex = 1;
      this.epicRadioButton.Tag = (object) "Epic";
      this.epicRadioButton.Text = "Epic";
      this.epicRadioButton.UseVisualStyleBackColor = true;
      this.MIRadioButton.AutoSize = true;
      this.MIRadioButton.Checked = true;
      this.MIRadioButton.Font = new Font("Microsoft Sans Serif", 10f, FontStyle.Bold, GraphicsUnit.Point, (byte) 0);
      this.MIRadioButton.ForeColor = Color.GreenYellow;
      this.MIRadioButton.Location = new Point(36, 31);
      this.MIRadioButton.Margin = new Padding(4);
      this.MIRadioButton.Name = "MIRadioButton";
      this.MIRadioButton.Size = new Size(194, 24);
      this.MIRadioButton.TabIndex = 0;
      this.MIRadioButton.TabStop = true;
      this.MIRadioButton.Tag = (object) "Rare";
      this.MIRadioButton.Text = " Monster Infrequent";
      this.MIRadioButton.UseVisualStyleBackColor = true;
      ((Control) this.selectItemSubTypeWP).BackColor = Color.FromArgb(46, 41, 31);
      ((Control) this.selectItemSubTypeWP).Controls.Add((Control) this.weaponGroupBox);
      ((Control) this.selectItemSubTypeWP).Controls.Add((Control) this.armorGroupBox);
      ((Control) this.selectItemSubTypeWP).Dock = DockStyle.Fill;
      this.selectItemSubTypeWP.Header = true;
      this.selectItemSubTypeWP.HeaderBackgroundColor = Color.White;
      this.selectItemSubTypeWP.HeaderFont = new Font("Tahoma", 10f, FontStyle.Bold);
      this.selectItemSubTypeWP.HeaderImage = (Image) Resources.blackSmith1;
      this.selectItemSubTypeWP.HeaderImageVisible = true;
      this.selectItemSubTypeWP.HeaderTitle = "Select sub type";
      ((Control) this.selectItemSubTypeWP).Location = new Point(0, 0);
      ((Control) this.selectItemSubTypeWP).Margin = new Padding(4);
      ((Control) this.selectItemSubTypeWP).Name = "selectItemSubTypeWP";
      this.selectItemSubTypeWP.PreviousPage = 0;
      ((Control) this.selectItemSubTypeWP).Size = new Size(704, 390);
      this.selectItemSubTypeWP.SubTitle = "Click on type and click Next.";
      this.selectItemSubTypeWP.SubTitleFont = new Font("Tahoma", 8f);
      ((Control) this.selectItemSubTypeWP).TabIndex = 2;
      this.selectItemSubTypeWP.PageShow += new EventHandler<WizardPageEventArgs>(this.selectItemSubTypeWP_PageShow);
      this.weaponGroupBox.Controls.Add((Control) this.thrownButton);
      this.weaponGroupBox.Controls.Add((Control) this.shieldButton);
      this.weaponGroupBox.Controls.Add((Control) this.staffButton);
      this.weaponGroupBox.Controls.Add((Control) this.spearButton);
      this.weaponGroupBox.Controls.Add((Control) this.maceButton);
      this.weaponGroupBox.Controls.Add((Control) this.bowButton);
      this.weaponGroupBox.Controls.Add((Control) this.axeButton);
      this.weaponGroupBox.Controls.Add((Control) this.swordButton);
      this.weaponGroupBox.FlatStyle = FlatStyle.Flat;
      this.weaponGroupBox.ForeColor = Color.FromArgb(61, 56, 54);
      this.weaponGroupBox.Location = new Point(53, 103);
      this.weaponGroupBox.Margin = new Padding(4);
      this.weaponGroupBox.Name = "weaponGroupBox";
      this.weaponGroupBox.Padding = new Padding(4);
      this.weaponGroupBox.Size = new Size(576, 245);
      this.weaponGroupBox.TabIndex = 2;
      this.weaponGroupBox.TabStop = false;
      this.weaponGroupBox.Visible = false;
      this.thrownButton.BackgroundImage = (Image) Resources.typeThrown;
      this.thrownButton.BackgroundImageLayout = ImageLayout.Stretch;
      this.thrownButton.FlatStyle = FlatStyle.Flat;
      this.thrownButton.ForeColor = Color.FromArgb(61, 56, 54);
      this.thrownButton.Location = new Point(455, 128);
      this.thrownButton.Margin = new Padding(4);
      this.thrownButton.Name = "thrownButton";
      this.thrownButton.Size = new Size(93, 86);
      this.thrownButton.TabIndex = 10;
      this.thrownButton.UseVisualStyleBackColor = true;
      this.shieldButton.BackgroundImage = (Image) Resources.typeShield;
      this.shieldButton.BackgroundImageLayout = ImageLayout.Stretch;
      this.shieldButton.FlatStyle = FlatStyle.Flat;
      this.shieldButton.ForeColor = Color.FromArgb(61, 56, 54);
      this.shieldButton.Location = new Point(455, 20);
      this.shieldButton.Margin = new Padding(4);
      this.shieldButton.Name = "shieldButton";
      this.shieldButton.Size = new Size(93, 86);
      this.shieldButton.TabIndex = 9;
      this.shieldButton.UseVisualStyleBackColor = true;
      this.staffButton.BackgroundImage = (Image) Resources.typeStaff;
      this.staffButton.BackgroundImageLayout = ImageLayout.Stretch;
      this.staffButton.FlatStyle = FlatStyle.Flat;
      this.staffButton.ForeColor = Color.FromArgb(61, 56, 54);
      this.staffButton.Location = new Point(344, 20);
      this.staffButton.Margin = new Padding(4);
      this.staffButton.Name = "staffButton";
      this.staffButton.Size = new Size(87, 203);
      this.staffButton.TabIndex = 8;
      this.staffButton.UseVisualStyleBackColor = true;
      this.spearButton.BackgroundImage = (Image) Resources.typeSpear;
      this.spearButton.BackgroundImageLayout = ImageLayout.Stretch;
      this.spearButton.FlatStyle = FlatStyle.Flat;
      this.spearButton.ForeColor = Color.FromArgb(61, 56, 54);
      this.spearButton.Location = new Point(277, 20);
      this.spearButton.Margin = new Padding(4);
      this.spearButton.Name = "spearButton";
      this.spearButton.Size = new Size(40, 203);
      this.spearButton.TabIndex = 7;
      this.spearButton.UseVisualStyleBackColor = true;
      this.maceButton.BackgroundImage = (Image) Resources.typeMace;
      this.maceButton.BackgroundImageLayout = ImageLayout.Stretch;
      this.maceButton.FlatStyle = FlatStyle.Flat;
      this.maceButton.ForeColor = Color.FromArgb(61, 56, 54);
      this.maceButton.Location = new Point((int) sbyte.MaxValue, 36);
      this.maceButton.Margin = new Padding(4);
      this.maceButton.Name = "maceButton";
      this.maceButton.Size = new Size(53, 160);
      this.maceButton.TabIndex = 6;
      this.maceButton.UseVisualStyleBackColor = true;
      this.bowButton.BackgroundImage = (Image) Resources.typeBow;
      this.bowButton.BackgroundImageLayout = ImageLayout.Stretch;
      this.bowButton.FlatStyle = FlatStyle.Flat;
      this.bowButton.ForeColor = Color.FromArgb(61, 56, 54);
      this.bowButton.Location = new Point(200, 20);
      this.bowButton.Margin = new Padding(4);
      this.bowButton.Name = "bowButton";
      this.bowButton.Size = new Size(53, 203);
      this.bowButton.TabIndex = 5;
      this.bowButton.UseVisualStyleBackColor = true;
      this.axeButton.BackgroundImage = (Image) Resources.typeAxe;
      this.axeButton.BackgroundImageLayout = ImageLayout.Stretch;
      this.axeButton.FlatStyle = FlatStyle.Flat;
      this.axeButton.ForeColor = Color.FromArgb(61, 56, 54);
      this.axeButton.Location = new Point(65, 34);
      this.axeButton.Margin = new Padding(4);
      this.axeButton.Name = "axeButton";
      this.axeButton.Size = new Size(53, 160);
      this.axeButton.TabIndex = 4;
      this.axeButton.UseVisualStyleBackColor = true;
      this.swordButton.BackgroundImage = (Image) Resources.typeSword;
      this.swordButton.BackgroundImageLayout = ImageLayout.Stretch;
      this.swordButton.FlatStyle = FlatStyle.Flat;
      this.swordButton.ForeColor = Color.FromArgb(61, 56, 54);
      this.swordButton.Location = new Point(11, 36);
      this.swordButton.Margin = new Padding(4);
      this.swordButton.Name = "swordButton";
      this.swordButton.Size = new Size(47, 160);
      this.swordButton.TabIndex = 3;
      this.swordButton.UseVisualStyleBackColor = true;
      this.armorGroupBox.Controls.Add((Control) this.headButton);
      this.armorGroupBox.Controls.Add((Control) this.legButton);
      this.armorGroupBox.Controls.Add((Control) this.armButton);
      this.armorGroupBox.Controls.Add((Control) this.chestButton);
      this.armorGroupBox.FlatStyle = FlatStyle.Flat;
      this.armorGroupBox.ForeColor = Color.FromArgb(61, 56, 54);
      this.armorGroupBox.Location = new Point(637, 135);
      this.armorGroupBox.Margin = new Padding(4);
      this.armorGroupBox.Name = "armorGroupBox";
      this.armorGroupBox.Padding = new Padding(4);
      this.armorGroupBox.Size = new Size(495, 190);
      this.armorGroupBox.TabIndex = 1;
      this.armorGroupBox.TabStop = false;
      this.armorGroupBox.Visible = false;
      this.headButton.BackgroundImage = (Image) Resources.typeHead;
      this.headButton.BackgroundImageLayout = ImageLayout.Stretch;
      this.headButton.FlatStyle = FlatStyle.Flat;
      this.headButton.ForeColor = Color.FromArgb(61, 56, 54);
      this.headButton.Location = new Point(27, 54);
      this.headButton.Margin = new Padding(4);
      this.headButton.Name = "headButton";
      this.headButton.Size = new Size(93, 86);
      this.headButton.TabIndex = 5;
      this.headButton.UseVisualStyleBackColor = true;
      this.legButton.BackgroundImage = (Image) Resources.typeLeg;
      this.legButton.BackgroundImageLayout = ImageLayout.Stretch;
      this.legButton.FlatStyle = FlatStyle.Flat;
      this.legButton.ForeColor = Color.FromArgb(61, 56, 54);
      this.legButton.Location = new Point(372, 54);
      this.legButton.Margin = new Padding(4);
      this.legButton.Name = "legButton";
      this.legButton.Size = new Size(87, 80);
      this.legButton.TabIndex = 4;
      this.legButton.UseVisualStyleBackColor = true;
      this.armButton.BackgroundImage = (Image) Resources.typeArm;
      this.armButton.BackgroundImageLayout = ImageLayout.Stretch;
      this.armButton.FlatStyle = FlatStyle.Flat;
      this.armButton.ForeColor = Color.FromArgb(61, 56, 54);
      this.armButton.Location = new Point(265, 60);
      this.armButton.Margin = new Padding(4);
      this.armButton.Name = "armButton";
      this.armButton.Size = new Size(80, 74);
      this.armButton.TabIndex = 3;
      this.armButton.UseVisualStyleBackColor = true;
      this.chestButton.BackgroundImage = (Image) Resources.typeChest;
      this.chestButton.BackgroundImageLayout = ImageLayout.Stretch;
      this.chestButton.FlatStyle = FlatStyle.Flat;
      this.chestButton.ForeColor = Color.FromArgb(61, 56, 54);
      this.chestButton.Location = new Point(152, 41);
      this.chestButton.Margin = new Padding(4);
      this.chestButton.Name = "chestButton";
      this.chestButton.Size = new Size(84, 114);
      this.chestButton.TabIndex = 2;
      this.chestButton.UseVisualStyleBackColor = true;
      ((Control) this.selectTypeWP).BackColor = Color.FromArgb(46, 41, 31);
      ((Control) this.selectTypeWP).Controls.Add((Control) this.miscButton);
      ((Control) this.selectTypeWP).Controls.Add((Control) this.armorButton);
      ((Control) this.selectTypeWP).Controls.Add((Control) this.questItemButton);
      ((Control) this.selectTypeWP).Controls.Add((Control) this.parchmentButton);
      ((Control) this.selectTypeWP).Controls.Add((Control) this.artifactButton);
      ((Control) this.selectTypeWP).Controls.Add((Control) this.scrollButton);
      ((Control) this.selectTypeWP).Controls.Add((Control) this.formulaButton);
      ((Control) this.selectTypeWP).Controls.Add((Control) this.charmButton);
      ((Control) this.selectTypeWP).Controls.Add((Control) this.relicButton);
      ((Control) this.selectTypeWP).Controls.Add((Control) this.amuletButton);
      ((Control) this.selectTypeWP).Controls.Add((Control) this.ringButton);
      ((Control) this.selectTypeWP).Controls.Add((Control) this.weaponButton);
      ((Control) this.selectTypeWP).Cursor = Cursors.Default;
      ((Control) this.selectTypeWP).Dock = DockStyle.Fill;
      ((Control) this.selectTypeWP).ForeColor = Color.FromArgb(64, 0, 0);
      this.selectTypeWP.Header = true;
      this.selectTypeWP.HeaderBackgroundColor = Color.White;
      this.selectTypeWP.HeaderFont = new Font("Tahoma", 10f, FontStyle.Bold);
      this.selectTypeWP.HeaderImage = (Image) componentResourceManager.GetObject("selectTypeWP.HeaderImage");
      this.selectTypeWP.HeaderImageVisible = true;
      this.selectTypeWP.HeaderTitle = "Select item type";
      ((Control) this.selectTypeWP).Location = new Point(0, 0);
      ((Control) this.selectTypeWP).Margin = new Padding(4);
      ((Control) this.selectTypeWP).Name = "selectTypeWP";
      this.selectTypeWP.PreviousPage = 0;
      ((Control) this.selectTypeWP).Size = new Size(704, 390);
      this.selectTypeWP.SubTitle = "Click on type and click Next";
      this.selectTypeWP.SubTitleFont = new Font("Tahoma", 8f);
      ((Control) this.selectTypeWP).TabIndex = 1;
      ((Control) this.selectTypeWP).Paint += new PaintEventHandler(this.advancedWizardPage1_Paint);
      this.miscButton.BackgroundImage = (Image) Resources.parchment;
      this.miscButton.BackgroundImageLayout = ImageLayout.Stretch;
      this.miscButton.FlatStyle = FlatStyle.Flat;
      this.miscButton.ForeColor = Color.FromArgb(61, 56, 54);
      this.miscButton.Location = new Point(567, 103);
      this.miscButton.Margin = new Padding(4);
      this.miscButton.Name = "miscButton";
      this.miscButton.Size = new Size(96, 108);
      this.miscButton.TabIndex = 12;
      this.toolTip.SetToolTip((Control) this.miscButton, "Misc Itms");
      this.miscButton.UseVisualStyleBackColor = true;
      this.armorButton.BackgroundImage = (Image) componentResourceManager.GetObject("armorButton.BackgroundImage");
      this.armorButton.BackgroundImageLayout = ImageLayout.Stretch;
      this.armorButton.Cursor = Cursors.Default;
      this.armorButton.FlatStyle = FlatStyle.Flat;
      this.armorButton.ForeColor = Color.FromArgb(61, 56, 54);
      this.armorButton.Location = new Point(163, 130);
      this.armorButton.Margin = new Padding(4);
      this.armorButton.Name = "armorButton";
      this.armorButton.Size = new Size(117, 165);
      this.armorButton.TabIndex = 2;
      this.toolTip.SetToolTip((Control) this.armorButton, "Armor");
      this.armorButton.UseVisualStyleBackColor = true;
      this.questItemButton.BackgroundImage = (Image) Resources.questItems;
      this.questItemButton.BackgroundImageLayout = ImageLayout.Stretch;
      this.questItemButton.FlatStyle = FlatStyle.Flat;
      this.questItemButton.ForeColor = Color.FromArgb(61, 56, 54);
      this.questItemButton.Location = new Point(508, 217);
      this.questItemButton.Margin = new Padding(4);
      this.questItemButton.Name = "questItemButton";
      this.questItemButton.Size = new Size(51, 81);
      this.questItemButton.TabIndex = 11;
      this.toolTip.SetToolTip((Control) this.questItemButton, "Quest Items");
      this.questItemButton.UseVisualStyleBackColor = true;
      this.parchmentButton.BackgroundImage = (Image) Resources.parchment;
      this.parchmentButton.BackgroundImageLayout = ImageLayout.Stretch;
      this.parchmentButton.FlatStyle = FlatStyle.Flat;
      this.parchmentButton.ForeColor = Color.FromArgb(61, 56, 54);
      this.parchmentButton.Location = new Point(567, 219);
      this.parchmentButton.Margin = new Padding(4);
      this.parchmentButton.Name = "parchmentButton";
      this.parchmentButton.Size = new Size(96, 81);
      this.parchmentButton.TabIndex = 10;
      this.toolTip.SetToolTip((Control) this.parchmentButton, "Parchments");
      this.parchmentButton.UseVisualStyleBackColor = true;
      this.artifactButton.BackgroundImage = (Image) Resources.typeArtifact1;
      this.artifactButton.BackgroundImageLayout = ImageLayout.Stretch;
      this.artifactButton.FlatStyle = FlatStyle.Flat;
      this.artifactButton.ForeColor = Color.FromArgb(61, 56, 54);
      this.artifactButton.Location = new Point(409, 219);
      this.artifactButton.Margin = new Padding(4);
      this.artifactButton.Name = "artifactButton";
      this.artifactButton.Size = new Size(91, 81);
      this.artifactButton.TabIndex = 9;
      this.toolTip.SetToolTip((Control) this.artifactButton, "Artifacts");
      this.artifactButton.UseVisualStyleBackColor = true;
      this.scrollButton.BackgroundImage = (Image) Resources.typeScroll;
      this.scrollButton.BackgroundImageLayout = ImageLayout.Stretch;
      this.scrollButton.FlatStyle = FlatStyle.Flat;
      this.scrollButton.ForeColor = Color.FromArgb(61, 56, 54);
      this.scrollButton.Location = new Point(293, 218);
      this.scrollButton.Margin = new Padding(4);
      this.scrollButton.Name = "scrollButton";
      this.scrollButton.Size = new Size(96, 81);
      this.scrollButton.TabIndex = 8;
      this.toolTip.SetToolTip((Control) this.scrollButton, "Scrolls");
      this.scrollButton.UseVisualStyleBackColor = true;
      this.formulaButton.BackgroundImage = (Image) Resources.typeFormula;
      this.formulaButton.BackgroundImageLayout = ImageLayout.Stretch;
      this.formulaButton.FlatStyle = FlatStyle.Flat;
      this.formulaButton.ForeColor = Color.FromArgb(61, 56, 54);
      this.formulaButton.Location = new Point(405, 167);
      this.formulaButton.Margin = new Padding(4);
      this.formulaButton.Name = "formulaButton";
      this.formulaButton.Size = new Size(91, 42);
      this.formulaButton.TabIndex = 7;
      this.toolTip.SetToolTip((Control) this.formulaButton, "Formula");
      this.formulaButton.UseVisualStyleBackColor = true;
      this.charmButton.BackgroundImage = (Image) Resources.typeCharm;
      this.charmButton.BackgroundImageLayout = ImageLayout.Stretch;
      this.charmButton.FlatStyle = FlatStyle.Flat;
      this.charmButton.ForeColor = Color.FromArgb(61, 56, 54);
      this.charmButton.Location = new Point(347, 164);
      this.charmButton.Margin = new Padding(4);
      this.charmButton.Name = "charmButton";
      this.charmButton.Size = new Size(45, 42);
      this.charmButton.TabIndex = 6;
      this.toolTip.SetToolTip((Control) this.charmButton, "Charms");
      this.charmButton.UseVisualStyleBackColor = true;
      this.relicButton.BackgroundImage = (Image) Resources.typeRelic;
      this.relicButton.BackgroundImageLayout = ImageLayout.Stretch;
      this.relicButton.FlatStyle = FlatStyle.Flat;
      this.relicButton.ForeColor = Color.FromArgb(61, 56, 54);
      this.relicButton.Location = new Point(293, 164);
      this.relicButton.Margin = new Padding(4);
      this.relicButton.Name = "relicButton";
      this.relicButton.Size = new Size(45, 42);
      this.relicButton.TabIndex = 5;
      this.toolTip.SetToolTip((Control) this.relicButton, "Relics");
      this.relicButton.UseVisualStyleBackColor = true;
      this.amuletButton.BackgroundImage = (Image) Resources.typeAmulate;
      this.amuletButton.BackgroundImageLayout = ImageLayout.Stretch;
      this.amuletButton.FlatStyle = FlatStyle.Flat;
      this.amuletButton.ForeColor = Color.FromArgb(61, 56, 54);
      this.amuletButton.Location = new Point(347, 118);
      this.amuletButton.Margin = new Padding(4);
      this.amuletButton.Name = "amuletButton";
      this.amuletButton.Size = new Size(45, 42);
      this.amuletButton.TabIndex = 4;
      this.toolTip.SetToolTip((Control) this.amuletButton, "Amulet");
      this.amuletButton.UseVisualStyleBackColor = true;
      this.ringButton.BackgroundImage = (Image) Resources.typeRing__2_;
      this.ringButton.BackgroundImageLayout = ImageLayout.Stretch;
      this.ringButton.FlatStyle = FlatStyle.Flat;
      this.ringButton.ForeColor = Color.FromArgb(61, 56, 54);
      this.ringButton.Location = new Point(293, 118);
      this.ringButton.Margin = new Padding(4);
      this.ringButton.Name = "ringButton";
      this.ringButton.Size = new Size(45, 42);
      this.ringButton.TabIndex = 3;
      this.toolTip.SetToolTip((Control) this.ringButton, "Ring");
      this.ringButton.UseVisualStyleBackColor = true;
      this.weaponButton.BackgroundImage = (Image) Resources.typeWeapons;
      this.weaponButton.BackgroundImageLayout = ImageLayout.Stretch;
      this.weaponButton.Cursor = Cursors.Default;
      this.weaponButton.FlatStyle = FlatStyle.Flat;
      this.weaponButton.ForeColor = Color.FromArgb(61, 56, 54);
      this.weaponButton.Location = new Point(25, 129);
      this.weaponButton.Margin = new Padding(4);
      this.weaponButton.Name = "weaponButton";
      this.weaponButton.Size = new Size(117, 165);
      this.weaponButton.TabIndex = 1;
      this.toolTip.SetToolTip((Control) this.weaponButton, "Weapons");
      this.weaponButton.UseVisualStyleBackColor = true;
      this.weaponButton.Click += new EventHandler(this.weaponButton_Click);
      ((Control) this.previewWP).BackColor = Color.FromArgb(46, 41, 31);
      ((Control) this.previewWP).Controls.Add((Control) this.descriptionWebBrowser);
      ((Control) this.previewWP).Controls.Add((Control) this.previewItemLabel);
      ((Control) this.previewWP).Controls.Add((Control) this.previewItemPictureBox);
      ((Control) this.previewWP).Dock = DockStyle.Fill;
      this.previewWP.Header = true;
      this.previewWP.HeaderBackgroundColor = Color.White;
      this.previewWP.HeaderFont = new Font("Tahoma", 10f, FontStyle.Bold);
      this.previewWP.HeaderImage = (Image) Resources.blackSmith1;
      this.previewWP.HeaderImageVisible = true;
      this.previewWP.HeaderTitle = "Preview";
      ((Control) this.previewWP).Location = new Point(0, 0);
      ((Control) this.previewWP).Margin = new Padding(4);
      ((Control) this.previewWP).Name = "previewWP";
      this.previewWP.PreviousPage = 4;
      ((Control) this.previewWP).Size = new Size(704, 390);
      this.previewWP.SubTitle = "Click Finish to add item to the sack.";
      this.previewWP.SubTitleFont = new Font("Tahoma", 8f);
      ((Control) this.previewWP).TabIndex = 6;
      this.descriptionWebBrowser.Location = new Point(253, 95);
      this.descriptionWebBrowser.Margin = new Padding(4);
      this.descriptionWebBrowser.MinimumSize = new Size(27, 25);
      this.descriptionWebBrowser.Name = "descriptionWebBrowser";
      this.descriptionWebBrowser.Size = new Size(439, 276);
      this.descriptionWebBrowser.TabIndex = 3;
      this.previewItemLabel.AutoSize = true;
      this.previewItemLabel.Font = new Font("Microsoft Sans Serif", 10f, FontStyle.Bold, GraphicsUnit.Point, (byte) 0);
      this.previewItemLabel.ForeColor = Color.Goldenrod;
      this.previewItemLabel.Location = new Point(237, 313);
      this.previewItemLabel.Margin = new Padding(4, 0, 4, 0);
      this.previewItemLabel.Name = "previewItemLabel";
      this.previewItemLabel.Size = new Size(59, 20);
      this.previewItemLabel.TabIndex = 2;
      this.previewItemLabel.Text = "label2";
      this.previewItemLabel.Visible = false;
      this.previewItemPictureBox.BackgroundImageLayout = ImageLayout.Zoom;
      this.previewItemPictureBox.Location = new Point(53, 108);
      this.previewItemPictureBox.Margin = new Padding(4);
      this.previewItemPictureBox.Name = "previewItemPictureBox";
      this.previewItemPictureBox.Size = new Size(164, 240);
      this.previewItemPictureBox.SizeMode = PictureBoxSizeMode.CenterImage;
      this.previewItemPictureBox.TabIndex = 1;
      this.previewItemPictureBox.TabStop = false;
      this.toolTip.BackColor = Color.Silver;
      this.toolTip.ForeColor = Color.Black;
      this.AutoScaleDimensions = new SizeF(8f, 16f);
      this.AutoScaleMode = AutoScaleMode.Font;
      this.BackColor = SystemColors.AppWorkspace;
      this.BackgroundImage = (Image) componentResourceManager.GetObject("$this.BackgroundImage");
      this.ClientSize = new Size(1188, 570);
      this.Controls.Add((Control) this.itemWizard);
      this.Controls.Add((Control) this.CurrentCharacterLabel);
      this.Controls.Add((Control) this.menuStrip1);
      this.FormBorderStyle = FormBorderStyle.FixedDialog;
      this.Icon = (Icon) componentResourceManager.GetObject("$this.Icon");
      this.IsMdiContainer = true;
      this.MainMenuStrip = this.menuStrip1;
      this.Margin = new Padding(4);
      this.MaximizeBox = false;
      this.Name = nameof (Form1);
      this.StartPosition = FormStartPosition.CenterScreen;
      this.Text = "Blacksmith for Titan Quest";
      this.FormClosing += new FormClosingEventHandler(this.Form1_FormClosing);
      this.Load += new EventHandler(this.Form1_Load);
      this.Shown += new EventHandler(this.Form1_Shown);
      this.menuStrip1.ResumeLayout(false);
      this.menuStrip1.PerformLayout();
      ((Control) this.itemWizard).ResumeLayout(false);
      ((Control) this.prefixWP).ResumeLayout(false);
      ((Control) this.prefixWP).PerformLayout();
      ((Control) this.selectItemWP).ResumeLayout(false);
      this.seedNUDown.EndInit();
      this.itemSelectionTabControl.ResumeLayout(false);
      this.ImageTabPage.ResumeLayout(false);
      this.listTabPage.ResumeLayout(false);
      ((ISupportInitialize) this.pictureBox).EndInit();
      ((Control) this.classSelectWP).ResumeLayout(false);
      this.classificationGroupBox.ResumeLayout(false);
      this.classificationGroupBox.PerformLayout();
      ((Control) this.selectItemSubTypeWP).ResumeLayout(false);
      this.weaponGroupBox.ResumeLayout(false);
      this.armorGroupBox.ResumeLayout(false);
      ((Control) this.selectTypeWP).ResumeLayout(false);
      ((Control) this.previewWP).ResumeLayout(false);
      ((Control) this.previewWP).PerformLayout();
      ((ISupportInitialize) this.previewItemPictureBox).EndInit();
      this.ResumeLayout(false);
      this.PerformLayout();
    }
  }
}
