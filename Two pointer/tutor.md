# TWO POINTERS — PATTERNS, TEMPLATES & APPLICATIONS

## 1. Two Pointers là gì?

**Two Pointers** không phải một thuật toán cụ thể.

Nó là một **kỹ thuật thiết kế thuật toán**:

> Thay vì duyệt tất cả các cặp phần tử bằng 2 vòng `for`, ta sử dụng hai con trỏ và di chuyển chúng dựa trên một invariant hoặc một tính chất đặc biệt của dữ liệu.

Ví dụ brute force:

```java
for (int i = 0; i < n; i++) {
    for (int j = i + 1; j < n; j++) {
        // check pair nums[i], nums[j]
    }
}
```

Complexity:

```text
O(n²)
```

Two Pointers trong nhiều trường hợp có thể giảm xuống:

```text
O(n)
```

hoặc:

```text
O(n log n)
```

nếu cần sort trước.

---

# 2. Ý tưởng quan trọng nhất của Two Pointers

Điểm quan trọng không phải là:

> "Có hai biến `left` và `right`."

Mà là:

> Sau mỗi lần di chuyển pointer, ta phải loại bỏ được một phần không gian tìm kiếm mà không làm mất đáp án.

Đây chính là **invariant / monotonic property** giúp Two Pointers hoạt động.

Ví dụ với sorted array:

```text
nums = [1, 2, 4, 6, 10]
target = 8
```

Ta đặt:

```text
left                 right
 ↓                     ↓
[1, 2, 4, 6, 10]
```

Sum:

```text
1 + 10 = 11 > 8
```

Vì array sorted:

```text
10 + bất kỳ số nào bên phải left
```

đều không thể nhỏ hơn nếu ta tăng left.

Vấn đề ở đây là `10` đang quá lớn.

Do đó:

```java
right--;
```

Ta loại bỏ `10` khỏi không gian tìm kiếm.

Đây chính là logic cốt lõi của Two Pointers.

---

# 3. Những pattern Two Pointers quan trọng

Có thể chia Two Pointers thành các pattern lớn:

```text
Two Pointers
│
├── 1. Opposite Direction
│      left → ← right
│
├── 2. Same Direction
│      slow → fast →
│
├── 3. Fast / Slow Pointer
│      Linked List / Cycle
│
├── 4. Merge Two Sequences
│      i → array1
│      j → array2
│
├── 5. Subsequence Pointer
│
├── 6. Expand / Shrink Window
│      Sliding Window
│
├── 7. Partition Pointer
│      QuickSort / Dutch Flag
│
├── 8. Interval Two Pointers
│
└── 9. K-Sum → Sort + Two Pointers
```

Ta sẽ lần lượt phân tích từng pattern.

---

# PART I — OPPOSITE-DIRECTION TWO POINTERS

# 4. Pattern 1 — Left / Right từ hai đầu

Đây là dạng Two Pointers kinh điển nhất.

```text
left                           right
 ↓                               ↓
[a, b, c, d, e, f, g, h]
```

Hai pointer di chuyển:

```text
left  →
right ←
```

Template:

```java
int left = 0;
int right = nums.length - 1;

while (left < right) {

    // process nums[left], nums[right]

    if (...) {
        left++;
    } else if (...) {
        right--;
    } else {
        left++;
        right--;
    }
}
```

Pattern này thường xuất hiện khi:

```text
1. Array sorted
2. Tìm pair
3. So sánh đối xứng
4. Cần maximize/minimize dựa trên hai đầu
```

---

# 5. Bài toán 167 — Two Sum II

## Problem

Cho sorted array:

```text
numbers = [2,7,11,15]
target = 9
```

Tìm 2 phần tử có tổng bằng `target`.

---

## Brute Force

```java
for (int i = 0; i < n; i++) {
    for (int j = i + 1; j < n; j++) {
        if (numbers[i] + numbers[j] == target) {
            ...
        }
    }
}
```

Complexity:

```text
O(n²)
```

---

# 6. Quan sát để dùng Two Pointers

Array đã sorted:

```text
2  7  11  15
↑           ↑
L           R
```

Tính:

```text
sum = numbers[left] + numbers[right]
```

Có 3 trường hợp.

### Case 1

```text
sum == target
```

Đã tìm thấy đáp án.

---

### Case 2

```text
sum < target
```

Ta cần tổng lớn hơn.

Vì array sorted:

```java
left++;
```

Ví dụ:

```text
2 + 7 = 9
```

Nếu sum là:

```text
2 + 5 = 7 < 9
```

Giảm `right` chỉ làm tổng nhỏ hơn nữa.

Do đó lựa chọn duy nhất có ích:

```text
increase left
```

---

### Case 3

```text
sum > target
```

Ta cần tổng nhỏ hơn:

```java
right--;
```

---

# 7. Implementation

```java
class Solution {
    public int[] twoSum(int[] numbers, int target) {

        int left = 0;
        int right = numbers.length - 1;

        while (left < right) {

            int sum = numbers[left] + numbers[right];

            if (sum == target) {
                return new int[]{left + 1, right + 1};
            }

            if (sum < target) {
                left++;
            } else {
                right--;
            }
        }

        return new int[]{-1, -1};
    }
}
```

Complexity:

```text
Time  : O(n)
Space : O(1)
```

---

# 8. Template Pair Sum trên sorted array

```java
int left = 0;
int right = nums.length - 1;

while (left < right) {

    int sum = nums[left] + nums[right];

    if (sum == target) {
        // answer
    }

    if (sum < target) {
        left++;
    } else {
        right--;
    }
}
```

Đây là một template cực kỳ quan trọng.

