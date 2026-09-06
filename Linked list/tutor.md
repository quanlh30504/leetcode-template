# Linked List Patterns & Templates

## 1. Linked List thực sự khó ở đâu?

Linked List không khó vì thuật toán phức tạp. Phần khó chủ yếu nằm ở việc **quản lý reference**.

Với array:

```text
nums[i]
nums[i + 1]
```

ta có thể truy cập trực tiếp.

Nhưng với Linked List:

```text
current
   |
   v
 [1] -> [2] -> [3] -> null
```

muốn đến node tiếp theo phải đi qua:

```java
current = current.next;
```

Và nguy hiểm nhất là khi sửa:

```java
current.next = ...
```

Nếu sửa sai thứ tự, ta có thể làm mất toàn bộ phần còn lại của list.

---

# 2. Node structure cơ bản

LeetCode thường định nghĩa:

```java
public class ListNode {
    int val;
    ListNode next;

    ListNode() {}

    ListNode(int val) {
        this.val = val;
    }

    ListNode(int val, ListNode next) {
        this.val = val;
        this.next = next;
    }
}
```

Ví dụ:

```text
1 -> 2 -> 3 -> null
```

thực chất là:

```java
ListNode node3 = new ListNode(3);
ListNode node2 = new ListNode(2, node3);
ListNode node1 = new ListNode(1, node2);

ListNode head = node1;
```

---

# 3. Những operation cơ bản cần thành thạo

## Traversal

Template:

```java
ListNode current = head;

while (current != null) {
    System.out.println(current.val);
    current = current.next;
}
```

Complexity:

```text
Time  O(n)
Space O(1)
```

---

## Tính độ dài

```java
int length = 0;

ListNode current = head;

while (current != null) {
    length++;
    current = current.next;
}
```

---

# 4. Pattern 1 — Linear Traversal

Đây là pattern cơ bản nhất.

## Dấu hiệu nhận biết

Đề yêu cầu:

```text
find
count
sum
check
update
```

trên từng node.

Ví dụ:

* tìm một giá trị
* đếm node
* remove node theo condition
* kiểm tra sorted
* tìm maximum

Template:

```java
ListNode current = head;

while (current != null) {

    // process current

    current = current.next;
}
```

---

# 5. Pattern 2 — Dummy Head / Sentinel Node

Đây là một trong những pattern **quan trọng nhất của Linked List**.

## Vấn đề

Giả sử:

```text
1 -> 2 -> 3
```

ta muốn delete `1`.

Nếu không dùng dummy:

```java
head = head.next;
```

Nhưng nếu delete `2`:

```java
prev.next = current.next;
```

Như vậy ta phải xử lý riêng:

```text
delete head
delete non-head
```

Dummy node giúp loại bỏ special case này.

---

# 6. Dummy Node là gì?

Tạo một node giả nằm trước head:

```text
dummy
  |
  v
 -1 -> 1 -> 2 -> 3
       ^
       head
```

Implementation:

```java
ListNode dummy = new ListNode(0);
dummy.next = head;
```

Sau khi xử lý:

```java
return dummy.next;
```

Không return:

```java
return head;
```

vì `head` có thể đã bị delete.

---

# 7. Template Dummy Node

```java
ListNode dummy = new ListNode(0);
dummy.next = head;

ListNode prev = dummy;
ListNode current = head;

while (current != null) {

    // modify list

    prev = current;
    current = current.next;
}

return dummy.next;
```

---

# 8. Ví dụ — LeetCode 203. Remove Linked List Elements

## Problem

Cho:

```text
1 -> 2 -> 6 -> 3 -> 4 -> 5 -> 6
```

và:

```text
val = 6
```

Output:

```text
1 -> 2 -> 3 -> 4 -> 5
```

---

## Solution

Ta cần pointer `prev`.

```text
dummy -> 1 -> 2 -> 6 -> 3
              ^    ^
             prev current
```

Nếu:

```java
current.val == val
```

thì:

```java
prev.next = current.next;
```

Có nghĩa là:

```text
2 -> 6 -> 3
```

trở thành:

```text
2 ------> 3
```

Implementation:

```java
class Solution {
    public ListNode removeElements(ListNode head, int val) {

        ListNode dummy = new ListNode(0);
        dummy.next = head;

        ListNode prev = dummy;
        ListNode current = head;

        while (current != null) {

            if (current.val == val) {
                prev.next = current.next;
            } else {
                prev = current;
            }

            current = current.next;
        }

        return dummy.next;
    }
}
```

### Điểm rất quan trọng

Không được:

```java
if (current.val == val) {
    prev.next = current.next;
}

prev = current;
```

vì node `current` vừa bị delete không còn thuộc list nữa.

---

# 9. Pattern 3 — Reverse Linked List

Đây là pattern Linked List quan trọng nhất.

LeetCode:

```text
206 Reverse Linked List
92  Reverse Linked List II
25  Reverse Nodes in k-Group
234 Palindrome Linked List
143 Reorder List
```

---

# 10. Reverse Linked List — tư tưởng

Input:

```text
1 -> 2 -> 3 -> null
```

Output:

```text
3 -> 2 -> 1 -> null
```

Ta muốn đổi:

```text
1 -> 2
```

thành:

```text
1 <- 2
```

Nhưng nếu làm:

```java
current.next = prev;
```

thì sẽ mất pointer tới node tiếp theo.

Do đó phải lưu:

```java
ListNode next = current.next;
```

trước.

---

# 11. Template Reverse chuẩn

```java
ListNode prev = null;
ListNode current = head;

while (current != null) {

    ListNode next = current.next;

    current.next = prev;

    prev = current;

    current = next;
}

return prev;
```

