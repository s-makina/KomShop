package com.komshop.data.retrofit.dto

import androidx.annotation.Keep

@Keep
data class OrderDto(
    val payment_method: String = "bacs",
    val payment_method_title: String = "Payment upon delivery",
    val set_paid: Boolean = true,
    val billing: BillingDto,
    val line_items: List<OrderItem>
)

@Keep
data class BillingDto(
    val first_name: String,
    val last_name: String,
    val country: String = "MW"
)

@Keep
data class OrderItem(
    val product_id: String,
    val quantity: Int
)

//{
//    "payment_method": "bacs",
//    "payment_method_title": "Direct Bank Transfer",
//    "set_paid": true,
//    "billing": {
//    "first_name": "John",
//    "last_name": "Doe",
//    "address_1": "969 Market",
//    "address_2": "",
//    "city": "San Francisco",
//    "state": "CA",
//    "postcode": "94103",
//    "country": "US",
//    "email": "john.doe@example.com",
//    "phone": "(555) 555-5555"
//},
//    "shipping": {
//    "first_name": "John",
//    "last_name": "Doe",
//    "address_1": "969 Market",
//    "address_2": "",
//    "city": "San Francisco",
//    "state": "CA",
//    "postcode": "94103",
//    "country": "US"
//},
//    "line_items": [
//    {
//        "product_id": 93,
//        "quantity": 2
//    },
//    {
//        "product_id": 22,
//        "variation_id": 23,
//        "quantity": 1
//    }
//    ],
//    "shipping_lines": [
//    {
//        "method_id": "flat_rate",
//        "method_title": "Flat Rate",
//        "total": "10.00"
//    }
//    ]
//}