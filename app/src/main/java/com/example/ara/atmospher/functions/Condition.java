package com.example.ara.atmospher.functions;
public class Condition {
    public String getCondition(int statusId) {

        switch (statusId) {

            case 200:
                return "بارش خفیف همراه با رعد و برق";
            case 201:
                return "بارش  همراه با رعد و برق";
            case 202:
                return "بارش سنکین همراه با رعد و برق";
            case 210:
                return "رعد و برق خفیف";
            case 211:
                return "رعد و برق";
            case 212:
                return "رعد و برق شدید";
            case 221:
                return "رعد و برق پراکنده";
            case 230:
                return "بارش ریز خفیف همراه با رعد و برق";
            case 231:
                return "بارش ریز همراه با رعد و برق";
            case 232:
                return "بارش ریز شدید همراه با رعد و برق";


            case 300:
                return "بارش ریز خفیف";
            case 301:
                return "بارش ریز";
            case 302:
                return "بارش ریز سنگین";
            case 310:
                return "بارش سبک";
            case 311:
                return "بارش";
            case 312:
                return "بارش سنگین";
            case 313:
                return "بارش ریز همراه با رگبار";
            case 314:
                return "بارش ریز همراه با رگبار سنگین";
            case 321:
                return "بارش ریز رگباری";
            case 500:
                return "بارش سبک";
            case 501:
                return "بارش معتدل";
            case 502:
                return "بارش شدید";
            case 503:
                return "بارش بسیار شدید";
            case 504:
                return "بارش مفرط";


            case 511:
                return "بارش منجمد";
            case 520:
                return "بارش رگباری سبک";
            case 521:
                return "بارش رگباری";
            case 522:
                return "بارش رگباری شدید";
            case 531:
                return "بارش  رگباری پراکنده";


            case 600:
                return "بارش برف سبک";
            case 601:
                return "بارش برف";
            case 602:
                return "بارش برف سنگین";
            case 611:
                return "بارش برف و باران";
            case 612:
                return "بارش برف و باران رگباری";
            case 615:
                return "بارش برف و باران سبک";
            case 616:
                return "بارش برف و باران";
            case 620:
                return "بارش برف رگباری سبک";
            case 621:
                return "بارش برف رگباری";
            case 622:
                return "بارش برف رگباری سنگین";
            case 701:
                return "غبارآلود";
            case 711:
                return "دود";
            case 721:
                return "مه رقیق";
            case 731:
                return "گرد باد شن";
            case 741:
                return "مه غلیظ";
            case 751:
                return "شن";
            case 761:
                return "گرد و غبار";
            case 762:
                return "خاکستر آتشفشانی";
            case 771:
                return "توفان";
            case 781:
                return "گردباد";

            case 800:
                return "صاف";
            case 801:
                return "اندکی ابری";
            case 802:
                return "ابرهای پراکنده";
            case 803:
                return "ابرهای شکسته";
            case 804:
                return "پوشیده از ابر";
            default:
                return "وضعیت تعریف نشده";
        }
    }

}