Đây là template nên thuộc lòng.

---

# 12. Trace Reverse

Ban đầu:

```text
prev = null

current
 |
 v
 1 -> 2 -> 3 -> null
```

### Iteration 1

```java
next = current.next;
```

```text
next = 2
```

Sau:

```java
current.next = prev;
```

ta có:

```text
1 -> null

2 -> 3
```

Sau:

```java
prev = current;
current = next;
```

```text
prev
 |
 v
 1 -> null

current
 |
 v
 2 -> 3
```

---

### Iteration 2

```text
2 -> 1 -> null

3 -> null
```

Cuối cùng:

```text
3 -> 2 -> 1 -> null
```

---

# 13. Invariant của Reverse Linked List

Khi interview có thể explain:

> At every iteration, `prev` is the head of the already reversed portion, while `current` points to the first node of the remaining unreversed portion.

Hay:

```text
prev    current
 |        |
 v        v
reversed  remaining
```

---

# 14. Recursive Reverse Linked List

Có một implementation rất nổi tiếng:

```java
public ListNode reverseList(ListNode head) {

    if (head == null || head.next == null) {
        return head;
    }

    ListNode newHead = reverseList(head.next);

    head.next.next = head;
    head.next = null;

    return newHead;
}
```

Ví dụ:

```text
1 -> 2 -> 3
```

Recursion xử lý:

```text
reverse(2 -> 3)
```

trả:

```text
3 -> 2
```

Lúc này:

```java
head = 1;
head.next = 2;
```

Ta chạy:

```java
head.next.next = head;
```

tức:

```java
2.next = 1;
```

sau đó:

```java
1.next = null;
```

Result:

```text
3 -> 2 -> 1
```

---

# 15. Pattern 4 — Fast & Slow Pointer

Đây là pattern cực kỳ phổ biến.

Ta có:

```java
ListNode slow = head;
ListNode fast = head;
```

Sau mỗi vòng:

```java
slow = slow.next;
fast = fast.next.next;
```

Tức:

```text
slow: 1 step
fast: 2 steps
```

---

# 16. Ứng dụng của Fast & Slow Pointer

Thường dùng cho:

```text
Find middle
Detect cycle
Find cycle entry
Palindrome
Reorder List
Linked List Merge Sort
```

Các bài tiêu biểu:

```text
141 Linked List Cycle
142 Linked List Cycle II
876 Middle of the Linked List
234 Palindrome Linked List
143 Reorder List
148 Sort List
```

---

# 17. Find Middle

Problem:

```text
1 -> 2 -> 3 -> 4 -> 5
```

Output:

```text
3
```

Template:

```java
ListNode slow = head;
ListNode fast = head;

while (fast != null && fast.next != null) {
    slow = slow.next;
    fast = fast.next.next;
}

return slow;
```

---

## Trace

```text
1 -> 2 -> 3 -> 4 -> 5
S
F
```

Iteration 1:

```text
1 -> 2 -> 3 -> 4 -> 5
     S
          F
```

Iteration 2:

```text
1 -> 2 -> 3 -> 4 -> 5
          S
                    F
```

`fast.next == null`

stop.

`slow = 3`.

---

# 18. Even Length Case

```text
1 -> 2 -> 3 -> 4 -> 5 -> 6
```

Template trên trả:

```text
4
```

tức **second middle**.

Đây chính là behavior của LeetCode 876.

---

# 19. Pattern 5 — Floyd Cycle Detection

Problem:

```text
1 -> 2 -> 3 -> 4
         ^       |
         |_______|
```

Có cycle.

Ta dùng:

```text
slow = 1 step
fast = 2 steps
```

Nếu không có cycle:

```text
fast eventually becomes null
```

Nếu có cycle:

```text
fast eventually catches slow
```

---

# 20. LeetCode 141 — Linked List Cycle

```java
public boolean hasCycle(ListNode head) {

    ListNode slow = head;
    ListNode fast = head;

    while (fast != null && fast.next != null) {

        slow = slow.next;
        fast = fast.next.next;

        if (slow == fast) {
            return true;
        }
    }

    return false;
}
```

Complexity:

```text
Time  O(n)
Space O(1)
```

---

# 21. Vì sao Fast sẽ bắt Slow?

Giả sử cycle length = `C`.

Mỗi iteration:

```text
slow moves 1
fast moves 2
```

Khoảng cách giữa hai pointer thay đổi:

```text
2 - 1 = 1
```

mỗi iteration.

Trong cycle, khoảng cách được tính modulo `C`.

Do đó chắc chắn có thời điểm:

```text
distance % C == 0
```

và:

```text
slow == fast
```

---

# 22. LeetCode 142 — Linked List Cycle II

Không chỉ hỏi có cycle mà hỏi:

> cycle bắt đầu ở đâu?

Ví dụ:

```text
1 -> 2 -> 3 -> 4 -> 5
         ^              |
         |______________|
```

Cycle entry:

```text
3
```

Algorithm:

### Step 1

Tìm meeting point bằng Floyd.

### Step 2

Đặt:

```java
pointer1 = head;
pointer2 = meetingPoint;
```

Sau đó cùng đi:

```java
pointer1 = pointer1.next;
pointer2 = pointer2.next;
```

Node chúng gặp nhau chính là cycle entry.

---

## Template

```java
public ListNode detectCycle(ListNode head) {

    ListNode slow = head;
    ListNode fast = head;

    while (fast != null && fast.next != null) {

        slow = slow.next;
        fast = fast.next.next;

        if (slow == fast) {

            ListNode p1 = head;
            ListNode p2 = slow;

            while (p1 != p2) {
                p1 = p1.next;
                p2 = p2.next;
            }

            return p1;
        }
    }

    return null;
}
```

