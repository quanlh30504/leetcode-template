# SLIDING WINDOW — PATTERNS, TEMPLATES & INTERVIEW GUIDE

## 1. Sliding Window là gì?

Sliding Window là kỹ thuật dùng để xử lý các bài toán liên quan đến:

* **subarray** — đoạn liên tiếp trong array
* **substring** — đoạn liên tiếp trong string

Thay vì xét tất cả:

```text
[left ... right]
```

bằng 2 vòng `for` với độ phức tạp thường là:

```text
O(n²)
```

ta giữ một cửa sổ:

```text
[left ... right]
```

và di chuyển nó dần:

```text
right -> mở rộng window
left  -> thu nhỏ window
```

Mỗi phần tử thường:

* được `right` đưa vào window tối đa 1 lần
* được `left` loại khỏi window tối đa 1 lần

nên rất nhiều bài có complexity:

```text
O(n)
```

---

# 2. Khi nào nghĩ đến Sliding Window?

Một dấu hiệu cực mạnh:

> Bài toán hỏi về một **đoạn liên tiếp**.

Ví dụ:

```text
longest substring...
shortest subarray...
maximum sum of k consecutive...
number of subarrays...
substring containing...
at most K...
without repeating...
```

Keyword thường gặp:

```text
substring
subarray
consecutive
contiguous
longest
shortest
maximum
minimum
at most K
exactly K
contains
distinct
frequency
```

---

# 3. Mental model quan trọng nhất

Ta thường có:

```java
int left = 0;

for (int right = 0; right < n; right++) {

    // 1. add nums[right] vào window

    while (window không hợp lệ) {

        // 2. remove nums[left]

        left++;
    }

    // 3. window hiện tại hợp lệ
    // update result
}
```

Đây chính là template quan trọng nhất của Sliding Window.

Có thể hiểu:

```text
right = explore
left  = repair
```

`right` liên tục khám phá window lớn hơn.

Nếu window vi phạm điều kiện:

```text
left
  ↓
[ ............. right ]
```

ta di chuyển `left` để sửa window.

---

# 4. Sliding Window có những pattern chính nào?

Có thể chia thành các nhóm lớn:

```text
Sliding Window
│
├── 1. Fixed-size Window
│
├── 2. Variable Window — Maximum/Longest
│
├── 3. Variable Window — Minimum/Shortest
│
├── 4. Frequency / Distinct Character Window
│
├── 5. At Most K Window
│
├── 6. Exactly K via AtMost
│
├── 7. Counting Subarrays
│
├── 8. Window Matching / Anagram
│
├── 9. Replacement Window
│
└── 10. Sliding Window + Monotonic Deque
```

Ta sẽ đi từng pattern.

---

# PART I — FIXED SIZE WINDOW

# 5. Pattern 1: Fixed-size Sliding Window

Đây là dạng Sliding Window dễ nhất.

## Dấu hiệu

Window có kích thước cố định:

```text
k
```

Ví dụ:

> Find maximum sum of any subarray of size `k`.

---

## Brute Force

Array:

```text
[1, 3, 2, 6, -1, 4]
```

`k = 3`.

Ta tính:

```text
[1,3,2] = 6
[3,2,6] = 11
[2,6,-1] = 7
[6,-1,4] = 9
```

Nếu mỗi window lại tính sum từ đầu:

```text
O(n * k)
```

---

## Sliding Window idea

Window đầu:

```text
1 3 2
```

sum:

```text
6
```

Di chuyển một bước:

```text
1 3 2
  ↓ ↓ ↓
  3 2 6
```

Không cần tính lại.

Chỉ:

```text
remove 1
add 6
```

```text
newSum = oldSum - nums[left] + nums[right]
```

---

# Template

```java
long window = 0;

for (int right = 0; right < nums.length; right++) {

    window += nums[right];

    if (right >= k) {
        window -= nums[right - k];
    }

    if (right >= k - 1) {
        // window size == k
        result = Math.max(result, window);
    }
}
```

---

# Ví dụ: LeetCode 643

## Maximum Average Subarray I

Cho:

```text
nums = [1,12,-5,-6,50,3]
k = 4
```

Các window:

```text
[1,12,-5,-6]      sum = 2
[12,-5,-6,50]     sum = 51
[-5,-6,50,3]      sum = 42
```

Max sum:

```text
51
```

Average:

```text
51 / 4 = 12.75
```

Implementation:

```java
class Solution {
    public double findMaxAverage(int[] nums, int k) {
        long sum = 0;

        for (int i = 0; i < k; i++) {
            sum += nums[i];
        }

        long maxSum = sum;

        for (int right = k; right < nums.length; right++) {

            sum += nums[right];

            sum -= nums[right - k];

            maxSum = Math.max(maxSum, sum);
        }

        return (double) maxSum / k;
    }
}
```

Complexity:

```text
Time:  O(n)
Space: O(1)
```

---

# Khi nào dùng Fixed Window?

Nhận diện:

```text
size k
length k
k consecutive elements
k-day period
k characters
```

Bài luyện tập:

```text
643. Maximum Average Subarray I
1456. Maximum Number of Vowels in a Substring of Given Length
1343. Number of Sub-arrays of Size K and Average >= Threshold
2461. Maximum Sum of Distinct Subarrays With Length K
```

---

# PART II — VARIABLE WINDOW

# 6. Pattern 2: Longest Valid Window

Đây là pattern quan trọng nhất.

Ta muốn:

```text
maximum length
```

với một constraint nào đó.

Ví dụ:

```text
Longest substring without repeating characters
```

Mental model:

```text
expand right

if invalid:
    shrink left

when valid:
    update maximum
```

Template:

```java
int left = 0;
int result = 0;

for (int right = 0; right < n; right++) {

    add(right);

    while (!valid()) {
        remove(left);
        left++;
    }

    result = Math.max(
        result,
        right - left + 1
    );
}
```

---

