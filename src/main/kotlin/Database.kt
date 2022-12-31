import org.jetbrains.skia.Bitmap
import org.jetbrains.skiko.toBitmap
import java.awt.*
import java.awt.image.BufferedImage
import java.io.*
import java.nio.file.Files
import java.nio.file.Path
import java.util.*
import kotlin.io.path.name


private val logger = mu.KotlinLogging.logger {}
public object Database {
  private const val ITEMUNITSIZE = 32f
  private val infoDB: MutableMap<String, Info> = mutableMapOf()
  private val textDB: MutableMap<String, String> = mutableMapOf()
  private val arcFiles: MutableMap<String, ArcFile> = mutableMapOf()
  private val bitmaps: MutableMap<String, Bitmap> = mutableMapOf()
  private val prefixesTable: MutableMap<String, MutableMap<String, Info>> = mutableMapOf()
  private val suffixesTable: MutableMap<String, MutableMap<String, Info>> = mutableMapOf()
  private var gameLanguage = ""
  private var scale = 1f
  private val defaultBitmap: Bitmap

  private val miscList: MutableList<Info> = mutableListOf()

  //    public static Database DB;
  public var AutoDetectLanguage = true
  public var TQLanguage = ""
  public var ArzFile: ArzFile? = null
  public var ArzFileIT: ArzFile? = null
  public var ArzFileAE: ArzFile? = null
  public var ArzFileMod: ArzFile? = null
  public val ItemUnitSize = (32f * this.scale).toInt()

  //
//    public int HalfUnitSize => this.ItemUnitSize / 2;
//
  public var Scale: Float
    get() = this.scale;
    set(value) {
      if (value < 0.40000000596046448)
        scale = 0.4f;
      else if (value > 2.0)
        scale = 2f;
    }


  init {
    defaultBitmap = CreateDefaultBitmap()
  }