---

# 23. Pattern 6 — Two Pointers với khoảng cách cố định

Đây là pattern khác với fast/slow.

Ta có:

```text
first
second
```

và tạo một khoảng cách:

```text
first đi trước second k node
```

Sau đó cả hai cùng di chuyển.

Ứng dụng phổ biến nhất:

```text
Find kth node from end
Remove nth node from end
```

---

# 24. LeetCode 19 — Remove Nth Node From End

Input:

```text
1 -> 2 -> 3 -> 4 -> 5
```

```text
n = 2
```

Output:

```text
1 -> 2 -> 3 -> 5
```

Ta muốn delete:

```text
4
```

---

# 25. Ý tưởng Two Pointer Gap

Tạo dummy:

```text
dummy -> 1 -> 2 -> 3 -> 4 -> 5
```

Đưa `fast` đi trước `slow`:

```text
n + 1
```

bước.

Sau đó move cả hai.

Khi `fast == null`:

```text
slow
 |
 v
3 -> 4 -> 5
```

Node cần delete:

```java
slow.next;
```

---

## Implementation

```java
public ListNode removeNthFromEnd(ListNode head, int n) {

    ListNode dummy = new ListNode(0);
    dummy.next = head;

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
```

---

# 26. Pattern này thực chất là gì?

Một invariant rất quan trọng:

```text
distance(fast, slow) = n + 1
```

Do đó khi:

```text
fast == null
```

thì:

```text
slow.next
```

chính là nth node from end.

---

# 27. Pattern 7 — Merge Two Sorted Linked Lists

Đây là một template cực kỳ quan trọng.

LeetCode:

```text
21  Merge Two Sorted Lists
23  Merge k Sorted Lists
148 Sort List
```

Input:

```text
list1:
1 -> 3 -> 5

list2:
2 -> 4 -> 6
```

Output:

```text
1 -> 2 -> 3 -> 4 -> 5 -> 6
```

---

# 28. Ý tưởng

Có:

```text
p1
p2
```

So sánh:

```java
p1.val
p2.val
```

Node nhỏ hơn được nối vào result.

Dummy rất hữu ích:

```text
dummy -> result
```

---

# 29. Merge Template chuẩn

```java
public ListNode mergeTwoLists(
        ListNode list1,
        ListNode list2) {

    ListNode dummy = new ListNode(0);
    ListNode tail = dummy;

    while (list1 != null && list2 != null) {

        if (list1.val <= list2.val) {

            tail.next = list1;
            list1 = list1.next;

        } else {

            tail.next = list2;
            list2 = list2.next;
        }

        tail = tail.next;
    }

    tail.next = list1 != null
            ? list1
            : list2;

    return dummy.next;
}
```

---

# 30. Một invariant đáng nhớ

```text
dummy.next ... tail
```

luôn chứa:

> tất cả những node nhỏ nhất đã được xử lý và đã sorted.

---

# 31. Pattern 8 — Split + Reverse + Merge

Đây là một **composite pattern** cực kỳ quan trọng.

Nhiều bài Linked List Medium thực chất chỉ là kết hợp:

```text
find middle
+
reverse
+
merge
```

Hai bài điển hình:

```text
234 Palindrome Linked List
143 Reorder List
```

---

# 32. LeetCode 234 — Palindrome Linked List

Problem:

```text
1 -> 2 -> 3 -> 2 -> 1
```

return:

```text
true
```

Naive solution:

```text
Linked List -> Array
```

sau đó two pointers.

Complexity:

```text
Time  O(n)
Space O(n)
```

Nhưng ta có thể:

```text
Time  O(n)
Space O(1)
```

---

# 33. Pattern

### Step 1

Find middle:

```text
1 -> 2 -> 3 -> 2 -> 1
          ^
         slow
```

### Step 2

Reverse second half:

```text
1 -> 2 -> 3

1 -> 2
```

### Step 3

Compare:

```text
first half

1 -> 2

second half reversed

1 -> 2
```

---

## Implementation

```java
public boolean isPalindrome(ListNode head) {

    if (head == null || head.next == null) {
        return true;
    }

    ListNode slow = head;
    ListNode fast = head;

    while (fast != null && fast.next != null) {
        slow = slow.next;
        fast = fast.next.next;
    }

    ListNode second = reverse(slow);
    ListNode first = head;

    while (second != null) {

        if (first.val != second.val) {
            return false;
        }

        first = first.next;
        second = second.next;
    }

    return true;
}

private ListNode reverse(ListNode head) {

    ListNode prev = null;
    ListNode current = head;

    while (current != null) {

        ListNode next = current.next;

        current.next = prev;

        prev = current;
        current = next;
    }

    return prev;
}
```

---

# 34. LeetCode 143 — Reorder List

Problem:

```text
1 -> 2 -> 3 -> 4 -> 5
```

transform thành:

```text
1 -> 5 -> 2 -> 4 -> 3
```

Pattern:

```text
Find Middle
     ↓
Split List
     ↓
Reverse Second Half
     ↓
Merge Alternately
```

---

# 35. Step 1 — Find Middle

```text
1 -> 2 -> 3 -> 4 -> 5
          ^
         slow
```

---

# 36. Step 2 — Split

Ta muốn:

```text
1 -> 2 -> 3

4 -> 5
```

Vì vậy thường find middle theo variant:

```java
ListNode slow = head;
ListNode fast = head;

while (fast.next != null &&
       fast.next.next != null) {

    slow = slow.next;
    fast = fast.next.next;
}
```

Sau đó:

