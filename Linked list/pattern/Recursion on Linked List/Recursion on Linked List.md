Đúng pattern này rất đáng học vì **Recursion trên Linked List** xuất hiện ở nhiều bài, nhưng điểm khó là phải hiểu được **“recursive function đang đại diện cho cái gì”**, chứ không chỉ học thuộc `head.next`.

---

# 1. Recursion on Linked List là gì?

Với Linked List:

```text
1 → 2 → 3 → 4 → 5 → null
```

Mỗi node chỉ biết node tiếp theo:

```text
1.next = 2
2.next = 3
3.next = 4
...
```

Do đó Linked List có một cấu trúc đệ quy tự nhiên:

```text
List(1 → 2 → 3 → 4)

        =
Node(1) + List(2 → 3 → 4)
```

Hay:

```text
head
 ↓
1 → [2 → 3 → 4]
     ↑
     head.next
```

Vì vậy rất nhiều bài có thể viết theo tư tưởng:

```java
solve(head)
    ↓
solve(head.next)
```

---

# 2. Mental model quan trọng nhất

Khi gặp recursion trên Linked List, **đừng nghĩ trước về toàn bộ list**.

Hãy hỏi:

> **Nếu tôi đã giải quyết xong `head.next`, tôi phải làm gì với `head`?**

Ví dụ:

```text
1 → 2 → 3 → 4
```

Ta gọi:

```java
solve(1)
```

thì:

```text
solve(1)
    ↓
solve(2)
    ↓
solve(3)
    ↓
solve(4)
```

Tới node cuối:

```text
solve(4)
```

thì:

```java
if (head == null || head.next == null) {
    return head;
}
```

Sau đó recursion **unwind**:

```text
solve(4) → return 4
solve(3) → xử lý 3 + kết quả của 4
solve(2) → xử lý 2 + kết quả của 3,4
solve(1) → xử lý 1 + kết quả của 2,3,4
```

Đây chính là điểm quan trọng:

```text
CALL STACK

solve(1)
   ↓
solve(2)
   ↓
solve(3)
   ↓
solve(4)
   ↓
base case

       ↑
       │
    unwind
       │
       ↑
```

---

# 3. Có 3 dạng Recursion Linked List rất hay gặp

Mình recommend phân loại như sau:

```text
Recursion on Linked List
│
├── 1. Traverse / Process
│      └── Reverse, compare, calculate...
│
├── 2. Divide & Conquer
│      └── Merge Sort, Merge K Lists...
│
└── 3. Recursive Structural Manipulation
       └── Reverse K Group, Reverse Between...
```

Ngoài ra còn một dạng rất quan trọng:

```text
Recursive + Two Pointers
```

như Palindrome Linked List.

---

# 4. Pattern 1 — Recursive Traversal

Đây là dạng cơ bản nhất.

## Ví dụ: Print Linked List in Reverse

### Đề bài

Cho một singly linked list, hãy in các phần tử theo thứ tự ngược lại.

```text
Input:
1 → 2 → 3 → 4 → 5

Output:
5 4 3 2 1
```

---

## Tư tưởng

Thay vì:

```text
1
2
3
4
5
```

ta đi đến cuối trước:

```text
1
 ↓
2
 ↓
3
 ↓
4
 ↓
5
 ↓
null
```

Sau đó khi recursion unwind:

```text
5
4
3
2
1
```

### Implement

```java
public void printReverse(ListNode head) {
    if (head == null) {
        return;
    }

    printReverse(head.next);

    System.out.println(head.val);
}
```

---

## Luồng chạy

Input:

```text
1 → 2 → 3 → null
```

Call:

```text
printReverse(1)
```

chưa print `1`.

Gọi:

```text
printReverse(2)
```

chưa print `2`.

Gọi:

```text
printReverse(3)
```

chưa print `3`.

Gọi:

```text
printReverse(null)
```

return.

Bây giờ unwind:

```text
printReverse(3)
→ print 3

printReverse(2)
→ print 2

printReverse(1)
→ print 1
```

Output:

```text
3 2 1
```

---

# 5. Pattern quan trọng: Before recursion vs After recursion

Đây là một trong những trick quan trọng nhất.

### Process trước recursion

```java
process(head);
solve(head.next);
```

thì xử lý:

```text
1 → 2 → 3
```

theo:

```text
1 2 3
```

---

### Process sau recursion

```java
solve(head.next);
process(head);
```

thì xử lý:

```text
3 2 1
```

Tức là:

```text
BEFORE recursion
→ forward order

AFTER recursion
→ reverse order
```

Đây là nền tảng của rất nhiều bài Linked List recursion.

---

# 6. Pattern 2 — Recursive Reverse Linked List

Đây là bài kinh điển.

## Đề bài

**LeetCode 206 — Reverse Linked List**

Cho:

```text
1 → 2 → 3 → 4 → 5
```

reverse thành:

```text
5 → 4 → 3 → 2 → 1
```

---

## Tư tưởng

Ta giả sử:

```text
reverse(2 → 3 → 4 → 5)
```

đã được giải quyết.

Kết quả:

```text
5 → 4 → 3 → 2
```

Còn:

```text
1 → 2
```

Ta cần biến:

```text
1 → 2
```

thành:

```text
1 ← 2
```

Tức:

```java
head.next.next = head;
```

Sau đó:

```java
head.next = null;
```

---

## Implement

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

---

# 7. Trace cực kỳ quan trọng

Input:

```text
1 → 2 → 3 → null
```

Call:

```text
reverse(1)
    ↓
reverse(2)
    ↓
reverse(3)
```

`3` là base case:

```text
reverse(3) → return 3
```

Bây giờ:

### Unwind tại node 2

Hiện tại:

```text
2 → 3
```

Ta chạy:

```java
head.next.next = head;
```

tức:

```java
3.next = 2;
```

thành:

```text
2 ← 3
```

Sau đó:

```java
head.next = null;
```

```text
2    3
     ↓
     2
```

Kết quả:

```text
3 → 2
```

---

### Unwind tại node 1

Hiện tại:

```text
1 → 2
```

thực hiện:

```java
head.next.next = head;
```

tức:

```text
2.next = 1;
```

thành:

```text
1 ← 2 ← 3
```

Sau:

```java
head.next = null;
```

Kết quả:

```text
3 → 2 → 1
```

---

# 8. Pattern 3 — Recursive Merge Sort

Đây chính là bài vừa nói ở câu trước.

## Đề bài

**LeetCode 148 — Sort List**

Cho:

```text
4 → 2 → 1 → 3
```

sort thành:

```text
1 → 2 → 3 → 4
```

Yêu cầu thường hướng tới:

```text
O(n log n)
```

---

## Tư tưởng

Recursion đại diện cho:

> **Sort toàn bộ list bắt đầu từ node `head`.**

```java
sortList(head)
```

có nghĩa:

```text
"hãy trả về một sorted linked list chứa toàn bộ node từ head trở đi"
```

Ta chia:

```text
4 → 2 → 1 → 3

        ↓

4 → 2    |    1 → 3
```

Sau đó recursion:

```text
sort(4 → 2)
sort(1 → 3)
```

Cuối cùng merge.

---

## Implement

```java
public ListNode sortList(ListNode head) {
    if (head == null || head.next == null) {
        return head;
    }

    // Split
    ListNode slow = head;
    ListNode fast = head.next;

    while (fast != null && fast.next != null) {
        slow = slow.next;
        fast = fast.next.next;
    }

    ListNode right = slow.next;
    slow.next = null;

    // Recursively sort
    ListNode leftSorted = sortList(head);
    ListNode rightSorted = sortList(right);

    // Merge
    return merge(leftSorted, rightSorted);
}
```

---

## Mental model

```text
sortList(head)
      │
      ├── sortList(left)
      │
      ├── sortList(right)
      │
      └── merge(left, right)
```

Đây là:

```text
Divide
  ↓
Recursion
  ↓
Combine
```

---

# 9. Pattern 4 — Recursive Reverse K Group

## Đề bài

**LeetCode 25 — Reverse Nodes in k-Group**

Cho:

```text
1 → 2 → 3 → 4 → 5 → 6 → 7 → 8
```

`k = 3`.

Mỗi group đủ `3` node phải reverse:

```text
3 → 2 → 1 → 6 → 5 → 4 → 7 → 8
```

Group cuối chỉ có:

```text
7 → 8
```

không đủ `3`, giữ nguyên.

---

# 10. Tư tưởng recursion

Đây là một pattern rất đẹp:

> **Tôi xử lý group hiện tại, sau đó giao phần còn lại cho recursion.**

Ví dụ:

```text
1 → 2 → 3 | 4 → 5 → 6 | 7 → 8
```

Xử lý group đầu:

