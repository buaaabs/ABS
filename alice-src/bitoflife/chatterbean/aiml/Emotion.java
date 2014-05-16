/*
Copyleft (C) 2005 H�lio Perroni Filho
xperroni@yahoo.com
ICQ: 2490863

This file is part of ChatterBean.

ChatterBean is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation; either version 2 of the License, or (at your option) any later version.

ChatterBean is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.

You should have received a copy of the GNU General Public License along with ChatterBean (look at the Documents/ directory); if not, either write to the Free Software Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307 USA, or visit (http://www.gnu.org/licenses/gpl.txt).
*/

package bitoflife.chatterbean.aiml;

import java.util.HashMap;

import hha.aiml.BotEmotion;

import org.xml.sax.Attributes;

import bitoflife.chatterbean.AliceBot;
import bitoflife.chatterbean.Context;
import bitoflife.chatterbean.Match;

public class Emotion extends TemplateElement
{
  /*
  Attributes
  */
	HashMap<String,Integer> map = new HashMap<String,Integer>();
	
	int vitality = 0; //活跃度         困倦-兴奋
	int happiness = 0; //快乐度	忧伤-快乐
	int confidence = 0;//置信度	不确定-确定
	int mighty =0; //强势度	恐惧-愤怒
//	范围均是-10000~10000
  
  /*
  Constructors
  */
	public int normalization(int value) {
		if (value < -10000)
			return -10000;
		if (value > 10000)
			return 10000;
		return value;
	}
  public Emotion(Attributes attributes)
  {
	  int len = attributes.getLength();
	  for (int i=0;i<len;++i)
	  {
		  map.put(attributes.getQName(i),normalization( Integer.parseInt(attributes.getValue(i))));
	  }
  }
  
  public Emotion(String name, Object... children)
  {
    super(children);
  }
  
  /*
  Methods
  */
  
  public boolean equals(Object obj)
  {
    if (obj == null) return false;
    Emotion compared = (Emotion) obj;
    return super.equals(compared);
  }
    
  public String process(Match match)
  {
    String output = super.process(match);
    
    if (match == null)
      output = "<emotion>" + output + "</emotion>";
    else
    {
//    	BotEmotion.main.ShowText(String.valueOf(map.get("happiness")));
    	if (map.containsKey("happiness"))
    		BotEmotion.changeHappiness(map.get("happiness"));
    	if (map.containsKey("confidence"))
    		BotEmotion.changeConfidence(map.get("confidence"));
    	if (map.containsKey("mighty"))
    		BotEmotion.changeMighty(map.get("mighty"));
    	if (map.containsKey("vitality"))
    		BotEmotion.changeVitality(map.get("vitality"));
    }
    //java.lang.System.out.println("Set:"+output);
    return output;
  }
}
