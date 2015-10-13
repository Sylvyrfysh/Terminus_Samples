package com.form2bgames.englishproject.commands;

import java.io.File;

import lib.syl.mathutils.Vec3;
import lib.syl.mathutils.Vec4;

import com.form2bgames.englishproject.EnglishMain;
import com.sylvyrfysh.terminusengine.command.Command;
import com.sylvyrfysh.terminusengine.screen.objmodel.Model;

public class Expand implements Command{
	private Model model;
	private float scalar=.02f;
	public Expand(){
		model=new Model(new File("res/models/expand.obj"),EnglishMain.plainShader);
	}
	@Override
	public void execute(){
		scalar+=Math.pow((CommandPipeline.tg.getLastFrameDuration()/1000),.575f);
		
		model.drawModel(new Vec3(0),new Vec3(0),scalar,new Vec4(1));
	}

	@Override
	public void undo(){
		// TODO Auto-generated method stub

	}

}
