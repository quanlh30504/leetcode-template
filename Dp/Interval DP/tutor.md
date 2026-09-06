# Interval DP

## 1. Interval DP là gì?

**Interval DP** là Dynamic Programming trong đó mỗi trạng thái đại diện cho đáp án của **một đoạn liên tiếp**.

Thông thường:

```text
dp[l][r]
```

có nghĩa:

> Đáp án tốt nhất cho đoạn từ index `l` đến index `r`.

Ví dụ:

```text
array = [a, b, c, d, e]

dp[1][3]
```

đại diện cho bài toán con trên:

```text
[b, c, d]
```

Điểm đặc biệt của Interval DP là:

```text
bài toán lớn [l ... r]
        ↓
được xây dựng từ
        ↓
các interval nhỏ hơn
```

Ví dụ:

```text
[l ................ r]

      split tại k

[l .... k] [k+1 .... r]
```

Hoặc:

```text
[l ................ r]

bỏ l
   [l+1 ........... r]

bỏ r
[l ........... r-1]

bỏ cả hai
   [l+1 ..... r-1]
```

Đây chính là lý do chúng ta gọi nó là **Interval DP**.

---

# 2. Khi nào nên nghĩ đến Interval DP?

Một dấu hiệu rất mạnh là đề bài hỏi về:

```text
subarray
substring
segment
range
interval
```

và thao tác trên một đoạn có thể được suy ra từ các đoạn nhỏ hơn.

Một số keyword thường gặp:

| Problem characteristic           | Khả năng |
| -------------------------------- | -------: |
| Remove characters/items          |      Cao |
| Merge items/piles                |      Cao |
| Split một đoạn                   |  Rất cao |
| Choose left/right                |      Cao |
| Choose pivot `k`                 |  Rất cao |
| Palindrome                       |      Cao |
| Polygon triangulation            |  Rất cao |
| Matrix multiplication order      |  Rất cao |
| Cut a stick                      |  Rất cao |
| Game trên `[l,r]`                |      Cao |
| Burst / remove items theo thứ tự |  Rất cao |

Một pattern đặc biệt quan trọng:

```text
"What order should we perform operations?"
```

Ví dụ:

```text
burst balloons
merge stones
cut stick
remove characters
triangulate polygon
```

Khi **thứ tự thực hiện operation ảnh hưởng kết quả**, Interval DP thường là ứng viên mạnh.

---

# 3. Tư duy cốt lõi

Hầu hết Interval DP có dạng:

```text
dp[l][r]
```

Sau đó ta phải trả lời 3 câu hỏi.

### Câu 1: State nghĩa là gì?

Ví dụ:

```text
dp[l][r]
= maximum score obtainable from interval [l,r]
```

hoặc:

```text
dp[l][r]
= minimum cost to completely process interval [l,r]
```

hoặc:

```text
dp[l][r]
= length of longest palindromic subsequence inside [l,r]
```

State phải được định nghĩa cực kỳ chính xác.

---

# 4. Dependency của Interval DP

Ví dụ:

```text
dp[l][r]
```

có thể phụ thuộc vào:

```text
dp[l+1][r]
dp[l][r-1]
dp[l+1][r-1]
```

hoặc:

```text
dp[l][k]
dp[k+1][r]
```

Ta có:

```text
        dp[l][r]
       /        \
 dp[l][k]    dp[k+1][r]
```

Do đó interval nhỏ phải được tính trước interval lớn.

Đây là lý do Bottom-up Interval DP thường duyệt theo:

```text
length = 1
length = 2
length = 3
...
length = n
```

---

# 5. Template cơ bản nhất

## Bottom-up

```java
for (int len = 1; len <= n; len++) {

    for (int left = 0; left + len - 1 < n; left++) {

        int right = left + len - 1;

        // calculate dp[left][right]
    }
}
```

Ví dụ:

```text
n = 5
```

Ta sẽ tính:

```text
length = 1

[0,0]
[1,1]
[2,2]
[3,3]
[4,4]

length = 2

[0,1]
[1,2]
[2,3]
[3,4]

length = 3

[0,2]
[1,3]
[2,4]

length = 4

[0,3]
[1,4]

length = 5

[0,4]
```

Đây là thứ tự cực kỳ tự nhiên cho Interval DP.

---

# 6. Template Split Interval

Một trong những recurrence phổ biến nhất:

```text
dp[l][r]
=
best over every split point k
```

Ví dụ:

```text
[l ........ k][k+1 ......... r]
```

Ta có:

```text
dp[l][r]
=
min/max(
    dp[l][k]
    +
    dp[k+1][r]
    +
    cost(...)
)
```

Template:

```java
for (int len = 2; len <= n; len++) {

    for (int left = 0; left + len - 1 < n; left++) {

        int right = left + len - 1;

        dp[left][right] = INF;

        for (int k = left; k < right; k++) {

            dp[left][right] = Math.min(
                dp[left][right],
                dp[left][k]
                + dp[k + 1][right]
                + cost(left, k, right)
            );
        }
    }
}
```

Complexity thường là:

```text
Number of intervals: O(n²)

For every interval:
    try O(n) split points

Total:
    O(n³)

Space:
    O(n²)
```

