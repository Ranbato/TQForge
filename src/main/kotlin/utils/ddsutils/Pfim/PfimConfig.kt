
    public class PfimConfig(
        val bufferSize:Int = 0x8000,
            val targetFormat:TargetFormat = TargetFormat.Native,
            val decompress:Boolean = true,
        val applyColorMap:Boolean = true)
    {
//
//        public override bool Equals(object obj)
//        {
//            if (ReferenceEquals(null, obj)) return false;
//            if (ReferenceEquals(this, obj)) return true;
//            if (obj.GetType() != this.GetType()) return false;
//            return Equals((PfimConfig) obj);
//        }
//
//        protected bool Equals(PfimConfig other)
//        {
//            return ApplyColorMap == other.ApplyColorMap && Allocator.Equals(other.Allocator) &&
//                   BufferSize == other.BufferSize && TargetFormat == other.TargetFormat &&
//                   Decompress == other.Decompress;
//        }
//
//        public override int GetHashCode()
//        {
//            unchecked
//            {
//                var hashCode = ApplyColorMap.GetHashCode();
//                hashCode = (hashCode * 397) ^ Allocator.GetHashCode();
//                hashCode = (hashCode * 397) ^ BufferSize;
//                hashCode = (hashCode * 397) ^ (int) TargetFormat;
//                hashCode = (hashCode * 397) ^ Decompress.GetHashCode();
//                return hashCode;
//            }
//        }
//
//        public override string ToString()
//        {
//            return $"{nameof(ApplyColorMap)}: {ApplyColorMap}, {nameof(Allocator)}: {Allocator}, {nameof(BufferSize)}: {BufferSize}, {nameof(TargetFormat)}: {TargetFormat}, {nameof(Decompress)}: {Decompress}";
//        }
    }

