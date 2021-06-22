package com.commerce.backend.model.request.report;

import com.commerce.backend.constants.ReportType;
import lombok.Data;

@Data
public class ReportRequest {

    Long adId;

    Long reason;
    
    Long user_id;
    
    String otherReson;
    
    ReportType type;
}