Đây là complexity cực kỳ phổ biến của Interval DP.

---

# 7. Pattern 1 — Shrink From Ends

Đây là dạng đơn giản nhất.

Ta xét:

```text
[l ............ r]
```

và quyết định liên quan đến:

```text
left
right
```

Recurrence thường giống:

```text
dp[l][r]
=
f(
    dp[l+1][r],
    dp[l][r-1],
    dp[l+1][r-1]
)
```

Ví dụ kinh điển:

# LeetCode 516 — Longest Palindromic Subsequence

Cho:

```text
s = "bbbab"
```

Ta định nghĩa:

```text
dp[l][r]
=
length của longest palindromic subsequence
trong s[l...r]
```

Nếu:

```text
s[l] == s[r]
```

hai character có thể trở thành hai đầu palindrome:

```text
dp[l][r]
=
dp[l+1][r-1] + 2
```

Nếu:

```text
s[l] != s[r]
```

ít nhất một trong hai không nằm trong optimal solution:

```text
dp[l][r]
=
max(
    dp[l+1][r],
    dp[l][r-1]
)
```

Recurrence:

```text
if s[l] == s[r]:

    dp[l][r] = dp[l+1][r-1] + 2

else:

    dp[l][r] =
        max(
            dp[l+1][r],
            dp[l][r-1]
        )
```

Base case:

```text
dp[i][i] = 1
```

Java:

```java
class Solution {

    public int longestPalindromeSubseq(String s) {

        int n = s.length();

        int[][] dp = new int[n][n];

        for (int i = 0; i < n; i++) {
            dp[i][i] = 1;
        }

        for (int len = 2; len <= n; len++) {

            for (
                int left = 0;
                left + len - 1 < n;
                left++
            ) {

                int right = left + len - 1;

                if (
                    s.charAt(left)
                    == s.charAt(right)
                ) {

                    dp[left][right] =
                        len == 2
                        ? 2
                        : dp[left + 1][right - 1] + 2;

                } else {

                    dp[left][right] =
                        Math.max(
                            dp[left + 1][right],
                            dp[left][right - 1]
                        );
                }
            }
        }

        return dp[0][n - 1];
    }
}
```

---

# 8. Pattern 2 — Game Interval DP

Một pattern rất phổ biến:

```text
players pick from left or right
```

Ví dụ:

```text
[1, 5, 233, 7]
```

Player có thể lấy:

```text
1

hoặc

7
```

Sau đó bài toán trở thành interval nhỏ hơn.

Ví dụ:

# LeetCode 486 — Predict the Winner

Một cách define state rất đẹp:

```text
dp[l][r]
=
maximum score difference
current player can obtain
against opponent
```

Nếu chọn `nums[l]`:

```text
nums[l] - dp[l+1][r]
```

Tại sao lại trừ?

Bởi vì:

```text
dp[l+1][r]
```

là advantage mà opponent đạt được trong lượt tiếp theo.

Tương tự nếu chọn right:

```text
nums[r] - dp[l][r-1]
```

Recurrence:

```text
dp[l][r]
=
max(
    nums[l] - dp[l+1][r],
    nums[r] - dp[l][r-1]
)
```

Code:

```java
class Solution {

    public boolean predictTheWinner(int[] nums) {

        int n = nums.length;

        int[][] dp = new int[n][n];

        for (int i = 0; i < n; i++) {
            dp[i][i] = nums[i];
        }

        for (int len = 2; len <= n; len++) {

            for (
                int left = 0;
                left + len - 1 < n;
                left++
            ) {

                int right = left + len - 1;

                int chooseLeft =
                    nums[left]
                    - dp[left + 1][right];

                int chooseRight =
                    nums[right]
                    - dp[left][right - 1];

                dp[left][right] =
                    Math.max(
                        chooseLeft,
                        chooseRight
                    );
            }
        }

        return dp[0][n - 1] >= 0;
    }
}
```

Đây là một template rất nên nhớ:

```text
scoreDifference
=
myScore - opponentBestScore
```

---

# 9. Pattern 3 — Choose a Pivot

Đây là dạng quan trọng nhất của Interval DP.

Cho:

```text
[l ................ r]
```

Ta chọn:

```text
k
```

sau đó bài toán chia thành hai phần.

```text
       k
       ↓
[l ................. r]

[l ... k]     [k ... r]
```

Một recurrence thường có dạng:

```text
dp[l][r]
=
min/max over k {
    dp[l][k]
    +
    dp[k][r]
    +
    cost(l,k,r)
}
```

---

# 10. Ví dụ kinh điển — Minimum Score Triangulation

## LeetCode 1039

Ta có polygon:

```text
0 ----- 1
|      /
|    /
|  /
3 ----- 2
```

Ta muốn triangulate polygon.

Giả sử xét interval:

```text
i ........ j
```

Nếu chọn vertex:

```text
k
```

ta tạo triangle:

```text
(i, k, j)
```

và polygon được chia thành:

```text
[i ... k]

[k ... j]
```

Do đó:

```text
dp[i][j]
=
min(
    dp[i][k]
    +
    dp[k][j]
    +
    values[i] * values[k] * values[j]
)
```

với:

```text
i < k < j
```

Java:

