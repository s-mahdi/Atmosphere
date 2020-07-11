package com.example.ara.atmospher.functions;

import com.example.ara.atmospher.R;

public class Icon {
    public int getIcon(int statusId) {

        switch (statusId) {
            case 200:
            case 201:
            case 202:
            case 210:
            case 211:
            case 212:
            case 221:
            case 230:
            case 231:
            case 232:
                return R.drawable.ic_thunderstorm;

            case 300:
            case 301:
            case 302:
            case 310:
            case 311:
            case 312:
            case 313:
            case 314:
            case 321:
                return R.drawable.ic_showr_rain;

            case 522:
            case 500:
            case 501:
            case 502:
            case 503:
            case 504:
            case 520:
            case 521:
            case 531:
                return R.drawable.ic_rain;

            case 600:
            case 601:
            case 602:
            case 611:
            case 612:
            case 615:
            case 616:
            case 620:
            case 621:
            case 622:
                return R.drawable.ic_snow;

            case 781:
            case 771:
            case 762:
            case 761:
            case 751:
            case 741:
            case 731:
            case 721:
            case 711:
            case 701:
                return R.drawable.ic_mist;

            case 800:
                return R.drawable.ic_clear_sky;

            case 801:
                return R.drawable.ic_few_clouds;

            case 802:
                return R.drawable.ic_scattered_clouds;

            case 803:
            case 804:
                return R.drawable.ic_broken_clouds;

            default:
                return 0;
        }

    }
}
