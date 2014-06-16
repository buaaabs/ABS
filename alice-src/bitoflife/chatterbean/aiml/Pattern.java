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

import hha.aiml.Jcseg;
import hha.main.MainActivity;

import java.util.Arrays;
import java.util.List;

import org.xml.sax.Attributes;

public class Pattern implements AIMLElement
{
  /*
  Attribute Section
  */

  String[] pattern;

  private int hashCode;
  
  /*
  Constructor Section
  */
  private boolean isToUseChineseTranslate = true;
  
  public Pattern()
  {
  }
  
  public Pattern(String pattern)
  {
	 String p = pattern.trim();
	 if(isToUseChineseTranslate) {    
		 p = Jcseg.chineseTranslate(p);
		 p.toUpperCase();
		 java.lang.System.out.println(p);
	 }
	 this.pattern = p.split(" ");
    
	 hashCode = Arrays.hashCode(this.pattern);
  }
  
  public Pattern(Attributes attributes)
  {
  }
  
  /*
  Method Section
  */
  
  public void appendChild(AIMLElement child)
  {
    String text = child.toString();
   // System.out.println(text);
    if(isToUseChineseTranslate) {    
		 text = Jcseg.chineseTranslate(text);
		 text = text.toUpperCase();
//		 MainActivity.main.ShowTextOnUIThread(text);
	//	 System.out.println(text);
	 }
    if (pattern == null)
      pattern = new String[] {text};
    else
    {
      int length = pattern.length;
      String[] larger = new String[length + 1];
      java.lang.System.arraycopy(pattern, 0, larger, 0, length);
      larger[length] = text;
      pattern = larger;
    }
  }
  
 
  
  public void appendChildren(List<AIMLElement> children)
  {
    StringBuilder builder = new StringBuilder();
    for (AIMLElement child : children)
      builder.append(child);
    
    String text = builder.toString().trim();
//    System.out.println(text);
    if(isToUseChineseTranslate) {    
		 text = Jcseg.chineseTranslate(text);
		 text = text.toUpperCase();
//		 MainActivity.main.ShowTextOnUIThread(text);
	//	 System.out.println(text);
	 }
    pattern = text.split(" ");
    hashCode = Arrays.hashCode(pattern);
  }

  public boolean equals(Object obj)
  {
    if (obj == null || !(obj instanceof Pattern)) return false;
    Pattern compared = (Pattern) obj;
    return Arrays.equals(pattern, compared.pattern);
  }

  public int hashCode()
  {
    return hashCode;
  }

  public String toString()
  {
    StringBuilder buffer = new StringBuilder();
    for (int i = 0, n = pattern.length;;)
    {
      buffer.append(pattern[i]);
      if (++i >= n) break;
      buffer.append(" ");
    }
    
    return "<pattern>"+buffer.toString()+"</pattern>";
  }
  
  /*
  Property Section
  */

  public String[] getElements()
  {
    return pattern;
  }

  public void setElements(String[] pattern)
  {
    this.pattern = pattern;
    hashCode = Arrays.hashCode(pattern);
  }
}
