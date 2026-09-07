Với **LeetCode 25 — Reverse Nodes in k-Group**, có vài cách implement đáng học. Quan trọng nhất là hiểu **pointer structure**, vì bài này thực chất là bài tổng hợp của:

* Reverse Linked List
* Find `k` nodes
* Reconnect các group
* Xử lý group cuối không đủ `k`

Mình sẽ đi từ **dễ hiểu → tối ưu/kinh điển**.

---

# 1. Đề bài

Cho linked list:

```text
1 → 2 → 3 → 4 → 5 → 6 → 7 → 8
```

và:

```text
k = 3
```

Mỗi nhóm đủ `3` node phải reverse:

```text
Group 1:
1 → 2 → 3
↓
3 → 2 → 1

Group 2:
4 → 5 → 6
↓
6 → 5 → 4

Group cuối:
7 → 8
```

Không đủ `k` node nên **giữ nguyên**.

Kết quả:

```text
3 → 2 → 1 → 6 → 5 → 4 → 7 → 8
```

---

# 2. Approach 1 — Iterative + Standard Reverse

Đây là approach mình khuyên bạn **nắm đầu tiên**.

Ý tưởng:

```text
while còn group:
    1. tìm node thứ k
    2. reverse group
    3. nối group vừa reverse vào list
```

## Code

```java
public ListNode reverseKGroup(ListNode head, int k) {
    ListNode dummy = new ListNode(0, head);

    ListNode groupPrev = dummy;

    while (true) {

        // 1. Find the kth node
        ListNode kth = getKth(groupPrev, k);

        // Không đủ k node
        if (kth == null) {
            break;
        }

        // Node phía sau group
        ListNode groupNext = kth.next;

        // 2. Reverse group
        ListNode prev = groupNext;
        ListNode curr = groupPrev.next;

        while (curr != groupNext) {
            ListNode next = curr.next;

            curr.next = prev;
            prev = curr;
            curr = next;
        }

        // 3. Reconnect
        ListNode oldGroupStart = groupPrev.next;

        groupPrev.next = kth;

        groupPrev = oldGroupStart;
    }

    return dummy.next;
}
```

Helper:

```java
private ListNode getKth(ListNode curr, int k) {
    while (curr != null && k > 0) {
        curr = curr.next;
        k--;
    }

    return curr;
}
```

---

# 3. Hiểu `groupPrev`, `kth`, `groupNext`

Đây là **3 pointer quan trọng nhất**.

Ban đầu:

```text
dummy
  |
  v
  1 → 2 → 3 → 4 → 5 → 6 → 7 → 8
  ^
groupPrev
```

Với `k = 3`:

```text
groupPrev
   |
   v
dummy → 1 → 2 → 3 → 4 → 5 → 6 → 7 → 8
                  ^
                 kth
```

`kth = 3`.

Sau đó:

```java
ListNode groupNext = kth.next;
```

nên:

```text
dummy → 1 → 2 → 3 → 4 → 5 → 6 → 7 → 8
                  ^    ^
                 kth  groupNext
```

Hay:

```text
current group:

1 → 2 → 3

groupNext:

4 → 5 → 6 → 7 → 8
^
```

`groupNext` cực kỳ quan trọng vì nó đánh dấu:

> **Điểm kết thúc của group hiện tại.**

---

# 4. Trace code — Group đầu tiên

Ban đầu:

```text
dummy → 1 → 2 → 3 → 4 → 5 → 6 → 7 → 8
         ^
    groupPrev
```

Tìm `kth`:

```text
kth = 3
```

Ta có:

```text
groupNext = 4
```

---

## Reverse

Code:

```java
ListNode prev = groupNext;
ListNode curr = groupPrev.next;
```

Nên:

```text
prev = 4
curr = 1
```

Hình dung:

```text
prev
 ↓
 4 → 5 → 6 → 7 → 8

curr
 ↓
 1 → 2 → 3 → 4
```

---

### Iteration 1

```java
ListNode next = curr.next;
```

```text
next = 2
```

Sau đó:

```java
curr.next = prev;
```

`1.next = 4`

```text
1 → 4 → 5 → 6 → 7 → 8
```

Sau:

```java
prev = curr;
curr = next;
```

Ta có:

```text
prev = 1
curr = 2
```

---

### Iteration 2

```text
prev = 1
curr = 2
```

```java
next = curr.next;
```

```text
next = 3
```

Sau:

```java
curr.next = prev;
```

Tức:

```text
2 → 1 → 4 → 5 → 6 → 7 → 8
```

Sau:

```text
prev = 2
curr = 3
```

---

### Iteration 3

```text
prev = 2
curr = 3
next = 4
```

Thực hiện:

```java
curr.next = prev;
```

→

```text
3 → 2 → 1 → 4 → 5 → 6 → 7 → 8
```

Sau đó:

```text
prev = 3
curr = 4
```

Vì:

```text
curr == groupNext
```

nên reverse kết thúc.

---

# 5. Nhưng list vẫn chưa được nối đúng!

Hiện tại:

```text
dummy → 1

3 → 2 → 1 → 4 → 5 → 6 → 7 → 8
```

