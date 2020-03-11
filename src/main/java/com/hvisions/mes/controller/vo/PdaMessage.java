package com.hvisions.mes.controller.vo;

import lombok.Data;

/**
 * @author dpeng
 * @description websocket通知给PDA的通知类
 * @date 2019-07-20 15:00
 */
@Data
public class PdaMessage {

    private Integer id;

    private String msg;
}
