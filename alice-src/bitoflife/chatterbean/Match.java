/*
Copyleft (C) 2005 Hélio Perroni Filho
xperroni@yahoo.com
ICQ: 2490863

This file is part of ChatterBean.

ChatterBean is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation; either version 2 of the License, or (at your option) any later version.

ChatterBean is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.

You should have received a copy of the GNU General Public License along with ChatterBean (look at the Documents/ directory); if not, either write to the Free Software Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307 USA, or visit (http://www.gnu.org/licenses/gpl.txt).
 */

package bitoflife.chatterbean;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bitoflife.chatterbean.text.Sentence;

import static bitoflife.chatterbean.text.Sentence.ASTERISK;

/**
 * Contains information about a match operation, which is needed by the classes
 * of the <code>bitoflife.chatterbean.aiml</code> to produce a proper response.
 */
public class Match implements Serializable {
	/*
	 * Inner Classes
	 */

	public enum Section {
		PATTERN, THAT, TOPIC, INPUT, REGEX;
	}

	/*
	 * Attributes
	 */

	/**
	 * Version class identifier for the serialization engine. Matches the number
	 * of the last revision where the class was created / modified.
	 */
	private static final long serialVersionUID = 8L;

	private final Map<Section, List<String>> sections = new HashMap<Section, List<String>>();

	private AliceBot callback;

	private Sentence uinput;

	private Sentence input;

	private Sentence that;

	private Sentence topic;

	private String[] matchPath;

	{
		sections.put(Section.PATTERN, new ArrayList<String>(2)); // Pattern
																	// wildcards
		sections.put(Section.THAT, new ArrayList<String>(2)); // That wildcards
		sections.put(Section.TOPIC, new ArrayList<String>(2)); // Topic
																// wildcards
		sections.put(Section.INPUT, new ArrayList<String>(2));
		sections.put(Section.REGEX, new ArrayList<String>(2));
	}

	/*
	 * Constructor
	 */

	public Match() {
	}

	public Match(AliceBot callback, String[] str) {
		this.callback = callback;
		matchPath = str;
	}

	public Match(AliceBot callback, Sentence User_input, Sentence that,
			Sentence input, Sentence topic) {
		this.callback = callback;
		this.uinput = User_input;
		this.input = input;
		this.that = that;
		this.topic = topic;
		setUpMatchPath(User_input.normalized(), that.normalized(),
				input.normalized(), topic.normalized());
	}

	public Match(Sentence input) {
		this(null, input, ASTERISK, ASTERISK, ASTERISK);
	}

	/*
	 * Methods
	 */

	private void appendWildcard(List<String> section, Sentence source,
			int beginIndex, int endIndex) {
		if (beginIndex == endIndex)
			section.add(0, "");
		else
			try {
				section.add(0, source.original(beginIndex, endIndex));
			} catch (Exception e) {
				// throw new RuntimeException("Source: {\"" +
				// source.getOriginal() + "\", \"" + source.getNormalized() +
				// "\"}\n" +
				// "Begin Index: " + beginIndex + "\n" +
				// "End Index: " + endIndex, e);
			}
	}

	private void setUpMatchPath(String[] pattern, String[] that,
			String[] input, String[] topic) {
		int m = pattern.length, n = that.length, o = topic.length, p = input.length;
		matchPath = new String[m + 1 + n + 1 + o + 1 + p];
		matchPath[m] = "<THAT>";
		matchPath[m + 1 + n] = "<TOPIC>";
		matchPath[m + 1 + n + 1 + o] = "<INPUT>";

		System.arraycopy(pattern, 0, matchPath, 0, m);
		System.arraycopy(that, 0, matchPath, m + 1, n);
		System.arraycopy(topic, 0, matchPath, m + 1 + n + 1, o);
		System.arraycopy(input, 0, matchPath, m + 1 + n + 1 + o + 1, p);
	}

	public void appendRegex(int begin, int end) {
		appendWildcard(sections.get(Section.REGEX), uinput, begin, end);
	}

	public void appendWildcard(int beginIndex, int endIndex) {
		int uinputLength = uinput.length();
		if (beginIndex <= uinputLength) {
			appendWildcard(sections.get(Section.PATTERN), uinput, beginIndex,
					endIndex);
			return;
		}

		beginIndex = beginIndex - (uinputLength + 1);
		endIndex = endIndex - (uinputLength + 1);

		int thatLength = that.length();
		if (beginIndex <= thatLength) {
			appendWildcard(sections.get(Section.THAT), that, beginIndex,
					endIndex);
			return;
		}

		beginIndex = beginIndex - (thatLength + 1);
		endIndex = endIndex - (thatLength + 1);

		int topicLength = topic.length();
		if (beginIndex <= topicLength)
			appendWildcard(sections.get(Section.TOPIC), topic, beginIndex,
					endIndex);

		beginIndex = beginIndex - (topicLength + 1);
		endIndex = endIndex - (topicLength + 1);

		int inputLength = input.length();
		if (beginIndex < inputLength)
			appendWildcard(sections.get(Section.INPUT), input, beginIndex,
					endIndex);
	}

	/**
	 * Gets the contents for the (index)th wildcard in the matched section.
	 */
	public String wildcard(Section section, int index) {
		List<String> wildcards = sections.get(section);
		// fixed by lcl
		if (wildcards.size() == 0)
			return "";
		int i = index - 1;
		if (i < wildcards.size() && i > -1)
			return wildcards.get(i);
		else
			return "";
	}

	/*
	 * Properties
	 */

	public AliceBot getCallback() {
		return callback;
	}

	public void setCallback(AliceBot callback) {
		this.callback = callback;
	}

	public String[] getMatchPath() {
		return matchPath;
	}

	public String getMatchPath(int index) {
		return matchPath[index];
	}

	public int getMatchPathLength() {
		return matchPath.length;
	}
}