```java
ListNode second = slow.next;
slow.next = null;
```

---

# 37. Step 3 — Reverse second half

```text
4 -> 5
```

thành:

```text
5 -> 4
```

---

# 38. Step 4 — Alternating Merge

```text
first:
1 -> 2 -> 3

second:
5 -> 4
```

Merge:

```text
1 -> 5 -> 2 -> 4 -> 3
```

---

## Full Implementation

```java
public void reorderList(ListNode head) {

    if (head == null || head.next == null) {
        return;
    }

    // 1. find middle
    ListNode slow = head;
    ListNode fast = head;

    while (fast.next != null &&
           fast.next.next != null) {

        slow = slow.next;
        fast = fast.next.next;
    }

    // 2. split
    ListNode second = slow.next;
    slow.next = null;

    // 3. reverse second half
    second = reverse(second);

    // 4. alternating merge
    ListNode first = head;

    while (second != null) {

        ListNode next1 = first.next;
        ListNode next2 = second.next;

        first.next = second;
        second.next = next1;

        first = next1;
        second = next2;
    }
}
```

---

# 39. Pattern 9 — Delete Node / Skip Node

Một thao tác thường xuyên xuất hiện là:

```java
prev.next = current.next;
```

Hoặc nếu biết node trước target:

```java
prev.next = target.next;
```

Concept:

```text
prev -> target -> next
```

biến thành:

```text
prev ----------> next
```

---

# 40. LeetCode 83 — Remove Duplicates from Sorted List

Input:

```text
1 -> 1 -> 2 -> 3 -> 3
```

Output:

```text
1 -> 2 -> 3
```

Vì list đã sorted nên duplicate nằm cạnh nhau.

```java
public ListNode deleteDuplicates(ListNode head) {

    ListNode current = head;

    while (current != null &&
           current.next != null) {

        if (current.val == current.next.val) {

            current.next = current.next.next;

        } else {

            current = current.next;
        }
    }

    return head;
}
```

Điểm quan trọng:

Khi delete:

```java
current.next = current.next.next;
```

ta **không move current**.

Vì node tiếp theo mới cũng có thể duplicate.

Ví dụ:

```text
1 -> 1 -> 1
```

---

# 41. LeetCode 82 — Remove Duplicates from Sorted List II

Problem khó hơn.

Input:

```text
1 -> 2 -> 3 -> 3 -> 4 -> 4 -> 5
```

Output:

```text
1 -> 2 -> 5
```

Không phải giữ một `3`.

Mà phải remove **toàn bộ group duplicate**.

Đây là bài điển hình của:

```text
dummy
+
prev
+
current
+
skip group
```

Implementation:

```java
public ListNode deleteDuplicates(ListNode head) {

    ListNode dummy = new ListNode(0, head);

    ListNode prev = dummy;
    ListNode current = head;

    while (current != null) {

        if (current.next != null &&
            current.val == current.next.val) {

            int duplicateValue = current.val;

            while (current != null &&
                   current.val == duplicateValue) {

                current = current.next;
            }

            prev.next = current;

        } else {

            prev = current;
            current = current.next;
        }
    }

    return dummy.next;
}
```

---

# 42. Pattern 10 — Partition / Build Multiple Lists

Đây là pattern khá mạnh nhưng ít người để ý.

Idea:

Thay vì sửa một list phức tạp, ta tạo nhiều list con.

Ví dụ:

```text
less
equal
greater
```

Sau đó concatenate.

---

# 43. LeetCode 86 — Partition List

Input:

```text
1 -> 4 -> 3 -> 2 -> 5 -> 2
```

```text
x = 3
```

Output:

```text
1 -> 2 -> 2 -> 4 -> 3 -> 5
```

Yêu cầu:

```text
nodes < x
```

đứng trước:

```text
nodes >= x
```

nhưng giữ relative order.

---

# 44. Solution

Tạo:

```text
small list

large list
```

```java
ListNode smallDummy = new ListNode(0);
ListNode largeDummy = new ListNode(0);

ListNode small = smallDummy;
ListNode large = largeDummy;
```

Traversal:

```java
while (head != null) {

    if (head.val < x) {
        small.next = head;
        small = small.next;
    } else {
        large.next = head;
        large = large.next;
    }

    head = head.next;
}
```

Cuối:

```java
large.next = null;

small.next = largeDummy.next;

return smallDummy.next;
```

Full:

```java
public ListNode partition(ListNode head, int x) {

    ListNode smallDummy = new ListNode(0);
    ListNode largeDummy = new ListNode(0);

    ListNode small = smallDummy;
    ListNode large = largeDummy;

    while (head != null) {

        if (head.val < x) {

            small.next = head;
            small = small.next;

        } else {

            large.next = head;
            large = large.next;
        }

        head = head.next;
    }

    large.next = null;

    small.next = largeDummy.next;

    return smallDummy.next;
}
```

---

# 45. Vì sao `large.next = null` quan trọng?

Giả sử original list:

```text
1 -> 4 -> 2
```

Ta di chuyển reference của node nhưng node cuối large có thể vẫn giữ `next` cũ.

Nếu không terminate:

```java
large.next = null;
```

có thể:

* tạo incorrect connection
* tạo cycle
* list result chứa node không mong muốn

---

# 46. Pattern 11 — Intersection of Two Linked Lists

LeetCode 160.

Cho:

```text
A:
4 -> 1
       \
        8 -> 4 -> 5
       /
5 -> 6 -> 1
B:
```

Ta cần tìm node `8`.

Lưu ý:

Không so sánh:

```java
a.val == b.val
```

Mà phải so:

```java
a == b
```

