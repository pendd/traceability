package com.hvisions.mes.mapper;

import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.hvisions.mes.domain.OrderResourceFile;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 工序资料上传
 *
 * @author dpeng
 * @create 2019-07-02 13:32
 */
@Mapper
@Repository
public interface OrderResourceFileMapper {

    /**
     *  获取所有的工序上传文件
     * @return
     */
    List<OrderResourceFile> selectAllOrderResourceFile(Pagination page);

    /**
     * 上传文件
     *
     * @param orderId  工序ID
     * @param fileName 文件名
     * @param filePath 文件路径
     */
    void insertOrderResourceFile(@Param("orderId")Integer orderId, @Param("fileName")String fileName, @Param("filePath")String filePath);

    /**
     *  批量删除
     * @param ids
     */
    void deleteOrderResourceFileList(Integer[] ids);
}