```java
class Solution {

    public int minScoreTriangulation(int[] values) {

        int n = values.length;

        int[][] dp = new int[n][n];

        for (int len = 3; len <= n; len++) {

            for (
                int left = 0;
                left + len - 1 < n;
                left++
            ) {

                int right = left + len - 1;

                dp[left][right] =
                    Integer.MAX_VALUE;

                for (
                    int k = left + 1;
                    k < right;
                    k++
                ) {

                    int score =
                        dp[left][k]
                        + dp[k][right]
                        + values[left]
                        * values[k]
                        * values[right];

                    dp[left][right] =
                        Math.min(
                            dp[left][right],
                            score
                        );
                }
            }
        }

        return dp[0][n - 1];
    }
}
```

Complexity:

```text
Time  O(n³)
Space O(n²)
```

---

# 11. Pattern cực kỳ quan trọng — Think About the LAST Operation

Đây là tư tưởng giúp giải rất nhiều bài Interval DP khó.

Giả sử đề hỏi:

> Ta nên thực hiện các operation theo thứ tự nào?

Thay vì hỏi:

```text
Operation đầu tiên là gì?
```

hãy thử hỏi:

```text
Operation CUỐI CÙNG là gì?
```

Đây chính là trick của:

# LeetCode 312 — Burst Balloons

---

# 12. Burst Balloons

Ta có:

```text
nums = [3,1,5,8]
```

Nếu burst balloon `k`, coins:

```text
leftValue * nums[k] * rightValue
```

Vấn đề:

```text
neighbors thay đổi sau mỗi lần burst
```

Nếu hỏi:

```text
balloon nào burst FIRST?
```

thì rất khó.

Vì ta không biết hàng xóm của nó sau này sẽ như thế nào.

Nhưng hãy đổi câu hỏi:

```text
Trong interval [left,right],
balloon nào được burst LAST?
```

Giả sử:

```text
k
```

là balloon cuối cùng.

Khi `k` được burst cuối cùng:

```text
left-1
```

và:

```text
right+1
```

chắc chắn là hai neighbor của nó.

Do toàn bộ balloon giữa:

```text
left ... k-1
```

và:

```text
k+1 ... right
```

đã biến mất.

Ta có:

```text
          k burst last
              ↓

[left .... k .... right]

   ↓                 ↓

[left,k-1]      [k+1,right]
```

Hai bài toán con trở nên **independent**.

Đây chính là dấu hiệu cực mạnh của DP.

---

# 13. State Burst Balloons

Thêm boundary:

```text
1 [3,1,5,8] 1
```

Define:

```text
dp[left][right]
=
maximum coins obtainable
by bursting all balloons
inside [left,right]
```

Nếu `k` burst cuối:

```text
coins =
dp[left][k-1]
+
nums[left-1] * nums[k] * nums[right+1]
+
dp[k+1][right]
```

Do đó:

```text
dp[left][right]
=
max over k
```

Java:

```java
class Solution {

    public int maxCoins(int[] nums) {

        int n = nums.length;

        int[] arr = new int[n + 2];

        arr[0] = 1;
        arr[n + 1] = 1;

        for (int i = 0; i < n; i++) {
            arr[i + 1] = nums[i];
        }

        int[][] dp = new int[n + 2][n + 2];

        for (int len = 1; len <= n; len++) {

            for (
                int left = 1;
                left + len - 1 <= n;
                left++
            ) {

                int right = left + len - 1;

                for (
                    int k = left;
                    k <= right;
                    k++
                ) {

                    int coins =
                        dp[left][k - 1]
                        +
                        arr[left - 1]
                        * arr[k]
                        * arr[right + 1]
                        +
                        dp[k + 1][right];

                    dp[left][right] =
                        Math.max(
                            dp[left][right],
                            coins
                        );
                }
            }
        }

        return dp[1][n];
    }
}
```

Đây là một trong những bài quan trọng nhất để hiểu Interval DP.

Nếu hiểu được tư tưởng:

```text
choose the LAST operation
```

thì rất nhiều Interval DP Hard trở nên dễ hiểu hơn.

---

# 14. Pattern 4 — Cutting Interval

Một bài kinh điển khác:

# LeetCode 1547 — Minimum Cost to Cut a Stick

Cho:

```text
stick length = 7

cuts = [1,3,4,5]
```

Ta có:

```text
0---1-------3---4---5-------7
```

Mỗi lần cắt, cost bằng chiều dài đoạn stick hiện tại.

Điều làm bài toán khó là:

```text
thứ tự cut ảnh hưởng tổng cost
```

Ví dụ:

```text
cut 3 first

vs

cut 1 first
```

có thể tạo tổng cost khác nhau.

Đây chính là dấu hiệu:

```text
ORDER MATTERS
```

→ nghĩ Interval DP.

---

# 15. Thêm Boundary

Ta transform:

```text
cuts =
[0,1,3,4,5,7]
```

Define:

```text
dp[i][j]
=
minimum cost
để thực hiện toàn bộ cut
nằm giữa cuts[i] và cuts[j]
```

Nếu không có cut ở giữa:

```text
j == i + 1
```

thì:

```text
dp[i][j] = 0
```

Nếu chọn:

