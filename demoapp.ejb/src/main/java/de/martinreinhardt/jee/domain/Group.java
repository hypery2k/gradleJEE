/**
 * JEE6 gradle-based Demo App
 *
 * File: Group.java, 28.07.2014, 10:49:55, mreinhardt
 *
 * @project https://github.com/hypery2k/gradleJEE
 *
 * @copyright 2014 Martin Reinhardt contact@martinreinhardt-online.de
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 */
package de.martinreinhardt.jee.domain;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.xml.bind.annotation.XmlElement;

import de.martinreinhardt.jee.domain.validation.ValidateDateRange;
import de.martinreinhardt.jee.util.RegExHelper;

@ValidateDateRange(start = "from", end = "to")
public class Group implements Serializable {

	/**
	 * Serial ID
	 */
	private static final long serialVersionUID = 5568409075053724809L;

	@NotNull
	@XmlElement
	@Pattern(regexp = RegExHelper.SIMPLE_NAME, message = "{validation.person.name.invalid}")
	private String            name;

	@XmlElement
	@NotNull
	private Date              from;

	@XmlElement
	@NotNull
	private Date              to;

	public Group() {
		super();
	}

	public Group(String name, Date from, Date to) {
		super();
		this.name = name;
		this.from = from;
		this.to = to;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getFrom() {
		return from;
	}

	public void setFrom(Date from) {
		this.from = from;
	}

	public Date getTo() {
		return to;
	}

	public void setTo(Date to) {
		this.to = to;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Group [");
		if (name != null) {
			builder.append("name=");
			builder.append(name);
			builder.append(", ");
		}
		if (from != null) {
			builder.append("from=");
			builder.append(from);
			builder.append(", ");
		}
		if (to != null) {
			builder.append("to=");
			builder.append(to);
		}
		builder.append("]");
		return builder.toString();
	}

}