---

# 9. Pattern 2 — Palindrome

Ví dụ:

## LeetCode 125 — Valid Palindrome

```text
"A man, a plan, a canal: Panama"
```

Sau khi bỏ ký tự không phải letter/digit:

```text
amanaplanacanalpanama
```

Ta kiểm tra:

```text
left                               right
 ↓                                   ↓
 a m a n a p l a n a c a n a l p a n a m a
```

Nếu:

```text
s[left] != s[right]
```

→ không palindrome.

Nếu bằng:

```java
left++;
right--;
```

---

# 10. Implementation

```java
class Solution {
    public boolean isPalindrome(String s) {

        int left = 0;
        int right = s.length() - 1;

        while (left < right) {

            while (
                left < right &&
                !Character.isLetterOrDigit(s.charAt(left))
            ) {
                left++;
            }

            while (
                left < right &&
                !Character.isLetterOrDigit(s.charAt(right))
            ) {
                right--;
            }

            char a = Character.toLowerCase(s.charAt(left));
            char b = Character.toLowerCase(s.charAt(right));

            if (a != b) {
                return false;
            }

            left++;
            right--;
        }

        return true;
    }
}
```

---

# 11. Template Palindrome

```java
int left = 0;
int right = s.length() - 1;

while (left < right) {

    if (s.charAt(left) != s.charAt(right)) {
        return false;
    }

    left++;
    right--;
}

return true;
```

---

# 12. Bài 680 — Valid Palindrome II

Problem:

Có thể xóa tối đa **1 character**.

Ví dụ:

```text
abca
```

Ta có:

```text
a b c a
↑     ↑
L     R
```

`a == a`

→ move both.

```text
b c
↑ ↑
L R
```

`b != c`.

Tại đây ta được quyền xóa một character.

Có hai khả năng:

```text
xóa b
```

kiểm tra:

```text
c
```

hoặc:

```text
xóa c
```

kiểm tra:

```text
b
```

Tức là:

```java
isPalindrome(left + 1, right)
||
isPalindrome(left, right - 1)
```

Implementation:

```java
class Solution {

    public boolean validPalindrome(String s) {

        int left = 0;
        int right = s.length() - 1;

        while (left < right) {

            if (s.charAt(left) != s.charAt(right)) {
                return check(s, left + 1, right)
                    || check(s, left, right - 1);
            }

            left++;
            right--;
        }

        return true;
    }

    private boolean check(String s, int left, int right) {

        while (left < right) {

            if (s.charAt(left) != s.charAt(right)) {
                return false;
            }

            left++;
            right--;
        }

        return true;
    }
}
```

Complexity:

```text
O(n)
```

Không phải `O(n²)` vì helper chỉ gọi tại mismatch đầu tiên.

---

# PART II — SAME-DIRECTION TWO POINTERS

# 13. Pattern 3 — Read Pointer / Write Pointer

Hai pointer cùng đi từ trái sang phải.

```text
slow
 ↓
[a b c d e f]
 ↑
fast
```

Hay gọi là:

```text
read pointer
write pointer
```

Ý tưởng:

```text
fast = đọc dữ liệu
slow = vị trí ghi kết quả
```

Pattern này cực kỳ phổ biến với:

```text
remove
compress
deduplicate
move
filter
in-place modification
```

---

# 14. Template

```java
int slow = 0;

for (int fast = 0; fast < nums.length; fast++) {

    if (shouldKeep(nums[fast])) {

        nums[slow] = nums[fast];
        slow++;
    }
}
```

Ý nghĩa:

```text
[0, slow)
```

luôn chứa dữ liệu hợp lệ.

Đây chính là invariant.

---

# 15. LeetCode 283 — Move Zeroes

Problem:

```text
nums = [0,1,0,3,12]
```

Output:

```text
[1,3,12,0,0]
```

Không được tạo array mới.

---

# 16. Ý tưởng Read / Write

Ta muốn các số khác 0 đứng đầu array.

```text
write = vị trí tiếp theo dành cho non-zero
read  = scan toàn array
```

Ban đầu:

```text
[0,1,0,3,12]
 ↑
write
 ↑
read
```

`0` → skip.

read tới `1`.

```text
[0,1,0,3,12]
 ↑ ↑
 W R
```

ghi:

```java
nums[write] = nums[read];
```

array:

```text
[1,1,0,3,12]
```

sau đó:

```text
write = 1
```

Tiếp tục.

Cuối cùng:

```text
[1,3,12,3,12]
       ↑
      write
```

Phần:

```text
[0, write)
```

là các phần tử non-zero hợp lệ.

Ta fill phần còn lại bằng zero.

---

# 17. Implementation

```java
class Solution {
    public void moveZeroes(int[] nums) {

        int write = 0;

        for (int read = 0; read < nums.length; read++) {

            if (nums[read] != 0) {
                nums[write] = nums[read];
                write++;
            }
        }

        while (write < nums.length) {
            nums[write] = 0;
            write++;
        }
    }
}
```

Complexity:

```text
Time  O(n)
Space O(1)
```

---

# 18. Một implementation khác dùng swap

```java
class Solution {
    public void moveZeroes(int[] nums) {

        int slow = 0;

        for (int fast = 0; fast < nums.length; fast++) {

            if (nums[fast] != 0) {

                int temp = nums[slow];
                nums[slow] = nums[fast];
                nums[fast] = temp;

                slow++;
            }
        }
    }
}
```

Invariant:

```text
[0, slow)
```

luôn chứa non-zero.

---

# 19. LeetCode 26 — Remove Duplicates from Sorted Array

Input:

```text
[1,1,2,2,3]
```

