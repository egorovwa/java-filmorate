package ru.yandex.practicum.filmorate.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FriendShip {
    private final Integer id;
    private final Integer userId;
    private final Integer friendId;
    private final Boolean status;

    public FriendShip(Integer id, Integer userId, Integer friendId, Boolean status) {
        this.id = id;
        this.userId = userId;
        this.friendId = friendId;
        this.status = status;
    }
}
