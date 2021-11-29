package com.example.beeradvisor

object BeerExpert {
    fun getBrands(color:String) : ArrayList<String>{
        val brands:ArrayList<String> = ArrayList()
        when(color){
            "amber"->{
                brands.add("Jack Amber")
                brands.add("Red Moose")
            }
            else ->{
                brands.add("Jail Pale Ale")
                brands.add("Gout Stout")
            }
        }
        return brands

    }
}