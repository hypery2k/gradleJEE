package de.martinreinhardt.jee;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;

@Singleton
@Startup
public class StartupImpl {

		@PostConstruct
    public void init() {
        
    }

}
