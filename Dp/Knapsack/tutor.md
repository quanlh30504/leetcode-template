# Bài giảng DP Pattern: 0/1 Knapsack & Subset Sum

Đây là một trong những **DP pattern quan trọng nhất trong coding interview**. Nếu hiểu đúng bản chất, bạn có thể dùng cùng một tư duy để giải rất nhiều bài như:

* 0/1 Knapsack
* Subset Sum
* Partition Equal Subset Sum
* Target Sum
* Last Stone Weight II
* Minimum Subset Sum Difference
* Count Number of Subsets With Sum K
* Ones and Zeroes
* các bài chọn item dưới constraint về capacity, budget, time, memory...

Điểm cốt lõi không phải nhớ công thức, mà là nhận ra:

> **Mỗi item có 2 lựa chọn: lấy hoặc không lấy. Item chỉ được sử dụng tối đa 1 lần.**

Đó chính là chữ **0/1**.

---

# 1. Mental model của 0/1 Knapsack

Giả sử có `n` món đồ.

Mỗi món có:

```text
weight[i]
value[i]
```

Ba lô chứa tối đa:

```text
capacity = W
```

Mỗi item:

```text
không lấy → 0
lấy       → 1
```

Không được:

```text
item 1 lấy 2 lần
item 1 lấy 3 lần
...
```

Ví dụ:

```text
weights = [1, 3, 4]
values  = [15,20,30]

capacity = 4
```

Ta có:

```text
item A: weight = 1, value = 15
item B: weight = 3, value = 20
item C: weight = 4, value = 30
```

Các lựa chọn:

```text
A              weight = 1    value = 15
B              weight = 3    value = 20
C              weight = 4    value = 30
A + B          weight = 4    value = 35  <- best
```

Answer:

```text
35
```

---

# 2. Tư tưởng quan trọng nhất: Take / Skip

Đứng tại item `i`, chỉ có hai khả năng.

## Skip

Không lấy item hiện tại:

```text
profit = solve(i + 1, capacity)
```

## Take

Nếu còn đủ capacity:

```text
profit =
    value[i]
    + solve(
        i + 1,
        capacity - weight[i]
      )
```

Sau đó:

```text
answer = max(skip, take)
```

Đây chính là recurrence nền tảng:

```text
f(i, capacity)
    =
max(
    f(i + 1, capacity),

    value[i]
    + f(i + 1, capacity - weight[i])
)
```

Notice:

```text
i + 1
```

xuất hiện ở cả hai nhánh.

Đây là chi tiết cực kỳ quan trọng.

Nó có nghĩa:

> Sau khi quyết định với item `i`, ta không được quay lại dùng item `i` nữa.

Đó chính là **0/1 Knapsack**.

---

# 3. Bắt đầu từ Brute Force DFS

Đừng học DP bằng cách nhảy ngay vào `dp[][]`.

Hãy bắt đầu bằng decision tree.

```java
class Solution {

    public int knapsack(
            int[] weights,
            int[] values,
            int capacity
    ) {
        return dfs(weights, values, 0, capacity);
    }

    private int dfs(
            int[] weights,
            int[] values,
            int i,
            int capacity
    ) {

        if (i == weights.length) {
            return 0;
        }

        // option 1: skip item i
        int skip = dfs(
                weights,
                values,
                i + 1,
                capacity
        );

        // option 2: take item i
        int take = 0;

        if (weights[i] <= capacity) {
            take =
                    values[i]
                    + dfs(
                        weights,
                        values,
                        i + 1,
                        capacity - weights[i]
                    );
        }

        return Math.max(skip, take);
    }
}
```

State ở đây là:

```text
(i, capacity)
```

Tại sao?

Bởi vì để biết tương lai có thể kiếm thêm bao nhiêu value, ta chỉ cần biết:

```text
đang xét item nào
capacity còn bao nhiêu
```

Không cần biết trước đó đã chọn những item nào.

---

# 4. Vì sao Brute Force chậm?

Mỗi item có hai lựa chọn:

```text
Take
Skip
```

Decision tree gần giống:

```text
                 item 0
               /        \
            skip        take
             /            \
          item1           item1
         /   \            /   \
      skip   take      skip   take
```

Với `n` item:

```text
O(2^n)
```

Nhưng nhiều state bị tính lại.

Ví dụ:

```text
dfs(5, 10)
```