vì intersection nghĩa là **same node object**.

---

# 47. Cách 1 — Length Alignment

Nếu:

```text
lengthA = 8
lengthB = 5
```

advance A:

```text
8 - 5 = 3
```

nodes.

Sau đó move cả hai.

---

# 48. Cách 2 — Pointer Switching

Đây là solution nổi tiếng hơn.

```java
public ListNode getIntersectionNode(
        ListNode headA,
        ListNode headB) {

    ListNode a = headA;
    ListNode b = headB;

    while (a != b) {

        a = (a == null)
                ? headB
                : a.next;

        b = (b == null)
                ? headA
                : b.next;
    }

    return a;
}
```

---

# 49. Vì sao pointer switching hoạt động?

Giả sử:

```text
A unique length = a
B unique length = b
shared length   = c
```

Pointer A đi:

```text
a + c + b
```

Pointer B đi:

```text
b + c + a
```

Hai distance bằng nhau:

```text
a + b + c
```

nên hai pointer sẽ được align tại intersection.

Nếu không intersection:

```text
a == b == null
```

---

# 50. Pattern 12 — Reverse a Sublist

Đây là extension của Reverse Linked List.

Bài:

```text
92 Reverse Linked List II
```

Input:

```text
1 -> 2 -> 3 -> 4 -> 5

left = 2
right = 4
```

Output:

```text
1 -> 4 -> 3 -> 2 -> 5
```

Ta không reverse toàn bộ list mà chỉ reverse:

```text
2 -> 3 -> 4
```

---

# 51. Head Insertion Technique

Một template rất nổi tiếng:

```java
public ListNode reverseBetween(
        ListNode head,
        int left,
        int right) {

    ListNode dummy = new ListNode(0, head);

    ListNode prev = dummy;

    for (int i = 1; i < left; i++) {
        prev = prev.next;
    }

    ListNode current = prev.next;

    for (int i = 0;
         i < right - left;
         i++) {

        ListNode move = current.next;

        current.next = move.next;

        move.next = prev.next;

        prev.next = move;
    }

    return dummy.next;
}
```

---

# 52. Visualize Head Insertion

Ví dụ:

```text
1 -> 2 -> 3 -> 4 -> 5
```

reverse `[2,4]`.

Ban đầu:

```text
prev
 |
 v
1 -> 2 -> 3 -> 4 -> 5
     ^
   current
```

Lấy `3` ra:

```text
1 -> 2 -> 4 -> 5
```

insert `3` sau `prev`:

```text
1 -> 3 -> 2 -> 4 -> 5
```

Lấy `4`:

```text
1 -> 3 -> 2 -> 5
```

insert:

```text
1 -> 4 -> 3 -> 2 -> 5
```

Done.

---

# 53. Pattern 13 — Reverse in Groups

LeetCode 25:

```text
Reverse Nodes in k-Group
```

Input:

```text
1 -> 2 -> 3 -> 4 -> 5
```

```text
k = 2
```

Output:

```text
2 -> 1 -> 4 -> 3 -> 5
```

Pattern:

```text
Find group
    ↓
Reverse group
    ↓
Connect group
    ↓
Continue
```

---

# 54. Group Structure

Ta thường maintain:

```text
groupPrev
groupNext
```

Ví dụ:

```text
dummy -> 1 -> 2 -> 3 -> 4 -> 5
 ^
groupPrev
```

Nếu:

```text
k = 2
```

thì group end:

```text
2
```

và:

```text
groupNext = 3
```

Ta reverse:

```text
1 -> 2
```

thành:

```text
2 -> 1
```

rồi reconnect.

---

# 55. Template Reverse K Group

```java
public ListNode reverseKGroup(
        ListNode head,
        int k) {

    ListNode dummy = new ListNode(0, head);

    ListNode groupPrev = dummy;

    while (true) {

        ListNode kth = getKth(groupPrev, k);

        if (kth == null) {
            break;
        }

        ListNode groupNext = kth.next;

        ListNode prev = groupNext;
        ListNode current = groupPrev.next;

        while (current != groupNext) {

            ListNode next = current.next;

            current.next = prev;

            prev = current;
            current = next;
        }

        ListNode oldGroupStart =
                groupPrev.next;

        groupPrev.next = kth;

        groupPrev = oldGroupStart;
    }

    return dummy.next;
}

private ListNode getKth(
        ListNode current,
        int k) {

    while (current != null && k > 0) {
        current = current.next;
        k--;
    }

    return current;
}
```

Đây là bài rất tốt để luyện khả năng quản lý pointer.

---

# 56. Pattern 14 — Linked List Merge Sort

LeetCode 148:

```text
Sort List
```

Nếu dùng:

```text
Array + sort
```

thì mất lợi thế Linked List.

Merge Sort cực kỳ phù hợp với Linked List.

---

# 57. Vì sao Merge Sort phù hợp?

Array:

```text
merge
```

cần thêm memory.

Linked List:

```text
merge
```

chỉ cần thay đổi pointer.

Pattern:

```text
Find middle
      ↓
Split
   ↙      ↘
Sort      Sort
   ↘      ↙
     Merge
```

---

# 58. Implementation

```java
public ListNode sortList(ListNode head) {

    if (head == null ||
        head.next == null) {

        return head;
    }

    ListNode slow = head;
    ListNode fast = head.next;

    while (fast != null &&
           fast.next != null) {

        slow = slow.next;
        fast = fast.next.next;
    }

    ListNode right = slow.next;
    slow.next = null;

    ListNode left = sortList(head);
    right = sortList(right);

    return merge(left, right);
}
```

Merge:

```java
private ListNode merge(
        ListNode a,
        ListNode b) {

    ListNode dummy = new ListNode(0);
    ListNode tail = dummy;

    while (a != null && b != null) {

        if (a.val <= b.val) {

            tail.next = a;
            a = a.next;

        } else {

            tail.next = b;
            b = b.next;
        }

        tail = tail.next;
    }

    tail.next = a != null ? a : b;

    return dummy.next;
}
```

Complexity:

```text
Time  O(n log n)

Recursive stack:
O(log n)
```

---

# 59. Pattern 15 — Recursion on Linked List

Linked List bản chất có recursive structure:

```text
List =
node + remaining list
```

hay:

```text
head
+
head.next
```

Do đó rất nhiều bài có thể nghĩ:

> Nếu recursion đã giải được phần list bắt đầu từ `head.next`, tôi xử lý `head` thế nào?

Các bài thích hợp:

```text
Reverse Linked List
Merge Two Sorted Lists
Swap Nodes in Pairs
Remove Nodes
```

---

# 60. LeetCode 24 — Swap Nodes in Pairs

Input:

```text
1 -> 2 -> 3 -> 4
```

Output:

```text
2 -> 1 -> 4 -> 3
```

Recursive thought:

```text
swap(1,2)
+
solve(3 -> 4)
```

Implementation:

```java
public ListNode swapPairs(ListNode head) {

    if (head == null ||
        head.next == null) {

        return head;
    }

    ListNode second = head.next;

    head.next =
            swapPairs(second.next);

    second.next = head;

    return second;
}
```

---

# 61. Pattern 16 — Add Numbers Digit by Digit

Một nhóm Linked List problem thực chất tương tự:

```text
school arithmetic
```

Ví dụ:

```text
2 -> 4 -> 3

5 -> 6 -> 4
```

represents:

```text
342
465
```

Output:

```text
7 -> 0 -> 8
```

because:

```text
342 + 465 = 807
```

LeetCode 2:

```text
Add Two Numbers
```

---

# 62. Core template

Ta maintain:

```java
int carry = 0;
```

Mỗi step:

```java
int sum = a + b + carry;

digit = sum % 10;

carry = sum / 10;
```

Implementation:

```java
public ListNode addTwoNumbers(
        ListNode l1,
        ListNode l2) {

    ListNode dummy = new ListNode(0);
    ListNode tail = dummy;

    int carry = 0;

    while (l1 != null ||
           l2 != null ||
           carry != 0) {

        int x = l1 != null
                ? l1.val
                : 0;

        int y = l2 != null
                ? l2.val
                : 0;

        int sum = x + y + carry;

        carry = sum / 10;

        tail.next =
                new ListNode(sum % 10);

        tail = tail.next;

        if (l1 != null) {
            l1 = l1.next;
        }

        if (l2 != null) {
            l2 = l2.next;
        }
    }

    return dummy.next;
}
```

---

# 63. Tổng hợp các pattern quan trọng nhất

## Pattern 1 — Traversal

```java
while (current != null) {
    current = current.next;
}
```

Ứng dụng:

```text
count
find
check
update
```

---

## Pattern 2 — Dummy Node

```java
ListNode dummy = new ListNode(0, head);
```

Dùng khi:

```text
head có thể bị thay đổi
delete
insert
merge
partition
```

---

## Pattern 3 — Reverse

```java
ListNode prev = null;
ListNode current = head;

while (current != null) {

    ListNode next = current.next;

    current.next = prev;

    prev = current;
    current = next;
}
```

---

## Pattern 4 — Slow / Fast

```java
while (fast != null &&
       fast.next != null) {

    slow = slow.next;
    fast = fast.next.next;
}
```

Dùng cho:

```text
middle
cycle
split
palindrome
merge sort
```

---

## Pattern 5 — Pointer Gap

```text
fast ------ n nodes ------ slow
```

Dùng cho:

```text
nth node from end
remove nth node from end
```

---

## Pattern 6 — Merge

```java
while (a != null && b != null) {

    if (a.val <= b.val) {
        ...
    } else {
        ...
    }
}
```

---

## Pattern 7 — Split + Reverse + Merge

Dùng cho:

```text
Palindrome
Reorder List
```

---

## Pattern 8 — Floyd Cycle

```java
slow = slow.next;
fast = fast.next.next;
```

Dùng cho:

```text
detect cycle
cycle entry
```

---

## Pattern 9 — Skip / Delete

```java
prev.next = current.next;
```

---

## Pattern 10 — Multiple Lists

```text
small
large
```

hoặc:

```text
less
equal
greater
```

Sau đó nối lại.

---

## Pattern 11 — Reverse Range / Group

```text
reverseBetween
reverseKGroup
```

---

## Pattern 12 — Divide and Conquer

```text
find middle
split
recursive solve
merge
```

Điển hình:

```text
Merge Sort
```

---

# 64. Linked List Decision Tree

Khi đọc đề hãy hỏi theo thứ tự sau.

## Câu hỏi 1

Có cần:

```text
delete / insert / modify head?
```

Có →

```text
Dummy Node
```

---

## Câu hỏi 2

Có keyword:

```text
middle
half
cycle
```

→

```text
Fast + Slow Pointer
```

---

## Câu hỏi 3

Có:

```text
k-th / n-th from end
```

→

```text
Two Pointers with Fixed Gap
```

---

## Câu hỏi 4

Có:

```text
reverse
```

→

```text
prev/current/next
```

---

## Câu hỏi 5

Có:

```text
palindrome
reorder
```

→ nghĩ:

```text
Middle
+
Reverse
+
Compare/Merge
```

---

## Câu hỏi 6

Có:

```text
two sorted lists
```

