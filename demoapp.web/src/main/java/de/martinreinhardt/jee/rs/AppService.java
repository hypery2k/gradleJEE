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
package de.martinreinhardt.jee.rs;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Sample Info REST service, showing system information
 * 
 * @author mreinhardt
 * 
 */
@Path("app")
public class AppService {

	public static final String INF_ATTR_HOSTNAME = "hostname";

	public static final String INF_ATTR_MEM_FREE = "freeMemory";

	@Path("memory")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public long freeMem() {
		long memory = Runtime.getRuntime().freeMemory();
		return memory;
	}

	@Path("info")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Object info() {
		Response.ResponseBuilder response = null;

		Map<String, String> responseObj = new HashMap<String, String>();
		// build up info JSON
		responseObj.put(INF_ATTR_HOSTNAME, "");
		responseObj.put(INF_ATTR_MEM_FREE, String.valueOf(Runtime.getRuntime().freeMemory()));

		response = Response.status(Response.Status.OK).entity(responseObj);

		return response;
	}

}
