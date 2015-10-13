package com.form2bgames.englishproject;

import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.glDrawArrays;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL15.glBufferData;
import static org.lwjgl.opengl.GL15.glDeleteBuffers;
import static org.lwjgl.opengl.GL15.glGenBuffers;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

import java.nio.FloatBuffer;

import lib.syl.mathutils.Vec2;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL20;

import com.sylvyrfysh.terminusengine.screen.GLTextureHandler;
import com.sylvyrfysh.terminusengine.screen.shader.Shader;

public class FontRenderer{
	
	public static Integer fTex;

	public float[] data=new float[256];
	public float[] odata=new float[128];

	public void drawString(Vec2 pos,int charsInColumn, String text,Shader fontShader) {
		int texUnitLoc = GL20.glGetUniformLocation(fontShader.getID(), "texUnit");
		fontShader.useShader();
		GL20.glUniform1i(texUnitLoc,0);
		GL20.glUseProgram(0);
	    GLTextureHandler.setActiveTexture(fTex);
	    
	    int xoff=0,yoff=0,doff=0,odoff=0;
	    
	    for(char s:text.toCharArray()){
	    	CharCoords c=new CharCoords();
	    	c.x=(int)s%16;
	    	c.y=(int)s/16;
	    	
	    	xoff+=16;
	    	if(xoff==(charsInColumn*16)){
	    		xoff=0;
	    		yoff+=16;
	    	}
	    	
	    	data[doff]=pos.x+(xoff/Display.getWidth());
	    	data[doff+1]=pos.y+(yoff/Display.getHeight());
	    	data[doff+2]=0.002f;
	    	data[doff+3]=0.0f;
	    	odata[odoff]=c.x;
	    	odata[odoff+1]=c.y;
	    	
	    	doff+=4;
	    	odoff+=2;
	    	
			
	    	
	    }
	    int fvbo=glGenBuffers();
		FloatBuffer vertexPositionsBuffer=BufferUtils.createFloatBuffer(256);
		vertexPositionsBuffer.put(data);
		vertexPositionsBuffer.flip();
		glBindBuffer(GL_ARRAY_BUFFER, fvbo);
		glBufferData(GL_ARRAY_BUFFER, vertexPositionsBuffer, GL_STATIC_DRAW);
		
		int cvbo=glGenBuffers();
		vertexPositionsBuffer=BufferUtils.createFloatBuffer(128);
		vertexPositionsBuffer.put(odata);
		vertexPositionsBuffer.flip();
		glBindBuffer(GL_ARRAY_BUFFER, cvbo);
		glBufferData(GL_ARRAY_BUFFER, vertexPositionsBuffer, GL_STATIC_DRAW);
		
		int vao = glGenVertexArrays();
        glBindVertexArray(vao);
		
        glBindBuffer(GL_ARRAY_BUFFER, fvbo);
		glVertexAttribPointer(0, 4, GL_FLOAT, false, 0, 0);
		
		glBindBuffer(GL_ARRAY_BUFFER, cvbo);
		glVertexAttribPointer(1, 2, GL_FLOAT, false, 0, 0);
		
		glEnableVertexAttribArray(0);
		glEnableVertexAttribArray(1);
		
		glBindVertexArray(0);
		
		glDeleteBuffers(fvbo);
		vertexPositionsBuffer=null;
		glBindBuffer(GL_ARRAY_BUFFER, 0);
		fontShader.useShader();
		glBindVertexArray(vao);
		glDrawArrays(GL_TRIANGLES, 0, 256/4);
		glBindVertexArray(0);
		GL20.glUseProgram(0);
		
	    
	}
	
	
	
	static class CharCoords {
	    public int x, y;
	}
}
