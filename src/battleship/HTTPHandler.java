package battleship;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

public class HTTPHandler {

	public static void UpdateSite(String victoryString) throws ClientProtocolException, IOException {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		try {
			// Supply POST URL for online score board
			HttpPost httpPost = new HttpPost("https://battleship-scores-jaden71.c9users.io/victories");
			List<NameValuePair> nvps = new ArrayList<NameValuePair>();

			// Add parameters for sending
			nvps.add(new BasicNameValuePair("game", victoryString));

			httpPost.setEntity(new UrlEncodedFormEntity(nvps));

			// Send the POST request
			CloseableHttpResponse response2 = httpclient.execute(httpPost);
			// Get response
			try {
				HttpEntity entity2 = response2.getEntity();
				EntityUtils.consume(entity2);
			} finally {
				response2.close();
			}
		} finally {
			// Close the connection client
			httpclient.close();
		}
	}
}
