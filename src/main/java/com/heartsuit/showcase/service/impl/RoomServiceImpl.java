package com.heartsuit.showcase.service.impl;

import com.heartsuit.showcase.dao.RoomDao;
import com.heartsuit.showcase.domain.Room;
import com.heartsuit.showcase.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RoomServiceImpl implements RoomService {
    private RoomDao roomDao;

    @Autowired
    public RoomServiceImpl(RoomDao roomDao) {
        this.roomDao = roomDao;
    }

    @Override
    public void insert(Room room) {
        roomDao.insert(room);
    }

    @Override
    public List<Room> findAll() {
        return roomDao.findAll();
    }

    @Override
    public List<Room> findRoomByType(Room room) {
        return roomDao.findRoomByType(room);
    }
}