Output logical:

```text
[1,2,3,...]
```

return:

```text
3
```

Vì array sorted, duplicate nằm cạnh nhau.

Ta dùng:

```text
slow = index phần tử unique cuối cùng
fast = scan
```

---

# 20. Visualization

```text
[1,1,2,2,3]

 slow
 ↓
[1,1,2,2,3]
     ↑
    fast
```

Khi:

```java
nums[fast] != nums[slow]
```

ta tìm thấy unique mới.

```java
slow++;
nums[slow] = nums[fast];
```

---

# 21. Implementation

```java
class Solution {
    public int removeDuplicates(int[] nums) {

        if (nums.length == 0) {
            return 0;
        }

        int slow = 0;

        for (int fast = 1; fast < nums.length; fast++) {

            if (nums[fast] != nums[slow]) {

                slow++;
                nums[slow] = nums[fast];
            }
        }

        return slow + 1;
    }
}
```

---

# 22. Tại sao return `slow + 1`?

`slow` là index cuối.

Ví dụ:

```text
[1,2,3]
 0 1 2
```

Có:

```text
slow = 2
```

nhưng số lượng phần tử:

```text
3
```

nên:

```java
return slow + 1;
```

---

# 23. Generalized template — Filter array in-place

Ví dụ muốn giữ các phần tử thỏa điều kiện:

```java
int write = 0;

for (int read = 0; read < nums.length; read++) {

    if (condition(nums[read])) {
        nums[write++] = nums[read];
    }
}

return write;
```

Có thể giải:

```text
Remove Element
Move Zeroes
Remove Duplicates
Compress String
Filter invalid items
```

---

# PART III — MERGE TWO POINTERS

# 24. Pattern 4 — Two Sorted Sequences

Giả sử:

```text
A = [1,3,5,8]
B = [2,4,6,7]
```

Ta dùng:

```text
i → A
j → B
```

So sánh:

```java
A[i] vs B[j]
```

Phần nhỏ hơn được xử lý trước.

---

# 25. Template

```java
int i = 0;
int j = 0;

while (i < a.length && j < b.length) {

    if (a[i] < b[j]) {
        // process a[i]
        i++;
    } else {
        // process b[j]
        j++;
    }
}

while (i < a.length) {
    ...
    i++;
}

while (j < b.length) {
    ...
    j++;
}
```

Complexity:

```text
O(n + m)
```

---

# 26. LeetCode 88 — Merge Sorted Array

```text
nums1 = [1,2,3,0,0,0]
m = 3

nums2 = [2,5,6]
n = 3
```

Output:

```text
[1,2,2,3,5,6]
```

---

# 27. Một lỗi phổ biến

Nếu merge từ đầu:

```text
1 2 3 0 0 0
↑
```

khi chèn phần tử vào `nums1`, ta có thể overwrite dữ liệu chưa xử lý.

Do đó ta merge **từ cuối về đầu**.

```text
nums1:
1 2 3 0 0 0
    ↑     ↑
    i     k

nums2:
2 5 6
    ↑
    j
```

---

# 28. Template merge backwards

```java
int i = m - 1;
int j = n - 1;
int k = m + n - 1;

while (j >= 0) {

    if (i >= 0 && nums1[i] > nums2[j]) {
        nums1[k--] = nums1[i--];
    } else {
        nums1[k--] = nums2[j--];
    }
}
```

Full solution:

```java
class Solution {
    public void merge(
        int[] nums1,
        int m,
        int[] nums2,
        int n
    ) {

        int i = m - 1;
        int j = n - 1;
        int k = m + n - 1;

        while (j >= 0) {

            if (i >= 0 && nums1[i] > nums2[j]) {
                nums1[k] = nums1[i];
                i--;
            } else {
                nums1[k] = nums2[j];
                j--;
            }

            k--;
        }
    }
}
```

---

# PART IV — SUBSEQUENCE TWO POINTERS

# 29. Pattern 5 — Subsequence Matching

Một string `s` là subsequence của `t` nếu ta có thể xóa một số ký tự khỏi `t` mà không thay đổi order.

Ví dụ:

```text
s = "abc"
t = "ahbgdc"
```

`abc` là subsequence.

---

# 30. Two pointers

```text
s: a b c
   ↑
   i

t: a h b g d c
   ↑
   j
```

Nếu:

```java
s.charAt(i) == t.charAt(j)
```

thì:

```java
i++;
```

Bất kể match hay không:

```java
j++;
```

---

# 31. LeetCode 392 — Is Subsequence

```java
class Solution {
    public boolean isSubsequence(String s, String t) {

        int i = 0;
        int j = 0;

        while (i < s.length() && j < t.length()) {

            if (s.charAt(i) == t.charAt(j)) {
                i++;
            }

            j++;
        }

        return i == s.length();
    }
}
```

Complexity:

```text
O(|t|)
```

---

# 32. Invariant

Pointer `i` biểu diễn:

> Có bao nhiêu character đầu tiên của `s` đã match được theo đúng thứ tự.

Nếu cuối cùng:

```java
i == s.length()
```

→ toàn bộ `s` đã match.

---

# PART V — FAST / SLOW POINTER

# 33. Pattern 6 — Fast and Slow Pointer

Pattern này thường dùng với Linked List.

```text
slow → đi 1 step
fast → đi 2 steps
```

Ứng dụng:

```text
Find middle
Detect cycle
Find cycle start
Nth node from end
Happy Number
```

---

# 34. LeetCode 876 — Middle of Linked List

Ví dụ:

```text
1 → 2 → 3 → 4 → 5
```

Pointer:

```text
slow = 1
fast = 1
```

