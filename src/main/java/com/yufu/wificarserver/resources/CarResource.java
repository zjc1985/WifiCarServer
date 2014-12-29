package com.yufu.wificarserver.resources;

import java.io.IOException;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.yufu.wificarserver.connector.SocketCarConnector;

@Path("/car")
public class CarResource {

	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String carInfo() throws IOException {
		SocketCarConnector.getInstance().send("carInfo");
		return SocketCarConnector.getInstance().getCarStatus().toString();
	}

	@Path("/forward")
	@POST
	@Produces(MediaType.TEXT_PLAIN)
	public String carForward() throws IOException {
		SocketCarConnector.getInstance().send("go");
		return "post received";
	}

	@Path("/back")
	@POST
	@Produces(MediaType.TEXT_PLAIN)
	public String carBack() throws IOException {
		SocketCarConnector.getInstance().send("back");
		return "post received";
	}

	@Path("/left")
	@POST
	@Produces(MediaType.TEXT_PLAIN)
	public String carLeft() throws IOException {
		SocketCarConnector.getInstance().send("left");
		return "post received";
	}

	@Path("/right")
	@POST
	@Produces(MediaType.TEXT_PLAIN)
	public String carRight() throws IOException {
		SocketCarConnector.getInstance().send("right");
		return "post received";
	}

	@Path("/toggleStream")
	@POST
	@Produces(MediaType.TEXT_PLAIN)
	public String toggleStream() throws IOException {
		SocketCarConnector.getInstance().send("toggleStream");
		return "post received";
	}
	
	@Path("/command")
	@POST
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.TEXT_PLAIN)
	public String command(String command) throws IOException {
		SocketCarConnector.getInstance().send(command);
		return "command "+command +" received";
	}

}
