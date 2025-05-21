/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */

package com.myproject.virtual_girlfriend_app.repository;

/**
 *
 * @author ASUS
 */

import com.myproject.virtual_girlfriend_app.model.GirlFriend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface GirlFriendRepository extends JpaRepository<GirlFriend, Long> {
    // Spring Data JPA 会自动根据方法名生成查询
    // 例如，如果你需要通过名字查找 (假设名字唯一，实际可能不唯一)
    // Optional<GirlFriend> findByName(String name);
}
