package com.hvisions.mes.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.hvisions.mes.domain.OrderResourceFile;
import com.hvisions.mes.service.IOrderResourceFileService;
import com.hvisions.mes.util.UUIDGenerator;
import org.springframework.web.bind.annotation.RequestParam;
import springfox.documentation.annotations.ApiIgnore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 工序资料上传
 *
 * @author dpeng
 * @create 2019-07-02 13:37
 */
@RestController
@RequestMapping("/json/orderResourceFile")
@ApiIgnore
public class OrderResourceFileController {

    @Value("${web.uploadPath}")
    private String uploadPath;

    @Autowired
    private IOrderResourceFileService fileService;

    /**
     *  获取所有上传的文件
     * @return
     */
    @RequestMapping("/getAllOrderResourceFile")
    public Map<String, Object> getAllOrderResourceFile(@RequestParam(defaultValue = "1")Integer page,
                                                       @RequestParam(defaultValue = "15")Integer rows) {
        Page<OrderResourceFile> data = fileService.queryAllOrderResourceFile(new Page<>(page, rows));
        Map<String,Object> result = new HashMap<>(16);
        result.put("total",data.getTotal());
        result.put("rows",data.getRecords());
        return result;
    }

    /**
     *  上传文件
     * @param file      文件对象
     * @param orderId   工序ID
     * @return
     */
    @RequestMapping("/uploadOrderFile")
    public String uploadOrderFile(MultipartFile file,Integer orderId) {
        String flag;
        // 文件名称
        String filename = file.getOriginalFilename();
        // 获取文件后缀
        String prefix=filename.substring(filename.lastIndexOf("."));
        // 文件路径
        String path = uploadPath + UUIDGenerator.getUUID() + prefix;

        File uploadFile = new File(uploadPath);
        if (!uploadFile.exists()) {
            uploadFile.mkdirs();
        }

        try {
            file.transferTo(new File(path));
            fileService.increaseOrderResourceFile(orderId,filename,path);
            flag = "true";
        } catch (IOException e) {
            e.printStackTrace();
            flag = "false";
        }
        return flag;
    }

    /**
     *  批量删除
     * @param ids  主键数组
     * @return     状态值  成功  失败
     */
    @RequestMapping("/removeOrderResourceFileList")
    public String removeOrderResourceFileList(Integer[] ids) {
        String flag;
        try {
            fileService.removeOrderResourceFileList(ids);
            flag = "true";
        } catch (Exception e) {
            e.printStackTrace();
            flag = "false";
        }
        return flag;
    }
}
