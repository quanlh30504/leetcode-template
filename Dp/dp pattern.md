Có. Thực tế **0/1 Knapsack và Unbounded Knapsack chỉ là một nhánh trong “bản đồ DP”**.

Một cách học DP hiệu quả hơn là không học theo từng bài LeetCode, mà học theo các **pattern state + transition**. Phần lớn bài DP trong coding interview có thể quy về khoảng 8–10 pattern lớn.

---

# 1. Bản đồ các DP Pattern quan trọng

Bạn có thể hình dung:

```text
Dynamic Programming
│
├── 1. Linear / Prefix DP
│      ├── Fibonacci
│      ├── Climbing Stairs
│      ├── House Robber
│      └── Decode Ways
│
├── 2. Knapsack / Choice DP
│      ├── 0/1 Knapsack
│      ├── Unbounded Knapsack
│      ├── Bounded Knapsack
│      ├── Group Knapsack
│      └── Multi-dimensional Knapsack
│
├── 3. Grid DP
│      ├── Unique Paths
│      ├── Minimum Path Sum
│      └── Dungeon Game
│
├── 4. Two-Sequence / String DP
│      ├── LCS
│      ├── Edit Distance
│      ├── Distinct Subsequences
│      └── Interleaving String
│
├── 5. Subsequence DP
│      ├── LIS
│      ├── Largest Divisible Subset
│      └── Russian Doll Envelopes
│
├── 6. Interval DP
│      ├── Matrix Chain Multiplication
│      ├── Burst Balloons
│      └── Palindrome Partition
│
├── 7. State Machine DP
│      ├── Stock problems
│      ├── cooldown
│      ├── transaction limit
│      └── multiple states
│
├── 8. Tree / DAG DP
│      ├── House Robber III
│      ├── longest path
│      └── subtree decisions
│
├── 9. Bitmask DP
│      ├── TSP
│      ├── Assignment
│      └── visit subset problems
│
└── 10. Digit DP
       └── count numbers satisfying constraints
```

Nếu mục tiêu là coding interview, mình khuyên học theo thứ tự:

```text
Linear DP
    ↓
0/1 + Unbounded Knapsack
    ↓
Grid DP
    ↓
LCS / String DP
    ↓
LIS
    ↓
State Machine DP
    ↓
Interval DP
    ↓
Tree DP
    ↓
Bitmask DP
    ↓
Digit DP
```

---

# 2. Trước hết: mở rộng họ Knapsack

Bạn đã biết hai loại:

```text
0/1 Knapsack
item dùng 0 hoặc 1 lần

Unbounded Knapsack
item dùng vô hạn lần
```

Nhưng còn vài biến thể rất điển hình.

---

# 3. Unbounded Knapsack

## Ý tưởng

Khác với 0/1:

```text
take item i
→ vẫn có thể tiếp tục lấy item i
```

DFS:

```text
0/1:

take =
value[i] + dfs(i + 1, capacity - weight[i])
                     ↑
                  sang item mới
```

Unbounded:

```text
take =
value[i] + dfs(i, capacity - weight[i])
                     ↑
                 vẫn item i
```

Đó là khác biệt cốt lõi.

---

## Bottom-up 1D

0/1:

```java
for (int c = capacity; c >= weight; c--)
```

Unbounded:

```java
for (int c = weight; c <= capacity; c++)
```

Template:

```java
public int unboundedKnapsack(
        int[] weights,
        int[] values,
        int capacity
) {

    int[] dp = new int[capacity + 1];

    for (int i = 0; i < weights.length; i++) {

        int weight = weights[i];
        int value = values[i];

        for (int c = weight; c <= capacity; c++) {

            dp[c] = Math.max(
                    dp[c],
                    value + dp[c - weight]
            );
        }
    }

    return dp[capacity];
}
```

---

# 4. Coin Change chính là Unbounded Knapsack

Ví dụ:

```text
coins = [1,2,5]
amount = 11
```

Mỗi coin:

```text
có thể dùng unlimited times
```

Nên đây là:

```text
Unbounded Knapsack
```

Nhưng objective thay đổi.

Knapsack:

```text
maximize value
```

Coin Change:

```text
minimize number of coins
```

---

## Coin Change — Minimum number of coins

State:

```text
dp[x] =
minimum coins needed to create amount x
```

Base:

```text
dp[0] = 0
```

Transition:

```text
dp[x]
=
min(
    dp[x],
    1 + dp[x - coin]
)
```

Java:

```java
class Solution {

    public int coinChange(int[] coins, int amount) {

        int INF = amount + 1;

        int[] dp = new int[amount + 1];

        Arrays.fill(dp, INF);

        dp[0] = 0;

        for (int coin : coins) {

            for (int sum = coin; sum <= amount; sum++) {

                dp[sum] = Math.min(
                        dp[sum],
                        1 + dp[sum - coin]
                );
            }
        }

        return dp[amount] == INF
                ? -1
                : dp[amount];
    }
}
```

---

# 5. Một insight cực kỳ quan trọng: Combination vs Permutation

Hai bài nhìn gần như giống nhau:

```text
coins = [1,2,5]
target = 5
```

Nhưng:

### Coin Change II

```text
1 + 2 + 2
```

và:

```text
2 + 1 + 2
```

