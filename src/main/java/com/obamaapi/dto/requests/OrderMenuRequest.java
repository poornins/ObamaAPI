package com.obamaapi.dto.requests;

import lombok.Getter;
import java.util.Date;

@Getter
public class OrderMenuRequest {
    private Date date;
    private int quantity;
    private long menuId;
    private long orderId;


}
