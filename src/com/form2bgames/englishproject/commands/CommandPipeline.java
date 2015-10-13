package com.form2bgames.englishproject.commands;

import com.sylvyrfysh.terminusengine.TerminusGame;
import com.sylvyrfysh.terminusengine.command.Command;

public class CommandPipeline{
	private static Command[] commands;
	private static Command cCommand;
	private static int count=-1;
	public static TerminusGame tg;
	public static void init(TerminusGame tg){
		CommandPipeline.tg=tg;
		commands=new Command[]{
				new BigUniverseDot(0),
				new BigUniverseDot(1),
			};
	}
	public static void next(){
		count++;
		cCommand=commands[count];
	}
	public static void render(){
		cCommand.execute();
	}
}
