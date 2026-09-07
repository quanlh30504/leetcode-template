Dưới đây là một **bài giảng hoàn chỉnh về Head Insert Technique** trong Linked List. Mục tiêu là sau bài này bạn có thể nhìn thấy pattern `move → detach → insert at head` và tự nhận ra nó trong các bài Linked List.

# 📚 Bài giảng: Head Insert Technique trong Linked List

---

## 1. Head Insert Technique là gì?

**Head Insert Technique** là kỹ thuật:

> Lấy một node ra khỏi vị trí hiện tại rồi **chèn node đó vào đầu của một linked-list segment**.

Ví dụ ban đầu:

```text
A → B → C → D → E
```

Ta lấy `D` ra:

```text
A → B → C → E

D
```

Sau đó insert `D` vào đầu một segment bắt đầu từ `B`:

```text
A → D → B → C → E
```

Node `D` đã được:

```text
remove → insert at head
```

Đây chính là **Head Insert**.

---

# 2. Tại sao kỹ thuật này quan trọng?

Trong Linked List, một thao tác rất thường gặp là:

> "Lấy node kế tiếp và đưa nó lên đầu."

Nếu dùng array, việc này thường tốn `O(n)` vì phải shift phần tử.

Nhưng với Linked List:

```text
A → B → C → D
```

chỉ cần thay đổi vài con trỏ:

```text
D.next = B
A.next = D
```

là thành:

```text
A → D → B → C
```

→ **O(1)** cho một lần insert.

Đây là một trong những kỹ thuật pointer manipulation quan trọng nhất khi giải Linked List.

---

# 3. Mẫu cơ bản nhất

Giả sử:

```text
prev
 ↓
 A → B → C → D
     ↑
    current
```

Ta muốn lấy `C` và đưa nó lên đầu:

```text
A → C → B → D
```

Ta cần 4 bước.

### Step 1 — lấy node cần move

```java
ListNode move = current.next;
```

Ta có:

```text
A → B → C → D
     ↑   ↑
 current move
```

---

### Step 2 — remove `move`

```java
current.next = move.next;
```

Trở thành:

```text
A → B → D
```

và:

```text
C
```

được tách ra.

---

### Step 3 — cho `move` trỏ vào head hiện tại

```java
move.next = prev.next;
```

Nếu:

```text
prev → B → ...
```

thì:

```text
C → B → ...
```

---

### Step 4 — đưa `move` vào đầu

```java
prev.next = move;
```

Kết quả:

```text
A → C → B → D
```

---

# 4. Công thức cần nhớ

Đây là pattern quan trọng:

```java
ListNode move = current.next;

current.next = move.next;

move.next = prev.next;

prev.next = move;
```

Bạn có thể ghi nhớ bằng 4 từ:

> **Take → Detach → Point → Insert**

Hoặc:

```text
1. Take
2. Remove
3. Connect
4. Insert
```

---

# 5. Hình dung bằng "đầu đoạn"

Giả sử có:

```text
prev
 ↓
P → A → B → C → D
    ↑
  segment head
```

Ta muốn lấy `C` đưa lên đầu segment.

Trước:

```text
P → A → B → C → D
    ↑
  HEAD
```

Sau:

```text
P → C → A → B → D
    ↑
  HEAD
```

Điểm quan trọng:

> `P.next` chính là **head của segment**.

Do đó:

```java
move.next = prev.next;
prev.next = move;
```

chính là:

```text
move → oldHead
prev  → move
```

---

# 6. Visualize một lần thật chậm

Ban đầu:

```text
prev
 ↓
 P → A → B → C → D
         ↑
       current
```

Ta muốn đưa `C` lên đầu.

### ① Take

```text
move = current.next
```

```text
P → A → B → C → D
        ↑   ↑
      current move
```

---

### ② Detach

```text
current.next = move.next
```

```text
P → A → B → D

C
```

---

### ③ Point backward to segment head

```text
move.next = prev.next
```

`prev.next` là `A`.

```text
C → A → B → D
```

---

### ④ Insert

```text
prev.next = move
```

```text
P → C → A → B → D
```

Done.

---

# 7. Tại sao gọi là "Head Insert"?

Vì `move` luôn được đưa vào:

```text
HEAD
 ↓
A → B → C
```

thành:

