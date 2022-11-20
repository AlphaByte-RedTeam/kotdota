package com.kelompoktiga.kotdota

object SavedData {
    private val itemNames = arrayOf<String>(
        "ini name",
        "ini name",
        "ini name",
    )

    private val itemId = arrayOf<Int>(
        1, 2, 3,
    )

    private val itemImages = arrayOf<Int>(
        R.drawable.hero,
        R.drawable.hero,
        R.drawable.hero,
    )

    private val itemPrices = arrayOf<Int>(
        100, 200, 300,
    )

    private val itemLocations = arrayOf<String>(
        "spawn",
        "spawn",
        "spawn",
    )

    val savedList: ArrayList<Saved>
        get() {
            val list = arrayListOf<Saved>()
            for (position in savedList.indices) {
                val item = Saved()
                item.id = itemId[position]
                item.name = itemNames[position]
                item.img = itemImages[position]
                item.price = itemPrices[position]
                item.location = itemLocations[position]
                list.add(item)
            }
            return list
        }
}

