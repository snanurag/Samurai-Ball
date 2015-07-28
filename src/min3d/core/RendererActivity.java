package min3d.core;

import min3d.Shared;
import min3d.handlers.CustomHandler;
import min3d.interfaces.ISceneController;
import android.app.Activity;
import android.graphics.Typeface;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.os.Handler;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.google.ads.AdRequest;
import com.google.ads.AdSize;
import com.google.ads.AdView;
import com.knb.R;

/**
 * Extend this class when creating your min3d-based Activity. 
 * Then, override initScene() and updateScene() for your main
 * 3D logic.
 * 
 * Override onCreateSetContentView() to change layout, if desired.
 * 
 * To update 3d scene-related variables from within the the main UI thread,  
 * override onUpdateScene() and onUpdateScene() as needed.
 */
public class RendererActivity extends Activity implements ISceneController
{
	public Scene scene;
	protected GLSurfaceView _glSurfaceView;
	
	protected Handler _initSceneHander;
	protected Handler _updateSceneHander;
	
    private boolean _renderContinuously;
    
    private AdView adView;

	final Runnable _initSceneRunnable = new Runnable() 
	{
        public void run() {
            onInitScene();
        }
    };
    
	final Runnable _updateSceneRunnable = new Runnable() 
    {
        public void run() {
            onUpdateScene();
        }
    };
    

    @Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);

		_initSceneHander = new Handler();
		_updateSceneHander = new Handler();
		
		//
		// These 4 lines are important.
		//
		Shared.context(this);
		Renderer r;
		
		scene = new Scene(this);

		//Custom code if block but not else 
		if(Shared.renderer()!=null){
			r = Shared.renderer();
			r.setScene(scene);
		}
		else{

			r = new Renderer(scene);
		}
		Shared.renderer(r);
		
		//Custom Code
		setContentView(R.layout.main);
		setTextViews();
		setHandlersForTextViews();
		_glSurfaceView = (GLSurfaceView) findViewById(R.id.glview);
		