  //
//    public void dumpPrefixAndSuffixData(String file)
//    {
//      Dictionary<String, Dictionary<String, int>> dictionary1 = new Dictionary<String, Dictionary<String, int>>();
//      using (StreamReader streamReader = new StreamReader(file))
//      {
//        String str1;
//        while ((str1 = streamReader.ReadLine()) != null)
//        {
//          if (!String.IsNullOrWhiteSpace(str1) && !str1.StartsWith("#") && str1.IndexOf("=", StringComparison.Ordinal) != -1)
//          {
//            String[] strArray = str1.Split('=');
//            String str2 = strArray[0];
//            String str3 = strArray[1];
//            String str4 = Regex.Replace(Path.GetFileNameWithoutExtension(str3), "_.+", "");
//            if (str4.EndsWith("B") || str4.EndsWith("b") || str4.EndsWith("A") || str4.EndsWith("a"))
//              str4 = str4.SubString(0, str4.Length - 1);
//            String key1 = str4.Replace("MAGE", "").Replace("MELEE", "").Replace("LIGHTNING", "").Replace("COLD", "").Replace("FIRE", "");
//            Dictionary<String, int> dictionary2;
//            if (!dictionary1.ContainsKey(key1))
//            {
//              dictionary2 = new Dictionary<String, int>();
//              dictionary1.Add(key1, dictionary2);
//            }
//            else
//              dictionary2 = dictionary1[key1];
//            foreach (KeyValuePair<String, float> keyValuePair in new LootTable(str3))
//            {
//              String key2 = keyValuePair.Key + key1;
//              if (dictionary2.ContainsKey(key2))
//                ++dictionary2[key2];
//              else
//                dictionary2.Add(key2, 1);
//            }
//          }
//        }
//        foreach (KeyValuePair<String, Dictionary<String, int>> keyValuePair1 in dictionary1)
//        {
//          String key = keyValuePair1.Key;
//          foreach (KeyValuePair<String, int> keyValuePair2 in keyValuePair1.Value)
//          {
//            Info info = this.GetInfo(keyValuePair2.Key);
//            String itemClassification = info.ItemClassification;
//            String descriptionTag = info.DescriptionTag;
//            String friendlyName = this.GetFriendlyName(descriptionTag);
//            Logger.Log(key + "=" + itemClassification + "=" + (object) keyValuePair2.Value + "=" + keyValuePair2.Key + "=" + descriptionTag + "=" + friendlyName);
//          }
//        }
//      }
//    }
//
//    public void dumpAllItemRecords()
//    {
//    }
//
//    public void dumpAllTex()
//    {
//      List<String> StringList = new List<String>();
//      String path1 = "E:\\bitmaplist.txt";
//      if (!File.Exists(path1))
//        return;
//      Logger.Debug("Reading items from " + path1);
//      using (StreamReader streamReader = new StreamReader(path1))
//      {
//        String str1;
//        while ((str1 = streamReader.ReadLine()) != null)
//        {
//          if (!String.IsNullOrWhiteSpace(str1) && !str1.StartsWith("#"))
//          {
//            String str2 = str1.Trim();
//            if (!StringList.Contains(str2))
//              StringList.Add(str2);
//          }
//        }
//      }
//      String path2 = "E:\\arcitems.txt";
//      if (!File.Exists(path2))
//        return;
//      Logger.Debug("Reading items from " + path2);
//      using (StreamReader streamReader = new StreamReader(path2))
//      {
//        String str3;
//        while ((str3 = streamReader.ReadLine()) != null)
//        {
//          if (!String.IsNullOrWhiteSpace(str3) && !str3.StartsWith("#"))
//          {
//            String str4 = str3.Trim();
//            Bitmap bitmap = this.LoadBitmap(str4);
//            if (bitmap != null)
//            {
//              String str5 = "e:\\uniqueBmapImages\\" + Path.GetFileNameWithoutExtension(str4) + ".png";
//              if (!File.Exists(str5))
//                bitmap.Save(str5, ImageFormat.Png);
//            }
//            else
//              Logger.Log("bmap null =" + str4);
//          }
//        }
//      }
//    }
//
  public fun LoadAllItems() {
    val itemListFiles = Config.itemListFiles;
    var num1 = 0;
    var num2 = 0;
    for (index in itemListFiles.indices) {
      val str1 = itemListFiles[index];
      val file = File(".\\Data\\items\\$str1")
      if (file.exists()) {
        file.bufferedReader().useLines { sequence ->
          sequence.forEach { str2 ->

            if (!str2.isNullOrBlank() && !str2.startsWith("#") && str2.indexOf("=") != -1) {
              val strArray = str2.split('=');
              val str3 = strArray[0];
              val itemId = strArray[1];
              ++num2;
              val info = this.GetInfo(itemId);
              if (info != null) {
                this.LoadBitmap(info.Bitmap);
                if (info.ItemId != null) {
                  val recordType = info.RecordType;
                  val itemClassification = info.ItemClassification;
                  this.GetFriendlyName(info.DescriptionTag);
                }
              }
            }
          }
        }
        logger.debug { "Found $num2  items in $str1" }
        num1 += num2;
        num2 = 0;
      }
    }
    logger.debug { "Loaded $num1 records." }
    val str4 = "ItemwisePrefix.txt";
    val itemprefixfile = File(".\\Data\\items\\$str4")
    if (itemprefixfile.exists()) {
      var num3 = 0;
      itemprefixfile.bufferedReader().useLines { sequence ->
        sequence.forEach { str5 ->
          if (!str5.isNullOrBlank() && !str5.startsWith("#") && str5.indexOf("=") != -1) {
            val strArray = str5.split('=');
            val StringValue = TQData.getItemType(strArray[0]).getStringVal();
            if (StringValue.isNullOrEmpty()) {
              logger.error { "Error: ${strArray[0]}" }
            }
            val str6 = strArray[1];
            val str7 = strArray[2];
            val itemId1 = strArray[3];
            val str8 = strArray[3];
            ++num3;
            val info = this.GetInfo(itemId1);
            if (info != null) {
              val itemId2 = info.ItemId;
              if (itemId2 != null) {
                var dictionary: MutableMap<String, Info>
                if (!this.prefixesTable.containsKey(StringValue)) {
                  dictionary = mutableMapOf()
                  this.prefixesTable[StringValue] = dictionary;
                } else {
                  dictionary = this.prefixesTable[StringValue]!!
                }
                if (!dictionary.containsKey(itemId2))
                  dictionary.put(itemId2, info);
              }
            }
          }
        }
      }
      logger.debug { "Found $num3  prefixes in $str4" }
    }
    val str9 = "ItemwiseSuffix.txt";
    val itemSuffix = File(".\\Data\\items\\$str9")
    if (!itemSuffix.exists()) {
      return
    }
    var num4 = 0;
    itemSuffix.bufferedReader().useLines { sequence ->


      sequence.forEach { str10 ->

        if (!str10.isNullOrBlank() && !str10.startsWith("#") && str10.indexOf("=") != -1) {
          val strArray = str10.split('=');
          val StringValue = TQData.getItemType(strArray[0]).getStringVal();
          val str11 = strArray[1];
          val str12 = strArray[2];
          val itemId3 = strArray[3];
          val str13 = strArray[3];
          ++num4;
          val info = this.GetInfo(itemId3);
          if (info != null) {
            val itemId4 = info.ItemId;
            if (itemId4 != null) {
              var dictionary: MutableMap<String, Info>
              if (!this.suffixesTable.containsKey(StringValue)) {
                dictionary = mutableMapOf()
                this.suffixesTable.put(StringValue, dictionary);
              } else {
                dictionary = this.suffixesTable[StringValue]!!
              }
              if (!dictionary.containsKey(itemId4))
                dictionary.put(itemId4, info);
            }
          }
        }
      }
    }
    logger.debug { "Found $num4 suffixes in $str9" }
    var num5 = 0;
    val str14 = "misc.txt";
    val miscFile = File(".\\Data\\items\\$str14")
    if (miscFile.exists()) {
      miscFile.bufferedReader().useLines { sequence ->

        sequence.forEach { str15 ->
          if (!str15.isNullOrBlank() && !str15.startsWith("#") && str15.indexOf("=") != -1) {
            val strArray = str15.split('=');
            val str16 = strArray[0];
            val itemId = strArray[1];
            ++num5;
            val info = this.GetInfo(itemId);
            if (info != null) {
              this.LoadBitmap(info.Bitmap);
              this.miscList.add(info);
              if (info.ItemId != null) {
                val recordType = info.RecordType;
                val itemClassification = info.ItemClassification;
                this.GetFriendlyName(info.DescriptionTag);
              }
            }
          }
        }
      }
    }
    logger.debug {
      "Found $num5 items in $str14"
    }
  }

