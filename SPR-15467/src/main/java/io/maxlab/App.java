package io.maxlab;

import org.springframework.http.HttpMethod;
import org.springframework.http.client.*;

import java.net.URI;
import java.util.Collections;

/**
 * Hello world!
 *
 */
public class App 
{

        public static void main(String[] args) throws Exception {

            // request header using `sudo tcpdump -s 0 -A port 8080 -i lo` contains twice Content-Length: 4 which is forbidden by http rfc
            executeAPut(new Netty4ClientHttpRequestFactory());
            // request header using `sudo tcpdump -s 0 -A port 8080 -i lo` contains only one header `Content-Length: 4` which is expected
            executeAPut(new SimpleClientHttpRequestFactory());
        }

        private static void executeAPut(ClientHttpRequestFactory clientHttpRequestFactory)  throws Exception {
            final ClientHttpRequest request = clientHttpRequestFactory.createRequest(new URI("http://127.0.0.1:8080"), HttpMethod.PUT);
            request.getBody().write("toto".getBytes());
            request.getHeaders().put("Content-Length", Collections.singletonList("2"));
            final ClientHttpResponse response = request.execute();
            System.out.println(response.getStatusText());
        }
}
