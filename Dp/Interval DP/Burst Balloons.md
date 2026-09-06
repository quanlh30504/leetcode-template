Đây là một bài **Interval DP kinh điển**, và nó có tư tưởng rất giống **1039. Minimum Score Triangulation of Polygon** mà bạn vừa học. Nhưng có một cú “đảo góc nhìn” rất quan trọng:

> Với Triangulation: chọn **triangle cuối cùng**.
> Với Burst Balloons: chọn **balloon cuối cùng được burst**.

Nếu nắm được ý này thì transition của Burst Balloons gần như tự xuất hiện.

---

# 1. Hiểu đề bài

Bài **Burst Balloons** cho một mảng:

```text
nums = [3, 1, 5, 8]
```

Mỗi phần tử là một balloon.

Khi burst một balloon `i`, bạn nhận được:

```text
nums[left] * nums[i] * nums[right]
```

Trong đó:

* `left` = balloon gần nhất bên trái chưa bị burst
* `right` = balloon gần nhất bên phải chưa bị burst

Sau khi burst `i`, balloon đó biến mất.

Mục tiêu:

```text
maximize total coins
```

---

# 2. Ví dụ

```text
[3, 1, 5, 8]
```

Nếu burst `1` trước:

```text
3 * 1 * 5 = 15
```

Array còn:

```text
[3, 5, 8]
```

Nếu sau đó burst `5`:

```text
3 * 5 * 8 = 120
```

...

Điểm khó là:

> **Giá trị nhận được khi burst một balloon phụ thuộc vào thứ tự burst.**

Ví dụ balloon `5`:

Nếu burst nó khi:

```text
[3, 1, 5, 8]
```

thì:

```text
1 * 5 * 8 = 40
```

Nhưng nếu `1` đã bị burst:

```text
[3, 5, 8]
```

thì:

```text
3 * 5 * 8 = 120
```

Vì vậy không thể đơn giản:

```text
dp[i] = ...
```

theo từng balloon.

---

# 3. Tại sao cách nghĩ "balloon nào burst trước?" khó?

Giả sử:

```text
[3, 1, 5, 8]
```

Ta chọn balloon `i = 1` burst trước:

```text
3 * 1 * 5
```

Sau đó bài toán còn lại là:

```text
[3, 5, 8]
```

Nhưng vấn đề là:

> Những balloon nào sẽ còn tồn tại lúc ta xử lý một subproblem?

Việc burst ở bên ngoài có thể làm thay đổi **hai hàng xóm** của balloon bên trong.

Điều này làm state kiểu:

```text
dp[l][r] = maximum coins từ l đến r
```

nếu nghĩ theo thứ tự burst **từ đầu đến cuối**, trở nên rất khó.

---

# 4. Cú đổi góc nhìn: chọn balloon cuối cùng

Đây là insight quan trọng nhất của bài.

Thay vì hỏi:

> Balloon nào được burst đầu tiên?

Hãy hỏi:

> **Balloon nào được burst cuối cùng trong một interval?**

Đây là tư duy rất đặc trưng của Interval DP.

---

# 5. Ví dụ

Xét:

```text
[a, b, c, d, e]
```

Giả sử `c` là balloon **cuối cùng được burst** trong interval:

```text
[a ... e]
```

Tại thời điểm `c` được burst:

```text
a ... c ... e
```

Tất cả balloon giữa `a` và `c` đã biến mất.

Tất cả balloon giữa `c` và `e` cũng đã biến mất.

Vì `c` là balloon cuối cùng nên hai hàng xóm của nó lúc này chắc chắn là:

```text
a
```

và:

```text
e
```

Do đó coins nhận được từ lần burst cuối:

```text
a * c * e
```

---

# 6. Và đây chính là thứ giúp bài toán tách đôi

Trước khi burst `c`:

```text
[a ... c ... e]
```

Các balloon bên trái:

```text
[a ... c]
```

và bên phải:

```text
[c ... e]
```

có thể được burst **độc lập**.

Ta có:

```text
left subproblem
+
right subproblem
+
last balloon
```

Tức:

```text
dp[l][k]
+
dp[k][r]
+
nums[l] * nums[k] * nums[r]
```

Nhìn quen không?

Nó gần như **y hệt 1039 Triangulation**:

```text
dp[l][k]
+
dp[k][r]
+
cost(l,k,r)
```

Đây là cùng một pattern:

> **Interval Partition DP + chọn `k` làm điểm chia / phần tử cuối cùng.**

---

# 7. Nhưng phải thêm boundary `1`

Đây là điểm đặc biệt của Burst Balloons.

Đề quy định:

> Nếu balloon ở bên trái/phải không tồn tại thì coi giá trị của nó là `1`.

Thay vì mỗi lần xử lý boundary, ta thêm hai balloon giả:

```text
nums = [3,1,5,8]
```

thành:

```text
[1, 3, 1, 5, 8, 1]
 ↑                 ↑
boundary           boundary
```

Hai số `1` này **không được burst**.

---

# 8. Define state

Sau khi padding:

```text
[1, 3, 1, 5, 8, 1]
```

Ta define:

```text
dp[l][r]
=
maximum coins obtained by bursting
all balloons strictly between l and r
```

Chú ý chữ **strictly between**.

Tức:

```text
dp[l][r]
```

không burst:

```text
l
```

và:

```text
r
```

mà chỉ burst:

```text
l+1 ... r-1
```

---

# 9. Đây là state cực kỳ quan trọng

Ví dụ:

```text
[1, 3, 1, 5, 8, 1]
```

Xét:

```text
dp[0][5]
```

nghĩa là:

```text
burst tất cả balloon:

3, 1, 5, 8
```

nhưng giữ lại:

```text
1 -------- 1
l            r
```

Hai boundary `1` đóng vai trò là **hai hàng xóm cố định**.

---

# 10. Tại sao define như vậy tốt hơn?

Bởi vì nếu `k` là balloon cuối cùng được burst trong:

```text
(l, r)
```

thì ngay thời điểm cuối:

```text
k
```

chắc chắn có hai hàng xóm:

```text
l
```

và:

```text
r
```

Do tất cả balloon giữa chúng đã bị burst trước đó.

Vậy coins cuối cùng chắc chắn là:

```text
nums[l] * nums[k] * nums[r]
```

**Không còn phụ thuộc vào thứ tự bên trong nữa.**

Đây chính là lý do ta chọn **last balloon**.

---

# 11. Transition

Giả sử:

```text
l < k < r
```

và `k` là balloon cuối cùng được burst.

Khi đó:

### Phần bên trái

```text
dp[l][k]
```

Burst tất cả balloon giữa:

```text
l ... k
```

### Phần bên phải

```text
dp[k][r]
```

Burst tất cả balloon giữa:

```text
k ... r
```

### Burst `k` cuối cùng

```text
nums[l] * nums[k] * nums[r]
```

Tổng:

```text
dp[l][k]
+ dp[k][r]
+ nums[l] * nums[k] * nums[r]
```

Thử mọi `k`:

```text
dp[l][r]
=
max(
    dp[l][k]
    + dp[k][r]
    + nums[l] * nums[k] * nums[r]
)
```

với:

```text
l < k < r
```

---

# 12. Ví dụ cực nhỏ

Giả sử:

```text
nums = [3, 1, 5]
```

Padding:

```text
[1, 3, 1, 5, 1]
```

Ta cần:

```text
dp[0][4]
```

Tức burst:

```text
3, 1, 5
```

Có 3 khả năng cho balloon cuối cùng.

---

### `k = 1` → balloon `3` cuối

Cost cuối:

```text
1 * 3 * 1 = 3
```

Hai bên:

```text
dp[0][1] = 0
dp[1][4]
```

Candidate:

```text
0 + dp[1][4] + 3
```

---

### `k = 2` → balloon `1` cuối

Cost:

```text
1 * 1 * 1 = 1
```

Candidate:

```text
dp[0][2]
+ dp[2][4]
+ 1
```

---

### `k = 3` → balloon `5` cuối

Cost:

```text
1 * 5 * 1 = 5
```

Candidate:

```text
dp[0][3]
+ dp[3][4]
+ 5
```

Lấy:

```text
max(...)
```

---

# 13. Điểm quan trọng: `k` không phải "burst k trước"

Đây là chỗ rất dễ nhầm.

Trong Burst Balloons:

```text
k
```

được chọn là:

> **balloon cuối cùng được burst trong interval `[l,r]`.**

Không phải:

> balloon đầu tiên được burst.

Đây là lý do transition mới ổn định.

---

# 14. Vì sao chọn "last" thay vì "first"?

Nếu chọn `k` burst **đầu tiên**:

```text
[l ... k ... r]
```

thì sau khi burst `k`, hàng xóm của các balloon còn lại thay đổi.

Ta không biết subproblem `[l,k]` và `[k,r]` có còn độc lập không.

Nhưng nếu `k` burst **cuối cùng**:

```text
[l ... k ... r]
```

thì trước khi burst `k`:

```text
mọi thứ giữa l và k đã biến mất
mọi thứ giữa k và r đã biến mất
```

nên:

```text
left neighbor  = l
right neighbor = r
```

