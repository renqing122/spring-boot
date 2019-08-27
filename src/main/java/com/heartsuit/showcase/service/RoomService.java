package com.heartsuit.showcase.service;

import com.heartsuit.showcase.domain.Room;

import java.util.List;

public interface RoomService {
    void insert(Room room);

    List<Room> findAll();


    List<Room> findRoomByType(Room room);
}
