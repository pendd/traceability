package com.hvisions.mes.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.hvisions.mes.domain.OrderResourceFile;
import com.hvisions.mes.mapper.OrderResourceFileMapper;
import com.hvisions.mes.service.IOrderResourceFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author dpeng
 * @create 2019-07-02 13:36
 */
@Service
public class OrderResourceFileServiceImpl implements IOrderResourceFileService {

    @Autowired
    private OrderResourceFileMapper fileMapper;

    /**
     *  获取所有工序资料文件
     * @return
     */
    @Override
    public Page<OrderResourceFile> queryAllOrderResourceFile(Page<OrderResourceFile> page) {
        page.setRecords(fileMapper.selectAllOrderResourceFile(page));
        return page;
    }

    /**
     *  上传文件
     * @param orderId  工序ID
     * @param fileName 文件名
     * @param filePath 文件路径
     */
    @Override
    public void increaseOrderResourceFile(Integer orderId, String fileName, String filePath) {
        fileMapper.insertOrderResourceFile(orderId,fileName,filePath);
    }

    /**
     *  批量删除
     * @param ids
     */
    @Override
    public void removeOrderResourceFileList(Integer[] ids) {
        fileMapper.deleteOrderResourceFileList(ids);
    }
}