Ta cần:

```text
dummy → 3 → 2 → 1 → 4 → 5 → 6 → 7 → 8
```

Đây chính là phần:

```java
ListNode oldGroupStart = groupPrev.next;

groupPrev.next = kth;

groupPrev = oldGroupStart;
```

Trước reverse:

```text
groupPrev.next = 1
```

Nên:

```java
oldGroupStart = 1;
```

Sau reverse:

```text
kth = 3
```

Ta làm:

```java
groupPrev.next = kth;
```

→

```text
dummy → 3 → 2 → 1 → 4 → 5 → 6 → 7 → 8
```

---

# 6. Tại sao `groupPrev = oldGroupStart`?

Đây là chỗ rất dễ nhầm.

Sau khi reverse:

```text
1 → 2 → 3
```

trở thành:

```text
3 → 2 → 1
```

Node `1` ban đầu là **head**:

```text
1 → 2 → 3
^
head
```

Sau reverse, `1` trở thành **tail**:

```text
3 → 2 → 1
         ^
        tail
```

Do đó:

```java
groupPrev = oldGroupStart;
```

tức:

```text
groupPrev = 1
```

Bây giờ:

```text
3 → 2 → 1 → 4 → 5 → 6 → 7 → 8
         ^
      groupPrev
```

`groupPrev` đang đứng ngay trước group tiếp theo:

```text
        group 2
           ↓
3 → 2 → 1 → 4 → 5 → 6 → 7 → 8
         ^
    groupPrev
```

---

# 7. Xử lý Group 2

Bây giờ:

```text
groupPrev = 1
```

Tìm `kth` với `k = 3`:

```text
3 → 2 → 1 → 4 → 5 → 6 → 7 → 8
         ↑         ↑
    groupPrev     kth
```

Tức:

```text
group:

4 → 5 → 6
```

và:

```text
groupNext = 7
```

Reverse:

```text
4 → 5 → 6
```

thành:

```text
6 → 5 → 4
```

Reconnect:

```text
3 → 2 → 1 → 6 → 5 → 4 → 7 → 8
```

Sau đó:

```text
groupPrev = 4
```

---

# 8. Group cuối

Còn:

```text
7 → 8
```

Nhưng:

```text
k = 3
```

Không đủ 3 node.

`getKth()` trả về:

```java
null
```

nên:

```java
if (kth == null) {
    break;
}
```

Kết quả giữ nguyên:

```text
3 → 2 → 1 → 6 → 5 → 4 → 7 → 8
```

---

# 9. Invariant cực kỳ quan trọng

Trong khi chạy, ta luôn có:

```text
[processed] → [current group] → [unprocessed]
```

Ví dụ giữa quá trình:

```text
3 → 2 → 1 → 4 → 5 → 6 → 7 → 8
          ↑
      groupPrev
```

Ta có:

```text
[3 → 2 → 1] [4 → 5 → 6] [7 → 8]
  processed      current    unprocessed
```

Sau khi reverse:

```text
[3 → 2 → 1] [6 → 5 → 4] [7 → 8]
```

Tiếp tục:

```text
[3 → 2 → 1 → 6 → 5 → 4] [7 → 8]
        processed             remaining
```

Đây là mental model rất tốt để debug bài.

---

# 10. Approach 2 — Head Insert

Vì bạn vừa học **Head Insert Technique**, bài này thực ra có thể giải bằng chính technique đó.

Thay vì standard reverse:

```text
prev
curr
next
```

ta giữ:

```text
groupPrev
current
```

và liên tục lấy node sau `current` đưa lên đầu group.

Code:

```java
public ListNode reverseKGroup(ListNode head, int k) {
    ListNode dummy = new ListNode(0, head);

    ListNode groupPrev = dummy;

    while (true) {

        // Find kth node
        ListNode kth = groupPrev;

        for (int i = 0; i < k; i++) {
            kth = kth.next;

            if (kth == null) {
                return dummy.next;
            }
        }

        ListNode current = groupPrev.next;

        // Head Insert
        for (int i = 0; i < k - 1; i++) {

            ListNode move = current.next;

            // Detach move
            current.next = move.next;

            // Insert move at head
            move.next = groupPrev.next;
            groupPrev.next = move;
        }

        // current is now the tail
        groupPrev = current;
    }
}
```

Đây là implementation rất đẹp nếu bạn đã hiểu Head Insert.

---

# 11. Trace Head Insert

Ví dụ:

```text
1 → 2 → 3 → 4 → 5
k = 3
```

Ban đầu:

```text
groupPrev
    ↓
dummy → 1 → 2 → 3 → 4 → 5
         ↑
       current
```

---

### Iteration 1

```java
move = current.next;
```

```text
move = 2
```

Detach:

```java
current.next = move.next;
```

→

```text
1 → 3 → 4 → 5

2
```

Insert:

```java
move.next = groupPrev.next;
groupPrev.next = move;
```

→

```text
dummy → 2 → 1 → 3 → 4 → 5
```

---

### Iteration 2

`current` vẫn là:

```text
1
```

`move`:

```text
move = 3
```

Detach:

```text
2 → 1 → 4 → 5

3
```

Insert `3` lên đầu:

```text
dummy → 3 → 2 → 1 → 4 → 5
```

Done `k - 1 = 2` lần.

Ta có:

```text
3 → 2 → 1 → 4 → 5
```

và:

```text
groupPrev = current
           = 1
```

---

# 12. Tại sao Head Insert chỉ chạy `k - 1` lần?

Đây là điểm rất quan trọng.

Group:

```text
1 → 2 → 3 → 4
```

Node đầu tiên:

```text
1
```

đã nằm đúng vị trí cuối cùng sau reverse:

```text
4 → 3 → 2 → 1
```

Ta chỉ cần lấy:

```text
2
3
4
```

và lần lượt đưa lên đầu.

Do đó:

```text
k nodes
↓
k - 1 lần move
```

Ví dụ `k = 4`:

```text
1 → 2 → 3 → 4

move 2 → 2 → 1
move 3 → 3 → 2 → 1
move 4 → 4 → 3 → 2 → 1
```

---

# 13. So sánh 2 implementation

|               | Standard Reverse       | Head Insert                    |
| ------------- | ---------------------- | ------------------------------ |
| Technique     | 3 pointers             | Head Insert                    |
| Pointer chính | `prev`, `curr`, `next` | `groupPrev`, `current`, `move` |
| `current`     | di chuyển              | **cố định**                    |
| `groupPrev`   | di chuyển giữa groups  | di chuyển giữa groups          |
| Reverse       | đảo từng `next`        | lấy node đưa lên đầu           |
| Số lần        | `k`                    | `k-1`                          |
| Space         | O(1)                   | O(1)                           |
| Time          | O(n)                   | O(n)                           |

---

# 14. Approach 3 — Recursive

Đây là implementation kinh điển khác.

Ý tưởng:

```text
reverse first k nodes
        ↓
reverse phần còn lại
        ↓
connect
```

Code:

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

    // Reverse first k nodes
    ListNode prev = null;
    ListNode curr = head;

    for (int i = 0; i < k; i++) {
        ListNode next = curr.next;

        curr.next = prev;
        prev = curr;
        curr = next;
    }

    // head is now the tail of reversed group
    head.next = reverseKGroup(curr, k);

    return prev;
}
```

---

# 15. Trace Recursive

Ví dụ:

```text
1 → 2 → 3 → 4 → 5 → 6
k = 3
```

Call:

```text
reverseKGroup(1)
```

Reverse:

```text
1 → 2 → 3
```

thành:

```text
3 → 2 → 1
```

`curr`:

```text
4
```

Sau đó:

```java
head.next = reverseKGroup(curr, k);
```

tức:

```java
1.next = reverseKGroup(4, 3);
```

Call thứ 2:

```text
4 → 5 → 6
```

reverse:

```text
6 → 5 → 4
```

Rồi:

```text
4.next = reverseKGroup(null, 3)
```

→ `null`

Cuối cùng:

```text
6 → 5 → 4
```

được nối vào:

```text
3 → 2 → 1
```

Kết quả:

```text
3 → 2 → 1 → 6 → 5 → 4
```

---

# 16. Khi nào dùng approach nào?

Nếu đi phỏng vấn, mình recommend:

### Level 1 — Hiểu bài

```text
Find k
↓
Reverse k
↓
Reconnect
↓
Repeat
```

### Level 2 — Implement

Ưu tiên:

> **Standard iterative**

vì dễ kiểm soát pointer và không dùng recursion.

### Level 3 — Pattern recognition

Bạn nên nhận ra:

> **Reverse in k-group có thể giải bằng Head Insert.**

Đặc biệt với kiến thức bạn vừa học:

```text
Head Insert
     ↓
Reverse Segment
     ↓
Reverse k-group
```

### Level 4 — Alternative

Biết thêm:

> **Recursive**

để hiểu cách chia bài thành các subproblem.

---

## Cheat sheet cần nhớ

```text
Reverse Nodes in k-Group

1. Find k-th node
       ↓
2. Nếu không đủ k → stop
       ↓
3. Save groupNext
       ↓
4. Reverse current group
       ↓
5. Connect previous group
       ↓
6. old group head → trở thành tail
       ↓
7. groupPrev = old group head
       ↓
8. Repeat
```

Và nếu dùng **Head Insert**:

```text
current = group head

repeat k - 1 times:

    move = current.next

    current.next = move.next

    move.next = groupPrev.next

    groupPrev.next = move
```

Mental model:

```text
Before:

groupPrev → 1 → 2 → 3 → 4
             ↑
           current


Move 2:

groupPrev → 2 → 1 → 3 → 4
                  ↑
                current


Move 3:

groupPrev → 3 → 2 → 1 → 4
                       ↑
                     current
```

**`current` đứng yên, còn `move` liên tục bị lấy ra và đưa lên đầu.** Đây chính là Head Insert Technique mà bạn vừa học, và nó là một cách rất tự nhiên để giải `Reverse Nodes in k-Group`.
