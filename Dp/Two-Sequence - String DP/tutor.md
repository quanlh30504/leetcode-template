# Two-Sequence / String DP

## 1. Two-Sequence DP là gì?

Two-Sequence DP là nhóm bài Dynamic Programming mà trạng thái phụ thuộc đồng thời vào **hai sequence**.

Hai sequence có thể là:

```text
String + String
Array + Array
String + Pattern
String + Target
```

Ví dụ:

```text
text1 = "abcde"
text2 = "ace"
```

State điển hình:

```text
dp[i][j]
```

Trong đó:

```text
i = số phần tử đã xét của sequence 1
j = số phần tử đã xét của sequence 2
```

Hay nói chính xác hơn:

```text
dp[i][j]
=
answer của bài toán
khi xét prefix:

sequence1[0 ... i-1]
sequence2[0 ... j-1]
```

Đây là cách định nghĩa phổ biến nhất.

---

# 2. Tại sao `dp[i][j]` thường dùng prefix?

Giả sử:

```text
s1 = "abcd"
s2 = "ace"
```

State:

```text
dp[3][2]
```

nghĩa là đang xét:

```text
s1 prefix = "abc"
s2 prefix = "ac"
```

Tức:

```text
i = 3
→ characters:
s1[0], s1[1], s1[2]

j = 2
→ characters:
s2[0], s2[1]
```

Character cuối của hai prefix là:

```text
s1[i - 1]
s2[j - 1]
```

Điểm này cực kỳ quan trọng vì phần lớn bug Two-Sequence DP đến từ việc nhầm:

```text
dp[i][j]
```

với character:

```text
s[i]
s[j]
```

Trong prefix DP chuẩn, character cuối là:

```text
s1[i - 1]
s2[j - 1]
```

---

# 3. Mental Model quan trọng nhất

Hãy tưởng tượng một grid:

```text
          s2
          ""   a   c   e
       +----+---+---+---+
    "" |    |   |   |   |
       +----+---+---+---+
s1  a  |    |   |   |   |
       +----+---+---+---+
    b  |    |   |   |   |
       +----+---+---+---+
    c  |    |   |   |   |
       +----+---+---+---+
    d  |    |   |   |   |
       +----+---+---+---+
```

Mỗi cell:

```text
dp[i][j]
```

đại diện cho bài toán con của:

```text
s1 prefix dài i
s2 prefix dài j
```

Các transition phổ biến:

```text
dp[i-1][j]
dp[i][j-1]
dp[i-1][j-1]
```

Có thể hình dung:

```text
          dp[i-1][j]
               ↓

dp[i][j-1] → dp[i][j]

              ↖
       dp[i-1][j-1]
```

Ba hướng này chính là "signature" của Two-Sequence DP.

---

# 4. Khi nào nên nghĩ đến Two-Sequence DP?

Các dấu hiệu rất mạnh:

```text
compare two strings
compare two arrays

subsequence của hai sequence

convert string A → string B

match string với pattern

count ways to form target

interleave two strings

minimum operations between two strings

common subsequence
```

Keyword thường gặp:

```text
subsequence
common
match
transform
convert
delete
insert
replace
pattern
interleave
distinct ways
```

Nếu đề cho:

```text
two strings / two arrays
```

và ta cần đưa ra quyết định dựa trên:

```text
current character of both sequences
```

thì nên thử:

```text
dp[i][j]
```

---

# 5. Four Questions của Two-Sequence DP

Khi gặp bài mới, hãy hỏi:

```text
1. dp[i][j] nghĩa là gì?

2. Nếu current characters match:
   s1[i-1] == s2[j-1]
   thì làm gì?

3. Nếu mismatch:
   có những lựa chọn nào?

4. Base case khi một sequence rỗng là gì?
```

Gần như toàn bộ Two-Sequence DP có thể suy ra bằng 4 câu này.

---

# 6. Pattern tổng quát

Giả sử:

```text
a = sequence1
b = sequence2
```

Ta thường có:

```java
for (int i = 1; i <= m; i++) {
    for (int j = 1; j <= n; j++) {

        if (a.charAt(i - 1) == b.charAt(j - 1)) {
            dp[i][j] = ... dp[i - 1][j - 1];
        } else {
            dp[i][j] = combine(
                dp[i - 1][j],
                dp[i][j - 1],
                dp[i - 1][j - 1]
            );
        }
    }
}
```

Điểm khác biệt giữa các bài chính là:

```text
combine là gì?

max?
min?
sum?
OR?
```

---

# 7. Các family chính

Two-Sequence DP có thể chia thành:

| Pattern             | State result        |
| ------------------- | ------------------- |
| Common subsequence  | max length          |
| Transform strings   | min operations      |
| Count matching ways | number of ways      |
| Boolean matching    | true / false        |
| Interleaving        | true / false        |
| Weighted transform  | min cost            |
| Construct result    | DP + reconstruction |

Chúng ta sẽ đi từng nhóm.

---

# PART I — COMMON SUBSEQUENCE DP

# 8. Bài kinh điển nhất: Longest Common Subsequence

## LeetCode 1143 — Longest Common Subsequence

Cho:

```text
text1 = "abcde"
text2 = "ace"
```

Kết quả:

```text
"ace"
length = 3
```

---

# 9. Subsequence là gì?

Subsequence:

* không cần contiguous
* nhưng phải giữ nguyên relative order

Ví dụ:

```text
"abcde"
```

Các subsequence:

```text
"ace"
"abd"
"bde"
"abc"
```

Nhưng:

```text
"eca"
```

không phải vì order bị đảo.

---

# 10. State của LCS

Define:

```text
dp[i][j]
=
length của Longest Common Subsequence
giữa:

text1[0 ... i-1]
text2[0 ... j-1]
```

Ví dụ:

```text
dp[3][2]
```

nghĩa là:

```text
text1 = "abc"
text2 = "ac"
```

---

# 11. Recurrence của LCS

Ta xét:

```text
text1[i - 1]
text2[j - 1]
```

Có 2 case.

## Case 1 — Characters match

Nếu:

```text
text1[i-1] == text2[j-1]
```

Ví dụ:

```text
abc
  ↑

ac
 ↑
```

hai character cuối đều là `c`.

Ta có thể sử dụng character này trong common subsequence.

Do đó:

```text
dp[i][j]
=
1 + dp[i-1][j-1]
```

Tức:

```text
LCS("abc", "ac")
=
1 + LCS("ab", "a")
```

---

# 12. Case mismatch

Nếu:

```text
text1[i-1] != text2[j-1]
```

Ví dụ:

```text
abcd
   ↑ d

ace
  ↑ e
```

Ta không thể dùng cả hai character cuối cùng cùng lúc.