```text
k
```

làm cut đầu tiên trong interval:

```text
cuts[i] -------- cuts[k] -------- cuts[j]
```

Cost hiện tại:

```text
cuts[j] - cuts[i]
```

Sau đó có hai subproblem:

```text
dp[i][k]

dp[k][j]
```

Recurrence:

```text
dp[i][j]
=
min {
    dp[i][k]
    +
    dp[k][j]
    +
    cuts[j] - cuts[i]
}
```

---

# 16. Java Implementation

```java
class Solution {

    public int minCost(
        int n,
        int[] cuts
    ) {

        int m = cuts.length;

        int[] positions =
            new int[m + 2];

        positions[0] = 0;
        positions[m + 1] = n;

        for (int i = 0; i < m; i++) {
            positions[i + 1] = cuts[i];
        }

        Arrays.sort(positions);

        int size = m + 2;

        int[][] dp =
            new int[size][size];

        for (int len = 2; len < size; len++) {

            for (
                int left = 0;
                left + len < size;
                left++
            ) {

                int right = left + len;

                dp[left][right] =
                    Integer.MAX_VALUE;

                for (
                    int k = left + 1;
                    k < right;
                    k++
                ) {

                    int cost =
                        dp[left][k]
                        +
                        dp[k][right]
                        +
                        positions[right]
                        - positions[left];

                    dp[left][right] =
                        Math.min(
                            dp[left][right],
                            cost
                        );
                }

                if (
                    right == left + 1
                ) {
                    dp[left][right] = 0;
                }
            }
        }

        return dp[0][size - 1];
    }
}
```

Một cách viết sạch hơn là bắt đầu với interval có ít nhất một cut:

```java
for (int gap = 2; gap < size; gap++) {

    for (
        int left = 0;
        left + gap < size;
        left++
    ) {

        int right = left + gap;

        dp[left][right] =
            Integer.MAX_VALUE;

        for (
            int k = left + 1;
            k < right;
            k++
        ) {

            dp[left][right] =
                Math.min(
                    dp[left][right],
                    dp[left][k]
                    +
                    dp[k][right]
                    +
                    positions[right]
                    - positions[left]
                );
        }
    }
}
```

Các state:

```text
dp[i][i+1]
```

mặc định bằng `0`.

---

# 17. Một observation cực kỳ quan trọng

Ở Burst Balloons ta chọn:

```text
LAST operation
```

Trong Cut Stick ta chọn:

```text
FIRST operation
```

Tại sao?

Không có luật bắt buộc Interval DP phải chọn first hay last.

Mục tiêu thực sự là:

> Chọn cách nhìn khiến sau decision hiện tại, bài toán được chia thành các subproblem độc lập.

Đây mới là tư tưởng cốt lõi.

---

# 18. Pattern 5 — Minimax + Pivot

Ví dụ:

# LeetCode 375 — Guess Number Higher or Lower II

Ta cần guess số trong:

```text
[left,right]
```

Nếu chọn:

```text
k
```

và đoán sai, ta phải trả:

```text
k
```

Sau đó số thực nằm ở:

```text
[left,k-1]
```

hoặc:

```text
[k+1,right]
```

Nhưng ta phải đảm bảo chiến thắng trong **worst case**.

Do đó:

```text
cost =
k
+
max(
    dp[left][k-1],
    dp[k+1][right]
)
```

Ta muốn chọn `k` sao cho worst-case cost nhỏ nhất:

```text
dp[left][right]
=
min over k {
    k
    +
    max(
        dp[left][k-1],
        dp[k+1][right]
    )
}
```

Đây là combination của:

```text
Interval DP
+
Minimax
```

Rất đáng luyện.

---

# 19. Pattern 6 — Merge Equal Boundaries

Một dạng Interval DP khó hơn xuất hiện trong bài:

# LeetCode 664 — Strange Printer

Printer có khả năng:

```text
print một character trên một continuous interval
```

Ví dụ:

```text
"aaa"
```

chỉ cần:

```text
1 operation
```

Điểm quan trọng:

nếu hai character giống nhau:

```text
s[i] == s[k]
```

ta có thể merge operation của chúng.

State:

```text
dp[i][j]
=
minimum number of turns
to print s[i...j]
```

Ban đầu coi:

```text
s[i]
```

được print riêng:

```text
dp[i][j]
=
1 + dp[i+1][j]
```

Nhưng nếu:

```text
s[i] == s[k]
```

ta có thể print chúng trong cùng một turn.

Một recurrence:

```text
dp[i][j]
=
min(
    dp[i][j],
    dp[i+1][k-1]
    +
    dp[k][j]
)
```

Java:

```java
class Solution {

    public int strangePrinter(String s) {

        int n = s.length();

        int[][] dp = new int[n][n];

        for (int i = 0; i < n; i++) {
            dp[i][i] = 1;
        }

        for (int len = 2; len <= n; len++) {

            for (
                int left = 0;
                left + len - 1 < n;
                left++
            ) {

                int right =
                    left + len - 1;

                dp[left][right] =
                    1 + dp[left + 1][right];

                for (
                    int k = left + 1;
                    k <= right;
                    k++
                ) {

                    if (
                        s.charAt(left)
                        == s.charAt(k)
                    ) {

                        int middle =
                            k == left + 1
                            ? 0
                            : dp[left + 1][k - 1];

                        dp[left][right] =
                            Math.min(
                                dp[left][right],
                                middle
                                + dp[k][right]
                            );
                    }
                }
            }
        }

        return dp[0][n - 1];
    }
}
```