có thể được gọi rất nhiều lần.

Vậy ta dùng memoization.

---

# 5. Top-down DP: DFS + Memoization

```java
class Solution {

    Integer[][] memo;

    public int knapsack(
            int[] weights,
            int[] values,
            int capacity
    ) {

        memo = new Integer[
                weights.length
        ][capacity + 1];

        return dfs(
                weights,
                values,
                0,
                capacity
        );
    }

    private int dfs(
            int[] weights,
            int[] values,
            int i,
            int capacity
    ) {

        if (i == weights.length) {
            return 0;
        }

        if (memo[i][capacity] != null) {
            return memo[i][capacity];
        }

        int skip = dfs(
                weights,
                values,
                i + 1,
                capacity
        );

        int take = 0;

        if (weights[i] <= capacity) {
            take =
                    values[i]
                    + dfs(
                        weights,
                        values,
                        i + 1,
                        capacity - weights[i]
                    );
        }

        return memo[i][capacity]
                = Math.max(skip, take);
    }
}
```

Số state:

```text
n * capacity
```

Mỗi state xử lý `O(1)`.

Vì vậy:

```text
Time  = O(n * W)
Space = O(n * W)
```

---

# 6. Từ DFS chuyển thành Bottom-up DP

Đây là bước cực kỳ quan trọng để hiểu DP.

Ta định nghĩa:

```text
dp[i][c]
```

là:

> Maximum value có thể đạt được khi sử dụng **i item đầu tiên**, với capacity `c`.

Chú ý:

```text
i item đầu tiên
```

chứ không phải index `i`.

Do đó:

```text
dp[0][c] = 0
```

vì không có item nào thì value bằng `0`.

---

# 7. Transition 2D của 0/1 Knapsack

Giả sử đang xét item:

```text
itemIndex = i - 1
```

Có hai lựa chọn.

Không lấy:

```text
dp[i][c] = dp[i - 1][c]
```

Lấy:

```text
value[item]
+
dp[i - 1][c - weight[item]]
```

Vậy:

```text
dp[i][c]
=
max(
    dp[i - 1][c],

    value[i - 1]
    +
    dp[i - 1][c - weight[i - 1]]
)
```

nếu đủ capacity.

---

# 8. Template chuẩn 2D 0/1 Knapsack

```java
public int knapsack(
        int[] weights,
        int[] values,
        int capacity
) {

    int n = weights.length;

    int[][] dp =
            new int[n + 1][capacity + 1];

    for (int i = 1; i <= n; i++) {

        int weight = weights[i - 1];
        int value = values[i - 1];

        for (int c = 0; c <= capacity; c++) {

            // Skip
            dp[i][c] =
                    dp[i - 1][c];

            // Take
            if (weight <= c) {

                dp[i][c] =
                        Math.max(
                            dp[i][c],
                            value
                            + dp[i - 1][c - weight]
                        );
            }
        }
    }

    return dp[n][capacity];
}
```

Đây là template bạn nên hiểu trước khi tối ưu sang 1D.

---

# 9. Ví dụ chạy DP

Cho:

```text
weight = [1,3,4]
value  = [15,20,30]

W = 4
```

Ban đầu:

```text
capacity

        0   1   2   3   4
dp[0]   0   0   0   0   0
```

Xét item:

```text
weight = 1
value = 15
```

Ta được:

```text
        0   1   2   3   4
dp[1]   0  15  15  15  15
```

Xét:

```text
weight = 3
value = 20
```

Tại capacity `4`:

```text
skip = dp[1][4]
     = 15

take = 20 + dp[1][1]
     = 20 + 15
     = 35
```

Nên:

```text
dp[2][4] = 35
```

Cuối cùng:

```text
answer = 35
```

---

# 10. Tại sao có thể optimize 2D → 1D?

Nhìn recurrence:

```text
dp[i][c]
```

chỉ cần:

```text
dp[i - 1][...]
```

Tức là row hiện tại chỉ phụ thuộc row trước.

Ta không cần giữ toàn bộ:

```text
n x W
```

mà chỉ cần:

```text
dp[W + 1]
```
```
dp[w]
```

Ý nghĩa:

Sau khi xử lý các item đã đi qua, dp[w] là answer tốt nhất với capacity w.
---

# 11. Template 1D chuẩn của 0/1 Knapsack