  //
//    public void dumpAllItems()
//    {
//      DBClasses.getClasses();
//      String[] keyTable = this.ArzFileAE.GetKeyTable();
//      using (StreamWriter streamWriter = new StreamWriter("E:\\temp\\ConcirneditemList.txt"))
//      {
//        for (int index = 0; index < keyTable.Length; ++index)
//        {
//          String itemId = keyTable[index];
//          if (itemId != null)
//          {
//            Info info = this.GetInfo(itemId);
//            if (info != null)
//            {
//              String recordType = info.RecordType;
//              String itemClassification = info.ItemClassification;
//              String descriptionTag = info.DescriptionTag;
//              String bitmap1 = info.Bitmap;
//              Bitmap bitmap2 = this.LoadBitmap(bitmap1);
//              String friendlyName = this.GetFriendlyName(descriptionTag);
//              if (bitmap2 != null && (String.IsNullOrEmpty(friendlyName) || String.IsNullOrEmpty(itemClassification)))
//              {
//                String str = "e:\\temp\\bmaps\\" + recordType + "_" + Path.GetFileNameWithoutExtension(bitmap1) + ".png";
//                if (!File.Exists(str))
//                  bitmap2.Save(str, ImageFormat.Png);
//                streamWriter.WriteLine(recordType + "=" + itemClassification + "=" + descriptionTag + "=" + friendlyName + "=" + itemId + "=" + bitmap1);
//              }
//            }
//          }
//        }
//        Logger.Log("done dumping");
//      }
//    }
//
//    public List<Info> getMisc() => this.miscList;
//
//    public List<Info> getInfos(ItemType type, ItemClass classification)
//    {
//      List<Info> infos = new List<Info>();
//      if (type == ItemType.Misc)
//        return this.miscList;
//      foreach (KeyValuePair<String, Info> keyValuePair in this.infoDB)
//      {
//        if (this.isMatch(keyValuePair.Value, type, classification))
//          infos.Add(keyValuePair.Value);
//      }
//      if (infos.Count <= 0)
//        Logger.Log("Warning: No records found for type=" + type.GetStringValue() + " and class=" + classification.GetStringValue());
//      return infos;
//    }
//
//    private bool isMatch(Info inf, ItemType type, ItemClass classification = ItemClass.None)
//    {
//      String StringValue = type.GetStringValue();
//      if (inf.RecordType.Equals(StringValue))
//      {
//        if (TQData.isWeaponOrArmor(type) && classification != ItemClass.None)
//          return classification.GetStringValue() == inf.ItemClassification || classification == ItemClass.Legendary && (ItemClass.Quest.GetStringValue() == inf.ItemClassification || ItemClass.Quest.GetStringValue() == inf.ItemClassification);
//        if (TQData.isWeaponOrArmor(type) && classification == ItemClass.None)
//          return true;
//        if ((ItemType.Ring == type || ItemType.Amulet == type) && classification != ItemClass.None)
//          return classification.GetStringValue() == inf.ItemClassification;
//        if ((ItemType.Ring == type || ItemType.Amulet == type) && classification == ItemClass.None)
//          return true;
//        if (ItemType.Charm == type && classification != ItemClass.None)
//        {
//          String withoutExtension = Path.GetFileNameWithoutExtension(inf.ItemId);
//          return ItemClass.Normal == classification && withoutExtension.StartsWith("01") || ItemClass.Epic == classification && withoutExtension.StartsWith("02") || ItemClass.Legendary == classification && withoutExtension.StartsWith("03");
//        }
//        if (ItemType.Charm == type && classification == ItemClass.None)
//          return true;
//        if (ItemType.Relic == type && classification != ItemClass.None)
//        {
//          String withoutExtension = Path.GetFileNameWithoutExtension(inf.ItemId);
//          return ItemClass.Normal == classification && withoutExtension.StartsWith("01") || ItemClass.Epic == classification && withoutExtension.StartsWith("02") || ItemClass.Legendary == classification && withoutExtension.StartsWith("03");
//        }
//        if (ItemType.Relic == type && classification == ItemClass.None)
//          return true;
//        if (ItemType.Scroll == type && classification != ItemClass.None)
//        {
//          String withoutExtension = Path.GetFileNameWithoutExtension(inf.ItemId);
//          return ItemClass.Normal == classification && withoutExtension.StartsWith("01") || ItemClass.Epic == classification && withoutExtension.StartsWith("02") || ItemClass.Legendary == classification && withoutExtension.StartsWith("03");
//        }
//        if (ItemType.Scroll == type && classification == ItemClass.None)
//          return true;
//        if (ItemType.Formula == type && classification != ItemClass.None)
//        {
//          String itemId = inf.GetString("artifactName");
//          DBRecord recordFromFile = Database.GetRecordFromFile(itemId);
//          if (String.IsNullOrEmpty(itemId))
//            return false;
//          String upperInvariant = recordFromFile.GetString("artifactClassification", 0).uppercase();
//          return upperInvariant != null && String.Equals(upperInvariant, classification.GetStringValue(), StringComparison.OrdinalIgnoreCase);
//        }
//        if (ItemType.Formula == type && classification == ItemClass.None)
//          return true;
//        if (ItemType.Artifact == type && classification != ItemClass.None)
//        {
//          String upperInvariant = inf.GetString("artifactClassification").uppercase();
//          return upperInvariant != null && String.Equals(upperInvariant, classification.GetStringValue(), StringComparison.OrdinalIgnoreCase);
//        }
//        return ItemType.Artifact == type && classification == ItemClass.None || ItemType.Quest == type || ItemType.Parchment == type || classification != ItemClass.None && inf.ItemClassification.Equals((object) classification);
//      }
//      return ItemType.Quest == type && inf.ItemClassification.Equals("Quest");
//    }
//
//
//
//    public static bool ExtractArcFile(String arcFileName, String destination)
//    {
//      if (TQDebug.DatabaseDebugLevel > 0)
//        TQDebug.DebugWriteLine(String.Format((IFormatProvider) CultureInfo.InvariantCulture, "Database.ExtractARCFile('{0}', '{1}')", new object[2]
//        {
//          (object) arcFileName,
//          (object) destination
//        }));
//      bool arcFile;
//      try
//      {
//        arcFile = new ArcFile(arcFileName).ExtractArcFile(destination);
//      }
//      catch (IOException ex)
//      {
//        TQDebug.DebugWriteLine("Exception occurred");
//        TQDebug.DebugWriteLine(ex.ToString());
//        arcFile = false;
//      }
//      if (TQDebug.DatabaseDebugLevel > 1)
//        TQDebug.DebugWriteLine(String.Format((IFormatProvider) CultureInfo.InvariantCulture, "Extraction Result = {0}", new object[1]
//        {
//          (object) arcFile
//        }));
//      if (TQDebug.DatabaseDebugLevel > 0)
//        TQDebug.DebugWriteLine("Exiting Database.ReadARCFile()");
//      return arcFile;
//    }
//
  private fun FigureDBFileToUse(rootFolder: String): String? {
    logger.warn { "Database.FigureDBFileToUse($rootFolder)" }
    var folder = File(rootFolder)
    if (!folder.exists()) {
      logger.error("Error - Root Folder does not exist");
      return null;
    }
    val str1 = Path.of(rootFolder, "Text_").toString()
    val str2 = ".arc";
    var str3: String? = null;
    val gameLanguage = this.gameLanguage;

    logger.info { "gameLanguage = $gameLanguage baseFile = $str1" }
    if (!gameLanguage.isNullOrEmpty()) {
      logger.debug("Try looking up LocaleID")

      str3 = Locale.getAvailableLocales().find { culture ->

        logger.info { "Trying ${culture.displayLanguage}" }
        return@find (culture.displayLanguage.uppercase() == gameLanguage.uppercase() || culture.displayVariant.uppercase() == gameLanguage.uppercase())

      }?.language

      if (str3 != null && str3.uppercase() == "CS") {
        str3 = "CZ";
      }
      logger.info { "cultureID = $str3" }
      if (str3 != null) {
        val path = str1 + str3 + str2
        logger.debug { "Detected cultureID from gameLanguage filename = $path" }
        if (File(path).exists()) {
          return path
        }
      }
    }
    val letterIsoLanguageName = Locale.getDefault().language
    logger.info { "Using cultureID from OS cultureID = $letterIsoLanguageName" }
    if (letterIsoLanguageName != null) {
      val path = str1 + letterIsoLanguageName + str2;
      logger.debug { "Trying filename $path" }
      if (File(path).exists()) {
        return path;
      }
    }
    val str4 = "EN";
    val path = str1 + str4 + str2;
    logger.debug { "Forcing English Language cultureID = $str4  filename = $path" }
    if (File(path).exists()) {
      return path
    }
    logger.warn("Detection Failed - searching for files");
    val files =
      Files.walk(Path.of(rootFolder)).filter { item -> item.name.matches(Regex.fromLiteral("Text_??.arc")) }.findFirst()

    if (!files.isEmpty) {
      logger.debug { "Found some files  filename = ${files.get()}" }
      return files.get().toString()
    }
    logger.error { "Failed to determine Language file!\nExiting Database.FigureDBFileToUse()" }

    return null;
  }

