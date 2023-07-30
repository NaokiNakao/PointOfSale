package com.nakao.pos.util;

import lombok.Builder;
import lombok.Getter;

/**
 * @author Naoki Nakao on 7/24/2023
 * @project POS
 */

@Builder
@Getter
public class EmailMessage {

    private String to;
    private String subject;
    private String text;

}