Mỗi vòng:

```java
slow = slow.next;
fast = fast.next.next;
```

Iteration:

```text
slow = 2
fast = 3
```

sau đó:

```text
slow = 3
fast = 5
```

Khi fast tới cuối:

```text
slow = middle
```

---

# 35. Implementation

```java
class Solution {
    public ListNode middleNode(ListNode head) {

        ListNode slow = head;
        ListNode fast = head;

        while (
            fast != null &&
            fast.next != null
        ) {
            slow = slow.next;
            fast = fast.next.next;
        }

        return slow;
    }
}
```

Complexity:

```text
Time  O(n)
Space O(1)
```

---

# 36. Tại sao slow ở giữa?

Giả sử fast đã đi:

```text
2k
```

steps.

Thì slow đi:

```text
k
```

steps.

Khi fast đi hết list:

```text
2k ≈ n
```

nên:

```text
k ≈ n / 2
```

→ slow ở giữa.

---

# 37. LeetCode 141 — Linked List Cycle

Ví dụ:

```text
1 → 2 → 3 → 4 → 5
        ↑         ↓
        ← ← ← ← ←
```

Nếu có cycle:

```text
slow = 1 step
fast = 2 steps
```

Fast cuối cùng sẽ bắt kịp slow.

Tương tự hai người chạy vòng quanh track.

Người nhanh hơn cuối cùng sẽ gặp người chậm hơn.

---

# 38. Implementation

```java
public boolean hasCycle(ListNode head) {

    ListNode slow = head;
    ListNode fast = head;

    while (
        fast != null &&
        fast.next != null
    ) {

        slow = slow.next;
        fast = fast.next.next;

        if (slow == fast) {
            return true;
        }
    }

    return false;
}
```

Đây gọi là:

```text
Floyd's Cycle Detection Algorithm
```

---

# 39. Pattern 7 — Fixed Distance Two Pointers

Rất quan trọng.

Ví dụ:

## LeetCode 19 — Remove Nth Node From End

Ta cần xóa node thứ `n` từ cuối.

Ví dụ:

```text
1 → 2 → 3 → 4 → 5
n = 2
```

cần xóa:

```text
4
```

---

# 40. Ý tưởng

Cho `fast` đi trước `slow` một khoảng cố định.

```text
fast
   ↓
dummy → 1 → 2 → 3 → 4 → 5
↑
slow
```

Fast đi trước:

```text
n + 1
```

steps.

Sau đó move cả hai cùng nhau.

Khi:

```text
fast == null
```

slow đứng ngay trước node cần xóa.

---

# 41. Implementation

```java
class Solution {
    public ListNode removeNthFromEnd(ListNode head, int n) {

        ListNode dummy = new ListNode(0, head);

        ListNode slow = dummy;
        ListNode fast = dummy;

        for (int i = 0; i <= n; i++) {
            fast = fast.next;
        }

        while (fast != null) {
            slow = slow.next;
            fast = fast.next;
        }

        slow.next = slow.next.next;

        return dummy.next;
    }
}
```

Pattern tổng quát:

```text
1. Establish a gap
2. Move both pointers
3. When fast reaches end
4. slow reaches desired relative position
```

---

# PART VI — TWO POINTERS + SORTING

# 42. Pattern 8 — 3Sum / K-Sum

Một trong các pattern quan trọng nhất.

## LeetCode 15 — 3Sum

Cho:

```text
nums = [-1,0,1,2,-1,-4]
```

Tìm tất cả triplet:

```text
a + b + c = 0
```

---

# 43. Brute force

```text
for i
    for j
        for k
```

Complexity:

```text
O(n³)
```

---

# 44. Optimization

Sort:

```text
[-4,-1,-1,0,1,2]
```

Giữ một số:

```text
nums[i]
```

Bài toán còn lại:

```text
nums[left] + nums[right] = -nums[i]
```

Tức là chuyển từ:

```text
3Sum
```

thành:

```text
2Sum sorted
```

---

# 45. Visualization

```text
[-4,-1,-1,0,1,2]

     i
     ↓
[-4,-1,-1,0,1,2]
        ↑       ↑
       left    right
```

Target:

```text
-nums[i]
```

Nếu sum:

```text
nums[i] + nums[left] + nums[right]
```

### sum < 0

```java
left++;
```

### sum > 0

```java
right--;
```

### sum == 0

save answer.

---

# 46. Duplicate Handling

Đây là phần quan trọng nhất của 3Sum.

Nếu:

```text
nums[i] == nums[i - 1]
```

thì skip:

```java
if (i > 0 && nums[i] == nums[i - 1]) {
    continue;
}
```

Sau khi tìm thấy answer:

```java
while (
    left < right &&
    nums[left] == nums[left + 1]
) {
    left++;
}
```

Tương tự right.

---

# 47. Implementation

```java
class Solution {
    public List<List<Integer>> threeSum(int[] nums) {

        Arrays.sort(nums);

        List<List<Integer>> result = new ArrayList<>();

        for (int i = 0; i < nums.length - 2; i++) {

            if (i > 0 && nums[i] == nums[i - 1]) {
                continue;
            }

            int left = i + 1;
            int right = nums.length - 1;

            while (left < right) {

                int sum =
                    nums[i] +
                    nums[left] +
                    nums[right];

                if (sum == 0) {

                    result.add(Arrays.asList(
                        nums[i],
                        nums[left],
                        nums[right]
                    ));

                    while (
                        left < right &&
                        nums[left] == nums[left + 1]
                    ) {
                        left++;
                    }

                    while (
                        left < right &&
                        nums[right] == nums[right - 1]
                    ) {
                        right--;
                    }

                    left++;
                    right--;

                } else if (sum < 0) {

                    left++;

                } else {

                    right--;
                }
            }
        }

        return result;
    }
}
```