```java
public int knapsack(
        int[] weights,
        int[] values,
        int capacity
) {

    int[] dp =
            new int[capacity + 1];

    for (int i = 0; i < weights.length; i++) {

        int weight = weights[i];
        int value = values[i];

        for (
                int c = capacity;
                c >= weight;
                c--
        ) {

            dp[c] =
                    Math.max(
                        dp[c],
                        value + dp[c - weight]
                    );
        }
    }

    return dp[capacity];
}
```

Complexity:

```text
Time  = O(n * W)
Space = O(W)
```

Nhưng có một câu cực kỳ quan trọng:

> **Tại sao capacity phải loop từ phải sang trái?**

Đây là một trong những câu hỏi interview rất hay.

---

# 12. Tại sao 0/1 Knapsack phải loop từ phải → trái?

Giả sử chỉ có một item:

```text
weight = 2
value = 3

capacity = 4
```

Item chỉ được dùng **một lần**.

Ban đầu:

```text
dp = [0,0,0,0,0]
```

Nếu bạn loop từ trái → phải:

```java
for (int c = weight; c <= capacity; c++) {
    dp[c] =
        Math.max(
            dp[c],
            value + dp[c - weight]
        );
}
```

Khi:

```text
c = 2
```

ta update:

```text
dp[2] = 3
```

Array:

```text
[0,0,3,0,0]
```

Sau đó:

```text
c = 4
```

ta tính:

```text
dp[4]
=
3 + dp[2]
=
3 + 3
=
6
```

Nhưng `dp[2] = 3` vừa được tạo bởi **chính item hiện tại**.

Có nghĩa:

```text
item weight=2
```

được dùng hai lần.

Sai với 0/1 Knapsack.

---

# 13. Loop ngược giải quyết vấn đề thế nào?

Ta chạy:

```text
4 → 3 → 2
```

Khi:

```text
c = 4
```

ta đọc:

```text
dp[2]
```

Nhưng `dp[2]` vẫn là giá trị **trước khi xử lý item hiện tại**.

Do đó item hiện tại không thể tự reuse.

Mental model:

```text
for each item:
    scan capacity backward
```

nghĩa là:

> mỗi item chỉ được ảnh hưởng đến mỗi state một lần.

Cực kỳ quan trọng:

```text
0/1 Knapsack
capacity: HIGH → LOW
```

Trong khi:

```text
Unbounded Knapsack
capacity: LOW → HIGH
```

---

# 14. Subset Sum thực chất là 0/1 Knapsack

Bài:

> Cho array `nums`, có tồn tại subset nào có tổng bằng `target` không?

Ví dụ:

```text
nums = [1, 5, 11, 5]
target = 11
```

Có:

```text
[11]
```

hoặc:

```text
[1,5,5]
```

Answer:

```text
true
```

Hãy so sánh với Knapsack.

Trong Knapsack:

```text
item weight = nums[i]
capacity    = target
```

Mỗi number:

```text
take / skip
```

Mỗi number chỉ dùng một lần.

Đây chính xác là:

```text
0/1 Knapsack
```

Chỉ khác objective.

0/1 Knapsack hỏi:

```text
maximize value
```

Subset Sum hỏi:

```text
can we reach target?
```

Vì vậy DP chuyển từ:

```java
int
```

sang:

```java
boolean
```

---

# 15. DFS tư duy cho Subset Sum

Định nghĩa:

```text
dfs(i, remaining)
```

là:

> từ index `i` trở đi, có tạo được tổng `remaining` không?

Hai lựa chọn:

```text
skip nums[i]

take nums[i]
```

Transition:

```text
dfs(i, remaining)
=
dfs(i + 1, remaining)
OR
dfs(i + 1, remaining - nums[i])
```

Base case:

```text
remaining == 0
→ true
```

---

# 16. DFS + Memo Subset Sum

```java
class Solution {

    Boolean[][] memo;

    public boolean subsetSum(
            int[] nums,
            int target
    ) {

        memo =
            new Boolean[
                nums.length
            ][target + 1];

        return dfs(nums, 0, target);
    }

    private boolean dfs(
            int[] nums,
            int i,
            int remaining
    ) {

        if (remaining == 0) {
            return true;
        }

        if (
            i == nums.length
            || remaining < 0
        ) {
            return false;
        }

        if (memo[i][remaining] != null) {
            return memo[i][remaining];
        }

        boolean skip =
                dfs(
                    nums,
                    i + 1,
                    remaining
                );

        boolean take =
                dfs(
                    nums,
                    i + 1,
                    remaining - nums[i]
                );

        return memo[i][remaining]
                = skip || take;
    }
}
```

