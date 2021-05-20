package com.commerce.backend.model.dto;

import com.commerce.backend.constants.ReportType;
import com.commerce.backend.model.entity.UserAds;
import com.commerce.backend.model.response.user.UserResponse;
import lombok.Data;

import java.util.Date;

@Data
public class ReportTypeVo {

    private Long id;

    private UserResponse user;

    private ReportType type;

    private UserAds ad;

    private Date createdAt;

    private Date updatedAt;
}
