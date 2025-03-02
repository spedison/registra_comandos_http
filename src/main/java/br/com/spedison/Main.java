package br.com.spedison;

import org.eclipse.jetty.ee10.servlet.ServletContextHandler;
import org.eclipse.jetty.ee10.servlet.ServletHolder;
import org.eclipse.jetty.server.*;

public class Main {
    public static void main(String[] args) throws Exception {

        // Ajusta os parâmetros comuns.
        if (args == null || args.length == 0) {
            args = new String[]{"0.0.0.0", "8080"};
        } else if (args.length == 1) {
            String[] newArgs = new String[]{"0.0.0.0", args[0]};
            args = newArgs;
        }

        int port = Integer.parseInt(args[1]); // Porta do servidor
        Server server = new Server();

        // Configurar para rodar no IP 0.0.0.0
        ServerConnector connector = new ServerConnector(server);
        connector.setHost(args[0]);
        connector.setPort(port);
        server.addConnector(connector);

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");
        server.setHandler(context);

        // Adiciona o Servlet para lidar com qualquer requisição
        context.addServlet(new ServletHolder(new RequestHandler()), "/*");

        System.out.println("🚀 Servidor rodando em http://0.0.0.0:" + port);
        server.start();
        server.join();
    }
}