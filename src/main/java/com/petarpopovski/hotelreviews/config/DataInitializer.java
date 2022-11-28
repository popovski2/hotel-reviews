package com.petarpopovski.hotelreviews.config;

import com.petarpopovski.hotelreviews.model.Geolocation;
import com.petarpopovski.hotelreviews.service.interfaces.HotelService;
import com.petarpopovski.hotelreviews.service.interfaces.ReviewService;
import com.petarpopovski.hotelreviews.service.interfaces.UserService;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class DataInitializer {

    private final HotelService hotelService;
    private final ReviewService reviewService;
    private final UserService userService;

    public DataInitializer(HotelService hotelService, ReviewService reviewService, UserService userService) {
        this.hotelService = hotelService;
        this.reviewService = reviewService;
        this.userService = userService;
    }

    /**
     *
     * @desc Initializes data for seeding the database (users, hotels and reviewes)
     *
     */
    //@PostConstruct
    public void initData(){

        this.userService.registerAsAdministrator("ppopovski53@gmail.com","petarpopovski","1234");
        this.userService.registerAsRegular("loofland@gmail.com","loof_land","1234");

        this.hotelService.create("Hotel Indigo Rome","Rome, Italy","https://imgcy.trivago.com/c_lfill,d_dummy.jpeg,e_sharpen:60,f_auto,h_225,q_auto,w_225/itemimages/15/35/153597_v22.jpeg"
                ,"Rooms have elegant furnishings and parquet floors. Each includes air conditioning, a mini-bar, international channels and a private bathroom with a hairdryer and toiletries.", new Geolocation(38.1838139,54.1868239));
        this.hotelService.create("Artemide Hotel","Lazio, Italy","https://imgcy.trivago.com/c_lfill,d_dummy.jpeg,e_sharpen:60,f_auto,h_225,q_auto,w_225/itemimages/16/87/16878_v4.jpeg",
                "Rooms have elegant furnishings and parquet floors. Each includes air conditioning, a mini-bar, international channels and a private bathroom with a hairdryer and toiletries.",new Geolocation(38.1838139,54.1868239));
        this.hotelService.create("Mariott Hotel","Siena, Italy","https://imgcy.trivago.com/c_lfill,d_dummy.jpeg,e_sharpen:60,f_auto,h_225,q_auto,w_225/itemimages/17/28/17286_v6.jpeg",
                "Rooms have elegant furnishings and parquet floors. Each includes air conditioning, a mini-bar, international channels and a private bathroom with a hairdryer and toiletries.",new Geolocation(38.1838139,54.1868239));
        this.hotelService.create("Floris Hotel","Catania, Italy","https://imgcy.trivago.com/c_lfill,d_dummy.jpeg,e_sharpen:60,f_auto,h_225,q_auto,w_225/itemimages/27/34/2734513_v1.jpeg",
                "Rooms have elegant furnishings and parquet floors. Each includes air conditioning, a mini-bar, international channels and a private bathroom with a hairdryer and toiletries.",new Geolocation(38.1838139,54.1868239));
        this.hotelService.create("Hotel Quirinale","Milano, Italy","https://imgcy.trivago.com/c_lfill,d_dummy.jpeg,e_sharpen:60,f_auto,h_225,q_auto,w_225/itemimages/17/43/17430_v6.jpeg",
                "Rooms have elegant furnishings and parquet floors. Each includes air conditioning, a mini-bar, international channels and a private bathroom with a hairdryer and toiletries.",new Geolocation(38.1838139,54.1868239));
        this.hotelService.create("Una Hotels","Naples, Italy","https://imgcy.trivago.com/c_lfill,d_dummy.jpeg,e_sharpen:60,f_auto,h_225,q_auto,w_225/itemimages/95/06/950631_v4.jpeg",
                "Rooms have elegant furnishings and parquet floors. Each includes air conditioning, a mini-bar, international channels and a private bathroom with a hairdryer and toiletries.",new Geolocation(38.1838139,54.1868239));
        this.hotelService.create("Hotel Tree Charme","Paris, France","https://imgcy.trivago.com/c_lfill,d_dummy.jpeg,e_sharpen:60,f_auto,h_225,q_auto,w_225/itemimages/16/88/16883006.jpeg",
                "Rooms have elegant furnishings and parquet floors. Each includes air conditioning, a mini-bar, international channels and a private bathroom with a hairdryer and toiletries.",new Geolocation(38.1838139,54.1868239));
        this.hotelService.create("Hotel Giolli","Bologna, Italy","https://imgcy.trivago.com/c_lfill,d_dummy.jpeg,e_sharpen:60,f_auto,h_225,q_auto,w_225/itemimages/17/14/17146_v13.jpeg",
                "Rooms have elegant furnishings and parquet floors. Each includes air conditioning, a mini-bar, international channels and a private bathroom with a hairdryer and toiletries.",new Geolocation(38.1838139,54.1868239));
        this.hotelService.create("Hotel Raphael","Paris, France","https://imgcy.trivago.com/c_lfill,d_dummy.jpeg,e_sharpen:60,f_auto,h_225,q_auto,w_225/itemimages/17/43/17436_v12.jpeg",
                "Rooms have elegant furnishings and parquet floors. Each includes air conditioning, a mini-bar, international channels and a private bathroom with a hairdryer and toiletries.",new Geolocation(38.1838139,54.1868239));
        this.hotelService.create("Trevi 95 Hotel","Rome, Italy","https://imgcy.trivago.com/c_lfill,d_dummy.jpeg,e_sharpen:60,f_auto,h_225,q_auto,w_225/itemimages/22/00/2200778_v21.jpeg",
                "Rooms have elegant furnishings and parquet floors. Each includes air conditioning, a mini-bar, international channels and a private bathroom with a hairdryer and toiletries.",new Geolocation(38.1838139,54.1868239));

        for (int i = 1; i <this.hotelService.listAllHotels().size()+1 ; i++) {
            this.reviewService.create(1L,Long.parseLong(String.valueOf(i)),5,"Amazing stay here! I gladly reccommend this hotel to everyone who loves comfort. Straight 5!!");
        }

    }
}
