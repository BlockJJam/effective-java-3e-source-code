package effectivejava.chapter8.item52;

// 재정의된 메서드 호출 메커니즘 (313쪽, 코드 52-2의 일부)
public class SparklingWine extends Wine {
    @Override String name() { return "발포성 포도주"; }
}
