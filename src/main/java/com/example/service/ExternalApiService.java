package com.example.service;

import com.example.exception.ApiCallException;
import com.example.exception.EncryptException;
import com.example.util.classes.Result;
import com.example.util.classes.SimpleMessageResponse;
import com.example.util.classes.ResultResponse;
import com.example.util.classes.external.ApiMockResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.crypto.*;
import javax.crypto.spec.DESKeySpec;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;
import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
@Service
public class ExternalApiService {

    private static final String DES_KEY = "ionix123456";
    private static final String API_URL = "https://my.api.mockaroo.com/test-tecnico/search/";
    private static final String API_KEY = "X-API-Key";
    private static final String API_KEY_VALUE = "f2f719e0";
    private static final String DES = "DES";

    /**
     * Function to encrypt a param using Des
     * @param paramToEncryp
     * @return
     * @throws InvalidKeyException
     * @throws NoSuchAlgorithmException
     * @throws NoSuchPaddingException
     * @throws InvalidKeySpecException
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     */
    private String encryptParamDes(String paramToEncryp) {
        try {
            // Cifrar el parámetro utilizando DES
            DESKeySpec keySpec = new DESKeySpec(DES_KEY.getBytes(StandardCharsets.UTF_8));
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
            Cipher cipher = Cipher.getInstance(DES);
            cipher.init(Cipher.ENCRYPT_MODE, keyFactory.generateSecret(keySpec));
            byte[] encryptedParam = cipher.doFinal(paramToEncryp.getBytes(StandardCharsets.UTF_8));

            return Base64.getEncoder().encodeToString(encryptedParam);

        } catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException |
                 InvalidKeySpecException | IllegalBlockSizeException | BadPaddingException  e) {
            throw new EncryptException(e.getMessage());
        }
    }

    /**
     * Function to consume an external API
     * @param param
     * @return
     */
    public ResponseEntity<Object> getExternalService(String param){

        String encryptedDes = encryptParamDes(param);
        String apiUrl = API_URL + encryptedDes;

        // Make the Get call to the external Api
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        HttpGet request = new HttpGet(apiUrl);
        request.addHeader(API_KEY,API_KEY_VALUE);
        try {
            long startTime = System.nanoTime();
            CloseableHttpResponse response = httpClient.execute(request);
            // Convert the response entity to a String
            String responseString = EntityUtils.toString(response.getEntity());
            //Get the status code
            Integer responseCode = response.getStatusLine().getStatusCode();

            //If the response is not success
            if(responseCode != 200){
                SimpleMessageResponse messageResult = new ObjectMapper().readValue(responseString, SimpleMessageResponse.class);
                return new ResponseEntity( messageResult,HttpStatus.valueOf(responseCode));
            }

            //calculate the time duration to set the elapsedTime
            long endTime = System.nanoTime();
            long elapsedTime = TimeUnit.MILLISECONDS.convert(endTime - startTime, TimeUnit.NANOSECONDS);

            //Get the response
            ApiMockResponse apiResponse = new ObjectMapper().readValue(responseString, ApiMockResponse.class);

            //Set the response into the response needed
            ResultResponse resultResponse = new ResultResponse();
            resultResponse.setResponseCode(apiResponse.getResponseCode());
            resultResponse.setDescription(apiResponse.getDescription());
            resultResponse.setElapsedTime(elapsedTime);
            int registerCount = apiResponse.getResult().getItems().size();
            Result results = new Result(registerCount);
            resultResponse.setResult(results);

            return new ResponseEntity(resultResponse, HttpStatus.OK);

        } catch (IOException e) {
            throw new ApiCallException("The api call has failed, reason :"+e.getMessage());
        }

    }
}