# 7. LeetCode 3 — Longest Substring Without Repeating Characters

Input:

```text
s = "abcabcbb"
```

Output:

```text
3
```

Longest:

```text
"abc"
```

---

## Window condition

Window phải thỏa:

```text
không character nào xuất hiện > 1 lần
```

Ta có thể giữ:

```java
int[] freq = new int[128];
```

---

## Dry run

String:

```text
a b c a b c b b
```

Ban đầu:

```text
left = 0
```

### right = 0

```text
[a]
```

valid.

```text
max = 1
```

### right = 1

```text
[a b]
```

valid.

```text
max = 2
```

### right = 2

```text
[a b c]
```

valid.

```text
max = 3
```

### right = 3

Thêm `a`.

```text
[a b c a]
```

`a` xuất hiện 2 lần.

Invalid.

Ta shrink:

```text
remove s[left] = a
left++
```

Window:

```text
[b c a]
```

valid lại.

---

## Implementation

```java
class Solution {
    public int lengthOfLongestSubstring(String s) {

        int[] freq = new int[128];

        int left = 0;
        int result = 0;

        for (int right = 0; right < s.length(); right++) {

            char c = s.charAt(right);

            freq[c]++;

            while (freq[c] > 1) {

                freq[s.charAt(left)]--;

                left++;
            }

            result = Math.max(
                result,
                right - left + 1
            );
        }

        return result;
    }
}
```

---

# Tư duy

Invariant của window:

```text
every character frequency <= 1
```

Sau vòng:

```java
while (...)
```

window chắc chắn hợp lệ.

Ta có thể update:

```java
result = Math.max(result, right - left + 1);
```

---

# Template Frequency Window

Đây là template rất đáng nhớ:

```java
int[] freq = new int[128];

int left = 0;

for (int right = 0; right < s.length(); right++) {

    freq[s.charAt(right)]++;

    while (/* invalid */) {

        freq[s.charAt(left)]--;

        left++;
    }

    // valid window
}
```

---

# 8. Optimize LeetCode 3 bằng last seen index

Có một cách thậm chí gọn hơn.

Thay vì shrink từng bước:

```text
a b c a
```

ta biết `a` trước đó nằm ở đâu.

Giữ:

```java
lastIndex[c]
```

Khi gặp lại character:

```java
left = Math.max(
    left,
    lastIndex[c] + 1
);
```

Code:

```java
class Solution {
    public int lengthOfLongestSubstring(String s) {

        int[] last = new int[128];

        Arrays.fill(last, -1);

        int left = 0;
        int result = 0;

        for (int right = 0; right < s.length(); right++) {

            char c = s.charAt(right);

            left = Math.max(
                left,
                last[c] + 1
            );

            last[c] = right;

            result = Math.max(
                result,
                right - left + 1
            );
        }

        return result;
    }
}
```

Đây cũng là Sliding Window.

Chỉ khác:

```text
left jump trực tiếp
```

thay vì:

```text
left++ từng bước
```

---

# PART III — AT MOST K

# 9. Pattern 3: At Most K

Một trong những pattern phổ biến nhất:

```text
at most K ...
```

Ví dụ:

```text
at most K distinct characters
at most K zeros
at most K replacements
at most K bad elements
```

Template:

```java
int left = 0;

for (int right = 0; right < n; right++) {

    add(nums[right]);

    while (constraint > k) {

        remove(nums[left]);

        left++;
    }

    result = Math.max(
        result,
        right - left + 1
    );
}
```

---

# 10. LeetCode 904 — Fruit Into Baskets

Bản chất:

> Longest subarray containing at most 2 distinct values.

Ví dụ:

```text
[1,2,1,2,3]
```

Longest valid:

```text
[1,2,1,2]
```

length:

```text
4
```

---

## State cần lưu

Ta cần:

```text
frequency của từng loại fruit
distinct = số loại đang tồn tại
```

Window valid:

```text
distinct <= 2
```

---

## Implementation

```java
class Solution {
    public int totalFruit(int[] fruits) {

        Map<Integer, Integer> freq = new HashMap<>();

        int left = 0;
        int result = 0;

        for (int right = 0; right < fruits.length; right++) {

            int fruit = fruits[right];

            freq.put(
                fruit,
                freq.getOrDefault(fruit, 0) + 1
            );

            while (freq.size() > 2) {

                int leftFruit = fruits[left];

                freq.put(
                    leftFruit,
                    freq.get(leftFruit) - 1
                );

                if (freq.get(leftFruit) == 0) {
                    freq.remove(leftFruit);
                }

                left++;
            }

            result = Math.max(
                result,
                right - left + 1
            );
        }

        return result;
    }
}
```

---

# Generalization

Fruit Into Baskets thực chất chỉ là:

```text
Longest Subarray With At Most K Distinct Elements
```

Template:

```java
Map<Integer, Integer> freq = new HashMap<>();

int left = 0;

for (int right = 0; right < nums.length; right++) {

    freq.merge(nums[right], 1, Integer::sum);

    while (freq.size() > k) {

        int x = nums[left];

        freq.put(x, freq.get(x) - 1);

        if (freq.get(x) == 0) {
            freq.remove(x);
        }

        left++;
    }

    result = Math.max(
        result,
        right - left + 1
    );
}
```

---

# PART IV — BINARY / ZERO-FLIPPING WINDOW

# 11. LeetCode 1004 — Max Consecutive Ones III

Problem:

Cho binary array.

Ta được flip tối đa `k` số `0` thành `1`.

Tìm longest consecutive `1`.

Ví dụ:

```text
nums = [1,1,1,0,0,0,1,1,1,1,0]
k = 2
```

Bản chất:

> Tìm longest subarray chứa **at most k zeros**.

Đây là `At Most K`.

---

## State

Ta chỉ cần:

```java
zeroCount
```

Window valid:

```text
zeroCount <= k
```

Implementation:

```java
class Solution {
    public int longestOnes(int[] nums, int k) {

        int left = 0;
        int zeros = 0;
        int result = 0;

        for (int right = 0; right < nums.length; right++) {

            if (nums[right] == 0) {
                zeros++;
            }

            while (zeros > k) {

                if (nums[left] == 0) {
                    zeros--;
                }

                left++;
            }

            result = Math.max(
                result,
                right - left + 1
            );
        }

        return result;
    }
}
```

---

# Pattern transformation rất quan trọng

Nếu đề nói:

```text
Bạn được thay đổi tối đa K phần tử...
```

hãy thử biến đổi thành:

```text
Window được phép chứa tối đa K bad elements.
```

Ví dụ:

```text
flip at most K zeros

→ window contains at most K zeros
```

---

# PART V — REPLACEMENT WINDOW

# 12. LeetCode 424 — Longest Repeating Character Replacement

Problem:

Cho string uppercase.

Bạn có thể thay đổi tối đa:

```text
k
```

characters.

Tìm longest substring có thể biến thành toàn cùng một ký tự.

Ví dụ:

```text
s = "AABABBA"
k = 1
```

Output:

```text
4
```

---

# 13. Tư tưởng quan trọng

Giả sử window:

```text
A A B A
```

Length:

```text
4
```

Frequency:

```text
A = 3
B = 1
```

Nếu muốn cả window trở thành:

```text
AAAA
```

ta cần thay:

```text
1
```

character.

Công thức:

```text
replacementsNeeded
=
windowLength - maxFrequency
```

Vì:

```text
maxFrequency
```

là số character ta giữ nguyên.

Phần còn lại phải replace.

---

Window valid nếu:

```text
windowLength - maxFreq <= k
```

---

# Template đặc biệt

```java
int left = 0;
int maxFreq = 0;

for (int right = 0; right < s.length(); right++) {

    freq[s.charAt(right)]++;

    maxFreq = Math.max(
        maxFreq,
        freq[s.charAt(right)]
    );

    while (
        right - left + 1 - maxFreq > k
    ) {

        freq[s.charAt(left)]--;

        left++;
    }

    result = Math.max(
        result,
        right - left + 1
    );
}
```

---

# Implementation

```java
class Solution {
    public int characterReplacement(String s, int k) {

        int[] freq = new int[26];

        int left = 0;
        int maxFreq = 0;
        int result = 0;

        for (int right = 0; right < s.length(); right++) {

            int index =
                s.charAt(right) - 'A';

            freq[index]++;

            maxFreq = Math.max(
                maxFreq,
                freq[index]
            );

            while (
                right - left + 1 - maxFreq > k
            ) {

                freq[
                    s.charAt(left) - 'A'
                ]--;

                left++;
            }

            result = Math.max(
                result,
                right - left + 1
            );
        }

        return result;
    }
}
```

---

# Vì sao `maxFreq` không giảm khi left di chuyển?

Đây là điểm rất hay bị hỏi trong interview.

Giả sử:

```text
maxFreq = 5
```

sau khi left shrink thì tần suất thật sự có thể chỉ còn:

```text
4
```

Nhưng ta vẫn giữ `maxFreq = 5`.

Tại sao?

Bởi vì mục tiêu của ta không phải xác định chính xác:

```text
window hiện tại có valid tuyệt đối hay không
```

mà là tìm:

```text
maximum window length từng có thể đạt được.
```

`maxFreq` có thể là **stale maximum**.

Nhưng nó không làm ta bỏ lỡ đáp án tốt hơn.

Ta chỉ mở rộng result khi có khả năng đạt một window có size lớn hơn dựa trên một frequency maximum đã từng thấy.

Đây là optimization đặc biệt của bài 424.

Không nên copy tư tưởng stale value này sang mọi Sliding Window khác.

---

# PART VI — MINIMUM WINDOW

# 14. Pattern 4: Minimum / Shortest Valid Window

Pattern trước là:

```text
Longest valid window
```

Nhưng đôi khi đề hỏi:

```text
minimum length
shortest substring
smallest window
```

Khi đó logic hơi đảo ngược.

Ta:

```text
expand right cho đến khi valid

while valid:
    record answer
    shrink left
```

Template:

```java
int left = 0;

for (int right = 0; right < n; right++) {

    add(right);

    while (valid()) {

        result = Math.min(
            result,
            right - left + 1
        );

        remove(left);

        left++;
    }
}
```

Điểm cần nhớ:

### Longest

```java
while (invalid) {
    shrink();
}

update max;
```

### Shortest

```java
while (valid) {
    update min;
    shrink();
}
```

---

# 15. LeetCode 209 — Minimum Size Subarray Sum

Problem:

Cho positive integer array.

Tìm minimum length subarray có:

```text
sum >= target
```

Ví dụ:

```text
target = 7

nums =
[2,3,1,2,4,3]
```

Các candidate:

```text
[2,3,1,2] sum = 8 length = 4
[4,3]     sum = 7 length = 2
```

Answer:

```text
2
```

---

## Implementation

```java
class Solution {
    public int minSubArrayLen(
        int target,
        int[] nums
    ) {

        int left = 0;
        long sum = 0;

        int result = Integer.MAX_VALUE;

        for (int right = 0; right < nums.length; right++) {

            sum += nums[right];

            while (sum >= target) {

                result = Math.min(
                    result,
                    right - left + 1
                );

                sum -= nums[left];

                left++;
            }
        }

        return result == Integer.MAX_VALUE
            ? 0
            : result;
    }
}
```

---

# Vì sao bài này Sliding Window chạy được?

Điều cực kỳ quan trọng:

```text
nums[i] > 0
```

Vì array positive:

```text
expand right → sum không giảm
shrink left  → sum không tăng
```

Ta có monotonic property.

---

# Nếu có negative number thì sao?

Ví dụ:

```text
[5, -10, 20]
```

Khi mở rộng right:

```text
sum
```

có thể:

