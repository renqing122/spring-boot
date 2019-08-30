package com.heartsuit.showcase.service;

import com.heartsuit.showcase.domain.Room;

import java.util.List;

public interface RoomService {
    void insert(Room room);

    List<Room> TenantFindAll();
    List<Room> OperatorFindAll();
    void reSetRoomIsAbandoned(Room room);
    List<Room> findRoomByType(Room room);
}
