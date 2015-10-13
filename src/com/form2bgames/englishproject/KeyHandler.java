package com.form2bgames.englishproject;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import lib.syl.mathutils.Vec3;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import com.sylvyrfysh.terminusengine.screen.AbstractKeyHandler;

public class KeyHandler extends AbstractKeyHandler{
	public KeyHandler(){
		 setKey(Keyboard.KEY_S,new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0){
				Movement.mForward=arg0.getActionCommand()==AbstractKeyHandler.pressed?true:false;
			}
		});
	       setKey(Keyboard.KEY_W,new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0){
				Movement.mBack=arg0.getActionCommand()==AbstractKeyHandler.pressed?true:false;
			}
		});
	       setKey(Keyboard.KEY_A,new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0){
				Movement.mLeft=arg0.getActionCommand()==AbstractKeyHandler.pressed?true:false;
			}
		});
	       setKey(Keyboard.KEY_D,new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent arg0){
					Movement.mRight=arg0.getActionCommand()==AbstractKeyHandler.pressed?true:false;
				}
			});
	       setKey(Keyboard.KEY_Q,new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent arg0){
					Movement.mUp=arg0.getActionCommand()==AbstractKeyHandler.pressed?true:false;
				}
			});
	       setKey(Keyboard.KEY_E,new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent arg0){
					Movement.mDown=arg0.getActionCommand()==AbstractKeyHandler.pressed?true:false;
				}
			});
	       setKey(Keyboard.KEY_ESCAPE,new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0){
				System.exit(0);
			}
		});
	       setKey(Keyboard.KEY_LSHIFT,new ActionListener(){

				@Override
				public void actionPerformed(ActionEvent e){
					Movement.sPressed=e.getActionCommand()==AbstractKeyHandler.pressed?true:false;
				}
			});
	       setKey(Keyboard.KEY_LEFT,new ActionListener(){

				@Override
				public void actionPerformed(ActionEvent e){
					if(e.getActionCommand()==pressed){Movement.cam.rotation=new Vec3(0);}
				}
			});
	       setKey(Keyboard.KEY_L,new ActionListener(){

				@Override
				public void actionPerformed(ActionEvent e){
					if(e.getActionCommand()==pressed){Movement.lMove=true;}else{Movement.lMove=false;}
				}
			});
	       setKey(Keyboard.KEY_B,new ActionListener(){

				@Override
				public void actionPerformed(ActionEvent e){
					if(e.getActionCommand()==pressed){GL11.glClearColor(0,0,0,1);}else{GL11.glClearColor(0.5f,0.5f,0.5f,1.0f);}
				}
			});
	       
	       
	}
}
