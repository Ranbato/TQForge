import java.lang.NumberFormatException
import kotlin.io.path.Path
import kotlin.io.path.nameWithoutExtension

public class Info public constructor(record: DBRecord) {
  private val record: DBRecord
  private var descriptionVar: String = ""
  private var itemClassificationVar: String = ""
  private var bitmapVar: String = ""
  private var shardBitmapVar: String = ""
  private var itemClassVar: String = ""
  private var completedRelicLevelVar: String = ""
  private var qualityVar: String = ""
  private var styleVar: String = ""
  private var itemScalePercent: String = ""

  init {
    this.record = record
    this.AssignVariableNames()
  }

  private fun AssignVariableNames(): Unit {
    val upperInvariant = this.record.recordType.uppercase();
    if (upperInvariant.startsWith("LOOTRANDOMIZER", true)) {
      this.descriptionVar = "lootRandomizerName";
      this.itemClassificationVar = "itemClassification";
      this.bitmapVar = "";
      this.shardBitmapVar = ""
      this.itemClassVar = ""
      this.completedRelicLevelVar = ""
      this.qualityVar = ""
      this.styleVar = ""
    } else if (upperInvariant.startsWith("ITEMRELIC", true) || upperInvariant.startsWith("ITEMCHARM", true)) {
      this.descriptionVar = "description";
      this.itemClassificationVar = "itemClassification";
      this.bitmapVar = "relicBitmap";
      this.shardBitmapVar = "shardBitmap";
      this.itemClassVar = "Class";
      this.completedRelicLevelVar = "completedRelicLevel";
      this.qualityVar = ""
      this.styleVar = "itemText";
    } else if (upperInvariant.startsWith("ONESHOT_DYE", true)) {
      this.descriptionVar = "description";
      this.itemClassificationVar = ""
      this.bitmapVar = "bitmap";
      this.shardBitmapVar = ""
      this.itemClassVar = "Class";
      this.completedRelicLevelVar = ""
      this.qualityVar = ""
      this.styleVar = ""
    } else if (upperInvariant.startsWith("ONESHOT", true) || upperInvariant.startsWith("QUESTITEM", true)) {
      this.descriptionVar = "description";
      this.itemClassificationVar = "itemClassification";
      this.bitmapVar = "bitmap";
      this.shardBitmapVar = ""
      this.itemClassVar = "Class";
      this.completedRelicLevelVar = ""
      this.qualityVar = ""
      this.styleVar = "itemText";
    } else if (upperInvariant.startsWith("ITEMARTIFACTFORMULA", true)) {
      this.descriptionVar = "description";
      this.itemClassificationVar = "itemClassification";
      this.bitmapVar = "artifactFormulaBitmapName";
      this.shardBitmapVar = ""
      this.itemClassVar = "Class";
      this.completedRelicLevelVar = ""
      this.qualityVar = ""
      this.styleVar = ""
    } else if (upperInvariant.startsWith("ITEMARTIFACT", true)) {
      this.descriptionVar = "description";
      this.itemClassificationVar = "itemClassification";
      this.bitmapVar = "artifactBitmap";
      this.shardBitmapVar = ""
      this.itemClassVar = "Class";
      this.completedRelicLevelVar = ""
      this.qualityVar = ""
      this.styleVar = ""
    } else if (upperInvariant.startsWith("ITEMEQUIPMENT", true)) {
      this.descriptionVar = "description";
      this.itemClassificationVar = "itemClassification";
      this.bitmapVar = "bitmap";
      this.shardBitmapVar = ""
      this.itemClassVar = "Class";
      this.completedRelicLevelVar = ""
      this.qualityVar = ""
      this.styleVar = "itemText";
    } else {
      this.descriptionVar = "itemNameTag";
      this.itemClassificationVar = "itemClassification";
      this.bitmapVar = "bitmap";
      this.shardBitmapVar = ""
      this.itemClassVar = "Class";
      this.completedRelicLevelVar = ""
      this.qualityVar = "itemQualityTag";
      this.styleVar = "itemStyleTag";
    }
    this.itemScalePercent = "itemScalePercent";
  }

  public fun friendlyName(): String? = Database.GetFriendlyName(this.DescriptionTag);

  public fun cleanName(): String {
    var text = this.friendlyName();
    if (text.isNullOrEmpty()) {
      text = Path(this.ItemId).nameWithoutExtension
    }
    val str = Item.ClipColorTag(text);
    val s1 = this.GetString("itemLevel");
    val s2 = this.GetString("levelRequirement");
    var num1 = 0;
    var num2 = 0;
    if (!s1.isNullOrEmpty()) {
      num1 = try {
        Integer.parseInt(s1)
      } catch (ex: NumberFormatException) {
        0;
      }
    }
    if (!s2.isNullOrEmpty()) {
      num2 = try {
        Integer.parseInt(s2)
      } catch (ex: NumberFormatException) {
        0;
      }
    }
    return if (num1 > num2) {
      "$str ($num1)"
    } else if (num1 < num2) {
      "$str ($num2)"
    } else {
      str
    }
  }


    public fun GetInt32(variable:String) = this.record.GetInt32(variable, 0);

    public fun GetSingle(variable:String):Float = this.record.GetSingle(variable, 0);

    public fun GetString(variable:String):String = this.record.GetString(variable, 0);

    public val ItemId = this.record.Id;

    public val RecordType = this.record.recordType;

    public val DescriptionTag = this.GetString(this.descriptionVar);

    public val ItemClassification = this.GetString(this.itemClassificationVar);

    public val QualityTag = this.GetString(this.qualityVar);

    public val StyleTag =this.GetString(this.styleVar);

    public val Bitmap = this.GetString(this.bitmapVar)

    public val ShardBitmap = this.GetString(this.shardBitmapVar)

    public val ItemClass = this.GetString(this.itemClassVar)

    public val ItemLevel = this.GetString("itemLevel")

    public val CompletedRelicLevel = this.GetInt32(this.completedRelicLevelVar)

    public val ItemScalePercent = (1.0 +  this.GetSingle(this.itemScalePercent)/ 100.0).toFloat()
  }