```text
tăng
↓
giảm
↓
tăng
```

Không còn monotonic.

Sliding Window thông thường có thể sai.

Đây là dấu hiệu cực quan trọng:

> Không phải cứ subarray là dùng Sliding Window.

---

# PART VII — WINDOW MATCHING

# 16. Pattern 5: Permutation / Anagram Window

Dạng bài:

```text
Find whether s contains a permutation of p
Find all anagrams of p in s
```

Đặc điểm:

Window có fixed size:

```text
p.length()
```

và cần so sánh frequency.

Các bài nổi tiếng:

```text
567. Permutation in String
438. Find All Anagrams in a String
```

---

# 17. LeetCode 567 — Permutation in String

Ví dụ:

```text
s1 = "ab"
s2 = "eidbaooo"
```

Trong `s2` có:

```text
"ba"
```

là permutation của:

```text
"ab"
```

Answer:

```text
true
```

---

## Idea

Frequency cần:

```text
a:1
b:1
```

Window luôn size:

```text
s1.length()
```

Ta kiểm tra frequency.

---

## Simple implementation

```java
class Solution {
    public boolean checkInclusion(
        String s1,
        String s2
    ) {

        if (s1.length() > s2.length()) {
            return false;
        }

        int[] need = new int[26];
        int[] window = new int[26];

        for (char c : s1.toCharArray()) {
            need[c - 'a']++;
        }

        int k = s1.length();

        for (int right = 0; right < s2.length(); right++) {

            window[
                s2.charAt(right) - 'a'
            ]++;

            if (right >= k) {

                window[
                    s2.charAt(right - k) - 'a'
                ]--;
            }

            if (right >= k - 1 &&
                Arrays.equals(need, window)) {

                return true;
            }
        }

        return false;
    }
}
```

Vì alphabet chỉ có:

```text
26
```

`Arrays.equals()` thực tế vẫn rất nhanh.

Complexity:

```text
O(26n)
≈ O(n)
```

---

# PART VIII — MINIMUM WINDOW SUBSTRING

# 18. LeetCode 76 — Minimum Window Substring

Đây là một trong những Sliding Window quan trọng nhất.

Problem:

```text
s = "ADOBECODEBANC"
t = "ABC"
```

Tìm substring nhỏ nhất chứa đủ:

```text
A
B
C
```

Answer:

```text
"BANC"
```

---

# 19. State

Ta có:

```java
need[c]
window[c]
```

Nhưng nếu mỗi lần lại loop cả alphabet để kiểm tra:

```text
window contains need?
```

thì không đẹp.

Ta dùng:

```text
required
formed
```

Ví dụ:

```text
t = "AABC"
```

Distinct characters:

```text
A B C
```

nên:

```text
required = 3
```

Khi:

```text
window[A] == need[A]
```

thì:

```text
formed++
```

---

# Implementation

```java
class Solution {
    public String minWindow(
        String s,
        String t
    ) {

        if (s.length() < t.length()) {
            return "";
        }

        int[] need = new int[128];

        int required = 0;

        for (char c : t.toCharArray()) {

            if (need[c] == 0) {
                required++;
            }

            need[c]++;
        }

        int[] window = new int[128];

        int formed = 0;
        int left = 0;

        int bestLength = Integer.MAX_VALUE;
        int bestStart = 0;

        for (int right = 0; right < s.length(); right++) {

            char c = s.charAt(right);

            window[c]++;

            if (
                need[c] > 0 &&
                window[c] == need[c]
            ) {
                formed++;
            }

            while (formed == required) {

                int length =
                    right - left + 1;

                if (length < bestLength) {

                    bestLength = length;

                    bestStart = left;
                }

                char leftChar =
                    s.charAt(left);

                window[leftChar]--;

                if (
                    need[leftChar] > 0 &&
                    window[leftChar] < need[leftChar]
                ) {
                    formed--;
                }

                left++;
            }
        }

        if (bestLength == Integer.MAX_VALUE) {
            return "";
        }

        return s.substring(
            bestStart,
            bestStart + bestLength
        );
    }
}
```

---

# Mental model

Ta mở rộng:

```text
ADOBEC
```

đã chứa:

```text
A B C
```

→ valid.

Bây giờ nhiệm vụ không phải mở rộng nữa.

Ta thử:

```text
shrink left
```

để xem còn valid không.

Đây là lý do:

```java
while (formed == required)
```

chứ không phải:

```java
if (...)
```

Ta muốn shrink **tối đa có thể**.

---

# PART IX — COUNTING SUBARRAYS

# 20. Pattern 6: Count valid subarrays

Đây là pattern khó nhưng cực kỳ quan trọng.

Ví dụ:

> Count number of subarrays with at most K distinct elements.

Giả sử sau khi shrink ta có valid window:

```text
[left ... right]
```

Nếu toàn bộ window valid thì các suffix kết thúc tại `right` cũng valid:

```text
[right]
[right-1 ... right]
[right-2 ... right]
...
[left ... right]
```

Số lượng là:

```text
right - left + 1
```

Đây là công thức cực mạnh.

---

# Template

```java
long count = 0;

int left = 0;

for (int right = 0; right < n; right++) {

    add(right);

    while (invalid()) {

        remove(left);

        left++;
    }

    count += right - left + 1;
}
```

---

# Ví dụ

Window:

```text
indices:

2 3 4 5
```

với:

```text
left  = 2
right = 5
```

Các valid subarray kết thúc tại 5:

```text
[5]
[4,5]
[3,4,5]
[2,3,4,5]
```

Count:

```text
5 - 2 + 1
= 4
```

---

# PART X — EXACTLY K

# 21. Pattern 7: Exactly K = AtMost(K) - AtMost(K-1)

Đây là một transformation cực kỳ quan trọng.

Nếu bài hỏi:

```text
exactly K
```

Sliding Window thường khó duy trì trực tiếp.

Nhưng:

