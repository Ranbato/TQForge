package unused.util.compression;

import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.nio.ByteBuffer;

import unused.util.ddsutil.ByteBufferedImage;

public abstract class BufferDecompressor {

	
	protected ByteBuffer uncompressedBuffer;
	protected Dimension dimension;
	
	
	/**
	 * @return
	 */
	public BufferedImage getImage() {
		
		BufferedImage image = new ByteBufferedImage(
				this.dimension.width, 
				this.dimension.height, 
				this.uncompressedBuffer);
		return image;
	}
}
