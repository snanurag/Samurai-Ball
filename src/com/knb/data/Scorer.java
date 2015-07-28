package com.knb.data;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import min3d.Shared;
import min3d.handlers.CustomHandler;
import min3d.handlers.HandlerDataObject;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.knb.R;
import com.knb.constants.SBStore;
import com.knb.generators.InitBallStateGenerator;

public class Scorer {

	private static volatile int scores;
	
	private static volatile int bestScore;
	
	private static String FILENAME = "best_score";
	
	private static HandlerDataObject data = new HandlerDataObject();
	private static void increaseScoresHighest(){
		scores += 60;
	}
	
	private static void increaseScoresMedium(){
		scores +=40;
	}
	
	private static void increaseScoresLowest(){
		scores +=20;
	}
	
	public static void score(float speed){
		if (ContextData.getValue(SBStore.ACTIVITY) != null
				&& ContextData.getValue(SBStore.ACTIVITY).equals(
						SBStore.MAIN_ACTIVITY)){ 
			if(speed > InitBallStateGenerator.SPEED_HIGHEST_ZONE_START){
				increaseScoresHighest();
			}
			else if(speed > InitBallStateGenerator.SPEED_MEDIUM_ZONE_START){
				increaseScoresMedium();
			}
			else{
				increaseScoresLowest();
			}
			
			Handler scoresHandler = Shared.getHandler(R.id.scores);
			Message scoresMsg = scoresHandler.obtainMessage();
			scoresMsg.what = CustomHandler.EDIT_VIEW;
			scoresMsg.obj = data;
			data.setString("Score "+scores);
			scoresHandler.sendMessage(scoresMsg);
		}
	}
	
	public static void setScoreToZero(){
		scores = 0;
		Handler scoresHandler = Shared.getHandler(R.id.scores);
		Message scoresMsg = scoresHandler.obtainMessage();
		scoresMsg.what = CustomHandler.EDIT_VIEW;
		scoresMsg.obj = data;
		data.setString("Score "+scores);
		scoresHandler.sendMessage(scoresMsg);

	}
	
	public static void persistBest(){

		int best = readBest();
		if(best<scores){
			FileOutputStream fos;
			try {
				fos = Shared.context().openFileOutput(FILENAME, Context.MODE_PRIVATE);
				fos.write(String.valueOf(scores).getBytes());
				fos.close();

			} catch (FileNotFoundException e) {
				Log.e("SamuraiBall", "File not found while saving the best score "+e);
			} catch (IOException e) {
				Log.e("SamuraiBall", "Error in writing the best score "+e);
			}
		}
	}
	
	public static int readBest(){

		FileInputStream fis;
		try {
			fis = Shared.context().openFileInput(FILENAME);
			BufferedReader bfr = new BufferedReader(new InputStreamReader(fis));
			String line = bfr.readLine();
			if(line!=null){
				bestScore = Integer.parseInt(line);
			}

		} catch (FileNotFoundException e) {
			Log.e("SamuraiBall", "File not found while reading the best score "+e);
		} catch (IOException e) {
			Log.e("SamuraiBall", "Error in reading the best score "+e);
		}
		return bestScore;
	}
	
	public static String getCurrentScore(){
		return String.valueOf(scores);
	}
}
