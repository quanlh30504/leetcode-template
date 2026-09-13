Bạn đang hỏi **thuật toán tính ƯCLN (GCD) và BCNN (LCM)**. Đây là một pattern rất quan trọng trong DSA, đặc biệt bài vừa rồi.

## 1. ƯCLN — GCD

**GCD (Greatest Common Divisor)** = ước chung lớn nhất.

Ví dụ:

```text
12 = 1 × 12 = 2 × 6 = 3 × 4
18 = 1 × 18 = 2 × 9 = 3 × 6

GCD(12, 18) = 6
```

### Euclidean Algorithm — thuật toán Euclid

Công thức quan trọng nhất:

```text
GCD(a, b) = GCD(b, a % b)
```

Lặp cho đến khi `b = 0`.

Ví dụ:

```text
GCD(48, 18)

48 % 18 = 12
18 % 12 = 6
12 % 6  = 0

=> GCD = 6
```

### Java

```java
public static long gcd(long a, long b) {
    while (b != 0) {
        long temp = a % b;
        a = b;
        b = temp;
    }

    return a;
}
```

Có thể nhớ bằng flow:

```text
(a, b)
  ↓
a % b
  ↓
(b, a % b)
  ↓
...
  ↓
b == 0
  ↓
a = GCD
```

---

# 2. BCNN — LCM

**LCM (Least Common Multiple)** = bội chung nhỏ nhất.

Ví dụ:

```text
12: 12, 24, 36, 48, ...
18: 18, 36, 54, ...

LCM(12, 18) = 36
```

Công thức:

```text
LCM(a, b) = |a × b| / GCD(a, b)
```

Ví dụ:

```text
a = 12
b = 18
GCD = 6

LCM = 12 × 18 / 6
    = 36
```

### Java

```java
public static long lcm(long a, long b) {
    return a / gcd(a, b) * b;
}
```

### Tại sao viết:

```java
a / gcd(a, b) * b
```

thay vì:

```java
a * b / gcd(a, b)
```

?

Để **giảm nguy cơ overflow**.

Ví dụ:

```text
a = 1,000,000,000
b = 1,000,000,000
```

`a * b` có thể vượt giới hạn kiểu dữ liệu trước khi chia.

Viết:

```java
a / gcd(a, b) * b
```

thì an toàn hơn trong nhiều trường hợp.

---

# 3. Mối quan hệ cực kỳ quan trọng

Với số nguyên dương:

```text
GCD(a, b) × LCM(a, b) = a × b
```

Hay:

```text
LCM(a, b) = a × b / GCD(a, b)
```

Bạn nên nhớ thành một cặp:

```text
              GCD
               │
               ▼
        GCD(a, b)
               │
               ▼
LCM = a / GCD × b
```

---

# 4. GCD của nhiều số

Ví dụ:

```text
A = [24, 36, 60]
```

Ta tính tuần tự:

```text
GCD(24, 36) = 12

GCD(12, 60) = 12

=> GCD(24, 36, 60) = 12
```

Java:

```java
long result = A[0];

for (int i = 1; i < A.length; i++) {
    result = gcd(result, A[i]);
}
```

---

# 5. LCM của nhiều số

Tương tự:

```text
A = [4, 6, 8]
```

```text
LCM(4, 6) = 12

LCM(12, 8) = 24

=> LCM(4, 6, 8) = 24
```

Java:

```java
long result = A[0];

for (int i = 1; i < A.length; i++) {
    result = lcm(result, A[i]);
}
```

---

# 6. Liên hệ trực tiếp với bài bạn vừa hỏi

Bài trước có:

```text
B = [2, 3, 4]
```

Ta tính:

```text
LCM(2, 3) = 6
LCM(6, 4) = 12
```

Vậy:

```text
LCM(B) = 12
```

Điều kiện:

```text
x chia hết cho 2
x chia hết cho 3
x chia hết cho 4
```

được rút gọn thành:

```text
x % 12 == 0
```

Đây chính là **mathematical reduction** giúp bài từ:

```text
for x in A:
    for b in B:
        check x % b
```

thành:

```text
L = LCM(B)

for x in A:
    if x % L == 0:
        ...
```

---

## 7. Một mẹo nhớ cho phỏng vấn

### GCD

> **Chia lấy dư cho đến khi dư bằng 0.**

```java
while (b != 0) {
    long temp = a % b;
    a = b;
    b = temp;
}
return a;
```

### LCM

> **Lấy một số chia GCD trước rồi nhân số còn lại.**

```java
return a / gcd(a, b) * b;
```

### Complexity

Với Euclidean Algorithm:

```text
GCD: O(log(min(a, b)))
LCM: O(log(min(a, b)))
```

Đây là hai công thức bạn nên thuộc lòng khi làm **Number Theory / Math trong LeetCode**.
