import java.lang.Exception

public class DBRecord(val Id:String, val recordType:String) : Iterable<Variable>
  {
    private var variables:MutableMap<String, Variable>  = mutableMapOf()
    override fun iterator(): Iterator<Variable> {
      TODO("Not yet implemented")
    }


    public fun GetAllStrings( variableName:String):Array<String>?
    {
        val variable = this.variables[variableName.uppercase()];
        return if(variable != null){
            val allStrings = mutableListOf<String>()
            for (index in 0 until variable.NumberOfValues())
                allStrings[index] = variable.GetString(index)
            allStrings.toTypedArray()
        } else {
            null
        }
    }


    public fun GetInt32(variableName:String, index:Int):Int
    {
        return this.variables[variableName.uppercase()]?.GetInt32(index)?:0

    }

    public fun GetSingle( variableName:String,  index:Int):Float
    {

        return this.variables[variableName.uppercase()]?.GetSingle(index)?:0.0f

    }

    public fun GetString(variableName:String, index:Int):String
    {
        return this.variables[variableName.uppercase()]?.GetString(index)?:""
      }

    public fun Set( variable:Variable):Unit{this.variables[variable.Name.uppercase()]= variable}
//
//    IEnumerator IEnumerable.GetEnumerator() => (IEnumerator) this.GetEnumerator();
//
//    public string ToShortString() => string.Format((IFormatProvider) CultureInfo.CurrentCulture, "{0},{1},{2}", new object[3]
//    {
//      (object) this.Id,
//      (object) this.RecordType,
//      (object) this.Count
//    });
//
//    public void Write(string baseFolder, string fileName = null)
//    {
//      string path1 = Path.Combine(baseFolder, this.Id);
//      string path2 = Path.GetDirectoryName(path1);
//      if (fileName != null)
//      {
//        path1 = Path.Combine(baseFolder, fileName);
//        path2 = baseFolder;
//      }
//      if (!Directory.Exists(path2))
//        Directory.CreateDirectory(path2);
//      using (StreamWriter streamWriter = new StreamWriter(path1, false))
//      {
//        foreach (Variable variable in this)
//          streamWriter.WriteLine(variable.ToString());
//      }
//    }
//
//    public string Id { get; private set; }
//
//
    public fun Count() =this.variables.size
//
//    public Variable this[string variableName]
//    {
//      get
//      {
//        try
//        {
//          return this.variables[variableName.ToUpperInvariant()];
//        }
//        catch (KeyNotFoundException ex)
//        {
//          return (Variable) null;
//        }
//      }
//    }
  }

