package com.hvisions.mes.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.hvisions.mes.domain.OrderResourceFile;

import java.util.List;

/**
 * 工序资料上传接口
 *
 * @author dpeng
 * @create 2019-07-02 13:35
 */
public interface IOrderResourceFileService {

    /**
     *  获取所有工序资料文件
     * @return
     */
    Page<OrderResourceFile> queryAllOrderResourceFile(Page<OrderResourceFile> page);

    /**
     * 上传文件
     *
     * @param orderId  工序ID
     * @param fileName 文件名
     * @param filePath 文件路径
     */
    void increaseOrderResourceFile(Integer orderId, String fileName, String filePath);

    /**
     *  批量删除
     * @param ids
     */
    void removeOrderResourceFileList(Integer[] ids);
}
