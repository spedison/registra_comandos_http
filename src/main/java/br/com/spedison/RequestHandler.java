package br.com.spedison;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.*;
import java.util.Date;
import java.util.Random;

public class RequestHandler extends HttpServlet {

    private static final String FILE_PATH = "./logs/comandos__"; // Arquivo onde os comandos serão salvos

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        StringBuilder requestData = new StringBuilder();

        // Captura informações da requisição
        requestData.append("Hora:       " + new Date()).append("\n");
        requestData.append("Método:     ").append(req.getMethod()).append("\n");
        requestData.append("Path:       ").append(req.getRequestURI()).append("\n");
        requestData.append("Params:     ").append(req.getQueryString()).append("\n");
        requestData.append("Headers:\n");

        // Captura os headers da requisição
        req.getHeaderNames().asIterator().forEachRemaining(header ->
                requestData.append("  ").append(header).append(": ").append(req.getHeader(header)).append("\n"));

        // Captura o corpo da requisição
        requestData.append("Body:\n");
        String line;
        try (BufferedReader reader = req.getReader()) {
            while ((line = reader.readLine()) != null) {
                requestData.append(line).append("\n");
            }
        }

        requestData.append("-----------------------------\n");

        salvarEmArquivo(requestData.toString());

        // Responde apenas com "OK"
        resp.setContentType("text/plain");
        resp.setStatus(HttpServletResponse.SC_OK);
        resp.getWriter().write("OK");
    }

    private void salvarEmArquivo(String data) {
        Random random = new Random();
        File file = new File(FILE_PATH + new Date() + "_" +random.nextInt()+".log");
        file.getParentFile().mkdirs(); // Garante que os diretórios existam

        try (FileWriter writer = new FileWriter(file, true);
             BufferedWriter bufferedWriter = new BufferedWriter(writer)) {
            bufferedWriter.write(data);
            bufferedWriter.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}