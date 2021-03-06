/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package main;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import geo.Model3D;
import org.lwjgl.LWJGLException;

/**
 *
 * @author Wesley
 */
public class Game {
    private final int WINDOW_WIDTH = 1280;
    private final int WINDOW_HEIGHT = 720;
    
    private final String PLAYER1_MODEL_FILE = "Royo2.obj";
    private final String PLAYER1_TEXTURE_FILE = "RoyoUV_vf.png";
    
    private final String PLAYER2_MODEL_FILE = "falcon06_unrotated.obj";
    private final String PLAYER2_TEXTURE_FILE = "falcon01_simpletexture02.jpg";
    
    private final String GROUND_MODEL_FILE = "square.obj";
    private final String PAVEMENT_TEXTURE_FILE = "pavement04.jpg";
    
    private final String BUILDING_MODEL_FILE = "building1.obj";
    private final String BUILDING_TEXTURE_FILE = "mirror_building.jpg";
    private final String BURNING_BUILDING_TEXTURE_FILE = "burned_building04.jpg";
    
    private final String FLAME_MODEL_FILE = "square.obj";
    private final String FLAME_TEXTURE_FILE = "flame1.png";

    private int numOfBuildings = 5; // currently we square this number, so there are numOfBuildings x numOfBuildings
    
    private Window mainWindow;
    
    public Game(){
        mainWindow = new Window(WINDOW_WIDTH, WINDOW_HEIGHT);
    }
    
    public void initPlayer() throws LWJGLException{
        Model3D player1Model = new Model3D();
        player1Model.loadDataFromFile(PLAYER1_MODEL_FILE);
        player1Model.createCollider();
        mainWindow.addModel(player1Model, PLAYER1_TEXTURE_FILE);
        
        Model3D player2Model = new Model3D();
        player2Model.loadDataFromFile(PLAYER2_MODEL_FILE);
        player2Model.createCollider();
        mainWindow.addPlayer2(player2Model, PLAYER2_TEXTURE_FILE);
        
        Model3D flameModel = new Model3D();
        flameModel.loadDataFromFile(FLAME_MODEL_FILE);
        flameModel.setModelScaleX(0.05);
        flameModel.setModelScaleZ(0.3);
        flameModel.createCollider();
        mainWindow.addModel(flameModel, FLAME_TEXTURE_FILE);
        
        mainWindow.initPlayerController(player1Model, player2Model, flameModel);
    }
    
    private void generateEnv() {
        mainWindow.addBurnTexture(BURNING_BUILDING_TEXTURE_FILE);
        
    	Model3D groundModel = new Model3D();
    	groundModel.loadDataFromFile(GROUND_MODEL_FILE);
    	groundModel.setModelX(-50);
        groundModel.setModelZ(-450);
        groundModel.setModelScaleX(4);
        groundModel.setModelScaleZ(4);
    	mainWindow.addModel(groundModel, PAVEMENT_TEXTURE_FILE);
    	
    	Random rand = new Random();
    	
    	for(int i = 0; i < numOfBuildings + 1; i++)
    	{
    		for(int j = 0; j < numOfBuildings + 1; j++)
    		{
    			double scalar = rand.nextFloat() + .5;
		    	Model3D buildingModel = new Model3D();
		    	buildingModel.loadDataFromFile(BUILDING_MODEL_FILE);
		    	buildingModel.setModelX(50*i);
		    	buildingModel.setModelY(25*scalar); // 50 is the height of building1.obj
		    	buildingModel.setModelZ(-50*j - 100);
		    	buildingModel.setModelScaleX(.5);
		    	buildingModel.setModelScaleY(1*scalar);
		    	buildingModel.setModelScaleZ(.5);
                buildingModel.createCollider();
		    	mainWindow.addModel(buildingModel, BUILDING_TEXTURE_FILE);
    		}
    	}
    }
    
    public void run(){
        try{
            mainWindow.init();
            initPlayer();
            generateEnv();
            mainWindow.display();
        } catch (LWJGLException ex) {
            mainWindow.destroy();
        }
    }
}
