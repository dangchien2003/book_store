package org.example.paymentservice.service.impl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.example.paymentservice.configuration.VNPAYConfig;
import org.example.paymentservice.service.VnpayService;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

@Service
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class VnpayServiceImpl implements VnpayService {
    @Override
    public String generateUrl(String orderId, int amount) throws UnsupportedEncodingException {
        String vnpVersion = "2.1.0";
        String vnpCommand = "pay";
        String orderType = "other";

//        String bankCode = "VNBANK ";
//        change
        String vnpIpAddr = "127.0.0.1";

        String vnpTmnCode = VNPAYConfig.vnpTmnCode;

        Map<String, String> vnpParams = new HashMap<>();
        vnpParams.put("vnp_Version", vnpVersion);
        vnpParams.put("vnp_Command", vnpCommand);
        vnpParams.put("vnp_TmnCode", vnpTmnCode);
        vnpParams.put("vnp_Amount", String.valueOf(amount * 100));
        vnpParams.put("vnp_CurrCode", "VND");
//        vnpParams.put("vnp_BankCode", bankCode);
        vnpParams.put("vnp_TxnRef", orderId);
        vnpParams.put("vnp_OrderInfo", "Thanh toan don hang:" + orderId);
        vnpParams.put("vnp_OrderType", orderType);
        vnpParams.put("vnp_Locale", "vn");
        vnpParams.put("vnp_ReturnUrl", VNPAYConfig.vnpReturnUrl);
        vnpParams.put("vnp_IpAddr", vnpIpAddr);

        Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String vnpCreateDate = formatter.format(cld.getTime());
        vnpParams.put("vnp_CreateDate", vnpCreateDate);

//        cld.add(Calendar.MINUTE, 10);
//        String vnpExpireDate = formatter.format(cld.getTime());
//        vnpParams.put("vnpExpireDate", vnpExpireDate);

        String queryUrl = VNPAYConfig.getQueryUrl(vnpParams);
        return VNPAYConfig.vnpPayUrl + "?" + queryUrl;
    }
}
