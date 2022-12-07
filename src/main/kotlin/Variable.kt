
  public class Variable(Name:String ,dataType: VariableDataType ,  numberOfValues:Int)
  {
    private lateinit var values: Array<String?>;
    public fun  NumberOfValues (): Int = values.size

//    public object this[int index]
//    {
//      get => this.values[index];
//      set => this.values[index] = value;
//    }

    init
    {
      this.values = arrayOfNulls<String>(numberOfValues)
    }

    public fun GetInt32(index:Int) :Int =  values[index]!!.toInt()
    public fun GetSingle(index:Int) :Float =  values[index]!!.toFloat()
    public fun GetString(index:Int) :String =  values[index]!!
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