Đây là dạng nâng cao hơn Burst Balloons khá nhiều.

---

# 20. Top-down Interval DP

Không phải lúc nào cũng cần Bottom-up.

Ta hoàn toàn có thể dùng:

```text
DFS + Memoization
```

Template:

```java
class Solution {

    Integer[][] memo;

    int dfs(int left, int right) {

        if (left > right) {
            return 0;
        }

        if (left == right) {
            return ...;
        }

        if (memo[left][right] != null) {
            return memo[left][right];
        }

        int result = ...;

        for (int k = left; k <= right; k++) {

            result = Math.min(
                result,
                ...
            );
        }

        return memo[left][right] = result;
    }
}
```

Top-down rất hữu ích khi đang **tìm recurrence**.

Thông thường nên tư duy theo thứ tự:

```text
1. Write recursive brute force

2. Define state

3. Identify repeated states

4. Add memoization

5. Convert to bottom-up nếu cần
```

Đây thường dễ hơn việc cố nghĩ bottom-up ngay từ đầu.

---

# 21. Bottom-up hay Top-down?

| Top-down                    | Bottom-up                  |
| --------------------------- | -------------------------- |
| Recurrence dễ viết          | Performance thường tốt hơn |
| Gần với tư duy recursive    | Không recursion stack      |
| Dễ debug logic              | Dễ control iteration order |
| Chỉ compute state cần thiết | Compute toàn bộ table      |
| Có recursion overhead       | Không recursion overhead   |

Trong interview:

```text
Brute force recursion
        ↓
Memoization
        ↓
Bottom-up optimization
```

là một cách explain rất tốt.

---

# 22. Interval DP và Partition DP khác nhau thế nào?

Hai loại này khá dễ bị nhầm.

Ví dụ Partition DP:

```text
dp[i]
=
best answer for prefix [0...i]
```

sau đó thử:

```text
for k
```

để chọn partition cuối cùng.

Ví dụ:

```text
Palindrome Partitioning
Word Break
Partition Array for Maximum Sum
```

State chủ yếu là:

```text
dp[i]
```

Trong khi Interval DP:

```text
dp[left][right]
```

quan tâm cả hai boundary.

Ví dụ:

```text
Burst Balloons
Strange Printer
Triangulation
Cut Stick
```

Rule dễ nhớ:

```text
Prefix DP

dp[i]
```

vs

```text
Interval DP

dp[l][r]
```

---

# 23. Một mental model rất quan trọng

Khi gặp bài Interval DP, hãy tưởng tượng:

```text
           [l ............... r]

                       ↓

              choose decision

             /               \

      smaller interval     smaller interval
```

Ví dụ split:

```text
              [l ..... r]

                   k

          /                  \

     [l ... k]          [k+1 ... r]
```

Hoặc endpoints:

```text
              [l ..... r]

           /      |       \

      remove l remove r remove both

         ↓        ↓        ↓

    [l+1,r] [l,r-1] [l+1,r-1]
```

---

# 24. Template tổng quát số 1 — Shrinking Boundaries

```java
int[][] dp = new int[n][n];

for (int i = 0; i < n; i++) {
    dp[i][i] = BASE;
}

for (int len = 2; len <= n; len++) {

    for (
        int left = 0;
        left + len - 1 < n;
        left++
    ) {

        int right =
            left + len - 1;

        dp[left][right] =
            combine(
                dp[left + 1][right],
                dp[left][right - 1],
                dp[left + 1][right - 1]
            );
    }
}
```

Ứng dụng:

```text
Longest Palindromic Subsequence
Predict the Winner
Stone Game VII
```

---

# 25. Template tổng quát số 2 — Split at k

```java
long[][] dp = new long[n][n];

for (int len = 2; len <= n; len++) {

    for (
        int left = 0;
        left + len - 1 < n;
        left++
    ) {

        int right =
            left + len - 1;

        dp[left][right] = INF;

        for (
            int k = left;
            k < right;
            k++
        ) {

            dp[left][right] =
                Math.min(
                    dp[left][right],

                    dp[left][k]
                    +
                    dp[k + 1][right]
                    +
                    cost(left, k, right)
                );
        }
    }
}
```

Đây là template quan trọng nhất.

---

# 26. Template tổng quát số 3 — Pivot nằm bên trong interval

Một số bài dùng:

```text
left < k < right
```

thay vì:

```text
left <= k < right
```

Ví dụ triangulation:

```java
for (int len = 3; len <= n; len++) {

    for (
        int left = 0;
        left + len - 1 < n;
        left++
    ) {

        int right =
            left + len - 1;

        dp[left][right] = INF;

        for (
            int k = left + 1;
            k < right;
            k++
        ) {

            dp[left][right] =
                Math.min(
                    dp[left][right],

                    dp[left][k]
                    +
                    dp[k][right]
                    +
                    cost(left, k, right)
                );
        }
    }
}
```