```text
3 → 2 → 1
```

Sau đó:

```text
reverseKGroup(4 → 5 → 6)
```

trả về:

```text
6 → 5 → 4 → 7 → 8
```

Cuối cùng nối:

```text
3 → 2 → 1 → 6 → 5 → 4 → 7 → 8
```

---

## Implement

```java
public ListNode reverseKGroup(ListNode head, int k) {

    // Check whether there are k nodes
    ListNode node = head;

    for (int i = 0; i < k; i++) {
        if (node == null) {
            return head;
        }

        node = node.next;
    }

    // Reverse current group
    ListNode prev = null;
    ListNode curr = head;

    for (int i = 0; i < k; i++) {
        ListNode next = curr.next;

        curr.next = prev;
        prev = curr;
        curr = next;
    }

    // head is now tail of reversed group
    head.next = reverseKGroup(curr, k);

    return prev;
}
```

---

# 11. Trace

Input:

```text
1 → 2 → 3 → 4 → 5 → 6 → 7 → 8
k = 3
```

### Call 1

Current group:

```text
1 → 2 → 3
```

Reverse:

```text
3 → 2 → 1
```

`curr` đang ở:

```text
4
```

Gọi:

```java
reverseKGroup(4, 3)
```

---

### Call 2

Current:

```text
4 → 5 → 6
```

Reverse:

```text
6 → 5 → 4
```

`curr`:

```text
7
```

Gọi:

```java
reverseKGroup(7, 3)
```

---

### Call 3

Không đủ 3 node:

```text
7 → 8
```

return:

```text
7 → 8
```

---

### Unwind

Call 2:

```text
6 → 5 → 4
```

`head` của group là `4`.

Ta:

```java
head.next = reverseResult;
```

nên:

```text
6 → 5 → 4 → 7 → 8
```

Call 1:

```text
3 → 2 → 1
```

`head` của group là `1`.

Nối:

```text
3 → 2 → 1 → 6 → 5 → 4 → 7 → 8
```

---

# 12. Pattern 5 — Recursive Palindrome Linked List

Đây là một pattern rất hay vì recursion giúp ta mô phỏng việc đi từ **hai đầu vào giữa**.

## Đề bài

**LeetCode 234 — Palindrome Linked List**

Cho:

```text
1 → 2 → 2 → 1
```

kiểm tra có phải palindrome không.

Output:

```text
true
```

Ví dụ:

```text
1 → 2 → 3
```

Output:

```text
false
```

---

# 13. Tư tưởng

Linked List không có:

```text
head
tail
```

theo kiểu random access.

Ta có thể dùng recursion để đi đến cuối.

Đồng thời giữ một pointer:

```java
front
```

bắt đầu từ head.

Ví dụ:

```text
front
 ↓
1 → 2 → 2 → 1
             ↑
          recursion
```

Recursion đi tới cuối:

```text
1 → 2 → 2 → 1
             ↑
```

Khi unwind:

```text
compare:

front = 1
back  = 1
```

sau đó:

```text
front = 2
back  = 2
```

---

## Implement

```java
private ListNode front;

public boolean isPalindrome(ListNode head) {
    front = head;
    return check(head);
}

private boolean check(ListNode curr) {
    if (curr == null) {
        return true;
    }

    if (!check(curr.next)) {
        return false;
    }

    if (front.val != curr.val) {
        return false;
    }

    front = front.next;

    return true;
}
```

---

# 14. Trace

```text
1 → 2 → 2 → 1
↑
front
```

Recursion đi:

```text
check(1)
 ↓
check(2)
 ↓
check(2)
 ↓
check(1)
 ↓
check(null)
```

Unwind:

### Node cuối

```text
front = 1
curr  = 1

equal
```

Move:

```text
front → 2
```

### Node thứ 3

```text
front = 2
curr  = 2

equal
```

Move:

```text
front → 2
```

### Node thứ 2

```text
front = 2
curr = 2
```

equal.

### Node đầu

```text
front = 1
curr = 1
```

equal.

→ `true`.

---

# 15. Pattern 6 — Recursive Merge Two Sorted Lists

## Đề bài

**LeetCode 21 — Merge Two Sorted Lists**

Cho:

```text
list1 = 1 → 3 → 5
list2 = 2 → 4 → 6
```

Merge thành:

```text
1 → 2 → 3 → 4 → 5 → 6
```

---

# 16. Tư tưởng

Ta so sánh hai head:

