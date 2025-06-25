package ro.mpp2025.javaprojectui.client;

import com.google.protobuf.ServiceException;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpRequest;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClient;
import ro.mpp2025.javaprojectui.Proba;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.Callable;

public class ProjectRestClient {
    RestClient restClient = RestClient.builder().
            requestInterceptor(new CustomRestClientInterceptor()).
            build();

    public static final String URL = "http://localhost:8080/concurs/probe";

    private <T> T execute(Callable<T> callable) throws ServiceException {
        try {
            return callable.call();
        } catch (ResourceAccessException | HttpClientErrorException e) {
            throw new ServiceException(e);
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }


    public List<Proba> getAll() throws ServiceException {
        return execute(() -> restClient.get()
                .uri(URL)
                .retrieve()
                .body(new ParameterizedTypeReference<List<Proba>>() {}));
    }

    public Proba getById(Integer id) throws ServiceException {
        return execute(() -> restClient.get()
                .uri(URL + "/" + id)
                .retrieve()
                .body(Proba.class));
    }

    public Proba add(Proba proba) throws ServiceException {
        return execute(() -> restClient.post()
                .uri(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .body(proba)
                .retrieve()
                .body(Proba.class));
    }

    public Proba update(Proba proba, Integer id) throws ServiceException {
        return execute(() -> restClient.put()
                .uri(URL + "/" + id)
                .contentType(MediaType.APPLICATION_JSON)
                .body(proba)
                .retrieve()
                .body(Proba.class));
    }

    public void delete(Integer id) throws ServiceException {
        execute(() -> {
            restClient.delete()
                    .uri(URL + "/" + id)
                    .retrieve()
                    .toBodilessEntity();
            return null;
        });
    }


    public class CustomRestClientInterceptor implements ClientHttpRequestInterceptor {

        @Override
        public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
            System.out.println("Sending a "+request.getMethod()+ " request to "+request.getURI()+ " and body ["+new String(body)+"]");
            ClientHttpResponse response=null;
            try {
                response = execution.execute(request, body);
                System.out.println("Got response code " + response.getStatusCode());
            }catch(IOException ex){
                System.err.println("Eroare executie "+ex);
            }
            return response;
        }
    }
}
