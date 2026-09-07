Chắc chắn. **Linked List Merge Sort** là một pattern rất đáng học vì nó vừa giúp bạn hiểu Merge Sort, vừa là một pattern kinh điển cho các bài linked list cần sort.

Mình sẽ đi theo thứ tự:

1. Tại sao Merge Sort đặc biệt phù hợp với Linked List
2. Pattern tổng quát
3. Implement recursive chuẩn
4. Trace từng step bằng ví dụ
5. Implement với `split` bằng fast/slow
6. Implement bottom-up iterative
7. So sánh các implementation
8. Những lỗi pointer dễ gặp
9. Cheat sheet để nhớ khi phỏng vấn

---

# 1. Linked List Merge Sort là gì?

Bài kinh điển:

**LeetCode 148 — Sort List**

Cho:

```text
4 → 2 → 1 → 3
```

Sort thành:

```text
1 → 2 → 3 → 4
```

Với Array, ta có thể dùng:

```text
Merge Sort
Quick Sort
Heap Sort
...
```

Nhưng với Linked List, **Merge Sort cực kỳ phù hợp** vì ta không cần random access.

---

# 2. Tại sao Merge Sort hợp với Linked List?

Với array:

```text
[4, 2, 1, 3]
```

Muốn chia đôi:

```text
[4, 2] [1, 3]
```

rất dễ vì có index.

Nhưng Linked List:

```text
4 → 2 → 1 → 3
```

không có:

```java
list[mid]
```

Muốn tìm midpoint, ta dùng:

> **Fast & Slow Pointer**

```text
slow → đi 1 bước
fast → đi 2 bước
```

Khi `fast` tới cuối:

```text
slow ≈ middle
```

Sau đó cắt list thành 2 phần.

---

# 3. Pattern tổng quát

Linked List Merge Sort có **3 bước lớn**:

```text
                Linked List
                     │
                     ▼
                  SPLIT
                     │
             ┌───────┴───────┐
             ▼               ▼
          left              right
             │               │
             ▼               ▼
          SORT             SORT
             │               │
             └───────┬───────┘
                     ▼
                   MERGE
                     │
                     ▼
                  Sorted
```

Hay nhớ:

```text
Divide → Sort → Merge
```

Recursive:

```text
sort(list)
    ↓
split
    ↓
sort(left)
sort(right)
    ↓
merge(left, right)
```

---

# 4. Implement chuẩn — Recursive

Đây là implementation mình khuyên bạn **học đầu tiên**.

```java
public ListNode sortList(ListNode head) {
    // Base case
    if (head == null || head.next == null) {
        return head;
    }

    // 1. Split
    ListNode slow = head;
    ListNode fast = head.next;

    while (fast != null && fast.next != null) {
        slow = slow.next;
        fast = fast.next.next;
    }

    ListNode right = slow.next;
    slow.next = null;

    // 2. Sort two halves
    ListNode leftSorted = sortList(head);
    ListNode rightSorted = sortList(right);

    // 3. Merge
    return merge(leftSorted, rightSorted);
}
```

Merge:

```java
private ListNode merge(ListNode left, ListNode right) {
    ListNode dummy = new ListNode(0);
    ListNode tail = dummy;

    while (left != null && right != null) {

        if (left.val <= right.val) {
            tail.next = left;
            left = left.next;
        } else {
            tail.next = right;
            right = right.next;
        }

        tail = tail.next;
    }

    if (left != null) {
        tail.next = left;
    }

    if (right != null) {
        tail.next = right;
    }

    return dummy.next;
}
```

---

# 5. Bây giờ trace toàn bộ bằng ví dụ

Ta dùng:

```text
4 → 2 → 1 → 3
```

---

## STEP 1 — Split

Ban đầu:

```text
4 → 2 → 1 → 3
```

Code:

```java
ListNode slow = head;
ListNode fast = head.next;
```

Vị trí:

```text
slow
 ↓
4 → 2 → 1 → 3
    ↑
   fast
```

---

### Loop lần 1

```java
slow = slow.next;
fast = fast.next.next;
```

Kết quả:

```text
    slow
      ↓
4 → 2 → 1 → 3
          ↑
         fast
```

`fast` đang ở `3`.

Kiểm tra:

```java
fast != null
fast.next != null
```

`fast.next == null`

→ stop.

---

## STEP 2 — Cắt list

```java
ListNode right = slow.next;
```

`slow = 2`

nên:

```text
right = 1
```

Sau đó:

