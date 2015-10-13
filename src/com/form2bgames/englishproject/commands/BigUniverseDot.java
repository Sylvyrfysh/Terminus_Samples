package com.form2bgames.englishproject.commands;

import java.io.File;

import lib.syl.mathutils.Vec3;
import lib.syl.mathutils.Vec4;

import com.form2bgames.englishproject.EnglishMain;
import com.sylvyrfysh.terminusengine.command.Command;
import com.sylvyrfysh.terminusengine.screen.objmodel.Model;

public class BigUniverseDot implements Command{
	private Model model;
	private int cnt;
	private float smaller=1;
	public BigUniverseDot(int cnt){
		this.cnt=cnt;
		model=new Model(new File("res/models/ttest.obj"),EnglishMain.plainShader);
		System.out.println("#Faces: "+model.getNumFaces()*4);
	}
	@Override
	public void execute(){
		if(cnt==0){
			model.drawModel(new Vec3(0),new Vec3(0),new Vec4(0.3f));
			model.drawModel(new Vec3(1,2,0),new Vec3(0),new Vec4(0.3f));
			model.drawModel(new Vec3(-7,2,0),new Vec3(0),new Vec4(0.3f));
			model.drawModel(new Vec3(-3,-15,0),new Vec3(0),new Vec4(0.3f));
		}else{
			smaller-=(CommandPipeline.tg.getLastFrameDuration()/10000);
			model.drawModel(new Vec3(0),new Vec3(0),smaller,new Vec4(0.3f));
			if(smaller<.9f){
				CommandPipeline.next();
			}
		}
	}

	@Override
	public void undo(){
		// TODO Auto-generated method stub

	}
}