```text
exactly(K)
=
atMost(K)
-
atMost(K - 1)
```

Ví dụ:

```text
number of subarrays with exactly 2 distinct integers
```

Ta tính:

```text
subarrays with <= 2 distinct
-
subarrays with <= 1 distinct
```

Phần còn lại chính xác là:

```text
2 distinct
```

---

# 22. LeetCode 992 — Subarrays with K Different Integers

Ví dụ:

```text
nums = [1,2,1,2,3]
k = 2
```

Ta cần exactly `2` distinct.

Solution:

```java
return atMost(nums, 2)
     - atMost(nums, 1);
```

---

## Code

```java
class Solution {

    public int subarraysWithKDistinct(
        int[] nums,
        int k
    ) {

        return atMost(nums, k)
             - atMost(nums, k - 1);
    }

    private int atMost(
        int[] nums,
        int k
    ) {

        Map<Integer, Integer> freq =
            new HashMap<>();

        int left = 0;
        int result = 0;

        for (
            int right = 0;
            right < nums.length;
            right++
        ) {

            freq.put(
                nums[right],
                freq.getOrDefault(
                    nums[right],
                    0
                ) + 1
            );

            while (freq.size() > k) {

                int x = nums[left];

                freq.put(
                    x,
                    freq.get(x) - 1
                );

                if (freq.get(x) == 0) {
                    freq.remove(x);
                }

                left++;
            }

            result +=
                right - left + 1;
        }

        return result;
    }
}
```

---

# Vì sao `right - left + 1`?

Nếu:

```text
[left ... right]
```

chứa ≤ K distinct thì:

```text
[left + 1 ... right]
[left + 2 ... right]
...
[right]
```

cũng chứa ≤ K distinct.

Do đó toàn bộ:

```text
right - left + 1
```

subarray đều hợp lệ.

---

# Pattern này còn dùng cho

```text
930. Binary Subarrays With Sum
1248. Count Number of Nice Subarrays
992. Subarrays with K Different Integers
```

---

# 23. Exactly Sum K — trick tương tự

Với non-negative / binary array:

```text
count(sum == goal)
=
count(sum <= goal)
-
count(sum <= goal - 1)
```

Ví dụ LeetCode:

```text
930. Binary Subarrays With Sum
```

Helper:

```java
private int atMost(int[] nums, int goal) {

    if (goal < 0) {
        return 0;
    }

    int left = 0;
    int sum = 0;
    int result = 0;

    for (int right = 0; right < nums.length; right++) {

        sum += nums[right];

        while (sum > goal) {

            sum -= nums[left];

            left++;
        }

        result += right - left + 1;
    }

    return result;
}
```

Sau đó:

```java
return atMost(nums, goal)
     - atMost(nums, goal - 1);
```

---

# PART XI — NICE SUBARRAY

# 24. LeetCode 1248 — Count Number of Nice Subarrays

Problem:

Tìm số subarray chứa chính xác:

```text
k odd numbers
```

Ta transform:

```text
odd number = 1
even number = 0
```

Problem trở thành:

```text
number of subarrays whose sum == k
```

Sau đó:

```text
exactly K odds
=
atMost(K odds)
-
atMost(K - 1 odds)
```

Code:

```java
class Solution {

    public int numberOfSubarrays(
        int[] nums,
        int k
    ) {

        return atMost(nums, k)
             - atMost(nums, k - 1);
    }

    private int atMost(
        int[] nums,
        int k
    ) {

        int left = 0;
        int odd = 0;
        int result = 0;

        for (int right = 0; right < nums.length; right++) {

            if (nums[right] % 2 != 0) {
                odd++;
            }

            while (odd > k) {

                if (nums[left] % 2 != 0) {
                    odd--;
                }

                left++;
            }

            result +=
                right - left + 1;
        }

        return result;
    }
}
```

---

# PART XII — SLIDING WINDOW + MONOTONIC DEQUE

# 25. Pattern 8: Maximum/Minimum trong mỗi Window

Problem nổi tiếng:

```text
239. Sliding Window Maximum
```

Cho:

```text
nums =
[1,3,-1,-3,5,3,6,7]

k = 3
```

Các windows:

```text
[1,3,-1]    max = 3
[3,-1,-3]   max = 3
[-1,-3,5]   max = 5
[-3,5,3]    max = 5
[5,3,6]     max = 6
[3,6,7]     max = 7
```

Output:

```text
[3,3,5,5,6,7]
```

---

# Vì sao không dùng biến `max` đơn giản?

Giả sử:

```text
[9, 5, 3]
```

max:

```text
9
```

Window slide:

```text
[5, 3, 1]
```

`9` rời window.

Ta phải tìm max lại.

Nếu scan cả window:

```text
O(k)
```

mỗi step.

Total:

```text
O(nk)
```

---

# Monotonic Deque

Ta giữ index sao cho value:

```text
decreasing
```

Ví dụ deque chứa:

```text
[9, 5, 3]
```

front luôn là maximum.

Khi thêm một số mới:

```text
6
```

mọi số phía sau nhỏ hơn 6:

```text
5
3
```

không còn giá trị.

Vì `6`:

* lớn hơn chúng
* lại xuất hiện sau chúng

nên trước khi `6` rời window, chúng không thể trở thành maximum.

Ta remove chúng.

---

# Template

```java
Deque<Integer> deque =
    new ArrayDeque<>();

for (int right = 0; right < n; right++) {

    while (
        !deque.isEmpty() &&
        nums[deque.peekLast()]
            <= nums[right]
    ) {
        deque.pollLast();
    }

    deque.offerLast(right);

    if (
        deque.peekFirst()
            <= right - k
    ) {
        deque.pollFirst();
    }

    if (right >= k - 1) {

        result.add(
            nums[deque.peekFirst()]
        );
    }
}
```

---

# Complete solution