//		_glSurfaceView.setBackgroundResource(R.drawable.background);
//		_glSurfaceView.setZOrderOnTop(true);
//		_glSurfaceView.getHolder().setFormat(PixelFormat.TRANSLUCENT);
		//till here	
		
        glSurfaceViewConfig();
		_glSurfaceView.setRenderer(r);
		_glSurfaceView.setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);
		
		//Custom Code
		//onCreateSetContentView();
		//till here	
		
		//Custom Code : For display of Ads
		FrameLayout layout = (FrameLayout) findViewById(R.id.main);
		AdView adView = new AdView(this, AdSize.BANNER, "a14f6d65a954acf");
	    layout.addView(adView);
	    adView.loadAd(new AdRequest());
	    //till here

		
	}
    
    /**
     * Any GlSurfaceView settings that needs to be executed before 
     * GLSurfaceView.setRenderer() can be done by overriding this method. 
     * A couple examples are included in comments below.
     */
    protected void glSurfaceViewConfig()
    {
	    // Example which makes glSurfaceView transparent (along with setting scene.backgroundColor to 0x0)
	    // _glSurfaceView.setEGLConfigChooser(8,8,8,8, 16, 0);
	    // _glSurfaceView.getHolder().setFormat(PixelFormat.TRANSLUCENT);

		// Example of enabling logging of GL operations 
		// _glSurfaceView.setDebugFlags(GLSurfaceView.DEBUG_CHECK_GL_ERROR | GLSurfaceView.DEBUG_LOG_GL_CALLS);
    }
	
	protected GLSurfaceView glSurfaceView()
	{
		return _glSurfaceView;
	}
	
	/**
	 * Separated out for easier overriding...
	 */
	protected void onCreateSetContentView()
	{
		setContentView(_glSurfaceView);
	}
	
	@Override
	protected void onResume() 
	{
		super.onResume();
		_glSurfaceView.onResume();
	}
	
	@Override
	protected void onPause() 
	{
		super.onPause();
		_glSurfaceView.onPause();
	}
	
	//Custom Code
	@Override
	protected void onDestroy() {
		if (adView != null) {
			adView.destroy();
		}
		super.onDestroy();
	}
	//till here
	
	/**
	 * Instantiation of Object3D's, setting their properties, and adding Object3D's 
	 * to the scene should be done here. Or any point thereafter.
	 * 
	 * Note that this method is always called after GLCanvas is created, which occurs
	 * not only on Activity.onCreate(), but on Activity.onResume() as well.
	 * It is the user's responsibility to build the logic to restore state on-resume.
	 */
	public void initScene()
	{
	}

	/**
	 * All manipulation of scene and Object3D instance properties should go here.
	 * Gets called on every frame, right before rendering.   
	 */
	public void updateScene()
	{
	}
	
    /**
     * Called _after_ scene init (ie, after initScene).
     * Unlike initScene(), gets called from the UI thread.
     */
    public void onInitScene()
    {
    }
    
    /**
     * Called _after_ updateScene()
     * Unlike initScene(), gets called from the UI thread.
     */
    public void onUpdateScene()
    {
    }
    
    /**
     * Setting this to false stops the render loop, and initScene() and onInitScene() will no longer fire.
     * Setting this to true resumes it. 
     */
    public void renderContinuously(boolean $b)
    {
    	_renderContinuously = $b;
    	if (_renderContinuously)
    		_glSurfaceView.setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);
    	
    	else
    		_glSurfaceView.setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
    }
    
	public Handler getInitSceneHandler()
	{
		return _initSceneHander;
	}
	
	public Handler getUpdateSceneHandler()
	{
		return _updateSceneHander;
	}

    public Runnable getInitSceneRunnable()
    {
    	return _initSceneRunnable;
    }
	
    public Runnable getUpdateSceneRunnable()
    {
    	return _updateSceneRunnable;
    }
    
    private void setHandlersForTextViews(){
    	
    	CustomHandler playHandler = new CustomHandler();
    	playHandler.setTextView((TextView)findViewById(R.id.play));
    	Shared.putHandler(R.id.play, playHandler);
    	
    	CustomHandler highestHandler = new CustomHandler();
    	highestHandler.setTextView((TextView)findViewById(R.id.highest));
		Shared.putHandler(R.id.highest, highestHandler);
		
		CustomHandler scoresHandler = new CustomHandler();
		scoresHandler.setTextView((TextView)findViewById(R.id.scores));
		Shared.putHandler(R.id.scores, scoresHandler);

		CustomHandler yourScoreHandler = new CustomHandler();
		yourScoreHandler.setTextView((TextView)findViewById(R.id.your_score));
		Shared.putHandler(R.id.your_score, yourScoreHandler);

		CustomHandler newBestHandler = new CustomHandler();
		newBestHandler.setTextView((TextView)findViewById(R.id.new_best));
		Shared.putHandler(R.id.new_best, newBestHandler);

		CustomHandler bestScoreHandler = new CustomHandler();
		bestScoreHandler.setTextView((TextView)findViewById(R.id.best_score));
    	Shared.putHandler(R.id.best_score, bestScoreHandler);
    	
		CustomHandler scoreEndHandler = new CustomHandler();
		scoreEndHandler.setTextView((TextView)findViewById(R.id.score_end));
		Shared.putHandler(R.id.score_end, scoreEndHandler);
		
		CustomHandler touchBladeHandler = new CustomHandler();
		touchBladeHandler.setTextView((TextView)findViewById(R.id.touch_blade));
		Shared.putHandler(R.id.touch_blade, touchBladeHandler);
		
    }
    
    private void setTextViews(){
    	
    	TextView play = (TextView)findViewById(R.id.play);
    	TextView highest = (TextView)findViewById(R.id.highest);
    	TextView scores = (TextView)findViewById(R.id.scores);
    	TextView yourScore = (TextView)findViewById(R.id.your_score);
    	TextView scoreEnd = (TextView)findViewById(R.id.score_end);
    	TextView newBest = (TextView)findViewById(R.id.new_best);
    	TextView bestScore = (TextView)findViewById(R.id.best_score);
    	TextView touchBlade = (TextView)findViewById(R.id.touch_blade);

    	Typeface face = Typeface.createFromAsset(getAssets(), "brush-script.ttf"); 
		
    	play.setTypeface(face); 
		highest.setTypeface(face);
		scores.setTypeface(face);
		yourScore.setTypeface(face);
		scoreEnd.setTypeface(face);
		newBest.setTypeface(face);
		bestScore.setTypeface(face);
		touchBlade.setTypeface(face);
		
    }
    
    
}
