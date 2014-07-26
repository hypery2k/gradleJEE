/**
 * JEE6 gradle-based Demo App
 *
 * File: AppServlet.java, 23.07.2014, 12:49:55, mreinhardt
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
package de.martinreinhardt.jee.services.internal;

import java.util.Date;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;

/**
 * EJB sample for startup singelton bean
 * @author mreinhardt
 *
 */
@Startup
@Singleton(name = "startup")
public class StartupImpl {

	/**
	 * Remember startup time
	 */
	private Date startUpTime;

	/**
	 * Init the application start
	 */
	@PostConstruct
	public void init() {
		// save startup time
		this.setStartUpTime(new Date());
	}

	/**
	 * @return the startUpTime
	 */
	public Date getStartUpTime() {
		return startUpTime;
	}

	/**
	 * @param startUpTime
	 *          the startUpTime to set
	 */
	public void setStartUpTime(Date startUpTime) {
		this.startUpTime = startUpTime;
	}

}