```java
class Solution {
    public int[] maxSlidingWindow(
        int[] nums,
        int k
    ) {

        int n = nums.length;

        int[] result =
            new int[n - k + 1];

        Deque<Integer> deque =
            new ArrayDeque<>();

        int index = 0;

        for (int right = 0; right < n; right++) {

            while (
                !deque.isEmpty() &&
                nums[deque.peekLast()]
                    <= nums[right]
            ) {
                deque.pollLast();
            }

            deque.offerLast(right);

            int left =
                right - k + 1;

            if (
                deque.peekFirst() < left
            ) {
                deque.pollFirst();
            }

            if (right >= k - 1) {

                result[index++] =
                    nums[deque.peekFirst()];
            }
        }

        return result;
    }
}
```

Complexity:

```text
Time: O(n)
Space: O(k)
```

Mặc dù có `while`, mỗi index:

```text
enter deque once
leave deque once
```

nên total vẫn:

```text
O(n)
```

---

# PART XIII — WHILE VS IF

# 26. Một lỗi cực phổ biến

Nhiều người viết:

```java
if (windowInvalid()) {
    left++;
}
```

trong khi cần:

```java
while (windowInvalid()) {
    left++;
}
```

Tại sao?

Bởi vì remove một phần tử chưa chắc khiến window valid lại.

Ví dụ:

```text
window chứa 4 distinct
K = 2
```

Remove một character:

```text
có thể vẫn còn 3 distinct
```

nên phải tiếp tục shrink.

---

## Quy tắc

Nếu invariant yêu cầu:

```text
window PHẢI valid trước khi update result
```

thường dùng:

```java
while (...)
```

---

# 27. Khi nào `if` có thể dùng?

Một số optimized template cho longest window dùng:

```java
if (...)
```

để giữ window size không giảm.

Ví dụ có solution optimized của LeetCode 424.

Nhưng khi mới học nên ưu tiên:

```java
while
```

vì logic rõ ràng và khó sai hơn.

---

# PART XIV — CORE TEMPLATES

# 28. Template 1 — Fixed size

```java
int left = 0;

for (int right = 0; right < n; right++) {

    add(right);

    if (right - left + 1 > k) {

        remove(left);

        left++;
    }

    if (right - left + 1 == k) {

        updateAnswer();
    }
}
```

---

# 29. Template 2 — Longest valid window

```java
int left = 0;
int result = 0;

for (int right = 0; right < n; right++) {

    add(right);

    while (invalid()) {

        remove(left);

        left++;
    }

    result = Math.max(
        result,
        right - left + 1
    );
}
```

Think:

```text
expand
repair
record
```

---

# 30. Template 3 — Shortest valid window

```java
int left = 0;
int result = Integer.MAX_VALUE;

for (int right = 0; right < n; right++) {

    add(right);

    while (valid()) {

        result = Math.min(
            result,
            right - left + 1
        );

        remove(left);

        left++;
    }
}
```

Think:

```text
expand until valid
record
shrink aggressively
```

---

# 31. Template 4 — Count At Most K

```java
int left = 0;
long result = 0;

for (int right = 0; right < n; right++) {

    add(right);

    while (constraint > k) {

        remove(left);

        left++;
    }

    result +=
        right - left + 1;
}
```

---

# 32. Template 5 — Exactly K

```java
exactlyK(nums, k) {

    return atMost(nums, k)
         - atMost(nums, k - 1);
}
```

Đây là một trong những template đáng thuộc nhất.

---

# 33. Template 6 — Frequency Map

```java
Map<Character, Integer> freq =
    new HashMap<>();

int left = 0;

for (int right = 0; right < s.length(); right++) {

    char c = s.charAt(right);

    freq.put(
        c,
        freq.getOrDefault(c, 0) + 1
    );

    while (invalid()) {

        char removed =
            s.charAt(left);

        freq.put(
            removed,
            freq.get(removed) - 1
        );

        if (freq.get(removed) == 0) {
            freq.remove(removed);
        }

        left++;
    }
}
```

---

# 34. Array hay HashMap?

Nếu alphabet cố định:

```text
'a' - 'z'
'A' - 'Z'
ASCII
```

ưu tiên array:

```java
int[] freq = new int[26];
```

hoặc:

```java
int[] freq = new int[128];
```

Ưu điểm:

```text
faster
simpler
less overhead
```

Nếu key không giới hạn:

```text
integer
Unicode
arbitrary value
```

dùng:

```java
HashMap
```

---

# PART XV — CÁCH NHẬN DIỆN PATTERN

# 35. Decision Tree

Khi gặp bài liên quan substring/subarray, hỏi lần lượt:

## Question 1

Có phải:

```text
contiguous?
```

Nếu không:

```text
Sliding Window thường không đúng.
```

---

## Question 2

Window size cố định không?

Nếu:

```text
size k
```

→ Fixed Sliding Window.

---

## Question 3

Đề hỏi longest / maximum length?

Nếu có:

```text
expand right
while invalid → shrink
update max
```

---

## Question 4

Đề hỏi shortest / minimum length?

Nếu có:

```text
expand
while valid:
    update min
    shrink
```

---

## Question 5

Đề nói:

```text
at most K
```

→ giữ counter.

```java
while (counter > k)
```

---

## Question 6

Đề nói:

```text
exactly K
```

hãy thử:

```text
AtMost(K) - AtMost(K - 1)
```

---

## Question 7

Đề yêu cầu:

```text
count number of valid subarrays
```

hãy nghĩ đến:

```java
result += right - left + 1;
```

---

## Question 8

Đề hỏi max/min mỗi window?

→ Sliding Window + Monotonic Deque.

---

# PART XVI — INVARIANT

# 36. Sliding Window thực chất là bài toán giữ invariant

Khi code, đừng chỉ nghĩ:

```text
left và right chạy như thế nào?
```

Hãy hỏi:

> Invariant của window là gì?

Ví dụ LeetCode 3:

```text
No duplicate character
```

LeetCode 904:

