package com.commerce.backend.service;

import com.commerce.backend.constants.MessageType;
import com.commerce.backend.dao.ReportReasonsRepository;
import com.commerce.backend.helper.resHelper;
import com.commerce.backend.model.entity.ReportReasons;
import com.commerce.backend.model.response.BasicResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReportReasonsServiceImpl implements ReportReasonsService{

    private final ReportReasonsRepository reportReasonsRepository;
    private final UserService userService;

    @Autowired
    public ReportReasonsServiceImpl(ReportReasonsRepository reportReasonsRepository, UserService userService) {
        this.reportReasonsRepository = reportReasonsRepository;
        this.userService = userService;
    }

    @Override
    public BasicResponse getReasons() {
        return resHelper.res(reportReasonsRepository.findAll(), true, MessageType.Success.getMessage(), null);
    }

    @Override
    public BasicResponse createReason(String reason, String reasonAr) {
        if(userService.isAdmin()) {
            if (reason != null) {
                ReportReasons reportReasons = new ReportReasons();
                reportReasons.setValue(reason);
                reportReasons.setValueAr(reasonAr);
                return resHelper.res(reportReasonsRepository.save(reportReasons), true, MessageType.Success.getMessage(), null);
            }
        }else{
            return resHelper.res(null, false, MessageType.NotAuthorized.getMessage(), null);
        }
        return resHelper.res(null, false, MessageType.Fail.getMessage(), null);
    }

    @Override
    public BasicResponse updateReason(Long id, String reason, String reasonAr) {
        if(userService.isAdmin()) {
            if (reason != null && id != null) {
                ReportReasons reportReasons = reportReasonsRepository.findById(id).orElse(null);
                if (reportReasons != null) {
                    reportReasons.setValue(reason);
                    reportReasons.setValueAr(reasonAr);
                    return resHelper.res(reportReasonsRepository.save(reportReasons), true, MessageType.Success.getMessage(), null);
                }
            }
        }else{
            return resHelper.res(null, false, MessageType.NotAuthorized.getMessage(), null);
        }
        return resHelper.res(null, false, MessageType.Fail.getMessage(), null);
    }

    @Override
    public BasicResponse removeReason(Long id) {
        try {
            if(userService.isAdmin()) {
                if (id != null) {
                    reportReasonsRepository.deleteById(id);
                    return resHelper.res(MessageType.Deleted.getMessage(), true, MessageType.Success.getMessage(), null);
                } else {
                    return resHelper.res(null, false, MessageType.Missing.getMessage(), null);
                }
            }else{
                return resHelper.res(null, false, MessageType.NotAuthorized.getMessage(), null);
            }
        }catch (Exception ex) {
            return resHelper.res(ex, false, MessageType.Fail.getMessage(), null);
        }
    }
}
