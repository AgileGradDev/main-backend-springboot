package com.example.demo.api.service;

import com.example.demo.api.advice.exception.CNotOwnerException;
import com.example.demo.api.advice.exception.CResourceNotExistException;
import com.example.demo.api.advice.exception.CUserNotFoundException;
import com.example.demo.api.entity.Good;
import com.example.demo.api.entity.Store;
import com.example.demo.api.entity.User;
import com.example.demo.api.repo.GoodJpaRepo;
import com.example.demo.api.repo.StoreJpaRepo;
import com.example.demo.api.repo.UserJpaRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class GoodService {

    private final GoodJpaRepo goodJpaRepo;
    private final UserJpaRepo userJpaRepo;
    private final StoreJpaRepo storeJpaRepo;

    public Store findStore(String storeName) {
        return Optional.ofNullable(storeJpaRepo.findByName(storeName)).orElseThrow(CResourceNotExistException::new);
    }

    public Optional<User> findUser(Long uid) {
        return Optional.ofNullable(userJpaRepo.findById(uid)).orElseThrow(CUserNotFoundException::new);
    }

    public int countLike(String storeName){
        return goodJpaRepo.countByStore(findStore(storeName));
    }

    public List<Good> userLikes(User user){
        return Optional.ofNullable(goodJpaRepo.findByUser(user)).orElseThrow(CResourceNotExistException::new);
    }

    public Good good(User user, String storeName){
        Store store = findStore(storeName);
        Good like = Good.builder()
                .user(user)
                .store(store)
                .build();
        return goodJpaRepo.save(like);
    }

    public boolean cancelGood(User user, String storeName){
        Good like = goodJpaRepo.findByUserAndStore(user, findStore(storeName)).orElseThrow(CResourceNotExistException::new);
        Long userId = like.getUser().getId();
        if (!user.getId().equals(userId))
            throw new CNotOwnerException();
        goodJpaRepo.delete(like);
        return true;
    }
}