---

# 17. Bottom-up 2D Subset Sum

Định nghĩa:

```text
dp[i][s]
```

=

> Có thể dùng `i` number đầu tiên để tạo sum `s` hay không?

Base case:

```text
dp[i][0] = true
```

Tại sao?

Bởi vì tổng `0` luôn tạo được bằng:

```text
empty subset {}
```

Transition:

```text
skip:
dp[i - 1][s]

take:
dp[i - 1][s - nums[i - 1]]
```

Do đó:

```text
dp[i][s]
=
dp[i - 1][s]
||
dp[i - 1][s - num]
```

---

# 18. Template 2D Subset Sum

```java
public boolean subsetSum(
        int[] nums,
        int target
) {

    int n = nums.length;

    boolean[][] dp =
            new boolean[n + 1][target + 1];

    for (int i = 0; i <= n; i++) {
        dp[i][0] = true;
    }

    for (int i = 1; i <= n; i++) {

        int num = nums[i - 1];

        for (int sum = 1; sum <= target; sum++) {

            // skip
            dp[i][sum] =
                    dp[i - 1][sum];

            // take
            if (num <= sum) {

                dp[i][sum] =
                        dp[i][sum]
                        ||
                        dp[i - 1][sum - num];
            }
        }
    }

    return dp[n][target];
}
```

---

# 19. Template 1D Subset Sum — template nên nhớ

```java
public boolean subsetSum(
        int[] nums,
        int target
) {

    boolean[] dp =
            new boolean[target + 1];

    dp[0] = true;

    for (int num : nums) {

        for (
                int sum = target;
                sum >= num;
                sum--
        ) {

            dp[sum] =
                    dp[sum]
                    || dp[sum - num];
        }
    }

    return dp[target];
}
```

Đây là một template cực kỳ quan trọng.

Bạn nên đọc:

```java
dp[sum] = dp[sum] || dp[sum - num];
```

thành tiếng Việt:

> Sum `sum` tạo được nếu trước đó nó đã tạo được, **hoặc** ta có thể tạo `sum - num`, sau đó thêm `num`.

---

# 20. Example Subset Sum step-by-step

```text
nums = [1,5,11,5]
target = 11
```

Ban đầu:

```text
dp[0] = true
```

Có nghĩa:

```text
sum 0 reachable
```

Array logic:

```text
sum:
0 1 2 3 4 5 6 7 8 9 10 11

T F F F F F F F F F F F
```

Sau `num = 1`:

```text
T T F F F F F F F F F F
```

Reachable:

```text
0
1
```

Sau `num = 5`:

```text
0
1
5
6
```

vì:

```text
5 = 5
6 = 1 + 5
```

Sau `num = 11`:

```text
11
```

reachable.

Answer:

```text
true
```

---

# 21. Một framework nhận diện bài 0/1 Knapsack

Khi gặp bài DP, hãy tự hỏi theo thứ tự:

```text
1. Có một tập các item / number không?

2. Với mỗi item có quyết định:
   Take / Skip không?

3. Mỗi item chỉ được sử dụng tối đa một lần không?

4. Có constraint kiểu:
   capacity
   sum
   budget
   time
   memory
   number of zeros/ones
   ...?

5. Objective là gì?
```

Nếu objective là:

```text
maximum / minimum
```

thường dùng:

```java
Math.max(...)
Math.min(...)
```

Nếu hỏi:

```text
có thể / không thể
```

dùng:

```java
boolean
OR
```

Nếu hỏi:

```text
bao nhiêu cách
```

dùng:

```java
count += ...
```

Đây là cách cực hay để không phải học thuộc từng bài.

---

# 22. Ba dạng quan trọng nhất

Có thể xem cùng một skeleton:

```text
for each item:
    for capacity descending:
        dp[current] = combine(
            skip,
            take
        )
```

Chỉ thay đổi phép `combine`.

### Maximum value

```java
dp[c] =
    Math.max(
        dp[c],
        value + dp[c - weight]
    );
```

### Feasibility

