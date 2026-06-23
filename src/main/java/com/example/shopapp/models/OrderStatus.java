package com.example.shopapp.models;

import lombok.Builder;

@Builder

public class OrderStatus {

    public static final String PENDING = "pedding";

    public  static final String PROCESSING = "processing";

    public static final String SHIPPED = "SHIPPED";

    public static final String DELIVERED = "DELIVERED";

    public static final String CANCELLED = "CANCELLED";
}