Một optimal LCS phải bỏ ít nhất một trong hai.

Option 1:

```text
bỏ text1[i-1]

dp[i-1][j]
```

Option 2:

```text
bỏ text2[j-1]

dp[i][j-1]
```

Ta lấy:

```text
max
```

Do đó:

```text
dp[i][j]
=
max(
    dp[i-1][j],
    dp[i][j-1]
)
```

---

# 13. Full recurrence LCS

```text
if text1[i-1] == text2[j-1]:

    dp[i][j]
    =
    1 + dp[i-1][j-1]

else:

    dp[i][j]
    =
    max(
        dp[i-1][j],
        dp[i][j-1]
    )
```

Base case:

```text
dp[0][j] = 0
dp[i][0] = 0
```

Vì:

```text
LCS("", anything) = 0
```

---

# 14. Java — LCS

```java
class Solution {

    public int longestCommonSubsequence(
        String text1,
        String text2
    ) {

        int m = text1.length();
        int n = text2.length();

        int[][] dp = new int[m + 1][n + 1];

        for (int i = 1; i <= m; i++) {

            for (int j = 1; j <= n; j++) {

                char c1 = text1.charAt(i - 1);
                char c2 = text2.charAt(j - 1);

                if (c1 == c2) {

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

Complexity:

```text
Time  O(m × n)
Space O(m × n)
```

---

# 15. LCS là một master pattern

Rất nhiều bài thực chất chỉ là biến thể của LCS:

```text
1143 Longest Common Subsequence

1035 Uncrossed Lines

583 Delete Operation for Two Strings

1092 Shortest Common Supersequence

712 Minimum ASCII Delete Sum

1312 Minimum Insertion Steps to Make a String Palindrome
```

Hiểu LCS cực kỳ quan trọng.

---

# 16. LeetCode 1035 — Uncrossed Lines

Bài cho hai arrays:

```text
nums1 = [1,4,2]
nums2 = [1,2,4]
```

Ta nối các giá trị giống nhau sao cho lines không cắt nhau.

Điều kiện:

```text
lines không được cross
```

thực chất tương đương với:

```text
relative order phải được giữ nguyên
```

Đó chính là:

```text
subsequence
```

Vậy bài trở thành:

```text
Longest Common Subsequence của 2 arrays
```

Java:

```java
class Solution {