là **cùng một combination**.

### Combination Sum IV

Hai thứ trên được coi là:

```text
2 permutations khác nhau
```

Khác biệt nằm ở **loop order**.

---

# 6. Count Combination

Coin outer:

```java
for (int coin : coins) {

    for (int sum = coin; sum <= target; sum++) {

        dp[sum] += dp[sum - coin];
    }
}
```

Template:

```java
public int countCombinations(
        int[] coins,
        int target
) {

    int[] dp = new int[target + 1];

    dp[0] = 1;

    for (int coin : coins) {

        for (int sum = coin; sum <= target; sum++) {

            dp[sum] += dp[sum - coin];
        }
    }

    return dp[target];
}
```

Tại sao?

Bởi vì ta xử lý:

```text
tất cả combination dùng coin 1
→ rồi thêm coin 2
→ rồi thêm coin 5
```

Order bị cố định theo item.

---

# 7. Count Permutation

Đổi thứ tự loop:

```java
for (int sum = 1; sum <= target; sum++) {

    for (int num : nums) {

        if (num <= sum) {
            dp[sum] += dp[sum - num];
        }
    }
}
```

Bây giờ:

```text
1 → 2
```

và:

```text
2 → 1
```

được count riêng.

Đây là một rule rất đáng nhớ:

| Bài toán    | Loop         |
| ----------- | ------------ |
| Combination | item outer   |
| Permutation | target outer |

---

# 8. Bounded Knapsack

Tiếp theo là:

> Mỗi item được dùng tối đa `count[i]` lần.

Ví dụ:

```text
item A:
weight = 2
value = 5
quantity = 3
```

Có thể lấy:

```text
0
1
2
hoặc 3 item A
```

State vẫn giống Knapsack:

```text
dp[capacity]
```

Transition conceptually:

```text
dp[c] =
max(
    dp[c],
    value * k
      + previous[c - weight * k]
)

k = 0 ... quantity
```

Bản đơn giản:

```java
for (int i = 0; i < n; i++) {

    for (int copy = 0; copy < count[i]; copy++) {

        for (
            int c = capacity;
            c >= weight[i];
            c--
        ) {

            dp[c] = Math.max(
                    dp[c],
                    value[i] + dp[c - weight[i]]
            );
        }
    }
}
```

Đây về bản chất biến:

```text
item quantity = 3
```

thành:

```text
3 item 0/1 độc lập
```

Có các optimization nâng cao như binary decomposition:

```text
13 copies
→ 1 + 2 + 4 + 6
```

nhưng interview phổ thông chưa cần học ngay.

---

# 9. Group Knapsack

Một biến thể rất hay:

> Có nhiều group, mỗi group chỉ được chọn tối đa một item.

Ví dụ:

```text
Laptop:
    A
    B
    C

Phone:
    D
    E

Watch:
    F
    G
```

Ta có budget `W`.

Mỗi category chỉ mua tối đa một sản phẩm.

Đây là:

```text
Group Knapsack
```

Template:

```java
for (List<Item> group : groups) {

    for (int c = capacity; c >= 0; c--) {

        for (Item item : group) {

            if (item.weight <= c) {

                dp[c] = Math.max(
                        dp[c],
                        dp[c - item.weight]
                            + item.value
                );
            }
        }
    }
}
```

Mental model:

```text
for each group
    choose:
        nothing
        item A
        item B
        item C
```

Ứng dụng:

* chọn một option mỗi category
* resource allocation
* chọn một project mỗi department
* chọn một configuration mỗi component

---

# 10. Pattern lớn tiếp theo: Linear DP

Đây là loại DP cơ bản nhất.

Các bài có dạng:

```text
answer tại i
phụ thuộc vào một vài position trước đó
```

Thường là:

```text
dp[i]
```

---

# 11. Fibonacci Pattern

Ví dụ:

```text
F(i) = F(i-1) + F(i-2)
```

DP:

```java
int[] dp = new int[n + 1];

dp[0] = 0;
dp[1] = 1;

for (int i = 2; i <= n; i++) {

    dp[i] =
        dp[i - 1]
        + dp[i - 2];
}
```

Đây là pattern:

```text
Prefix DP
```

Tức là:

> `dp[i]` mô tả lời giải cho prefix `[0...i]`.

---

# 12. House Robber — Linear Take / Skip

Đây là bài cực kỳ quan trọng để hiểu DP.

Cho:

```text
houses = [2,7,9,3,1]
```

Không được lấy hai nhà cạnh nhau.

Tại house `i`:

```text
skip i
→ dp[i-1]

take i
→ nums[i] + dp[i-2]
```

Vậy:

```text
dp[i]
=
max(
    dp[i-1],
    nums[i] + dp[i-2]
)
```

Java:

```java
class Solution {

    public int rob(int[] nums) {

        int n = nums.length;

        if (n == 1) {
            return nums[0];
        }

        int[] dp = new int[n];

        dp[0] = nums[0];

        dp[1] = Math.max(
                nums[0],
                nums[1]
        );

        for (int i = 2; i < n; i++) {

            dp[i] = Math.max(
                    dp[i - 1],
                    nums[i] + dp[i - 2]
            );
        }

        return dp[n - 1];
    }
}
```

Có thể optimize:

```java
int prev2 = 0;
int prev1 = 0;

for (int num : nums) {

    int current = Math.max(
            prev1,
            num + prev2
    );

    prev2 = prev1;
    prev1 = current;
}

return prev1;
```

---

# 13. Khi nào nhận ra Linear DP?

Nếu đề nói:

```text
đi từ trái sang phải
```

và decision tại `i` chỉ phụ thuộc:

```text
i-1
i-2
i-k
```

hãy nghĩ:

```text
dp[i]
```

Các bài:

* Climbing Stairs
* Min Cost Climbing Stairs
* House Robber
* Decode Ways
* Maximum Subarray
* Delete and Earn

---

# 14. Grid DP

Pattern tiếp theo rất phổ biến:

```text
matrix / grid
```

State thường là:

```text
dp[r][c]
```

=

> lời giải tốt nhất / số cách để đến cell `(r,c)`.

---

# 15. Unique Paths

Robot:

```text
(0,0)
```

đi đến:

```text
(m-1,n-1)
```

chỉ được:

```text
right
down
```

Muốn tới `(r,c)` chỉ có thể từ:

```text
(r-1,c)
hoặc
(r,c-1)
```

Vậy:

```text
dp[r][c]
=
dp[r-1][c]
+
dp[r][c-1]
```

Java:

```java
class Solution {

    public int uniquePaths(int m, int n) {

        int[][] dp = new int[m][n];

        for (int r = 0; r < m; r++) {
            dp[r][0] = 1;
        }

        for (int c = 0; c < n; c++) {
            dp[0][c] = 1;
        }

        for (int r = 1; r < m; r++) {

            for (int c = 1; c < n; c++) {

                dp[r][c] =
                        dp[r - 1][c]
                        + dp[r][c - 1];
            }
        }

        return dp[m - 1][n - 1];
    }
}
```

---

# 16. Grid DP cũng có các objective khác nhau

### Count paths

```text
+
```

```java
dp[r][c] =
    dp[r - 1][c]
    + dp[r][c - 1];
```

### Minimum cost

```text
min
```

```java
dp[r][c] =
    grid[r][c]
    + Math.min(
        dp[r - 1][c],
        dp[r][c - 1]
    );
```

### Maximum score

```text
max
```

```java
dp[r][c] =
    grid[r][c]
    + Math.max(
        dp[r - 1][c],
        dp[r][c - 1]
    );
```

Một lần nữa:

> State structure gần như giữ nguyên, chỉ objective thay đổi.

---

# 17. Template Grid DP

```java
for (int r = 0; r < rows; r++) {

    for (int c = 0; c < cols; c++) {

        dp[r][c] =
            combine(
                dp[r - 1][c],
                dp[r][c - 1]
            );
    }
}
```

Ứng dụng:

```text
Unique Paths
Minimum Path Sum
Maximum path
Obstacle grid
Dungeon Game
Cherry Pickup
Triangle
```

---

# 18. Two-Sequence DP — một pattern cực lớn

Khi đề cho:

```text
string A
string B
```

hoặc:

```text
array A
array B
```

và hỏi relationship giữa hai sequence, hãy nghĩ ngay:

```text
dp[i][j]
```

Thông thường:

> `dp[i][j]` = answer liên quan đến prefix đầu tiên dài `i` của A và prefix đầu tiên dài `j` của B.

---

# 19. Longest Common Subsequence

Ví dụ:

```text
text1 = "abcde"
text2 = "ace"
```

Answer:

```text
ace
length = 3
```

State:

```text
dp[i][j]
=
LCS length của
text1[0...i-1]
text2[0...j-1]
```

Nếu:

```text
text1[i-1] == text2[j-1]
```

ta match:

```text
dp[i][j]
=
1 + dp[i-1][j-1]
```

Nếu không match:

```text
bỏ char của text1
hoặc
bỏ char của text2
```

```text
dp[i][j]
=
max(
    dp[i-1][j],
    dp[i][j-1]
)
```

---

# 20. Template LCS

```java
class Solution {

    public int longestCommonSubsequence(
            String a,
            String b
    ) {

        int m = a.length();
        int n = b.length();

        int[][] dp =
                new int[m + 1][n + 1];

        for (int i = 1; i <= m; i++) {

            for (int j = 1; j <= n; j++) {

                if (
                    a.charAt(i - 1)
                    ==
                    b.charAt(j - 1)
                ) {

                    dp[i][j] =
                        1 + dp[i - 1][j - 1];

                } else {

                    dp[i][j] =
                        Math.max(
                            dp[i - 1][j],
                            dp[i][j - 1]
                        );
                }
            }
        }

        return dp[m][n];
    }
}
```

---

# 21. Mental model của Two-Sequence DP

Tại:

```text
(i,j)
```

hãy nghĩ:

```text
A[i]
B[j]
```

Ta thường có decisions:

```text
match chúng

skip A[i]

skip B[j]

replace / insert / delete
```

Chính vì vậy rất nhiều bài string có state:

```text
dp[i][j]
```

---

# 22. Edit Distance

Đây cũng là Two-Sequence DP.

Operations:

```text
insert
delete
replace
```

Nếu characters giống:

```text
dp[i][j]
=
dp[i-1][j-1]
```

Nếu khác:

```text
dp[i][j]
=
1 + min(
        dp[i-1][j],     // delete
        dp[i][j-1],     // insert
        dp[i-1][j-1]    // replace
    )
```

Java:

```java
class Solution {

    public int minDistance(
            String word1,
            String word2
    ) {

        int m = word1.length();
        int n = word2.length();

        int[][] dp =
                new int[m + 1][n + 1];

        for (int i = 0; i <= m; i++) {
            dp[i][0] = i;
        }

        for (int j = 0; j <= n; j++) {
            dp[0][j] = j;
        }

        for (int i = 1; i <= m; i++) {

            for (int j = 1; j <= n; j++) {

                if (
                    word1.charAt(i - 1)
                    ==
                    word2.charAt(j - 1)
                ) {

                    dp[i][j] =
                        dp[i - 1][j - 1];

                } else {

                    dp[i][j] =
                        1 + Math.min(
                            dp[i - 1][j - 1],
                            Math.min(
                                dp[i - 1][j],
                                dp[i][j - 1]
                            )
                        );
                }
            }
        }

        return dp[m][n];
    }
}
```

---

# 23. Những bài thuộc Two-Sequence DP

Rất nên luyện:

```text
1143 Longest Common Subsequence

72 Edit Distance

115 Distinct Subsequences

97 Interleaving String

583 Delete Operation for Two Strings

712 Minimum ASCII Delete Sum

1092 Shortest Common Supersequence
```

Pattern nhận diện:

> Hai sequence + quyết định match / skip / transform.

---

# 24. LIS Pattern — Subsequence DP

Một họ DP cực kỳ hay:

> Tìm subsequence tốt nhất kết thúc tại position `i`.

Ví dụ:

```text
nums = [10,9,2,5,3,7,101,18]
```

LIS:

```text
2,3,7,101
```

length:

```text
4
```

---

# 25. State của LIS

Định nghĩa:

```text
dp[i]
=
length của LIS
kết thúc CHÍNH XÁC tại i
```

Đây là điểm quan trọng.

Ta kiểm tra mọi `j < i`.

Nếu:

```text
nums[j] < nums[i]
```

thì có thể nối `nums[i]` vào subsequence kết thúc ở `j`.

Transition:

```text
dp[i]
=
max(
    dp[i],
    dp[j] + 1
)
```

---

# 26. LIS O(n²) template

```java
class Solution {

    public int lengthOfLIS(int[] nums) {

        int n = nums.length;

        int[] dp = new int[n];

        Arrays.fill(dp, 1);

        int answer = 1;

        for (int i = 0; i < n; i++) {

            for (int j = 0; j < i; j++) {

                if (nums[j] < nums[i]) {

                    dp[i] = Math.max(
                            dp[i],
                            dp[j] + 1
                    );
                }
            }

            answer =
                Math.max(answer, dp[i]);
        }

        return answer;
    }
}
```

---

# 27. Generic Subsequence DP template

Đây không chỉ dành cho increasing.

Generic:

```java
for (int i = 0; i < n; i++) {

    dp[i] = base;

    for (int j = 0; j < i; j++) {

        if (canExtend(j, i)) {

            dp[i] =
                combine(
                    dp[i],
                    dp[j] + contribution
                );
        }
    }
}
```

Các bài:

```text
Longest Increasing Subsequence

Longest String Chain

Largest Divisible Subset

Longest Arithmetic Subsequence

Russian Doll Envelopes
```

Điểm nhận diện:

> Ta muốn biết lời giải tốt nhất **kết thúc tại `i`**.

---

# 28. Interval DP

Đây là một level DP cao hơn.

Nếu bài hỏi:

```text
answer cho đoạn [l...r]
```

hãy nghĩ:

```text
dp[l][r]
```

Khác LCS ở chỗ hai dimension không phải hai array.

Chúng là:

```text
left boundary
right boundary
```

---

# 29. Tư tưởng Interval DP

State:

```text
dp[l][r]
=
best answer cho interval [l,r]
```

Ta thường thử:

```text
split interval tại k
```

Ví dụ:

```text
[l ........ r]

       k

[l ... k] + [k+1 ... r]
```

Transition generic:

```text
dp[l][r]
=
best over all k:
    combine(
        dp[l][k],
        dp[k+1][r]
    )
```

---

# 30. Thứ tự duyệt Interval DP

Đây là thứ quan trọng nhất.

Nếu muốn tính:

```text
dp[l][r]
```

thì các interval nhỏ hơn phải được biết trước.

Do đó loop theo:

```text
length = 1 → n
```

Template:

```java
for (int len = 2; len <= n; len++) {

    for (
        int left = 0;
        left + len - 1 < n;
        left++
    ) {

        int right =
                left + len - 1;

        for (
            int k = left;
            k < right;
            k++
        ) {

            dp[left][right] =
                combine(
                    dp[left][right],
                    dp[left][k],
                    dp[k + 1][right]
                );
        }
    }
}
```

---

# 31. Matrix Chain Multiplication

Classic Interval DP.

Có matrices:

```text
A B C D
```

Cần quyết định:

```text
((AB)C)D

(A(BC))D

A((BC)D)

A(B(CD))
...
```

Tại range `[l,r]`:

```text
thử vị trí split k
```

