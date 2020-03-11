package com.hvisions.mes;

import com.hvisions.mes.domain.OrderDetail;
import com.hvisions.mes.domain.User;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.Months;
import org.joda.time.Years;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

/**
 * @author dpeng
 * @description
 * @date 2019-08-29 23:50
 */
public class Test {

    public static void main(String[] args) {

        int days = Days.daysBetween(DateTime.parse("2018-02-16"), DateTime.parse("2019-03-02")).getDays();
        System.out.println(days);

        int months = Months.monthsBetween(DateTime.parse("2017-9-3"), DateTime.parse("2019-3-4")).getMonths();
        System.out.println(months);

        int years = Years.yearsBetween(DateTime.parse("2010-3-4"), DateTime.parse("2019-4-5")).getYears();
        int monthOfYear = DateTime.parse("2010-3-4").getMonthOfYear();
        int monthOfYear1 = DateTime.parse("2019-4-5").getMonthOfYear();
        int dayOfMonth1 = DateTime.parse("2010-3-4").getDayOfMonth();
        int dayOfMonth = DateTime.parse("2019-4-5").getDayOfMonth();
        System.out.println(years);
        System.out.println(monthOfYear);
        System.out.println(monthOfYear1);
        System.out.println(dayOfMonth1);
        System.out.println(dayOfMonth);

    }

    @org.junit.Test
    public void test() {
        ArrayList<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);

        List<Integer> newList = new ArrayList<>();
        newList.add(2);
        newList.add(2);
        newList.add(5);

        list.removeAll(newList);

        System.out.println(list);


        System.err.println(DateTime.now().plusDays(-1));
    }

    @org.junit.Test
    public void test2() {

        List<OrderDetail> list = new ArrayList<>();

        OrderDetail orderDetail1 = new OrderDetail();
//        orderDetail1.setOrderNum(2);
        orderDetail1.setOrderId(1);

        OrderDetail orderDetail2 = new OrderDetail();
        orderDetail2.setOrderNum(3);
        orderDetail2.setOrderId(2);

        list.add(orderDetail1);
        list.add(orderDetail2);



        Optional<OrderDetail> max = list.stream().filter(od -> od.getOrderNum() != null).max(Comparator.comparing(OrderDetail::getOrderNum));

        System.out.println(max.orElse(null));

    }

    @org.junit.Test
    public void test3() {
        ArrayList<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);

        int[] size = new int[] {0};

        list.forEach(a -> {
            size[0]++;
        });
        System.out.println(size[0]);
    }

    @org.junit.Test
    public void test4() {
        User user = User.builder().name("admin").age(20).build();
        user = user.toBuilder().name("lily").build();
        System.out.println(user);
    }
}