  public fun GetFriendlyName(tagId: String): String? {
    return this.textDB[tagId.uppercase()]
  }

//
//    private static int getIndexOfNewLineTag(String text, int startIndex)
//    {
//      int indexOfNewLineTag = text.IndexOf("{^N}", startIndex, StringComparison.Ordinal);
//      if (indexOfNewLineTag < 0)
//        indexOfNewLineTag = text.IndexOf("{^n}", startIndex, StringComparison.Ordinal);
//      return indexOfNewLineTag;
//    }

  public fun GetInfo(itemIdOrg: String): Info? {
    if (itemIdOrg.isNullOrEmpty())
      return null

    val itemId = TQData.NormalizeRecordPath(itemIdOrg)
    var info2: Info?
    info2 = this.infoDB[itemId];
    if (info2 == null) {
      val recordFromFile = this.GetRecordFromFile(itemId);
      if (recordFromFile == null)
        return null;
      info2 = Info(recordFromFile);
      this.infoDB[itemId] = info2;
    }
    return info2;
  }

  //
//    public String GetItemAttributeFriendlyText(String itemAttribute, bool addVariable = true)
//    {
//      ItemAttributesData attributeData = ItemAttributes.GetAttributeData(itemAttribute);
//      if (attributeData == null)
//        return "?" + itemAttribute + "?";
//      String attributeTextTag = ItemAttributes.GetAttributeTextTag(attributeData);
//      if (String.IsNullOrEmpty(attributeTextTag))
//        return "?" + itemAttribute + "?";
//      String attributeFriendlyText = this.GetFriendlyName(attributeTextTag);
//      if (String.IsNullOrEmpty(attributeFriendlyText))
//        attributeFriendlyText = "ATTR<" + itemAttribute + "> TAG<" + attributeTextTag + ">";
//      if (addVariable && attributeData.Variable.Length > 0)
//        attributeFriendlyText = attributeFriendlyText + " " + attributeData.Variable;
//      return attributeFriendlyText;
//    }
//
  public fun GetRecordFromFile(itemIdOrg: String): DBRecord? {
    val itemId = TQData.NormalizeRecordPath(itemIdOrg);
    var recordFromFile: DBRecord? = null;
    if (this.ArzFileMod != null)
      recordFromFile = this.ArzFileMod?.GetItem(itemId);
    if (recordFromFile == null && this.ArzFileAE != null)
      recordFromFile = this.ArzFileAE?.GetItem(itemId);
    if (recordFromFile == null && this.ArzFileIT != null)
      recordFromFile = this.ArzFileIT?.GetItem(itemId);
    if (recordFromFile == null && this.ArzFile != null)
      recordFromFile = this.ArzFile?.GetItem(itemId);
    return recordFromFile;
  }

//    public static String HtmlColor(Color color) => String.Format((IFormatProvider) CultureInfo.InvariantCulture, "#{0:X2}{1:X2}{2:X2}", new object[3]
//    {
//      (object) color.R,
//      (object) color.G,
//      (object) color.B
//    });

