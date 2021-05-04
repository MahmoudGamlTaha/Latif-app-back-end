package com.commerce.backend.helper;

import com.commerce.backend.constants.MessageType;
import com.commerce.backend.model.response.BasicResponse;
import org.springframework.data.domain.Pageable;

import java.util.HashMap;

public class resHelper {
    public static BasicResponse res(Object obj, boolean success, String msg, Pageable pageable) {
        BasicResponse res = new BasicResponse();
        HashMap<String, Object> map = new HashMap<>();

        if( obj instanceof Exception) {
            map.put(MessageType.Data.getMessage(), ((Exception) obj).getStackTrace());
        } else {
            map.put(MessageType.Data.getMessage(),  obj);
            if(pageable != null) {
                map.put(MessageType.CurrentPage.getMessage(), pageable.getPageNumber());
            }
        }
        res.setMsg(msg);
        res.setSuccess(success);
        res.setResponse(map);
        return res;
    }
}