```text
distinct <= 2
```

LeetCode 1004:

```text
zeros <= k
```

LeetCode 424:

```text
windowLength - maxFreq <= k
```

LeetCode 209:

```text
sum >= target
```

LeetCode 76:

```text
window contains all required chars
```

Một khi xác định được invariant, code Sliding Window thường gần như tự viết ra.

---

# PART XVII — SLIDING WINDOW KHÔNG PHẢI LÚC NÀO CŨNG DÙNG ĐƯỢC

# 37. Ví dụ nguy hiểm

Problem:

```text
Find shortest subarray with sum >= K
```

Nếu:

```text
nums[i] > 0
```

dùng Sliding Window.

Nhưng nếu:

```text
nums[i]
```

có thể negative:

```text
[2, -1, 2]
```

thì việc:

```text
expand right
```

không đảm bảo sum tăng.

Việc:

```text
shrink left
```

không đảm bảo sum giảm.

Invariant không còn monotonic.

Khi đó có thể phải dùng:

```text
prefix sum
HashMap
monotonic deque
binary search
```

tùy bài.

---

# 38. Sliding Window cần monotonic repair property

Ta thường cần property:

> Nếu window invalid, di chuyển `left` có thể đưa nó dần về valid.

Ví dụ:

```text
distinct > K
```

remove element khỏi trái:

```text
distinct không thể tăng
```

Perfect.

Ví dụ:

```text
number of zeros > K
```

remove element:

```text
zeros không thể tăng
```

Perfect.

Ví dụ positive sum:

```text
sum >= target
```

remove left:

```text
sum giảm
```

Perfect.

Nhưng với negative number:

```text
sum
```

không có monotonic property như vậy.

---

# PART XVIII — INTERVIEW EXPLANATION TEMPLATE

# 39. Cách explain Sliding Window bằng tiếng Anh

Bạn có thể dùng structure:

> Since the problem asks for a contiguous subarray, I would consider a sliding window approach.

Sau đó:

> I'll maintain two pointers, `left` and `right`, representing the current window.

Tiếp:

> The right pointer expands the window, while the left pointer shrinks it whenever the window violates the constraint.

Ví dụ LeetCode 3:

> The invariant I want to maintain is that every character appears at most once in the current window.

LeetCode 1004:

> I'll maintain the number of zeros inside the current window. Whenever the number of zeros exceeds `k`, I'll move the left pointer until the window becomes valid again.

Sau đó complexity:

> Each element enters and leaves the window at most once, so the time complexity is O(n), with O(1) extra space.

Đây là cách diễn giải rất chuẩn trong interview.

---

# PART XIX — PATTERN MAP

# 40. Nhóm các bài nổi tiếng

## Pattern A — Fixed Window

```text
643  Maximum Average Subarray I
1456 Maximum Number of Vowels in a Substring of Given Length
1343 Number of Sub-arrays of Size K and Average >= Threshold
2461 Maximum Sum of Distinct Subarrays With Length K
```

---

## Pattern B — Longest Valid Window

```text
3    Longest Substring Without Repeating Characters
904  Fruit Into Baskets
1004 Max Consecutive Ones III
1493 Longest Subarray of 1's After Deleting One Element
1695 Maximum Erasure Value
```

---

## Pattern C — At Most K

```text
904  Fruit Into Baskets
1004 Max Consecutive Ones III
340  Longest Substring with At Most K Distinct Characters
159  Longest Substring with At Most Two Distinct Characters
```

---

## Pattern D — Replacement

```text
424 Longest Repeating Character Replacement
1004 Max Consecutive Ones III
```

---

## Pattern E — Minimum Window

```text
209 Minimum Size Subarray Sum
76  Minimum Window Substring
1234 Replace the Substring for Balanced String
```

---

## Pattern F — Anagram / Permutation

```text
567 Permutation in String
438 Find All Anagrams in a String
```

---

## Pattern G — Exactly K / Counting

```text
992  Subarrays with K Different Integers
930  Binary Subarrays With Sum
1248 Count Number of Nice Subarrays
```

---

## Pattern H — Monotonic Deque

```text
239 Sliding Window Maximum
1438 Longest Continuous Subarray With Absolute Diff <= Limit
```

---

# PART XX — BÀI 1438: SLIDING WINDOW + 2 DEQUES

# 41. Longest Continuous Subarray With Absolute Diff <= Limit

Constraint:

```text
max(window) - min(window) <= limit
```

Ta cần biết liên tục:

```text
maximum
minimum
```

trong window.

Nếu mỗi step scan lại:

```text
O(n²)
```

Ta dùng:

```text
maxDeque
minDeque
```

`maxDeque`:

```text
decreasing
```

front = max.

`minDeque`:

```text
increasing
```

front = min.

Implementation:

```java
class Solution {
    public int longestSubarray(
        int[] nums,
        int limit
    ) {

        Deque<Integer> maxDeque =
            new ArrayDeque<>();

        Deque<Integer> minDeque =
            new ArrayDeque<>();

        int left = 0;
        int result = 0;

        for (int right = 0;
             right < nums.length;
             right++) {

            while (
                !maxDeque.isEmpty() &&
                nums[maxDeque.peekLast()]
                    < nums[right]
            ) {
                maxDeque.pollLast();
            }

            maxDeque.offerLast(right);

            while (
                !minDeque.isEmpty() &&
                nums[minDeque.peekLast()]
                    > nums[right]
            ) {
                minDeque.pollLast();
            }

            minDeque.offerLast(right);

            while (
                nums[maxDeque.peekFirst()]
                -
                nums[minDeque.peekFirst()]
                > limit
            ) {

                if (
                    maxDeque.peekFirst()
                    == left
                ) {
                    maxDeque.pollFirst();
                }

                if (
                    minDeque.peekFirst()
                    == left
                ) {
                    minDeque.pollFirst();
                }

                left++;
            }

            result = Math.max(
                result,
                right - left + 1
            );
        }

        return result;
    }
}
```