```java
slow.next = null;
```

Ta có:

```text
left:

4 → 2


right:

1 → 3
```

Đây là điểm cực kỳ quan trọng.

Trước:

```text
4 → 2 → 1 → 3
```

Sau:

```text
4 → 2     1 → 3
```

---

# 6. Recursive tiếp tục

Code:

```java
ListNode leftSorted = sortList(head);
```

Tức:

```text
sortList(4 → 2)
```

và:

```java
ListNode rightSorted = sortList(right);
```

Tức:

```text
sortList(1 → 3)
```

---

# 7. Sort `4 → 2`

Split:

```text
4 → 2
```

Ta tiếp tục chia:

```text
4

2
```

Vì:

```java
head.next == null
```

nên:

```java
return head;
```

`4` đã sorted.

Tương tự `2`.

Sau đó:

```text
leftSorted = 4
rightSorted = 2
```

Merge:

```text
4
2
```

So sánh:

```text
4 > 2
```

→ lấy `2`.

```text
2
```

Sau đó `right == null`, nối phần còn lại:

```text
2 → 4
```

Vậy:

```text
sortList(4 → 2)
```

trả về:

```text
2 → 4
```

---

# 8. Sort `1 → 3`

Tương tự:

```text
1 → 3
```

chia thành:

```text
1

3
```

Merge:

```text
1 < 3
```

→

```text
1 → 3
```

Vậy:

```text
sortList(1 → 3)
```

trả về:

```text
1 → 3
```

---

# 9. Merge lớn nhất

Bây giờ recursive call đầu tiên nhận được:

```text
leftSorted:

2 → 4
```

và:

```text
rightSorted:

1 → 3
```

Ta cần merge:

```text
2 → 4

1 → 3
```

Đây chính là **Merge Pattern**.

---

# 10. Merge step-by-step

Ban đầu:

```text
left
 ↓
2 → 4

right
 ↓
1 → 3
```

Có:

```text
dummy → ?
         ↑
        tail
```

---

### Step 1

So sánh:

```text
2 vs 1
```

`1` nhỏ hơn.

Lấy `1`:

```text
dummy → 1
```

Move:

```text
right = 3
tail = 1
```

---

### Step 2

```text
left = 2
right = 3
```

So sánh:

```text
2 < 3
```

Lấy `2`:

```text
dummy → 1 → 2
```

---

### Step 3

```text
left = 4
right = 3
```

So sánh:

```text
4 > 3
```

Lấy `3`:

```text
dummy → 1 → 2 → 3
```

---

### Step 4

Bây giờ:

```text
left = 4
right = null
```

Không còn gì để compare.

Nối phần còn lại:

```java
tail.next = left;
```

→

```text
dummy → 1 → 2 → 3 → 4
```

Return:

```java
return dummy.next;
```

→

```text
1 → 2 → 3 → 4
```

---

# 11. Toàn bộ recursion tree

Với:

```text
4 → 2 → 1 → 3
```

Có thể hình dung:

```text
                 4 2 1 3
                    │
                  split
                 /     \
               4 2     1 3
               / \     / \
              4   2   1   3
               \ /     \ /
              merge   merge
                ↓       ↓
               2 4     1 3
                 \     /
                  merge
                    ↓
                 1 2 3 4
```

Đây chính là Merge Sort.

---

# 12. Tại sao `fast = head.next`?

Đây là một chi tiết bạn nên đặc biệt nhớ.

Ta dùng:

```java
slow = head;
fast = head.next;
```

thay vì:

```java
fast = head;
```

Mục đích là để `slow` dừng ở **node cuối của left half**.

Ví dụ:

```text
1 → 2 → 3 → 4
```

Ta muốn:

```text
1 → 2

3 → 4
```

Với:

```java
slow = head;
fast = head.next;
```

ta được:

```text
slow = 2
```

sau đó:

```java
right = slow.next; // 3
slow.next = null;
```

→ hoàn hảo.

---

# 13. Nếu dùng `fast = head` thì sao?

Ví dụ:

```text
1 → 2 → 3 → 4
```

Có thể khiến `slow` tiến tới:

```text
3
```

và chia thành:

```text
1 → 2 → 3

4
```

Không sai về mặt logic nếu xử lý đúng, nhưng cách:

```java
slow = head;
fast = head.next;
```

làm việc chia đôi rất tự nhiên và tránh một số edge case.

---

# 14. Merge chính là một pattern riêng

Bạn nên tách mental model:

```text
Linked List Merge Sort
        │
        ├── Split
        │    └── Fast / Slow
        │
        └── Merge
             └── Two Pointers
```

Phần Merge:

```java
private ListNode merge(ListNode left, ListNode right) {
    ListNode dummy = new ListNode(0);
    ListNode tail = dummy;

    while (left != null && right != null) {

        if (left.val <= right.val) {
            tail.next = left;
            left = left.next;
        } else {
            tail.next = right;
            right = right.next;
        }

        tail = tail.next;
    }

    tail.next = (left != null) ? left : right;

    return dummy.next;
}
```

Mental model:

```text
left:   2 → 4
right:  1 → 3

         ↓ compare

result:  1 → 2 → 3 → 4
```

Luôn lấy **node nhỏ hơn ở đầu hai list**.

---

# 15. Tại sao Merge không cần tạo Node mới?

Đây là ưu điểm lớn của Linked List.

Ví dụ:

```text
left:

2 → 4


right:

1 → 3
```

Khi lấy `1`:

```java
tail.next = right;
right = right.next;
```

Ta **không tạo node mới**.

Chỉ thay đổi pointer:

```text
dummy → 1
         ↑
        tail
```

Sau đó lấy `2`.

Do đó Merge có:

```text
Time:  O(n)
Extra Space: O(1)
```

---

# 16. Complexity của Recursive Merge Sort

Mỗi level phải đi qua toàn bộ `n` nodes để merge:

```text
Level 1: n
Level 2: n
Level 3: n
...
```

Số level:

```text
log n
```

Do đó:

```text
Time = O(n log n)
```

Recursive call stack:

```text
O(log n)
```

Nên:

```text
Time:  O(n log n)
Space: O(log n)
```

Lưu ý:

> Không phải O(n) space vì ta không tạo array phụ. Extra space chủ yếu là recursion stack.

---

# 17. Approach 2 — Bottom-Up Iterative Merge Sort

Có một implementation khác rất đáng biết:

> **Bottom-Up Merge Sort**

Không dùng recursion.

Ý tưởng:

Đầu tiên sort từng block size `1`:

```text
4 2 1 3
```

Merge từng cặp:

```text
[4] [2] → [2,4]

[1] [3] → [1,3]
```

Ta có:

```text
2 → 4 → 1 → 3
```

---

Sau đó block size `2`:

```text
[2,4] [1,3]
```

Merge:

```text
[1,2,3,4]
```

---

Pattern:

```text
size = 1
  ↓
merge pairs
  ↓
size = 2
  ↓
merge pairs
  ↓
size = 4
  ↓
merge pairs
  ↓
size = 8
...
```

Code:

```java
public ListNode sortList(ListNode head) {
    if (head == null || head.next == null) {
        return head;
    }

    int length = getLength(head);

    ListNode dummy = new ListNode(0);
    dummy.next = head;

    for (int size = 1; size < length; size *= 2) {

        ListNode prev = dummy;
        ListNode curr = dummy.next;

        while (curr != null) {

            ListNode left = curr;

            ListNode right = split(left, size);

            curr = split(right, size);

            ListNode[] merged = merge(left, right);

            prev.next = merged[0];
            prev = merged[1];
        }
    }

    return dummy.next;
}
```

Có thể viết helper `split` và `merge` như:

```java
private ListNode split(ListNode head, int size) {
    if (head == null) {
        return null;
    }

    for (int i = 1; head.next != null && i < size; i++) {
        head = head.next;
    }

    ListNode next = head.next;
    head.next = null;

    return next;
}
```

Merge:

```java
private ListNode[] merge(ListNode a, ListNode b) {
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

    tail.next = (a != null) ? a : b;

    while (tail.next != null) {
        tail = tail.next;
    }

    return new ListNode[]{dummy.next, tail};
}
```

### Complexity

```text
Time:  O(n log n)
Space: O(1)
```

Đây là điểm mạnh của Bottom-Up:

> **Không recursion → O(1) auxiliary space.**

---

# 18. Recursive vs Bottom-Up

|             | Recursive        | Bottom-Up            |
| ----------- | ---------------- | -------------------- |
| Dễ hiểu     | ⭐⭐⭐⭐⭐            | ⭐⭐⭐                  |
| Code        | đơn giản         | phức tạp             |
| Time        | O(n log n)       | O(n log n)           |
| Extra Space | O(log n)         | O(1)                 |
| Interview   | **Nên dùng**     | Bonus                |
| Pattern     | Divide & Conquer | Iterative Merge Sort |

