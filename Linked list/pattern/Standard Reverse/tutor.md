Đúng pattern **Standard Reverse Linked List** nên được học như một template độc lập, vì nó là nền tảng cho rất nhiều bài như Reverse Between, Reverse K-Group, Palindrome Linked List,...

# 1. Standard Reverse — Pattern chuẩn

Cho:

```text
1 → 2 → 3 → 4 → 5 → null
```

Muốn:

```text
5 → 4 → 3 → 2 → 1 → null
```

Pattern chuẩn dùng **3 pointer**:

```text
prev
curr
next
```

Template:

```java
ListNode prev = null;
ListNode curr = head;

while (curr != null) {
    ListNode next = curr.next;

    curr.next = prev;

    prev = curr;
    curr = next;
}

return prev;
```

Đây là template bạn nên **nhớ thuộc**.

---

# 2. Vai trò của 3 pointer

Ban đầu:

```text
prev = null

curr
 ↓
1 → 2 → 3 → 4 → 5 → null
```

Ý nghĩa:

```text
prev = phần đã reverse
curr = node đang xử lý
next = phần chưa xử lý
```

Invariant:

```text
[ REVERSED ] ← prev

[ CURRENT ] → [ UNPROCESSED ]
                ↑
               next
```

Cụ thể:

```text
prev        curr
 ↓           ↓
1 ← ...     2 → 3 → 4 → 5
```

Sau mỗi iteration:

> Lấy `curr`, đảo hướng `curr.next` về `prev`, rồi tiến cả hai pointer.

---

# 3. 4 bước chuẩn trong mỗi iteration

Đây là phần quan trọng nhất.

```java
ListNode next = curr.next;
```

### Step 1 — Save next

```text
curr
 ↓
2 → 3 → 4 → 5
    ↑
   next
```

Ta phải lưu `3`.

Tại sao?

Vì ngay sau đó ta sẽ phá:

```text
2 → 3
```

---

### Step 2 — Reverse pointer

```java
curr.next = prev;
```

Ví dụ:

```text
prev = 1
curr = 2
```

Trước:

```text
1    2 → 3
↑    ↑
prev curr
```

Sau:

```text
1 ← 2    3
     ↑
    curr
```

Ta vừa đảo:

```text
2 → 3
```

thành:

```text
2 → 1
```

---

### Step 3 — Move `prev`

```java
prev = curr;
```

```text
prev
 ↓
2 → 1
```

---

### Step 4 — Move `curr`

```java
curr = next;
```

```text
prev        curr
 ↓           ↓
2 → 1       3 → 4 → 5
```

Vậy một iteration luôn là:

```text
SAVE → REVERSE → MOVE PREV → MOVE CURR
```

---

# 4. Trace đầy đủ

Ví dụ:

```text
1 → 2 → 3 → 4 → 5 → null
```

## Initial

```text
prev = null
curr = 1
```

```text
null    1 → 2 → 3 → 4 → 5
 ↑      ↑
prev   curr
```

---

## Iteration 1

### Save

```java
next = curr.next;
```

```text
next = 2
```

### Reverse

```java
curr.next = prev;
```

→

```text
1 → null

2 → 3 → 4 → 5
```

### Move

```java
prev = curr;
curr = next;
```

→

```text
prev        curr
 ↓           ↓
1 → null    2 → 3 → 4 → 5
```

---

# 5. Iteration 2

```text
prev = 1
curr = 2
```

Save:

```text
next = 3
```

Reverse:

```text
2.next = 1
```

→

```text
2 → 1 → null

3 → 4 → 5
```

Move:

```text
prev = 2
curr = 3
```

---

# 6. Iteration 3

```text
prev        curr
 ↓           ↓
2 → 1       3 → 4 → 5
```

Save:

```text
next = 4
```

Reverse:

```text
3.next = 2
```

→

```text
3 → 2 → 1 → null

4 → 5
```

Move:

```text
prev = 3
curr = 4
```

---

# 7. Iteration 4

```text
prev        curr
 ↓           ↓
3 → 2 → 1   4 → 5
```

Save:

```text
next = 5
```

Reverse:

```text
4.next = 3
```

→

```text
4 → 3 → 2 → 1

5
```

Move:

```text
prev = 4
curr = 5
```

---

# 8. Iteration 5

```text
prev        curr
 ↓           ↓
4 → 3 → 2 → 1   5
```

Save:

```text
next = null
```

Reverse:

```text
5.next = 4
```

→

```text
5 → 4 → 3 → 2 → 1 → null
```

Move:

```text
prev = 5
curr = null
```

Loop kết thúc.

Return:

```java
return prev;
```

→

```text
5 → 4 → 3 → 2 → 1
```

---

# 9. Tại sao return `prev` chứ không phải `curr`?

