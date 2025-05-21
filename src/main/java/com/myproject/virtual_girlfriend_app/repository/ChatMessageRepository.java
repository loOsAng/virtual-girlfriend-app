package com.myproject.virtual_girlfriend_app.repository;

import com.myproject.virtual_girlfriend_app.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {
    // 根据女友ID查找所有聊天记录，并按时间戳排序
    List<ChatMessage> findByGirlfriendIdOrderByTimestampAsc(Long girlfriendId);
}