---

# 27. Closed Interval vs Open Interval

Có hai cách define state.

## Closed interval

```text
dp[l][r]
```

xử lý:

```text
l, l+1, ..., r
```

Tức:

```text
[l,r]
```

Ví dụ Burst Balloons:

```text
dp[l][r]
=
burst tất cả balloon từ l đến r
```

---

## Open interval

Một số bài define:

```text
dp[l][r]
```

xử lý mọi thứ **nằm giữa** `l` và `r`.

Tức:

```text
(l,r)
```

Ví dụ Cut Stick:

```text
dp[i][j]
=
process all cuts between
cuts[i] and cuts[j]
```

Boundary `i,j` không phải operation cần xử lý mà là hai biên.

Hiểu sự khác nhau này giúp tránh rất nhiều bug index.

---

# 28. Vì sao thêm sentinel rất hữu ích?

Burst Balloons:

```text
nums = [3,1,5,8]
```

Ta thêm:

```text
[1,3,1,5,8,1]
```

Cut Stick:

```text
cuts = [1,3,4,5]
```

Ta thêm:

```text
[0,1,3,4,5,n]
```

Sentinel giúp:

```text
boundary case
```

trở thành:

```text
normal case
```

Thay vì phải viết:

```java
if (left == 0) ...
if (right == n - 1) ...
```

ta có thể dùng một recurrence thống nhất.

Đây là kỹ thuật cực kỳ hay trong DP.

---

# 29. Tại sao Interval DP thường O(n³)?

Có:

```text
O(n²)
```

interval khác nhau.

Vì:

```text
left = 0..n-1
right = left..n-1
```

Số lượng:

```text
n(n+1)/2

≈ O(n²)
```

Trong mỗi interval ta có thể thử:

```text
O(n)
```

pivot/split.

Nên:

```text
O(n² × n)

=

O(n³)
```

Space:

```text
dp[n][n]

=

O(n²)
```

---

# 30. Nhìn constraint để nhận diện

Nếu:

```text
n <= 30
```

có thể có exponential / bitmask / interval.

Nếu:

```text
n <= 100
```

`O(n³)` thường khá ổn.

Nếu:

```text
n <= 500
```

`O(n³)` có thể bắt đầu nặng.

Nếu:

```text
n <= 2000
```

thường phải tìm:

```text
O(n²)
```

hoặc optimization đặc biệt.

Do đó constraint cũng giúp đoán pattern.

---

# 31. Dạng nâng cao — Extra State

Không phải Interval DP nào cũng chỉ có:

```text
dp[l][r]
```

Đôi khi cần:

```text
dp[l][r][k]
```

Ví dụ nổi tiếng:

```text
LeetCode 546
Remove Boxes
```

State có thể biểu diễn:

```text
dp[l][r][k]
```

trong đó:

```text
k
=
số box cùng màu với boxes[r]
được nối từ bên ngoài interval
```

Đây là một trong những Interval DP khó nhất trên LeetCode.

Nó cho ta một bài học quan trọng:

> Nếu `dp[l][r]` không chứa đủ thông tin để quyết định tương lai, hãy thêm một state dimension.

Giống các DP khác.

---

# 32. Dạng nâng cao — Merge Stones

# LeetCode 1000 — Minimum Cost to Merge Stones

Đây là:

```text
Interval DP
+
Merge
+
Constraint về số piles
```

Ta có:

```text
stones = [3,2,4,1]

K = 2
```

Mỗi lần merge `K` consecutive piles.

Điểm khó là trạng thái không chỉ phụ thuộc interval mà còn có thể phụ thuộc:

```text
interval đang được merge thành bao nhiêu piles
```

Một cách define đầy đủ:

```text
dp[l][r][p]
=
minimum cost to merge
stones[l...r]
into exactly p piles
```

Đây là progression tự nhiên từ:

```text
dp[l][r]
```

sang:

```text
dp[l][r][extraState]
```

---

# 33. Các recurrence family cần nhớ

Không nên học recurrence cụ thể của từng bài.

Hãy học các **family**.

| Family           | Recurrence shape                   |
| ---------------- | ---------------------------------- |
| Remove endpoints | `dp[l+1][r]`, `dp[l][r-1]`         |
| Match endpoints  | `dp[l+1][r-1] + ...`               |
| Split interval   | `dp[l][k] + dp[k+1][r]`            |
| Pivot            | `dp[l][k] + dp[k][r] + cost`       |
| Last operation   | left part + action(k) + right part |
| Game             | action - opponentDP                |
| Minimax          | `min_k(action + max(left,right))`  |
| Merge equal item | merge two matching positions       |
| Extra state      | `dp[l][r][k]`                      |

Nếu quen các family này, Interval DP sẽ đỡ cảm giác mỗi bài là một trick riêng biệt.

---

# 34. Checklist khi gặp một bài mới

Hãy tự hỏi theo đúng trình tự:

```text
1. Answer có phải cho một interval không?

2. Nếu define dp[l][r],
   nó có đủ thông tin không?

3. Tôi có thể:
   - remove left?
   - remove right?
   - match left/right?
   - choose k?
   - split tại k?

4. First operation có làm subproblem độc lập không?

5. Nếu không:
   thử nghĩ về LAST operation.

6. Base case nhỏ nhất là gì?

7. Dependency có phải interval nhỏ hơn không?

8. Nếu bottom-up:
   tôi phải iterate length như thế nào?

9. Complexity:
   states × transitions = ?

10. Có cần thêm sentinel / boundary không?
```

Đây là checklist nên dùng trong coding interview.

---

# 35. Sai lầm phổ biến số 1 — Define state không rõ

Không nên nói:

```text
dp[l][r] stores the answer.
```

Nên nói:

```text
dp[l][r] represents the minimum cost
to completely process all cuts
strictly between positions l and r.
```

Hoặc:

```text
dp[l][r] represents the maximum coins
we can obtain by bursting
all balloons from l through r.
```

State definition càng rõ, recurrence càng dễ suy ra.

---

# 36. Sai lầm số 2 — Không xác định independence

Đây là điểm cực kỳ quan trọng.

Không phải cứ:

```text
choose k
```

là được viết:

```text
dp[l][k] + dp[k+1][r]
```

Ta chỉ được làm vậy nếu hai subproblem trở nên:

```text
independent
```

sau decision.

Ví dụ Burst Balloons.

Nếu chọn balloon **first**, left/right chưa độc lập vì boundary sẽ thay đổi.

Nhưng nếu chọn balloon **last**, chúng độc lập.

Đó là lý do recurrence hoạt động.

---

# 37. Sai lầm số 3 — Duyệt sai thứ tự

Ví dụ:

```text
dp[l][r]
```

phụ thuộc:

```text
dp[l+1][r]
dp[l][r-1]
```

Nếu tính:

```text
dp[l][r]
```

trước các state này thì sai.

An toàn nhất:

```text
for len = 1..n
```

vì interval nhỏ luôn được tính trước.

---

# 38. Sai lầm số 4 — Confuse index với position

Cut Stick là ví dụ.

Có:

```text
positions[index]
```

Ở đây:

```text
i,j,k
```

là **index trong cuts array**.

Trong khi:

```text
positions[j] - positions[i]
```

mới là length thật.

Không được viết nhầm:

```text
j - i
```

---

# 39. Sai lầm số 5 — Base case

Ví dụ:

```text
dp[i][i]
```

có thể là:

```text
1
```

trong Longest Palindromic Subsequence.

Nhưng:

```text
0
```

trong một số cost problem.

Ví dụ Cut Stick:

```text
dp[i][i+1] = 0
```

vì không có cut ở giữa.

Base case phụ thuộc hoàn toàn vào **state definition**.

---

# 40. Java Interview Template

Một template rất nên thuộc:

```java
int[][] dp = new int[n][n];

for (int len = 1; len <= n; len++) {

    for (
        int left = 0;
        left + len - 1 < n;
        left++
    ) {

        int right =
            left + len - 1;

        // base case

        if (left == right) {
            dp[left][right] = ...;
            continue;
        }

        // transition

        for (
            int k = left;
            k < right;
            k++
        ) {

            dp[left][right] =
                ...
        }
    }
}

return dp[0][n - 1];
```

Bạn không cần thuộc recurrence.

Bạn cần thuộc skeleton:

```text
length
    left
        right
            split k
```

Tức:

```java
for (len)
    for (left)
        right = left + len - 1;

        for (k)
```

Đây chính là signature của rất nhiều Interval DP.

---

# 41. Cách explain Interval DP trong coding interview

Một cách nói tự nhiên bằng tiếng Anh:

> I'll define `dp[left][right]` as the optimal answer for the subarray from `left` to `right`, inclusive.

Sau đó:

> Since every transition depends only on smaller intervals, I'll process the intervals in increasing order of length.

Nếu có pivot:

> For each interval, I'll try every possible split point `k`.

Nếu là Burst Balloons:

> Choosing the first balloon to burst makes the remaining subproblems dependent on each other. Instead, I'll consider which balloon is burst last. Once `k` is the last balloon, the left and right intervals become independent.

Đây là một explanation rất mạnh trong interview.

---

# 42. LeetCode Learning Path

Mình khuyến nghị học theo thứ tự sau.

| Level | Problem                                          | Pattern                     |
| ----- | ------------------------------------------------ | --------------------------- |
| 1     | **516. Longest Palindromic Subsequence**         | Shrink endpoints            |
| 1     | **486. Predict the Winner**                      | Game interval               |
| 1     | **877. Stone Game**                              | Game interval               |
| 2     | **375. Guess Number Higher or Lower II**         | Minimax + pivot             |
| 2     | **1039. Minimum Score Triangulation of Polygon** | Split/pivot                 |
| 2     | **1547. Minimum Cost to Cut a Stick**            | Cut interval                |
| 2     | **312. Burst Balloons**                          | Last operation              |
| 3     | **1690. Stone Game VII**                         | Game + interval             |
| 3     | **1130. Minimum Cost Tree From Leaf Values**     | Split interval              |
| 3     | **664. Strange Printer**                         | Merge equal positions       |
| 4     | **1000. Minimum Cost to Merge Stones**           | Interval + extra constraint |
| 4     | **546. Remove Boxes**                            | 3D Interval DP              |
| 4     | **1563. Stone Game V**                           | Advanced split DP           |

