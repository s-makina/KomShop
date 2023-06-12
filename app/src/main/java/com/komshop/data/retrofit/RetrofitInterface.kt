package com.komshop.data.retrofit

import com.komshop.data.retrofit.dto.NetworkResponse
import com.komshop.data.retrofit.dto.ProductCategoryDto
import com.komshop.data.retrofit.dto.ProductDto
import retrofit2.http.*

interface RetrofitInterface {

    @FormUrlEncoded
    @POST("login")
    suspend fun authenticate(
        @Field("username") phone: String,
        @Field("password") password: String,
    ): NetworkResponse

    @FormUrlEncoded
    @POST("register")
    suspend fun signup(
        @Field("name") name: String,
        @Field("phone") phone: String,
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("password_confirmation") passwordConf: String,
    ): NetworkResponse

    @GET("auction-types")
    suspend fun getAuctionTypes(): NetworkResponse

    @GET("check-deposit/{reference_number}")
    suspend fun submitDepositRef(
        @Path("reference_number") referenceNumber: String,
        @Query("auction_type_id") auctionTypeId: String,
    ): NetworkResponse

    @GET("auction-types") //auction-types
    suspend fun getAuctions(@Query("type") auctionType: String): NetworkResponse

    @GET("wp-json/wc/v3/products")
    suspend fun getProducts(): List<ProductDto>

    @GET("/wp-json/wc/v3/products/categories")
    suspend fun getProductCategory(@Query("hide_empty") hideEmpty: Boolean = true): List<ProductCategoryDto>

    @GET("wp-json/wc/v3/products")
    suspend fun getPaginatedProductList(
        @Query("page") page: Int,
        @Query("per_page") pageSize: Int,
        @Query("search") searchTerm: String,
        @Query("category") category: String
    ): List<ProductDto>//NetworkResponse

    @POST("place-a-bid/{item}/{amount}")
    suspend fun placeAbid(
        @Path("item") itemId: String,
        @Path("amount") bidAmount: String,
    ): NetworkResponse

    @GET("auctions")
    suspend fun getMyBids(): NetworkResponse

    @GET("user/{code}")
    suspend fun getUser(@Path("code") code: String): NetworkResponse

}


