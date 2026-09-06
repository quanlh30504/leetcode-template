0/1 Knapsack vs Unbounded Knapsack

Hai pattern nhìn gần giống hệt nhau.

Khác nhau ở:

```text
mỗi item dùng được bao nhiêu lần?
```

0/1 Knapsack:

```text
0 hoặc 1 lần
```

Unbounded:

```text
0,1,2,3,... lần
```

Ví dụ Coin Change:

```text
coins = [1,2,5]
```

Coin `2` có thể dùng nhiều lần.

Do đó capacity loop:

```text
LOW → HIGH
```

Ví dụ:

```java
for (int coin : coins) {

    for (
        int sum = coin;
        sum <= target;
        sum++
    ) {

        ...
    }
}
```

Ngược lại 0/1:

```java
for (int num : nums) {

    for (
        int sum = target;
        sum >= num;
        sum--
    ) {

        ...
    }
}
```

Bạn nên nhớ bảng tư duy:

| Pattern            |   Item reuse | Capacity loop |
| ------------------ | -----------: | ------------- |
| 0/1 Knapsack       | tối đa 1 lần | `high → low`  |
| Unbounded Knapsack |    unlimited | `low → high`  |

Nhưng tốt nhất không chỉ thuộc bảng.

Hãy hiểu:

> Loop direction quyết định liệu update của item hiện tại có được sử dụng lại ngay trong cùng iteration hay không.
