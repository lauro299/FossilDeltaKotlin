package org.fossildelta.lib


import org.junit.Before
import org.junit.Test

import org.junit.Assert.*
import java.nio.charset.Charset

class FossilDeltaTest {

    @Before
    fun setUp() {
    }

    @Test
    fun create() {
        var fossilDelta = FossilDelta(("On the 15th of September, 1840, about six o'clock in the morning, the Ville de Montereau, just on the point of starting, was sending forth great whirlwinds of smoke, in front of the Quai St. Bernard.\n" +
                "\n" +
                "People came rushing on board in breathless haste. The traffic was obstructed by casks, cables, and baskets of linen. The sailors answered nobody. People jostled one another. Between the two paddle-boxes was piled up a heap of parcels; and the uproar was drowned in the loud hissing of the steam, which, making its way through the plates of sheet-iron, enveloped everything in a white cloud, while the bell at the prow kept ringing continuously.\n" +
                "\n" +
                "At last, the vessel set out; and the two banks of the river, stocked with warehouses, timber-yards, and manufactories, opened out like two huge ribbons being unrolled.\n" +
                "\n" +
                "A young man of eighteen, with long hair, holding an album under his arm, remained near the helm without moving. Through the haze he surveyed steeples, buildings of which he did not know the names; then, with a parting glance, he took in the Île St. Louis, the Cité, Nôtre Dame; and presently, as Paris disappeared from his view, he heaved a deep sigh.\n" +
                "\n" +
                "Frederick Moreau, having just taken his Bachelor's degree, was returning home to Nogent-sur-Seine, where he would have to lead a languishing existence for two months, before going back to begin his legal studies. His mother had sent him, with enough to cover his expenses, to Havre to see an uncle, from whom she had expectations of his receiving an inheritance. He had returned from that place only yesterday; and he indemnified himself for not having the opportunity of spending a little time in the capital by taking the longest possible route to reach his own part of the country.")
                .toByteArray(Charset.forName("UTF-8")),
                ("On the 15th of September, 1840, about six o'clock in the morning, the Ville de Montereau, just on the point of starting, was sending forth great whirlwinds of smoke, in front of the Quai St. Bernard.\n" +
                        "\n" +
                        "People came rushing on board in breathless haste. The traffic was obstructed by casks, cables, and baskets of linen. The sailors answered nobody. People jostled one another. Between the two paddle-boxes was piled up a heap of parcels; and the uproar was drowned in the loud hissing of the steam, which, making its way through the plates of sheet-iron, enveloped everything in a white cloud, while the bell at the prow kept ringing continuously.\n" +
                        "\n" +
                        "At last, the vessel set out; and the two banks of the river, stocked with warehouses, timber-yards, and manufactories, opened out like two huge ribbons being unrolled.\n" +
                        "\n" +
                        "A young man of nineteen, with short hair, holding an album under his arm, remained near the helm without moving. Through the haze he surveyed steeples, buildings of which he did not know the names; then, with a parting glance, he took in the Île St. Louis, the Cité, Nôtre Dame; and presently, as Paris disappeared from his view, he heaved a deep sigh.\n" +
                        "\n" +
                        "Frederick Moreau, having just taken his Master's degree, was returning home to Nogent-sur-Seine, where he would have to lead a languishing existence for one month, before going back to begin his legal studies. His mother had sent him, with enough to cover his expenses, to Havre to see an uncle, from whom she had expectations of his receiving an inheritance. He had returned from that place only yesterday; and he indemnified himself for not having the opportunity of spending a little time in the capital by taking the longest possible route to reach his own part of the country.")
                        .toByteArray(Charset.forName("UTF-8")))
        var result = fossilDelta.create().toString(Charset.forName("UTF-8"))
        assertTrue(result.equals("RQ\n" +
                "C~@0,K:nineteen, with short5f@DI,5:Maste1h@J3,9:one month6Z@Ku,gWX7C;"))
    }

