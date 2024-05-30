package com.onlineshop.onlineshop;
import com.onlineshop.onlineshop.Models.vk.VkApiResponse;
import com.onlineshop.onlineshop.Models.vk.vkProfileInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class ApiService {
    @Autowired
    private WebClient webClient;
    private String serviceKey = "292a6012292a6012292a6012d92a3213ae2292a292a60124f7def0bc83a1353c93ac08f";

    public Mono<VkApiResponse> exchangeSilentAuthToken(String silentToken, int uuid) {
        String urlTemplate = "https://api.vk.com/method/auth.exchangeSilentAuthToken?v=5.131&token=%s&access_token=%s&uuid=%d";
        String url = String.format(urlTemplate, silentToken, serviceKey, uuid);
        return webClient.get()
                .uri(url)
                .retrieve()
                .bodyToMono(VkApiResponse.class);
    }

    public Mono<vkProfileInfo> getProfileInfo(String accessToken) {
        String urlTemplate = "https://api.vk.com/method/account.getProfileInfo?access_token=%s";
        String url = String.format(urlTemplate, accessToken);
        return webClient.get()
                .uri(url)
                .retrieve()
                .bodyToMono(vkProfileInfo.class);
    }

}