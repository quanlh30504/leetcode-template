Với bài **Sort a Linked List** — điển hình là **LeetCode 148. Sort List** — có khá nhiều approach. Điểm quan trọng là Linked List khác Array ở chỗ **không có random access `O(1)`**, nên một số thuật toán sort quen thuộc sẽ không còn tối ưu.

Ta xét:

```text
4 → 2 → 1 → 3
```

Mục tiêu:

```text
1 → 2 → 3 → 4
```

---

# 1. Approach 1 — Convert Linked List → Array → Sort

## Tư tưởng

Vì Array hỗ trợ random access tốt, ta có thể:

```text
Linked List
    ↓
Array / ArrayList
    ↓
Arrays.sort()
    ↓
Linked List
```

Ví dụ:

```text
4 → 2 → 1 → 3

    ↓

[4, 2, 1, 3]

    ↓ sort

[1, 2, 3, 4]

    ↓

1 → 2 → 3 → 4
```

### Implement

```java
public ListNode sortList(ListNode head) {
    if (head == null || head.next == null) {
        return head;
    }

    List<Integer> values = new ArrayList<>();

    ListNode curr = head;

    while (curr != null) {
        values.add(curr.val);
        curr = curr.next;
    }

    Collections.sort(values);

    curr = head;
    int i = 0;

    while (curr != null) {
        curr.val = values.get(i++);
        curr = curr.next;
    }

    return head;
}
```

## Luồng chạy

Với:

```text
4 → 2 → 1 → 3
```

### Bước 1 — copy value

```text
values = [4, 2, 1, 3]
```

### Bước 2 — sort

```text
values = [1, 2, 3, 4]
```

### Bước 3 — ghi ngược vào Linked List

```text
head
 ↓
4 → 2 → 1 → 3
```

Gán:

```text
4 → 1
2 → 2
1 → 3
3 → 4
```

Kết quả:

```text
1 → 2 → 3 → 4
```

### Complexity

```text
Time  = O(n log n)
Space = O(n)
```

### Ưu / nhược

Ưu:

* Rất đơn giản.
* Dễ implement.

Nhược:

* Tốn `O(n)` memory.
* Ta đang sort **value**, không thực sự sort các node.
* Không phải solution đẹp nhất cho bài Linked List.

> Trong interview, có thể nói đây là baseline solution, sau đó chuyển sang Merge Sort để tận dụng cấu trúc Linked List.

---

# 2. Approach 2 — Insertion Sort

Đây là approach khá tự nhiên với Linked List.

## Tư tưởng

Insertion Sort xây dựng một phần đã sorted.

Ví dụ:

```text
4 → 2 → 1 → 3
```

Ta coi:

```text
4
```

đã sorted.

Lấy `2`:

```text
4
↑
insert 2

→ 2 → 4
```

Lấy `1`:

```text
2 → 4

insert 1

→ 1 → 2 → 4
```

Lấy `3`:

```text
1 → 2 → 4

insert 3

→ 1 → 2 → 3 → 4
```

Pattern:

```text
sorted part | current | unprocessed

[ sorted ] → current → [ remaining ]
```

---

## Implement

```java
public ListNode insertionSortList(ListNode head) {
    ListNode dummy = new ListNode(0);

    ListNode curr = head;

    while (curr != null) {
        ListNode next = curr.next;

        ListNode prev = dummy;

        while (prev.next != null &&
               prev.next.val <= curr.val) {
            prev = prev.next;
        }

        curr.next = prev.next;
        prev.next = curr;

        curr = next;
    }

    return dummy.next;
}
```

---

## Giải thích cực kỳ quan trọng

Có 3 pointer chính:

```text
dummy
  ↓
sorted list

prev

curr
```

Ta luôn lấy `curr` ra khỏi list:

```text
dummy → sorted → curr → remaining
```

Sau đó tìm vị trí insert:

```text
dummy → 1 → 2 → 4
             ↑
            prev

curr = 3
```

Insert:

```java
curr.next = prev.next;
prev.next = curr;
```

thành:

```text
1 → 2 → 3 → 4
```

---

## Trace đầy đủ

Input:

```text
4 → 2 → 1 → 3
```

### Iteration 1

```text
curr = 4
```

Sorted:

```text
4
```

```text
dummy → 4
```

---

### Iteration 2

```text
curr = 2
```

Tìm vị trí:

```text
dummy → 4
          ↑
          prev
```

`4 <= 2` false.

Insert trước `4`:

```text
dummy → 2 → 4
```

---

### Iteration 3

```text
curr = 1
```

`1` nhỏ hơn `2`.

```text
dummy → 1 → 2 → 4
```

---

### Iteration 4

```text
curr = 3
```

Ta đi:

```text
dummy → 1 → 2 → 4
             ↑
            prev
```

`2 <= 3` → đi tiếp.

`4 <= 3` → stop.

Insert `3` trước `4`:

```text
dummy → 1 → 2 → 3 → 4
```

