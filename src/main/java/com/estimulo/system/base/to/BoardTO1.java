package com.estimulo.system.base.to;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class BoardTO1 extends BaseTO{

    private int num;
    private String subject;
    private String passwardBoard;
    private String reg_date;
    private String writer;
    private int ref;
    private int re_step;
    private int re_lever;
    private String content;
    private String filedBoard;
    
}
