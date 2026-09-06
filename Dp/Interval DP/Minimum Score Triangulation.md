Đây là một bài **Interval DP kinh điển**. Nếu hiểu được bài này thì bạn sẽ thấy rất rõ một tư tưởng quan trọng:

> **Khi bài toán yêu cầu chia một đoạn / polygon thành nhiều phần, hãy thử nghĩ: “phần cuối cùng được chia như thế nào?”**

---

# 1. Hiểu đề bài trước

Ta có một polygon lồi, mỗi đỉnh có một giá trị.

Ví dụ:

```text
values = [1, 3, 1, 4, 1, 5]
```

Có 6 đỉnh:

```text
        3
      /   \
     1     1
     |     |
     5     4
      \   /
        1
```

Ta phải **triangulate** polygon, tức là chia polygon thành các tam giác.

Với `n` đỉnh thì luôn có:

```text
n - 2
```

tam giác.

---

# 2. Weight của một triangle là gì?

Nếu một triangle có 3 đỉnh:

```text
a, b, c
```

thì:

```text
weight = a * b * c
```

Ví dụ:

```text
[1, 3, 1]
```

thì:

```text
weight = 1 * 3 * 1 = 3
```

Tổng score của polygon là tổng weight của tất cả triangle.

Mục tiêu:

```text
minimize total score
```

---

# 3. Vấn đề khó nằm ở đâu?

Giả sử có:

```text
A B C D E
```

Ta phải chia polygon thành các triangle.

Có rất nhiều cách triangulate.

Ví dụ có thể:

```text
A-B-C
A-C-D
A-D-E
```

hoặc:

```text
A-B-C
B-C-D
B-D-E
```

Mỗi cách cho score khác nhau.

Ta cần tìm cách có score nhỏ nhất.

---

# 4. Tại sao đây là Interval DP?

Đây là insight quan trọng.

Hãy tưởng tượng ta lấy một **đoạn các vertex liên tiếp**:

```text
l ........ r
```

Ví dụ:

```text
A B C D E F
↑         ↑
l         r
```

Thay vì giải cả polygon một lần, ta hỏi:

> **Chi phí nhỏ nhất để triangulate phần polygon từ vertex `l` đến vertex `r` là bao nhiêu?**

Đây chính là state:

```text
dp[l][r]
=
minimum score needed
to triangulate vertices l...r
```

Hay:

```text
dp[l][r] = min cost to triangulate interval [l, r]
```

Đây chính là **Interval DP**.

---

# 5. Tại sao `[l, r]` lại tạo thành một subproblem hợp lệ?

Đây là điểm hình học quan trọng.

Giả sử:

```text
A B C D E F
```

Ta xét:

```text
A B C D E
```

Các vertex vẫn liên tiếp nhau trên polygon.

Ta có thể coi:

```text
A -------- E
 \          /
  B C D
```

là một polygon nhỏ hơn được tạo bởi:

* các cạnh liên tiếp `A-B-C-D-E`
* cộng với đường chéo `E-A`

Vì polygon ban đầu **convex**, đường chéo này nằm hoàn toàn bên trong polygon.

Do đó `[l, r]` thực sự là một subproblem độc lập.

Đây là một đặc điểm rất thường gặp của Interval DP:

```text
Một đoạn [l...r]
→ có thể giải độc lập
→ kết quả của nó dùng để xây đoạn lớn hơn
```

---

# 6. Nhưng `dp[l][r]` tính như thế nào?

Đây mới là phần quan trọng nhất.

Giả sử:

```text
A B C D E
```

và:

```text
l = A
r = E
```

Ta cần triangulate:

```text
A B C D E
```

Hãy hỏi:

> **Triangle cuối cùng của phần `[A...E]` là triangle nào?**

---

# 7. Chọn một vertex `k` ở giữa

Ta chọn:

```text
k ∈ (l, r)
```

Ví dụ:

```text
A B C D E
    ↑
    k = C
```

Khi đó ta tạo triangle:

```text
A - C - E
```

Visual:

```text
A -------- E
 \         /
  \   C   /
   \ / \ /
```

Triangle này có cost:

```text
values[A] * values[C] * values[E]
```

Nhưng vẫn còn hai phần:

```text
A ... C
```

và

```text
C ... E
```

Tức là:

```text
dp[A][C]
```

và:

```text
dp[C][E]
```

---

# 8. Vì vậy nếu chọn `k`

Cost sẽ là:

```text
dp[l][k]
+
dp[k][r]
+
values[l] * values[k] * values[r]
```

Đây là **một candidate answer**.

Nhưng ta có thể chọn nhiều `k` khác nhau:

```text
l < k < r
```

Nên phải thử tất cả.

Do cần minimum:

