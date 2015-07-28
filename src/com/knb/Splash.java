package com.knb;

import java.io.IOException;

import min3d.Shared;
import min3d.core.Renderer;
import min3d.core.TextureManager;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.knb.data.ObjectLoader;
import com.knb.data.RawObjCollection;

public class Splash extends Activity {
//
//	// ===========================================================
//	// "Constructors"
//	// ===========================================================
//
//	/** Called when the activity is first created. */
//	@Override
//	public void onCreate(Bundle icicle) {
//		super.onCreate(icicle);
//		setContentView(R.layout.splash);
//
//		Shared.context(Splash.this);
//		Shared.textureManager(new TextureManager());
//		Shared.renderer(new Renderer(null));
//
//		
//		/*
//		 * New Handler to start the Menu-Activity and close this Splash-Screen
//		 * after some seconds.
//		 */
//		new Handler().post(new Runnable() {
//			@Override
//			public void run() {
//
//				try {
//					Log.i("anurag", "before loading objs");
//					RawObjCollection.loadAllData(getResources());
//					Log.i("anurag", "after loading objs");
//				} catch (IOException e) {
//					Log.e("SamuraiBall", "Error in loading All Data");
//				}
//				new ObjectLoader(null, Splash.this).run();
//				/* Create an Intent that will start the Menu-Activity. */
//				Intent mainIntent = new Intent(Splash.this,
//						CommonActivity.class);
//				Splash.this.startActivity(mainIntent);
//				Splash.this.finish();
//
//			}
//		});
//	}
}