package com.form2bgames.englishproject.commands;

import java.io.File;

import lib.syl.mathutils.Vec3;
import lib.syl.mathutils.Vec4;

import com.form2bgames.englishproject.EnglishMain;
import com.sylvyrfysh.terminusengine.command.Command;
import com.sylvyrfysh.terminusengine.screen.objmodel.Model;

public class SmallerUniverseDot implements Command{
	private Model model;
	private int dot;
	private Vec4 color=new Vec4(0.3f);
	public SmallerUniverseDot(int dot){
		this.dot=dot;
		model=new Model(new File("res/models/untitled.obj"),EnglishMain.plainShader);
	}
	@Override
	public void execute(){
		if(dot==0)
			model.drawModel(new Vec3(0),new Vec3(0),.9f,color);
		else{
			color.add(new Vec4(CommandPipeline.tg.getLastFrameDuration()/5000));
			if(color.x>1){
				color=new Vec4(1);
			}
			model.drawModel(new Vec3(0),new Vec3(0),.9f,color);
		}
	}

	@Override
	public void undo(){
		// TODO Auto-generated method stub

	}

}