```java
dp[s] =
    dp[s]
    || dp[s - num];
```

### Count

```java
dp[s] += dp[s - num];
```

Đây gần như là toàn bộ family của pattern này.

---

# 23. Pattern 1 — Partition Equal Subset Sum

LeetCode 416.

Bài toán:

```text
nums = [1,5,11,5]
```

Có thể chia thành hai subset có tổng bằng nhau không?

Gọi:

```text
total = sum(nums)
```

Nếu chia được:

```text
subset1 + subset2 = total

subset1 = subset2
```

suy ra:

```text
subset1 = total / 2
```

Vậy bài toán biến thành:

> Có subset nào sum bằng `total / 2` không?

Tức là **Subset Sum**.

---

# 24. Java — Partition Equal Subset Sum

```java
class Solution {

    public boolean canPartition(int[] nums) {

        int total = 0;

        for (int num : nums) {
            total += num;
        }

        if (total % 2 != 0) {
            return false;
        }

        int target = total / 2;

        boolean[] dp =
                new boolean[target + 1];

        dp[0] = true;

        for (int num : nums) {

            for (
                    int sum = target;
                    sum >= num;
                    sum--
            ) {

                dp[sum] =
                        dp[sum]
                        || dp[sum - num];
            }
        }

        return dp[target];
    }
}
```

Mental transformation:

```text
Equal Partition
      ↓
find subset = total / 2
      ↓
Subset Sum
      ↓
0/1 Knapsack
```

---

# 25. Pattern 2 — Count Subsets With Sum K

Bây giờ không hỏi:

```text
có tồn tại không?
```

mà hỏi:

```text
có bao nhiêu subset?
```

Ví dụ:

```text
nums = [1,2,3,3]
target = 6
```

Có thể có:

```text
[1,2,3]
[1,2,3 khác]
[3,3]
```

Ta thay:

```java
boolean[]
```

bằng:

```java
long[]
```

Base:

```java
dp[0] = 1;
```

Tại sao `1`?

Có đúng một cách tạo tổng `0` trước khi xét item:

```text
chọn empty subset
```

Transition:

```java
dp[sum] += dp[sum - num];
```

---

# 26. Template Count Subset Sum

```java
public long countSubsets(
        int[] nums,
        int target
) {

    long[] dp = new long[target + 1];

    dp[0] = 1;

    for (int num : nums) {

        for (
                int sum = target;
                sum >= num;
                sum--
        ) {

            dp[sum] += dp[sum - num];
        }
    }

    return dp[target];
}
```

Đọc transition:

```text
dp[sum] hiện tại
+
số cách tạo sum-num rồi thêm num
```

---

# 27. Pattern 3 — Target Sum

LeetCode 494 là một bài rất hay vì nhìn ban đầu không giống Knapsack.

Cho:

```text
nums = [1,1,1,1,1]
target = 3
```

Mỗi number phải đặt:

```text
+
hoặc
-
```

Ví dụ:

```text
-1 +1 +1 +1 +1 = 3
```

Ta cần đếm số cách.

Gọi:

```text
P = tổng các number mang dấu +
N = tổng các number mang dấu -
```

Ta có:

```text
P - N = target
```

Mặt khác:

```text
P + N = total
```

Cộng hai phương trình:

```text
2P = total + target
```

Suy ra:

```text
P = (total + target) / 2
```

Bài toán trở thành:

> Có bao nhiêu subset có tổng bằng `(total + target)/2`?

Đó chính là **Count Subset Sum**.

Đây là kiểu transformation rất quan trọng.

---

# 28. Java — Target Sum

```java
class Solution {

    public int findTargetSumWays(
            int[] nums,
            int target
    ) {

        int total = 0;

        for (int num : nums) {
            total += num;
        }

        if (Math.abs(target) > total) {
            return 0;
        }

        if ((total + target) % 2 != 0) {
            return 0;
        }

        int subsetTarget =
                (total + target) / 2;

        int[] dp =
                new int[subsetTarget + 1];

        dp[0] = 1;

        for (int num : nums) {

            for (
                    int sum = subsetTarget;
                    sum >= num;
                    sum--
            ) {

                dp[sum] +=
                        dp[sum - num];
            }
        }

        return dp[subsetTarget];
    }
}
```

---

# 29. Một chi tiết rất hay: số 0 trong Target Sum

Ví dụ:

```text
nums = [0,1]
target = 1
```

Số `0` có thể là:

```text
+0
-0
```

Hai cách khác nhau mặc dù kết quả giống nhau.

Với:

```java
dp[0] = 1;
```

khi `num = 0`:

```java
dp[0] += dp[0];
```

thành:

```text
1 → 2
```

DP tự động count đúng hai lựa chọn.

Đây là một điểm rất đẹp của recurrence.

---

# 30. Pattern 4 — Minimum Subset Sum Difference

Cho array, chia thành hai subset sao cho:

```text
|sum1 - sum2|
```

nhỏ nhất.

Ta biết:

```text
sum1 + sum2 = total
```

Suy ra:

```text
sum2 = total - sum1
```

Difference:

```text
|sum1 - sum2|

= |sum1 - (total - sum1)|

= |2 * sum1 - total|
```

Muốn difference nhỏ nhất thì:

```text
sum1
```

phải càng gần:

```text
total / 2
```

càng tốt.

Vậy:

> Tìm subset sum lớn nhất ≤ `total / 2`.

Lại trở về Subset Sum.

---

# 31. Template Minimum Partition Difference

```java
public int minimumDifference(int[] nums) {

    int total = 0;

    for (int num : nums) {
        total += num;
    }

    int target = total / 2;

    boolean[] dp =
            new boolean[target + 1];

    dp[0] = true;

    for (int num : nums) {

        for (
                int sum = target;
                sum >= num;
                sum--
        ) {

            dp[sum] =
                    dp[sum]
                    || dp[sum - num];
        }
    }

    for (int sum = target; sum >= 0; sum--) {

        if (dp[sum]) {

            int other =
                    total - sum;

            return Math.abs(
                    other - sum
            );
        }
    }

    return total;
}
```

---

# 32. Pattern 5 — Last Stone Weight II

LeetCode 1049.

Bài toán có các viên đá:

```text
stones = [2,7,4,1,8,1]
```

Mỗi lần smash:

```text
x và y
→ |x-y|
```

Nhìn có vẻ là simulation.

Nhưng bản chất có thể coi mỗi stone được gán:

```text
+
hoặc
-
```

Kết quả cuối cùng gần tương đương:

```text
|sum(groupA) - sum(groupB)|
```

Ta cần minimize difference.

Vậy bài này chính là:

```text
Minimum Subset Sum Difference
```

Hay nói cách khác:

```text
Partition
→ Subset Sum
→ 0/1 Knapsack
```

---

# 33. Pattern 6 — Maximize value với budget / time

Một bài có thể không dùng từ `"knapsack"`.

Ví dụ:

```text
Có n project.

project i:
cost[i]
profit[i]

budget = B

Mỗi project chỉ làm một lần.

Maximize profit.
```

Đây vẫn là:

```text
weight   = cost
value    = profit
capacity = budget
```

Template:

```java
for (int i = 0; i < n; i++) {

    for (
        int b = budget;
        b >= cost[i];
        b--
    ) {

        dp[b] =
            Math.max(
                dp[b],
                profit[i] + dp[b - cost[i]]
            );
    }
}
```

Tên biến khác nhưng pattern hoàn toàn giống nhau.

---

# 34. Pattern 7 — 2-dimensional Knapsack

LeetCode 474 — Ones and Zeroes.

Mỗi string có:

```text
numberOfZeros
numberOfOnes
```

Ta có capacity:

```text
m zeros
n ones
```

Mỗi string chỉ lấy một lần.

Đây vẫn là 0/1 Knapsack, nhưng thay vì một capacity:

```text
capacity
```

ta có hai capacity:

```text
zeros
ones
```

State:

```text
dp[z][o]
```

=

> maximum number of strings có thể chọn với `z` zeros và `o` ones.

Transition:

```text
dp[z][o]
=
max(
    dp[z][o],
    1 + dp[z-zeroCount][o-oneCount]
)
```

---

# 35. Java — Ones and Zeroes

```java
class Solution {

    public int findMaxForm(
            String[] strs,
            int m,
            int n
    ) {

        int[][] dp =
                new int[m + 1][n + 1];

        for (String s : strs) {

            int zeros = 0;
            int ones = 0;

            for (char ch : s.toCharArray()) {

                if (ch == '0') {
                    zeros++;
                } else {
                    ones++;
                }
            }

            for (
                int z = m;
                z >= zeros;
                z--
            ) {

                for (
                    int o = n;
                    o >= ones;
                    o--
                ) {

                    dp[z][o] =
                            Math.max(
                                dp[z][o],
                                1
                                + dp[z - zeros][o - ones]
                            );
                }
            }
        }

        return dp[m][n];
    }
}
```