  public fun LoadArcFileData(
    scope: String,
    resourceIdOrg: String,
    rootFolderOrg: String,
    alternateFolder: Boolean
  ): ByteArray {
    var resourceId = TQData.NormalizeRecordPath(resourceIdOrg);
    var length = resourceId.indexOf('\\');
    if (length <= 0)
      return ByteArray(0)
    var path = resourceId.substring(0, length);
    var rootFolder = File(rootFolderOrg, "Resources").path
    val flag1 = path.uppercase() == "XPACK"
    if (flag1 || (!flag1 && alternateFolder)) {
      rootFolder = File(rootFolder, "XPack").path
      val num = length
      length = resourceId.indexOf('\\', length + 1);
      if (length <= 0)
        return ByteArray(0)
      path = resourceId.substring(num + 1, length - num - 1);
      resourceId = resourceId.substring(num + 1);
    }
    val flag2 = path.uppercase() == "XPACK2"
    if (flag2 || (!flag2 && alternateFolder)) {
      rootFolder = File(rootFolder, "XPack2").path
      val num1 = length
      val num2 = resourceId.indexOf('\\', length + 1);
      if (num2 <= 0)
        return ByteArray(0)
      path = resourceId.substring(num1 + 1, num2 - num1 - 1);
      resourceId = resourceId.substring(num1 + 1);
    }
    return this.ReadARCFile(File(rootFolder, changeExtension(path, ".arc")).path, resourceId);
  }