Complexity:

```text
Sorting = O(n log n)

Outer loop = n
Two pointers = n

Total = O(n²)
```

---

# 48. General K-Sum pattern

Một tư duy rất quan trọng:

```text
4Sum
    ↓ fix one
3Sum
    ↓ fix one
2Sum
    ↓
Two Pointers
```

Ví dụ:

```text
K-Sum
```

thường được giải bằng:

```text
recursion + two pointers base case
```

Base case:

```text
k == 2
```

→ dùng Two Sum sorted.

---

# PART VII — GREEDY TWO POINTERS

# 49. Pattern 9 — Container With Most Water

## LeetCode 11

Có:

```text
height = [1,8,6,2,5,4,8,3,7]
```

Chọn hai line tạo container lớn nhất.

Area:

```text
width * height
```

Trong đó:

```text
width = right - left

height = min(height[left], height[right])
```

Nên:

```java
area =
    (right - left)
    * Math.min(height[left], height[right]);
```

---

# 50. Câu hỏi quan trọng

Tại sao luôn move pointer có height thấp hơn?

Ví dụ:

```text
L                         R
|                         |
|                         |
|       |                 |
|       |                 |
```

Giả sử:

```text
height[left] < height[right]
```

Current area bị giới hạn bởi:

```text
height[left]
```

Nếu move:

```java
right--;
```

thì:

```text
width giảm
```

nhưng chiều cao container vẫn không thể vượt qua:

```text
height[left]
```

→ không có cơ hội cải thiện.

Muốn tìm area lớn hơn, ta phải bỏ height thấp hơn:

```java
left++;
```

hy vọng gặp line cao hơn.

---

# 51. Implementation

```java
class Solution {
    public int maxArea(int[] height) {

        int left = 0;
        int right = height.length - 1;

        int result = 0;

        while (left < right) {

            int width = right - left;

            int h = Math.min(
                height[left],
                height[right]
            );

            result = Math.max(
                result,
                width * h
            );

            if (height[left] < height[right]) {
                left++;
            } else {
                right--;
            }
        }

        return result;
    }
}
```

---

# 52. Đây là một kiểu Two Pointer khác

Nó không phải:

```text
sum < target → left++
sum > target → right--
```

mà là:

```text
loại bỏ boundary không thể tạo đáp án tốt hơn
```

Đây là tư duy rất quan trọng.

---

# PART VIII — TRAPPING RAIN WATER

# 53. LeetCode 42 — Trapping Rain Water

Một bài Two Pointers rất nổi tiếng.

Ví dụ:

```text
height =
[0,1,0,2,1,0,1,3,2,1,2,1]
```

Water tại index `i`:

```text
water[i]
=
min(maxLeft, maxRight)
-
height[i]
```

Brute force:

Mỗi index tìm:

```text
maxLeft
maxRight
```

→ `O(n²)`.

Có DP prefix/suffix:

```text
O(n) time
O(n) space
```

Nhưng Two Pointers có:

```text
O(n) time
O(1) space
```

---

# 54. State

Ta giữ:

```java
leftMax
rightMax
```

và:

```java
left
right
```

Nếu:

```text
height[left] < height[right]
```

ta biết phía left có boundary bên phải đủ cao để quyết định lượng nước tại left.

Ta xử lý left.

Ngược lại xử lý right.

---

# 55. Implementation

```java
class Solution {
    public int trap(int[] height) {

        int left = 0;
        int right = height.length - 1;

        int leftMax = 0;
        int rightMax = 0;

        int water = 0;

        while (left < right) {

            if (height[left] < height[right]) {

                if (height[left] >= leftMax) {
                    leftMax = height[left];
                } else {
                    water += leftMax - height[left];
                }

                left++;

            } else {

                if (height[right] >= rightMax) {
                    rightMax = height[right];
                } else {
                    water += rightMax - height[right];
                }

                right--;
            }
        }

        return water;
    }
}
```

Đây là bài khó vì việc move pointer phụ thuộc vào việc:

> Boundary thấp hơn là phía mà ta đã có đủ thông tin để tính nước.

---

# PART IX — INTERVAL TWO POINTERS

# 56. Pattern 10 — Two Sorted Interval Lists

Ví dụ:

```text
A = [[0,2], [5,10], [13,23]]
B = [[1,5], [8,12], [15,24]]
```

Ta muốn tìm intersection.

Đây là LeetCode:

```text
986. Interval List Intersections
```

---

# 57. Intersection của hai interval

Có:

```text
[a1, a2]
[b1, b2]
```

Intersection:

```text
start = max(a1, b1)
end   = min(a2, b2)
```

Nếu:

```text
start <= end
```

→ tồn tại intersection.

---

# 58. Pointer nào move?

Ví dụ:

```text
A = [5,10]
B = [8,12]
```

Intersection:

```text
[8,10]
```

Interval A kết thúc trước:

```text
10 < 12
```

A không thể intersection thêm với B hiện tại nữa.

Do đó:

```java
i++;
```

Quy tắc:

> Interval kết thúc trước được move.

---

# 59. Implementation

```java
class Solution {
    public int[][] intervalIntersection(
        int[][] firstList,
        int[][] secondList
    ) {

        List<int[]> result = new ArrayList<>();

        int i = 0;
        int j = 0;

        while (
            i < firstList.length &&
            j < secondList.length
        ) {

            int start = Math.max(
                firstList[i][0],
                secondList[j][0]
            );

            int end = Math.min(
                firstList[i][1],
                secondList[j][1]
            );

            if (start <= end) {
                result.add(new int[]{start, end});
            }

            if (
                firstList[i][1]
                < secondList[j][1]
            ) {
                i++;
            } else {
                j++;
            }
        }

        return result.toArray(new int[result.size()][]);
    }
}
```

