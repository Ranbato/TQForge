
  public class Variable(val Name:String ,val DataType: VariableDataType , val numberOfValues:Int,
    val string:Array<String?> = arrayOfNulls(numberOfValues)
  )
  {
    public fun  NumberOfValues (): Int = numberOfValues

//    public object this[int index]
//    {
//      get => this.values[index];
//      set => this.values[index] = value;
//    }


    public fun GetInt32(index:Int) :Int =  string[index]!!.toInt()
    public fun GetSingle(index:Int) :Float =  string[index]!!.toFloat()
    public fun GetString(index:Int) :String =  string[index]!!
    public fun setString(index:Int, value:String):Unit { string[index] = value}
//
//    public override string ToString()
//    {
//      string format = "{0}";
//      if (this.DataType == VariableDataType.Float)
//        format = "{0:f6}";
//      StringBuilder stringBuilder = new StringBuilder(64);
//      stringBuilder.Append(this.Name);
//      stringBuilder.Append(",");
//      for (int index = 0; index < this.NumberOfValues; ++index)
//      {
//        if (index > 0)
//          stringBuilder.Append(";");
//        object[] objArray = new object[1]
//        {
//          this.values[index]
//        };
//        stringBuilder.AppendFormat((IFormatProvider) CultureInfo.InvariantCulture, format, objArray);
//      }
//      stringBuilder.Append(",");
//      return stringBuilder.ToString();
//    }
//
//    public string ToStringValue()
//    {
//      string format = "{0}";
//      if (this.DataType == VariableDataType.Float)
//        format = "{0:f6}";
//      StringBuilder stringBuilder = new StringBuilder(64);
//      for (int index = 0; index < this.NumberOfValues; ++index)
//      {
//        if (index > 0)
//          stringBuilder.Append(", ");
//        object[] objArray = new object[1]
//        {
//          this.values[index]
//        };
//        stringBuilder.AppendFormat((IFormatProvider) CultureInfo.InvariantCulture, format, objArray);
//      }
//      return stringBuilder.ToString();
//    }
//
//
  }
