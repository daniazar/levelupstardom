/*
 * Copyright (c) 2009-2010 jMonkeyEngine
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are
 * met:
 *
 * * Redistributions of source code must retain the above copyright
 *   notice, this list of conditions and the following disclaimer.
 *
 * * Redistributions in binary form must reproduce the above copyright
 *   notice, this list of conditions and the following disclaimer in the
 *   documentation and/or other materials provided with the distribution.
 *
 * * Neither the name of 'jMonkeyEngine' nor the names of its contributors
 *   may be used to endorse or promote products derived from this software
 *   without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED
 * TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package mygame.stage.gui;

import com.jme3.app.SimpleApplication;
import com.jme3.bullet.PhysicsSpace;
import com.jme3.font.BitmapFont;
import com.jme3.input.FlyByCamera;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Geometry;
import java.awt.Dimension;
import mygame.stage.GameStageEnvironment;
import mygame.stage.scene.SceneObjectImpl;

public class TestTimer extends SimpleApplication implements GameStageEnvironment{

     public static void main(String[] args){
        TestTimer app = new TestTimer();
        app.start();
    }

    @Override
    public void simpleInitApp() {

        guiNode.attachChild(new LevelController(this, new SceneObjectImpl(new Geometry()), new SceneObjectImpl(new Geometry()), 10));

        //env.getGuiNode().attachChild(new LevelController(env, new SceneObjectImpl(new Geometry()), new SceneObjectImpl(new Geometry()), 10));


    }

    public <T> T getGlobalProperty(Object key, Class<T> type) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void setGlobalProperty(Object key, Object value) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public PhysicsSpace getPhysicsSpace() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Dimension getScreenSize() {
      return new Dimension(settings.getWidth(), settings.getHeight());
    }

    public void setDefaultInputState(boolean enabled) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public FlyByCamera getFlyCamera() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public BitmapFont getGuiFont(){
        return guiFont;
    }

     public ViewPort getViewPort(){
         return viewPort;
     }
}