→

```text
Merge + Dummy
```

---

## Câu hỏi 7

Có:

```text
sort linked list
```

→

```text
Merge Sort
=
Find Middle
+
Split
+
Merge
```

---

## Câu hỏi 8

Có:

```text
divide nodes based on condition
```

→

```text
Multiple Dummy Lists
+
Concatenate
```

---

# 65. Những lỗi pointer thường gặp

## Lỗi 1 — Mất phần còn lại của list

Sai:

```java
current.next = prev;
current = current.next;
```

Sau:

```java
current.next = prev;
```

`current.next` không còn chỉ vào original next nữa.

Đúng:

```java
ListNode next = current.next;

current.next = prev;

current = next;
```

---

# 66. Lỗi 2 — Return head thay vì dummy.next

Sai:

```java
return head;
```

trong bài có thể delete head.

Đúng:

```java
return dummy.next;
```

---

# 67. Lỗi 3 — So sánh node bằng value

Trong Intersection/Cycle:

Sai:

```java
a.val == b.val
```

Đúng:

```java
a == b
```

Vì ta quan tâm tới **node identity**, không phải value.

---

# 68. Lỗi 4 — Không break connection khi split

Ví dụ merge sort.

Sai:

```java
ListNode right = slow.next;
```

nhưng quên:

```java
slow.next = null;
```

Hai list vẫn nối với nhau.

Recursive call có thể không giảm problem size và gây:

```text
infinite recursion
```

---

# 69. Lỗi 5 — NullPointerException với fast

Sai:

```java
while (fast.next.next != null)
```

Nếu:

```java
fast == null
```

hoặc:

```java
fast.next == null
```

sẽ lỗi.

Template an toàn:

```java
while (fast != null &&
       fast.next != null) {
```

---

# 70. Các pointer name nên dùng khi interview

Nên dùng tên có ý nghĩa:

```java
current
prev
next

slow
fast

dummy
tail

first
second

left
right

groupPrev
groupNext
```

Tránh:

```java
a1
x
temp1
temp2
node123
```

nếu không cần thiết.

---

# 71. Linked List Template Library

Đây là phần nên lưu vào note để ôn interview.

## Template A — Traverse

```java
ListNode current = head;

while (current != null) {

    // process

    current = current.next;
}
```

---

## Template B — Dummy

```java
ListNode dummy = new ListNode(0);
dummy.next = head;

return dummy.next;
```

---

## Template C — Reverse

```java
ListNode prev = null;
ListNode current = head;

while (current != null) {

    ListNode next = current.next;

    current.next = prev;

    prev = current;
    current = next;
}

return prev;
```

---

## Template D — Middle

```java
ListNode slow = head;
ListNode fast = head;

while (fast != null &&
       fast.next != null) {

    slow = slow.next;
    fast = fast.next.next;
}
```

---

## Template E — Cycle

```java
ListNode slow = head;
ListNode fast = head;

while (fast != null &&
       fast.next != null) {

    slow = slow.next;
    fast = fast.next.next;

    if (slow == fast) {
        return true;
    }
}
```

---

## Template F — Fixed Gap

```java
ListNode slow = dummy;
ListNode fast = dummy;

for (int i = 0; i <= n; i++) {
    fast = fast.next;
}

while (fast != null) {
    slow = slow.next;
    fast = fast.next;
}
```

---

## Template G — Merge

```java
ListNode dummy = new ListNode(0);
ListNode tail = dummy;

while (a != null && b != null) {

    if (a.val <= b.val) {

        tail.next = a;
        a = a.next;

    } else {

        tail.next = b;
        b = b.next;
    }

    tail = tail.next;
}

tail.next = a != null ? a : b;

return dummy.next;
```

---

# 72. LeetCode Roadmap cho Linked List

Nên luyện theo thứ tự này.

## Level 1 — Fundamental

```text
206 Reverse Linked List
21  Merge Two Sorted Lists
83  Remove Duplicates from Sorted List
203 Remove Linked List Elements
876 Middle of the Linked List
```

Mục tiêu:

```text
traversal
dummy
reverse
slow-fast
```

---

## Level 2 — Two Pointer

```text
19  Remove Nth Node From End
141 Linked List Cycle
142 Linked List Cycle II
160 Intersection of Two Linked Lists
```

Mục tiêu:

```text
pointer gap
Floyd
pointer alignment
```

---

## Level 3 — Combination Patterns

```text
234 Palindrome Linked List
143 Reorder List
86  Partition List
24  Swap Nodes in Pairs
2   Add Two Numbers
```

Mục tiêu:

```text
middle
reverse
merge
dummy
multiple lists
recursion
```

---

## Level 4 — Advanced Pointer Manipulation

```text
92 Reverse Linked List II

25 Reverse Nodes in k-Group

82 Remove Duplicates from Sorted List II
```

Mục tiêu:

```text
sublist manipulation
group manipulation
complex pointer rewiring
```

---

## Level 5 — Divide and Conquer

```text
148 Sort List
23 Merge k Sorted Lists
```

Mục tiêu:

```text
merge sort
heap
divide & conquer
```

---

# 73. Pattern Map cho các bài quan trọng