```text
dp[l][r]
=
min over k(
    dp[l][k]
    +
    dp[k+1][r]
    +
    cost(l,k,r)
)
```

Đây chính là interval DP canonical.

---

# 32. Burst Balloons

LeetCode 312 cũng là Interval DP rất điển hình.

Trick của bài:

Thay vì hỏi:

> balloon nào bắn đầu tiên?

hãy hỏi:

> balloon nào bắn **cuối cùng** trong interval?

Nếu `k` là balloon cuối:

```text
left interval đã xử lý
right interval đã xử lý
```

Transition:

```text
dp[l][r]
=
max(
    dp[l][k-1]
    +
    dp[k+1][r]
    +
    nums[l-1] * nums[k] * nums[r+1]
)
```

Đây là một tư duy DP rất quan trọng:

> Đôi khi lựa chọn "first action" làm dependency rối. Hãy thử chọn **last action**.

---

# 33. State Machine DP

Đây là pattern bạn đã gặp trong stock problems.

Thay vì:

```text
dp[index]
```

ta có:

```text
dp[index][state]
```

Ví dụ stock:

```text
state = NOT_HOLDING
state = HOLDING
```

Hoặc bài short selling của bạn:

```text
BEGIN
LONG
SHORT
```

---

# 34. Stock I dưới dạng State Machine

State:

```text
cash =
profit khi hiện tại không hold stock

hold =
profit khi hiện tại đang hold stock
```

Transitions:

```text
hold =
max(
    oldHold,
    oldCash - price
)

cash =
max(
    oldCash,
    oldHold + price
)
```

Đây là:

```text
state transition DP
```

---

# 35. Generic State Machine DP

Giả sử có `S` states.

```java
for (int i = 0; i < n; i++) {

    for (int state = 0; state < S; state++) {

        for (Transition t :
                transitions[state]) {

            dp[i][t.nextState] =
                Math.max(
                    dp[i][t.nextState],
                    dp[i - 1][state]
                    + t.value
                );
        }
    }
}
```

Mental model:

```text
state A
  ↓
state B
  ↓
state C
```

Vấn đề trở thành:

> Đang ở state nào và action hiện tại cho phép chuyển sang state nào?

---

# 36. Khi nào nghĩ tới State Machine DP?

Khi đề có trạng thái nghiệp vụ rõ:

```text
holding / not holding

cooldown / available

open / closed

buy / sell

long / short

inside / outside

previous action affects next action
```

Các bài stock là ví dụ tốt nhất:

```text
121 Best Time to Buy and Sell Stock

122 Stock II

123 Stock III

188 Stock IV

309 Cooldown

714 Transaction Fee
```

---

# 37. Tree DP

Tree DP không còn duyệt:

```text
i = 0 → n
```

mà dependency nằm theo cấu trúc tree.

State thường được tính bằng DFS.

---

# 38. House Robber III

Tree:

```text
        3
       / \
      2   3
       \   \
        3   1
```

Không được rob:

```text
parent và child cùng lúc
```

Tại mỗi node ta cần hai state:

```text
rob node

skip node
```

DFS trả về:

```text
[skip, take]
```

Nếu take node:

```text
take =
node.val
+ skip(left)
+ skip(right)
```

Nếu skip:

```text
skip =
max(left)
+
max(right)
```

---

# 39. Tree DP Java template

```java
class Solution {

    public int rob(TreeNode root) {

        int[] result = dfs(root);

        return Math.max(
                result[0],
                result[1]
        );
    }

    // [skip, take]
    private int[] dfs(TreeNode node) {

        if (node == null) {
            return new int[]{0, 0};
        }

        int[] left = dfs(node.left);
        int[] right = dfs(node.right);

        int take =
                node.val
                + left[0]
                + right[0];

        int skip =
                Math.max(left[0], left[1])
                +
                Math.max(right[0], right[1]);

        return new int[]{
                skip,
                take
        };
    }
}
```

Đây là pattern cực kỳ đẹp:

```text
child state
       ↓
combine
       ↓
parent state
```

---

# 40. Tree DP generic template

```java
State dfs(Node node) {

    if (node == null) {
        return baseState;
    }

    State left = dfs(node.left);
    State right = dfs(node.right);

    return combine(
        node,
        left,
        right
    );
}
```

Các bài:

* House Robber III
* Binary Tree Cameras
* Maximum Path Sum
* Longest Path
* Tree matching
* subtree optimization

---

# 41. DAG DP

Tree DP thực ra là một trường hợp đặc biệt của:

```text
DP trên graph không có cycle
```

Nếu graph là DAG:

```text
A → B → C
  ↘ D
```

Ta có thể:

```text
topological sort
```

rồi propagate DP.

Ví dụ longest path:

```java
for (int u : topoOrder) {

    for (Edge edge : graph[u]) {

        int v = edge.to;

        dp[v] = Math.max(
                dp[v],
                dp[u] + edge.weight
        );
    }
}
```

Điểm nhận diện:

> State có dependency theo directed acyclic graph.

---

# 42. Bitmask DP

Đây là advanced DP nhưng cực quan trọng nếu:

```text
n nhỏ, khoảng 15–20
```

và state phải nhớ:

> Những item nào đã được sử dụng?

Trong Knapsack, ta chỉ cần:

```text
index
capacity
```

Nhưng đôi khi thứ tự chọn linh hoạt khiến `index` không đủ.

Ta encode subset bằng bitmask.

Ví dụ:

```text
4 cities

mask = 0101
```

có nghĩa:

```text
city 0 visited
city 2 visited
```

---

# 43. Traveling Salesman DP

State:

```text
dp[mask][last]
```

=

> minimum cost để visit tập `mask` và hiện tại đang ở `last`.

Transition:

```text
nextMask =
mask | (1 << next)
```

```text
dp[nextMask][next]
=
min(
    dp[nextMask][next],

    dp[mask][last]
    + cost[last][next]
)
```

---

# 44. Bitmask DP skeleton

```java
int totalMasks = 1 << n;

int[][] dp =
        new int[totalMasks][n];

for (int[] row : dp) {
    Arrays.fill(row, INF);
}

dp[1][0] = 0;

for (int mask = 0; mask < totalMasks; mask++) {

    for (int last = 0; last < n; last++) {

        if ((mask & (1 << last)) == 0) {
            continue;
        }

        for (int next = 0; next < n; next++) {

            if ((mask & (1 << next)) != 0) {
                continue;
            }

            int nextMask =
                    mask | (1 << next);

            dp[nextMask][next] =
                    Math.min(
                        dp[nextMask][next],
                        dp[mask][last]
                            + cost[last][next]
                    );
        }
    }
}
```

Complexity:

```text
O(2^n * n²)
```

Nghe lớn, nhưng khi:

```text
n <= 15
```

thì đôi khi hoàn toàn hợp lý.

---

# 45. Digit DP

Đây là advanced pattern khác.

Ví dụ:

> Có bao nhiêu số từ `0 → N` không chứa hai chữ số `1` liên tiếp?

Không thể iterate đến:

```text
N = 10^18
```

Ta xử lý từng digit.

State thường:

```text
dp[position][tight][otherState]
```

Trong đó:

```text
position
=
đang xét digit nào

tight
=
prefix hiện tại có bằng prefix của N không?

otherState
=
thông tin bài cần nhớ
```

Ví dụ:

```text
previous digit
number of non-zero digits
digit sum
has repeated digit?
```

Đây là pattern chuyên sâu hơn, thường dành cho competitive programming hơn interview thông thường.

---

# 46. Một cách phân loại DP dễ nhớ hơn

Thay vì nhớ 10 cái tên, hãy nhìn vào câu hỏi:

## Dạng 1 — Tôi đang đứng ở position nào?

```text
dp[i]
```

→ Linear DP.

---

## Dạng 2 — Tôi đã dùng bao nhiêu resource?

```text
dp[capacity]
```

→ Knapsack.

---

## Dạng 3 — Tôi đang đứng ở cell nào?

```text
dp[r][c]
```

→ Grid DP.

---

## Dạng 4 — Tôi đang ở position nào trong hai sequence?

```text
dp[i][j]
```

→ LCS / String DP.

---

## Dạng 5 — Subsequence tốt nhất kết thúc ở đâu?

```text
dp[i]
```

→ LIS family.

---

## Dạng 6 — Tôi đang xử lý interval nào?

```text
dp[l][r]
```

→ Interval DP.

---

## Dạng 7 — Hiện tại tôi đang ở trạng thái nghiệp vụ nào?

```text
dp[i][state]
```

→ State Machine DP.

---

## Dạng 8 — Với subtree này tôi có những trạng thái nào?

```text
dfs(node) → states
```

→ Tree DP.

---

## Dạng 9 — Tôi đã chọn những object nào?

```text
dp[mask]
```

→ Bitmask DP.

---

# 47. Đây mới là kỹ năng quan trọng nhất: thiết kế state

Khi gặp bài DP mới, đừng hỏi ngay:

> Dùng công thức gì?

Hãy hỏi:

> Để đưa ra quyết định tiếp theo, tôi cần biết những thông tin gì từ quá khứ?

Ví dụ Knapsack:

```text
cần biết:
item nào đang xét
capacity còn lại

→ dfs(i, capacity)
```

Stock:

```text
cần biết:
ngày nào
đang holding hay không
còn bao nhiêu transaction

→ dfs(day, transactions, state)
```

LCS:

```text
cần biết:
đang ở đâu trong string A
đang ở đâu trong string B

→ dfs(i,j)
```

Interval:

```text
cần biết:
range hiện tại

→ dfs(left,right)
```

Bitmask:

```text
cần biết:
những item nào đã được sử dụng

→ dfs(mask,...)
```

Đây chính là **state design**.

---

# 48. Một quy trình chuẩn để giải bất kỳ bài DP nào

Mình khuyên bạn dùng framework sau.

## Step 1 — Viết brute-force decision

Hỏi:

```text
Tại state này tôi có những choices nào?
```

Ví dụ:

```text
take / skip
match / skip
move right / down
buy / sell / wait
split at k
```

---

## Step 2 — Xác định state

Hỏi:

> Những biến nào quyết định toàn bộ tương lai?

Ví dụ:

```text
dfs(i)

dfs(i, capacity)

dfs(i, j)

dfs(left, right)

dfs(i, state)

dfs(node)

dfs(mask, last)
```

---

