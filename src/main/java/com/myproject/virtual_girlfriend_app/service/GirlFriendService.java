package com.myproject.virtual_girlfriend_app.service; // 使用你的包名

import com.myproject.virtual_girlfriend_app.model.ChatMessage;
import com.myproject.virtual_girlfriend_app.model.GirlFriend;
import com.myproject.virtual_girlfriend_app.repository.ChatMessageRepository;
import com.myproject.virtual_girlfriend_app.repository.GirlFriendRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class GirlFriendService {

    private final GirlFriendRepository girlFriendRepository;
    private final ChatMessageRepository chatMessageRepository;
    private final HttpClient httpClient;

    @Value("${deepseek.api.key}")
    private String deepSeekApiKey;

    @Value("${deepseek.api.endpoint}")
    private String deepSeekApiEndpoint;

    @Autowired
    public GirlFriendService(GirlFriendRepository girlFriendRepository,
                             ChatMessageRepository chatMessageRepository) {
        this.girlFriendRepository = girlFriendRepository;
        this.chatMessageRepository = chatMessageRepository;
        this.httpClient = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(10))
                .build();
    }

    @Transactional
    public GirlFriend createGirlFriend(GirlFriend girlFriend) {
        // 确保 characterAttr 和 fromWorld 在保存前已设置 (如果前端传来的是 character 和 from)
        // 如果GirlFriend的setter已经处理了，这里就不需要额外逻辑
        return girlFriendRepository.save(girlFriend);
    }

    public List<GirlFriend> getAllGirlFriends() {
        return girlFriendRepository.findAll();
    }

    public Optional<GirlFriend> getGirlFriendById(Long id) {
        return girlFriendRepository.findById(id);
    }

    @Transactional
    public Optional<GirlFriend> updateGirlFriend(Long id, GirlFriend updatedGfDetails) {
        return girlFriendRepository.findById(id)
            .map(existingGf -> {
                existingGf.setName(updatedGfDetails.getName());
                existingGf.setAge(updatedGfDetails.getAge());
                existingGf.setHome(updatedGfDetails.getHome());
                existingGf.setWork(updatedGfDetails.getWork());
                existingGf.setHobby(updatedGfDetails.getHobby());
                existingGf.setCharacterAttr(updatedGfDetails.getCharacterAttr());
                existingGf.setBirthday(updatedGfDetails.getBirthday());
                existingGf.setFromWorld(updatedGfDetails.getFromWorld());
                return girlFriendRepository.save(existingGf);
            });
    }

    @Transactional
    public boolean deleteGirlFriend(Long id) {
        if (girlFriendRepository.existsById(id)) {
            // 先删除关联的聊天记录
            List<ChatMessage> relatedChats = chatMessageRepository.findByGirlfriendIdOrderByTimestampAsc(id);
            chatMessageRepository.deleteAll(relatedChats);
            // 再删除女友
            girlFriendRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // 这个方法现在应该可以正常工作了
    public String performAction(Long gfId, String actionName) {
        Optional<GirlFriend> gfOptional = girlFriendRepository.findById(gfId);
        if (gfOptional.isEmpty()) {
            return "未找到该伴侣!";
        }
        GirlFriend gf = gfOptional.get();
        switch (actionName.toLowerCase()) {
            case "holdhand": return gf.holdHand();
            case "hug": return gf.Hug();
            case "kiss": return gf.Kiss();
            case "shopping": return gf.Shopping();
            case "watchmovie": return gf.WatchMovie();
            case "havefood": return gf.HaveFood();
            case "travel": return gf.Travel();
            case "celebrate520": return gf.Celebrate520();
            case "takephoto": return gf.TakePhoto();
            case "cookfood": return gf.CookFood();
            case "fit": return gf.Fit();
            case "walk": return gf.Walk();
            default: return "未知的互动";
        }
    }

    private String generateCharacterPrompt(GirlFriend gf) {
         return String.format(
            "%s是一个%d岁的女孩。她的家乡是%s，职业是%s，喜欢%s。性格特点是%s，生日是%s,来自于%s作品，请用符合这些特征的方式对话。",
            gf.getName(), gf.getAge(), gf.getHome(), gf.getWork(), gf.getHobby(), gf.getCharacterAttr(), gf.getBirthday(), gf.getFromWorld()
        );
    }

    @Transactional
    public String chatWithGirlFriend(Long gfId, String userInput) {
        Optional<GirlFriend> gfOptional = girlFriendRepository.findById(gfId);
        if (gfOptional.isEmpty()) {
            return "找不到聊天对象...";
        }
        GirlFriend gf = gfOptional.get();

        saveChatMessage(gfId, "user", userInput, LocalDateTime.now());

        try {
            String systemPromptContent = generateCharacterPrompt(gf);
            String systemPromptJson = String.format("{\"role\":\"system\",\"content\":\"%s\"}", systemPromptContent.replace("\"", "\\\""));
            String userMessageJson = String.format("{\"role\":\"user\",\"content\":\"%s\"}", userInput.replace("\"", "\\\""));
            String requestBody = String.format("{\"model\":\"deepseek-chat\",\"messages\":[%s,%s],\"temperature\":0.7}", systemPromptJson, userMessageJson);

            HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(deepSeekApiEndpoint))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + deepSeekApiKey)
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .timeout(Duration.ofSeconds(30))
                .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            String responseBody = response.body();

            int start = responseBody.indexOf("\"content\":\"") + 11;
            int end = responseBody.indexOf("\"", start);

            if (start > 10 && end > start && end <= responseBody.length()) {
                String aiResponse = responseBody.substring(start, end).replace("\\n", "\n").replace("\\\"", "\"");
                saveChatMessage(gfId, "girlfriend", aiResponse, LocalDateTime.now());
                return aiResponse;
            } else {
                System.err.println("Error parsing DeepSeek API response: " + responseBody);
                saveChatMessage(gfId, "system", "AI响应解析错误: " + responseBody.substring(0, Math.min(responseBody.length(), 200)), LocalDateTime.now());
                return "AI好像有点小情绪，没能理解我的回复格式...";
            }
        } catch (Exception e) {
            e.printStackTrace();
            saveChatMessage(gfId, "system", "网络连接不太稳定呢... " + e.getMessage(), LocalDateTime.now());
            return "网络连接不太稳定呢...";
        }
    }

    private void saveChatMessage(Long girlfriendId, String sender, String message, LocalDateTime timestamp) {
        System.out.println("保存了");
        ChatMessage chatMessage = new ChatMessage(girlfriendId, sender, message, timestamp);
        chatMessageRepository.save(chatMessage);
    }

    public List<ChatMessage> getChatHistory(Long girlfriendId) {
        if (!girlFriendRepository.existsById(girlfriendId)) {
            return Collections.emptyList();
        }
        return chatMessageRepository.findByGirlfriendIdOrderByTimestampAsc(girlfriendId);
    }
}