package net.shadowmage.ancientwarfare.core.gui.elements;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.shadowmage.ancientwarfare.core.config.AWLog;
import net.shadowmage.ancientwarfare.core.gui.GuiContainerBase.ActivationEvent;
import net.shadowmage.ancientwarfare.core.gui.Listener;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;


/**
 * base class for gui elements that contain other elements
 * E.G. Scrollable area, Tabbed area, Multi-button controls
 * 
 * @author John
 *
 */
public class Composite extends GuiElement
{

protected List<GuiElement> elements = new ArrayList<GuiElement>();

public Composite(int topLeftX, int topLeftY, int width, int height)
  {
  super(topLeftX, topLeftY, width, height); 
  addDefaultListeners();
  this.keyboardInterface = true;
  this.mouseInterface = true;
  }

/**
 * sub-classes should override this method to add their custom default listeners. * 
 */
protected void addDefaultListeners()
  {
  this.addNewListener(new Listener(Listener.ALL_EVENTS)
    {
    @Override
    public boolean onEvent(ActivationEvent evt)
      {
      if((evt.type & Listener.KEY_TYPES) != 0)
        {
        for(GuiElement element : elements)
          {
          element.handleKeyboardInput(evt);
          }
        }
      else if((evt.type & Listener.MOUSE_TYPES) != 0)
        {
        AWLog.logDebug("sending mouse event to elements...");
        if(isMouseOverElement(evt.mx, evt.my))
          {
          /**
           * adjust mouse event position for relative to composite
           */
          int x = evt.mx;
          int y = evt.my;
//          evt.mx-=renderX;
//          evt.my-=renderY;
          for(GuiElement element : elements)
            {
            element.handleMouseInput(evt);
            }
          evt.mx = x;
          evt.my = y;
          }
        else if(evt.type==Listener.MOUSE_UP)
          {
          for(GuiElement element : elements)
            {
            element.setSelected(false);
            }
          }
        }
      return true;
      }
    });
  }

@Override
public void render(int mouseX, int mouseY, float partialTick)
  {
  /**
   * adjust mouse input position for relative to composite
   */
  if(!isMouseOverElement(mouseX, mouseY))
    {
    mouseX = Integer.MIN_VALUE;
    mouseY = Integer.MIN_VALUE;
//    mouseX-=renderX;
//    mouseY-=renderY;
    }
  else
    {
    }    
  setViewport();
  Minecraft.getMinecraft().renderEngine.bindTexture(backgroundTextureLocation);
  this.renderQuarteredTexture(256, 256, 0, 0, 256, 240, renderX, renderY, width, height);
//  GL11.glTranslatef(renderX, renderY, 0);
  for(GuiElement element : this.elements)
    {
//    if(element.renderY > height || element.renderY + element.height <0)
//      {
//      continue;
//      }
    element.render(mouseX, mouseY, partialTick);
    }    
  resetViewport();
  }

public void addGuiElement(GuiElement element)
  {
  this.elements.add(element);
  }

protected void setViewport()
  {    
  Minecraft mc = Minecraft.getMinecraft();
  ScaledResolution scaledRes = new ScaledResolution(mc.gameSettings, mc.displayWidth, mc.displayHeight);
  int guiScale = scaledRes.getScaleFactor();
  GL11.glEnable(GL11.GL_SCISSOR_TEST);  
  
  GL11.glScissor(renderX*guiScale, mc.displayHeight - renderY*guiScale - height*guiScale, width*guiScale, height*guiScale);
  }

protected void resetViewport()
  {
  GL11.glDisable(GL11.GL_SCISSOR_TEST);
  }

/**
 * sub-classes of Composite should override this method
 * to feed their current scrolled position into their elements
 * (e.g. scrollbar composite will use scrollbar pos)
 */
@Override
public void updateRenderPosition(int guiLeft, int guiTop)
  {
  super.updateRenderPosition(guiLeft, guiTop);
  updateElementPositions(guiLeft, guiTop);
  }

protected void updateElementPositions(int guiLeft, int guiTop)
  {
  for(GuiElement element : this.elements)
    {
    element.updateRenderPosition(renderX, renderY);
    }
  }

}