Notice cả hai dimension đều loop ngược:

```text
z: high → low
o: high → low
```

vì mỗi string chỉ được dùng một lần.

---

# 36. Exact capacity vs At-most capacity

Có một nuance khá quan trọng.

Trong Knapsack cổ điển:

> capacity tối đa là `W`.

Không nhất thiết phải dùng hết.

Vì vậy:

```java
int[] dp = new int[W + 1];
```

khởi tạo `0` là hợp lý.

Nhưng có những bài hỏi:

> Phải đạt **chính xác** capacity `W`.

Lúc đó không thể mặc định mọi state đều reachable.

Ví dụ:

```text
items weight = [3]
target = 2
```

Không thể tạo weight `2`.

Ta thường initialize:

```java
Arrays.fill(dp, NEG_INF);
dp[0] = 0;
```

Ví dụ:

```java
int[] dp = new int[capacity + 1];

Arrays.fill(dp, Integer.MIN_VALUE / 2);

dp[0] = 0;
```

Sau đó:

```java
for (item) {

    for (int c = capacity; c >= weight; c--) {

        dp[c] =
            Math.max(
                dp[c],
                value + dp[c - weight]
            );
    }
}
```

Đây là kỹ thuật rất đáng nhớ trong những bài `"exactly"`.

---

# 37. 0/1 Knapsack vs Unbounded Knapsack

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

---

# 38. Bộ template mình khuyên bạn học thuộc

## Template A — Max value 0/1 Knapsack

```java
int[] dp = new int[capacity + 1];

for (int i = 0; i < n; i++) {

    for (
        int c = capacity;
        c >= weight[i];
        c--
    ) {

        dp[c] =
            Math.max(
                dp[c],
                value[i] + dp[c - weight[i]]
            );
    }
}

return dp[capacity];
```

---

## Template B — Subset Sum / Feasibility

```java
boolean[] dp =
        new boolean[target + 1];

dp[0] = true;

for (int num : nums) {

    for (
        int sum = target;
        sum >= num;
        sum--
    ) {

        dp[sum] =
            dp[sum]
            || dp[sum - num];
    }
}

return dp[target];
```

---

## Template C — Count subsets

```java
long[] dp =
        new long[target + 1];

dp[0] = 1;

for (int num : nums) {

    for (
        int sum = target;
        sum >= num;
        sum--
    ) {

        dp[sum] +=
            dp[sum - num];
    }
}

return dp[target];
```

Ba template này giải được một lượng rất lớn bài DP dạng subset.

---

# 39. Cách tư duy chuẩn trong interview

Giả sử gặp một bài mới.

Đừng nghĩ ngay:

```text
"Oh bài này dùng dp[]"
```

Hãy đi qua chuỗi suy luận:

```text
Input có n objects.

Mỗi object có decision:
take / skip.

Mỗi object chỉ được quyết định một lần.

→ Decision tree.

Future phụ thuộc vào:
index
capacity / remaining target.

→ State:
dfs(i, remaining)

Có overlapping subproblems.

→ memoization.

Số state:
n * target

→ O(n * target)

Transition chỉ phụ thuộc previous item.

→ bottom-up 2D.

Previous row only.

→ optimize 1D.

0/1 item.

→ capacity traverse backward.
```

Đây mới là tư duy DP bạn cần luyện.

---

# 40. Công thức tổng quát của cả family

Bạn có thể hình dung mọi bài 0/1 Knapsack theo skeleton:

```java
for (ITEM item : items) {

    for (
        int state = maxState;
        state >= cost(item);
        state--
    ) {

        dp[state] =
            combine(
                dp[state],
                contribution(item)
                + dp[state - cost(item)]
            );
    }
}
```

Chỉ thay đổi `combine`.

Knapsack:

```text
max
```

Subset Sum:

```text
OR
```

Count subset:

```text
+
```

Minimize:

```text
min
```

Đây là insight quan trọng hơn rất nhiều so với việc thuộc lòng code.

---