```text
dp[l][r]
=
min(
    dp[l][k]
    + dp[k][r]
    + values[l] * values[k] * values[r]
)
```

với:

```text
l < k < r
```

---

# 9. Đây chính là tư tưởng "cut interval"

Bạn nên đặc biệt ghi nhớ pattern này.

Ta có:

```text
[l ---------------- r]
```

Chọn một điểm `k`:

```text
[l ------ k ------ r]
```

Sau đó chia thành:

```text
[l ---- k] + [k ---- r] + triangle(l,k,r)
```

Tức:

```text
             [l,r]
                |
          choose k
          /      \
      [l,k]      [k,r]
          \      /
        triangle
```

Đây chính là **Interval Partition DP**.

---

# 10. Tại sao triangle `(l, k, r)` là đủ?

Đây là chỗ hình học cần hiểu.

Khi ta chọn:

```text
k
```

triangle:

```text
(l, k, r)
```

được tạo bởi 3 vertex của polygon.

Nó chia phần `[l,r]` thành hai vùng độc lập:

```text
[l ... k]
```

và

```text
[k ... r]
```

Do polygon convex nên các đường chéo:

```text
(l,k)
(k,r)
```

không gây vấn đề giao nhau.

Vì thế ta có thể tối ưu hai phần độc lập rồi cộng lại.

Đây chính là **optimal substructure** của DP.

---

# 11. Base case

Khi:

```text
l + 1 == r
```

thì chỉ có:

```text
2 vertices
```

Ví dụ:

```text
A B
```

Không thể tạo triangle.

Cost:

```text
0
```

Vì vậy:

```text
dp[l][l+1] = 0
```

Đây là base case tự nhiên nhất.

---

# 12. Tại sao `dp[l][l]` không phải base case chính?

Bạn cũng có thể initialize:

```text
dp[i][i] = 0
```

nhưng về mặt ý nghĩa polygon, interval:

```text
[i, i]
```

không phải một polygon cần triangulate.

Quan trọng hơn là:

```text
dp[l][l+1] = 0
```

vì hai vertex cũng chưa tạo được triangle.

---

# 13. Ví dụ cực nhỏ: `[1,2,3]`

Có đúng 3 vertex:

```text
A = 1
B = 2
C = 3
```

Chỉ có một triangle:

```text
A B C
```

Score:

```text
1 * 2 * 3 = 6
```

DP:

```text
dp[0][2]
```

Chỉ có một `k`:

```text
k = 1
```

nên:

```text
dp[0][2]
=
dp[0][1]
+
dp[1][2]
+
1*2*3
```

Hai interval:

```text
dp[0][1] = 0
dp[1][2] = 0
```

Do đó:

```text
dp[0][2] = 6
```

---

# 14. Ví dụ 4 vertex

Giả sử:

```text
values = [1, 2, 3, 4]
```

Polygon:

```text
A ---- B
|      |
D ---- C
```

Ta cần 2 triangles.

Có hai cách chính.

### Cách 1: chọn đường chéo `A-C`

```text
A B
 \|
  C
  |
  D
```

Triangles:

```text
(A,B,C)
(A,C,D)
```

Cost:

```text
1*2*3 + 1*3*4
= 6 + 12
= 18
```

DP tương ứng:

```text
k = B
```

hoặc khi `l=0,r=3`:

```text
dp[0][1]
+
dp[1][3]
+
values[0]*values[1]*values[3]
```

---

### Cách 2: chọn đường chéo `B-D`

Triangles:

```text
(A,B,D)
(B,C,D)
```

Cost:

```text
1*2*4 + 2*3*4
= 8 + 24
= 32
```

DP sẽ thử cả hai `k`:

```text
k = 1
k = 2
```

rồi lấy:

```text
min(18, 32) = 18
```

---

# 15. Đây là lý do phải thử `k`

Khi nhìn:

```text
dp[l][r]
```

ta không biết cách triangulate tối ưu sẽ dùng đường chéo nào.

Vì vậy:

```text
for k = l + 1 → r - 1
```

thử tất cả khả năng.

Đây là pattern rất quan trọng:

```text
Interval DP
    ↓
Chọn một điểm k ở giữa
    ↓
chia [l,r] thành [l,k] + [k,r]
    ↓
combine
    ↓
min / max
```

---

# 16. Bottom-Up được xây như thế nào?

Transition:

```text
dp[l][r]
=
min(
    dp[l][k]
    + dp[k][r]
    + values[l] * values[k] * values[r]
)
```

Dependency:

```text
                dp[l][r]
                 /    \
                /      \
          dp[l][k]    dp[k][r]
```

Hai interval con đều **ngắn hơn** `[l,r]`.

Vậy ta phải tính:

```text
interval nhỏ
      ↓
interval lớn
```

Đây là bản chất của Bottom-Up.

---

# 17. Cách loop dễ hiểu nhất: theo `length`

```java
for (int len = 3; len <= n; len++) {
    
    for (int l = 0; l + len - 1 < n; l++) {
        
        int r = l + len - 1;
        
        ...
    }
}
```

Tại sao bắt đầu:

```text
len = 3
```

?

Vì ít nhất phải có 3 vertex mới tạo được triangle.

---

# 18. Full code

```java
class Solution {
    public int minScoreTriangulation(int[] values) {
        int n = values.length;

        int[][] dp = new int[n][n];

        for (int len = 3; len <= n; len++) {

            for (int l = 0; l + len - 1 < n; l++) {

                int r = l + len - 1;

                dp[l][r] = Integer.MAX_VALUE;

                for (int k = l + 1; k < r; k++) {

                    int cost =
                        dp[l][k]
                        + dp[k][r]
                        + values[l] * values[k] * values[r];

                    dp[l][r] = Math.min(
                        dp[l][r],
                        cost
                    );
                }
            }
        }

        return dp[0][n - 1];
    }
}
```

---

# 19. Trace thứ tự tính DP

Với:

```text
[1, 2, 3, 4, 5]
```

Ta tính theo length:

### Length = 3

```text
dp[0][2]
dp[1][3]
dp[2][4]
```

### Length = 4

```text
dp[0][3]
dp[1][4]
```

### Length = 5

```text
dp[0][4]
```

Visual:

```text
       0   1   2   3   4
   0   -   -   X   X   X
   1       -   -   X   X
   2           -   -   X
   3               -   -
   4                   -
```

`X` được tính từ interval ngắn hơn.

---

# 20. Một insight rất quan trọng về "last triangle"

Bạn có thể tự hỏi:

> Tại sao cứ chọn triangle `(l,k,r)`?

Bởi vì khi triangulate một polygon/interval `[l,r]`, **hai endpoint `l` và `r` luôn thuộc boundary của subproblem**.

Trong một triangulation, tồn tại một triangle chứa cạnh:

```text
(l,r)
```

Triangle đó phải có dạng:

```text
(l, k, r)
```

với:

```text
l < k < r
```

Vì vậy:

> **Thay vì thử tất cả cách triangulate, chỉ cần thử vertex `k` của triangle chứa cạnh `(l,r)`.**

Sau khi cố định `k`, phần còn lại tự tách thành hai bài toán nhỏ.

Đây chính là trick giúp bài toán từ exponential brute force trở thành DP.

---

# 21. So sánh với Predict the Winner

Hai bài bạn vừa học liên tiếp thực ra rất liên quan.

### Predict the Winner

```text
dp[l][r]
=
maximum score difference
current player can obtain
```

Transition:

```text
dp[l][r]
=
max(
    nums[l] - dp[l+1][r],
    nums[r] - dp[l][r-1]
)
```

Pattern:

```text
Interval DP
→ lấy đầu hoặc cuối
→ subproblem nhỏ hơn
```

---

### Minimum Score Triangulation

```text
dp[l][r]
=
minimum cost to triangulate [l,r]
```

Transition:

```text
dp[l][r]
=
min(
    dp[l][k]
    + dp[k][r]
    + cost(l,k,r)
)
```

Pattern:

```text
Interval DP
→ chọn điểm k để partition interval
→ giải 2 interval con
→ combine
```

---

# 22. Hai loại Interval DP bạn nên phân biệt

Đây là note rất đáng ghi vào tài liệu DSA của bạn:

```text
INTERVAL DP
│
├── 1. Shrink interval
│      dp[l][r] phụ thuộc vào
│      dp[l+1][r]
│      dp[l][r-1]
│      dp[l+1][r-1]
│
│      Ví dụ:
│      Predict the Winner
│      Palindrome-related DP
│
└── 2. Partition interval
       chọn k trong (l,r)

       dp[l][r]
       =
       min/max over k:
       dp[l][k] + dp[k][r] + cost

       Ví dụ:
       Minimum Score Triangulation
       Matrix Chain Multiplication
       Burst Balloons (biến thể)
```

### Mental model cực ngắn:

**Shrink:**

```text
[l -------- r]
 ↓          ↓
[l+1 ----- r]
[l ------- r-1]
```

**Partition:**

```text
[l -------- r]
      ↓
[l --- k] [k --- r]
```

Và với **1039**, hãy nghĩ ngay:

> **“Polygon → chọn triangle cuối chứa cạnh `(l,r)` → thử `k` → chia thành `[l,k]` và `[k,r]`.”**

Đó chính là tư tưởng Interval DP của bài này.
