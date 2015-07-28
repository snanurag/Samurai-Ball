package min3d;

import java.util.HashMap;

import min3d.core.Renderer;
import min3d.core.TextureManager;
import min3d.handlers.CustomHandler;
import android.content.Context;

/**
 * Holds static references to TextureManager, Renderer, and the application Context. 
 */
public class Shared 
{
	private static Context _context;
	private static Renderer _renderer;
	private static TextureManager _textureManager;
	private static HashMap<Integer, CustomHandler> handlerMap = new HashMap<Integer, CustomHandler>(); 
	
	public static Context context()
	{
		return _context;
	}
	public static void context(Context $c)
	{
		_context = $c;
	}

	public static Renderer renderer()
	{
		return _renderer;
	}
	public static void renderer(Renderer $r)
	{
		_renderer = $r;
	}
	
	/**
	 * You must access the TextureManager instance through this accessor
	 */
	public static TextureManager textureManager()
	{
		return _textureManager;
	}
	public static void textureManager(TextureManager $bm)
	{
		_textureManager = $bm;
	}
	
	public static void putHandler(int viewId, CustomHandler viewHandler){
		handlerMap.put(viewId, viewHandler);
	}
	
	public static CustomHandler getHandler(int viewId){
		return handlerMap.get(viewId);
	}
}