```text
1 → 3 → 5
↑

2 → 4 → 6
↑
```

`1 < 2`, vậy:

```text
1
```

chắc chắn là node đầu tiên.

Phần còn lại:

```text
3 → 5
```

và:

```text
2 → 4 → 6
```

Vậy:

```text
1.next = merge(3 → 5, 2 → 4 → 6)
```

Đây là recursion.

---

## Implement

```java
public ListNode mergeTwoLists(ListNode list1, ListNode list2) {

    if (list1 == null) {
        return list2;
    }

    if (list2 == null) {
        return list1;
    }

    if (list1.val <= list2.val) {
        list1.next = mergeTwoLists(list1.next, list2);
        return list1;
    } else {
        list2.next = mergeTwoLists(list1, list2.next);
        return list2;
    }
}
```

---

# 17. Trace

```text
1 → 3 → 5
2 → 4 → 6
```

Compare:

```text
1 < 2
```

nên:

```text
1.next = merge(3→5, 2→4→6)
```

Compare:

```text
3 > 2
```

nên:

```text
2.next = merge(3→5, 4→6)
```

Compare:

```text
3 < 4
```

nên:

```text
3.next = merge(5, 4→6)
```

Compare:

```text
5 > 4
```

nên:

```text
4.next = merge(5, 6)
```

Compare:

```text
5 < 6
```

nên:

```text
5.next = merge(null, 6)
```

Base:

```text
return 6
```

Kết quả unwind:

```text
1 → 2 → 3 → 4 → 5 → 6
```

---

# 18. Pattern 7 — Remove Nodes Recursively

Một dạng khác rất hay gặp.

## Đề bài

**LeetCode 203 — Remove Linked List Elements**

Cho:

```text
1 → 2 → 6 → 3 → 6
```

và:

```text
val = 6
```

Xóa tất cả node có value `6`.

Kết quả:

```text
1 → 2 → 3
```

---

# 19. Tư tưởng

Đừng nghĩ:

> Tôi phải xử lý cả list.

Hãy nghĩ:

> `removeElements(head.next)` sẽ trả về list đã xóa sạch các `6` từ phía sau. Tôi chỉ cần quyết định `head` có giữ lại hay không.

Ví dụ:

```text
1 → 2 → 6 → 3 → 6
```

Ta gọi:

```text
remove(1)
    ↓
remove(2)
    ↓
remove(6)
    ↓
remove(3)
    ↓
remove(6)
```

Base:

```text
remove(null) → null
```

Unwind.

---

## Implement

```java
public ListNode removeElements(ListNode head, int val) {

    if (head == null) {
        return null;
    }

    head.next = removeElements(head.next, val);

    if (head.val == val) {
        return head.next;
    }

    return head;
}
```

---

# 20. Điểm cực kỳ quan trọng

Dòng:

```java
head.next = removeElements(head.next, val);
```

có nghĩa:

> "Hãy sửa phần list phía sau `head`, rồi tôi nối `head` vào phần đã được sửa."

Đây là pattern rất tổng quát:

```java
head.next = solve(head.next);
```

Sau đó:

```java
return head;
```

hoặc:

```java
return head.next;
```

tùy `head` có được giữ hay không.

---

# 21. Tổng hợp các bài nên biết

| Problem                             | Pattern recursion   | Ý tưởng chính                                 |
| ----------------------------------- | ------------------- | --------------------------------------------- |
| **206 Reverse Linked List**         | Recursive reversal  | Reverse phần sau rồi nối `head`               |
| **21 Merge Two Sorted Lists**       | Recursive divide    | Chọn node nhỏ hơn làm head                    |
| **203 Remove Linked List Elements** | Recursive traversal | Solve suffix rồi quyết định giữ `head`        |
| **234 Palindrome Linked List**      | Recursion + pointer | Unwind để so sánh từ cuối                     |
| **25 Reverse Nodes in k-Group**     | Recursive group     | Reverse group hiện tại + recurse phần còn lại |
| **92 Reverse Linked List II**       | Recursive sublist   | Reverse một đoạn + reconnect                  |
| **148 Sort List**                   | Divide & Conquer    | Split + recursively sort + merge              |
| **23 Merge k Sorted Lists**         | Divide & Conquer    | Recursively merge các list                    |
| **430 Flatten a Multilevel DLL**    | Recursive structure | Flatten child rồi nối với next                |

---

# 22. Đặc biệt: LeetCode 92 — Reverse Linked List II

