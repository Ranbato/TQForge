package utils.ddsutils.Pfim
    data class MipMapOffset(val width:Int,val height:Int,val stride:Int,val dataOffset:Int, val dataLen:Int)
    {

         fun equals( other: MipMapOffset):Boolean
        {
            return stride == other.stride && width == other.width && height == other.height && dataOffset == other.dataOffset && dataLen == other.dataLen;
        }

        public override fun  toString():String
        {
            return "stride: ${stride}, width: ${width}, Height: ${height}, DataOffset: ${dataOffset}, DataLen: $dataLen";
        }

        public override fun equals(other:Any?):Boolean
        {
            if (other == null) return false
            if (other.javaClass != this.javaClass) return false;
            return equals(other as MipMapOffset);
        }

        public override fun hashCode(): Int {
                var hashCode = stride;
                hashCode = (hashCode * 397) xor width;
                hashCode = (hashCode * 397) xor height;
                hashCode = (hashCode * 397) xor dataOffset;
                hashCode = (hashCode * 397) xor dataLen;
                return hashCode;
            }
        }