Đây là ví dụ rất đẹp của:

```text
Sliding Window
+
Data Structure
```

Sliding Window không nhất thiết chỉ dùng:

```text
sum
counter
HashMap
```

Nó có thể kết hợp:

```text
Deque
TreeMap
Heap
Frequency array
HashMap
```

---

# PART XXI — CÁC LỖI THƯỜNG GẶP

# 42. Sai window length

Window inclusive:

```text
[left ... right]
```

length:

```java
right - left + 1
```

Không phải:

```java
right - left
```

---

# 43. Quên remove state khi left++

Sai:

```java
while (invalid) {
    left++;
}
```

Đúng:

```java
while (invalid) {

    remove(nums[left]);

    left++;
}
```

---

# 44. Remove HashMap nhưng không xóa key có frequency 0

Sai:

```java
freq.put(x, freq.get(x) - 1);
```

Nếu dùng:

```java
freq.size()
```

để tính distinct thì phải:

```java
if (freq.get(x) == 0) {
    freq.remove(x);
}
```

---

# 45. Update answer sai vị trí

Longest valid window:

```java
while (invalid) {
    shrink();
}

result = max(...);
```

Không nên update trước khi repair window.

---

Minimum valid window:

```java
while (valid) {

    result = min(...);

    shrink();
}
```

Nếu update sau `while` thì window đã invalid.

---

# 46. Dùng Sliding Window cho array có negative number mà không chứng minh

Luôn tự hỏi:

```text
Nếu expand right thì constraint thay đổi monotonic không?

Nếu shrink left thì có sửa constraint theo một chiều không?
```

Nếu không:

```text
Sliding Window có thể không đúng.
```

---

# PART XXII — BIG PICTURE

# 47. Sliding Window thực ra chỉ có 3 câu hỏi

Khi làm một bài Sliding Window, hãy xác định:

## 1. State của window là gì?

Ví dụ:

```text
sum
number of zeros
number of distinct
frequency
maximum frequency
max/min
```

---

## 2. Window invalid khi nào?

Ví dụ:

```java
sum > k

zeros > k

distinct > k

freq[c] > 1

windowLength - maxFreq > k
```

---

## 3. Khi nào update answer?

### Longest

Sau khi repair:

```java
result = Math.max(
    result,
    right - left + 1
);
```

### Shortest

Trong khi valid:

```java
while (valid) {

    result = Math.min(...);

    shrink();
}
```

### Count

Sau khi repair:

```java
result +=
    right - left + 1;
```

Nếu trả lời được ba câu này thì gần như solution đã hoàn thành.

---

# 48. Master Template

Template mình khuyên dùng khi interview:

```java
int left = 0;

for (int right = 0; right < n; right++) {

    // 1. EXPAND
    add(right);

    // 2. REPAIR
    while (invalid()) {

        remove(left);

        left++;
    }

    // 3. PROCESS VALID WINDOW
    updateAnswer(
        left,
        right
    );
}
```

Hãy nhớ ba từ:

```text
EXPAND
REPAIR
PROCESS
```

---

# 49. Cách học Sliding Window hiệu quả

Không nên học 20 lời giải riêng lẻ.

Hãy học theo pattern.

Thứ tự mình khuyên luyện:

```text
Level 1
↓
643  Maximum Average Subarray I

Level 2
↓
3    Longest Substring Without Repeating Characters

Level 3
↓
1004 Max Consecutive Ones III

Level 4
↓
904  Fruit Into Baskets

Level 5
↓
424  Longest Repeating Character Replacement

Level 6
↓
209  Minimum Size Subarray Sum

Level 7
↓
567  Permutation in String
438  Find All Anagrams

Level 8
↓
76   Minimum Window Substring

Level 9
↓
930  Binary Subarrays With Sum
1248 Count Number of Nice Subarrays

Level 10
↓
992  Subarrays with K Different Integers

Level 11
↓
239  Sliding Window Maximum

Level 12
↓
1438 Longest Continuous Subarray With Absolute Diff <= Limit
```

Nếu bạn giải được nhóm trên và hiểu **vì sao template hoạt động**, phần lớn Sliding Window interview problems sẽ trở thành variation của những pattern quen thuộc.

---

# 50. Cheat Sheet cuối cùng

```text
FIXED SIZE
------------------------------
add right
if size > k:
    remove left

--------------------------------

LONGEST VALID WINDOW
------------------------------
add right

while invalid:
    remove left
    left++

ans = max(ans, windowSize)

--------------------------------

SHORTEST VALID WINDOW
------------------------------
add right

while valid:
    ans = min(ans, windowSize)
    remove left
    left++

--------------------------------

COUNT VALID SUBARRAYS
------------------------------
add right

while invalid:
    remove left
    left++

ans += windowSize

--------------------------------

EXACTLY K
------------------------------

exactly(K)
=
atMost(K)
-
atMost(K-1)

--------------------------------

WINDOW REPLACEMENT
------------------------------

replacementNeeded
=
windowSize
-
maxFrequency

--------------------------------

DISTINCT WINDOW
------------------------------

while distinct > K:
    shrink

--------------------------------

NO DUPLICATES
------------------------------

while freq[current] > 1:
    shrink

--------------------------------

SLIDING MAXIMUM
------------------------------

Sliding Window
+
Monotonic Deque
```

---

# 51. Một câu cần nhớ

Có thể coi phần lớn Sliding Window problems là việc hoàn thành câu sau:

```text
I maintain a window [left, right]
such that ____________________.
```

Ví dụ:

```text
such that it contains no duplicate characters.

such that it contains at most K zeros.

such that it contains at most K distinct values.

such that the required number of replacements
does not exceed K.

such that max - min <= limit.
```

Khoảng trống đó chính là **invariant**.

Khi tìm ra invariant, bạn thường đã tìm ra phần khó nhất của bài toán Sliding Window.