  private fun changeExtension(file: String, ext: String): String {
    val index = file.lastIndexOf(".")
    return if(index == -1 ){
      "$file$ext"
    } else {
      val segment = file.substring(0, index)
      "$segment$ext"
    }
  }

  private fun LoadARZFile() {
    logger.info("Database.LoadARZFile()");
    var empty = ""
    try {
      val installLocation = Config.installLocation;
      this.ArzFileAE =
        this.LoadARZFile(Path.of(installLocation, "Database/database.arz").toString(), "Anniversary Edition");
    } catch (ex: Exception) {
      logger.error { "Error in load arz File for TQ\nuse AE:true $ex" }
    }

    logger.debug("Exiting Database.LoadARZFile()");
  }

  private fun LoadARZFile(filepath: String, scope: String): ArzFile? {
    logger.info { "Load $scope database arz file file = $filepath" }
    var arzFile: ArzFile? = null;
    if (File(filepath).exists()) {
      arzFile = ArzFile(filepath);
      arzFile.Read();
    }
    return arzFile;
  }

  public fun LoadBitmap(resourceIdOrg: String): Bitmap? {
    if (resourceIdOrg.isNullOrEmpty())
      return null
    logger.info { "Database.LoadBitmap(${resourceIdOrg})" }
    val resourceId = TQData.NormalizeRecordPath(resourceIdOrg);

    var bitmap2 = this.bitmaps[resourceId];

    if (bitmap2 == null) {
      val data = this.LoadResource(resourceId);
      if (data.isEmpty()) {
        logger.warn("Failure loading resource.  Using default bitmap");
        bitmap2 = this.defaultBitmap;
      } else {
        logger.info { "Loaded resource size=${data.size}" }
        try {
          //https://github.com/dolda2000/haven-client/blob/0fcceaf00da523576482cf97625d5a0eef8dfaae/src/haven/Resource.java#L967
          //https://github.com/RaynsAS/Custom-Salem/tree/master/src/haven
          //bitmap2 = BitmapCode.LoadFromTexMemory(data, 0, data.Length);

          BitmapCode.LoadFromTexMemory(data,0,data.size)

        } catch (ex2: Exception) {
          logger.warn { "Error loading bitmap for $resourceId" }
          bitmap2 = null;
        }
        if (bitmap2 == null) {
          logger.warn { "Failure creating bitmap from resource data len=${data.size}" }
          bitmap2 = this.defaultBitmap
        }
        logger.info { "Created Bitmap ${bitmap2?.width} x ${bitmap2?.height}" }
      }
      this.bitmaps[resourceId] = bitmap2!!
    }
    logger.debug("Exiting Database.LoadBitmap()")
    return bitmap2;
  }

  public fun LoadDBFile() {
    this.LoadTextDB();
    this.LoadARZFile();
    this.LoadRelicOverlayBitmap();
  }

  public fun LoadRelicOverlayBitmap(): Bitmap? {
    val bitmap = this.LoadBitmap("Items\\Relic\\ItemRelicOverlay.tex");
    return if (bitmap == this.defaultBitmap) null else bitmap
  }

  public fun LoadResource(resourceId: String): ByteArray {
    val installLocation = Config.installLocation;
    return this.LoadArcFileData("AE", resourceId, installLocation, false);
  }

