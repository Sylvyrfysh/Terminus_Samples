package com.form2bgames.englishproject;

import org.lwjgl.opengl.Display;

import lib.syl.mathutils.Vec3;

import com.sylvyrfysh.terminusengine.camera.Camera;
import com.sylvyrfysh.terminusengine.light.Light;

public class Movement{
	public static Camera cam;
	static{
		cam=new Camera(new Vec3(0,0,2f),new Vec3(0),67f,(float)Display.getWidth()/Display.getHeight(),0.001f,600f);
		cam.setSpotlight(new Light(new Vec3(0,0,0)));
	}
	public static boolean mForward=false,mBack=false,mLeft=false,mRight=false,mUp=false,mDown=false,cPressed=false,sPressed=false,lMove=false;
	public static float mSpeed=0.001f;
	
	public static Vec3 right=new Vec3(1,0,0),left=right.negate(),up=new Vec3(0,0,1),down=up.negate(),forward=new Vec3(0,0,1),back=forward.negate();
	
	public static void update(float elapsed){
		mSpeed=0.001f*(sPressed?10:1)*(cPressed?.1f:1)*elapsed;
		forward=cam.rotation;
		
		
		
		if(!lMove){
			if(mForward){
				moveForward();
			}
			if(mBack){
				cam.updateCamera(cam.position.add(new Vec3(0f,0f,-mSpeed)),cam.rotation);
			}
			if(mRight){
				cam.updateCamera(cam.position.add(new Vec3(mSpeed,0f,0f)),cam.rotation);
			}
			if(mLeft){
				cam.updateCamera(cam.position.add(new Vec3(-mSpeed,0f,0f)),cam.rotation);
			}
			if(mUp){
				cam.updateCamera(cam.position.add(new Vec3(0f,mSpeed,0f)),cam.rotation);
			}
			if(mDown){
				cam.updateCamera(cam.position.add(new Vec3(0f,-mSpeed,0f)),cam.rotation);
			}
		}else{
			if(mForward){
				cam.sl.pos.add(new Vec3(0f,0f,mSpeed));
			}
			if(mBack){
				cam.sl.pos.add(new Vec3(0f,0f,-mSpeed));
			}
			if(mRight){
				cam.sl.pos.add(new Vec3(mSpeed,0f,0f));
			}
			if(mLeft){
				cam.sl.pos.add(new Vec3(-mSpeed,0f,0f));
			}
			if(mUp){
				cam.sl.pos.add(new Vec3(0f,mSpeed,0f));
			}
			if(mDown){
				cam.sl.pos.add(new Vec3(0f,-mSpeed,0f));
			}
		}
		
	}
	private static void moveForward(){
		
		cam.updateCamera(cam.position.add(new Vec3(0f,0f,mSpeed)),cam.rotation);
		
	}
}