---

## Complexity

Worst case:

```text
Time = O(n²)
Space = O(1)
```

Ví dụ list đã reverse:

```text
5 → 4 → 3 → 2 → 1
```

Mỗi node phải tìm gần như toàn bộ sorted list.

### Khi nào dùng?

Nếu đề có:

```text
n nhỏ
```

hoặc cần:

```text
O(1) extra space
```

thì Insertion Sort là một option.

Nhưng với `n` lớn, không phải lựa chọn tốt.

---

# 3. Approach 3 — Merge Sort ⭐ Recommended

Đây là **solution chuẩn nhất cho Sort Linked List**.

LeetCode 148 thường nên nghĩ ngay:

```text
Linked List + Sorting
        ↓
    Merge Sort
```

Tại sao?

Merge Sort cần 2 operation:

```text
1. Split
2. Merge
```

Cả hai đều rất hợp với Linked List.

---

# 3.1. Tư tưởng tổng quát

Input:

```text
4 → 2 → 1 → 3
```

Split:

```text
4 → 2       1 → 3
```

Split tiếp:

```text
4    2      1    3
```

Mỗi node đơn lẻ tự nó đã sorted.

Sau đó merge:

```text
4 + 2
 ↓
2 → 4
```

và:

```text
1 + 3
 ↓
1 → 3
```

Cuối cùng:

```text
2 → 4
   +
1 → 3

↓

1 → 2 → 3 → 4
```

Pattern:

```text
              4 → 2 → 1 → 3
                     │
                   SPLIT
                 /       \
              4 → 2      1 → 3
              /   \      /   \
             4     2    1     3
              \   /      \   /
              2→4        1→3
                 \       /
                  \     /
                 1→2→3→4
```

---

# 3.2. Có 2 bài toán con

## A. Split

Dùng:

```text
Fast / Slow Pointer
```

```java
ListNode slow = head;
ListNode fast = head.next;

while (fast != null && fast.next != null) {
    slow = slow.next;
    fast = fast.next.next;
}
```

Sau đó:

```java
ListNode right = slow.next;
slow.next = null;
```

Đây là đoạn **cực kỳ quan trọng**.

Ví dụ:

```text
4 → 2 → 1 → 3
    ↑
   slow
```

Sau khi:

```java
right = slow.next;
slow.next = null;
```

ta có:

```text
left:

4 → 2 → null


right:

1 → 3 → null
```

---

# 3.3. Sort recursively

```java
ListNode leftSorted = sortList(head);
ListNode rightSorted = sortList(right);
```

Sau khi sort:

```text
leftSorted:

2 → 4


rightSorted:

1 → 3
```

---

# 3.4. Merge

Bây giờ merge hai list đã sorted:

```text
2 → 4

1 → 3
```

Dùng Two Pointers:

```text
left = 2
right = 1
```

So sánh:

```text
1 < 2
```

lấy `1`.

```text
result:

1
```

Tiếp:

```text
left = 2
right = 3
```

```text
2 < 3
```

lấy `2`.

```text
result:

1 → 2
```

Tiếp:

```text
left = 4
right = 3
```

lấy `3`.

```text
result:

1 → 2 → 3
```

Cuối cùng:

```text
4
```

Kết quả:

```text
1 → 2 → 3 → 4
```

---

# 3.5. Full implementation

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
    } else {
        tail.next = right;
    }

    return dummy.next;
}
```

---

# 3.6. Luồng code từ đầu đến cuối

Input:

```text
4 → 2 → 1 → 3
```

Gọi:

```java
sortList(4)
```

### Level 1

Split:

```text
4 → 2 | 1 → 3
```

Gọi:

```text
sortList(4 → 2)
sortList(1 → 3)
```

---

### Left branch

```text
4 → 2
```

Split:

```text
4 | 2
```

Sort:

```text
4
2
```

Merge:

```text
2 → 4
```

---

### Right branch

```text
1 → 3
```

Split:

```text
1 | 3
```

Merge:

```text
1 → 3
```

---

### Final merge

```text
left:

2 → 4


right:

1 → 3
```

Process:

```text
compare 2, 1
→ take 1

1
```

```text
compare 2, 3
→ take 2

1 → 2
```

```text
compare 4, 3
→ take 3

1 → 2 → 3
```

`right == null`

Attach remaining left:

```text
1 → 2 → 3 → 4
```

---

# 3.7. Complexity

Mỗi level:

```text
Merge = O(n)
```

Số level:

```text
log n
```

Do đó:

```text
Time = O(n log n)
```

Recursive stack:

```text
O(log n)
```

Không cần tạo Array:

```text
Auxiliary space = O(log n)
```

Đây là solution **nên ưu tiên trong interview**.

---

# 4. Approach 4 — Bottom-Up Merge Sort ⭐

Có một biến thể rất quan trọng:

> **Iterative / Bottom-Up Merge Sort**

Thay vì recursion:

```text
divide
divide
divide
...
merge
merge
merge
```

ta bắt đầu từ những list có size `1`.

---

## Tư tưởng

Input:

```text
4 → 2 → 1 → 3
```

Ban đầu mỗi node là một sorted list:

```text
[4] [2] [1] [3]
```

### size = 1

Merge từng cặp:

```text
[4] + [2] → [2,4]

