package me.geso.example;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.*;

public class Test {

	public static void main(String[] args) {
		while (true) {
			try (CloseableHttpClient client = HttpClients.createDefault()) {
				HttpGet httpPost = new HttpGet("http://localhost:18080");

				CloseableHttpResponse response = client.execute(httpPost);
				String inputLine;
				try (BufferedReader in = new BufferedReader(new InputStreamReader(response.getEntity().getContent()))) {
					while ((inputLine = in.readLine()) != null)
						System.out.println(inputLine);
				}
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
	}
}
