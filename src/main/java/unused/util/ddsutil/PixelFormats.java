package unused.util.ddsutil;

import unused.util.com.memo33.jsquish.Squish;

import unused.util.jogl.DDSImage;
import unused.util.model.TextureImage;

/**
 * Collects pixelformat conversions and interfaces.
 * This is WIP and not used yet, as it is the prelude to refactoring.
 * @author danielsenff
 *
 */
public class PixelFormats {

	/**
	 * 
	 * @param pixelFormat DDSImage pixelformat
	 * @return
	 * @throws Exception
	 */
	public static Squish.CompressionType getSquishCompressionFormat(final int pixelFormat)
			throws Exception {
		switch(pixelFormat) { 
		case DDSImage.D3DFMT_DXT1:
			return Squish.CompressionType.DXT1;
		case DDSImage.D3DFMT_DXT3: 
			return Squish.CompressionType.DXT3;
		case DDSImage.D3DFMT_DXT5: 
			return Squish.CompressionType.DXT5;
		default:
			throw new Exception("given pixel format not supported compression format");
		}
	}
	
	/**
	 * Convert Integer-CompressionType of {@link DDSImage} to {@link Squish}-Enum
	 * @param compressionType D3DFMT
	 * @return Squish.CompressionType
	 */
	public static Squish.CompressionType selectedCompression(final int compressionType) {
		
		// TODO maybe do as hasmap?
//		 Hashtable numbers = new Hashtable();
//	     numbers.put("one", new Integer(1));
//	     numbers.put("two", new Integer(2));
//	     numbers.put("three", new Integer(3));
	 
		
		switch(compressionType) {
			default:
			case DDSImage.D3DFMT_A8R8G8B8:
				return null;
			case DDSImage.D3DFMT_DXT1:
				return Squish.CompressionType.DXT1;
			case DDSImage.D3DFMT_DXT3:
				return Squish.CompressionType.DXT3;
			case DDSImage.D3DFMT_DXT5:
				return Squish.CompressionType.DXT5;
			case DDSImage.D3DFMT_R8G8B8:
				return null;
			}
	}
	
	/**
	 * Returns the verbose Pixelformat this DDSFile for the pixelformat-code
	 * @param pixelformat
	 * @return String
	 */
	public static String verbosePixelformat(final int pixelformat) {
		// TODO get rid of such constructs
		switch(pixelformat) {
		default:
			return TextureImage.PixelFormat.Unknown.toString();
		case DDSImage.D3DFMT_A8R8G8B8:
			return TextureImage.PixelFormat.Unknown.toString();
		case DDSImage.D3DFMT_DXT1:
			return TextureImage.PixelFormat.DXT1.toString();
		case DDSImage.D3DFMT_DXT2:
			return TextureImage.PixelFormat.DXT2.toString();
		case DDSImage.D3DFMT_DXT3:
			return TextureImage.PixelFormat.DXT3.toString();
		case DDSImage.D3DFMT_DXT4:
			return TextureImage.PixelFormat.DXT4.toString();
		case DDSImage.D3DFMT_DXT5:
			return TextureImage.PixelFormat.DXT5.toString();
		case DDSImage.D3DFMT_R8G8B8:
			return TextureImage.PixelFormat.R8G8B8.toString();
		case DDSImage.D3DFMT_X8R8G8B8:
			return TextureImage.PixelFormat.X8R8G8B8.toString();
		}
	}
	
	/**
	 * Returns the internal Integer-value for the input pixelformat-Name
	 * @param pixelformatVerbose
	 * @return
	 */
	public static int verbosePixelformat(final String pixelformatVerbose) {
		// TODO get rid of such constructs
		if (pixelformatVerbose.equals(TextureImage.PixelFormat.DXT1.toString())) {
			return DDSImage.D3DFMT_DXT1;
		} else if (pixelformatVerbose.equals(TextureImage.PixelFormat.DXT2.toString())) {
			return DDSImage.D3DFMT_DXT2;
		} else if (pixelformatVerbose.equals(TextureImage.PixelFormat.DXT3.toString())) {
			return DDSImage.D3DFMT_DXT3;
		} else if (pixelformatVerbose.equals(TextureImage.PixelFormat.DXT4.toString())) {
			return DDSImage.D3DFMT_DXT4;
		} else if (pixelformatVerbose.equals(TextureImage.PixelFormat.DXT5.toString())) {
			return DDSImage.D3DFMT_DXT5;
		} else if (pixelformatVerbose == TextureImage.PixelFormat.R8G8B8.toString()) {
			return DDSImage.D3DFMT_R8G8B8;
		} else if (pixelformatVerbose.equals(TextureImage.PixelFormat.X8R8G8B8.toString())) {
			return DDSImage.D3DFMT_X8R8G8B8;
		} else if (pixelformatVerbose.equals(TextureImage.PixelFormat.A8R8G8B8.toString())) {
			return DDSImage.D3DFMT_A8R8G8B8;
		} else {
			return DDSImage.D3DFMT_UNKNOWN;
		}
	}
	
	/**
	 * TODO get rid of such constructs
	 * @param pixelformat
	 * @return
	 */
	public static int convertPixelformat(final TextureImage.PixelFormat pixelformat) {
		int format;
		switch(pixelformat) {
			default:
			case Unknown:
				format = DDSImage.D3DFMT_UNKNOWN;
				break;
			case DXT5:
				format = DDSImage.D3DFMT_DXT5;
				break;
			case DXT4:
				format = DDSImage.D3DFMT_DXT4;
				break;
			case DXT3:
				format = DDSImage.D3DFMT_DXT3;
				break;
			case DXT2:
				format = DDSImage.D3DFMT_DXT2;
				break;
			case DXT1:
				format = DDSImage.D3DFMT_DXT1;
				break;
			case A8R8G8B8:
				format = DDSImage.D3DFMT_A8R8G8B8;
				break;
			case X8R8G8B8:
				format = DDSImage.D3DFMT_X8R8G8B8;
				break;
			case R8G8B8:
				format = DDSImage.D3DFMT_R8G8B8;
				break;
		}
		return format;
	}
	
	/**
	 * Returns true if the pixelformat is compressed a kind of DXTn-Compression
	 * TODO The {@link DDSImage} specifies isCompressed even on D3DFMT_A8R8G8B8, D3DFMT_R8G8B8 and D3DFMT_X8R8G8B8
	 * this doesn't
	 * @param pixelformat DDSImage pixelformat
	 * @return boolean is compressed
	 */
	public static boolean isDXTCompressed(final int pixelformat) {
		switch(pixelformat) {
			default:
			case DDSImage.D3DFMT_A8R8G8B8:
			case DDSImage.D3DFMT_R8G8B8:
			case DDSImage.D3DFMT_X8R8G8B8:
				return false;
			case DDSImage.D3DFMT_DXT1:
			case DDSImage.D3DFMT_DXT2:
			case DDSImage.D3DFMT_DXT3:
			case DDSImage.D3DFMT_DXT4:
			case DDSImage.D3DFMT_DXT5:
				return true;			
		}
	}
}