| Problem                   | Pattern chính               |
| ------------------------- | --------------------------- |
| 206 Reverse Linked List   | Reverse                     |
| 21 Merge Two Sorted Lists | Dummy + Merge               |
| 19 Remove Nth Node        | Dummy + Pointer Gap         |
| 876 Middle Linked List    | Fast/Slow                   |
| 141 Linked List Cycle     | Floyd                       |
| 142 Cycle II              | Floyd + Math                |
| 160 Intersection          | Pointer Switching           |
| 203 Remove Elements       | Dummy + Delete              |
| 83 Remove Duplicates      | Skip Node                   |
| 82 Remove Duplicates II   | Dummy + Skip Group          |
| 234 Palindrome            | Middle + Reverse + Compare  |
| 143 Reorder List          | Middle + Reverse + Merge    |
| 86 Partition List         | Multiple Dummy Lists        |
| 92 Reverse Linked List II | Reverse Sublist             |
| 24 Swap Nodes Pairs       | Pointer Rewrite / Recursion |
| 25 Reverse k Group        | Reverse Sublist + Group     |
| 148 Sort List             | Middle + Merge Sort         |
| 2 Add Two Numbers         | Parallel Traversal + Carry  |

---

# 74. Mental Model quan trọng nhất

Đừng nhìn Linked List như:

```text
một chuỗi node
```

Hãy nhìn nó như những **segments**:

```text
processed
|
v
...

current
|
v
...

unprocessed
|
v
...
```

Ví dụ reverse:

```text
<---- reversed ---->  <----- remaining ----->

3 -> 2 -> 1 -> null    4 -> 5 -> 6
^                       ^
prev                    current
```

Bạn chỉ cần duy trì đúng invariant giữa các segment.

---

# 75. Một nguyên tắc vàng khi thay đổi pointer

Trước bất kỳ câu lệnh kiểu:

```java
current.next = ...
```

hãy tự hỏi:

> Sau câu lệnh này tôi còn cách nào truy cập phần list phía sau không?

Nếu không chắc chắn, hãy save:

```java
ListNode next = current.next;
```

trước.

Đây là lý do template reverse luôn có:

```java
ListNode next = current.next;

current.next = prev;
```

chứ không làm ngược lại.

---

# 76. Cách explain Linked List trong Coding Interview

Ví dụ Reverse Linked List.

Bạn có thể nói:

> I'll iterate through the list while maintaining two pointers, `prev` and `current`.
> `prev` represents the already reversed portion, while `current` represents the remaining portion.
> Before reversing the current pointer, I save `current.next` because otherwise I would lose access to the rest of the list.
> Then I reverse the pointer, advance both pointers, and finally `prev` becomes the new head.

---

Find middle:

> I'll use a slow and fast pointer. The slow pointer moves one node at a time while the fast pointer moves two nodes at a time. Therefore, when the fast pointer reaches the end, the slow pointer will be at the middle of the list.

---

Remove Nth from end:

> I'll maintain a fixed gap of `n + 1` nodes between the fast and slow pointers. When the fast pointer reaches the end, the slow pointer will be immediately before the node that needs to be removed.

---

Merge Two Sorted Lists:

> I'll keep a tail pointer representing the end of the merged portion. At each step, I append the smaller of the two current nodes and advance the corresponding pointer.

---

# 77. Tư duy quan trọng nhất khi học Linked List

Thay vì học riêng:

```text
206
234
143
148
25
```

hãy nhận ra:

```text
206
    ↓
Reverse

876
    ↓
Find Middle

21
    ↓
Merge
```

Sau đó:

```text
234
=
876
+
206
+
Compare
```

```text
143
=
876
+
206
+
Merge Alternately
```

```text
148
=
876
+
Divide
+
21
```

```text
25
=
Find k nodes
+
206 on a range
+
Reconnect
```

Đây mới chính là cách học theo **pattern**.

---

# 78. Core Patterns cần thuộc lòng

Nếu chỉ có thời gian học 7 pattern Linked List, hãy thuộc:

```text
1. Dummy Node

2. Reverse Linked List

3. Fast & Slow Pointer

4. Fixed Gap Two Pointers

5. Merge Two Sorted Lists

6. Split + Reverse + Merge

7. Pointer Rewiring / Sublist Manipulation
```

Phần lớn bài Linked List interview đều được xây dựng từ những block này.

---

# 79. Cheat Sheet cuối cùng

```text
HEAD MAY CHANGE
    ↓
Dummy node
```

```text
MIDDLE / HALF
    ↓
Slow + Fast
```

```text
CYCLE
    ↓
Floyd Slow + Fast
```

```text
NTH FROM END
    ↓
Fixed pointer gap
```

```text
REVERSE
    ↓
prev / current / next
```

```text
PALINDROME
    ↓
Middle
+ Reverse second half
+ Compare
```

```text
REORDER
    ↓
Middle
+ Split
+ Reverse
+ Alternating merge
```

```text
SORT LINKED LIST
    ↓
Middle
+ Split
+ Merge Sort
```

```text
DELETE
    ↓
prev.next = current.next
```

```text
PARTITION
    ↓
Multiple dummy lists
+ concatenate
```

```text
REVERSE RANGE / GROUP
    ↓
Save boundaries
+ Reverse
+ Reconnect
```

---

# 80. Learning sequence đề xuất

Học và tự implement không nhìn code theo thứ tự:

```text
206 Reverse Linked List
        ↓
876 Middle Linked List
        ↓
141 Linked List Cycle
        ↓
21 Merge Two Sorted Lists
        ↓
203 Remove Elements
        ↓
19 Remove Nth From End
        ↓
160 Intersection
        ↓
234 Palindrome
        ↓
143 Reorder List
        ↓
86 Partition List
        ↓
92 Reverse Linked List II
        ↓
24 Swap Nodes in Pairs
        ↓
82 Remove Duplicates II
        ↓
148 Sort List
        ↓
25 Reverse Nodes in k-Group
```

Nếu bạn có thể tự implement và giải thích toàn bộ chuỗi này, phần **Linked List trong Coding Interview đã ở mức rất chắc**.
