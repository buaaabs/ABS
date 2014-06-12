/*
Copyleft (C) 2005 Hélio Perroni Filho
xperroni@yahoo.com
ICQ: 2490863

This file is part of ChatterBean.

ChatterBean is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation; either version 2 of the License, or (at your option) any later version.

ChatterBean is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.

You should have received a copy of the GNU General Public License along with ChatterBean (look at the Documents/ directory); if not, either write to the Free Software Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307 USA, or visit (http://www.gnu.org/licenses/gpl.txt).
*/

package bitoflife.chatterbean.aiml;

import org.xml.sax.Attributes;
import bitoflife.chatterbean.Match;
import bitoflife.chatterbean.Match.Section;

import static bitoflife.chatterbean.Match.Section.PATTERN;

public class Sharp extends TemplateElement
{
  /*
  Attributes
  */

  private int index;

  /*
  Constructor
  */
  
  public Sharp(Attributes attributes)
  {
    String value = attributes.getValue(0);
    index = (value != null ? Integer.parseInt(value) : 1);
  }
  
  public Sharp(int index)
  {
    this.index = index;
  }
  
  /*
  Methods
  */

  public boolean equals(Object obj)
  {
    if (obj == null || !(obj instanceof Sharp))
      return false;
    else
    {
      Sharp star = (Sharp) obj;
      return (index == star.index);
    }
  }

  public int hashCode()
  {
    return index;
  }
  
  public String toString()
  {
    return "<sharp index=\"" + index + "\"/>";
  }

  public String process(Match match)
  {
    String wildcard = match.wildcard(Section.REGEX, index);
  //  java.lang.System.out.println("Star:"+wildcard);
    return (wildcard != null ? wildcard.trim() : "");
  }
}
