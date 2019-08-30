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
     * 顾客查询所有房间
     * @return 所有房间信息
     */
    @RequestMapping(value = "/tenantQueryAll", method = RequestMethod.GET)
    @ResponseBody
    public List<Room> query1() {
        return roomService.TenantFindAll();
    }

    /**
     * 客服查询所有房间
     * @return 所有房间信息
     */
    @RequestMapping(value = "/operatorQueryAll", method = RequestMethod.GET)
    @ResponseBody
    public List<Room> query2() {
        return roomService.OperatorFindAll();
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

    /**
     * 切换房间禁用状态
     * @return 所有房间信息
     */
    @RequestMapping(value = "/reSetRoomIsAbandoned", method = RequestMethod.POST)
    @ResponseBody
    public void reSetRoomIsAbandoned(@RequestBody Room room) {
        roomService.reSetRoomIsAbandoned(room);
    }

}