---

# PART X — SLIDING WINDOW LÀ MỘT DẠNG TWO POINTER

# 60. Quan hệ giữa Two Pointers và Sliding Window

Sliding Window thường sử dụng:

```text
left
right
```

nên về implementation nó là một dạng Two Pointers.

Nhưng tư duy khác nhau.

### Classical Two Pointers

Thường:

```text
left và right đại diện cho hai candidate
```

Ví dụ:

```text
Two Sum
Container
Palindrome
```

### Sliding Window

Hai pointer đại diện cho:

```text
một contiguous range [left, right]
```

Ta duy trì property của cả window.

Ví dụ:

```text
Longest Substring Without Repeating Characters
Longest Repeating Character Replacement
Minimum Size Subarray Sum
```

---

# 61. Sliding Window template

```java
int left = 0;

for (int right = 0; right < nums.length; right++) {

    // add nums[right]

    while (windowInvalid()) {

        // remove nums[left]

        left++;
    }

    // process valid window
}
```

Có dạng:

```text
expand → invalid → shrink → valid
```

Đây là pattern riêng rất lớn, nên khi học DSA thường tách Sliding Window thành một chapter riêng.

---

# PART XI — PARTITION POINTER

# 62. Pattern 11 — Partition Array

Ví dụ cần đưa:

```text
mọi số <= pivot sang trái
mọi số > pivot sang phải
```

Ta dùng:

```text
boundary
scan
```

Template:

```java
int boundary = 0;

for (int i = 0; i < nums.length; i++) {

    if (nums[i] <= pivot) {

        swap(nums, boundary, i);
        boundary++;
    }
}
```

Đây là nền tảng của:

```text
QuickSort
QuickSelect
Dutch National Flag
```

---

# 63. Dutch National Flag — 3 pointers

LeetCode 75:

```text
Sort Colors
```

Input:

```text
[2,0,2,1,1,0]
```

Ta muốn:

```text
[0,0,1,1,2,2]
```

Dùng:

```text
low
mid
high
```

Invariant:

```text
[0, low)       = 0
[low, mid)     = 1
[mid, high]    = unknown
(high, n - 1]  = 2
```

Implementation:

```java
class Solution {
    public void sortColors(int[] nums) {

        int low = 0;
        int mid = 0;
        int high = nums.length - 1;

        while (mid <= high) {

            if (nums[mid] == 0) {

                swap(nums, low, mid);
                low++;
                mid++;

            } else if (nums[mid] == 1) {

                mid++;

            } else {

                swap(nums, mid, high);
                high--;
            }
        }
    }

    private void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }
}
```

Một detail rất quan trọng:

Sau:

```java
swap(mid, high);
high--;
```

không:

```java
mid++;
```

Tại sao?

Vì phần tử vừa swap từ `high` sang `mid`:

```text
chưa được kiểm tra
```

---

# PART XII — SQUARED SORTED ARRAY

# 64. LeetCode 977 — Squares of a Sorted Array

Input:

```text
[-4,-1,0,3,10]
```

Square:

```text
[16,1,0,9,100]
```

Nếu square rồi sort:

```text
O(n log n)
```

Nhưng array ban đầu sorted.

Giá trị absolute lớn nhất nằm ở:

```text
left hoặc right
```

---

# 65. Two pointer solution

```text
[-4,-1,0,3,10]
 ↑           ↑
 L           R
```

Compare:

```java
Math.abs(nums[left])
Math.abs(nums[right])
```

Số lớn hơn có square lớn nhất.

Ta fill result từ cuối.

---

# 66. Implementation

```java
class Solution {
    public int[] sortedSquares(int[] nums) {

        int n = nums.length;

        int[] result = new int[n];

        int left = 0;
        int right = n - 1;

        int write = n - 1;

        while (left <= right) {

            int leftSquare =
                nums[left] * nums[left];

            int rightSquare =
                nums[right] * nums[right];

            if (leftSquare > rightSquare) {

                result[write] = leftSquare;
                left++;

            } else {

                result[write] = rightSquare;
                right--;
            }

            write--;
        }

        return result;
    }
}
```

Đây là pattern:

```text
Two Ends + Fill Result Backwards
```

rất đáng nhớ.

---

# PART XIII — CÁCH NHẬN DIỆN TWO POINTERS

# 67. Signal 1 — Sorted array

Nếu đề nói:

```text
sorted array
```

hãy ngay lập tức nghĩ:

```text
Binary Search?
Two Pointers?
```

Nếu đề hỏi:

```text
pair / triplet / combination
```

Two Pointers thường là candidate rất mạnh.

Ví dụ:

```text
Two Sum II
3Sum
4Sum
Closest Sum
```

---

# 68. Signal 2 — Pair từ hai đầu

Nếu answer phụ thuộc:

```text
nums[left]
nums[right]
```

và có thể quyết định pointer nào cần loại bỏ:

```text
Two Pointers
```

Ví dụ:

```text
Container With Most Water
Trapping Rain Water
Palindrome
```

---

# 69. Signal 3 — In-place transformation

Nếu đề có:

```text
Do it in-place
O(1) extra space
remove
move
compress
```

hãy nghĩ:

```text
read/write pointer
```

Ví dụ:

```text
Move Zeroes
Remove Duplicates
Remove Element
String Compression
```

---

# 70. Signal 4 — Two sorted collections

