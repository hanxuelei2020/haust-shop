package com.haust.service.service.third;

import com.haust.service.domain.third.ExpressInfo;

import java.util.List;
import java.util.Map;

public interface ExpressService {
    public List<Map<String, String>> getAllVendor();
    public String getVendorName(String vendorCode);
    public ExpressInfo getExpressInfo(String expCode, String expNo);

}
