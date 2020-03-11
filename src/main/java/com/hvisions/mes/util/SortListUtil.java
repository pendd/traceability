package com.hvisions.mes.util;

public class SortListUtil<T> {
    /**
    public void Sort(List<T> list, final String method, final String sort) {
        Collections.sort(list, new Comparator<Object>() {
            public int compare(Object a, Object b) {
                int ret = 0;
                try {
                    Method m1 = ((T) a).getClass().getMethod(method, null);
                    Method m2 = ((T) b).getClass().getMethod(method, null);
                    if (sort != null && "desc".equals(sort))// 倒序
                        ret = m2.invoke(((T) b), null).toString()
                                .compareTo(m1.invoke(((T) a), null).toString());
                    else
                        // 正序
                        ret = m1.invoke(((T) a), null).toString()
                                .compareTo(m2.invoke(((T) b), null).toString());
                } catch (NoSuchMethodException ne) {
                    System.out.println(ne);
                } catch (IllegalAccessException ie) {
                    System.out.println(ie);
                } catch (InvocationTargetException it) {
                    System.out.println(it);
                }
                return ret;
            }
        });
    }
**/
}
