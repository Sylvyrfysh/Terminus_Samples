package com.form2bgames.englishproject;

import static org.lwjgl.opengl.GL11.GL_BACK;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_CULL_FACE;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_TEST;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glCullFace;
import static org.lwjgl.opengl.GL11.glEnable;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.DisplayMode;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

import com.form2bgames.englishproject.commands.CommandPipeline;
import com.sylvyrfysh.terminusengine.TerminusGame;
import com.sylvyrfysh.terminusengine.screen.GLTextureHandler;
import com.sylvyrfysh.terminusengine.screen.shader.Shader;
import com.sylvyrfysh.terminusengine.screen.shader.ShaderLocation;

public class EnglishMain extends TerminusGame{
	private static int forceX=-1,forceY=-1;
	public static void main(String[] args){
		new Screen();
		System.out.println((int)' ');
		for(String s:args){
			if(s.startsWith("-res")){
				int start=4;
				int eFirst=s.indexOf('x');
				int sStart=eFirst+1;
				forceX=Integer.parseInt(s.substring(start,eFirst));
				forceY=Integer.parseInt(s.substring(sStart,s.length()));
			}
		}
		TerminusGame tg=new EnglishMain();
		tg.preInit();
		System.setProperty("org.lwjgl.util.Debug","true");
		try{
			DisplayMode dm=null;
			if(forceX!=-1&&forceY!=-1){
				dm=new DisplayMode(forceX,forceY);
			}
			tg.start("English Project",dm,true,false,null);
		}catch(Exception e){
			// TODO Auto-generated catch block
			Sys.alert("error",e.getMessage());
		}
	}
	public static Shader plainShader,fontShader;
	Texture font;
	FontRenderer fr=new FontRenderer();
	@Override
	protected void init(){
	    glClearColor(0.5f,0.5f,0.5f,1.0f);
		//glClearColor(1,0,0,0);
	    glEnable(GL_DEPTH_TEST);
	    glEnable(GL_CULL_FACE);
	    glCullFace(GL_BACK);
	    try{
			Mouse.create();
		}catch(LWJGLException e2){
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
	    Mouse.setGrabbed(true);
	    try{
			font=TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("res/fonts/ascii.png"));
		}catch(IOException e1){
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	    
	    kh=new KeyHandler();
	    try{
	    	plainShader=new Shader(new BufferedReader(new FileReader(new File("res/shaders/vertShader"))),new BufferedReader(new FileReader(new File("res/shaders/fragShader"))),
					new ShaderLocation[] {new ShaderLocation("position",0),new ShaderLocation("normals",1),new ShaderLocation("texCoords",2)
					,new ShaderLocation("Ks",3),new ShaderLocation("Ka",4),new ShaderLocation("Kd",5)}
			);
	    	fontInit();
		}catch(Exception e){
			Sys.alert("UH OH","We can't make the shader, so try again?");
			e.printStackTrace();
		}
	    CommandPipeline.init(this);
	    CommandPipeline.next();
	}
	@Override
	protected void update(float elapsed){
		kh.pollEvents();
		
		pollMouse();
		
	    Movement.update(elapsed);
	}
	private void pollMouse(){
		//float dX=(float)Mouse.getDX()/5;
		//float dY=-(float)Mouse.getDY()/5;
		
		
		
		Movement.cam.updateCamera(Movement.cam.position,Movement.cam.rotation/*.add(new Vec3((float)dY/5,(float)dX/5,0))*/);
		
	}
	@Override
	protected void render(){
		Movement.cam.useCameraView();
	    glClear(GL_COLOR_BUFFER_BIT|GL_DEPTH_BUFFER_BIT);
	    CommandPipeline.render();
	}
	@Override
	public void run(){
		// TODO Auto-generated method stub
		
	}
	static class Screen extends JFrame{
			/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		public Screen(){
			try{
				UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
			}catch(ClassNotFoundException|InstantiationException|IllegalAccessException|UnsupportedLookAndFeelException e){
				e.printStackTrace();
			}
			output.setEditable(false);
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			BorderLayout b=new BorderLayout();
			setLayout(b);
			setTitle("Server Console");
			CapturePane capturePane=new CapturePane();
			add(capturePane);
			setSize(800,500);
			setLocation(60,60);
			setVisible(true);
			PrintStream ps=System.out;
			PrintStream ep=System.err;
			System.setOut(new PrintStream(new StreamCapturer("STDOUT",capturePane,ps)));
			System.setErr(new PrintStream(new StreamCapturer("STDERR",capturePane,ep)));
		}
		public class CapturePane extends JPanel implements Consumer{
			/**
			 * 
			 */	
			private static final long serialVersionUID = 1L;
			public CapturePane(){
				setLayout(new BorderLayout());
				setMinimumSize(new Dimension(300,200));
				JScrollPane j=new JScrollPane(output);
				j.setSize(new Dimension(500,360));
				j.setMaximumSize(new Dimension(500,360));
				j.setMinimumSize(new Dimension(300,200));
				j.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder("Server Log"),BorderFactory.createEmptyBorder(5,5,5,5)),j.getBorder()));
				add(j);
				output.append("Version 2.2\n");
				output.setCaretPosition(output.getText().length());
			}
			@Override
			public void appendText(final String text){
				SimpleDateFormat sdf=new SimpleDateFormat("MM/dd/yy HH:mm:ss");
				Date dt=new Date();
				String S=sdf.format(dt);
				final String sText=S+": "+text;
				if(EventQueue.isDispatchThread()){
					output.append(sText);
					output.setCaretPosition(output.getText().length());
				}else{
					EventQueue.invokeLater(new Runnable(){
						@Override
						public void run(){
							appendText(text);
						}
					});
				}
			}
		}
		public interface Consumer{
			public void appendText(String text);
		}
		public class StreamCapturer extends OutputStream{
			private StringBuilder buffer;
			private String prefix;
			private Consumer consumer;
			private PrintStream old;
			public StreamCapturer(String prefix,Consumer consumer,PrintStream old){
				this.prefix=prefix;
				buffer=new StringBuilder(128);
				buffer.append("[").append(prefix).append("] ");
				this.old=old;
				this.consumer=consumer;
			}
			@Override
			public void write(int b)throws IOException{
				char c=(char) b;
				String value=Character.toString(c);
				buffer.append(value);
				if(value.equals("\n")){
					consumer.appendText(buffer.toString());
					buffer.delete(0, buffer.length());
					buffer.append("[").append(prefix).append("] ");
				}
				old.print(c);
			}
		}
		public static JTextArea output=new JTextArea();
	}
	private void fontInit() throws FileNotFoundException{
		fontShader=new Shader(new BufferedReader(new FileReader(new File("res/shaders/fontVert"))),new BufferedReader(new FileReader(new File("res/shaders/fontFrag"))),
				new ShaderLocation[] {new ShaderLocation("position",0),new ShaderLocation("position",0)}
		);
		//font=TextureLoader.getTexture("GIF",);
		FontRenderer.fTex=GLTextureHandler.addTexture(font);
	}
}
