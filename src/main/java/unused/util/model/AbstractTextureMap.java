/**
 * 
 */
package unused.util.model;

import unused.util.com.memo33.jsquish.Squish;


import java.awt.image.BufferedImage;
import java.nio.ByteBuffer;


import unused.util.compression.DXTBufferCompressor;

import unused.util.ddsutil.PixelFormats;


/**
 * Abstract TextureMap
 * @author danielsenff
 *
 */
public abstract class AbstractTextureMap implements TextureMap {

	public AbstractTextureMap() {}
	
	@Override
	public ByteBuffer[] getDXTCompressedBuffer(final int pixelformat) 
			throws Exception {
		Squish.CompressionType compressionType = PixelFormats.getSquishCompressionFormat(pixelformat);
		return this.getDXTCompressedBuffer(compressionType );
	}
	
	/**
	 * @param bi
	 * @param compressionType
	 * @return
	 */
	@Override
	public ByteBuffer compress(final BufferedImage bi, 
			final Squish.CompressionType compressionType) {
		DXTBufferCompressor compi = new DXTBufferCompressor(bi, compressionType);
		return compi.getByteBuffer();
	}

}