Nếu interviewer không yêu cầu O(1) space:

> **Recursive là lựa chọn tốt nhất để code nhanh và ít bug.**

---

# 19. Một cách implement recursive khác

Có thể viết `split` thành helper.

```java
public ListNode sortList(ListNode head) {
    if (head == null || head.next == null) {
        return head;
    }

    ListNode right = split(head);

    ListNode leftSorted = sortList(head);
    ListNode rightSorted = sortList(right);

    return merge(leftSorted, rightSorted);
}
```

Helper:

```java
private ListNode split(ListNode head) {
    ListNode slow = head;
    ListNode fast = head.next;

    while (fast != null && fast.next != null) {
        slow = slow.next;
        fast = fast.next.next;
    }

    ListNode right = slow.next;
    slow.next = null;

    return right;
}
```

Mình thích version này hơn trong interview vì `sortList()` thể hiện rất rõ pattern:

```text
SORT
 ↓
SPLIT
 ↓
SORT LEFT
 ↓
SORT RIGHT
 ↓
MERGE
```

---

# 20. Những lỗi cực kỳ dễ mắc

## Lỗi 1 — Không cắt list

Sai:

```java
ListNode right = slow.next;
// missing:
slow.next = null;
```

Nếu không cắt:

```text
left = 4 → 2 → 1 → 3
right = 1 → 3
```

Hai list vẫn nằm trong cùng một linked list.

Recursive sẽ không có base case đúng → có thể dẫn tới infinite recursion / stack overflow.

---

## Lỗi 2 — Quên lưu `right` trước khi cắt

Phải:

```java
ListNode right = slow.next;
slow.next = null;
```

Không được:

```java
slow.next = null;
ListNode right = slow.next;
```

Vì lúc này `right == null`.

---

## Lỗi 3 — Merge nhưng không move pointer

Sai:

```java
if (left.val < right.val) {
    tail.next = left;
}
```

Phải có:

```java
left = left.next;
```

và:

```java
tail = tail.next;
```

---

# 21. Invariant của Merge

Trong merge:

```text
left:       2 → 4
right:      1 → 3
result:     1 → ...
            ↑
           tail
```

Invariant:

> `result` luôn sorted.

Mỗi iteration:

```text
min(left.head, right.head)
```

được đưa vào `result`.

Do `left` và `right` **đều đã sorted**, chỉ cần so sánh node đầu tiên.

Đây chính là lý do Merge Sort hoạt động.

---

# 22. Pattern Recognition

Khi gặp Linked List + Sort:

```text
"Sort this linked list"
```

hãy nghĩ ngay:

```text
Linked List
     ↓
Need sorting
     ↓
Merge Sort
     ↓
Fast/Slow → Split
     ↓
Recursion
     ↓
Two Pointers → Merge
```

Không nên nghĩ ngay tới Quick Sort vì partition trên Linked List phức tạp hơn và Merge Sort có guarantee:

```text
O(n log n)
```

---

# 23. Cheat Sheet

Cuối cùng, chỉ cần nhớ **3 pattern**:

### Pattern 1 — Find Middle

```java
ListNode slow = head;
ListNode fast = head.next;

while (fast != null && fast.next != null) {
    slow = slow.next;
    fast = fast.next.next;
}
```

↓

```text
slow = end of left half
```

---

### Pattern 2 — Split

```java
ListNode right = slow.next;
slow.next = null;
```

↓

```text
left  → ...
right → ...
```

---

### Pattern 3 — Merge

```java
while (left != null && right != null) {

    if (left.val <= right.val) {
        tail.next = left;
        left = left.next;
    } else {
        tail.next = right;
        right = right.next;
    }

    tail = tail.next;
}

tail.next = (left != null) ? left : right;
```

↓

```text
sorted left + sorted right
            ↓
       sorted result
```

Toàn bộ **Linked List Merge Sort** thực chất chỉ là:

```text
             SORT LIST
                 │
                 ▼
              SPLIT
          Fast / Slow
                 │
          ┌──────┴──────┐
          ▼             ▼
        LEFT          RIGHT
          │             │
       sort()        sort()
          │             │
          └──────┬──────┘
                 ▼
               MERGE
          Two Pointers
                 │
                 ▼
             SORTED LIST
```

Đây là một pattern rất đáng ghi nhớ vì nó kết hợp **3 pattern bạn đã học**: **Fast/Slow Pointer + Recursion/Divide & Conquer + Two Pointers Merge**.
