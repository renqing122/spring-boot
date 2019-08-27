package com.heartsuit.showcase.roa;

import com.heartsuit.showcase.domain.Room;
import com.heartsuit.showcase.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/room")
public class RoomController {
    private RoomService roomService;

    @Autowired
    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }
    /**
     * 新增房间
     * @param room 房间信息
     */
    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    @ResponseBody
    public void insert(@RequestBody Room room) {
        roomService.insert(room);
    }

    /**
     * 查询所有房间
     * @return 所有房间信息
     */
    @RequestMapping(value = "/queryAll", method = RequestMethod.GET)
    @ResponseBody
    public List<Room> query() {
        return roomService.findAll();
    }

    /**
     * 根据type查询房间
     * @return 所有房间信息
     */
    @RequestMapping(value = "/queryRoomByType", method = RequestMethod.POST)
    @ResponseBody
    public List<Room> queryType(@RequestBody Room room) {
        return roomService.findRoomByType(room);
    }
}
