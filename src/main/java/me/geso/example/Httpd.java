package me.geso.example;

import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.Slf4jRequestLog;
import org.eclipse.jetty.server.handler.AbstractHandler;
import org.eclipse.jetty.server.handler.HandlerCollection;
import org.eclipse.jetty.server.handler.RequestLogHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

public class Httpd {
	public static void main(String[] args) throws Exception {
		int port = 18080;
		Server server = new Server();

		// server.addEventListener(new MyListener());
		ReusePortServerConnector connector = new ReusePortServerConnector(server);
		connector.setPort(port);
		server.setConnectors(new Connector[]{connector});

		String id = UUID.randomUUID().toString();

		HandlerCollection handlers = new HandlerCollection();
		handlers.addHandler(new AbstractHandler() {
			@Override
			public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
				response.setContentType("text/html");
				response.setStatus(HttpServletResponse.SC_OK);
				response.getWriter().println("<h1>Hello " + id +"</h1>");
				baseRequest.setHandled(true);
			}
		});
		server.setHandler(handlers);

		Slf4jRequestLog requestLog = new Slf4jRequestLog();
		requestLog.setExtended(true);
		requestLog.setLogCookies(false);
		requestLog.setLogTimeZone("GMT");
		RequestLogHandler requestLogHandler = new RequestLogHandler();
		requestLogHandler.setRequestLog(requestLog);
		handlers.addHandler(requestLogHandler);

		server.start();
		server.join();
	}

}