Đây là bài rất đáng học sau `206`.

### Đề bài

Cho:

```text
1 → 2 → 3 → 4 → 5
```

`left = 2`, `right = 4`.

Reverse đoạn `[2,4]`:

```text
1 → 4 → 3 → 2 → 5
```

Pattern:

```text
1 → [2 → 3 → 4] → 5
       ↓
1 → [4 → 3 → 2] → 5
```

Bài này có thể giải bằng:

* Iterative Head Insert
* Recursive

Trong interview, **Head Insert thường dễ kiểm soát pointer hơn**, nhưng recursive version rất tốt để luyện pattern recursion.

---

# 23. Một cách phân loại để nhận diện Recursion

Khi gặp Linked List, hãy thử đặt câu hỏi:

### Case 1

> Bài toán trên `head.next` giống hệt bài toán trên `head`?

Ví dụ:

```text
removeElements(head)
```

→ thử:

```java
solve(head.next)
```

---

### Case 2

> Tôi có thể giải quyết suffix trước rồi xử lý `head` khi unwind không?

Ví dụ:

```text
Reverse
Palindrome
Remove Nodes
```

→ rất phù hợp recursion.

---

### Case 3

> Tôi có thể chia list thành các phần nhỏ, giải quyết từng phần rồi combine không?

Ví dụ:

```text
Sort List
Merge K Lists
```

→ **Divide & Conquer recursion**.

---

### Case 4

> Tôi xử lý một group hiện tại, sau đó phần còn lại là cùng một bài toán?

Ví dụ:

```text
Reverse K Group
```

→:

```text
solve(current group)
+
solve(rest)
```

---

# 24. Cheat Sheet — Recursion on Linked List

Đây là phần mình khuyên bạn ghi nhớ:

```text
                 LINKED LIST
                      │
                      ▼
                  RECURSION
                      │
        ┌─────────────┼─────────────┐
        ▼             ▼             ▼
     SUFFIX        UNWIND       DIVIDE/CONQUER
        │             │             │
   head.next      process head    split
        │             │             │
        ▼             ▼             ▼
    solve(next)    reverse       solve(left)
                   compare        solve(right)
                   reconnect      merge
```

### Template 1 — Process suffix

```java
public ListNode solve(ListNode head) {
    if (head == null) {
        return null;
    }

    head.next = solve(head.next);

    // process head

    return head;
}
```

### Template 2 — Reverse

```java
public ListNode solve(ListNode head) {
    if (head == null || head.next == null) {
        return head;
    }

    ListNode newHead = solve(head.next);

    head.next.next = head;
    head.next = null;

    return newHead;
}
```

### Template 3 — Divide & Conquer

```java
public ListNode solve(ListNode head) {

    if (head == null || head.next == null) {
        return head;
    }

    // split
    ListNode left = ...;
    ListNode right = ...;

    // recursive
    left = solve(left);
    right = solve(right);

    // combine
    return merge(left, right);
}
```

### Template 4 — Process current + recurse remainder

```java
public ListNode solve(ListNode head) {

    // process current part

    ListNode rest = solve(nextPart);

    // connect current part with rest

    return result;
}
```

Đây chính là pattern của **Reverse K Group**.

---

# 25. Thứ tự nên luyện

Nếu mục tiêu của bạn là phỏng vấn DSA, mình sẽ học theo thứ tự này:

```text
206 Reverse Linked List
        ↓
21 Merge Two Sorted Lists
        ↓
203 Remove Linked List Elements
        ↓
234 Palindrome Linked List
        ↓
92 Reverse Linked List II
        ↓
25 Reverse Nodes in k-Group
        ↓
148 Sort List
        ↓
23 Merge k Sorted Lists
```

Trong đó 4 bài quan trọng nhất để hình thành pattern là:

```text
206
 ↓
"solve(next) rồi xử lý head"

21
 ↓
"chọn head rồi recurse phần còn lại"

25
 ↓
"xử lý current group rồi recurse remainder"

148
 ↓
"divide → recurse → merge"
```

Nếu nắm được **4 mental model này**, bạn sẽ không còn nhìn recursion Linked List như một nhóm các bài riêng lẻ nữa, mà sẽ thấy chúng đều xoay quanh một câu hỏi:

> **“Nếu tôi đã biết kết quả của phần `head.next` / phần còn lại, thì tôi cần làm gì để biến nó thành kết quả cho `head`?”**