    public int maxUncrossedLines(
        int[] nums1,
        int[] nums2
    ) {

        int m = nums1.length;
        int n = nums2.length;

        int[][] dp = new int[m + 1][n + 1];

        for (int i = 1; i <= m; i++) {

            for (int j = 1; j <= n; j++) {

                if (
                    nums1[i - 1]
                    == nums2[j - 1]
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

Bài học:

> Đừng chỉ associate LCS với String. Nó là pattern cho mọi ordered sequence.

---

# PART II — TRANSFORM ONE STRING INTO ANOTHER

# 17. LeetCode 72 — Edit Distance

Đây là một trong những bài Two-Sequence DP quan trọng nhất.

Cho:

```text
word1 = "horse"
word2 = "ros"
```

Operations:

```text
insert
delete
replace
```

Mỗi operation cost = 1.

Tìm minimum operations để:

```text
word1 → word2
```

---

# 18. State Edit Distance

Define:

```text
dp[i][j]
=
minimum operations
để convert:

word1[0 ... i-1]

thành:

word2[0 ... j-1]
```

---

# 19. Base Case

Nếu:

```text
word1 = ""
word2 = "abc"
```

ta phải insert:

```text
a
b
c
```

3 lần.

Do đó:

```text
dp[0][j] = j
```

Nếu:

```text
word1 = "abc"
word2 = ""
```

phải delete 3 lần:

```text
dp[i][0] = i
```

---

# 20. Match Case

Nếu:

```text
word1[i-1] == word2[j-1]
```

hai character cuối đã giống nhau.

Không cần operation.

```text
dp[i][j]
=
dp[i-1][j-1]
```

Ví dụ:

```text
ca
 ↑

ba
 ↑
```

Character `a` đã match.

Chỉ cần solve:

```text
c → b
```

---

# 21. Mismatch Case

Nếu:

```text
word1[i-1] != word2[j-1]
```

có 3 operation.

---

## Replace

Thay:

```text
word1[i-1]
```

thành:

```text
word2[j-1]
```

Sau replace, hai character cuối đã match.

Còn lại:

```text
dp[i-1][j-1]
```

Cost:

```text
1 + dp[i-1][j-1]
```

---

## Delete

Delete:

```text
word1[i-1]
```

Sau đó:

```text
word1 prefix giảm đi 1
```

nhưng target vẫn giữ nguyên.

```text
1 + dp[i-1][j]
```

---

## Insert

Ta insert character:

```text
word2[j-1]
```

vào word1.

Character target cuối đã được xử lý.

word1 index không giảm:

```text
1 + dp[i][j-1]
```

---

# 22. Recurrence Edit Distance

```text
if word1[i-1] == word2[j-1]:

    dp[i][j]
    =
    dp[i-1][j-1]

else:

    dp[i][j]
    =
    1 + min(
        dp[i-1][j-1],  // replace
        dp[i-1][j],    // delete
        dp[i][j-1]     // insert
    )
```

---

# 23. Java — Edit Distance

```java
class Solution {

    public int minDistance(
        String word1,
        String word2
    ) {

        int m = word1.length();
        int n = word2.length();

        int[][] dp = new int[m + 1][n + 1];

        for (int i = 0; i <= m; i++) {
            dp[i][0] = i;
        }

        for (int j = 0; j <= n; j++) {
            dp[0][j] = j;
        }

        for (int i = 1; i <= m; i++) {

            for (int j = 1; j <= n; j++) {

                char c1 = word1.charAt(i - 1);
                char c2 = word2.charAt(j - 1);

                if (c1 == c2) {

                    dp[i][j] =
                        dp[i - 1][j - 1];

                } else {

                    int replace =
                        dp[i - 1][j - 1];

                    int delete =
                        dp[i - 1][j];

                    int insert =
                        dp[i][j - 1];

                    dp[i][j] =
                        1 + Math.min(
                            replace,
                            Math.min(
                                delete,
                                insert
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

# 24. Edit Distance Template

Đây là template rất đáng nhớ:

```java
if (a[i - 1] == b[j - 1]) {

    dp[i][j] =
        dp[i - 1][j - 1];

} else {

    dp[i][j] =
        cost + min(
            dp[i - 1][j - 1],
            dp[i - 1][j],
            dp[i][j - 1]
        );
}
```

Ý nghĩa ba state:

```text
diagonal → replace/match

up → delete from sequence 1

left → insert into sequence 1
```

Có thể nhớ bằng grid:

```text
             delete
                ↓

             [i-1,j]
                 |
                 |
replace → [i-1,j-1] → [i,j]
                           ↑
                         insert
                       [i,j-1]
```

---

# PART III — DELETE-ONLY TRANSFORMATION

# 25. LeetCode 583 — Delete Operation for Two Strings

Cho:

```text
word1 = "sea"
word2 = "eat"
```

Chỉ được:

```text
delete
```

Tìm minimum deletions để hai strings giống nhau.

Một cách suy nghĩ cực hay:

> Phần mà ta giữ lại ở cả hai string phải là common subsequence.

Ta muốn giữ lại nhiều character nhất.

Đó chính là:

```text
Longest Common Subsequence
```

Nếu:

```text
L = LCS(word1, word2)
```

thì:

```text
word1 cần delete:
m - L

word2 cần delete:
n - L
```

Total:

```text
m + n - 2L
```

---

# 26. Java — Delete Operation via LCS

```java
class Solution {

    public int minDistance(
        String word1,
        String word2
    ) {

        int m = word1.length();
        int n = word2.length();

        int[][] dp = new int[m + 1][n + 1];

        for (int i = 1; i <= m; i++) {

            for (int j = 1; j <= n; j++) {

                if (
                    word1.charAt(i - 1)
                    == word2.charAt(j - 1)
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

        int lcs = dp[m][n];

        return m + n - 2 * lcs;
    }
}
```

---

# PART IV — WEIGHTED DELETE DP

# 27. LeetCode 712 — Minimum ASCII Delete Sum

Cho:

```text
s1 = "sea"
s2 = "eat"
```

Ta có thể delete character.

Nhưng cost không phải `1`.

Cost:

```text
ASCII value
```

Ví dụ:

```text
'a' = 97
'b' = 98
```

Mục tiêu:

```text
minimum total ASCII cost
để hai strings giống nhau
```

---

# 28. State

```text
dp[i][j]
=
minimum ASCII delete cost
để:

s1 prefix i
s2 prefix j

trở thành giống nhau
```

---

# 29. Base Cases

Nếu:

```text
s2 = ""
```

thì ta phải delete toàn bộ `s1`.

```text
dp[i][0]
=
dp[i-1][0]
+
s1[i-1]
```

Tương tự:

```text
dp[0][j]
=
dp[0][j-1]
+
s2[j-1]
```

---

# 30. Transition

Nếu match:

```text
s1[i-1] == s2[j-1]
```

không cần delete:

```text
dp[i][j]
=
dp[i-1][j-1]
```

Nếu mismatch:

Ta có hai choice.

Delete từ `s1`:

```text
ASCII(s1[i-1])
+
dp[i-1][j]
```

Delete từ `s2`:

```text
ASCII(s2[j-1])
+
dp[i][j-1]
```

Lấy min.

---

# 31. Java

```java
class Solution {

    public int minimumDeleteSum(
        String s1,
        String s2
    ) {

        int m = s1.length();
        int n = s2.length();

        int[][] dp = new int[m + 1][n + 1];

        for (int i = 1; i <= m; i++) {

            dp[i][0] =
                dp[i - 1][0]
                + s1.charAt(i - 1);
        }

        for (int j = 1; j <= n; j++) {

            dp[0][j] =
                dp[0][j - 1]
                + s2.charAt(j - 1);
        }

        for (int i = 1; i <= m; i++) {

            for (int j = 1; j <= n; j++) {

                char c1 = s1.charAt(i - 1);
                char c2 = s2.charAt(j - 1);

                if (c1 == c2) {

                    dp[i][j] =
                        dp[i - 1][j - 1];

                } else {

                    int delete1 =
                        c1 + dp[i - 1][j];

                    int delete2 =
                        c2 + dp[i][j - 1];

                    dp[i][j] =
                        Math.min(
                            delete1,
                            delete2
                        );
                }
            }
        }

        return dp[m][n];
    }
}
```

Bài này cho thấy:

```text
Two-Sequence DP
không thay đổi structure

chỉ thay:
objective + transition cost
```

---

# PART V — COUNTING TWO-SEQUENCE DP

# 32. LeetCode 115 — Distinct Subsequences

Đây là bài rất quan trọng.

Cho:

```text
s = "rabbbit"
t = "rabbit"
```

Tìm số cách xóa character trong `s` để tạo `t`.

Answer:

```text
3
```

---

# 33. State

Define:

```text
dp[i][j]
=
number of ways
to form:

t[0 ... j-1]

using:

s[0 ... i-1]
```

Điểm cực kỳ quan trọng:

```text
sequence 1 = source
sequence 2 = target
```

---

# 34. Base Cases

Target empty:

```text
t = ""
```

Có đúng một cách tạo empty string:

```text
không chọn character nào
```

Do đó:

```text
dp[i][0] = 1
```

với mọi `i`.

Nhưng nếu source empty:

```text
s = ""
```

mà target non-empty:

```text
dp[0][j] = 0
```

---

# 35. Transition khi characters mismatch

Nếu:

```text
s[i-1] != t[j-1]
```

current source character không thể đóng góp vào target cuối.

Chỉ có:

```text
skip s[i-1]
```

Do đó:

```text
dp[i][j]
=
dp[i-1][j]
```

---

# 36. Transition khi characters match

Nếu:

```text
s[i-1] == t[j-1]
```

ta có **hai lựa chọn**.

### Option 1 — Use current character

Dùng:

```text
s[i-1]
```

để match:

```text
t[j-1]
```

Sau đó:

```text
dp[i-1][j-1]
```

### Option 2 — Skip current character

Không dùng:

```text
s[i-1]
```

Target vẫn giữ nguyên:

```text
dp[i-1][j]
```

Do đó:

```text
dp[i][j]
=
dp[i-1][j-1]
+
dp[i-1][j]
```

---

# 37. Distinct Subsequences Recurrence

```text
if s[i-1] == t[j-1]:

    dp[i][j]
    =
    dp[i-1][j-1]
    +
    dp[i-1][j]

else:

    dp[i][j]
    =
    dp[i-1][j]
```

Đây là một pattern rất quan trọng:

> Nếu current item match, ta có thể `use` hoặc `skip`.

Nó khá giống tư tưởng:

```text
0/1 Knapsack
```

---

# 38. Java

```java
class Solution {

    public int numDistinct(
        String s,
        String t
    ) {

        int m = s.length();
        int n = t.length();

        long[][] dp = new long[m + 1][n + 1];

        for (int i = 0; i <= m; i++) {
            dp[i][0] = 1;
        }

        for (int i = 1; i <= m; i++) {

            for (int j = 1; j <= n; j++) {

                if (
                    s.charAt(i - 1)
                    == t.charAt(j - 1)
                ) {

                    dp[i][j] =
                        dp[i - 1][j - 1]
                        +
                        dp[i - 1][j];

                } else {

                    dp[i][j] =
                        dp[i - 1][j];
                }
            }
        }

        return (int) dp[m][n];
    }
}
```

---

# 39. Comparison: LCS vs Distinct Subsequences

Cả hai đều check:

```text
s1[i-1] == s2[j-1]
```

Nhưng objective khác nhau.

### LCS

Ta tìm:

```text
maximum length
```

nên dùng:

```text
max
```

### Distinct Subsequences

Ta tìm:

```text
number of ways
```

nên dùng:

```text
+
```

Đây là bài học lớn:

> DP structure có thể giống nhau, nhưng operator phụ thuộc objective.

---

# PART VI — INTERLEAVING DP

# 40. LeetCode 97 — Interleaving String

Cho:

```text
s1 = "aabcc"
s2 = "dbbca"

s3 = "aadbbcbcac"
```

Hỏi có thể tạo `s3` bằng cách interleave:

```text
s1
s2
```

mà vẫn giữ relative order trong từng string hay không.

---

# 41. Tại sao Two Pointer Greedy dễ sai?

Giả sử:

```text
s1[i] == s3[k]
s2[j] == s3[k]
```

ta không biết nên lấy từ `s1` hay `s2`.

Greedy chọn một bên có thể dẫn đến dead end sau này.

Đây chính là:

```text
branching choices
+
repeated states
```

→ DP.

---

# 42. State

Define:

```text
dp[i][j]
=
true nếu:

s1 prefix i
+
s2 prefix j

có thể tạo:

s3 prefix i+j
```

Ta không cần dimension thứ ba vì:

```text
k = i + j
```

Đây là một kỹ thuật rất quan trọng:

> Nếu một state variable có thể suy ra từ những variable khác, không cần lưu nó.

---

# 43. Transition

Đang xét:

```text
s3[i + j - 1]
```

Ta có thể lấy character cuối từ `s1`.

Nếu:

```text
s1[i-1]
==
s3[i+j-1]
```

và trước đó:

```text
dp[i-1][j] == true
```

thì:

```text
dp[i][j] = true
```

Hoặc lấy từ `s2`:

```text
s2[j-1]
==
s3[i+j-1]
```

và:

```text
dp[i][j-1]
```

Do đó:

```text
dp[i][j]
=
(
    dp[i-1][j]
    &&
    s1[i-1] == s3[i+j-1]
)
||
(
    dp[i][j-1]
    &&
    s2[j-1] == s3[i+j-1]
)
```

---

# 44. Java — Interleaving String

```java
class Solution {

    public boolean isInterleave(
        String s1,
        String s2,
        String s3
    ) {

        int m = s1.length();
        int n = s2.length();

        if (m + n != s3.length()) {
            return false;
        }

        boolean[][] dp =
            new boolean[m + 1][n + 1];

        dp[0][0] = true;

        for (int i = 0; i <= m; i++) {

            for (int j = 0; j <= n; j++) {

                if (i == 0 && j == 0) {
                    continue;
                }

                int k = i + j - 1;

                boolean fromS1 = false;
                boolean fromS2 = false;

                if (
                    i > 0
                    &&
                    dp[i - 1][j]
                    &&
                    s1.charAt(i - 1)
                    == s3.charAt(k)
                ) {

                    fromS1 = true;
                }

                if (
                    j > 0
                    &&
                    dp[i][j - 1]
                    &&
                    s2.charAt(j - 1)
                    == s3.charAt(k)
                ) {

                    fromS2 = true;
                }

                dp[i][j] =
                    fromS1 || fromS2;
            }
        }

        return dp[m][n];
    }
}
```

---

# PART VII — PATTERN MATCHING DP

# 45. LeetCode 44 — Wildcard Matching

Pattern có:

```text
?
```

match exactly one character.

```text
*
```

match any sequence, kể cả empty.

Ví dụ:

```text
s = "adceb"
p = "*a*b"
```

→ true.

---

# 46. State

Define:

```text
dp[i][j]
=
s prefix length i
có match
pattern prefix length j
hay không
```

---

# 47. Normal Character / `?`

Nếu:

```text
p[j-1] == s[i-1]
```

hoặc:

```text
p[j-1] == '?'
```

thì current characters match.

Ta chuyển:

```text
dp[i][j]
=
dp[i-1][j-1]
```

---

# 48. `*` Case

Đây là phần quan trọng.

`*` có thể đại diện cho:

### Zero characters

Ta bỏ `*`:

```text
dp[i][j-1]
```

### One or more characters

Ta dùng `*` để consume:

```text
s[i-1]
```

nhưng giữ `*` để có thể consume tiếp.

```text
dp[i-1][j]
```

Do đó:

```text
dp[i][j]
=
dp[i][j-1]
||
dp[i-1][j]
```

---

# 49. Base case của Wildcard

```text
dp[0][0] = true
```

Empty string match empty pattern.

Một empty string có thể match:

```text
*
**
***
```

nên:

```text
dp[0][j]
```

chỉ true nếu toàn bộ pattern prefix đều là `*`.

---

# 50. Java — Wildcard Matching

```java
class Solution {

    public boolean isMatch(
        String s,
        String p
    ) {

        int m = s.length();
        int n = p.length();

        boolean[][] dp =
            new boolean[m + 1][n + 1];

        dp[0][0] = true;

        for (int j = 1; j <= n; j++) {

            if (p.charAt(j - 1) == '*') {
                dp[0][j] = dp[0][j - 1];
            }
        }

        for (int i = 1; i <= m; i++) {

            for (int j = 1; j <= n; j++) {

                char sc = s.charAt(i - 1);
                char pc = p.charAt(j - 1);

                if (
                    pc == sc
                    || pc == '?'
                ) {

                    dp[i][j] =
                        dp[i - 1][j - 1];

                } else if (pc == '*') {

                    dp[i][j] =
                        dp[i][j - 1]
                        ||
                        dp[i - 1][j];
                }
            }
        }

        return dp[m][n];
    }
}
```

---

# 51. Một insight cực hay về `*`

Recurrence:

```text
dp[i][j]
=
dp[i][j-1]
||
dp[i-1][j]
```

có nghĩa:

```text
dp[i][j-1]
→ star matches empty

dp[i-1][j]
→ star consumes one more character
```

Đây là cách reasoning chuẩn khi gặp wildcard/star trong DP.

---

# PART VIII — REGULAR EXPRESSION DP

# 52. LeetCode 10 — Regular Expression Matching

Pattern gồm:

```text
.
```

match một character bất kỳ.

```text
*
```

có nghĩa:

```text
zero or more của character ngay trước nó
```

Ví dụ:

```text
a*
```

có thể match:

```text
""
"a"
"aa"
"aaa"
```

Điểm này khác Wildcard Matching.

Wildcard:

```text
*
=
any arbitrary sequence
```

Regex:

```text
x*
=
repeat x zero or more times
```

---

# 53. State

Vẫn là:

```text
dp[i][j]
=
s prefix i
matches
p prefix j
```

---

# 54. Normal Match

Nếu:

```text
p[j-1] == s[i-1]
```

hoặc:

```text
p[j-1] == '.'
```

thì:

```text
dp[i][j]
=
dp[i-1][j-1]
```

---

# 55. Khi `p[j-1] == '*'`

`*` luôn liên quan đến:

```text
p[j-2]
```

Ví dụ:

```text
a*
```

Ta có hai case.

---

## Zero occurrence

Ta bỏ cả:

```text
a*
```

Do đó:

```text
dp[i][j-2]
```

---

## One or more occurrences

Chỉ được nếu:

```text
p[j-2]
```

match:

```text
s[i-1]
```

Tức:

```text
p[j-2] == s[i-1]
```

hoặc:

```text
p[j-2] == '.'
```

Khi đó `*` consume current character:

```text
dp[i-1][j]
```

Ta vẫn giữ pattern `a*` vì nó có thể consume thêm.

---

# 56. Recurrence Regex

```text
if normal match:

    dp[i][j]
    =
    dp[i-1][j-1]

else if p[j-1] == '*':

    dp[i][j]
    =
    dp[i][j-2]

    if previous pattern char matches s[i-1]:

        dp[i][j]
        |=
        dp[i-1][j]
```

---

# 57. Java — Regular Expression Matching

```java
class Solution {

    public boolean isMatch(
        String s,
        String p
    ) {

        int m = s.length();
        int n = p.length();

        boolean[][] dp =
            new boolean[m + 1][n + 1];

        dp[0][0] = true;

        for (int j = 2; j <= n; j++) {

            if (p.charAt(j - 1) == '*') {

                dp[0][j] =
                    dp[0][j - 2];
            }
        }

        for (int i = 1; i <= m; i++) {

            for (int j = 1; j <= n; j++) {

                char pc = p.charAt(j - 1);

                if (
                    pc == s.charAt(i - 1)
                    || pc == '.'
                ) {

                    dp[i][j] =
                        dp[i - 1][j - 1];

                } else if (pc == '*') {

                    // zero occurrences
                    dp[i][j] =
                        dp[i][j - 2];

                    char prev =
                        p.charAt(j - 2);

                    if (
                        prev == s.charAt(i - 1)
                        || prev == '.'
                    ) {

                        dp[i][j] =
                            dp[i][j]
                            ||
                            dp[i - 1][j];
                    }
                }
            }
        }

        return dp[m][n];
    }
}
```

---

# PART IX — SHORTEST COMMON SUPERSEQUENCE

# 58. LeetCode 1092 — Shortest Common Supersequence

Cho:

```text
str1 = "abac"
str2 = "cab"
```

Ta cần tạo shortest string chứa cả:

```text
str1
str2
```

như subsequence.

Ví dụ:

```text
"cabac"
```

---

# 59. Liên hệ với LCS

Nếu hai string có common characters, ta chỉ cần giữ chúng một lần trong supersequence.

Do đó length:

```text
m + n - LCS
```

Nhưng bài yêu cầu return actual string.

Vậy ta cần:

```text
DP
+
reconstruction
```

---

# 60. Reconstruction từ LCS table

Sau khi tính:

```text
dp[i][j]
=
LCS length
```

bắt đầu từ:

```text
i = m
j = n
```

Nếu:

```text
str1[i-1] == str2[j-1]
```

character này nằm trong cả hai:

```text
append once
i--
j--
```

Nếu mismatch:

Ta nhìn:

```text
dp[i-1][j]
dp[i][j-1]
```

Nếu:

```text
dp[i-1][j] > dp[i][j-1]
```

thì đi lên:

```text
append str1[i-1]
i--
```

Ngược lại:

```text
append str2[j-1]
j--
```

Cuối cùng reverse string.

---

# 61. Java — Shortest Common Supersequence

```java
class Solution {

    public String shortestCommonSupersequence(
        String str1,
        String str2
    ) {

        int m = str1.length();
        int n = str2.length();

        int[][] dp =
            new int[m + 1][n + 1];

        // Build LCS table
        for (int i = 1; i <= m; i++) {

            for (int j = 1; j <= n; j++) {

                if (
                    str1.charAt(i - 1)
                    == str2.charAt(j - 1)
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

        StringBuilder result =
            new StringBuilder();

        int i = m;
        int j = n;

        while (i > 0 && j > 0) {

            char c1 = str1.charAt(i - 1);
            char c2 = str2.charAt(j - 1);

            if (c1 == c2) {

                result.append(c1);

                i--;
                j--;

            } else if (
                dp[i - 1][j]
                >=
                dp[i][j - 1]
            ) {

                result.append(c1);
                i--;

            } else {

                result.append(c2);
                j--;
            }
        }

        while (i > 0) {

            result.append(
                str1.charAt(i - 1)
            );

            i--;
        }

        while (j > 0) {

            result.append(
                str2.charAt(j - 1)
            );

            j--;
        }

        return result.reverse().toString();
    }
}
```

Bài này dạy một skill quan trọng:

> DP table không chỉ dùng để lấy score; nó còn chứa decision history để reconstruct solution.

---

# PART X — TOP-DOWN TEMPLATE

# 62. DFS + Memoization

Two-Sequence DP rất tự nhiên khi viết recursive.

Generic structure:

```java
Integer[][] memo;

int dfs(
    String a,
    String b,
    int i,
    int j
) {

    if (i == a.length()) {
        return ...;
    }

    if (j == b.length()) {
        return ...;
    }

    if (memo[i][j] != null) {
        return memo[i][j];
    }

    if (a.charAt(i) == b.charAt(j)) {

        return memo[i][j] =
            ... dfs(i + 1, j + 1);

    } else {

        return memo[i][j] =
            combine(
                dfs(i + 1, j),
                dfs(i, j + 1),
                dfs(i + 1, j + 1)
            );
    }
}
```

Điểm khác với Bottom-up:

Top-down thường define:

```text
state từ suffix
```

Ví dụ:

```text
dfs(i,j)
=
answer cho:

a[i ... end]
b[j ... end]
```

Bottom-up thường dùng:

```text
prefix
```

Cả hai đều đúng.

---

# 63. Top-down LCS

```java
class Solution {

    Integer[][] memo;

    public int longestCommonSubsequence(
        String text1,
        String text2
    ) {

        memo =
            new Integer[
                text1.length()
            ][
                text2.length()
            ];

        return dfs(
            text1,
            text2,
            0,
            0
        );
    }

    private int dfs(
        String a,
        String b,
        int i,
        int j
    ) {

        if (
            i == a.length()
            || j == b.length()
        ) {
            return 0;
        }

        if (memo[i][j] != null) {
            return memo[i][j];
        }

        if (
            a.charAt(i)
            == b.charAt(j)
        ) {

            return memo[i][j] =
                1 + dfs(
                    a,
                    b,
                    i + 1,
                    j + 1
                );
        }

        return memo[i][j] =
            Math.max(
                dfs(
                    a,
                    b,
                    i + 1,
                    j
                ),
                dfs(
                    a,
                    b,
                    i,
                    j + 1
                )
            );
    }
}
```

---

# 64. Bottom-up vs Top-down Mental Model

Top-down:

```text
dfs(i,j)

sequence suffix bắt đầu tại i,j
```

Bottom-up:

```text
dp[i][j]

sequence prefix dài i,j
```

Hai cách thường mirror nhau:

```text
Top-down:

i + 1
j + 1

Bottom-up:

i - 1
j - 1
```

---

# PART XI — SPACE OPTIMIZATION

# 65. Tại sao có thể optimize từ O(mn) xuống O(n)?

Trong nhiều Two-Sequence DP:

```text
dp[i][j]
```

chỉ phụ thuộc vào:

```text
previous row
current row
```

Ví dụ LCS:

```text
dp[i-1][j]
dp[i][j-1]
dp[i-1][j-1]
```

Do đó ta không cần giữ toàn bộ matrix.

Có thể dùng:

```text
prev[]
curr[]
```

Space:

```text
O(n)
```

---

# 66. LCS Space Optimized

```java
class Solution {

    public int longestCommonSubsequence(
        String text1,
        String text2
    ) {

        if (
            text1.length()
            <
            text2.length()
        ) {

            String temp = text1;
            text1 = text2;
            text2 = temp;
        }

        int m = text1.length();
        int n = text2.length();

        int[] prev = new int[n + 1];

        for (int i = 1; i <= m; i++) {

            int[] curr =
                new int[n + 1];

            for (int j = 1; j <= n; j++) {

                if (
                    text1.charAt(i - 1)
                    ==
                    text2.charAt(j - 1)
                ) {

                    curr[j] =
                        1 + prev[j - 1];

                } else {

                    curr[j] =
                        Math.max(
                            prev[j],
                            curr[j - 1]
                        );
                }
            }

            prev = curr;
        }

        return prev[n];
    }
}
```

Space:

```text
O(min(m,n))
```

nếu ta đặt shorter string làm dimension thứ hai.

---

# 67. Khi nào KHÔNG nên space optimize?

Nếu đề yêu cầu:

```text
reconstruct actual sequence
```

ví dụ:

```text
return LCS string

return edit operations

return shortest common supersequence
```

thì full matrix thường dễ hơn.

Vì reconstruction cần biết các decisions trước đó.

Quy tắc:

```text
only need answer value
→ optimize space

need actual path/string
→ giữ full DP table
```

---

# PART XII — MASTER TEMPLATES

# 68. Template A — Max Common Subsequence

```java
int[][] dp = new int[m + 1][n + 1];

for (int i = 1; i <= m; i++) {

    for (int j = 1; j <= n; j++) {

        if (
            a.charAt(i - 1)
            == b.charAt(j - 1)
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
```

Ứng dụng:

```text
LCS
Uncrossed Lines
Shortest Common Supersequence
Delete Operation
```

---

# 69. Template B — Minimum Transformation Cost

```java
for (int i = 0; i <= m; i++) {
    dp[i][0] = base(i);
}

for (int j = 0; j <= n; j++) {
    dp[0][j] = base(j);
}

for (int i = 1; i <= m; i++) {

    for (int j = 1; j <= n; j++) {

        if (a[i - 1] == b[j - 1]) {

            dp[i][j] =
                dp[i - 1][j - 1];

        } else {

            dp[i][j] =
                min(
                    dp[i - 1][j],
                    dp[i][j - 1],
                    dp[i - 1][j - 1]
                )
                + cost;
        }
    }
}
```

Ứng dụng:

```text
Edit Distance
weighted edit distance
minimum ASCII delete
```

---

# 70. Template C — Counting Ways

```java
dp[0][0] = 1;

for (...) {

    if (match) {

        dp[i][j] =
            useCurrent
            +
            skipCurrent;

    } else {

        dp[i][j] =
            skipCurrent;
    }
}
```

Ứng dụng:

```text
Distinct Subsequences
```

---

# 71. Template D — Boolean Two-Sequence DP

```java
boolean[][] dp =
    new boolean[m + 1][n + 1];

dp[0][0] = true;

for (...) {

    dp[i][j] =
        condition1
        ||
        condition2
        ||
        condition3;
}
```

Ứng dụng:

```text
Interleaving String
Wildcard Matching
Regular Expression Matching
```

---

# PART XIII — HOW TO DERIVE RECURRENCE

# 72. Không học thuộc recurrence

Khi gặp bài, hãy nhìn:

```text
current prefix ends at:

a[i-1]
b[j-1]
```

Sau đó hỏi:

```text
Nếu hai character này match thì sao?

Nếu không match thì operation nào
có thể loại bỏ uncertainty?
```

Ví dụ:

## LCS

Mismatch:

```text
bỏ một trong hai
```

→ max.

## Edit Distance

Mismatch:

```text
insert
delete
replace
```

→ min.

## Distinct Subsequences

Match:

```text
use
skip
```

→ sum.

## Interleaving

Current target character có thể đến từ:

```text
s1
hoặc
s2
```

→ OR.

Điều bạn nên nhớ không phải recurrence, mà là:

```text
objective → operator
```

---

# 73. Objective → Operator

| Objective         | Operator |   |   |
| ----------------- | -------- | - | - |
| longest / maximum | `max`    |   |   |
| minimum cost      | `min`    |   |   |
| number of ways    | `+`      |   |   |
| existence         | `        |   | ` |
| all conditions    | `&&`     |   |   |

Đây là một heuristic cực kỳ hữu ích.

---

# PART XIV — CÁC CẠNH TRONG DP GRID NGHĨA LÀ GÌ?

# 74. Movement Interpretation

Trong Two-Sequence DP:

```text
dp[i-1][j-1]
```

thường có nghĩa:

```text
consume cả hai sequence
```

Diagonal:

```text
↖
```

---

```text
dp[i-1][j]
```

thường có nghĩa:

```text
consume sequence 1
không consume sequence 2
```

Move up.

---

```text
dp[i][j-1]
```

thường nghĩa:

```text
consume sequence 2
không consume sequence 1
```

Move left.

---

# 75. Grid View cực hữu ích

```text
            j-1         j

i-1       diagonal     up-state

i         left-state   current
```

Hay:

```text
                dp[i-1][j]
                     ↓

dp[i-1][j-1]  →   dp[i][j]
     ↘

          dp[i][j-1] →
```

Nên tự hỏi:

```text
move diagonal = consume gì?

move up = bỏ / consume gì?

move left = bỏ / consume gì?
```

Nếu trả lời được, recurrence thường rất rõ.

---

# PART XV — BASE CASE PATTERNS

# 76. Base Case phụ thuộc meaning của empty sequence

Đây là phần cực kỳ quan trọng.

---

## LCS

```text
LCS("", s) = 0
```

nên:

```text
dp[0][j] = 0
dp[i][0] = 0
```

---

## Edit Distance

```text
"" → "abc"
```

cần 3 inserts.

```text
dp[0][j] = j
```

---

## Distinct Subsequences

Tạo empty target:

```text
anything → ""
```

có một cách:

```text
choose nothing
```

nên:

```text
dp[i][0] = 1
```

---

## Boolean matching

```text
empty vs empty
```

thường:

```text
true
```

nhưng empty string vs non-empty pattern phụ thuộc pattern semantics.

Ví dụ Wildcard:

```text
""
vs
"***"
```

true.

Regex:

```text
""
vs
"a*b*c*"
```

cũng true.

Do đó không được mechanically set toàn bộ first row = false.

---

# PART XVI — COMMON BUGS

# 77. Bug 1 — `i` vs `i-1`

Nếu state là:

```text
dp[i][j]
=
answer cho prefix length i,j
```

thì current character là:

```java
s1.charAt(i - 1)
s2.charAt(j - 1)
```

Không phải:

```java
s1.charAt(i)
```

---

# 78. Bug 2 — Base case chưa đầy đủ

Edit Distance mà không initialize:

```text
dp[i][0]
dp[0][j]
```

thì recurrence không thể hoạt động.

---

# 79. Bug 3 — Nhầm subsequence với substring

LCS:

```text
subsequence
```

nên mismatch được phép:

```text
skip character
```

Nếu là Longest Common Substring thì khi mismatch:

```text
dp[i][j] = 0
```

Hai bài rất khác nhau.

---

# 80. Longest Common Substring

Nếu muốn tìm longest common **substring**, state:

```text
dp[i][j]
=
length của common substring
ENDING tại:

s1[i-1]
s2[j-1]
```

Nếu match:

```text
dp[i][j]
=
1 + dp[i-1][j-1]
```

Nếu mismatch:

```text
dp[i][j] = 0
```

Ta giữ global max.

---

# 81. Java — Longest Common Substring

```java
public int longestCommonSubstring(
    String a,
    String b
) {

    int m = a.length();
    int n = b.length();

    int[][] dp =
        new int[m + 1][n + 1];

    int result = 0;

    for (int i = 1; i <= m; i++) {

        for (int j = 1; j <= n; j++) {

            if (
                a.charAt(i - 1)
                == b.charAt(j - 1)
            ) {

                dp[i][j] =
                    1 + dp[i - 1][j - 1];

                result =
                    Math.max(
                        result,
                        dp[i][j]
                    );

            } else {

                dp[i][j] = 0;
            }
        }
    }

    return result;
}
```

So sánh:

```text
LCS:
mismatch → max(up,left)

Longest Common Substring:
mismatch → 0
```

Đây là một distinction rất quan trọng.

---

# PART XVII — LCS + PALINDROME CONNECTION

# 82. Longest Palindromic Subsequence

Một string:

```text
s = "bbbab"
```

Reverse:

```text
rev = "babbb"
```

Longest Palindromic Subsequence có thể được nhìn như:

```text
LCS(s, reverse(s))
```

Do đó:

```text
LPS
=
LCS(
    s,
    reverse(s)
)
```

---

# 83. LeetCode 516 bằng Two-Sequence DP

```java
class Solution {

    public int longestPalindromeSubseq(
        String s
    ) {

        String reversed =
            new StringBuilder(s)
                .reverse()
                .toString();

        int n = s.length();

        int[][] dp =
            new int[n + 1][n + 1];

        for (int i = 1; i <= n; i++) {

            for (int j = 1; j <= n; j++) {

                if (
                    s.charAt(i - 1)
                    ==
                    reversed.charAt(j - 1)
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

        return dp[n][n];
    }
}
```

Bài này có thể solve bằng:

```text
Interval DP
```

hoặc:

```text
Two-Sequence LCS DP
```

Đây là một ví dụ cho thấy một bài có thể có nhiều DP formulations.

---

# 84. Minimum Insertions to Make Palindrome

LeetCode 1312.

Nếu:

```text
L = Longest Palindromic Subsequence
```

thì những character không nằm trong LPS phải được matched bằng insertion.

Do đó:

```text
minimum insertions
=
n - LPS
```

Và:

```text
LPS = LCS(s, reverse(s))
```

---

# PART XVIII — RECOGNITION TABLE

# 85. Nhìn đề → nghĩ pattern nào?

| Đề bài hỏi                                | Pattern nên nghĩ              |
| ----------------------------------------- | ----------------------------- |
| longest common ordered elements           | LCS                           |
| convert A → B                             | Edit Distance                 |
| only delete to make equal                 | LCS / Delete DP               |
| count ways source forms target            | Distinct Subsequences         |
| mix two strings preserving order          | Interleaving                  |
| string matches wildcard                   | Boolean pattern DP            |
| regex `.` and `*`                         | Regex DP                      |
| construct shortest string containing both | LCS + reconstruction          |
| common contiguous sequence                | Longest Common Substring      |
| palindrome subsequence                    | Interval DP hoặc LCS(reverse) |

---

# PART XIX — TWO-SEQUENCE DP CHECKLIST

# 86. Checklist interview

Khi gặp bài, hãy đi theo thứ tự:

```text
1. Có hai sequence không?

2. Có đang so sánh prefix/suffix của hai sequence không?

3. Define:
   dp[i][j] nghĩa chính xác là gì?

4. Current elements:
   a[i-1], b[j-1]

5. Nếu match:
   transition là gì?

6. Nếu mismatch:
   bỏ cái nào?
   transform thế nào?
   branch ra sao?

7. Objective:
   max?
   min?
   count?
   boolean?

8. Empty sequence nghĩa là gì?

9. Dependency:
   diagonal?
   up?
   left?

10. Có cần reconstruct answer không?

11. Nếu không reconstruct:
   có space optimize được không?
```

---

# PART XX — INTERVIEW EXPLANATION TEMPLATE

# 87. Cách nói khi phỏng vấn

Một opening rất chuẩn:

> I'll use dynamic programming over prefixes of the two strings.

Sau đó:

> Let `dp[i][j]` represent the answer for the first `i` characters of the first string and the first `j` characters of the second string.

Sau đó xét recurrence:

> I'll compare the last characters of those prefixes: `s1[i - 1]` and `s2[j - 1]`.

Nếu match:

> If they match, both characters can be consumed together, so the transition comes from `dp[i - 1][j - 1]`.

Nếu mismatch:

> Otherwise, I'll consider the valid operations that remove the mismatch.

Cuối cùng:

> There are `m × n` states and each state takes constant time, so the time complexity is `O(mn)` and the space complexity is `O(mn)`.

Đây là format explain rất mạnh cho phần lớn Two-String DP.

---

# PART XXI — LEETCODE ROADMAP

# 88. Level 1 — Fundamental

Học theo thứ tự:

### 1. 1143 — Longest Common Subsequence

Học:

```text
dp[i][j]
match / mismatch
max
```

### 2. 1035 — Uncrossed Lines

Học:

```text
nhận diện LCS ẩn dưới array problem
```

### 3. Longest Common Substring

Học:

```text
subsequence vs substring
```

---

# 89. Level 2 — Transformation

### 4. 583 — Delete Operation for Two Strings

Học:

```text
LCS transformation
```

### 5. 72 — Edit Distance

Học:

```text
insert
delete
replace
```

### 6. 712 — Minimum ASCII Delete Sum

Học:

```text
weighted transformation
```

---

# 90. Level 3 — Counting / Boolean

### 7. 115 — Distinct Subsequences

Học:

```text
count DP
use / skip
```

### 8. 97 — Interleaving String

Học:

```text
boolean DP
multiple sources
```

---

# 91. Level 4 — Pattern Matching

### 9. 44 — Wildcard Matching

Học:

```text
*
zero or more arbitrary characters
```

### 10. 10 — Regular Expression Matching

Học:

```text
complex pattern semantics
zero / repeated use
```

---

# 92. Level 5 — Reconstruction / Derived Problems

### 11. 1092 — Shortest Common Supersequence

Học:

```text
DP reconstruction
```

### 12. 516 — Longest Palindromic Subsequence

Solve bằng:

```text
LCS(s, reverse(s))
```

### 13. 1312 — Minimum Insertion Steps to Make a String Palindrome

Học relation:

```text
n - LPS
```

---

# PART XXII — CÁC BÀI NÊN LUYỆN

Một list tốt:

```text
1143. Longest Common Subsequence
1035. Uncrossed Lines
72. Edit Distance
583. Delete Operation for Two Strings
712. Minimum ASCII Delete Sum for Two Strings
115. Distinct Subsequences
97. Interleaving String
44. Wildcard Matching
10. Regular Expression Matching
1092. Shortest Common Supersequence
516. Longest Palindromic Subsequence
1312. Minimum Insertion Steps to Make a String Palindrome
392. Is Subsequence
524. Longest Word in Dictionary through Deleting
1458. Max Dot Product of Two Subsequences
1771. Maximize Palindrome Length From Subsequences
```

---

# PART XXIII — MASTER SUMMARY

Two-Sequence DP gần như luôn bắt đầu bằng:

```text
dp[i][j]
```

với ý nghĩa:

```text
answer của:
sequence1 prefix i
sequence2 prefix j
```

Current elements:

```text
a[i-1]
b[j-1]
```

Ba transition phổ biến:

```text
dp[i-1][j-1]
→ consume both

dp[i-1][j]
→ consume first

dp[i][j-1]
→ consume second
```

Sau đó objective quyết định operator:

```text
Longest
→ max

Minimum cost
→ min

Number of ways
→ +

Can / cannot
→ OR
```

---

# 94. Cheat Sheet cuối cùng

```text
LCS
-----------------------------
match:
    1 + diagonal

mismatch:
    max(up, left)
```

```text
EDIT DISTANCE
-----------------------------
match:
    diagonal

mismatch:
    1 + min(
        diagonal,   replace
        up,         delete
        left        insert
    )
```

```text
DISTINCT SUBSEQUENCES
-----------------------------
match:
    use + skip

    diagonal + up

mismatch:
    skip

    up
```

```text
INTERLEAVING
-----------------------------
from s1:
    up && match

from s2:
    left && match

result:
    OR
```

```text
WILDCARD '*'
-----------------------------
match empty:
    left

consume character:
    up

result:
    left OR up
```

```text
REGEX 'x*'
-----------------------------
zero occurrences:
    dp[i][j-2]

one/more:
    dp[i-1][j]
    nếu x match current char
```

---

# 95. Công thức tư duy quan trọng nhất

Khi gặp hai sequence:

```text
A
B
```

hãy nghĩ:

```text
       dp[i][j]

       ↓

What does this state mean?

       ↓

Compare:

A[i-1]
B[j-1]

       ↓

MATCH?
 /   \
yes   no

       ↓

What choices remain?

       ↓

max / min / sum / boolean
```

Nếu làm được chuỗi reasoning này, bạn không cần memorize phần lớn recurrence.

---

# 96. Mối liên hệ với các DP pattern khác

Bạn có thể nhìn toàn bộ các pattern đã học như sau:

```text
0/1 Knapsack

dp[i][capacity]

→ process items
→ take / skip
```

```text
Interval DP

dp[left][right]

→ process a range
→ shrink / split / choose pivot
```

```text
Two-Sequence DP

dp[i][j]

→ process two prefixes
→ consume first / second / both
```

Ba pattern này khác nhau chủ yếu ở:

```text
"What do the dimensions represent?"
```

Đây là mindset rất quan trọng khi học DP.

---

# 97. Template Java nên thuộc

```java
int m = s1.length();
int n = s2.length();

int[][] dp =
    new int[m + 1][n + 1];

// initialize base cases

for (int i = 1; i <= m; i++) {

    for (int j = 1; j <= n; j++) {

        char c1 =
            s1.charAt(i - 1);

        char c2 =
            s2.charAt(j - 1);

        if (c1 == c2) {

            dp[i][j] =
                transitionWhenMatch(
                    dp[i - 1][j - 1]
                );

        } else {

            dp[i][j] =
                combine(
                    dp[i - 1][j],
                    dp[i][j - 1],
                    dp[i - 1][j - 1]
                );
        }
    }
}

return dp[m][n];
```

Không nên thuộc implementation cụ thể của từng bài.

Hãy thuộc framework:

```text
STATE
↓
BASE CASE
↓
MATCH
↓
MISMATCH
↓
OBJECTIVE OPERATOR
↓
ITERATION ORDER
↓
COMPLEXITY
```

Đó mới là reusable pattern của **Two-Sequence / String DP**.