    @Test
    fun create2(){
        var fossilDelta = FossilDelta(("\"MY DEAR MR. SHERLOCK HOLMES,--\n" +
                "\n" +
                "\"There has been a bad business during the night at 3, Lauriston Gardens,\n" +
                "off the Brixton Road. Our man on the beat saw a light there about two in\n" +
                "the morning, and as the house was an empty one, suspected that something\n" +
                "was amiss. He found the door open, and in the front room, which is bare\n" +
                "of furniture, discovered the body of a gentleman, well dressed, and\n" +
                "having cards in his pocket bearing the name of 'Enoch J. Drebber,\n" +
                "Cleveland, Ohio, U.S.A.' There had been no robbery, nor is there any\n" +
                "evidence as to how the man met his death. There are marks of blood in\n" +
                "the room, but there is no wound upon his person. We are at a loss as to\n" +
                "how he came into the empty house; indeed, the whole affair is a puzzler.\n" +
                "If you can come round to the house any time before twelve, you will find\n" +
                "me there. I have left everything _in statu quo_ until I hear from you.\n" +
                "If you are unable to come I shall give you fuller details, and would\n" +
                "esteem it a great kindness if you would favour me with your opinion.\n" +
                "Yours faithfully,\n" +
                "\n" +
                "\"TOBIAS GREGSON.\"")
                .toByteArray(Charset.forName("UTF-8")),
                ("\"The man was seen,\" continued Lestrade. \"A milk boy, passing on his way\n" +
                        "to the dairy, happened to walk down the lane which leads from the mews\n" +
                        "at the back of the hotel. He noticed that a ladder, which usually lay\n" +
                        "there, was raised against one of the windows of the second floor, which\n" +
                        "was wide open. After passing, he looked back and saw a man descend the\n" +
                        "ladder. He came down so quietly and openly that the boy imagined him to\n" +
                        "be some carpenter or joiner at work in the hotel. He took no particular\n" +
                        "notice of him, beyond thinking in his own mind that it was early for him\n" +
                        "to be at work. He has an impression that the man was tall, had a reddish\n" +
                        "face, and was dressed in a long, brownish coat. He must have stayed in\n" +
                        "the room some little time after the murder, for we found blood-stained\n" +
                        "water in the basin, where he had washed his hands, and marks on the\n" +
                        "sheets where he had deliberately wiped his knife.\"")
                        .toByteArray(Charset.forName("UTF-8")))
        var result = fossilDelta.create().toString(Charset.forName("UTF-8"))
        assertTrue(result.equals("EA\n" +
                "EA:\"The man was seen,\" continued Lestrade. \"A milk boy, passing on his way\n" +
                "to the dairy, happened to walk down the lane which leads from the mews\n" +
                "at the back of the hotel. He noticed that a ladder, which usually lay\n" +
                "there, was raised against one of the windows of the second floor, which\n" +
                "was wide open. After passing, he looked back and saw a man descend the\n" +
                "ladder. He came down so quietly and openly that the boy imagined him to\n" +
                "be some carpenter or joiner at work in the hotel. He took no particular\n" +
                "notice of him, beyond thinking in his own mind that it was early for him\n" +
                "to be at work. He has an impression that the man was tall, had a reddish\n" +
                "face, and was dressed in a long, brownish coat. He must have stayed in\n" +
                "the room some little time after the murder, for we found blood-stained\n" +
                "water in the basin, where he had washed his hands, and marks on the\n" +
                "sheets where he had deliberately wiped his knife.\"23cPiK;"))
        //print("Delta:\n${fossilDelta.create().toString(Charset.forName("UTF-8"))}")//toString(Charset.forName("UTF-8"))}")
    }

    @Test
    fun apply1(){
        var fossilDelta = FossilDelta("".toByteArray(),
                ("").toByteArray())

        var result = fossilDelta.apply(("On the 15th of September, 1840, about six o'clock in the morning, the Ville de Montereau, just on the point of starting, was sending forth great whirlwinds of smoke, in front of the Quai St. Bernard.\n" +
                "\n" +
                "People came rushing on board in breathless haste. The traffic was obstructed by casks, cables, and baskets of linen. The sailors answered nobody. People jostled one another. Between the two paddle-boxes was piled up a heap of parcels; and the uproar was drowned in the loud hissing of the steam, which, making its way through the plates of sheet-iron, enveloped everything in a white cloud, while the bell at the prow kept ringing continuously.\n" +
                "\n" +
                "At last, the vessel set out; and the two banks of the river, stocked with warehouses, timber-yards, and manufactories, opened out like two huge ribbons being unrolled.\n" +
                "\n" +
                "A young man of eighteen, with long hair, holding an album under his arm, remained near the helm without moving. Through the haze he surveyed steeples, buildings of which he did not know the names; then, with a parting glance, he took in the Île St. Louis, the Cité, Nôtre Dame; and presently, as Paris disappeared from his view, he heaved a deep sigh.\n" +
                "\n" +
                "Frederick Moreau, having just taken his Bachelor's degree, was returning home to Nogent-sur-Seine, where he would have to lead a languishing existence for two months, before going back to begin his legal studies. His mother had sent him, with enough to cover his expenses, to Havre to see an uncle, from whom she had expectations of his receiving an inheritance. He had returned from that place only yesterday; and he indemnified himself for not having the opportunity of spending a little time in the capital by taking the longest possible route to reach his own part of the country.")
                .toByteArray(), ("RQ\n" +
                "C~@0,K:nineteen, with short5f@DI,5:Maste1h@J3,9:one month6Z@Ku,gWX7C;").toByteArray(), null)
        println(result.toString(Charset.forName("UTF-8")))
        assertTrue(result.toString(Charset.forName("UTF-8")).equals("On the 15th of September, 1840, about six o'clock in the morning, the Ville de Montereau, just on the point of starting, was sending forth great whirlwinds of smoke, in front of the Quai St. Bernard.\n" +
                "\n" +
                "People came rushing on board in breathless haste. The traffic was obstructed by casks, cables, and baskets of linen. The sailors answered nobody. People jostled one another. Between the two paddle-boxes was piled up a heap of parcels; and the uproar was drowned in the loud hissing of the steam, which, making its way through the plates of sheet-iron, enveloped everything in a white cloud, while the bell at the prow kept ringing continuously.\n" +
                "\n" +
                "At last, the vessel set out; and the two banks of the river, stocked with warehouses, timber-yards, and manufactories, opened out like two huge ribbons being unrolled.\n" +
                "\n" +
                "A young man of nineteen, with short hair, holding an album under his arm, remained near the helm without moving. Through the haze he surveyed steeples, buildings of which he did not know the names; then, with a parting glance, he took in the Île St. Louis, the Cité, Nôtre Dame; and presently, as Paris disappeared from his view, he heaved a deep sigh.\n" +
                "\n" +
                "Frederick Moreau, having just taken his Master's degree, was returning home to Nogent-sur-Seine, where he would have to lead a languishing existence for one month, before going back to begin his legal studies. His mother had sent him, with enough to cover his expenses, to Havre to see an uncle, from whom she had expectations of his receiving an inheritance. He had returned from that place only yesterday; and he indemnified himself for not having the opportunity of spending a little time in the capital by taking the longest possible route to reach his own part of the country."))
    }