  private fun LoadTextDB() {
    logger.warn("Database.LoadTextDB()");
    try {
      if (Config.installLocation != null) {
        val use = this.FigureDBFileToUse(Path.of(Config.installLocation, "Text").toString())
        logger.info { "Find Anniversary Edition text file dbFile = $use" }
        if (use != null) {
          this.ParseTextDB(use, "text\\commonequipment.txt");
          this.ParseTextDB(use, "text\\menu.txt");
          this.ParseTextDB(use, "text\\monsters.txt");
          this.ParseTextDB(use, "text\\npc.txt");
          this.ParseTextDB(use, "text\\quest.txt");
          this.ParseTextDB(use, "text\\skills.txt");
          this.ParseTextDB(use, "text\\ui.txt");
          this.ParseTextDB(use, "text\\uniqueequipment.txt");
          this.ParseTextDB(use, "text\\xcommonequipment.txt");
          this.ParseTextDB(use, "text\\xmenu.txt");
          this.ParseTextDB(use, "text\\xmonsters.txt");
          this.ParseTextDB(use, "text\\xnpc.txt");
          this.ParseTextDB(use, "text\\xquest.txt");
          this.ParseTextDB(use, "text\\xskills.txt");
          this.ParseTextDB(use, "text\\xui.txt");
          this.ParseTextDB(use, "text\\xuniqueequipment.txt");
          this.ParseTextDB(use, "text\\x2commonequipment.txt");
          this.ParseTextDB(use, "text\\x2monsters.txt");
          this.ParseTextDB(use, "text\\x2npc.txt");
          this.ParseTextDB(use, "text\\x2quest.txt");
          this.ParseTextDB(use, "text\\x2ui.txt");
          this.ParseTextDB(use, "text\\x2uniqueequipment.txt");
          this.ParseTextDB(use, "text\\modStrings.txt");
        }
      }
    } catch (ex: Exception) {
      logger.error("Error in load textDB for AE  $ex")
      return
    }
    if (this.textDB.isEmpty()) {
      logger.error("Exception - Could not load Text DB.");
      return
    }
    return;
  }

//    public static String MakeSafeForHtml(String text)
//    {
//      text = Regex.Replace(text, "&", "&amp;");
//      text = Regex.Replace(text, "<", "&lt;");
//      text = Regex.Replace(text, ">", "&gt;");
//      return text;
//    }

  private fun ParseTextDB(databaseFile: String, filename: String) {
    logger.info { "Database.ParseTextDB($databaseFile, $filename" }
    val buffer = this.ReadARCFile(databaseFile, filename);
    if (buffer == null) {
      logger.error { "Error in ARC File: $databaseFile does not contain an entry for '$filename'" }
    } else {
      BufferedReader(InputStreamReader(ByteArrayInputStream(buffer),Charsets.UTF_16)).use { streamReader ->
        val ch = '='
        var str1: String? = ""
        while (run {
            str1 = streamReader.readLine()
            return@run str1
          } != null) {
          val str2 = str1!!.trim();
          if (str2.length >= 2 && !str2.startsWith("//")) {
            val strArray = str2.split(ch);
            if (strArray.size >= 2) {
              var str3 = strArray[1].trim();
              if (str3.indexOf('[') != -1) {
                val startIndex = str3.indexOf(']') + 1;
                val num = str3.indexOf('[', startIndex);
                var str4 = if (num != -1) str3.substring(startIndex, num - startIndex) else str3.substring(startIndex);
                val length = str4.indexOf("//");
                if (length != -1)
                  str4 = str4.substring(0, length);
                str3 = str4.trim();
              }
              this.textDB[strArray[0].trim().uppercase()] = str3;
            }
          }
        }
      }
      logger.info("Exiting Database.ParseTextDB()");
    }
  }

  private fun ReadARCFile(arcFileName: String, dataId: String): ByteArray {
    try {
      logger.info { "Database.ReadARCFile('${arcFileName}', '${dataId}')" }
      var arcFile = this.arcFiles[arcFileName]
      if (arcFile == null) {
        arcFile = ArcFile(arcFileName);
        this.arcFiles.put(arcFileName, arcFile);
      }
      val data = arcFile!!.GetData(dataId);
      logger.info("Exiting Database.ReadARCFile()");
      return data;
    } catch (ex: Exception) {
      logger.error("ReadARCFile Exception occurred", ex);
    }
    return ByteArray(0)
  }

