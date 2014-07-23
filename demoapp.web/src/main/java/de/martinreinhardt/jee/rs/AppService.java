package de.martinreinhardt.jee.rs;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("app")
public class AppService {

	@Path("memory")
	@GET
  @Produces(MediaType.APPLICATION_JSON)
	public long freeMem() {
		long memory = Runtime.getRuntime().freeMemory();
		return memory;
	}

}