    @Test
    fun apply2(){
        var fossilDelta = FossilDelta(("").toByteArray(),
                ("").toByteArray())

        var result = fossilDelta.apply(("\"MY DEAR MR. SHERLOCK HOLMES,--\n" +
                "\n" +
                "\"There has been a bad business during the night at 3, Lauriston Gardens,\n" +
                "off the Brixton Road. Our man on the beat saw a light there about two in\n" +
                "the morning, and as the house was an empty one, suspected that something\n" +
                "was amiss. He found the door open, and in the front room, which is bare\n" +
                "of furniture, discovered the body of a gentleman, well dressed, and\n" +
                "having cards in his pocket bearing the name of 'Enoch J. Drebber,\n" +
                "Cleveland, Ohio, U.S.A.' There had been no robbery, nor is there any\n" +
                "evidence as to how the man met his death. There are marks of blood in\n" +
                "the room, but there is no wound upon his person. We are at a loss as to\n" +
                "how he came into the empty house; indeed, the whole affair is a puzzler.\n" +
                "If you can come round to the house any time before twelve, you will find\n" +
                "me there. I have left everything _in statu quo_ until I hear from you.\n" +
                "If you are unable to come I shall give you fuller details, and would\n" +
                "esteem it a great kindness if you would favour me with your opinion.\n" +
                "Yours faithfully,\n" +
                "\n" +
                "\"TOBIAS GREGSON.\"")
                .toByteArray(), ("EB\n" +
                "EB:\"The man was seen,\" continued Lestrade. \"A milk boy, passing on his way\n" +
                "to the dairy, happened to walk down the lane which leads from the mews\n" +
                "at the back of the hotel. He noticed that a ladder, which usually lay\n" +
                "there, was raised against one of the windows of the second floor, which\n" +
                "was wide open. After passing, he looked back and saw a man descend the\n" +
                "ladder. He came down so quietly and openly that the boy imagined him to\n" +
                "be some carpenter or joiner at work in the hotel. He took no particular\n" +
                "notice of him, beyond thinking in his own mind that it was early for him\n" +
                "to be at work. He has an impression that the man was tall, had a reddish\n" +
                "face, and was dressed in a long, brownish coat. He must have stayed in\n" +
                "the room some little time after the murder, for we found blood-stained\n" +
                "water in the basin, where he had washed his hands, and marks on the\n" +
                "sheets where he had deliberately wiped his knife.\"\n" +
                "23cQLK;").toByteArray(), null)
        println(result.toString(Charset.forName("UTF-8")))
        assertTrue(result.toString(Charset.forName("UTF-8")).equals("\"The man was seen,\" continued Lestrade. \"A milk boy, passing on his way\n" +
                "to the dairy, happened to walk down the lane which leads from the mews\n" +
                "at the back of the hotel. He noticed that a ladder, which usually lay\n" +
                "there, was raised against one of the windows of the second floor, which\n" +
                "was wide open. After passing, he looked back and saw a man descend the\n" +
                "ladder. He came down so quietly and openly that the boy imagined him to\n" +
                "be some carpenter or joiner at work in the hotel. He took no particular\n" +
                "notice of him, beyond thinking in his own mind that it was early for him\n" +
                "to be at work. He has an impression that the man was tall, had a reddish\n" +
                "face, and was dressed in a long, brownish coat. He must have stayed in\n" +
                "the room some little time after the murder, for we found blood-stained\n" +
                "water in the basin, where he had washed his hands, and marks on the\n" +
                "sheets where he had deliberately wiped his knife.\"\n"))
    }


}
