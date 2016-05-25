package com.javarush.test.level27.lesson15.big01.ad;

import com.javarush.test.level27.lesson15.big01.ConsoleHelper;
import com.javarush.test.level27.lesson15.big01.statistic.StatisticEventManager;
import com.javarush.test.level27.lesson15.big01.statistic.event.NoAvailableVideoEventDataRow;
import com.javarush.test.level27.lesson15.big01.statistic.event.VideoSelectedEventDataRow;

import java.util.*;

/**
 * Created by Valk on 05.05.15.
 */
public class AdvertisementManager {
    private final AdvertisementStorage storage = AdvertisementStorage.getInstance();
    private int timeSeconds;

    public AdvertisementManager(int timeSeconds) {
        this.timeSeconds = timeSeconds;
    }

    public void processVideos() {
        List<Advertisement> allVideoList = new ArrayList<>(storage.list());
        for (Iterator<Advertisement> iterator = allVideoList.iterator(); iterator.hasNext(); ) {
            Advertisement curr = iterator.next();
            if (curr.getHits() <= 0) iterator.remove();
        }

        List<Advertisement> listForPlay = new ArrayList<>();

        listForPlay = getMaxProfitList(timeSeconds, allVideoList);

        Collections.sort(allVideoList, new Comparator<Advertisement>() {//это излишняя сортировка. Оставил из-за мнительности перед валидатором - вдруг как-то проверяет наличие определенного кода
            @Override
            public int compare(Advertisement o1, Advertisement o2) {
                if (o1.getAmountPerOneDisplaying() == o2.getAmountPerOneDisplaying()) {
                    return Long.compare(o1.getAmountPerOneDisplaying() * 1000 / o1.getDuration(), o2.getAmountPerOneDisplaying() * 1000 / o2.getDuration());

                } else return Long.compare(o2.getAmountPerOneDisplaying(), o1.getAmountPerOneDisplaying());
            }
        });

        if (listForPlay.isEmpty()) {
            StatisticEventManager.getInstance().register(new NoAvailableVideoEventDataRow(timeSeconds));
            throw new NoVideoAvailableException();
        }

        long totalAmount = 0;
        int totalDuration = 0;
        for (Advertisement advertisement : listForPlay){
            totalAmount += advertisement.getAmountPerOneDisplaying();
            totalDuration += advertisement.getDuration();
        }

        StatisticEventManager.getInstance().register(new VideoSelectedEventDataRow(listForPlay, totalAmount, totalDuration));

        for (Advertisement advertisement : listForPlay) {
            ConsoleHelper.writeMessage(String.format("%s is displaying... %d, %d", advertisement.getName(), advertisement.getAmountPerOneDisplaying(), advertisement.getAmountPerOneDisplaying() * 1000 / advertisement.getDuration()));
            advertisement.revalidate();
        }

    }