# 41. Những bài LeetCode nên luyện theo thứ tự

Nếu bạn đang học pattern này để interview, mình đề xuất progression:

| Level | Problem                         | Pattern                   |
| ----- | ------------------------------- | ------------------------- |
| 1     | Subset Sum                      | base pattern              |
| 2     | 416. Partition Equal Subset Sum | subset sum                |
| 3     | 1049. Last Stone Weight II      | min partition difference  |
| 4     | 494. Target Sum                 | algebra → count subset    |
| 5     | Count Subsets With Sum K        | counting                  |
| 6     | 474. Ones and Zeroes            | 2D capacity               |
| 7     | 879. Profitable Schemes         | multiple state + counting |
| 8     | 956. Tallest Billboard          | advanced subset DP        |

Đặc biệt nên chắc 4 bài:

```text
416 Partition Equal Subset Sum
494 Target Sum
1049 Last Stone Weight II
474 Ones and Zeroes
```

Nếu tự suy ra được cả bốn từ 0/1 Knapsack thì bạn đã hiểu pattern khá vững.

---

# 42. Cheat sheet nhận diện nhanh

Khi đề nói:

```text
choose some elements
select subset
divide into two groups
each element can be used once
maximum value under...
sum exactly...
is it possible...
number of ways...
```

hãy nghĩ ngay:

```text
                0/1 Decision
                     |
                 Take / Skip
                     |
              ----------------
              |              |
          Knapsack        Subset Sum
              |              |
          maximize         reachable
                             |
              ---------------------------------
              |               |               |
          partition         count          min diff
              |               |
          total/2         Target Sum
```

---

# 43. Sai lầm phổ biến nhất

Sai lầm số một:

```java
for (int sum = num; sum <= target; sum++)
```

trong bài 0/1.

Điều này có thể vô tình biến bài thành:

```text
unbounded knapsack
```

Sai lầm thứ hai là không định nghĩa state bằng câu tiếng Anh.

Trước khi code, hãy luôn viết:

```text
dp[s] means ...
```

Ví dụ:

```text
dp[s] =
whether a subset of processed elements
can create sum s
```

Sai lầm thứ ba là không hiểu `dp[0]`.

Feasibility:

```java
dp[0] = true;
```

vì empty set tạo tổng `0`.

Counting:

```java
dp[0] = 1;
```

vì có đúng một cách ban đầu để tạo tổng `0`: không chọn gì.

---

# 44. Một ví dụ tổng hợp để kiểm tra bạn đã hiểu chưa

Cho:

```text
nums = [2,3,7]
target = 5
```

Subset Sum:

```java
boolean[] dp =
    new boolean[6];

dp[0] = true;
```

Xét `2`:

```text
reachable:
0,2
```

Xét `3`:

```text
reachable:
0,2,3,5
```

Tại `sum = 5`:

```java
dp[5]
=
dp[5]
||
dp[5 - 3]

=
false
||
dp[2]

=
true
```

Ý nghĩa:

```text
trước khi dùng 3,
ta đã tạo được 2.

Thêm 3:
2 + 3 = 5.
```

Vậy:

```text
target reachable
```

Đây chính là cách bạn nên **đọc một transition DP**, thay vì chỉ nhìn nó như công thức.

---

# 45. Bản chất cuối cùng cần nhớ

Nếu chỉ nhớ 5 điều về bài này, hãy nhớ:

**1. 0/1 Knapsack bắt đầu từ decision tree**

```text
Take
Skip
```

**2. State thường là**

```text
(index, remaining capacity)
```

hoặc sau khi optimize:

```text
dp[capacity]
```

**3. Subset Sum chính là một dạng 0/1 Knapsack**

```text
weight = nums[i]
capacity = target
```

chỉ khác objective là boolean.

**4. Với 0/1 Knapsack 1D:**

```text
capacity phải chạy từ lớn → nhỏ
```

để item hiện tại không được reuse.

**5. Các biến thể chỉ thay đổi ý nghĩa của `dp`**

```text
Maximize:
dp[c] = max(...)

Feasibility:
dp[s] = dp[s] || ...

Counting:
dp[s] += ...
```

Nếu bạn nhìn được một bài mới và chuyển nó thành:

```text
items
+
take / skip
+
capacity / target
+
objective
```

thì gần như bạn đã nhận ra được **0/1 Knapsack family**.