  //    public String VariableToStringNice(Variable variable)
//    {
//      StringBuilder StringBuilder = new StringBuilder(64);
//      StringBuilder.Append(this.GetItemAttributeFriendlyText(variable.Name));
//      StringBuilder.Append(": ");
//      StringBuilder.Append(variable.ToStringValue());
//      return StringBuilder.ToString();
//    }
//
//    public static Collection<String> WrapWords(String text, int columns)
//    {
//      List<String> list = new List<String>();
//      int startIndex = 0;
//      int num = text.IndexOf(' ');
//      int indexOfNewLineTag = Database.getIndexOfNewLineTag(text, startIndex);
//      while (text.Length - startIndex > columns && num >= 0)
//      {
//        while (num >= 0 && num - startIndex < columns)
//        {
//          if (num < indexOfNewLineTag || indexOfNewLineTag < 0)
//            num = text.IndexOf(' ', num + 1);
//          else if (indexOfNewLineTag >= 0)
//          {
//            String str = text.SubString(startIndex, indexOfNewLineTag - startIndex);
//            startIndex = indexOfNewLineTag + 4;
//            list.Add(str);
//            indexOfNewLineTag = Database.getIndexOfNewLineTag(text, startIndex);
//          }
//        }
//        if (num >= 0 && (num < indexOfNewLineTag || indexOfNewLineTag < 0))
//        {
//          String str = text.SubString(startIndex, num - startIndex);
//          startIndex = num + 1;
//          list.Add(str);
//        }
//        else if (indexOfNewLineTag >= 0)
//        {
//          String str = text.SubString(startIndex, indexOfNewLineTag - startIndex);
//          startIndex = indexOfNewLineTag + 4;
//          list.Add(str);
//          indexOfNewLineTag = Database.getIndexOfNewLineTag(text, startIndex);
//        }
//      }
//      for (; indexOfNewLineTag >= 0; indexOfNewLineTag = Database.getIndexOfNewLineTag(text, startIndex))
//      {
//        String str = text.SubString(startIndex, indexOfNewLineTag - startIndex);
//        startIndex = indexOfNewLineTag + 4;
//        list.Add(str);
//      }
//      list.Add(text.SubString(startIndex));
//      return new Collection<String>((IList<String>) list);
//    }
//
//    private void WriteDebugLine_ExitFigureDBFile()
//    {
//      if (TQDebug.DatabaseDebugLevel <= 0)
//        return;
//      TQDebug.DebugWriteLine("Exiting Database.FigureDBFileToUse()");
//    }
//
//    public static float DesignDpi => 96f;
//
//    public String TooltipBodyTag => String.Format((IFormatProvider) CultureInfo.CurrentCulture, "<body bgcolor=#2e291f text=white><font face=\"Albertus MT\" size={0}>", new object[1]
//    {
//      (object) Convert.ToInt32(9f * this.Scale)
//    });
//
//    public String TooltipTitleTag => String.Format((IFormatProvider) CultureInfo.CurrentCulture, "<body bgcolor=#2e291f text=white><font face=\"Albertus MT\" size={0}>", new object[1]
//    {
//      (object) Convert.ToInt32(10f * this.Scale)
//    });
//
//    public String GameLanguage
//    {
//      get
//      {
//        if (this.gameLanguage == null && !this.AutoDetectLanguage)
//          this.gameLanguage = "en";
//        return this.gameLanguage;
//      }
//    }
//


  private fun CreateDefaultBitmap(): Bitmap {
    logger.info("Database.CreateDefaultBitmap()")
    val image = BufferedImage(ItemUnitSize, ItemUnitSize, BufferedImage.TYPE_INT_ARGB)
    val g = image.createGraphics()
    g.color = Color.BLACK
    g.composite = AlphaComposite.Src;
    g.fillRect(0, 0, ItemUnitSize, ItemUnitSize)

    g.composite = AlphaComposite.SrcOver
    g.color = Color.ORANGE
    val smallf = (5f * this.Scale)
    val large = Database.ItemUnitSize - (2f * smallf).toInt()
    val small = smallf.toInt()
    g.fillRect(small, small, large, large)

    val font = Font(Font.SANS_SERIF, Font.PLAIN, 12) // tends to be default font
    val text = "?"
    g.font = font
    //Size?
    //      Font font = new Font("Arial", (float) (this.ItemUnitSize - Convert.ToInt32(4f * num)), GraphicsUnit.Pixel);
    g.color = Color(1, 1, 1)
    // Get the FontMetrics
    val metrics: FontMetrics = g.getFontMetrics(font)
    // Determine the X coordinate for the text
    val x: Int =  (ItemUnitSize - metrics.stringWidth(text)) / 2
    // Determine the Y coordinate for the text (note we add the ascent, as in java 2d 0 is top of the screen)
    val y: Int =  (ItemUnitSize - metrics.getHeight()) / 2 + metrics.getAscent()
    g.drawString(text, x,y)
//      graphics.DrawString("?", font, (Brush) solidBrush2, new RectangleF(num, num, (float) this.ItemUnitSize - 2f * num, (float) this.ItemUnitSize - 2f * num), format);
    g.dispose()

    logger.info("Exiting Database.CreateDefaultBitmap()")
    return image.toBitmap()
  }
}
//    public Dictionary<String, Info> getPrefixes(ItemType type)
//    {
//      if (type == ItemType.Thrown)
//        type = ItemType.Bow;
//      if (this.prefixesTable.ContainsKey(type.GetStringValue()))
//        return this.prefixesTable[type.GetStringValue()];
//      Logger.Debug("No prefixes found for type " + type.GetStringValue());
//      return new Dictionary<String, Info>();
//    }
//
//    public Dictionary<String, Info> getSuffixes(ItemType type)
//    {
//      if (type == ItemType.Thrown)
//        type = ItemType.Bow;
//      if (this.suffixesTable.ContainsKey(type.GetStringValue()))
//        return this.suffixesTable[type.GetStringValue()];
//      Logger.Debug("No suffixes found for type " + type.GetStringValue());
//      return new Dictionary<String, Info>();
//    }
//

