package bitoflife.chatterbean.aiml;

/*
 Copyleft (C) 2005 Hélio Perroni Filho
 xperroni@yahoo.com
 ICQ: 2490863

 This file is part of ChatterBean.

 ChatterBean is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation; either version 2 of the License, or (at your option) any later version.

 ChatterBean is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.

 You should have received a copy of the GNU General Public License along with ChatterBean (look at the Documents/ directory); if not, either write to the Free Software Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307 USA, or visit (http://www.gnu.org/licenses/gpl.txt).
 */

import org.xml.sax.Attributes;

import bitoflife.chatterbean.AliceBot;
import bitoflife.chatterbean.Context;
import bitoflife.chatterbean.Match;

public class Var extends TemplateElement {
	/*
	 * Attributes
	 */

	private String name;
	private String value;

	/*
	 * Constructors
	 */

	public Var(Attributes attributes) {
		name = attributes.getQName(0);
		value = attributes.getValue(0);
	}

	public Var(String name, Object... children) {
		super(children);
		this.name = name;
	}

	/*
	 * Methods
	 */

	public boolean equals(Object obj) {
		if (obj == null)
			return false;
		Var compared = (Var) obj;
		if (!name.equals(compared.name))
			return false;
		return super.equals(compared);
	}

	public String process(Match match) {
		String output = super.process(match);

		if ("name".equals(name)) {
			if (output.isEmpty()) {
				try {
					String val = (String) match.getCallback().getContext()
							.property("predicate." + value);
					// java.lang.System.out.println("get:"+value.toString());
					return (val != null ? val : "");
				} catch (NullPointerException e) {
					return "";
				}
			} else {
				AliceBot bot = match.getCallback();
				Context context = (bot != null ? bot.getContext() : null);
				if (context != null)
					context.property("var." + value, output);
			}
		} else {
			AliceBot bot = match.getCallback();
			Context context = (bot != null ? bot.getContext() : null);
			if (context != null)
				context.property("var." + name, value);
		}
		// java.lang.System.out.println("Set:"+output);
		return output;
	}

	@Override
	public String toString() {
		StringBuilder value = new StringBuilder();
		value.append("<var name=\"" + name + "\">");
		for (TemplateElement i : children())
			value.append(i.toString());
		value.append("</var>");

		return value.toString();
	}
}