```text
HEAD
 ↓
X → A → B → C
```

Mỗi lần thêm một node, node đó trở thành **head mới**.

Ví dụ:

```text
A
```

insert `B`:

```text
B → A
```

insert `C`:

```text
C → B → A
```

insert `D`:

```text
D → C → B → A
```

Bạn có thể thấy ngay:

> **Repeated head insertion = reversal.**

Đây là insight rất quan trọng.

---

# 8. Head Insert chính là cách tạo Reverse

Giả sử:

```text
1 → 2 → 3 → 4
```

Ta muốn reverse.

Giữ `1` làm `current`.

Lấy `2`:

```text
2 → 1
```

Lấy `3`:

```text
3 → 2 → 1
```

Lấy `4`:

```text
4 → 3 → 2 → 1
```

Ta vừa reverse list bằng cách:

> **Repeatedly take the next node and insert it at the head.**

Đó chính là bản chất của rất nhiều Linked List reversal algorithms.

---

# 9. Áp dụng vào `Reverse Between`

Quay lại bài:

```text
Reverse Between
```

Ví dụ:

```text
1 → 2 → 3 → 4 → 5 → 6 → 7 → 8 → 9

left = 3
right = 7
```

Ta muốn:

```text
3 → 4 → 5 → 6 → 7
```

thành:

```text
7 → 6 → 5 → 4 → 3
```

Ta setup:

```text
prev
 ↓
 2 → 3 → 4 → 5 → 6 → 7 → 8 → 9
     ↑
   current
```

Ở đây:

```text
prev = 2
current = 3
```

`current` được giữ cố định.

---

## Iteration 1

Lấy `4`:

```text
2 → 3 → 4 → 5 → 6 → 7
    ↑
 current
```

Head insert:

```text
2 → 4 → 3 → 5 → 6 → 7
```

---

## Iteration 2

Lấy `5`:

```text
2 → 5 → 4 → 3 → 6 → 7
```

---

## Iteration 3

Lấy `6`:

```text
2 → 6 → 5 → 4 → 3 → 7
```

---

## Iteration 4

Lấy `7`:

```text
2 → 7 → 6 → 5 → 4 → 3
```

Nối với phần còn lại:

```text
1 → 2 → 7 → 6 → 5 → 4 → 3 → 8 → 9
```

---

# 10. Invariant của kỹ thuật

Đây là thứ bạn nên nhớ khi phỏng vấn.

Trong `Reverse Between`, sau mỗi iteration:

```text
prev
 ↓
[reversed part] → current → [unprocessed part]
```

Ví dụ:

### Ban đầu

```text
prev
 ↓
2 → 3 → 4 → 5 → 6 → 7 → 8
    ↑
  current
```

---

### Sau 1 iteration

```text
prev
 ↓
2 → 4 → 3 → 5 → 6 → 7 → 8
        ↑
      current
```

---

### Sau 2

```text
prev
 ↓
2 → 5 → 4 → 3 → 6 → 7 → 8
            ↑
          current
```

---

### Sau 3

```text
prev
 ↓
2 → 6 → 5 → 4 → 3 → 7 → 8
                ↑
              current
```

---

### Sau 4

```text
prev
 ↓
2 → 7 → 6 → 5 → 4 → 3 → 8
                    ↑
                  current
```

Ta luôn giữ:

```text
prev → reversed → current → unprocessed
```

Đây chính là **invariant**.

---

# 11. Tại sao `current` không di chuyển?

Đây là câu hỏi rất quan trọng.

Thông thường khi reverse Linked List, bạn quen với:

```java
prev = current;
current = current.next;
```

Tức là cả hai pointer đều di chuyển.

Nhưng Head Insert **khác**.

Ở đây:

```text
prev
 ↓
2 → 7 → 6 → 5 → 4 → 3 → 8
                    ↑
                  current
```

`current = 3` luôn đứng yên.

Tại sao?

Vì `current` chính là:

> **tail của phần đang được reverse.**

Mỗi node mới được lấy từ phía sau:

```text
8
```

và insert lên trước:

```text
7 → 6 → 5 → 4 → 3
```

Do đó tail vẫn là `3`.

---

# 12. So sánh hai kỹ thuật Reverse

## Technique 1 — Standard 3-pointer

```text
prev ← current ← next
```

Mỗi vòng:

```java
next = current.next;
current.next = prev;
prev = current;
current = next;
```

Visual:

```text
1 → 2 → 3 → 4

prev current next
 ↓     ↓     ↓
```

Các pointer liên tục di chuyển.

---

## Technique 2 — Head Insert

```text
prev → reversed → current → unprocessed
```

Mỗi vòng:

```java
move = current.next;

current.next = move.next;

move.next = prev.next;

prev.next = move;
```

Ở đây:

```text
prev       = fixed
current    = fixed
move       = changing
```

Điểm khác biệt cực kỳ quan trọng:

| Standard Reverse    | Head Insert               |
| ------------------- | ------------------------- |
| `prev` di chuyển    | `prev` cố định            |
| `current` di chuyển | `current` cố định         |
| đổi hướng `next`    | remove + insert           |
| 3 pointer chính     | `prev`, `current`, `move` |
| reverse trực tiếp   | xây reversed segment      |

---

# 13. Head Insert không chỉ dùng để Reverse

Đây mới là lý do nên học nó như một **technique**, thay vì chỉ học một bài.

Pattern:

```text
Take a node
    ↓
Detach it
    ↓
Insert it at the head
```

có thể dùng trong:

### ① Reverse một đoạn Linked List

```text
1 → 2 → 3 → 4 → 5

reverse [2,4]

1 → 4 → 3 → 2 → 5
```

### ② Reverse toàn bộ list

```text
1 → 2 → 3 → 4

→ 4 → 3 → 2 → 1
```

### ③ Di chuyển node lên đầu một segment

Ví dụ:

```text
A → B → C → D
```

move `D` lên:

```text
A → D → B → C
```

### ④ Một số bài Linked List yêu cầu rearrangement

Nếu đề có wording kiểu:

> take a node and insert it before/after another node

thì rất dễ là bài sử dụng pattern này.

---

# 14. Template tổng quát

Bạn có thể lưu template này:

```java
ListNode move = current.next;

// detach
current.next = move.next;

// insert at head
move.next = prev.next;
prev.next = move;
```

Visual:

```text
BEFORE

prev
 ↓
 P → A → B → C → D
         ↑
       current
```

Sau:

```text
AFTER

prev
 ↓
 P → C → A → B → D
```

---

# 15. Một mental model rất dễ nhớ

Hãy tưởng tượng bạn đang xếp bài:

```text
Current reversed pile:

HEAD
 ↓
4 → 3 → 2
```

Bạn lấy card tiếp theo:

```text
5
```

và đặt lên **đầu pile**:

```text
HEAD
 ↓
5 → 4 → 3 → 2
```

Tiếp:

```text
6
```

đặt lên đầu:

```text
HEAD
 ↓
6 → 5 → 4 → 3 → 2
```

Đó chính xác là Head Insert.

---

# 16. Ba câu hỏi khi gặp pattern này

Khi nhìn một Linked List algorithm, hãy tự hỏi:

### Question 1

**Node nào là node tôi muốn move?**

Thường:

```java
move = current.next;
```

---

### Question 2

**Tôi cần detach nó khỏi đâu?**

Thường:

```java
current.next = move.next;
```

---

### Question 3

**Tôi muốn insert nó vào đâu?**

Nếu insert vào head:

```java
move.next = prev.next;
prev.next = move;
```

Nếu bạn trả lời được 3 câu này, bạn gần như đã hiểu toàn bộ algorithm.

---

# 17. Cheat Sheet

```text
             HEAD INSERT
                  │
                  ▼
         Take current.next
                  │
                  ▼
             Detach it
                  │
                  ▼
       Point it to old head
                  │
                  ▼
        Make it new head
```

Code:

```java
ListNode move = current.next;

current.next = move.next;

move.next = prev.next;

prev.next = move;
```

Mental model:

```text
Before:

prev
 ↓
 P → A → B → C → D
         ↑
       current


Take C:

P → A → B → D

C


Insert C at head:

P → C → A → B → D
```

### ⭐ Core insight

> **Head Insert = repeatedly taking the next node and inserting it at the front of a segment.**

Và:

> **Repeated Head Insert naturally creates a reversed order.**

Đây chính là lý do technique này cực kỳ phù hợp cho các bài **reverse một đoạn Linked List** như `Reverse Between`.
