package com.heartsuit.showcase.roa;

import com.heartsuit.showcase.domain.Picture;
import org.bson.Document;
import org.bson.types.Binary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/picture")
public class PictureController {
    @Autowired
    private MongoTemplate mongoTemplate;
    @PostMapping("/file/uploadImage")
    @ResponseBody
    public String uploadImage(@RequestParam("picture") MultipartFile file){
        if(file.isEmpty())
            return "1";
        String fileName = file.getOriginalFilename();
        try {
            Picture uploadFile = new Picture();
            uploadFile.setName(fileName);
            uploadFile.setCreatedTime(new Date());
            uploadFile.setContent(new Binary(file.getBytes()));
            uploadFile.setContentType(file.getContentType());
            uploadFile.setSize(file.getSize());

            Picture savedFile = mongoTemplate.save(uploadFile);
            String url = "http://114.116.9.214:8000/file/image/"+ savedFile.getId();
            return savedFile.getId();
        } catch (IOException e) {
            e.printStackTrace();
            return "2";
        }
    }

    @GetMapping(value = "/file/image/{id}", produces = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE})
    @ResponseBody
    public byte[] image(@PathVariable String id) {
        byte[] data = null;
        Query query=new Query(Criteria.where("id").is(id));
        Picture file = mongoTemplate.findOne(query, Picture.class);
        if(file != null){
            data = file.getContent().getData();
        }
        return data;
    }

    @GetMapping(value = "/file/queryAll")
    @ResponseBody
    public List<Picture> queryAll() {
        Query query=new Query();
        List<Picture> pictures = mongoTemplate.find(query, Picture.class);
        return pictures;
    }
}
