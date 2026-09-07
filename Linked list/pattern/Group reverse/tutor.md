# LeetCode 25 — Reverse Nodes in k-Group

## 1. Đề bài yêu cầu gì?

Cho một linked list:

```text
1 → 2 → 3 → 4 → 5 → 6 → 7 → 8
```

và một số:

```text
k = 3
```

Hãy **reverse các node theo từng nhóm `k` node**.

Vì `k = 3`, ta chia list thành:

```text
[1 → 2 → 3] [4 → 5 → 6] [7 → 8]
```

Reverse từng group:

```text
[3 → 2 → 1] [6 → 5 → 4] [7 → 8]
```

Kết quả:

```text
3 → 2 → 1 → 6 → 5 → 4 → 7 → 8
```

---

# 2. Điểm quan trọng nhất của đề

**Chỉ reverse những group có đủ `k` nodes.**

Ví dụ:

```text
1 → 2 → 3 → 4 → 5
k = 3
```

Chia:

```text
[1 → 2 → 3] [4 → 5]
```

Group đầu đủ 3 node:

```text
3 → 2 → 1
```

Group cuối chỉ có 2 node nên **không reverse**.

Kết quả:

```text
3 → 2 → 1 → 4 → 5
```

---

# 3. Một ví dụ lớn hơn

Hãy dùng:

```text
head = 1 → 2 → 3 → 4 → 5 → 6 → 7 → 8 → 9 → 10
k = 4
```

Chia thành:

```text
[1 → 2 → 3 → 4]
[5 → 6 → 7 → 8]
[9 → 10]
```

Reverse group 1:

```text
4 → 3 → 2 → 1
```

Reverse group 2:

```text
8 → 7 → 6 → 5
```

Group 3 chỉ có 2 node → giữ nguyên:

```text
9 → 10
```

Kết quả:

```text
4 → 3 → 2 → 1 → 8 → 7 → 6 → 5 → 9 → 10
```

---

# 4. Tư tưởng lớn của bài

Đừng nghĩ:

> "Reverse toàn bộ linked list."

Mà hãy nghĩ:

> **Find a group of k → reverse group đó → nối group vừa reverse với group tiếp theo → lặp lại.**

Có thể hình dung:

```text
Original:

[ 1  2  3  4 ] [ 5  6  7  8 ] [ 9  10 ]
      ↓ reverse       ↓ reverse
[ 4  3  2  1 ] [ 8  7  6  5 ] [ 9  10 ]
```

---

# 5. Đây là nơi Head Insert Technique xuất hiện

Giả sử:

```text
1 → 2 → 3 → 4
```

và cần reverse group này.

Ta có:

```text
prev
 ↓
1 → 2 → 3 → 4
    ↑
  current
```

Head Insert:

### Lấy `3`

```text
1 → 3 → 2 → 4
```

### Lấy `4`

```text
1 → 4 → 3 → 2
```

Nhưng với Reverse in k-Group, ta cần xử lý **nhiều group liên tiếp**.

Ví dụ:

```text
1 → 2 → 3 → 4 → 5 → 6
k = 3
```

Group 1:

```text
1 → 2 → 3
```

reverse:

```text
3 → 2 → 1
```

Sau đó phải tiếp tục group 2:

```text
4 → 5 → 6
```

reverse:

```text
6 → 5 → 4
```

Cuối cùng:

```text
3 → 2 → 1 → 6 → 5 → 4
```

---

# 6. Có 2 vấn đề phải giải quyết

Bài này thực chất có **2 bài toán con**.

### Problem 1 — Có đủ `k` node không?

Ví dụ:

```text
1 → 2 → 3 → 4 → 5
```

`k = 3`.

Khi đang ở:

```text
4 → 5
```

phải biết:

```text
remaining nodes = 2 < k
```

→ dừng và giữ nguyên.

---

### Problem 2 — Reverse group rồi nối lại

Ví dụ:

```text
1 → 2 → 3 → 4 → 5 → 6
```

Sau khi reverse group đầu:

```text
3 → 2 → 1
```

nhưng phải nối:

```text
3 → 2 → 1 → 4 → 5 → 6
```

Sau đó mới reverse group tiếp theo.

---

# 7. Cách hình dung pointer

Một cách rất dễ hiểu là mỗi group có:

```text
groupPrev
groupStart
groupEnd
groupNext
```

Ví dụ:

```text
0 → 1 → 2 → 3 → 4 → 5 → 6
↑   ↑           ↑   ↑
prev start      end next
```

Với:

```text
k = 3
```

group:

```text
1 → 2 → 3
```

Sau reverse:

```text
3 → 2 → 1
```

Ta cần nối:

```text
prev → 3 → 2 → 1 → next
```

Tức:

```text
0 → 3 → 2 → 1 → 4 → 5 → 6
```

---

# 8. Vì sao bài này khó hơn Reverse Between?

`Reverse Between` chỉ có **một đoạn**:

```text
1 → [2 → 3 → 4] → 5
```

Reverse:

```text
1 → [4 → 3 → 2] → 5
```

Xong.

Nhưng `Reverse in Groups` có:

```text
[1 → 2 → 3]
[4 → 5 → 6]
[7 → 8 → 9]
...
```

Mỗi lần reverse xong phải:

1. Xác định group tiếp theo.
2. Reverse group đó.
3. Nối group trước với group hiện tại.
4. Kiểm tra group tiếp theo có đủ `k` node không.

Nên đây là bài rất tốt để luyện **Linked List pointer manipulation**.

---

# 9. Một invariant rất quan trọng

Trong quá trình xử lý, hãy luôn nghĩ:

```text
[processed groups] → [current group] → [unprocessed groups]
```

Ví dụ:

```text
3 → 2 → 1 → 6 → 5 → 4 → 7 → 8 → 9
                  ↑
             current group
```

Nếu:

```text
k = 3
```

thì:

```text
Processed:
3 → 2 → 1

Current:
6 → 5 → 4

Unprocessed:
7 → 8 → 9
```

Sau khi xử lý current:

```text
3 → 2 → 1 → 4 → 5 → 6 → 7 → 8 → 9
```

rồi chuyển sang:

```text
Current:
7 → 8 → 9
```

---

# 10. Có một insight rất quan trọng với `k`

Giả sử:

```text
n = 10
k = 3
```

Ta có:

```text
10 / 3 = 3 groups
```

và:

```text
10 % 3 = 1
```

nên:

```text
[1 2 3]
[4 5 6]
[7 8 9]
[10]
```

Chỉ 3 group được reverse.

Node `10` giữ nguyên.

---

# 11. Pseudocode

Trước khi code Java, hãy nghĩ algorithm như sau:

```text
dummy → head

groupPrev = dummy

while:

    1. Tìm node thứ k kể từ groupPrev
       Nếu không đủ k → break

    2. Reverse group

    3. Nối group trước với group đã reverse

    4. Di chuyển groupPrev tới cuối group vừa reverse
```

Visual:

```text
groupPrev
    ↓
    0 → 1 → 2 → 3 → 4 → 5 → 6
        └───────┘
          group
```

Sau reverse:

```text
    0 → 3 → 2 → 1 → 4 → 5 → 6
              ↑
          groupPrev
```

`groupPrev` phải đi tới `1`, bởi vì `1` trở thành **tail của group vừa reverse**.

---

# 12. Một điều cực kỳ đáng chú ý

Sau khi reverse:

```text
1 → 2 → 3
```

thành:

```text
3 → 2 → 1
```

thì:

```text
groupStart = 1
```

trở thành:

```text
groupEnd
```

Nói cách khác:

```text
BEFORE

start
 ↓
1 → 2 → 3
         ↑
        end


AFTER

3 → 2 → 1
         ↑
        new end
```

Đây là lý do khi implement bài này, bạn phải cực kỳ cẩn thận với pointer đại diện cho **đầu/cuối group**.

---

# 13. Liên hệ trực tiếp với Head Insert

Nếu bạn đã hiểu bài trước, hãy nhớ:

```text
Reverse Between
       ↓
Head Insert
       ↓
Reverse một segment
```

Còn:

```text
Reverse in Groups
       ↓
Find group
       ↓
Head Insert / Reverse
       ↓
Connect group
       ↓
Find next group
       ↓
Repeat
```

Vì vậy **Reverse in Groups không phải là một technique hoàn toàn mới**.

Nó là sự kết hợp của 3 thao tác:

```text
1. Find k nodes
2. Reverse k nodes
3. Connect k nodes
```

Trong đó bước `Reverse k nodes` có thể dùng chính **Head Insert Technique** mà bạn vừa học.

---

## ⭐ Mental model nên nhớ

Với:

```text
1 → 2 → 3 → 4 → 5 → 6 → 7 → 8
k = 3
```

hãy nhìn nó như:

```text
┌─────────┐ ┌─────────┐ ┌───────┐
│ 1 2 3   │ │ 4 5 6   │ │ 7 8   │
└─────────┘ └─────────┘ └───────┘
     ↓           ↓          ×
┌─────────┐ ┌─────────┐
│ 3 2 1   │ │ 6 5 4   │
└─────────┘ └─────────┘
```

→ **Đủ `k` thì reverse. Không đủ `k` thì giữ nguyên.**

Và mỗi group sau khi reverse phải được **nối chính xác với group trước và group sau**.

Nếu bạn muốn học bài này theo đúng flow của **Head Insert Technique**, thì cách tốt nhất tiếp theo là mình có thể **trace từng dòng code của một implementation `ReverseKGroup` với list `1 → 2 → ... → 12, k = 4`**, giống cách vừa trace `Reverse Between`; lúc đó bạn sẽ thấy rõ `groupPrev`, `current`, `move`, `groupEnd` thay đổi như thế nào.