## Step 3 — Viết recurrence

Ví dụ:

```text
Knapsack:

f(i,c)
=
max(
    f(i+1,c),
    value[i] + f(i+1,c-weight[i])
)
```

LCS:

```text
if equal:
    1 + f(i+1,j+1)

else:
    max(
        f(i+1,j),
        f(i,j+1)
    )
```

---

# 49. Step 4 — Base case

Ví dụ Knapsack:

```text
i == n
→ 0
```

Subset Sum:

```text
target == 0
→ true
```

LCS:

```text
i == m || j == n
→ 0
```

Tree:

```text
node == null
→ base
```

---

# 50. Step 5 — Memoization

Nếu state là:

```text
(i, capacity)
```

thì memo:

```java
Integer[][] memo;
```

Nếu:

```text
(i,j)
```

thì:

```java
Integer[][] memo;
```

Nếu:

```text
(i,state,k)
```

thì:

```java
Long[][][] memo;
```

---

# 51. Step 6 — Tính complexity bằng số lượng state

Đây là một rule rất mạnh:

> DP complexity ≈ `number of states × transitions per state`.

Knapsack:

```text
states =
n * W

transition =
O(1)

→ O(nW)
```

LCS:

```text
states =
m * n

transition =
O(1)

→ O(mn)
```

Interval:

```text
states =
n²

mỗi state thử n split

→ O(n³)
```

Bitmask TSP:

```text
states =
2^n * n

mỗi state thử n next

→ O(2^n * n²)
```

---

# 52. Step 7 — Chuyển sang bottom-up

Quan sát dependency.

Ví dụ:

```text
dp[i]
needs
dp[i-1], dp[i-2]

→ left to right
```

Knapsack:

```text
dp[c]
needs previous item

0/1:
right → left

Unbounded:
left → right
```

Interval:

```text
large interval
needs smaller intervals

→ length nhỏ → lớn
```

Đây chính là cách xác định **loop order**, không phải học thuộc.

---

# 53. Một bảng cheat sheet rất quan trọng

| Pattern            | State điển hình      | Transition         |
| ------------------ | -------------------- | ------------------ |
| Linear DP          | `dp[i]`              | từ `i-1`, `i-2`    |
| 0/1 Knapsack       | `dp[c]`              | take/skip, loop ↓  |
| Unbounded Knapsack | `dp[c]`              | reuse, loop ↑      |
| Grid DP            | `dp[r][c]`           | top/left/...       |
| LCS/String DP      | `dp[i][j]`           | match/skip         |
| LIS                | `dp[i]`              | thử `j < i`        |
| Interval DP        | `dp[l][r]`           | split `k`          |
| State Machine      | `dp[i][state]`       | state transition   |
| Tree DP            | `dfs(node) → states` | combine children   |
| Bitmask DP         | `dp[mask][...]`      | add unvisited item |

---

# 54. Cách phân biệt các pattern hay nhầm

## Knapsack vs LIS

Knapsack:

```text
constraint theo tổng resource

weight
capacity
budget
sum
```

LIS:

```text
constraint theo relationship giữa items

nums[j] < nums[i]
```

---

## LCS vs Interval DP

LCS:

```text
dp[i][j]
```

nhưng:

```text
i thuộc sequence A
j thuộc sequence B
```

Interval:

```text
dp[l][r]
```

cả hai đều thuộc:

```text
cùng một sequence
```

---

## Linear DP vs State Machine DP

Linear:

```text
dp[i]
```

quá khứ được summarize chỉ bằng answer.

State Machine:

```text
dp[i][state]
```

cùng một position nhưng có nhiều trạng thái khác nhau.

Ví dụ:

```text
day 5 holding stock
```

không giống:

```text
day 5 not holding stock
```

---

# 55. Một meta-template rất hữu ích

Nhiều DP thực chất đều có cấu trúc:

```text
state
    ↓
enumerate choices
    ↓
nextState
    ↓
combine answers
```

Pseudo-code:

```java
Result solve(State state) {

    if (baseCase(state)) {
        return baseResult;
    }

    Result answer = initialValue;

    for (Choice choice :
            possibleChoices(state)) {

        State next =
                transition(state, choice);

        Result candidate =
                contribution(choice)
                + solve(next);

        answer =
                combine(
                    answer,
                    candidate
                );
    }

    return answer;
}
```

Trong đó `combine` có thể là:

```text
max
min
sum
OR
AND
```

Ví dụ:

### Maximum

```java
answer = Math.max(answer, candidate);
```

### Minimum

```java
answer = Math.min(answer, candidate);
```

### Count ways

```java
answer += candidate;
```

### Feasibility

```java
answer |= candidate;
```

Đây mới là abstraction lớn nhất của DP.

---

# 56. Ví dụ map các bài nổi tiếng vào pattern

