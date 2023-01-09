package unused.util.ddsutil;

import java.awt.image.BufferedImage;

import unused.util.model.MipMaps;
import unused.util.model.SingleTextureMap;
import unused.util.model.TextureMap;

/**
 * @author danielsenff
 *
 */
public class TextureFactory {

	/**
	 * @param generateMipMaps
	 * @param sourceImage
	 * @return
	 */
	public static TextureMap createTextureMap(final boolean generateMipMaps, final BufferedImage sourceImage) {
		TextureMap maps;
		if (generateMipMaps) {
			maps = new MipMaps();
			((MipMaps)maps).generateMipMaps(sourceImage);
		} else
			maps = new SingleTextureMap(sourceImage);
		
		return maps;
	}
}