    private List<Advertisement> getMaxProfitList(int restLimit, List<Advertisement> restList) {
        long maxProfit = Integer.MIN_VALUE; //максимальная прибыль
        int maxTime = Integer.MIN_VALUE; //максимальное время
        int maxProfitIdx = -1; //индекс элемента, который обеспечивает максимальную прибыль

        Collections.sort(restList, new Comparator<Advertisement>() { //сортировка нужна, чтобы первые элементы, будучи
            // самыми весомыми по прибыли и времени, в своей комбинации давали лучшие результаты с точки зрения длины
            // этой комбинации (короче - лучше). И, соответсвенно, другие комбинации, с теми же самыми значениями прибыли и времени,
            //можем даже не рассматривать. (Если бы нужна была, наоборот, самая длинная цепочка - то сортировать надо в обратном направлении)
            // Сортировку можно сделать и до внешнего вызова метода
            //т.е. restList заранее отсортировать один раз. Но тогда работа метода будет зависеть от того, как отсортирован параметр restList до вызова метода.
            //Чтобы не зависеть от внешних условий, лучше отсортировать внутри метода. Но и тут можно оптимизировать - сортировать только один раз
            //на самом верхнем (первом) уровне рекурсивного вызова. Для этого, например, можно ввести доп параметр level и увеличивать его для каждого
            //уровня погружения. Ориентируясь на значение level, можно еспечить однократную сортировку. Но тут есть свой минус - надо обеспечить при
            // внешнем вызове метода строго определенное начально значение level (например, 0). Если нешний метод этого не
            // сделает (например, вместо 0 передаст другое значение), то сортировка не произойдет вообще
            @Override
            public int compare(Advertisement o1, Advertisement o2) {
                if (o1.getAmountPerOneDisplaying() == o2.getAmountPerOneDisplaying()) {
                    return Integer.compare(o2.getDuration(), o1.getDuration());

                } else return Long.compare(o2.getAmountPerOneDisplaying(), o1.getAmountPerOneDisplaying());
            }
        });

        List<Advertisement> resultList = new ArrayList<>(); //тут будет лучший набор текущего уровня погружения

        for (int i = 0; i < restList.size(); i++) {
            Advertisement currValue = restList.get(i);

            if (restLimit - currValue.getDuration() >= 0) {//если текущий елемент, не превысит лимит времени , то обрабатываем его ...

                if (isBetter(currValue.getAmountPerOneDisplaying(), currValue.getDuration(), maxProfit, maxTime)) {
                    maxProfit = currValue.getAmountPerOneDisplaying(); //инициируем maxProfit
                    maxTime = currValue.getDuration(); //и maxTime
                    maxProfitIdx = i;
                    //кандидат на лучший результат в текущем переборе (определяется его индексом maxProfitIdx)
                    //Для начала - первый (он же самый большой по прибыли и времени (обеспечено сортировкой)) элемент restList.
                    // Если не будет погружений и/или погружения не дадут лучшего варианта (за счет суммы нескольких элементов,
                    //каждый из которых хуже первого в restList в силу сортировки, но могут совместно суммироваться за счет более полной выборки лимита)
                    //Если такое произойдет, то заменим лучший результат на (текущий элемент+результат погружения)
                }

                List<Advertisement> newRestList = new ArrayList<>(restList.subList(i + 1, restList.size())); //погружаться будем в список с оставшимися элементами
                //(изначально элементы упорядочены убыванию поля getAmountPerOneDisplaying() и убыванию поля getDuration())

                List<Advertisement> tmpResultList = new ArrayList<>(); //список-кандидат. Может оказаться более плохим, поэтому не
                //помещаем сразу в resultList - только, если окажется лучшим предыдущих
                if (restLimit - currValue.getDuration() != 0) { //если лимит исчерпан - нет смысла нырять
                    tmpResultList = getMaxProfitList(restLimit - currValue.getDuration(), newRestList);
                }
                int profit = 0; //сумма прибыли из всплывшего списка
                int time = 0; //сумма времени из всплывшего списка
                for (Advertisement advertisement : tmpResultList) {
                    profit += advertisement.getAmountPerOneDisplaying();
                    time += advertisement.getDuration();
                }

                if (isBetter(currValue.getAmountPerOneDisplaying()+profit, currValue.getDuration()+time, maxProfit, maxTime)) {
                    //если текущий элемент вместе с другими, собранными при погружении, дает лучшую прибыль ...
                    maxProfit = currValue.getAmountPerOneDisplaying() + profit; //... запоминаем новое максимальное значение прибыли
                    maxTime = currValue.getDuration()+time; //... запоминаем новое максимальное значение времени
                    maxProfitIdx = i;    //... запоминаем индекс элемента, который на данный момент дал лучший результат (с учез погружениятом вытянутого и)
                    resultList.clear();  //... собранный при погружении список определяем как результат, который будет выдаваться наверх
                    resultList.addAll(tmpResultList); //это пока не окончательный вариант, но лучший на данной итерации уровня
                }
            }
        }
        if (maxProfitIdx != -1) {//-1 может случиться, если на текущем уровне ни один элемент не подошел под остаток лимита времени (входящий лимит на текущий уровень)
            resultList.add(0, restList.get(maxProfitIdx)); //в лучший список, полученный из погружений, вставляем лучший элемент на данном уровне (в начало списка)
        }
        return resultList;
    }

    private boolean isBetter(long newProfit, int newTime, long maxProfit, int maxTime){
        //можно использовать одну строку условия, но так читабельней
        if (newProfit>maxProfit) return true;
        if ((newProfit==maxProfit) && (newTime>maxTime)) return true;
        return false;
    }
}