| Problem                        | Pattern                 |
| ------------------------------ | ----------------------- |
| Climbing Stairs                | Linear DP               |
| House Robber                   | Linear Take/Skip        |
| Partition Equal Subset Sum     | 0/1 Knapsack            |
| Target Sum                     | Count Subset / 0/1      |
| Coin Change                    | Unbounded Knapsack      |
| Coin Change II                 | Unbounded + Combination |
| Combination Sum IV             | Unbounded + Permutation |
| Unique Paths                   | Grid DP                 |
| Minimum Path Sum               | Grid DP                 |
| Longest Common Subsequence     | Two-sequence DP         |
| Edit Distance                  | Two-sequence DP         |
| Interleaving String            | Two-sequence DP         |
| Longest Increasing Subsequence | Subsequence DP          |
| Burst Balloons                 | Interval DP             |
| Stock IV                       | State Machine DP        |
| House Robber III               | Tree DP                 |
| Traveling Salesman             | Bitmask DP              |

---

# 57. Roadmap mình khuyên bạn học tiếp

Sau 0/1 Knapsack, đừng nhảy ngay vào Digit DP. Hãy đi như này:

### Phase 1 — DP nền tảng

```text
70 Climbing Stairs
198 House Robber
213 House Robber II
746 Min Cost Climbing Stairs
```

Mục tiêu:

```text
dp[i]
take / skip
space optimization
```

---

### Phase 2 — Knapsack family

Bạn đang ở đây.

```text
416 Partition Equal Subset Sum
494 Target Sum
1049 Last Stone Weight II

322 Coin Change
518 Coin Change II
377 Combination Sum IV

474 Ones and Zeroes
```

Mục tiêu:

```text
0/1 vs unbounded
max/min/count/boolean
combination vs permutation
loop direction
```

---

### Phase 3 — Grid DP

```text
62 Unique Paths
63 Unique Paths II
64 Minimum Path Sum
120 Triangle
931 Minimum Falling Path Sum
```

Mục tiêu:

```text
dp[r][c]
dependency direction
2D → 1D optimization
```

---

### Phase 4 — String DP

```text
1143 LCS
72 Edit Distance
97 Interleaving String
115 Distinct Subsequences
583 Delete Operation for Two Strings
```

Mục tiêu:

```text
dp[i][j]
match / skip
prefix state
```

---

### Phase 5 — LIS family

```text
300 LIS
368 Largest Divisible Subset
646 Maximum Length of Pair Chain
673 Number of LIS
354 Russian Doll Envelopes
```

Mục tiêu:

```text
dp[i] = best ending at i
```

---

### Phase 6 — State Machine DP

Vì bạn đã học stock, nhóm này đặc biệt phù hợp.

```text
121 Stock I
122 Stock II
123 Stock III
188 Stock IV
309 Cooldown
714 Fee
```

Mục tiêu:

```text
state transition
holding/not holding
transaction count
```

---

### Phase 7 — Interval / Tree / Bitmask

Sau khi các pattern trên vững:

```text
312 Burst Balloons
1039 Minimum Score Triangulation
337 House Robber III
124 Binary Tree Maximum Path Sum
847 Shortest Path Visiting All Nodes
```

---

# 58. Nếu gom toàn bộ DP thành một tư duy duy nhất

Hãy nhớ câu này:

> **DP = brute force trên một state graph, sau đó cache những state lặp lại.**

Ví dụ Knapsack:

```text
(i, capacity)
```

là một node.

LCS:

```text
(i,j)
```

là một node.

Stock:

```text
(day, holding, transaction)
```

là một node.

Interval:

```text
(left,right)
```

là một node.

Bitmask:

```text
(mask,last)
```

là một node.

Transitions chính là các choices.

```text
state
   │
   ├── choice 1 → state A
   ├── choice 2 → state B
   └── choice 3 → state C
```

Nếu graph state này có overlapping subproblems, bạn có DP.

---

# 59. Checklist khi gặp bài DP mới

Bạn có thể dùng checklist này trong interview:

```text
1. Brute force choice là gì?

2. State tối thiểu cần nhớ là gì?

3. dfs(...) nhận những parameters nào?

4. Base case là gì?

5. Transition là gì?

6. Có overlapping states không?

7. Có bao nhiêu states?

8. Mỗi state thử bao nhiêu transitions?

9. Memoization complexity là bao nhiêu?

10. Bottom-up dependency hướng nào?

11. Có optimize dimension được không?
```

Nếu trả lời được 11 câu này, thường bạn đã giải được phần lớn bài DP.

---

# 60. Mental map cuối cùng nên ghi nhớ

```text
                     DP
                     │
       ┌─────────────┼─────────────┐
       │             │             │
     Position      Resource      Structure
       │             │             │
     dp[i]         dp[c]         Tree/DAG
       │             │
   Linear DP      Knapsack
                     │
              ┌──────┼──────┐
              │      │      │
             0/1  Unbounded Bounded


Two sequences
      │
   dp[i][j]
      │
  LCS/Edit Distance


One sequence + relationship
      │
    dp[i]
      │
     LIS


One sequence + range
      │
   dp[l][r]
      │
 Interval DP


Position + mode/status
      │
 dp[i][state]
      │
State Machine


Need remember selected set
      │
   dp[mask]
      │
 Bitmask DP
```

Nếu học DP theo bản đồ này, bạn sẽ dần chuyển từ trạng thái **“bài này dùng công thức DP nào?”** sang:

```text
Đề đang cho mình những decisions gì?

Để mô tả tương lai,
state cần chứa thông tin gì?

Dependency giữa các state là gì?

→ recurrence tự xuất hiện.
```

Đó là mức hiểu DP cần hướng tới khi làm coding interview.