**chắc chắn.**

Đây chính là trick.

---

# 15. So sánh với Triangulation 1039

Bạn vừa học bài:

**Minimum Score Triangulation of Polygon**

State:

```text
dp[l][r]
=
minimum cost to triangulate [l,r]
```

Chọn:

```text
k
```

tạo triangle cuối:

```text
(l,k,r)
```

Transition:

```text
dp[l][k]
+ dp[k][r]
+ values[l] * values[k] * values[r]
```

---

Burst Balloons:

```text
dp[l][r]
=
maximum coins
from bursting balloons between l and r
```

Chọn:

```text
k
```

làm balloon cuối:

```text
(l, k, r)
```

Transition:

```text
dp[l][k]
+ dp[k][r]
+ nums[l] * nums[k] * nums[r]
```

Bạn có thể thấy:

```text
1039:
partition interval
       ↓
triangle(l,k,r)

Burst Balloons:
partition interval
       ↓
last burst = k
       ↓
cost(l,k,r)
```

**Hai bài gần như cùng một template DP.**

---

# 16. Bottom-Up

State:

```text
dp[l][r]
```

phụ thuộc:

```text
dp[l][k]
dp[k][r]
```

Hai interval con đều nhỏ hơn `[l,r]`.

Do đó:

```text
interval nhỏ
      ↓
interval lớn
```

Ta loop theo `length`.

Nhưng lưu ý:

```text
dp[l][r]
```

có ý nghĩa là các balloon **strictly between** `l` và `r`.

Muốn có ít nhất một balloon để burst thì:

```text
r - l >= 2
```

nên:

```java
for (int len = 2; len < n; len++)
```

---

# 17. Code chuẩn

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

        // len = distance between l and r
        for (int len = 2; len <= n + 1; len++) {

            for (int l = 0; l + len < n + 2; l++) {

                int r = l + len;

                for (int k = l + 1; k < r; k++) {

                    int coins =
                        dp[l][k]
                        + dp[k][r]
                        + arr[l] * arr[k] * arr[r];

                    dp[l][r] = Math.max(
                        dp[l][r],
                        coins
                    );
                }
            }
        }

        return dp[0][n + 1];
    }
}
```

---

# 18. Trace cấu trúc DP

Với:

```text
[1, 3, 1, 5, 8, 1]
```

Ta không cần quan tâm toàn bộ bảng ngay.

Ví dụ muốn tính:

```text
dp[0][5]
```

thì:

```text
             dp[0][5]
           /     |     \
          /      |      \
     k = 1     k = 2    k = 3 ...
       /          |         \
 dp[0][1]      dp[0][2]   dp[0][3]
 dp[1][5]      dp[2][5]   dp[3][5]
```

Mỗi `k` đại diện cho **một lựa chọn balloon cuối cùng**.

---

# 19. Pattern cần nhớ

Đây là phần mình nghĩ bạn nên note lại trong bộ Pattern Interval DP của bạn.

```text
INTERVAL PARTITION DP

dp[l][r]
    ↓
chọn k nằm giữa l và r
    ↓
chia interval:

[l -------- r]
      ↓
[l --- k] + [k --- r]
```

General form:

```text
dp[l][r]
=
OPT over k (
    dp[l][k]
    + dp[k][r]
    + cost(l,k,r)
)
```

---

## Triangulation

```text
k = vertex của triangle cuối

cost = values[l] * values[k] * values[r]

OPT = min
```

## Burst Balloons

```text
k = balloon cuối cùng được burst

cost = nums[l] * nums[k] * nums[r]

OPT = max
```

---

# 20. Mental model quan trọng nhất

Khi gặp **Burst Balloons**, đừng nghĩ:

> "Mình nên burst balloon nào trước?"

Hãy đổi thành:

> **"Trong interval `[l,r]`, balloon nào sẽ là balloon cuối cùng được burst?"**

Sau khi chọn `k`:

```text
        l             r
        |             |
        v             v
        [---- k ----]
             ↑
        burst cuối
```

Lúc đó `l` và `r` chính là hai hàng xóm của `k`.

Vì vậy:

```text
cost = arr[l] * arr[k] * arr[r]
```

và hai bên độc lập:

```text
dp[l][k] + dp[k][r]
```

=> **Interval Partition DP.**

### Note một dòng:

> **Burst Balloons = Interval DP + chọn phần tử cuối cùng + partition `[l,k]` và `[k,r]` + maximize.**

Và đây là một pattern rất đáng nhớ: **khi "chọn phần tử đầu tiên" làm subproblem khó vì neighbor/state thay đổi, hãy thử đổi góc nhìn sang "phần tử cuối cùng".**
