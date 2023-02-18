package com.chinhnd.recruit.dto;

import lombok.Data;

@Data
public class StatusJobRegisterDTO {
    private  Long jobRegisterId;
    private Long statusJobRegisterId;
    private String reason;
}