Đây là điểm phải nhớ.

Khi loop kết thúc:

```text
curr = null
```

Trong khi:

```text
prev = new head
```

Ví dụ:

```text
5 → 4 → 3 → 2 → 1 → null
^
prev

curr = null
```

Do đó:

```java
return prev;
```

---

# 10. Pattern chuẩn dưới dạng diagram

Bạn có thể ghi nhớ như này:

```text
                curr
                 ↓
prev        A → B → C → D
 ↓
REVERSED
```

Mỗi vòng:

```text
1. next = curr.next

2. curr.next = prev

3. prev = curr

4. curr = next
```

Sau đó:

```text
          curr
           ↓
prev      B → C → D
 ↓
A
```

Tiếp tục.

Invariant:

```text
prev
 ↓
[ reversed ]

curr
 ↓
[ unprocessed ]
```

---

# 11. Tại sao phải `next` trước `curr.next = prev`?

Đây là lỗi pointer kinh điển.

Nếu:

```text
prev = 1
curr = 2
```

và ta làm ngay:

```java
curr.next = prev;
```

thì:

```text
2 → 1
```

Nhưng đường tới:

```text
3 → 4 → 5
```

đã bị mất.

Do đó bắt buộc:

```java
ListNode next = curr.next;
```

**trước khi thay đổi `curr.next`.**

Mental model:

> `next` là "dây cứu hộ" giữ phần list chưa reverse.

---

# 12. Standard Reverse khác Head Insert thế nào?

Đây là điểm liên quan trực tiếp đến những gì bạn vừa học.

## Standard Reverse

```text
prev ← curr → next
```

Dùng:

```text
prev
curr
next
```

Cả `prev` và `curr` đều di chuyển.

```text
Iteration:

prev → curr → unprocessed
 ↓       ↓
move    move
```

---

## Head Insert

```text
groupPrev → current → move → ...
```

`current` **không di chuyển**.

Ta lấy `move`:

```text
groupPrev → move → current → ...
```

Ví dụ:

```text
1 → 2 → 3 → 4
```

Head Insert:

```text
2 → 1 → 3 → 4
```

rồi:

```text
3 → 2 → 1 → 4
```

---

# 13. Hai pattern nên phân biệt

```text
STANDARD REVERSE
────────────────────────

prev ← curr → next

curr.next = prev

prev = curr
curr = next
```

vs.

```text
HEAD INSERT
────────────────────────

groupPrev → current → move

move.next = groupPrev.next
groupPrev.next = move

current vẫn đứng yên
```

### Mental model:

**Standard Reverse:**

> Tôi đi từ trái sang phải và đảo từng mũi tên.

**Head Insert:**

> Tôi giữ một node cố định và liên tục lấy node phía sau đưa lên đầu.

---

# 14. Standard Reverse có 2 biến thể quan trọng

## Variant A — Reverse toàn bộ list

```java
public ListNode reverseList(ListNode head) {
    ListNode prev = null;
    ListNode curr = head;

    while (curr != null) {
        ListNode next = curr.next;

        curr.next = prev;

        prev = curr;
        curr = next;
    }

    return prev;
}
```

Đây là **template gốc**.

---

## Variant B — Reverse một đoạn `[left, right]`

Ví dụ:

```text
1 → 2 → 3 → 4 → 5
```

`left = 2`, `right = 4`

Kết quả:

```text
1 → 4 → 3 → 2 → 5
```

Vẫn dùng Standard Reverse, nhưng phải thêm:

```text
prev
curr
next
```

và quan trọng nhất là:

```text
node trước segment
node sau segment
```

Đó là lý do `Reverse Between` phức tạp hơn Full Reverse.

---

# 15. Standard Reverse là pattern nền cho rất nhiều bài

Bạn có thể xây cây pattern như:

```text
              LINKED LIST
                   │
            STANDARD REVERSE
                   │
       ┌───────────┼────────────┐
       │           │            │
   Full List   Sub-list      K Group
       │           │            │
  LC 206       LC 92          LC 25
       │
       └──────────────┐
                      │
              Palindrome List
                  LC 234
```

Vì vậy khi gặp:

> "reverse linked list"

hãy bật ngay template:

```java
ListNode prev = null;
ListNode curr = head;

while (curr != null) {
    ListNode next = curr.next;
    curr.next = prev;
    prev = curr;
    curr = next;
}

return prev;
```

### Công thức nhớ nhanh

```text
Standard Reverse:

next = curr.next;  // SAVE
curr.next = prev;  // REVERSE
prev = curr;       // MOVE
curr = next;       // MOVE
```

**Chỉ cần nhớ 4 dòng này là bạn có thể xây rất nhiều bài Linked List nâng cao từ nó.**