Nếu có:

```text
two sorted arrays
two sorted lists
two interval lists
```

hãy nghĩ:

```text
i pointer
j pointer
```

Ví dụ:

```text
Merge Sorted Array
Interval Intersection
Merge sort
```

---

# 71. Signal 5 — Linked List

Nếu đề hỏi:

```text
cycle
middle
nth from end
```

hãy nghĩ:

```text
slow / fast
```

---

# 72. Signal 6 — Subsequence

Nếu hỏi:

```text
Is s a subsequence of t?
```

hoặc:

```text
match characters while preserving order
```

hãy nghĩ:

```text
two forward pointers
```

---

# PART XIV — NHỮNG TEMPLATE CẦN THUỘC

# 73. Template A — Opposite direction

```java
int left = 0;
int right = nums.length - 1;

while (left < right) {

    if (conditionFound) {
        ...
    } else if (needLarger) {
        left++;
    } else {
        right--;
    }
}
```

Ứng dụng:

```text
Two Sum II
3Sum
Container With Most Water
Palindrome
```

---

# 74. Template B — Read / Write

```java
int write = 0;

for (int read = 0; read < nums.length; read++) {

    if (isValid(nums[read])) {
        nums[write] = nums[read];
        write++;
    }
}
```

Ứng dụng:

```text
Move Zeroes
Remove Element
Remove Duplicates
```

---

# 75. Template C — Merge

```java
int i = 0;
int j = 0;

while (i < a.length && j < b.length) {

    if (a[i] <= b[j]) {
        ...
        i++;
    } else {
        ...
        j++;
    }
}
```

---

# 76. Template D — Subsequence

```java
int i = 0;
int j = 0;

while (i < s.length() && j < t.length()) {

    if (s.charAt(i) == t.charAt(j)) {
        i++;
    }

    j++;
}

return i == s.length();
```

---

# 77. Template E — Fast / Slow

```java
ListNode slow = head;
ListNode fast = head;

while (
    fast != null &&
    fast.next != null
) {
    slow = slow.next;
    fast = fast.next.next;
}
```

---

# 78. Template F — Fixed gap

```java
ListNode slow = head;
ListNode fast = head;

for (int i = 0; i < k; i++) {
    fast = fast.next;
}

while (fast != null) {
    slow = slow.next;
    fast = fast.next;
}
```

---

# PART XV — MỘT FRAMEWORK TƯ DUY KHI GẶP BÀI TWO POINTERS

Khi gặp problem, hãy tự hỏi 6 câu.

## Question 1

Array/string có sorted không?

Nếu có:

```text
pair search → Two Pointers
```

---

## Question 2

Ta có cần kiểm tra tất cả `n²` pair không?

Nếu brute force là:

```java
for (i)
    for (j)
```

hãy tìm xem có thể loại bỏ một nhóm candidate sau mỗi comparison không.

---

## Question 3

Pointer đại diện cho cái gì?

Không nên code ngay:

```java
int left;
int right;
```

Trước tiên phải định nghĩa:

```text
left = ?
right = ?
```

Ví dụ Two Sum:

```text
left  = smallest remaining candidate
right = largest remaining candidate
```

Move Zeroes:

```text
read  = element being inspected
write = next valid position
```

Fast/slow:

```text
slow = 1-step traveler
fast = 2-step traveler
```

---

# 79. Question 4 — Invariant là gì?

Ví dụ Remove Duplicates:

```text
nums[0 ... slow]
```

luôn là:

```text
unique elements
```

Ví dụ Move Zeroes:

```text
nums[0 ... write-1]
```

luôn là:

```text
non-zero values
```

Ví dụ sliding window:

```text
[left, right]
```

luôn thỏa:

```text
window condition
```

---

# 80. Question 5 — Tại sao move pointer này?

Đây là câu interview cực quan trọng.

Bạn không nên chỉ nói:

> If sum is smaller, move left.

Nên giải thích:

> Because the array is sorted, decreasing the right pointer would only make the sum even smaller. Therefore, the only way to potentially reach the target is to move the left pointer forward and increase the sum.

---

# 81. Question 6 — Pointer có bao giờ move backward không?

Đa số bài Two Pointers:

```text
each pointer moves monotonically
```

Ví dụ:

```text
left → → →
right ← ← ←
```

Mỗi pointer đi tối đa `n` lần.

Do đó dù có loop lồng:

```java
for (...)
    while (...)
```

complexity vẫn có thể là:

```text
O(n)
```

vì tổng số lần pointer move chỉ là `O(n)`.

Đây cũng là reasoning quan trọng của Sliding Window.

---

# PART XVI — CÁC BÀI LEETCODE THEO PATTERN

## Level 1 — Foundation

### Opposite pointers

```text
125. Valid Palindrome
167. Two Sum II
344. Reverse String
977. Squares of a Sorted Array
```

### Same-direction

```text
26. Remove Duplicates from Sorted Array
27. Remove Element
283. Move Zeroes
392. Is Subsequence
```

---

# 82. Level 2 — Medium

```text
11. Container With Most Water
15. 3Sum
75. Sort Colors
80. Remove Duplicates from Sorted Array II
88. Merge Sorted Array
142. Linked List Cycle II
167. Two Sum II
209. Minimum Size Subarray Sum
443. String Compression
680. Valid Palindrome II
986. Interval List Intersections
```

---

# 83. Level 3 — Interview Important

```text
15. 3Sum
16. 3Sum Closest
18. 4Sum
19. Remove Nth Node From End
42. Trapping Rain Water
61. Rotate List
75. Sort Colors
142. Linked List Cycle II
287. Find the Duplicate Number
611. Valid Triangle Number
881. Boats to Save People
```

