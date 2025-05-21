package com.myproject.virtual_girlfriend_app.controller; // 使用你的包名

import com.myproject.virtual_girlfriend_app.model.ChatMessage;
import com.myproject.virtual_girlfriend_app.model.GirlFriend;
import com.myproject.virtual_girlfriend_app.service.GirlFriendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/girlfriends")
@CrossOrigin(origins = "*") // 开发时允许所有，生产环境应配置具体来源
public class GirlFriendController {

    private final GirlFriendService girlFriendService;

    @Autowired
    public GirlFriendController(GirlFriendService girlFriendService) {
        this.girlFriendService = girlFriendService;
    }

    @PostMapping
    public ResponseEntity<GirlFriend> createGirlFriend(@RequestBody GirlFriend girlFriend) {
        // 校验逻辑
        if (girlFriend.getName() == null || girlFriend.getName().trim().isEmpty()) {
            return ResponseEntity.badRequest().body(null); // 或者返回错误信息体
        }
        // 确保characterAttr和fromWorld被正确设置，如果前端传来的是旧的字段名
        // 例如: girlFriend.setCharacterAttr(girlFriend.getCharacter());
        // 但更好的做法是前端直接发送正确的字段名 characterAttr 和 fromWorld

        GirlFriend createdGf = girlFriendService.createGirlFriend(girlFriend);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdGf);
    }

    @GetMapping
    public ResponseEntity<List<GirlFriend>> getAllGirlFriends() {
        List<GirlFriend> gfs = girlFriendService.getAllGirlFriends();
        return ResponseEntity.ok(gfs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GirlFriend> getGirlFriendById(@PathVariable Long id) {
        Optional<GirlFriend> gfOptional = girlFriendService.getGirlFriendById(id);
        return gfOptional.map(ResponseEntity::ok)
                         .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<GirlFriend> updateGirlFriend(@PathVariable Long id, @RequestBody GirlFriend girlFriendDetails) {
        if (girlFriendDetails.getName() == null || girlFriendDetails.getName().trim().isEmpty()) {
            return ResponseEntity.badRequest().body(null);
        }
        Optional<GirlFriend> updatedGfOptional = girlFriendService.updateGirlFriend(id, girlFriendDetails);
        return updatedGfOptional.map(ResponseEntity::ok)
                                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGirlFriend(@PathVariable Long id) {
        boolean deleted = girlFriendService.deleteGirlFriend(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/{id}/action/{actionName}")
    public ResponseEntity<String> performAction(@PathVariable Long id, @PathVariable String actionName) {
        String result = girlFriendService.performAction(id, actionName);
        if (result.startsWith("未找到该伴侣")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
        }
        return ResponseEntity.ok(result);
    }

    static class ChatRequest {
        private String message;
        public String getMessage() { return message; }
        public void setMessage(String message) { this.message = message; }
    }

    @PostMapping("/{id}/chat")
    public ResponseEntity<String> chatWithGirlFriend(@PathVariable Long id, @RequestBody ChatRequest chatRequest) {
        if (chatRequest == null || chatRequest.getMessage() == null || chatRequest.getMessage().trim().isEmpty()) {
            return ResponseEntity.badRequest().body("消息内容不能为空");
        }
        String response = girlFriendService.chatWithGirlFriend(id, chatRequest.getMessage());
        if (response.startsWith("找不到聊天对象")) {
             return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}/chat-history")
    public ResponseEntity<List<ChatMessage>> getChatHistory(@PathVariable Long id) {
        List<ChatMessage> history = girlFriendService.getChatHistory(id);
        return ResponseEntity.ok(history);
    }
}