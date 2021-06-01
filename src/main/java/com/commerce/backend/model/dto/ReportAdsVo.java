package com.commerce.backend.model.dto;

import com.commerce.backend.model.entity.ReportReasons;
import lombok.Data;

@Data
public class ReportAdsVo extends ReportTypeVo {
    private ReportReasons reason;
}
