package servicePresenter;

import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;


@Path("/course")
public class Course {


	
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public String courseEdition(@FormParam("tittle") String tittle,
							 @FormParam("leader") String leader,
						     @FormParam("content") String content){
		
		
		return "coucou";
	}
	
}