[1] + [3] → [1,3]
```

Ta có:

```text
2 → 4 → 1 → 3
```

nhưng conceptually là:

```text
[2,4] [1,3]
```

### size = 2

Merge:

```text
[2,4] + [1,3]
```

→

```text
[1,2,3,4]
```

---

# 4.1. Pattern

```text
size = 1
    ↓
merge pairs

size = 2
    ↓
merge pairs

size = 4
    ↓
merge pairs

size = 8
    ↓
...
```

Đây chính là:

```text
1 → 2 → 4 → 8 → 16 ...
```

---

# 4.2. Implementation

```java
public ListNode sortList(ListNode head) {
    if (head == null || head.next == null) {
        return head;
    }

    int length = getLength(head);

    ListNode dummy = new ListNode(0, head);

    for (int size = 1; size < length; size *= 2) {

        ListNode prev = dummy;
        ListNode curr = dummy.next;

        while (curr != null) {

            ListNode left = curr;

            ListNode right = split(left, size);

            curr = split(right, size);

            ListNode merged = merge(left, right);

            prev.next = merged;

            while (prev.next != null) {
                prev = prev.next;
            }
        }
    }

    return dummy.next;
}
```

Helper `split`:

```java
private ListNode split(ListNode head, int size) {
    if (head == null) {
        return null;
    }

    for (int i = 1; head.next != null && i < size; i++) {
        head = head.next;
    }

    ListNode second = head.next;
    head.next = null;

    return second;
}
```

Helper `merge`:

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

---

## Complexity

```text
Time  = O(n log n)
Space = O(1)
```

Điểm rất hay:

```text
Recursive Merge Sort:
O(n log n) time
O(log n) stack

Bottom-Up Merge Sort:
O(n log n) time
O(1) extra space
```

Tuy nhiên implementation phức tạp hơn đáng kể.

---

# 5. So sánh các approach

| Approach             |         Time | Extra Space | Độ khó | Khi dùng        |
| -------------------- | -----------: | ----------: | ------ | --------------- |
| Array + Sort         | `O(n log n)` |      `O(n)` | ⭐      | Baseline        |
| Insertion Sort       |      `O(n²)` |      `O(1)` | ⭐⭐     | n nhỏ           |
| Recursive Merge Sort | `O(n log n)` |  `O(log n)` | ⭐⭐⭐    | **Recommended** |
| Bottom-Up Merge Sort | `O(n log n)` |      `O(1)` | ⭐⭐⭐⭐   | Cần O(1) space  |

---

# 6. Pattern quan trọng cần nhớ

Với **Linked List Sorting**, đừng cố nhớ cả đống code. Hãy nhớ pattern:

```text
              SORT LINKED LIST
                     │
                     ▼
                MERGE SORT
                     │
          ┌──────────┴──────────┐
          ▼                     ▼
        SPLIT                  MERGE
          │                     │
     Fast / Slow           Two Pointers
          │                     │
          ▼                     ▼
     left | right        sorted left + right
```

Cụ thể:

### Split

```java
ListNode slow = head;
ListNode fast = head.next;

while (fast != null && fast.next != null) {
    slow = slow.next;
    fast = fast.next.next;
}

ListNode right = slow.next;
slow.next = null;
```

### Recursive Sort

```java
ListNode leftSorted = sortList(head);
ListNode rightSorted = sortList(right);
```

### Merge

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
```

---

# 7. Một insight rất đáng nhớ cho interview

Đây là lý do **Merge Sort đặc biệt phù hợp với Linked List**:

### Array

Merge Sort:

```text
O(n log n)
```

nhưng Linked List lại có lợi thế:

```text
Split
→ chỉ cần thay đổi pointer

Merge
→ chỉ cần thay đổi pointer
```

Không cần:

```text
shift elements
```

như khi Insert vào Array.

Ví dụ merge:

```text
1 → 4 → 7

2 → 3 → 8
```

Ta không cần tạo node mới.

Chỉ cần:

```text
1 → 2 → 3 → 4 → 7 → 8
```

bằng cách thay đổi `next`.

Đó chính là lý do khi gặp:

> **"Sort a Linked List in O(n log n)"**

thì phản xạ nên là:

```text
Linked List
     ↓
Merge Sort
     ↓
Fast/Slow → Split
     +
Two Pointer → Merge
```

**Nếu phỏng vấn LeetCode 148, mình sẽ ưu tiên Recursive Merge Sort.** Bottom-Up Merge Sort chỉ nên đưa ra khi interviewer hỏi thêm về việc giảm auxiliary space từ `O(log n)` xuống `O(1)`.
