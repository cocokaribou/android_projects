package com.example.openapi_test.Data

import com.google.gson.annotations.SerializedName

data class DataVO(
    @field:SerializedName("LOCALDATA_072218_GS") var voObject: VoObject,
) {

    data class VoObject(
        @field:SerializedName("list_total_count") var listCount: Int,
        @field:SerializedName("row") var bakeries: List<Bakery>,
        ) {
        data class Bakery(
            @field:SerializedName("DTLSTATENM") var storeState: String,
            @field:SerializedName("BPLCNM") var storeNm: String,
            @field:SerializedName("SITETEL") var storeTel: String,
            @field:SerializedName("SITEWHLADDR") var storeAdr: String,
        )
    }
}
