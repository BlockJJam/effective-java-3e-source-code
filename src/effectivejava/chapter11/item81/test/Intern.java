package effectivejava.chapter11.item81.test;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

// ConcurrentMap으로 구현한 동시성 정규화 맵
public class Intern {
    private static final ConcurrentMap<String, String> map = new ConcurrentHashMap<>();

    //  아래 더 빠른 녀석 소개
//    public static String intern(String s){
//        String previousValue = map.putIfAbsent(s, s);
//        return previousValue == null? s : previousValue;
//    }

    // 코드 81-2 ConcurrentMap으로 구현한 동시성 정규화 맵 - 더 빠르다! (432쪽)
    public static String intern(String s){
        String result = map.get(s); // get이 null을 더 빨리 체크할 수 있기 때문
        if(result == null){
            result = map.putIfAbsent(s, s);
            if(result == null)
                result = s;
        }

        return result;
    }


}
