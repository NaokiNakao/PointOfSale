package com.nakao.pointofsale.util;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class EmailMessage {

    private String to;
    private String subject;
    private String text;

}