---

# 43. Thứ tự luyện tập mình khuyến nghị

Đừng bắt đầu bằng Burst Balloons ngay.

Hãy đi theo progression:

```text
516
 ↓
486
 ↓
375
 ↓
1039
 ↓
1547
 ↓
312
 ↓
1690
 ↓
1130
 ↓
664
 ↓
1000
 ↓
546
```

Lý do:

```text
516
```

giúp hiểu:

```text
dp[l][r]
```

Sau đó:

```text
486
```

giúp hiểu:

```text
interval game
```

Tiếp theo:

```text
375
```

giúp hiểu:

```text
try every k
```

Sau đó:

```text
1039 / 1547
```

giúp hiểu:

```text
split interval
```

Sau đó:

```text
312
```

dạy concept quan trọng:

```text
choose LAST operation
```

Cuối cùng:

```text
664 / 1000 / 546
```

là các biến thể advanced.

---

# 44. 4 bài bắt buộc phải thực sự hiểu

Nếu mục tiêu là coding interview, bốn bài mình đánh giá quan trọng nhất cho Interval DP là:

```text
516 Longest Palindromic Subsequence
```

để hiểu:

```text
shrink boundaries
```

```text
1039 Minimum Score Triangulation
```

để hiểu:

```text
split at k
```

```text
1547 Minimum Cost to Cut a Stick
```

để hiểu:

```text
interval + boundary
```

và đặc biệt:

```text
312 Burst Balloons
```

để hiểu:

```text
reverse the perspective
first operation → last operation
```

Nếu thực sự hiểu bốn bài này thay vì memorize code, bạn đã nắm được phần lớn tư tưởng Interval DP.

---

# 45. So sánh với Knapsack DP

Knapsack thường có state:

```text
dp[i][capacity]
```

Tư duy:

```text
process item i
```

và quyết định:

```text
take
skip
```

Interval DP lại có:

```text
dp[left][right]
```

Tư duy:

```text
process interval [left,right]
```

và quyết định thường là:

```text
remove left
remove right
match endpoints
split at k
choose first operation
choose last operation
```

Do đó:

```text
Knapsack
→ item-oriented DP

Interval DP
→ range-oriented DP
```

---

# 46. Một cách phân loại DP rất hữu ích

Khi gặp DP, trước tiên thử xác định **shape của state**.

```text
dp[i]
```

thường là:

```text
linear / prefix DP
```

```text
dp[i][capacity]
```

thường là:

```text
knapsack
```

```text
dp[i][j]
```

có thể là:

```text
two sequences
grid
interval
```

Nếu `i,j` đại diện cho:

```text
left boundary
right boundary
```

thì gần như chắc chắn:

```text
Interval DP
```

Nếu:

```text
dp[i][mask]
```

thường liên quan:

```text
bitmask DP
```

Nếu:

```text
dp[node][state]
```

thường là:

```text
tree DP
```

Nhận diện **state shape** là một kỹ năng rất quan trọng khi học DP.

---

# 47. Công thức tư duy cuối cùng

Khi gặp một bài có vẻ là Interval DP, hãy nghĩ:

```text
STEP 1

What does dp[l][r] mean?
```

↓

```text
STEP 2

What decision can I make
inside [l,r]?
```

↓

```text
STEP 3

Does that decision produce
smaller independent intervals?
```

↓

Nếu chưa:

```text
Try reversing the operation order.

FIRST
  ↓
LAST
```

↓

```text
STEP 4

Write recurrence.
```

↓

```text
STEP 5

Find base case.
```

↓

```text
STEP 6

Memoization first.
```

↓

```text
STEP 7

Bottom-up:
iterate by interval length.
```

---

# 48. Interval DP Cheat Sheet

```text
STATE

dp[l][r]
=
optimal answer for interval [l,r]
```

```text
ITERATION

for len = 1 → n
    for left
        right = left + len - 1
```

```text
ENDPOINT TRANSITION

dp[l][r]
←
dp[l+1][r]
dp[l][r-1]
dp[l+1][r-1]
```

```text
SPLIT TRANSITION

dp[l][r]
=
best over k {
    dp[l][k]
    +
    dp[k+1][r]
    +
    cost(...)
}
```

```text
PIVOT TRANSITION

dp[l][r]
=
best over k {
    dp[l][k]
    +
    dp[k][r]
    +
    cost(l,k,r)
}
```

```text
GAME

dp[l][r]
=
max(
    value[l] - dp[l+1][r],
    value[r] - dp[l][r-1]
)
```

```text
MINIMAX

dp[l][r]
=
min_k {
    cost(k)
    +
    max(
        dp[l][k-1],
        dp[k+1][r]
    )
}
```

```text
COMPLEXITY

states:
O(n²)

transition:
O(n)

total:
O(n³)

space:
O(n²)
```

Và câu quan trọng nhất cần nhớ:

> **Nếu việc chọn operation đầu tiên làm các subproblem vẫn phụ thuộc nhau, hãy thử nghĩ xem operation cuối cùng là gì.**

Đây chính là một trong những insight mạnh nhất của Interval DP.