---

# PART XVII — NHỮNG BÀI RẤT ĐÁNG HỌC THEO THỨ TỰ

Mình đề xuất learning path:

```text
1. 344 Reverse String
        ↓
2. 125 Valid Palindrome
        ↓
3. 167 Two Sum II
        ↓
4. 283 Move Zeroes
        ↓
5. 26 Remove Duplicates
        ↓
6. 392 Is Subsequence
        ↓
7. 88 Merge Sorted Array
        ↓
8. 977 Squares Sorted Array
        ↓
9. 11 Container With Most Water
        ↓
10. 15 3Sum
        ↓
11. 75 Sort Colors
        ↓
12. 876 Middle Linked List
        ↓
13. 141 Linked List Cycle
        ↓
14. 19 Remove Nth From End
        ↓
15. 986 Interval Intersection
        ↓
16. 42 Trapping Rain Water
```

---

# PART XVIII — TỔNG HỢP PATTERN

| Pattern           | Pointer movement     | Dùng khi                   |
| ----------------- | -------------------- | -------------------------- |
| Opposite pointers | `L → ← R`            | sorted pair, palindrome    |
| Read / Write      | `slow → fast →`      | modify array in-place      |
| Merge             | `i →`, `j →`         | two sorted sequences       |
| Subsequence       | `i →`, `j →`         | preserve order             |
| Fast / Slow       | `slow +1`, `fast +2` | linked list, cycle         |
| Fixed Gap         | same direction       | kth/nth from end           |
| Sliding Window    | `L →`, `R →`         | contiguous subarray/string |
| Partition         | boundary + scan      | QuickSort, Sort Colors     |
| Interval pointers | `i →`, `j →`         | sorted interval lists      |
| K-Sum             | fix + two pointers   | 3Sum, 4Sum                 |

---

# PART XIX — MENTAL MODEL QUAN TRỌNG NHẤT

Đừng học Two Pointers theo kiểu:

```text
"Thấy sorted thì left/right."
```

Hãy học theo mental model:

```text
Candidate Space
      ↓
Compare current candidates
      ↓
Can I safely discard one side?
      ↓
YES
      ↓
Move pointer
      ↓
Repeat
```

Hai pointer có thể mang nhiều ý nghĩa khác nhau:

```text
Two Sum
left/right = candidate values

Move Zeroes
read/write = source/destination

Subsequence
i/j = progress in two sequences

Linked List
slow/fast = different traveling speeds

Sliding Window
left/right = boundaries of a valid range

Partition
boundary/scan = processed/unprocessed boundary
```

Nếu hiểu được **ý nghĩa của pointer và invariant**, bạn không cần thuộc lòng code.

---

# PART XX — CHEAT SHEET

Khi nhìn thấy:

```text
sorted + pair
```

nghĩ:

```java
left = 0;
right = n - 1;
```

---

Khi nhìn thấy:

```text
remove / move / compress in-place
```

nghĩ:

```java
write = 0;

for (read...) {
    if (valid) {
        nums[write++] = nums[read];
    }
}
```

---

Khi nhìn thấy:

```text
two sorted arrays
```

nghĩ:

```java
i = 0;
j = 0;
```

---

Khi nhìn thấy:

```text
linked list middle / cycle
```

nghĩ:

```java
slow = slow.next;
fast = fast.next.next;
```

---

Khi nhìn thấy:

```text
nth from end
```

nghĩ:

```text
create fixed gap
then move together
```

---

Khi nhìn thấy:

```text
3Sum / 4Sum
```

nghĩ:

```text
sort
fix element
reduce to 2Sum
```

---

Khi nhìn thấy:

```text
longest / shortest contiguous substring
```

nghĩ:

```text
Sliding Window
```

---

# FINAL MENTAL MAP

```text
                         TWO POINTERS
                              │
       ┌──────────────────────┼──────────────────────┐
       │                      │                      │
 Opposite Direction     Same Direction        Different Speed
       │                      │                      │
    L → ← R              slow → fast →          slow / fast
       │                      │                      │
 Two Sum II             Move Zeroes            Find Middle
 Palindrome             Remove Duplicate       Detect Cycle
 3Sum                   Remove Element         Cycle Start
 Container
       │
       ├── Sorted Pair
       ├── Greedy Boundary
       └── K-Sum


             TWO SEQUENCES
                  │
          ┌───────┴────────┐
          │                │
        Merge          Subsequence
        i / j              i / j
          │                │
 Merge Sorted         Is Subsequence
 Interval Lists       Sequence Match


             RANGE / PARTITION
                  │
          ┌───────┴────────┐
          │                │
    Sliding Window      Partition
      L / R          boundary / scan
          │                │
 Longest Window       Sort Colors
 Min Window           QuickSelect
```

## Điều cần nhớ cuối cùng

**Two Pointers không phải là "dùng hai biến".**

Một lời giải thực sự là Two Pointers khi:

```text
1. Hai pointer mô tả trạng thái tìm kiếm.
2. Có invariant rõ ràng.
3. Dựa vào thông tin hiện tại,
   ta quyết định pointer nào phải move.
4. Việc move pointer có thể loại bỏ candidate
   mà chắc chắn không phải đáp án.
5. Pointer thường move monotonic,
   nhờ đó giảm complexity từ O(n²) xuống O(n).
```

Một cách diễn đạt rất tốt trong coding interview là:

> "The key observation is that I don't need to explore every pair. Because of the ordering/invariant, after evaluating the current two pointers, I can safely discard one side of the search space."

Đó chính là bản chất của **Two Pointers**.
